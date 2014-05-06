<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Google Map in jQuery dialog box</title>

<script src="http://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript"></script>
 <script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>

<script>
    var map;
    var coords = new Object();
    coords.lat = 45.57560020947792;
    coords.lng = 9.613037109375;
    
    $(document).ready(function() {
        
        $( "#map_container" ).dialog({
            autoOpen:false,
            width: 555,
            height: 400,
            resizeStop: function(event, ui) {google.maps.event.trigger(map, 'resize') },
            open: function(event, ui) {google.maps.event.trigger(map, 'resize'); },   
            buttons: {
            	"conferma": function(){
                 document.getElementById("latitudine").value = document.getElementById("lati").value;
             	 document.getElementById("longitudine").value = document.getElementById("long").value;
             	$( this ).dialog( "close" );
           	 	}
            }
        });  

        $( "#showMap" ).click(function() {           
            $( "#map_container" ).dialog( "open" );
            map.setCenter(new google.maps.LatLng(coords.lat, coords.lng), 10);
            google.maps.event.addListener(map, "rightclick", function(event) {
        	    var lat = event.latLng.lat();
        	    var lng = event.latLng.lng();
        	    document.getElementById("lati").value = lat;
        	    document.getElementById("long").value = lng;        	    
        	});
            google.maps.event.addListener(marker, 'dragend', function(evt){
           	 	var lat = evt.latLng.lat();
        	 	var lng = evt.latLng.lng();
        	 	document.getElementById("lati").value = lat;
        	 	document.getElementById("long").value = lng; 
           });

            return false;
        });  
        
        $(  "input:submit,input:button, a, button", "#controls" ).button();
        initialize();
        var myLatlng = new google.maps.LatLng(coords.lat, coords.lng);            
        var marker = new google.maps.Marker({
              position: myLatlng, 
              draggable:true
        });
      
        marker.setMap(map);
     });
    function initialize() {      
    
        var latlng = new google.maps.LatLng(coords.lat, coords.lng);
        var myOptions = {
          zoom: 6,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
       map = new google.maps.Map(document.getElementById("map_canvas"),  myOptions);
       
    } 
    
    
</script>
</head>

<body>
    <div id="map_container" title="Location Map">    
        <div id="map_canvas" style="width:100%;height:90%;"></div>
       
        <input type="text"  id = "lati"/>
		<input type="text" id = "long"/>
	</div>
    
    <div id="controls">
        <input type="button" name="showMap" value="Show Map" id="showMap" />
        <input type="text"  id = "latitudine"/>
		<input type="text" id = "longitudine"/>
    </div>    
</body>
</html>
