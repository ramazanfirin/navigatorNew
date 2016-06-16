package com.nagivator.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.component.gmap.GMap;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.nagivator.model.Branch;
import com.nagivator.model.Order;
import com.nagivator.model.OrderPriority;
import com.nagivator.model.Poi;
import com.nagivator.model.Vehicle;
import com.navigator.util.CityCurfTest;
import com.navigator.util.CityCurfUtil;
import com.navigator.util.GeoUtil;
import com.navigator.util.NameValuePair;
import com.navigator.util.Util;

@ManagedBean
@SessionScoped
public class Navigator2 extends BaseController implements Serializable {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	private static Logger LOGGER =Logger.getLogger(Navigator2.class);
	
	public Navigator2() throws Exception {
		super();
		try {
			ilceList = (List<SelectItem>)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ilceList");
			if(ilceList==null|| ilceList.size()==0){
				ilceList = CityCurfTest.getIlceList();
				FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("ilceList", ilceList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MapModel emptyModel = new DefaultMapModel();
	GMap map = new GMap();
	String binaNo;
	
	List<SelectItem> ilceList= new ArrayList<SelectItem>();
	List<SelectItem> mahalleList= new ArrayList<SelectItem>();
	List<SelectItem> sokakList= new ArrayList<SelectItem>();
	List<SelectItem> binaList= new ArrayList<SelectItem>();
	
	String ilce;
	String  mahalle;
	String sokak;
	String bina;

	String importantPlace;
	private double manuelInputlat;
	private double manuelInputlng;
	
	String hiddenLat;
	String hiddenLng;
	
	String lat;
	String lng;
	
	String centerLat;
	String centerLng;
	String centerZoom;
	
	Marker marker;
	
	String description;
	
	Vehicle vehicle = new Vehicle();
	
	Branch branch = new Branch();
	
	Map<String,Long> vehicleOptions = new HashMap<String,Long>();
	
	List<Branch> branchList = new ArrayList<Branch>();
	
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	OrderPriority priority = new OrderPriority();
	
	boolean sendImmediately=true;
	
	List<NameValuePair> userMessages = new ArrayList<NameValuePair>();
	
	String distance;
	String duration;
	String remoteAddress;
	
	boolean showMeskunMahal=false;
	
	String meskunMahal;
	
	public void hideMeskunMahal(){
		showMeskunMahal = false;
		visualiteMeskunMahal();
		
	}
	
	public void showOneMeskunMahal(){
		try {
			List<Poi> list = getServiceProvider().getPersistanceService().searchPoi(meskunMahal);
			Poi poi = list.get(0);
			Marker marker = new Marker(new LatLng(Double.parseDouble(poi.getLat()), Double.parseDouble(poi.getLng())),"", "",blueDot);
			marker.setTitle("Meskun Mahal " + poi.getName()		);
			emptyModel.addOverlay(marker);
			setCenter(poi.getLat(), poi.getLng());
			setZoom("15");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}
	
	public List<String> completeText(String query) {
		List<String> returnList = new  ArrayList<String>();
		try {
        	
			List<Poi> list = getServiceProvider().getPersistanceService().searchPoi(query);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Poi poi = (Poi) iterator.next();
				returnList.add(poi.getName());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
        return returnList;
    }
	
	public void visualiteMeskunMahal(){
		try {
			if(showMeskunMahal){
			
			List<Poi> list =getServiceProvider().getPersistanceService().getPoiList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Poi poi = (Poi) iterator.next();
				Marker marker = new Marker(new LatLng(Double.parseDouble(poi.getLat()), Double.parseDouble(poi.getLng())),"", "",blueDot);
				marker.setTitle("Meskun Mahal " + poi.getName()		);
				emptyModel.addOverlay(marker);
				
			}
		}else{
			List<Marker> list = emptyModel.getMarkers();
			List<Marker> newList = new ArrayList<Marker>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Marker marker = (Marker) iterator.next();
				if(marker.getTitle().contains("Meskun"))
					newList.add(marker);
			}
			removeMarkersFromMap(newList);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}
	
	public void removeMarkersFromMap(List<Marker> newList){
		for (Iterator iterator2 = newList.iterator(); iterator2.hasNext();) {
			Marker marker = (Marker) iterator2.next();
			emptyModel.getMarkers().remove(marker);
		}
	}
	
	public void clearMarkers(){
		List<Marker> list = emptyModel.getMarkers();
		List<Marker> newList = new ArrayList<Marker>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Marker marker = (Marker) iterator.next();
			if(marker.getTitle().contains("Meskun"))
				continue;
			newList.add(marker);
		}
		removeMarkersFromMap(newList);
		
	}
	
	public void showRotaValues(){
		userMessages.clear();
		NameValuePair distancePair = new NameValuePair("Uzaklik", distance);
		NameValuePair durationPair = new NameValuePair("Sure", duration);
		NameValuePair remoteAddressPair = new NameValuePair("Adres", remoteAddress);
		
		userMessages.add(distancePair);
		userMessages.add(durationPair);
		userMessages.add(remoteAddressPair);
	}
	
	public void addMarkerFromMap() throws Exception{
		try {
			addMarkerToMap(Double.valueOf(lat),Double.valueOf(lng),getPointOfInformation(),"");
			setCenter(centerLat, centerLng);
			setZoom(centerZoom);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}
	
	public List<NameValuePair> getPointOfInformation() throws Exception{
//		String[] projections = OnlineCoordinateConverter.convertToBelediyeCoordinate(lng,lat).split(" ");
//		
//	    Float lngFloat= Float.parseFloat(projections[0]);
//		Float latFloat= Float.parseFloat(projections[1]);
//		lngFloat = lngFloat+Float.parseFloat("31.2");
//		latFloat = latFloat+Float.parseFloat("102.5"); 
		//List<NameValuePair> values = CityCurfUtil.getInformaitonOfCoordinates(String.valueOf(lngFloat), String.valueOf(latFloat));
		List<NameValuePair> values = CityCurfUtil.getInformaitonOfCoordinates(String.valueOf(lng), String.valueOf(lat));
		values.add(new NameValuePair("Enlem", String.valueOf(lat)));
		values.add(new NameValuePair("Boylam", String.valueOf(lng)));
		return values;
	}
	
	public void getKapiNo() throws Exception{
		try {
		
			List<String> list= CityCurfUtil.getKapiNo(bina);
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinar tespit edildi",""));
			String lat=list.get(0);
			String lng=list.get(1);
			/*
			String[]  coordinates = OnlineCoordinateConverter.convertCoordinate( lat,lng).split(" ");
		    Float lngFloat= Float.parseFloat(coordinates[0]);
			Float latFloat= Float.parseFloat(coordinates[1]);
			lngFloat = lngFloat-Float.parseFloat("0.000230");
			latFloat = latFloat-Float.parseFloat("0.000922");
			*/
			Float lngFloat= Float.parseFloat(lat);
			Float latFloat= Float.parseFloat(lng);
			
			List<NameValuePair> data = Util.getMarkerInformation(String.valueOf(latFloat), String.valueOf(lngFloat), Util.findValueOfSelectItem(ilceList,ilce), Util.findValueOfSelectItem(mahalleList,mahalle), Util.findValueOfSelectItem(sokakList,sokak), Util.findValueOfSelectItem(binaList,bina));
			addMarkerToMap(Double.valueOf(String.valueOf(latFloat)).doubleValue(),Double.valueOf(String.valueOf(lngFloat)).doubleValue(),data,"");
			
			setCenter(String.valueOf(latFloat), String.valueOf(lngFloat));
			setZoom("17");
			
		   System.out.println("bitti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "koordinat tespit edilirken hata olustu",""));
		}
	}
	
	public void getImportantPlaceCoordinates(){
		try {
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(0, new NameValuePair("isim", importantPlace));
			addMarkerToMap(Double.parseDouble(hiddenLat),Double.parseDouble(hiddenLng),data,blueDot);
			
			setCenter(hiddenLat, hiddenLng);
			setZoom("17");
			System.out.println("bitti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "koordinat tespit edilirken hata olustu",""));
		}
	}
	
public void investigatePoint() throws Exception{
		
		try {
			userMessages.clear();
			userMessages.addAll(getPointOfInformation());		
			
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Inceleme tamamlandi","Lat:" + lat + ", Lng:" + lng));
//			setCenter(centerLat,centerLng);
//			setZoom(centerZoom);
			//showInfoWindow(values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}

public void onMarkerSelect(OverlaySelectEvent event) {
	try {
		marker = (Marker) event.getOverlay();
		lat = String.valueOf(marker.getLatlng().getLat());
		lng = String.valueOf(marker.getLatlng().getLng());
		System.out.println("dikkat "+ lat+","+lng);
		//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected",marker.getTitle()));
		setDescription("");;
		vehicleOptions= new HashMap<String, Long>();
		branchList = getServiceProvider().getPersistanceService().getBranchList();
		branch = new Branch();
		if(branchList.size()>0)
		vehicleOptions= prepareVehicleList(branchList.get(0).getId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
	}
	//RequestContext.getCurrentInstance().execute("PF('sendDialog').show()");	
}

public String showInfoWindow(List<NameValuePair> list){
	String finalReturn="<div id=\"content\"><div id=\"siteNotice\"></div><h1 id=\"firstHeading\" class=\"firstHeading\">Aciklama</h1><div id=\"bodyContent\">";
	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		NameValuePair selectItem = (NameValuePair) iterator.next();
		finalReturn = finalReturn+""+selectItem.getName()+":"+selectItem.getValue()+"<br/>";
		
	}
	finalReturn = finalReturn +"</div></div>";
	RequestContext.getCurrentInstance().execute("openInfoWindow2('"+finalReturn+"')");
	return "";
}

	
	public void getManuelInputData(){
		try {
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(0, new NameValuePair("Enlem", String.valueOf(manuelInputlat)));
			data.add(1, new NameValuePair("Boylam", String.valueOf(manuelInputlng)));
			addMarkerToMap(manuelInputlat,manuelInputlng,data,"");
			
			setCenter(String.valueOf(manuelInputlat), String.valueOf(manuelInputlng));
			setZoom("17");
			System.out.println("bitti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "koordinat tespit edilirken hata olustu",""));
		}
	}

	public void addMarkerToMap(double lat,double lng,Object data,String icon){
		Marker marker = new Marker(new LatLng(lat, lng),"", data,icon);
		marker.setTitle(getLabelForMarker(data));
		emptyModel.addOverlay(marker);
		//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added","Lat:" + lat + ", Lng:" + lng));
		
		
	}
	
	public String getLabelForMarker(Object data){
		String result =	"";
		List<NameValuePair> list = (List<NameValuePair>)data;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			NameValuePair nameValuePair = (NameValuePair) iterator.next();
			//result = result+" "+nameValuePair.getName()+":"+nameValuePair.getValue()+"& CRLF &";
			result = result+nameValuePair.getValue()+",";
		}
		return result+"";
	}
	
	public String setCenter(String latValue, String lngValue) {
		RequestContext.getCurrentInstance().execute("setMapCenter2(" + latValue + "," + lngValue + ")");
		//map.setCenter(manuelInputlat+","+manuelInputlng);map.getCenter();map.getZoom();
		
		return "";
	}

	public String setZoom(String zoom) {
		RequestContext.getCurrentInstance().execute("setZoom2(" + zoom + ")");
		//map.setZoom(17);
		return "";
	}
	
public void sendCoordinateImmediately() throws Exception{
	vehicle =getServiceProvider().getPersistanceService().getVechile(vehicle.getId());
	branch =getServiceProvider().getPersistanceService().getBranch(branch.getId());
	if(vehicle.getDevice().getRegId().equals("") || vehicle.getDevice().getRegId()==null ){
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seçilen araçtaki cihaz kayıt edilmemiş",""));
		return;
	}
	
		sendImmediately = true;
		sendCoordinate();
	}
	
	public void sendCoordinate() throws Exception{
		try {
			currentUser = getCurrentUser();
			vehicle =getServiceProvider().getPersistanceService().getVechile(vehicle.getId());
			if(description==null || description.equals("")){
				//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata Olustu", "Not Alani bos olamaz"));
				//return;
				
				description= "Gorev Tarihi : "+ sdf.format(new Date());
			}
			
			Order order = new Order();
			order.setLat(String.valueOf(marker.getLatlng().getLat()));
			order.setLng(String.valueOf(marker.getLatlng().getLng()));
			order.setVehicle(vehicle);
			order.setUser(currentUser);
			order.setDate(new Date());
			order.setAddress(description);
			order.setBranch(branch);
			
			List<NameValuePair> list = (List<NameValuePair>)marker.getData();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				NameValuePair nameValuePair = (NameValuePair) iterator.next();
				if(nameValuePair.getName().equals("Ilce")){
					order.setIlce(nameValuePair.getValue());
					
				}if(nameValuePair.getName().equals("Mahalle")){
					order.setMahalle(nameValuePair.getValue());
				}if(nameValuePair.getName().equals("Sokak")){
					order.setSokak(nameValuePair.getValue());
				}if(nameValuePair.getName().equals("KapiNo")){
					order.setBina(nameValuePair.getValue());
				}
			}
			
			if(order.getIlce()==null || order.getIlce().equals("")){
				GeoUtil.fillObject(order, String.valueOf(lat),String.valueOf(lng));
			}
			
			order.getStatus().setId(Util.ORDER_STATUS_NEW);
			order.setPriority(priority);
			//getServiceProvider().getPersistanceService().saveOrUpdate(currentUser);
			getServiceProvider().getPersistanceService().saveOrUpdate(order);
			
			Order tempOrder = (Order)getServiceProvider().getPersistanceService().getObject(Order.class, order.getId());
			Util.updateUser(tempOrder.getVehicle().getDevice().getRegId(),tempOrder, "newOrder",sendImmediately);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinat Gönderildi",""));
			sendImmediately = false;
			RequestContext.getCurrentInstance().execute("hideDialog()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "koordinat tespit edilirken hata olustu",""));
		}
		
		
	}
	
public void branchOnChange() throws Exception{
		
		try {
			//mahalleList = CityCurfUtil.getMahalleList(ilce);
			vehicleOptions = prepareVehicleList(branch.getId());
			System.out.println("onchange calisti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
			e.printStackTrace();
		}
		
	}
	
public void ilceOnChange() throws Exception{
		
		try {
			//mahalleList = CityCurfUtil.getMahalleList(ilce);
			mahalleList = CityCurfTest.getMahalleList(ilce);
			sokakList  = new ArrayList<SelectItem>();
			binaList  = new ArrayList<SelectItem>();
			System.out.println("onchange calisti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
			e.printStackTrace();
		}
		
	}
	
	public void mahalleOnChange() throws Exception{
		
		try {
			//sokakList = CityCurfUtil.getSokakList(mahalle);
			sokakList = CityCurfTest.getSokakList(mahalle);
			binaList  = new ArrayList<SelectItem>();
			System.out.println("onchange calisti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
		
	}
	
	public void sokakOnChange() throws Exception{
		
		try {
			//binaList = CityCurfUtil.getSokakList(sokak);
			binaList = CityCurfTest.getBinaList(sokak);
			System.out.println("onchange calisti");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
		
	}
	
	public Map<String,Long> prepareVehicleList(Long branchId){
		Map<String,Long> map = new HashMap<String,Long>();
		try{
			
			Branch branch =  getServiceProvider().getPersistanceService().getBranch(branchId);
			Set<Vehicle> vehicleList = branch.getVehicleList();
			for (Iterator iterator = vehicleList.iterator(); iterator.hasNext();) {
				Vehicle vehicle = (Vehicle) iterator.next();
				map.put(vehicle.getPlate(),vehicle.getId());
				
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluÅŸtu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		return map;
	}
	
	
	public GMap getMap() {
		return map;
	}

	public void setMap(GMap map) {
		this.map = map;
	}

	public String getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public List<SelectItem> getIlceList() {
		return ilceList;
	}

	public void setIlceList(List<SelectItem> ilceList) {
		this.ilceList = ilceList;
	}

	public List<SelectItem> getMahalleList() {
		return mahalleList;
	}

	public void setMahalleList(List<SelectItem> mahalleList) {
		this.mahalleList = mahalleList;
	}

	public List<SelectItem> getSokakList() {
		return sokakList;
	}

	public void setSokakList(List<SelectItem> sokakList) {
		this.sokakList = sokakList;
	}

	public List<SelectItem> getBinaList() {
		return binaList;
	}

	public void setBinaList(List<SelectItem> binaList) {
		this.binaList = binaList;
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = ilce;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}

	public String getSokak() {
		return sokak;
	}

	public void setSokak(String sokak) {
		this.sokak = sokak;
	}

	public String getBina() {
		return bina;
	}

	public void setBina(String bina) {
		this.bina = bina;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImportantPlace() {
		return importantPlace;
	}

	public void setImportantPlace(String importantPlace) {
		this.importantPlace = importantPlace;
	}

	public double getManuelInputlat() {
		return manuelInputlat;
	}

	public void setManuelInputlat(double manuelInputlat) {
		this.manuelInputlat = manuelInputlat;
	}

	public double getManuelInputlng() {
		return manuelInputlng;
	}

	public void setManuelInputlng(double manuelInputlng) {
		this.manuelInputlng = manuelInputlng;
	}

	public String getHiddenLat() {
		return hiddenLat;
	}

	public void setHiddenLat(String hiddenLat) {
		this.hiddenLat = hiddenLat;
	}

	public String getHiddenLng() {
		return hiddenLng;
	}

	public void setHiddenLng(String hiddenLng) {
		this.hiddenLng = hiddenLng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(String centerLat) {
		this.centerLat = centerLat;
	}

	public String getCenterLng() {
		return centerLng;
	}

	public void setCenterLng(String centerLng) {
		this.centerLng = centerLng;
	}

	public String getCenterZoom() {
		return centerZoom;
	}

	public void setCenterZoom(String centerZoom) {
		this.centerZoom = centerZoom;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public OrderPriority getPriority() {
		return priority;
	}

	public void setPriority(OrderPriority priority) {
		this.priority = priority;
	}

	public boolean isSendImmediately() {
		return sendImmediately;
	}

	public void setSendImmediately(boolean sendImmediately) {
		this.sendImmediately = sendImmediately;
	}

	public List<NameValuePair> getUserMessages() {
		return userMessages;
	}

	public void setUserMessages(List<NameValuePair> userMessages) {
		this.userMessages = userMessages;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public boolean isShowMeskunMahal() {
		return showMeskunMahal;
	}

	public void setShowMeskunMahal(boolean showMeskunMahal) {
		this.showMeskunMahal = showMeskunMahal;
	}

	public String getMeskunMahal() {
		return meskunMahal;
	}

	public void setMeskunMahal(String meskunMahal) {
		this.meskunMahal = meskunMahal;
	}

	public void setVehicleOptions(Map<String, Long> vehicleOptions) {
		this.vehicleOptions = vehicleOptions;
	}

	public Map<String, Long> getVehicleOptions() {
		return vehicleOptions;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}




	
}