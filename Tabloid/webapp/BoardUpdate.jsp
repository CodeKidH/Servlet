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
		
		f.action="update_ok.do";
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
		<input type= "hidden" name="num" value="${vo.num }">
		
		<div id="bbsCreated">
			<div>
				<dl>
					<dt>����</dt>
					<dd>
						<input type="text" name="subject" size="74" maxlength="100" value="${vo.subject }"/>
					</dd>
				</dl>
			</div>
			
			<div>
				<dl>
					<dt>writer</dt>
					<dd>
						<input type="text" name="name" size="35" maxlength="20" value="${vo.name }"/>
					</dd>
				</dl>
			</div>
			
			<div>
				<dl>
					<dt>email</dt>
					<dd>
						<input type="text" name="email" size="35" maxlength="50"/>
					</dd>
				</dl>
			</div>
			
			<div id="bbsCreated_content">
				<dl>
					<dt>����</dt>
					<dd>
						<textarea name="content" cols="63" rows="12">${vo.content }</textarea>
					</dd>
				</dl>
			</div>
			
			<div>
				<dl>
					<dt>pwd</dt>
					<dd>
						<input type="password" name="pwd" size="35" maxlength="7"/>
					</dd>
				</dl>
			</div>
			
			<div id="bbsCreated_footer">
				<input type="button" value="�����ϱ�" onclick="sendIt();"/>
				<input type="reset" value="�ٽ��Է�" onclick="document.myForm.subject.focus();"/>
				<input type="button" value="�������" onclick="location.href='content.do?num=${vo.num}'"/>
			</div>
		</div>
	</form>
</div>
</body>
</html>