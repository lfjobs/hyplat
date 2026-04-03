<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>考核测试打印</title>
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
		body td{
			font-size:11px;
		
		}
		</style>
		<script type="text/javascript">
		    var basePath = "<%=basePath%>";
		</script>
	</head>
	<body>
		<div id="tableprint" align="center">
		   <table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight:bold;font-size:16px">
						&nbsp;考核测试单
					</td>
				</tr>
			</table>
			   <br/>
			   <br/>
               <table width="900" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<thead>
				   <tr style="text-align: center;">
				     <th width="120">行业分类</th>
				     <th width="120">产品编号</th>
				     <th width="130">产品条码</th>
				     <th width="122">产品规格</th>
				     <th width="122">产品名称</th>
				     <th width="122">成本单价</th>
				     <th width="120">
				     <c:if test="${status=='00'}">考核数量</c:if>
					 <c:if test="${status=='01'}">合格数量</c:if>
					 <c:if test="${status=='02'}">不合格数量</c:if>
					 </th>
				     <th width="122">审核人</th>
				     <th width="150">审核人部门</th>
				     <th width="122">审核时间</th>
				     <th width="122">审核状态</th>
				   </tr>
				</thead>
				<tbody>
				<s:iterator value="list" var="l">
				
				<tr >
						
						<td><span id="tradecode">${l[0]}</span>
						</td>
						<td><span id="itemNumber">${l[1]}</span></td>
						<td><span id="goodBar">${l[2]}</span></td>

						<td><span id="goodName">${l[3]}</span></td>
						<td><span id="goodStandard">${l[4]}</span></td>
						<td><span id="price">${l[5]}</span></td>
						<td><span id="btnumber">
						        <s:if test="status=='00'">${l[6]}</s:if>
						        <s:if test="status=='01'">${l[7]}</s:if>
						        <s:if test="status=='02'">${l[8]}</s:if>
						        </span></td>
						<td><span id="auditor">${l[9]}</span></td>
						<td><span id="organizationName">${l[10]}</span></td>
						<td><span id="dcheckTime">${l[11]}</span></td>
						<td><span id="status" style="display:none;">${status}</span>
						     <s:if test="status=='00'">待考核</s:if>
						       <s:if test="status=='01'">通过</s:if>
						       <s:if test="status=='02'">未通过</s:if>
						</td>

					</tr>
				</s:iterator>
				</tbody>
			</table>
			
			
	</body>
</html>
