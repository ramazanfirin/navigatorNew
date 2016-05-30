package com.nagivator.model;

import java.util.List;
import java.util.Set;



public class Branch extends BasicObject{

		private Long lat;
	private Long lng;
	private Set<Vehicle> vehicleList;
	
	

	
	public Long getLat() {
		return lat;
	}
	public void setLat(Long lat) {
		this.lat = lat;
	}
	public Long getLng() {
		return lng;
	}
	public void setLng(Long lng) {
		this.lng = lng;
	}
	public Set<Vehicle> getVehicleList() {
		return vehicleList;
	}
	public void setVehicleList(Set<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	
	
}
