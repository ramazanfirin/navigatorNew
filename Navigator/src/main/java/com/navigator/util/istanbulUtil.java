package com.navigator.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.nagivator.model.KeyValueDTO;

public class istanbulUtil {

	
	public static void main(String[] args) throws Exception {
	//	getIlceList();
		//System.out.println(ilceList);
		
//		getMahalleList("2052"); //celmekoy
//		System.out.println(mahalleList);
		
//		getSokakList("2052","40765");//tasdelen
//		System.out.println(sokakList);
		
		
		
	getKapiNoList("2052","40769","Şadırvan Sk.");
	//System.out.println(sokakList);
	
//	genelArama("duygu");
	}
	public static List<KeyValueDTO> getIlceList() throws Exception{
List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
 String url = "http://cbsproxy.ibb.gov.tr/?SehirHaritasiIlceListele151&";		
		
		String result =  Util.getUrl(url);
		System.out.println(result);
		
//		result = result.replace("fields", "\"fields\"");
//		result = result.replace("data", "\"data\"");
//		result = result.replace("id", "\"id\"");
//		result = result.replace("'", "\"");
//		result = result.replace("ilceadi", "\"ilceadi\"");
//		result = result.replace("xcoor", "\"xcoor\"");
//		result = result.replace("ycoor", "\"ycoor\"");
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONObject addressList = (JSONObject)jsonObject.get("AdresList");;
		JSONObject addresslet = (JSONObject)addressList.get("Adresler");;
		
		JSONArray array = (JSONArray)addresslet.get("Adres");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			//JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item.get("ID"), (String)item.get("ADI")));
		}
		
		return resultList;
	}
	public static List<KeyValueDTO> getMahalleList(String ilceId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://cbsproxy.ibb.gov.tr/?SehirHaritasiMahalleListele151&&ID="+ilceId;		
		String result = getUrl(url);
		

		System.out.println(result);
		
		System.out.println(result);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONObject addressList = (JSONObject)jsonObject.get("AdresList");;
		JSONObject addresslet = (JSONObject)addressList.get("Adresler");;
		
		JSONArray array = (JSONArray)addresslet.get("Adres");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			//JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item.get("ID"), (String)item.get("ADI")));
		}
		
		return resultList;
		
	
	}
	
	public static List<KeyValueDTO> getSokakList(String ilceID,String mahalleId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://cbsproxy.ibb.gov.tr/?SehirHaritasiYolListele151&&ilceID="+ilceID+"&mahalleID="+mahalleId+"&yolAdi=";		
		String result = Util.getUrl(url);
		System.out.println(result);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONObject addressList = (JSONObject)jsonObject.get("AdresList");;
		JSONObject addresslet = (JSONObject)addressList.get("Adresler");;
		
		JSONArray array = (JSONArray)addresslet.get("Adres");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			//JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item.get("ID"), (String)item.get("ADI")));
		}
		
		return resultList;
	}	
	
	public static List<KeyValueDTO> getKapiNoList(String ilceID,String mahalleId,String yolAdi) throws Exception{
		
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "http://cbsproxy.ibb.gov.tr/?SehirHaritasiKapiListele151&&ilceID="+ilceID+"&mahalleID="+mahalleId+"&yolAdi="+URLEncoder.encode(yolAdi, "UTF-8")+"&kapiNo=";		
		
		String result = Util.getUrl(url);
		System.out.println(result);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);
		JSONObject jsonObject = (JSONObject) obj;
		
		JSONObject addressList = (JSONObject)jsonObject.get("AdresList");;
		JSONObject addresslet = (JSONObject)addressList.get("Adresler");;
		
		JSONArray array = (JSONArray)addresslet.get("Adres");
		for (int i = 0; i < array.size(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			//JSONObject item2 = (JSONObject)item.get("data");
			resultList.add(new KeyValueDTO((String)item.get("LON")+" "+(String)item.get("LAT"), "NO:"+(String)item.get("KAPI")+"-"+(String)item.get("ADI")));
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
	
	public static String getUrl(String url) throws Exception{
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(url);
request.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		HttpResponse response = client.execute(request);
		
		 HttpEntity entity = response.getEntity();
		 //String aa=EntityUtils.toString(entity, HTTP.ISO_8859_1);
		 
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(),"UTF8"));

	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}

	
	return result.toString();
	//	 return aa;
	}
}
