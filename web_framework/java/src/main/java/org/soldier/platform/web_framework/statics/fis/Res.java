package org.soldier.platform.web_framework.statics.fis;

public class Res {
	private String uri;
	private String type;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(128);
		buffer.append("uri=");
		buffer.append(uri);
		buffer.append(",type=");
		buffer.append(type);
		return buffer.toString();
	}
}
