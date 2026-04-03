<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登记证信息列表</title>
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
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/driving/registrationlist.js"></script>
<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var carCylinderId = "";
			var token = 0;
			var select = 1;
			var pNumber = ${pageNumber}; 
			var notoken = 0;
			var search = '${search}';
</script>
</head>
<body>

	<form name="addressForm" id="addressForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none" />

		<div id="main_main" class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th width="50" align="center">选择</th>
						<th width="80" align="center">车主</th>
						<th width="80" align="center">车牌号码</th>
						<th width="80" align="center">登记证号</th>
						<th width="100" align="center">联系电话</th>
						<th width="80" align="center">气瓶安装数量</th>
						<th width="100" align="center">发动机号</th>
						<th width="120" align="center">车架号</th>
					</tr>
				</thead>
				<tbody>
					
					<s:iterator var="item" value="pageForm.list" status="index">
						<tr id="${item[1]}" title="${item[21]}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"/></td>
							</td>
							<td><span id="staffname">${item[34]}</span></td>
							<td><span id="licensenumber">${item[21]}</span></td>
							<td><span id="cylinderModel">${item[23]}</span></td>
							<td><span id="concatphone">${item[35]}</span></td>
							<td><span id="certificateNumber">${item[29]}</span>
							</td>
							<td><span id="manufactureCompany">${item[27]}</span>
							</td>
							<td><span id="manufactureCompany">${item[26]}</span>
								<span id="carCylinderId" style="display: none;">${item[1]}</span>
							</td>
						</tr>
						
					</s:iterator> 
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/productregister/ea_getlistregistrationlist.jspa?showtype=registration"></c:param>
			</c:import>
		</div>
	</form>
	<!-- 查询窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 40%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="300" id="carlinderSearchTable">
					<tr>
						<td align="right">
							气瓶编号：
						</td>
						<td >
							<input name="carCylinderInformation.cylinderNum" />
						</td>
					</tr>
					<tr>
						<td  align="right">
							登记证号：
						</td>
						<td >
							<input name="carCylinderInformation.certificateNumber" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
