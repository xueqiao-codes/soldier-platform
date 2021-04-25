package org.soldier.platform.svr_platform.thrift;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InpServer extends TServer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InpServer.class.getName());

	public static class Args extends AbstractServerArgs<Args> {
		public int minWorkerThreads = 5;
		public int maxWorkerThreads = Integer.MAX_VALUE;
		public ExecutorService executorService;
		public int stopTimeoutVal = 60;
		public TimeUnit stopTimeoutUnit = TimeUnit.SECONDS;

		public Args(TServerTransport transport) {
			super(transport);
		}

		public Args minWorkerThreads(int n) {
			minWorkerThreads = n;
			return this;
		}

		public Args maxWorkerThreads(int n) {
			maxWorkerThreads = n;
			return this;
		}

		public Args executorService(ExecutorService executorService) {
			this.executorService = executorService;
			return this;
		}
	}
	
	// Executor service for handling client connections
	private ExecutorService executorService_;

	// Flag for stopping the server
	// Please see THRIFT-1795 for the usage of this flag
	private volatile boolean stopped_ = false;

	private final TimeUnit stopTimeoutUnit;

	private final long stopTimeoutVal;
	private boolean shouldCloseExecutorService;

	public InpServer(Args args) {
		super(args);

		stopTimeoutUnit = args.stopTimeoutUnit;
		stopTimeoutVal = args.stopTimeoutVal;

		executorService_ = args.executorService != null ? args.executorService
				: createDefaultExecutorService(args);
		shouldCloseExecutorService = (args.executorService == null);
	}

	private static ExecutorService createDefaultExecutorService(Args args) {
		SynchronousQueue<Runnable> executorQueue = new SynchronousQueue<Runnable>();
		return new ThreadPoolExecutor(args.minWorkerThreads,
				args.maxWorkerThreads, 60, TimeUnit.SECONDS, executorQueue);
	}

	public void serve() {
		try {
			serverTransport_.listen();
		} catch (TTransportException ttx) {
			LOGGER.error("Error occurred during listening.", ttx);
			return;
		}

		// Run the preServe event
		if (eventHandler_ != null) {
			eventHandler_.preServe();
		}

		stopped_ = false;
		setServing(true);
		while (!stopped_) {
			int failureCount = 0;
			try {
				TTransport client = serverTransport_.accept();
				WorkerProcess wp = new WorkerProcess(client);
				while (true) {
					int rejections = 0;
					try {
						executorService_.execute(wp);
						break;
					} catch (RejectedExecutionException ex) {
						LOGGER.warn("ExecutorService rejected client "
								+ (++rejections) + " times(s)", ex);
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							LOGGER.warn("Interrupted while waiting to place client on"
									+ " executor queue.");
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
			} catch (TTransportException ttx) {
				if (!stopped_) {
					++failureCount;
					LOGGER.warn(
							"Transport error occurred during acceptance of message.",
							ttx);
				}
			}
		}

		close();
		

		// Loop until awaitTermination finally does return without a interrupted
		// exception. If we don't do this, then we'll shut down prematurely. We
		// want
		// to let the executorService clear it's task queue, closing client
		// sockets
		// appropriately.
		long timeoutMS = stopTimeoutUnit.toMillis(stopTimeoutVal);
		long now = System.currentTimeMillis();
		while (timeoutMS >= 0) {
			try {
				executorService_.awaitTermination(timeoutMS,
						TimeUnit.MILLISECONDS);
				break;
			} catch (InterruptedException ix) {
				long newnow = System.currentTimeMillis();
				timeoutMS -= (newnow - now);
				now = newnow;
			}
		}
		setServing(false);
	}

	public void stop() {
		stopped_ = true;
		serverTransport_.interrupt();
	}
	
	public void handleRequest(TTransport client) throws RejectedExecutionException {
		executorService_.execute(new WorkerProcess(client));
	}
	
	public void close() {
		if (shouldCloseExecutorService) {
			executorService_.shutdown();
		}
	}

	private class WorkerProcess implements Runnable {

		/**
		 * Client that this services.
		 */
		private TTransport client_;
		private InpUncaughtExceptionHandler handler;

		/**
		 * Default constructor.
		 * 
		 * @param client
		 *            Transport to process
		 */
		private WorkerProcess(TTransport client) {
			client_ = client;
			handler = new InpUncaughtExceptionHandler();
		}
		
		private class InpUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
			private Thread.UncaughtExceptionHandler originalHandler;
			private Thread attachThread;
			
			private InpUncaughtExceptionHandler() {
				
			}
			
			public void attach(Thread thread) {
				if (attachThread != null) {
					return ;
				}
				originalHandler = thread.getUncaughtExceptionHandler();
				attachThread = thread;
				
				thread.setUncaughtExceptionHandler(this);
			}
			
			public void detach() {
				attachThread.setUncaughtExceptionHandler(originalHandler);
			}
			
			
			@Override
			public void uncaughtException(Thread thread, Throwable e) {
				InpServer.LOGGER.error("uncaughtException " + e.getMessage(), e);
				WorkerProcess.this.closeClientThorough();
				
				if (originalHandler != null) {
					originalHandler.uncaughtException(thread, e);
				}
			}
			
		}
		
		private void closeClientThorough() {
			if (client_ instanceof InpSocket) {
				((InpSocket)client_).closeThorough();
			} else {
				client_.close();
			}
		}

		/**
		 * Loops on processing a client forever
		 */
		public void run() {
			TProcessor processor = null;
			TTransport inputTransport = null;
			TTransport outputTransport = null;
			TProtocol inputProtocol = null;
			TProtocol outputProtocol = null;

			TServerEventHandler eventHandler = null;
			ServerContext connectionContext = null;
			
			handler.attach(Thread.currentThread());
			
			processor = processorFactory_.getProcessor(client_);
			inputTransport = inputTransportFactory_.getTransport(client_);
			outputTransport = outputTransportFactory_.getTransport(client_);
			inputProtocol = inputProtocolFactory_
						.getProtocol(inputTransport);
			outputProtocol = outputProtocolFactory_
						.getProtocol(outputTransport);

			eventHandler = getEventHandler();
			if (eventHandler != null) {
				connectionContext = eventHandler.createContext(
						inputProtocol, outputProtocol);
			}
			// we check stopped_ first to make sure we're not supposed to be
			// shutting
			// down. this is necessary for graceful shutdown.
			if (eventHandler != null) {
				eventHandler.processContext(connectionContext,
						inputTransport, outputTransport);
			}

			try {
				processor.process(inputProtocol, outputProtocol);
			} catch (TException e) {
				closeClientThorough();
				LOGGER.error(e.getMessage(), e);
			}

			if (eventHandler != null) {
				eventHandler.deleteContext(connectionContext, inputProtocol,
						outputProtocol);
			}
			
			if (inputTransport != null) {
				inputTransport.close();
			}
			
			if (outputTransport != null) {
				outputTransport.close();
			}
			
			handler.detach();
		}
	}

}
