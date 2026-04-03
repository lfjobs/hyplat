<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事档案单据查看</title>
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
		var pbasePath="<%=basePath%>";
		$(function() {
		$(".JQueryClose2").click(function() {
				re_load();
			 });
	     function re_load() {
		  document.location.href = pbasePath
				+ "/ea/personalarchive/ea_getPArchiveSheetList.jspa";
				}
	
	});
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<form name="PArchiveForm" id="PArchiveForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						人事档案单据
					</div>
				</div>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="30" align="right">
							凭证号：
						</td>
						<td>
							<span>${paBill.journalNum}</span>
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							<span id="billsType">异动单</span>
						</td>
						<td align="right">
							责任人：
						</td>
						<td>
							<span>${paBill.staffName}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames">${paBill.companyName}</span>
						</td>
						<td align="right">
							部门：
						</td>
						<td align="left" id="dept">
							<span>${paBill.organizationName}</span>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td align="left" colspan=2>
							<span>${paBill.companyBankNum}</span>
						</td>
					</tr>
				</table>
				<table width="99%" height="150px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 150px; overflow: scroll;">

								<table width="1300" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<thead>
										<tr>
											<th align="center" bgcolor="#E4F1FA">
												序号
											</th>
											<th align="center" bgcolor="#E4F1FA">
												人员编号
											</th>
											<th align="center" bgcolor="#E4F1FA">
												档案编号
											</th>
											<th align="center" bgcolor="#E4F1FA">
												人员姓名
											</th>
											<th align="center" bgcolor="#E4F1FA">
												曾用名
											</th>
											<th align="center" bgcolor="#E4F1FA">
												性别
											</th>
											<th align="center" bgcolor="#E4F1FA">
												出生日期
											</th>
											<th align="center" bgcolor="#E4F1FA">
												国籍
											</th>
											<th align="center" bgcolor="#E4F1FA">
												籍贯
											</th>
											<th align="center" bgcolor="#E4F1FA">
												民族
											</th>
											<th align="center" bgcolor="#E4F1FA">
												身份证
											</th>
											<th align="center" bgcolor="#E4F1FA">
												价格
											</th>
										</tr>
									</thead>
									<tbody>
										<%
											int number = 1;
										%>
										<s:iterator value="pageForm.list">

											<tr>
												<td height="30" align="center" bgcolor="#FFFFFF">
													<span id="numbers"><%=number%></span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="staffCode">${staffCode}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="recordCode">${recordCode}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="staffName">${staffName}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="usedNmae">${usedNmae}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="sex">${sex}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="birthday">${birthday}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="nationality">${nationality}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="nativePlace">${nativePlace}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="nation">${nation}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="staffIdentityCard">${staffIdentityCard}</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
													<span id="price">${price}</span>
												</td>
											</tr>
											<%
												number++;
											%>
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
							<span id="ccompanyname" class="qk">${cashierBillsVO1.ccompanyname}</span>
							<input type="hidden" id="ccompanyID" name="paBill.ccompanyID"
								value="${paBill.ccompanyID}" />
							<input type="hidden" id="ccompanyname" name="paBill.ccompanyName"
								value="${cashierBillsVO1.ccompanyname}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${cashierBillsVO1.companyTel}</span>
							<input type="hidden" id="companyTel" name="paBill.ccompanyTel" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${cashierBillsVO1.cresponsible}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${cashierBillsVO1.accountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${cashierBillsVO1.responsibleTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${cashierBillsVO1.companyAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${cashierBillsVO1.industryType}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${cashierBillsVO1.contactConnections}</span>
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
							<span id="contactUserName" class="qk">${cashierBillsVO2.contactUserName}</span>
							<input type="hidden" id="contactUserID" name="paBill.cstaffID"
								value="${paBill.cstaffID}" />
							<input type="hidden" id="contactUserName"
								name="paBill.cstaffName"
								value="${cashierBillsVO2.contactUserName}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${cashierBillsVO2.tel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${cashierBillsVO2.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${cashierBillsVO2.userAccountNum}</span>
							<select style="display: none" id="userNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${cashierBillsVO2.userQq}</span>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${cashierBillsVO2.email}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${cashierBillsVO2.userAddr}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${cashierBillsVO2.phone}</span>

						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="button" class="btn001 JQueryClose2" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>

	</body>
</html>
