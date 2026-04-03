<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>企业印章使用管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/corporationcode/SignManager.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>
		<div style="z-index: 30;">
			<table class="address">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="120" align="center">
							印章名称
						</th>
						<th width="100" align="center">
							盖章人员
						</th>
						<th width="160" align="center">
							时间日期
						</th>
						<th width="100" align="center">
							印章状态
						</th>
						<th width="300" align="center">
							备注说明
						</th>
					</tr>
					<tr>

					</tr>
				</thead>
			</table>
		</div>
		<div class="jqmWindow" id="showSignManager"
			style="width: 480px; height: 220px; top: 30%; left: 30%; z-index: 120; display: none">
			<div class="drag">
				印章使用管理
				<div class="close"></div>
			</div>
			<table align="center">
				<tr>
					<td>
						盖章人员：
						<input type="text" />
						&nbsp;&nbsp; 印章名称：
						<input type="text" />
						<br />
						时间日期：
						<input type="text" onfocus="date(this);"  />
							&nbsp;&nbsp; 印章位置： 
						<input type="text" />
						<br />
						关联表名：
						<input type="text" />
						&nbsp;&nbsp; 关联属性：
						<input type="text" />
						<br />
						印章状态：
						<input type="text" />
						&nbsp;&nbsp; 备注说明：
						<input type="text" />
						<br />
						<p align="center">
							<input type="button" value="确定" />
							<input type="button" value="取消" />
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="jqmWindow" id="querySignManger" style="display: none">
			<div class="drag">
				印章查询
				<div class="close"></div>
			</div>
			印章名称：
			<input type="text" />
			<br />
			印章状态：
			<input type="text" />
			<br />
			关联表名：
			<input type="text" />
			<br />
			开始时间：
			<input type="text" onfocus="date(this);" />
			<br />
			结束时间：
			<input type="text" onfocus="date(this);" />
			<br />
			<input type="button" value="查询" />
			<input type="button" value="取消" />
		</div>
		<div class="jqmWindow" id="queryRelation" style="display: none">
			<div class="drag">
				印章使用次数查询
				<div class="close"></div>
			</div>
			印章名称：
			<input type="text" />
			<br />
			印章状态：
			<input type="text"/>
			<br />
			关联属性：
			<input type="text" />
			<br />
			开始时间：
			<input type="text" onfocus="date(this);" />
			<br />
			结束时间：
			<input type="text" onfocus="date(this);" />
			<br />
			<input type="button" value="查询" />
			<input type="button" value="取消" />
		</div>

	</body>
</html>
