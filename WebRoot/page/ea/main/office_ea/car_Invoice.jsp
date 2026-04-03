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
		<title>购车发票管理</title>
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
			var invoiceID = '';
			var search='${search}';
			var personurl = "";
			var notoken = 0;
			var pNumber = ${pageNumber};
			var carID = parent.carID;
			var token=0;
			var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carInvoice_edit.js"></script>
	</head>
	<body>
		<form name="carInvoiceForm" id="carInvoiceForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
			<input type="hidden" id="thisdate" />
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="100" align="center">
								车牌号
							</th>
							<th width="50" align="center">
								车辆类型
							</th>
							<th width="100" align="center">
								发动机号码
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="100" align="center">
								发票代号
							</th>
							<th width="100" align="center">
								开票日期
							</th>
							<th width="100" align="center">
								购货单位
							</th>
							<th width="140" align="center">
								身份证号码/组织机构代码
							</th>
							<th width="100" align="center">
								厂牌型号
							</th>
							<th width="100" align="center">
								产地
							</th>
							<th width="100" align="center">
								单价销货单位名称
							</th>
							<th width="100" align="center">
								地址
							</th>
							<th width="180" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${invoiceID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${invoiceID}" />
									<input type="hidden" name="invoiceKey" id="invoiceKey"
										value="${invoiceKey}" />
									<input type="hidden" name="invoiceID" id="invoiceID"
										value="${invoiceID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carInvoice.carID}"
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
									<span id="carpeople">${carpeople}</span>
								</td>
								<td class="td_bg01">
									<span id="invoiceCode">${invoiceCode}</span>
								</td>
								<td class="td_bg01">
									<span id="invoiceDate">${fn:substring(invoiceDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="purchaseUnits">${purchaseUnits}</span>
								</td>
								<td class="td_bg01">
									<span id="purchaseCode">${purchaseCode}</span>
								</td>
								<td class="td_bg01">
									<span id="brandModel">${brandModel}</span>
								</td>
								<td class="td_bg01">
									<span id="origin">${origin}</span>
								</td>
								<td class="td_bg01">
									<span id="salesName">${salesName}</span>
								</td>
								<td class="td_bg01">
									<span id="salesAddress">${salesAddress}</span>
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<span id="invoiceID" style="display:none">${invoiceID}</span>
                                 <span id="invoiceKey"  style="display:none">${invoiceKey}</span>
								</td>

								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/carinvoice/ea_getCarInvoiceList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
					</c:param>
				</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <iframe src="" style="height:0;" name="main" width="99%" scrolling="no"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
			</div>
		</form>
		<!-- 车辆购车发票添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 450px; left: 80%; top: 10%;" id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							购车发票管理添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">

						<tr>
							<td style="width:30%;" align="right">
								<span class="xx">*</span>发票代号：
							</td>
							<td>
								<input name="carinvoice.invoiceCode" id="invoiceCode"
									class="invoiceCode isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>开票日期：
							</td>
							<td>
								<input name="carinvoice.invoiceDate" id="invoiceDate" size="20"
									onfocus="date(this);" class="invoiceDate isremove put3" />
							</td>
						</tr>

						<tr>
							<td align="right">
								购货单位：
							</td>
							<td>
								<input name="carinvoice.purchaseUnits" id="purchaseUnits"
									size="20" />
							</td>
						</tr>

						<tr>
							<td align="right">
								<span class="xx">*</span>身份证号码：
							</td>
							<td>
								<input name="carinvoice.purchaseCode" id="purchaseCode"
									size="20" class="purchaseCode isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>厂牌型号：
							</td>
							<td>
								<input name="carinvoice.brandModel" id="brandModel" size="20"
									class="brandModel isremove put3" />
							</td>

						</tr>
						<tr>
							<td align="right">
								产地：
							</td>
							<td>
								<input name="carinvoice.origin" id="origin" size="20" />
							</td>

						</tr>


						<tr>
							<td align="right">
								单价销货单位名称：
							</td>
							<td>
								<input name="carinvoice.salesName" id="salesName" size="20" />
							</td>
						</tr>

						<tr>
							<td align="right">
								<span class="xx">*</span>地址：
							</td>
							<td colspan="2">
								<input name="carinvoice.salesAddress" id="salesAddress"
									size="30" class="salesAddress isremove put3" />
							</td>

						</tr>

						<tr>
							<td align="right">
								备注：
							</td>
							<td colspan="2">
								<input name="carinvoice.remarks" id="remarks" size="30" />
								<input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td  colspan="6" align="center">
								
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
		<!-- 购车发票修改页面 -->
        <div class="contentbannb jqmWindow jqmWindowcss2" style="width: 450px; left: 70%; top: 10%;" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							购车发票管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate" cellpadding="5"
						cellspacing="5">
						<tr>
							<td style="width:30%;"align="right">
								发票代号：
							</td>
							<td>
								<input name="carinvoice.invoiceCode" class="invoiceCode isremove put3" 
									value="${carinvoice.invoiceCode }" id="invoiceCode" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								发票日期：
							</td>
							<td>
								<input name="carinvoice.invoiceDate" class="invoiceDate isremove put3"
									value="${carinvoice.invoiceDate }" onfocus="date(this);"
									id="invoiceDate" size="20" />
							</td>

						</tr>

						<tr>
							<td align="right">
								购买单位：
							</td>
							<td>
								<input name="carinvoice.purchaseUnits" 
									value="${carinvoice.purchaseUnits }" id="purchaseUnits"
									 size="20" />
							</td>
						</tr>

						<tr>
							<td align="right">
								身份证号码：
							</td>
							<td>
								<input name="carinvoice.purchaseCode" class="purchaseCode isremove put3"
									value="${carinvoice.purchaseCode }" id="purchaseCode"  size="20" />
							</td>


						</tr>


						<tr>

							<td align="right">
								厂牌型号：
							</td>
							<td>
								<input name="carinvoice.brandModel" class="brandModel isremove put3" 
									value="${carinvoice.brandModel}" id="brandModel"
									 size="20" />
							</td>

						</tr>

						<tr>

							<td align="right">
								产地：
							</td>
							<td>
								<input name="carinvoice.origin" value="${carinvoice.origin }"
									id="origin"  size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								单价销货单位名称：
							</td>
							<td>
								<input name="carinvoice.salesName" 
									value="${carinvoice.salesName}" id="salesName" size="20" />
							</td>

						</tr>

						<tr>

							<td align="right">
								地址：
							</td>
							<td>
								<input name="carinvoice.salesAddress" class="salesAddress isremove put3" 
									value="${carinvoice.salesAddress }" id="salesAddress"
									style="margin-left: 2px;" size="30" />
							</td>

						</tr>

						<tr>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="carinvoice.remarks" value="${carinvoice.remarks }"
									id="remarks" size="30" />
							</td>
						</tr>
						<tr>
							<td colspan="6" align="center">
								
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
       
		<!-- 查询信息 -->
			<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="100%"id="carSearchTable" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carinvoice.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carinvoice.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carinvoice.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carinvoice.carpeople" />
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
