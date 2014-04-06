<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ricerca avanzata</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
		
			<p>Nome:<input type="text" name="nome" ></p>
			<p>Data:</p>
			<p> <input type="text" id="data" name="data"></p>
			<p> <input type="time" id="ora" name="ora" > </p>
			
			<p>descrizione:<input type="text" name="descrizione"  ></p>
			<p>note:<input type="text" name="note"></p>
			<p>superficie:<input type="text" name="superficie" ></p>
			<p>larghezza:<input type="text" name="larghezza" ></p>
			<p>altezza:<input type="text" name="altezza" ></p>
			<p>volume_specifico<input type="number" name=volume_specifico ></p>
			
			<p>dati sull'ubicazione</p>
			<p>bacino:<input type="text" name="bacino"></p>
			<p>sottobacino:<input type="text" name="sottobacino">
			<p>comune:<input type="text" name="comune"></p>
			<p>idcomune:<input readonly="readonly" type="text" id="idcomune" name="idcomune" /></p>
			<p>provncia:<input type="text" name="provincia"></p>
			<p>regione:<input type="text" name="regione"></p> 
			<p>nazione:<input type="text" name="nazione"></p>
			<p>latitudine:<input type="text" name="latitudine" ></p>
			<p>longitudine:<input type="text" name="longitudine"></p>
			<p>quota:<input type="text" name="quota"></p>
			<p>esposizione:<input type="text" name="esposizione"></p>
		
			<input type="hidden" name="operazione" value="cercaProcesso">
			<input type="submit" name ="submit" value="OK">
			 
		</form>
</body>
</html>