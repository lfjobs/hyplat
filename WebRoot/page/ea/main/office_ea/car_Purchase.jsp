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
		<title>车辆购置税政管理</title>
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
			var purchaseID = '';
		   	var search='${search}';
			var personurl = "";
			var notoken = 0;
			var pNumber = ${pageNumber};
			var carID=parent.carID;
			var token=0;
			var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carPurchase_edit.js"></script>
	</head>
	<body>
		<form name="purchaseForm" id="purchaseForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
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
							<th width="100" align="center">
								编号
							</th>
							<th width="100" align="center">
								纳税人
							</th>
							<th width="100" align="center">
								厂牌型号
							</th>
							<th width="100" align="center">
								征收机关名称
							</th>
							<th width="100" align="center">
								经办人签章
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${purchaseID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${purchaseID}" />
									<input type="hidden" name="purchaseKey" id="purchaseKey"
										value="${purchaseKey}" />
									<input type="hidden" name="purchaseID" id="purchaseID"
										value="${purchaseID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${purchase.carID}"
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
									<span id="numbers">${numbers}</span>
								</td>
								<td class="td_bg01">
									<span id="taxpayer">${taxpayer}</span>
								</td>
								<td class="td_bg01">
									<span id="brandModel">${brandModel}</span>
								</td>
								<td class="td_bg01">
									<span id="collectionNum">${collectionNum}</span>
								</td>
								<td class="td_bg01">
									<span id="agentSignature">${agentSignature}</span>
									<span id="purchaseKey" style="display:none">${purchaseKey}</span>
                                 	<span id="purchaseID"  style="display:none">${purchaseID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carpurchase/ea_getPurchaseList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
			</form>
		<!-- 车辆购置税政的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 450px; left:60%; top: 10%" id="jqModel">
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
							<td style="width:30%;" align="right">
								<span class="xx">*</span>编号：
							</td>
							<td>
								<input name="purchase.numbers" id="numbers"
									class="numbers isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td  align="right">
								<span class="xx">*</span>纳税人：
							</td>
							<td>
								<input name="purchase.taxpayer" id="taxpayer"
									class="taxpayer isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>厂牌型号：
							</td>
							<td>
								<input name="purchase.brandModel" id="brandModel"
									class="brandModel isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>征收机关名称：
							</td>
							<td>
								<input name="purchase.collectionNum" id="collectionNum"
									class="collectionNum isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>经办人签章：
							</td>
							<td>
								<input name="purchase.agentSignature" id="agentSignature"
									size="20" class="agentSignature isremove put3" />
								<input name="carInformation.carID" type="hidden" id="numCarID" />
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
		<!-- 修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 450px; left: 60%; top: 10%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆购置税政管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 30%;" align="right">
								编号：
							</td>
							<td>
								<input name="purchase.numbers" id="numbers"
									value="${purchase.numbers }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								纳税人：
							</td>
							<td>
								<input name="purchase.taxpayer" id="taxpayer"
									value="${purchase.taxpayer }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								厂牌型号：
							</td>
							<td>
								<input name="purchase.brandModel" id="brandModel"
									value="${purchase.brandModel }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								征收机关名称：
							</td>
							<td>
								<input name="purchase.collectionNum" id="collectionNum"
									value="${purchase.collectionNum }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								经办人签章：
							</td>
							<td>
								<input name="purchase.agentSignature" id="agentSignature"
									value="${purchase.agentSignature }" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input name="purchase.purchaseKey" id="purchaseKey"
									type="hidden" class="input" size="20" />
								<input name="purchase.purchaseID" id="purchaseID" type="hidden"
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
		
		<!-- 车辆购置税政管理信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
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
							<input name="purchase.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="purchase.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="purchase.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="purchase.carPeople" />
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
