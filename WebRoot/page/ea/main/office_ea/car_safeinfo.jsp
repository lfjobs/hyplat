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
		<title>车辆安全信息</title>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript">
		 var basePath = '<%=basePath%>';
		 var carSafeID = '';
		 var search='${search}';
		 var personurl = "";
		 var notoken = 0;
		 var pNumber = ${pageNumber};
		 var carID=parent.carID;
		 var token=0;
		 var type='${type}';
		 
		</script>
	</head>
	<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/car_safe_info.js"></script>
	<body>
		<form name="carSafeinformationForm" id="carSafeinformationForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="60" align="center" >
								车牌号
							</th>
							<th width="60" align="center" >
								车型
							</th>
							<th width="100" align="center" >
								发动机号
							</th>
							<th width="200" align="center">
								责任人
							</th>
							<th width="60" align="center">
								事故时间
							</th>
							<th width="80" align="center">
								事故经过
							</th>
							<th width="60" align="center">
								事故责任人
							</th>
							<th width="60" align="center">
								直接损失金额
							</th>
							<th width="60" align="center">
								间接损失金额
							</th>
							<th width="60" align="center">
								处理时间
							</th>
							<th width="60" align="center">
								处理责任人
							</th>
							<th width="60" align="center">
								处理意见
							</th>
							<th width="60" align="center">
								事故档案盒
							</th>
							<th width="60" align="center">
								凭证号
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${carSafeID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${carSafeID}" />
									<input type="hidden" name="carSafeKey" id="carSafeKey"
										value="${carSafeKey}" />
									<input type="hidden" name="carSafeID" id="carSafeID"
										value="${carSafeID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carsafeinformation.carID}"
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
									<span id="carPeople">${carPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="accidentTime">${fn:substring(accidentTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="accidentpass">${accidentpass}</span>
								</td>
								<td class="td_bg01">
									<span id="accidentdutypeople">${accidentdutypeople}</span>
								</td>
								<td class="td_bg01">
									<span id="directlossmoney">${directlossmoney}</span>
								</td>
								<td class="td_bg01">
									<span id="indirectlossmoney">${indirectlossmoney}</span>
								</td>
								<td class="td_bg01">
									<span id="disposeTime">${fn:substring(disposeTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="disposepeople">${disposepeople}</span>
								</td>
								
								<td class="td_bg01">
									<span id="disposeidea">${disposeidea}</span>
								</td>
								<td class="td_bg01">
									<span id="accidentbox">${accidentbox}</span>
								</td>
								<td class="td_bg01">
									<span id="certificatenum">${certificatenum}</span>
									<span id="carSafeID" style="display:none">${carSafeID}</span>
                                 	<span id="carSafeKey"  style="display:none">${carSafeKey}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carsafeinformation/ea_getCarsafeinformationList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>		
            </div>
		</form>
		<!-- 车辆安全信息的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 700px; left: 65%; top: 10%" id="jqModel">
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

							<td  style="width:15%;"align="right">
								<span class="xx">*</span>事故时间：
							</td>
							<td style="width:33%;">
								<input name="carsafeinformation.accidentTime" id="accidentTime"
									onfocus="date(this);" class="accidentTime isremove put3"
									size="20" />
							</td>
							<td style="width:19%;" align="right">
								事故经过：
							</td>
							<td>
								<input name="carsafeinformation.accidentpass" id="accidentpass"
									size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>直接损失金额：
							</td>
							<td>
								<input name="carsafeinformation.directlossmoney"
									id="directlossmoney" class="directlossmoney isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>间接损失金额：
							</td>
							<td>
								<input name="carsafeinformation.indirectlossmoney"
									id="indirectlossmoney" class="indirectlossmoney isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								处理意见：
							</td>
							<td>
								<input name="carsafeinformation.disposeidea" id="disposeidea"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>事故责任人：
							</td>
							<td>
								<input name="carsafeinformation.accidentdutypeople"
									id="accidentdutypeople"
									class="accidentdutypeople isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>处理责任人：
							</td>
							<td>
								<input name="carsafeinformation.disposepeople"
									id="disposepeople" class="disposepeople isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>处理时间：
							</td>
							<td>
								<input name="carsafeinformation.disposeTime" id="disposeTime"
									onfocus="date(this);" class="disposeTime isremove put3 "
									size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								事故档案盒：
							</td>
							<td>
								<input name="carsafeinformation.accidentbox" id="accidentbox"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>凭证号：
							</td>
							<td>
								<input name="carsafeinformation.certificatenum"
									id="certificatenum" class="certificatenum isremove put3"
									size="20" />
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
		<!-- 车辆安全信息的修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 700px; left: 65%; top: 10%" id="jqModelup">
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
								事故时间：
							</td>
							<td>
								<input name="carsafeinformation.accidentTime"
									value="${ carsafeinformation.accidentTime}" id="accidentTime"
									onfocus="date(this);" size="20" />
							</td>
							<td align="right">
								事故经过：
							</td>
							<td>
								<input name="carsafeinformation.accidentpass"
									value="${carsafeinformation.accidentpass }" id="accidentpass"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								直接损失金额：
							</td>
							<td>
								<input name="carsafeinformation.directlossmoney"
									value="${carsafeinformation.directlossmoney }"
									id="directlossmoney" size="20" />
							</td>
							<td align="right">
								间接损失金额：
							</td>
							<td>
								<input name="carsafeinformation.indirectlossmoney"
									value="${carsafeinformation.indirectlossmoney }"
									id="indirectlossmoney" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								处理意见：
							</td>
							<td>
								<input name="carsafeinformation.disposeidea"
									value="${carsafeinformation.disposeidea }" id="disposeidea"
									size="20" />
							</td>
							<td align="right">
								事故责任人：
							</td>
							<td>
								<input name="carsafeinformation.accidentdutypeople"
									value="${carsafeinformation.accidentdutypeople }"
									id="accidentdutypeople" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								处理责任人：
							</td>
							<td>
								<input name="carsafeinformation.disposepeople"
									value="${ carsafeinformation.disposepeople}" id="disposepeople"
									size="20" />
							</td>
							<td align="right">
								处理时间：
							</td>
							<td>
								<input name="carsafeinformation.disposeTime"
									value="${carsafeinformation.disposeTime }" id="disposeTime"
									onfocus="date(this);" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								事故档案盒：
							</td>
							<td>
								<input name="carsafeinformation.accidentbox"
									value="${carsafeinformation.accidentbox }" id="accidentbox"
									size="20" />
							</td>
							<td align="right">
								凭证号：
							</td>
							<td>
								<input name="carsafeinformation.certificatenum"
									value="${ carsafeinformation.certificatenum}"
									id="certificatenum" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input name="carsafeinformation.carSafeID" id="carSafeID"
									type="hidden" class="input" size="20" />
								<input name="carsafeinformation.carSafeKey" id="carSafeKey"
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
		
		<!-- 车辆安全信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
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
						<td align="right">
							<input name="carsafeinformation.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carsafeinformation.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carsafeinformation.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carsafeinformation.carPeople" />
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
