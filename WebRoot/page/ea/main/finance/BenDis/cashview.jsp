<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>退款单</title>

<%@page import="hy.ea.bo.Company"%>
<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/ea/finance/responsible_edit.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />

<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />

<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/finance/invoicing/xmTree.js"></script>

<style type="text/css">
#table3 input {
	height: 20px;
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

#CostSheetForm  td {
	white-space: nowrap;
}

.baokx {
	display: none;
}

.clickTrClass {
	background-color: #C0E3FA;
}

.clickTdClass {
	background-color: #FF99C2;
}
</style>

</head>
<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	<div id="apDiv1"></div>
	<!-- 项目预算添加表单开始 -->
	<form name="CostSheetForm" id="CostSheetForm" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="billtname" id="billtname" /> <input
			type="hidden" name="srandom" value="${session_value}" />
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0"
			style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
			<tr>

			</tr>
		</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
				<table class="linetable" id="table3" cellpadding="10"
					cellspacing="5">
					<tr>
						<td align="center" colspan="6" id="titlestyle"><span>退款单</span>

						</td>
					</tr>
					<tr>

						<td align="right">公司：</td>
						<td><input type="text" readonly class="inputbottom"
							style="width: 180px;" name="cashierBills.companyName"
							value="${cashierBills.companyName}" /> <input type="hidden"
							name="zctype" value="${zctype}" /></td>
						<td align="right">创收部门：</td>
						<td id="dept"><input type="hidden" id="departmentID"
							name="cashierBills.departmentID"
							value="${cashierBills.departmentID}" /> <input type="text"
							id="departmentName" class="inputbottom notnull"
							name="cashierBills.departmentName"
							value="${cashierBills.departmentName}" style="width:160px;"
							readonly="readonly" /></td>
						<td align="right">部门责任人：</td>
						<td style="width:150px;">
							<%-- 获取责任人将姓名编号ID均保存 --%> <input type="text"
							value="${cashierBills.staffName}" name="cashierBills.staffName"
							id="staffname" class="notnull inputbottom" style="width:100px;" />
							<input type="text" style="display:none;"
							name="cashierBills.staffID" value="${cashierBills.staffID}"
							id="staffid" /> <input type="text" style="display:none;"
							name="cashierBills.staffCode" id="staffcode"
							value="${cashierBills.staffCode}" readonly="readonly" /></td>
					</tr>
					<tr>
						<td height="20" align="right">主项目名称：</td>

						<td style="width:210px;"><input type="text" id="xmtypename"
							readonly name="cashierBills.xmtypename"
							value="${cashierBills.xmtypename}" class="notnull inputbottom"
							style="width:180px;" class="put3" /> <input type="hidden"
							name="cashierBills.xmtype" id="xmtype"
							value="${cashierBills.xmtype}" readonly="readonly" /></td>
						<td align="right"><span class="STYLE1">子项目名称：</span>
						</td>
						<td style="width:200px;"><input type="text" id="projectName"
							readonly name="cashierBills.projectName"
							class="notnull inputbottom" value="${cashierBills.projectName}"
							style="width:160px;" /><input type="hidden" id="proID"
							name="cashierBills.proID" value="${cashierBills.proID}" /><input
							type="hidden" id="projectCode" name="cashierBills.projectCode"
							value="${cashierBills.projectCode}" /><input type="hidden"
							id="xmtypename2" value="${cashierBills.xmtypename}"
							readonly="readonly" /></td>
						<td align="right">凭证号：</td>
						<td class="yz"><input type="text" class="inputbottom"
							style="width: 160px;" id="journalNum"
							name="cashierBills.journalNum" readonly="readonly"
							value="${cashierBills.journalNum}" /></td>
					</tr>
				</table>
			</div>

			<input type="hidden" id="line" />
			<ul id="xmul" class="filetree"></ul>
			<s:token></s:token>
	</form>
	<div style="margin-top:10px;">
		备注：<input type="text" name="cashierBills.remark" class="inputbottom"
			style="width:96%;" value="${cashierBills.remark}" readonly="readonly">
		<p>&nbsp;</p>
		<table class="table" style="width:100%;">

			<thead>
				<tr>
					<th>品名编号</th>
					<th>品名名称</th>
					<th>单位</th>
					<th>数量</th>
					<th>单价</th>
					<th>金额</th>
					<th>目标部门</th>
					<th>目标业务员</th>
					<th>往来公司</th>
					<th>往来个人</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="list">

					<tr>
						<td>${goodsNum}</td>
						<td>${goodsName}</td>
						<td>${goodsVariableID}</td>
						<td>${quantity}</td>
						<td>${price}</td>
						<td>${money}</td>
						<td>${targetDeptName}</td>
						<td>${targetSalerName}</td>
						<td>${ccompanyName}</td>
						<td>${connectName}</td>
					</tr>
				</s:iterator>


			</tbody>
		</table>




		<p>
			制单人：<input type="text" class="inputbottom" size="15"
				value="${cashierBills.inputName}" readonly="readonly" />&nbsp;&nbsp;&nbsp;制单日期：<input
				type="text" class="inputbottom" id="createD"
				value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />"
				size="20" readonly="readonly">
		</p>
	</div>

	</div>

	</div>



</body>
</html>

