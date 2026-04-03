<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tiantai.nwa.tbank.bo.TAbcCashday" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>单据现金银行日记账</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
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

 th, TH {
		font-size: 12px;
		border-color: #000000;
		height:18;
}
</style>
</head>
<%
	//List cashdayList  = (List)request.getAttribute("cashdayList");
	//Iterator iter = accountList.iterator();
%>
<body>
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
		<tr>
			<th colspan="34" align="center" style="font-size: 20px">银行日记账</th>
		</tr>
		<tr>
			<th width="3%" align="center">ID号</th>
			<th width="3%" align="center">省市代码</th>
			<th width="3%" align="center">账号</th>
			<th width="3%" align="center">货币码</th>
			<th width="3%" align="center">交易日期</th>
			<th width="3%" align="center">交易时间</th>
			<th width="3%" align="center">日志号</th>
			<th width="3%" align="center">交易类型</th>
			<th width="3%" align="center">交易行号</th>
			<th width="3%" align="center">户名</th>
			<th width="3%" align="center">发生额标志</th>
			<th width="3%" align="center">对方账号省市代码</th>
			<th width="3%" align="center">对方账号</th>
			<th width="3%" align="center">对方账号货币码</th>
			<th width="3%" align="center">对方账号户名</th>
			<th width="3%" align="center">对方账号开户行</th>
			<th width="3%" align="center">现转标志</th>
			<th width="3%" align="center">错账日期</th>
			<th width="3%" align="center">错账传票号</th>
			<th width="3%" align="center">交易金额</th>
			<th width="3%" align="center">账户余额</th>
			<th width="3%" align="center">上笔余额</th>
			<th width="3%" align="center">手续费总额</th>
			<th width="3%" align="center">凭证种类</th>
			<th width="3%" align="center">凭证省市代号</th>
			<th width="3%" align="center">凭证批次号</th>
			<th width="3%" align="center">凭证号</th>
			<th width="3%" align="center">客户参考号</th>
			<th width="3%" align="center">交易码</th>
			<th width="3%" align="center">柜员号</th>
			<th width="3%" align="center">传票号</th>
			<th width="3%" align="center">摘要</th>
			<th width="3%" align="center">附言</th>
			<th width="3%" align="center">交易来源</th>	
		</tr>
		
		<s:iterator value="cashdayList">
			<tr>
				<td>${pid}</td>
				<td>${prov}</td>
				<td>${accno}</td>
				<td>${cur}</td>
				<td>${trdate}</td>
				<td>${timestab}</td>
				<td>${trjrn}</td>
				<td>${trtype}</td>
				<td>${trbankno}</td>
				<td>${accname}</td>
				<td>${amtindex}</td>
				<td>${oppprov}</td>
				<td>${oppaccno}</td>
				<td>${oppcur}</td>
				<td>${oppname}</td>
				<td>${oppbkname}</td>
				<td>${cshindex}</td>
				<td>${errdate}</td>
				<td>${errvchno}</td>
				<td>${amt}</td>
				<td>${bal}</td>
				<td>${preamt}</td>
				<td>${totchg}</td>
				<td>${vouchertype}</td>
				<td>${voucherprov}</td>
				<td>${voucherbat}</td>
				<td>${voucherno}</td>
				<td>${custref}</td>
				<td>${transcode}</td>
				<td>${teller}</td>
				<td>${vchno}</td>
				<td>${abs}</td>
				<td>${postscript}</td>
				<td>${trfrom}</td>				
			</tr>
		</s:iterator>
	</table>
</body>
</html>
