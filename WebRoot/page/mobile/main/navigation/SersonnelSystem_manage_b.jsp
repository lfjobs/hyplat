<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>售中服务管理科</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/mobilecss.css" rel="stylesheet"
			type="text/css" />
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

a:link,a:visited,a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a {
	color: #333;
	text-decoration: none;
}

a:active {
	color: #002f76;
	font-weight: bold;
}

.roundedCorners {
	width: 750px;
	padding: 10px;
	margin: auto;
	background-color: #FFF;
	border: 1px solid #CCE6F1;
	/* Do rounding (native in Safari, Firefox and Chrome) */
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
}
</style>
		<style type="text/css">
<!--
.table03 {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
	margin: 10px 0 0 10px；
}

.table03 th {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
}

.table03 td {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

		<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
		<script type="text/JavaScript">
addEvent(window, 'load', initCorners);
function initCorners() {
var setting = {
tl: { radius: 6 },
tr: { radius: 6 },
bl: { radius: 6 },
br: { radius: 6 },
antiAlias: true
}
curvyCorners(setting, ".roundedCorners");
}</script>
	</head>
	<body>
		<div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" class="actived"><span>售中服务处</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<div id="subNav2">
						<p class="secondNav">
							<a href="#"
								onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'">预定产品</a>
						</p>
					</div>
				</div>
	</body>
</html>
