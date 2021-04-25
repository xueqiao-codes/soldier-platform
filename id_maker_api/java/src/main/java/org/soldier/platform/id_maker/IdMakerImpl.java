package org.soldier.platform.id_maker;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;

class IdMakerImpl extends IdMaker {
	private int type;
	private IdList currentIdList;
	private ReentrantLock lock;
	private int lastLevelSize;
	
	public IdMakerImpl(int type) throws IdException {
		this.type = type;
		currentIdList = new IdList();
		lock = new ReentrantLock();
		
		allocIdFromDao();
	}
	
	public int getType() {
		return type;
	}
	
	public void setLastLevelSize(int size) {
		lastLevelSize = size;
	}

	private long allocIdFromDao() throws IdException {
		IdMakerDaoStub stub = new IdMakerDaoStub();
		try {
			IdAllocResult result = stub.allocIds(RandomUtils.nextInt(), 500, type);
			addIds(result.getBeginId() + 1, result.getAllocSize() - 1);
			setLastLevelSize(result.getAllocSize());
			return result.getBeginId();
		} catch (Exception e) {
			throw new IdException("Alloc id failed, " + e.getMessage());
		}
	}
	
	@Override
	public long createId() throws IdException{
		lock.lock();
		try {
			if (currentIdList.size() > 0) {
				long result  = currentIdList.popId();
				if (currentIdList.size() < (lastLevelSize /10)) {
					BackgroundUpdater.getInstance().update(this);
				}
				return result;
			} 
		} finally {
			lock.unlock();
		}
		
		return allocIdFromDao();
	}
	
	public void addIds(long id, int size) {
		lock.lock();
		try {
			currentIdList.addZone(id, id + size - 1);
			lastLevelSize = size;
		} catch (IdException e) {
			AppLog.e(e.getMessage(), e);
		}
		lock.unlock();
	}
}
