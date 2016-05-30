package com.navigator.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nagivator.model.Branch;
import com.nagivator.model.Device;
import com.nagivator.model.ForgetPasswordModel;
import com.nagivator.model.Order;
import com.nagivator.model.Poi;
import com.nagivator.model.TrackItem;
import com.nagivator.model.User;
import com.nagivator.model.Vehicle;
import com.nagivator.model.cbs.Bina;
import com.nagivator.model.cbs.Ilce;
import com.nagivator.model.cbs.Mahalle;
import com.nagivator.model.cbs.Sokak;
import com.navigator.dao.PersistanceDao;



public class PersistanceServiceImpl implements PersistanceService {


	PersistanceDao persistanceDao;

	public User getUser(String username) throws Exception {
		// TODO Auto-generated method stub
		return persistanceDao.getUser(username);
	}

	public void saveOrUpdate(Object user) throws Exception {
		persistanceDao.saveOrUpdate(user);
		
	}
	
//	public void delete(Object user) throws Exception {
//		persistanceDao.delete(user);
//		
//	}
	
	public List<User> searchUser(String username) throws Exception {
		return persistanceDao.searchUser(username);
	}

	
	public PersistanceDao getPersistanceDao() {
		return persistanceDao;
	}

	public void setPersistanceDao(PersistanceDao persistanceDao) {
		this.persistanceDao = persistanceDao;
	}

	public List<Device> searchDevice(String msisdn) throws Exception {
		return persistanceDao.searchDevice(msisdn);
	}

	public List<Vehicle> getVechiles() throws Exception {
		return persistanceDao.getVechiles();
	}

	public Vehicle getVechile(Long id) throws Exception {
		return persistanceDao.getVechile(id);
	}

	public List<Device> getDevices() throws Exception {
		return persistanceDao.getDevices();
	}

	@Transactional
	public List<Vehicle> searchVehicle(String plate) throws Exception {
		return persistanceDao.searchVehicle(plate);
	}

	public List<Branch> searchBranch(String name) throws Exception {
		return persistanceDao.searchBranch(name);
	}

	public List<Branch> getBranchList() throws Exception {
		return persistanceDao.getBranchList();
	}

	public List<Order> searchOrder(Date start, Date End) throws Exception {
		return persistanceDao.searchOrder(start, End);
	}

	public List<Order> getOpenOrders(String imei) throws Exception {
		return persistanceDao.getOpenOrders(imei);
	}

	public Device getDeviceByImei(String imei) throws Exception {
		return persistanceDao.getDeviceByImei(imei);
	}

	public Object getObject(Class clazz, Long id) throws Exception {
		return persistanceDao.getObject(clazz, id);
	}

	public List<Poi> searchPoi(String name) throws Exception {
		return persistanceDao.searchPoi(name);
	}

	public List<Poi> getPoiList() throws Exception {
		return persistanceDao.getPoiList();
	}

	public void deleteBranch(Branch branch) throws Exception {
		persistanceDao.deleteBranch(branch);
		
	}

	public void deleteDevice(Device object) throws Exception {
		persistanceDao.deleteDevice(object);
		
	}

	public void deleteOrder(Order object) throws Exception {
		persistanceDao.deleteOrder(object);
		
	}

	public void deleteTrackItem(TrackItem object) throws Exception {
		persistanceDao.deleteTrackItem(object);
		
	}

	public void deleteUser(User object) throws Exception {
		persistanceDao.deleteUser(object);
		
	}

	public void deleteVehicle(Vehicle object) throws Exception {
		persistanceDao.deleteVehicle(object);
		
	}

	public void deletePoi(Poi object) throws Exception {
		persistanceDao.deletePoi(object);
		
	}

	public List<Ilce> getIlceList(boolean all) throws Exception {
		// TODO Auto-generated method stub
		return persistanceDao.getIlceList(all);
	}

	public List<Mahalle> getMahalleList(boolean all) throws Exception {
		// TODO Auto-generated method stub
		return persistanceDao.getMahalleList(all);
	}

	public List<Sokak> getSokakList(boolean all) throws Exception {
		// TODO Auto-generated method stub
		return persistanceDao.getSokakList(all);
	}

	public List<Bina> getBinaList(boolean all) throws Exception {
		// TODO Auto-generated method stub
		return persistanceDao.getBinaList(all);
	}

	public void saveOrUpdateAll(List list) throws Exception {
		// TODO Auto-generated method stub
		persistanceDao.saveOrUpdateAll(list);
	}

	@Override
	public User getUserByMail(String mail) throws Exception {
		return persistanceDao.getUserByMail(mail);
	}

	@Override
	public void saveOrUpdate(ForgetPasswordModel user) throws Exception {
		persistanceDao.saveOrUpdate(user);
		
	}


	

	
	
	
}
