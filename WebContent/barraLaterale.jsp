<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
</head>
<body>


<div class="container-fluid">
<div class="row">
<div class="col-sm-3 col-md-2 sidebar">
<ul class="nav nav-sidebar">
     <li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
     <li><a href="Servlet?operazione=formCercaProcessi"> ricerca processo</a></li>
     <li><a href="Servlet?operazione=mostraTuttiProcessi"> mostra tutti i processi</a></li>
     <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni metereologiche</a></li>
	 <li><a href="Servlet?operazione=scegliStazioniDeltaT">distribuzioneFrequenzaCumulativa con media mobile su deltaT</a></li>
     <li><a href="Servlet?operazione=scegliStazioniT"> distribuzioneFrequenzaCumulativa su temperature</a></li>
     <li><a href="Servlet?operazione=scegliStazioniPrecipitazioni"> distribuzioneFrequenzaCumulativa con media mobile su precipitazioni</a></li>
     <li><a href="Servlet?operazione=formInserisciStazione"> inserisci stazione</a></li>
     <li><a href="Servlet?operazione=mostraStazioniMaps"> mostra stazioni su mappa</a></li>
     <li><a href="Servlet?operazione=formRicercaStazione"> ricerca stazione</a></li>
</ul>

</div>
</div>
</div>

</body>
</html>