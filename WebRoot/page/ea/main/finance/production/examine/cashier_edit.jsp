<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>出纳审核管理</title>
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
         .bitian
		{
		color: red;
		}
         </style>
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
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/cashier_edit.js"></script>
  <script type="text/javascript">
    var basePath = "<%=basePath%>";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	var deptID="${cashierBillsVO.departmentID}";
    var pNumber=${pageNumber};
    var history="${history}";
    var search="${search}";
    var sdate="";
    var edate="";
    var subjectsName="";
	var subjectsnumber="";
	var select=1;
	var notoken = 0;
	
	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 if(checkopertionName=="bankNum"){
		 	url = url + "?departmentID="+deptID;
		 }
		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
	   $("#daoRu").attr("src",basePath+url);
	   $("#bankJqm").jqmShow();
	}
	
	$(document).ready(function() {//销售单FORM
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
			$("#"+checkopertionName,$("#"+checkform)).attr("name","cashierBills.companyBankNum");
		}
		if(checkopertionName =="partnerName"){
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
});
</script>
</head>
<body>
<form name="cashierTallyForm" id="cashierTallyForm" method="post" enctype="multipart/form-data"> 
         <input type="submit" name="submit" style="display:none"/>
         <input type="text" name="statusForPerson" id="statusForPerson" value="${cashierBillsVO.depStatue}" style="display:none"/>
<div class="content1" style="width:100%;">
  <div class="contentbannb">
  	<div class="divtx">出纳审核管理</div>
  </div>
  <table width="99%" border="0" id="table3" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
  <tr>
      <td height="20" align="right">粘贴单编号：</td>
      <td>
      <input type="hidden"  name="cashierBills.cashierBillsID" id="cashierID" value="${cashierBillsVO.cashierBillsID}"/>
       <input type="hidden" name="cashierBills.cashierBillsKey" id="goodsBillsKey" value="${cashierBillsVO.cashierBillsKey}"/>
  ${cashierBillsVO.journalNum}</td>
      <td align="right">单据类别：</td>
      <td>
     ${cashierBillsVO.billsType}</td>
       <td align="right">制单日期：</td>
      <td>${fn:substring(cashierBillsVO.cashierDate, 0, 10)}</td>
    </tr>
  
    <tr>
       <td height="20" width="7%" align="right"><span class="STYLE1">公司：</span></td>
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
       <td height="20" width="7%" align="right"><span class="STYLE1">银行账号：</span></td>
       <td ><span id="bankNumk">${cashierBillsVO.companyBankNum}</span><a class="bankNumk">修改</a>
       <input type="text" id="bankNum" readonly="readonly" value="${cashierBillsVO.companyBankNum}" size="45" style="display: none;"/>
			<a href="#"	onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')" style="display: none;" class="bankNum">选择</a></td>
      <td align="right">票据支票管理:</td>
       <td align="left" >${cashierBillsVO.billCheck}</td>
      <td align="right"></td>
       <td width="15%" align="left" ></td>
    </tr>
  </table>
