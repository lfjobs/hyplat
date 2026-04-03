<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>入库管理</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/storage_add.js"></script>
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
  var treeNames="<%=session.getAttribute("organizationName")%>";
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
	var type="${type}";

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
   
   
    //change事件加载下一级内容
                $("select.warehouse").change(function(){
                		if(notoken){
							alert("正在加载数据")
							return;
						  }
						notoken=1;
                		if($.trim($(this).attr("title"))=='4'){
                		notoken=0;
                		return;
                		}
                		aryParam=$.trim($(this).attr("title"));
                		 $parent=$(this).parent().parent();
                		var wareID=$.trim($(this).children("option:selected").val());
                		 var url =  basePath+"/ea/warehouse/sajax_ea_getListWareHouseZiAjax.jspa?warehouse.wareID="+wareID+"&warehouse.wareType="+aryParam+"&date="+new Date();
       							 $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
											      var wareHouseList = member.wareHouseList;
											        var se="";
											       
											      if(wareHouseList != null&&wareHouseList.length>0){
												      for(var i=0;i<wareHouseList.length;i++){
												    		var op="<option value='"+wareHouseList[i].wareID+"'>"+wareHouseList[i].wareName+"</option>";
												      		se+=op;
												      }
												      var xe=parseInt(aryParam)+1;
												      $parent.find("select[title='"+ xe +"'] option").remove();
												      $parent.find("select[title='"+ xe +"']").append("<option value=''>无</option>");
											          $parent.find("select[title='"+ xe +"']").append(se);
											      }else{
											      	 var xe=parseInt(aryParam)+1;
											      	for(var i=xe;i<=4;i++){
											      	  $parent.find("select[title='"+ i +"'] option").remove();
												      $parent.find("select[title='"+ i +"']").append("<option value='' selected='selected'>无</option>");
											      	
											      	}
											      }
											       notoken=0;
						                        },
						                        error: function cbf(data){
						                           notoken=0;
						                           alert("数据获取失败！")
						                        }
						                    });
                			})
});

		document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    			//alert($("input#typeID").val());
    				if($("input#typeID").val()==''){
						return false;
    				}
        			if($("input#typeID").val()!=''){
			        	var typeName = $("#typeID", $("table#searchgood")).val();
						$(":input#parms").val("parameter=" + typeName);
						cx("parameter=" + typeName);
					}
    			}
			}
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
						入库单据
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
							<span id="billsType">入库单</span>
						</td>
						<td align="right">
							责任人：
						</td>
						<td>
							<input type="hidden" id="partnerID" name="financialBill.staffID"
								readonly="readonly" />
							<input type="text" id="partnerName"
								name="financialBill.StaffName" size="15" />
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
				<table width="99%" height="150px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 150px; overflow: scroll;">
								<table width="1500" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											库
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											区
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											架
										</th>
										<th align="center" bgcolor="#E4F1FA" width="80px">
											位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											品名编号
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
											品牌
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单位
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数量
										</th>
										<th align="center" bgcolor="#E4F1FA">
											单价
										</th>
										<th align="center" bgcolor="#E4F1FA">
											金额
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											付款方式
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											物流方式
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											提醒内容
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<tr>
									<tr id="kelong" style="display: none">
										<td height="30" align="center" bgcolor="#FFFFFF">
											<input id="numbers" name="numbers" class="input" size="2" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<select class="warehouse put3" id="pware" style="width: 60px" title="1" name="pware">
												<option value="" >无</option>
												<c:forEach items="${warehouseList}" var="warehouse">
													<option value="${warehouse.wareID}" >${warehouse.wareName}</option>
												</c:forEach>
											</select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
												<select class="warehouse put3" id="parea" style="width: 60px" title="2" name="parea">
													<option value="" >无</option>
												</select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
												<select class="warehouse put3" id="pframe" style="width: 60px" title="3" name="pframe">
													<option value="" >无</option>
												</select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<select class="warehouse put3" id="place" style="width: 60px" title="4" name="place">
												<option value="" >无</option>
											</select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" name="goodsID" id="goodsID" />
											<span id="goodsNum"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="sortCode">${sortCode}</span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="goodsName"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="type"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="brand"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="modelNumber"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<select id="unit" name="unit"></select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="quantity" class="jisuan isNaN put3" id="quantity"
												size="3" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="unitPrice" class="jisuan isNaN put3" id="price"
												size="5" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="amount" readonly="readonly" class="input"
												id="money" size="5" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.payTypeList}" listKey="codeValue"
												listValue="codeValue" id="payType" name="paymentType"
												theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20101101dfs3uhdprp0000000031','#payType','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="%{#request.logisticsList}"
												listKey="codeValue" listValue="codeValue" id="logistics"
												name="logisticsType" theme="simple"></s:select>
											<a href="#"
												onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')">新添</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remindContent" id="remindedContent" size="15"
												style="margin-left: 2px;" />
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

				<table width="99%" height="30" border="0" align="center"
					cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
					style="margin-bottom: 5px;">
					<tr>
						<td bgcolor="#FFFFFF" colspan="3">
							<input type="button" class="ACT_btn" name="button4" value="选择物品"
								id="shuju" />
							<input type="button" class="ACT_btn" name="button5"
								value="选择往来单位" id="xzwlaw" />
							<input type="button" class="ACT_btn" name="button4"
								value="选择往来个人" id="xzwlgr" />
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${cashierBillsVO.ccompanyname}</span>
							<input type="hidden" id="ccompanyID"
								name="financialBill.ccompanyID"
								value="${financialBill.ccompanyID}" />
							<input type="hidden" id="ccompanyname"
								name="financialBill.ccompanyName"
								value="${cashierBillsVO.ccompanyname}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${cashierBillsVO.companyTel}</span>
							<input type="hidden" id="ccompanyID"
								name="financialBill.companyTel"
								value="${cashierBillsVO.companyTel}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${cashierBillsVO.cresponsible}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum">${cashierBillsVO.accountNum}</span>
							<select style="display: none" id="aNum" name="financialBill.accountNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${cashierBillsVO.responsibleTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${cashierBillsVO.companyAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${cashierBillsVO.industryType}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${cashierBillsVO.contactConnections}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="financialBill.ccompanyRelationship" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${cashierBillsVO.contactUserName}</span>
							<input type="hidden" id=contactUserID name="financialBill.cstaffID"
								value="${financialBill.cstaffID}" />
							<input type="hidden" id="contactUserName"
								name="financialBill.cstaffName"
								value="${cashierBillsVO.contactUserName}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${cashierBillsVO.tel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${cashierBillsVO.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${cashierBillsVO.userAccountNum}</span>
							<select style="display: none" id="userNum" name="financialBill.userAccountNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${cashierBillsVO.userQq}</span>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${cashierBillsVO.email}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${cashierBillsVO.userAddr}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${cashierBillsVO.phone}</span>
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择"
								name="financialBill.cstaffRelationship" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="hidden" name="cashierBills.status"
								id="cashierstatus" value="${cashierBillsVO.status}" />
							<input type="button" class="btn001 JQueryunitret" name="button"
								value="重置往来单位" />
							<input type="button" class="btn001 JQuerypersonret" name="button"
								value="重置往来个人" />
							<input type="button" class="btn001 JQuerySubmitgd" name="button3"
								value="提交审核" />
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
								<span id="goodtitle">物品编码或名称：</span>
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
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
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

		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
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
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">

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
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
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
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
			</div>
		</div>
	</body>
</html>
