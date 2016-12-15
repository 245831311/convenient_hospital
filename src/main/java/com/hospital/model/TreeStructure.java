package com.hospital.model;

public class TreeStructure{
	
	private String Id;
	
	private String title;
	
	private String mobile;



	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String toJSON(){
		return "{'title':'"+title+"','Id':'"+Id+"','mobile':'"+mobile+"'}";
	}
}
