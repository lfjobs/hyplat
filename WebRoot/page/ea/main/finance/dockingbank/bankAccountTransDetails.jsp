<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tiantai.nwa.tbank.bo.BankAccount"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>银行账户交易明细</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"	type="text/css" />
		<link href="<%=basePath%>css/ea/main.css" rel="stylesheet"	type="text/css" />
		<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/overlayer.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>js/jqModal/css/jqModal_blue.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript"	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"	src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/finance/bankAccountTransDetails.js"></script>

		<script type="text/javascript">
			var basePath = "<%=basePath%>";		 
		</script>
	</head>
	<%
		List accountList = (List) request.getAttribute("accountList");
		Iterator iter = accountList.iterator();
	%>
	<body style="background-image: url(<%=basePath%>/images/bg01.jpg)">
		<form name="SearchForm" method="post" id="SearchForm">
			<input type="submit" name="submit" style="display: none" />			
			<input type="hidden" name="innerTransCode" id="innerTransCode" value="0002" />
			<div class="main">
				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="1" bgcolor="#D1DDE9" style="margin-top: 8px">
					<tr bgcolor="#E7E7E7">
						<td height="24" width="20%" align="left" bgcolor="#d8e6f4">
							&nbsp;
							<span class="txt">银行交易明细</span>&nbsp;
						</td>
						<td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;							
						</td>
						<td width="20%" height="24" align="right" bgcolor="#d8e6f4">
						</td>
					</tr>
				</table>
				<table id="cataffSearchTable" border="0" width="100%"
					cellspacing="10" style="border: #D1DDE9 1px solid;">

					<tr>
						<td align="right" class="txt02">
							选择银行账号：
						</td>
						<td colspan="2">							
							<select name="accountlist" id="accountlist">
								<%
									while (iter.hasNext()) {
										BankAccount bankAccount = (BankAccount) iter.next();
								%>
								<option value="<%=bankAccount.getPkey()%>"><%=bankAccount.getAccount()%>|<%=bankAccount.getBanksx()%>|<%=bankAccount.getProvcode()%>|<%=bankAccount.getCurrency()%></option>
								<%
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							开始时间：
						</td>
						<td colspan="2">
							<input type="text" name="startDate" id="startDate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly size="25" />
						</td>
					</tr>
					<tr>
						<td align="right" style="width: 30%;">
							截止时间：
						</td>
						<td style="width: 18%;">
							<input type="text" name="endDate" id="endDate"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly size="25" />
						</td>
						<td>
							<input type="button" class="input-button" name="tosearch" id="tosearch" value=" 查询 " />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
				<table id="resultTable" border="0" width="100%" cellspacing="10" style="border: #D1DDE9 1px solid;">
					<tr>
						<td>
							<div style="position: absolute; width: 100%; height: 420px; overflow: scroll;">
								<table name="detailTable" id="detailTable" class="table" cellspacing="0" width="2240" >
									<thead>
										<tr class="tablewith">											
											<th width="10px" align="center">序号</th>
											<th width="25px" align="center">省市代码</th>
											<th width="30px" align="center">账号</th>
											<th width="20px" align="center">货币码</th>
											<th width="40px" align="center">交易日期</th>
											<th width="30px" align="center">交易时间</th>
											<th width="50px" align="center">日志号</th>
											<th width="25px" align="center">交易类型</th>
											<th width="30px" align="center">交易行号</th>
											<th width="30px" align="center">户名</th>
											<th width="20px" align="center">发生额标志</th>
											<th width="50px" align="center">对方账号省市代码</th>
											<th width="30px" align="center">对方账号</th>
											<th width="50px" align="center">对方账号货币码</th>
											<th width="50px" align="center">对方账号户名</th>
											<th width="50px" align="center">对方账号开户行</th>
											<th width="20px" align="center">现转标志</th>
											<th width="20px" align="center">错账日期</th>
											<th width="30px" align="center">错账传票号</th>
											<th width="20px" align="center">交易金额</th>
											<th width="20px" align="center">账户余额</th>
											<th width="20px" align="center">上笔余额</th>
											<th width="30px" align="center">手续费总额</th>
											<th width="20px" align="center">凭证种类</th>
											<th width="40px" align="center">凭证省市代号</th>
											<th width="30px" align="center">凭证批次号</th>
											<th width="20px" align="center">凭证号</th>
											<th width="30px" align="center">客户参考号</th>											
											<th width="20px" align="center">交易码</th>
											<th width="20px" align="center">柜员号</th>
											<th width="20px" align="center">传票号</th>
											<th width="20px" align="center">摘要</th>
											<th width="20px" align="center">附言</th>
											<th width="20px" align="center">交易来源</th>
										</tr>
									</thead>
									<tbody> 
										
									</tbody>
								</table>																							
							</div>							
						</td>
					</tr>
				</table>
			</div>
		</form>

		
	</body>
</html>