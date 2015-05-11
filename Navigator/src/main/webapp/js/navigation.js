  var currentMarker = null;
    var markers = [];
    Proj4js.defs["EPSG:2322"] = "+proj=tmerc +lat_0=0 +lon_0=36 +k=1 +x_0=500000 +y_0=0 +ellps=intl +units=m +no_defs";
    var infowindow = new google.maps.InfoWindow({
        content: contentString,
        maxWidth: 500
    });
    var contentString ="test";
    var directionsDisplay =new google.maps.DirectionsRenderer();
    var directionsService = new google.maps.DirectionsService();
    
  
    
    function drawDirection(latitude, longitude){
    	directionsDisplay =new google.maps.DirectionsRenderer({suppressMarkers: true});
        directionsService = new google.maps.DirectionsService();
        //directionsDisplay.suppressMarkers = true;
        var gmap = PF('map').getMap();
    	directionsDisplay.setMap(gmap);

    	var source=	new google.maps.LatLng("38.78706897793826", "35.585227590054274")	;
    	var destination=	new google.maps.LatLng(latitude, longitude)	;
    	var request = {
    		      origin: source,
    		      destination: destination,
    		      // Note that Javascript allows us to access the constant
    		      // using square brackets and a string value as its
    		      // "property."
    		      travelMode: google.maps.TravelMode.DRIVING
    	};
    	
    	directionsService.route(request, function(response, status) {
    	    if (status == google.maps.DirectionsStatus.OK) {
    	     directionsDisplay.setDirections(response);
    	     //alert(response.routes[0].legs[0].distance.text+ " "+response.routes[0].legs[0].duration.text);
    	     var a = '<div>uzaklık :'+response.routes[0].legs[0].distance.text+ ',<br/> \r\n '+' Sure :'+response.routes[0].legs[0].duration.text+'<br/> Adress : '+response.routes[0].legs[0].end_address+'</div>';
    	    // alert('a');
    	     openInfoWindow(a);
    	    }
    	  });
    }
    
    function openInfoWindow(info){
    	var gmap = PF('map').getMap();
    	var lat = document.getElementById('form:lat').value;
    	var lng = document.getElementById('form:lng').value;
    	//var position = new google.maps.LatLng(document.getElementById('form:lat').value, document.getElementById('form:lng').value);

    	//for testing
    	alert(lat + " " + lng + " (types: " + (typeof lat) + ", " + (typeof lng) + ")");
    	var myLatlng = new google.maps.LatLng(parseFloat(document.getElementById('form:lat').value),parseFloat(document.getElementById('form:lng').value));    	
    	
    	infowindow.setContent(info);
    	infowindow.setPosition(myLatlng);
    	infowindow.open(gmap);
    }
 
    function getMapParameter(){
    	var gmap = PF('map').getMap();
        document.getElementById('form:centerlat').value = gmap.center.lat();
        document.getElementById('form:centerlng').value = gmap.center.lng();
        document.getElementById('form:zoom').value = gmap.zoom;
     //   document.getElementById('form:lat').value = event.latLng.lat();
     //   document.getElementById('form:lng').value = event.latLng.lng();
    }
    
    function resetRota(){
    	directionsDisplay.setMap(null);
    	
    	//directionsDisplay.set('directions', null);
    }
    
    function doProjection(){
    	var lng = document.getElementById('form:lng').value;
    	var lat = document.getElementById('form:lat').value;
    	var pnt = new OpenLayers.LonLat(lng,lat);
    	pnt=pnt.transform(new OpenLayers.Projection('EPSG:4326'), new OpenLayers.Projection('EPSG:2322'));
    	
  	
    	
    	document.getElementById('form:projectionlat').value = pnt.lat;
        document.getElementById('form:projectionlng').value = pnt.lon;
    	
    }
    
    function doProjectionFromGPS(x,y){
    	//x=x-0.0001;
    	//y=y-0.0001;
    	var pnt = new OpenLayers.LonLat(x,y);
    	pnt=pnt.transform(new OpenLayers.Projection('EPSG:2322'), new OpenLayers.Projection('EPSG:4326'));
    	
    	document.getElementById('form:manuelInputlat').value = pnt.lat-0.00012;
        document.getElementById('form:manuelInputlng').value = pnt.lon-0.00023;
    	
    }
    
    function hideDialog(){
    	PF('sendDialog').hide();
    }
    
    function handlePointClick(event) {
        if(currentMarker === null) {
            document.getElementById('form:lat').value = event.latLng.lat();
            document.getElementById('form:lng').value = event.latLng.lng();
            
            
            getMapParameter();
            
           currentMarker = new google.maps.Marker({
             position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
            });
 
            PF('map').addOverlay(currentMarker);
 
            PF('dlg').show();
            
           // currentMarker.setMap(null);
           // currentMarker = null;
        }   
    }
 
    function markerAddComplete() {
        //var title = document.getElementById('form:address');
       // currentMarker.setTitle(title.value);
        //title.value = "";
        // markers.push(currentMarker);
       // currentMarker = null;
        PF('dlg').hide();
    }
 
    function cancel() {
        PF('dlg').hide();
        currentMarker.setMap(null);
        currentMarker = null;
 
        return false;
    }
    

    
    function setMapCenter(latitude, longitude) {
    	PF('map').getMap().setCenter(new google.maps.LatLng(latitude, longitude));
    	
    }
    
    function setZoom(zoom) {
    	PF('map').getMap().setZoom(zoom);
    }
    
    function updateSourceData(value){
    	document.getElementById('form:dataSource').value=value;
    }
    
    //Version 2
    
    function setMapCenter2(latitude, longitude) {
    	PF('map').getMap().setCenter(new google.maps.LatLng(latitude, longitude));
    	
    }
    
    function setZoom2(zoom) {
    	PF('map').getMap().setZoom(zoom);
    }
    
    function handlePointClick2(event) {
      
            document.getElementById('dialogForm:lat').value = event.latLng.lat();
            document.getElementById('dialogForm:lng').value = event.latLng.lng();
            
            
           getMapParameter2();
//            
//           currentMarker = new google.maps.Marker({
//             position:new google.maps.LatLng(event.latLng.lat(), event.latLng.lng())
//            });
// 
//            PF('map').addOverlay(currentMarker);
 
            PF('dlg').show();
            
         
    }
    
    function getMapParameter2(){
    	var gmap = PF('map').getMap();
        document.getElementById('dialogForm:centerlat').value = gmap.center.lat();
        document.getElementById('dialogForm:centerlng').value = gmap.center.lng();
        document.getElementById('dialogForm:zoom').value = gmap.zoom;
     //   document.getElementById('form:lat').value = event.latLng.lat();
     //   document.getElementById('form:lng').value = event.latLng.lng();
    }
    
    function openInfoWindow2(info){
    	var gmap = PF('map').getMap();
    	var lat = document.getElementById('dialogForm:lat').value;
    	var lng = document.getElementById('dialogForm:lng').value;
    	//var position = new google.maps.LatLng(document.getElementById('form:lat').value, document.getElementById('form:lng').value);

    	//for testing
    	//alert(lat + " " + lng + " (types: " + (typeof lat) + ", " + (typeof lng) + ")");
    	
    	alert(info);
    	
//    	var myLatlng = new google.maps.LatLng(Number(lat),Number(lng));    	
//    	
//    	infowindow.setContent(info);
//    	infowindow.setPosition(myLatlng);
//    	infowindow.open(gmap);
    }
    
    function drawDirection2(){
    	var latitude = document.getElementById('dialogForm:lat').value;
    	var longitude = document.getElementById('dialogForm:lng').value;
    	directionsDisplay.setMap(null);
    	directionsDisplay =new google.maps.DirectionsRenderer({suppressMarkers: true});
        directionsService = new google.maps.DirectionsService();
        //directionsDisplay.suppressMarkers = true;
        var gmap = PF('map').getMap();
    	directionsDisplay.setMap(gmap);

    	var source=	new google.maps.LatLng("38.7371731635486", "35.5098129413818")	;
    	var destination=	new google.maps.LatLng(latitude, longitude)	;
    	var request = {
    		      origin: source,
    		      destination: destination,
    		      // Note that Javascript allows us to access the constant
    		      // using square brackets and a string value as its
    		      // "property."
    		      travelMode: google.maps.TravelMode.DRIVING
    	};
    	
    	directionsService.route(request, function(response, status) {
    	    if (status == google.maps.DirectionsStatus.OK) {
    	     directionsDisplay.setDirections(response);
    	     //alert(response.routes[0].legs[0].distance.text+ " "+response.routes[0].legs[0].duration.text);
    	     var a = '<div>uzaklık :'+response.routes[0].legs[0].distance.text+ ',<br/> \r\n '+' Sure :'+response.routes[0].legs[0].duration.text+'<br/> Adress : '+response.routes[0].legs[0].end_address+'</div>';
    	    // alert('a');
    	     //openInfoWindow2(a);
    	     document.getElementById('propertiesForm:distance').value = response.routes[0].legs[0].distance.text;
    	     document.getElementById('propertiesForm:duration').value = response.routes[0].legs[0].duration.text;
    	     document.getElementById('propertiesForm:remoteAddress').value = response.routes[0].legs[0].end_address;
    	     showRota();
    	    }
    	  });
    }
    function cancel2() {
        PF('dlg').hide();
//        currentMarker.setMap(null);
//        currentMarker = null;
// 
        return false;
    }