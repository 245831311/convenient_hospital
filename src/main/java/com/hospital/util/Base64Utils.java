package com.hospital.util;


public class Base64Utils {

	public static String encodeString(String src) {
		return new String(org.apache.commons.codec.binary.Base64
				.encodeBase64(src.getBytes()));

	}

	public static String decodeString(String src) {
		return new String(org.apache.commons.codec.binary.Base64
				.decodeBase64(src.getBytes()));
	}

	/**
	 * 将字符串转换成不含==符号的base64位编码
	 */
	public static String encodeStringWithoutSign(String src) {
		String result = encodeString(src);
		result = result.substring(0, result.length()-2);
		return result; 
	}
	
	/**
	 * 将一段不含==符号的base64位编码转成字符串
	 * @param src
	 * @return
	 */
	public static String decodeStringWithoutSign(String src) {
		String result = src+"==";
		result = decodeString(result);
		return result;
	}
	
	
	public static void main(String[] args) {
		String id= "ssvasdwe";
		System.out.println(encodeString(id));
	}
}
