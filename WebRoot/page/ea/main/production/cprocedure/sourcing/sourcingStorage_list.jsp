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
    
    <title>生产采购入库管理</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/sourcing/sourcingStorage_list.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var cashierBillsID="";
		var type="${type}";
		var fiveClear="${fiveClear}";
		var temporaryJournalNum="${cashierbills.journalNum}";
		var temporaryStaffName="${cashierbills.inputName}";
		var temporaryStatus="${cashierbills.status}";
		var temporarySDate="${sDate}",temporaryEDate="${eDate}";
	</script>
  </head>
  
  <body>
    <div  id="main_main" class="main_main"> 	
	    <div id="fexdiv">
		  	<table class="fexlist">
		  	<thead>
		  		<tr>
		  			<th width="40" align="center">选择</th>
		  			<th width="140" align="center">单据编号</th>
		  			<th width="155" align="center">公司名称</th>
		  			<th width="80" align="center">单据类型</th>
		  			<th width="120" align="center">部门</th>
		  			<th width="120" align="center">收货人</th>
		  			<th width="120" align="center">制单人</th>
		  			<th width="170" align="center">付款方式</th>
		  			<th width="100" align="center">单据时间</th>
					<th width="90" align="center">单据状态</th>
			  		</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageForm.list}" var="list">
		  				<tr id="${list[0]}">
		  					<td><input type="radio" name="radio" value="${list[0]}" class="radio"></td>
		  					<td>${list[1]}</td>
		  					<td>${list[2]}</td>
		  					<td>${list[3]}</td>
		  					<td>${list[4]}</td>
		  					<td>${list[5]}</td>
		  					<td>${list[6]}</td>
		  					<td>${list[7]}</td>
		  					<td>${list[8]}</td>
		  					<td>${list[9]=='15'?'已入库':list[9]=='22'?'草稿':list[9]=='26'?'审核中':'已收货'}</td>
		  				</tr>
		  			</c:forEach>
		  		</tbody>
		  	</table>
		</div>
	</div>
	<form id="forms" method="post" name="forms">
		<input type="submit" id="submits" name="submits" style="display: none;">
	</form>
	<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
	<c:import url="../../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/sourcingsto/ea_storageList.jspa?pageNumber=${pageNumber}&cashierbills.journalNum=${cashierbills.journalNum}&cashierbills.inputName=${cashierbills.inputName}&cashierbills.status=${cashierbills.status}&sDate=${sDate}&eDate=${eDate}&fiveClear=${fiveClear}">
		</c:param>
	</c:import>
  </body>
</html>
