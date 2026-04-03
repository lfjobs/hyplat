<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆状态管理</title>
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/carStatus.js"></script>
		<style type="text/css">
.xx {
	color: #FF0000;
	margin-right: 2px;
}
</style>
		<script type="text/javascript">
   var token = 0;
   var select = 1;
   var stID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var carID = parent.carID;
   var carNum = parent.carNum;
   var staffID = parent.staffID;
   var notoken = 0;
   var search = '${search}';
   var carInformation = '${carInformation.carNum}';
   var type='${type}';
</script>

	</head>
	<body>
		<div id="main_main" class="main_main">
			<table class="carstatus">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="100" align="center">
							车牌号
						</th>
						<th width="70" align="center">
							车辆状态
						</th>
						<th width="200" align="center">
							操作时间
						</th>
						<th width="140" align="center">
							责任人
						</th>
						<th width="100" align="center">
							责任人部门
						</th>
						<th width="170" align="center">
							责任人公司
						</th>
					</tr>
				</thead>

				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${stID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${stID}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="carNum">${carNum}</span>
								<span id="stID" style="display: none">${stID}</span>
							</td>
							<td class="td_bg01">
								<span id="statusname">${statusname}</span>
							</td>
							<td class="td_bg01">
								<span id="operateDate">${fn:substring(operateDate, 0,
									19)}</span>
							</td>
							<td class="td_bg01">
								<span id="staffName">${staffName}(${staffCode})</span>
							</td>
							<td class="td_bg01">
								<span id="organizationName">${organizationName}</span>
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyName}</span>
							</td>

						</tr>
						<%
							number += 1;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/carstatus/ea_getCarStatusList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}$type=${type}">
				</c:param>
			</c:import>

		</div>

		<!-- 车辆状态添加修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 400px; left: 80%; top: 15%;" id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆状态管理
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">

						<tr>
							<td style="width: 30%;" align="right">
								车辆状态：
							</td>
							<td>
								<select name="carStatus.status">
									<option value="00">
										未使用
									</option>
									<option value="01">
										已使用
									</option>
									<option value="10">
										下线
									</option>
								</select>
							</td>
						</tr>

						<tr>
							<td colspan="6" align="center">
								<input name="carStatus.stID" id="stIDs" type="hidden" />
								<input name="carInformation.carID" id="numCarID" type="hidden" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>


		<!-- 车辆状态信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carStatus.carNum" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="carInformation.carID" type="hidden" id="carIDs" />
					<input name="type" type="hidden" value="${type}" />
				</div>
			</div>
		</form>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
