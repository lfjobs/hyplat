<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
		
		
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
      		
		<script type="text/javascript" src="<%=basePath%>js/ea/validate.js" ></script>
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/ea/validate.css" />
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/css.css"  />
		
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
		
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		
        <link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>js/tree/common/css/style.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		

		
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>	
		
		<script type="text/javascript"src="<%=basePath%>js/accifr/js/category.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
		
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/addOrEditProductDesign.js"></script>
			
<style type="text/css">
		

   
   #table3 input{
   
   height:20px;
   }
   
#querya{

cursor:pointer;
}
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

.bitian {
	color: red;
}
<!--

-表格拖动列宽 -->.bg table {
	font-size: 12px;
	color: #000000;
}

.bg td,th {
	font-size: 12px;
	color: #000000;
	text-align: center;
	line-height: 15px;
	height: 20px;
}

.bg td.tit,th.tit {
	background-color: #e2e2e2;
	color: #000;
	height: 17px;
	text-align: center;
	line-height: 15px;
}

.bg td.num,ht.num {
	background-color: #e2e2e2;
	color: #000;
	text-align: right;
	line-height: 15px;
	width: 30px;
	height: 22px;
}

.resizeDivClass {
	text-align: right;
	width: 3px;
	margin: 0px 0 0px 0;
	background: #fff;
	border: 0px;
	float: right;
	cursor: e-resize;
}

.auto_arrange {
	table-layout: fixed
}

.auto_arrange td,th {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}



  .querybtn{
  background:url(http://localhost:8080/hyplat/images/search16.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;

  
  }
  
    .addbtn{
  background:url(http://localhost:8080/hyplat/images/u15.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  vertial-align:middle;

  
  }
  
  .tdiv{
  
  width:97%;
  }
  
  .caz{
  
  display:none;
  }
  
  .menubtn{
  border:1px solid #FFFFFF;
  width:70px;
  background:#dbe5f1;
  color:#000000;
  font-size:10pt;
  font-weight:bold;
  vertial-align:middle;
  cursor:pointer;
  height:26px;
  }
  
  .grey{
  color:#aca899;
  
  }
  .text_tree{
  width:220px;
   background:#ffffff;
   overflow:auto;
  }
.baokx{
 display:none;
}
  



 
</style>
<style type="text/css">
	.treeTable
	{
		cursor: pointer;vertical-align: middle;
	}
	td#hierarchy{
		padding-right: 5px;
		padding-left: 5px;
	}
</style>
<script type="text/javascript">
  	var treeID = "organization20100909zijmxn7qze0000001402";
  	var treeNames="教务（生产）处";
  	var gongsiname="北京天太世统科技有限责任公司";
	var basePath = "http://localhost:8080/hyplat/";
	var token = 0;
	var pNumber="0";
	var search="";
	var pageNumber=null;
	var journalNum = "";
	var notoken = 0;
	var select=2;
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var type="";
	var status=""
	var jumptype = "";
	var xmtype="";
	var xmtypename="";
	var billID = "2015062605161800002";
	var summary="";
	var zorg = "";
	var zorgname = "";
	var cashierBillsID="";
	var  billsType = "";
	var zctype = "";
	var placeholder="";//占位符
	var selects =2;;//克隆行标识符
	var addOrEdit="add";//区别物品添加与修改操作
	var parentId="productpackaging2015062642D26ECYXS0000000042";
	var flexbutton="";
	var fiveClear = "";

	
	


	


	</script>
   <script type="text/javascript">
		
	function MouseDownToResize(obj) {
		setTableLayoutToFixed();
		obj.mouseDownX = event.clientX;
		obj.pareneTdW = obj.parentElement.offsetWidth;
		obj.pareneTableW = goodtable.offsetWidth;
		obj.setCapture();
	}

   
	function MouseMoveToResize(obj) {
		if (!obj.mouseDownX)
			return false;
		var newWidth = obj.pareneTdW * 1 + event.clientX * 1 - obj.mouseDownX;
		if (newWidth > 0) {
			obj.parentElement.style.width = newWidth;
			goodtable.style.width = obj.pareneTableW * 1 + event.clientX * 1
					- obj.mouseDownX;
		}
	}
	function MouseUpToResize(obj) {
		obj.releaseCapture();
		obj.mouseDownX = 0;
	}
	function setTableLayoutToFixed() {
		if (goodtable.style.tableLayout == 'fixed')
			return;
		var headerTr = goodtable.rows[0];
		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
		}

		for ( var i = 0; i < headerTr.cells.length; i++) {
			headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
		}
		goodtable.style.tableLayout = 'fixed';
	}
	
	
