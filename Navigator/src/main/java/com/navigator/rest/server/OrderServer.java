package com.navigator.rest.server;


import java.util.Date;

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

import com.nagivator.model.Order;
import com.nagivator.model.OrderStatus;
import com.nagivator.model.TrackItem;
import com.navigator.service.PersistanceService;
import com.navigator.service.ServiceProvider;
import com.navigator.util.Util;



// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/order")
public class OrderServer {

	@Autowired
	PersistanceService persistanceService;
	
	
	
  public OrderServer() {
		super();
		System.out.println("rest olustu");
	}
  
  @Context
  private ServletContext context=null; 

    @GET
	@Path("/{param}/{param1}/{param2}")
    @Produces(MediaType.APPLICATION_JSON)
	public void printMessage(@PathParam("param") String orderId,@PathParam("param1") String lat,@PathParam("param2") String lng) throws Exception{
    	
    	ServletContext  servletContext =(ServletContext) context;
    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
    	PersistanceService persistanceService= serviceProvider.getPersistanceService();
		  	
    	Order order =(Order)persistanceService.getObject(Order.class, new Long(orderId));
    	TrackItem trackItem = new TrackItem();
    	trackItem.setDate(new Date());
    	trackItem.setLat(lat);
    	trackItem.setLng(lng);
    	//trackItem.set
    	persistanceService.saveOrUpdate(trackItem);
    	order.getTrackItems().add(trackItem);
    	
    	persistanceService.saveOrUpdate(order);
    	
    	System.out.println(new Date()+" "+orderId+" "+lat+" "+lng);
    	return;
	}
    
    @GET
	@Path("/{param}/{param1}")
    @Produces(MediaType.APPLICATION_JSON)
	public void printMessage(@PathParam("param") String orderId,@PathParam("param1") String status) throws Exception{
    	
    	ServletContext  servletContext =(ServletContext) context;
    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceProvider serviceProvider= (ServiceProvider)context.getBean("serviceProvider");
    	PersistanceService persistanceService= serviceProvider.getPersistanceService();
		  	
    	//System.out.println("status geldi "+status);
    	OrderStatus orderStatus = new OrderStatus();
    	
    	if("completed".equals(status))
    		orderStatus.setId(Util.ORDER_STATUS_USER_COMPLETED);
    	if("started".equals(status))
    		orderStatus.setId(Util.ORDER_STATUS_START);
    	if("cancelled".equals(status))
    		orderStatus.setId(Util.ORDER_STATUS_USER_CANCELLED);
    	
    	Order order =(Order)persistanceService.getObject(Order.class, new Long(orderId));
    	order.setStatus(orderStatus);
    	
    	persistanceService.saveOrUpdate(order);
    	
    	return;
	}
    
   
} 