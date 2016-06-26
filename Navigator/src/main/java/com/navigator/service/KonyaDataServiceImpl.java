package com.navigator.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.nagivator.model.KeyValueDTO;
import com.navigator.util.CityCurfTest;
import com.navigator.util.CityCurfUtil;
import com.navigator.util.KonyaUtil;
import com.navigator.util.Util;

public class KonyaDataServiceImpl implements CbsDataService{

	@Override
	public List<SelectItem> getIlceList() throws Exception {
		return Util.convertSelectItem(KonyaUtil.getIlceList());
	}

	@Override
	public List<SelectItem> getMahalleList(String url1) throws Exception {
		return Util.convertSelectItem(KonyaUtil.getMahalleList(url1));
	}

	@Override
	public List<SelectItem> getSokakList(String url1) throws Exception {
		return Util.convertSelectItem(KonyaUtil.getSokakList(url1));
	}


	@Override
	public List<SelectItem> getBinaList(String url1,String mahalledId) throws Exception {
		return Util.convertSelectItem(KonyaUtil.getKapiNoList(url1, mahalledId));
	}
	
	@Override
	public List<KeyValueDTO> genelAramaByNumber(KeyValueDTO string) throws Exception {
		return KonyaUtil.genelAramaByNumber(string);
	}

	@Override
	public List<KeyValueDTO> genelArama(String string) throws Exception {
		return KonyaUtil.genelArama(string);
	}

	@Override
	public List<String> getKapiNo(String binaNo) throws Exception {
		return KonyaUtil.getKapiCoordinate(binaNo);
	}

}
