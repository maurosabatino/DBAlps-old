<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="controller.ControllerJson"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Autocomplete - Multiple values</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
  	<% ControllerJson.createJsonDanni("C:\\Users\\Mauro\\workspace\\Tirocinio\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\DBAlps\\"); %>
    var availableTags = <%=ControllerJson.getJsonDanni("C:\\Users\\Mauro\\workspace\\Tirocinio\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\DBAlps\\", "IT")%>;
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
    $( "#tags" )
      // don't navigate away from the field on tab when selecting an item
      .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).data( "ui-autocomplete" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
       
          var terms = split( this.value );
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          
          return false;
        }
      });
  
  });
  </script>
</head>
<body>
 <form action="/DBAlps/Servlet" name="dati" method="POST">
<div class="ui-widget">
  <label for="tags">Tag programming languages: </label>
  <input id="tags" name="tag" size="50">
  <input id="id" name="id" size="50">
</div>
<input type="hidden" name="operazione" value="provaAutocomlete">
			<input type="submit" name ="submit" value="OK">
</form>
 
 
</body>
</html>