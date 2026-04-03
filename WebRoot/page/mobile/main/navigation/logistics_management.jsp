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
		<title>办公室-后勤管理</title>
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
	}
	</script>
	</head>
	<body>
		<div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" class="actived"><span>后勤管理办</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<div id="subNav2" align="center">
						<p class="secondNav">
							<a href="#"
								onClick="parent.daohang.document.location.href='<%=basePath%>/ea/mobilecontactuser/ea_getListContactUser.jspa'">个人接待科</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="parent.daohang.document.location.href='<%=basePath%>/ea/mobilecontactConnection/ea_getListContactConnection.jspa'">单位接待科</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="parent.daohang.document.location.href='<%=basePath%>/ea/car/ea_getCarInformationList.jspa'">车辆管理科</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="parent.daohang.document.location.href='<%=basePath%>/ea/mobileelectricity/ea_getListForPage.jspa'">用电管理科</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onClick="parent.daohang.document.location.href='<%=basePath%>/ea/mobilewater/ea_getListForPage.jspa'">用水管理科</a>
						</p>
					</div>
				</div>
			</div>
			<div class="header">
				<div class="padder">
					<div class="nav">
						<div class="navLaftBg">
							<div class="navRightBg">
								<div class="mainNav">
									<a href="#" class="actived"><span>库房管理科</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<div id="subNav2">
							<p class="secondNav">
								<a href="#"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/information_management_a.jsp'">资料库房办</a>
							</p>
							<p class="secondNav">
								<a href="#"
									onClick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/information_management_b.jsp'">财务库房办</a>
							</p>
							<p class="secondNav">
								<a href="#"
									onClick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/information_management_c.jsp'">实物库房办</a>
							</p>
						</div>
					</div>
				</div>
	</body>
</html>

