package com.hospital.util;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* 字符串 DESede(3DES) 加密 */
public class Crypto43DES{
	private static final String	Algorithm		= "DESede";					// 定义

	private static String		defaultKeyStr	= "A314BA5A3C83DES88888WSWS";	// 默认密钥

	// 封装默认密钥的加密方法
	public static byte[] encrypt(byte[] src) {
		return encryptMode(defaultKeyStr.getBytes(), src);
	}

	// 封装默认密钥的解密方法
	public static byte[] decrypt(byte[] src) {
		return decryptMode(defaultKeyStr.getBytes(), src);
	}

	// 指定密钥加密方法
	public static byte[] encrypt(byte[] src, String keyStr) {
		return encryptMode(keyStr.getBytes(), src);
	}

	// 指定密钥解密方法
	public static byte[] decrypt(byte[] src, String keyStr) {
		return decryptMode(keyStr.getBytes(), src);
	}

	// 加密算法,可用
	// DES,DESede,Blowfish
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	@SuppressWarnings("restriction")
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	@SuppressWarnings("restriction")
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else
				hs = hs + stmp;
			if (n < b.length - 1) {
				hs = hs + ":";
			}
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) {
		// 添加新安全算法,如果用JCE就要把它添加进去
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = defaultKeyStr.getBytes(); // 24字节的密钥
		//String szSrc = "This is a 3DES test. 测试";
		String szSrc = "123456";
		System.out.println("加密前的字符串gh:" + szSrc);
		byte[] encoded = encrypt(szSrc.getBytes());
		System.out.println("加密后的字符串gh:" + new String(encoded));
		String gh = new String(decrypt(encoded));
		System.out.println("gh解密后：" + gh);
	}
}