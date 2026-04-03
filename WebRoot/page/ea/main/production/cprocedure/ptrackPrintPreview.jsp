<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>生产跟踪打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body {
	margin-left: 15px;
}

#textDate {
	text-decoration: underline;
}

body td {
	font-size: 11px;
}
</style>
<script type="text/javascript">
		    var basePath = "<%=basePath%>
	";
</script>
</head>
<body>
	<div id="tableprint" align="center">
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="background: #FFFFFF;">
			<tr>
				<td height="25" align="center"
					style="font-weight:bold;font-size:16px">&nbsp;生产跟踪单</td>
			</tr>
		</table>
       <br />
       <br/>
		<table width="900" border="0" cellpadding="0" cellspacing="0"
			style="background: #FFFFFF;" class="table">
			<thead>
				<tr>
					<th width="160" align="center">生产批次号</th>
					<th width="140" align="center">产品编号</th>
					<th width="140" align="center">产品名称</th>
					<th width="150" align="center">生产部门</th>
					<th width="60" align="center">项目负责人</th>
					<th width="160" align="center">跟踪日期</th>
					<th width="140" align="center">生产量</th>
					<th width="140" align="center">跟踪员</th>
					<th width="80" align="center">备注</th>
					<th width="80" align="center">状态</th>
				</tr>

			</thead>
			<tbody class="JQueryflexme">
				<s:iterator value="list">
					<tr style="height: 20px;">
						<td><span id="lot">${lot}</span>
						</td>
						<td><span id="productNumber">${productNumber}</span>
						</td>
						<td><span id="productName">${productName}</span></td>
						<td><span id="productionDepartment">${productionDepartment}</span></td>

						<td><span id="projectLeader">${projectLeader}</span></td>
						<td><span id="trackTime">${trackTime}</span></td>
						<td><span id="throughput">${throughput}</span></td>

						<td><span id="trackman">${trackman}</span></td>
						<td><span id="remark">${remark}</span></td>
						<td>
								     
								   <span id="status" style="display:none;" >${status}</span>
								       <s:if test='status=="00"'>未审核</s:if>
								       <s:if test='status=="01"'>已审核</s:if>
					
								     
								     </td>

					</tr>
				</s:iterator>
			</tbody>
			</body>
</html>
