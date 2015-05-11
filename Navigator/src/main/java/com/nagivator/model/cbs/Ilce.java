package com.nagivator.model.cbs;

import java.util.HashSet;
import java.util.Set;

import com.nagivator.model.TrackItem;

public class Ilce extends Base{

	Set<TrackItem> mahalleList = new HashSet<TrackItem>();

	public Set<TrackItem> getMahalleList() {
		return mahalleList;
	}

	public void setMahalleList(Set<TrackItem> mahalleList) {
		this.mahalleList = mahalleList;
	}
	
}
