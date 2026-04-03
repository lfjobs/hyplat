<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>采购单据修改</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/Purchase_add1.js"></script>
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
		<style type="text/css">
.hide {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
	border-bottom: 0px;
	background: transparent;
}

.classhide {
	display: none;
}

#goodstable input {
	width: 180px;
}

#contactcompany input {
	width: 180px;
}

#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>

		<script type="text/javascript">
  	var treeID = '<%=session.getAttribute("organizationID")%>';
  	var treeNames="<%=session.getAttribute("organizationName")%>";
	var tokens = 0;
	var keyvalue="";
	var basePath = "<%=basePath%>";
	var cashierBillsID="${cashierBills.cashierBillsID}";
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
	$(document).ready(function() {    //获取凭证号
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
</script>

	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
		<div id="apDiv1"></div>
		<form name="PurchaseForm" id="PurchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID==null?'':cashierBills.cashierBillsID}" id="cashierBillsID"/>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
						    <input type="button" class="menubtn JQuerySubmitgd grey disabled" 
						    name="button3" value="保存" disabled="disabled"/><input type="button" 
						    class="menubtn JQuerySubmitgd grey disabled" name="button" value="提交审核" disabled="disabled"/><input type="button" 
						    class="menubtn grey disabled" name="button" id="addgoodtr" value="增加一行" disabled="disabled"/><input type="button" 
						    class="menubtn grey disabled" name="button" id="delgoodtr" value="删除新增行" disabled="disabled"/><input type="button" 
						    class="menubtn grey disabled" name="button" id="JQueryprint" value="打印预览" disabled="disabled"/><input type="button" 
						    class="menubtn JQueryClose" name="button2" value="关闭" height="300px"/><input type="button" 
						    class="menubtn grey" name="button" value="帮助" disabled="disabled"/><input type="hidden" 
						    value="${jumptype}" name="jumptype"/> 
							</nobr>	
						</td>
					</tr>
				</table>
			<div style="width: 100%;" id="maindiv">
			<div id="content1">
				<table width="800" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:10px;background: #FFFFFF;">
					<tr><td colspan="6" align="center" id="titlestyle">采购单</td></tr>
					<tr>
					<td align="right" colspan="5">单据编号：</td>
					<td align="left">
					 <c:if test="${type =='add'}">
			               <input type="text" style="width: 160px;"
						id="journalNum" class="inputbottom" name="financialBill.journalNum"
						readonly="readonly" value="${financialBill.journalNum}"/>
					 </c:if>
					 <c:if test="${type =='edit'}">
			               <input type="text" class="inputbottom" style="width: 160px;"
						   readonly="readonly" value="${financialBill.journalNum}"/>
					 </c:if>
					
					</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>供应商：
						</td>
						<td>
							<input type="hidden" id="ccompanyID"
								name="financialBill.ccompanyID"
								value="${financialBill.ccompanyID}" />
							<input type="text" id="ccompanyname"
								name="financialBill.ccompanyName"
								value="${financialBill.ccompanyName}" placeholder="单击选择供应商"
								class="notnull inputbottom" style="width: 150px;" readonly="readonly"/>
						</td>
						<td align="right">
							单位电话：
						</td>
						<td>
							<input type="text" id="companyTel"
								name="financialBill.ccompanyTel" value="${financialBill.ccompanyTel}" 
								class="isNaN ckTextLength inputbottom" style="width: 150px;" maxlength="30"/>
						</td>
						<td align="right">
							预付定金：
						</td>
						<td>
							<input type="text" class="isNaN ckTextLength inputbottom" maxlength="40" 
							style="width: 100px;" name="financialBill.frontMoney" value="${financialBill.frontMoney}" id="frontMoney"/>
							<select name="financialBill.moneyType" id="moneyType" style="width:60px;">
							 <option value="元">元</option>
							 <option value="美元">美元</option>
							 <option value="欧元">欧元</option>
							 <option value="韩元">韩元</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>采购员：
						</td>
						<td>
							<input type="hidden" id="partnerID" name="financialBill.staffID"
								readonly="readonly" value="${financialBill.staffID==null?ManStaffId:financialBill.staffID}"/>
							<input type="text" id="partnerName" onclick="importGY('table3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								name="financialBill.staffName" value="${financialBill.staffName==null?ManStaffName:financialBill.staffName}" 
								style="width: 150px;" class="notnull inputbottom" readonly="readonly"/>
						</td>
						<td align="right">
							采购员电话：
						</td>
						<td align="left" id="dept">
							<input type="text" style="width: 150px;" class="isNaN ckTextLength inputbottom" 
							maxlength="30" id="staffPhone" name="financialBill.staffPhone" value="${financialBill.staffPhone}"/>
						</td>
						<td align="right">
							<div id="u1170_rtf">
							付款方式：
							</div>
						</td>
						<td align="left">
						    <input type="text" style="width: 150px;" class="inputbottom" 
							value="${cashierBills.appstyle==00?'请选择':cashierBills.appstyle==01?'银行转帐':cashierBills.appstyle==02?'银行支票转账'
                                  :cashierBills.appstyle==03?'支付宝转帐':cashierBills.appstyle==04?'App转账':cashierBills.appstyle==05?'POS机转账'
                                  :cashierBills.appstyle==06?'现金转账':''}" readonly="readonly"/>
						    <input type="hidden" value="${cashierBills.appstyle}" id="payType"
												name="financialBill.paymentType" />
						    <%--<s:select list="%{#request.payTypeList}" listKey="codeValue"
												listValue="codeValue" id="payType" name="financialBill.paymentType"
												theme="simple"></s:select>
							<a href="#" onclick="toCCode('scode20101101dfs3uhdprp0000000031','#payType','#cashierTallyForm')">新添</a>
						--%></td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>采购日期：
						</td>
						<td>
							<input type="text" style="width:150px;" class="notnull inputbottom" 
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="purchaseDate" name="financialBill.purchaseDate" value="${financialBill.purchaseDate}"/>
						</td>
						<td align="right">
							<span style="color:red;">*</span>对方科目：
						</td>
						<td align="left">
							<input type="hidden" value="${financialBill.subjectID}" id="subjectsID"
												name="financialBill.subjectID" />
							<input type="text" readonly="readonly"
									value="${financialBill.subjectName}" placeholder="点击选择科目" 
									class="notnull inputbottom" style="width:150px;"
									id="tosubjects" name="financialBill.subjectName" />
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
					<td align="left" colspan="6">
					 <c:if test="${type =='add'}">
			              <input type="button" class="ACT_btn JQueryRelation" name="button" value="关联票据"/>
					      <span style="color:red;">*保存或提交审核前，请先关联出纳单票据！</span>
					 </c:if>
					 <c:if test="${type =='edit'}"></c:if>
					</td>
					</tr>
				</table>
				</div>
				
				<table width="800" height="300px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-left:10px;margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 900px; height: 300px; overflow: auto;">
								<table width="900" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品名称
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
											选择科目
										</th>
										<th align="center" bgcolor="#E4F1FA">
											供应商
										</th>
										<th align="center" bgcolor="#E4F1FA">
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<%--关联票据物品--%>
									<tr id="kelongold" style="display: none">
										<td height="30" align="center" bgcolor="#FFFFFF">
										    <input type="hidden" id="identifyingCode" name="identifyingCode" value="00"/>
											<input id="goodsNomber" name="goodsNomber" 
											style="border:0px;width:100%;height:100%;" size="2" readonly/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="goodsName" id="oldgoodsName" 
											style="height:100%;border:0px;" readonly="readonly">
											<input type="hidden" name="goodsID" id="oldgoodsID"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="price" id="oldprice" 
											style="margin-left:2px;height:100%;border:0Px;" size="5" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="quantity" id="oldquantity" size="5" 
											style="margin-left: 2px;height:100%;border:0px;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input type="text" id="oldunit" name="goodsVariableID" size="5" 
										    style="margin-left: 2px;height:100%;border:0px;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="money" readonly="readonly"
												id="money" size="5" style="margin-left: 2px;border:0px;"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" id="oldsubjectsID" name="subjectsID" />
											<input type="text" readonly="readonly" style="height:100%;border:0px;" id="oldtosubjects" name="subjectsName" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input type="hidden" id="oldccompanyID" name="ccompanyID" />
											<input name="ccompanyName" id="oldccompanyName" class="input" size="15"
												style="margin-left: 2px;width:100%;height:100%;border:0px;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remindedContent" id="oldremindedContent" class="input" size="15"
												style="margin-left: 2px;width:100%;height:100%;border:0px;"/>
										</td>
										<td align="center" width="40" bgcolor="#FFFFFF">
										     无
										</td>
									</tr>
									<tr id="kelong" style="display: none">
										<td height="30" align="center" bgcolor="#FFFFFF">
										    <input type="hidden" id="identifyingCode" name="identifyingCode" value="01"/>
											<input id="goodsNomber" name="goodsNomber" style="border:0px;width:100%;height:100%;" class="input" size="2" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span>
											<input name="goodsName" id="goodsName" placeholder="点击选择物品" class="panduan" size="10" style="height:100%;border:0px;" readonly="readonly">
											<input type="hidden" name="goodsID" id="goodsID"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span>
											<input maxlength="10" name="price" style="margin-left:2px;height:100%;border:0Px;" 
											class="posnum jisuan  input " id="price" size="5"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span>
											<input maxlength="10" name="quantity" 
											class="positiveNum jisuan put3 input" id="quantity" size="5" style="margin-left: 2px;height:100%;border:0px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span style="color:red;">*</span>
										    <input type="text" id="unit" name="goodsVariableID" class="panduan " maxlength="20" size="5" 
										    style="margin-left: 2px;height:100%;border:0px;" readonly="readonly"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="money" readonly="readonly" class="input"
												id="money" size="5" style="margin-left: 2px;border:0px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" value="${subjectsID}" id="subjectsID"
												name="subjectsID" />
											<span id="subjectsName">${subjectsName}</span>
											<span style="color:red;">*</span><input type="text" readonly="readonly"
												value="${subjectsName}" placeholder="点击选择科目" style="height:100%;border:0px;" class="panduan input" size="10"
												id="tosubjects" name="subjectsName" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <span style="color:red;">*</span><input type="text" name="ccompanyName" id="ccompanyname" placeholder="单击选择" class="input panduan" size="10"
												style="height:100%;border:0px;" readonly="readonly"/>
											 <input type="hidden" id="ccompanyID" name="ccompanyID" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remindedContent" id="remindedContent" class="input" size="15"
												style="margin-left: 2px;width:100%;height:100%;border:0px;" />
										</td>
										<td align="center" width="40" bgcolor="#FFFFFF"><%--
										    <span id="caozuo"><a href="#" id="baocun">保存</a></span>
											--%><a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<br/>
				<table width="800" border="0" id="table4" align="center"
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
							<span class="STYLE1">制单人：</span>
						</td>
						<td>
							<%--<span id="spname">${ManStaffName}</span--%>
							<input type="text" readonly="readonly" value="${ManStaffName}"
									class="inputbottom" style="width:150px;"
									id="spname"/>
						</td>
						<td align="right">
							<span class="STYLE1">制&nbsp;单&nbsp;日&nbsp;期：</span>
						</td>
						<td>
							<%--<span id="zddate">
							${financialBill.billsDate==null?param.curDateTime:financialBill.billsDate}
							</span>
							--%>
							<input type="text" readonly="readonly" value="${financialBill.billsDate==null?param.curDateTime:financialBill.billsDate}"
									class="inputbottom" style="width:150px;"
									id="zddate"/>
						</td>
					</tr>
				</table>
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
							<td width="100" align="right">编码或名称：</td>
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
		<%------------------------------------关联出纳单------------------------------------%>
		<form name="selectcashierForm" id="selectcashierForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择关联票据
						</div>
					</div>
					<table width="99%" height="33" id="searchcashier" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="80" align="right">
								黏贴单编号：
							</td>
							<td width="60">
								<input name="journalNum" class="input" id="journalNum" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
							    &nbsp;&nbsp;
								<input type="button" class="btn02" id="searchca" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcashier" name="button5"
									value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsyca" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxyca" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzyca">共&nbsp;&nbsp; <span style="color: red"
									id="zycountca"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 320px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02ca"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 310px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<%------------------------------------选择往来单位(供应商)------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
								<input  type="hidden" id="inputname" size="10"/>
								<input  type="hidden" id="inputid" size="10"/>
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 320px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 310px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<%------------------------------------选择往来个人------------------------------------%>
		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="userjqModel">
				<div class="content1" style="width: 100%; height: 400px">
					<div class="contentbannb">
						<div class="drag">
							选择往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="40" align="right">
								姓名：
							</td>
							<td width="50">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="100">
								<s:select list="codeRelationList" listKey="codeValue"
									listValue="codeValue" headerKey="" headerValue="--全部--"
									id="relation" name="relation" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchuu" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qduser" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzgr" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
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
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
	</body>
</html>
