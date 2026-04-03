<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>归还入库单修改</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/receivestorage_edit.js"></script>
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
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	font-size: 8px;
	font-weight: bold;
}
</style>	    
		<script type="text/javascript">
  	var treeID = '<%=session.getAttribute("organizationID")%>';
  	var treeNames="<%=session.getAttribute("organizationName")%>";
	var tokens = 0;
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var financialbillID="${financialBill.financialbillID}";
	var deptID="${financialBill.departmentID}";
	var billStatus="${financialBill.billStatus}";
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
	var loginstaffcheck=${loginstaffcheck};//判断审核次数
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
$(document).ready(function(){
     tree1=new dhtmlXTreeObject("ckaadTree","100%","100%",0);
		    tree1.enableDragAndDrop(false); 
		    tree1.enableHighlighting(1);
	        tree1.enableCheckBoxes(0);
			tree1.enableThreeStateCheckboxes(false);
			tree1.setSkin(basePath+'js/tree/dhx_skyblue');
			tree1.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree1.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree1.insertNewChild("0","001","实物仓库",0,0,0,0);
			//tree1.insertNewChild("0","002","资料仓库",0,0,0,0);
			//tree1.insertNewChild("0","003","财务仓库",0,0,0,0);
			tree1.setOnClickHandler(function(){
			                        if(token)return;
			                        token = 1;
			                        $(".input01").attr("value","");
                                    $("#desc").attr("html","");
			                       treeid= tree1.getSelectedItemId();
			                       treename = tree1.getItemText(treeid);
			                        $("#codeName").attr("value",treename);
							           tree1.deleteChildItems(treeid);
						  	            var getListCCodeurl=basePath+"ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+treeid+"&date="+new Date().toLocaleString();
									     $.ajax({
						                        url: encodeURI(getListCCodeurl),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
										           var member = eval("("+data+")");
										            var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath + "page/ea/not_login.jsp";
								                  }
										           var depotManagelist = member.depotManagelist;
										          
										           if(null == depotManagelist){
										              return;
										           }    
										            for(var i=0;i<depotManagelist.length;i++)
												   {
										             tree1.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0,0,0,0);
										             tree1.setUserData(depotManagelist[i].depotID,"depotState",depotManagelist[i].depotState);
										           }
										            token = 0;
												  
								            },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
						     
			         $("#mainframe").attr(
				 				"src",basePath +"ea/cdepotmanage/ea_getListDepotManageTree.jspa?pageNumber=${pageNumber}&depotID="+ treeid + "&treename=" + treename + "&usetype=ck");
				 	 $(window).resize(); 
				 				
			});
});
</script>
	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<div id="apDiv1"></div>
		<form name="InventoryForm" id="InventoryForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;border:0px solid red;">
				<div style="width: 100%;text-align:left;border:0px solid red;">
				<table id="titleClone" width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;
					margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
				<tr><td align="left" style="height:24px;">
			    <nobr>
				<input type="button" 
				class="menubtn JQuerySubmitgd" name="button3"value="保存" /><input type="button" 
								class="menubtn JQuerySubmitgd" name="button" value="提交审核"/><input type="button" 
								class="menubtn grey" name="button" id="addgoodtr" value="增加一行" disabled="disabled"/><input type="button" 
								class="menubtn grey" name="button" id="delgoodtr" value="删除新增行" disabled="disabled"/><input type="button" 
								class="menubtn JQueryprint" name="button" value="打印预览"/><input type="button" 
								class="menubtn JQueryClose" name="button2"value="关闭" /><input type="button" 
								class="menubtn grey" name="button" value="帮助" disabled="disabled"/>
				</nobr>
					</td>
				</tr>
			    </table>
				</div>
				<table width="1000" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:5px;background: #FFFFFF;">
					<tr>
					<td colspan="6" align="center" id="titlestyle">归还入库单
					<!-- 归还入库单ID -->
					 <input type="hidden" id="financialbillID" name="financialBill.financialbillID" 
				    value="${financialBill.financialbillID}"/>
				    <input type="hidden" id="cashierBillsID" 
				    value="${cashierBills.cashierBillsID}"/>
					</td>
					</tr>
					<tr>
					<td align="right" colspan="5">单据编号：</td>
					<td align="left">
						<input type="text" style="width: 160px;" class="inputbottom"
							name="financialBill.journalNum" id="journalNum"
							readonly="readonly" value="${financialBill.journalNum}"/>
						<input type="hidden" id="cashierID" value="${financialBill.cashierBillsID}">
					</td>
					</tr>
					<tr>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>领&nbsp;用&nbsp;人：
						</td>
						<td>
							<input type="hidden" id="choosedepotID" name="financialBill.staffsID"
								readonly="readonly" value="${financialBill.staffID==null?ManStaffId:financialBill.staffID}"/>
							<input type="text" id="collarPerson" onclick="importGY('td2','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择领用人" name="financialBill.staffsName" value="${financialBill.staffName==null?ManStaffName1:financialBill.staffName}" style="width: 160px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
						<td align="right">
							 <span style="color:red;">*</span>领用仓库：
						</td>
						<td>
							
						   <input name="financialBill.depotName" value="${financialBill.depotName}" placeholder="单击选择领用仓库" 
						   id="choosedepotName" style="width: 160px;" class="notnull inputbottom" readonly="readonly">
						   <input type="hidden" name="financialBill.depotID" value="${financialBill.depotID}" id="choosedepotID"/>
						
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
							<input type="text"
								 class=" inputbottom billsDate telephone isNaN " style="width: 160px;"
								 id="ccompanyTel" name="financialBill.ccompanyTel" value="${financialBill.ccompanyTel}"/>
						</td>
						<td height="30" align="right">
		                   <span style="color:red;">*</span>对方科目：
						</td>
						<td> 
						   <input type="hidden" id="subjectsID"
								name="financialBill.subjectID" value="${financialBill.subjectID}" />
							<input type="text" readonly="readonly"
								 class="notnull inputbottom " style="width: 160px;"
									id="subjectsName" name="financialBill.subjectName" value="${financialBill.subjectName}" readonly="readonly"/>
						</td>
						<td align="right">
						           联系电话：
						</td>
						<td align="left">
							<input type="text"
								 class=" inputbottom billsDate telephone isNaN" style="width: 160px;"
								 id="staffPhone" name="financialBill.staffPhone" value="${financialBill.staffPhone}"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>入库日期：
						</td>
						<td>
							<input type="text" readonly="readonly"
								 class="notnull inputbottom billsDate" style="width: 160px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								 id="purchaseDate" name="financialBill.billsDate" value="${financialBill.billsDate}" readonly="readonly"/>
						</td>
						<td align="right">
						    <div id="u1170_rtf">
							物流方式：
							</div>
						</td>
						<td align="left">
							<input type="text" readonly="readonly"
								 class="notnull inputbottom billsDate" style="width: 160px;"
								 id="logisticsType" name="financialBill.logisticsType" value="${financialBill.logisticsType}" readonly="readonly"/>
						</td>
					</tr>	
				</table>
				<table width="800" height="158px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:15px;margin-top: 5px" id="tt">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 915px; height: 158px; overflow: auto;">
								<table width="915px" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品名称	
										</th>
										<th align="center" bgcolor="#E4F1FA">
											规格
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											批次
										</th>
										<th align="center" bgcolor="#E4F1FA">
											库房
										</th>
										<th align="center" bgcolor="#E4F1FA">
											入库位置
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" bgcolor="#E4F1FA">
											科目
										</th>
										<th align="center" bgcolor="#E4F1FA">
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<c:forEach items="${billsgoodlist}" var="list" varStatus="ltm">
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
											<span>${list.goodsName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.typeID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.goodsNum}</span>
										</td>					
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.sortCode}</span> 
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.depotName==null?"无":list.depotName}</span>
										</td>
										
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
											<span>${list.goodsnumber}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${financialBill.drdepotName}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.price}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span>${list.quantity}</span>
										</td>
										<td>
											<span>${list.goodsVariableID==null?'':list.goodsVariableID}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span id="amount">${list.price*list.quantity}</span>
											<input type="hidden" id="amount" value="${list.price*list.isQualify}"/>
										</td>
										
										
										<td align="center" bgcolor="#FFFFFF">
											<span>${list.subjectsName}</span>
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
										<td height="30" align="center" bgcolor="#FFFFFF">
											<input id="goodsNomber" name="goodsNomber" style="border:0px;height:100%;text-align:center;" class="input" size="2" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span>
											<input name="goodsName" id="goodsName" style="height:100%;border:0px;text-align:center;" size="10" placeholder="点击选择物品" class="panduan input" readonly="readonly">
											<input type="hidden" name="goodsID" id="goodsID"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="typeID" id="typeID" style="border:0;height:100%;text-align:center;" size="5" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="standard" id="standard" style="border:0px;height:100%;text-align:center;" size="5" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
					                        <input name="goodsNum" id="goodsNum" style="border:0px;height:100%;text-align:center;" size="15" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<input maxlength="10" name="price" id="price"
												size="5" style="border:0px;height:100%;" readonly="readonly"/>
										<%--<input maxlength="10" name="price" class="jisuan isNaN put3" id="price"
												size="5" style="border:0px;height:100%;" />
										--%>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span><input maxlength="10" name="isQualify" class="positiveNum jisuan isNaN put3"
												id="isQualify" size="8" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span style="color:red;">*</span>
										    <input type="text" id="goodsVariableID" name="goodsVariableID" class="panduan ckTextLength" maxlength="20" size="5" 
										    style="margin-left: 2px;height:100%;border:0px;"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input readonly="readonly" class="input"
												id="amount" size="8" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" id="subjectsID" name="subjectsID" />
											<span style="color:red;">*</span><input type="text" readonly="readonly" placeholder="点击选择科目" class="tosubjects panduan" size="10"
												id="subjectsName" name="subjectsName" style="border:0px;height:100%;"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										     <span style="color:red;">*</span><input type="text" name="ccompanyName" id="ccompanyname" placeholder="单击选择" class="input panduan" size="10"
												style="height:100%;border:0px;" readonly="readonly"/>
											 <input type="hidden" id="ccompanyID" name="ccompanyID" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remindedContent" id="remindedContent" size="15"
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
							<input type="hidden" name="financialBill.goodsmoney" 
							id="goodsmoney" value="${financialBill.goodsmoney}"/>
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
							<span>制单人：</span>
						</td>
						<td>
							<input type="text" readonly="readonly" value="${ManStaffName1}"
									class="inputbottom" style="width:150px;"
									id="spname"/>
						</td>
						<td align="right">
							<span>制&nbsp;单&nbsp;日&nbsp;期：</span>
						</td>
						<td>
							<input type="text" readonly="readonly" value=""
									class="inputbottom" style="width:150px;"
									id="zddate"/>
						</td>
					</tr>
				</table>
		<table width="99%" border="0" style="margin-left: 15px;" align="center" cellpadding="0" cellspacing="0" class="tab">
				   <tr><td colspan="2">&nbsp;</td></tr>
				   <tr>
				   <td align="left" width="50px">备注：</td>
				   <td align="left" colspan="9">
				   <input type="text" id="remark" class="inputbottom" style="width:80%;" readonly="readonly"/>
				   </td>
				   </tr>
				</table>
				<table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl" class="tab">
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
					    <c:if test='${billcheckmap["gsjlzt"]!=null&&billcheckmap["gsjlzt"]!=""}'>
					    	${billcheckmap["gsjlzt"]=="02"?"通过":"驳回"}
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
					    <c:if test='${billcheckmap["bmzgzt"]!=null&&billcheckmap["bmzgzt"]!=""}'>
					    	${billcheckmap["bmzgzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["rsczt"]!=null&&billcheckmap["rsczt"]!=""}'>
					    	${billcheckmap["rsczt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["cwshzt"]!=null&&billcheckmap["cwshzt"]!=""}'>
					    	${billcheckmap["cwshzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["skrzt"]!=null&&billcheckmap["skrzt"]!=""}'>
					    	${billcheckmap["skrzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["zjlzt"]!=null&&billcheckmap["zjlzt"]!=""}'>
					    	${billcheckmap["zjlzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["zgzt"]!=null&&billcheckmap["zgzt"]!=""}'>
					    	${billcheckmap["zgzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["zbrsczt"]!=null&&billcheckmap["zbrsczt"]!=""}'>
					    	${billcheckmap["zbrsczt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["zbcwzt"]!=null&&billcheckmap["zbcwzt"]!=""}'>
					    	${billcheckmap["zbcwzt"]=="02"?"通过":"驳回"}
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
						<c:if test='${billcheckmap["jkrzt"]!=null&&billcheckmap["jkrzt"]!=""}'>
					    	${billcheckmap["jkrzt"]=="02"?"通过":"驳回"}
					    </c:if>
					</td>
				</tr>
			</table>
			</div>
			<s:token></s:token>
		</form>
		<!--审核备注 -->
        <div class="jqmWindow jqmWindowcss" style="width: 350px;top:50%" id="jqModelSearch">
                <div class="drag">审核信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                	<tr>
                		<td colSpan="2" align="center">
                			<input type="radio" name="status" class="status" value="02"/>审核通过
                			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                			<input type="radio" name="status" class="status" value="03"/>驳回
                		</td>
                	</tr>
                    <tr>
                        <td>审核意见：</td>
                        <td>
                        <input type="text" id="remarks" class="inputbottom" style="width:280px;"/>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tocheckok" value="确定" />
                    <input type="button" class="input-button" id="tocheckclose" value="取消" />
                </div>
		</div>
	
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
				    <div id="ckaadTree" class="text_tree" style="overflow: scroll; z-index: 99; height: 300px;"></div> 
				    </td>
				    <td style="width: 84%;" valign="top">
				      <iframe src="" name="ccode" width="100%" height="300" marginwidth="0" marginheight="0" scrolling="yes" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
				    </td>
				  </tr>
				</table>
				</div>
					</div>
			</div>
			<s:token></s:token>
		</form>
	<%--******************************************物品选择****************************************--%>
		<form name="goodsForms" id="goodsForms" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;left: 53%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">选择物品</div>
					</div>
					<table width="99%" height="33" id="searchgoods" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">物品编码或名称：</td>
							<td width="142"><input name="typeID" class="input"
								id="typeID" size="20" style="margin-left: 2px;" /></td>
							<td height="33" width="450"><input type="button" class="btn02"
								ID="searchGoods" name="button7" value="查询" /> <input
								type="button" class="btn02" id="selectGoods" name="button5"
								value="确定" /> <input type="button" class="btn02 xzwp"
								name="button" value="新增" /> <input type="button"
								class="btn02 JQueryreturns" name="button4" value="关闭" /> <input
								type="hidden" name="parmss" id="parmss" /></td>
							<td width="80"><a id="wpsy_1" title="0">上一页</a></td>
							<td width="80"><a id="wpxy_1" title="0">下一页</a></td>
							<td width="100"><a id="wpzy_1">共&nbsp;&nbsp; <span
									style="color: red" id="wpzycount"></span>&nbsp;&nbsp;页
							</a></td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="aadTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_03"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
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
