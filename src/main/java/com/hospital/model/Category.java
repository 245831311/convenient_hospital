package com.hospital.model;

import java.io.Serializable;

public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int parentId;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", parentId=" + parentId + ", name="
				+ name + "]";
	}
}
