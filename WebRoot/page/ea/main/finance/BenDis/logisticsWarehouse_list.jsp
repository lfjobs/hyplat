<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>物流出库页面</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script src="<%=basePath%>js/ea/finance/BenDis/logisticsWarehouse_list.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var id="";
		var str="${str}";
		var type=parseInt("${te.cusType}");
	</script>
  </head>
  <body>
 	<div id="fexdiv">
 	<form id="form" name="form" action="" method="post">
	  	<table class="fexlist" id="table">
	  		<thead>
	  			<tr id="titleTr">
	  				<th align="center" width="30">选择</th>
	  				<th align="center" width="100">出库编号</th>
	  				<th align="center" width="100">订单编号</th>
	  				<th align="center" width="70">仓库编号</th>
	  				<th align="center" width="100">仓库名称</th>
	  				<th align="center" width="120">仓库地址</th>
	  				<th align="center" width="100">负责人</th>
	  				<th align="center" width="70">出库时间</th>
	  			</tr>
	  		</thead>
	  		<tbody>
	  			<c:forEach items="${pageForm.list}" var="l">
	  					<tr id="${l[0]}">
	  						<td><input type="radio" name="radio" class="radio"></td>
	  						<td>${l[1]}</td>
	  						<td>${l[2]}</td>
	  						<td>${l[3]}</td>
	  						<td>${l[5]}<input type="hidden" id="potid" value="${l[4]}"></td>
	  						<td>${l[6]}</td>
	  						<td>${l[8]}<input type="hidden" id="staffId" value="${l[7]}"></td>
	  						<td>${l[9]}</td>
	  					</tr>
	  			</c:forEach>
	  		</tbody>
	  	</table>
	  	<input type="submit" name="submit" id="submit" style="display: none;">
	  	<s:token></s:token>
	</form>
  	</div>
  		<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/logware/ea_getHomePageInformationList.jspa?type=01&pageNumber=${pageNumber}">
		</c:param>
	</c:import>
  		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
  </body>
</html>
