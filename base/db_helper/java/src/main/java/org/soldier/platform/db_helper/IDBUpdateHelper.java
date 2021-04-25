package org.soldier.platform.db_helper;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

public interface IDBUpdateHelper<T> {
	
	/**
	 * 执行流程
	 */
	public IDBUpdateHelper<T> execute() throws ErrorInfo;
	
	/**
	 *  准备操作数据，验证
	 */
	public void onPrepareData() throws ErrorInfo, Exception;
	
	
	/**
	 *  执行操作
	 */
	public void onUpdate() throws ErrorInfo, Exception;
	
	/**
	 *  获取执行结果数据
	 */
	public T getResult();
}
