<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>


</head>
<body>

<%Partecipante part = (Partecipante)session.getAttribute("partecipante"); %>
<div class="row">
<div class="col-sm-3 col-md-2 sidebar" id=login>
<ul class="nav nav-sidebar">
<%if(part!=null&&(part.hasRole(Role.AMMINISTRATORE))){ %>
 <li><a href="Servlet?operazione=formCreaUtente"> Crea Utente</a></li>
 <li><a href="Servlet?operazione=visualizzaTuttiUtenti">visualizza tutti gli utente</a></li>
 <li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
 <li><a href="Servlet?operazione=mostraTuttiProcessi"> mostra tutti i processi</a></li>
 <li><a href="Servlet?operazione=formCercaProcessi"> ricerca processo</a></li>
 <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni metereologiche</a></li>
 <li><a href="Servlet?operazione=formRicercaStazione"> ricerca stazione</a></li>
 <li><a href="Servlet?operazione=mostraStazioniMaps"> mostra stazioni su mappa</a></li>
 <li><a href="Servlet?operazione=scegliStazioniDeltaT">distribuzione deltaT</a></li>
 <li><a href="Servlet?operazione=scegliStazioniT"> distribuzione temperature</a></li>
 <li><a href="Servlet?operazione=scegliStazioniPrecipitazioni"> distribuzione precipitazioni</a></li>
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
<%} %>
<%if(part==null){ %>
<li><a href="Servlet?operazione=formInserisciProcesso"> inserisci Segnalazione</a></li>
<%} %>   
</ul>
</div>
</div>
</body>
</html>