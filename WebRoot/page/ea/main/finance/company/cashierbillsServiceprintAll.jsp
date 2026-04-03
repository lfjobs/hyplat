<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出纳单据基本信息</title>
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
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
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

.weizhi {
	position: absolute;
	z-index: 1;
	margin-top:-32px;
	margin-left:180px;
}

#topboder td {
	border-top-style:hidden; 
}

</style>
 <script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
 </head>
<body>
<s:iterator value="serviceBillsPrintVOList">
<div id="tableprint" align="center">
 <table width="700" height="539" border="0" cellpadding="0" cellspacing="0">
 <tr>
 <td>
<div align=center>
<table width="620"  border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr>
    <td height="25" align="center" style="font-weight:bold">&nbsp;${billsType}</td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
   
   
  </tr>
  <tr>
    <td width="5%" height="25" align="right">公司：</td>
    <td width="25%" align="left">${companyname}</td>
    <td width="5%" align="right">部门：</td>
    <td width="10%" align="left">${departmentname}</td>
    <td width="5%" align="right">责任人：</td>
    <td width="10%" align="left"><c:if test="${staffname!=null}">${staffname}---${recordcode}</c:if></td>
    <td  width="20%" colspan="2" align="right">制单日期：${fn:substring(cashierDate, 0, 10)}</td>
  </tr>
  <tr>
    <td width="12%" height="25" align="right">银行账户：</td>
    <td colspan="1" align="left">${companyBankNum}</td>
     <td  colspan="8" align="center">凭证号：<%
										hy.ea.bo.finance.vo.CashierBillsVO data =(hy.ea.bo.finance.vo.CashierBillsVO)request.getAttribute("cashierBillsVO");
											if (data != null) {
												StringBuffer barCode = new StringBuffer();
												barCode.append("<img src='");
												barCode.append(request.getContextPath());
												barCode.append("/CreateBarCode?data=");
												barCode.append(data.getJournalNum());
												barCode.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
												out.println(barCode.toString());
											} else {
												out.println("no data");
											}
									%><br/>
									${journalNum}</td>
  </tr>
</table>
<table width="620" cellpadding="0" cellspacing="0" class="table">
  <tr>
    <th height="15" width="70" align="center" bgcolor="#E4F1FA">录入时间</th>
    <th width="70" align="center" bgcolor="#E4F1FA">咨询起日期</th>
    <th width="80" align="center" bgcolor="#E4F1FA">咨询止日期</th>
    <th width="50" align="center" bgcolor="#E4F1FA">工作地点</th>
    <th width="50" align="center" bgcolor="#E4F1FA">服务方式</th>
    <th width="100" align="center" bgcolor="#E4F1FA">咨询跟踪内容</th>

  </tr>
 <s:iterator value="goodsList" >
  <tr>
    <td height="15" align="center" bgcolor="#FFFFFF"> ${fn:substring(entryTime, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(startDate, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(endDate, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${workSite}</td>
    <td align="center" bgcolor="#FFFFFF"> ${serviceWay}</td>
    <td align="center" bgcolor="#FFFFFF"> ${serviceContent}</td>
  </tr>
  </s:iterator>
</table>
	<!-- 显示审核公章 -->
	<s:if test="status !=null>
	<s:if test="status =='10'"><img class="weizhi" src="<%=basePath%>images/ea/finance/zuofei.png" /></s:if>
	<s:elseif test="status =='06'"><img class="weizhi" src="<%=basePath%>images/ea/finance/yishen.png" /></s:elseif>
	<s:else><img class="weizhi" src="<%=basePath%>images/ea/finance/daishen.png" /></s:else>
	</s:if>
<table width="620" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="80" height="25" align="right">公司经理：</td>
    <td>&nbsp;</td>
    <td width="90" align="right">部门主管：</td>
    <td>&nbsp;</td>
    <td width="80" align="right">人事处：</td>
    <td>&nbsp;</td>
    <td width="80" align="right">财务审核：</td>
    <td>&nbsp;</td>
    <td width="80" align="center">收款人签字：</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="80" height="25" align="right">总部总经理：</td>
    <td>&nbsp;</td>
    <td width="90" align="right">总部部门主管：</td>
    <td>&nbsp;</td>
    <td width="80" align="right">总部人事处：</td>
    <td>&nbsp;</td>
    <td width="80" align="right">总财务审核：</td>
    <td>&nbsp;</td>
    <td width="80" align="center">交款人签字：</td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
</td>
</tr>
</table>
</div>
</s:iterator>
</body>
</html>