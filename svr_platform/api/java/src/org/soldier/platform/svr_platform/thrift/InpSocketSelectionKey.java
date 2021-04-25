package org.soldier.platform.svr_platform.thrift;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class InpSocketSelectionKey extends SelectionKey {
	private Selector selector;
	private InpSocket socket;
	
	private SelectionKey currentSelectionKey;
	
	public InpSocketSelectionKey(Selector selector,
			InpSocket socket) {
		this.selector = selector;
		this.socket = socket;
	}
	
	@Override
	public void cancel() {
		if (currentSelectionKey != null) {
			currentSelectionKey.cancel();
		}
	}

	@Override
	public SelectableChannel channel() {
		if ((interestOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
			return socket.getInChannel();
		}
		if ((interestOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
			return socket.getOutChannel();
		}
		return null;
	}

	@Override
	public int interestOps() {
		if (currentSelectionKey != null) {
			return currentSelectionKey.interestOps();
		}
		return 0;
	}

	@Override
	public SelectionKey interestOps(int ops) {
		try {
			if (currentSelectionKey != null) {
				currentSelectionKey.interestOps(0);
				currentSelectionKey.attach(null);
			}
			if (ops  == SelectionKey.OP_READ) {
				currentSelectionKey = socket.getInChannel().register(selector,
						ops, this);
			} else if (ops == SelectionKey.OP_WRITE) {
				currentSelectionKey = socket.getOutChannel().register(selector,
						ops, this);
			} 
			if (ops == 0) {
				currentSelectionKey = null;
			}
		} catch (ClosedChannelException e) {
			e.printStackTrace();
			currentSelectionKey = null;
		}
		return this;
	}

	@Override
	public boolean isValid() {
		if (currentSelectionKey != null) {
			return currentSelectionKey.isValid();
		}
		return false;
	}

	@Override
	public int readyOps() {
		if (currentSelectionKey != null) {
			return currentSelectionKey.readyOps();
		}
		return 0;
	}

	@Override
	public Selector selector() {
		return selector;
	}

}
