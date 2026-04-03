<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>预算单据--查看</title>
		
		<%@page import="hy.ea.bo.Company"%>
		<%@page import="java.util.Date"%>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_edit.js"></script>
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
				<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/xmTree.js"></script>
			
<style type="text/css">
		

   
   #table3 input{
   
   height:20px;
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

  

  

  .baokx{
 display:none;
}
  
 
</style>
		<script type="text/javascript">
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var tokens = 0;
	var basePath = "<%=basePath%>";
	var jumptype = "${jumptype}";
	var status = "${cashierBills.status}";
	var cashierBillsID  = "${cashierBills.cashierBillsID}";
	var xmtype="${cashierBills.xmtype}";
	var  billsType = "${billsType}";
	var zctype="${zctype}";
	var proID = "${cashierBills.proID}";
	var projectName = "${cashierBills.projectName}";
	var select = 1;

  
	


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
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;"><%--
		<div id="apDiv1"></div>
		--%><!-- 项目预算添加表单开始 -->
		<form name="CostSheetForm" id="CostSheetForm" method="post"
			enctype="multipart/form-data">
			
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td style="height:24px;" align="left">
							
							
							
							<input type="button"  class="menubtn JQuerySubmitgd grey"  disabled="disabled"
								value="保存" /><input type="button" class="menubtn print" name="button3"  
								value="打印" /><input type="button" class="menubtn audit" name="button3" 
								value="比价审核" /><input type="button"  class="menubtn newsheet" 
								value="增加" /><input type="button"  class="menubtn updatesheet grey"  disabled="disabled"
								value="修改" /><input type="button"  class="menubtn deletesheet" 
								value="删除" /><input type="button" class="menubtn addline grey" name="button3"  disabled="disabled"
								value="增行" /><input type="button" class="menubtn deleteline grey" name="button3" disabled="disabled"
								value="删行" /><input type="button" class="menubtn" name="button3"  
								value="帮助" /><input type="button" class="menubtn closewindow" 
								value="关闭" />
								<input type="hidden" value="${cashierBills.status}" id="status"/>
								<input type="hidden" value="${cashierBills.cashierBillsID}" name="cashierBills.cashierBillsID"/> 
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
			<table class="linetable" id="table3" cellpadding="10" cellspacing="5">
			   <tr><td align="center" colspan="6" id="titlestyle" ><span></span><input type="hidden" name="cashierBills.billsType"/></td></tr>
				<tr>
					
				<td align="right">公司：</td>
					<td><input type="text" readonly class="inputbottom" style="width: 180px;"
						name="cashierBills.companyName" value="${cashierBills.companyName}" />
						<input type="hidden" name="zctype" value="${zctype}"/>
					</td>
					<td align="right">创收部门：</td>
					<td id="dept">
					<input
						type="hidden" id="departmentID" 
						name="cashierBills.departmentID" value="${cashierBills.departmentID}"/>
					<input
						type="text" id="departmentName" class="inputbottom notnull" 
						name="cashierBills.departmentName"
						value="${cashierBills.departmentName}" style="width:160px;cursor:pointer;" onclick="getDeptInfo()"/>
						
							<input type="button" class="btncon deptbtn" />
						</td>
					 <td align="right"><span class="xx">*</span>部门责任人：
					</td>
					<td style="width:150px;">
						<%-- 获取责任人将姓名编号ID均保存 --%> <input type="text"  value="${cashierBills.staffName}"
						name="cashierBills.staffName" id="staffname"
						class="notnull inputbottom"  style="width:100px;" /> <input
						type="text" style="display:none;" name="cashierBills.staffID" value="${cashierBills.staffID}"
						id="staffid" /> <input type="text" style="display:none;"
						name="cashierBills.staffCode" id="staffcode" value="${cashierBills.staffCode}"/>
						
						<input type="button" class="btncon deptbtn"/>
						</td>
					
				</tr>

				
				<tr>
					<td height="20" align="right" ><span class="xx">*</span>主项目名称：</td>

					<td style="width:210px;"><input type="text" id="xmtypename" readonly
						name="cashierBills.xmtypename" value="${cashierBills.xmtypename}"
						class="notnull inputbottom" style="width:180px;" class="put3" /> <input
						type="hidden" name="cashierBills.xmtype" id="xmtype"
						value="${cashierBills.xmtype}" />
						<input type="button" class="btncon projectbtn" />
					</td>


					<td align="right"><span
						class="STYLE1"><span class="xx">*</span>子项目名称：</span></td>
					<td style="width:200px;"><input type="text" id="projectName" readonly
						name="cashierBills.projectName"  class="notnull inputbottom" value="${cashierBills.projectName}"
						style="width:160px;" /><input type="hidden" id="proID"
						name="cashierBills.proID" value="${cashierBills.proID}" /><input
						type="hidden" id="projectCode" name="cashierBills.projectCode"
						value="${cashierBills.projectCode}" /><input type="hidden"
						id="xmtypename2" value="${cashierBills.xmtypename}" />
						<input type="button" class="btncon projectbtn"/>
						</td>
					
                   
						<td align="right">凭证号：</td>
					<td class="yz"><input type="text" class="inputbottom"
						style="width: 160px;" id="journalNum" 
						name="cashierBills.journalNum" readonly="readonly"
						value="${cashierBills.journalNum}" />
					</td>
                    

				</tr>


			</table>
			</div>
