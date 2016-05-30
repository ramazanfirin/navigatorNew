package com.nagivator.web.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nagivator.model.ForgetPasswordModel;
import com.nagivator.model.User;


@Component
@ManagedBean(name="forgetController")
@ViewScoped
public class ForgetPasswordController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(ForgetPasswordController.class);
	
	String email;
		
	public ForgetPasswordController() throws Exception {
		super();
		//loadCurrentUser();

	}
	
	public void sendNewPassword() throws Exception{
		User user = getServiceProvider().getPersistanceService().getUserByMail(email);
		if(user==null){
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email Bulunamadi") ;
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return;
		}
		ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel();
		forgetPasswordModel.setEmail(email);
		forgetPasswordModel.setDate(new Date());
		forgetPasswordModel.setKey(generateKey());
		getServiceProvider().getPersistanceService().saveOrUpdate(forgetPasswordModel);
		
		sendMail();
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mail Adresinizi kontrol ediniz.") ;
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String sendMail(){
		
		return "";
	}
	public String generateKey(){
		return "";
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void updateUser() throws Exception{
		getServiceProvider().getPersistanceService().saveOrUpdate(currentUser);
		HttpSession session = (HttpSession)  FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("username",currentUser);
		FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(FacesMessage.SEVERITY_INFO,"Isleminiz tamamlamnd,",""));
	}
	
	
	
	
	
	
	
	

	
	
	
	
}
