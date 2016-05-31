package com.nagivator.web.bean;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nagivator.model.ForgetPasswordModel;
import com.nagivator.model.User;
import com.navigator.util.EmailUtil;


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
		try {
			ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel();
			forgetPasswordModel.setEmail(email);
			forgetPasswordModel.setDate(new Date());
			String key = generateKey();
			forgetPasswordModel.setKey(key);
			getServiceProvider().getPersistanceService().saveOrUpdateForgetPassword(forgetPasswordModel);
			
			sendMail(forgetPasswordModel.getEmail(),key);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Mail Adresinize bilgiler gonderilmistır.") ;
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",e.getMessage()) ;
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			e.printStackTrace();
		}
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	String getAbsoluteApplicationUrl() throws URISyntaxException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        URI uri = new URI(request.getRequestURL().toString());
        URI newUri = new URI(uri.getScheme(), null,
                uri.getHost(),
                uri.getPort(),
                request.getContextPath().toString(),null, null);
        return newUri.toString();
 }
	
	public String sendMail(String to,String key) throws Exception{
		String uri = getAbsoluteApplicationUrl();
		EmailUtil.sendTextMessage(to, uri+"/resetPassword.xhtml?key="+key,"Şifre Hatırlatma","şifrenizi belirlemek için tıklayınız.");
		return "";
	}
	public String generateKey(){
		Random randomGenerator = new Random();
		int a=randomGenerator.nextInt(100000);
		return String.valueOf(a);
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
