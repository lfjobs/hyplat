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
		<title>车辆使用信息</title>
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var conditionID='';
		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID=parent.carID;
		var token=0;
		var type='${type}';
		</script>
	</head>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/employeeuserd.js"></script>
	<body>
	<form name="carEmployConditionForm" id="carEmployConditionForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="70" align="center">
								选择
							</th>
							<th width="60" align="center">
								车牌号
							</th>
							<th width="60" align="center">
								车型
							</th>
							<th width="60" align="center">
								车架号
							</th>
							<th width="100" align="center">
								发动机号
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="60" align="center">
								车辆当前情况
							</th>
							<th width="60" align="center">
								买卖时间
							</th>
							<th width="60" align="center">
								成交金额
							</th>
							<th width="60" align="center">
								买方姓名
							</th>
							<th width="60" align="center">
								买方电话
							</th>
							<th width="60" align="center">
								凭证号	
							</th>
							<th width="60" align="center">
								移动单号
							</th>
								<th width="80" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${conditionID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${conditionID}" />
									<input type="hidden" name="conditionkey" id="conditionkey"
										value="${conditionkey}" />
									<input type="hidden" name="conditionID" id="conditionID"
										value="${conditionID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${employcondition.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="frameNum">${frameNum}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carPeople">${carPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="currentsituation">${currentsituation}</span>
								</td>
								<td class="td_bg01">
									<span id="conectTime">${fn:substring(conectTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="assignmentMoney">${assignmentMoney}</span>
								</td>
								<td class="td_bg01">
									<span id="buyName">${buyName}</span>
								</td>
								<td class="td_bg01">
									<span id="buytelphone">${buytelphone}</span>
								</td>
								<td class="td_bg01">
									<span id="certificatenum">${certificatenum}</span>
								</td>
								<td class="td_bg01">
									<span id="trackNumber">${trackNumber}</span>
								</td>
								<td class="td_bg01">
									<span id="remark">${remark}</span>
									<span id="conditionkey" style="display:none">${conditionkey}</span>
                                 	<span id="conditionID"  style="display:none">${conditionID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/employcondition/ea_getemployconditionList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
			</form>
			<!-- 车辆使用信息的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 54%; top: 15%" id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">
						<tr>
							<td style="width:13%;"align="right">
								车辆当前情况：
							</td>
							<td style="width:33%;">
								<input name="employcondition.currentsituation"
									id="currentsituation" size="20" />
							</td>
							<td style="width:19%;" align="right">
								<span class="xx">*</span>买卖时间：
							</td>
							<td>
								<input name="employcondition.conectTime" id="conectTime"
									onfocus="date(this);" class="conectTime isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>成交金额：
							</td>
							<td>
								<input name="employcondition.assignmentMoney"
									id="assignmentMoney" class="assignmentMoney isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>买方姓名：
							</td>
							<td>
								<input name="employcondition.buyName" id="buyName"
									class="buyName isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>买方电话：
							</td>
							<td>
								<input name="employcondition.buytelphone" id="buytelphone"
									class="buytelphone isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>凭证号：
							</td>
							<td>
								<input name="employcondition.certificatenum" id="certificatenum"
									class="certificatenum isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>移动单号：
							</td>
							<td>
								<input name="employcondition.trackNumber" id="trackNumber"
									class="trackNumber isremove put3" size="20" />
							</td>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="employcondition.remark" id="remark" size="20" />
								<input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
							
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
		<!-- 修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 54%; top: 15%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 13%" align="right">
								车辆当前情况：
							</td>
							<td style="width: 33%">
								<input name="employcondition.currentsituation"
									id="currentsituation" size="10" />
							</td>
							<td style="width: 19%" align="right">
								买卖时间：
							</td>
							<td>
								<input name="employcondition.conectTime" id="conectTime"
									onfocus="date(this);" class="conectTime isremove put3"
									size="10" />
							</td>
						</tr>
						<tr>
							<td align="right">
								成交金额：
							</td>
							<td>
								<input name="employcondition.assignmentMoney"
									id="assignmentMoney" class="assignmentMoney isremove put3"
									size="10" />
							</td>
							<td align="right">
								买方姓名：
							</td>
							<td>
								<input name="employcondition.buyName" id="buyName"
									class="buyName isremove put3" size="10" />
							</td>
						</tr>
						<tr>
							<td align="right">
								买方电话：
							</td>
							<td>
								<input name="employcondition.buytelphone" id="buytelphone"
									class="buytelphone isremove put3" size="10" />
							</td>
							<td align="right">
								凭证号：
							</td>
							<td>
								<input name="employcondition.certificatenum" id="certificatenum"
									class="certificatenum isremove put3" size="10" />
							</td>
						</tr>
						<tr>
							<td align="right">
								移动单号：
							</td>
							<td>
								<input name="employcondition.trackNumber" id="trackNumber"
									class="trackNumber isremove put3" size="10" />
							</td>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="employcondition.remark" id="remark" size="10" />
							</td>
						</tr>
						<tr>
						<tr>
							<td colspan="4" align="center">
								<input name="employcondition.conditionkey" id="conditionkey"
									type="hidden" class="input" size="20" />
								<input name="employcondition.conditionID" id="conditionID"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQuerySubmits"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturns"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>

		<!-- 车辆使用管理信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" width="100%" cellpadding="5"
					cellspacing="5">
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="employcondition.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="employcondition.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="employcondition.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="employcondition.carPeople" />
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
	</body>
</html>
