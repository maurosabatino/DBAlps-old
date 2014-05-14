<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserisci un processo</title>
<script type="text/javascript">
function verificaInserisci(modulo)
{
	if (modulo.nome.value == "") {
		alert("Campo -titolo- mancante.\nModulo non spedito.");
		modulo.nome.focus();
		return false;
	}
	if (modulo.note.value == "") {
		alert("Campo -autore- mancante.\nModulo non spedito.");
		modulo.autore.focus();
		return false;
	}
	if (modulo.bacino.value == "") {
		alert("Campo -prezzo- mancante.\nModulo non spedito.");
		modulo.prezzo.focus();
		return false;
	}


return true;
}
</script>
</head>
<body>



<form action="/DBAlps/Servlet" onSubmit="return verificaInserisci(this);" name="dati" method="POST">
			<p>Nome:<input type="text" name="nome" value="prova"></p>
			<p>Aggregazione giornaliera:<input type="text" name="aggregazioneGiornaliera" value="agg"></p>
			<p>Note:<input type="text" name="note" value="note"></p>
			<p>Periodo Funzionamento:<input type="text" name="periodoFunzionamento" value="periodo"></p>
			<p>Oraria:<input type="text" name="oraria" value="false"></p>
			<br>					
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
			
			<input type="hidden" name="operazione" value="inserisciStazione">
			<input type="submit" name ="submit" value="OK">
		</form>
</body>
</html>