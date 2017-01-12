package com.navigator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.nagivator.model.KeyValueDTO;

public class KonyaUtil {

	
	public static void main(String[] args) throws Exception {
		getIlceList();
		//System.out.println(ilceList());
		
	//	getMahalleList("14");
//		System.out.println(mahalleList);
		
	//	getSokakList("1613");
//		System.out.println(sokakList);
		
	//getKapiNoList("1077444","1610");
	//System.out.println(sokakList);
	
	//genelArama("duygu");
	
	//getToken();
	}
	
	
	public static String getToken() throws ClientProtocolException, IOException, ParseException{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();

		 String url = "https://kentrehberi.konya.bel.tr/layerAuth/createToken";
		 
		 
		 HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			StringEntity input = new StringEntity("{\"mahalle_id\":"+1+"}");
			input.setContentType("application/json");
			post.setEntity(input);

			post.setHeader("Accept", "application/json, text/plain, */*");
			post.setHeader("Accept-Encoding", "gzip,deflate");
			post.setHeader("Accept-Language", "en-US,en;q=0.8");
			post.setHeader("Connection", "keep-alive");
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("Host", "kentrehberi.konya.bel.tr");
//			//post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "https://kentrehberi.konya.bel.tr/");
			post.setHeader("Origin", "https://kentrehberi.konya.bel.tr");
			//post.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTJUMTU6MTY6NTAuMTYzKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTJUMTU6NDA6NTAuMTYzKzAyOjAwIn0=.HBcOSL/3+1OAXkPbhoNRum6bhMBQc6tmWdCMB6ik+hc=");
			//post.setHeader("Cookie","__utma=123124625.1069603549.1484122413.1484122413.1484122413.1; __utmz=123124625.1484122413.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTFUMTU6MTE6MDUuMDQwKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTFUMTU6MzU6MDUuMDQxKzAyOjAwIn0%3D.h6LcXu1zs9hj9kdirGTf1L6ZxYMna0d7KoIKsPC8zVQ%3D");
			post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
		//	
			HttpResponse response = client.execute(post);
			
			BufferedReader rd = new BufferedReader(
		         new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result2 = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result2.append(line);
			}
			
		 
		// String aa = toJson(result2.toString());
		 String aa = result2.toString();
		 System.out.println(aa);
		 
		 JSONParser parser = new JSONParser();
			Object obj = parser.parse(aa);
			JSONObject jsonObject = (JSONObject) obj;
			String token = (String)jsonObject.get("message");
			
			
			System.out.println(token);
			return token;
	}
	
	public static List<KeyValueDTO> getIlceList() throws Exception{
List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();

 String url = "https://kentrehberi.konya.bel.tr/ilce/getIlcelerWGeom";

 
  HttpClient client = new DefaultHttpClient();
	HttpPost post = new HttpPost(url);
	
	StringEntity input = new StringEntity("{\"mahalle_id\":"+1+"}");
	input.setContentType("application/json");
	post.setEntity(input);

	post.setHeader("Accept", "application/json, text/plain, */*");
	post.setHeader("Accept-Encoding", "gzip,deflate");
	post.setHeader("Accept-Language", "en-US,en;q=0.8");
	post.setHeader("Connection", "keep-alive");
	post.setHeader("Content-Type", "application/json; charset=UTF-8");
	post.setHeader("Host", "kentrehberi.konya.bel.tr");
//	//post.setHeader("X-Requested-With", "XMLHttpRequest");
post.setHeader("Referer", "https://kentrehberi.konya.bel.tr/");
	post.setHeader("Origin", "https://kentrehberi.konya.bel.tr");
	post.setHeader("Authorization", "Bearer "+getToken());
	//post.setHeader("Cookie","__utma=123124625.1069603549.1484122413.1484122413.1484122413.1; __utmz=123124625.1484122413.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTFUMTU6MTE6MDUuMDQwKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTFUMTU6MzU6MDUuMDQxKzAyOjAwIn0%3D.h6LcXu1zs9hj9kdirGTf1L6ZxYMna0d7KoIKsPC8zVQ%3D");
	post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
//	
	HttpResponse response = client.execute(post);
	
	BufferedReader rd = new BufferedReader(
         new InputStreamReader(response.getEntity().getContent()));

	StringBuffer result2 = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result2.append(line);
	}
	
 
 String aa = toJson(result2.toString());
 System.out.println(aa);
 
 JSONParser parser = new JSONParser();
	Object obj = parser.parse(aa);
	JSONArray jsonArray = (JSONArray) obj;
	
