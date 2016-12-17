package com.hospital.model;

import java.io.Serializable;

public class UserOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String doctorId;
	
	private String userId;
	
	private int orderNo;
	
	private String orderTime;
	
	/**
	 * 1-7:星期一到星期日
	 */
	private int week;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}
	
	
}
