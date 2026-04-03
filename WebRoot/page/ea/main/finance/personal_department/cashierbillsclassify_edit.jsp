<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="hy.ea.bo.Company"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>部门分单据录入</title>
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
			src="<%=basePath%>js/ea/finance/cashierbillsclassify_edit.js"></script>
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
	left: 750px;
	top: 387px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
.bitian
{
color: red;
}

<!-- ----表格拖动列宽 -->

.bg table {
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

.bg td.tit           ,th.tit {
	background-color: #e2e2e2;
	color: #000;
	height: 17px;
	text-align: center;
	line-height: 15px;
}

.bg td.num           ,ht.num {
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
	var cashierBillsID="${cashierBills.cashierBillsID}";
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
   	var differenceStyle="${differenceStyle}";
	var types="${BType}";
	var quzhi="";
	var level="${level}";
	var aa=0;
	
	var jnumber='';//用于预算资金
	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID;
		 }
		 if(checkopertionName=="partnerName"){
		 	url = url + "?title1=title1";
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
			$("#staffName",$("#"+checkform)).attr("value",no);
		$("#staffCode",$("#"+checkform)).attr("value",childopertionName);
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
});
</script>
		

		<script> 
function MouseDownToResize(obj){ 
setTableLayoutToFixed(); 
obj.mouseDownX=event.clientX; 
obj.pareneTdW=obj.parentElement.offsetWidth; 
obj.pareneTableW=goodtable.offsetWidth; 
obj.setCapture(); 
} 
function MouseMoveToResize(obj){ 
    if(!obj.mouseDownX) return false; 
    var newWidth=obj.pareneTdW*1+event.clientX*1-obj.mouseDownX; 
    if(newWidth>0) 
    { 
obj.parentElement.style.width = newWidth; 
goodtable.style.width=obj.pareneTableW*1+event.clientX*1-obj.mouseDownX; 
} 
} 
function MouseUpToResize(obj){ 
obj.releaseCapture(); 
obj.mouseDownX=0; 
} 
function setTableLayoutToFixed() 
{ 
 if(goodtable.style.tableLayout=='fixed') return; 
   var headerTr=goodtable.rows[0]; 
    for(var i=0;i<headerTr.cells.length;i++) 
    { 
    headerTr.cells[i].styleOffsetWidth=headerTr.cells[i].offsetWidth; 
    } 
     
    for(var i=0;i<headerTr.cells.length;i++) 
    { 
    headerTr.cells[i].style.width=headerTr.cells[i].styleOffsetWidth; 
    } 
    goodtable.style.tableLayout='fixed'; 
} 
</script>
	
		<!-- 	<SCRIPT language="javascript">  
