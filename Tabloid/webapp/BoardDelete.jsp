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
<script type="text/javascript">
	function sendIt(){
		f = document.myForm;
		
		str = f.pwd.value;
		str = str.trim();
		
		if(!str){
			alert("�߸��Է�");
			f.pwd.focus();
			return;
		}
		
		f.pwd.value= str;
		
		f.action="delete_ok.do";
		f.submit();
	}
</script>
</head>
<body>
<div id="bbs">
	<div id="bbs_title">
		�Խ���
	</div>
	
	<form name="myForm" method="post" action="">
		<input type="hidden" name="num" value="${num}">
		<div id="bbsCreated">
			<div>
				<dl>
					<dt>pw</dt>
					<dd>
						<input type="password" name="pwd" size="35" maxlength="7"/>&nbsp;
					</dd>
				</dl>
			</div>
		</div>
		
		<div id="bbsCreated_footer">
			<input type="button" value="�����ϱ�" onclick="sendIt();"/>
			<input type="reset" value="�ٽ��Է�" onclick="document.myForm.pwd.focus();"/>
			<input type="button" value="�������" onclick="location.href='content.do?num=${num}'"/>
		</div>
	</form>
</div>
</body>
</html>