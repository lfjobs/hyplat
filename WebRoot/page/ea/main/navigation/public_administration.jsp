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
		<title>办公室行政管理</title>
<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath %>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath %>js/dropdown/extendPageMenu.js"></script>

	</head>
	<body>
	<div align="left" style="margin-left: 250px;margin-top: 80px;">
		<table>
			<tr>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/unitManager.jsp'"></div>
					<div class="center_a"><strong>单位管理</strong></div></td>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/enterprisecultruebuild.jsp'"></div>
					<div class="center_a"><strong>企业文化建设</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath %>ea/informbills/ea_getOfficeFileManager.jspa?'"></div>
					<div class="center_a"><strong>行政建设管理</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/workfileManager.jsp'"></div>
					<div class="center_a"><strong>办公资料管理</strong></div></td>
				<td>
					<div class="na_back_img" 
						onclick="document.location.href='<%=basePath %>page/ea/main/navigation/databankpublicserach.jsp'"></div>
					<div class="center_a"><strong>资料库公共查询管理</strong></div></td>
			</tr>

		</table>
	</div>

	</body>
</html>