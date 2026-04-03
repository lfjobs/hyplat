<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产订单管理</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/productionAssembly_order_list.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="",category="${category}",fiveClear="${fiveClear}";
		var condition="utboundOrderVo.journalnum=${utboundOrderVo.journalnum}&utboundOrderVo.goodsname=${utboundOrderVo.goodsname}&utboundOrderVo.staffName=${utboundOrderVo.staffName}";
	</script>
  </head>
  <body>
    <div  id="main_main" class="main_main"> 	
	    <div id="fexdiv">
		  	<table class="fexlist">
		  	<thead>
		  		<tr>
		  			<th width="40" align="center">选择</th>
		  			<th width="130" align="center">订单编号</th>
		  			<th width="130" align="center">产品名称</th>
		  			<th width="110" align="center">产品编号</th>
		  			<th width="90" align="center">产品品牌</th>
		  			<th width="73" align="center">往来个人</th>
		  			<th width="70" align="center">单价</th>
		  			<th width="70" align="center">数量</th>
		  			<th width="100" align="center">金额</th>
		  			<th width="60" align="center">状态</th>
			  		</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageForm.list}" var="l">
		  				<tr id="${l[1]}">
		  					<td><input type="radio" name="radio" value="${l[0]}" class="radio"></td>
		  					<td>${l[2]}</td>
		  					<td>${l[3]}</td>
		  					<td>${l[4]}</td>
		  					<td>${l[5]}</td>
		  					<td>${l[6]}</td>
		  					<td>${l[7]}</td>
		  					<td>${l[8]}</td>
		  					<td>${l[9]}</td>
		  					<td>${l[10]=='28'?'生产中':'未生产'}</td>
		  				</tr>
		  			</c:forEach>
		  		</tbody>
		  	</table>
		</div>
	</div>
  <form method="post" id="forms" name="forms" >
  <input type="submit" id="submits" name="submits" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
  </form>
	<c:import url="../../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/assembly/ea_getProductOrderList.jspa?pageNumber=${pageNumber}&productionAmount.batchNumber=${productionAmount.batchNumber}&productionAmount.goodsName=${productionAmount.goodsName}&productionAmount.staffName=${productionAmount.staffName}&fiveClear=${fiveClear}">
		</c:param>
	</c:import>
  </body>
</html>
