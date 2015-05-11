package com.navigator.dao;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.nagivator.model.Branch;
import com.nagivator.model.Device;
import com.nagivator.model.Order;
import com.nagivator.model.Poi;
import com.nagivator.model.TrackItem;
import com.nagivator.model.User;
import com.nagivator.model.Vehicle;
import com.nagivator.model.cbs.Bina;
import com.nagivator.model.cbs.Ilce;
import com.nagivator.model.cbs.Mahalle;
import com.nagivator.model.cbs.Sokak;



public class PersistanceDaoImpl extends HibernateDaoSupport implements PersistanceDao {

	    public static final String collectionName = "upsDataReal7";
	    
	    @Autowired
	    public  HibernateTemplate hibernateTemplate;

	    private SessionFactory sessionFactory;
    	String isDemo;


		public User getUser(String username) throws Exception {
			List result = getHibernateTemplate().find("from User as c where c.username ='"+ username+"'");
			if(result.size()>0)
				return (User)result.get(0);
			else
				return null;
		}


		public void saveOrUpdate(Object user) throws Exception {
			hibernateTemplate.saveOrUpdate(user);
			
		}


//		public void delete(Object user) throws Exception {
//			hibernateTemplate.udelete(user);
//			
//		}

		public void deleteBranch(Branch branch) throws Exception {
			branch.setEnabled(false);
			saveOrUpdate(branch);
		}
		
		public void deleteDevice(Device object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}
		
		public void deleteOrder(Order object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}
		
		public void deleteTrackItem(TrackItem object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}
		
		public void deleteUser(User object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}

		public void deleteVehicle(Vehicle object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}
		
		public void deletePoi(Poi object) throws Exception {
			object.setEnabled(false);
			saveOrUpdate(object);
		}
		
		public List<User> searchUser(String username) throws Exception {
			String query = 	"from User as c where 1=1 and c.enabled=true";
			if(username!=null && !username.equals(""))
				query = query + "and c.username like '"+username+"%'";
				
			return getHibernateTemplate().find(query);
		}


		public List<Device> searchDevice(String msisdn) throws Exception {
			String query = 	"from Device as c where 1=1 and c.enabled=true";
			if(msisdn!=null && !msisdn.equals(""))
				query = query + "and c.msisdn like '"+msisdn+"%'";
				
			return getHibernateTemplate().find(query);
		}

		@Transactional
		public List<Vehicle> getVechiles() throws Exception {
			String query = 	"from Vehicle as c where 1=1 and c.enabled=true";				
			return getHibernateTemplate().find(query);
		}


		public Vehicle getVechile(Long id) throws Exception {
			return (Vehicle)getHibernateTemplate().get(Vehicle.class, id);

		}
		
		public Object getObject(Class clazz,Long id) throws Exception {
			return getHibernateTemplate().get(clazz, id);

		}


		public List<Device> getDevices() throws Exception {
			String query = 	"from Device as c where 1=1 and c.enabled=true";	
			return getHibernateTemplate().find(query);
		}


		public List<Vehicle> searchVehicle(String plate) throws Exception {
			String query = 	"from Vehicle as c where 1=1 and c.enabled=true";
			if(plate!=null && !plate.equals(""))
				query = query + "and c.plate like '"+plate+"%'";
				
			return getHibernateTemplate().find(query);
		}


		public List<Branch> searchBranch(String name) throws Exception {
			String query = 	"from Branch as c where 1=1 and c.enabled=true";
			if(name!=null && !name.equals(""))
				query = query + "and c.name like '"+name+"%'";
			return getHibernateTemplate().find(query);
		}


		public List<Branch> getBranchList() throws Exception {
			String query = 	"from Branch as c where 1=1 and c.enabled=true";	
			return getHibernateTemplate().find(query);
		}


		public List<Order> searchOrder(Date start, Date End) throws Exception {
			String query = 	"from Order as c where 1=1 and c.enabled=true";
			if(start!=null)
				query = query + "and c.date > "+start.getTime();
			if(End!=null)
				query = query + "and c.date < "+End.getTime();
			return getHibernateTemplate().find(query);
		}


		public List<Order> getOpenOrders(String imei) throws Exception {
			//String query = 	"from Order as c where 1=1 and c.vehicle.device.imei='"+imei+"' and c.status.id in(1,2,4)";
			String query = 	"from Order as c where 1=1  and c.status.id in(1,2,4)";
			return getHibernateTemplate().find(query);
		}


		public Device getDeviceByImei(String imei) throws Exception {
			List result = getHibernateTemplate().find("from Device as c where  c.enabled=true and c.imei ='"+ imei+"'");
			if(result.size()>0)
				return (Device)result.get(0);
			else
				return null;
		}


		public List<Poi> searchPoi(String name) throws Exception {
			String query = 	"from Poi as c where 1=1 and c.enabled=true ";
			if(name!=null && !name.equals(""))
				query = query + "and c.name like '%"+name+"%'";
			return getHibernateTemplate().find(query);
		}


		public List<Poi> getPoiList() throws Exception {
			String query = 	"from Poi as c where 1=1 and c.enabled=true ";	
			return getHibernateTemplate().find(query);
		}


		public List<Ilce> getIlceList(boolean all) throws Exception {
			// TODO Auto-generated method stub
			String query = 	"from Ilce as c where 1=1";	
			if(!all)
				query = query+" and c.complated = false";
			return getHibernateTemplate().find(query);

		}


		public List<Mahalle> getMahalleList(boolean all) throws Exception {
			String query = 	"from Mahalle as c where 1=1";	
			if(!all)
				query = query+" and c.complated = false";
			return getHibernateTemplate().find(query);
		}


		public List<Sokak> getSokakList(boolean all) throws Exception {
			String query = 	"from Sokak as c where 1=1";
			if(!all)
				query = query+" and c.complated = false";
			return getHibernateTemplate().find(query);
		}


		public List<Bina> getBinaList(boolean all) throws Exception {
			String query = 	"from Bina as c where 1=1";	
			
			if(!all)
				query = query+" and c.complated = false";
		   Query baseQuery = getSession().createQuery(query);
		   baseQuery.setFirstResult(0); // modify this to adjust paging
		   baseQuery.setMaxResults(115000);
		   
		   
		   
		    return (List<Bina>) baseQuery.list();		
					
		}


		public void saveOrUpdateAll(List list) throws Exception {
			// TODO Auto-generated method stub
			getHibernateTemplate().saveOrUpdateAll(list);
			
		}


		


		//		public HibernateTemplate getHibernateTemplate() {
//			return hibernateTemplate;
//		}
//
//
//		public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
//			this.hibernateTemplate = hibernateTemplate;
//		}
	

	
}