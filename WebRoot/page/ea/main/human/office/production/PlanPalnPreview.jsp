<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>年度招聘计划表</title>
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
				<td height="30" align="center">
					<span class="STYLE1">年度招聘计划表</span>
				</td>
			</tr>
			<tr>
				<td height="20" align="right">
					制表日期：<span id="year" style="text-decoration: underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>年
							<span id="month" style="text-decoration: underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>月
							<span id="date" style="text-decoration: underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>日
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
					岗位
				</th>
				<th width="60" align="center">
					部门
				</th>
				<th width="60" align="center">
					责任人
				</th>
				<th width="60" align="center">
					起时间
				</th>
				<th width="60" align="center">
					止时间
				</th>
				<th width="40" align="center">
					现有人数
				</th>
				<th width="40" align="center">
					拟增人数
				</th>
				<th width="100" align="center">
					增加原因
				</th>
				<th width="40" align="center">
					拟减人数
				</th>
				<th width="100" align="center">
					减员原因
				</th>
				<th width="60" align="center">
					拟录用人数
				</th>
				<th width="100" align="center">
					拟招聘渠道
				</th>
			</tr>
			<c:forEach var="entity" items="${listBasic}" varStatus="step">
			<tr>
				<td align="center" height="30">
					${step.index+1 }
				</td>
				<td align="center">
					${entity[0] }
				</td>
				<td align="center">
					${entity[1] }
				</td>
				<td align="center">
					${entity[2] }
				</td>
				<td align="center">
					${entity[3] }
				</td>
				<td align="center">
					${entity[4] }
				</td>
				<td align="center">
					${entity[5] }
					<c:set value="${result + entity[5]}" var="result"></c:set>
				</td>
				<td align="center">
					${entity[6] }
					<c:set value="${result1 + entity[6]}" var="result1"></c:set>
				</td>
				<td align="center">
					${entity[7] }
				</td>
				<td align="center">
					${entity[8] }
					<c:set value="${result2 + entity[8]}" var="result2"></c:set>
				</td>
				<td align="center">
					${entity[9] }
				</td>
				<td align="center">
					${entity[10] }
					<c:set value="${result3 + entity[10]}" var="result3"></c:set>
				</td>
				<td align="center">
					${entity[11] }
				</td>
			</tr>
			</c:forEach>
			<tr align="center">
				<td  height="30">合计</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>${result}</td>
				<td>${result1}</td>
				<td></td>
				<td>${result2}</td>
				<td></td>
				<td>${result3}</td>
				<td></td>
			</tr>
			<tr>
				<td align="center" height="50" colspan="2" rowspan="2">总经理审批</td>
				<td colspan="5" height="30" style="border-bottom:0px solid #ffffff;">&nbsp;</td>
				<td align="center" colspan="2" rowspan="2">人力资源部负责人</td>
				<td colspan="4" style="border-bottom:0px solid #ffffff;">&nbsp;</td>
			</tr>
			<tr>
				<td height="20" colspan="5" style="padding-left:100px;border-top:0px solid #ffffff;">日期：</td>
				<td colspan="4" style="padding-left:100px;border-top:0px solid #ffffff;">日期：</td>
			</tr>
		</table>
	</body>
</html>