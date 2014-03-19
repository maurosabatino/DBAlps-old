<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ricerca avanzata</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
		
			<p>Nome:<input type="text" name="nome" ></p>
			<p>Data:<input type="datetime" name="data" id="datepicker"></p>
			<p>descrizione:<input type="text" name="descrizione"  ></p>
			<p>note:<input type="text" name="note"></p>
			<p>superficie:<input type="text" name="superficie" ></p>
			<p>larghezza:<input type="text" name="larghezza" ></p>
			<p>altezza:<input type="text" name="altezza" ></p>
			<p>volume_specifico<input type="number" name=volume_specifico ></p>
			
			<p>dati sull'ubicazione</p>
			<p>bacino:<input type="text" name="bacino"></p>
			<p>sottobacino:<input type="text" name="sottobacino">
			<p>comune:<input type="text" name="comune"></p>
			<p>provncia:<input type="text" name="provincia"></p>
			<p>regione:<input type="text" name="regione"></p> 
			<p>nazione:<input type="text" name="nazione"></p>
			<p>latitudine:<input type="text" name="latitudine" ></p>
			<p>longitudine:<input type="text" name="longitudine"></p>
			<p>quota:<input type="text" name="quota"></p>
			<p>esposizione:<input type="text" name="esposizione"></p>
		
			<input type="hidden" name="operazione" value="cercaProcesso">
			<input type="submit" name ="submit" value="OK">
			 
		</form>
</body>
</html>