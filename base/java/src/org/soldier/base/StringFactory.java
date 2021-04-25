package org.soldier.base;

import java.io.UnsupportedEncodingException;

/**
 *  字符串工具函数
 *  
 *  为了避免字符串API集合的相关缺陷，字符串的创建统一由工厂适配
 */
public class StringFactory {
	public static String newUtf8String(final byte[] stringCharacters){
		if(stringCharacters == null){
			return null;
		}
		
		try {
			return new String(stringCharacters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String netUtf8String(final byte[] stringCharacters, int pos) {
		if(stringCharacters == null){
			return null;
		}
		
		try {
			return new String(stringCharacters, 0, pos, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String netUtf8String(final byte[] stringCharacters, int offset, int length) {
		if(stringCharacters == null){
			return null;
		}
		
		try {
			return new String(stringCharacters, offset, length, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
