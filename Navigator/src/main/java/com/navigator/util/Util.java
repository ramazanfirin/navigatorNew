package com.navigator.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.nagivator.model.Order;


public class Util {
	
	static  String key ="AIzaSyA5vr74kwURbtKibIJUcKIe0Y019xj2gwE";
	
	public static final String ROLE_ADMIN="ROLE_ADMIN";
	public static final String ROLE_USER="ROLE_USER";
	
	public static final Long ORDER_STATUS_NEW=new Long(1);
	public static final Long ORDER_STATUS_START=new Long(2);
	public static final Long ORDER_STATUS_OPERATOR_CANCELLED=new Long(3);
	public static final Long ORDER_STATUS_USER_CANCELLED=new Long(4);
	public static final Long ORDER_STATUS_COMPLETED=new Long(5);
	
	public static final Long ORDER_PRIORITY_HIGH=new Long(1);
	public static final Long ORDER_PRIORITY_MEDIUM=new Long(2);
	public static final Long ORDER_PRIORITY_LOW=new Long(3);
	
	public static boolean isEmptyOrNull(Object object){
		if(object==null)
			return true;
		
		if(object instanceof String){
			String s = (String)object;
			if(s.equals("") || s==null)
				return true;
		}
		
		return false;
	}
	
	public static String getUrl(String url) throws Exception{
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}

	
	return result.toString();
	}
	
	
	
	public static List<NameValuePair> getMarkerInformation(String lon, String lat,String ilce,String mahalle,String sokak,String KapiNo){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		list.add(new NameValuePair("Enlem",lon));
		list.add(new NameValuePair("Boylam",lat));
		list.add(new NameValuePair("Ilce",ilce));
		list.add(new NameValuePair("Mahalle",mahalle));
		list.add(new NameValuePair("Sokak",sokak));
		list.add(new NameValuePair("KapiNo",KapiNo));
		return list;
	}
	
	public static List<NameValuePair> getMarkerInformation(String placeName,String lon, String lat){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(!placeName.equals(""))
			list.add(new NameValuePair("Isim",placeName));
		list.add(new NameValuePair("Enlem",lon));
		list.add(new NameValuePair("Boylam",lat));
		
		return list;
	}
	
	public List<NameValuePair> getMarkerInformation(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		return list;
	}
	
	public static void updateUser(String id,Order order ,String message,boolean immediately) throws Exception{
		Sender sender= new Sender(key);
    	
    	Result result = sender.send(prepareMessage(immediately, message, order), id,10);
    	System.out.println("bitti");
	}
	
	public static Message prepareMessage(boolean immediately,String message,Order order){
		if (immediately) {
			Message message2 = new Message.Builder()
	        .collapseKey("deneme")
	        //.timeToLive(300)
	        .delayWhileIdle(true)
	        //.dryRun(true)
	        //.restrictedPackageName(restrictedPackageName)
	        .addData("message", message)
	        .addData("orderId", order.getId().toString())
	        .addData("immediately", "true")
	        .addData("lat",order.getLat())
        .addData("lng",order.getLng())
	        .build();
			return message2;
		}else{
		Message message2 = new Message.Builder()
        .collapseKey("deneme")
        //.timeToLive(300)
        .delayWhileIdle(true)
        //.dryRun(true)
        //.restrictedPackageName(restrictedPackageName)
        .addData("message", message)
        .addData("orderId", order.getId().toString())
        .addData("immediately", "false")
        
        .build();
		return message2;
		}
		
	}
	
	public static String findValueOfSelectItem(List<SelectItem> values,String target){
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			if(selectItem.getValue().toString().equals(target))
				return selectItem.getLabel();
		}
		
		return "";
	}
}
