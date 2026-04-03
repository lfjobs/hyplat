<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>凭证管理--查看</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/voucher/voucher_see.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>		
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
	left: 550px;
	top: 350px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var pNumber='${pageNumber}';
    var search="${search}";
    var select=0;
    var billid="<%=request.getAttribute("billid")%>";
     var  st = "${voucher.status}";
     var aa="${aa}";//00 凭证录入 01凭证审核 02 凭证记账
</script>
</head>	
	<body>
		<%-- 用来显示审核章 --%>
		<div id="apDiv1"></div>
		<form name="costSheetForm" id="costSheetForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">凭证管理</div>
				</div>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="20" width="7%" align="right"><span class="STYLE1">公司：</span></td>
						<td>${voucher.companyName}</td>
						<td align="right">部门：</td>
						<td align="left" id="dept">${voucher.organizationName}</td>
						<td height="20" align="right">凭证号：</td>
						<td>${voucher.voucherNum }</td>
					</tr>
				</table>
				<table width="99%" height="250px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1" style="position: absolute; width: 100%; height: 240px; overflow: scroll;">
								<table width="1215" align="center" cellpadding="0" cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">凭证科目</th>
										<th align="center" bgcolor="#E4F1FA">摘要</th>
										<th align="center" bgcolor="#E4F1FA">借方金额</th>
										<th align="center" bgcolor="#E4F1FA">贷方金额</th>
									</tr>
									<s:iterator value="pageForm.list">
											<tr class="xggoods" style="height: 10px;" id="${csdID}">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="subjectsName">${subjectsName}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="reasonThing">${reasonThing}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="loan">${loan}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span  id="forLoan">${forLoan}</span>
											</td>
											
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="40" width="7%" align="right"><span class="STYLE1">制单人：</span></td>
						<td>${voucher.makePeople}</td>
						<td align="right">制单日期：</td>
						<td>${fn:substring(voucher.makeDate, 0, 19)}</td>
				
						<td height="40" width="7%" align="right"><span class="STYLE1">审核人：</span></td>
						<td>${voucher.auditingPeo}</td>
						<td align="right">审核日期：</td>
						<td>${fn:substring(voucher.auditingDate, 0, 19)}</td>
					
						<td height="40" width="7%" align="right"><span class="STYLE1">记账人：</span></td>
						<td>${voucher.tallyPeople}</td>
						<td align="right">记账日期：</td>
						<td>${fn:substring(voucher.tallyDate, 0, 19)}</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td height="30" align="center">
							<input type="button" class="btn001 JQueryprint" name="button1" value="打印预览"/>
							<input type="button" class="btn001 JQueryClose" name="button2" value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