<table width="99%" height="165px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td valign="top">
<div id= "Layer1" style= "position:absolute; width:100%; height:160px; overflow: scroll;">
<table width="3000" align="center" cellpadding="0" cellspacing="0" class="table" id="goodtable">
  <tr>
    <th height="30" align="center" bgcolor="#E4F1FA">款源日期</th>
    <th align="center"  bgcolor="#E4F1FA">入账日期</th>
    <th width="75" align="center"  bgcolor="#E4F1FA">有效日期</th>
    <th width="110" align="center"  bgcolor="#E4F1FA">批号/期号</th>
    <th align="center" bgcolor="#E4F1FA">品名编号</th>
    <th align="center" bgcolor="#E4F1FA">统一分类条码</th>
    <th align="center" width="140" bgcolor="#E4F1FA">科目</th>
     <th align="center" bgcolor="#E4F1FA">费用或品名名称</th>
     <th align="center" bgcolor="#E4F1FA">摘要</th>
    <th align="center"  width="120" bgcolor="#E4F1FA">费用类别</th>
    <th width="60" align="center" bgcolor="#E4F1FA">类型</th>
     <th width="60" align="center" bgcolor="#E4F1FA">品牌</th>
    <th width="60" align="center" bgcolor="#E4F1FA">型号</th>
    <th width="60" align="center" bgcolor="#E4F1FA">品牌规格</th>
    <th width="60" align="center" bgcolor="#E4F1FA">单位</th>
    <th align="center" bgcolor="#E4F1FA">数量</th>
    <th align="center" bgcolor="#E4F1FA">重量</th>
    <th align="center" bgcolor="#E4F1FA">箱规格</th>
    <th align="center" width="140" bgcolor="#E4F1FA">单价管理</th>
    <th align="center" bgcolor="#E4F1FA">单价</th>
    <!-- <th align="center" bgcolor="#E4F1FA">金额</th> -->
    <th align="center" bgcolor="#E4F1FA">其他金额</th>
    <th align="center"  width="120" bgcolor="#E4F1FA">库房管理</th>
    <th align="center" bgcolor="#E4F1FA">借方金额</th>
    <th align="center" bgcolor="#E4F1FA">贷方金额</th>
    <th align="center" bgcolor="#E4F1FA" width="40">方向</th>
    <th align="center" bgcolor="#E4F1FA">余额</th>
  </tr>
  
  <s:iterator value="pageForm.list">
  <tr class="xggoods" id="${goodsBillsID}">
    <td height="30" align="center" bgcolor="#FFFFFF">
     <span id="startDate">${fn:substring(startDate, 0, 10)}</span>
     <input name="startDate" class="model1" onfocus="date(this);"
		value="${fn:substring(startDate, 0, 10)}"
		style="margin-left: 2px;" size="10" />
   </td>
   
   <td align="center" bgcolor="#FFFFFF">
    <span id="endDate">${fn:substring(endDate, 0, 19)}</span>
    <input type="hidden" name="edate" class="model1 endDate" id="username4"
		readonly="readonly"
		style="margin-left: 2px; padding: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;"
		size="10" />
   </td>
  
  <td align="center" bgcolor="#FFFFFF">
    <span id="periodDate">${fn:substring(periodDate, 0,
		10)}</span>
	<input name="periodDate" class="model1" id="periodDate"
		onfocus="date(this);"
		value="${fn:substring(periodDate, 0, 10)}"
		style="margin-left: 2px;" size="10" />
  </td>
  
    <td align="center" bgcolor="#FFFFFF">
    <span id="batchNumber">${batchNumber}</span>
    <input name="batchNumber" class="model1" id="batchNumber"
		value="${batchNumber}" style="margin-left: 2px;" size="10" />
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <input type="hidden" name="goodsID" value="${goodsID}"
		id="goodsID" />
	<span class="bhide" id="goodsCoding">${goodsCoding}</span>
	</td>
	
     <td align="center" bgcolor="#FFFFFF">
     <span id="defaultStorage" class="bhide">${defaultStorage}</span>
     </td>
     <td align="center" bgcolor="#FFFFFF">  
    <span class="bitian">*</span>
	<input type="hidden" value="${subjectsID}" id="subjectsID"
		name="subjectsID" />
	<span id="subjectsName">${subjectsName}</span>
	<input type="text" readonly="readonly"
		value="${subjectsName}" class="put3 model1" size="8"
		id="subjectsName" name="subjectsName" />
	<a href="#" class="tosubjects model1">选择科目</a>
    </td>
     <td align="center" bgcolor="#FFFFFF">
     <span id="goodsName" class="bhide">${goodsName}</span>
     </td>
     
    <td align="center" bgcolor="#FFFFFF">
    <span id="reasonThing">${reasonThing}</span>
	<input name="reasonThing" value="${reasonThing}"
		class="model1" id="reasonThing" style="margin-left: 2px;" />
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="costType">${costType}</span>
	<s:select class="dis" list="%{#request.costTypeList}"
		listKey="codeValue" listValue="codeValue" id="costType"
		name="costType" theme="simple"></s:select>
	<a href="#" class="model1"
		onclick="toCCode('scode20101101dfs3uhdprp0000000030','#costType','#cashierTallyForm')">新添</a>
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="typeID" class="bhide">${typeID}</span>
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="mnemonicCode" class="bhide">${mnemonicCode}</span>
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="model" class="bhide">${model}</span>
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="standard" class="bhide">${standard}</span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
	<span id="goodsVariableID">${goodsVariableID}</span>
	<select class="dis" id="goodsVariableID"
		name="goodsVariableID">
		<option value="">
			请选择
		</option>
		<s:if test='VariableID.length()!=0'>
			<s:if test="variableID==goodsVariableID">
				<option value="${variableID}" selected="selected"> 
					${variableID}
				</option>
			</s:if>
			<s:else>
				<option value="${variableID}">
					${variableID}
				</option>
			</s:else>
		</s:if>
		<s:if test='Variable1ID.length()!=0'>
			<s:if test="Variable1ID==goodsVariableID">
				<option value="${Variable1ID}" selected="selected">
					${Variable1ID}
				</option>
			</s:if>
			<s:else>
				<option value="${Variable1ID}">
					${Variable1ID}
				</option>
			</s:else>
		</s:if>
		<s:if test='Variable2ID.length()!=0'>
			<s:if test="Variable2ID==goodsVariableID">
				<option value="${Variable2ID}" selected="selected">
					${Variable2ID}
				</option>
			</s:if>
			<s:else>
				<option value="${Variable2ID}">
					${Variable2ID}
				</option>
			</s:else>
		</s:if>
		<s:if test='Variable3ID.length()!=0'>
			<s:if test="Variable3ID==goodsVariableID">
				<option value="${Variable3ID}" selected="selected">
					${Variable3ID}
				</option>
			</s:if>
			<s:else>
				<option value="${Variable3ID}">
					${Variable3ID}
				</option>
			</s:else>
		</s:if>
		<s:if test='Variable4ID.length()!=0'>
			<s:if test="Variable4ID==goodsVariableID">
				<option value="${Variable4ID}" selected="selected">
					${Variable4ID}
				</option>
			</s:if>
			<s:else>
				<option value="${Variable4ID}">
					${Variable4ID}
				</option>
			</s:else>
		</s:if>
	</select>
	</td>
	
    <td align="center" bgcolor="#FFFFFF">
    <span id="quantity">${quantity}</span>
	<input name="quantity" class="jisuan isNaN model1"
		value="${quantity}" id="quantity" size="3"
		style="margin-left: 2px;" />
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="weight">${weight}</span>
	<input name="weight" class="model1" value="${weight}"
		id="weight" size="3" style="margin-left: 2px;" />
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="boxStandard">${boxStandard}</span>
	<input class="model1" name="boxStandard"
		value="${boxStandard}" id="boxStandard" size="3"
		style="margin-left: 2px;" />
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
   	<span id="priceManage">${priceManage}</span>
	<s:select class="dis" list="%{#request.priceManageList}"
		listKey="codeValue" listValue="codeValue" id="priceManage"
		name="priceManage" theme="simple"></s:select>
	<a href="#" class="model1"
		onclick="toCCode('scode20101101dfs3uhdprp0000000032','#priceManage','#cashierTallyForm')">新添</a>
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
   	<span id="price">${price}</span>
	<input name="price" class="jisuan isNaN  model1"
		value="${price}" class="input" id="price" size="5"
		style="margin-left: 2px;" />
    </td>
    <!-- 
    <td align="center" bgcolor="#FFFFFF">
    <span id="money">${money}</span>
	<input name="money" readonly="readonly" value="${money}"
		type="hidden" class="model1" id="money" size="5"
		style="margin-left: 2px;" />
    </td>
     -->
    <td align="center" bgcolor="#FFFFFF">
    <span id="otherAmount">${otherAmount}</span>
	<input name="otherAmount" value="${otherAmount}"
		class="isNaN model1" id="otherAmount" size="3"
		style="margin-left: 2px;" />
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="depotType">${depotType}</span>
	<s:select list="#{'出库':'出库','入库':'入库','库存':'库存','其它':'其它'}"
		class="dis" name="depotType" id="depotType" theme="simple"></s:select>
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="loan">${loan}</span>
	<input name="loan" value="${loan}" class="isNaN model1"
		id="loan" size="5" style="margin-left: 2px;" />
    </td>
    
    <td align="center" bgcolor="#FFFFFF">
    <span id="forLoan">${forLoan}</span>
	<input name="forLoan" value="${forLoan}"
		class="isNaN model1" id="forLoan" size="5"
		style="margin-left: 2px;" />
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="direction">${direction}</span>
	<input name="direction" id="direction" size="1" class="isNaN model1" style="border: 0" value="${direction}" />
	<!-- 
	 <span id="direction">${direction}</span>
	<s:select class="dis" list="#{'借':'借','贷':'贷','其它':'其它'}"
		name="direction" id="direction" theme="simple"></s:select>
	-->
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="balance">${balance}</span>
	<input name="balance" value="${balance}"
		class="isNaN model1" id="balance" size="3"
		style="margin-left: 2px;" />
    </td>
  </tr>
  </s:iterator>
