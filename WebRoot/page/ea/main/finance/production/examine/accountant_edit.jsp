<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@page import="hy.ea.bo.Company"%>
<%@page import="hy.ea.bo.CAccount"%>
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
			CAccount caccount = (CAccount) session.getAttribute("account");
			if(session==null||c==null||caccount==null){
				response.sendRedirect(basePath+"page/ea/index.jsp");
			}
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>拨款</title>
		
		
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
			src="<%=basePath%>js/ea/finance/accountant_edit.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
			
		
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
       <link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<%-- <script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script> --%>
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/xmTree.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
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

<!---表格拖动列宽 -->
.bg table {
	font-size: 12px;
	color: #000000;
}

.bg td,th {
	font-size: 12px;
	color: #000000;
	/* text-align: center; */
	line-height: 15px;
	height: 20px;
}

.bg td.tit,th.tit {
	background-color: #e2e2e2;
	color: #000;
	height: 17px;
	/* text-align: center; */
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

.querybtn{
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  background:url(<%=basePath%>images/search16.png) no-repeat;
}
  
.addbtn{
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  vertial-align:middle;
  background:url(<%=basePath%>images/u15.png) no-repeat;
}
  
.tdiv{
  width:97%;
}
  
.caz{
  display:none;
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
  
.text_tree{
  width:220px;
  background:#ffffff;
  overflow:auto;
}
  
.baokx{
 display:none;
}
 
</style>
<script type="text/javascript">
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var staffname="<%=caccount.getStaffName()%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var basePath = "<%=basePath%>";
	var token = 0;
	var pNumber="${pageNumber}";
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var journalNum = "";
	var notoken = 0;
	var select=1;
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var type="${type}";
	var status="${cashierBills.status}"
	var jumptype = "${jumptype}";
	var xmtype="${xmtype}";
	var xmtypename="${param.xmtypename}";
	var billID = "${billID}";
	var summary="${summary}";
	var proID="${cashierBills.proID}";
	var zorg = "${zorg}";
	var zorgname = "${zorgname}";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	var  billsType = "${cashierBills.billsType}";
	var zctype = "${zctype}";
	var goodsnum=0;
	var sta="${status}";
	var verid;
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
		<!-- 项目预算添加表单开始 -->
		<form name="CashApplyBillsform" id="CashApplyBillsform" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="srandom" value="${session_value}"/>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
							<input type="button"  class="menubtn JQuerySubmitgd" style="width:150px"  value="保存并提交审核" />
							<input type="button" class="menubtn print" name="button3"  value="打印" />
							<input type="button" class="menubtn pass grey" id="pass" disabled="disabled" value="确认拨款" />
							<input type="button" class="menubtn nopass grey" id="nopass" disabled="disabled" value="暂不拨款" />
							<input type="button" class="menubtn qrsk grey" disabled="disabled" id="qrsk" value="确认收款" />
							<input type="button" class="menubtn generate grey" disabled="disabled" id="nopass" value="生成" />
							<input type="button" class="menubtn deleteline" name="button3" value="帮助" />
							<input type="button" class="menubtn closewindow" value="关闭" />
							<input type="hidden" value="${jumptype}" name="jumptype"/> 
							</nobr>	
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
		  <div id="content1">
		  <table class="linetable" id="table3" cellpadding="10" cellspacing="5" width="650px">
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
						type="text" readonly id="departmentName" class="inputbottom" 
						name="cashierBills.departmentName"
						value="${cashierBills.departmentName}" style="width:160px;"/>
						</td>
					 <td align="right">部门责任人：
					</td>
					<td style="width:150px;">
						<%-- 获取责任人将姓名编号ID均保存 --%> <input type="text" readonly value="${cashierBills.staffName}"
						name="cashierBills.staffName" id="staffname"
						class="inputbottom"  style="width:100px;" />
						</td>
				</tr>
				<tr>
					<td height="20" align="right" >主项目名称：</td>

					<td style="width:210px;"><input type="text" id="xmtypename" readonly
						name="cashierBills.xmtypename" value="${cashierBills.xmtypename}"
						class="inputbottom" style="width:180px;"/>
					</td>
					<td align="right"><span class="STYLE1"> 子项目名称：</span></td>
					<td style="width:200px;"><input type="text" id="projectName" readonly
						name="cashierBills.projectName"  class="inputbottom" value="${cashierBills.projectName}"
						style="width:160px;" />
					</td>
					<td align="right">凭证号：</td>
					<td class="yz"><input type="text" class="inputbottom"
						style="width: 160px;" id="journalNum"
						name="cashierBills.journalNum" readonly="readonly"
						value="${cashierBills.journalNum}" />
						<input type="hidden"  name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}" id="cashierBillsID"/>
						<input type="hidden"  name="cashierBills.cashierBillsKey" value="${cashierBills.cashierBillsKey}"/>
					</td>
				</tr>
				<tr>
						<td align="right" class="nolinetd"  height="30">
							<span class="xx">*</span>拨款方式：
						</td>
	     					<td id="dept" width="15%" class="nolinetd">
	     					<span id="spappstyle" style="display: none;">${cashierBills.appstyle=='01'?'银行转帐':cashierBills.appstyle=='02'?'银行支票转账':cashierBills.appstyle=='03'?'支付宝转帐':cashierBills.appstyle=='04'?'App转账':cashierBills.appstyle=='05'?'POS机转账':'现金转账'}</span>
							<select style="width: 100%;border:0;" name="cashierBills.appstyle" id="appstyle">
								<option value="01">
									银行转帐
								</option>
								<option value="02">
									银行支票转账
								</option>
								<option value="03">
									支付宝转帐
								</option>
								<option value="04">
									App转账
								</option>
								<option value="05">
									POS机转账
								</option>
								<option value="06">
									现金转账
								</option>
							</select>
						</td>
					</tr>
			</table>
		</div>
		<input type="hidden" id="line"/>
		<!-- <div id="Layer1"> -->
			<ul id="xmul" class="filetree"></ul>
		<!-- </div> -->
		<div style="margin-top:10px;"><!--  id="add" -->
	备注：<input type="text" name="cashierBills.remark" class="inputbottom" style="width:96%;" readonly value="${cashierBills.remark }"> <p>
		制单人：<input type="text" readonly class="inputbottom" size="15" value="${cashierBills.inputName}"/>&nbsp;&nbsp;&nbsp;制单日期：<input type="text" readonly class="inputbottom" id="createD" value="${fn:substring(cashierBills.cashierDate, 0, 10)}" size="20" >
		</p>
		</div>
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none;" 
				id="audittbl">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center" width="80px">审核信息：</td>
					<td align="left" colspan="9"><input type="text" id="remark"
						class="inputbottom"  readonly="readonly"  style="width:80%;" value="${commStr}"/>
					</td>
				</tr>
				<tr>
					<td height="25" align="right">公司经理：</td>
					<td><input type="text" class="inputbottom gsjl" value='${bcheckMap["gsjl"] }'/>
					<c:if test='${bcheckMap["gsjl"]==null||bcheckMap["gsjl"]==""}'>
						<input type="button" class="btncon verify" disabled="true" id="gsjl" />
					</c:if>
					</td>
					<td align="right">部门主管：</td>
					<td><input type="text" class="inputbottom bmzg" value='${bcheckMap["bmzg"] }'/>
					<c:if test='${bcheckMap["bmzg"]==null||bcheckMap["bmzg"]==""}'>
						<input type="button" class="btncon verify" disabled="true" id="bmzg"/>
					</c:if>
					</td>
					<td align="right">人事处：</td>
					<td><input type="text" class="inputbottom rsc" value="${bcheckMap['rsc'] }"/>
					<c:if test='${bcheckMap["rsc"]==null||bcheckMap["rsc"]==""}'>
						<input type="button" class="btncon verify" disabled="true" id="rsc" />
					</c:if>
					</td>
					<td align="right">财务审核：</td>
					<td><input type="text" class="inputbottom cwsh" value="${bcheckMap['cwsh'] }"/>
					<c:if test='${bcheckMap["cwsh"]==null||bcheckMap["cwsh"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="cwsh" />
					</c:if>
					</td>
					<td align="center">收款人确认：</td>
					<td><input type="text" class="inputbottom skr" value="${bcheckMap['skr'] }"/>
					<c:if test='${bcheckMap["skr"]==null||bcheckMap["skr"]==""}'>
					<input type="button" class="btncon verify " disabled="true" id="skr" />
					</c:if>
					</td>
				</tr>
				<tr>
					<td height="25" align="right">总部总经理：</td>
					<td><input type="text" class="inputbottom zjl" value='${bcheckMap["zjl"] }'/>
					<c:if test='${bcheckMap["zjl"]==null||bcheckMap["zjl"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="zjl" />
					</c:if>
					</td>
					<td align="right">总部部门主管：</td>
					<td><input type="text" class="inputbottom zg" value="${bcheckMap['zg'] }"/>
					<c:if test='${bcheckMap["zg"]==null||bcheckMap["zg"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="zg" />
					</c:if>
					</td>
					<td align="right">总部人事处：</td>
					<td><input type="text" class="inputbottom zbrsc" value="${bcheckMap['zbrsc'] }"/>
					<c:if test='${bcheckMap["zbrsc"]==null||bcheckMap["zbrsc"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="zbrsc" />
					</c:if>
					</td>
					<td align="right">总财务审核：</td>
					<td><input type="text" class="inputbottom zbcw" value="${bcheckMap['zbcw'] }"/>
					<c:if test='${bcheckMap["zbcw"]==null||bcheckMap["zbcw"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="zbcw" />
					</c:if>
					</td>
					<td align="center">交款人确认：</td>
					<td><input type="text" class="inputbottom jkr" value="${bcheckMap['jkr'] }"/>
					<c:if test='${bcheckMap["jkr"]==null||bcheckMap["jkr"]==""}'>
					<input type="button" class="btncon verify" disabled="true" id="jkr" />
					</c:if>
					</td>
				</tr>
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
		

		<%------------------------------------选择往来个人、往来单位------------------------------------%>
		
		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel2">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							往来个人、往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								名称：
							</td>
							<td width="142">
								<input name="comUserID" class="input" id="comUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchuu" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qduser" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									<input type="hidden" name="parms" id="grparms" />
									<input type="hidden" name="cutype" id="cutype" />
			
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
							<td width="15%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="dwTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px; width: 100%"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="85%" valign="top" align="left">
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
		
		<%------------------------------------银行账号------------------------------------%>
		
		<form name="selectnumForm" id="selectnumForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							银行账号
						</div>
					</div>
					<table width="99%" height="33" id="searchnum"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								名称：
							</td>
							<td width="142">
								<input name="numID" class="input" id="numID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchnum" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdnum" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									<input type="hidden" name="parms" id="nparms" />
									<input type="hidden" name="cutype" id="ntype" />
			
							</td>
							<td width="80">
								<a id="nrsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="nrxy" title="0">下一页</a>
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
							<td width="100%" valign="top" align="left">
								<div id="body_num" style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
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
	
		<!--单据类别 -->
		<form name="billForm" id="billForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 50%; top: 30%"
				id="jqModelbill">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
				请选择生成单据类别
					<div class="close">
					</div>
				</div>
				<table width="250" id="SearchTable">
					
					<tr>
						<td align="right">
							生成单据类别：
						</td>
						<td style="width: 80px">
							<select id="selectbilltype">
							<option value="请选择">请选择</option>
							<option value="费用报销单">费用报销单</option>
							<option value="费用出/入库报销单">费用出/入库报销单</option>
							<option value="退/费货报销单据">退/费货报销单据</option>
							<option value="支款单">支款单</option>
							<option value="采购单">采购单</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="printprev"
						value=" 确定 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		
		 <!-- -----------------------------------审核-------------------------------- -->
    <form name="SendForm2" id="SendForm2" method="post">
        <div class="jqmWindow"
            style="display: none; width: 400px; height: 190px; left: 20%; top: 40%;"
            id="shyj">
            <input type="submit" name="submit2" style="display: none" />
            <div class="contentbannb">
                <div class="drag">
                    审核
                    <div class="close"></div>
                </div>
            </div>
            <center>
			<table width="100%" id="SearchTable2" cellspacing="15"
				cellpadding="15">
				<tr>
					<td align="right" width='25%'>审核意见：</td>
					<td align="left" colspan="2"><textarea rows="5" cols="40"
							style="max-width: 270px;max-height: 100px;" name="comments"
							id="comments" class="ckTextLength" maxlength="1000"></textarea>
						<input  name="cashierBills.status" id="status1" style="display:none"/>
						<input  name="statusForPerson" id="statusForPerson" value="00" style="display:none"/>
					</td>
				</tr>
			</table>
			<div align="center">
                <input type="button" class="input-button" id="submitResult2" value=" 提交 " /> <input type="submit" name="submit" style="display:none"/>
            </div>
            </center>
        </div>
    </form>
	
	
	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
			
</body>
</html>

