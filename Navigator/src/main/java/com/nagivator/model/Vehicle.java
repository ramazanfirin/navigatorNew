package com.nagivator.model;



public class Vehicle extends BasicObject{


	private String plate;
	private Device device;
	private Branch branch = new Branch();

	
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	
}
