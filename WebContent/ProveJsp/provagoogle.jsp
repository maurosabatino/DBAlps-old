<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <%@page import="java.awt.event.*"%>
<head>
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map-canvas { height: 50%; weight:50% }
    </style>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBXR0WMPOi00Eh1-K7xAlVYumQkpRqbFSU&sensor=false">
    </script>
    <script type="text/javascript">
    var geocoder;
    var map;
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(45.019990, 7.639469),
          zoom: 15
        };
        geocoder = new google.maps.Geocoder();
        map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        
        var marker = new google.maps.Marker({ position: new google.maps.LatLng(45.019990, 7.639469),
            map: map, 
            title: 'Questo è un testo di suggerimento' });
        google.maps.event.addListener(map, "rightclick", function(event) {
    	    var lat = event.latLng.lat();
    	    var lng = event.latLng.lng();
    	    document.getElementById("latitudine").value = lat;
    	    document.getElementById("longitudine").value = lng;
    	    // populate yor box/field with lat, lng
    	    
    	});
			
      }
      function codeAddress() {
    	    var address = document.getElementById("address").value;
    	    geocoder.geocode( { 'address': address ,"componentRestrictions":{"country":"IT"}}, function(results, status) {
    	      if (status == google.maps.GeocoderStatus.OK) {
    	        map.setCenter(results[0].geometry.location);
    	        var marker = new google.maps.Marker({
    	            map: map,
    	            position: results[0].geometry.location
    	        });
    	      } else {
    	        alert("Geocode was not successful for the following reason: " + status);
    	      }
    	    });
    	  }
      
      

      
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    
   
<title>google</title>

</head>
<body onload="initialize()">

<input type="text" id = "latitudine">
<input type="text" id = "longitudine">
<button> conferma coordinate</button>
 <div>
 	
    <input id="address" type="text">
   
    <input type="button" value="Encode" onclick="codeAddress()">
  </div>
<div id="map-canvas"/>
</body>
</html>