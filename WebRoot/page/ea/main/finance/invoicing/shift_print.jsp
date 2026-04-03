<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>移库单据打印</title>
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
</style>
		<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold">
						&nbsp;${financialBill.billsType}
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
						${companyname}
					</td>
					<td width="7%" align="left">
						部门：
					</td>
					<td width="13%" align="left">
						${organizationname}
					</td>
					<td width="7%" align="left">
						责任人：
					</td>
					<td width="20%" align="left">
						<c:if test="${financialBill.staffName!=null}">${financialBill.staffName}</c:if>
					</td>
					<td width="30%" colspan="2" align="left">
						制单日期：${fn:substring(financialBill.billsDate, 0, 10)}
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="13%" align="left">
						制单人：${financialBill.billStaffName}
					</td>
					<td width="9%" height="25" align="left">
						银行账户：
					</td>
					<td width="25%" height="25" align="left">
						${financialBill.companyBankNum}
					</td>
					<td colspan="8" align="left">
						凭证号：<%
						hy.ea.bo.invoicing.FinancialBill data = (hy.ea.bo.invoicing.FinancialBill) request
								.getAttribute("FinancialBill");
						if (data != null) {
							StringBuffer barCode = new StringBuffer();
							barCode.append("<img src='");
							barCode.append(request.getContextPath());
							barCode.append("/CreateBarCode?data=");
							barCode.append(data.getJournalNum());
							barCode
									.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
							out.println(barCode.toString());
						} else {
							out.println("no data");
						}
					%><br />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ${financialBill.journalNum}
					</td>
				</tr>
			</table>
			<table width="620" cellpadding="0" cellspacing="0" class="table">
				<tr>
					<th align="center">
								库
							</th>
							<th align="center">
								区
							</th>
							<th align="center">
								架
							</th>
							<th align="center">
								位
							</th>
							<th align="center">
								统一分类条码
							</th>
							<th align="center">
								费用或品名名称
							</th>
							<th align="center">
								类型
							</th>
							<th align="center">
								单位
							</th>
							<th align="center">
								规格
							</th>
							<th align="center">
								数量
							</th>
							<th align="center">
								移库量
							</th>
							<th align="center">
								库
							</th>
							<th align="center">
								区
							</th>
							<th align="center">
								架
							</th>
							<th align="center">
								位
							</th>
							<th align="center">
								备注
							</th>
				</tr>
				<s:iterator value="BillsGoodList">
					<tr>
					
								<td align="center" bgcolor="#FFFFFF">${pware}</td>
								<td align="center" bgcolor="#FFFFFF">${parea}</td>
								<td align="center" bgcolor="#FFFFFF">${pframe}</td>
								<td align="center" bgcolor="#FFFFFF">${place}</td>
								<td align="center" bgcolor="#FFFFFF">${sortCode}</td>
								<td align="center" bgcolor="#FFFFFF">${goodsName}</td>
								<td align="center" bgcolor="#FFFFFF">${type}</td>
								<td align="center" bgcolor="#FFFFFF">${unit}</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="acquiesceStandard"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">${invenQuantity}</td>
								<td align="center" bgcolor="#FFFFFF">${shiftQuantity}</td>
								<td align="center" bgcolor="#FFFFFF">${ku}</td>
								<td align="center" bgcolor="#FFFFFF">${qu}</td>
								<td align="center" bgcolor="#FFFFFF">${jia}</td>
								<td align="center" bgcolor="#FFFFFF">${wei}</td>
					</tr>
				</s:iterator>
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
