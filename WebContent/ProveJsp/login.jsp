<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
<script type="text/javascript">
 $(document).ready(function() {
	$("#login-form").dialog({
		autoOpen: false,
        height: 300,
    	width: 350,
    	modal: true
    });
	$( "#login-button" ).button().click(function() {
    $( "#login-form" ).dialog( "open" );
  });
});
 
</script>
<div id="login-form" title="Login">
		<form action="/DBAlps/Servlet" name="dati" method="POST">
		<p>Username:<input type="text" name="username"/></p> 
		<p>password:<input type="text" name="password"/></p>
		<input type="hidden" name="operazione" value="login"/>
		<input type="submit" name ="submit" value="OK"/>
		
		</form>
		</div>
<% Partecipante part = (Partecipante)session.getAttribute("partecipante");
	if (part==null){
%>		
<button id="login-button">login</button>
<%}else{ %>
<form action="/DBAlps/Servlet" name="dati" method="POST">
<%=part.getUsername()%>
<input type=hidden name=operazione value=logout>
<input type=submit value="logout">
</form>
<% }%>
</body>
</html>