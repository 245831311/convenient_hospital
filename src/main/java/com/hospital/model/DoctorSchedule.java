package com.hospital.model;

import java.io.Serializable;

public class DoctorSchedule implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String nickName;
	
	private String email;
	
	private String mobile;
	
	private String password;
	
	private int code;
	
	private String department;
	
	private String section;
	
	private String description;
	
	private String headUrl;
	
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", nickName=" + nickName + ", email="
				+ email + ", mobile=" + mobile + ", password=" + password
				+ ", code=" + code + ", department=" + department
				+ ", section=" + section + ", description=" + description
				+ ", headUrl=" + headUrl + ", createTime=" + createTime + "]";
	}
	
}
