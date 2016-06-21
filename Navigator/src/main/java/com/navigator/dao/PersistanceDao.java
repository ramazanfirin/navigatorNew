package com.navigator.dao;

import java.util.Date;
import java.util.List;

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


public interface PersistanceDao {
	
	public User getUser(String username) throws Exception;
	public User getUserByMail(String mail) throws Exception;
	public  void saveOrUpdate(Object user) throws Exception;
	public  void saveOrUpdateAll(List list) throws Exception;
//	public  void delete(Object user) throws Exception;
	
	public List<User> searchUser(String username) throws Exception;
	public List<Device> searchDevice(String msisdn) throws Exception;
	public List<Device> getDevices() throws Exception;
	
	
	public List<Vehicle> searchVehicle(String plate) throws Exception;
	public List<Vehicle> getVechiles() throws Exception;
	public Vehicle getVechile(Long id) throws Exception;
	
	public List<Branch> searchBranch(String name) throws Exception;
	public List<Branch> getBranchList() throws Exception;
	public Branch getBranch(Long id) throws Exception;
	
	public List<Order> searchOrder(Date start,Date End,String status) throws Exception;
	
	
	public List<Order> getOpenOrders(String imei,boolean checkCompany) throws Exception;
	
	public Device getDeviceByImei(String imei,boolean checkCompany) throws Exception;
	public void updateDeviceByImei(String imei,String id) throws Exception;
	
	public Object getObject(Class clazz,Long id) throws Exception;
	
	public List<Poi> searchPoi(String name) throws Exception;
	public List<Poi> getPoiList() throws Exception;
	
	public void deleteBranch(Branch branch) throws Exception;
	public void deleteDevice(Device object) throws Exception;
	public void deleteOrder(Order object) throws Exception;
	public void deleteTrackItem(TrackItem object) throws Exception;
	public void deleteUser(User object) throws Exception ;
	public void deleteVehicle(Vehicle object) throws Exception ;
	public void deletePoi(Poi object) throws Exception ;
	
	public List<Ilce> getIlceList(boolean all) throws Exception ;
	public List<Mahalle> getMahalleList(boolean all) throws Exception ;
	public List<Sokak> getSokakList(boolean all) throws Exception ;
	public List<Bina> getBinaList(boolean all) throws Exception ;
	
	public  void saveOrUpdateForgetPassword(ForgetPasswordModel user) throws Exception;
	
	public ForgetPasswordModel findForgetPasswordByKey(String key); 
	public void updatePassword(String username,String password); 
}
