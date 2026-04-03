<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${financialBill.billsType}打印预览</title>
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
		<script src="<%=basePath%>/js/ea/finance/invoicing/purchase_print.js" type="text/javascript"></script>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid ;
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
	font-size: 14px;
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
#second td{
	padding-left: 5px;"
}
#third th{
	font-weight: 400;
}
.fifth tr td{
	text-align: left;
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
			<table width="760" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold;font-size: 18px;line-height: 38px;">&nbsp;
					    <c:if test="${financialBill.billStatus =='14'}">
						</c:if>

						<c:if test="${financialBill.billsType eq '采购入库单'}">
							收货入库单
						</c:if>
						<c:if test="${financialBill.billsType ne '采购入库单'}">
							${financialBill.billsType}
						</c:if>
					</td>
				</tr>
			</table>
			<table width="750" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
				</tr>
				<tr>
					<td width="7%" height="25" align="left">
						公司：
					</td>
					<td width="28%" align="left">
						${companyname}
					</td>
					<td width="7%" align="left">
						部门：
					</td>
					<td width="17%" align="left">
					    ${organizationname}
					</td>
					<td width="9%" align="left">
						责任人：
					</td>
					<td width="18%" align="left">
						<c:if test="${financialBill.staffsName!=null}">${financialBill.staffsName}</c:if>
					</td>
					<td width="15%" colspan="2" align="left">
						&nbsp;&nbsp;&nbsp;${fn:substring(financialBill.billsDate, 0, 10)}
					</td>
				</tr>
			</table>
			<table width="750" border="1" cellpadding="0" cellspacing="0" id="second" class="table"
			style="border-bottom: hidden">
				<tr >
					<td width="15%" align="left" style="height: 30px;">
						制单人：${financialBill.billStaffName}
					</td>
					<td width="30%" align="left">
						验货人：<c:if test="${financialBill.staffsName!=null}">${financialBill.staffsName}</c:if>
					</td>
					<%--<td width="20%" align="left">
						<c:if test="${financialBill.staffsName!=null}">${financialBill.staffsName}</c:if>
					</td>--%>

					<td align="left" rowspan="2">
					
						单据编号：<%
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
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${financialBill.journalNum}
					</td>
				</tr>
				<tr>
					<td colspan="2" style="height: 30px;">库房名：${financialBill.depotName}</td>
				</tr>
			</table>

			<table width="750" cellpadding="0" cellspacing="0" class="table" id="third">
				<tr style="height: 35px;">
					<th align="center" >
						序号
					</th>
					<th align="center">
						收货数量
					</th>
					<th align="center">
						品名编号
					</th>
					<th align="center">
						统一分类条码
					</th>
					<th align="center">
						费用或品名名称
					</th>
					<th align="center">
						类别
					</th>
					<th align="center">
						品牌规格
					</th>
					<th>
						单位
					</th>
					<th>
						合格数量
					</th>
					<th>
						单价
					</th>
					<th>
						金额
					</th>
					<th>
						供货商
					</th>
					<th>
						往来责任人
					</th>
					<th>
						备注
					</th>
				</tr>
				<s:iterator value="warehouseList" status="number">
					<tr style="height: 35px;">
						<td align="left" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 收货数量 -->
						<td align="left" bgcolor="#FFFFFF">
							<span id="reQuantity">${reQuantity}</span>
						</td>
						<!-- 品名编号 -->
						<td align="left" bgcolor="#FFFFFF">
							<span id="goodsNum">${goodsNum}</span>
						</td>
						<!-- 统一分类条码 -->
						<td align="left" bgcolor="#FFFFFF">
							<span id="sortCode">${sortCode}</span>
						</td>
						<!-- 费用或品名名称 -->
						<td align="left" bgcolor="#FFFFFF">
							${goodsName}
						</td>
						<!-- 类型 -->
						<td align="left" bgcolor="#FFFFFF">
							${typeID}
						</td>
						<!-- 品牌规格-->
						<td align="left" bgcolor="#FFFFFF">
							${standard}
						</td>
						<!-- 单位-->
						<td align="left" bgcolor="#FFFFFF">
							${goodsVariableID}
						</td>
						<!-- 合格数量-->
						<td align="right" bgcolor="#FFFFFF">
							${isQualify}
						</td>
						<!-- 单价-->
						<td align="right" bgcolor="#FFFFFF">
							${price}
						</td>
						<!-- 金额-->
						<td align="right" bgcolor="#FFFFFF">
							<span  class="monsum" id="amount">${isQualify*price}</span>
						</td>
						<!-- 供货商-->
						<td align="left" bgcolor="#FFFFFF">
							${ccompanyName}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${connectName}
						</td>	
						<!-- 备注-->
						<td align="left" bgcolor="#FFFFFF">
							${remindedContent}
						</td>
					</tr>

				</s:iterator>

				<tr>
					<td height="35" align="center" bgcolor="#FFFFFF">
						合计
					</td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="right" bgcolor="#FFFFFF">
					    <span id="camount">
					    ${camount=='' ? "0" :camount}
					    </span>
					</td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF">
					<td align="center" bgcolor="#FFFFFF">
						<span></span>
					</td>
				</tr>
			</table>
			<table width="750" border="0" cellpadding="0" cellspacing="0"
				style="border-left: 1px solid #000000; border-right: 1px solid #000000">
				<tr>
					<td width="186" height="35" align="center"
						style="border-right: 1px solid #000000;">
						应付款单位或个人所缴金额
					</td>
					<td id="daxie" style="padding-left: 5px;">
						金额（大写）：&nbsp;
						<span id="6"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">万</span>&nbsp;
						<span id="5"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">仟</span>&nbsp;
						<span id="4"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">佰</span>&nbsp;
						<span id="3"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">拾</span>&nbsp;
						<span id="2"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">元</span>&nbsp;
						<span id="1"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">角</span>&nbsp;
						<span id="0"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="color: red">分</span>
					</td>
				</tr>
			</table>
			<table width="750" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<%--<tr>--%>
					<%--<td width="70" height="35" align="center">--%>
				                    <%--供应商--%>
					<%--</td>--%>
					<%--<td width="285"   >--%>
						<%--&nbsp;&nbsp;${contactCompanyView.companyName}--%>
					<%--</td>--%>
					<%--<td width="90" align="center">--%>
						<%--单位电话&nbsp;&nbsp;&nbsp;&nbsp;--%>
					<%--</td>--%>
					<%--<td width="100" style="padding-left: 5px;"  colspan="3">--%>
						<%--${contactCompanyView.companyTel}--%>
					<%--</td>--%>

				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td width="90" align="left" style="padding-left: 10px;height: 35px;">--%>
						<%--&nbsp;单位负责人--%>
					<%--</td>--%>
					<%--<td width="110" style="padding-left: 5px;" >--%>
						<%--${contactCompanyView.cresponsible}--%>
					<%--</td>--%>
					<%--<td  style="padding-left: 5px;height: 35px;">--%>
						<%--&nbsp;负责人电话--%>
					<%--</td>--%>
					<%--<td  style="padding-left: 5px;" colspan="3">--%>
						<%--${contactCompanyView.responsibleTel}--%>
					<%--</td>--%>
				<%--</tr>--%>
				<tr>
					<td height="35" align="center">
						公司地址
					</td>
					<td colspan="2" style="padding-left: 8px;" >
						${contactCompanyView.companyAddr}
					</td>
					<td align="center" width="15%">
						单位往来关系
					</td>
					<td style="padding-left: 5px;">
						${financialBill.ccompanyRelationship}
					</td>
				</tr>
				<tr>
					<td height="35" align="center">
						备注
					</td>
					<td height="15" colspan="6" align="center">
						<input type="text"
							style="width: 530px; height: 15px; background: none; border: 0px; text-align: center" />
					</td>
				</tr>
			</table>
			<table width="770" border="0" cellpadding="0" cellspacing="0" class="fifth" style="margin-top: 18px;">
				<tr style="padding: 0px;line-height: 5px;">
					<td width="74" height="10" align="center" style="padding: 0px 0px 0px 11px;">
						公司经理：
					</td>
					<td width="50" align="center">
						${billcheckmap["gsjl"]}
					</td>
					<td width="84" align="center">
						部门主管：
					</td>
					<td width="40" align="center">
						${billcheckmap["bmzg"]}
					</td>
					<td width="74" align="center">
						人事处：
					</td>
					<td width="50" align="center">
						${billcheckmap["rsc"]}
					</td>
					<td width="74" align="center">
						财务审核：
					</td>
					<td width="50" align="center">
						${billcheckmap["cwsh"]}
					</td>
					<td width="90" align="center">
						收货人确认：
					</td>
					<td width="50" align="center">
						${billcheckmap["skr"]}
					</td>
				</tr>
				<tr style="padding: 0px;line-height: 5px;">
					<td width="95" height="35" align="center" style="padding-left: 10px;">
						总部总经理：
					</td>
					<td>
						${billcheckmap["zjl"]}
					</td>
					<td align="center" width="108">
						总部部门主管：
					</td>
					<td>
						${billcheckmap["zg"]}
					</td>
					<td align="center" width="90">
						总部人事处：
					</td>
					<td>
						${billcheckmap["zbrsc"]}
					</td>
					<td align="center" width="90">
						总财务审核：
					</td>
					<td>
						${billcheckmap["zbcw"]}
					</td>
					<td align="center" width="90">
						交款人确认：
					</td>
					<td>
						${billcheckmap["jkr"]}
					</td>
				</tr>
			</table>
		</div>
		<br />
	</body>
</html>
