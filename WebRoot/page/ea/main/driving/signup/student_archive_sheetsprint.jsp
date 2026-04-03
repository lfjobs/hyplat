<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学员档案单据基本信息</title>
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
			src="<%=basePath%>js/ea/finance/company/cashierbillsprint.js"></script>
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
    var  st = "${cashierBillsVO.status}";
    var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold;">
						&nbsp;
						<span style="font-size: 15px">${cashierBillsVO.billsType}</span>
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>


				</tr>
				<tr>
					<td width="3%" height="25" align="right">
						公司：
					</td>
					<td width="25%">
						${paBill.companyName}
					</td>
					<td width="5%" align="right">
						部门：
					</td>
					<td width="12%">
						${paBill.organizationName}
					</td>
					<td width="8%" align="right">
						责任人：
					</td>
					<td width="20%" align="left">
						${paBill.billStaffName}
					</td>
					<td width="15%" colspan="2" align="right">
						制单日期：${paBill.billsDate}
					</td>
				</tr>
				<tr>
					<td width="10%" height="25" align="right">
						银行账户：
					</td>
					<td colspan="1">
						${paBill.companyBankNum}
					</td>
					<td colspan="8" align="center">
						凭证号：<%
						hy.ea.bo.invoicing.StudentArchiveBill data = (hy.ea.bo.invoicing.StudentArchiveBill) request
								.getAttribute("paBill");
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
						${paBill.journalNum}
					</td>
				</tr>
			</table>
			<table width="620" cellpadding="0" cellspacing="0" class="table">
				<tr>
					<th height="15" width="50" align="center">
						人员编号
					</th>
					<th width="85" align="center">
						档案编号
					</th>
					<th width="50" align="center" bgcolor="#E4F1FA">
						人员姓名
					</th>
					<th width="50" align="center" bgcolor="#E4F1FA">
						曾用名
					</th>
					<th align="center">
						性别
					</th>
					<th align="center">
						出生日期
					</th>
					<th align="center">
						国籍
					</th>
					<th align="center">
						籍贯
					</th>
					<th align="center">
						民族
					</th>
					<th align="center">
						身份证
					</th>
				</tr>
				<s:iterator value="pageForm.list">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							${staffCode}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${recordCode}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${staffName}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${usedNmae}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${sex}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${birthday}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${nationality}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${nativePlace}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${nation}
						</td>
						<td align="center" bgcolor="#FFFFFF">
							${staffIdentityCard}
						</td>

					</tr>
				</s:iterator>
				<tr>
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
					<td align="center" bgcolor="#FFFFFF">
						&nbsp;
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
						${cashierBillsVO1.ccompanyname}
					</td>
					<td width="60" align="center">
						单位电话
					</td>
					<td width="90">
						${cashierBillsVO1.companyTel}
					</td>
					<td width="83" align="center">
						单位负责人
					</td>
					<td width="120">
						${cashierBillsVO1.cresponsible}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						公司地址
					</td>
					<td colspan="3">
						${cashierBillsVO1.companyAddr}
					</td>
					<td align="center">
						单位往来关系
					</td>
					<td>
						${cashierBillsVO1.contactConnections}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						往来个人
					</td>
					<td>
						${cashierBillsVO2.contactUserName}
					</td>
					<td align="center">
						电话
					</td>
					<td>
						${cashierBillsVO2.tel}
					</td>
					<td align="center">
						个人身份证号
					</td>
					<td>
						${cashierBillsVO2.staffIdentityCard}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						QQ
					</td>
					<td>
						${cashierBillsVO2.userQq}
					</td>
					<td align="center">
						邮箱
					</td>
					<td>
						${cashierBillsVO.email}
					</td>
					<td align="center">
						个人往来关系
					</td>
					<td>
						${cashierBillsVO2.phone}
					</td>
				</tr>
				<tr>
					<td height="15" align="center">
						地址
					</td>
					<td colspan="5">
						${cashierBillsVO2.userAddr}
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
