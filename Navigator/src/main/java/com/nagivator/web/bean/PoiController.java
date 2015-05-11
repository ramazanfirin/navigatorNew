package com.nagivator.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.gmap.GMap;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.stereotype.Component;

import com.nagivator.model.Poi;


@Component
@ManagedBean(name="poiController")
@SessionScoped
public class PoiController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(PoiController.class);
	
	Poi poi = new Poi();
	List<Poi> poiList = new ArrayList<Poi>();
	MapModel emptyModel = new DefaultMapModel();
	GMap map = new GMap();
	
	String name;
	
	Marker draggableMarker;;
		
	public PoiController() {
		super();
		reset();

	}
	
	public void reset(){
		 poi = new Poi();
		
	}
	
	public void showPoi(){
		emptyModel.getMarkers().clear();
		double lat = Double.parseDouble(poi.getLat());
		double lng = Double.parseDouble(poi.getLng());
		Marker marker = new Marker(new LatLng(lat,lng), poi.getName());
		emptyModel.addOverlay(marker);
	} 
	
	public void prepareDraggableMarker(){
		emptyModel.getMarkers().clear();
		double lat = Double.parseDouble("38.72956137445706");
		double lng = Double.parseDouble("35.47995459062804");
		draggableMarker = new Marker(new LatLng(lat,lng));
		draggableMarker.setDraggable(true);
		emptyModel.addOverlay(draggableMarker);
	}
	
	public void onMarkerDrag(MarkerDragEvent event) {
		draggableMarker = event.getMarker();
        poi.setLat(String.valueOf(draggableMarker.getLatlng().getLat()));
        poi.setLng(String.valueOf(draggableMarker.getLatlng().getLng()));
    }
	
	public void create(){
		try{
					
			getServiceProvider().getPersistanceService().saveOrUpdate(poi);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			reset();	
			search();
			// send mail for password
			RequestContext.getCurrentInstance().execute("PF('createUser').hide()");
//			RequestContext.getCurrentInstance().update("dataGrid");
//			RequestContext.getCurrentInstance().update(":form1:dataGrid");
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		
	}
	
	
	

	public void delete(){
		try{
			getServiceProvider().getPersistanceService().deletePoi(poi);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			reset();	
			search();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	
	public void search(){
		try{
			poiList = getServiceProvider().getPersistanceService().searchPoi(name);
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Poi getPoi() {
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	public List<Poi> getPoiList() {
		return poiList;
	}

	public void setPoiList(List<Poi> poiList) {
		this.poiList = poiList;
	}

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public GMap getMap() {
		return map;
	}

	public void setMap(GMap map) {
		this.map = map;
	}
	
	
}
