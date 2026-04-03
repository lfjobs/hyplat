<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>产品包装设计统计表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<style type="text/css">
		body,th {
			font-size: 12px;
		}
		
		.table {
			border-collapse: collapse;
			border: 1px solid;
			font-size: 12px;
		}
		
		.table1 {
			border-collapse: collapse;
			border: 0px solid #ffffff;
			font-size: 12px;
			margin-top: 20px;
		}
		
		.table th {
			border: 1px solid;
		}
		
		.table td {
			border: 1px solid;
			color: #333;
		}
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		.STYLE1 {
			font-size: 16px;
			font-weight: bold;
		}
		</style>
	</head>
	<body>
		<table id="content" width="800px" align="center" cellpadding="0" cellspacing="0"
			class="table1">
			<tr>
				<td height="50" align="center" colspan="9">
					<span class="STYLE1">产品包装设计价格定位汇总统计表</span>
				</td>
			</tr>
			<tr>
				<td width="20px">
				&nbsp;
				</td>
				<td width="80px">
					公司：
				</td>
				<td width="200px" align="left" >
				${obj[1] }
				</td>
				<td width="80px">
					部门：
				</td>
				<td width="100px" align="left" >
				${obj[2] }
				</td>
				<td>
					责任人：
				</td>
				<td align="left" >
				${ printname}
				</td>
				<td height="20" align="right">
					制表日期：
				</td>
				<td width="100px" >
				${printDate}
				</td>
			</tr>
		</table>
		<table id="content" width="800px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<th height="30" align="center" width="40">
					序号
				</th>
				<th width="60" align="center">
					品名编号
				</th>
				<th width="60" align="center">
					品名名称
				</th>
				<th width="60" align="center">
					单位
				</th>
				<th width="40" align="center">
					规格
				</th>
				<th width="60" align="center">
					重量
				</th>
				<th width="60" align="center">
					数量
				</th>
				<th width="40" align="center">
					价格类型
				</th>
				<th width="40" align="center">
					单价
				</th>
				<th width="100" align="center">
					金额
				</th>
			</tr>
			<c:forEach var='arr' items="${printList}" varStatus="step">
			<tr>
				<td align="center" height="30">
					${step.index+1 }
				</td>
				<td align="center">
					<span id=" ">${arr[5] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[6] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[7] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[8] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[10] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[9] }</span>
				</td>
				<td align="center">
					<span id=" ">${arr[15] }</span> 
				</td>
				<td align="center">
					<span id=" ">${arr[16] }</span> 元
				</td>
				<td align="center">
					<span id=" ">${arr[17] }</span> 元
				</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>