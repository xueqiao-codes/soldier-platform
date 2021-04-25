package org.soldier.platform.oa.user.ao.server.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class AoSession {
	private static final int SECRET_KEY_LENGTH = 32;
	private String userName;
	private String secretKey;
	private String falconSig;
	
	private AoSession() {
	}
	
	public AoSession(String userName) {
		this.userName = userName;
		this.secretKey = generateSecretKey();
	}
	
	public void setFalconSig(String falconSig) {
		this.falconSig = falconSig;
	}
	
	public String getFalconSig() {
		return falconSig;
	}
	
	private static String generateSecretKey() {
		StringBuffer buffer = new StringBuffer(32);
		for (int index = 0; index < SECRET_KEY_LENGTH; ++index) {
			buffer.append((char)((int)'a' + RandomUtils.nextInt(26)));
		}
		return buffer.toString();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public String getSessionKey() {
		StringBuilder sessionKeyBuilder = new StringBuilder(128);
		sessionKeyBuilder.append(secretKey);
		sessionKeyBuilder.append(userName);
		
		if (falconSig != null && !falconSig.isEmpty()) {
			sessionKeyBuilder.append("|falconSig=").append(falconSig);
		}
		
		return sessionKeyBuilder.toString();
	}
	
	public static AoSession fromSessionKey(String sessionKey) {
		if (sessionKey.length() < SECRET_KEY_LENGTH) {
			return null;
		}
		
		String[] sessionSplits = StringUtils.split(sessionKey, "|");
		if (sessionSplits.length < 1) {
			return null;
		}
		
		if (sessionSplits[0].length() < SECRET_KEY_LENGTH) {
			return null;
		}
		
		AoSession session = new AoSession();
		session.secretKey = sessionSplits[0].substring(0, SECRET_KEY_LENGTH);
		session.userName = sessionSplits[0].substring(SECRET_KEY_LENGTH);
		
		for (int index = 1; index < sessionSplits.length; ++index) {
			String[] keyValues = StringUtils.split(sessionSplits[index], "=");
			if (keyValues.length >= 2) {
				if ("falconSig".equals(keyValues[0])) {
					session.falconSig = StringUtils.join(keyValues, "=", 1, keyValues.length);
				}
			}
		}
		
		return session;
	}
}
