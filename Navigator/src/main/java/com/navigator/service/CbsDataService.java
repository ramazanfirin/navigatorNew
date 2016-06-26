package com.navigator.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.nagivator.model.KeyValueDTO;

public interface CbsDataService {
	List<SelectItem> getIlceList() throws Exception;
	List <SelectItem>  getMahalleList(String url1) throws Exception;
	List <SelectItem>  getSokakList(String url1) throws Exception;
	List <SelectItem> getBinaList(String url1,String mahalleId) throws Exception;
	List<KeyValueDTO> genelAramaByNumber(KeyValueDTO dto) throws Exception;
	List<KeyValueDTO> genelArama(String string) throws Exception;
	List<String> getKapiNo(String binaNo) throws Exception;
}
