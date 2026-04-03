<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>出纳单据基本信息z</title>
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css"/>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
			 <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/production/cashierbillssummary_edit.js"></script>
   <style type="text/css"> 
	.hide{ 
	 border-left: 0px; 
	 border-right: 0px; 
	 border-top: 0px; 
	 border-bottom: 0px; 
	 background: transparent; 
	} 
	.classhide{
	display: none;
	}
	#table input{
	width: 180px;
	}
	#goodstable input{
	width: 180px;
	}
	#contactcompany input{
	width: 180px;
	}
	#apDiv1 {
	position: absolute;
	left: 750px;
	top: 387px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
	</style> 
    </head>
  <script type="text/javascript">
    var basePath = "<%=basePath%>";
     var pNumber=${pageNumber};
</script>

<body style="overflow-y: auto;">
<%-- 用来显示审核章 --%>
		<div id="apDiv1"></div>
<form name="cashierTallyForm" id="cashierTallyForm" method="post" enctype="multipart/form-data"> 
         <input type="submit" name="submit" style="display:none"/>
         <input type="text" name="cashierstatus" id="cashierstatus" value="${cashierBillsVO.status }" style="display: none" />
<div class="content1" style="width:100%;">
  <div class="contentbannb">
  	<div class="divtx">出纳单据基本信息</div>
  </div>
  <table width="99%" border="0" id="table3" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
  <tr>
      <td height="25" align="right">粘贴单编号：</td>
      <td>
       ${cashierBillsVO.journalNum}</td>
      <td align="right">单据类别：</td>
      <td>
     ${cashierBillsVO.billsType}</td>
       <td align="right"><font color="#000000">制单日期：</font></td>
      <td>${fn:substring(cashierBillsVO.cashierDate, 0, 10)}</td>
    </tr>
  
    <tr>
       <td height="25" width="7%" align="right"><span class="STYLE1">公司：</span></td>
       <td >${cashierBillsVO.companyname}</td>
       <td align="right">部门：</td>
       <td align="left"  id="dept">
	       ${cashierBillsVO.departmentname}
	       </td>
      <td align="right"><div id="u1170_rtf">责任人：</div></td>
       <td width="15%" align="left" id="staff">
      <c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}---${cashierBillsVO.recordcode}</c:if>
	       </td>
    </tr>
    <tr>
       <td height="25" width="7%" align="right"><span class="STYLE1">银行账号：</span></td>
       <td >${cashierBillsVO.companyBankNum}</td>
        <td align="right">票据支票管理:</td>
       <td align="left" >${cashierBillsVO.billCheck}</td>
      <td align="right"></td>
       <td width="15%" align="left" ></td>
    </tr>
  </table>
<table width="99%" height="160px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td valign="top">
<div id= "Layer1" style= "position:absolute; width:100%; height:160px; overflow: scroll;">
<table width="3000" align="center" cellpadding="0" cellspacing="0" class="table" id="goodtable">
  <tr>
    <th height="25" align="center" bgcolor="#E4F1FA">款源日期</th>
    <th align="center"  bgcolor="#E4F1FA">入账日期</th>
    <th align="center"  bgcolor="#E4F1FA">有效日期</th>
    <th align="center"  bgcolor="#E4F1FA">批号/期号</th>
    <th align="center" bgcolor="#E4F1FA">品名编号</th>
    <th align="center" bgcolor="#E4F1FA">统一分类条码</th>
