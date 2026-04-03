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
		<title>车辆CNG信息</title>
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
		var cngID = '';
   		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID=parent.carID;
		var token=0;
		var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carCNG_edit.js"></script>
	</head>
	<body>
		<form name="carCNGForm" id="carCNGForm" method="post">
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
							<th width="100" align="center">
								改装厂名称
							</th>
							<th width="100" align="center">
								改装厂电话
							</th>
							<th width="100" align="center">
								改装厂联系人
							</th>
							<th width="100" align="center">
								改装企业名称
							</th>
							<th width="100" align="center">
								改装许可证
							</th>
							<th width="100" align="center">
								改装时间
							</th>
							<th width="100" align="center">
								改装前燃料种类
							</th>
							<th width="50" align="center">
								改装后燃料种类
							</th>
							<th width="60" align="center">
								气瓶型号
							</th>
							<th width="60" align="center">
								改装合格证号
							</th>
							<th width="60" align="center">
								气瓶改装合格证号
							</th>
							<th width="60" align="center">
								气瓶定期检验登记证号
							</th>
							<th width="60" align="center">
								气瓶检验时间
							</th>
							<th width="60" align="center">
								气瓶检测厂名称
							</th>
							<th width="60" align="center">
								气瓶检测厂电话
							</th>
							<th width="60" align="center">
								气瓶检测厂责任人
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
							<tr id="${cngID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${cngID}" />
									<input type="hidden" name="cngKey" id="cngKey"
										value="${cngKey}" />
									<input type="hidden" name="cngID" id="cngID" value="${cngID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carCNG.carID}"
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
									<span id="refitName">${refitName}</span>
								</td>
								<td class="td_bg01">
									<span id="refitPhone">${refitPhone}</span>
								</td>
								<td class="td_bg01">
									<span id="refitContact">${refitContact}</span>
								</td>
								<td class="td_bg01">
									<span id="refitCompany">${refitCompany}</span>
								</td>
								<td class="td_bg01">
									<span id="refitLicenseCode">${refitLicenseCode} </span>
								</td>
								<td class="td_bg01">
									<span id="refitDate">${fn:substring(refitDate , 0, 10)} </span>
								</td>
								<td class="td_bg01">
									<span id="refitAgoFuel">${refitAgoFuel}</span>
								</td>
								<td class="td_bg01">
									<span id="refitAfterFuel">${refitAfterFuel}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderType">${cylinderType}</span>
								</td>
								<td class="td_bg01">
									<span id="refitEligibleCode">${refitEligibleCode}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderEligibleCode">${cylinderEligibleCode} </span>
								</td>
								<td class="td_bg01">
									<span id="cylinderDetectCode">${cylinderDetectCode}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderDetectDate">${fn:substring(cylinderDetectDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderDetectName">${cylinderDetectName}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderDetectPhone">${cylinderDetectPhone}</span>
								</td>
								<td class="td_bg01">
									<span id="cylinderDetectContact">${cylinderDetectContact}  </span>
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<span id="cngKey" style="display:none">${cngKey}</span>
                                 	<span id="cngID"  style="display:none">${cngID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carcng/ea_getCarCNGList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车辆CNG的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 700px; left: 60%; top: 8%" id="jqModel">
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

							<td  style="width:17%;"align="right">
								<span class="xx">*</span>改装厂名称：
							</td>
							<td style="width:33%;">
								<input name="carCNG.refitName" id="refitName"
									class="refitName isremove put3" size="20" />
							</td>
							<td  style="width:18%;"align="right">
								<span class="xx">*</span>改装厂电话：
							</td>
							<td>
								<input name="carCNG.refitPhone" id="refitPhone"
									class="refitPhone isremove put3" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>改装厂联系人：
							</td>
							<td>
								<input name="carCNG.refitContact" id="refitContact"
									class="refitContact isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>改装企业名称：
							</td>
							<td>
								<input name="carCNG.refitCompany" id="refitCompany"
									class="refitCompany isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>改装许可证：
							</td>
							<td>
								<input name="carCNG.refitLicenseCode" id="refitLicenseCode"
									size="20" class="refitLicenseCode isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>改装时间：
							</td>
							<td>
								<input name="carCNG.refitDate" id="refitDate"
									class="refitDate isremove put3" onfocus="date(this);" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>改装前燃料种类：
							</td>
							<td>
								<input name="carCNG.refitAgoFuel" id="refitAgoFuel" size="20"
									class="refitAgoFuel isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>改装后燃料种类：
							</td>
							<td>
								<input name="carCNG.refitAfterFuel" id="refitAfterFuel"
									class="refitAfterFuel isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>气瓶型号：
							</td>
							<td>
								<input name="carCNG.cylinderType" id="cylinderType"
									class="cylinderType isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>改装合格证号：
							</td>
							<td>
								<input name="carCNG.refitEligibleCode" id="refitEligibleCode"
									class="refitEligibleCode isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>气瓶改装合格证号：
							</td>
							<td>
								<input name="carCNG.cylinderEligibleCode"
									id="cylinderEligibleCode"
									class="cylinderEligibleCode isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>气瓶定期检验登记证号：
							</td>
							<td>
								<input name="carCNG.cylinderDetectCode" id="cylinderDetectCode"
									class="cylinderDetectCode isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>气瓶检验时间：
							</td>
							<td>
								<input name="carCNG.cylinderDetectDate" id="cylinderDetectDate"
									class="cylinderDetectDate isremove put3" onfocus="date(this);"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>气瓶检测厂名称：
							</td>
							<td>
								<input name="carCNG.cylinderDetectName" id="cylinderDetectName"
									class="cylinderDetectName isremove put3" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>气瓶检测厂电话：
							</td>
							<td>
								<input name="carCNG.cylinderDetectPhone"
									id="cylinderDetectPhone"
									class="cylinderDetectPhone isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>气瓶检测厂责任人：
							</td>
							<td>
								<input name="carCNG.cylinderDetectContact"
									id="cylinderDetectContact"
									class="cylinderDetectContact isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="1" align="right">
								备注：
							</td>
							<td colspan="3">
								<input name="carCNG.remarks" id="remarks" size="50" />
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
		<!-- 车辆cng修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 700px; left: 60%; top: 8%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆cng管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td align="right">
								改装厂名称：
							</td>
							<td>
								<input name="carCNG.refitName" id="refitName"
									value="${carCNG.refitName}" size="20" />
							</td>
							<td align="right">
								改装厂电话：
							</td>
							<td>
								<input name="carCNG.refitPhone" id="refitPhone"
									value="${carCNG.refitPhone}" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								改装厂联系人：
							</td>
							<td>
								<input name="carCNG.refitContact" id="refitContact"
									value="${carCNG.refitContact} " size="20" />
							</td>
							<td align="right">
								改装企业名称：
							</td>
							<td>
								<input name="carCNG.refitCompany" id="refitCompany"
									value="${ carCNG.refitCompany}" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								改装许可证：
							</td>
							<td>
								<input name="carCNG.refitLicenseCode" id="refitLicenseCode"
									value="${ carCNG.refitLicenseCode}" size="20" />
							</td>

							<td align="right">
								改装时间：
							</td>
							<td>
								<input name="carCNG.refitDate" id="refitDate"
									value="${carCNG.refitDate }" onfocus="date(this);" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								改装前燃料种类：
							</td>
							<td>
								<input name="carCNG.refitAgoFuel" id="refitAgoFuel"
									value="${carCNG.refitAgoFuel }" size="20" />
							</td>
							<td align="right">
								改装后燃料种类：
							</td>
							<td>
								<input name="carCNG.refitAfterFuel" id="refitAfterFuel"
									value="${carCNG.refitAfterFuel }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								气瓶型号：
							</td>
							<td>
								<input name="carCNG.cylinderType" id="cylinderType"
									value="${ carCNG.cylinderType}" size="20" />
							</td>
							<td align="right">
								改装合格证号：
							</td>
							<td>
								<input name="carCNG.refitEligibleCode" id="refitEligibleCode"
									value="${carCNG.refitEligibleCode }" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								气瓶改装合格证号：
							</td>
							<td>
								<input name="carCNG.cylinderEligibleCode"
									id="cylinderEligibleCode"
									value="${ carCNG.cylinderEligibleCode}" size="20" />
							</td>
							<td align="right">
								气瓶定期检验登记证号：
							</td>
							<td>
								<input name="carCNG.cylinderDetectCode" id="cylinderDetectCode"
									value="${ carCNG.cylinderDetectCode}" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								气瓶检验时间：
							</td>
							<td>
								<input name="carCNG.cylinderDetectDate" id="cylinderDetectDate"
									value="${ carCNG.cylinderDetectDate}" onfocus="date(this);"
									size="10" />
							</td>
							<td align="right">
								气瓶检测厂名称：
							</td>
							<td>
								<input name="carCNG.cylinderDetectName" id="cylinderDetectName"
									value="${carCNG.cylinderDetectName }" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								气瓶检测厂电话：
							</td>
							<td>
								<input name="carCNG.cylinderDetectPhone"
									id="cylinderDetectPhone" value="${carCNG.cylinderDetectPhone }"
									size="20" />
							</td>
							<td align="right">
								气瓶检测厂责任人：
							</td>
							<td>
								<input name="carCNG.cylinderDetectContact"
									id="cylinderDetectContact"
									value="${carCNG.cylinderDetectContact }" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								备注：
							</td>
							<td colspan="3">
								<input name="carCNG.remarks" id="remarks" size="40" />
								<input name="carInformation.carID" type="hidden" id="carIDs" />
							</td>
						</tr>
						<tr>
						<tr>
							<td colspan="4" align="center">
								<input name="carCNG.cngID" id="cngID" type="hidden"
									class="input" size="20" />
								<input name="carCNG.cngKey" id="cngKey" type="hidden"
									class="input" size="20" />
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
	
		<!-- 车辆cng信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
				
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carCNG.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carCNG.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carCNG.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carCNG.carPeople" />
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
