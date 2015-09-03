<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<div id="bbsList">
	<div align="right">
		<c:choose>
			<c:when test="${empty sessionScope.login}">
				<a href="adminLogin.do">[관리자]</a>
			</c:when>
			<c:otherwise>
				<a href="adminLogout.do">[로그아웃]</a>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div id="bbsList_title">
	자유게이판
	</div>
	
	<div id="bbsList_header">
		<div id="leftHeader">
			<form name="searchForm" method="post" action="">
				<select name="searchKey">
					<option value="subject">title</option>
					<option value="name">writer</option>
					<option value="content">content</option>
				</select>
				<input type="text" name="searchValue"/>
				<input type="button" value="search" onclick=""/>
			</form>
		</div>
		
		<div id="righrHeader">
			<input type="button" value="to write" onclick="location.href='created.do'"/>
		</div>
	</div>
	
	<div id="bbsList_list">
		<div id="title">
			<dl>
				<dt><b>number</b></dt>
				<dt><b>title</b></dt>
				<dt><b>writer</b></dt>
				<dt><b>date</b></dt>
				<dt><b>count</b></dt>
			</dl>
		</div>
		
		<div id="list">
			
			<c:forEach items="${list}" var="item"> 
				<dl>
					<dd>${item.num}</dd>
					<dd><a href="content.do?num=${item.num}">${item.subject}</dd>
					<dd>${item.name}</dd>
					<dd>${item.dated}</dd>
					<dd>${item.hitcount}</dd>
				</dl>
			</c:forEach>
		</div>
		
		<div id="footer">
			<p>
			${pageIndexList}
			</p>
		</div>
	</div>

	
</div>
</body>
</html>