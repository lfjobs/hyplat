<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>金币入库单</title>
    
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script language="javascript" type="text/javascript" 
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script src="<%=basePath%>js/ea/finance/BenDis/goldInStorage_list.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var id="";
	</script>

  </head>
  
  <body>
    <div id="fexdiv">
 	<form id="form" name="form" action="" method="post">
	  	<table class="fexlist" id="table">
	  		<thead>
	  			<tr id="titleTr">
	  				<th align="center" width="30">选择</th>
	  				<th align="center" width="100">用户所属公司</th>
	  				<th align="center" width="70">用户名称</th>
	  				<th align="center" width="70">用户编号</th>
	  				<th align="center" width="100">产品所属公司</th>
	  				<th align="center" width="80">产品编号</th>
	  				<th align="center" width="100">产品名称</th>
	  				<th align="center" width="60">产品数量</th>
	  				<th align="center" width="60">收款金额</th>
	  				<th align="center" width="80">金币（佣金）</th>
	  				<th align="center" width="70">会员类别</th>
	  				<th align="center" width="70">凭证号</th>
	  				<th align="center" width="70">仓库名称</th>
	  				<th align="center" width="70">仓库地址</th>
	  				<th align="center" width="70">制单人</th>
	  				<th align="center" width="70">制单时间</th>
	  			</tr>
	  		</thead>
	  		<tbody>
	  			<c:forEach items="${pageForm.list}" var="l">
	  					<tr id="${l[0]}"  class="bodyTr">
	  						<td><input type="radio" name="radio" class="radio"></td>
	  						<td></td>
	  						<td>${l[1]}</td>
	  						<td>${l[2]}</td>
	  						<td>${l[3]}</td>
	  						<td>${l[4]}</td>
	  						<td>${l[5]}</td>
	  						<td>${l[6]}</td>
	  						<td>${l[7]}</td>
	  						<td>${l[7]}</td>
	  						<td>${l[8]}</td>
	  						<td>${l[9]}</td>
	  						<td>${l[10]}</td>
	  						<td>${l[11]}</td>
	  						<td>${l[13]}</td>
	  						<td>${l[12]}</td>
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
			value="ea/goldStock/ea_getGoldOfTheHomePage.jspa?type=00&pageNumber=${pageNumber}">
		</c:param>
	</c:import>
  		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
  </body>
</html>
