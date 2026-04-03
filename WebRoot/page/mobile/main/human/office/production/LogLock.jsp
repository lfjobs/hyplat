<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>时间加锁管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript">
   var token = 0;
   var select = 1;
   var logLockID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;

</script>
		<script src="<%=basePath%>js/ea/human/office/production/loglock.js"></script>
		<style>
table {
	font-size: 10px;
}

.in {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div { /*width:auto;*/
	width: 100%;
	height: 200px;
	position: absolute; <%--
	overFlow-x: hidden;
	overFlow-y: scroll; --%>
	overFlow-x: hidden;
	overFlow-y: auto;
}
</style>
	</head>
	<body>
		<div>
			<table>
				<s:iterator value="pageForm.list">
					<tr>
						<td width="156">
							<table width="156" class="in" border="1">
								<tr>
									<td width="42">
										开始日期
									</td>
									<td width="98">
										<span id="startDate" class="datas">${fn:substring(startDate,0,10)}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td width="42">
										结束日期
									</td>
									<td width="98">
										<span id="endDate" class="datas">${fn:substring(endDate,0,10)}</span>
										<input type="hidden" name="logLockkey" style="display: none"
											value="${logLockkey}" />
										<input type="hidden" name="logLockID" style="display: none"
											value="${logLockID}" />
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										加锁账号
									</td>
									<td>
										<span id="accountName">${accountName}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										加锁时间
									</td>
									<td>
										<span id="lockDate">${fn:substring(lockDate,0,10)}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										加锁状态
									</td>
									<td>
										<span id="st">${status=='00'?'已加锁':'已作废'}</span>
										<span id="status" style="display: none">${status}</span>
										<span></span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</body>
</html>
