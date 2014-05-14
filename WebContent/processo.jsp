<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

<jsp:useBean id="HTMLc" class="bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
 <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
   <link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
   <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css">
 <script src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.min.js"></script>
 <script src="js/SeparateDate.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Processo</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<jsp:include page="barraLaterale.jsp"></jsp:include>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<jsp:getProperty name="HTMLc" property="content"/>
</div>



</body>
</html>