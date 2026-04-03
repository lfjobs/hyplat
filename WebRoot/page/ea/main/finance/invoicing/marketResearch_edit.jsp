<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>市场调查单据</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/marketResearch_edit.js"></script>
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
    var type="${type}";
    var select=0;
    var sdate="${sdate}";
    var edate="${edate}";
</script>

	<body>
		<form name="purchaseForm" id="purchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="billStatus" id="billStatus" value="${financialBill.billStatus}"/>
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						市场调查单据
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
							<input type="hidden" name="financialBill.journalNum"
								id="journalNum" value="${financialBill.journalNum}" />
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
						<td align="right">附件：</td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
						<td width="15%" align="left"></td>
					</tr>
				</table>
				<table width="99%" height="158px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 158px; overflow: scroll;">
								<table width="1255" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA" id="reQuantity" style="display:none">
											收货数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名编号
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
											品牌
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
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
										<th align="center" width="100" bgcolor="#E4F1FA">
											调查金额
										</th>
										<th align="center" width="100" bgcolor="#E4F1FA">
											误差金额
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											处理意见
										</th>

									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="numbers">${numbers}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF" id="reQuantity" style="display: none">
												<input class="input jisuan put3" size="5" style="margin-left: 2px;" name="reQuantity" id="reQuantity" value="${quantity}"/>
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
												<span id="type">${type}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="brand">${brand}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="modelNumber">${modelNumber}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="unit" name="unit">${unit}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="quantity">${quantity}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="unitPrice">${unitPrice}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="amount">${amount}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="surveyAmount">${surveyAmount}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="errorAmount">${errorAmount}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span name="processOpinion">${processOpinion}</span>
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>   
						<td width="15%">
							<span id="ccompanyname" class="qk">${contactCompanyView.companyName}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${contactCompanyView.companyTel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${contactCompanyView.cresponsible}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${financialBill.accountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${contactCompanyView.responsibleTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${contactCompanyView.companyAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${contactCompanyView.industryType}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${financialBill.ccompanyRelationship}</span>
							</div>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${contactUser.staffName}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${contactUser.reference}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${contactUser.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${financialBill.userAccountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${contactUser.referenceCode}</span>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${contactUser.referenceOrganization}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${contactUser.staffAddress}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${financialBill.cstaffRelationship}</span>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td height="30" align="center">
							<input type="button" class="btn001 JQueryPrint" name="button2"
								value="打印预览" />
							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

	</body>
</html>