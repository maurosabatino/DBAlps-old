<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="controller.ControllerDatabase"%>
<!DOCTYPE html>
<html>
<script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/globalize.js"></script>
 <script src="js/globalize.culture.de-DE.js"></script>
 
  <script>
  $(function() {
    $( "#data" ).datepicker({
    	changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd"});
    });
  </script>
  
  <script>
  $.widget( "ui.timespinner", $.ui.spinner, {
	    options: {
	      step: 60 * 1000,
	      page: 60
	    },
	 
	    _parse: function( value ) {
	      if ( typeof value === "string" ) {
	        if ( Number( value ) == value ) {
	          return Number( value );
	        }
	        return +Globalize.parseDate( value );
	      }
	      return value;
	    },
	 
	    _format: function( value ) {
	      return Globalize.format( new Date(value), "t" );
	    }
	  });
  $(function() {
	  $( "#ora" ).timespinner();
	  var current = $( "#ora" ).timespinner( "value" );
	  Globalize.culture( "de-DE");
	  $( "#ora" ).timespinner( "value", current );
  });
  </script>
    <script type="text/javascript">
	$(function() {
		var states =
  		$("#comune").autocomplete({
  			source: states,
 			focus: function( event, ui ) {
    	$( "#comune" ).val( ui.item.comune);
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserisci un processo</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
		
			
			
			<p>Nome:<input type="text" name="nome" value="nome"></p>
			<p>Data:</p>
			<p> <input type="text" id="data" name="data"></p>
			<p> <input type="time" id="ora" name="ora" > </p>
			
			<p>descrizione:<input type="text" name="descrizione" value="descrizione" ></p>
			<p>note:<input type="text" name="note" value="not"></p>
			<p>superficie:<input type="text" name="superficie" value="12"></p>
			<p>larghezza:<input type="text" name="larghezza" value="12"></p>
			<p>altezza:<input type="text" name="altezza" value="12"></p>
			<p>volume_specifico<input type="number" name=volume_specifico value="12"></p>
			
			<p>dati sull'ubicazione</p>
			<p>bacino:<input type="text" name="bacino"></p>
			<p>sottobacino:<input type="text" name="sottobacino">
			<p>comune:<input type="text" id="comune" name="comune" /> </p>
			<p>idcomune:<input readonly="readonly" type="text" id="idcomune" name="idcomune" /></p>
			<p>provncia:<input readonly="readonly" type="text" id="provincia" name="provincia" /></p>
			<p>regione:<input readonly="readonly" type="text" id="regione" name="regione" /></p> 
			<p>nazione:<input readonly="readonly" type="text" id="nazione" name="nazione" /></p>
			<p>latitudine:<input type="text" name="latitudine" value="12"></p>
			<p>longitudine:<input type="text" name="longitudine"value="14"></p>
			<p>quota:<input type="text" name="quota" value="12"></p>
			<p>esposizione:<input type="text" name="esposizione" value="nord"></p>
		
			<input type="hidden" name="operazione" value="inserisciProcesso">
			<input type="submit" name ="submit" value="OK">
			 
		</form>
</body>
</html>