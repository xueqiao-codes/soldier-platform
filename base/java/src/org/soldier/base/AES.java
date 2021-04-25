package org.soldier.base;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	/**
	 * AES加密
	 * 
	 * @param sSrc
	 *            字符串源
	 * @param sKey
	 *            加密key， 不能为null并且长度必须16位
	 * @return null 表示加密失败，否则返回加密字符串
	 * 
	 */
	public static String encrypt(String sSrc, String sKey) {
		Assert.True(sSrc != null && !sSrc.isEmpty());
		Assert.True(sKey != null && sKey.length() == 16);
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		// "算法/模式/补码方式"
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			IvParameterSpec iv = new IvParameterSpec(
					"0102030405060708".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			// 此处使用BASE64做转码功能，同时能起到2次加密的作用
			return Base64.encodeBase64String(cipher.doFinal(sSrc.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  AES解密字符串
	 * @param sSrc 源串
	 * @param sKey 解密key
	 * @return null 解密失败， 否则返回解密字符串
	 */
	public static String decrypt(String sSrc, String sKey) {
		if (sSrc == null) {
			return null;
		}
		if (sKey == null || sKey.length() != 16) {
			return null;
		}

		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(
					"0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encryptedBytes = Base64.decodeBase64(sSrc);
			if (encryptedBytes == null) {
				return null;
			}
			return new String(cipher.doFinal(encryptedBytes));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		String cKey = "1234567890123456";
		// 需要加密的字串
		String cSrc = "Email : arix04@xxx.com";
		System.out.println(cSrc);
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = AES.encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AES.decrypt(enString, cKey);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");
		
		lStart = System.currentTimeMillis();
		enString = AES.encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
	}

}
