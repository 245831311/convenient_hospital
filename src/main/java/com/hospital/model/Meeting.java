package com.hospital.model;

import java.io.Serializable;
/**
 * 会议实体
 * @author yubing
 *
 */
public class Meeting implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -62509929262998193L;

	private String meetingId;

    private String code;

    private Boolean sponsorType;

    private Boolean mediaType;

    private String subject;

    private Long planStartTime;

    private Long planEndTime;

    private Double planLong;

    private Long realStartTime;

    private Long realEndTime;

    private Double realLong;

    private String sponsorId;

    private String sponPwd;

    private Boolean status;

    private String place;

    private String spare;

    private String orgId;
    
    /**
     * 会议发起方式
     *
     */
    public static enum SponsorType{
    	ImmediateMeeting("即时会议",1),
    	OrderMeeting("预约会议",2);
    	
    	String desc ="";
    	int value = 0;
    	private SponsorType(String desc, int value){
    		this.desc = desc;
    		this.value = value;
    	}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
    }
    
    /**
     * 会议媒体类型
     *
     */
    public static enum MediaType{
    	VIDEO("视频会议",0),
    	AUDIO("语音会议",1);
    	
    	String desc ="";
    	int value = 0;
    	private MediaType(String desc, int value){
    		this.desc = desc;
    		this.value = value;
    	}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
    }
    
    /**
     * 会议状态
     *
     */
    public static enum Status{
    	UNPROCESS("未进行",0),
    	PROCING("正在进行",1),
    	END("已结束",2);
    	
    	String desc ="";
    	int value = 0;
    	private Status(String desc, int value){
    		this.desc = desc;
    		this.value = value;
    	}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
    }

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(Boolean sponsorType) {
		this.sponsorType = sponsorType;
	}

	public Boolean getMediaType() {
		return mediaType;
	}

	public void setMediaType(Boolean mediaType) {
		this.mediaType = mediaType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Long planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Long getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Long planEndTime) {
		this.planEndTime = planEndTime;
	}

	public Long getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Long realStartTime) {
		this.realStartTime = realStartTime;
	}

	public Long getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Long realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponPwd() {
		return sponPwd;
	}

	public void setSponPwd(String sponPwd) {
		this.sponPwd = sponPwd;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Double getPlanLong() {
		return planLong;
	}

	public void setPlanLong(Double planLong) {
		this.planLong = planLong;
	}

	public Double getRealLong() {
		return realLong;
	}

	public void setRealLong(Double realLong) {
		this.realLong = realLong;
	}
}