</table>
</div>
    </td>
  </tr>
</table>
  <table width="99%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-bottom:5px;">
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
      <td width="10%" height="30" align="right"><span class="STYLE1">往来单位：</span></td>
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
      <td height="30" align="right"><span class="STYLE1">单位负责人电话：</span></td>
      <td>${cashierBillsVO.responsibleTel}</td>
      <td align="right"><span class="STYLE1">公司地址：</span></td>
      <td >${cashierBillsVO.companyAddr}</td>
      <td align="right"><span class="STYLE1">行业类别：</span></td>
      <td>${cashierBillsVO.industryType}</td>
       <td height="30" align="right"><span class="STYLE1">单位往来关系：</span></td>
      <td>${cashierBillsVO.contactConnections}</td>
    </tr>
  </table>
  <table width="99%" border="0" align="center" id="table5" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
    <tr>
      <td width="10%" height="30" align="right"><span class="STYLE1">往来个人：</span></td>
      <td width="15%">
      <span id="contactUserName">${cashierBillsVO.contactUserName}</span>
     </td>
      <td width="10%" align="right"><span class="STYLE1">电话：</span></td>
      <td width="15%">${cashierBillsVO.tel}</td>
      <td width="10%" align="right"><span class="STYLE1">个人身份证号：</span></td>
      <td width="15%">${cashierBillsVO.staffIdentityCard}</td>
     <td align="right"><span class="STYLE1">银行账号：</span></td>
      <td>${cashierBillsVO.userAccountNum}</td>
       </tr>
    <tr>
      <td height="30" align="right"><span class="STYLE1">QQ：</span></td>
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
      <input type="hidden"  name="cashierBills.status" id="cashierstatus" value="${cashierBillsVO.status}"/>
       <input type="button" class="btn001 JQuerySubmitbh" name="button4" value="驳回" />   
   		<input type="button" class="btn002 JQuerySubmit" name="button4" value="审核通过" />
              
      <input type="button" class="btn001 JQueryClose" name="button2" value="返回" /></td>
    </tr>
  </table>
  <s:token></s:token>
  </div>
