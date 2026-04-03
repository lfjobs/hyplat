<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>移库单据</title>
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/shift_edit.js"></script>
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
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
		<script type="text/javascript">
	var tokens = 0;
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var financialbillID="${financialBill.financialbillID}";
	var token = 0;
	var pNumber=${pageNumber};
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var notoken = 0;
	var sdate="${sdate}";
    var edate="${edate}";
</script>

	</head>
	<body>
		<div id="apDiv1"></div>
		<form name="PurchaseForm" id="PurchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						移库单据
					</div>
				</div>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="20" align="right">
							凭证号：
						</td>
						<td>
							<input type="hidden" name="financialBill.financialbillID"
								id="cashierID" value="${financialBill.financialbillID}" />
							<input type="hidden" name="financialBill.financialbillKey"
								id="goodsBillsKey" value="${financialBill.financialbillKey}" />
							${financialBill.journalNum}
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							${financialBill.billsType}
						</td>
						<td align="right">
							制单日期：
						</td>
						<td>
							${fn:substring(financialBill.billsDate, 0, 10)}
						</td>
					</tr>
					<tr>
						<td height="20" width="7%" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							${companyname} 
						<td align="right">
							部门：
						</td>
						<td align="left" id="dept">
							${organizationname}
						</td>
						<td align="right">
							<div id="u1170_rtf">
								责任人：
							</div>
						</td>
						<td width="15%" align="left" id="staff">
						${financialBill.staffName}
						</td>
					</tr>
					<tr>
						<td height="20" width="7%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td>
							${financialBill.companyBankNum}
						</td>
						<td align="right"></td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
					</tr>
				</table>
				<div id="Layer1"
					style="width:100%; height: 340px; overflow: scroll;">
					<table width="100%" align="center" cellpadding="0" cellspacing="0"
						class="table" id="tt">
						<tr>
							<th height="25" align="center" bgcolor="#E4F1FA">
								序号
							</th>
							<th align="center" bgcolor="#E4F1FA">
								库
							</th>
							<th align="center" bgcolor="#E4F1FA">
								区
							</th>
							<th align="center" bgcolor="#E4F1FA">
								架
							</th>
							<th align="center" bgcolor="#E4F1FA">
								位
							</th>
							<th align="center" bgcolor="#E4F1FA">
								统一分类条码
							</th>
							<th align="center" bgcolor="#E4F1FA">
								费用或品名名称
							</th>
							<th align="center" bgcolor="#E4F1FA">
								类型
							</th>
							<th align="center" bgcolor="#E4F1FA">
								单位
							</th>
							<th align="center" bgcolor="#E4F1FA">
								规格
							</th>
							<th align="center" bgcolor="#E4F1FA">
								数量
							</th>
							<th align="center" bgcolor="#E4F1FA">
								移库量
							</th>
							<th align="center" bgcolor="#E4F1FA">
								库
							</th>
							<th align="center" bgcolor="#E4F1FA">
								区
							</th>
							<th align="center" bgcolor="#E4F1FA">
								架
							</th>
							<th align="center" bgcolor="#E4F1FA">
								位
							</th>
							<th align="center" bgcolor="#E4F1FA">
								备注
							</th>
							<th align="center" bgcolor="#E4F1FA">
								
							</th>
						</tr>
						<c:set var="aa" value="1"></c:set>
						<s:iterator value="BillsGoodList" >
							<tr id="kelong" class="xggoods">
								<td height="25" align="center" bgcolor="#FFFFFF">
									<span name="number">${aa }</span>
									<c:set var="aa" value="${aa +1}"></c:set>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<input id="goodsID" name=goodsID type="hidden" />
									<span id="warehouseName">${pware}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="areaName">${parea}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="frameName">${pframe}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="positionName">${place}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="sortCode">${sortCode}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="goodsName">${goodsName}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="type">${type}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="unit">${unit}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="acquiesceStandard"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="invenQuantity">${invenQuantity}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="stocktakingQuantity">${shiftQuantity}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="ku">${ku}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="qu">${qu}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="jia">${jia}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="wei">${wei}</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="remindContent"></span>
								</td>
							</tr>
						</s:iterator>
					</table>
					</div>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
						<tr>
						<td height="30" align="center">
								<input type="button" class="btn001 JQueryprint" name="button1"
									value="打印预览"/>
								<input type="button" class="btn001 JQueryClose" name="button2"
									value="返回" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
		</form>
	</body>
</html>
