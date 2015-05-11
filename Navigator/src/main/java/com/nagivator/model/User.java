package com.nagivator.model;



public class User{

	private Long id;
	
	private String username="";
	private String password="";
	private Authority authority = new Authority();
	private Boolean enabled=true;
	
	private String passwordTemp;
	
	private String name;
	private String surname;
	private String email;
	private String phone;
	
	Boolean sendAlertMail=true;
	Boolean sendAlertSms=true;
	
	public void reset(){
		username = "";
		password = "";
		id = null;
		enabled = true;
		authority = new Authority();
		name = "";
		surname = "";
		email ="";
		phone="";	
		
		
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
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getPasswordTemp() {
		return passwordTemp;
	}
	public void setPasswordTemp(String passwordTemp) {
		this.passwordTemp = passwordTemp;
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

	public Boolean getSendAlertMail() {
		return sendAlertMail;
	}

	public void setSendAlertMail(Boolean sendAlertMail) {
		this.sendAlertMail = sendAlertMail;
	}

	public Boolean getSendAlertSms() {
		return sendAlertSms;
	}

	public void setSendAlertSms(Boolean sendAlertSms) {
		this.sendAlertSms = sendAlertSms;
	}
	
}
