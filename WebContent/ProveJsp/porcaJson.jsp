<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="controller.ControllerJson"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Autocomplete</title>
  <script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>

  
  <script type="text/javascript">
	$(function() {
		var states = <%= ControllerJson.getJsonLocazioneAmminitrativa("C:\\Users\\Mauro\\workspace\\Tirocinio\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\DBAlps\\") %>;
  		$("#comune").autocomplete({
  			source: states,
 			focus: function( event, ui ) {
    	$( "#comune" ).val( ui.item.label);
     	  	return false;
  			},
		select: function(event, ui) {
			$('#idcomune').val(ui.item.idComune);
	  		$('#provincia').val(ui.item.provincia);	
	 		$('#regione').val(ui.item.regione);
	  		$('#nazione').val(ui.item.nazione);
 	 	}
 	 	});
  });
  </script>
</head>
<body>
<div class="container">




<input type="text" id="comune" name="comune" /> 
<input type="hidden" id="idcomune" name="idcomune">
<input readonly="readonly" type="text" id="provincia" name="provincia" />
<input readonly="readonly" type="text" id="regione" name="regione" />
<input readonly="readonly" type="text" id="nazione" name="nazione" />




</div>
 
</body>
</html>