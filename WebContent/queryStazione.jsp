<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
   <link rel="stylesheet" type="text/css" href="sidebar.css"/>
 <script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div class="row">
	<div class="col-xs-6 col-md-11 col-md-push-1"><h3>Query sulla stazione metereologica</h3></div>
	</div>
	<br>
	<br>
	<div class="list-group">
	<div class="row">
  	<div class="col-xs-6 col-md-4  col-md-push-1"><a href="Servlet?operazione=scegliStazioniDeltaT" class="list-group-item"> mostra tutte le stazioni</a></div>
  	</div>	
  	<div class="row">
  	<div class="col-xs-6 col-md-4  col-md-push-1"><a href="Servlet?operazione=scegliStazioniT" class="list-group-item"> mostra stazioni su mappa</a></div>
  	</div>
  	<div class="row">
  	<div class="col-xs-6 col-md-4  col-md-push-1"><a href="Servlet?operazione=formRicercaStazione" class="list-group-item"> ricerca stazione</a></div>
  	</div>
  	</div>


</body>
</html>