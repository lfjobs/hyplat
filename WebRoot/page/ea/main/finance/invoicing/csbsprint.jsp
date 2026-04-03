<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>预算单据打印</title>
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
	font-size: 12px;
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
    var taxstatusDu="${cashierBills.status}";
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
		<%--<div id="apDiv1"></div>
		--%><div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold">
						&nbsp;${cashierBills.billsType}
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
						${cashierBills.companyName}
					</td>
					<td width="7%" align="left">
						部门：
					</td>
					<td width="13%" align="left">
						${cashierBills.departmentName}
					</td>
					<td width="10%" align="left">
						责任人：
					</td>
					<td width="20%" align="left">
						<c:if test="${cashierBills.staffName!=null}">${cashierBills.staffName}</c:if>
					</td>
					
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="18%" align="left">
						制单人：${cashierBills.inputName}
					</td>
					<td width="30%"  align="center">
						制单日期：${fn:substring(cashierBills.cashierDate, 0, 10)}
					</td>
					
					<td colspan="8" align="right">
						凭证号：<%
						hy.ea.bo.finance.CashierBills data = (hy.ea.bo.finance.CashierBills) request
								.getAttribute("cashierBills");
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
					%>
					</td>
				</tr>
				<tr><td height="5" colspan="10" align="right">${cashierBills.journalNum}</td></tr>
				
				<tr>
					<td align="left" colspan="4">
						项目名称：${cashierBills.projectName}
						
					</td>
					
				</tr>
			</table>
			<table width="620" cellpadding="0"  style="margin-top:5px;"cellspacing="0" class="table">
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
						单位
					</th>
					<th align="center">
						数量
					</th>
					<th align="center">
						预算单价
					</th>
					<th align="center">
						预算总金额
					</th>
					<th align="center">
						往来公司
					</th>
					<th align="center">
						往来个人
					</th>
				</tr>
				<s:iterator value="goodBillslist" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 产品编号 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="goodsNum">${goodsNum}</span>
						</td>
						<!-- 产品名称 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="goodsName">${goodsName}</span>
						</td>
						
						<!-- 单位 -->
						<td align="center" bgcolor="#FFFFFF">
							${goodsVariableID}
						</td>
						<!-- 数量 -->
						<td align="center" bgcolor="#FFFFFF">
							${quantity}
						</td>
						<!-- 单价 -->
						<td align="center" bgcolor="#FFFFFF">
							${price}
						</td>
						<!-- 总金额 -->
						<td align="center" bgcolor="#FFFFFF">
							<span id="money" class="moneysum">${money}</span>
							
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="connectName" >${ccompanyName}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="connectName" >${connectName}</span>
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
						<span id="monsum"></span>
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="border: 1px solid #000000;border-top:none;"
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
			<table width="620" border="0" cellpadding="3" cellspacing="3">
			<tr>
				<td height="30" align="left" colspan="2" style="color: red">填写说明:责任人属于支款责任人，往来单位属于收方单位（必填项），往来个人属于收方责任人</td>
			</tr>
			<tr>
				<td height="30" align="left" colspan="2">备注:${cashierBillsVO.remark}<br/>行政财务管理：<br />
				(1)日清月结、凡超24时、延迟1日、每日折扣相应责任人1分(20元)
				(2)无入库单折扣相应责任人1分(20元)<br />
				(3)无收入日清、每日折扣相应责任人1分(20元)
				(4)无支出日清、每日折扣相应责任人1分(20元)<br />
				(5)无资产日清出入库帐折扣相应责任人1分(20元)
				(6)未尽帐务事谊按相应处理<br />
				(7)项目产品物品名称必须用词准确，用词不准确乱用词语每条扣1分<br />
				(8)项目产品物品名称必须填写所属单位、责任人、电话，少一项扣1分<br />
				(9)申请责任人流程必须按原则审核否则审核责任人按每项2分扣责任分<br />
				(10)以前用语不准，申请单、报销单请予及时纠正，不予纠正应给予严肃处理。<br />
				(11)会计部每月须完成以前三个月的问题单纠正任务。</td>
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
