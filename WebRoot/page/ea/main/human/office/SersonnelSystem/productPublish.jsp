<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@page import="hy.ea.bo.Company"%>
<%@page import="java.util.Date"%>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company) session.getAttribute("currentcompany");
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<title>产品发布</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
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
		
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>	
		
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
	line-height: 20px;
	height: 25px;
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
  background:url(<%=basePath%>images/search16.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;

  
  }
  
    .addbtn{
  background:url(<%=basePath%>images/u15.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;
  vertial-align:middle;

  
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
  	var treeID = "<%=session.getAttribute("organizationID")%>";
  	var treeNames="<%=session.getAttribute("organizationName")%>";
  	var gongsiname="<%=c.getCompanyName()%>";
	var basePath = "<%=basePath%>";
	var token = 0;
	var pNumber="${pageNumber}";
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var journalNum = "";
	var notoken = 0;
	var select=${fn:length(productPackagingList)+1};
	var treeid="";
	var treename="";
	var depotPID="";
	var depotID="";
	var typestring="${pptype}";
	var type="${type}";
	var status="${status}"
	var jumptype = "${jumptype}";
	var xmtype="${xmtype}";
	var xmtypename="${param.xmtypename}";
	var billID = "${billID}";
	var summary="${summary}";
	var zorg = "${zorg}";
	var zorgname = "${zorgname}";
	var cashierBillsID="${cashierBills.cashierBillsID}";
	var  billsType = "${billsType}";
	var zctype = "${zctype}";
	var placeholder="";//占位符
	var selects =${fn:length(productPriceCategoryList)+1};;//克隆行标识符
	var addOrEdit="add";//区别物品添加与修改操作
	var parentId="${productDesign.ppID}";
	var flexbutton="${flexbutton}";
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
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
							<input type="button"  class="menubtn JQuerySubmitfb" value="保存" />
							<input type="button" class="menubtn" name="button3" value="帮助" />
							<input type="button" class="menubtn closewindow" value="关闭" />
							<input type="hidden" value="${jumptype}" name="jumptype"/> 
						</nobr>	
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
		  <div id="content1" style="width: 1100px;">
		  <table class="linetable" id="table3" cellpadding="10" cellspacing="5" width="1100">
			    <tr>
			   		<td align="center" colspan="8" id="titlestyle" ><span>产品发布管理</span></td>
			    </tr>
			    <tr>
			    	<td align="right" width="100">项目分类：</td>
			   		<td>
						<input type="text" id="xmtypename" readonly class="inputbottom notnull" style="width: 100px;"
						name="productDesign.xmtypename" value="${productDesign.xmtypename }" />
						<input type="hidden" id="xmtype" name="productDesign.xmtype" value="${productDesign.xmtype }"/>
					</td>
					<td align="right" width="100">产品行业：</td>
					<td id="dept">
						<select name="prdCategory" id="prdCategory"   class="inputbottom notnull"   style="width:150px;">	
							<c:if test="${ccode!=null}">
								<option value="${ccode.codeID }">(${ccode.codeSn})${ccode.codeValue}</option>		
							</c:if>									
						</select>	
					</td>
					<td height="20" align="right" >产品编号：</td>
					<td >
						<input  id="goodsNum" class="inputbottom notnull" name="productDesign.goodsNum" style="width: 180px" value="${productDesign.goodsNum}" readonly="readonly"/>
					</td>
					<td align="right" width="100">产品名称：</td>
					<td>
						<input type="text" id="goodsName" readonly class="inputbottom notnull" style="width: 160px;"
						name="productDesign.goodsName" value="${productDesign.goodsName }" />
						<input type="hidden" id="goodsID" name="productDesign.goodsID" value="${productDesign.goodsID }"/>
					</td>
					<td style="display: none">
						<input type="hidden" id="ppKey" name="productDesign.ppKey"  value="${productDesign.ppKey}" />
						<input type="hidden" id="ppID" name="productDesign.ppID"  value="${productDesign.ppID}"  />
						<input type="hidden" id="parentId" name="productDesign.parentId" value="${productDesign.parentId}"  />
					</td>
				</tr>
			</table>
		</div>
		<div id="aadTree1" style="overflow:auto;z-index:99;display:yes; border: 0px solid #000000;"></div> 
		<div id="Layer1" style="width: 1100px;height: auto;">
			<table class="table bg auto_arrange .goodtable2" id="goodtable" style="width: 100%;">
									<tr>
										<th align="left" bgcolor="#E4F1FA"  style="word-break:keep-all;" >
											
										</th>
										
										<th align="center" bgcolor="#E4F1FA" >
											<span class="xx">*</span> 品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 </span>  <span class="xx">*</span>品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>规格
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>类型
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>重量(kg)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="xx">*</span>数量
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>单价(￥)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
										  <span class="xx">*</span>金额(￥)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>LOGO
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 <span class="xx">*</span>主题图
										</th>
										
									</tr>
									
									<c:forEach var='c' items="${productPackagingList}" varStatus="status">
											<tr id="kelong${status.index + 2 }" class="checkgoods">
												<!-- 层级 -->
													
												<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
													<img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="showOrHideChildren treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable">${c.goodsName }</span>
												</td>
												<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="productPackagingMap[${status.index + 2 }].goodsID" id="goodsID" value="${c.goodsID }"/>
													<input id="goodsNum" style="width:90%;border:0px;"  value="${c.goodsNum }" readonly="readonly"/>
												</td>
												<!-- 产品名称 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="goodsName" name="productPackagingMap[${status.index + 2 }].goodsName" style="width:90%;border:0px;"  readonly="readonly"  value="${c.goodsName }"/>
												</td>
												<!-- 规格 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="standard" name="productPackagingMap[${status.index + 2 }].standard" style="width:90%;border:0px;" readonly="readonly" value="${c.standard}"/>
												</td>
												<!-- 类型 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="typeID" name="productPackagingMap[${status.index + 2 }].type" style="width:90%;border:0px;"  readonly="readonly"  value="${c.type }"
														/>
												</td>
												<!-- 重量 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="weight" name="productPackagingMap[${status.index + 2 }].weight" style="width:90%;border:0px;" readonly="readonly" value="${c.weight }"
														/>
												</td>
												<!-- 数量 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="quantity" name="productPackagingMap[${status.index + 2 }].quantity" style="width:90%;border:0px;"  readonly="readonly"  value="${c.quantity }"
														/>
												</td>
												<!-- 单价 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="price" name="productPackagingMap[${status.index + 2 }].price" style="width:90%;border:0px;"  readonly="readonly"  value="${c.price }"
														/>
												</td>
												<!-- 金额 -->
												<td align="center" bgcolor="#FFFFFF">
													<input id="money" name="productPackagingMap[${status.index + 2 }].money" style="width:90%;border:0px;"  readonly="readonly"  value="${c.money }"
														/>
												</td>
												<!-- LOGO -->
												<td align="center" bgcolor="#FFFFFF">
													<c:if test="${c.logo==null||c.logo==''}">
														<img id="logo" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${c.logo!=null&&c.logo!=''}">
														<img id="logo"  class="preview"  src="<%=basePath%>${c.logo}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>
													<input  type="hidden" id="logo" name="productPackagingMap[${status.index + 2 }].logo" style="width:90%;border:0px;"  value="${c.logo }" />
												</td>
												<!-- 主题图片 -->
												<td align="center" bgcolor="#FFFFFF">
													<c:if test="${c.image==null||c.image==''}">
														<img id="image" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${c.image!=null&&c.image!=''}">
														<img id="image"  class="preview"  src="<%=basePath%>${c.image}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>
													<input  type="hidden" id="image" name="productPackagingMap[${status.index + 2 }].image" style="width:90%;border:0px;"   value="${c.image }" />	
												</td>
												<td style="display: none;">
														<input type="hidden" id="ppKey" name="productPackagingMap[${status.index + 2 }].ppKey"  value="${c.ppKey }"/>
														<input type="hidden" id="ppID" name="productPackagingMap[${status.index + 2 }].ppID"  value="${c.ppID }"/>
														<input type="hidden" id="parentId" name="productPackagingMap[${status.index + 2 }].parentId"  value="${c.parentId }"/>
														<input type=hidden id="sorting" name="productPackagingMap[${status.index + 2 }].sorting"    value="${c.sorting }">
												</td>
											</tr>
									</c:forEach>
									
									
									
								  <tr id="kelong" style="display:none;">
										
										<!-- 层级 -->
											
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											<img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="showOrHideChildren treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable"></span>
										</td>
										
										<!-- 产品编号 -->
										<td align="center" bgcolor="#FFFFFF">
										<input type="hidden" name="goodsID" id="goodsID" />
											<input id="goodsNum" name="goodsNum" style="width:90%;border:0px;"  readonly="readonly"/>
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="goodsName" name="goodsName" style="width:90%;border:0px;"  readonly="readonly"/>
										</td>
										<!-- 规格 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="standard" name="standard" style="width:90%;border:0px;"  readonly="readonly"/>
										</td>
										<!-- 类型 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="typeID" name="type" style="width:90%;border:0px;"  readonly="readonly"/>
										</td>
										<!-- 重量 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="weight" name="weight" style="width:90%;border:0px;"  readonly="readonly" />
										</td>
										<!-- 数量 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="quantity" name="quantity" style="width:90%;border:0px;"  readonly="readonly" />
										</td>
										<!-- 单价 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="price" name="price" style="width:90%;border:0px;"  readonly="readonly"/>
										</td>
										<!-- 金额 -->
										<td align="center" bgcolor="#FFFFFF">
											<input id="money" name="money" style="width:90%;border:0px;"  readonly="readonly" />
										</td>
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
											<img id="logo"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="logo" name="logo" style="width:90%;border:0px;"  class=" fou "  />
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
											<img id="image"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="image" name="image" style="width:90%;border:0px;"  class=" fou "  />
										</td>
										<td style="display: none;">
												<input type="hidden" id="ppKey" name="ppKey"  />
												<input type="hidden" id="ppID" name="ppID"  />
												<input type="hidden" id="parentId" name="parentId"  />
												<input type="hidden" id="sorting" name="sorting"   >
										</td>
									</tr>
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
		</div>
		<form name="profitForm" id="profitForm" method="post" enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div id="priceList" style="margin-top: 15px;width: 1100px;" >
				<table class="table bg auto_arrange .goodtable2" id="goodtable1" style="width: 100%">
									<tr>
										<th align="center" bgcolor="#E4F1FA" >
											 所属中联园区
										</th>
										<th align="center" bgcolor="#E4F1FA" >
										   企业商城	
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											微分金商城
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											微信显示
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											微分金店
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											 网站商城
										</th>
										
										<th align="center" bgcolor="#E4F1FA" >
											 其他发布
										</th>
									
									</tr>
									<tr class="checkgoods">
										<!-- 所属园区 -->
										<td align="center" bgcolor="#FFFFFF">
											<input  name="productDesign.weiDianType"  id="weiDianType"  style="width:90%;border:0px;"  readonly="readonly"  value="${productDesign.weiDianType}"
												/>
										</td>
										<!-- 企业商城 -->
										<td align="center" bgcolor="#FFFFFF">
											<select name="productDesign.ppEnterprise"  style="width: 60px;">
												<option value="01" ${productDesign.ppEnterprise=='1'?'selected="selected"':''} />是</option>
												<option value="02" ${productDesign.ppEnterprise=='2'?'selected="selected"':''} />否</option>
											</select>
										</td>
										<!-- 微分金商场发布-->
										<td align="center" bgcolor="#FFFFFF" style="width:90px ;border:0px;">
										<select name="productDesign.ppDifferential"  style="width: 60px;">
												<option value="01" ${productDesign.ppDifferential=='1'?'selected="selected"':''} />是</option>
												<option value="02" ${productDesign.ppDifferential=='2'?'selected="selected"':''} />否</option>
											</select>
										</td>
										<!-- 类型 -->
										
										<!-- 微信发布-->
										<td align="center" bgcolor="#FFFFFF">
										<select name="productDesign.showweixin"  style="width: 60px;">
												<option value="01" ${productDesign.showweixin=='01'?'selected="selected"':''} />是</option>
												<option value="02" ${productDesign.showweixin=='02'?'selected="selected"':''} />否</option>
											</select>
										</td>
										<!-- 微分金店发布 -->
										<td align="center" bgcolor="#FFFFFF">
										<select name="productDesign.ppDifferentialshop"  style="width: 60px;">
												<option value="01" ${productDesign.ppDifferentialshop=='1'?'selected="selected"':''} />是</option>
												<option value="02" ${productDesign.ppDifferentialshop=='2'?'selected="selected"':''} />否</option>
											</select>
										</td>
										<!-- 网站发布 -->
										<td align="center" bgcolor="#FFFFFF">
										<select name="productDesign.ppWeb"  style="width: 60px;">
												<option value="01" ${productDesign.ppWeb=='1'?'selected="selected"':''} />是</option>
												<option value="02" ${productDesign.ppWeb=='2'?'selected="selected"':''} />否</option>
											</select>
										</td>
										<%--<td align="center" bgcolor="#FFFFFF">
											<s:select  list="%{#request.priceManageList}" cssStyle="margin-left: 2px;width: 95%;margin-right: 5px;" id="categoryOther" 
											listKey="codeValue" listValue="codeValue" 
											name="category" theme="simple"></s:select>
										</td> --%>
										<!--其他发布 -->
										<td align="center" bgcolor="#FFFFFF">
										<select name="productDesign.ppOther"  style="width: 60px;">
												<option value="1" ${productDesign.ppOther=='1'?'selected="selected"':''} />是</option>
												<option value="2" ${productDesign.ppOther=='2'?'selected="selected"':''} />否</option>
											</select>
										</td>
																		</tr> 
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
				  <table width="100%" id="productPriceCategory" class="table bg auto_arrange .goodtable2"  style="display: none;">
					
					<input type="hidden" id="pcID" name="productPriceCategoryMap[0].pcID"   value="${  productPriceCategoryList[0].pcID}" />
                    <input type="hidden" id="pcKey" name="productPriceCategoryMap[0].pcKey"  value="${  productPriceCategoryList[0].pcKey}"  />
					
				</table>
		</div>
			<div id="Profitshare" style="margin-top: 15px;width: 1100px;">
			<%-- <div style="width: 100px;height: 25px; font-size: 14px;color:#15428b ;font-weight: bold">分利机制</div>
			<table class="table bg auto_arrange .goodtable2" id="goodtable1" style="width: 100%;">
				<tr>
					<th align="center" bgcolor="#E4F1FA" >
						代理商
					</th>
					<th align="center" bgcolor="#E4F1FA" >
						 微分金店
					</th>
					<th align="center" bgcolor="#E4F1FA" >
						公司
					</th>
					<th align="center" bgcolor="#E4F1FA" >
						合伙人
					</th>
					<th align="center" bgcolor="#E4F1FA" >
						 营销员
					</th>
					<th align="center" bgcolor="#E4F1FA" >
						 消费者积分
					</th>
					
				</tr>
				<tr class="checkgoods">
					<!-- 代理商 -->
					<td align="center" bgcolor="#FFFFFF">
						<input  name="profitshare.agent"  id="agent"  style="width:90%;border:0px;" value="${profitshare.agent }"/>
					</td>
					<!--  微分金店 -->
					<td align="center" bgcolor="#FFFFFF" style="width:90px ;border:0px;" >
					<div class="tdiv">
						<input id="shop" name="profitshare.shop" style="width:90%;border:0px;" value="${profitshare.shop }"/>
					</div>
					</td>
					<!--公司-->
					<td align="center" bgcolor="#FFFFFF">
					<div class="tdiv">
						<input id="company" name="profitshare.company" style="width:90%;border:0px;" value="${profitshare.company }"/>
					</div>
					</td>
					<!-- 合伙人 -->
					<td align="center" bgcolor="#FFFFFF">
					<div class="tdiv">
						<input id="partner" name="profitshare.partner" style="width:90%;border:0px;" value="${profitshare.partner }"/>
					</div>
					</td>
					<!-- 营销员 -->
					<td align="center" bgcolor="#FFFFFF">
					<div class="tdiv">
						<input id="salesman" name="profitshare.salesman" style="width:90%;border:0px;" value="${profitshare.salesman }"/>
					</div>
					</td>
					<!-- 消费者积分 -->
					<td align="center" bgcolor="#FFFFFF">
					<div class="tdiv">
						<input id="integral" name="profitshare.integral" style="width:90%;border:0px;" value="${profitshare.integral }"/>
					</div>
					</td>
				</tr> 
			</table>
		</div> --%>
		<input type="hidden" name="profitshare.ppid"  value="${productDesign.ppID}"/>
		
							
		<div style="margin-top:10px;">
		备注：<input type="text" id="remark" name="profitshare.remark" class="inputbottom" style="width:96%;" value="${profitshare.remark}">
		<p>责任人：<input type="text" class="inputbottom" size="15" value="${staff.staffName}"/>&nbsp;&nbsp;&nbsp;发布日期：<input type="text" name="profitshare.publishDate" class="inputbottom" id="PackagingDate" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" size="20" >
		</p>
		</div>
		</form>		
       </div> 
		<!-- 项目预算添加表单结束 -->
		<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
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
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
								<input type="hidden" id="clicktr" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页 </a>
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
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>		
</body>
</html>

