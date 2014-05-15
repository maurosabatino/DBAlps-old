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
<div class="panel panel-default">
	<div class="panel-heading">Query sui processi</div>
	<br>
	<br>
		<div class="list-group">
  			<a href="Servlet?operazione=formRicercaSingola&attributi=nome" class="list-group-item">Cerca per nome</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=anno" class="list-group-item">Cerca per anno</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=caratteristicaSito_IT" class="list-group-item">Cerca per caratteristica sito</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=tpnome_IT" class="list-group-item">cerca per tipologia processo</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=comune-provincia-regione-nazione-sottobacino-bacino" class="list-group-item">ricerca territoriale</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=quota" class="list-group-item">ricerca per quota</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=dtipo_IT" class="list-group-item">ricerca per danni</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=nomeLitologia_IT-proprietaTermiche_IT-statoFratturazione_IT" class="list-group-item">ricerca per litologia</a>
  			<a href="Servlet?operazione=formRicercaSingola&attributi=" class="list-group-item">ricerca sulla mappa(da implementare)</a>
  			
          	<a href="Servlet?operazione=mostraTuttiProcessi" class="list-group-item"> mostra tutti i processi</a>
			<a href="Servlet?operazione=mostraProcessiMaps" class="list-group-item"> mostra processi sulla mappa</a>
 			<a href="Servlet?operazione=formCercaProcessi" class="list-group-item"> ricerca processo</a>
  		</div>
  		
</div>

</body>
</html>