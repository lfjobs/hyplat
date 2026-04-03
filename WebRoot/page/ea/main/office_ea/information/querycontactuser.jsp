<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>往来个人管理</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/contactuser/Telcontactuser.js"></script>
		<script type="text/javascript">
			var relationID = '';
			var basePath='<%=basePath%>';
			var pNumber = 10;
			var token = 0;
			var notoken = 0;
			var search='${search}';
		</script>
	</head>
	<body>
		<div style="font-size:12px;">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询往来个人
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							姓名：
						</td>
						<td width="261">
							<input name="contactUser.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							编号：
						</td>
						<td width="261">
							<input name="contactUser.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人身份证号：
						</td>
						<td>
							<input name="contactUser.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人地址：
						</td>
						<td>
							<input name="contactUser.staffAddress" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人电话：
						</td>
						<td>
							<input name="contactUser.reference" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来关系：
						</td>
						<td>
							<s:select list="codeRelationList" listKey="codeValue"
								id="relation" listValue="codeValue" name="contactUser.relation"
								theme="simple" headerKey="" headerValue="全部"></s:select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>