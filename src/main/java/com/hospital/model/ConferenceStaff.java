package com.hospital.model;

import java.io.Serializable;

public class ConferenceStaff implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String doctorId;
	
	/**
	 * 主持人:默认会议创始人 0:否 1:是
	 */
	private int IsHolder;

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

	public int getIsHolder() {
		return IsHolder;
	}

	public void setIsHolder(int isHolder) {
		IsHolder = isHolder;
	}

	@Override
	public String toString() {
		return "ConferenceStaff [id=" + id + ", doctorId=" + doctorId
				+ ", IsHolder=" + IsHolder + "]";
	}
}
