package com.nagivator.web.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.stereotype.Component;

import com.nagivator.model.Order;
import com.nagivator.model.TrackItem;
import com.navigator.util.Util;


@Component
@ManagedBean(name="reportController")
@SessionScoped
public class ReportController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(ReportController.class);
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
	
	Order order = new Order();
	List<Order> orderList = new ArrayList<Order>();
	
	Date startDate;
	Date endDate;
	
	MapModel emptyModel;
	
	public ReportController() {
		super();
	}	
	public void search(){
		try{
			orderList = getServiceProvider().getPersistanceService().searchOrder(startDate, endDate);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	public void reset(){
		order = new Order();
	}
	
//	public void delete(){
//		try{
//			getServiceProvider().getPersistanceService().delete(order);
//			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
//			reset();	
//			search();
//		}catch(Exception e){
//			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
//			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
//		}
//	}
	
	public void cancel(){
		try{
			Long orderId = order.getId();
			order.getStatus().setId(Util.ORDER_STATUS_OPERATOR_CANCELLED);
			getServiceProvider().getPersistanceService().saveOrUpdate(order);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			reset();	
			search();
			Order tempOrder = (Order)getServiceProvider().getPersistanceService().getObject(Order.class, orderId);
			//Util.updateUser(tempOrder.getVehicle().getDevice().getRegId(),tempOrder, "cancelled",false);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	public void showDetails(){
		emptyModel = new DefaultMapModel();
		Set<TrackItem> trackItems = order.getTrackItems();
		for (Iterator iterator = trackItems.iterator(); iterator.hasNext();) {
			TrackItem trackItem = (TrackItem) iterator.next();
			Marker marker = new Marker(new LatLng(Double.valueOf(trackItem.getLat()), Double.valueOf(trackItem.getLng())),"");
			marker.setTitle(DATE_FORMAT.format(trackItem.getDate()));
			emptyModel.addOverlay(marker);
		}
		//hedef nokta
		Marker marker = new Marker(new LatLng(Double.valueOf(order.getLat()), Double.valueOf(order.getLng())),"","",blueDot);
		marker.setTitle("Hedef");
		emptyModel.addOverlay(marker);
	}
	
	public void sendAgain() {
		try{
		Long orderId = order.getId();
		Order tempOrder = (Order)getServiceProvider().getPersistanceService().getObject(Order.class, orderId);
		tempOrder.getStatus().setId(Util.ORDER_STATUS_NEW);;
		Util.updateUser(tempOrder.getVehicle().getDevice().getRegId(),tempOrder, "newOrder",true);
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	
	
}
