<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>市场调查单据打印</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/marketResearch_print.js"></script>
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
		<div id="apDiv1"></div>
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
					<td width="6%" height="25" align="left">
						公司：
					</td>
					<td width="30%" align="left">
						${companyname}
					</td>
					<td width="6%" align="left">
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
					<td width="32%" colspan="2" align="left">
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
					<td width="29%" height="25" align="left">
						${financialBill.companyBankNum}
					</td>
					<td colspan="8" align="left">
						凭证号:<%
						hy.ea.bo.invoicing.FinancialBill data = (hy.ea.bo.invoicing.FinancialBill) request
								.getAttribute("financialBill");
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
					<th width="70" align="center">
						品名编号
					</th>
					<th width="80" align="center">
						费用或品名名称
					</th>
					<th width="60" align="center">
						类型
					</th>
					<th width="55" align="center">
						品牌
					</th>
					<th width="55" align="center">
						型号
					</th>
					<th width="40" align="center">
						单位
					</th>
					<th width="40" align="center">
						数量
					</th>
					<th width="40" align="center">
						单价
					</th>
					<th width="60" align="center">
						金额
					</th>
					<th width="60" align="center">
						调查金额
					</th>
					<th width="60" align="center">
						误差金额
					</th>
				</tr>
				<s:iterator value="pageForm.list">
					<tr>
						<!-- 品名编号 -->
						<td align="center" bgcolor="#FFFFFF">
							${goodsNum}
						</td>
						<!--费用或品名名称 -->
						<td align="center" bgcolor="#FFFFFF">
							${goodsName}
						</td>
						<!-- 类型 -->
						<td align="center" bgcolor="#FFFFFF">
							${type}
						</td>
						<!-- 品牌 -->
						<td align="center" bgcolor="#FFFFFF">
							${brand}
						</td>
						<!-- 型号 -->
						<td align="center" bgcolor="#FFFFFF">
							${modelNumber}
						</td>
						<!-- 单位 -->
						<td align="center" bgcolor="#FFFFFF">
							${unit}
						</td>
						<!-- 数量 -->
						<td align="center" bgcolor="#FFFFFF">
							${quantity}
						</td>
						<!-- 单价 -->
						<td align="center" bgcolor="#FFFFFF">
							${unitPrice}
						</td>
						<!-- 金额 -->
						<td align="center" bgcolor="#FFFFFF" class="moneysum">
							${amount}
						</td>
						<!-- 调查金额 -->
						<td align="center" bgcolor="#FFFFFF" class="surmoneysum">
							${surveyAmount}
						</td>
						<!-- 误差金额 -->
						<td align="center" bgcolor="#FFFFFF" class="errmoneysum">
							${errorAmount}
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
						&nbsp;
					</td>
					<td id="monsum" align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td id="surmonsum" align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td id="errmonsum" align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="border-left: 1px solid #000000; border-right: 1px solid #000000">
				<tr>
					<td width="208" height="15" align="center"
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
						${contactCompanyView.companyName}
					</td>
					<td width="60" align="center">
						单位电话
					</td>
					<td width="90">
						${contactCompanyView.companyTel}
					</td>
					<td width="83" align="center">
						单位负责人
					</td>
					<td width="120">
						${contactCompanyView.cresponsible}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						公司地址
					</td>
					<td colspan="3">
						${contactCompanyView.companyAddr}
					</td>
					<td align="center">
						单位往来关系
					</td>
					<td>
						${costSheetBill.ccompanyRelationship}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						往来个人
					</td>
					<td>
						${contactUser.staffName}
					</td>
					<td align="center">
						电话
					</td>
					<td>
						${contactUser.reference}
					</td>
					<td align="center">
						个人身份证号
					</td>
					<td>
						${contactUser.staffIdentityCard}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						QQ
					</td>
					<td>
						${contactUser.referenceCode}
					</td>
					<td align="center">
						邮箱
					</td>
					<td>
						${contactUser.referenceOrganization}
					</td>
					<td align="center">
						个人往来关系
					</td>
					<td>
						${costSheetBill.cstaffRelationship}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						地址
					</td>
					<td colspan="5">
						${contactUser.staffAddress}
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