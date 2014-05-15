<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.partecipante.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NavBar</title>
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
     <a class="navbar-brand" href="index.jsp">DBAlps</a>
    </div>
   
  
  <div class="navbar-collapse collapse">
 <% Partecipante part = (Partecipante)session.getAttribute("partecipante");
	if (part==null){
%>	
<ul class="nav navbar-nav navbar-right">

<li><button class="btn btn-info navbar-btn " data-toggle="modal" data-target=".login-form">Login</button></li>

</ul>

  <%}else{ %>
  <form  class="navbar-form navbar-right" action="/DBAlps/Servlet" name="dati" method="POST">
  <div class="form-group">
  <div>
<p class="navbar-text">Utente: <%=part.getUsername()%></p> 

<input type=hidden name=operazione value=logout>
<button type="submit" class="btn btn-warning navbar-btn btn-sm">Logout</button>
</div>
</div>

</form>
<% }%>

  </div>
   </div>
   
 
</div>


<div class="modal login-form" tabindex="-6" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  				<div class="modal-dialog modal-sm">
    				<div class="modal-content">
     					<form  action="/DBAlps/Servlet" name="dati" method="POST">
  							<div class="form-group">
  							<br>
  							<br>
  							<div class="row">
  							<div class="col-md-10 col-md-offset-1">
   							<label for="username">Username </label><input type="text" name="username" id="username" class="form-control" placeholder="username"/>
							</div>
							</div>
							<div class="row">
							<div class="col-md-10 col-md-offset-1">
							<label for="password">Password </label><input type="password" name="password" id = "password" class="form-control" placeholder="password"/>
							<input type="hidden" name="operazione" value="login"/>
							</div>
							</div>
							</div>
							<div class="row">
							<div class="col-md-10 col-md-offset-4">
							<button type="submit" class="btn btn-default">Login</button>
							</div>
							</div>
							<br>
							<br>
 						</form>
    				</div>
  				</div>
  </div>
</body>
</html>