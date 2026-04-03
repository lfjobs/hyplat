
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
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
		<title>办公室办公管理</title>
<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath %>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath %>js/dropdown/extendPageMenu.js"></script>

	</head>
	<body>
	<div style="text-align:center;padding-top: 16%">
		<table border="0" width="90%"  cellspacing="0" cellpadding="5" align="center">
			<tr>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/strategic_planning.jsp'"></div>
					<div class="center_a"><strong>企业战略规划管理</strong></div></td>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/public_administration.jsp'"></div>
					<div class="center_a"><strong>行政管理</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/information_managements.jsp'"></div>
					<div class="center_a"><strong>信息管理</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/logistics_management_sx.jsp'"></div>
					<div class="center_a"><strong>后勤管理</strong></div></td>
				<td>
					<div class="na_back_img" 
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/office_dc.jsp'"></div>
					<div class="center_a"><strong>督察（审核）管理</strong></div></td>
			</tr>

		</table>
	</div>

	</body>
</html>
