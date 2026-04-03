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
			if(session==null||c==null){
				response.sendRedirect(basePath+"page/ea/index.jsp");
			}
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
				<table class="linetable" id="table3" cellpadding="20"
					cellspacing="5">
					<tr>
					    <input type="hidden" value="${cashierBills.cashierBillsID}" id="clientPositioningID"/>
						<td align="center" colspan="6" id="titlestyle"><span></span><input
							type="hidden" name="cashierBills.billsType" />
						</td>
					</tr>
					<tr>

						<td align="right">公司：</td>
						<td><input type="text" readonly class="inputbottom1"
							style="width:100%;" name="cashierBills.companyName"
							value="${cashierBills.companyName}" /> <input type="hidden"
							name="zctype" value="${zctype}" /></td>
						<td align="right">创收部门：</td>
						<td id="dept"><input type="text" id="departmentName"
							class="inputbottom1 notnull" name="cashierBills.departmentName"
							value="${cashierBills.departmentName}"
							style="width:100%;cursor:pointer;" onclick="getDeptInfo()" /></td>
						<%-- <td align="right">部门责任人：
					</td>
					<td style="width:150px;">
						获取责任人将姓名编号ID均保存 <input type="text"  value="${cashierBills.staffName}"
						name="cashierBills.staffName" id="staffname"
						class="notnull inputbottom1"  style="width:100px;" /> <input
						type="text" style="display:none;" name="cashierBills.staffID" value="${cashierBills.staffID}"
						id="staffid" /> <input type="text" style="display:none;"
						name="cashierBills.staffCode" id="staffcode" value="${cashierBills.staffCode}"/>
						</td> --%>
						<td align="right">订单编号：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%;" id="journalNum"
							name="cashierBills.journalNum" readonly="readonly"
							value="${cashierBills.journalNum}" /></td>

					</tr>
					<tr>
						<td height="20" align="right">用户名称：</td>

						<td style="width:210px;"><input type="text" id="xmtypename"
							readonly name="cashierBills.xmtypename"
							value="${shr.staffName}" class="inputbottom1"
							style="width:100%;"/></td>


						<td align="right"><span class="STYLE1">用户账号：</span>
						</td>
						<td style="width:200px;"><input type="text" id="projectName"
							readonly name="cashierBills.projectName"
							class="inputbottom1"
							value="${wfjzh.account}" style="width:100%;" /></td>
						<td align="right">用户会员类型：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%;" id="journalNum" name="cashierBills.wfthytpe"
							readonly="readonly" value="${wfjzh.cusType=='1'?'平台':wfjzh.cusType=='2'?'公司平台商城会员':wfjzh.cusType=='3'?'经理商城业主会员':wfjzh.cusType=='4'?'主管商城野猪会员':wfjzh.cusType=='5'?'业务商城业主会员':wfjzh.cusType=='6'?'消费者会员':'消费者'}" /></td>
					</tr>
					<tr>
						<td height="20" align="right">商家：</td>

						<td style="width:200px;"><input type="text"
							id="xmtypename" readonly name="cashierBills.xmtypename"
							value="${cashierBills.companyName}" class="inputbottom1"
							style="width:100%;"/></td>
						<td height="20" align="right">支付方式：</td>

						<td style="width:200px;"><input type="text"
							id="xmtypename" readonly name="cashierBills.xmtypename"
							value="${cashierBills.wfStatus4=='00'?'微信支付':cashierBills.wfStatus4=='01'?'支付宝支付':cashierBills.wfStatus4=='02'?'银联支付':cashierBills.wfStatus4=='04'?'钱盒子刷卡支付':cashierBills.wfStatus4=='05'?'积分购物':'线下转账'}" class="inputbottom1"
							style="width:100%;"/></td>
						<td align="right">物流单位：</td>
						<td class="yz"><input type="text" class="inputbottom1"
							style="width:100%" id="journalNum" name="cashierBills.wlcomname"
							readonly="readonly" value="${cashierBills.wlcomname}" /></td>
					</tr>
					<tr>
						<td height="20" align="right">收货信息：</td>
						<td colspan="5"><input type="text" id="xmtypename" readonly
							name="cashierBills.xmtypename"
							value="${sh.receivename}  ${sh.receivetel}  ${sh.receiveaddress} "
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">发货信息：</td>
						<td colspan="5"><input type="text" id="xmtypename" readonly
							name="cashierBills.xmtypename"
							value="${sh.sendname}   ${sh.sendtel}   ${sh.sendaddress}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<c:if test="${cashierBills.wfStatus4=='04'}">
					<tr>
						<td height="20" align="right">刷卡人姓名：</td>
						<td colspan="5"><input type="text" id="boxPayName" readonly
							name="cashierBills.boxPayName" value="${cashierBills.boxPayName}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">刷卡人手机号：</td>
						<td colspan="5"><input type="text" id="backName" readonly
							name="cashierBills.boxPayTel" value="${cashierBills.boxPayTel}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">发卡行：</td>
						<td colspan="5"><input type="text" id="backName" readonly
							name="cashierBills.backName" value="${cashierBills.backName}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">卡号：</td>
						<td colspan="5"><input type="text" id="userAccountNum" readonly
							name="cashierBills.userAccountNum" value="${cashierBills.userAccountNum}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">流水号：</td>
						<td colspan="5"><input type="text" id="trade_no" readonly
							name="cashierBills.trade_no" value="${cashierBills.trade_no}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">刷卡时间：</td>
						<td colspan="5"><input type="text" id="tradeDate" readonly
							name="cashierBills.tradeDate" value="${cashierBills.tradeDate}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">提交审核时间：</td>
						<td colspan="5"><input type="text" id="boxDate" readonly
							name="cashierBills.boxDate" value="${cashierBills.boxDate}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">盒子SN号：</td>
						<td colspan="5"><input type="text" id="snNum" readonly
							name="cashierBills.snNum" value="${cashierBills.snNum}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					<tr>
						<td height="20" align="right">备注信息：</td>
						<td colspan="5"><input type="text" id="remark" readonly
							name="cashierBills.remark" value="${cashierBills.remark}"
							class="inputbottom1" style="width:100%;"/>
						</td>
					</tr>
					</c:if>
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
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>产品名称</th>
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
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
						<th align="center" bgcolor="#E4F1FA" width="80px"><span
								class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
								onMouseMove="MouseMoveToResize(this);"
								onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="1" height="14" />
						</span>待配送数量</th>
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
						<th align="center" bgcolor="#E4F1FA" width="100px"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="3" height="14" />
						</span>是否是赠品</th>
						<th align="center" width="49px" bgcolor="#E4F1FA"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="5" height="14" />
						</span> 操作</th>
						<th align="center" width="69px" bgcolor="#E4F1FA"><span
							class="resizeDivClass" onMouseDown="MouseDownToResize(this);"
							onMouseMove="MouseMoveToResize(this);"
							onMouseUp="MouseUpToResize(this);"><img
								src="<%=basePath%>/images/header_bg.gif" width="5" height="14" />
						</span> 操作</th>
						<th align="center" width="79px" bgcolor="#E4F1FA"><span
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
						  <input type="hidden" value="${ppID}" id="ppid"/>
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
							<td height="30" align="center" bgcolor="#FFFFFF"><span class="d">${(fhnum==null||fhnum=="")?quantity:fhnum}</span>
							</td>
							<!-- 报开学时间 -->
							<td height="30" align="center" bgcolor="#FFFFFF"><span>${rebate}</span>
							</td>
							<!-- 单位 -->
							<td align="center" bgcolor="#FFFFFF"><span
								id="goodsVariableID" class="a">${money}</span></td>
							<td align="center" bgcolor="#FFFFFF"><span
								id="goodsVariableID" class="a"><s:if test="premiums==1">是</s:if><s:else>否</s:else></span></td>
							<td align="center" bgcolor="#FFFFFF"><a href="javascript:viewDetail('${ppID}');">详情</a></td>
						    <td align="center" bgcolor="#FFFFFF"><a  id="apply" target="_blank">申请退货</a></td>
						    <td align="center" bgcolor="#FFFFFF"><a    id="add" >添加运单信息</a></td>
						</tr>
						<% number++;%>
					</s:iterator>
				</table>
			</div>
			<s:token></s:token>
	</form>
	
	<div style="margin-top:20px; margin-left: 20px" align="left">
		下单日期：<input type="text" class="inputbottom1" id="createD"
			value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />
			size="20"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 订单状态：<input type="text" id="fkStatus" value="${cashierBills.fkStatus=='01'?'未付款':cashierBills.fkStatus=='00'?'已付款未发货':cashierBills.fkStatus=='02'?'已出库正在物流':cashierBills.fkStatus=='03'?'用户已收货':cashierBills.fkStatus=='04'?'已分配金币':cashierBills.fkStatus=='05'?'申请退货':cashierBills.fkStatus=='06'?'同意退货':cashierBills.fkStatus=='07'?'退货中':cashierBills.fkStatus=='08'?'买家退货，卖家确认收货':cashierBills.fkStatus=='09'?'已转账确认审核中':cashierBills.fkStatus=='10'?'申请售后':cashierBills.fkStatus=='11'?'同意售后':cashierBills.fkStatus=='12'?'同意售后':cashierBills.fkStatus=='13'?'售后成功':'自动收获时金币不够未分配金币'}"
			class="inputbottom1" style="cursor:pointer;" size="15"/><%----------onclick="getStaffInfo('${cashierBills.fkStatus}')"---------%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 产品总数量：<input type="text" class="inputbottom1" id="b"
			style="cursor:pointer;" size="15"/><%--onclick="getStaffInfo('${cashierBills.inputid}')" --%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 总金额：<input type="text" class="inputbottom1" id="c" value="<fmt:formatNumber
										value="${cashierBills.priceSub}" pattern="#,#00.000" />"
			style="cursor:pointer;" size="15"/><%--onclick="getStaffInfo('${cashierBills.inputid}')" --%>
			</p>
	</div>
	<%------------------------------------用于表单提交-----------------------------------%>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){
	var a=0;
	/* $(".a").each(function(){
		a= parseFloat($(this).text())+a;
	});
	$("#c").val(a); */
	
	var f=0;
	$(".d").each(function(){
		f= parseFloat($(this).text())+f;
	})
	$("#b").val(f);
});
</script>
</body>

</html>