	for (int i = 0; i < jsonArray.size(); i++) {
		JSONObject object = (JSONObject)jsonArray.get(i);
		Long id = (Long)object.get("id");
		String name = (String)object.get("adi_numarasi");
		resultList.add(new KeyValueDTO(String.valueOf(id), name));
		
	}
	
	System.out.println("dikkat");
// 
// if(1==1)
//	 return null;
 
 
//		String result =  Util.getUrl(url);
//		System.out.println(result);
//		
//		result = result.replace("fields", "\"fields\"");
//		result = result.replace("data", "\"data\"");
//		result = result.replace("id", "\"id\"");
//		result = result.replace("'", "\"");
//		result = result.replace("ilceadi", "\"ilceadi\"");
//		result = result.replace("xcoor", "\"xcoor\"");
//		result = result.replace("ycoor", "\"ycoor\"");
//		
//		System.out.println(result);
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(result);
//		JSONObject jsonObject = (JSONObject) obj;
//		
//		JSONArray array = (JSONArray)jsonObject.get("fields");
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject item = (JSONObject)array.get(i);
//			JSONObject item2 = (JSONObject)item.get("data");
//			resultList.add(new KeyValueDTO((String)item2.get("id"), (String)item2.get("ilceadi")));
//		}
		
		return resultList;
	}
	public static List<KeyValueDTO> getMahalleList(String ilceId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "https://kentrehberi.konya.bel.tr/ilce/getMahallelerOfIlceWGeom";		
		
		 HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			StringEntity input = new StringEntity("{\"ilce_id\":"+ilceId+"}");
			input.setContentType("application/json");
			post.setEntity(input);

		
		
			post.setHeader("Accept", "application/json, text/plain, */*");
			post.setHeader("Accept-Encoding", "gzip,deflate");
			post.setHeader("Accept-Language", "en-US,en;q=0.8");
			post.setHeader("Connection", "keep-alive");
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("Host", "kentrehberi.konya.bel.tr");
//			//post.setHeader("X-Requested-With", "XMLHttpRequest");
		post.setHeader("Referer", "https://kentrehberi.konya.bel.tr/");
			post.setHeader("Origin", "https://kentrehberi.konya.bel.tr");
			post.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTJUMTU6MTY6NTAuMTYzKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTJUMTU6NDA6NTAuMTYzKzAyOjAwIn0=.HBcOSL/3+1OAXkPbhoNRum6bhMBQc6tmWdCMB6ik+hc=");
			//post.setHeader("Cookie","__utma=123124625.1069603549.1484122413.1484122413.1484122413.1; __utmz=123124625.1484122413.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTFUMTU6MTE6MDUuMDQwKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTFUMTU6MzU6MDUuMDQxKzAyOjAwIn0%3D.h6LcXu1zs9hj9kdirGTf1L6ZxYMna0d7KoIKsPC8zVQ%3D");
			post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
		//	
			HttpResponse response = client.execute(post);
			
			BufferedReader rd = new BufferedReader(
		         new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result2 = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result2.append(line);
			}
			
		 
		 String aa = toJson(result2.toString());
		 System.out.println(aa);
		 
		 JSONParser parser = new JSONParser();
			Object obj = parser.parse(aa);
			JSONObject jsonobject = (JSONObject) obj;
			
			JSONArray jsonArray = (JSONArray)jsonobject.get("mahalleler");
			
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = (JSONObject)jsonArray.get(i);
				Object id = (Object)object.get("id");
				String name = (String)object.get("adi_numarasi");
				resultList.add(new KeyValueDTO(String.valueOf(id), name));
				
			}
			
			System.out.println("dikkat");
			
			return resultList;
		
	
	}
	
	public static List<KeyValueDTO> getSokakList(String mahalleId) throws Exception{
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "https://kentrehberi.konya.bel.tr/mahalle/getCaddeSokaklar";		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		StringEntity input = new StringEntity("{\"mahalle_id\":"+mahalleId+"}");
		input.setContentType("application/json");
		post.setEntity(input);

	
	
		post.setHeader("Accept", "application/json, text/plain, */*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "kentrehberi.konya.bel.tr");
//		//post.setHeader("X-Requested-With", "XMLHttpRequest");
	post.setHeader("Referer", "https://kentrehberi.konya.bel.tr/");
		post.setHeader("Origin", "https://kentrehberi.konya.bel.tr");
		post.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTJUMTU6MTY6NTAuMTYzKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTJUMTU6NDA6NTAuMTYzKzAyOjAwIn0=.HBcOSL/3+1OAXkPbhoNRum6bhMBQc6tmWdCMB6ik+hc=");
		//post.setHeader("Cookie","__utma=123124625.1069603549.1484122413.1484122413.1484122413.1; __utmz=123124625.1484122413.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTFUMTU6MTE6MDUuMDQwKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTFUMTU6MzU6MDUuMDQxKzAyOjAwIn0%3D.h6LcXu1zs9hj9kdirGTf1L6ZxYMna0d7KoIKsPC8zVQ%3D");
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
	//	
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
	         new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result2 = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result2.append(line);
		}
		
	 
	 String aa = toJson(result2.toString());
	 System.out.println(aa);
	 
	 JSONParser parser = new JSONParser();
		Object obj = parser.parse(aa);
		JSONArray jsonArray = (JSONArray) obj;
		
		//JSONArray jsonArray = (JSONArray)jsonobject.get("mahalleler");
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject)jsonArray.get(i);
			Object id = (Object)object.get("id");
			String name = (String)object.get("adi_numarasi");
			resultList.add(new KeyValueDTO(String.valueOf(id), name));
			
		}
		
		System.out.println("dikkat");
		
		return resultList;
	}	
	
	public static List<KeyValueDTO> getKapiNoList(String sokakId,String mahalleId) throws Exception{
		
		List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
		String url = "https://kentrehberi.konya.bel.tr/mahalle/getKapilar";		
		
	
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		String entity="{\"mahalle_id\":"+mahalleId+",\"caddesokak_id\":"+sokakId+"}";
		StringEntity input = new StringEntity(entity);
		input.setContentType("application/json");
		post.setEntity(input);

	
	
		post.setHeader("Accept", "application/json, text/plain, */*");
		post.setHeader("Accept-Encoding", "gzip,deflate");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/json; charset=UTF-8");
		post.setHeader("Host", "kentrehberi.konya.bel.tr");
//		//post.setHeader("X-Requested-With", "XMLHttpRequest");
	post.setHeader("Referer", "https://kentrehberi.konya.bel.tr/");
		post.setHeader("Origin", "https://kentrehberi.konya.bel.tr");
		post.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTJUMTU6MTY6NTAuMTYzKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTJUMTU6NDA6NTAuMTYzKzAyOjAwIn0=.HBcOSL/3+1OAXkPbhoNRum6bhMBQc6tmWdCMB6ik+hc=");
		//post.setHeader("Cookie","__utma=123124625.1069603549.1484122413.1484122413.1484122413.1; __utmz=123124625.1484122413.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InJlaGJlci52YXRhbmRhcyIsImlzc3VlZF9hdCI6IjIwMTctMDEtMTFUMTU6MTE6MDUuMDQwKzAyOjAwIiwiZXhwaXJlc19hdCI6IjIwMTctMDEtMTFUMTU6MzU6MDUuMDQxKzAyOjAwIn0%3D.h6LcXu1zs9hj9kdirGTf1L6ZxYMna0d7KoIKsPC8zVQ%3D");
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
	//	
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
	         new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result2 = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result2.append(line);
		}
		
	 
	 String aa = toJson(result2.toString());
	 System.out.println(aa);
	 
	 JSONParser parser = new JSONParser();
		Object obj = parser.parse(aa);
		JSONArray jsonArray = (JSONArray) obj;
		
		//JSONArray jsonArray = (JSONArray)jsonobject.get("mahalleler");
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject)jsonArray.get(i);
			String id = (String)object.get("geom");
			id=id.replace("POINT (", "").replace(")", "");
			String name = (String)object.get("adi_numarasi");
			resultList.add(new KeyValueDTO(String.valueOf(id), name));
			
		}
		
		System.out.println("dikkat");
		
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
	
	public static String toJson(final String encrypted) {
	    try {
	        SecretKey key = new SecretKeySpec(Base64.decodeBase64("u/Gu5posvwDsXUnV5Zaq4g=="), "AES");
	        AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decodeBase64("5D9r9ZVzEYYgha93/aUK2w=="));
	        byte[] decodeBase64 = Base64.decodeBase64(encrypted);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, key, iv);

	        return new String(cipher.doFinal(decodeBase64), "UTF-8");
	    } catch (Exception e) {
	        throw new RuntimeException("This should not happen in production.", e);
	    }
	}
}
