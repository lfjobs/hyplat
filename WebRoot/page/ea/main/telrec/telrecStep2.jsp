<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<s:head theme="xhtml" />
		<sx:head />

		<title>用户电话记录</title>
		<LINK href="<%=basePath%>/jsp/css/admin.css" type="text/css"
			rel="stylesheet" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>page/ea/main/telrec/js/flexigrid.20110701.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>page/ea/main/telrec/js/telrecStep1.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/jsp/script/customerManager.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>page/ea/main/telrec/js/telrecStep2.css" />
		<script type="text/javascript">
			var basePath="<%=basePath%>";
		var customer_name="<%=request.getParameter("customer_name")%>";
		var user_name="<%=request.getParameter("user_name")%>";
		var startdate="<%=request.getParameter("startdate")%>";
			$(document).ready(function(){
				$("#playWav").click(function(){
				var url="jsp/playwav.jsp?wavpath="+$("input[name='wavpath']:checked").val();
					window.showModalDialog(url,"dialogWidth=500px;dialogHeight=500px");
				});
			})	
		</script>

	</head>
	<body>
		<div style="z-index: 30;" class="main_main" id="main_main">
			<table id="flex1">
				<thead>
					<tr>
						<th width="100" align="center">
							用户
						</th>
						<th width="100" align="center">
							客户
						</th>
						<th width="160" align="center">
							开始时间
						</th>
						<th width="160" align="center">
							结束时间
						</th>
						<th width="400" align="center">
							内容
						</th>
						<th width="120" align="center">
							电话号码
						</th>
						<th width="100" align="center">
							呼入/呼出
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="jqmWindow"
			style="width: 460px; right: 35%;; top: 20%; position: absolute; display: none; z-index: 60;"
			id="jqModelSearch">
			<div class="drag">
				通话记录查询
				<div class="close">
				</div>
			</div>
			<form action="<%=basePath%>page/ea/main/telrec/telrecStep2.jsp"
				method="post">
				<table>
					<tr>
						<td align="right">
							客户名：
						</td>
						<td>
							<input type="text" name="customer_name" id="customer"
								contentEditable="false" />
							&nbsp;
							<input type="button" id="showCustomerName" value="客户名">
						</td>
					</tr>
					<tr>
						<td align="right">
							用户名：
						</td>
						<td>
							<input type="text" name="user_name" id="user"
								contentEditable="false" />
							&nbsp;
							<input type="button" id="showUserName" value="用户名">
						</td>
					</tr>
					<tr>
						<td align="right">
							查询日期：
						</td>
						<td>
							<input type="text" name="startdate" id="startdate"
								contentEditable="false" onfocus="date(this)">
						</td>
					</tr>
					<tr>
						<td align="right">
							<input class="queryclose" type="submit" value="查询" align="right">
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="showCustomerName"
			style="width: 300px; right: 10%; top: 20%; position: absolute; display: none; z-index: 120;"
			id="CustomerName">
			<div class="drag">
				客户名
				<br>
				<select size="5" style="width: 280px;" id="SearchCustomerName">
				</select>
			</div>
		</div>
		<div class="showUserName"
			style="width: 300px; right: 10%; top: 45%; position: absolute; display: none; z-index: 120;"
			id="UserName">
			<div class="drag">
				用户名
				<br>
				<select size="5" style="width: 280px;" id="SearchUserName">
				</select>
			</div>
		</div>
	</body>
</html>
