package com.hospital.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式判断工具类
 * 
 */
public class CheckUtils {

	// 判断电话
	public static boolean isTelephone(String phonenumber) {
		String phone = "0\\d{2,3}-\\d{7,8}";
		Pattern p = Pattern.compile(phone);
		Matcher m = p.matcher(phonenumber);
		return m.matches();
	}

	// 判断手机号
	public static boolean isMobileNO(String mobiles) {
		//带国际码的手机号 modify by fzcheng 20150930
		Pattern p = Pattern.compile("^(\\+[0-9]{2})*(13|14|15|17|18)[0-9]{9}$");
		// Pattern p =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断邮箱
	public static boolean isEmail(String email) {
		String str = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 判断日期格式:yyyy-mm-dd

	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))" + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	// 验证金额
	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 验证应用名称
	 * 验证内容"只能是字母,数字或字母数字组合!"
	 * by fzc
	 * @param str
	 * @return
	 */
	public static boolean isAppName(String str){
		String appName = "^[0-9a-zA-Z\\-]*$";
		Pattern p = Pattern.compile(appName);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 验证产品名称
	 * 验证内容"只能是汉字,字母,数字、横线、下划线及其组合!"
	 * by fzc
	 * @param str
	 * @return
	 */
	public static boolean isProductName(String str){
		String productName = "^[0-9a-zA-Z-_\\u4e00-\\u9faf ]*$";
		Pattern p = Pattern.compile(productName);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 应用描述
	 * 验证内容"只能输入字母，数字或者汉字,字数在一百字以内!"
	 * by fzc
	 * @param str
	 * @return
	 */
	public static boolean isAppDesc(String str){
		String appDesc = "^[\\s\\S]{0,100}$";
		Pattern p = Pattern.compile(appDesc);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 大于1或者等于1的正整数
	 * 验证内容"!"
	 * by fzc
	 * @param str
	 * @return
	 */
	public static boolean isMinIs2(String str){
		String minIs2 = "\\b[1-9]\\d*\\b[1-9]";
		Pattern p = Pattern.compile(minIs2);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 判断是否正整数
	 * add by LHJ
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		String regex = "^\\d*$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	public static void main(String[] args) {
		int str = 0123;
	//	System.out.println(isInteger(str));
	}

}
