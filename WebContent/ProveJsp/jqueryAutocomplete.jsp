<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<link rel="stylesheet" href="css/ui-lightness/jquery-ui.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script>
  $(function() {
    var ente = [
     <%
     String url = "jdbc:postgresql://localhost:5432/DBAlps";
		String user = "admin";
		String pwd = "dbalps";
		try{
			Connection conn = DriverManager.getConnection(url,user,pwd);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from ente");
			while(rs.next()){
				%>
				"<%=rs.getString("nome") %>",
			<%}

		
	} catch (SQLException e) {System.out.println(e.getMessage());}
     %>
    ];
    $( "#ente" ).autocomplete({
    	minLength: 0,
      source: ente
    });
  });
  </script>
  <script type="text/javascript">
  $(function() {
	    var comune = [
	     <%
	     
			try{
				Connection conn = DriverManager.getConnection(url,user,pwd);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from comune");
				while(rs.next()){
					%>
					
					"<%=rs.getString("nomecomune")%>",
				<%}

			conn.close();
		} catch (SQLException e) {System.out.println(e.getMessage());}
	     %>
	    ];
	    $( "#comune" ).autocomplete({
	    	minLength: 0,
	      source: comune
	    });
	  });
  </script>
  
  <div class="ui-widget">
  <label for="ente">Tags: </label>
  <input id="ente">
  
</div>

<div class="ui-widget">
  <label for="comune">Tags: </label>
  <input id="comune">
  
</div>

</body>
</html>