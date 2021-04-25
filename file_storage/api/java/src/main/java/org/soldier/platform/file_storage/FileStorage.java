package org.soldier.platform.file_storage;

import org.soldier.platform.filestorage.dao.HttpOption;

/**
 * 分布式的文件存储服务 Api
 * @author wileywang
 */
public abstract class FileStorage {
	protected int timeout = 15000;
	protected String storageKey;
	
	
	protected FileStorage(String storageKey) {
		this.storageKey = storageKey;
	}
	
	protected void checkPath(String path) throws FileStorageException {
		if (path == null || path.isEmpty()) {
			throw new FileStorageException("path is empty");
		}
		if (path.startsWith("/")) {
			throw new FileStorageException("path should not startwith /");
		}
	}
	
	/**
	 *  读取一个特定path下的文件内容
	 * @param path 形式为xxx/yyy.jpg xxx/yyy/zzz.txt, 不能以/开头
	 * @return null 表示文件不存在， 正确返回相应的内容
	 * @throws FileStorageException 意外的错误发生
	 */
	public abstract byte[] read(String path) throws FileStorageException;
	
	/**
	 *  写入数据到一个特定路径的文件中， 如果文件存在，则覆盖文件内容
	 * @param path 形式为xxx/yyy.jpg xxx/yyy/zzz.txt, 不能以/开头
	 * @param content 文件内容， 不能为null
	 * @throws FileStorageException 写入失败时抛出异常
	 */
	public abstract void write(String path, byte[] content) throws FileStorageException;
	
	
	public abstract void write(String path, byte[] content, HttpOption option) throws FileStorageException;
	
	/**
	 *  删除指定路径的文件
	 * @param path 形式为xxx/yyy.jpg xxx/yyy/zzz.txt, 不能以/开头
	 */
	public abstract void delete(String path) throws FileStorageException;
	
	/**
	 *  获取拼接的一个可以访问的链接
	 * @param path
	 * @return
	 */
	public abstract String getUrl(String path) throws FileStorageException;
	
	public enum URLMode{
		HTTP,
		HTTPS
	}
	
	public abstract String getUrl(String path, URLMode mode) throws FileStorageException;
	
	/**
	 * 设置超时时间， 默认
	 * @param timeout 超时时间，ms为单位
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public String getStorageKey() {
		return storageKey;
	}
}
