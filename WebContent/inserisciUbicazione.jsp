<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
			<p>quota:<input type="number" name="quota"></p>
			<input type="hidden" name="operazione" value="inserisciUbicazione">
			<input type="submit" name ="submit" value="OK">
		</form>

</body>
</html>