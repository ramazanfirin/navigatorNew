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

import com.nagivator.model.Branch;
import com.nagivator.model.Device;
import com.nagivator.model.Vehicle;


@Component
@ManagedBean(name="vehicleController")
@SessionScoped
public class VehicleController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(VehicleController.class);
	
	Vehicle vehicle = new Vehicle();
	List<Vehicle> vehicleList = new ArrayList<Vehicle>();
	
	String plate;
	Map<String,Long> deviceOptions = new HashMap<String,Long>();
	
	Map<String,Long> branchOptions = new HashMap<String,Long>();
	
	public VehicleController() {
		super();
		reset();

	}
	
	public void reset(){
		 vehicle = new Vehicle();
		 vehicle.setDevice(new Device());
	}
	
	
	
	
	
	public void create(){
		try{
					
			getServiceProvider().getPersistanceService().saveOrUpdate(vehicle);
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
			getServiceProvider().getPersistanceService().deleteVehicle(vehicle);
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
			vehicleList = getServiceProvider().getPersistanceService().searchVehicle(plate);
			//System.out.println(vehicleList.get(0).getBranch().getName());
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	public Map<String,Long> prepareDeviceList(){
		Map<String,Long> map = new HashMap<String,Long>();
		try{
			
			
			List<Device> vehicleList = getServiceProvider().getPersistanceService().getDevices();
			for (Iterator iterator = vehicleList.iterator(); iterator.hasNext();) {
				Device device = (Device) iterator.next();
				map.put(device.getMsisdn(),device.getId());
				
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		return map;
	}
	
	public Map<String,Long> prepareBranchList(){
		Map<String,Long> map = new HashMap<String,Long>();
		try{
			
			
			List<Branch> vehicleList = getServiceProvider().getPersistanceService().getBranchList();
			for (Iterator iterator = vehicleList.iterator(); iterator.hasNext();) {
				Branch device = (Branch) iterator.next();
				map.put(device.getName(),device.getId());
				
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		return map;
	}







	public Vehicle getVehicle() {
		return vehicle;
	}







	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}







	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}







	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}







	public String getPlate() {
		return plate;
	}







	public void setPlate(String plate) {
		this.plate = plate;
	}







	public Map<String, Long> getDeviceOptions() {
//		if(deviceOptions.size()==0){
		deviceOptions = prepareDeviceList();
//		}
		return deviceOptions;
	}







	public void setDeviceOptions(Map<String, Long> deviceOptions) {
		this.deviceOptions = deviceOptions;
	}

	public Map<String, Long> getBranchOptions() {
		branchOptions = prepareBranchList();
		return branchOptions;
	}

	public void setBranchOptions(Map<String, Long> branchOptions) {
		this.branchOptions = branchOptions;
	}

	


	


}
