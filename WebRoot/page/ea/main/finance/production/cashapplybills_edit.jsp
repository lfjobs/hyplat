<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>现金请购明细审批单</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
		-->
		<style type="text/css">
.font {
	font-size: 12px;
}

.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 12px;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
	height: 20px;
}
</style>
	</head>

	<body>
		<div align="center">
			<table width="860px" class="font">
				<tr>
					<td colspan="3" align="center"
						style="font-family: 黑体; font-size: 18px; font-weight: 300">
						现金请购明细审批单
					</td>
				</tr>
				<tr>
					<td align="left" width="40%" height="25px">
						申请公司：
						<span></span>
					</td>
					<td align="left" width="30%">
						申请部门：
						<span></span>
					</td>
					<td align="left" width="30%">
						申请人：
						<span></span>
					</td>
				</tr>
			</table>
			<table width="860px" class="table">
				<thead>
					<tr>
						<td align="center" width="40">
							序号
						</td>
						<td align="center" width="80">
							部门
						</td>
						<td align="center" width="150">
							项目单号
						</td>
						<td align="center" width="80">
							使用责任人
						</td>
						<td align="center" width="120">
							资金需用项目名称
						</td>
						<td align="center" width="60">
							规格
						</td>
						<td align="center" width="60">
							数量
						</td>
						<td align="center" width="60">
							单价
						</td>
						<td align="center" width="80">
							金额
						</td>
						<td align="center" width="100">
							资金需求因素
						</td>
					</tr>
				</thead>
				<tbody>
					<%
						int num = 1;
						int money = 0;
					%>
					<s:iterator value="CashApplyList">
						<tr>
							<td align="center" width="40">
								<span><%=num%></span>
							</td>
							<td align="center" width="80">
								<span>${organizationName}</span>
							</td>
							<td align="center" width="150">
								<span>${journalNum}</span>
							</td>
							<td align="center" width="80">
								<span>${staffName}</span>
							</td>
							<td align="center" width="120">
								<span>${goodsName}</span>
							</td>
							<td align="center" width="60">
								<span>${moneySpent}</span>
							</td>
							<td align="center" width="60">
								<span>${quantity}</span>
							</td>
							<td align="center" width="60">
								<span>${price}</span>
							</td>
							<td align="center" width="80">
								<span>${money}</span>
							</td>
							<td align="center" width="100">
								<span>${boxStandard}</span>
							</td>
						</tr>
						<%
							num++;
						%>
					</s:iterator>
					<tr>
						<td align="center" width="40">

						</td>
						<td align="center" width="80">

						</td>
						<td align="center" width="150">
							合计
						</td>
						<td align="center" width="80">

						</td>
						<td align="center" width="120">

						</td>
						<td align="center" width="60">

						</td>
						<td align="center" width="60">

						</td>
						<td align="center" width="60">

						</td>
						<td align="center" width="80">
							<span>${Money }</span>
						</td>
						<td align="center" width="100">

						</td>
					</tr>
				</tbody>
			</table>
			<table width="860px" class="font">
				<tr>
					<td align="left" width="20%">
						公司经理：
					</td>
					<td align="left" width="20%">
						部门主管：
					</td>
					<td align="left" width="20%">
						财务审核：
					</td>
					<td align="left" width="20%">
						会计：
					</td>
					<td align="left" width="20%">
						出纳：
					</td>
				</tr>
				<tr>
					<td align="left" width="20%">
						总经理：
					</td>
					<td align="left" width="20%">
						总部部门主管：
					</td>
					<td align="left" width="20%">
						财务部审核：
					</td>
					<td align="left" width="20%">
						总会计：
					</td>
					<td align="left" width="20%">
						总出纳：
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
