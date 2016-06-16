package com.nagivator.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import com.nagivator.model.Device;
import com.nagivator.model.Vehicle;


@Component
@ManagedBean(name="deviceController")
@SessionScoped
public class DeviceController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(DeviceController.class);
	
	Device device = new Device();
	List<Device> deviceList = new ArrayList<Device>();
	
	String msisdn;

	
	public DeviceController() {
		super();

	}
	
	public void reset(){
		device = new Device();
	}
	
	
	
	
	
	public void createDevice(){
		try{
					
			getServiceProvider().getPersistanceService().saveOrUpdate(device);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			device = new Device();	
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
	
	
	

	public void deleteDevice(){
		try{
			getServiceProvider().getPersistanceService().deleteDevice(device);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			device = new Device();	
			search();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	
	public void search(){
		try{
			deviceList = getServiceProvider().getPersistanceService().searchDevice(msisdn);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	


	

	public Device getDevice() {
		return device;
	}







	public void setDevice(Device device) {
		this.device = device;
	}







	public List<Device> getDeviceList() {
		return deviceList;
	}







	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}







	public String getMsisdn() {
		return msisdn;
	}







	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}


}
