package org.soldier.platform.oss.api;

import java.util.Map;

import com.aliyun.oss.model.CannedAccessControlList;

public class HttpOption {
	private String contentType; 
	private long expireTimestamp = -1;
	private String contentEncoding; 
	private String catchControl; 
	private String contentDisposition; 
	private Map<String,String> userMetaData;
	
	private CannedAccessControlList acl;
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public long getExpireTimestamp() {
		return expireTimestamp;
	}
	public void setExpireTimestamp(long expireTimestamp) {
		this.expireTimestamp = expireTimestamp;
	}
	
	public String getContentEncoding() {
		return contentEncoding;
	}
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}
	
	public String getCatchControl() {
		return catchControl;
	}
	public void setCatchControl(String catchControl) {
		this.catchControl = catchControl;
	}
	
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	
	public Map<String,String> getUserMetaData() {
		return userMetaData;
	}
	public void setUserMetaData(Map<String,String> userMetaData) {
		this.userMetaData = userMetaData;
	}
	
	public CannedAccessControlList getAcl() {
		return acl;
	}
	public void setAcl(CannedAccessControlList acl) {
		this.acl = acl;
	}
}
