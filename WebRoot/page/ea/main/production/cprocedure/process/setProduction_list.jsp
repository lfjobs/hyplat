<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产量设置</title>
     <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/setProduction_list.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var productionAmountID="";
		var condition="productionAmount.batchNumber=${productionAmount.batchNumber}&productionAmount.goodsName=${productionAmount.goodsName}&productionAmount.staffName=${productionAmount.staffName}";
		var category="${category}";
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
		  			<th width="130" align="center">生产批次号</th>
		  			<th width="170" align="center">公司名称</th>
		  			<th width="140" align="center">产品名称</th>
		  			<th width="100" align="center">预计生产量</th>
		  			<th width="80" align="center">责任人</th>
		  			<th width="80" align="center">制单人</th>
		  			<th width="100" align="center">制单日期</th>
		  			<th width="60" align="center">状态</th>
			  		</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageForm.list}" var="list">
		  				<tr id="${list.productionAmountID}">
		  					<td><input type="radio" name="radio" value="${list.productionAmountID}" class="radio"></td>
		  					<td>${list.batchNumber}</td>
		  					<td>${list.companyName}</td>
		  					<td>${list.goodsName}</td>
		  					<td>${list.amount}</td>
		  					<td>${list.staffName}</td>
		  					<td>${list.singleName}</td>
		  					<td>${list.singleDate}</td>
		  					<td>${list.status=='00'?'草稿':'已提交'}</td>
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
			value="ea/setpro/ea_listPage.jspa?pageNumber=${pageNumber}&productionAmount.batchNumber=${productionAmount.batchNumber}&productionAmount.goodsName=${productionAmount.goodsName}&productionAmount.staffName=${productionAmount.staffName}">
		</c:param>
	</c:import>
  </body>
</html>
