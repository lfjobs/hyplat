<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目列表打印</title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 18px;
}

.td {
	border: #cccccc 1px solid;
}
</style>
</head>
<body>
	<table width="800" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>项目列表</h2>
			</td>
		</tr>
	</table>
	<table width="100%" align="center" class="table" style="font-size:16px;"
		cellpadding="10">

			<thead>
				<tr>
					<th width="40" align="center">
						序号
					</th>
					<th width="100" align="center">
						项目编号
					</th>
					<th width="150" align="center">
						项目名称
					</th>
					<th width="100" align="center">
						开始日期
					</th>
					<th width="100" align="center">
						结束日期
					</th>
 
					<th width="200" align="center">
						公司名称
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="70" align="center">
						负责人
					</th>
					<th width="70" align="center">
						创建人
					</th>
					<th width="120" align="center">
						创建日期
					</th>
				</tr>
			</thead>
            <tbody>			
				<s:iterator value="projectlist">
					<tr id="${proID}">
						<td align="center">
						
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${proID}" />
						
						</td>
						<td align="center">
							<span id="projectCode">${projectCode}</span>
						</td>
						<td align="center">
							<span id="projectName">${projectName}</span>
						</td>
						
						<td align="center">
							<span id="startDate">${fn:substring(startDate,0,10)}</span>
						</td>
						<td align="center">
							<span id="endDate">${fn:substring(endDate,0,10)}</span>
						</td>
						<td align="center">
							<span id="companyName">${companyName}</span>
						</td>
						<td align="center">
							<span id="organizationName">${organizationName}</span>
						</td>
						<td align="center">
							<span id="staffName">${staffName}</span>
						</td>
						<td align="center">
							<span id="createName">${createName}</span>
						</td>

						<td align="center">
							<span id="createDate">${fn:substring(createDate,0,19)}</span>
						</td>
						
					</tr>
				</s:iterator>
			</tbody>

	</table>
	
</body>
</html>
