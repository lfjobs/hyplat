<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>招聘渠道打印</title>
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
	<body
		>
		<table id="content" width="800px" align="center" cellpadding="0" cellspacing="0"
			class="table">
			<tr>
				<td height="32" colspan="11" align="center">
					<span class="STYLE1">${companyname}———招聘渠道管理</span>
				</td>
			</tr>
			<tr>
				<th height="20" align="center" width="40">
					序号
				</th>
				<th width="100" align="center">
					类别
				</th>
				<th width="100" align="center">
					名称
				</th>
				<th width="100" align="center">
					网址
				</th>
				<th width="220" align="center">
					详细地址
				</th>
				<th width="100" align="center">
					固定电话
				</th>
				<th width="100" align="center">
					乘车路线
				</th>
				<th width="100" align="center">
					联系人
				</th>
				<th width="100" align="center">
					联系方式
				</th>
				<th width="100" align="center">
					联系部门
				</th>
				<th width="100" align="center">
					备注
				</th>
			</tr>
			<c:forEach var="entity" items="${channelList}" varStatus="step">
			<tr>
				<td align="center" height="25">
					${step.index+1 }
				</td>
				<td align="center">
					${entity.sorts }
				</td>
				<td align="center">
					${entity.channelname }
				</td>
				<td align="center">
					${entity.url }
				</td>
				<td align="center">
					${entity.fullAddress }
				</td>
				<td align="center">
					${entity.tel }
				</td>
				<td align="center">
					${entity.carroute }
				</td>
				<td align="center">
					${entity.contactman }
				</td>
				<td align="center">
					${entity.contactway }
				</td>
				<td align="center">
					${entity.contactdept }
				</td>
				<td align="center">
					${entity.note }
				</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>