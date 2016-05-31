package com.nagivator.web.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.nagivator.model.ForgetPasswordModel;
import com.nagivator.model.User;



@ManagedBean(name="resetPasswordController")
@ViewScoped
public class ResetPasswordController extends BaseController{

	private static Logger LOGGER =Logger.getLogger(ResetPasswordController.class);
	
	String newPassword;
	String newPasswordAgain;
	String key;	
	Boolean renderPasswordInputs=true;
	ForgetPasswordModel model;
	String message;
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public void processActionToken() {
        System.out.println("test");
    }

@PostConstruct
public void init() throws Exception{
//	if(FacesContext.getCurrentInstance()==null)
//		return;
	key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
	if(key==null || key.equals("")){
		message = "Gecersiz Istek";
        renderPasswordInputs = false;
	}
	
	 model = getServiceProvider().getPersistanceService().findForgetPasswordByKey(key);
	if(model==null || model.getEnabled()==false){
		message = "Gecersiz Istek";
        renderPasswordInputs = false;
	}
}
	public ResetPasswordController() throws Exception {
		super();
		//loadCurrentUser();ç
		init();
			
	}
	
	
	public void resetPassword() throws Exception{
		String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
		if(!newPassword.equals(newPasswordAgain)){
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Sifreler ayni değil") ;
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return;
		}
		
		User user = getServiceProvider().getPersistanceService().getUserByMail(model.getEmail());
		//user.setPassword(newPassword);
		getServiceProvider().getPersistanceService().updatePassword(user.getUsername(), newPassword);
		model.setEnabled(false);
		getServiceProvider().getPersistanceService().saveOrUpdateForgetPassword(model);
		
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Islem tamamlandi") ;
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return;
	}


	public static Logger getLOGGER() {
		return LOGGER;
	}


	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}


	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Boolean getRenderPasswordInputs() {
		return renderPasswordInputs;
	}


	public void setRenderPasswordInputs(Boolean renderPasswordInputs) {
		this.renderPasswordInputs = renderPasswordInputs;
	}


	public ForgetPasswordModel getModel() {
		return model;
	}


	public void setModel(ForgetPasswordModel model) {
		this.model = model;
	}
	
	
	
	
	
	
	
	

	
	
	
	
}
