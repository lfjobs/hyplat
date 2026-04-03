<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>单据--修改</title>
		
		<%@page import="hy.ea.bo.Company"%>
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
			<link href="<%=basePath%>css/css.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/splitbill_edit.js"></script>
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
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
			
<style type="text/css">
		

   
   #table3 input{
   
   height:20px;
   }
   
#querya{

cursor:pointer;
}
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

.bitian {
	color: red;
}
<!--

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

.bg td.tit,th.tit {
	background-color: #e2e2e2;
	color: #000;
	height: 17px;
	text-align: center;
	line-height: 15px;
}

.bg td.num,ht.num {
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


  
     #CostSheetForm  td
        {
            white-space: nowrap;
        }
  .querybtn{
  background:url(<%=basePath%>images/search16.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;

  
  }
  
    .addbtn{
  background:url(<%=basePath%>images/u15.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  vertial-align:middle;

  
  }  
  .caz{
  
  display:none;
  }
  
  #menu .btn001{
  margin:0px;
  }
  
    .menubtn{
  border:1px solid #FFFFFF;
  width:70px;
  background:#dbe5f1;
  color:#000000;
  font-size:10pt;
  font-weight:bold;
  vertial-align:middle;
  cursor:pointer;
  height:26px;
  }
  
    
  .grey{
  color:#aca899;
  
  }
 
</style>
		<script type="text/javascript">
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var tokens = 0;
	var basePath = "<%=basePath%>";
	var deptID="${costSheetBill.departmentID}";
	var token = 0;
	var pNumber="${pageNumber}";
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var notoken = 0;
	var journalNum = "";
	var select=1;
	var treename="";
	var depotPID="";
	var depotID="";
	var type="${type}";
	var status="${cashierBills.status}";
	var billstype="${cashierBills.billsType}";
	var jumptype = "${jumptype}";
	var xmtype="${xmtype}";
	var xmtypename="${param.xmtypename}";
	var billID = "${billID}";
	var summary="${summary}";
	var zorg = "${zorg}";
	var zorgname = "${zorgname}";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	</script>
   <script type="text/javascript">
		
	function MouseDownToResize(obj) {
		setTableLayoutToFixed();
		obj.mouseDownX = event.clientX;
		obj.pareneTdW = obj.parentElement.offsetWidth;
		obj.pareneTableW = goodtable.offsetWidth;
		obj.setCapture();
	}

   
	function MouseMoveToResize(obj) {
		if (!obj.mouseDownX)
			return false;
		var newWidth = obj.pareneTdW * 1 + event.clientX * 1 - obj.mouseDownX;
		if (newWidth > 0) {
			obj.parentElement.style.width = newWidth;
			goodtable.style.width = obj.pareneTableW * 1 + event.clientX * 1
					- obj.mouseDownX;
		}
	}
	function MouseUpToResize(obj) {
		obj.releaseCapture();
		obj.mouseDownX = 0;
	}
	function setTableLayoutToFixed() {
		if (goodtable.style.tableLayout == 'fixed')
			return;
		var headerTr = goodtable.rows[0];
		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
		}

		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
		}
		goodtable.style.tableLayout = 'fixed';
	}
