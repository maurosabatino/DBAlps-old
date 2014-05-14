<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>
</head>
<body>
    <center>
        <form method="post" action="Servlet" enctype="multipart/form-data">
            
            autore<input type="text" name="autore" >
           anno <input type="text" name="anno" >
            titolo<input type="text" name="titolo" >
            in<input type="text" name="in" >
           fonte <input type="text" name="fonte" >
           url <input type="text" name="urlweb" >
           note <input type="text" name="note" >
            <select name="tipo">
            <option value="document">Document</option>
            <option value="map">Map</option>
            <option value="image">Image</option>
            <option value="photo">Photo</option>
            </select>
            
            Select file to upload: <input type="file" name="uploadFile" />
                      
            
            <input type="hidden" name="operazione" value="uploadAllegatoProcesso">
            <input type="submit" value="Upload" />
        </form>
    </center>
</body>
</html>