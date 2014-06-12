<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>


</head>
<body>
<%Partecipante part = (Partecipante)session.getAttribute("partecipante"); %>
<div class="row">
<div class="col-sm-3 col-md-2 sidebar" id=login>
<ul class="nav nav-sidebar">
<%if(part!=null&&(part.hasRole(Role.AMMINISTRATORE))){ %>
	
	 <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Inserisci <b class="caret"></b></a>
          <ul class="dropdown-menu">
             <li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
             <li><a href="Servlet?operazione=formInserisciStazione">Inserisci Stazione</a></li>
             <li><a href="Servlet?operazione=elencocaricaDatiClimatici">Inserisci Dati Climatici</a></li>
             <li><a href="Servlet?operazione=scegliProcessoAllegato">Allegato Processo</a></li>
             <li><a href="Servlet?operazione=scegliStazioneAllegato">Allegato Stazione</a></li>
			 
          </ul>
     </li>
 
 	<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Modifica <b class="caret"></b></a>
          <ul class="dropdown-menu">
          	<li><a href="Servlet?operazione=mostraTuttiProcessiModifica">Modifica un Processo </a></li>
          	<li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni</a></li>
          </ul>
     </li>
	<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Query <b class="caret"></b></a>
          <ul class="dropdown-menu">
          	<li><a href="Servlet?operazione=queryProcesso"> Query sul Processo</a></li>
          	<li><a href="Servlet?operazione=queryStazione"> Query Stazione Metereologica</a></li>
          	<li><a href="Servlet?operazione=queryClimatiche"> Query Climatiche</a></li>
          	<li><a href="Servlet?operazione=listaElaborazioni"> Elaborazioni</a></li>
          </ul>
     </li>
     <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Pannello Amministratore <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="Servlet?operazione=formCreaUtente"> Crea Utente</a></li>
            <li><a href="Servlet?operazione=visualizzaTuttiUtenti">visualizza tutti gli utenti</a></li>
            <li><a href="Servlet?operazione=ricaricaJson">ricarica gli autocomplete</a></li>
          </ul>
     </li>
 
 
<%} %>
<%if(part!=null&&(part.hasRole(Role.AVANZATO))){ %>
<li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
<li><a href="Servlet?operazione=formCercaProcessi"> ricerca processo</a></li>
  <li><a href="Servlet?operazione=mostraTuttiProcessi"> mostra tutti i processi</a></li>
     <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni metereologiche</a></li>
      <li><a href="Servlet?operazione=formRicercaStazione"> ricerca stazione</a></li>
      <li><a href="Servlet?operazione=mostraStazioniMaps"> mostra stazioni su mappa</a></li>
      <li><a href="Servlet?operazione=scegliStazioniDeltaT">distribuzione deltaT</a></li>
     <li><a href="Servlet?operazione=scegliStazioniT"> distribuzione temperature</a></li>
     <li><a href="Servlet?operazione=scegliStazioniPrecipitazioni"> distribuzione precipitazioni</a></li>
     <li><a href="Servlet?operazione=query"> interrogazione db</a></li>
<%} %>
<%if(part!=null&&(part.hasRole(Role.BASE))){ %>
<li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
<li><a href="Servlet?operazione=formCercaProcessi"> ricerca processo</a></li>
  <li><a href="Servlet?operazione=mostraTuttiProcessi"> mostra tutti i processi</a></li>
     <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni metereologiche</a></li>
      <li><a href="Servlet?operazione=formRicercaStazione"> ricerca stazione</a></li>
      <li><a href="Servlet?operazione=mostraStazioniMaps"> mostra stazioni su mappa</a></li>
      <li><a href="Servlet?operazione=scegliStazioniDeltaT">distribuzione deltaT</a></li>
     <li><a href="Servlet?operazione=scegliStazioniT"> distribuzione temperature</a></li>
     <li><a href="Servlet?operazione=scegliStazioniPrecipitazioni"> distribuzione precipitazioni</a></li>
     <li><a href="Servlet?operazione=query"> interrogazione db</a></li>
<%} %>
<%if(part==null){ %>
<li><a href="Servlet?operazione=formInserisciProcesso"> inserisci Segnalazione</a></li>
<%} %>   
</ul>
</div>
</div>
</body>
</html>