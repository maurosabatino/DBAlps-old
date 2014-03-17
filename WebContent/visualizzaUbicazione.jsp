<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<jsp:useBean id="opb" scope="request" class="bean.Ubi"/>
<jsp:setProperty name="opb" property="*"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>







 <jsp:getProperty property="visualizza" name="opb" /> 
</body>
</html>