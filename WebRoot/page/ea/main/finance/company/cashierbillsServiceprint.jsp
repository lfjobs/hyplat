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
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/company/cashierbillsServiceprint.js"></script>
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

#topboder td {
	border-top-style:hidden; 
}

</style>
 <script type="text/javascript">
    var  st = "${cashierBillsVO.consultStatus}";
    var basePath = "<%=basePath%>";
</script>
 </head>
<body>
<div id="apDiv1"></div>
<div id="tableprint" align="center">
<table width="620"  border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr>
    <td height="25" align="center" style="font-weight:bold">&nbsp;${cashierBillsVO.billsType}</td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
   
   
  </tr>
  <tr>
    <td width="5%" height="25" align="right">公司：</td>
    <td width="25%" align="left">${cashierBillsVO.companyname}</td>
    <td width="5%" align="right">部门：</td>
    <td width="10%" align="left">${cashierBillsVO.departmentname}</td>
    <td width="5%" align="right">责任人：</td>
    <td width="10%" align="left"><c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}---${cashierBillsVO.recordcode}</c:if></td>
    <td  width="20%" colspan="2" align="right">制单日期：${fn:substring(cashierBillsVO.cashierDate, 0, 10)}</td>
  </tr>
  <tr>
    <td width="12%" height="25" align="right">银行账户：</td>
    <td colspan="1" align="left">${cashierBillsVO.companyBankNum}</td>
     <td  colspan="8" align="center">凭证号：<%
										hy.ea.bo.finance.vo.CashierBillsVO data =(hy.ea.bo.finance.vo.CashierBillsVO)request.getAttribute("cashierBillsVO");
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
									%><br/>
									${cashierBillsVO.journalNum}</td>
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
 <s:iterator value="pageForm.list">
  <tr>
    <td height="15" align="center" bgcolor="#FFFFFF"> ${fn:substring(entryTime, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(startDate, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(endDate, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${workSite}</td>
    <td align="center" bgcolor="#FFFFFF">${serviceWay}</td>
    <td align="center" bgcolor="#FFFFFF">${serviceContent}</td>
  </tr>
  </s:iterator>
</table>
<table id="rightboder" width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
  <tr id="topboder">
    <td width="70" height="15" align="center" >往来单位</td>
    <td width="190" >${cashierBillsVO.ccompanyname}</td>
    <td width="60" align="center" >单位电话</td>
    <td width="90" >${cashierBillsVO.companyTel}</td>
    <td width="83" align="center" >单位负责人</td>
    <td width="120" >${cashierBillsVO.cresponsible}</td>
  </tr>
  <tr>
    <td height="15" align="center">公司地址</td>
    <td colspan="3">${cashierBillsVO.companyAddr}</td>
    <td align="center">单位往来关系</td>
    <td>${cashierBillsVO.contactConnections}</td>
  </tr>
  <tr>
    <td height="15" align="center">往来个人</td>
    <td>${cashierBillsVO.contactUserName}</td>
    <td  align="center">电话</td>
    <td>${cashierBillsVO.tel}</td>
    <td align="center">个人身份证号</td>
    <td>${cashierBillsVO.staffIdentityCard}</td>
  </tr>
  <tr>
    <td height="15" align="center">QQ</td>
    <td>${cashierBillsVO.userQq}</td>
    <td align="center">邮箱</td>
    <td>${cashierBillsVO.email}</td>
    <td  align="center">个人往来关系</td>
    <td>${cashierBillsVO.phone}</td>
  </tr>
  <tr>
    <td height="15" align="center">地址</td>
    <td colspan="5">${cashierBillsVO.userAddr}</td>
  </tr>
  <tr>
    <td height="15" align="center">备注</td>
    <td height="15" colspan="6" align="center">
    <input  type="text" 
		style="width: 530px; height: 15px; background: none; border: 0px; text-align: center" />
    </td>
  </tr>
</table>
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
<br />
<br />
<br />
<br />
</body>
</html>