</form>

  <div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
		id="newccode">
		<div class="drag">
			添加
		</div>
		<table>
			<tr>
				<td>
					代码名字：
				</td>
				<td>
					<input id="ccodevalue" />
					<input id="codePID" type="hidden" />
					<input id="selectID" type="hidden" />
					<input id="formID" type="hidden" />
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveCCode()"
				value="确定" />
			<input type="button" class="input-button JQueryreturn1" value="取消" />
		</div>
  </div>
  <div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
		id="selectsubjects">
		<div class="drag">
			选择
		</div>
		<table>
			<tr>
				<td>
					科目名字：
				</td>
				<td align="left" class="subjects">
					<select id="province" number='0' style="width: 110px;"></select>
					<select id="city" number='1' style="width: 110px;"></select>
					<select id="county" number='2'
						style="width: 110px; display: none;"></select>
					<select id="addressTown" number='3'
						style="width: 110px; display: none;"></select>
					<select id="addressVillage" number='4'
						style="width: 110px; display: none;"></select>
					<select id="addressCommunity" number='5'
						style="width: 110px; display: none;"></select>
					<select id="addressFloor" number='6'
						style="width: 110px; display: none;"></select>
					<select id="addressLayer" number='7'
						style="width: 110px; display: none;"></select>
					<select id="addressSize" number='8'
						style="width: 110px; display: none;"></select>
				</td>
				<td>
					<a href="#"
						onclick="window.open('<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp')">新增</a>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" id="savesubjects"
				value="确定" />
			<input type="button" class="input-button JQueryreturns" value="取消" />
		</div>
	</div>

    <!-- -----------------------------------审核-------------------------------- -->
    <form name="SendForm2" id="SendForm2" method="post">
        <div class="jqmWindow"
            style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
            id="jqModelSend2">
            <input type="submit" name="submit2" style="display: none" />
            <div class="contentbannb">
                <div class="drag">
                    审核
                    <div class="close"></div>
                </div>
            </div>
            <center>
            <table width="100%" id="SearchTable2" cellspacing="20"
                cellpadding="20">
                <tr>
                    <td align="right" width='15%'>审核意见：</td>
                    <td align="left"><textarea rows="5" cols="40"   style="max-width: 270px;max-height: 100px;" 
                            name="comments" id="comments" class="ckTextLength"
                            maxlength="1000"></textarea></td>
                </tr>
            </table>
            <div align="center">
                <input type="button" class="input-button" id="submitResult2"
                    value=" 提交 " /> <input type="hidden" name="myaudit.auditorstatus"
                    id="auditorstatu" value="" />
            </div>
            </center>
        </div>
    </form>
  <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 " style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" />
			</div>
		</div>
	</body>
</html>