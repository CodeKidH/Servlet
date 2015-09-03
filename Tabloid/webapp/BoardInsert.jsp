<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
	function sendIt(){
		f = document.myForm;
		f.action = "insert.do";
		f.submit();
	}
</script>
</head>
<body>
<div id = "bbs">
	<div id="bbs_title">
	jsp, servlet
	</div>
	
	<form name="myForm" method="post" action="">
		<div id="bbsCreated">
			<div>
				<dl>
					<dt>title</dt>
					<dd>
						<input type="text" name="subject" size="74" maxlength="100"/>
						
					</dd>
				</dl>
			</div>
			
			<div>
				<dl>
					<dt>writer</dt>
					<dd>
						<input type="text" name="name" size="35" maxlength="20"/>
					</dd>
				</dl>
			</div>
			
			<div id="bbsCreadted_content">
				<dl>
					<dt>content</dt>
					<dd>
						<textarea name="content" cols="63" rows="12"></textarea>
					</dd>
				</dl>
			</div>
			
			<div>
				<dl>
					<dt>pw</dt>
					<dd>
						<input type="password" name="pwd" size="35" maxlength="7"/>&nbsp;삭제필요
					</dd>
				</dl>
			</div>
			
			<div id="bbsCreated_footer">
				<input type="button" value="등록" onclick="sendIt()"/>
				<input type="reset" value="다시" onclick="document.myForm.subject.focus();"/>
				<input type="button"value="취소" onclick="location.href='list.do'"/>
			</div>
		</div>
	</form>
</div>

</body>
</html>