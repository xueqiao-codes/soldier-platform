package org.soldier.platform.id_maker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker_dao.IdAllocResult;
import org.soldier.platform.id_maker_dao.IdMakerDao;
import org.soldier.platform.id_maker_dao.IdMakerDao.allocIds_args;
import org.soldier.platform.id_maker_dao.IdMakerDao.allocIds_result;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoAsyncStub;
import org.soldier.platform.svr_platform.client.IMethodCallback;


class BackgroundUpdater  {	
	private static BackgroundUpdater sInstance;
	public static BackgroundUpdater getInstance() {
		if (sInstance == null) {
			synchronized(BackgroundUpdater.class) {
				if (sInstance == null) {
					sInstance = new BackgroundUpdater();
				}
			}
		}
		return sInstance;
	}
	
	private Map<Integer, UpdateTask> sTaskMap;
	public BackgroundUpdater() {
		sTaskMap = new ConcurrentHashMap<Integer, UpdateTask>();
	}
	
	public void update(IdMakerImpl maker) {
		if (sTaskMap.containsKey(maker.getType())) {
			return ;
		}
		
		UpdateTask task = new UpdateTask(maker);
		sTaskMap.put(maker.getType(), task);
		task.execute();
	}
	
	private class UpdateTask {
		private IdMakerImpl idMaker;
		
		public UpdateTask(IdMakerImpl maker) {
			this.idMaker = maker;
		}
		
		public void execute() {
			IdMakerDaoAsyncStub stub = new IdMakerDaoAsyncStub();
			try {
				stub.allocIds(RandomUtils.nextInt(), 1000,
						idMaker.getType(), 
						new IMethodCallback<IdMakerDao.allocIds_args, IdMakerDao.allocIds_result>() {
							
							@Override
							public void onComplete(long callId, allocIds_args req,
									allocIds_result resp) {
								if (resp.isSetSuccess()) {
									IdAllocResult result = resp.getSuccess();
									idMaker.addIds(result.getBeginId(), result.getAllocSize());
									AppLog.d("UpdateTask execute success type=" + req.getType() + ", ids=["
												+ resp.getSuccess().toString() +"]");
								} else {
									AppLog.e("UpdateTask failed type=" + req.getType() 
											+ " throws " + resp.err.toString());
								}
								remove();
							}

							@Override
							public void onError(long callId, allocIds_args req,
									Exception e) {
								remove();
								AppLog.e("UpdateTask Failed, type=" + req.getType() + "," + e.getMessage(), e);
							}
							
						});
			} catch (TException e) {
				remove();
				AppLog.e(e.getMessage(), e);
			}
		}
		
		private void remove() {
			sTaskMap.remove(idMaker.getType());
		}
		
	}
}
