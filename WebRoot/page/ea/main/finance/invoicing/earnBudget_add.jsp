<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@page import="hy.ea.bo.Company"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>收入预算单据</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company) session.getAttribute("currentcompany");
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
			src="<%=basePath%>js/ea/finance/invoicing/earnBudget_add.js"></script>
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
	left: 750px;
	top: 387px;
	width: 63px;
	height: 32px;
	z-index: 1;
}

.bitian {
	color: red;
}

.bordernone {
	border: 0;
	height: 25px;
	line-height: 25px;
}

<!--
---
-表格拖动列宽 -->.bg table {
	font-size: 12px;
	color: #000000;
}

.bg td,th {
	font-size: 12px;
	color: #000000;
	text-align: center;
	line-height: 15px;
	height: 20px;
}

.bg td.tit            ,th.tit {
	background-color: #e2e2e2;
	color: #000;
	height: 17px;
	text-align: center;
	line-height: 15px;
}

.bg td.num            ,ht.num {
	background-color: #e2e2e2;
	color: #000;
	text-align: right;
	line-height: 15px;
	width: 30px;
	height: 22px;
}

.resizeDivClass {
	text-align: right;
	width: 3px;
	margin: 0px 0 0px 0;
	background: #fff;
	border: 0px;
	float: right;
	cursor: e-resize;
}

.auto_arrange {
	table-layout: fixed
}

.auto_arrange td,th {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}
</style>


		<script type="text/javascript">
  	var treeID = '<%=session.getAttribute("organizationID")%>';
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var  tokens = 0;
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var csbID="${earnBudgetBill.ebbID}";
	var deptID="${cashierBillsVO.departmentID}";
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
   	var treeid;
   	var treename;
   	var subjectsNumbers;
   	var tree;
	var quzhi="";
	var subRule = new Array();
	var endnumber = 0;
	var treeName='';
	var treePID='';
	var treePName='';
	var oldtreeid='';
	var aa=0;
	var type = "${type}";
	var sztype = "${sztype}";
	var toSee = "${toSee}";
	var addType = "${addType}";
