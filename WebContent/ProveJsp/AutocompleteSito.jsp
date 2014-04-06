<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="controller.ControllerDatabase"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

  <script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>

  
  <script type="text/javascript">
	$(function() {
		var sitoProcesso = <%= ControllerDatabase.getJsonSitoProcesso("C:/Users/Mauro/Desktop/") %>;
  		$("#sito").autocomplete({
  			source: sitoProcesso,
 			focus: function( event, ui ) {
    	$( "#sito" ).val( ui.item.label);
     	  	return false;
  			},
		select: function(event, ui) {
			$('#idsito').val(ui.item.idSito);
	  		
 	 	}
 	 	});
  		
 	 
  });
  </script>
<input type="text" id="sito" name="sito" /> 
<input readonly="readonly" type="text" id="idsito" name="idsito">
<input type="text" id="litologia" name="litologia" /> 
</body>
</html>