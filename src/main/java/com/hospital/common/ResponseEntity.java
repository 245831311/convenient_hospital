package com.hospital.common;

import java.util.Map;

/**
 * HTTP响应Entity基类
 * 
 *
 */
public class ResponseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3288974655972496353L;

	/**
	 * 返回码
	 */
	private Integer code;

	/**
	 * 附加信息
	 */
	private String message;

	/**
	 * 时间戳
	 */
	private Long timestamp;

	/**
	 * 企业id
	 */
	private String orgName;

	/**
	 * 应用id
	 */
	private String appName;

	/**
	 * 回执
	 */
	private Map<String, Object> receipt;

	public ResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Map<String, Object> getReceipt() {
		return receipt;
	}

	public void setReceipt(Map<String, Object> receipt) {
		this.receipt = receipt;
	}

}
