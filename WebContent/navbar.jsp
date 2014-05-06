<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
     <a class="navbar-brand" href="index.jsp">DBAlps</a>
    </div>
   <ul class="nav navbar-nav">
     <li class="active"><a href="#">info</a></li>
     <li><a href="#">contatti</a></li>
  </ul>
  
  <div class="navbar-collapse collapse">
 <% Partecipante part = (Partecipante)session.getAttribute("partecipante");
	if (part==null){
%>	
  <form class="navbar-form navbar-right" action="/DBAlps/Servlet" name="dati" method="POST">
   <label for="username">Username </label><input type="text" name="username" id="username" class="form-control" placeholder="username"/>
	<label for="password">Password </label><input type="text" name="password" id = "password" class="form-control" placeholder="password"/>
	<input type="hidden" name="operazione" value="login"/>
	<input type="submit" name ="submit" value="login"/>
 </form>
  <%}else{ %>
  <form  class="navbar-form navbar-right" action="/DBAlps/Servlet" name="dati" method="POST">
<%=part.getUsername()%>
<input type=hidden name=operazione value=logout>
<input type=submit value="logout">
</form>
<% }%>

  </div>
   </div>
   
 
</div>
</body>
</html>