package org.soldier.platform.svr_platform.thrift;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TTransportException;
import org.soldier.base.Assert;


public class InpNonblockingSocket extends TNonblockingTransport {
	private InpSocket socket;
	
	public InpNonblockingSocket(long key) throws TTransportException {
		socket = new InpSocket(key, false);
		socket.open();
		
		Assert.True(!socket.getInChannel().isBlocking());
		Assert.True(!socket.getOutChannel().isBlocking());
	}
	
	@Override
	public boolean startConnect() throws IOException {
		throw new IOException("InpNonblockingSocket is aways connected");
	}

	@Override
	public boolean finishConnect() throws IOException {
		throw new IOException("InpNonblockingSocket is aways connected");
	}

	@Override
	public SelectionKey registerSelector(Selector selector, int interests)
			throws IOException {
		InpSocketSelectionKey key = new InpSocketSelectionKey(selector, socket);
		if (interests != SelectionKey.OP_READ && interests != SelectionKey.OP_WRITE) {
			throw new IOException("InpNonblockingSocket only support OP_READ or OP_WRITE");
		}
		return key.interestOps(interests);
	}

	@Override
	public int read(ByteBuffer buffer) throws IOException {
		return socket.getInChannel().read(buffer);
	}

	@Override
	public int write(ByteBuffer buffer) throws IOException {
		return socket.getOutChannel().write(buffer);
	}

	@Override
	public boolean isOpen() {
		return socket != null && socket.isOpen();
	}

	@Override
	public void open() throws TTransportException {
		socket.open();
	}

	@Override
	public void close() {
		socket.close();
		socket = null;
	}

	@Override
	public int read(byte[] buf, int off, int len) throws TTransportException {
		return socket.read(buf, off, len);
	}

	@Override
	public void write(byte[] buf, int off, int len) throws TTransportException {
		socket.write(buf, off, len);
	}
	
	public InpSocket getInpSocket() {
		return socket;
	}

}
