<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人注册管理</title> 
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>

<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/common/common_word.js"></script>
<script src="<%=basePath%>js/WFJClient/personal.js"></script>
<script type="text/javascript">
    var ghua = "${param.ghua}";
	   var 	staffID = '';
		   var  basePath='<%=basePath%>';           
		   var  pNumber =${pageNumber};  
		   var  search='${search}';
		
		   var  token=0;
		   var notoken = 0;
		   var treeid;
		   var treename;
		   var tree;
		   var saff;
		   var sdate="${sdate}";
		   var edate="${edate}";
		   var identifier='${param.identifier}';
		   var selects =1;
		   //判断flexigrid中的button
		   var flexbutton = '<%=request.getParameter("flexbutton")%>';
		</script>
</head>
<body>
	<form style="display: none;" name="addressForm" id="addressForm"
		method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display: none" />
	</form>

	<div id="main_main" class="main_main">
		<table class="address">
			<thead>
				<tr>
					<th width="30" align="center">选择</th>
					<th width="60" align="center">序号</th>
					<th width="130" align="center">人员编号</th>
					<th width="130" align="center">人员姓名</th>
					<th width="180" align="center">身份证</th>
					<th width="160" align="center">最常联系方式</th>
					<th width="110" align="center">账号</th>
				</tr>
			</thead>
			<tbody>
			<% int number =1; %>
				 <c:forEach var='arr' items="${pageForm.list}" varStatus="number"> 
			<tr id="${arr[0] }">
			
							<td><input type="radio" name="chbox" /></td>  
						 </td>
						<td><span id="id">${number.index+1 }</span>
						</td> <td>
						
							<span id="staffcode">${arr[0]}</span>
							</td>
							<td>
								<span id="staffName">${arr[1]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${arr[2]}</span>
							</td>
							<td>
								<span id="reference">${arr[3]}</span>
							</td>
							
							<td>
								<span id="account">${arr[4]}</span>
							</td>
					</tr>
					<% number++; %>
				</c:forEach>
			</tbody>
			
		</table>
		
		 <c:import url="../../ea/page_navigator.jsp">
				<c:param name="actionPath"                              
											
					value="ea/wfjcustomer/ea_getwfjEcusInfo.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}"></c:param>
			</c:import>
			
	</div>

	<!--搜索窗口 -->
	<div class="jqmWindow " style="width: 270px; right: 35%; top: 20%"
		id="jqModelSearch">
		<form name="postSearchForm" id="postSearchForm" method="post">
			<input type="submit" name="submit" id="submit" style="display: none" /> 
			人员姓名：
			<input name="staff.staffName" />
			 电话：
			<input name="staff.reference" />

			<input type="button" id="tosearch" value=" 查询 " />
			<input name="search" type="hidden" value="search" />
		</form>
		
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	
</body>
</html>
