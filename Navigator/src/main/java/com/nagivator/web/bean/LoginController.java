package com.nagivator.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.nagivator.model.User;
import com.navigator.service.PersistanceService;
import com.navigator.util.Util;


@Component
@ManagedBean(name="loginController")
@SessionScoped
public class LoginController extends BaseController{
	
	private static final Log LOG = LogFactory.getLog(LoginController.class);
	private static Logger LOGGER =Logger.getLogger("LoginController");

        // beans used by this controller, injected by spring
	//private LoginBean loginBean;
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	@Qualifier("persistanceService")
//	private PersistanceService persistanceService;
	
	String username ;
	String password;
    String email;
	
	
	public String login() {
		try{
			
			
		
		PersistanceService service  =getServiceProvider().getPersistanceService();
		service.getUser("tewst");
			
		authenticationManager = getAuthenticationManager();	
			
		 // check if userdata is given 
		 if (username == null || password == null) {
	            FacesMessage facesMsg = new FacesMessage(
	            FacesMessage.SEVERITY_ERROR, "Error", "login.failed" );
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            LOGGER.info("Login not started because userName or Password is empty: "+username);
	            return null;
	        }
	       
		 // authenticate afainst spring security
		 Authentication request = new UsernamePasswordAuthenticationToken(username, password);            
	            
	        Authentication result = authenticationManager.authenticate(request);
	        SecurityContext securityContext =SecurityContextHolder.getContext();
	        securityContext.setAuthentication(result);
	        
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			session.setAttribute("username", username);;
			
			
			
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			//session.setAttribute("yetki", securityContext.getAuthentication().getAuthorities());securityContext.getAuthentication().getAuthorities().contains(Util.ROLE_ADMIN);
			LOGGER.info("Login sucessfull:"+username);
	 
	    } catch (Exception e) {
	    	//LOGGER.info("Login failed: " + e.getMessage());
	    	LOGGER.error("Login failed:"+ e.getMessage()  , e);
	        FacesMessage facesMsg = new FacesMessage(
	            FacesMessage.SEVERITY_ERROR, "Error", "login.failed") ;
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            
	        return null;
	    }
	    return "successLogin";
	    //return "pages/Navigation";
		
	}
		
	public String logout() throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		if(session==null){
			return "invalid";
		}
		else{
			session.invalidate();
			return "logoutsuccess";
		}
		
		
	}	
		
	public void forgotPassword() throws Exception{
		User user=getServiceProvider().getPersistanceService().getUser(email);
		if(user == null){
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mail adresi bulunamadı") ;
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else{
			
			String subject = "Şifre Hatırlatma";
			String messageContent = "Merhaba "+user.getName();
			messageContent += "<br/>Şifreniz :"+user.getPassword();
			
			List<String> toList = new ArrayList<String>();
			toList.add(user.getEmail());
			
			//getServiceProvider().getAlertService().sendMail(toList, subject, messageContent);
			
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "İnfo", "Mail adresinize şifreniz gönderilmiştir.") ;
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
	}
	
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public static Log getLog() {
			return LOG;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	
	
		
}	