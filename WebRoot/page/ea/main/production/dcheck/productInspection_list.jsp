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
    
    <title>生产检验管理</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/dcheck/productInspection_list.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="";
		var condition="utboundOrderVo.journalnum=${utboundOrderVo.journalnum}&utboundOrderVo.goodsname=${utboundOrderVo.goodsname}&utboundOrderVo.staffName=${utboundOrderVo.staffName}";
		var fiveClear="${fiveClear}";
	</script>
  </head>
  <body>
    <div  id="main_main" class="main_main"> 	
	    <div id="fexdiv">
		  	<table class="fexlist">
		  	<thead>
		  		<tr>
		  			<th width="40" align="center">选择</th>
		  			<th width="130" align="center">检验批次号</th>
		  			<th width="130" align="center">产品名称</th>
		  			<th width="110" align="center">产品编号</th>
		  			<th width="90" align="center">生产数量</th>
		  			<th width="73" align="center">规格</th>
		  			<th width="70" align="center">单价</th>
		  			<th width="100" align="center">合格数量</th>
		  			<th width="100" align="center">不合格数量</th>
		  			<th width="100" align="center">检验人</th>
		  			<th width="100" align="center">检验时间</th>
		  			<th width="100" align="center">状态</th>
			  		</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageForm.list}" var="l">
		  				<tr id="${l[1]}">
		  					<td><input type="radio" name="radio" value="${l[0]}" class="radio"></td>
		  					<td>${l[2]}</td>
		  					<td>${l[3]}</td>
		  					<td>${l[4]}</td>
		  					<td>${l[7]}</td>
		  					<td>${l[5]}</td>
		  					<td>${l[6]}</td>
		  					<td>${l[8]}</td>
		  					<td>${l[9]}</td>
		  					<td>${l[10]}</td>
		  					<td>${l[11]}</td>
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
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/assembly/ea_getProductOrderList.jspa?pageNumber=${pageNumber}&productionAmount.batchNumber=${productionAmount.batchNumber}&productionAmount.goodsName=${productionAmount.goodsName}&productionAmount.staffName=${productionAmount.staffName}&fiveClear=${fiveClear}">
		</c:param>
	</c:import>
  </body>
</html>
