<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>记账凭证打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/"; %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/formatMoney.js" type="text/javascript"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/voucher/printing_print.js"></script>
<style type="text/css">
.table {
	border-collapse:collapse;
	border:1px solid #000000;
	font-size:9px;
	 
}
.table th {
	border:1px solid #000000;
	color:#000000;
}
.table td {
	border:1px solid #000000;
	color:#000000;
}
body,td,th {
	font-size: 9px;
}
div{
	margin-bottom: 122px}
body {
	margin-left: 15px;
}
#apDiv1 {
	position:absolute;
	left:850px;
	top:287px;
	width:63px;
	height:32px;
	z-index:1;
}
</style>
 <script type="text/javascript">
 	 var  st = "${voucher.vouchercategory}";
    var basePath = "<%=basePath%>";
</script>
 </head>
<body>
<center>
<div id="tableprint" align="center" style="height: 600px;width:1000px;" >
<table width="620"  border="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr><td height="30" width="300">&nbsp;<span style="font-size: 25px;position: relative;left:250px">${voucher.VTName}凭证</span></td>
  	  <td style="position: relative;left:270px">附件数</td>
  </tr>
</table>
<table>
<br>
	<tr>
		<td width="270px" style="font-size: 15px;">${voucher.VTJc} 字  第  ${voucher.journalnum} 号</td>
		<td width="270px" style="font-size: 15px;">凭证日期 ${voucher.voucherdate}</td>
		<td width="270px" style="font-size: 15px;">会计期间    ${fp.year} 年 ${fn:substring(voucher.voucherdate,4,6)} 期  </td>
		<td class="u1"> 1/1</td>
	</tr>
</table>
<table class="table tab">
<br><br>
	<tr align="center" style="font-weight: bold;">
		<td width="240px" height="65"><span style=" font-size: 20px;">科目编号</span>
			<input type="hidden" value="${voucher.voucherid}" class="cherid"></td>
		<td width="210px" height="65"><span style=" font-size: 20px;">借方金额</span></td>
		<td width="210px" height="65"><span style=" font-size: 20px;">贷方金额</span></td>
		<td width="300px" height="65"><span style=" font-size: 20px;">摘要</span></td>
	</tr>
</table>
<table class="table" style="position: relative;top:-15px">
	<br>
	<tr style="font-wezght: bold;" >
		<td width="240px" height="65" style="font-size: 20px;">合计</td>
		<td width="210px" height="65" class="money1" align="right" style="font-size: 20px;"></td>
		<td width="210px" height="65" class="money2" align="right" style="font-size: 20px;"></td>
		<td width="300px" height="65"></td>
	<!-- 	<td style="position: relative; left:-150px;font-size:16px;">合计</td>
		<td class="money1"  style="position: relative; left:-50px;font-size:16px;"></td>
		<td style="position: relative; left:30px;font-size:16px;" class="money2">贷方金额合计</td> -->
	</tr>
</table>
<table>
<br><br><br>
	<tr>
		<td width="250px" style="font-size: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;会计</td>
		<td width="220px" style="font-size: 15px;">制单    ${voucher.makepeople}</td>
		<td width="200px" style="font-size: 15px;">记账    ${voucher.tallypeople}</td>
		<td width="200px" style="font-size: 15px;">审核    ${voucher.auditingpeo}</td>
		<td width="220px" style="font-size: 15px;">打印日期  ${vdate}</td>
	</tr>
</table>
</div>
</body>
</html>
