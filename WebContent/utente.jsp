<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/DBAlps/Servlet" name="dati" method="POST">
		
			
			
			<p>Nome:<input type="text" name="nome" value="mauro"></p>
			<p>Cognome:<input type="text" name="cognome" value="rossi"></p>	
			<p>Username:<input type="text" name="username" value="ros"></p>
			<p>password:<input type="text" name="password" value="mauro"></p>		
			<p>email:<input type="text" name="email" value="rossi@mail" ></p>
			<p>ruolo:<input type="text" name="ruolo" value="admin"></p>
			
		
			<input type="hidden" name="operazione" value="inserisciUtente">
			<input type="submit" name ="submit" value="OK">
			 
		</form>
</body>
</html>