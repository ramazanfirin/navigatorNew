package com.nagivator.model.cbs;

public class Bina extends Base{

	Sokak sokak  =new Sokak();
    Float lat;
    Float lng;
    Float orijinalLat;
    Float orijinalLng;
	
	public Sokak getSokak() {
		return sokak;
	}

	public void setSokak(Sokak sokak) {
		this.sokak = sokak;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getOrijinalLat() {
		return orijinalLat;
	}

	public void setOrijinalLat(Float orijinalLat) {
		this.orijinalLat = orijinalLat;
	}

	public Float getOrijinalLng() {
		return orijinalLng;
	}

	public void setOrijinalLng(Float orijinalLng) {
		this.orijinalLng = orijinalLng;
	}
	
}
