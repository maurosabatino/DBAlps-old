<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>marker tanti</title>
<style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
    </style>
    
   <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
</head>
<body>
<script type="text/javascript">

var city = {};
city['prova'] = {
		coord: new google.maps.LatLng(41.878113, -87.629798)
};
city['prova2'] = {
		coord: new google.maps.LatLng(41.878113, -87.629798)
};


function initialize() {
	  var mapOptions = {
	    zoom: 4,
	    center: new google.maps.LatLng(37.09024, -95.712891),
	    mapTypeId: google.maps.MapTypeId.SATELLITE
	  };

	  var map = new google.maps.Map(document.getElementById('map-canvas'),
	      mapOptions);
	  
	  for (var c in city) {
		  var markerOption = {
				  map: map,
				  position: city[c].coord
		  };
		  var marker = new google.maps.Marker(markerOptions);
	 }
}
google.maps.event.addDomListener(window, 'load', initialize);

</script>
 <div id="map-canvas"></div>
</body>
</html>