<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.CAccount"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	CAccount account = (CAccount) session.getAttribute("account");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>电子印章实例</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
		-->
		<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/signApp/SignManager.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/signApp/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/signApp/signatureApp.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript">
			var signparam={
			"SignListUrl":"<%=basePath%>ea/signmanager/querysignmanager.jspa",
			"SignExURL":"<%=basePath%>/js/signApp/SignEx.jsp",
			"accountid":"<%=account.getAccountID()%>",
			"basePath":"<%=basePath%>"
			};
			var signmanagerid ="<%=request.getParameter("signmanagerid")%>";
			var signid ="<%=request.getParameter("signid")%>";
			var relationtable ="<%=request.getParameter("relationtable")%>";
			var signstat ="<%=request.getParameter("signstat")%>";
			var starttime ="<%=request.getParameter("starttime")%>";
			var endtime ="<%=request.getParameter("endtime")%>";
		</script>
	</head>

	<body>

		<div id="main" style="top: 10px; position: absolute; z-index: 30">
			<table class="flexi" id="flexi">
			</table>
		</div>
		<div class="jqmWindow" id="jqModelSearch"
			style="width: 500px; right: 40%;; top: 15%; position: absolute; display: none; z-index: 120;">
			<div class="drag">
				电子印章实例查询
				<div class="close">
				</div>
			</div>
			<form name="querygoods" action="<%=basePath%>js/signApp/query.jsp" method="post">
				<table>
					<tr>
						<td align="right">
							用户：
						</td>
						<td>
							<input type="text" name="signmanagerid" id="signmanagerid" />
						</td>
						<td align="right">
							印章：
						</td>
						<td>
							<input type="text" name="signid" id="signid" />
						</td>
					</tr>
					<tr>
						<td align="right">
							关联的表：
						</td>
						<td>
							<input type="text" name="relationtable" id="relationtable" />
						</td>
						<td align="right">
							印章状态：
						</td>
						<td>
							<!-- 
								<select style="width:152" id="signstat">
									<option value="">请选择</option>							
									<option value="true">已审批</option>
									<option value="false">已报废</option>
								</select>
							 -->
							<input type="text" name="signstat" id="signstat" />
						</td>
					</tr>
					<tr>
						<td align="right">
							盖章日期：
						</td>
						<td>
							<input type="text" name="starttime" id="starttime"
								onfocus="date(this)" />
						</td>
						<td align="right">
							查询日期：
						</td>
						<td>
							<input type="text" name="endtime" id="endtime"
								onfocus="date(this)" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<input class="queryclose" type="submit" value="查询" align="right" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
