<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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
	
	ProductId: ${productId }
	<br><br>

	ProductName: <%=request.getParameter("productName") %>
	<br><br>
	
	ProductDesc: <s:property value="[1].productDesc"/>
	<br><br>
	
	ProductPrice: <s:property value="[0].productPrice"/>
	<br><br>
	<%-- <%=request %> --%>
	
	ProductName1: ${sessionScope.product.productName }
	<s:property value="#session.product.productName"/>
	<br><br>
	
	ProductName2: ${requestScope.test.productName }
	<s:property value="#request.test.productName"/>
	<br><br>
	
	<!-- 使用OGNL 调用public 类型的静态字段和静态方法 -->
	<s:property value="@java.lang.Math@PI"/>
	<br><br>

	<s:property value="@java.lang.Math@cos(0)"/>
	<br><br>
	
	<!-- 调用对象栈的方法为一个属性赋值 -->
	<s:property value="setProductName('ben')"/>
	
	<s:property value="productName"/>
	<br><br>
	<!-- 调用数组对象的属性 -->
	<%
		String[] names = new String[]{"aa","bb","cc","dd"};
		request.setAttribute("names", names);
	%>
	length: <s:property value="#attr.names.length"/>
	<br><br>
	names[2]: <s:property value="#attr.names[2]"/>
	<br><br>
	<!-- 使用OGNL 访问Map -->
	<%
		Map<String,String> letters = new HashMap<>();
		request.setAttribute("letters", letters);
		letters.put("aa", "a");
		letters.put("bb", "b");
		letters.put("cc", "c");
		letters.put("dd", "d");
	%>
	<s:property value="#attr.letters.size"/>
	<s:property value="#attr.letters['aa']"/>
</body>
</html>