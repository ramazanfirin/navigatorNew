package com.navigator.service;



public class ServiceProvider {

	private PersistanceService persistanceService;

	public PersistanceService getPersistanceService() {
		return persistanceService;
	}

	public void setPersistanceService(PersistanceService persistanceService) {
		this.persistanceService = persistanceService;
	}

}
