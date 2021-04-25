package org.soldier.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public final class Md5 {
	public static String toMD5(String s) {
		if (s != null) {
			try {
				byte[] bs = s.getBytes("UTF-8");
				return encrypt(bs);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String encrypt(byte[] obj) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(obj);
			byte[] bs = md5.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bs.length; i++) {
				sb.append(Integer
						.toHexString((0x000000ff & bs[i]) | 0xffffff00)
						.substring(6));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toMd5(byte[] content) {
		return encrypt(content);
	}
	
	public static String toMD5(File file) throws IOException {  
		 FileInputStream fileInputStream = null;
		 try {
			 MessageDigest MD5 = MessageDigest.getInstance("MD5");
			 fileInputStream = new FileInputStream(file);
			 byte[] buffer = new byte[8192];
			 int length;
			 while ((length = fileInputStream.read(buffer)) != -1) {
				 MD5.update(buffer, 0, length);
			 }
			 return new String(Hex.encodeHex(MD5.digest()));
		 } catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		 } finally {
			 if (fileInputStream != null){
				 fileInputStream.close();
			 }
		 }
		 return null;
	 }  
}