<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>调拨出库单${param.titletype}</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@page import="hy.ea.bo.Company"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany"); 
		%>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
				type="text/javascript"></script>
		<link rel="stylesheet"
				href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/allocationManagement_add.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>	
		<link rel="STYLESHEET" type="text/css"
	    href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
	    
<style type="text/css">
#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
.text_tree{
	overflow: scroll; z-index: 99;
	height: 300px;background-color: #ffffff;}
</style>
<script type="text/javascript">
  	var treeID = '<%=session.getAttribute("organizationID")%>';
  	var treeNames="<%=session.getAttribute("organizationName")%>";
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var financialbillID="${financialBill.financialbillID}";
	var deptID="${financialBill.departmentID}";
	var token = 0;
	var pNumber=${pageNumber};
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var myform='';
	var notoken = 0;
	var journalNum = "";
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var sdate="${sdate}";
    var edate="${edate}";
	var depotName="";
	var select="";
	var docNull;
	var subjectsNumbers;
	var endnumber = 0;
	var subRule = new Array();
	var loginstaffcheck=${loginstaffcheck};
	var pagetype="${type}";
	var companytreeid = "<%=c.getCompanyID()%>";
	var companytreename = "<%=c.getCompanyName()%>";
	var successtype="";
	var type = "${type}";
	$(document).ready(function() {
		//获取机构
		var pid="";
		var pname="";
		pid=companytreeid;
	    pname=companytreename;

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;

					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName + "'value='"
								+ obj.companyID + "'>" + obj.companyName
								+ "</option>";
					}
					$("#deptIDdr").html(str);
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
		//获取凭证号
    	var url="<%=basePath%>ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
		$.ajax({
                url: url,
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
			    var member = eval("(" + data + ")");
			    var nologin = member.nologin;
				if(nologin){
					document.location.href ="<%=basePath%>page/ea/not_login.jsp";
				}
		        $("#journalNum").val(member.BillID);
	    },
              error: function cbf(data){
				         alert("数据获取失败！")
				 }
		});
    });
	
	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID;
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
		}
		if(checkopertionName =="partnerName"){
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
}); 


