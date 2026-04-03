<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>归还入库单单据</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/breakrkBill_dan.js"></script>
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
	<body>
		<div id="apDiv1"></div>
		<form name="InventoryForm" id="InventoryForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
			    <br/>
				<div style="width: 100%;text-align:left;">
				<input type="button" class="ACT_btn JQuerySubmitgd" name="button3"
								value="保存" />
				<input type="button" class="ACT_btn JQueryClose" name="button2"
								value="取消" />
				<input type="button" class="btn001" name="button" value="提交审核"/>
				<input type="button" class="ACT_btn" name="button" id="addgoodtr" value="本行后增加一行"/>
				<input type="button" class="ACT_btn" name="button" id="delgoodtr" value="删除本行"/>
				<input type="button" class="btn001" name="button" value="打印预览"/>
				<input type="button" class="btn001" name="button" value="复制"/>
				</div>
				<br/>
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
					<td colspan="6" align="center">
					<h2>归还入库单</h2>
					</td></tr>
					<tr>
					<td align="right" colspan="5">单据编号：</td>
					<td align="left">
					<input type="text" style="width: 200px; border: 0"
								id="journalNum" name="godownbill.journalNum"
								readonly="readonly"/>
					</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>发货仓库：
						</td>
						<td>
					      <input type="hidden" id="sendoutID"
							name="godownbill.sendoutID"/>
						  <input type="text" id="sendoutName"
							name="godownbill.sendoutName" class="input"/>
						</td>
						<td align="right">
							联系电话：
						</td>
						<td>
							<input type="text" id="Tel"
								name="Tel" class="isNaN ckTextLength input" maxlength="30"/>
						</td>
						<td align="right">
							物流方式：
						</td>
						<td>
						<s:select list="%{#request.logisticsList}"
										listKey="codeValue" listValue="codeValue" id="logistics"
										name="godownbill.logisticsType" theme="simple" style="width: 80px;"></s:select>
									<a href="#"
										onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')">新添</a>
						<input type="button" class="ACT_btn" name="button" value="关联票据"/>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>发货人：
						</td>
						<td id="td2">
							<input type="hidden" id="partnerID" name="sendstaffID"
								readonly="readonly" value="${ManStaffId}"/>
							<input type="text" id="partnerName" onclick="importGY('td2','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择发货人" name="sendstaffName" value="${ManStaffName}" size="20" class="notnull input" readonly="readonly"/>
						</td>
						<td align="right">
							联系电话：
						</td>
						<td align="left" id="dept">
							<input type="text" size="20" class="isNaN ckTextLength input" maxlength="30" id="staffphone" name="staffphone"/>
						</td>
						<td align="right">
							<div id="u1170_rtf">
							物流费用：
							</div>
						</td>
						<td align="left">
						  <input type="text" class="isNaN ckTextLength input" maxlength="40" size="15" name="moneynum" id="moneynum"/>
							<select name="moneytype" id="moneytype" style="width:60px;">
							 <option value="元">元</option>
							 <option value="美元">美元</option>
							 <option value="欧元">欧元</option>
							 <option value="韩元">韩元</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>入库日期：
						</td>
						<td>
							<input type="text" size="20" class="notnull input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="godownDate" name="godownbill.godownDate"/>
						</td>
						<td align="right">
							<span style="color:red;">*</span>对方科目：
						</td>
						<td align="left">
							<input type="hidden" id="subjectsID"
												name="godownbill.subjectID" />
							<input type="text" readonly="readonly"
								placeholder="单击选择科目"	 class="notnull input tosubjects" size="10"
									id="subjectsName" name="godownbill.subjectName" readonly="readonly"/>
						</td>
						<td align="right">
						    <div id="u1170_rtf">
							支付方式：
							</div>
						</td>
						<td align="left">
						<div style="float:left;">
						<s:select list="%{#request.payTypeList}" listKey="codeValue"
												listValue="codeValue" id="payType" name="godownbill.paymentType"
												theme="simple"></s:select>
							<a href="#" onclick="toCCode('scode20101101dfs3uhdprp0000000031','#payType','#cashierTallyForm')">新添</a>
						</div>
						<div style="float:left;">
						   &nbsp;支付状态：<select name="paystatus" id="paystatus" style="width:70px;">
							 <option value="已付清">已付清</option>
							 <option value="未付">未付</option>
							 <option value="已付部分">已付部分</option>
							</select>
						   </div>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span style="color:red;">*</span>收货仓库：
						</td>
						<td>
						   <input name="godownbill.depotName" placeholder="单击选择收货仓库" id="depotName" size="15" class="notnull input" readonly="readonly">
						   <input type="hidden" name="godownbill.depotID" id="depotID"  />
						   </td>
						<td align="right">
							<span style="color:red;">*</span>接收人：
						</td>
						<td align="left" id="td3">
							<input type="hidden" id="partnerID" name="godownbill.staffsID"
								readonly="readonly" value="${ManStaffId}"/>
							<input type="text" id="partnerName" onclick="importGY('td3','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
								placeholder="点击选择发货人" name="godownbill.staffsName" value="${ManStaffName}" size="20" class="notnull input" readonly="readonly"/>
						</td>
						<td align="right">
				                        已付：
						</td>
						<td align="left">
						 <input type="text" id="payok"
								name="godownbill.payok" class="input" size="10"/>
						未付：<input type="text" id="payno"
								name="godownbill.payno" class="input" size="10"/>
						</td>
					</tr>
				</table>
				
				<table width="99%" height="300px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 300px; overflow: scroll;">
								<table width="1040" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">
											序号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											物品名称
										</th>
										<th align="center" bgcolor="#E4F1FA">
											类型
										</th>
										<th align="center" bgcolor="#E4F1FA">
											型号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											批次
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
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<tr id="kelong" style="display: none">
										<td height="30" align="center" bgcolor="#FFFFFF">
											<input id="numbers" name="numbers" class="input" size="2" style="border:0px;height:100%;"/>
											<input type="hidden" id="goodsType" name="goodsType"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span>
											<input name="goodsName" id="shujus" placeholder="点击选择物品" size="10" style="border:0px;height:100%;" class="panduan input" readonly="readonly">
											<input type="hidden" name="goodsID" id="goodsID"  />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="standard" id="standard" size="5" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
										    <input name="modelNumber" id="modelNumber" size="5" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
					                        <input name="goodsNum" id="goodsNum" size="5" style="border:0px;height:100%;" readonly="readonly">
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span><input maxlength="10" name="unitPrice" class="jisuan isNaN put3" id="price"
												size="5" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="color:red;">*</span><input maxlength="10" name="invenQuantity" class="positiveNum jisuan isNaN put3"
												id="invenQuantity" size="3" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<%--
										    <select id="unit" name="unit"></select>
										    --%>
										    <span style="color:red;">*</span>
										    <input name="unit" id="unit" size="5" style="border:0px;height:100%;" maxlength="10" class="put3">
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="sumPrice" readonly="readonly" class="input"
												id="sumPrice" size="5" style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" id="subjectsID"
												name="subjectsID" />
											<span style="color:red;">*</span>
											<input type="text" readonly="readonly" class="tosubjects panduan" style="border:0px;height:100%;" size="10"
												placeholder="单击选择科目" name="subjectsName" id="subjectsName"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="remarks" id="remarks" size="15"
												style="border:0px;height:100%;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
									
									
									
									
									
									
									<tr>
										<td height="30" align="center" bgcolor="#FFFFFF">
											<span style="font-weight:bold;">合计</span>
										</td>
										<td align="center" bgcolor="#FFFFFF" colspan="5">
											<span id="countmoney">0</span>
											<input type="hidden" name="godownbill.goodsmoney" id="goodsmoney"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span style="font-weight:bold;">金额（大写）：</span>
										</td>
										<td align="left" bgcolor="#FFFFFF" colspan="6" id="daxie">
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
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td width="20%" align="right">
							<span class="STYLE1" style="font-weight:bold;">制单人：</span>
						</td>
						<td width="30%">
							<span id="spname">${ManStaffName}</span>
						</td>
						<td width="20%" align="right">
							<span class="STYLE1" style="font-weight:bold;">制单日期：</span>
						</td>
						<td width="29%">
							<span id="zddate"></span>
						</td>
					</tr>
				</table>
			</div>
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
		<%----------------支付物流方式---------------------------%>
		<div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
			id="newccode">
			<div class=" ">
				添加
			</div>
			<table>
				<tr>
					<td>
						支付物流方式名称：
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
