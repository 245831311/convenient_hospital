package com.hospital.constants;

/**
 * 常量类
 * @author yubing
 */
public class AppConstants {
	/**
	 * 用户登陆用户session key
	 */
	public static final String SESSION_USER = "web_conference_user";
	/**
	 * 用户登陆组织机构 session key
	 */
	public static final String SESSION_ORGANIZATION = "web_conference_organization";
	
	/**
	 * 会议材料上传路径
	 */
	public static final String IMAGE_UPLOAD_DIR = "image_upload_dir";
	
	/**
	 * 参会人员资源预留基数
	 */
	public static final double GET_RESOURCE_BASE = 1.1;
	
	/**
	 * 公共配置文件名,用于ResourcesUtil查找文件中配置的值
	 */
	public static final String COMMON_PROPERTIES = "common";
	
	/**
	 * common.properties中配置的key begin
	 */
	public static final String REDIS_IP = "redis.ip";
	public static final String REDIS_PORT = "redis.port";
	public static final String REDIS_AGENDAS = "redis.agendas";
	public static final String REDIS_PARTICIPANTS = "redis.participants";
	public static final String REDIS_UPLOADMEETINGFILELOG = "redis.uploadMeetingFileLog";
	/**
	 * common.properties中配置的key end
	 */
	
	/**
	 * 字符集 begin
	 */
	public static final String CHARSET_ISO8859_1 = "ISO-8859-1";
	public static final String CHARSET_UTF8 = "UTF-8";
	/**
	 * 字符集end
	 */
}
