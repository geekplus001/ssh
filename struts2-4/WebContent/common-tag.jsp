<%@page import="com.ben.struts2.valuestack.PersonComparator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ben.struts2.valuestack.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<s:debug></s:debug>
	<br>
	
	s:property: 打印值栈中的属性值： 对于对象栈， 打印值栈中对应的属性值
	<br><br>
	<s:property value="productName"/>
	
	<br>
	<s:property value="#session.date"/>
	<br>
	
	<s:property value="#parameters.name[0]"/>
	<br>
	
	s:url: 创建一个URL 字符串的
	<br><br>
	<s:url value="/getProduct" var="url">
		<s:param name="productId" value="2002"></s:param>
	</s:url>
	${url }
	<br><br>
	
	<s:url value="/getProduct" var="url2">
	<!-- 对于value 值会自动进行OGNL 解析，若不希望进行OGNL 解析， 使用单引号引起来 -->
		<s:param name="productId" value="productId"></s:param>
	</s:url>
	${url2 }
	
	<br><br>
	
	<!-- 构建一个请求action 的地址 -->
	<s:url action="testAction" namespace="/helloWorld"
			 method="save" var="url4"></s:url>
	${url4 }
	<br><br>
	
	<s:url action="testUrl"  var="url5" includeParams="all"></s:url>
	${url5 }
	
	<br><br>
	s：set: 向page request session application 域对象中加入一个属性值
	<br><br>
	<s:set name="productName" value="aaa" scope="request"></s:set>
	productName: ${requestScope.productName }
	<br><br>
	<%
		Person person = new Person();
		person.setName("ben");
		person.setAge(20);
		
		request.setAttribute("person", person);
	%>
	<s:push value="#request.person">
		${name }
	</s:push>
	<br><br>
	-- ${name } --
	<br><br>
	<s:if test="productPrice > 1000">I7 处理器</s:if>
	<s:elseif test="productPrice > 800">I5 处理器</s:elseif>
	<s:else>I3 处理器</s:else>
	<br><br>
	s: iterator 遍历 并 一次压入和弹出
	<br><br>
	<%
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("AA",10));
		persons.add(new Person("EE",50));
		persons.add(new Person("CC",30));
		persons.add(new Person("BB",20));
		persons.add(new Person("DD",40));
		
		request.setAttribute("persons", persons);
	%>
	<s:iterator value="#request.persons" status="status">
		index: ${status.index } count: ${status.count } ${name } -- ${age }<br>
	</s:iterator>
	<br><br>
	<s:iterator value="persons">
		${name } -- ${age }
	</s:iterator>
	<br><br>
	s:sort  排序
	<%
		PersonComparator pc = new PersonComparator();
		request.setAttribute("comparator", pc);
	%>
	
	<s:sort comparator="#request.comparator" source="persons" var="persons2"></s:sort>
	
	<s:iterator value="#attr.persons2">
		${name }--${age }<br>
	</s:iterator>
	<br><br>
	s:date 对date排版
	<s:date name="#session.date" format="yyyy-MM-dd hh:mm:ss" var="date2"/>
	date: ${date2 }
	<br><br>
	<br><br>
	<br><br>
	<br><br>
	<br><br>
</body>
</html>