function closePort(){
   loadcab.window.closePort();// 关闭读数据端口

}      
</script>
<script type="text/javascript">
//选择库房
var basePath = '<%=basePath%>';
var pNumber='${pageNumber}';
var treeid;
var treename;
var date;
var token = 0;
var tree1;
var comId="";
var loginstaffcheck=${loginstaffcheck};//判断审核次数
</script>
	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<div id="apDiv1"></div>
		<form name="InventoryForm" id="InventoryForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
				<div style="width: 100%;text-align:left;">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
						<input type="hidden" name="cashierBills.cashierBillsID" 
						value="${cashierBills.cashierBillsID==null?'':cashierBills.cashierBillsID}" id="cashierBillsID"/>
						<input type="button" class="menubtn JQuerySubmitgd" name="button3" value="保存" /><input type="button" 
						class="menubtn JQuerySubmitgd" name="button" value="提交审核"/><input type="button" 
						class="menubtn" name="button" id="addgoodtr" value="增加一行"/><input type="button" 
						class="menubtn" name="button" id="delgoodtr" value="删除新增行"/><input type="button" 
						class="menubtn grey" name="button" value="打印预览" disabled="disabled"/><input type="button" 
						class="menubtn JQueryClose" name="button2" value="关闭" /><input type="button" 
						class="menubtn grey" name="button" value="复制" disabled="disabled"/>
						</nobr>	
						</td>
					</tr>
				</table>
				</div>
				<table width="1000" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:5px;background: #FFFFFF;">
					<tr>
					<td colspan="6" align="center" id="titlestyle">调拨出库单</td>
					</tr>
					<tr>
					<td align="right" colspan="5">单据编号：</td>
					<td align="left">
					 <c:if test="${type =='add'}">
			               <input type="text" style="width: 160px;"
								id="journalNum" class="inputbottom" name="financialBill.journalNum"
								readonly="readonly"/>
					 </c:if>
					 <c:if test="${type =='edit'}">
			               <input type="text" style="width: 160px;" class="inputbottom"
								name="financialBill.journalNum"
								readonly="readonly" value="${financialBill.journalNum}"/>
					 </c:if>
					</td>
					</tr>
					<tr>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>调出机构：
						</td>
						<td>
						  <input type="hidden" id="ccompanyID" style="width: 160px;"
								name="financialBill.ccompanyID" value="<%=c.getCompanyID()%>" />
						  <input type="text" id="ccompanyName" style="width: 200px;"
								name="financialBill.ccompanyName" value="<%=c.getCompanyName()%>" 
							class="inputbottom"	readonly="readonly"/>
						</td>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>调入机构：
						</td>
						<td>
					      <select id="deptIDdr" name="drcompanyid">
						  </select>
						</td>
						<td height="30" align="right">
							<span style="color:red;">*</span>责&nbsp;任&nbsp;人：
						</td>
						<td id="td2">
							<input type="hidden" id="partnerID" name="financialBill.staffID"
								readonly="readonly" value="${financialBill.staffID==null?ManStaffId:financialBill.staffID}"/>
							<input type="text" id="partnerName" onclick="importGY('td2','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择人员" name="financialBill.staffName" value="${financialBill.staffName==null?ManStaffName1:financialBill.staffName}" style="width: 160px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
					</tr>
					<tr>
					    <td align="right">
							联系电话：
						</td>
						<td>
							<input type="text" id="companyTel" style="width: 160px;"
								name="financialBill.ccompanyTel" value="${financialBill.ccompanyTel}" 
								class="isNaN ckTextLength inputbottom" maxlength="30"/>
						</td>
						<td align="right">
							联系电话：
						</td>
						<td>
							<input type="text" id="drccompanyTel" style="width: 160px;"
								name="financialBill.drccompanyTel" value="${financialBill.drccompanyTel}" 
								class="isNaN ckTextLength inputbottom" maxlength="30"/>
						</td>
						<td align="right">
						           联系电话：
						</td>
						<td align="left">
							<input type="text" id="staffPhone" name="financialBill.staffPhone"  style="width: 160px;"
							value="${financialBill.staffPhone}" class="isNaN ckTextLength inputbottom" maxlength="30"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>出库日期：
						</td>
						<td>
							<input type="text" id="purchaseDate" style="width: 160px;"
							name="financialBill.purchaseDate" value="${financialBill.purchaseDate}" size="20" class="notnull inputbottom" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right">
							对方科目：
						</td>
						<td align="left">
							<input type="hidden" id="subjectsID"
								name="financialBill.subjectID" value="${financialBill.subjectID}" />
							<input type="text" readonly="readonly"
								placeholder="单击选择科目"	 class="inputbottom tosubjects" style="width: 160px;"
									id="subjectsName" name="financialBill.subjectName" value="${financialBill.subjectName}" readonly="readonly"/>
						</td>
						<td align="right">
						    <div id="u1170_rtf">
							物流方式：
							</div>
						</td>
						<td align="left">
						<s:select list="%{#request.logisticsList}"
										listKey="codeValue" listValue="codeValue" id="logistics"
										name="financialBill.logisticsType" theme="simple"></s:select>
									<a href="#"
										onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')">新添</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>调出仓库：
						</td>
						<td>
						   <input name="financialBill.depotName" value="${financialBill.depotName}" placeholder="单击选择调出仓库" 
						   id="choosedepotName" style="width: 160px;" class="notnull inputbottom" readonly="readonly">
						   <input type="hidden" name="financialBill.depotID" value="${financialBill.depotID}" id="choosedepotID"/>
						</td>
						<td height="30" align="right">
							<span style="color:red;">*</span>调入仓库：
						</td>
						<td>
						   <input name="financialBill.drdepotName" value="${financialBill.drdepotName}" placeholder="单击选择调入仓库" 
						   id="drchoosedepotName" style="width: 160px;" class="notnull inputbottom" readonly="readonly">
						   <input type="hidden" name="financialBill.drdepotID" value="${financialBill.drdepotID}" id="drchoosedepotID"/>
						</td>
						<td height="30" align="right"><span><font class='font' size="2">调入区域：</font></span></td>
						<td>
							<select id="area" style="width: 140px" class="selectlist" name="">

							</select>
							<input type="hidden" name="financialBill.area" id="area_ID" value="${financialBill.area}" class="inputVal"/>
							<input type="hidden" name="financialBill.areaName" id="area_Name" value="${financialBill.areaName}" class="inputVal"/>
						</td>
					</tr>


					<tr>
						<td height="30" align="right"><span><font class='font' size="2">调入货架：</font></span></td>
						<td>
							<select id="frame" style="width: 140px" name="" class="selectlist">

							</select>
							<input type="hidden" name="financialBill.frame" id="frame_ID" value="${financialBill.frame}" class="inputVal"/>
							<input type="hidden" name="financialBill.frameName" id="frame_Name" value="${financialBill.frameName}" class="inputVal"/>
						</td>
						<td height="30" align="right"><span><font class='font' size="2">调入展位：</font></span></td>
						<td>
							<select id="position" style="width: 140px" class="selectlist">

							</select>
							<input type="hidden" name="financialBill.position" id="position_ID" value="${financialBill.position}" class="inputVal"/>
							<input type="hidden" name="financialBill.positionName" id="position_Name" value="${financialBill.positionName}" class="inputVal"/>
						</td>
						<td align="right">
							<span style="color:red;">*</span>出&nbsp;库&nbsp;人：
						</td>
						<td align="left" id="td3">
							<input type="hidden" id="partnerID" name="financialBill.staffsID"
								   readonly="readonly" value="${financialBill.staffsID==null?ManStaffId:financialBill.staffsID}"/>
							<input type="text" id="partnerName" onclick="importGY('td3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								   placeholder="点击选择人员" name="financialBill.staffsName" value="${financialBill.staffsName==null?ManStaffName1:financialBill.staffsName}" style="width: 160px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
					</tr>

				</table>
				
				<table width="1000" height="200px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:15px;margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 1000px; height: 200px; overflow: auto;">
								<table width="1000" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品名称
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品类别
										</th>
										<th align="center" bgcolor="#E4F1FA">
											条码
										</th>
										<th align="center" bgcolor="#E4F1FA">
											科目
										</th>
										<th align="center" bgcolor="#E4F1FA">
											存货代码
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											库房
										</th>
										<th align="center" bgcolor="#E4F1FA">
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<c:forEach items="${billgoodlist}" var="list" varStatus="ltm">
									 <tr>
										<td height="30" width="50" align="center" bgcolor="#FFFFFF">
										    <input type="hidden" id="goodsBillsID" name="goodsmapold[${ltm.index}].goodsBillsID" value="${list.goodsBillsID}"/>
										    <input type="hidden" id="goodsBillsKey" name="goodsmapold[${ltm.index}].goodsBillsKey" value="${list.goodsBillsKey}"/>
											<input type="hidden" id="inventoryID" name="inventoryID" value="${list.inventoryID}"/>
											<input type="hidden" id="standard" name="standard" value="${list.standard}"/>
											<input type="hidden" name="goodsmapold[${ltm.index}].goodsNomber" id="goodsNomber" 
											size="5" style="border:0px;height:100%;" value="${list.goodsNomber==null?ltm.index+1:list.goodsNomber}"> 
										    <span>${list.goodsNomber==null?ltm.index+1:list.goodsNomber}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsNum}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.typeID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.sortCode}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.subjectsName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsnumber}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.goodsVariableID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.price}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.quantity}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.money}</span>
										    <input type="hidden" id="money" value="${list.money}"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.depotName==null?"无":list.depotName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.remark}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>无</span>
										</td>
									</tr>
									</c:forEach>
									<tr id="kelong" style="display: none">
										<td height="30" width="50" align="center" bgcolor="#FFFFFF">
											<input id="goodsNomber" name="goodsNomber" class="input" size="5" style="border:0px;height:100%;"/>
											<input type="hidden" id="inventoryID" name="inventoryID"/>
											<input type="hidden" id="standard" name="standard"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="goodsNum" id="goodsCoding" size="10" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span style="color:red;">*</span>
											<input name="goodsName" id="goodsName" placeholder="点击" size="10" style="border:0px;height:100%;" class="panduan input" readonly="readonly">
											<input type="hidden" name="goodsID" id="goodsID"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="typeID" id="typeID" size="5" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
					                       <input name="sortCode" id="sortCode" size="5" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
					                        <input type="hidden" id="subjectsID"
												name="subjectsID" />
											<input type="text" readonly="readonly" class="tosubjects" style="border:0px;height:100%;" size="10"
												placeholder="单击选择科目" name="subjectsName" id="subjectsName"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="goodsnumber" id="goodsNum" size="8"
												style="border:0px;height:100%;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="goodsVariableID" id="goodsVariableID" size="5" style="border:0px;height:100%;" maxlength="10" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span style="color:red;">*</span><input maxlength="10" name="price" class="jisuan isNaN put3" id="price"
												size="5" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										   <input maxlength="10" name="quantity" id="quantity" 
										   size="3" style="border:0px;height:100%;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="money" readonly="readonly" class="input"
												id="money" size="5" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="depotName" id="depotName" size="5" style="border:0px;height:100%;" readonly="readonly">
					                        <input type="hidden" name="depotID" id="depotID">
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remark" id="remark" size="18"
												style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="1000" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td height="30" align="right" bgcolor="#FFFFFF">
							<span>合&nbsp;&nbsp;&nbsp;计：</span>
						</td>
						<td align="left" bgcolor="#FFFFFF">
							<input type="text" readonly="readonly" value="0"
									class="inputbottom" style="width:150px;"
									id="countmoney"/>
							<input type="hidden" name="financialBill.goodsmoney" id="countmoney"/>
						</td>
						<td align="right" bgcolor="#FFFFFF">
							<span>金额（大写）：</span>
						</td>
						<td align="left" bgcolor="#FFFFFF" colspan="5" id="daxie">
						  &nbsp;&nbsp;
							<span id="6"></span>&nbsp;&nbsp;
							<span style="color: red">万</span>&nbsp;&nbsp;
							<span id="5"></span>&nbsp;&nbsp;
							<span style="color: red">仟</span>&nbsp;&nbsp;
							<span id="4"></span>&nbsp;&nbsp;
							<span style="color: red">佰</span>&nbsp;&nbsp;
							<span id="3"></span>&nbsp;&nbsp;
							<span style="color: red">拾</span>&nbsp;&nbsp;
							<span id="2"></span>&nbsp;&nbsp;
							<span style="color: red">元</span>&nbsp;&nbsp;
							<span id="1"></span>&nbsp;&nbsp;
							<span style="color: red">角</span>&nbsp;&nbsp;
							<span id="0"></span>&nbsp;&nbsp;
							<span style="color: red">分</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">制单人：</span>
						</td>
						<td>
							<%--<span id="spname">${ManStaffName}</span--%>
							<input type="text" readonly="readonly" value="${ManStaffName1}"
									class="inputbottom" style="width:150px;"
									id="spname"/>
						</td>
						<td align="right">
							<span class="STYLE1">制&nbsp;单&nbsp;日&nbsp;期：</span>
						</td>
						<td>
							<input type="text" readonly="readonly" value="${financialBill.billsDate==null?param.curDateTime:financialBill.billsDate}"
									class="inputbottom" style="width:150px;"
									id="zddate"/>
						</td>
					</tr>
				</table><%--
				<c:if test="${type =='edit'}">
				<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
				   <tr><td colspan="2">&nbsp;</td></tr>
				   <tr>
				   <td align="left" width="50px">备注：</td>
				   <td align="left" colspan="9"><input type="text" id="remark" class="inputbottom" style="width:80%;"/></td>
				   </tr>
				</table>
	            <table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
				<tr><td>
				<input type="hidden" id="staffauditname" 
				value="${ManStaffName}">
				<input type="hidden" id="staffauditcode" 
				value="${ManStaffCode}">
				<input type="hidden" id="staffauditid" 
				value="${ManStaffId}">
				</td></tr>
				<tr>
					<td height="25" align="right">
						公司经理：
					</td>
					<td>
						<input type="text" class="inputbottom gsjl" value="${billcheckmap['gsjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
						<input type="button" class="btncon verify" id="gsjl" />
					    </c:if>
					</td>
					<td align="right">
						部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom bmzg" value="${billcheckmap['bmzg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>
						<input type="button" class="btncon verify" id="bmzg" />
					    </c:if>
					</td>
					<td  align="right">
						人事处：
					</td>
					<td>
						<input type="text" class="inputbottom rsc" value="${billcheckmap['rsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
						<input type="button" class="btncon verify" id="rsc"/>
						</c:if>
					</td>
					<td  align="right">
						财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom cwsh" value="${billcheckmap['cwsh']}" readonly="readonly"/>
						<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
						<input type="button" class="btncon verify" id="cwsh"/>
						</c:if>
					</td>
					<td  align="center">
						收款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom skr" value="${billcheckmap['skr']}"readonly="readonly"/>
						<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
						<input type="button" class="btncon verify" id="skr"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td  height="25" align="right">
						总部总经理：
					</td>
					<td>
						<input type="text" class="inputbottom zjl" value="${billcheckmap['zjl']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
						<input type="button" class="btncon verify"  id="zjl"/>
						</c:if>
					</td>
					<td  align="right">
						总部部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom zg" value="${billcheckmap['zg']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
						<input type="button" class="btncon verify" id="zg"/>
						</c:if>
					</td>
					<td  align="right">
						总部人事处：
					</td>
					<td>
						<input type="text" class="inputbottom zbrsc" value="${billcheckmap['zbrsc']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
						<input type="button" class="btncon verify" id="zbrsc"/>
						</c:if>
					</td>
					<td align="right">
						总财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom zbcw" value="${billcheckmap['zbcw']}" readonly="readonly"/>
						<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
						<input type="button" class="btncon verify" id="zbcw"/>
						</c:if>
					</td>
					<td  align="center">
						交款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom jkr" value="${billcheckmap['jkr']}" readonly="readonly"/>
						<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
						<input type="button" class="btncon verify" id="jkr"/>
						</c:if>
					</td>
				</tr>
			</table>
	        </c:if>
			--%></div>
			<s:token></s:token>
		</form>
	<%--*****************选择仓库****************--%>
	<form name="ckForms" id="ckForms" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;left: 53%;"
				id="ckjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">选择仓库</div>
					</div>
					<table width="99%" height="33" id="searchck" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td height="33" align="center">
							<input type="button" class="btn02" id="ckok" name="button5" value="确定" />
							<input type="button" class="btn02 xzck" name="button" value="新增" />
							<input type="button" class="btn02 JQueryreturns" name="button4" value="关闭" /> 
							<input type="hidden" name="parmss" id="parmss" />
							</td>
						</tr>
					</table>
			    <form name="codeForm" method="post"></form>
				<div class="main_main">
				<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				  <tr>
				    <td  id="qh_sw" style="width: 15%;" valign="top">
				    <div id="ckaadTree" class="text_tree" >
				    	<ul  id="xmul"  class="filetree">
				    		<ul>
					    		<li>
					    			<span class='Warehouse folder cycle0 cy001' id="001">实物仓库</span>
					    		</li>
				    		</ul>
				    	</ul>
				    </div> 
				    <div id="ckaadTree2" class="text_tree" style="display: none;">
				    	<ul  id="xmul2"  class="filetree">
				    		<ul>
					    		<li>
					    			<span class='Warehouse folder cyc0 cl001' id="001">实物仓库</span>
					    		</li>
				    		</ul>
				    	</ul>
				    </div> 
				    </td>
				    <td style="width: 84%;" valign="top">
				      <iframe src="" name="ccode" width="100%" height="300" marginwidth="0" marginheight="0" scrolling="yes" frameborder="0" 
				      id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
				    </td>
				  </tr>
				</table>
				</div>
					</div>
			</div>
			<s:token></s:token>
		</form>
	<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							
						</div>
					</div>
					<div id="loading" style="position: relative;top: 13rem;left: 22rem;z-index: 3001;display: none">
					<img  src="<%=basePath%>/images/ea/lottery/loading.gif" style="float: left"/>
					<p style="float: left;line-height: 32px;margin-left: 1rem;">正在查询，请稍后！</p>
				</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								<span id="goodtitle">条码或物品编号：</span>
							</td>
							<td width="80">
								<input name="typeID" class="input" id="typeID" size="20"
									style="margin-left: 2px;" />
								<input id="hiddenText" type="text" style="display:none" />
							</td>
							<td height="33">
							<nobr>
							&nbsp;&nbsp;
							    <input type="button" value="手动输入" class="btn02 manual"/>
								<input style="display:none;"type="button" value="扫描输入" class="btn02 scan"/>
								<input type="button" class="btn02" id="searchGood"
									name="button7" value="查询" style="display:none;"/>
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<c:if test="${type=='02'}">
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								</c:if>
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />							
								<iframe width="0" height="0" id="loadcab" name="loadcab"></iframe>
								</nobr>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 2px; margin-bottom: 2px;">
						<tr>
							<td width="100%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 300px; width: 100%; overflow: auto;">
									<table width="98%" height="26" align='center' cellspacing="0"
										cellpadding="1" style='font-size: 12px;' class='bannb_01'>
										<tr>
											<td height="24" align="left" valign="top" class="txt01">
												&nbsp;点击
											</td>
										</tr>
									</table>
									<table width='99%' align='center' id='gotable' cellpadding='0'
										cellspacing='0' class='table'>
										<thead>
										<tr>
											<th height='21' align='center' bgcolor='#E4F1FA'>
												选择
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												物品名称
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												库房
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												条码
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												物品编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												序列号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												类型
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												库存量
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												单位
											</th>
										</tr>                        
										</thead>
										<tbody id="tbodya">

										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<%----------------支付物流方式---------------------------%>
		<div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
			id="newccode">
			<div class=" ">
				添加
			</div>
			<table>
				<tr>
					<td>
						物流方式名称：
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
		<%------------------------选择科目------------------------%>
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
		<%--选择人员--%>
		<div id="bankJqm" class="jqmWindow"
			style="width: 84%; height: 400px; absolute; display: none; left: 10%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
	<script type="text/javascript">
	//库房管理
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
    </script>
	</body>
</html>
