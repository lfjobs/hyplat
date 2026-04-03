<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>实际单据查看1</title>
		
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
			src="<%=basePath%>js/ea/finance/earnings_edit.js"></script>
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

#CostSheetForm  td{
    white-space: nowrap;
}

.baokx{
 display:none;
}

  .clickTrClass{background-color:#C0E3FA; }
  .clickTdClass{background-color:#FF99C2; }
 
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
	var notoken=0;
	var bCheckList="${bCheckList}"
	var verid;
	var proID="${cashierBills.proID}";
	var sta="${status}";
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
			<input type="hidden" name="srandom" value="${session_value}"/>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td style="height:24px;" align="left">
							<input type="button" class="menubtn JQuerySubmitgd grey" disabled="disabled" value="保存" />
							<input type="button" class="menubtn print" name="button3" value="打印" />
							<input type="button" class="menubtn generate grey" name="button3" disabled="disabled" value="生成" />
							<input type="button" class="menubtn updatesheet grey" disabled="disabled" value="修改" />
							<input type="button" class="menubtn addline grey" name="button3" disabled="disabled" value="增行" />
							<input type="button" class="menubtn deleteline grey" name="button3" disabled="disabled" value="删行" />
							<input type="button" class="menubtn" name="button3" value="帮助" />
							<input type="button" class="menubtn closewindow" value="关闭" />
							<input type="hidden" value="${cashierBills.status}" id="status" />
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
			<div id="content1">
			   <table class="linetable" id="table3" cellpadding="10" cellspacing="5">
			   <tr><td align="center" colspan="6" id="titlestyle" ><span>${cashierBills.billsType}</span><input type="hidden" name="cashierBills.billsType" value="${cashierBills.billsType}"/></td></tr>
			   <input type="hidden" name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}" id="cashierBillsID"/>
				<tr>
				<td align="right">凭证号：</td>
					<td class="yz"><input type="text" class="inputbottom"
						style="width: 160px;" id="journalNum" 
						name="cashierBills.journalNum" readonly="readonly"
						value="${cashierBills.journalNum}" />
					</td>	
				<td align="right">公司：</td>
					<td><input type="text" readonly class="inputbottom" style="width: 180px;"
						name="cashierBills.companyName" value="${cashierBills.companyName}" />
						<input type="hidden" name="zctype" value="${zctype}"/>
					</td>
					<td align="right">创收部门：</td>
					<td id="dept">
					<input
						type="text" id="departmentName" class="inputbottom" 
						name="cashierBills.departmentName"
						value="${cashierBills.departmentName}" style="width:100px;" readonly="readonly"/>
						</td>
					 <td align="right">部门责任人：
					</td>
					<td style="width:150px;">
						<input type="text"  value="${cashierBills.staffName}"
						name="cashierBills.staffName" id="staffname"
						class="inputbottom"  style="width:100px;" readonly="readonly"/>
						</td>
				</tr>
				<tr>
				<td align="right">目标部门：</td>
					<td class="yz"><input type="text" class="inputbottom"
						style="width: 160px;" id="targetDeptName" 
						name="cashierBills.targetDeptName" readonly="readonly"
						value="${cashierBills.targetDeptName}" />
					</td>	
				<td align="right">目标业务员：</td>
					<td><input type="text" readonly class="inputbottom" 
						name="cashierBills.targetSalerName" value="${cashierBills.targetSalerName}" readonly="readonly"/>
					</td>
					<td align="right">目标起时间：</td>
					<td id="dept" style="width:80px;">
					<input
						type="text" id="targetStart" class="inputbottom" 
						name="cashierBills.targetStart"
						value="${fn:substring(cashierBills.targetStart,0,10)}" style="width:80px;" readonly="readonly"/>
						</td>
					 <td align="right">目标止时间：
					</td>
					<td style="width:70px;">
						<%-- 获取责任人将姓名编号ID均保存 --%> <input type="text"  value="${fn:substring(cashierBills.targetEnd,0,10)}"
						name="cashierBills.targetEnd" id="targetEnd"
						class="inputbottom"  style="width:70px;" readonly="readonly"/>
						</td>
					<td align="right">目标月份：
					</td>
					<td style="width:70px;">
						<input type="text" class="inputbottom" value="自动生成" style="width:50px;" readonly="readonly"/>
						</td>
				</tr>
				<tr>
					<td height="20" align="right" >项目分类：</td>

					<td style="width:210px;"><input type="text" id="xmtypename" 
						name="cashierBills.xmtypename" value="${cashierBills.xmtypename}"
						class="notnull inputbottom" style="width:180px;" class="put3" /> <input
						type="hidden" name="cashierBills.xmtype" id="xmtype"
						value="${cashierBills.xmtype}" readonly="readonly"/>
					</td>
					<td align="right"><span
						class="STYLE1">项目名称：</span></td>
					<td style="width:200px;"><input type="text" id="projectName" 
						name="cashierBills.projectName"  class="notnull inputbottom" value="${cashierBills.projectName}"
						style="width:160px;" /><input type="hidden" id="proID"
						name="cashierBills.proID" value="${cashierBills.proID}" /><input
						type="hidden" id="projectCode" name="cashierBills.projectCode"
						value="${cashierBills.projectCode}" /><input type="hidden"
						id="xmtypename2" value="${cashierBills.xmtypename}" readonly="readonly"/>
					</td>
                    <td align="center">
                    <span class="xx">*</span><span>拨款方式：</span>
                    </td>
                    <td align="left">
                    <span id="spappstyle" style="display: none;">${cashierBills.appstyle=='01'?'银行转帐':cashierBills.appstyle=='02'?'银行支票转账':cashierBills.appstyle=='03'?'支付宝转帐':cashierBills.appstyle=='04'?'App转账':cashierBills.appstyle=='05'?'POS机转账':'现金转账'}</span>
                    <select style="width: 100%;border:0;" name="cashierBills.appstyle" class="inputbottom" id="appstyle">
                        <option value="00">
                            	请选择
                        </option>
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
            
           </div>
           <input type="hidden" id="line"/>
        <!-- <div id="Layer1"> -->
            <ul id="xmul" class="filetree"></ul>
        <!-- </div> -->
			<table class="linetable" id="table3"  cellspacing="20" style="width:950px;">
				   <tr>
                    <td colspan="8" height="10px"></td>
                    </tr>
					<tr>
					<td align="left" colspan="2" width="30px">
					付方
					</td>
					<td align="center" width="50px">
					付方人：
					</td>
					<td align="left" class="appC" width="150px">
					<input type="hidden" id="appropriationcompanyID" name="cashApplyBills.appropriationcompanyID"  value="${cashApplyBills.appropriationcompanyID}"/>
					<input type="text"  id="appropriationcompanyName"  class="querys model1 fou inputbottom"  size="20"
								name="cashApplyBills.appropriationcompanyName" style="width:75%; display:inline;" value="${cashApplyBills.appropriationcompanyName}"/>
					<input type="button" class="btncon ffbtn sfk wlgr" id="appC"/>
					</td>
					<td align="center" width="55px">
					<span>付方帐号：</span>
					</td>
					<td align="left" class="anum" width="150px">
					<input type="text"  name="cashApplyBills.appropriationNum" id="" class="inputbottom" style="width:150px;" value="${cashApplyBills.appropriationNum}"/>
					<input type="button" class="nwlgr btncon ffzhbtn sfk" id="anum"/>
					</td>
					<td align="left" colspan="2" width="30px">
					收方
					</td>
					<td align="center" width="50px">
					收方人：
					</td>
					<td align="center" class="rece" width="150px">
					<input type="hidden" id="ReceivablesID" name="cashApplyBills.ReceivablesID"  value="${cashApplyBills.receivablesID}"/>
					<input type="text"  id="ReceivablesName"  class="querys model1 fou inputbottom"  size="20"
											 name="cashApplyBills.ReceivablesName" style="width:75%; display:inline;" value="${cashApplyBills.receivablesName}"/>
					<input type="button" class="btncon sfbtn sfk wlgr"  id="rece"/>
					</td>
					<td align="center" width="55px">
					<span>收方帐号：</span>
					</td>
					<td align="left" class="rnum" width="150px">
					<input type="text" name="cashApplyBills.recropriationNum" id="" class="inputbottom" style="width:150px;" value="${cashApplyBills.recropriationNum}"/>
					<input type="button" class="nwlgr btncon sfzhbtn sfk" id="rnum"/>
					</td>
				</tr>
			</table>
			<div style="margin-top:10px;">
			备注：<input type="text" name="cashierBills.remark" class="inputbottom" style="width:800px;" readonly="readonly" value="${cashierBills.remark}">
			<p>制单人：<input type="text" class="inputbottom" size="15" value="${staff.staffName}"/>&nbsp;&nbsp;&nbsp;制单日期：<input type="text" class="inputbottom" id="createD" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" size="20" >
			</p>
			</div>
			<table width="1000px" border="0" cellpadding="0" cellspacing="0" style="display:none;" 
				id="aa">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="left" width="70px">审核信息：</td>
					<td align="left" colspan="9"><input type="text" id="remark"
						class="inputbottom" style="width:80%;" readonly="readonly" value="${commStr}"/>
					</td>
				</tr>
				<tr class="aduittr">
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
				<tr class="aduittr">
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
						<input  name="cashierBills.cashierBillsID" id="cashierBillsID" style="display:none"/>
						<input  name="statusForPerson" id="statusForPerson" value="00" style="display:none"/>
					</td>
				</tr>
				<!-- <tr id="fl"  style="display: none;"> 
					<td colspan="2">
						<div width="100%">
							<table width="100%" cellspacing="10" cellpadding="10"
								style="border-top: 1px solid #FFFFFF;">
								<tr id="xs"  style="display: none;">
									<td>销售明细（订单明细）设置分类:</td>
									<td><input type="radio" name="xsradio" value="ck" id="xsradio"/>
										出库产品</td>
									<td><input type="radio" name="xsradio" value="fck" id="xsradio"/>
										非库存产品</td>
								</tr>
								<tr id="sq"  style="display: none;">
									<td>收款明细、欠款明细设置分类:</td>
									<td><input type="radio" name="sqradio" value="fy" id="sqradio"/> 费用</td>
									<td><input type="radio" name="sqradio" value="wp" id="sqradio"/>
										物品采购</td>
								</tr>
								<tr id="pz"  style="display: none;">  style="display: none;"
									<td colspan="3" style="border-top: 1px solid #FFFFFF;" align="right"><input type="checkbox" name="pzckbox"  cellpadding="20" margin="20" value="sc"/>&nbsp;生成凭证&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</table>
							
						</div></td>
				</tr> -->
			</table>
			<div align="center">
                <input type="button" class="input-button" id="submitResult2" value=" 提交 " /> <input type="submit" name="submit" style="display:none"/>
            </div>
            </center>
        </div>
    </form>
    
    <!--单据类别 -->
		<form name="sbillForm" id="sbillForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 50%; top: 30%"
				id="jqModelsbill">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
				请选择打印单据类型
					<div class="close">
					</div>
				</div>
				<table width="250" id="SearchTable">
					<tr>
						<td style="width: 80px">
							
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" id="xp" name="dy" value="xp"/>小票
						</td>
						<td style="width: 80px">
							<input type="radio" id="pj" name="dy" value="pj" checked="checked"/>票据
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
	
	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
</body>
</html>

