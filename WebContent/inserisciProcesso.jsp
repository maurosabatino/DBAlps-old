<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  </script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserisci un processo</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
		
			
			
			 <p>Nome:<input type="text" name="nome" value="nome"></p>
			<p>Data:<input type="datetime" name="data" id="datepicker"></p>
			<p>descrizione:<input type="text" name="descrizione" value="descrizione" ></p>
			<p>note:<input type="text" name="note" value="not"></p>
			<p>superficie:<input type="text" name="superficie" value="12"></p>
			<p>larghezza:<input type="text" name="larghezza" value="12"></p>
			<p>altezza:<input type="text" name="altezza" value="12"></p>
			<p>volume_specifico<input type="number" name=volume_specifico value="12"></p>
			
			<p>dati sull'ubicazione</p>
			<p>bacino:<input type="text" name="bacino"></p>
			<p>sottobacino:<input type="text" name="sottobacino">
			<p>comune:<input type="text" name="comune"></p>
			<p>provncia:<input type="text" name="provincia"></p>
			<p>regione:<input type="text" name="regione"></p> 
			<p>nazione:<input type="text" name="nazione"></p>
			<p>latitudine:<input type="text" name="latitudine" value="12"></p>
			<p>longitudine:<input type="text" name="longitudine"value="14"></p>
			<p>quota:<input type="text" name="quota" value="12"></p>
			<p>esposizione:<input type="text" name="esposizione" value="nord"></p>
		
			<input type="hidden" name="operazione" value="inserisciProcesso">
			<input type="submit" name ="submit" value="OK">
			 
		</form>
</body>
</html>