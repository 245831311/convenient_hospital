package com.hospital.model;

import java.io.Serializable;
/**
 * 登陆日志实体
 * @author yubing
 *
 */
public class LoginLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6509048669321452129L;

	private String loginLogId;

    private String userId;

    private Long time;

    private String ip;

    private String address;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(String loginLogId) {
		this.loginLogId = loginLogId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}