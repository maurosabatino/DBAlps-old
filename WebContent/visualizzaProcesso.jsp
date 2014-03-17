<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<jsp:useBean id="Processo" class="bean.Processo" scope="request" />
<jsp:setProperty  name="Processo" property="*"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>visualizzaProcesso</title>
</head>
<body>
<jsp:getProperty name="Processo" property="visualizzaprocesso"/>

</body>
</html>