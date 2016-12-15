package com.hospital.model;

import java.io.Serializable;

/**
 * 资源分配表
 * @author yubing
 *
 */
public class ResourceAlloc implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1799785113553707353L;

	private Long orgId;

    private Long datetime;

    private Byte timeSection;

    private Byte allocCount;

    private String allocSets;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Byte getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(Byte timeSection) {
        this.timeSection = timeSection;
    }

    public Byte getAllocCount() {
        return allocCount;
    }

    public void setAllocCount(Byte allocCount) {
        this.allocCount = allocCount;
    }

    public String getAllocSets() {
        return allocSets;
    }

    public void setAllocSets(String allocSets) {
        this.allocSets = allocSets == null ? null : allocSets.trim();
    }
}