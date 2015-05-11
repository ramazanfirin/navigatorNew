package com.navigator.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import com.nagivator.model.Order;

public class GeoUtil {

	public static GeocodeResponse getLocationInformation(String lat,String lng) throws Exception{
		final Geocoder geocoder = new Geocoder();
		LatLng latLng = new LatLng(new BigDecimal(lat), new BigDecimal(lng));
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(latLng).getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		return geocoderResponse;
	}
	
	public static Boolean check(GeocodeResponse reocodeResponse){
		if(reocodeResponse == null || reocodeResponse.getResults()==null || reocodeResponse.getResults().size()==0)
			return false;
		
		if(reocodeResponse.getResults().get(0)==null)
			return false;
					
		return true;		
	}
	
	public static String getLocationData(GeocodeResponse reocodeResponse,String type) {
		
		try {
			if(!check(reocodeResponse))
				return null;
			
			return getData(reocodeResponse.getResults().get(0), type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getData(GeocoderResult geocoderResult,String type){
		List<GeocoderAddressComponent> list = geocoderResult.getAddressComponents();
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			GeocoderAddressComponent geocoderAddressComponent = (GeocoderAddressComponent) iterator.next();
			if(geocoderAddressComponent.getTypes().contains(type))
				return geocoderAddressComponent.getLongName();
		}
		
		return "";
	}
	
	
	public static void fillObject(Order instance,String lat,String lng){
			try {
				if(!lat.equals("0") && !lat.equals("0")){
					System.out.println(lat+","+lng);
					GeocodeResponse geocodeResponse = getLocationInformation(lat, lng);
					//instance.setCounty(getLocationData(geocodeResponse,"country"));
					instance.setIlce(getLocationData(geocodeResponse,"administrative_area_level_2"));
					instance.setSokak(getLocationData(geocodeResponse,"route"));
					instance.setMahalle(getLocationData(geocodeResponse,"neighborhood"));
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		
		//return instance;
	}
	
	
	public static void main(String[] args) {
		Order order  = new Order();
		fillObject(order, "38.73188201062065", "35.46985973487608");
		System.out.println(order.getIlce());
		System.out.println(order.getMahalle());
		System.out.println(order.getSokak());
	}
}
