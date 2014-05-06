<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<jsp:useBean id="HTMLc" class="bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
   <link rel="stylesheet" type="text/css" href="sidebar.css"/>
    <script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Utente</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<jsp:include page="barraLaterale.jsp"></jsp:include>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<jsp:getProperty name="HTMLc" property="content"/>
</div>
</body>
</html>