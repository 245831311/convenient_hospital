package com.hospital.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

public class Department implements Serializable {

	/**
	 * 部门对象
	 */
	private static final long serialVersionUID = 529648054596235927L;
	@JsonSerialize(using = ToStringSerializer.class)
	private long departId;
	@JsonSerialize(using = ToStringSerializer.class)
	private long orgId;
	private String code;
	@JsonSerialize(using = ToStringSerializer.class)
	private long createTime;
	private String name;
	private String orgShortName;
	private String englishName;
	private int valid;
	private String spare;

	public long getDepartId() {
		return departId;
	}

	public void setDepartId(long departId) {
		this.departId = departId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}
