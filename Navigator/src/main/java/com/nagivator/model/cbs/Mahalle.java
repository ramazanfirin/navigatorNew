package com.nagivator.model.cbs;

import java.util.HashSet;
import java.util.Set;

import com.nagivator.model.TrackItem;

public class Mahalle extends Base{

	Set<TrackItem> sokakList = new HashSet<TrackItem>();
	Ilce ilce = new Ilce();

	public Set<TrackItem> getSokakList() {
		return sokakList;
	}

	public void setSokakList(Set<TrackItem> sokakList) {
		this.sokakList = sokakList;
	}

	public Ilce getIlce() {
		return ilce;
	}

	public void setIlce(Ilce ilce) {
		this.ilce = ilce;
	}
	
}
