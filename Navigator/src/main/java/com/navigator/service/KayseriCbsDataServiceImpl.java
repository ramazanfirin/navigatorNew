package com.navigator.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.nagivator.model.KeyValueDTO;
import com.navigator.util.CityCurfTest;
import com.navigator.util.CityCurfUtil;

public class KayseriCbsDataServiceImpl implements CbsDataService{

	@Override
	public List<SelectItem> getIlceList() throws Exception {
		return CityCurfTest.getIlceList();
	}

	@Override
	public List<SelectItem> getMahalleList(String url1) throws Exception {
		return CityCurfTest.getMahalleList(url1);
	}

	@Override
	public List<SelectItem> getSokakList(String url1) throws Exception {
		return CityCurfTest.getSokakList(url1);
	}

	@Override
	public List<SelectItem> getBinaList(String url1,String mahalleId) throws Exception {
		return CityCurfTest.getBinaList(url1);
				
				
	}

	@Override
	public List<KeyValueDTO> genelAramaByNumber(KeyValueDTO string) throws Exception {
		return CityCurfUtil.genelAramaByNumber(string.getKey());
	}

	@Override
	public List<KeyValueDTO> genelArama(String string) throws Exception {
		return CityCurfUtil.genelArama(string);
	}

	@Override
	public List<String> getKapiNo(String binaNo) throws Exception {
		return CityCurfUtil.getKapiNo(binaNo);
	}

}
