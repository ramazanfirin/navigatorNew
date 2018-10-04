package com.navigator.dao;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nagivator.model.BasicObject;
import com.nagivator.model.Branch;
import com.nagivator.model.Company;
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
			BasicObject o = (BasicObject)user;
			Company company = getCompany();
			if(company == null)
				company = o.getCompany();
			
			o.setCompany(company);
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
			String query = 	"from User as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";
			if(username!=null && !username.equals(""))
				query = query + "and c.username like '"+username+"%'";
				
			return getHibernateTemplate().find(query);
		}


		public List<Device> searchDevice(String msisdn) throws Exception {
			String query = 	"from Device as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";
			if(msisdn!=null && !msisdn.equals(""))
				query = query + "and c.msisdn like '"+msisdn+"%'";
				
			return getHibernateTemplate().find(query);
		}

		@Transactional
		public List<Vehicle> getVechiles() throws Exception {
			String query = 	"from Vehicle as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";				
			return getHibernateTemplate().find(query);
		}


		public Vehicle getVechile(Long id) throws Exception {
			return (Vehicle)getHibernateTemplate().get(Vehicle.class, id);

		}
		
		public Object getObject(Class clazz,Long id) throws Exception {
			return getHibernateTemplate().get(clazz, id);

		}


		public List<Device> getDevices() throws Exception {
			String query = 	"from Device as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";	
			return getHibernateTemplate().find(query);
		}


		public List<Vehicle> searchVehicle(String plate) throws Exception {
			String query = 	"from Vehicle as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";
			if(plate!=null && !plate.equals(""))
				query = query + "and c.plate like '"+plate+"%'";
				
			return getHibernateTemplate().find(query);
		}


		public List<Branch> searchBranch(String name) throws Exception {
			
			
			
			String query = 	"from Branch as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";
			if(name!=null && !name.equals(""))
				query = query + "and c.name like '"+name+"%'";
			return getHibernateTemplate().find(query);
		}


		public List<Branch> getBranchList() throws Exception {
			String query = 	"from Branch as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true";	
			return getHibernateTemplate().find(query);
		}


		public List<Order> searchOrder(final Date start, final Date End,final String status) throws Exception {
//			String query = 	"from Order as c where 1=1 and  c.company.id=:companyid and  c.enabled=true";
//			if(start!=null)
//				query = query + " and c.date > :startDate ";
//			if(End!=null)
//				query = query + " and c.date < :endDate ";
//			
//			if(!"all".equals(status))
//				query = query + " and c.status.id =:statusid ";
//			
//			query = query+" order by c.date desc";
//			Query updateQuery = getHibernateTemplate().getSessionFactory().getCurrentSession(). 
//					createQuery(query)
//	                   .setParameter("companyid", getCompanyId());
//	                   
//	                   
//	        if(start!=null)
//	        	updateQuery.setParameter("startDate", start);
//	        if(End!=null)
//	        	updateQuery.setParameter("endDate", End);			
//	        if(!"all".equals(status))
//	        	updateQuery.setParameter("statusid", new Long(status));		
			
			//return getHibernateTemplate().find(updateQuery);
	       
	        List customers = getHibernateTemplate().executeFind(new HibernateCallback<List>() {
	            @Override
	            public List doInHibernate(Session session) throws HibernateException, SQLException {
	            	String query = 	"from Order as c where 1=1 and  c.company.id=:companyid and  c.enabled=true";
	    			if(start!=null)
	    				query = query + " and c.date > :startDate ";
	    			if(End!=null)
	    				query = query + " and c.date < :endDate ";
	    			
	    			if(!"all".equals(status))
	    				query = query + " and c.status.id =:statusid ";
	    			
	    			query = query+" order by c.date desc";
	            	
	            	
	            	Query updateQuery = session.createQuery(query);
	            	updateQuery.setParameter("companyid", getCompanyId());
	            	
	            	if(start!=null)
	    	        	updateQuery.setParameter("startDate", start);
	    	        if(End!=null)
	    	        	updateQuery.setParameter("endDate", End);			
	    	        if(!"all".equals(status))
	    	        	updateQuery.setParameter("statusid", new Long(status));		
	    			
	                return updateQuery.list();
	            }
	            
	            
	    });
	        
	        
	        
			//return (List<Order>)updateQuery.list();
			return (List<Order>)customers;
		}


		public List<Order> getOpenOrders(String imei,boolean checkCompany) throws Exception {
			String query2 = 	"from Device v where v.imei='"+imei+"'";
			List list2 = getHibernateTemplate().find(query2);
			
			String query1 = 	"from Vehicle v where v.device.imei='"+imei+"'";
			List list = getHibernateTemplate().find(query1);
			
			
			
			String query = 	"from Order as c where 1=1 and c.branch.id in (select v.branch.id from Vehicle v where v.device.imei='"+imei+"')";
			if(checkCompany)
				query = query+" and c.company.id="+ getCompanyId();
			
			query = query+" and c.status.id in(1,2)";
			query = "from Order as c"; //sil
			return getHibernateTemplate().find(query);
		}


		public Device getDeviceByImei(String imei,boolean checkCompany) throws Exception {
			String query = 	"from Device as c where 1=1  ";
			if(checkCompany)
				query = query+" and c.company.id="+ getCompanyId();
			
			query = query+" and c.enabled=true and c.imei ='"+ imei+"'";
			
			List result = getHibernateTemplate().find(query);
			if(result.size()>0)
				return (Device)result.get(0);
			else
				return null;
		}


		public List<Poi> searchPoi(String name) throws Exception {
			String query = 	"from Poi as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true ";
			if(name!=null && !name.equals(""))
				query = query + "and c.name like '%"+name+"%'";
			return getHibernateTemplate().find(query);
		}


		public List<Poi> getPoiList() throws Exception {
			String query = 	"from Poi as c where 1=1 and c.company.id="+ getCompanyId()+" and c.enabled=true ";	
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

        public Company getCompany(){
        	ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpSession session = requestAttributes.getRequest().getSession();
			
			Company company = (Company)session.getAttribute("company");
			return company;
        }

        public Long getCompanyId(){
        	ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpSession session = requestAttributes.getRequest().getSession();
			
			Company company = (Company)session.getAttribute("company");
			return company.getId();
        }


		@Override
		public User getUserByMail(String mail) throws Exception {
			List result = getHibernateTemplate().find("from User as c where c.email ='"+ mail+"'");
			if(result.size()>0) 
				return (User)result.get(0);
			else
				return null;
		}



		public void saveOrUpdateForgetPassword(ForgetPasswordModel user) throws Exception {
			getHibernateTemplate().saveOrUpdate(user);
			
		}


		@Override
		public ForgetPasswordModel findForgetPasswordByKey(String key) {
			List result = getHibernateTemplate().find("from ForgetPasswordModel as c where c.key ='"+ key+"'");
			if(result.size()>0) 
				return (ForgetPasswordModel)result.get(0);
			else
				return null;
		}


		@Override
		public void updatePassword(String username, String password) {
			Query updateQuery = getSession().createQuery("update User u set u.password = :password where username = :username")
	                   .setParameter("username", username)
	                   .setParameter("password", password);
		
			int noOfUpdatedRows = updateQuery.executeUpdate();
		}


		@Override
		public Branch getBranch(Long id) throws Exception {
			return (Branch)getHibernateTemplate().get(Branch.class, id);
		}


		@Override
		public void updateDeviceByImei(String imei, String id) throws Exception {
			Query updateQuery = getSession().createQuery("update Device d set d.regId = :regId where d.imei = :imei")
	                   .setParameter("regId", id)
	                   .setParameter("imei", imei);
		
			int noOfUpdatedRows = updateQuery.executeUpdate();
			
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
