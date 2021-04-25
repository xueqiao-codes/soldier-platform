package org.soldier.platform.svr_platform.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class InpServerTransport extends TServerTransport {
	private long requestKey;
	private boolean running;
	
	public InpServerTransport(long requestKey) {
		this.requestKey = requestKey;
		this.running = true;
	}
	
	@Override
	public void listen() throws TTransportException {
		InpTransportManager.getInstance().getConnectQueue().registerKey(requestKey, 0);;
	}

	@Override
	public void close() {

	}

	@Override
	protected TTransport acceptImpl() throws TTransportException {
		while (running) {
			InpConnectInfo request = null;
			try {
				request = InpTransportManager.getInstance()
					.getConnectQueue().poll(requestKey, 10000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
			}
			if (request != null) {
				return new InpSocket(request.isClientBlocking(), 
				request.getInChannel(), request.getOutChannel());
			}
		}
		throw new TTransportException("server stoped");
	}
	
	@Override
	public void interrupt() {
		running = false;
//		System.out.println("interupt service for key=" + requestKey);
	}
}
