package com.nagivator.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class Order extends BasicObject{

	private User user;
	private Vehicle vehicle;
	private String lat;
	private String lng;
	private Date date;
	private String address;
	private String ilce;
	private String mahalle;
	private String sokak;
	private String bina;
	
	Set<TrackItem> trackItems = new HashSet<TrackItem>();
	
	private OrderStatus status = new OrderStatus();
	private OrderPriority priority = new OrderPriority();
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIlce() {
		return ilce;
	}
	public void setIlce(String ilce) {
		this.ilce = ilce;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public String getSokak() {
		return sokak;
	}
	public void setSokak(String sokak) {
		this.sokak = sokak;
	}
	public String getBina() {
		return bina;
	}
	public void setBina(String bina) {
		this.bina = bina;
	}
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
	public Set<TrackItem> getTrackItems() {
		return trackItems;
	}
	public void setTrackItems(Set<TrackItem> trackItems) {
		this.trackItems = trackItems;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public OrderPriority getPriority() {
		return priority;
	}
	public void setPriority(OrderPriority priority) {
		this.priority = priority;
	}
	
	
	
}
