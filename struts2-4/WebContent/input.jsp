<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:debug></s:debug>
	
	<%-- <s:property value="exceptionStack"/> --%>
	<s:property value="exception"/>
	${exception }
	<br>
	<s:property value="exception.message"/>
	${exception.message }
	<br><br>

	<form action="product-save.action" method="post">
		
		ProductName:<input type="text" name="productName"/>
		<br><br>
		
		ProductDesc:<input type="text" name="productDesc"/>
		<br><br>
		
		ProductPrice:<input type="text" name="productPrice"/>
		<br><br>
		
		<input type="submit" value="Submit"/>
		<br><br>
		
	</form>

</body>
</html>