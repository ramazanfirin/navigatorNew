package com.nagivator.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import com.nagivator.model.User;


@Component
@ManagedBean(name="userController")
@SessionScoped
public class UserController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(UserController.class);
	
	User user = new User();
	User newUser = new User();
	
	List<User> userList = new ArrayList<User>();
	String userName ;

	String oldPassword;
	String newPassword;
	
	String name;
	String surname;
	String email;
	String phone;
	
	User currentUserClone= new User();
	
	public String getLoggedUserNameSurname() throws Exception{
		return getCurrentUser().getName()+" "+getCurrentUser().getSurname();
	}
	
	public String getContextPath(){
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
	     
		FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getRequestServletPath();;
		FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getRequestServerName();
		
		FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getRequestServletPath();
		//FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getRequest
	    String serverRealPath = servletContext.getRealPath("/");
	    String serverContextPath = servletContext.getContextPath();
	    
	    return serverContextPath;
	}
	public UserController() {
		super();

	}
	
	public void setCurrentUserClone() throws Exception{
		currentUserClone = getCurrentUser();
	}
	
	public void resetUserObject()throws Exception{
		newUser = new User();
	}
	
	
	
	public void updateMyInformation(){
		try{	
			User currentUser =getCurrentUser();
			getServiceProvider().getPersistanceService().saveOrUpdate(currentUser);
			loadCurrentUser();
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		
	}
	
	
	
	
	public void createUser(){
		try{
					
			getServiceProvider().getPersistanceService().saveOrUpdate(newUser);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			resetUserObject();	
			
			// send mail for password
			RequestContext.getCurrentInstance().execute("PF('createUser').hide()");
//			RequestContext.getCurrentInstance().update("dataGrid");
//			RequestContext.getCurrentInstance().update(":form1:dataGrid");
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
		
	}
	
	
	public void updatePassword(){
		try{
			User currentUser =getCurrentUser();
			if(!currentUser.getPassword().equals(oldPassword)){
				FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"yanlış şifre.",""));
				return;
			}	
			currentUser.setPassword(newPassword);
			getServiceProvider().getPersistanceService().saveOrUpdate(currentUser);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Islem Basarili.",""));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}

	public void deleteUser(){
		try{
			getServiceProvider().getPersistanceService().deleteUser(newUser);
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
			resetUserObject();	
			search();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	public void search(){
		try{
			userList = getServiceProvider().getPersistanceService().searchUser(userName);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hata oluştu",""));
			LOGGER.error("Hata Olutu:"+ e.getMessage()  , e);
		}
	}
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getCurrentUserClone() {
		return currentUserClone;
	}

	public void setCurrentUserClone(User currentUserClone) {
		this.currentUserClone = currentUserClone;
	}
	

	
		


}