</script>
<script>
			//父页面必须用此方法，以供子页(弹出层返回数据)调用**********************
			function paret(e){
				//alert("I'm parent");
			  	//隐藏弹出层
			  	//$("#category").hide();
			}	
			
			$(function(){
                $(".divCTL").hover(function(){                    
                    $(".divFL").show();
                },function(){
                    $(".divFL").hide();
                });
            });
		</script>
	</head>
	<body style="background:#ffffff;border-top:5px solid #c5d9f1;">
	
		<div id="apDiv1"></div> 
		
	<!-- 	<form name="CostSheetForm" id="CostSheetForm" method="post"
			enctype="multipart/form-data">
			 -->
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
						
							<input type="button" id="saveCPDJInfo"  class="menubtn JQuerySubmitCPDJ" 
								value="保存" /><!-- <input type="button" class="menubtn print grey" name="button3" disabled="disabled"
								value="打印" /><input type="button" class="menubtn audit grey" name="button3" disabled="disabled"
								value="比价审核" /><input type="button"  class="menubtn newsheet grey" disabled="disabled"
								value="增加" /><input type="button"  class="menubtn updatesheet grey"  disabled="disabled"
								value="修改" /><input type="button"  class="menubtn deletesheet grey"  disabled="disabled"
								value="删除" /> <input type="button" class="menubtn addline" name="button3" 
								value="增行" />--><!-- <input type="button" class="menubtn deleteline" name="button3"
								value="删行" /> --><input type="button" class="menubtn" name="button3"
								value="帮助" /><input type="button" class="menubtn closewindow" 
								value="关闭" />
								<input type="hidden" value="" name="jumptype"/> 
							</nobr>	
						</td>
					</tr>
				</table>
		<form id="CostCPDJForm" name="CostCPDJForm" method="post" >
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
		  <div id="content1" style="width: 1100px;">
		  <table class="linetable" id="table3" cellpadding="10" cellspacing="5" width="1100">
			    <tr>
			   		<td align="center" colspan="8" id="titlestyle" ><span>产品价格设置</span></td>
			    </tr>
			</table>
		</div>
		<div id="priceList" style="margin-top: 5px;width: 1100px;" >
				<table class="table bg auto_arrange .goodtable2" id="goodtable1" >
									<tr>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 公司名称
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 部门
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>责任人
										</th>					
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>包装日期
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品编号
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品名称
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品单位
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品类型
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品数量
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>单价
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>金额
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品重量
										</th>
									</tr>
									
									<c:forEach var='arr' items="${pageForm.list}">
									<tr class="checkgoods">
										<!--公司名称 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input   id="id" type="hidden" name="productPackajiage.ppID" value="${arr[0]}"/>
												<input   id="comid"type="hidden" name="productPackajiage.compID" value="${arr[1]}"/>
												<input id="standard" readonly="readonly" name="companyname" style="width:70%;border:0px;"  class=" fou"  value="${arr[2] }"	/>
											</div>
										</td>
										<!-- 部门 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="standard" readonly="readonly" name="journalNum" style="width:70%;border:0px;"  class=" fou"  value="${arr[3] }"	/>
											</div>
										</td>
										<!-- 责任人 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="standard" readonly="readonly" name="staffname" style="width:70%;border:0px;"  class=" fou"  value="${arr[4] }"	/>
											</div>
										</td>
										<!--包装日期 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="standard" readonly="readonly" name="packagingdate" style="width:70%;border:0px;"  class=" fou"  value="${arr[5] }"	/>
											</div>
										</td>
										<!--产品编号 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="goodsid" style="width:70%;border:0px;"  class=" fou"  value="${arr[6] }"	/>
											</div>
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="goodsname" style="width:70%;border:0px;"  class=" fou"  value="${arr[7] }"	/>
											</div>
										</td>
										<!--产品单位-->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="variableID" style="width:70%;border:0px;"  class=" fou"  value="${arr[8] }"	/>
											</div>
										</td>
										<!-- 产品类型-->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="acquiescestandard" style="width:70%;border:0px;"  class=" fou"  value="${arr[16] }"	/>
											</div>
										</td>
										<!--产品数量 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="quantity" style="width:70%;border:0px;"  class=" fou"  value="${arr[10] }"	/>
											</div>
										</td>
										<!--单价 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="productDesign.standard" style="width:70%;border:0px;"  class=" fou"  value="${arr[17] }"	/>
											</div>
										</td>
										<!-- 金额 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="productDesign.standard" style="width:70%;border:0px;"  class=" fou"  value="${arr[18] }"	/>
											</div>
										</td>
										<!-- 产品数量 -->
										<td align="center" bgcolor="#FFFFFF">
											<div class="tdiv">
												<input id="standard" readonly="readonly" name="productDesign.standard" style="width:70%;border:0px;"  class=" fou"  value="${arr[11] }"	/>
												
											</div>
										</td>
									</tr> 
									</c:forEach>
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
				  <table width="100%" id="productPriceCategory" class="table bg auto_arrange .goodtable2"  style="display: none;">
					
					<input type="hidden" name="productPriceCategoryMap[0].category"  class="history" id="categoryOther" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" readonly="readonly" value="零售价" />
					<input type="hidden" name="productPriceCategoryMap[0].price"  class="history"  id="price" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" value="3" />
					<input type="hidden" name="productPriceCategoryMap[0].money" class="history" id="money" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"  value="6.00" />
					<input type="hidden" id="pcID" name="productPriceCategoryMap[0].pcID"   value="productPriceCategory2015062642D26ECYXS0000000043" />
                    <input type="hidden" id="pcKey" name="productPriceCategoryMap[0].pcKey"  value="4028e42c4e2ea1d4014e2ef3b6f70031"  />
					
				</table>
			</div>
			<div id="priceList" style="margin-top: 20px;width: 1100px;" >
				<table class="table bg auto_arrange .goodtable2" id="goodtable1" >
									<tr>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span>产品出厂价
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span>产品零售价
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>公司营销佣金
										</th>					
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>公司高级合伙人佣金
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>公司合伙人佣金
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>微分金店营销佣金
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>代理商VIP会员营销佣金
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>代理商会员营销佣金
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="http://localhost:8080/hyplat//images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>消费者积分
										</th>
									</tr>
									
									<tr class="checkgoods">
									
										<!--产品出厂价 -->
												<input type="hidden" id="ppID" name="productPackajiage.ppId"     value="${pageForm.list[0][0]}"/>
												<input type="hidden" id="compId" name="productPackajiage.compId"  value="${pageForm.list[0][1]}"/>
												<input type="hidden" id ="pjkey" name="productPackajiage.pckkey"  value="${productPackajiage.pckkey}"/>
												<input type="hidden" id ="pjkey1" name="productPackajiage.pckID"  value="${productPackajiage.pckID}"/>
											
												
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="ppccj" name="productPackajiage.ppccj" style="width:70%;border:0px;" class="notnull" 	 value="${productPackajiage.ppccj}"/>
											</div>
										</td>
										<!--产品零售价-->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="pplsj" name="productPackajiage.pplsj" style="width:70%;border:0px;" class="notnull"	 value="${productPackajiage.pplsj}"/>
											</div>
										</td>
										<!--公司营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="compyxyj" name="productPackajiage.compyxyj" style="width:70%;border:0px;"  class="notnull"  value="${productPackajiage.compyxyj}"	/>
											</div>
										</td>
										<!--公司高级合伙人营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="compgjhhryj" name="productPackajiage.compgjhhryj" style="width:70%;border:0px;"  class="notnull"	 value="${productPackajiage.compgjhhryj}"/>
											</div>
										</td>
										<!--公司合伙人营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="comphhryj" name="productPackajiage.comphhryj" style="width:70%;border:0px;"  class="notnull"	  value="${productPackajiage.comphhryj}"/>
											</div>
										</td>
										<!--微分金店营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="wfjdyj" name="productPackajiage.wfjdyj" style="width:70%;border:0px;"    class="notnull" value="${productPackajiage.wfjdyj}"	/>
											</div>
										</td>
										<!--代理商VIP会员营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="dlsVIPhyyj" name="productPackajiage.dlsVIPhyyj" style="width:70%;border:0px;" class="notnull"  value="${productPackajiage.dlsVIPhyyj}"	/>
											</div>
										</td>
										<!--代理商会员营销佣金 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="dlshyyj" name="productPackajiage.dlshyyj" style="width:70%;border:0px;"   class="notnull"  value="${productPackajiage.dlshyyj}"	/>
											</div>
										</td>
										<!--消费者积分 -->
										<td align="center" bgcolor="#FFFFFF">
											 <div class="tdiv">
												<input id="xfzjf" name="productPackajiage.xfzjf" style="width:70%;border:0px;" class="notnull"  value="${productPackajiage.xfzjf}"	/>
											</div>
										</td>
										
									</tr> 
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
				  <table width="100%" id="productPriceCategory" class="table bg auto_arrange .goodtable2"  style="display: none;">
					
					<input type="hidden" name="productPriceCategoryMap[0].category"  class="history" id="categoryOther" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" readonly="readonly" value="零售价" />
					<input type="hidden" name="productPriceCategoryMap[0].price"  class="history"  id="price" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" value="3" />
					<input type="hidden" name="productPriceCategoryMap[0].money" class="history" id="money" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"  value="6.00" />
					<input type="hidden" id="pcID" name="productPriceCategoryMap[0].pcID"   value="productPriceCategory2015062642D26ECYXS0000000043" />
                    <input type="hidden" id="pcKey" name="productPriceCategoryMap[0].pcKey"  value="4028e42c4e2ea1d4014e2ef3b6f70031"  />
					
				</table>
		</div>
		
       </div>
	</form>		
</body>
</html>