<!--      <th align="center" bgcolor="#E4F1FA">票据编号</th> -->
    <th align="center" bgcolor="#E4F1FA">费用或品名名称</th>
    <th align="center" bgcolor="#E4F1FA">摘要</th>
    <th align="center" bgcolor="#E4F1FA">品牌</th>
    <th align="center" bgcolor="#E4F1FA">等级</th>
    <th align="center" bgcolor="#E4F1FA">型号</th>
    <th align="center" bgcolor="#E4F1FA">品牌规格</th>
    <th align="center" bgcolor="#E4F1FA">单位</th>
    <th align="center" bgcolor="#E4F1FA">数量</th>
    <th align="center" bgcolor="#E4F1FA">重量</th>
    <th align="center" bgcolor="#E4F1FA">箱规格</th>
    <th align="center" bgcolor="#E4F1FA">单价管理</th>
   <!--  <th align="center" bgcolor="#E4F1FA">标识条码</th> -->
    <th align="center" bgcolor="#E4F1FA">单价</th>
    <th align="center" bgcolor="#E4F1FA">金额</th>
    <th align="center" bgcolor="#E4F1FA">费用类别</th>
    <th align="center" bgcolor="#E4F1FA">科目</th>
    <th align="center" bgcolor="#E4F1FA">库房管理</th>
    <th align="center" bgcolor="#E4F1FA">借方金额</th>
    <th align="center" bgcolor="#E4F1FA">贷方金额</th>
    <th align="center" bgcolor="#E4F1FA">方向</th>
    <th align="center" bgcolor="#E4F1FA">余额</th>
  </tr>
   <s:iterator value="pageForm.list">
  <tr class="xggoods" >
    <td height="25" align="center" bgcolor="#FFFFFF">
   ${fn:substring(startDate, 0, 10)}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${fn:substring(endDate, 0, 19)}</td>
    
    <td align="center" bgcolor="#FFFFFF">
    ${fn:substring(periodDate, 0, 10)}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${batchNumber}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${goodsCoding}</td>
     <td align="center" bgcolor="#FFFFFF">${defaultStorage}</td>
     <%--   <td align="center" bgcolor="#FFFFFF">${billsNumbers}</td> --%>
     <td align="center" bgcolor="#FFFFFF">${goodsName}</td>
      <td align="center" bgcolor="#FFFFFF">${reasonThing}</td>
    <td align="center" bgcolor="#FFFFFF">${mnemonicCode}</td>
    <td align="center" bgcolor="#FFFFFF">${mnemonicCode}</td>
    <td align="center" bgcolor="#FFFFFF">${model}</td>
    <td align="center" bgcolor="#FFFFFF">${standard}</td>
    <td align="center" bgcolor="#FFFFFF">${goodsVariableID}</td>
    <td align="center" bgcolor="#FFFFFF">
     ${quantity}
    </td>
    <td align="center" bgcolor="#FFFFFF">
     ${weight}
    </td>
    <td align="center" bgcolor="#FFFFFF">
     ${boxStandard}
    </td>
    <td align="center" bgcolor="#FFFFFF">
   ${priceManage}
       </td>
 <%--      <td align="center" bgcolor="#FFFFFF">
   ${identifyingCode}</td> --%>
    <td align="center" bgcolor="#FFFFFF">
   ${price}</td>
    <td align="center" bgcolor="#FFFFFF">
     ${money}</td>
     <%--   <td align="center" bgcolor="#FFFFFF">
    ${otherAmount}</td> --%>
    <td align="center" bgcolor="#FFFFFF">
    ${costType}</td>
    <td align="center" bgcolor="#FFFFFF">  
     ${subjectsName}
    </td>
    <td align="center" bgcolor="#FFFFFF">
    ${depotType}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${loan}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${forLoan}</td>
    <td align="center" bgcolor="#FFFFFF">
    ${direction}</td>
    <td align="center" bgcolor="#FFFFFF">
   ${balance}</td>
  </tr>
  </s:iterator>
</table>
</div>
    </td>
  </tr>
</table>
  <table width="99%" height="25" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-bottom:5px;">
    <tr>
      <td width="1%" bgcolor="#FFFFFF">&nbsp;</td>
     <td >
		财务仓库：${cashierBillsVO.bankDepotName}
	</td>
    <td >
		实物仓库：${cashierBillsVO.afterDiscount}
	</td>
	 <td >
		资料仓库：${cashierBillsVO.dataDepotName}
	</td>
    </tr>
  </table>
  <table width="99%" border="0" id="table4" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
    <tr>
      <td width="10%" height="25" align="right"><span class="STYLE1">往来单位：</span></td>
      <td width="15%">
        ${cashierBillsVO.ccompanyname}
     </td>
      <td width="10%" align="right"><span class="STYLE1">单位电话：</span></td>
      <td width="15%">${cashierBillsVO.companyTel}</td>
     <td width="10%" align="right"><span class="STYLE1">单位负责人：</span></td>
      <td width="15%">${cashierBillsVO.cresponsible}</td>
      <td width="10%" align="right"><span class="STYLE1">银行账户：</span></td>
      <td width="15%">${cashierBillsVO.accountNum}</td>
    </tr>
    <tr>
      <td height="25" align="right"><span class="STYLE1">单位负责人电话：</span></td>
      <td>${cashierBillsVO.responsibleTel}</td>
      <td align="right"><span class="STYLE1">公司地址：</span></td>
      <td >${cashierBillsVO.companyAddr}</td>
      <td align="right"><span class="STYLE1">行业类别：</span></td>
      <td>${cashierBillsVO.industryType}</td>
       <td height="25" align="right"><span class="STYLE1">单位往来关系：</span></td>
      <td>${cashierBillsVO.contactConnections}</td>
    </tr>
  </table>
  <table width="99%" border="0" align="center" id="table5" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
    <tr>
      <td width="10%" height="25" align="right"><span class="STYLE1">往来个人：</span></td>
      <td width="15%">
      ${cashierBillsVO.contactUserName}
     </td>
      <td width="10%" align="right"><span class="STYLE1">电话：</span></td>
      <td width="15%">${cashierBillsVO.tel}</td>
      <td width="10%" align="right"><span class="STYLE1">个人身份证号：</span></td>
      <td width="15%">${cashierBillsVO.staffIdentityCard}</td>
     <td align="right"><span class="STYLE1">银行账号：</span></td>
      <td>${cashierBillsVO.userAccountNum}</td>
       </tr>
    <tr>
      <td height="25" align="right"><span class="STYLE1">QQ：</span></td>
      <td >${cashierBills.referenceCode}</td>
      <td align="right"><span class="STYLE1">邮箱：</span></td>
      <td>${cashierBills.referenceOrganization}</td>
      <td align="right"><span class="STYLE1">地址：</span></td>
      <td>${cashierBills.staffAddress}</td>
       <td width="10%" align="right"><span class="STYLE1">个人往来关系：</span></td>
      <td width="15%">${cashierBillsVO.phone}</td>
    </tr>
  </table>
  <table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td height="30" align="center">
      <c:if test="${tohide!='tohide'}">
      <input type="button" class="btn001 JQueryClose" name="button2" value="返回" />
      </c:if></td>
    </tr>
  </table>
</div>
<s:token></s:token>
</form>
</body>
</html>
