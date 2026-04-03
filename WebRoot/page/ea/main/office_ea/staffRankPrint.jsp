
<%@page import="hy.ea.bo.human.publicreceipts.Publicreceipts"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工级别变更打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        
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
		body {
			margin-left: 15px;
		}
		#apDiv1 {
			position:absolute;
			left:507px;
			top:287px;
			width:63px;
			height:32px;
			z-index:1;
		}
		body td{
			font-size:11px;
		
		}
		</style>
		
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

 <script type="text/javascript">
    var  st = "${cashierBillsVO.status}";
    var basePath = "<%=basePath%>";
</script>
 </head>
<body>
<div id="apDiv1"></div>
<div id="tableprint" align="center">
<table width="620" border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr>
    <td  id="xxx" height="25" align="center" style="font-weight:bold;font-size:16px">&nbsp;员工级别变更单</td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
   
   
  </tr>
  <tr>
    <td width="5%" height="25" align="right">公司：</td>
    <td width="25%" align="left">${companyname}</td>
    <td width="13.5%" align="right">部门：</td>
    <td width="10%" align="left">${organizationname}</td>
    <td width="10%" align="center">责任人：${publicreceipts.principal }</td>
    
  </tr>
  <tr>
    <td width="12%" height="25" align="right">制单人：</td>
    <td colspan="2" align="left">${publicreceipts.operator}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单日期：${fn:substring(publicreceipts.applyDate, 0, 10)}</td>
    <td width="11%" align="right" height="10">凭证号：</td>
    <td width="10%" align="center" height="10"><% hy.ea.bo.human.publicreceipts.Publicreceipts data =(hy.ea.bo.human.publicreceipts.Publicreceipts)request.getAttribute("publicreceipts");
											if (data != null) {
												StringBuffer barCode = new StringBuffer();
												barCode.append("<img src='");
												barCode.append(request.getContextPath());
												barCode.append("/CreateBarCode?data=");
												barCode.append(data.getVoucherNum());
												barCode
														.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
												out.println(barCode.toString());
											} else {
												out.println("no data");
											}
									%><br/>
									${publicreceipts.voucherNum}</td>
  </tr>
</table>
<table width="600" height="190" border="0" align="center"
	cellpadding="0" cellspacing="0" class="table"
	style="background: #FFFFFF;">
	<tr>
		<td width="100"  height="30" align="right">
			人员编号：
		</td>
		<td width="100" align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankHumanID }</font>
		</td>
		<td width="100" align="right">
			原级别明细：
		</td>
		<td width="100" align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankOldScale}</font>
		</td>
		<td width="100" align="right">
			升降级别：
		</td>
		<td width="100" align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankNewScale }</font>
		</td>
	</tr>
	<tr>
		<td height="30" align="right">
			原级别起日期：
		</td>
		<td align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankStartdate }</font>
		</td>
		<td align="right">
			原级别止日期：
		</td>
		<td align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankEnddate }</font>
		</td>
		<td align="right">
			生效日期：
		</td>
		<td class="disName" align="left">
			<font style="margin-left:2px;">${publicreceiptsChild.rankEffectivedate }</font>
		</td>
	</tr>
	<tr>
		<td align="right" height="30">
			变动理由：
		</td>
		<td colspan="5" align="left">
      		<font style="margin-left:2px;">${publicreceiptsChild.rankChangeReason }</font>
		</td>
	</tr>
	<tr>
		<td align="right" height="50" width="100">
			工作内容：
		</td>
		<td colspan="5" align="left" width="500">
			&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.rankContent }
		</td>
	</tr>
	<tr>
		<td align="right" height="50" width="100">
			自我评定：<br />
			(业绩、优缺点)
		</td>
		<td colspan="5" align="left" width="500">
			&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.rankExamine }
		</td>
	</tr>
	</table>
	
<table width="620" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="92" height="25" align="right" style="font-size:12px">公司经理：</td>
    <td width="42">&nbsp;</td>
    <td width="90" align="right" style="font-size:12px">部门主管：</td>
    <td width="42">&nbsp;</td>
    <td width="82" align="right" style="font-size:12px">人事处：</td>
    <td width="42">&nbsp;</td>
    <td width="82" align="right" style="font-size:12px">财务审核：</td>
    <td width="42">&nbsp;</td>
    <td width="80" align="right" style="font-size:12px">责任人签字：</td>
    <td width="32">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" align="right" style="font-size:12px">总部总经理：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总部部门主管：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总部人事处：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总财务审核：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px"></td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
<br />
<br />
<br />
<br />
</body>
</html>
