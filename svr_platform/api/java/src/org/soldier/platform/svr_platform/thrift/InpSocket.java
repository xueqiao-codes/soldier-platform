package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.soldier.base.logger.AppLog;

public class InpSocket extends TTransport {
	private long key;
	private SourceChannel inChannel;
	private SinkChannel outChannel;
	private int timeout;
	private final boolean isBlocking;
	private boolean isClient;
	
	public InpSocket(long key, boolean isBlocking) {
		this.key = key;
		this.isBlocking = isBlocking;
		this.isClient = true;
	}
	
	public InpSocket(long key, int timeout) {
		this(key, true);
		this.timeout = timeout;
	}
	

	public InpSocket(boolean isClientBlocking,
			SourceChannel inChannel, SinkChannel outChannel) {
		isBlocking = isClientBlocking;
		this.isClient = false;
		this.inChannel = inChannel;
		this.outChannel = outChannel;
	}
	
	public long getKey() {
		return key;
	}

	@Override
	public boolean isOpen() {
		if (inChannel == null || outChannel == null) {
			return false;
		}
		
		return inChannel.isOpen() && outChannel.isOpen();
	}

	@Override
	public void open() throws TTransportException {
		if (inChannel != null && outChannel != null) {
			return ;
		}
		
		if (!isClient) {
			throw new TTransportException("Server InpSocket can not call open");
		}
		
		try {
			Pipe clientPipeIn = PipeManager.getInstance().openClientIn(isBlocking);
			Pipe clientPipeOut = PipeManager.getInstance().openClientOut(isBlocking);
			
			inChannel = clientPipeIn.source();
			outChannel = clientPipeOut.sink();
						
			InpConnectInfo request = new InpConnectInfo(key, 
					clientPipeOut.source(), clientPipeIn.sink(), isBlocking);
			InpTransportManager.getInstance().getConnectQueue().add(key, request);
		} catch (Exception e) {
			throw new TTransportException(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		closeToPipeManager(true);
		inChannel = null;
		outChannel = null;
	}
	
	@Override
	public void write(byte[] buf, int offset, int len) throws TTransportException {
		try {
			outChannel.write(ByteBuffer.wrap(buf, offset, len));
		} catch (IOException e) {
			throw new TTransportException(e.getMessage(), e);
		}
	}

	
	@Override
	public void flush() throws TTransportException{
	}

	@Override
	public int read(byte[] buf, int offset, int len) throws TTransportException {
		try {
			return inChannel.read(ByteBuffer.wrap(buf, offset, len));
		} catch (IOException e) {
			throw new TTransportException(e.getMessage(), e);
		}
	}
	
	private void closeToPipeManager(boolean addCache) {
		if (inChannel != null) {
			PipeManager.getInstance().closeIn(isClient,
					isBlocking,
					inChannel, addCache);
		}
		if (outChannel != null) {
			PipeManager.getInstance().closeOut(isClient, isBlocking, outChannel, addCache);
		}
	}
	
	public void closeThorough() {
		try {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		} catch(IOException e) {
			AppLog.e(e.getMessage(), e);
		}
		closeToPipeManager(false);
		inChannel = null;
		outChannel = null;
	}

	
	public SourceChannel getInChannel() {
		return inChannel;
	}
	
	public SinkChannel getOutChannel() {
		return outChannel;
	}
	
	public int getTimeout() {
		return timeout;
	}
}
