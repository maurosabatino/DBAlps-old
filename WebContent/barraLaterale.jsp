<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
 <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
   <link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
   <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css">
 <script src="js/jquery-1.11.0.js"></script>
 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"> </script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
</head>
<body>


<div class="row">
<div class="col-sm-3 col-md-2 sidebar" id=login>
<ul class="nav nav-sidebar">

	 <li><a href="Servlet?operazione=formCreaUtente"> Crea Utente</a></li>
     <li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
     <li><a href="Servlet?operazione=formCercaProcessi"> ricerca processo</a></li>
     <li><a href="Servlet?operazione=mostraTuttiProcessi"> mostra tutti i processi</a></li>
     <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni metereologiche</a></li>
	 <li><a href="Servlet?operazione=scegliStazioniDeltaT">distribuzione deltaT</a></li>
     <li><a href="Servlet?operazione=scegliStazioniT"> distribuzione temperature</a></li>
     <li><a href="Servlet?operazione=scegliStazioniPrecipitazioni"> distribuzione precipitazioni</a></li>
     <li><a href="Servlet?operazione=formInserisciStazione"> inserisci stazione</a></li>
     <li><a href="Servlet?operazione=mostraStazioniMaps"> mostra stazioni su mappa</a></li>
     <li><a href="Servlet?operazione=formRicercaStazione"> ricerca stazione</a></li>
</ul>

</div>
</div>


</body>
</html>