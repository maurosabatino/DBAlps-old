
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   
<head>
<meta charset="utf-8">
<title>Upload </title>
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
<body>
	<form  action="/DBAlps/Servlet" method="POST" enctype="multipart/form-data">
    	 <input type="file" name="files[]" multiple>
    	 <input type="text" name="idStazione">
    	 <select name="tabella">
    	 <option value="temperatura_avg$temperaturaavg">temperatura media</option>
    	 </select>
    	 <input type="text" id="datepicker" name="data">
    	 <input id="spinner" name="ora" >
         <input type="hidden" name="operazione" value="uploadCSVDatiClimatici">
         <input type="submit" name="invia" value="carica"/>
    </form>
            
</body> 
</html>