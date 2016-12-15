package com.hospital.model;

import java.io.Serializable;
/**
 * 议会人列表实体
 * @author yubing
 *
 */
public class Participant implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1260996393778235728L;

	private String participantId;

    private String mettingId;

    private String userId;

    private String phone;

    private String name;

    /**
     * 是否被邀请，
		0：否，
		1：是
     */
    private Integer isInvited;

    /**
     * 是否参加了会议,
		0：否
		1：是
     */
    private Integer isParticipated;

    private String resourceId;

    /**
     * 与会者角色，
		0：普通与会者
		1：主持人
     */
    private Integer role;

    private String password;

    /**
     * 是否预留人员,
		0:预留
		1：非预留
     */
    private Integer isReserved;
    
    public static enum IsParticipated{
    	NO("否",0),
    	YES("是",1);
    	
    	String desc = "";
    	int value = 0;
    	
    	private IsParticipated(String desc,int value){
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
    
    public static enum IsInvited{
    	NO("否",0),
    	YES("是",1);
    	
    	String desc = "";
    	int value = 0;
    	
    	private IsInvited(String desc,int value){
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
    
    public static enum IsReserved{
    	NOT_OBLIGATE("非预留",1),
    	OBLIGATE("预留",0);
    	
    	String desc = "";
    	int value = 0;
    	
    	private IsReserved(String desc,int value){
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
    
    public static enum Role{
    	MASTER("主持人",1),
    	COMMON("普通与会者",0);
    	
    	String desc = "";
    	int value = 0;
    	
    	private Role(String desc,int value){
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getMettingId() {
		return mettingId;
	}

	public void setMettingId(String mettingId) {
		this.mettingId = mettingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(Integer isReserved) {
		this.isReserved = isReserved;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(Integer isInvited) {
		this.isInvited = isInvited;
	}

	public Integer getIsParticipated() {
		return isParticipated;
	}

	public void setIsParticipated(Integer isParticipated) {
		this.isParticipated = isParticipated;
	}
}