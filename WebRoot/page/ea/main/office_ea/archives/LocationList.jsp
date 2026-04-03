<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>档案存储位置管理</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>


		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/office_ea/archives/LocationList.js"></script>


		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var locationid= "";
         var type = '${type}';
		</script>

		<style type="text/css">
.table td {
	white-space: nowrap;
	border-right: none;
}

a {
	text-decoration: none;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>
						<th width="90" align="center">
							位置名称
						</th>
						<th width="150" align="center">
							创建时间
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${locationid}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${locationid}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="locationname">${locationname}</span>
							</td>
							<td>
								<span id="createdate">${fn:substring(createdate,0,19)}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/location/ea_getLocationList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>

		<!--添加窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelAdd">
			<form name="postAddForm" id="postAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div id="title">
						添加
					</div>
					<div class="close">
					</div>
				</div>
				<center>
				<table width="100%" cellpadding="5"   cellspacing="10" id="addTable">
					<tr>
						<td align="right">
							位置名称：
						</td>
						<td align="left">
							<input type="text" name="archivelocation.locationname"
								id="locationname" />
							<input type="hidden" name="archivelocation.locationid"
								id="locationid" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">

							<input type="button" class="input-button" id="toSubmit"
								value=" 确定 " />
							<input type="button" class="input-button close" id="toCancel"
								value=" 关闭 " />
						</td>
					</tr>
				</table>
				</center>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="100%" cellpadding="5" cellspacing="10"
					id="cataffSearchTable">
					<tr>
						<td align="right">
							位置名称：
						</td>
						<td align="left">
							<input type="text" name="archivelocation.locationname" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
							<input name="type" type="hidden" value="${type}" />
						</td>

					</tr>
				</table>
			</form>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>