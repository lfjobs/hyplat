<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>调拨出库单查看审核</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/allocationManagement_edit.js"></script>
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

</style>
	</head>
	<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var pNumber=${pageNumber};
    var search="${search}";
    var select=0;
    var sdate="${sdate}";
    var edate="${edate}";
    var billStatus='${billStatus}';
    var loginstaffcheck=${loginstaffcheck};//判断审核次数
</script>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<form name="purchaseForm" id="purchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
                 <table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td height="30" align="left">
						<input type="button" class="menubtn grey" name="button3" value="保存" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="提交审核" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="增加一行" disabled="disabled"/><input type="button" 
						class="menubtn grey" name="button" value="删除新增行" disabled="disabled"/><input type="button" class="menubtn JQueryprint" name="button"
								value="打印预览" /><input type="button" class="menubtn JQueryClose" name="button2"
								value="关闭" /><input type="button" 
						class="menubtn grey" name="button" value="复制" disabled="disabled"/>
						</td>
					</tr>
				</table>
                <table width="800" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;">
					<tr>
						<td height="25" align="center" style="font-weight: bold" id="titlestyle">
						${billStatus=='07'?"调拨出库单":"出库管理"}
						</td>
					</tr>
				</table>
				<table width="800" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td height="30" align="right">
							单据编号：
						</td>
						<td>
							<input type="hidden" name="cashierBills.cashierBillsID"
								id="cashierID" value="${cashierBills.cashierBillsID}" />
							<input type="hidden" name="financialBill.financialbillID"
								id="goodsBillsKey" value="${financialBill.financialbillID}"/>
							<input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.journalNum}"/>
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
                          <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.billsType}"/>
						</td>
						<td align="right">
							制单日期：
						</td>
						<td>
                           <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${fn:substring(financialBill.billsDate, 0, 10)}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">调出机构：</span>
						</td>
						<td>
                         <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${companyname}"/>
						<td align="right">
							<span class="STYLE1">调入机构：</span>
						</td>
						<td align="left" id="dept">
                          <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.drccompanyName}"/>
						</td>
						<td align="right">
						   <span class="STYLE1">责任人：</span>
						</td>
						<td align="left" id="staff">
                          <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.staffName}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">联系方式：</span>
						</td>
						<td>
                         <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.ccompanyTel}"/>
						<td align="right">
							联系方式：
						</td>
						<td align="left">
                          <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.drccompanyTel}"/>
						</td>
						<td align="right">
						         联系方式：
						</td>
						<td align="left">
                          <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.staffPhone}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">调出仓库：</span>
						</td>
						<td>
                         <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.depotName}"/>
						</td>
						<td align="right">调入仓库：</td>
						<td align="left">
                         <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.drdepotName}"/>
                                                </td>
						<td align="right">调入区域：</td>
						<td align="left">
                         <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.areaName}"/>
                                                </td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">调入货架：</span>
						</td>
						<td>
							<input type="text" class="inputbottom" style="width: 160px;"
								   readonly="readonly" value="${financialBill.frameName}"/>
						</td>
						<td align="right">调入展位：</td>
						<td align="left">
							<input type="text" class="inputbottom" style="width: 160px;"
								   readonly="readonly" value="${financialBill.positionName}"/>
						</td>
						<td align="right">物流方式：</td>
						<td align="left">
							<input type="text" class="inputbottom" style="width: 160px;"
								   readonly="readonly" value="${financialBill.logisticsType}"/>
						</td>
					</tr>


				</table>
				<table width="800" height="158px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:15px;margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 800px; height: 158px; overflow: auto;">
								<table width="800" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											统一分类条码
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA">
											类别
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品牌规格
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											备注
										</th>
									</tr>
									<s:iterator value="BillsGoodList">
										<tr class="xggoods">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="goodsNomber">${goodsNomber}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsID" id="goodsID" value="${goodsID }"/>
												<span id="goodsNum">${goodsNum}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="sortCode">${sortCode}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsName">${goodsName}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="typeID">${typeID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="standard">${standard}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsVariableID" name="goodsVariableID">${goodsVariableID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="quantity">${quantity}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="price">${price}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="money">${money}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="remark">${remark}</span>
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
                <table width="99%" border="0" style="margin-left: 15px;" align="center" cellpadding="0" cellspacing="0">
				   <tr><td colspan="2">&nbsp;</td></tr>
				   <tr>
				   <td align="left" width="50px">备注：</td>
				   <td align="left" colspan="9">
				   <input type="text" id="remark" class="inputbottom" style="width:80%;" readonly="readonly"/>
				   </td>
				   </tr>
				</table>
				<table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
				<tr><td>
				<input type="hidden" id="staffauditname" 
				value="${ManStaffName}">
				<input type="hidden" id="staffauditcode" 
				value="${ManStaffCode}">
				<input type="hidden" id="staffauditid" 
				value="${ManStaffId}">
				</td></tr>
				<tr>
					<td height="25" align="right">
						公司经理：
					</td>
					<td>
						<input type="text" class="inputbottom gsjl" value="${billcheckmap['gsjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
						<input type="button" class="btncon verify" id="gsjl" />
					    </c:if>
					</td>
					<td align="right">
						部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom bmzg" value="${billcheckmap['bmzg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>
						<input type="button" class="btncon verify" id="bmzg" />
					    </c:if>
					</td>
					<td  align="right">
						人事处：
					</td>
					<td>
						<input type="text" class="inputbottom rsc" value="${billcheckmap['rsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
						<input type="button" class="btncon verify" id="rsc"/>
						</c:if>
					</td>
					<td  align="right">
						财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom cwsh" value="${billcheckmap['cwsh']}" readonly="readonly"/>
						<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
						<input type="button" class="btncon verify" id="cwsh"/>
						</c:if>
					</td>
					<td  align="center">
						收款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom skr" value="${billcheckmap['skr']}"readonly="readonly"/>
						<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
						<input type="button" class="btncon verify" id="skr"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td  height="25" align="right">
						总部总经理：
					</td>
					<td>
						<input type="text" class="inputbottom zjl" value="${billcheckmap['zjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
						<input type="button" class="btncon verify"  id="zjl"/>
						</c:if>
					</td>
					<td  align="right">
						总部部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom zg" value="${billcheckmap['zg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
						<input type="button" class="btncon verify" id="zg"/>
						</c:if>
					</td>
					<td  align="right">
						总部人事处：
					</td>
					<td>
						<input type="text" class="inputbottom zbrsc" value="${billcheckmap['zbrsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
						<input type="button" class="btncon verify" id="zbrsc"/>
						</c:if>
					</td>
					<td align="right">
						总财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom zbcw" value="${billcheckmap['zbcw']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
						<input type="button" class="btncon verify" id="zbcw"/>
						</c:if>
					</td>
					<td  align="center">
						交款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom jkr" value="${billcheckmap['jkr']}" readonly="readonly"/>
						<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
						<input type="button" class="btncon verify" id="jkr"/>
						</c:if>
					</td>
				</tr>
			</table>
			</div>
			<s:token></s:token>
		</form>
		<!--审核备注 -->
        <div class="jqmWindow jqmWindowcss" style="width: 350px;top:50%" id="jqModelSearch">
                <div class="drag">审核信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>备注：</td>
                        <td>
                        <input type="text" id="remarks" class="inputbottom" style="width:300px;"/>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tocheckok" value="确定" />
                    <input type="button" class="input-button" id="tocheckclose" value="取消" />
                </div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

	</body>
</html>
