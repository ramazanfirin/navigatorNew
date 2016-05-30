package com.nagivator.web.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nagivator.model.Company;
import com.nagivator.model.User;
import com.navigator.service.ServiceProvider;
import com.navigator.util.Util;




public class BaseController {
	
	private static final Log LOG = LogFactory.getLog(BaseController.class);

    private Map<String,String> auth = new HashMap<String, String>();
    
    User currentUser= new User();
    
    boolean isAdmin;
    
    String blueDot = "http://maps.google.com/mapfiles/ms/micons/blue-dot.png";
    
    public String getVersion() throws Exception{
    //	return getServiceProvider().getVersion();
    	return "";
    }
    
	public BaseController() {
		super();
		
		try {
			
			auth.put(Util.ROLE_ADMIN, Util.ROLE_ADMIN);
			auth.put(Util.ROLE_USER, Util.ROLE_USER);
			//loadCurrentUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Boolean checkAdminAccount(){
		
		HttpSession session = (HttpSession)  FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		SecurityContext securityContext  =(SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		Collection<SimpleGrantedAuthority> c= (Collection<SimpleGrantedAuthority>)securityContext.getAuthentication().getAuthorities();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			SimpleGrantedAuthority simpleGrantedAuthority = (SimpleGrantedAuthority) iterator.next();
			if(simpleGrantedAuthority.getAuthority().equals(Util.ROLE_ADMIN))
				return true;
			
		}
		return false;	
		
	}
	
	public void loadCurrentUser() throws Exception{
//		if(SecurityContextHolder.getContext().getAuthentication()==null)
//			currentUser = null;
//		else
		HttpSession session = (HttpSession)  FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		currentUser  = getServiceProvider().getPersistanceService().getUser((String)session.getAttribute("username"));
	}
	
	public ServiceProvider getServiceProvider() throws Exception{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
		return serviceProvider;
	}
	
	public AuthenticationManager getAuthenticationManager() throws Exception{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		AuthenticationManager serviceProvider= (AuthenticationManager)context.getBean("authenticationManager");
		return serviceProvider;
	}
	
	
	
	

	public Map<String, String> getAuth() {
		return auth;
	}

	public void setAuth(Map<String, String> auth) {
		this.auth = auth;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getCurrentUser() throws Exception{
		loadCurrentUser();
		return currentUser;
	}

	public boolean getIsAdmin() {
		//if(isAdmin==false){
			isAdmin = checkAdminAccount();
		//}	
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getCompanyId(){
		return getCompany().getId();
	}
	
	public Company getCompany(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Company company = (Company) session.getAttribute("company");
		return company;
	}
	
	

}
