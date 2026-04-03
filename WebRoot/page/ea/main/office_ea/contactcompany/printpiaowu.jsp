<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>打印预览票务</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
			.tablestyle{
			margin-top:3%;
			width:70%;
			text-align: center;
			border-collapse: collapse;
			border: 1px solid #000000;
			font-size: 10px;
			}
			.table th {
				border: 1px solid #000000;
				color: #000000;
			}
			
			.table td {
				border: 1px solid #000000;
				color: #000000;
			}
			
			body,td,th {
				font-size: 11px;
			}
		</style>
		<script type="text/javascript">
		 var ccompanyID = '';
		 var basePath='<%=basePath%>';           
		 var orgname ='${orginazname}';  
		 var token = 0 ;  
		 var personurl='';
		 var notoken = 0;
		 var retoken=0;
		 var select=1;
</script>
	</head>
	<body>
		<table class="tablestyle" align="center"  border = "1px" cellpadding="0" cellspacing="0"> 
			<tr>
			<th colspan="14">
				 ${orginazname}
			</th>
			</tr>
			<tr>
				<td>序号</td>
				<td>票据类型</td>
				<td>班次/车次</td>
				<td>出发地点</td>
				<td>到达地点</td>
				<td>出发日期</td>
				<td>到达日期</td>
				<td>出发时间</td>
				<td>到达时间</td>
				<td>标准价</td>
				<td>折扣价</td>
				<td>航舱等级</td>
				<td>席别</td>
				<td>票据号</td>
			</tr>
					<%
						int number = 1;
					%>
			<c:forEach var="str"  items="${beans}">
					
					<tr>
							<td><span><%=number%></span></td>
							<td><span>${str.ticketType}</span></td>
							<td><span>${str.classOrtrains}</span></td>
							<td><span>${str.startplace}</span></td>
							<td><span>${str.endplace}</span></td>
							<td><span>${fn:substring(str.departure,0,10)}</span></td>
							<td><span>${fn:substring(str.arrivalDate,0,10)}</span></td>
							<td><span>${str.departureTime}</span></td>
							<td><span>${str.arrivalTime}</span></td>
							<td><span>${str.normalPrice}</span></td>
							<td><span>${str.discount}</span></td>
							<td><span>${str.airTankLevel}</span></td>
							<td><span>${str.positionLamp}</span></td>
							<td><span>${str.billNumber}</span></td>
					</tr>
						<%
							number++;
						%>
				</c:forEach>
		</table>
	</body>
</html>
