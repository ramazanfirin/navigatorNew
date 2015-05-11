package com.nagivator.web.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.stereotype.Component;

import com.nagivator.model.cbs.Bina;
import com.nagivator.model.cbs.Ilce;
import com.nagivator.model.cbs.Mahalle;
import com.nagivator.model.cbs.Sokak;
import com.navigator.util.CityCurfTest;
import com.navigator.util.CityCurfUtil;
import com.navigator.util.OnlineCoordinateConverter;


@Component
@ManagedBean(name="branchController")
@SessionScoped
public class ParseController extends BaseController{

	public String parseIlce() throws Exception{
		getServiceProvider().getPersistanceService().getIlceList(true);
		
		return "";
	}
	
	public String insertIlce() throws Exception{
		List<SelectItem> itemList = CityCurfTest.getIlceList(); 
		for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			Ilce ilce = new Ilce();
			ilce.setName(selectItem.getLabel());
			ilce.setStringQuery(selectItem.getValue().toString());
			ilce.setComplated(false);
			getServiceProvider().getPersistanceService().saveOrUpdate(ilce);
		}
		
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"insert ilce tamamlandi",""));
		return "";
	}
	
	public String insertMahalle() throws Exception{
		List<Ilce> ilceList = getServiceProvider().getPersistanceService().getIlceList(false);
		for (Iterator iterator = ilceList.iterator(); iterator.hasNext();) {
			Ilce ilce = (Ilce) iterator.next();
			List<SelectItem> mahalleList = CityCurfTest.getMahalleList(ilce.getStringQuery());
			for (Iterator iterator2 = mahalleList.iterator(); iterator2.hasNext();) {
				SelectItem selectItem = (SelectItem) iterator2.next();
				Mahalle mahalle = new Mahalle();
				mahalle.setName(selectItem.getLabel());
				mahalle.setStringQuery(selectItem.getValue().toString());
				mahalle.setComplated(false);
				mahalle.setIlce(ilce);
				getServiceProvider().getPersistanceService().saveOrUpdate(mahalle);
			}
			ilce.setComplated(true);
			getServiceProvider().getPersistanceService().saveOrUpdate(ilce);
		}
		
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"mahalle ilce tamamlandi",""));
		return "";
	}
	
	
	
	public String insertSokak() throws Exception{
		List<Mahalle> mahalleList = getServiceProvider().getPersistanceService().getMahalleList(false);
		for (Iterator iterator = mahalleList.iterator(); iterator.hasNext();) {
			Mahalle mahalle = (Mahalle) iterator.next();
			List<SelectItem> sokakList = CityCurfTest.getSokakList(mahalle.getStringQuery());
			for (Iterator iterator2 = sokakList.iterator(); iterator2.hasNext();) {
				SelectItem selectItem = (SelectItem) iterator2.next();
				Sokak sokak = new Sokak();
				sokak.setName(selectItem.getLabel());
				sokak.setStringQuery(selectItem.getValue().toString());
				sokak.setComplated(false);
				sokak.setMahalle(mahalle);
				getServiceProvider().getPersistanceService().saveOrUpdate(sokak);
			}
			mahalle.setComplated(true);
			getServiceProvider().getPersistanceService().saveOrUpdate(mahalle);
		}
		
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"sokak ilce tamamlandi",""));
		return "";
	}
	
	public String insertBina() throws Exception{
		List<Sokak> sokakList = getServiceProvider().getPersistanceService().getSokakList(false);
		for (Iterator iterator = sokakList.iterator(); iterator.hasNext();) {
			Sokak sokak = (Sokak) iterator.next();
			List<SelectItem> binaList = CityCurfTest.getBinaList(sokak.getStringQuery());
			List<Bina> tempBinaList  = new ArrayList<Bina>();
			for (Iterator iterator2 = binaList.iterator(); iterator2.hasNext();) {
				SelectItem selectItem = (SelectItem) iterator2.next();
				Bina bina = new Bina();
				bina.setName(selectItem.getLabel());
				bina.setStringQuery(selectItem.getValue().toString());
				bina.setComplated(false);
				bina.setSokak(sokak);
				tempBinaList.add(bina);
				
			}
			getServiceProvider().getPersistanceService().saveOrUpdateAll(tempBinaList);
			sokak.setComplated(true);
			getServiceProvider().getPersistanceService().saveOrUpdate(sokak);
		}
		
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"sokak ilce tamamlandi",""));
		return "";
	}
	
	
	public String insertCoordinate() throws Exception{
		
		List<Bina> sokakList = getServiceProvider().getPersistanceService().getBinaList(false);
		List<Bina> tempBinaList  = new ArrayList<Bina>();
		
		
			for (Iterator iterator = sokakList.iterator(); iterator.hasNext();) {
				Bina bina = (Bina) iterator.next();
				try {
					List<String> list= CityCurfUtil.getKapiNo(bina.getStringQuery());
					String lat=list.get(0);
					String lng=list.get(1);
//					String[]  coordinates = OnlineCoordinateConverter.convertCoordinate( lat,lng).split(" ");
//					Float lngFloat= Float.parseFloat(coordinates[0]);
//					Float latFloat= Float.parseFloat(coordinates[1]);
//					lngFloat = lngFloat-Float.parseFloat("0.000230");
//					latFloat = latFloat-Float.parseFloat("0.000972");
//					bina.setLat(latFloat);
//					bina.setLng(lngFloat);
					bina.setComplated(true);
					bina.setOrijinalLat(Float.parseFloat(lat));
					bina.setOrijinalLng(Float.parseFloat(lng));				
					tempBinaList.add(bina);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tempBinaList.size()==100){
					getServiceProvider().getPersistanceService().saveOrUpdateAll(tempBinaList);
					tempBinaList.clear();
				}
			}
		
		
		
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinar tespit edildi",""));
		
			return "";
		
		
	}
	
	public String converToEG84() throws Exception{
		List<Bina> sokakList = getServiceProvider().getPersistanceService().getBinaList(true);
		List<Bina> tempBinaList  = new ArrayList<Bina>();
		
		
			for (Iterator iterator = sokakList.iterator(); iterator.hasNext();) {
				Bina bina = (Bina) iterator.next();
				if(!bina.getComplated())
					continue;
				
				if(!(bina.getLat()==null) )
					continue;
				
				if(bina.getOrijinalLat()==null || bina.getOrijinalLng()==null)
					continue;
				
				try {
//					List<String> list= CityCurfUtil.getKapiNo(bina.getStringQuery());
//					String lat=list.get(0);
//					String lng=list.get(1);
					String[]  coordinates = OnlineCoordinateConverter.convertCoordinate(bina.getOrijinalLat().toString(),bina.getOrijinalLng().toString()).split(" ");
					Float lngFloat= Float.parseFloat(coordinates[0]);
					Float latFloat= Float.parseFloat(coordinates[1]);
					latFloat = latFloat-Float.parseFloat("0.000972");
					lngFloat = lngFloat-Float.parseFloat("0.000230");
					bina.setLat(latFloat);
					bina.setLng(lngFloat);
					bina.setComplated(true);
//					bina.setOrijinalLat(Float.parseFloat(lat));
//					bina.setOrijinalLng(Float.parseFloat(lng));				
					tempBinaList.add(bina);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(tempBinaList.size()==100){
					getServiceProvider().getPersistanceService().saveOrUpdateAll(tempBinaList);
					tempBinaList.clear();
				}
			}
		
		
		
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinar tespit edildi",""));
		
		
		return "";
	}
}