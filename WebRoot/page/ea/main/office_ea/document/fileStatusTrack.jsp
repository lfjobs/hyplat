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
<title>文件状态跟踪表</title>

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
				<h2>文件状态跟踪表</h2></td>
		</tr>
	</table>
	<table width="800" align="center" class="table" style="font-size:16px;"
		cellpadding="10">
		<thead>
				<tr>
					<th width="50" align="center">序号</th>
					<th width="100" align="center">公文编号</th>

					<th width="300" align="center">文件标题</th>

					<th width="70" align="center">拟稿</th>
					<th width="70" align="center">审批</th>
					<th width="70" align="center">盖章</th>
					<th width="70" align="center">分发</th>
					<th width="70" align="center">阅读</th>
					<th width="70" align="center">归档</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="doclist">
					<tr class="docs" id="${docId}${docid}">
						<td class="td_bg01" align="center"><span><%=number%></span></td>

						<td class="td_bg01" align="center"><span id="docNum">${docNum}${docnum}</span></td>

						<td class="td_bg01" align="center"><span id="title">${title}</span></td>
						<td class="td_bg01" align="center"><s:if test='status=="I"'>拟稿</s:if></td>

						<td class="td_bg01" align="center"><s:if test='status=="S"||status=="T"'>审批</s:if>
							<s:if test='status=="R"'>驳回</s:if> <s:if test='status=="U"'>不予通过</s:if>
						</td>
						<td class="td_bg01" align="center"><s:if test='status=="A"'>盖章</s:if></td>
						<td class="td_bg01" align="center"><s:if test='status=="P"'>分发</s:if></td>
						<td class="td_bg01" align="center"><s:if test='status=="O"'>阅读</s:if></td>
						<td class="td_bg01" align="center"><s:if test='status=="G"'>归档</s:if></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		

	</table>
	

	</table>
</body>
</html>
