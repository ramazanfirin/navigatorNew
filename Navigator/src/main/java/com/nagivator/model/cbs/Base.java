package com.nagivator.model.cbs;

public class Base {

	private Long id;
	private String name;
	private String stringQuery;
	
	private Boolean complated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Boolean getComplated() {
		return complated;
	}

	public void setComplated(Boolean complated) {
		this.complated = complated;
	}

	public String getStringQuery() {
		return stringQuery;
	}

	public void setStringQuery(String stringQuery) {
		this.stringQuery = stringQuery;
	}

}
