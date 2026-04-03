<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>领用出库单打印预览</title>
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
		<script src="<%=basePath%>/js/ea/finance/invoicing/receivestorage_print.js" type="text/javascript"></script>
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
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold">&nbsp;
					
					    <c:if test="${financialBill.billStatus =='07'}">
						</c:if>
						${financialBill.billsType}
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
				<td></td>
				</tr>
				<tr>
					<td width="10%" height="25" align="left">公司：</td>
					<td width="30%" align="left">${companyname}</td>
					<td width="7%" align="left">部门：</td>
					<td width="23%" align="left">${organizationname}</td>
					<td width="10%" align="left">责任人：</td>
					<td width="20%" align="left">
						<c:if test="${financialBill.staffName!=null}">${financialBill.staffName}</c:if>
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="10%" align="left">
						制单人：
					</td>
					<td width="10%">
					    ${financialBill.billStaffName}
					</td>
					<td width="10%" height="25" align="left">
						制单日期：
					</td>
					<td width="13%" height="25" align="left">
						${fn:substring(financialBill.billsDate, 0, 10)}
					</td>
					<td width="57%" align="left">
						凭证号：<%
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
						&nbsp; &nbsp;  ${financialBill.journalNum}
					</td>
				</tr>
			</table>
			<table width="620" cellpadding="0" cellspacing="0" class="table">
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						物品名称	
					</th>
					<th align="center">
						规格
					</th>
					<th align="center">
						型号
					</th>
					<th align="center">
						批次
					</th>
					<th align="center">
						库房
					</th>
					<th align="center">
						入库位置
					</th>
					<th align="center">
						单价
					</th>
					<th align="center">
						数量
					</th>
					<th align="center">
						单位
					</th>
					<th align="center">
						金额
					</th>
					<th align="center">
						科目
					</th>
					<th align="center">
						物流方式
					</th>
					<th align="center">
						备注
					</th>
					</tr>
				<s:iterator value="warehouseList" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 品名编号 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="goodsNum">${goodsName}</span>
						</td>
						<!-- 统一分类条码 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="sortCode">${typeID}</span>
						</td>
						<!-- 费用或品名名称 -->
						<td align="center" bgcolor="#FFFFFF">
							${goodsNum}
						</td>
						<!-- 类别 -->
						<td align="center" bgcolor="#FFFFFF">
							${sortCode}
						</td>
						<!-- 品牌规格-->
						<td align="center" bgcolor="#FFFFFF">
							${depotName==null?"无":depotName}
						</td>
						<!-- 数量-->
						<td align="center" bgcolor="#FFFFFF">
							${financialBill.drdepotName}
						</td>
						<!-- 单价-->
						<td align="center" bgcolor="#FFFFFF">
							${price}
						</td>
						<!-- 金额-->
						<td align="center" bgcolor="#FFFFFF">
							<span id="quantity">${quantity}</span>
						</td>
						<!-- 物流方式-->
						<td align="center" bgcolor="#FFFFFF">
							${goodsVariableID}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span class="moneysum">${money}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${subjectsName}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${financialBill.logisticsType}
						</td>
						<!-- 备注-->
						<td align="center" bgcolor="#FFFFFF">
							${remark}
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td height="15" align="center" bgcolor="#FFFFFF">
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
					<td align="center" bgcolor="#FFFFFF">
					    <span >
					    ${camount=='' ? "0" :camount}
					    </span>
					</td>
					<td align="center" bgcolor="#FFFFFF"></td>
					<td align="center" bgcolor="#FFFFFF">
						<span></span>
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="border-left: 1px solid #000000; border-right: 1px solid #000000">
				<tr>
					<td width="90" height="15" align="center"
						style="border-right: 1px solid #000000;">
						金额合计
					</td>
					<td id="daxie">&nbsp;大写：&nbsp;&nbsp;<span id="6"></span>&nbsp;&nbsp;<span style="color:red">万</span>&nbsp;&nbsp;<span id="5"></span>&nbsp;&nbsp;<span style="color:red">仟</span>&nbsp;&nbsp;<span id="4"></span>&nbsp;&nbsp;<span style="color:red">佰</span>&nbsp;&nbsp;<span id="3"></span>&nbsp;&nbsp;<span style="color:red">拾</span>&nbsp;&nbsp;<span id="2"></span>&nbsp;&nbsp;<span style="color:red">元</span>&nbsp;&nbsp;<span id="1"></span>&nbsp;&nbsp;<span style="color:red">角</span>&nbsp;&nbsp;<span id="0"></span>&nbsp;&nbsp;<span style="color:red">分</span></td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr>
					<td width="70" height="15" align="center">
					         调出机构
					</td>
					<td width="300">
						${financialBill.ccompanyName}
					</td>
					<td width="50" align="center">
						电话
					</td>
					<td width="200">
						${financialBill.ccompanyTel}
					</td>
				</tr>
				<tr>
					<td width="70" height="15" align="center">
					         调入机构
					</td>
					<td width="300">
						${financialBill.drccompanyName}
					</td>
					<td width="50" align="center">
						电话
					</td>
					<td width="200">
						${financialBill.drccompanyTel}
					</td>
				</tr>
				<tr>
					<td width="70" height="15" align="center">
					     调出仓库   
					</td>
					<td width="300">
						${financialBill.depotName}
					</td>
					<td width="50" align="center">
					    调入仓库
					</td>
					<td width="200">
						${financialBill.drdepotName}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						备注
					</td>
					<td height="15" colspan="4" align="center">
						<input type="text"
							style="width: 530px; height: 15px; background: none; border: 0px; text-align: center"  value="${BillCheckList[remark]}"/>
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="74" height="25" align="right">
						公司经理：
					</td>
					<td width="50" align="center">
						${billcheckmap["gsjl"]}
					</td>
					<td width="84" align="right">
						部门主管：
					</td>
					<td width="40" align="center">
						${billcheckmap["bmzg"]}
					</td>
					<td width="74" align="right">
						人事处：
					</td>
					<td width="50" align="center">
						${billcheckmap["rsc"]}
					</td>
					<td width="74" align="right">
						财务审核：
					</td>
					<td width="50" align="center">
						${billcheckmap["cwsh"]}
					</td>
					<td width="74" align="center">
						收款人确认：
					</td>
					<td width="50" align="center">
						${billcheckmap["skr"]}
					</td>
				</tr>
				<tr>
					<td height="25" align="right">
						总部总经理：
					</td>
					<td>
						${billcheckmap["zjl"]}
					</td>
					<td align="right">
						总部部门主管：
					</td>
					<td>
						${billcheckmap["zg"]}
					</td>
					<td align="right">
						总部人事处：
					</td>
					<td>
						${billcheckmap["zbrsc"]}
					</td>
					<td align="right">
						总财务审核：
					</td>
					<td>
						${billcheckmap["zbcw"]}
					</td>
					<td align="center">
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
