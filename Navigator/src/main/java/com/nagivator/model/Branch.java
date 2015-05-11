package com.nagivator.model;

import java.util.List;
import java.util.Set;



public class Branch{

	private Long id;
	private String name;
	private Long lat;
	private Long lng;
	private Set<Vehicle> vehicleList;
	private Boolean enabled=true;
	
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
}