<ul id="xmul" class="filetree"></ul>
							<div id="Layer1" style="display:none;">
								<table 
									class="table bg auto_arrange .goodtable2" id="goodtable"
									style="width:500px;">
										
										
								<tr>
									
										<th align="center" bgcolor="#E4F1FA" width="110px" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="70px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>目标起时间
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>目标止时间
										</th>
										<th class="baokx" align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>报开学号
										</th>
										<th class="baokx" align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>学员期数
										</th>
											<th class="baokx" align="center" bgcolor="#E4F1FA" width="90px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>报开学时间
										
										</th>
										
										
										
										<th align="center" bgcolor="#E4F1FA" width="60px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> 单位
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>数量
										</th>
										<th align="center" bgcolor="#E4F1FA" width="95px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> 单价管理
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
										
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span> <span class="xx">*</span>预算单价
										</th>
										<th align="center" bgcolor="#E4F1FA" width="90px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>目标预算金额
										</th>
										<th align="center" bgcolor="#E4F1FA" width="70px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>目标部门
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="3"
													height="14" /> </span>  <span class="xx">*</span>目标业务员
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
									<s:iterator value="goodBillslist">
									<tr id="kelong<%=number%>" class="checkgoods">
										<!-- 序号 -->
										
										<td align="center" bgcolor="#FFFFFF">
										<span>${goodsNum}</span>
										
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF">
										<span>${goodsName}</span>
										
										</td>
										<!-- 目标起时间 -->
										<td align="center" bgcolor="#FFFFFF" >

										 <span>${fn:substring(targetStart,0,10)}</span>
												
									
										</td>
										<!-- 目标止时间 -->
										<td height="30" align="center" bgcolor="#FFFFFF">
										<span>${fn:substring(targetEnd,0,10)}</span>
											
										
										</td>
										
										<!-- 报开学号 -->
										<td class="baokx" align="center" bgcolor="#FFFFFF" >

										 <span>${studentCode}</span>
												
									
										</td>
										<!-- 学员期数 -->
										<td class="baokx"  height="30" align="center" bgcolor="#FFFFFF">
										<span>${studentPeriods}</span>
											
										
										</td>
										
											<!-- 报开学时间 -->
										<td class="baokx"  height="30" align="center" bgcolor="#FFFFFF">
										<span>${schoolopendate}</span>
											
										
										</td>
								
										
									
										
										<!-- 单位 -->
										<td align="center" bgcolor="#FFFFFF">
										
										<span id="goodsVariableID">${goodsVariableID}</span>
											
									
										</td>
										<!-- 数量 -->
										<td align="left" bgcolor="#FFFFFF">
										 <span>${quantity}</span>
										
										</td>
										<td align="center" bgcolor="#FFFFFF" >
											 <span>${priceManage}</span>
										
										</td>
										<!-- 单价 -->
										<td align="center" bgcolor="#FFFFFF">
										<span>${price}</span>
					
										</td>
										<!-- 总金额 -->
										<td align="center" bgcolor="#FFFFFF">
										<input type="hidden" name="ppID" id="ppID" value="${ppID}"/>
										<span>${money}</span>
											
										</td>
										
										<td align="left" bgcolor="#FFFFFF">
										<span>${targetDeptName}</span>
										
										</td>
										<td align="left" bgcolor="#FFFFFF">
										<span>${targetSalerName}</span>
										
										</td>
										<td align="left" bgcolor="#FFFFFF">
										<span>${ccompanyName}</span>
										
										</td>
										<td align="left" bgcolor="#FFFFFF">
										<input type="hidden"  id="connectID" value="${connectID}"/>
										<span class="connectName" style="cursor:pointer;">${connectName}</span>
										
										</td>
										<td align="center" bgcolor="#FFFFFF">
										
											无
										
										</td>
									</tr>
									<% number++;%>
									</s:iterator>
									
								  
								</table>
							
				
           </div>
			
			<s:token></s:token>
		</form>
	<div style="margin-top:10px;">
	          工作日志: <input type="text" name="cashierBills.rezhi" class="inputbottom" style="width:90%;" value="${cashierBills.rezhi}">
	   <p>&nbsp;</p>
		备注：<input type="text" name="cashierBills.remark" class="inputbottom" style="width:96%;" value="${cashierBills.remark}">
		<p>&nbsp;</p>
		<p>制单人：<input type="text" class="inputbottom"  style="cursor:pointer;" onclick="getStaffInfo('${cashierBills.inputid}')" size="15" value="${cashierBills.inputName}"/>&nbsp;&nbsp;&nbsp;制单日期：<input type="text" class="inputbottom" id="createD" value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />" size="20" >
		</p>
		</div>
				
       </div>
				
       </div>
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
		</form><%--
		
		
		

		--%><%------------------------------------代码树------------------------------------%>

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
				<input type="button" class="input-button JQueryreturn1" value="  取消  " />
			</div>
		</div>
		

   	<%------------------------------------项目分类框------------------------------------%>
	<div id="jqModel">
		<div id="treeBoxs" ></div>
	</div>
		
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
				
			
	<!--选择审核人员窗口 -->
	<div class="jqmWindow"
		style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
		id="jqModelSend">
		<div class="drag">报送审批</div>
		<div class="close"></div>
		<center>
		<form  id="sendForm" name="sendForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>
		<table width="100%" id="SearchTable2" cellspacing="20"
			cellpadding="20">
			<tr>
				<td align="right">审核人公司：</td>
				<td align="left"><select id="receiverCompanyID"
					name="cashierBills.examineComID" onchange="changeCompany(this);"
					style="width:200px;">
						<option value="">请选择审核人公司</option>
				</select>
				<input type="hidden"  id="examineComName"  name="cashierBills.examineComName"/>
				</td>
			</tr>
			<tr>
				<td align="right">审核人部门：</td>
				<td align="left"><select id="receiverDeptID"
					name="cashierBills.examineorgID" onchange="changeDept(this);"
					style="width: 200px;">
						<option value="">请选择审核人部门</option>
				</select>
				<input type="hidden"  id="examineorgName"  name="cashierBills.examineorgName"/>
				</td>
			</tr>
			<tr>
				<td align="right">审核人姓名：</td>
				<td align="left"><select name="cashierBills.examineID"
					id='receiverID' style="width: 200px;">
						<option value="">请选择审核人</option>
				</select>
				<input type="hidden"  id="examineName"  name="cashierBills.examineName"/>
				<input type="hidden"  id="examinecsbID"  name="cashierBills.cashierBillsID"/>
				<input type="hidden"  id="examineOpra"  name="examineOpra" value="update"/>
				</td>
			</tr>
		</table>
  
		<div align="center" style="margin-top: 25px;">
			<input type="button" class="input-button" id="submitResult" onclick="submitExamine()"
				value=" 提交 " /> 
		</div>
	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
</body>
</html>

