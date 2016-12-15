package com.hospital.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

public class DepartTree implements Serializable {
	/**
	 * 部门关系对象
	 */
	private static final long serialVersionUID = 8251313626404938752L;
	@JsonSerialize(using = ToStringSerializer.class)
	private long departTreeId;
	@JsonSerialize(using = ToStringSerializer.class)
	private long departId;
	@JsonSerialize(using = ToStringSerializer.class)
	private long orgId;
	@JsonSerialize(using = ToStringSerializer.class)
	private long parentDepartId;
	private List<DepartTree> parent;

	public long getDepartTreeId() {
		return departTreeId;
	}

	public void setDepartTreeId(long departTreeId) {
		this.departTreeId = departTreeId;
	}

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

	public long getParentDepartId() {
		return parentDepartId;
	}

	public void setParentDepartId(long parentDepartId) {
		this.parentDepartId = parentDepartId;
	}

	public List<DepartTree> getParent() {
		return parent;
	}

	public void setParent(List<DepartTree> parent) {
		this.parent = parent;
	}


}
