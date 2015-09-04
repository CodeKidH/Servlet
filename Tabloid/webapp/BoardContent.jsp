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
<c:if test="${!empty sessionScope.login}">
<script>
	function adminDelete(num){
		if(confirm("현재 자료를 삭제할까요?")){
			location.href = "adminDelete.do?num="+num;
		}
	}
</script>
</c:if>
</head>
<body>
<div id="bbs">
	<div id="bbs_title">
		게시판
	</div>
	
	<div id="bbsArticle">
		<div id="bbsArticle_header">
			${vo.subject}
		</div>
		
		<div>
			<dl>
				<dt>writer</dt>
				<dd>${vo.name}</dd>
				<dt>line</dt>
				<dd>10</dd>
			</dl>
		</div>
		
		<div>
			<dl>
				<dt>date</dt>
				<dd>${vo.dated}</dd>
				<dt>count</dt>
				<dd>${vo.hitcount }
			</dl>
		</div>
		
		<div id="bbsArticle_content">
			<table width="600" border="0">
				<tr>
					<td style="padding:20px 80px 20px 62px;" valign="top" height="150">
					${vo.content}
					</td>
				</tr>
			</table>
		</div>
		
		<div>
			이전글:작업중
		</div>
		<div>
			다음글
		</div>
	</div>
	<div id="bbsArticle_footer">
		<div id="leftFooter">
			<input type="button" value="수정" onclick="location.href='update.do?num=${vo.num}'"/>
			<c:choose>
				<c:when test="${empty sessionScope.login }">
					<input type="button" value="삭제" onclick="location.href='delete.do?num=${vo.num}'"/>
				</c:when>
				<c:otherwise>
					<input type="button" value="삭제" onclick="javacript:adminDelete('${vo.num }')"/>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div id="rightFooter">
			<input type="button" value="리스트" onclick="location.href='list.do'"/>
		</div>
	</div>
</div>
</body>
</html>