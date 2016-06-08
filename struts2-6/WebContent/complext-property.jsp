<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<s:form action="testComplextProperty">
		
		<s:textfield name="deptName" label="DeptName"></s:textfield>
		
		<!-- 映射属性的属性 -->
		<s:textfield name="mgr.name" label="MgrName"></s:textfield>
		<s:textfield name="mgr.birth" label="MgrBirth"></s:textfield>
	
		<s:submit></s:submit>
	
	</s:form>
	
</body>
</html>