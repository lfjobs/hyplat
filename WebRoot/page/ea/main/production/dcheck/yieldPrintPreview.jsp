<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>合格率打印</title>
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
						&nbsp;合格率测试单
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
				     <th width="120">项目编号</th>
				     <th width="130">产品条码</th>
				     <th width="122">产品名称</th>
				     <th width="122">产品规格</th>
				     <th width="102">考核数量</th>
				     <th width="122">合格数量</th>
				     <th width="150">合格率</th>
				   </tr>
				</thead>
				<tbody>
				<c:forEach  items="${lists}"  var="list">
				
				<tr >
						
						<td><span id="tradecode">${list[1]}</span>
						</td>
						<td><span id="itemNumber">${list[2]}</span></td>
						<td><span id="goodBar">${list[3]}</span></td>

						<td><span id="goodName">${list[4]}</span></td>
						<td><span id="goodStandard">${list[5]}</span></td>
						<td><span id="btnumber" >${list[6]}</span></td>
						<td><span id="percent" >${list[7]}</span></td>
						<td><span id="yield" class="number">${list[8]}</span></td>

					</tr>
				</c:forEach>
				</tbody>
			</table>
			
			
	</body>
</html>
