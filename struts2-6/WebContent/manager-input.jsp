<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="scripts/jquery-1.4.2.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		var count = 0;
		
		$("#add").click(function(){
			count++;
			
			$("#button").before("<tr><td>Mgrs[" + count + "].Name:</td><td><input name='mgrs[" + count + "].name'/></td></tr>")
			            .before("<tr><td>Mgrs[" + count + "].Birth:</td><td><input name='mgrs[" + count + "].birth'/></td></tr>");
			
			return false;
		
		});
		
		$("#remove").click(function(){

			count--;
			
			$("#button").prev("tr").remove();
			$("#button").prev("tr").remove();
			
			return false;
		
		});
		
	});
	
</script>
</head>
<body>
	
	<form action="testConversion2.action">
	
		<table>
		
			<tbody>
				
				<tr>
					<td>Mgrs[0].Name:</td>
					<td><input name="mgrs[0].name"/></td>
				</tr>
				
				<tr>
					<td>Mgrs[0].Birth:</td>
					<td><input name="mgrs[0].birth"/></td>
				</tr>
				
				<tr id="button">
					<td><button id="add">新加一个</button></td>
					<td><button id="remove">删除一个</button></td>
				</tr>
				
				<tr>
					<td colspan="2" align="right">
						<input type="submit" value="Submit"/>
					</td>
				</tr>
			
			</tbody>
			
		</table>
		
	</form>
	
</body>
</html>