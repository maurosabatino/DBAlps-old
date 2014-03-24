<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/globalize.js"></script>
  <script src="js/globalize.culture.de-DE.js"></script>
<script>


  $(function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
      dateFormat: "yy-mm-dd"
    });
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
	  $( "#spinner" ).timespinner();
	  var current = $( "#spinner" ).timespinner( "value" );
	  Globalize.culture( "de-DE");
	  $( "#spinner" ).timespinner( "value", current );
  });
  </script>
  
  <p>Date: <input type="text" id="datepicker"></p>
	<p>
  
  <input id="spinner" name="selector" >
 
</p>
</body>
</html>