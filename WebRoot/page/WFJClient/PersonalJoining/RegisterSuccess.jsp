<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人加盟商城业主会员-注册成功</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/WFJClient/style2.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
</head>
<body>
	<div class="wfj10_001">
		<iframe id="indexTop" align="center" width="100%" height="46px"
			src="<%=basePath%>page/WFJClient/Template/Top/Label.jsp"
			frameborder="no" border="0" marginwidth="0" marginheight="0"
			scrolling="no"></iframe>
	<div class="d1">
		<img src="<%=basePath%>images/WFJClient/PersonalJoining/gongxinin.png"/>
	</div>
	<div class="d2">
		<p id="">上海凯泉泵业集团科技有限公司</p>
	</div>
	<div class="d3">
		<p id="">合伙创业商城业主会员</p>
	</div>	
	<div class="d4">
		<img src="<%=basePath%>images/WFJClient/PersonalJoining/zc.png"/>
	</div>
	<div class="d5">
		<input type="button" value="确认登录"/>
	</div>	
	</div>
	<script type="text/javascript">
		$('#indexTop')
				.load(
						function() {
							var doc = document.getElementById("indexTop").contentWindow.document;
							doc.getElementById("topbar_title").innerHTML = "个人加盟商城业主会员（购买）";
							doc.getElementById("return").onclick = function() {
								window.history.go(0);
							}
						});
	</script>
</body>
</html>
