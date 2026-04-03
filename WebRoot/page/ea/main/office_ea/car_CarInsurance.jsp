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
		<title>车辆保险信息</title>
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
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var insuranceID = '';
   		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID=parent.carID;
		var token=0;
		var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carInsurance_edit.js"></script>
	</head>
	<body>
		<form name="carInsuranceForm" id="carInsuranceForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
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
								保险单号
							</th>
							<th width="80" align="center">
								保险公司名称
							</th>
							<th width="80" align="center">
								险种名称
							</th>
							<th width="80" align="center">
								被保险人身份证号码/组织机构代码
							</th>
							<th width="60" align="center">
								使用性质
							</th>
							<th width="60" align="center">
								厂牌型号
							</th>
							<th width="50" align="center">
								核定载客
							</th>
							<th width="60" align="center">
								起始时间
							</th>
							<th width="60" align="center">
								终止时间
							</th>
							<th width="60" align="center">
								保险金额
							</th>
							<th width="60" align="center">
								保险单位电话
							</th>
							<th width="60" align="center">
								卖保险人
							</th>
							<th width="60" align="center">
								卖保险人电话
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
							<tr id="${insuranceID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${insuranceID}" />
									<input type="hidden" name="insuranceKey" id="insuranceKey"
										value="${insuranceKey}" />
									<input type="hidden" name="insuranceID" id="insuranceID"
										value="${insuranceID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carInsurance.carID}"
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
									<span id="insuranceCode">${insuranceCode}</span>
								</td>
								<td class="td_bg01">
									<span id="insuranceComName">${insuranceComName}</span>
								</td>
								<td class="td_bg01">
									<span id="insuranceName">${insuranceName}</span>
								</td>
								<td class="td_bg01">
									<span id="purchaseCode">${purchaseCode}</span>
								</td>
								<td class="td_bg01">
									<span id="useNature">${useNature}</span>
								</td>
								<td class="td_bg01">
									<span id="brandModel">${brandModel}</span>
								</td>
								<td class="td_bg01">
									<span id="passengerNum">${passengerNum}</span>
								</td>
								<td class="td_bg01">
									<span id="startTime">${fn:substring(startTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="endTime">${fn:substring(endTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="insuranceAmount">${insuranceAmount}</span>
								</td>
								<td class="td_bg01">
									<span id="insurancePhone">${insurancePhone}</span>
								</td>
								<td class="td_bg01">
									<span id="sellInsurer">${sellInsurer}</span>
								</td>
								<td class="td_bg01">
									<span id="sellInsurerPhone">${sellInsurerPhone}</span>
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<span id="insuranceID" style="display:none">${insuranceID}</span>
                                 	<span id="insuranceKey"  style="display:none">${insuranceKey}</span>
								</td>

								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carinsurance/ea_getCarInsuranceList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <iframe src="" style="height:0;" name="main" width="99%" scrolling="no"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
			</div>
		</form>
		<!-- 车辆保险信息的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1" style="width: 680px; left: 60%; top: 15%"
			id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆保险管理添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5" cellspacing="5">

						<tr>
							<td style="width:13%;" align="right">
								<span class="xx">*</span>保险单号：
							</td>
							<td style="width:33%;">
								<input name="carInsurance.insuranceCode" id="insuranceCode"
									class="insuranceCode isremove put3" size="20" />
							</td>
							<td style="width:19%;" align="right">
								<span class="xx">*</span>保险公司名称：
							</td>
							<td>
								<input name="carInsurance.insuranceComName"
									id="insuranceComName" class="insuranceComName isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>险种名称：
							</td>
							<td>
								<input name="carInsurance.insuranceName" id="insuranceName"
									class="insuranceName isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>被保险人身份证号：
							</td>
							<td>
								<input name="carInsurance.purchaseCode" id="purchaseCode"
									class="purchaseCode isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								使用性质：
							</td>
							<td>
								<input name="carInsurance.useNature" id="useNature" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>厂牌型号：
							</td>
							<td>
								<input name="carInsurance.brandModel" id="brandModel"
									class="brandModel isremove put3" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>起始时间：
							</td>
							<td>
								<input name="carInsurance.startTime" id="startTime"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end\')}'})"
									class="input ss " size="20" />
							<input type="hidden" id="start"/>
				            <input type="hidden" id="end"/>
							</td>
							<td align="right">
								<span class="xx">*</span>终止时间：
							</td>
							<td>
								<input name="carInsurance.endTime" id="endTime"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"
									class="input ss " size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>保险金额：
							</td>
							<td>
								<input name="carInsurance.insuranceAmount" id="insuranceAmount"
									class="insuranceAmount isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>保险单位电话：
							</td>
							<td>
								<input name="carInsurance.insurancePhone" id="insurancePhone"
									class="insurancePhone isremove put3" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>卖保险人：
							</td>
							<td>
								<input name="carInsurance.sellInsurer" id="sellInsurer"
									class="sellInsurer isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>卖保险人电话：
							</td>
							<td>
								<input name="carInsurance.sellInsurerPhone"
									id="sellInsurerPhone" class="sellInsurerPhone isremove put3"
									size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								核定载客：
							</td>
							<td>
								<input name="carInsurance.passengerNum" id="passengerNum"
									size="20" />
							</td>
							<td align="right">
								备注：
							</td>
							<td >
								<input name="carInsurance.remarks" id="remarks" size="20" />
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
		<!-- 车辆保险的修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 60%; top: 15%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆保险管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 13%;" align="right">
								保险单号：
							</td>
							<td style="width: 33%;">
								<input name="carInsurance.insuranceCode"
									value="${carInsurance.insuranceCode}" id="insuranceCode"
									size="20" />
							</td style="width:19%;" >
							<td align="right">
								保险公司名称：
							</td>
							<td>
								<input name="carInsurance.insuranceComName"
									id="insuranceComName" value="${carInsurance.insuranceComName}"
									size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								险种名称：
							</td>
							<td>
								<input name="carInsurance.insuranceName" id="insuranceName"
									value="${carInsurance.insuranceName} " size="20" />
							</td>
							<td align="right">
								被保险人身份证号：
							</td>
							<td>
								<input name="carInsurance.purchaseCode"
									value="${carInsurance.purchaseCode }" id="purchaseCode"
									size="20" />
							</td>

						</tr>
						<tr>

							<td align="right">
								使用性质：
							</td>
							<td>
								<input name="carInsurance.useNature" id="useNature"
									value="${carInsurance.useNature }" size="20" />
							</td>
							<td align="right">
								厂牌型号：
							</td>
							<td>
								<input name="carInsurance.brandModel"
									value="${carInsurance.brandModel }" id="brandModel" size="20" />
							</td>


						</tr>
						<tr>

							<td align="right">
								起始时间：
							</td>
							<td>
								<input name="carInsurance.startTime" id="startTime"
									value="${carInsurance.startTime }" onfocus="date(this);"
									size="20" />
							</td>
							<td align="right">
								终止时间：
							</td>
							<td>
								<input name="carInsurance.endTime" id="endTime"
									value="${carInsurance.endTime }" onfocus="date(this);"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								保险金额：
							</td>
							<td>
								<input name="carInsurance.insuranceAmount" id="insuranceAmount"
									value="${carInsurance.insuranceAmount}" size="20" />
							</td>
							<td align="right">
								保险单位电话：
							</td>
                            <td>
								<input name="carInsurance.insurancePhone" id="insurancePhone"
									value="${carInsurance.insurancePhone }" size="20" />
							</td>
						</tr>
						<tr>
	
							<td align="right">
								卖保险人：
							</td>
							<td>
								<input name="carInsurance.sellInsurer" id="sellInsurer"
									value="${carInsurance.sellInsurer }" size="20" />
							</td>
							<td align="right">
								卖保险人电话：
							</td>
							<td>
								<input name="carInsurance.sellInsurerPhone"
									id="sellInsurerPhone" value="${carInsurance.sellInsurerPhone }"
									size="20" />
							</td>

						</tr>
						<tr>

							<td align="right">
								核定载客：
							</td>
							<td>
								<input name="carInsurance.passengerNum" id="passengerNum"
									value="${carInsurance.passengerNum}" size="20" />
							</td>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="carInsurance.remarks" id="remarks"
									value="${carInsurance.remarks}" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
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
							<input name="carInsurance.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carInsurance.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carInsurance.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="carInsurance.carPeople" />
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
