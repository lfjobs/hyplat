<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>归档信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script
	src="<%=basePath%>js/ea/driving/signup/driving_student_archiveInfor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var drivingAllInformationID = '';
   var basePath = '<%=basePath%>';
	var staffID = '${dtDrivingAllInformation.staffID}';
	var notoken = 0;
	var relationID = '${dtDrivingAllInformation.relationID}';
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>

<body>
	<form name="contactForm" id="contactForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<s:token></s:token>
		<div id="main_main" class="main_main">
			<table class="contact">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="100" align="center">归档号</th>
						<th width="80" align="center">芯片号</th>
						<th width="80" align="center">归档时间</th>
						<th width="80" align="center">责任人</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator value="beanList">
						<tr class="td_bg01 saveAjax trclass"  >
							<td class="td_bg01"><input type="radio" name="a"
								class="JQuerypersonvalue" />
							</td>
							<td class="td_bg01"><span id="archiveCode">${archiveCode}</span>
							</td>
							<td class="td_bg01"><span id="chipid">${chipid}</span>
							</td>
							<td class="td_bg01"><span id="intime">${fn:substring(intime,0,19)}</span>
							</td>
							<td class="td_bg01"><span id="outusername">${outusername}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<script type="text/javascript">
		$(function() {
			setTimeout(function() {
				$("div.bDiv").css(
						{
							"height" : parent.document
									.getElementById("mainframe11").offsetHeight
									- 57 + "px"
						});
			}, 100);
			$(window)
					.resize(
							function() {
								setTimeout(
										function() {
											$("div.bDiv")
													.css(
															{
																"height" : parent.document
																		.getElementById("mainframe11").offsetHeight
																		- 57
																		+ "px"
															});
										}, 100);
							});
		});
	</script>
</body>
</html>