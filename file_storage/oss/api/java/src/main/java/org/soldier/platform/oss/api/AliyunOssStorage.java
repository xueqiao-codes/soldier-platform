package org.soldier.platform.oss.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.soldier.base.AES;
import org.soldier.base.logger.AppLog;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.gson.Gson;

import net.qihoo.qconf.Qconf;

public class AliyunOssStorage {
	private static final String KEY = "SECURE_OSS_KEY88";

	private OssConfig ossConfig;
	private OSSClient ossClient;

	public AliyunOssStorage(String qconfPath) throws OssFailedException {
		try {
			ossConfig = new Gson().fromJson(Qconf.getConf(qconfPath), OssConfig.class);
			ossConfig.setAccessSecret(AES.decrypt(ossConfig.getAccessSecret(), KEY));
		
			ossClient = new OSSClient(ossConfig.getEndPoint(), ossConfig.getAccessKey(), ossConfig.getAccessSecret());
			AppLog.i("AliyunOssStorage init accessKey=" + ossConfig.getAccessKey()
				+ ", endPoint=" + ossConfig.getEndPoint()
				+ ", outerDomain=" + ossConfig.getOuterDomain()
				+ ", vpcDomain=" + ossConfig.getVpcDomain()
				+ ", bucketName=" + ossConfig.getBucketName());
		} catch (Throwable e) {
			throw new OssFailedException(e.getMessage(), e);
		}
	}

	private String getBucketName(String storageKey) {
		return ossConfig.getBucketName();
	}

	private String getPath(String storageKey, String path) {
		return storageKey + "/" + path;
	}
	
	public String getOuterDomain() {
		return ossConfig.getOuterDomain();
	}
	
	public String getVpcDomain() {
		return ossConfig.getVpcDomain();
	}

	public byte[] readFile(String storageKey, String path) throws OssFailedException  {
		try {
			OSSObject photoObject = ossClient.getObject(
					getBucketName(storageKey), getPath(storageKey, path));
			InputStream contentStream = photoObject.getObjectContent();
			try {
				return IOUtils.toByteArray(contentStream);
			} finally {
				IOUtils.closeQuietly(contentStream);
			}
		} catch (OSSException e) {
			if (e.getErrorCode().equals("NoSuchKey")) {
				return null;
			} else {
				throw new OssFailedException(e.getMessage(), e);
			}
		} catch (Throwable e) {
			throw new OssFailedException(e.getMessage(), e);
		}
	}

	public String writeFile(String storageKey
			, String path, byte[] content, HttpOption option) throws OssFailedException {
		InputStream input = new ByteArrayInputStream(content);
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(content.length);
			if (option != null) {
				if (null != option.getCatchControl()) {
					meta.setCacheControl(option.getCatchControl());
				}
				if (null != option.getContentDisposition()) {
					meta.setContentDisposition(option.getContentDisposition());
				}
				if (null != option.getContentEncoding()) {
					meta.setContentEncoding(option.getContentEncoding());
				}
				if (null != option.getContentType()) {
					meta.setContentType(option.getContentType());
				}
				if (option.getExpireTimestamp() > 0) {
					Date expireDate = new Date(option.getExpireTimestamp() * 1000);
					if (expireDate.getTime() > new Date().getTime()) {
						meta.setExpirationTime(expireDate);
					}
				}
				if (null != option.getAcl()) {
					meta.setObjectAcl(option.getAcl());
				}
				if (null != option.getUserMetaData()) {
					meta.setUserMetadata(option.getUserMetaData());
				}
			}
			
			PutObjectResult result = ossClient.putObject(
				getBucketName(storageKey), getPath(storageKey, path),
				input, meta);
			return result.getETag();
		} catch (Throwable e) {
			throw new OssFailedException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}
	
	public Date getFileDate(String storageKey, String path) throws OssFailedException {
		try {
			ObjectMetadata objectMetadata = ossClient.getObjectMetadata(
				getBucketName(storageKey),
				getPath(storageKey, path));
			return objectMetadata.getLastModified();
		} catch (Throwable e) {
			throw new OssFailedException(e.getMessage(), e);
		}
	}

	public void deleteFile(String storageKey, String path) throws OssFailedException {
		try {
			ossClient.deleteObject(getBucketName(storageKey), getPath(storageKey, path));
			return;
		} catch (OSSException e) {
			if (e.getErrorCode().equals("NoSuchKey")) {
				return;
			}
			throw new OssFailedException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new OssFailedException(e.getMessage(), e);
		}
	}
	
}
