<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<s:head theme="xhtml" />
		<sx:head />
		<title>客户工作阶段统计</title>
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>
	<body>
		<b>客户来电阶段统计</b>
		<hr>
		<s:form action="webCustomerTotal" method="post">
			<sx:datetimepicker label="起始时间" name="starttime"></sx:datetimepicker>
			<sx:datetimepicker label="起始时间" name="endtime"></sx:datetimepicker>
			<td class="tdLabel">
				<label for="widget_113710983" class="label">
					报表形式:
				</label>
			</td>
			<td>
				<select name="style">
					<option value="report">
						报表
					</option>
					<option value="histogram">
						柱状图
					</option>
				</select>
			</td>
			<s:submit></s:submit>
		</s:form>
		<br>
	</body>
</html>