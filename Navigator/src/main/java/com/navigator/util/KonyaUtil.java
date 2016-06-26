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
		
	//	getSokakList("1027");
//		System.out.println(sokakList);
		
//	getKapiNoList("44034","1027");
	//System.out.println(sokakList);
	
	genelArama("duygu");
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
			resultList.add(new KeyValueDTO((String)item2.get("id"), (String)item2.get("ilceadi")));
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
			resultList.add(new KeyValueDTO((String)item2.get("id"), (String)item2.get("mahalleadi")));
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
			resultList.add(new KeyValueDTO((String)item2.get("id"), (String)item2.get("caddesokakadi")));
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
			resultList.add(new KeyValueDTO((String)item2.get("xcoor")+" "+(String)item2.get("ycoor"), (String)item2.get("kapiadi")));
		}
		
		return resultList;
		

	}	
	
	public static List<String> getKapiCoordinate(String binaId){
		List<String> a = new ArrayList<String>();
		String[] b= binaId.split(" ");
		a.add(b[0]);
		a.add(b[1]);
		
		return a;
	}
	
	public static List<KeyValueDTO> genelAramaByNumber(KeyValueDTO binaId){
		List<KeyValueDTO> a = new ArrayList<KeyValueDTO>();
		String[] b= binaId.getKey().split(" ");
		KeyValueDTO dto = new KeyValueDTO("", b[0]);
		KeyValueDTO dto2 = new KeyValueDTO("", b[1]);
		
		a.add(dto);
		a.add(dto2);
		
		String[] c= binaId.getValue().split(",");
		for (int i = 0; i < c.length; i++) {
			a.add(new KeyValueDTO("", c[i]));
		}
		return a;
	}
	
	public static List<KeyValueDTO> genelArama(String s) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://kentrehberi.konya.bel.tr/mapviewer/maplink/kbb/helper/suggest.jsp?Keyword="+s;
		
		String result = Util.getUrl(url);
		System.out.println(result);
		result = result.replace("fields", "\"fields\"");
		result = result.replace("data", "\"data\"");
		result = result.replace("id", "\"id\"");
		result = result.replace("'", "\"");
		result = result.replace("ft", "\"ft\"");
		result = result.replace("x", "\"x\"");
		result = result.replace("y:", "\"y\":");
		result = result.replace("name", "\"name\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONArray array = (JSONArray) obj;
		
		//JSONArray array = (JSONArray)jsonObject.get("fields");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			double x = (double)item.get("x");
			double y = (double)item.get("y");
			resultList.add(new KeyValueDTO(String.valueOf(x)+" "+String.valueOf(y), (String)item.get("name")));
		}
		
		return resultList;
	}
}
