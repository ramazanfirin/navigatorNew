package com.nagivator.model;

import java.util.Date;

public class TrackItem extends BasicObject{

	
	
	private String lat;
	private String lng;
	private Date date;
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
}
