
 
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
 
@ManagedBean
@SessionScoped
public class AddMarkersView implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MapModel emptyModel;
      
    private String title;
      
    private double lat;
      
    private double lng;
    
    private String address  = "Cumhuriyet Mah. tasdelen istanbul";
  
    private Marker marker;
    
    public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String searchAddress(){
		try {
			final Geocoder geocoder = new Geocoder();
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			BigDecimal latValue = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat();
		    //lat =latValue.toString();
			BigDecimal lngValue = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng();
			//addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,"TEST", latValue.toString()+" "+lngValue.toString()));
			 //  RequestContext.getCurrentInstance().execute("setMapCenter("+latValue+","+lngValue+")");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
     
    public MapModel getEmptyModel() {
        return emptyModel;
    }
      
    public String getTitle() {
        return title;
    }
  
    public void setTitle(String title) {
        this.title = title;
    }
  
    public double getLat() {
        return lat;
    }
  
    public void setLat(double lat) {
        this.lat = lat;
    }
  
    public double getLng() {
        return lng;
    }
  
    public void setLng(double lng) {
        this.lng = lng;
    }
      
    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), title);
        emptyModel.addOverlay(marker);
       // emptyModel.
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }
    
    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        Marker marker = new Marker(new LatLng(latlng.getLat(), latlng.getLng()), address);
        emptyModel.addOverlay(marker);
        RequestContext.getCurrentInstance().execute("addMarkerFromServerSide("+latlng.getLat()+","+latlng.getLng()+")");
        
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
    }
      
    public AddMarkersView() {
		super();
		emptyModel = new DefaultMapModel();
        
        //Shared coordinates
        LatLng coord1 = new LatLng(36.879466, 30.667648);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
          
        //Basic marker
        emptyModel.addOverlay(new Marker(coord1, "Konyaalti"));
        emptyModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
        emptyModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
        emptyModel.addOverlay(new Marker(coord4, "Kaleici"));
	}

    @SuppressWarnings("restriction")
	@PostConstruct
    public void init()
    {
      this.emptyModel = new DefaultMapModel();
      

      LatLng coord1 = new LatLng(36.879466000000001D, 30.667648D);
      LatLng coord2 = new LatLng(36.883707000000001D, 30.689215999999998D);
      LatLng coord3 = new LatLng(36.879702999999999D, 30.706707000000002D);
      LatLng coord4 = new LatLng(36.885232999999999D, 30.702323D);
      

      this.emptyModel.addOverlay(new Marker(coord1, "Konyaalti"));
      this.emptyModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
      this.emptyModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
      this.emptyModel.addOverlay(new Marker(coord4, "Kaleici"));
    }
    
	public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        lat = marker.getLatlng().getLat(); 
        lng = marker.getLatlng().getLng();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }
}