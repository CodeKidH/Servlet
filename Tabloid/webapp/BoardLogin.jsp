<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<%
	request.setCharacterEncoding("UTF-8");
	String cp =request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div align="center" style="padding-top:100px">
	<form action="adminLogin_ok.do" method="post">
		<p>온니 주인</p>
		<p><a href="list.do">홈으로</a>
		<table>
			<tr valign="middle">
				<td><img src=""><br>
				<br><br>
				<table>
					<tr>
						<td><b>id</b></td>
						<td><input type="text" style="width:150px" name="uid"></td>
					</tr>
					<tr>
						<td><b>pw</b></td>
						<td><input type="password" style="width:150px;" name="upw"> </td>
					</tr>
					<tr>
						<td colspan="2" align="center"><br>
						<input type="submit" value="로긴"><br>
					</tr>
					<tr>
						<td colspan="2" align="center"><span style="color:red">${errMsg }</span><br>
					</tr>
					
				</table>
			</tr>
			
		</table>
	</form>

</div>
</body>
</html>