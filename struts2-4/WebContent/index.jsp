<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<a href="UserAction-save">User Save</a>
	<br><br>

	<a href="UserAction-update">User Update</a>
	<br><br>

	<a href="UserAction-delete">User Delete</a>
	<br><br>

	<a href="UserAction-query">User Query</a>
	<br><br>

	<a href="testDynamicMehodInvocation">Test DynamicMehodInvocation</a>
	<br><br>
	
	<a href="product-input.action">Product Input</a>
	<br><br>
	
	<a href="testTag.action?name=ben">Test Tag</a>
	<br><br>
	<%
		session.setAttribute("date", new Date());
	%>
</body>
</html>