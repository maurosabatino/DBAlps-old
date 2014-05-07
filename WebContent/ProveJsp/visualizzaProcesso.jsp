<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
 <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css"/>
   <link rel="stylesheet" type="text/css" href="css/sidebar.css"/>
 <script src="js/jquery-1.11.0.js"></script>
 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"> </script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.min.js"></script>
<body>

<jsp:include page="/navbar.jsp"></jsp:include>

<jsp:include page="/barraLaterale.jsp"></jsp:include>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

<div class="row"><h2>Nome Del Processo </h2></div>

<div class="row"><h2><small> Data e Ora</small></h2> </div>

<div class="row">
<dl class="dl-horizontal">
  <dt>Descrizione</dt>
  <dd>una descrizione del processo</dd>
</dl>
</div>
</div>

</body>
</html>