<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>twitter</title>
</head>
<body>

<script src="js/typehead.js" ></script>
<script src="js/jquery-1.11.0.js"></script>
<script src="js/jquery-ui-1.10.4.custom.js"></script>
<script src="js/hogan.js"></script>
<script type="text/javascript">


 

 
$('#typeahead').typeahead({
 
  source:  ["aaaa","bbb"]
  });

</script>



 <input class="typeahead" type="text" placeholder="States of USA" data-provided="typeahead">


</body>
</html>