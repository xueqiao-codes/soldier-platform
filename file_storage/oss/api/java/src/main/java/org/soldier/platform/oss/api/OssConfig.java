package org.soldier.platform.oss.api;

public class OssConfig {
	private String accessKey;
	private String accessSecret;
	private String bucketSuffix;
	private String bucketName;
	private String endPoint;
	private String vpcDomain;
	private String outerDomain;
	
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	public String getAccessSecret() {
		return accessSecret;
	}
	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}
	
	public String getBucketSuffix() {
		return bucketSuffix;
	}
	
	public void setBucketSuffix(String bucketSuffix) {
		this.bucketSuffix = bucketSuffix;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	public String getVpcDomain() {
		return vpcDomain;
	}
	public void setVpcDomain(String vpcDomain) {
		this.vpcDomain = vpcDomain;
	}
	
	public String getOuterDomain() {
		return outerDomain;
	}
	public void setOuterDomain(String outerDomain) {
		this.outerDomain = outerDomain;
	}
	
}
