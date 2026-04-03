<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>收入预算打印</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/csbsprint.js"></script>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 9px;
}

body {
	margin-left: 15px;
}

#apDiv1 {
	position: absolute;
	left: 507px;
	top: 287px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
		<script type="text/javascript">
    var taxstatusDu="${costSheetBill.billStatus}";
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold">
						&nbsp;${earnBudgetBill.billsType}
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
				</tr>
				<tr>
					<td width="7%" height="25" align="left">
						公司：
					</td>
					<td width="30%" align="left">
						${earnBudgetBill.companyName}
					</td>
					<td width="7%" align="left">
						部门：
					</td>
					<td width="13%" align="left">
						${earnBudgetBill.organizationName}
					</td>
					<td width="7%" align="left">
						责任人：
					</td>
					<td width="20%" align="left">
						<c:if test="${earnBudgetBill.staffName!=null}">${earnBudgetBill.staffName}</c:if>
					</td>
					<td width="30%" colspan="2" align="left">
						制单日期：${fn:substring(earnBudgetBill.billsDate, 0, 10)}
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="13%" align="left">
						制单人：${earnBudgetBill.billStaffName}
					</td>
					<td width="9%" height="25" align="left">
						银行账户：
					</td>
					<td width="25%" height="25" align="left">
						${earnBudgetBill.companyBankNum}
					</td>
					<td colspan="8" align="left">
						凭证号：<%
						hy.ea.bo.invoicing.EarnBudgetBill data = (hy.ea.bo.invoicing.EarnBudgetBill) request
								.getAttribute("earnBudgetBill");
						if (data != null) {
							StringBuffer barCode = new StringBuffer();
							barCode.append("<img src='");
							barCode.append(request.getContextPath());
							barCode.append("/CreateBarCode?data=");
							barCode.append(data.getBillNum());
							barCode
									.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
							out.println(barCode.toString());
						} else {
							out.println("no data");
						}
					%><br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ${earnBudgetBill.billNum}
					</td>
				</tr>
			</table>
			<table width="620" cellpadding="0" cellspacing="0" class="table">
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						产品编号
					</th>
					<th align="center">
						产品名称
					</th>
					<th align="center">
						产品重量
					</th>
					<th align="center">
						产品单位
					</th>
					<th align="center">
						产品规格
					</th>
					<th align="center">
						数量
					</th>
					<th align="center">
						预算单价
					</th>
					<th align="center">
						预算日金额
					</th>
					<th align="center">
						预算周金额
					</th>
					<th align="center">
						预算月金额
					</th>
					<th align="center">
						预算季度金额
					</th>
					<th align="center">
						预算年金额
					</th>
					<th align="center" width="20px">
						完成情况
					</th>
				</tr>
				<s:iterator value="earnBudgetDetailList" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 产品编号 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="productNum">${productNum}</span>
						</td>
						<!-- 产品名称 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="productName">${productName}</span>
						</td>
						<!-- 产品重量 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="weight">${weight}</span>
						</td>
						<!-- 单位 -->
						<td align="center" bgcolor="#FFFFFF">
							${productUnit}
						</td>
						<!-- 产品重量 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="productStandard">${productStandard}</span>
						</td>
						<!-- 数量 -->
						<td align="center" bgcolor="#FFFFFF">
							${bdquantity}
						</td>
						<!-- 单价 -->
						<td align="center" bgcolor="#FFFFFF">
							${bunitPrice}
						</td>
						<!-- 总金额 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="bdamount">${bdamount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="bwamount">${bwamount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="bmamount">${bmamount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="bsamount">${bsamount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="byamount">${byamount}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<input type="checkbox"/>完成
							<input type="checkbox"/>未完成
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td height="15" align="center" bgcolor="#FFFFFF">
						合计
					</td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						<span id="monsum"></span>
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="border-left: 1px solid #000000; border-right: 1px solid #000000">
				<tr>
					<td width="186" height="15" align="center"
						style="border-right: 1px solid #000000;">
						应付款单位或个人所缴金额
					</td>
					<td id="daxie">
						金额（大写）：&nbsp;&nbsp;
						<span id="6"></span>&nbsp;&nbsp;
						<span style="color: red">万</span>&nbsp;&nbsp;
						<span id="5"></span>&nbsp;&nbsp;
						<span style="color: red">仟</span>&nbsp;&nbsp;
						<span id="4"></span>&nbsp;&nbsp;
						<span style="color: red">佰</span>&nbsp;&nbsp;
						<span id="3"></span>&nbsp;&nbsp;
						<span style="color: red">拾</span>&nbsp;&nbsp;
						<span id="2"></span>&nbsp;&nbsp;
						<span style="color: red">元</span>&nbsp;&nbsp;
						<span id="1"></span>&nbsp;&nbsp;
						<span style="color: red">角</span>&nbsp;&nbsp;
						<span id="0"></span>&nbsp;&nbsp;
						<span style="color: red">分</span>
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr>
					<td width="70" height="15" align="center">
						往来单位
					</td>
					<td width="190">
						${earnBudgetBill.companyName}
					</td>
					<td width="60" align="center">
						单位电话
					</td>
					<td width="90">
						${earnBudgetBill.companyTel}
					</td>
					<td width="83" align="center">
						单位负责人
					</td>
					<td width="120">
						${earnBudgetBill.cresponsible}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						公司地址
					</td>
					<td colspan="3">
						${earnBudgetBill.companyAddr}
					</td>
					<td align="center">
						单位往来关系
					</td>
					<td>
						${earnBudgetBill.ccompanyRelationship}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						往来个人
					</td>
					<td>
						${earnBudgetBill.cstaffName}
					</td>
					<td align="center">
						电话
					</td>
					<td>
						${earnBudgetBill.reference}
					</td>
					<td align="center">
						个人身份证号
					</td>
					<td>
						${earnBudgetBill.staffIdentityCard}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						QQ
					</td>
					<td>
						${earnBudgetBill.referenceCode}
					</td>
					<td align="center">
						邮箱
					</td>
					<td>
						${earnBudgetBill.referenceOrganization}
					</td>
					<td align="center">
						个人往来关系
					</td>
					<td>
						${earnBudgetBill.cstaffRelationship}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						地址
					</td>
					<td colspan="5">
						${earnBudgetBill.staffAddress}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						备注
					</td>
					<td height="15" colspan="6" align="center">
						<input type="text"
							style="width: 530px; height: 15px; background: none; border: 0px; text-align: center" />
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="80" height="25" align="right">
						公司经理：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="90" align="right">
						部门主管：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						人事处：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						财务审核：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="center">
						收款人签字：
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="80" height="25" align="right">
						总部总经理：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="90" align="right">
						总部部门主管：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						总部人事处：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="right">
						总财务审核：
					</td>
					<td>
						&nbsp;
					</td>
					<td width="80" align="center">
						交款人签字：
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
</html>
