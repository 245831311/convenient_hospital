package com.hospital.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

public class OrgDepartJobUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1200340705134361956L;
	
	@JsonSerialize(using=ToStringSerializer.class)
    private Long orgId;
	@JsonSerialize(using=ToStringSerializer.class)
    private Long departId;
	@JsonSerialize(using=ToStringSerializer.class)
    private Long jobId;
	@JsonSerialize(using=ToStringSerializer.class)
    private Long userId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDepartId() {
        return departId;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}