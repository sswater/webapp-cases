<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="simple" scope="session" class="com.playcoding.webapp.SimpleClass"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp simple</title>
</head>
<body>

  <h1>this is a simple jsp</h1>

  <h2>now it is <u><%=new java.util.Date()%></u></h2>

  <h3>getCount = <%=simple.getCount()%></h3>

</body>
</html>
