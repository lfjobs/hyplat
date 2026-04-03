<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>实际单据查看</title>
		
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
			src="<%=basePath%>js/ea/finance/responsible_edit.js"></script>
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
	var  billsType = "${cashierBills.billsType}";
	var zctype="${zctype}";
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
			<input type="hidden" name="billtname" id="billtname"/>
			<input type="hidden" name="srandom" value="${session_value}"/>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td style="height:24px;" align="left">
							<input type="button" class="menubtn JQuerySubmitgd grey" disabled="disabled" value="保存" />
							<input type="button" class="menubtn print" name="button3" value="打印" />
							<input type="button" class="menubtn audit grey" name="button3" value="审核" />
							<input type="button" class="menubtn generate grey" name="button3" value="生成" />
							<input type="button" class="menubtn newsheet grey" disabled="disabled" value="增加" />
							<input type="button" class="menubtn updatesheet grey" disabled="disabled" value="修改" />
							<input type="button" class="menubtn deletesheet grey" value="删除" />
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
			   <tr><td align="center" colspan="6" id="titlestyle" ><span>${cashierBills.billsType}</span><input type="hidden" name="cashierBills.billsType" value="${cashierBills.billsType}"/>
				<input type="hidden"  name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}" id="cashierBillsID"/>
			   </td></tr>
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
						value="${cashierBills.departmentName}" style="width:160px;" readonly="readonly"/>
						</td>
					 <td align="right">部门责任人：
					</td>
					<td style="width:150px;">
						<%-- 获取责任人将姓名编号ID均保存 --%> <input type="text"  value="${cashierBills.staffName}"
						name="cashierBills.staffName" id="staffname"
						class="notnull inputbottom"  style="width:100px;" /> <input
						type="text" style="display:none;" name="cashierBills.staffID" value="${cashierBills.staffID}"
						id="staffid" /> <input type="text" style="display:none;"
						name="cashierBills.staffCode" id="staffcode" value="${cashierBills.staffCode}" readonly="readonly"/>
						</td>
				</tr>
				<tr>
					<td height="20" align="right" >主项目名称：</td>

					<td style="width:210px;"><input type="text" id="xmtypename" readonly
						name="cashierBills.xmtypename" value="${cashierBills.xmtypename}"
						class="notnull inputbottom" style="width:180px;" class="put3" /> <input
						type="hidden" name="cashierBills.xmtype" id="xmtype"
						value="${cashierBills.xmtype}" readonly="readonly"/>
					</td>
					<td align="right"><span
						class="STYLE1">子项目名称：</span></td>
					<td style="width:200px;"><input type="text" id="projectName" readonly
						name="cashierBills.projectName"  class="notnull inputbottom" value="${cashierBills.projectName}"
						style="width:160px;" /><input type="hidden" id="proID"
						name="cashierBills.proID" value="${cashierBills.proID}" /><input
						type="hidden" id="projectCode" name="cashierBills.projectCode"
						value="${cashierBills.projectCode}" /><input type="hidden"
						id="xmtypename2" value="${cashierBills.xmtypename}" readonly="readonly"/>
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

			<input type="hidden" id="line"/>
			<ul id="xmul" class="filetree"></ul>	
			<s:token></s:token>
		</form>
	<div style="margin-top:10px;"> 
		备注：<input type="text" name="cashierBills.remark" class="inputbottom" style="width:96%;" value="${cashierBills.remark}" readonly="readonly">
		<p>&nbsp;</p>
		<p>制单人：<input type="text" class="inputbottom" size="15" value="${cashierBills.inputName}" readonly="readonly"/>&nbsp;&nbsp;&nbsp;制单日期：<input type="text" class="inputbottom" id="createD" value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />" size="20" readonly="readonly">
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
			<table width="300" id="SearchTable">
				
				<tr>
					<td align="right">
						生成单据类别：
					</td>
					<td style="width: 185px">
						<input type="checkbox" value="收款单" checked="checked" name="btnname" id="btnname"/> 收款单 &nbsp;&nbsp;&nbsp;&nbsp;
						<!-- <input type="checkbox" value="待收款单" name="btnname" id="btnname"/> 待收款单 -->
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="billname"
					value=" 确定 "/>
				<input name="search" type="hidden" value="search" />
			</div>
		</div>
	</form>
	
	<%------------------------------------用于表单提交-----------------------------------%>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>

