<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="hy.ea.bo.Company"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>归档查看页面</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/company/finance/statemnetssummary_edit.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<style type="text/css">
.hide {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
	border-bottom: 0px;
	background: transparent;
}

.classhide {
	display: none;
}

#table input {
	width: 180px;
}

#goodstable input {
	width: 180px;
}

#contactcompany input {
	width: 180px;
}

#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>

		<script type="text/javascript">
  	var treeID ="<%=c.getCompanyID()%>";
    var treeNames="<%=c.getCompanyName()%>";
	var tokens = 0;
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	var deptID="${historyVO.departmentID}";
	var goodsBillsID="";
	var token = 0;
	var pNumber=${pageNumber};
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var myform='';
	var subjectsName="";
	var subjectsnumber="";
	var select=1;
	var notoken = 0;
	var journalNum = "";
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var sdate="${sdate}";
    var edate="${edate}";
	var depotName="";
	var taxdate="${historyVO.taxDate}";
	var differenceStyle="${differenceStyle}";
	
	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID;
		 }
		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
	  	 $("#daoRu").attr("src",basePath+url);
	  	 $("#bankJqm").jqmShow();
	}
	
	
		
$(document).ready(function() {//销售单FORM
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
		}
		if(checkopertionName =="partnerName"){
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
});       
</script>

	</head>
	<body style="overflow-y: auto;">
		<div id="apDiv1"></div>
		<form name="cashierTallyForm" id="cashierTallyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						归档单据
					</div>
				</div>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="30" align="right">
							粘贴单编号：
						</td>
						<td>

							<input type="hidden" name="historyCashierBills.cashierBillsID"
								id="cashierID" value="${historyVO.cashierBillsID}" />
							<input type="hidden" name="historyCashierBills.cashierBillsKey"
								id="goodsBillsKey" value="${historyVO.cashierBillsKey}" />
							<input type="hidden" name="historyCashierBills.depStatue" id="depStatue"
								value="00" />
							<input type="text" style="width: 200px; border: 0"
								value="${historyVO.journalNum }"
								id="journalNum" name="historyCashierBills.journalNum"
								readonly="readonly" />
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							<span id="spanBillsType"> ${historyVO.billsType}</span>
						</td>
						<td align="right">
							责任人：
						</td>
						<td>
							<input type="hidden" id="partnerID" name="historyCashierBills.staffID"
								readonly="readonly" value="${historyVO.staffID}" />
							<input type="text" id="partnerName" name="partnerName" size="15" value="${historyVO.staffname}"/>
							<a href="#"
								onclick="importGY('table3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames"></span>
						</td>
						<td align="right">
							部门：
							<input type="hidden" name="historyCashierBills.departmentID" id="depID"	value="${historyVO.departmentID}" />
						</td>
						<td align="left" id="dept">
							<input class="classhide1 " type="text"
								value="${historyVO.departmentname}" readonly="readonly" />
							<a href="#" title="departmentID" class="classhide1 update">修改</a>
							<select name="departmentID" id="departmentID" style="display:none;"
								style="width: 180px;">
								<option value="0">
									请选择
								</option>
								</select>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td align="left" colspan=3>
							<input type="text" id="bankNum"
								name="historyCashierBills.companyBankNum" readonly="readonly"
								value="${historyVO.companyBankNum}" size="35" />
							<a href="#"
								onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')">选择</a>
					</tr>
				</table>

				<table width="99%" height="150px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 150px; overflow: scroll;">
								<table width="2350" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
									<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											款源日期
										</th>
										<th align="center" bgcolor="#E4F1FA">
											入账日期
										</th>
										<th align="center" bgcolor="#E4F1FA">
											有效日期
										</th>
										<th align="center" bgcolor="#E4F1FA">
											批号/期号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											统一分类条码
										</th>
										<th align="center" bgcolor="#E4F1FA">
											标识条码
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA">
											类型
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品牌
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品牌规格
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											重量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											箱规格
										</th>
										<th width="100" bgcolor="#E4F1FA">
											单价管理
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											费用类别
										</th>
										<th align="center" bgcolor="#E4F1FA" width="130">
											科目
										</th>
										<th align="center" bgcolor="#E4F1FA" width="130">
											摘要
										</th>
										<th align="center" bgcolor="#E4F1FA">
											库房管理
										</th>
										<th align="center" bgcolor="#E4F1FA">
											借方金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											贷方金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											方向
										</th>
										<th align="center" bgcolor="#E4F1FA">
											余额
										</th>
										<th align="center" width="50" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods" id="${goodsBillsID}">
										<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="goodsNomber">${goodsNomber}</span>
												<input name="goodsNomber" class="model1" value="${goodsNomber}"
													style="margin-left: 2px;" size="1" />
											</td>
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="startDate">${fn:substring(startDate, 0,
													10)}</span>
												<input name="startDate" class="model1" onfocus="date(this);"
													value="${fn:substring(startDate, 0, 10)}"
													style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="endDate">${fn:substring(endDate, 0, 19)}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="periodDate">${fn:substring(periodDate, 0,
													10)}</span>
												<input name="periodDate" class="model1" id="periodDate"
													onfocus="date(this);"
													value="${fn:substring(periodDate, 0, 10)}"
													style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="batchNumber">${batchNumber}</span>
												<input name="batchNumber" class="model1" id="batchNumber"
													value="${batchNumber}" style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsID" value="${goodsID}"
													id="goodsID" />
												<span class="bhide" id="goodsCoding">${goodsCoding}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="defaultStorage" class="bhide">${defaultStorage}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="identifyingCode">${identifyingCode}</span>
												<input name="identifyingCode" value="${identifyingCode}"
													class="model1" id="identifyingCode"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsName" class="bhide">${goodsName}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="typeID" class="bhide">${typeID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="mnemonicCode" class="bhide">${mnemonicCode}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="model" class="bhide">${model}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="standard" class="bhide">${standard}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsVariableID">${goodsVariableID}</span>
												<select class="dis" id="goodsVariableID"
													name="goodsVariableID">
													<option value="">
														请选择
													</option>
													<s:if test='VariableID.length()!=0'>
														<s:if test="variableID==goodsVariableID">
															<option value="${variableID}" selected="selected">
																${variableID}
															</option>
														</s:if>
														<s:else>
															<option value="${variableID}">
																${variableID}
															</option>
														</s:else>
													</s:if>
													<s:if test='Variable1ID.length()!=0'>
														<s:if test="Variable1ID==goodsVariableID">
															<option value="${Variable1ID}" selected="selected">
																${Variable1ID}
															</option>
														</s:if>
														<s:else>
															<option value="${Variable1ID}">
																${Variable1ID}
															</option>
														</s:else>
													</s:if>
													<s:if test='Variable2ID.length()!=0'>
														<s:if test="Variable2ID==goodsVariableID">
															<option value="${Variable2ID}" selected="selected">
																${Variable2ID}
															</option>
														</s:if>
														<s:else>
															<option value="${Variable2ID}">
																${Variable2ID}
															</option>
														</s:else>
													</s:if>
													<s:if test='Variable3ID.length()!=0'>
														<s:if test="Variable3ID==goodsVariableID">
															<option value="${Variable3ID}" selected="selected">
																${Variable3ID}
															</option>
														</s:if>
														<s:else>
															<option value="${Variable3ID}">
																${Variable3ID}
															</option>
														</s:else>
													</s:if>
													<s:if test='Variable4ID.length()!=0'>
														<s:if test="Variable4ID==goodsVariableID">
															<option value="${Variable4ID}" selected="selected">
																${Variable4ID}
															</option>
														</s:if>
														<s:else>
															<option value="${Variable4ID}">
																${Variable4ID}
															</option>
														</s:else>
													</s:if>
												</select>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="quantity">${quantity}</span>
												<input name="quantity" class="jisuan isNaN model1"
													value="${quantity}" id="quantity" size="3"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="weight">${weight}</span>
												<input name="weight" class="model1" value="${weight}"
													id="weight" size="3" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="boxStandard">${boxStandard}</span>
												<input class="model1" name="boxStandard"
													value="${boxStandard}" id="boxStandard" size="3"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="priceManage">${priceManage}</span>
												<s:select class="dis" list="%{#request.priceManageList}"
													listKey="codeValue" listValue="codeValue" id="priceManage"
													name="priceManage" theme="simple"></s:select>
												<a href="#" class="model1"
													onclick="toCCode('scode20101101dfs3uhdprp0000000032','#priceManage','#cashierTallyForm')">新添</a>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="price">${price}</span>
												<input name="price" class="jisuan isNaN  model1"
													value="${price}" class="input" id="price" size="5"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="money">${money}</span>
												<input name="money" readonly="readonly" value="${money}"
													class="model1" id="money" size="5"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="costType">${costType}</span>
												<s:select class="dis" list="%{#request.costTypeList}"
													listKey="codeValue" listValue="codeValue" id="costType"
													name="costType" theme="simple"></s:select>
												<a href="#" class="model1"
													onclick="toCCode('scode20101101dfs3uhdprp0000000030','#costType','#cashierTallyForm')">新添</a>
											</td>
											<td align="left">
												<input type="hidden" value="${subjectsID}" id="subjectsID"
													name="subjectsID" />
												<span id="subjectsName">${subjectsName}</span>
												<input type="text" readonly="readonly"
													value="${subjectsName}" class="put3 model1" size="8"
													id="subjectsName" name="subjectsName" />
												<a href="#" class="tosubjects model1">选择科目</a>
											</td>
												<td align="center" bgcolor="#FFFFFF">
												<span id="reasonThing">${reasonThing}</span>
												<input name="reasonThing" value="${reasonThing}"
													class="model1" id="reasonThing" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="depotType">${depotType}</span>
												<s:select list="#{'出库':'出库','入库':'入库','库存':'库存','其它':'其它'}"
													class="dis" name="depotType" id="depotType" theme="simple"></s:select>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="loan">${loan}</span>
												<input name="loan" value="${loan}" class="isNaN model1"
													id="loan" size="5" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="forLoan">${forLoan}</span>
												<input name="forLoan" value="${forLoan}"
													class="isNaN model1" id="forLoan" size="5"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="direction">${direction}</span>
												<s:select class="dis" list="#{'借':'借','贷':'贷','其它':'其它'}"
													name="direction" id="direction" theme="simple"></s:select>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="balance">${balance}</span>
												<input name="balance" value="${balance}"
													class="isNaN model1" id="balance" size="3"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsBillsID"
													value="${goodsBillsID}" id="goodsBillsID" />
												<input type="hidden" name="goodsBillsKey"
													value="${goodsBillsKey}" id="goodsBillsKey" />
												<a href="#" class="ajaxxg"><img
														src="<%=basePath%>images/admin_images/edit.gif" width="16"
														height="16" title="修?改?" border="0" /> </a>
												<a href="#" class="ajaxsc"><img
														src="<%=basePath%>images/admin_images/gtk-del.png"
														width="16" height="16" title="删除" border="0" /> </a>
											</td>
										</tr>
									</s:iterator>
									<tr id="kelong" style="display: none">
									<td height="30" align="center" bgcolor="#FFFFFF">
											<input id="goodsNomber" name="goodsNomber" class="input" size="1" />
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF">
											<input name="startDate" onfocus="date(this);" class="input"
												id="username" style="margin-left: 2px;" size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="endDate" class="input " id="endDate"
												readonly="readonly"
												style="margin-left: 2px; padding: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;"
												size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="periodDate" class="input " id="periodDate"
												onfocus="date(this);" style="margin-left: 2px;" size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="batchNumber" class="input " id="batchNumber"
												style="margin-left: 2px;" size="15" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" name="goodsID" id="goodsID" />
											<span id="goodsCoding"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="defaultStorage">${defaultStorage}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="identifyingCode" value="${identifyingCode}"
												id="identifyingCode" style="margin-left: 2px;" />
										</td>

										<td align="center" bgcolor="#FFFFFF">
											<span id="goodsName"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="typeID"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="mnemonicCode"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="model"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="standard"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<select id="goodsVariableID" name="goodsVariableID"></select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="quantity" class="jisuan isNaN" id="quantity"
												size="3" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="weight" class="input" id="weight" size="3"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="boxStandard" class="input" id="boxStandard"
												size="3" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.priceManageList}"
												listKey="codeValue" listValue="codeValue" id="priceManage"
												name="priceManage" theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000032','#priceManage','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="price" class="jisuan isNaN" id="price" size="5"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="money" readonly="readonly" class="input"
												id="money" size="5" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.costTypeList}" listKey="codeValue"
												listValue="codeValue" id="costType" name="costType"
												theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000030','#costType','#cashierTallyForm')">新添</a>
										</td>
										<td align="left">
											<input type="hidden" id="subjectsID" name="subjectsID" />
											<input type="text" readonly="readonly" id="subjectsName"
												class="put3${differenceStyle}" size="8" name="subjectsName" />
											<a href="#" class="tosubjects">选择科目</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="reasonThing" value="${reasonThing}" style="width: 70px"
												id="reasonThing" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="#{'出库':'出库','入库':'入库','库存':'库存','其它':'其它'}"
												name="depotType" id="depotType" theme="simple"></s:select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="loan" class="isNaN" id="loan" size="5"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="forLoan" class="isNaN" id="forLoan" size="5"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="#{'借':'借','贷':'贷','其它':'其它'}"
												name="direction" id="direction" theme="simple"></s:select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="balance" class="isNaN" id="username5" size="3"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>

				<table width="99%" height="30" border="0" align="center"
					cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
					style="margin-bottom: 5px;">
					<tr>
						<td bgcolor="#FFFFFF" colspan="3">
							<input type="button" class="ACT_btn" name="button4" value="导入数据" />
							<input type="button" class="ACT_btn" name="button4" value="选择物品"
								id="shuju" />

							财务仓库：
							<input type="hidden" name="cashierBills.bankDepotID"
								id="bankDepotID" />
							<input name="historyCashierBills.bankDepotName" id="bankDepotName"
								size="15" readonly="readonly" />
							<a href="#" class="tobankdepotID">选择库房</a>
						</td>
						<td>
							实物仓库：
							<input type="hidden" name="historyCashierBills.discountMoney"
								id="discountMoney" />
							<input name="historyCashierBills.afterDiscount" id="afterDiscount"
								size="15" readonly="readonly" />
							<a href="#" class="todepotID">选择库房</a>
						</td>
						<td>
							资料仓库：
							<input type="hidden" name="historyCashierBills.dataDepotID"
								id="dataDepotID" />
							<input name="historyCashierBills.dataDepotName" id="dataDepotName"
								size="15" readonly="readonly" />
							<a href="#" class="todatadepotID">选择库房</a>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${historyVO.ccompanyname}</span>
							<input type="hidden" id="ccompanyID"
								name="historyCashierBills.ccompanyID"
								value="${historyVO.ccompanyID}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${historyVO.companyTel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${historyVO.cresponsible}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${historyVO.accountNum}</span>
							<input id="accountNum" type="hidden"
								name="historyCashierBills.accountNum"
								value="${historyVO.accountNum}" />
							<select style="display: none" id="aNum" name="accountNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${historyVO.responsibleTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${historyVO.companyAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${historyVO.industryType}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${historyVO.contactConnections}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="historyVO.contactConnections" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${historyVO.contactUserName}</span>
							<input type="hidden" id="contactUserID"
								name="historyCashierBills.contactUserID"
								value="${historyVO.contactUserID}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${historyVO.tel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${historyVO.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${historyVO.userAccountNum}</span>
							<input id="userAccountNum" type="hidden"
								name="historyCashierBills.userAccountNum"
								value="${historyVO.userAccountNum}" />
							<select style="display: none" id="userNum" name="userAccountNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right"><span class="STYLE1">QQ：</span></td>
      <td >${historyCashierBills.referenceCode}</td>
      <td align="right"><span class="STYLE1">邮箱：</span></td>
      <td>${historyCashierBills.referenceOrganization}</td>
      <td align="right"><span class="STYLE1">地址：</span></td>
      <td>${historyCashierBills.staffAddress}</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${historyVO.phone}</span>
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择" name="historyVO.phone"
								theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">

							<!-- <input type="button" class="btn001 JQuerySubmitgd" name="button3"
								value="保存" />  -->
							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择物品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="70">
								<input name="parameter" class="input" id="parameter" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70">
								物品类型：
							</td>
							<td width="100">
								<s:select list="codeList" listKey="codeValue" id="typeID" listValue="codeValue"
								 headerKey="" headerValue="请选择" name="typeID" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="40">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="40">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="80">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="aadTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
	
		<div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectsubjects">
			<div class="drag">
				选择
			</div>
			<table>
				<tr>
					<td>
						科目名字：
					</td>
					<td align="left" class="subjects">
						<select id="province" number='0' style="width: 110px;"></select>
						<select id="city" number='1' style="width: 110px;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
					<td>
						<a href="#"
							onclick="window.open('<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp')">新增</a>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savesubjects"
					value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectdepot">
			<div class="drag">
				选择
			</div>
			<table>
				<tr>
					<td>
						库房：
					</td>
					<td align="left" class="depot">
						<select id="province" number='0' style="width: 110px;"></select>
						<!-- <option>选择省</option>-->
						<select id="city" number='1' style="width: 110px; display: none;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<!-- <option>选择省</option>-->
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedepot" value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectbankdepot">
			<div class="drag">
				选择
			</div>
			<table>
				<tr>
					<td>
						库房：
					</td>
					<td align="left" class="banksdepot">
						<select id="province" number='0' style="width: 110px;"></select>
						<!-- <option>选择省</option>-->
						<select id="city" number='1' style="width: 110px; display: none;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<!-- <option>选择省</option>-->
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savebankdepot"
					value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectdatadepot">
			<div class="drag">
				选择
			</div>
			<table>
				<tr>
					<td>
						库房：
					</td>
					<td align="left" class="datadepot">
						<select id="province" number='0' style="width: 110px;"></select>
						<!-- <option>选择省</option>-->
						<select id="city" number='1' style="width: 110px; display: none;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<!-- <option>选择省</option>-->
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedatadepot"
					value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		<form name="cstaffForm" id="cstaffForm" method="post">
			<div class="jqmWindow jqmWindowcss3" style="top: 10%" id="jqModelkf">
				<div class="content1">
					<div class="contentbannb">
						<div class="drag">
							库房信息
							<div class="close"></div>
						</div>
					</div>
					<table width="485" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="120" height="35" align="right">
								仓库编码：
							</td>
							<td>
								<input type="text" id="depotCoding" class="put3"
									name="depotManage.depotCoding" />
							</td>
						</tr>
						<tr>
							<td height="35" align="right">
								仓库名称：
							</td>
							<td>
								<input name="depotManage.depotName" id="depotName" type="text"
									class="put3" size="20" />
							</td>
						</tr>
						<tr>
							<td height="35" align="right">
								仓库类别：
							</td>
							<td>
								<s:select list="typelist" listKey="codeID" id="itemID"
									listValue="codeValue" name="depotManage.itemID" theme="simple"></s:select>
								<a href="#"
									onclick="toCCode('scode20101014v5zed7cukk0000000004','#itemID','#cstaffForm')">新添</a>
							</td>
						</tr>
						<tr>
							<td height="35" align="right">
								使用状态：
							</td>
							<td>
								<select name="depotManage.useState" id="useState">
									<option value="00">
										未启用
									</option>
									<option value="01">
										已启用
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td height="35" align="right">
								备注：
							</td>
							<td>
								<textarea rows="3" cols="40" name="depotManage.remark"
									id="remark"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="center">
								<input name="depotManage.depotKey" id="depotKey" type="hidden"
									class="input" />
								<input name="depotManage.depotID" id="depotID" type="hidden"
									class="input" />
								<input id="treenum" type="hidden" class="input" />
								<input name="depotManage.depotPID" id="depotPID" type="hidden"
									class="input" />
								<input type="button" class="input-button JQuerySubmitkf"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button JQueryreturnkf"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<div id="bankJqm" class="jqmWindow" 
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
			</div>
		</div>
	</body>
</html>
