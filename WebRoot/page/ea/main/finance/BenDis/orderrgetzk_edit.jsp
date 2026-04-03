<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>支款单-查看</title>
		
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
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/BenDis/orderedit.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css"	media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
			
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

<!---表格拖动列宽 -->
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

#CostSheetForm  td{
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
	var billsType = "${cashierBills.billsType}";
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
<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	<%--
		<div id="apDiv1"></div>
		--%>
	<!-- 项目预算添加表单开始 -->
	<form name="CostSheetForm" id="CostSheetForm" method="post"
		enctype="multipart/form-data">
		<div id="Layer11" align="center">
			<!-- maindiv-->
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
				<table class="linetable" id="table3" cellpadding="20" cellspacing="5">
					<tr>
						<td align="center" colspan="6" id="titlestyle"><span>支款单</span><input
							type="hidden" name="cashierBills.billsType" />
						</td>
					</tr>
					<tr>
						<td width="10%" height="20" align="right" class="nolinetd">
							凭证号：
						</td>
						<td width="20%" >
							<input type="text" style="width:100%;" class="inputbottom1"
								id="journalNum" name="cashierBills.journalNum" 
								readonly="readonly" value="${cashierBills.journalNum}"/>
								<input type="hidden"  name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}"/>
								<input type="hidden"  name="cashierBills.cashierBillsKey" value="${cashierBills.cashierBillsKey}"/>
						</td>
						<td width="10%"  align="right" class="nolinetd">
							制单人：
						</td>
	     				 	<td width="20%" ><input style="width:100%;" class="inputbottom1" value="${cashierBills.inputName}" readonly="readonly"/>
							<input type="hidden" value="${cashierBills.status}" name="cashierBills.status"/>
						</td>
						<td  width="9%"  align="right" class="nolinetd">
							责任人：
						</td>
						<td width="15%">
						    <input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.staffName}"/>
						</td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1">票据支票管理：</span>
						</td>
						<td>
							<input type="text" name="cashierBills.billCheck" class="model1 xiaoguo inputbottom1"  style="display: inline;width:100%;border:0px;"  value="${cashierBills.billCheck}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" class="nolinetd">
							公司：
						</td>
						<td>
							<span><input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.companyName}"/></span><%--<%=c.getCompanyName()%> --%>
						</td>
						<td align="right" class="nolinetd">
							部门：
						</td>
	     					<td id="dept">
							<input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.departmentName}"/>
						</td>
						<td height="20" align="right" class="nolinetd"><span
						class="STYLE1">&nbsp;&nbsp;&nbsp;&nbsp;附：</span></td>
					   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张</td>
					</tr>
					<tr>
					    <td align="right" class="nolinetd">主项目名称：</td>
						
						<td><input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.xmtypename}"/></td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1" class="nolinetd">子项目名称：</span>
						</td>
						<td>
							<input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.projectName}"/>
						</td>
						<td height="30" align="right" class="nolinetd">
							<span class="STYLE1">子项目描述：</span>
						</td>
						<td colspan="3">
						   <input class="inputbottom1" style="width:100%;" readonly="readonly" value="${cashierBills.content}"/>
						</td>


					</tr>
				</table>
			</div>
			<div id="Layer10" align="left" style="margin-top: 20px">
				<table class="table bg auto_arrange .goodtable2" id="goodtable"
					style="width:650px;">
					<tr>
						<th align="center" bgcolor="#E4F1FA" width="30px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>序号</th>
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>产品编号</th>
						<th align="center" bgcolor="#E4F1FA" width="200px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>产品名称</th>
						<th align="center" bgcolor="#E4F1FA" width="150px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>产品批次号</th>
						<th align="center" bgcolor="#E4F1FA" width="110px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>产品类别</th>
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>单价</th>
						<th align="center" bgcolor="#E4F1FA" width="50px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>数量</th>
						<th align="center" bgcolor="#E4F1FA" width="50px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="3" height="14" />
						</span> 折扣</th>
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="3" height="14" />
						</span>产品总金额</th>
						<th align="center" width="49px" bgcolor="#E4F1FA"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="5" height="14" />
						</span> 操作</th>
					</tr>
					<%int number = 1;%>
					<s:set name="a" value="0" id="a"></s:set>
					<s:iterator value="goodBillslist">
						<tr id="kelong<%=number%>" class="checkgoods">
							<!-- 序号 -->
							<td align="center" bgcolor="#FFFFFF"><span><%=number%></span>

							</td>
							<td align="center" bgcolor="#FFFFFF"><span>${goodsNum}</span>
							</td>
							<!-- 产品名称 -->
							<td align="center" bgcolor="#FFFFFF"><span>${goodsName}</span>
							</td>
							<!-- 目标起时间 -->
							<td align="center" bgcolor="#FFFFFF"><span></span></td>
							<!-- 目标止时间 -->
							<td height="30" align="center" bgcolor="#FFFFFF"><span>${typeID}</span>
							</td>
							<!-- 报开学号 -->
							<td align="center" bgcolor="#FFFFFF"><span><fmt:formatNumber
										value="${price}" pattern="#,#00.000" />
							</span></td>
							<!-- 学员期数 -->
							<td height="30" align="center" bgcolor="#FFFFFF"><span class="d">${quantity}</span>
							</td>
							<!-- 报开学时间 -->
							<td height="30" align="center" bgcolor="#FFFFFF"><span>${rebate}</span>
							</td>
							<!-- 单位 -->
							<td align="center" bgcolor="#FFFFFF"><span
								id="goodsVariableID" class="a"><fmt:formatNumber value="${money}"
										pattern="#,#00.000" />
							</span></td>
							<td align="center" bgcolor="#FFFFFF"><a href="javascript:viewDetail('${ppID}');">详情</a></td>
						</tr>
						<% number++;%>
					</s:iterator>
				</table>
			</div>
			<s:token></s:token>
	</form>
	<div style="margin-top:20px; margin-left: 20px" align="left">
		下单日期：<input type="text" class="inputbottom1" id="createD" readonly="readonly"
			value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />"
			size="20"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 订单状态：<input type="text"  readonly="readonly" value="${cashierBills.fkStatus=='01'?'未付款':cashierBills.fkStatus=='00'?'已付款未发货':cashierBills.fkStatus=='02'?'已出库正在物流':cashierBills.fkStatus=='03'?'用户已收货':'已分配金币'}"
			class="inputbottom1" style="cursor:pointer;" size="15"/><%----------onclick="getStaffInfo('${cashierBills.fkStatus}')"---------%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 产品总数量：<input type="text"  readonly="readonly" class="inputbottom1" id="b"
			style="cursor:pointer;" size="15"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 总金额：<input type="text"  readonly="readonly" class="inputbottom1" id="c"
			style="cursor:pointer;" size="15"/>
			</p>
	</div>
	<%------------------------------------用于表单提交-----------------------------------%>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){
	var a=0;
	$(".a").each(function(){
		a= parseFloat($(this).text())+a;
	})
	$("#c").val(a);
	
	var f=0;
	$(".d").each(function(){
		f= parseFloat($(this).text())+f;
	})
	$("#b").val(f);
});
</script>
</body>

</html>

