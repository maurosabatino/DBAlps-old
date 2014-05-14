<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>Example</title>

</head>
<body>
<div id="gmap" style="width:400px;height:500px"></div>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
<script>
var map_center = new google.maps.LatLng(0.1700235000, 20.7319823000);

var map = new google.maps.Map(document.getElementById("gmap"), {
zoom:1,
center:map_center,
mapTypeId:google.maps.MapTypeId.HYBRID
});

var pos;
var marker;
var coordinate={};
coordinate[0] = {
x : 10,
y: 20
};
coordinate[1] = {
	x : 50,
		y: 70
		};
coordinate[2] = {
		x : 22,
		y: 20
		};
var marker_list = [];
for (var i = 0; i < 3; i++) { //creiamo 100 marker in posizione casuale
pos = new google.maps.LatLng(coordinate[i].x, coordinate[i].y);
marker = new google.maps.Marker({
position:pos,
map:map,
title:'Title'
});


var infowindow = new google.maps.InfoWindow();
//var storyClick = new Function("event", "alert('Click on marker " + i + " ');");
google.maps.event.addListener(marker, 'click', (function(marker, i) {
    return function() {
      infowindow.setContent(""+i);
      infowindow.open(map, marker);
    }
  })(marker, i));
	
    marker_list.push(marker);
}

var markerCluster = new MarkerClusterer(map, marker_list, {
gridSize:40,
minimumClusterSize: 4,
calculator: function(markers, numStyles) {
return {
text: markers.length,
index: numStyles
};
}
});


</script>

</body>
</html>