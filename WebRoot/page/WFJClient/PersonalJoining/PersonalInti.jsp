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
<title>个人加盟商城业主会员-初始设置账号密码</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
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
		<div class="im1">
			<div>
				<img 
					src="<%=basePath%>images/WFJClient/PersonalJoining/product.png" />
			</div>
			<div class="info1">
				<p>
					<span>合伙创业商城业主会员</span><br /> <span>￥1万</span>
				</p>
			</div>
		</div>
		<div class="border" style="height:245px;">
			<form>
				<table class="ta1">
					<tr>
						<td>公司名称:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>法人代表（负责人）:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>联系方式:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>账户:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>账号:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>&nbsp</td>
						<td ><input style="width:89px;"type="text"/><input class="bu2" type="button" value="获取验证码"/></td>
					</tr>
					<tr>
						<td>初始密码:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>确认密码:</td>
						<td><input type="text"/></td>
					</tr>
					<tr>
						<td>&nbsp</td>
						<td><input class="bu1" type="button" value="确认"/></td>
					</tr>
				</table>
			</form>
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

