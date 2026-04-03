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
		<title>车辆月度违章信息明细统计表</title>
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
			var carviolateID = '';
			var search='${search}';
			var personurl = "";
			var notoken = 0;
			var pNumber = ${pageNumber};
			var carID=parent.carID;
			var token=0;
			var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carviolate_edit.js"></script>
	</head>
	<body>
		<form name="carviolateForm" id="carviolateForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
							<th width="30" align="center" >
								选择
							</th>
							<th width="100" align="center" >
								车牌号
							</th>
							<th width="100" align="center" >
								车型
							</th>
							<th width="100" align="center" >
								发动机号
							</th>
							<th width="200" align="center">
								责任人
							</th>
							<th width="100" align="center">
								违章日期	
							</th>
							<th width="100" align="center">
								违章原因
							</th>
							<th width="100" align="center">
								处理情况
							</th>
							<th width="140" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${carviolateID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${carviolateID}" />
									<input type="hidden" name="carviolateKey" id="carviolateKey"
										value="${roadKey}" />
									<input type="hidden" name="carviolateID" id="carviolateID"
										value="${carviolateID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carviolate.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="violatePeople">${violatePeople}</span>
								</td>
								<td class="td_bg01">
									<span id="violateDate">${fn:substring(violateDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="violateReason">${violateReason}</span>
								</td>
								<td class="td_bg01">
									<span id="treatment1">${treatment=='00'?'未处理':'处理'}</span>
									<span id="treatment" style="display:none">${treatment=='00'?'00':'01'}</span>
								</td>
								<td class="td_bg01">
									<span id="remark">${remark}</span>
									<span id="carviolateID" style="display:none">${carviolateID}</span>
                                 	<span id="carviolateKey"  style="display:none">${carviolateKey}</span>
								</td>

								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carviolate/ea_getCarViolateList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车辆违章信息添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 450px; left: 60%; top: 15%" id="jqModel">
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

							<td style="width:30%;"align="right">
								<span class="xx">*</span>违章日期：
							</td>
							<td>
								<input name="carviolate.violateDate" id="violateDate"
									onfocus="date(this);" size="20"
									class="violateDate isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>违章原因：
							</td>
							<td>
								<input name="carviolate.violateReason" id="violateReason"
									size="30" class="violateReason isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								处理情况：
							</td>
							<td>
								<select name="carviolate.treatment" id="treatment">
									<option value="00">
										未处理
									</option>
									<option value="01">
										处理
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="carviolate.remark" id="remark" size="30" />
								 <input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								
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
		<!-- 车辆违章信息修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 450px; left: 60%; top: 15%" id="jqModelup">
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
							<td align="right">
								违章日期：
							</td>
							<td>
								<input name="carviolate.violateDate"
									value="${carviolate.violateDate }" onfocus="date(this);"
									id="violateDate" style="margin-left: 2px;" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								处理情况：
							</td>
							<td>
								<select name="carviolate.treatment" id="treatment">
									<option id="gc" value="00">
										未处理
									</option>
									<option id="jk" value="01">
										处理
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<div align="right">
									违章原因：
								</div>
							</td>
							<td>
								<input name="carviolate.violateReason"
									value="${carviolate.violateReason}" id="violateReason"
									style="margin-left: 2px;" size="30" />
								<div align="right"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div align="right">
									备注：
								</div>
							</td>
							<td>
								<input name="carviolate.remark" value="${carviolate.remark }"
									id="remark" style="margin-left: 2px;" size="30" />
								<div align="right"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div align="center">
									<input name="carInformation.carID" id="contactUserID"
										type="hidden" class="input" size="20" />
									<input type="button" class="input-button JQuerySubmits"
										style="cursor: pointer; width: 80px;" value="提交" />
									<input name="sub" value="${session_value}" type="hidden" />
									<!-- 代替token-->
									<input type="button" class="input-button JQueryreturns"
										style="cursor: pointer; width: 80px;" value="取消" />
								</div>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		<!-- 车辆违章信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" width="100%" cellpadding="5" cellspacing="5">
					
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carviolate.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carviolate.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carviolate.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carviolate.violatePeople" />
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