function  test(obj)  
{  
       var  range  =  obj.createTextRange();  
       alert("内容区宽度:  "  +  range.boundingWidth    
                                                 +  "px\r\n内容区高度:  "  +  range.boundingHeight  +  "px");  
             
}  
</SCRIPT> -->	
	</head>
	<body style="overflow-y: auto;">
			<%-- 用来显示审核章 --%>
		<div id="apDiv1"></div>
		<form name="cashierTallyForm" id="cashierTallyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						${billsType}据
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

							<input type="hidden" name="cashierBills.cashierBillsID"
								id="cashierID" value="${cashierBillsVO.cashierBillsID}" />
							<input type="hidden" name="cashierBills.cashierBillsKey"
								id="goodsBillsKey" value="${cashierBillsVO.cashierBillsKey}" />
							<input type="hidden" name="cashierBills.depStatue" id="depStatue"
								value="00" />
							<input type="text" style="width: 200px; border: 0"
								value="<c:if test="${cashierBillsVO.journalNum!=null}">${cashierBillsVO.journalNum }</c:if><c:if test="${cashierBillsVO.journalNum==null}">自动生成</c:if>"
								id="journalNum" name="cashierBills.journalNum"
								readonly="readonly" />
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							<span id="spanBillsType">${billsType}</span> 

						</td>
						<td align="right">
							责任人：
						</td>
						<td width="15%">
							<!--
							<input type="hidden" id="partnerID" name="cashierBills.staffID"
								readonly="readonly" value="${cashierBillsVO.staffID}" />
							<input type="text" id="partnerName" name="partnerName" class="hide"
								readonly="readonly"
								value="${cashierBillsVO.staffname}"
								size="15" />
							 -->

							<input type="hidden" id="partnerID" name="cashierBills.staffID"
								readonly="readonly" value="${cashierBillsVO.staffID}" />
							<input type="text" id="partnerName" name="partnerName"
								readonly="readonly"
								value="<c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}---${cashierBillsVO.recordcode}</c:if>"
								size="15" />
							<a href="#"
								onclick="importGY('table3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
							<input type="hidden" id="staffName" name="cashierBills.staffName" value="${cashierBillsVO.staffname}"/>
							<input type="hidden" id="staffCode" name="cashierBills.staffCode" value="${cashierBillsVO.recordcode}"/>
						</td>
						<td width="10%" align="right">
							票据支票管理:
						</td>
						<td width="15%">
							<input name="cashierBills.billCheck" id="billCheck"
								value="${cashierBillsVO.billCheck }" />
						</td>
						<!--制单日期 <input name="cashierBills.cashierDate" class="input"  onfocus="date(this);"  value="${fn:substring(cashierBillsVO.cashierDate, 0, 10)}" style="margin-left:2px;width: 170px" /> -->
					</tr>

					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames"></span>
						</td>
						<td align="right">
							部门：<input type="hidden" id="departmentName" name="cashierBills.departmentName"/>
						</td>
						<td align="left" id="dept">
							<input class="classhide " type="text"
								value="${cashierBillsVO.departmentname}" readonly="readonly" />
							<input type="hidden" name="cashierBills.departmentID"
								value="${cashierBillsVO.departmentID}" />
							<a href="#" title="departmentID" class="classhide update">修改</a>
							<select name="departmentID" id="departmentID"
								style="width: 180px;"></select>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td align="left" colspan="2">
							<!--  
							 <input type="text" id="bankNum" class="hide"
								name="cashierBills.companyBankNum" readonly="readonly"
								value="${cashierBillsVO.companyBankNum}" size="45" />
						 -->
							<input type="text" id="bankNum"
								name="cashierBills.companyBankNum" readonly="readonly"
								value="${cashierBillsVO.companyBankNum}" size="45" />
							<a href="#"
								onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')">选择</a>

						</td>
						<td align="left" colspan="3">
							附
							<input type="text" id="pieces"
								name="cashierBills.pieces" readonly="readonly"
								value="" size="3" />
							张
						</td>
					</tr>
				</table>

				<table width="99%" height="150px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 150px; overflow: scroll;">
								<table align="center" cellpadding="0" cellspacing="0"
									class="table bg auto_arrange" id="goodtable"
									style="overflow: hidden;">
									<tr>
										<th align="center" bgcolor="#E4F1FA" width="26px" >
											<font >序号</font>
											<span class="resizeDivClass" 
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);">
												<img
													src="<%=basePath%>/images/header_bg.gif" width="10"
													height="30"/>
											</span> 
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="30"/>
											</span> 款源日期
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 入账日期
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 有效日期
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 批号/期号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 品名编号
										</th>
										<!-- 
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 票据编号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 标识条码
										</th>
										<th align="center" bgcolor="#E4F1FA" width="32px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 芯片号
										</th>
										 -->
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 科目
										</th>
											<th align="center" bgcolor="#E4F1FA" width="50px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 摘要
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 品名名称
										</th>
										
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 费用类别
										</th>
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 类型
										</th>
										<th align="center" bgcolor="#E4F1FA" width="20px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 品牌
										</th>
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 型号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 品牌规格
										</th>
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 单位
										</th>
										<th align="center" bgcolor="#E4F1FA" width="18px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="18px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 重量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="30px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 箱规格
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 单价管理
										</th>
										<th align="center" bgcolor="#E4F1FA" width="20px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 单价
										</th>
										<th align="center" bgcolor="#E4F1FA" style="display: none;"
											width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 库房管理
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 借方金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="38px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 贷方金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 方向
										</th>
										<th align="center" bgcolor="#E4F1FA" width="24px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 余额
										</th>
									
										<!-- 
										<th align="center" width="30px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 其他金额
										</th>
										<th align="center" width="38px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 付款方式
										</th>
										<th align="center" width="38px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 物流方式
										</th>
										<th align="center" width="38px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 用款原因
										</th>
										<th align="center" width="38px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="30"/>
											</span> 提醒内容
										</th>
										 -->
										<th align="center" width="24px" bgcolor="#E4F1FA">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="30"/>
											</span> 操作
										</th>
									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods" id="${goodsBillsID}">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="goodsNomber">${goodsNomber}</span>
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
												<input name="endDate" class="model1" id="username4" value=""
													readonly="readonly"
													style="margin-left: 2px; padding: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;"
													size="10" />
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
											<!-- 
											<td align="center" bgcolor="#FFFFFF">
												<span id="defaultStorage" class="bhide">${defaultStorage}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="billsNumbers">${billsNumbers}</span>
												<input name="billsNumbers" value="${billsNumbers}"
													class="model1" id="billsNumbers" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="identifyingCode">${identifyingCode}</span>
												<input name="identifyingCode" value="${identifyingCode}"
													class="model1" id="identifyingCode"
													style="margin-left: 2px;" />
											</td>
											 -->
											<td align="left">
												<span class="bitian">*</span>
												<input type="hidden" value="${subjectsID}" id="subjectsID"
													name="subjectsID" />
												<span id="subjectsName">${subjectsName}</span>
												<input type="text" readonly="readonly"
													value="${subjectsName}" class="put3 model1" size="8"
													id="subjectsName" name="subjectsName" />
												<br /><a href="#" class="tosubjects model1">选择科目</a>
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="reasonThing">${reasonThing}</span>
												<input name="reasonThing" value="${reasonThing}"
													class="model1" id="reasonThing" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsName" class="bhide">${goodsName}</span>
											</td>
									
											<td align="center" bgcolor="#FFFFFF">
												<span id="costType">${costType}</span>
												<s:select class="dis" list="%{#request.costTypeList}"
													listKey="codeValue" listValue="codeValue" id="costType"
													name="costType" theme="simple"></s:select>
												<br /><a href="#" class="model1"
													onclick="toCCode('scode20101101dfs3uhdprp0000000030','#costType','#cashierTallyForm')">新添</a>
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
												<br /><a href="#" class="model1"
													onclick="toCCode('scode20101101dfs3uhdprp0000000032','#priceManage','#cashierTallyForm')">新添</a>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="price">${price}</span>
												<input name="price" class="jisuan isNaN  model1"
													value="${price}" class="input" id="price" size="5"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF" style="display: none;">
												<span id="money">${money}</span>
												<input name="money" readonly="readonly" value="${money}"
													type="hidden" class="model1" id="money" size="5"
													style="margin-left: 2px;" />
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
												<input value="${direction}"	name="direction" id="direction"
													 size="1" style="border: 0" class="model1"/>
												<!--<s:select class="dis" list="#{'借':'借','贷':'贷','其它':'其它'}"
													name="direction" id="direction" theme="simple"></s:select>-->
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="balance">${balance}</span>
												<input name="balance" value="${balance}"
													class="isNaN model1" id="username5" size="3"
													style="margin-left: 2px;" />
											</td>
													
											<!-- 
											<td align="center" bgcolor="#FFFFFF">
												<span id="otherAmount">${otherAmount}</span>
												<input name="otherAmount" value="${otherAmount}"
													class="isNaN model1" id="otherAmount" size="3"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="payType">${payType}</span>
												<s:select class="dis" list="%{#request.payTypeList}"
													listKey="codeValue" listValue="codeValue" id="payType"
													name="payType" theme="simple"></s:select>
												<br /><a href="#" class="model1"
													onclick="toCCode('scode20101101dfs3uhdprp0000000031','#payType','#cashierTallyForm')">新添</a>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="logistics">${logistics}</span>
									 			<s:select class="dis" list="%{#request.logisticsList}"
													listKey="codeValue" listValue="codeValue" id="logistics"
													name="logistics" theme="simple"></s:select>
												<br /><a href="#" class="model1"
													onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')">新添</a>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="moneySpent">${moneySpent}</span>
												<input name="moneySpent" value="${moneySpent}"
													class="model1" id="moneySpent" size="15"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="remindedContent">${remindedContent}</span>
												<input name="remindedContent" value="${remindedContent}"
													class="model1" id="remindedContent" size="15"
													style="margin-left: 2px;" />
											</td>
											 -->
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
									<tr id="kelong" style="display: none;">
										<td height="30" align="center" bgcolor="#FFFFFF" width="32px">
											<input id="goodsNomber" name="goodsNomber" class="input" style="width: 13px"
												/>
										</td>
										<td height="30" align="center" bgcolor="#FFFFFF"
											>
											<input name="startDate" onfocus="daytime(this)" class="input"
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
												style="margin-left: 2px;width: 62px"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" name="goodsID" id="goodsID" />
											<span id="goodsCoding"></span>
										</td>
										<!--
										<td align="center" bgcolor="#FFFFFF">
											<span id="defaultStorage">${defaultStorage}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="billsNumbers" value="${billsNumbers}"
												id="billsNumbers" style="width: 62px"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="identifyingCode" value="${identifyingCode}"
												id="identifyingCode" style="width: 62px"/>
										</td>
										  -->
										<td align="left">
											<span class="bitian">*</span>
											<input type="hidden" id="subjectsID" name="subjectsID" />
											<input type="text" readonly="readonly" id="subjectsName"
												class="notnull" size="8" name="subjectsName" /><br />
											<a href="#" class="tosubjects" >选择科目</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="reasonThing" value="${reasonThing}" style="width: 70px"
												id="reasonThing" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="goodsName"></span>
										</td>
									
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.costTypeList}" listKey="codeValue"
												listValue="codeValue" id="costType" name="costType"
												theme="simple"></s:select>
											<br /><a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000030','#costType','#cashierTallyForm')">新添</a>
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
											<br /><a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000032','#priceManage','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="price" class="jisuan isNaN" id="price" size="5"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
											<input name="money" readonly="readonly" class="input"
												type="hidden" id="money" size="5" style="margin-left: 2px;width: 26px" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="#{'出库':'出库','入库':'入库','库存':'库存','其它':'其它'}"
												name="depotType" id="depotType" theme="simple"></s:select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="loan" class="isNaN" id="loan" size="5"
												style="margin-left: 2px;" readonly="readonly" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="forLoan" class="isNaN" id="forLoan" size="5"
												style="margin-left: 2px;" readonly="readonly" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<input name="direction" id="direction" size="1" style="border: 0" value="贷"/>
											<!--<s:select list="#{'借':'借','贷':'贷','其它':'其它'}"
												name="direction" id="direction" theme="simple"></s:select>-->
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="balance" class="isNaN" id="username5" size="3"
												style="margin-left: 2px;" />
										</td>
											
										<!-- 
										<td align="center" bgcolor="#FFFFFF">
											<input name="otherAmount" value="${otherAmount}" style="width: 38px"
												id="otherAmount" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.payTypeList}" listKey="codeValue"
												listValue="codeValue" id="payType" name="payType"
												theme="simple"></s:select>
											<br /><a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000031','#payType','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.logisticsList}"
												listKey="codeValue" listValue="codeValue" id="logistics"
												name="logistics" theme="simple"></s:select>
											<br /><a href="#"
												onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="moneySpent" id="moneySpent" size="13"
												style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remindedContent" id="remindedContent" size="13"
												style="margin-left: 2px;" />
										</td>
										 -->
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
							<input type="button" class="ACT_btn" name="button4" value="选择项目"
								id="xiangmu" style="display:none;"/>
							<input type="button" class="ACT_btn" name="button5"
								value="选择往来单位" id="xzwlaw" />
							<input type="button" class="ACT_btn" name="button4"
								value="选择往来个人" id="xzwlgr" />
							<input type="button" class="ACT_btn" id="newG" name="button7"
								value="选择设备" />
						</td>
					</tr>
				
					<tr>
						<td>
							财务仓库：
							<input type="hidden" name="cashierBills.bankDepotID"
								id="bankDepotID" />
							<input name="cashierBills.bankDepotName" id="bankDepotName"
								value="${cashierBillsVO.bankDepotName}" size="15"
								readonly="readonly" />
							<a href="#" class="tobankdepotID">选择库房</a>
						</td>
						<td>
							实物仓库：
							<input type="hidden" name="cashierBills.discountMoney"
								id="discountMoney" />
							<input name="cashierBills.afterDiscount" id="afterDiscount"
								value="${cashierBillsVO.afterDiscount}" size="15"
								readonly="readonly" />
							<a href="#" class="todepotID">选择库房</a>
						</td>
						<td>
							资料仓库：
							<input type="hidden" name="cashierBills.dataDepotID"
								id="dataDepotID" />
							<input name="cashierBills.dataDepotName" id="dataDepotName"
								value="${cashierBillsVO.dataDepotName}" size="15"
								readonly="readonly" />
							<a href="#" class="todatadepotID">选择库房</a>
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
							<span id="ccompanyname" class="qk">${cashierBillsVO.ccompanyname}</span>
							<input type="hidden" id="ccompanyID"
								name="cashierBills.ccompanyID"
								value="${cashierBillsVO.ccompanyID}" />
							<input type="hidden" id="ccompanyname"
								name="cashierBills.ccompanyName"
								value="${cashierBillsVO.ccompanyname}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${cashierBillsVO.companyTel}</span>
							<input type="hidden" id="companyTel"
								name="cashierBills.companyTel"
								value="${cashierBillsVO.companyTel}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${cashierBillsVO.cresponsible}</span>
							<input type="hidden" id="cresponsible"
								name="cashierBills.cresponsible"
								value="${cashierBillsVO.cresponsible}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${cashierBillsVO.accountNum}</span>
							<input id="accountNum" type="hidden"
								name="cashierBills.accountNum"
								value="${cashierBillsVO.accountNum}" />
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
							<span id="responsibleTel" class="qk">${cashierBillsVO.responsibleTel}</span>
							<input type="hidden" id="responsibleTel"
								name="cashierBills.responsibleTel"
								value="${cashierBillsVO.responsibleTel}" />
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${cashierBillsVO.companyAddr}</span>
							<input type="hidden" id="companyAddr"
								name="cashierBills.companyAddr"
								value="${cashierBillsVO.companyAddr}" />
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${cashierBillsVO.industryType}</span>
							<input type="hidden" id="industryType"
								name="cashierBills.industryType"
								value="${cashierBillsVO.industryType}" />
						</td>
						<td height="25" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${cashierBillsVO.contactConnections}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="cashierBillsVO.contactConnections" theme="simple"></s:select>
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
							<span id="contactUserName" class="qk">${cashierBillsVO.contactUserName}</span>
							<input type="hidden" id="contactUserID"
								name="cashierBills.contactUserID"
								value="${cashierBillsVO.contactUserID}" />
							<input type="hidden" id="contactUserName"
								name="cashierBills.ctUserName"
								value="${cashierBillsVO.contactUserName}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${cashierBillsVO.tel}</span>
							<input type="hidden" id="tel"
								name="cashierBills.reference"
								value="${cashierBillsVO.tel}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${cashierBillsVO.staffIdentityCard}</span>
							<input type="hidden" id="staffIdentityCard"
								name="cashierBills.staffIdentityCard"
								value="${cashierBillsVO.staffIdentityCard}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${cashierBillsVO.userAccountNum}</span>
							<input id="userAccountNum" type="hidden"
								name="cashierBills.userAccountNum"
								value="${cashierBillsVO.userAccountNum}" />
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
							<span id="userQq" class="qk">${cashierBills.referenceCode}</span>
							<input id="userQq" type="hidden"
								name="cashierBills.referenceCode"
								value="${cashierBills.referenceCode}" />
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
							<span id="email" class="qk">${cashierBills.referenceOrganization}</span>
							<input id="email" type="hidden"
								name="cashierBills.referenceOrganization"
								value="${cashierBills.referenceOrganization}" />
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
								name="cashierBills.staffAddress"
								value="${cashierBills.staffAddress}" />
							<span id="userAddr" class="qk">${cashierBills.staffAddress}</span>
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
							<span id="phone" class="qk">${cashierBills.phone}</span>
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择" name="cashierBillsVO.phone"
								theme="simple"></s:select>
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
							<input type="text" id="" style="border: 0;" 
								name="" value="" readonly="readonly"/>
						</td>
						<td width="8%" height="30" align="right">
							<span class="STYLE1">设备编码：</span>
						</td>
						<td width="8%">
							<input type="text" id="goodsCoding" style="border: 0;" 
								name="cashierBills.goodsCoding" value="${cashierBillsVO.goodsCoding}" readonly="readonly"/>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">设备名称：</span>
						</td>
						<td width="10%">
							<input type="text" id="goodsName" style="border: 0;"
								name="cashierBills.goodsName" value="${cashierBillsVO.goodsName}" readonly="readonly"/>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">统一分类条码：</span>
						</td>
						<td width="15%">
							<input type="text" id="defaultStorage" style="border: 0;" 
								name="cashierBills.defaultStorage" value="${cashierBillsVO.defaultStorage}" readonly="readonly"/>
						</td>
						<td width="5%" align="right">
							<span class="STYLE1">设备品牌：</span>
						</td>
						<td width="12%">
							<input type="text" id="mnemonicCode" style="border: 0;" 
								name="cashierBills.mnemonicCode" value="${cashierBillsVO.mnemonicCode}" readonly="readonly"/>
						</td>
						
					</tr>
					<tr>
					<td width="10%" align="right">
							<span class="STYLE1">设备品牌规格：</span>
						</td>
						<td width="15%">
							<input type="text" id="standard" style="border: 0;" 
								name="cashierBills.standard" value="${cashierBillsVO.standard}" readonly="readonly"/>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">设备类型：</span>
						</td>
						<td>
							<input type="text" id="typeID" style="border: 0;" 
								name="cashierBills.typeID" value="${cashierBillsVO.typeID}" readonly="readonly"/>
						</td>
						<td align="right">
							<span class="STYLE1">设备型号：</span>
						</td>
						<td>
							<input type="text" id="model" style="border: 0;" 
								name="cashierBills.model" value="${cashierBillsVO.model}" readonly="readonly"/>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">设备单位：</span>
						</td>
						<td width="15%">
							<input type="text" id="variableID" style="border: 0;" 
								name="cashierBills.variableID" value="${cashierBillsVO.variableID}" readonly="readonly"/>
						</td>
						<td align="right" width="10%">
							<span class="STYLE1">默认规格：</span>
						</td>
						<td>
							<input type="text" id="acquiesceStandard" style="border: 0;" 
								name="cashierBills.acquiesceStandard" value="${cashierBillsVO.acquiescestandard}" readonly="readonly"/>
						</td>

					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="hidden" name="cashierBills.status"
								id="cashierstatus" value="${cashierBillsVO.status}" />
							<input type="button" class="btn001 JQueryprint" name="button"
								value="打印预览" />
							<input type="button" class="btn001 JQueryunitret" name="button"
								value="重置往来单位" />
							<input type="button" class="btn001 JQuerypersonret" name="button"
								value="重置往来个人" />
							<input type="button" style="display: none;"
								class="btn001 JQuerySubmitgd" name="button3" value="提交审核" />
							<input type="button" style="display: none;"
								class="btn001 JQuerySubmit" name="button4" value="草稿保存" />
							<input type="button" style="display: none;"
								class="btn001 JQueryInvalid" name="button4" value="作废" />
							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>

		<%------------------------------------物品选择------------------------------------%>
		<form name="SubjectsForm" id="SubjectsForm" method="post"
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
											<div id="SubjectsAadTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 320px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		<%------------------------------------选择项目------------------------------------%>
		<form name="billForm" id="billForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;" id="billjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">选择项目</div>
					</div>
					<table width="99%"  id="searchgood" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td align="right">凭证号</td>
							<td ><input id="journalNum"    size="15" name="cashierBills.journalNum" /></td>
							<td >
								<input type="button" class="btn02" id="chaxun" name="button7" value="查询" />
								<input type="button" class="btn02" id="qdbill" name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturnbill" name="button4" value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="50"><a id="wpsy" title="0">上一页</a></td>
							<td width="50"><a id="wpxy" title="0">下一页</a></td>
							<td width="80"><a id="wpzy">共&nbsp;&nbsp; <span style="color: red" id="wpzycount"></span>&nbsp;&nbsp;页</a></td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_03" style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;"></div>
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
								<input type="button" class="btn02 xzwp2" name="button" value="新增" />
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
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>

