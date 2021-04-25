package org.soldier.platform.svr_platform.thrift;

import java.io.File;
import java.io.IOException;

import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class AFUNIXServerSocketTransport extends TServerTransport {
	private AFUNIXServerSocket serverSocket;
	private File socketFile;
	
	public AFUNIXServerSocketTransport(File socketFile) {
		this.socketFile = socketFile;
	}
	
	@Override
	public void listen() throws TTransportException {
		try {
			serverSocket = AFUNIXServerSocket.bindOn(new AFUNIXSocketAddress(socketFile));
		} catch (Throwable e) {
			throw new TTransportException(e);
		}
	}

	@Override
	public void close() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				serverSocket = null;
			}
		}
	}

	@Override
	protected TTransport acceptImpl() throws TTransportException {
		if (serverSocket == null) {
			throw new TTransportException("server socket has be closed");
		}
		
		try {
			return new TSocket(serverSocket.accept());
		} catch (Throwable e) {
			throw new TTransportException(e);
		}
	}

}
