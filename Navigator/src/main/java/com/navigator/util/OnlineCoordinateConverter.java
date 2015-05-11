package com.navigator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

public class OnlineCoordinateConverter {

	public static String convertCoordinate(String x,String y) throws ClientProtocolException, IOException{
		String url = "http://geoweb.hft-stuttgart.de/openwctsv2/result.php";
		//url = "http://localhost/Ulasim.aspx/JSDurakKordinatAdi";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("search", "codes"));
		urlParameters.add(new BasicNameValuePair("source", "2322"));
		urlParameters.add(new BasicNameValuePair("target", "4326"));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("target_name", ""));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("coords", x+" "+ y));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("format", "api"));
		urlParameters.add(new BasicNameValuePair("submit", "1"));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		 
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(response.getEntity().getContent()));
	
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println(result);
		return result.toString();
	}
	
	public static String convertToBelediyeCoordinate(String x,String y) throws ClientProtocolException, IOException{
		String url = "http://geoweb.hft-stuttgart.de/openwctsv2/result.php";
		//url = "http://localhost/Ulasim.aspx/JSDurakKordinatAdi";
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("search", "codes"));
		urlParameters.add(new BasicNameValuePair("source", "4326"));
		urlParameters.add(new BasicNameValuePair("target", "2322"));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("target_name", ""));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("coords", x+" "+ y));
		urlParameters.add(new BasicNameValuePair("source_name:", ""));
		urlParameters.add(new BasicNameValuePair("format", "api"));
		urlParameters.add(new BasicNameValuePair("submit", "1"));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		 
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(response.getEntity().getContent()));
	
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.out.println(result);
		return result.toString();
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {

	String a = 	convertToBelediyeCoordinate("35.525876", "38.634437");
	System.out.println(a);
	}
}