</script>



	</head>
	<body style="overflow-y: auto;">
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
		<%-- 用来显示审核章 --%>
		<div id="apDiv1"></div>
		<form name="earnBudgetForm" id="earnBudgetForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						<s:if test='sztype=="s"'>收入预算单据</s:if><s:elseif test='sztype=="z"'>支出预算单据</s:elseif><s:else><span id="sz">收入预算单据</span></s:else>
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
						     <input type="hidden" name="sztype"  id="sztype"
								value="${sztype}" />
                            <input type="hidden" name="type" 
								value="${type}" />
							<input type="hidden" name="earnBudgetBill.ebbID" id="ebbIDs"
								value="${earnBudgetBill.ebbID}" />
							<input type="hidden" name="earnBudgetBill.ebbKey"
								id="goodsBillsKey" value="${earnBudgetBill.ebbKey}" />
							<input type="hidden" name="earnBudgetBill.billStatus"
								id="billStatus" value="${earnBudgetBill.billStatus}" />
							<input type="text" style="width: 200px; border: 0"
								value="<c:if test="${earnBudgetBill.billNum!=null}">${earnBudgetBill.billNum }</c:if><c:if test="${earnBudgetBill.billNum==null}">自动生成</c:if>"
								id="billNum" name="earnBudgetBill.billNum" readonly />
							
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							<s:if test='sztype=="s"'>收入预算单据</s:if><s:elseif test='sztype=="z"'>支出预算单据</s:elseif><s:else>							
							<select id="szbillstype">
								<option value="s" selected>
									收入预算单据
								</option>
								<option value="z">
									支出预算单据
								</option>
							</select>
							<span id="szj" style="display:none;"></span>
							</s:else>


							<input type="hidden" id="billsType" value="${earnBudgetBill.billsType}" name="earnBudgetBill.billsType"/>

						</td>
						<td align="right">
							责任人：
						</td>
						<td width="20%">		    
							<input type="hidden" id="partnerID" name="earnBudgetBill.staffID"
								readonly="readonly" value="${earnBudgetBill.staffID==null?ManStaffId:earnBudgetBill.staffID}" />
							<input type="text" id="partnerName"
								name="earnBudgetBill.staffName" class="put3" readonly="readonly"
								value="${earnBudgetBill.staffName==null?ManStaffName:earnBudgetBill.staffName}" size="15" />
							<input type="hidden" id="staffCode" value="${earnBudgetBill.staffCode==null?ManStaffCode:earnBudgetBill.staffCode}" name="earnBudgetBill.staffCode"/>
							<a href="#" class="see"
								onclick="importGY('table3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
					    </td>
						<td width="6%" align="right">
							预算年份:
						</td>
						<td width="18%">
						<span id="yearspan" style="display:none;">${earnBudgetBill.year}</span>
						<input type="text" value="${earnBudgetBill.year}" name="earnBudgetBill.year"  id="year" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="Wdate put3 year"/>
							<!--<input name="earnBudgetBill.year" id="year"
								value="${earnBudgetBill.year}" size="5" class="put3" />
						--></td>
					</tr>

					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames"><%=c.getCompanyName()%></span>
							<input type="hidden" value="<%=c.getCompanyName()%>" name="earnBudgetBill.companyName"/>
						    <input type="hidden"
								value="${earnBudgetBill.companyID}"
								name="earnBudgetBill.companyID" />
							 <input type="hidden"
								value="${earnBudgetBill.groupCompanySn}"
								name="earnBudgetBill.groupCompanySn" />
						</td>
						<td align="right">
							部门：

						</td>
						<td align="left">
							<span style="width: 180px;"><%=session.getAttribute("organizationName")%></span>
							<input type="hidden"
								value="<%=session.getAttribute("organizationName")%>"
								name="earnBudgetBill.organizationName" />
							<input type="hidden"
								value="${earnBudgetBill.organizationID}"
								name="earnBudgetBill.organizationID" />
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td align="left" >

							<input type="text" id="bankNum"
								name="earnBudgetBill.companyBankNum" readonly="readonly"
								value="${earnBudgetBill.companyBankNum}" size="26" />
							<a href="#" class="see"
								onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')">选择</a>

						</td>
						<td align="right">
							预算名称：
						</td>
						<td align="left">
							<input type="text" class="put3" id="budgetName" name="earnBudgetBill.budgetName" value="${earnBudgetBill.budgetName}"/>
						</td>
					</tr>
				</table>

				<table width="99%" height="150px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px" id="tb">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 150px; overflow: scroll;">
								<table align="center" cellpadding="0" cellspacing="0"
									class="table bg auto_arrange" id="goodtable"
									style="overflow: hidden;">
									<tr>
										<th align="center" valign="middle" bgcolor="#E4F1FA"
											width="38px">

											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"> <img
													src="<%=basePath%>/images/header_bg.gif" width="10"
													height="30" /> </span> 序号
										</th>
									    <th align="center" valign="middle" bgcolor="#E4F1FA"
											width="76px">

											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"> <img
													src="<%=basePath%>/images/header_bg.gif" width="10"
													height="30" /> </span> 月份
										</th>


										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品编号
										</th>



										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品名称
										</th>

										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品单位
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品规格
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品重量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="150px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 预算单价
										</th>
										
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="150px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 调整单价
										</th>
										<th align="center" bgcolor="#E4F1FA" width="150px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 月预算数量
										</th>
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="150px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 月调整数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 日预算金额
										</th>
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 日调整金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 周预算金额
										</th>
										
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 周调整金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 月预算金额
										</th>
										
										<th class="tiao"  align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 月调整金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span>季度预算金额
										</th>
										
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span>季度调整金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 年预算金额
										</th>
										
										<th class="tiao" align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 年调整金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 说明书
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 产品宣传
										</th>
										<th align="center" bgcolor="#E4F1FA" width="76px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30" /> </span> 公司文件
										</th>


										<th class="see operate" align="center" width="76px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="30" /> </span> 操作
										</th>
									</tr>
									<% int number = 1; %>
									<s:iterator value="earnBudgetDetailList">
										<tr class="xggoods" id="${ebdID}">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="goodsNomber">${goodsNomber}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].goodsNomber" value="${goodsNomber}" />
											</td>

                                            <td align="center" bgcolor="#FFFFFF">
												<span class="bhide">${month}</span>
												<input type="text" value="${month}" size="3" name="earnBudgetMap['<%=number%>'].month"  id="month" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'MM'})" class="Wdate put3 month"/>
												<!--<input type="hidden" name="earnBudgetMap['<%=number%>'].month" value="${month}" />
											--></td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="earnBudgetMap['<%=number%>'].goodsID" value="${goodsID}"
													id="goodsID" />
												<span class="bhide" id="productNum">${productNum}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].productNum" value="${productNum}"  id="productNum"/>
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="productName" class="bhide">${productName}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].productName" value="${productName}" />
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="productUnit">${productUnit}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].productUnit" value="${productUnit}" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="productStandard" class="bhide">${productStandard}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].productStandard" value="${productStandard}" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="weight" class="bhide">${weight}</span>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].weight" value="${weight}" />
											</td>
											<td  align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bunitPrice" value="${bunitPrice}"
													style="width: 70px" id="bunitPrice"
													class="jisuan put3 isNaN budget" />
											</td>
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tunitPrice" value="${tunitPrice}"
													style="width: 70px" id="tunitPrice"
													class="jisuant isNaN" />
											</td>
											<td  align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bdquantity" value="${bdquantity}"
													style="width: 70px" id="bdquantity"
													class="jisuan put3 isNaN budget" />
											</td>
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tdquantity" value="${tdquantity}"
													style="width: 70px" id="tdquantity"
													class="jisuant isNaN" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bdamount" class="bordernone" id="bdamount"
													value="${bdamount}" readonly />
											</td>
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tdamount" class="bordernone" id="tdamount"
													value="${tdamount}" readonly />
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bwamount" class="bordernone" id="bwamount"
													value="${bwamount}" readonly />
											</td>
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].twamount" class="bordernone" id="twamount"
													value="${twamount}" readonly />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bmamount" class="bordernone" id="bmamount"
													value="${bmamount}" readonly />
											</td>
											
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tmamount" class="bordernone" id="tmamount"
													value="${tmamount}" readonly />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].bsamount" class="bordernone" id="bsamount"
													value="${bsamount}" readonly />
											</td>
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tsamount" class="bordernone" id="tsamount"
													value="${tsamount}" readonly />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].byamount" class="bordernone" id="byamount"
													value="${byamount}" readonly />
											</td>
											
											<td class="tiao" align="center" bgcolor="#FFFFFF">
												<input name="earnBudgetMap['<%=number%>'].tyamount" class="bordernone" id="tyamount"
													value="${tyamount}" readonly />
											</td>
							
											<td align="center" bgcolor="#FFFFFF">
						
												<s:if test="manual!=null&&manual!='null'"><a   href="#" onclick="viewAttach('${manual}');">查看</a></s:if>
												<s:else>
												  无
												</s:else>
											<input type="hidden" name="earnBudgetMap['<%=number%>'].manual" value="${manual}" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												
												<s:if test="productPublicity!=null&&productPublicity!='null'"><a   href="#" onclick="viewAttach('${productPublicity}');">查看</a></s:if>
												<s:else>
												  无
												</s:else>
											    <input type="hidden" name="earnBudgetMap['<%=number%>'].productPublicity" value="${productPublicity}" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
											
											<s:if test="companyFile!=null&&companyFile!='null'"><a  href="#" onclick="viewAttach('${companyFile}');">查看</a></s:if>
												<s:else>
												  无
												</s:else>
												<input type="hidden" name="earnBudgetMap['<%=number%>'].companyFile" value="${companyFile}" />
											</td>
											
											<td class="see operate" align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="earnBudgetMap['<%=number%>'].ebdID"
													value="${ebdID}" id="ebdID" />
												<input type="hidden" name="earnBudgetMap['<%=number%>'].ebdKey"
													value="${ebdKey}" id="ebdKey" />
												<input type="hidden" name="earnBudgetMap['<%=number%>'].ebbID"
													value="${ebbID}" id="ebbID" />
												<input type="hidden" name="earnBudgetMap['<%=number%>'].delStatus"
													value="${delStatus}"/>
												
												<a href="#" class="ajaxsc"><img
														src="<%=basePath%>images/admin_images/gtk-del.png"
														width="16" height="16" title="删除" border="0" /> </a>
											</td>
										</tr>
										<%
											number++;
										%>
									</s:iterator>
									<tr id="kelong" style="display: none;">
										<td height="30" align="center" bgcolor="#FFFFFF" width="32px">
											<input id="goodsNomber" name="goodsNomber" class="input"
												readonly style="width: 13px" />
											<input id="goodsID" name="goodsID" value="" type="hidden" />
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF">

											<input type="text" value="${month}"
												name="month" id="month" size="3"
												onfocus="WdatePicker({skin:'whyGreen',dateFmt:'MM'})"
												class="Wdate put3" />
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF">

											<input id="productNum" class="bordernone" name="productNum"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="productName" class="bordernone" name="productName"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="productUnit" class="bordernone" name="productUnit"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="productStandard" class="bordernone"
												name="productStandard" value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="weight" class="bordernone" name="weight" value=""
												readonly />
										</td>

										<td align="left" bgcolor="#FFFFFF">
											<input name="bunitPrice" value="${bunitPrice}"
												style="width: 70px" id="bunitPrice"
												class="jisuan put3 isNaN budget" />
										</td>
										<td class="tiao" align="left" bgcolor="#FFFFFF">
											<input name="tunitPrice" value="${tunitPrice}"
												style="width: 70px" id="tunitPrice"
												class="jisuant isNaN" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="bdquantity" value="${bdquantity}"
												style="width: 70px" id="bdquantity"
												class="jisuan put3 isNaN budget" />
										</td>
										<td class="tiao" align="center" bgcolor="#FFFFFF">
											<input name="tdquantity" value="${tdquantity}"
												style="width: 70px" id="tdquantity"
												class="jisuant isNaN" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="bdamount" class="bordernone" name="bdamount"
												value="" readonly />
										</td>
										
										<td class="tiao" align="center" bgcolor="#FFFFFF">
											<input id="tdamount" class="bordernone" name="tdamount"
												value="" readonly />
										</td>

										<td align="center" bgcolor="#FFFFFF">
											<input id="bwamount" class="bordernone" name="bwamount"
												value="" readonly />
										</td>
										<td class="tiao" align="center" bgcolor="#FFFFFF">
											<input id="twamount" class="bordernone" name="twamount"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="bmamount" class="bordernone" name="bmamount"
												value="" readonly />
										</td>
										<td class="tiao" align="center" bgcolor="#FFFFFF">
											<input id="tmamount" class="bordernone" name="tmamount"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="bsamount" class="bordernone" name="bsamount"
												value="" readonly />
										</td>
										<td class="tiao"  align="center" bgcolor="#FFFFFF">
											<input id="tsamount" class="bordernone" name="tsamount"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="byamount" class="bordernone" name="byamount"
												value="" readonly />
										</td>
										<td  class="tiao" align="center" bgcolor="#FFFFFF">
											<input id="tyamount" class="bordernone" name="tyamount"
												value="" readonly />
										</td>
										<td align="center" bgcolor="#FFFFFF">
                                             <input id="manual" type="hidden" name="manual" value="" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										
											<input id="productPublicity" type="hidden"
												name="productPublicity" value="" />

										</td>
										<td align="center" bgcolor="#FFFFFF">
											
											<input id="companyFile" type="hidden" name="companyFile"
												value="" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="klsc"> <img
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
							<input type="hidden" class="ACT_btn see" name="button4" value="导入数据" />
							<input type="button" class="ACT_btn see" name="button4" value="选择产品"
								id="shuju" />
							<input type="button" class="ACT_btn see" name="button5"
								value="选择往来单位" id="xzwlaw" />
							<input type="button" class="ACT_btn see" name="button4"
								value="选择往来个人" id="xzwlgr" />
							<input type="button" class="ACT_btn see" id="newG" name="button7"
								value="选择设备" />
						</td>
					</tr>

					<tr>
						<td>
							财务仓库：
							<input type="hidden" name="earnBudgetBill.bankDepotID"
								id="bankDepotID" />
							<input name="earnBudgetBill.bankDepotName" id="bankDepotName"
								value="${earnBudgetBill.bankDepotName}" size="15"
								readonly="readonly" />
							<a href="#" class="tobankdepotID see">选择库房</a>
						</td>
						<td>
							实物仓库：
							<input type="hidden" name="earnBudgetBill.discountMoney"
								id="discountMoney" />
							<input name="earnBudgetBill.afterDiscount" id="afterDiscount"
								value="${earnBudgetBill.afterDiscount}" size="15"
								readonly="readonly" />
							<a href="#" class="todepotID see">选择库房</a>
						</td>
						<td>
							资料仓库：
							<input type="hidden" name="earnBudgetBill.dataDepotID"
								id="dataDepotID" />
							<input name="earnBudgetBill.dataDepotName" id="dataDepotName"
								value="${earnBudgetBill.dataDepotName}" size="15"
								readonly="readonly" />
							<a href="#" class="todatadepotID see">选择库房</a>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="25" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${earnBudgetBill.ccompanyName}</span>
							<input type="hidden" id="ccompanyID"
								name="earnBudgetBill.ccompanyID"
								value="${earnBudgetBill.ccompanyID}" />
							<input type="hidden" id="ccompanyname"
								name="earnBudgetBill.ccompanyName"
								value="${earnBudgetBill.ccompanyName}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${earnBudgetBill.companyTel}</span>
							<input type="hidden" id="companyTel"
								name="earnBudgetBill.companyTel"
								value="${earnBudgetBill.companyTel}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${earnBudgetBill.cresponsible}</span>
							<input type="hidden" id="cresponsible"
								name="earnBudgetBill.cresponsible"
								value="${earnBudgetBill.cresponsible}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${earnBudgetBill.accountNum}</span>
							<input id="accountNum" type="hidden"
								name="earnBudgetBill.accountNum"
								value="${earnBudgetBill.accountNum}" />
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
							<span id="responsibleTel" class="qk">${earnBudgetBill.responsibleTel}</span>
							<input type="hidden" id="responsibleTel"
								name="earnBudgetBill.responsibleTel"
								value="${earnBudgetBill.responsibleTel}" />
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${earnBudgetBill.companyAddr}</span>
							<input type="hidden" id="companyAddr"
								name="earnBudgetBill.companyAddr"
								value="${earnBudgetBill.companyAddr}" />
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${earnBudgetBill.industryType}</span>
							<input type="hidden" id="industryType"
								name="earnBudgetBill.industryType"
								value="${earnBudgetBill.industryType}" />
						</td>
						<td height="25" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${earnBudgetBill.ccompanyRelationship}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="earnBudgetBill.ccompanyRelationship" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="25" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${earnBudgetBill.cstaffName}</span>
							<input type="hidden" id="contactUserID"
								name="earnBudgetBill.cstaffID"
								value="${earnBudgetBill.cstaffID}" />
							<input type="hidden" id="contactUserName"
								name="earnBudgetBill.cstaffName"
								value="${earnBudgetBill.cstaffName}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${earnBudgetBill.reference}</span>
							<input type="hidden" id="tel" name="earnBudgetBill.reference"
								value="${earnBudgetBill.reference}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${earnBudgetBill.staffIdentityCard}</span>
							<input type="hidden" id="staffIdentityCard"
								name="earnBudgetBill.staffIdentityCard"
								value="${earnBudgetBill.staffIdentityCard}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${earnBudgetBill.userAccountNum}</span>
							<input id="userAccountNum" type="hidden"
								name="earnBudgetBill.userAccountNum"
								value="${earnBudgetBill.userAccountNum}" />
							<select style="display: none" id="userNum" name="userAccountNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${earnBudgetBill.referenceCode}</span>
							<input id="userQq" type="hidden"
								name="earnBudgetBill.referenceCode"
								value="${earnBudgetBill.referenceCode}" />
							<select style="display: none" id="referenceCode">
								<option value="">
									请选择
								</option>
							</select>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${earnBudgetBill.referenceOrganization}</span>
							<input id="email" type="hidden"
								name="earnBudgetBill.referenceOrganization"
								value="${earnBudgetBill.referenceOrganization}" />
							<select style="display: none" id="referenceOrganization">
								<option value="">
									请选择
								</option>
							</select>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<input id="userAddr" type="hidden"
								name="earnBudgetBill.staffAddress"
								value="${earnBudgetBill.staffAddress}" />
							<span id="userAddr" class="qk">${earnBudgetBill.staffAddress}</span>
							<select style="display: none" id="staffAddress">
								<option value="">
									请选择
								</option>
							</select>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${earnBudgetBill.cstaffRelationship}</span>
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择"
								name="earnBudgetBill.cstaffRelationship" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="goods"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="8%" height="30" align="right">
							<span class="STYLE1">车牌号：</span>
						</td>
						<td width="8%">
							<input type="text" id="" style="border: 0;" name="" value=""
								readonly="readonly" />
						</td>
						<td width="8%" height="30" align="right">
							<span class="STYLE1">设备编码：</span>
						</td>
						<td width="8%">
							<input type="text" id="goodsCoding" style="border: 0;"
								name="earnBudgetBill.goodsCoding"
								value="${earnBudgetBill.goodsCoding}" readonly="readonly" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">设备名称：</span>
						</td>
						<td width="10%">
							<input type="text" id="goodsName" style="border: 0;"
								name="earnBudgetBill.goodsName"
								value="${earnBudgetBill.goodsName}" readonly="readonly" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">统一分类条码：</span>
						</td>
						<td width="15%">
							<input type="text" id="defaultStorage" style="border: 0;"
								name="earnBudgetBill.defaultStorage"
								value="${earnBudgetBill.defaultStorage}" readonly="readonly" />
						</td>
						<td width="5%" align="right">
							<span class="STYLE1">设备品牌：</span>
						</td>
						<td width="12%">
							<input type="text" id="mnemonicCode" style="border: 0;"
								name="earnBudgetBill.mnemonicCode"
								value="${earnBudgetBill.mnemonicCode}" readonly="readonly" />
						</td>

					</tr>
					<tr>
						<td width="10%" align="right">
							<span class="STYLE1">设备品牌规格：</span>
						</td>
						<td width="15%">
							<input type="text" id="standard" style="border: 0;"
								name="earnBudgetBill.standard"
								value="${earnBudgetBill.standard}" readonly="readonly" />
						</td>
						<td height="30" align="right">
							<span class="STYLE1">设备类型：</span>
						</td>
						<td>
							<input type="text" id="typeID" style="border: 0;"
								name="earnBudgetBill.typeID" value="${earnBudgetBill.typeID}"
								readonly="readonly" />
						</td>
						<td align="right">
							<span class="STYLE1">设备型号：</span>
						</td>
						<td>
							<input type="text" id="model" style="border: 0;"
								name="earnBudgetBill.model" value="${earnBudgetBill.model}"
								readonly="readonly" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">设备单位：</span>
						</td>
						<td width="15%">
							<input type="text" id="variableID" style="border: 0;"
								name="earnBudgetBill.variableID"
								value="${earnBudgetBill.variableID}" readonly="readonly" />
						</td>
						<td align="right" width="10%">
							<span class="STYLE1">默认规格：</span>
						</td>
						<td>
							<input type="text" id="acquiesceStandard" style="border: 0;"
								name="earnBudgetBill.acquiesceStandard"
								value="${earnBudgetBill.acquiescestandard}" readonly="readonly" />
						</td>

					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0" 
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
				
							<input type="button" class="btn001 JQueryprint" name="button"
								value="打印预览" />
							<input type="button" class="btn001 JQueryunitret see" name="button"
								value="重置往来单位" />
							<input type="button" class="btn001 JQuerypersonret see" name="button"
								value="重置往来个人" />
							<input type="button" class="btn001 JQuerySubmitgd see" name="button3"
								value="确定预算" />
							<input type="button" class="btn001 JQuerySubmit see" name="button4"
								value="  保存 " />

							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>



		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
				<div class="close"></div>
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>



		<%------------------------------------选择产品------------------------------------%>
		<form name="selectproductForm" id="selectproductForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="productjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择公司产品
						</div>
					</div>
					<table width="99%" height="33" id="searchproduct" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								产品名称/编号：
							</td>
							<td width="60">
								<input name="parameter" class="input" id="parameter" size="15"
									style="margin-left: 2px; margin-right: 10px;" />
							</td>

							<td height="33">
								<input type="button" class="btn02" id="searchpp" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="addproduct"
									name="button5" value="确定" />
								<input type="button" class="btn02 pxzdw" name="button"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="pdwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="pdwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="pdwzy">共&nbsp;&nbsp; <span style="color: red"
									id="pzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02pr"
									style="margin-top: 2px; display: none; width: 100%; overflow: auto; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<%------------------------------------设备选择------------------------------------%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择物品
							<div class="close">
							</div>
						</div>
					</div>
					<table width="99%" height="33" id="searchgood2" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="112">
								<input name="parameter" class="input" id="parameter2" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood2"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood2"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp2" name="button"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms2" id="parms2" />
							</td>
							<td width="40">
								<a id="wpsy2" title="0">上一页</a>
							</td>
							<td width="40">
								<a id="wpxy2" title="0">下一页</a>
							</td>
							<td width="60">
								<a id="wpzy2">共&nbsp;&nbsp; <span style="color: red"
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
											<div id="aadTree2" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_022"
									style="margin-top: 2px; display: none; height: 320px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<%------------------------------------选择往来个人------------------------------------%>
		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="userjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="40" align="right">
								姓名：
							</td>
							<td width="50">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="100">
								<s:select list="codeRelationList" listKey="codeValue"
									listValue="codeValue" headerKey="" headerValue="--全部--"
									id="relation" name="relation" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchuu" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qduser" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzgr" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>


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




	</body>
</html>

