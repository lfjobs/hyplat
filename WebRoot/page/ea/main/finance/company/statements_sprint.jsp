<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>归档单据基本信息</title>
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
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/company/cashierbillsprint.js"></script>
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
</style>
 <script type="text/javascript">
    var  st = "${obj[19]}";
    var basePath = "<%=basePath%>";
</script>
 </head>
<body>
<div id="apDiv1"></div>
<div id="tableprint" align="center">
<table width="620"  border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr>
    <td height="25" align="center" style="font-weight:bold; ">&nbsp;<span style="font-size: 15px">${hcashierBillsVO.billsType}</span></td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
   
   
  </tr>
  <tr>
    <td width="3%" height="25" align="right">公司：</td>
    <td width="25%">${hcashierBillsVO.companyname}</td>
    <td width="5%" align="right">部门：</td>
    <td width="12%">${hcashierBillsVO.departmentname}</td>
    <td width="8%" align="right">责任人：</td>
    <td width="20%"><c:if test="${hcashierBillsVO.staffname!=null}">${hcashierBillsVO.staffname}---${hcashierBillsVO.recordcode}</c:if></td>
    <td  width="15%" colspan="2" align="right">制单日期：${fn:substring(hcashierBillsVO.cashierDate, 0, 10)}</td>
  </tr>
  <tr>
    <td width="10%" height="25" align="right">银行账户：</td>
    <td colspan="1">${hcashierBillsVO.companyBankNum}</td>
     <td  colspan="8" align="center">凭证号：<%
										hy.ea.bo.history.vo.HistoryCashierBillVO data =(hy.ea.bo.history.vo.HistoryCashierBillVO)request.getAttribute("hcashierBillsVO");
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
									${hcashierBillsVO.journalNum}</td>
  </tr>
</table>
<table width="620" cellpadding="0" cellspacing="0" class="table">
  <tr>
    <th height="15" width="50" align="center">款源日期</th>
    <th width="85" align="center">入账日期</th>
    <th width="50" align="center"  bgcolor="#E4F1FA">有效日期</th>
    <th width="50" align="center"  bgcolor="#E4F1FA">批号/期号</th>
    <th align="center">品名编号</th>
    <th align="center">费用或品名名称</th>
    <th align="center">类型</th>
    <th align="center">等级</th>
    <th align="center">型号</th>
    <th align="center">品牌规格</th>
    <th align="center">单位</th>
    <th align="center">数量</th>
    <th align="center">重量</th>
    <th align="center">单价</th>
    <th align="center">金额</th>
    <th align="center">费用类别</th>
    <th align="center">科目</th> 
    <th align="center">出入库</th>   
    <th align="center" width="60" bgcolor="#E4F1FA">摘要</th>
  </tr>
 <s:iterator value="pageForm.list">
  <tr>
    <td height="15" align="center" bgcolor="#FFFFFF"> ${fn:substring(startDate, 0, 10)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(endDate, 0, 19)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${fn:substring(periodDate, 0, 10)}</td>
    <td align="center" bgcolor="#FFFFFF"> ${batchNumber}</td>
    <td align="center" bgcolor="#FFFFFF">${goodsCoding}</td>
    <td align="center" bgcolor="#FFFFFF">${goodsName}</td>
    <td align="center" bgcolor="#FFFFFF">${typeID}</td>
    <td align="center" bgcolor="#FFFFFF">${goodsCoding}</td>
    <td align="center" bgcolor="#FFFFFF">${model}</td>
    <td align="center" bgcolor="#FFFFFF">${standard}</td>
    <td align="center" bgcolor="#FFFFFF">${variableID}</td>
    <td align="center" bgcolor="#FFFFFF">${quantity}</td>
    <td align="center" bgcolor="#FFFFFF">${weight}</td>
    <td align="center" bgcolor="#FFFFFF">${price}</td>
    <td align="center" bgcolor="#FFFFFF"><span id="money" >${money}</span></td>
    <td align="center" bgcolor="#FFFFFF">${costType}</td>
    <td align="center" bgcolor="#FFFFFF">${subjectsName}<span id="balance" style="display:none" class="moneysum">${money}</span></td>
    <td align="center" bgcolor="#FFFFFF"> ${depotType}</td>
    <td align="center" bgcolor="#FFFFFF"> ${reasonThing}</td>
    
  </tr>
  </s:iterator>
   <tr>
    <td height="15" align="center" bgcolor="#FFFFFF">合计</td>
    <td align="center" bgcolor="#FFFFFF"></td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF"><span id="monsum"></span></td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0"  style="border-left:1px solid #000000; border-right:1px solid #000000">
  <tr>
    <td width="186" height="15" align="center" style="border-right:1px solid #000000;">应付款单位或个人所缴金额</td>
    <td id="daxie">金额（大写）：&nbsp;&nbsp;<span id="6"></span>&nbsp;&nbsp;<span style="color:red">万</span>&nbsp;&nbsp;<span id="5"></span>&nbsp;&nbsp;<span style="color:red">仟</span>&nbsp;&nbsp;<span id="4"></span>&nbsp;&nbsp;<span style="color:red">佰</span>&nbsp;&nbsp;<span id="3"></span>&nbsp;&nbsp;<span style="color:red">拾</span>&nbsp;&nbsp;<span id="2"></span>&nbsp;&nbsp;<span style="color:red">元</span>&nbsp;&nbsp;<span id="1"></span>&nbsp;&nbsp;<span style="color:red">角</span>&nbsp;&nbsp;<span id="0"></span>&nbsp;&nbsp;<span style="color:red">分</span></td>
  </tr>
</table>
<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
  <tr>
    <td width="70" height="15" align="center">往来单位</td>
    <td width="190">${hcashierBillsVO.ccompanyname}</td>
    <td width="60" align="center">单位电话</td>
    <td width="90">${hcashierBillsVO.companyTel}</td>
    <td width="83" align="center">单位负责人</td>
    <td width="120">${hcashierBillsVO.cresponsible}</td>
  </tr>
  <tr>
    <td height="15" align="center">公司地址</td>
    <td colspan="3">${hcashierBillsVO.companyAddr}</td>
    <td align="center">单位往来关系</td>
    <td>${hcashierBillsVO.contactConnections}</td>
  </tr>
  <tr>
    <td height="15" align="center">往来个人</td>
    <td>${hcashierBillsVO.contactUserName}</td>
    <td  align="center">电话</td>
    <td>${hcashierBillsVO.tel}</td>
    <td align="center">个人身份证号</td>
    <td>${hcashierBillsVO.staffIdentityCard}</td>
  </tr>
  <tr>
    <td height="15" align="center">QQ</td>
    <td>${hcashierBillsVO.userQq}</td>
    <td align="center">邮箱</td>
    <td>${hcashierBillsVO.email}</td>
    <td  align="center">个人往来关系</td>
    <td>${hcashierBillsVO.phone}</td>
  </tr>
  <tr>
    <td height="15" align="center">地址</td>
    <td colspan="5">${hcashierBillsVO.userAddr}</td>
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