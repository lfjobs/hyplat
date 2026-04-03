<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>盘库单据</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/stocktaking_add.js"></script>
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

#table input {
	width: 180px;
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
	var tokens = 0;
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
	var trID="";

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
	<body>
		<div id="apDiv1"></div>
		<form name="PurchaseForm" id="PurchaseForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						盘库单据(双击选择物品)
					</div>
				</div>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="30" align="right">
							凭证号：
						</td>
						<td>
							<input type="text" style="width: 200px; border: 0" 
								id="journalNum" name="financialBill.journalNum" readonly="readonly" />
						</td>
						<td align="right">
							单据类别：
						</td>
						<td>
							<span id="billsType">盘库单</span>
						</td>
						<td align="right">
							<span class="xx">*</span>责任人：
						</td>
						<td>
							<input type="hidden" id="partnerID" name="financialBill.staffID"
								readonly="readonly" />
							<input type="text" id="partnerName"
								name="financialBill.StaffName" size="15" class="put3 input" readonly="readonly"/>
							<a href="#"
								onclick="importGY('table3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames"></span>
						</td>
						<td align="right">
							部门：
						</td>
						<td align="left" id="dept">
							<select name="financialBill.departmentID" id="departmentID"
								style="width: 180px;"></select>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td align="left" colspan=2>
							<input type="text" id="bankNum"
								name="financialBill.companyBankNum" readonly="readonly"
								value="${financialBill.companyBankNum}" size="35" />
							<a href="#"
								onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')">选择</a>
					</tr>
				</table>
				<div id="Layer1"
					style="width:100%; height: 340px; overflow: scroll;">
					<table width="100%" align="center" cellpadding="0" cellspacing="0"
						class="table" id="tt">
						<tr>
							<th height="25" align="center" bgcolor="#E4F1FA">
								序号
							</th>
							<th align="center" bgcolor="#E4F1FA">
								库
							</th>
							<th align="center" bgcolor="#E4F1FA">
								区
							</th>
							<th align="center" bgcolor="#E4F1FA">
								架
							</th>
							<th align="center" bgcolor="#E4F1FA">
								位
							</th>
							<th align="center" bgcolor="#E4F1FA">
								统一分类条码
							</th>
							<th align="center" bgcolor="#E4F1FA">
								费用或品名名称
							</th>
							<th align="center" bgcolor="#E4F1FA">
								类型
							</th>
							<th align="center" bgcolor="#E4F1FA">
								单位
							</th>
							<th align="center" bgcolor="#E4F1FA">
								规格
							</th>
							<th align="center" bgcolor="#E4F1FA">
								数量
							</th>
							<th align="center" bgcolor="#E4F1FA">
								盘库量
							</th>
							<th align="center" bgcolor="#E4F1FA">
								盘库结果
							</th>
							<th align="center" bgcolor="#E4F1FA">
								备注
							</th>
							<th align="center" bgcolor="#E4F1FA">
								
							</th>
							<th align="center" bgcolor="#E4F1FA">
								
							</th>
							<th align="center" bgcolor="#E4F1FA">
								
							</th>
						</tr>
						<c:set var="aa" value="1"></c:set>
						<s:iterator begin="1" end="20">
							<tr id="kelong${aa }" class="xggoods">
								<td height="25" align="center" bgcolor="#FFFFFF"><span name="number">${aa }</span><c:set var="aa" value="${aa +1}"></c:set></td>
								<td align="center" bgcolor="#FFFFFF">
									<input id="numbers" name="numbers" type="hidden" value="${aa }"/>
									<input id="goodsID" name=goodsID type="hidden"/>
									<input id="inventoryID" name="inventoryID" type="hidden"/>
									<input id="pware" name="pware" type="hidden"/>
									<input id="parea" name="parea" type="hidden"/>
									<input id="pframe" name="pframe" type="hidden"/>
									<input id="place" name="place" type="hidden"/>
									<span id="warehouseName"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="areaName"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="frameName"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="positionName"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="sortCode"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="goodsName"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="type"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="unit"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="acquiesceStandard"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="invenQuantity"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span class="xx" style="display: none;">*</span><input id="stocktakingQuantity" name="stocktakingQuantity" class="jisuan" size="5" style="margin-left: 2px; display: none"/>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<input id="stocktakingresults" name="stocktakingresults" style="margin-left: 2px; display: none; border: 0" size="5" readonly="readonly"/>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span id="remindContent"></span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</td>
								<td align="center" bgcolor="#FFFFFF">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								</td>
							</tr>
						</s:iterator>
					</table>
					</div>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
						<tr>
							<td height="30" align="center">
								<input type="button" class="btn001 JQuerySubmitgd"
									name="button3" value="保存" />
								<input type="button" class="btn001 JQueryClose" name="button2"
									value="返回" />
							</td>
						</tr>
					</table>
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
							选择物品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="142">
								<input name="typeID" class="input" id="typeID" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
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
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
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
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>
	</body>
</html>
