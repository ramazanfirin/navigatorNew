package com.navigator.rest.server;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.nagivator.model.Order;
import com.navigator.service.PersistanceService;
import com.navigator.service.ServiceProvider;



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
		a.getOpenOrders(imei);
    	System.out.println("imei has come ="+imei);
    	
    
    	List<Order> list = new ArrayList<Order>();
    	list.add(new Order());
    	
    	list=a.getOpenOrders(imei);
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			order.setVehicle(null);
			order.setUser(null);
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
   		
       Device device = service.getDeviceByImei(imei);
       if(device==null)
    	   return "NOK";
       	
       device.setRegId(regID);
       service.saveOrUpdate(device);
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
   		
       Device device = service.getDeviceByImei(imei);
       if(device==null)
    	   return "NOK";
       	
       if(device.getRegId()==null || device.getRegId().equals(""))
    	   return "NOK";
       
       return "OK";
   	}
   
} 