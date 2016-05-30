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
@ManagedBean(name="branchController")
@SessionScoped
public class BranchController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(BranchController.class);
	
	Branch branch = new Branch();
	List<Branch> branchList = new ArrayList<Branch>();
	
	String name;
		
	public BranchController() {
		super();
		reset();

	}
	
	public void reset(){
		 branch = new Branch();
		
	}
	
	
	
	
	
	public void create(){
		try{
			//branch.setCompany(getCompany());		
			getServiceProvider().getPersistanceService().saveOrUpdate(branch);
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
			getServiceProvider().getPersistanceService().deleteBranch(branch);
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
			branchList = getServiceProvider().getPersistanceService().searchBranch(name);
			//branchList.get(0).getVehicleList().isEmpty();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
