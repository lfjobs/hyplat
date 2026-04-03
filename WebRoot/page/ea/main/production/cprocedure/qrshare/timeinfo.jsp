<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>免费设置</title>
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>
	<script
			src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
			type="text/javascript"></script>





</head>

<body>


<div class="wrap_page" style="margin-top: 1.16rem">


	<table align="center" cellspacing="8" cellpadding="8">
		<tr>
			<td colspan="2" align="center" style="font-weight:bold;">免费时长设置</td>

		</tr>
		<tr>
			<td>场地:</td><td>${carManageAudit.siteName}</td>

		</tr>
		<tr>
			<td>车牌号:</td><td>${carManageAudit.carNumber}</td>

		</tr>
		<tr>
			<td>进/出：</td><td><c:if test="${carManageAudit.status eq '0'}">出</c:if> 	<c:if test="${carManageAudit.status eq '1'}">进</c:if></td>

		</tr>
		<tr>
			<td>开始时间</td><td>${fn:substring(carManageAudit.indate,0,19)}</td>

		</tr>
		<tr>
			<td>截止时间</td><td>${fn:substring(carManageAudit.outdate,0,19)}</td>

		</tr>
	</table>





</div>


</body>

</html>
