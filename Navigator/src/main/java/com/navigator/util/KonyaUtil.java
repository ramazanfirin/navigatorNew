package com.navigator.util;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.nagivator.model.KeyValueDTO;

public class KonyaUtil {

	
	public static void main(String[] args) throws Exception {
		//getIlceList();
		//System.out.println(ilceList);
		
		//getMahalleList("2");
//		System.out.println(mahalleList);
		
		getSokakList("1027");
//		System.out.println(sokakList);
		
	getKapiNoList("44034","1027");
	//System.out.println(sokakList);
	}
	public static List<KeyValueDTO> getIlceList() throws Exception{
List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
 String url = "http://kentrehberi.konya.bel.tr/mapviewer/maplink/kbb/helper/ilcelerkbb.jsp";		
		
		String result =  Util.getUrl(url);
		System.out.println(result);
		
		result = result.replace("fields", "\"fields\"");
		result = result.replace("data", "\"data\"");
		result = result.replace("id", "\"id\"");
		result = result.replace("'", "\"");
		result = result.replace("ilceadi", "\"ilceadi\"");
		result = result.replace("xcoor", "\"xcoor\"");
		result = result.replace("ycoor", "\"ycoor\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray array = (JSONArray)jsonObject.get("fields");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item2.get("ilceadi"), (String)item2.get("id")));
		}
		
		return resultList;
	}
	public static List<KeyValueDTO> getMahalleList(String ilceId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://kentrehberi.konya.bel.tr/mapviewer/maplink/kbb/helper/mahallelerkbb.jsp?IlceRef="+ilceId;		
		String result = Util.getUrl(url);
		
		result = result.replace("fields", "\"fields\"");
		result = result.replace("data", "\"data\"");
		result = result.replace("id", "\"id\"");
		result = result.replace("'", "\"");
		result = result.replace("mahalleadi", "\"mahalleadi\"");
		result = result.replace("xcoor", "\"xcoor\"");
		result = result.replace("ycoor", "\"ycoor\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray array = (JSONArray)jsonObject.get("fields");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item2.get("mahalleadi"), (String)item2.get("id")));
		}
		
		return resultList;
		
	
	}
	
	public static List<KeyValueDTO> getSokakList(String mahalleId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://kentrehberi.konya.bel.tr/mapviewer/maplink/kbb/helper/yollarkbb.jsp?MahalleRef="+mahalleId;		
		String result = Util.getUrl(url);

		result = result.replace("fields", "\"fields\"");
		result = result.replace("data", "\"data\"");
		result = result.replace("id", "\"id\"");
		result = result.replace("'", "\"");
		result = result.replace("caddesokakref", "\"caddesokakref\"");
		result = result.replace("caddesokakadi", "\"caddesokakadi\"");
		result = result.replace("ycoor", "\"ycoor\"");
		result = result.replace("xcoor", "\"xcoor\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray array = (JSONArray)jsonObject.get("fields");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item2.get("caddesokakadi"), (String)item2.get("caddesokakref")));
		}
		
		return resultList;
	}	
	
	public static List<KeyValueDTO> getKapiNoList(String sokakId,String mahalleId) throws Exception{
		
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://kentrehberi.konya.bel.tr/mapviewer/maplink/kbb/helper/kapilarkbb.jsp?CaddeSokakRef="+sokakId+"&MahalleRef="+mahalleId;		
		
		String result = Util.getUrl(url);
		result = result.replace("fields", "\"fields\"");
		result = result.replace("data", "\"data\"");
		result = result.replace("id", "\"id\"");
		result = result.replace("'", "\"");
		result = result.replace("yolref", "\"yolref\"");
		result = result.replace("kapiadi", "\"kapiadi\"");
		result = result.replace("ycoor", "\"ycoor\"");
		result = result.replace("xcoor", "\"xcoor\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONArray array = (JSONArray)jsonObject.get("fields");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item2.get("kapiadi"), (String)item2.get("id")));
		}
		
		return resultList;
		

	}	
}
