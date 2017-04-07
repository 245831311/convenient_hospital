package com.hospital.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 功能描述 加密常用类
 */
public class EncryptUtil {
	
	// 密钥是16位长度的byte[]进行Base64转换后得到的字符串
	
	/**
	 * <li>
	 * 方法名称:encrypt</li> <li>
	 * 加密方法
	 * 
	 * @param xmlStr
	 *            需要加密的消息字符串
	 * @return 加密后的字符串
	 */
	public static String encrypt(String xmlStr) {
		byte[] encrypt = null;
		
		try {
			// 取需要加密内容的utf-8编码。
			encrypt = xmlStr.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 取MD5Hash码，并组合加密数组
		byte[] md5Hasn = null;
		try {
			md5Hasn = EncryptUtil.MD5Hash(encrypt, 0, encrypt.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 组合消息体
		byte[] totalByte = EncryptUtil.addMD5(md5Hasn, encrypt);
		
		// 取密钥和偏转向量
		byte[] key = new byte[8];
		byte[] iv = new byte[8];
		SecretKeySpec deskey = new SecretKeySpec(key, "DES");
		IvParameterSpec ivParam = new IvParameterSpec(iv);
		
		// 使用DES算法使用加密消息体
		byte[] temp = null;
		try {
			temp = EncryptUtil.DES_CBC_Encrypt(totalByte, deskey, ivParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 使用Base64加密后返回
		return new BASE64Encoder().encode(temp);
	}
	
	/**
	 * <li>
	 * 方法名称:encrypt</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 解密方法
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param xmlStr
	 *            需要解密的消息字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String xmlStr) {
		// base64解码
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] encBuf = null;
		try {
			encBuf = decoder.decodeBuffer(xmlStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 取密钥和偏转向量
		byte[] key = new byte[8];
		byte[] iv = new byte[8];
		
		SecretKeySpec deskey = new SecretKeySpec(key, "DES");
		IvParameterSpec ivParam = new IvParameterSpec(iv);
		
		// 使用DES算法解密
		byte[] temp = null;
		try {
			temp = EncryptUtil.DES_CBC_Decrypt(encBuf, deskey, ivParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String decryptString = "";
		
		// 返回解密后的数组，其中前16位MD5Hash码要除去。
		try {
			decryptString = new String(temp, 16, temp.length - 16, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decryptString;
	}
	
	/**
	 * <li>
	 * 方法名称:TripleDES_CBC_Encrypt</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 经过封装的三重DES/CBC加密算法，如果包含中文，请注意编码。
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param sourceBuf
	 *            需要加密内容的字节数组。
	 * @param deskey
	 *            KEY 由24位字节数组通过SecretKeySpec类转换而成。
	 * @param ivParam
	 *            IV偏转向量，由8位字节数组通过IvParameterSpec类转换而成。
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] TripleDES_CBC_Encrypt(byte[] sourceBuf, SecretKeySpec deskey,
			IvParameterSpec ivParam) throws Exception {
		byte[] cipherByte;
		// 使用DES对称加密算法的CBC模式加密
		Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
		
		encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);
		
		cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回加密后的字节数组
		return cipherByte;
	}
	
	/**
	 * <li>
	 * 方法名称:TripleDES_CBC_Decrypt</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 经过封装的三重DES / CBC解密算法
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param sourceBuf
	 *            需要解密内容的字节数组
	 * @param deskey
	 *            KEY 由24位字节数组通过SecretKeySpec类转换而成。
	 * @param ivParam
	 *            IV偏转向量，由6位字节数组通过IvParameterSpec类转换而成。
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] TripleDES_CBC_Decrypt(byte[] sourceBuf, SecretKeySpec deskey,
			IvParameterSpec ivParam) throws Exception {
		
		byte[] cipherByte;
		// 获得Cipher实例，使用CBC模式。
		Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
		// 初始化加密实例，定义为解密功能，并传入密钥，偏转向量
		decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);
		
		cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回解密后的字节数组
		return cipherByte;
	}
	
	/**
	 * <li>
	 * 方法名称:DES_CBC_Encrypt</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 经过封装的DES/CBC加密算法，如果包含中文，请注意编码。
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param sourceBuf
	 *            需要加密内容的字节数组。
	 * @param deskey
	 *            KEY 由8位字节数组通过SecretKeySpec类转换而成。
	 * @param ivParam
	 *            IV偏转向量，由8位字节数组通过IvParameterSpec类转换而成。
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public static byte[] DES_CBC_Encrypt(byte[] sourceBuf, SecretKeySpec deskey,
			IvParameterSpec ivParam) throws Exception {
		byte[] cipherByte;
		// 使用DES对称加密算法的CBC模式加密
		Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
		
		encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);
		
		cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回加密后的字节数组
		return cipherByte;
	}
	
	/**
	 * <li>
	 * 方法名称:DES_CBC_Decrypt</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 经过封装的DES/CBC解密算法。
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param sourceBuf
	 *            需要解密内容的字节数组
	 * @param deskey
	 *            KEY 由8位字节数组通过SecretKeySpec类转换而成。
	 * @param ivParam
	 *            IV偏转向量，由6位字节数组通过IvParameterSpec类转换而成。
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] DES_CBC_Decrypt(byte[] sourceBuf, SecretKeySpec deskey,
			IvParameterSpec ivParam) throws Exception {
		
		byte[] cipherByte;
		// 获得Cipher实例，使用CBC模式。
		Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 初始化加密实例，定义为解密功能，并传入密钥，偏转向量
		decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);
		
		cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
		// 返回解密后的字节数组
		return cipherByte;
	}
	
	/**
	 * <li>
	 * 方法名称:MD5Hash</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * MD5，进行了简单的封装，以适用于加，解密字符串的校验。
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param buf
	 *            需要MD5加密字节数组。
	 * @param offset
	 *            加密数据起始位置。
	 * @param length
	 *            需要加密的数组长度。
	 * @return
	 * @throws Exception
	 */
	public static byte[] MD5Hash(byte[] buf, int offset, int length) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(buf, offset, length);
		return md.digest();
	}
	
	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param input
	 * @return
	 */
	public static String MD5Hash(String input) {
		try {
			byte[] bytes = input.getBytes();
			byte[] result = MD5Hash(bytes, 0, bytes.length);
			return byte2hex(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <li>
	 * 方法名称:byte2hex</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * 字节数组转换为二行制表示
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param inStr
	 *            需要转换字节数组。
	 * @return 字节数组的二进制表示。
	 */
	public static String byte2hex(byte[] inStr) {
		String stmp;
		StringBuffer out = new StringBuffer(inStr.length * 2);
		
		for (int n = 0; n < inStr.length; n++) {
			// 字节做"与"运算，去除高位置字节 11111111
			stmp = Integer.toHexString(inStr[n] & 0xFF);
			if (stmp.length() == 1) {
				// 如果是0至F的单位字符串，则添加0
				out.append("0" + stmp);
			} else {
				out.append(stmp);
			}
		}
		return out.toString();
	}
	
	/**
	 * <li>
	 * 方法名称:addMD5</li> <li>
	 * 功能描述:
	 * 
	 * <pre>
	 * MD校验码 组合方法，前16位放MD5Hash码。 把MD5验证码byte[]，加密内容byte[]组合的方法。
	 * </pre>
	 * 
	 * </li>
	 * 
	 * @param md5Byte
	 *            加密内容的MD5Hash字节数组。
	 * @param bodyByte
	 *            加密内容字节数组
	 * @return 组合后的字节数组，比加密内容长16个字节。
	 */
	public static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {
		int length = bodyByte.length + md5Byte.length;
		byte[] resutlByte = new byte[length];
		
		// 前16位放MD5Hash码
		for (int i = 0; i < length; i++) {
			if (i < md5Byte.length) {
				resutlByte[i] = md5Byte[i];
			} else {
				resutlByte[i] = bodyByte[i - md5Byte.length];
			}
		}
		
		return resutlByte;
	}
	
	/**
	 * 将email跟time加密成一个字符串
	 * @param email
	 * @param time
	 * @return
	 */
	public static String encryptData4EmailAndTime(String email,String time){
		String url = email+"#jiaxin#"+time;
		String encryptData = EncryptUtil.encrypt(url);
		encryptData = Base64Utils.encodeString(encryptData);
		return encryptData;
	}
	
	public static String[] decryptData4EmailAndTime(String url){
		String url1 = url;
		String result [] = null;
		String decryptData = Base64Utils.decodeString(url1);
		try{
			decryptData = EncryptUtil.decrypt(decryptData);
			result = decryptData.split("#jiaxin#");
		}catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String source = "xiao.ws@qq.com2004-01-02 17:42:60";
		System.out.println("原文: " + source);
		String encryptData = EncryptUtil.encrypt(source);
		System.out.println("加密后: " + Base64Utils.encodeString(encryptData));
		String decryptData;
		decryptData = EncryptUtil.decrypt(encryptData);
		System.out.println("解密后: " + decryptData);
		System.out.println("111:" + encryptData4EmailAndTime("fzc@suntek.com","2015-01-02 17:42:60"));
		String num="WUhrQ3FIYWgrdVJVc0JyMHk1azJ5bWJLSWljK05WenVyTGx5NVljYytWY2l3VXJPMGtQNHl2Z0VCNFNVS1RiTmZ2KzhrZjhNMkFiMg0KbFNhb2ZseGRvdz09";
		System.out.println(decryptData4EmailAndTime(num)[1]);
	}
}