</script>

	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<div id="apDiv1"></div>
		<!-- 项目预算添加表单开始 -->
		<form name="CostSheetForm" id="CostSheetForm" method="post"
			enctype="multipart/form-data">
			
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td style="height:24px;" align="left" id="menu">
								<input type="button"  class="menubtn JQuerySubmitgd" value="提交审核" />
								<input type="button" class="menubtn print" name="button3" value="打印" />
								<input type="button" class="menubtn addline"  name="button3" value="增行" />
								<input type="button" class="menubtn deleteline" name="button3" value="删行" />
								<input type="button" class="menubtn shengcheng" style="width:140px"  name="button3" value="确认预算生成收款单" />
								<input type="button" class="menubtn" name="button3" value="帮助" />
								<input type="button" class="menubtn closewindow" value="关闭" />
						</td>
					</tr>
				</table>
			
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
			<center>
				<div class="contentbannb" style="width:100%;">
				
					<div class="divtx" style="font-size:18px;margin-left:30%;margin-top:10px;">
						${cashierBills.billsType}
					</div>
			
				</div>
				</center>
				<table  class="linetable" id="table3"
					 >
					<tr>
						<td width="10%" height="20" align="right" class="nolinetd">
							凭证号：
						</td>
						<td  width="15%" >
							<input type="text" style="width: 200px; border: 0"
								id="journalNum" name="cashierBills.journalNum"
								readonly="readonly" value="${cashierBills.journalNum}"/>
								<input type="hidden"  name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}"/>
								<input type="hidden"  name="cashierBills.cashierBillsKey" value="${cashierBills.cashierBillsKey}"/>
						</td>
						<td width="10%"  align="right" class="nolinetd">
							制单人：
						</td>
	     				 	<td width="15%" >
							${cashierBills.inputName}<input type="hidden" value="${cashierBills.status}" name="cashierBills.status"/>
						</td>
						<td  width="9%"  align="right" class="nolinetd">
							责任人：
						</td>
						<td width="15%">
						    ${cashierBills.staffName}
						</td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1">票据支票管理：</span>
						</td>
						<td>
							<input type="text" name="cashierBills.billCheck" class="model1 xiaoguo"  style="display: inline;width:100%;border:0px;"  value="${cashierBills.billCheck}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" class="nolinetd">
							公司：
						</td>
						<td>
							<span><%=c.getCompanyName()%></span>
						</td>
						<td align="right" class="nolinetd">
							部门：
						</td>
	     					<td id="dept">
							${cashierBills.departmentName}
						</td>
						<td align="right" class="nolinetd">
							银行账号：
						</td>
						<td>
							<input type="text" id="bankaccount" name="cashierBills.companyBankNum"  value="${cashierBills.companyBankNum}"  class="model1 querys"  style="display: inline;width:98%;border:0px;"/>	
						</td>
						
						<td height="20" align="right" class="nolinetd"><span
						class="STYLE1">&nbsp;&nbsp;&nbsp;&nbsp;附：</span></td>
					   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张</td>
					</tr>
					<tr>
					    <td align="right" class="nolinetd">主项目名称：</td>
						
						<td>${cashierBills.xmtypename}</td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1" class="nolinetd">子项目名称：</span>
						</td>
						<td>
							${cashierBills.projectName}
						</td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1">子项目描述：</span>
						</td>
						<td colspan="3">
						   ${cashierBills.content}
						</td>


					</tr>
			
					
			</table>
        </div>
							<div id="Layer1"
								>
								<table 
									class="table bg auto_arrange .goodtable2" id="goodtable"
									>
									<tr>
										<th align="center" bgcolor="#E4F1FA" width="120px" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> <span class="xx">*</span> 品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="120px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA" width="110px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img style="vertical-align:middle;"
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="14" /> </span>  <span class="xx">*</span>款源日期
										</th>
										
										<th align="center" bgcolor="#E4F1FA" width="90px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> 单位
										</th>
										<th align="center" bgcolor="#E4F1FA" width="90px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="110px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> 单价管理
										</th>
										<th align="center" bgcolor="#E4F1FA" width="90px">
										
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> <span class="xx">*</span>单价
										</th>
										<th align="center" bgcolor="#E4F1FA" width="70px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="180px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="14" /> </span>  <span class="xx">*</span>往来公司
										</th>
										<th align="center" bgcolor="#E4F1FA" width="90px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="14" /> </span>  <span class="xx">*</span>往来个人
										</th>
										<th align="center" width="50px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="14" /> </span> 操作
										</th>
									</tr>
									<%
										int number = 1;
									%>
									<s:iterator value="pageForm.list">
									<tr id="kelong<%=number%>" class="checkgoods">
										<!-- 序号 -->
										<td align="center" bgcolor="#FFFFFF">
										<input type="hidden" name="goodsmap[<%=number%>].goodsID"  value="${goodsID}"/>
										<input type="hidden" name="goodsmap[<%=number%>].goodsBillsID"   value="${goodsBillsID}"/>
										<input type="hidden" name="goodsmap[<%=number%>].goodsBillsKey"   value="${goodsBillsKey}"/>
											${goodsCoding}
										<input  name="goodsmap[<%=number%>].goodsNum"  type="hidden"  value="${goodsCoding}"/> 
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF">
										${goodsName}
											<input  name="goodsmap[<%=number%>].goodsName"   type="hidden"  value="${goodsName}"/>
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF">
										${fn:substring(startDate,0,10)}
											<input name="goodsmap[<%=number%>].startDate" value="${fn:substring(startDate,0,10)}" type="hidden"/>
										
										</td>
										
										<!-- 单位 -->
										<td align="center" bgcolor="#FFFFFF">
										<span>${goodsVariableID}</span>
										<input  name="goodsmap[<%=number%>].goodsVariableID" type="hidden"  value="${goodsVariableID}"/>
										</td>
										<!-- 数量 -->
										<td align="left" bgcolor="#FFFFFF">
											<input id="quantity" name="goodsmap[<%=number%>].quantity" size="10"
												class="model1 jisuan fou" style="display:inline;width: 98%;" value="${quantity}"/>
										</td>
										<td align="center" bgcolor="#FFFFFF" >
											<span style="display:none;">${priceManage}</span>
											<s:select cssClass="dis" list="%{#request.priceManageList}"
												listKey="codeValue" listValue="codeValue" id="priceManage" 
												name="goodsmap[<%=number%>].priceManage" theme="simple" style="border:none;width:98%;"></s:select>
										</td>
										<!-- 单价 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="price" name="goodsmap[<%=number%>].price" class="jisuan model1 fou"
												 style="display:inline;width:98%;" value="${price}"/>
										</td>
										<!-- 总金额 -->
										<td align="center" bgcolor="#FFFFFF">
											<input name="goodsmap[<%=number%>].money" id="money" size="3" readonly
												style="width:100%; border:0px;" value="${money}" />
										</td>
										
										<td align="left" bgcolor="#FFFFFF">
											<input type="hidden" id="ccompanyID" name="goodsmap[<%=number%>].ccompanyID" value="${ccompanyID}" />
											<input type="text" id="ccompanyName" value="${ccompanyName}"
												style="width:88%;display:inline;" name="goodsmap[<%=number%>].ccompanyName"  class="querys model1 fou" />
											
										     <input type="btn" class="wanladw querybtn caz" />
										</td>
										<td align="left" bgcolor="#FFFFFF">
											<input type="hidden" id="connectID" name="goodsmap[<%=number%>].connectID"  value="${connectID}"/>
											<input type="text"  id="connectName"  class="querys model1 fou" value="${connectName}"
												 name="goodsmap[<%=number%>].connectName" style="width:77%;display:inline;"/>
										     <input type="btn" class="wlgr querybtn caz"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" />
											</a>
										</td>
									</tr>
									<% number++;%>
									</s:iterator>
								  <tr id="kelong" style="display:none;">
										<td align="center" bgcolor="#FFFFFF">
										<input type="hidden" name="goodsID" id="goodsID" />
											<input id="goodsNum" name="goodsNum" style="width:86%;height:100%;border:0px;"  class="querys fou"  readonly="readonly" 
												/>&nbsp;<input type="btn" class="shuju querybtn caz" />
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="goodsName" name="goodsName" style="width:86%;height:100%;border:0px;"  class="querys fou" 
												 readonly="readonly"/>&nbsp;<input type="btn" class="shuju querybtn caz"/>
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF">
											<input name="startDate" onfocus="date(this)" 
												id="username" style="width:96%;height:100%;display: inline;" class="model1 xiaoguo fou" />
										</td>
										
										<!-- 单位 -->
										<td align="center" bgcolor="#FFFFFF">
											<span >${goodsVariableID}</span>
												<select class="dis fou" id="goodsVariableID" style="border:none;height:100%;width:100%;"
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
										<!-- 数量 -->
										<td align="left" bgcolor="#FFFFFF">
											<input id="quantity" name="quantity" 
												class="model1 jisuan  fou" 
												style="display:inline;height:100%;width:98%;;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="display:none;">${priceManage}</span>
											<s:select cssClass="dis" list="%{#request.priceManageList}"
												listKey="codeValue" listValue="codeValue" id="priceManage" 
												name="priceManage" theme="simple" style="border:none;height:100%;width:98%;"></s:select>
										</td>
										<!-- 单价 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="price" name="price" class="jisuan model1  fou"
												 style="display:inline;height:100%;width:98%" />
										</td>
										<!-- 总金额 -->
										<td align="center" bgcolor="#FFFFFF">
											<input name="money" id="money" size="3" readonly
												style="width:100%;height:100%; border:0px;" />
										</td>
										<td align="left" bgcolor="#FFFFFF">
											<input type="hidden" id="ccompanyID" name="ccompanyID" />
											<input type="text" id="ccompanyName"
												style="width:88%;height:100%;display:inline;" name="ccompanyName"  class="querys model1 fou" />
										     <input type="btn" class="wanladw querybtn caz" />
										</td>
										<td align="left" bgcolor="#FFFFFF">
											<input type="hidden" id="connectID" name="connectID" />
											<input type="text"  id="connectName"  class="querys model1 fou"
												 name="connectName" style="width:77%;height:100%;display:inline;"/>
										     <input type="btn" class="wlgr querybtn caz" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										
											<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" />
											</a>
										</td>
									</tr>
									<tr style="display:none;"><td id="line"></td></tr>
							</table>
						</div>
		
			
			<s:token></s:token>
		</form>
		<!-- 项目预算添加表单结束 -->
		<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
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
							<td width="142">
								<input name="typeID" class="input" id="typeID" size="20"
									style="margin-left: 2px;" />
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
								<input type="hidden" id="clicktr" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页 </a>
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
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
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
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								单位名称：
							</td>
							<td width="142">
								<input name="ccompanyID" class="input" id="ccompanyIDs" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									<input type="hidden" name="parms" id="dwparms" />
			
							</td>
							<td width="80">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="dwzycount"></span>&nbsp;&nbsp;页 </a>
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
											<div id="dwTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
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
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								姓名：
							</td>
							<td width="142">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
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
									<input type="hidden" name="parms" id="grparms" />
			
							</td>
							<td width="80">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp;页 </a>
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
											<div id="grTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
	<%------------------------------------各种查询框------------------------------------%>
    
	<div id="goodsQuery" style="white-space:wrap;display:none;width:950px;height:320px;background:#E4F1FA;border:#a8c7ce 1px solid">
	  <div class="divtx" style="height:30px;line-height:30px;">查询</div><div class="close closes"></div>
	  <div style="width:950px;height:270px;overflow:auto;background:#E4F1FA;">
		<table class="table" cellpadding="10"  width="100%">
		<thead id="goodthead">
			
			</thead>
			<tbody id="goodboy">
			
			
			</tbody>
		</table>
        </div>
        <center>
		<table id="querya">
			<tr>
				<td width="80"><a id="wpsyq" style="cursor:pointer;" title="0" >上一页</a></td>
				<td width="80"><a id="wpxyq"  title="0" style="cursor:pointer;">下一页</a></td>
				<td width="100"><a id="wpzyq">共&nbsp;&nbsp; <span
						style="color: red;" id="wpzycountq"></span>&nbsp;&nbsp;页 </a>
						<input type="hidden"  id="querys"/>
					    <input type="hidden"  id="types"/>
						</td>
			</tr>
		</table>
		</center>

	</div>
	
	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
			
</body>
</html>

