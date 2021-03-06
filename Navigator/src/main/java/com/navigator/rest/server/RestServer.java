package com.navigator.rest.server;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nagivator.model.Device;
import com.nagivator.model.KeyValueDTO;
import com.nagivator.model.Order;
import com.navigator.service.PersistanceService;
import com.navigator.service.ServiceProvider;
import com.navigator.util.CityCurfTest;



// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class RestServer {

	@Autowired
	PersistanceService persistanceService;
	
	
	
  public RestServer() {
		super();
		System.out.println("rest olustu");
	}
  
  @Context
  private ServletContext context=null; 

// This method is called if TEXT_PLAIN is request
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sayPlainTextHello() {
    return "Hello Jersey";
  }

  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  public String sayXMLHello() {
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }

  
    @GET
	@Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrders(@PathParam("param") String imei) throws Exception{
    	ServletContext  servletContext =(ServletContext) context;
    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
    	PersistanceService a= serviceProvider.getPersistanceService();
		//a.getOpenOrders(imei,false);
    	System.out.println("imei has come ="+imei);
    	
    
    	List<Order> list = new ArrayList<Order>();
    	list.add(new Order());
    	
    	list=a.getOpenOrders(imei,false);
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			order.setVehicle(null);
			order.setCompany(null);
			order.setUser(null);
			order.setBranch(null);
		}
    	return list;
	}
    
    @GET
   	@Path("/RegisterId/{param}/{param2}")
    @Produces(MediaType.APPLICATION_JSON)
   	public String regsiterGCMId(@PathParam("param") String imei,@PathParam("param2") String regID) throws Exception{
       	ServletContext  servletContext =(ServletContext) context;
       	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
   		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
   		PersistanceService service= serviceProvider.getPersistanceService();  
   		
       Device device = service.getDeviceByImei(imei,false);
       if(device==null)
    	   return "NOK";
       	
//       device.setRegId(regID);
//       service.saveOrUpdate(device);
       service.updateDeviceByImei(imei,regID);
       return "OK";
   	}
    
    @GET
   	@Path("/checkRegister/{param}")
    @Produces(MediaType.APPLICATION_JSON)
   	public String regsiterGCMId(@PathParam("param") String imei) throws Exception{
       	ServletContext  servletContext =(ServletContext) context;
       	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
   		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
   		PersistanceService service= serviceProvider.getPersistanceService();  
   		
       Device device = service.getDeviceByImei(imei,false);
       if(device==null)
    	   return "NOK";
       	
       if(device.getRegId()==null || device.getRegId().equals(""))
    	   return "NOK";
       
       return "OK";
   	}
   //cbs data
    
    
    @GET
	@Path("/getIlceList")
    @Produces(MediaType.APPLICATION_JSON)
	public List<KeyValueDTO> getIlceList() throws Exception{
    	List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
    	
    	List<SelectItem> ilceList = CityCurfTest.getIlceList();
    	for (Iterator iterator = ilceList.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			result.add(new KeyValueDTO(selectItem.getLabel(),selectItem.getValue().toString()));
		}
	
    	return result;
    }
    
    @GET
	@Path("/getMahalleList/{param1}/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<KeyValueDTO> getMahalleist(@PathParam("param1") String ilceName) throws Exception{
    	List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
    	
    	List<SelectItem> list = CityCurfTest.getMahalleList(ilceName);
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			result.add(new KeyValueDTO(selectItem.getLabel(),selectItem.getValue().toString()));
		}
	
    	return result;
    }
   
    @GET
	@Path("/getSokakList/{param1}/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<KeyValueDTO> getLSokakList(@PathParam("param1") String name) throws Exception{
    	List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
    	
    	List<SelectItem> list = CityCurfTest.getSokakList(name);
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			result.add(new KeyValueDTO(selectItem.getLabel(),selectItem.getValue().toString()));
		}
	
    	return result;
    }
    
    @GET
	@Path("/getBinaList/{param1}/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<KeyValueDTO> getBinaList(@PathParam("param1") String name) throws Exception{
    	List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
    	
    	List<SelectItem> list = CityCurfTest.getBinaList(name);
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			SelectItem selectItem = (SelectItem) iterator.next();
			result.add(new KeyValueDTO(selectItem.getLabel(),selectItem.getValue().toString()));
		}
	
    	return result;
    }
    
    @GET
	@Path("/getCoordinate/{param1}/")
    @Produces(MediaType.APPLICATION_JSON)
	public List<KeyValueDTO> getCoordinate(@PathParam("param1") String binaNo) throws Exception{
    	List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
    	
    	List<String> list = CityCurfTest.getKapiNo(binaNo);
    	result.add(new KeyValueDTO("lat",list.get(0)));
    	result.add(new KeyValueDTO("lng",list.get(1)));
    	
	
    	return result;
    }
} 