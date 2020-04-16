<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <h3 align="center">
    <u>Image Upload Form</u>
 </h3>
 <form action="ImageUpload" method="post" enctype="multipart/form-data">
   Select the Images to Upload :
   <input type="file" name="files" multiple /><br /><br />
   <input type="submit" value="Upload" />
 </form>
</body>
</html>