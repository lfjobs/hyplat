<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>关联业务员</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/InvestDeviceBind/addYeWuYuan.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />

<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<link rel="stylesheet"
	href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var companyID = '${account.companyID}';
	var companyName = '${account.companyName}';
	var notoken = 0;
	var pageNumber = '${pageNumber}';
	var dbId = '${dbId}';
	var token;
</script>
</head>

<body>
	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>关联业务员</strong></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="main_main">
		<table class="JQueryflexme" id="deviceBind">
			<thead>
				<tr class="tablewith">
					<th width="30" align="center">
						选择
					</th>
					<th width="30" align="center">
						编号
					</th>
					<th width="50" align="center">
						姓名
					</th>
					<th width="100" align="center">
						会员类型
					</th>
					<th width="100" align="center">
					     微分金账号
					</th>
				</tr>
			</thead>
			<tbody>
				<% int number=1; %>
				<c:forEach items="${pageForm.list }" var="x">
					<tr id="${x[4] }">
					    <td>
							<input type="radio" name="deviceBindId" class="JQuerypersonvalue"
								value="${x[4] }" />
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="name">${x[0] }</span>
						</td>
						<td>
							<span id="vipType">${x[1] }</span>
						</td>
						<td>
							<span id="wfjAccount">${x[2] }</span>
						</td>
						<%-- <td>
							<a href="<%=basePath%>ea/devicebind/ea_delDeviceBindStaff.jspa?dbsid=${x[4] }&dbId=${dbId}" style="text-decoration:none;">删除</a>
						</td> --%>
					</tr>
					
					<%
						number++;
					%>
				</c:forEach>
			</tbody> 
		</table>
		<c:import url="../../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/devicebind/ea_selGlStaff.jspa?pageNumber=${pageNumber}&dbId=${dbId }">
			</c:param>
		</c:import>
	</div>
	
	<div id="jqmWindow2" class="jqmWindow"
		style="width: 70%; height: 500px; color: #333; absolute; display: none; left: 10%; top: 10%">
		<div class="drag">
			关联业务员查询
			<div class="close close2"></div>
		</div>
		<table width="100%" style="background: #eff" cellpadding="2">
			<tr>
				<!-- <td align="left">查询范围<input type="button"
					class="input-button" id="search" value="查询" /></td> -->
				<td align="right"><!-- <input type="button" class="input-button"
					id="checked" value="全选/全不选" /> --> <input type="button"
					class="input-button" id="searchAdd" value="添加" /> <input
					type="hidden" id="searchtype" value="" /></td>
			</tr>
		</table>
		<table width="100%" border="1">
			<tr>
				<td valign="top" style="width: 20%; background: #FFFFFF;">
					<div id="tree1"
						style="height: 420px; width: 300px; overflow: auto; border: 2px solid #ccc;">
						<ul id="browser" style="width: 180px;" class="filetree">
							<ul>
								<li><span class="folder">关联业务员</span>
									<ul>
										<li><a id="zzyg" target="admin1"><span
												class="folder">在职员工</span> </a>
											<ul id="orgid"></ul></li>
										<li><a id='yys'
											target="admin1"><span class="file">运营商会员</span> </a></li>
									</ul>
								</li>
							</ul>
						</ul>
					</div>
				</td>
				<td><iframe name="searchiFrame" id="searchiFrame" src=""
						width="100%" height="428px" allowTransparency="true"></iframe></td>
			<tr>
		</table>
	</div>
	
	<div class="jqmWindow" id="jqModel"
		style="display: none; width: 250px; height: 100px; right: 30%; top: 10%;"
		id="jqModelPosition">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			提示
			<div class="close"></div>
		</div>
		<center>
		<p class="tip">tip</p>
		<input type="button" class="confirm input-button" value=" 确定 " /> <input
			type="button" class="input-button close" value=" 取消   " /> </center>
	</div>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
