package com.nagivator.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.nagivator.model.Order;
import com.nagivator.model.OrderPriority;
import com.nagivator.model.Vehicle;
import com.navigator.util.CityCurfTest;
import com.navigator.util.CityCurfUtil;
import com.navigator.util.GeoUtil;
import com.navigator.util.NameValuePair;
import com.navigator.util.Util;

@ManagedBean
@SessionScoped
public class Navigator extends BaseController implements Serializable {

	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//String blueDot = "http://maps.google.com/mapfiles/ms/micons/blue-dot.png";

	private MapModel emptyModel;

	public Navigator() throws Exception {
		super();
		emptyModel = new DefaultMapModel();
		//ilceList = CityCurfUtil.getIlceList();
		try {
			ilceList = (List<SelectItem>)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ilceList");
			if(ilceList==null){
				ilceList = CityCurfTest.getIlceList();
				FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("ilceList", ilceList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String title;

	private double lat;

	private double lng;

	private double centerlat;

	private double centerlng;
	
	private double projectionlat;
	
	private double projectionlng;
	
	private double zoom;

	private String address = "Cumhuriyet Mah. tasdelen istanbul";

	private Marker marker;

	String informationOfPoint;
	
	double deviationLat=14;
	
	double deviationlng = 27;
	
	String dataSource;
	
	String infoContent ;
	
	String ilce;
	String  mahalle;
	String sokak;
	String bina;
	
	String importantPlace;
	
	List<SelectItem> ilceList= new ArrayList<SelectItem>();
	List<SelectItem> mahalleList= new ArrayList<SelectItem>();
	List<SelectItem> sokakList= new ArrayList<SelectItem>();
	List<SelectItem> binaList= new ArrayList<SelectItem>();
	
	private double manuelInputlat;
	
	private double manuelInputlng;
	
	List<NameValuePair>   markerAttiruteList;
	
	Vehicle vehicle = new Vehicle();
	
	OrderPriority priority = new OrderPriority();
	
	String orijinalAddress;
	
	boolean sendImmediately = false;
	
	public String searchAddress() {
		try {
			final Geocoder geocoder = new Geocoder();
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder	.geocode(geocoderRequest);
			BigDecimal latValue = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat();
			BigDecimal lngValue = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng();
			setCenter(latValue.toString(), lngValue.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
			
		}
		return "";
	}

	public String clearMarkers() {
		try {
			emptyModel.getMarkers().clear();
			emptyModel.getMarkers();
			setCenter(String.valueOf(centerlat), String.valueOf(centerlng));
			setZoom(String.valueOf(zoom));
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
			e.printStackTrace();
			return "";
		}

	}
	
	public void updateDataSource() {
		RequestContext.getCurrentInstance().execute("updateSourceData('cbs')");
	}
	
	public String resetRotaFromHtml() {
		RequestContext.getCurrentInstance().execute("resetRota()");
		return "";
	}


	public String setCenter(String latValue, String lngValue) {
		RequestContext.getCurrentInstance().execute("setMapCenter(" + latValue + "," + lngValue + ")");
		return "";
	}

	public String setZoom(String zoom) {
		RequestContext.getCurrentInstance().execute("setZoom(" + zoom + ")");
		return "";
	}
	
	public String drawDirection(String latValue, String lngValue) {
		RequestContext.getCurrentInstance().execute("drawDirection(" + latValue + "," + lngValue + ")");
		
		return "";
	}
	
	public String showInfoWindow(List<NameValuePair> list){
		String finalReturn="<div id=\"content\"><div id=\"siteNotice\"></div><h1 id=\"firstHeading\" class=\"firstHeading\">Aciklama</h1><div id=\"bodyContent\">";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			NameValuePair selectItem = (NameValuePair) iterator.next();
			finalReturn = finalReturn+""+selectItem.getName()+":"+selectItem.getValue()+"<br/>";
			
		}
		finalReturn = finalReturn +"</div></div>";
		RequestContext.getCurrentInstance().execute("openInfoWindow('"+finalReturn+"')");
		return "";
	}
	
	public String convertFromGPSData(String x,String y){
		RequestContext.getCurrentInstance().execute("doProjectionFromGPS('"+x+"','"+y+"')");
		return "";
	}

	public void addMarker() throws Exception{
		try {
			addMarkerToMap(lat,lng,getPointOfInformation(),"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}
	
	public void addMarkerFromGPSData() {
		
		try {
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			if(dataSource.equals("Google")){
				//data =getPointOfInformation();
				data.add(0, new NameValuePair("isim", importantPlace));
				addMarkerToMap(manuelInputlat,manuelInputlng,data,blueDot);
			}else{
				data = Util.getMarkerInformation(String.valueOf(manuelInputlat), String.valueOf(manuelInputlng), findValueOfSelectItem(ilceList,ilce), findValueOfSelectItem(mahalleList,mahalle), findValueOfSelectItem(sokakList,sokak), findValueOfSelectItem(binaList,bina));
				addMarkerToMap(manuelInputlat,manuelInputlng,data,"");
			}
			setCenter(String.valueOf(manuelInputlat), String.valueOf(manuelInputlng));
			setZoom("17");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
			e.printStackTrace();
		}
	}
	
	public void addMarkerToMap(double lat,double lng,Object data,String icon){
		Marker marker = new Marker(new LatLng(lat, lng),"", data,icon);
		marker.setTitle(getLabelForMarker(data));
		emptyModel.addOverlay(marker);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added","Lat:" + lat + ", Lng:" + lng));
		setCenter(String.valueOf(centerlat), String.valueOf(centerlng));
		setZoom(String.valueOf(zoom));
		
		informationOfPoint="";
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
	
	public String findValueOfSelectItem(List<SelectItem> values,String target){
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			if(selectItem.getValue().toString().equals(target))
				return selectItem.getLabel();
		}
		
		return "";
	}
	
	public void investigatePoint() throws Exception{
		
		try {
			List<NameValuePair> values = getPointOfInformation();		
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Inceleme tamamlandi","Lat:" + lat + ", Lng:" + lng));
			setCenter(String.valueOf(centerlat), String.valueOf(centerlng));
			setZoom(String.valueOf(zoom));
			showInfoWindow(values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Olustu","Hata Olustu"));
		}
	}
	
	public List<NameValuePair> getPointOfInformation() throws Exception{
		//System.out.println(String.valueOf(lat)+" "+String.valueOf(lng));
		projectionlng=projectionlng+deviationlng;
		projectionlat = projectionlat+deviationLat;
		List<NameValuePair> values = CityCurfUtil.getInformaitonOfCoordinates(String.valueOf(projectionlng), String.valueOf(projectionlat));
//		System.out.println(String.valueOf(projectionlng)+" "+String.valueOf(projectionlat));
//		System.out.println(informationOfPoint);
		values.add(new NameValuePair("Enlem", String.valueOf(lat)));
		values.add(new NameValuePair("Boylam", String.valueOf(lng)));
		return values;
	}
	
	public void cancelDialog() {
		informationOfPoint="";
	}
	
	public void sendCoordinateImmediately() throws Exception{
		
		sendImmediately = true;
		sendCoordinate();
	}
	
	
	public void sendCoordinate() throws Exception{
		if(orijinalAddress==null || orijinalAddress.equals("")){
			//FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata Olustu", "Not Alani bos olamaz"));
			//return;
			
			orijinalAddress= "Gorev Tarihi : "+ sdf.format(new Date());
		}
		
		Order order = new Order();
		order.setLat(String.valueOf(marker.getLatlng().getLat()));
		order.setLng(String.valueOf(marker.getLatlng().getLng()));
		order.setVehicle(vehicle);
		order.setUser(currentUser);
		order.setDate(new Date());
		order.setAddress(orijinalAddress);
		
		List<NameValuePair> list = (List<NameValuePair>)marker.getData();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			NameValuePair nameValuePair = (NameValuePair) iterator.next();
			if(nameValuePair.getName().equals("Ilce")){
				order.setIlce(nameValuePair.getValue());
				
			}if(nameValuePair.getName().equals("Mahalle")){
				order.setMahalle(nameValuePair.getValue());
			}if(nameValuePair.getName().equals("Sokak")){
				order.setSokak(nameValuePair.getValue());
			}
		}
		
		if(order.getIlce()==null || order.getIlce().equals("")){
			GeoUtil.fillObject(order, String.valueOf(lat),String.valueOf(lng));
		}
		
		order.getStatus().setId(Util.ORDER_STATUS_NEW);
		order.setPriority(priority);
		getServiceProvider().getPersistanceService().saveOrUpdate(order);
		
		Order tempOrder = (Order)getServiceProvider().getPersistanceService().getObject(Order.class, order.getId());
		Util.updateUser(tempOrder.getVehicle().getDevice().getRegId(),tempOrder, "newOrder",sendImmediately);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinat Gönderildi",""));
		sendImmediately = false;
		
		
	}
	
	public void drawDirections() {
		System.out.println(lat+","+lng);
		drawDirection(String.valueOf(lat), String.valueOf(lng));
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Yol Cizildi",""));
		setCenter(String.valueOf(centerlat), String.valueOf(centerlng));
		setZoom(String.valueOf(zoom));
	}
	
	public void setCenterZoom(){
		setCenter(String.valueOf(centerlat), String.valueOf(centerlng));
		setZoom(String.valueOf(zoom));
	}
	
	public void resetRota(){
		setCenterZoom();
		resetRotaFromHtml();
	}
	
	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
		lat = marker.getLatlng().getLat();
		lng = marker.getLatlng().getLng();
		System.out.println("dikkat "+ lat+","+lng);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected",marker.getTitle()));
		setOrijinalAddress("");
		RequestContext.getCurrentInstance().execute("PF('sendDialog').show()");	
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
	
	public void getKapiNo() throws Exception{
		
		try {
			//*******  mobil siteden gecis
//			String[] values = bina.split("&");
//			String binaNo=values[2];
//			
//			binaNo = binaNo.split("=")[1];
			
			String binaNo=bina;
			
			List<String> list= CityCurfUtil.getKapiNo(binaNo);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "koordinar tespit edildi",""));
			convertFromGPSData(list.get(0),list.get(1));
			updateDataSource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "koordinat tespit edilirken hata olustu",""));
		}
	}

	public double getCenterlng() {
		return centerlng;
	}

	public void setCenterlng(double centerlng) {
		this.centerlng = centerlng;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public double getCenterlat() {
		return centerlat;
	}

	public void setCenterlat(double centerlat) {
		this.centerlat = centerlat;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getInformationOfPoint() {
		return informationOfPoint;
	}

	public void setInformationOfPoint(String informationOfPoint) {
		this.informationOfPoint = informationOfPoint;
	}

	public double getProjectionlat() {
		return projectionlat;
	}

	public void setProjectionlat(double projectionlat) {
		this.projectionlat = projectionlat;
	}

	public double getProjectionlng() {
		return projectionlng;
	}

	public void setProjectionlng(double projectionlng) {
		this.projectionlng = projectionlng;
	}

	public double getDeviationLat() {
		return deviationLat;
	}

	public void setDeviationLat(double deviationLat) {
		this.deviationLat = deviationLat;
	}

	public double getDeviationlng() {
		return deviationlng;
	}

	public void setDeviationlng(double deviationlng) {
		this.deviationlng = deviationlng;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
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

	public List<SelectItem> getIlceList() {
		return ilceList;
	}

	public void setIlceList(List<SelectItem> ilceList) {
		this.ilceList = ilceList;
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = ilce;
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

	public List<NameValuePair> getMarkerAttiruteList() {
		return markerAttiruteList;
	}

	public void setMarkerAttiruteList(List<NameValuePair> markerAttiruteList) {
		this.markerAttiruteList = markerAttiruteList;
	}

	public String getImportantPlace() {
		return importantPlace;
	}

	public void setImportantPlace(String importantPlace) {
		this.importantPlace = importantPlace;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getOrijinalAddress() {
		return orijinalAddress;
	}

	public void setOrijinalAddress(String orijinalAddress) {
		this.orijinalAddress = orijinalAddress;
	}

	public OrderPriority getPriority() {
		return priority;
	}

	public void setPriority(OrderPriority priority) {
		this.priority = priority;
	}
}