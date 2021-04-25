package org.soldier.platform.svr_platform.thrift;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransportException;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFUNIXSocketTransport extends TIOStreamTransport {
	 private static final Logger LOGGER = LoggerFactory.getLogger(AFUNIXSocketTransport.class.getName());
	
	private AFUNIXSocket socket;
	private File socketFile;
	
	public AFUNIXSocketTransport(File socketFile) {
		this.socketFile = socketFile;
	}
	
	@Override
	public boolean isOpen() {
		if (socket == null) {
			return false;
		}
		return socket.isConnected();
	}

	@Override
	public void open() throws TTransportException {
		if (isOpen()) {
			throw new TTransportException(TTransportException.ALREADY_OPEN, "Socket already connected.");
		}
		if (socketFile == null) {
			throw new TTransportException(TTransportException.NOT_OPEN, "Cannot open null socket file");
		}
		if (!socketFile.exists()) {
			throw new TTransportException(TTransportException.NOT_OPEN, socketFile.getAbsolutePath() + " is not existed!");
		}
		
		try {
			if (socket == null) {
				socket = AFUNIXSocket.newInstance();
			}
			socket.connect(new AFUNIXSocketAddress(socketFile));
			inputStream_ = new BufferedInputStream(socket.getInputStream(), 1024);
			outputStream_ = new BufferedOutputStream(socket.getOutputStream(), 1024);
		} catch (Throwable e) {
			throw new TTransportException(TTransportException.NOT_OPEN, e.getMessage());
		}
	}

	@Override
	public void close() {
		super.close();
		if (socket == null) {
			return ;
		}
		
		try {
			socket.close();
		} catch (Throwable e) {
			LOGGER.warn(e.getMessage(), e);
		} finally {
			socket = null;
		}
	}


}
