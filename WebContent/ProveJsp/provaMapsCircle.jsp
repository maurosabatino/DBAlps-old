<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Circles</title>
   

  </head>
  <body>
      <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>



var cityCircle;
//document.getElementById("mytext").value=citymap[city].r;
function initialize() {
  // Create the map.
  var mapOptions = {
    zoom: 4,
    center: new google.maps.LatLng(12, 14),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };

  var map = new google.maps.Map(document.getElementById('gmap'),
      mapOptions);
  
    var populationOptions = {
      strokeColor: '#FF0000',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#FF0000',
      fillOpacity: 0.35,
      map: map,
      center: new google.maps.LatLng(12, 14), 
      radius: 100 ,
      editable:true

    };
    cityCircle = new google.maps.Circle(populationOptions);
  
  google.maps.event.addListener(cityCircle, 'radius_changed', function() {
	  var elem = document.getElementById("mytext");
	  elem.value = cityCircle.getRadius();
	  
  });
 
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
   <div id="gmap" style="width:400px;height:500px"></div>
    
      <input type="text" id="mytext" />
    
  </body>
</html>