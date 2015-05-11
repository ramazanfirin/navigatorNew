package com.nagivator.model.cbs;

import java.util.HashSet;
import java.util.Set;

import com.nagivator.model.TrackItem;

public class Sokak extends Base{

	Set<TrackItem> binaList = new HashSet<TrackItem>();
	Mahalle mahalle = new Mahalle();
	
	public Set<TrackItem> getBinaList() {
		return binaList;
	}
	public void setBinaList(Set<TrackItem> binaList) {
		this.binaList = binaList;
	}
	public Mahalle getMahalle() {
		return mahalle;
	}
	public void setMahalle(Mahalle mahalle) {
		this.mahalle = mahalle;
	}
}
