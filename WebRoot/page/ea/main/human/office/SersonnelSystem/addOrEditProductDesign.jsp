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
		<title>项目产品设计-新增与编辑</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
		<%-- <link rel="stylesheet" type="text/css"  href="<%=basePath%>/css/ea/staff.css" /> --%>
		
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
		
<%-- 		<link  rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript" src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" ></script> --%>
		
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
	var flexbutton="";
	var fiveClear = "${fiveClear}";

	
	


	


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
		<!-- 项目预算添加表单开始 -->
		<form name="CostSheetForm" id="CostSheetForm" method="post"
			enctype="multipart/form-data">
			
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
							<input type="button"  class="menubtn JQuerySubmitgd" 
								value="保存" /><!-- <input type="button" class="menubtn print grey" name="button3" disabled="disabled"
								value="打印" /><input type="button" class="menubtn audit grey" name="button3" disabled="disabled"
								value="比价审核" /><input type="button"  class="menubtn newsheet grey" disabled="disabled"
								value="增加" /><input type="button"  class="menubtn updatesheet grey"  disabled="disabled"
								value="修改" /><input type="button"  class="menubtn deletesheet grey"  disabled="disabled"
								value="删除" /> --><input type="button" class="menubtn addline" name="button3" 
								value="增行" /><!-- <input type="button" class="menubtn deleteline" name="button3"
								value="删行" /> --><input type="button" class="menubtn" name="button3"
								value="帮助" /><input type="button" class="menubtn closewindow" 
								value="关闭" />
								<input type="hidden" value=
								"${jumptype}" name="jumptype"/> 
							</nobr>	
						</td>
					</tr>
				</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
		  <div id="content1" style="width: 1100px;">
		  <table class="linetable" id="table3" cellpadding="10" cellspacing="5" width="1100">
			    <tr>
			   		<td align="center" colspan="8" id="titlestyle" ><span>项目产品设计管理</span></td>
			    </tr>
			    <tr>
			    	<td align="right" width="100"><span class="xx">*</span>项目分类：</td>
			   		<td>
						<input type="text" id="xmtypename" readonly class="inputbottom notnull" style="width: 100px;"
						name="productDesign.xmtypename" value="${productDesign.xmtypename }" />
						<input type="hidden" id="xmtype" name="productDesign.xmtype" value="${productDesign.xmtype }"/>
						<input type="button" class="btncon projectbtn" />
					</td>
					<td align="right" width="100"><span class="xx">*</span>产品行业：</td>
					<td id="dept">
						<select name="prdCategory" id="prdCategory"   class="inputbottom notnull"   style="width:140px;">	
							<c:if test="${ccode!=null}">
								<option value="${ccode.codeID }">(${ccode.codeSn})${ccode.codeValue}</option>		
							</c:if>									
						</select>	
						<input type="button" class="btncon xzcpfl" />
					</td>
					<td height="20" align="right" ><span class="xx">*</span>产品编号：</td>
					<td >
						<input  id="goodsNum" class="inputbottom notnull" name="productDesign.goodsNum" style="width: 180px" value="${productDesign.goodsNum}" />
						<input  type="hidden" name="productDesign.fiveClear"  value="${fiveClear}" />
					</td>
					<td align="right" width="100"><span class="xx">*</span>产品名称：</td>
					<td>
						<input type="text" id="goodsName" readonly class="inputbottom notnull" style="width: 160px;"
						name="productDesign.goodsName" value="${productDesign.goodsName }" />
						<input type="hidden" id="goodsID" name="productDesign.goodsID" value="${productDesign.goodsID }"/>
						<input type="button" class="btncon productSelect" />
					</td>
					<%-- <td  rowspan="4" align="right">
						<div contenteditable="" id="post_article_content" style="border:1px solid #000; width: 252px;height: 80px;overflow: hidden;background: url('<%=basePath%>${productDesign.image }') no-repeat 0 0 ;background-size:252px 80px;" >
						
						</div>
						<table id="upload_content" class="swfupload_main"   style="display:none;ba" width="100%">
							<tbody id="divFileProgressContainer">
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<input type="hidden" id="hidIdList" name="hidIdList" />
									</td>
								</tr>
							</tfoot>
						</table>
					</td> --%>
					<input type="hidden" id="ppKey" name="productDesign.ppKey"  value="${productDesign.ppKey}" />
																	<input type="hidden" id="ppID" name="productDesign.ppID"  value="${productDesign.ppID}"  />
																	<input type="hidden" id="parentId" name="productDesign.parentId" value="${productDesign.parentId}"  />
				</tr>
			</table>
		</div>
		<div id="aadTree1" style="overflow:auto;z-index:99;display:yes; border: 0px solid #000000;"></div> 
		<div id="Layer1" style="width: 1100px;">
			<table class="table bg auto_arrange .goodtable2" id="goodtable" >
									<tr>
										<th align="left" bgcolor="#E4F1FA"  style="word-break:keep-all;" >
											<span 
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" />&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span> 
										</th>
										<%-- <th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 序号
										</th> --%>
										<%-- <th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 品名编号
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>品名名称
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>规格
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>类型
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>重量(kg)
										</th>
										<--%>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>数量
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>单价(￥)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>金额(￥)
										</th> 
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>LOGO
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>主题图
										</th>
										<th align="center"  bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="5"
													height="14" /> </span> 操作
										</th>
									</tr>
									
									<c:forEach var='c' items="${productPackagingList}" varStatus="status">
											<tr id="kelong${status.index + 2 }" class="checkgoods">
												<!-- 层级 -->
													
												<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
													<img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="showOrHideChildren treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable">${c.goodsName }</span>
												</td>
												<!-- 序号 -->
												<%-- <td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<select id="sorting" name="productPackagingMap[${status.index + 2 }].sorting"    class=" fou" style="width:100%;">
														<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option>
														<option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
														<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option>
														<c:if test="${c.sorting!=null}">
														<option value="${c.sorting}" selected="selected" />${c.sorting}</option>		
														</c:if>	
													</select>	
													
												</div>
												</td> --%>
												<!-- 产品编号 -->
												
												<td align="center" bgcolor="#FFFFFF" style="display: none;">
												<input type="hidden" name="productPackagingMap[${status.index + 2 }].goodsID" id="goodsID" value="${c.goodsID }"/>
													<div class="tdiv">
													<input id="goodsNum" name="productPackagingMap[${status.index + 2 }].goodsNum" style="width:70%;border:0px;"  class="querys fou" value="${c.goodsNum }"/>
													&nbsp;<input type="btn" class="shuju querybtn caz" /></div>
												</td>
												<!-- 产品名称 -->
												<td align="center" bgcolor="#FFFFFF" style="display: none;">
												<div class="tdiv">
													<input id="goodsName" name="productPackagingMap[${status.index + 2 }].goodsName" style="width:70%;border:0px;"  class="querys fou"  value="${c.goodsName }"
														/>&nbsp;<input type="btn" class="shuju querybtn caz" />
												</div>
												</td>
												<%-- <!-- 规格 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="standard" name="productPackagingMap[${status.index + 2 }].standard" style="width:70%;border:0px;"  class=" fou" value="${c.standard}"
														/>
												</div>
												</td>
												<!-- 类型 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="typeID" name="productPackagingMap[${status.index + 2 }].type" style="width:70%;border:0px;"  class=" fou" value="${c.type }"
														/>
												</div>
												</td>
												<!-- 重量 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="weight" name="productPackagingMap[${status.index + 2 }].weight" style="width:90%;border:0px;"  class=" fou"  value="${c.weight }"
														/>
												</div>
												</td>
												 --%>
												<!-- 数量 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="quantity" name="productPackagingMap[${status.index + 2 }].quantity" style="width:90%;border:0px;"  class="  jisuan"  value="${c.quantity }"
														/>
												</div>
												</td>
												<!-- 单价 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="price" name="productPackagingMap[${status.index + 2 }].price" style="width:90%;border:0px;"  class=" fou jisuan"  value="${c.price }"
														/>
												</div>
												</td>
												<!-- 金额 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<input id="money" name="productPackagingMap[${status.index + 2 }].money" style="width:90%;border:0px;"  class=" fou jisuan"  value="${c.money }"
														/>
												</div>
												</td>
												<!-- LOGO -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<c:if test="${c.logo==null||c.logo==''}">
														<img id="logo" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${c.logo!=null&&c.logo!=''}">
														<img id="logo"  class="preview"  src="<%=basePath%>${c.logo}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>
													<input  type="hidden" id="logo" name="productPackagingMap[${status.index + 2 }].logo" style="width:90%;border:0px;"  class=" fou "  value="${c.logo }" />
												</div>
												</td>
												<!-- 主题图片 -->
												<td align="center" bgcolor="#FFFFFF">
												<div class="tdiv">
													<c:if test="${c.image==null||c.image==''}">
														<img id="image" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${c.image!=null&&c.image!=''}">
														<img id="image"  class="preview"  src="<%=basePath%>${c.image}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>
													<input  type="hidden" id="image" name="productPackagingMap[${status.index + 2 }].image" style="width:90%;border:0px;"  class=" fou "  value="${c.image }" />	
												</div>
												</td>
												
												<td align="center" bgcolor="#FFFFFF">
														<img src="<%=basePath%>images/but.png" class="addChild" style="cursor: pointer;"
															 title="增加下级" />
															 
															 
														<img src="<%=basePath%>/images/r_8_10.gif"  title="编辑"  class="edit" style="cursor: pointer;"/>
														<img src="<%=basePath%>images/r_8_08.gif" class="delete"   style="cursor: pointer;"
															 title="删除"  />
														<img src="<%=basePath%>images/sy.png" class="up"   style="cursor: pointer;"
															 title="上移"  />	 
														<img src="<%=basePath%>images/xy.png" class="down"   style="cursor: pointer;"
															 title="下移"  />
														<input type="hidden" id="ppKey" name="productPackagingMap[${status.index + 2 }].ppKey"  value="${c.ppKey }"/>
														<input type="hidden" id="ppID" name="productPackagingMap[${status.index + 2 }].ppID"  value="${c.ppID }"/>
														<input type="hidden" id="parentId" name="productPackagingMap[${status.index + 2 }].parentId"  value="${c.parentId }"/>
														<input type="hidden" id="sorting" name="productPackagingMap[${status.index + 2 }].sorting"    value="${c.sorting }">
												</td>
											</tr>
									</c:forEach>
									
									
									<tr id="kelong1" class="checkgoods">
										<!-- 层级 -->
											
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											 <img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable"></span> 
										</td>
										<!-- 序号 -->
										<%-- <td align="center" bgcolor="#FFFFFF">
										<div class="tdiv" style="width: 50px;">
											<select id="sorting" name="sorting"    class=" fou" style="width:100%;">
													<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option>
													<option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
													<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option>
											</select>	
										</div>
										</td> --%>
										<!-- 产品编号 -->
										
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
										<input type="hidden" name="goodsID" id="goodsID" />
											<div class="tdiv">
											<input id="goodsNum" name="goodsNum" style="width:70%;border:0px;"  class="querys fou" />
											&nbsp;<input type="btn" class="shuju querybtn caz" /></div>
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
										<div class="tdiv">
											<input id="goodsName" name="goodsName" style="width:70%;border:0px;"  class="querys fou" 
												/>&nbsp;<input type="btn" class="shuju querybtn caz" />
										</div>
										</td>
										<!-- 规格 -->
										<!-- <td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="standard" name="standard" style="width:70%;border:0px;"  class=" fou" 
												/>
										</div>
										</td>
										类型
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="typeID" name="type" style="width:70%;border:0px;"  class=" fou" 
												/>
										</div>
										</td>
										重量
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="weight" name="weight" style="width:90%;border:0px;"  class=" fou" 
												/>
										</div>
										</td>
										 -->
										<%-- 数量--%>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="quantity" name="quantity" style="width:90%;border:0px;"  class=" fou jisuan" 
												/>
										</div>
										</td>
										<%--单价--%>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="price" name="price" style="width:90%;border:0px;"  class=" fou jisuan" 
												/>
										</div>
										</td>
										<%-- 金额--%>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="money" name="money" style="width:90%;border:0px;"  class=" fou jisuan"  
												/>
										</div>
										</td>
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="logo"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="logo" name="logo" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="image"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="image" name="image" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										
										<td align="center" bgcolor="#FFFFFF">
												<img src="<%=basePath%>images/but.png" class="addChild" style="cursor: pointer;"
													 title="增加下级" />	
													
												<img src="<%=basePath%>/images/r_8_10.gif"  title="编辑"  class="edit" style="cursor: pointer;"/>
												<img src="<%=basePath%>images/r_8_08.gif" class="delete"   style="cursor: pointer;"
													 title="删除"  />
												<img src="<%=basePath%>images/sy.png" class="up"   style="cursor: pointer;"
															 title="上移"  />	 
												<img src="<%=basePath%>images/xy.png" class="down"   style="cursor: pointer;"
															 title="下移"  />	 
												<input type="hidden" id="ppKey" name="ppKey"  />
												<input type="hidden" id="ppID" name="ppID"  />
												<input type="hidden" id="parentId" name="parentId"  />
												<input type="hidden" id="sorting" name="sorting"   >
										</td>
									</tr> 
									
								  <tr id="kelong" style="display:none;">
										
										<!-- 层级 -->
											
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											<img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="showOrHideChildren treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable"></span>
										</td>
										<!-- 序号 -->
										<%-- <td align="center" bgcolor="#FFFFFF">
										<div class="tdiv" style="width: 50px;">
											<select id="sorting" name="sorting"    class=" fou" style="width:100%;">
													<option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option>
													<option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
													<option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option>
											</select>
										</div>
										</td> --%>
										<!-- 产品编号 -->
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
										<input type="hidden" name="goodsID" id="goodsID" />
											<div class="tdiv">
											<input id="goodsNum" name="goodsNum" style="width:70%;border:0px;"  class="querys fou"
												/>&nbsp;<input type="btn" class="shuju querybtn caz" /></div>
										</td>
										<!-- 产品名称 -->
										<td align="center" bgcolor="#FFFFFF" style="display: none;">
										<div class="tdiv">
											<input id="goodsName" name="goodsName" style="width:70%;border:0px;"  class="querys fou" 
												/>&nbsp;<input type="btn" class="shuju querybtn caz" />
										</div>
										</td>
										<!-- 规格 -->
										<!-- <td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="standard" name="standard" style="width:70%;border:0px;"  class=" fou" 
												/>
										</div>
										</td>
										类型
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="typeID" name="type" style="width:70%;border:0px;"  class=" fou" 
												/>
										</div>
										</td>
										重量
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="weight" name="weight" style="width:90%;border:0px;"  class=" fou" 
												/>
										</div>
										
										</td>
										 -->
								<!-- 	数量 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="quantity" name="quantity" style="width:90%;border:0px;"  class=" fou jisuan" 
												/>
										</div>
										</td>
									<!-- 	单价 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="price" name="price" style="width:90%;border:0px;"  class=" fou jisuan" 
												/>
										</div>
										</td>
										<!-- 金额 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="money" name="money" style="width:90%;border:0px;"  class=" fou jisuan" 
												/>
										</div>
										</td>
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="logo"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="logo" name="logo" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="image"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="image" name="image" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<td align="center" bgcolor="#FFFFFF">
												<img src="<%=basePath%>images/but.png" class="addChild" style="cursor: pointer;"
													 title="增加下级" />
												<img src="<%=basePath%>/images/r_8_10.gif"  title="编辑"  class="edit" style="cursor: pointer;"/>
												<img src="<%=basePath%>images/r_8_08.gif" class="delete" style="cursor: pointer;"
													 title="删除"  />
												<img src="<%=basePath%>images/sy.png" class="up"   style="cursor: pointer;"
															 title="上移"  />	 
												<img src="<%=basePath%>images/xy.png" class="down"   style="cursor: pointer;"
															 title="下移"  />	 
												<input type="hidden" id="ppKey" name="ppKey"  />
												<input type="hidden" id="ppID" name="ppID"  />
												<input type="hidden" id="parentId" name="parentId"  />
												<input type="hidden" id="sorting" name="sorting"   >
										</td>
									</tr>
									<tr style="display:none;"><td id="line"></td></tr>
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
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 商品类别
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span> <span class="xx">*</span> 部门分类
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>产品类型
										</th>					
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>规格
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>类型
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>重量(kg)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>数量
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>价格类别
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>单价(￥)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>金额(￥)
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>LOGO
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												onMouseDown="MouseDownToResize(this);"
												onMouseMove="MouseMoveToResize(this);"
												onMouseUp="MouseUpToResize(this);"><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>主题图
										</th>
									</tr>
									<tr class="checkgoods">
										<!-- 所属店铺 -->
										<td align="center" bgcolor="#FFFFFF">
											 <s:select list="%{#request.proType}" listKey="codeID" listValue="codeValue" 
											 name="productDesign.weiDianType" cssStyle="width:100%;" id="weiDianType" class="inputbottom" ></s:select>
										
										</td>
										
										<!-- 部门分类 -->
										<td align="center" bgcolor="#FFFFFF" style="width:70px ;border:0px;">
										<div class="tdiv">
											<select name="productDesign.departmentState"  id="departmentState" class="inputbottom" style="width: 100px;"   >
													 <option value="" selected="selected"/>请选择</option>
													 <option value="人事项目"/>人事项目</option>
													 <option value="办公室项目"/>办公室项目</option>
													 <option value="财务项目"/>财务项目</option>
													 <option value="生产项目"/>生产项目</option>
													 <option value="营销项目"/>营销项目</option>
													 <c:if test="${productDesign.departmentState!=null}">
														<option value="${productDesign.departmentState}" selected="selected" />${productDesign.departmentState}</option>		
													</c:if>	
											</select>	
										</div>
										</td>
										<!-- 项目分类 -->
										<td align="center" bgcolor="#FFFFFF" style="width:70px ;border:0px;">
										<div class="tdiv">
											<select name="productDesign.projectType"  id="projectType" class="inputbottom" style="width: 100px;"   >
													 <option value="01" selected="selected"/>一般产品</option>
													 <option value="02"/>供应商</option>
													 <option value="03"/>代理商</option>
													 <option value="04"/>微分金店</option>
													 <option value="05"/>合伙人</option>
													 
											</select>	
										</div>
										</td>
										<!-- 规格 -->
										<td align="center" bgcolor="#FFFFFF" style="width:70px ;border:0px;">
										<div class="tdiv">
											<input id="standard" name="productDesign.standard" style="width:70%;border:0px;"  class=" fou"  value="${productDesign.standard}"
												/>
										</div>
										</td>
										<!-- 类型 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="type" name="productDesign.type" style="width:70%;border:0px;"  class=" fou" value="${productDesign.type}"
												/>
										</div>
										</td>
										<!-- 重量 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="weight" name="productDesign.weight" style="width:90%;border:0px;"  class=" fou"  value="${productDesign.weight}"
												/>
										</div>
										</td>
										<!-- 数量 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="quantity" name="productDesign.quantity" style="width:90%;border:0px;"  class=" fou jisuan notnull"  value="${productDesign.quantity}"
												/>
										</div>
										</td>
										<!-- 价格类别 -->
										<td align="center" bgcolor="#FFFFFF">
											<s:select  list="%{#request.priceManageList}" cssStyle="margin-left: 2px;width: 95%;margin-right: 5px;" id="categoryOther" 
											listKey="codeValue" listValue="codeValue" 
											name="category" theme="simple"></s:select>
										</td>
										<!-- 单价 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="price" name="productDesign.price" style="width:90%;border:0px;"  class=" fou jisuan notnull"  value="${productDesign.price}"
												/>
										</div>
										</td>
										<!-- 金额 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<input id="money" name="productDesign.money" style="width:90%;border:0px;"  class=" fou notnull"  value="${productDesign.money}"
												/>
										</div>
										</td>
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<c:if test="${productDesign.logo==null||productDesign.logo==''}">
														<img id="logo" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${productDesign.logo!=null&&productDesign.logo!=''}">
														<img id="logo"  class="preview"  src="<%=basePath%>${productDesign.logo}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											</c:if>
											<input type="hidden" id="logo" name="productDesign.logo" style="width:90%;border:0px;"  class=" fou "  value="${productDesign.logo}"	
										</div>
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<c:if test="${productDesign.image==null||productDesign.image==''}">
														<img id="image" class="preview"  width="20" height="20" style="float: left;margin-left: 40%;"/>
													</c:if>	
													<c:if test="${productDesign.image!=null&&productDesign.image!=''}">
														<img id="image"  class="preview"  src="<%=basePath%>${productDesign.image}" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											</c:if>
											<input type="hidden" id="image" name="productDesign.image" style="width:90%;border:0px;"  class=" fou "  value="${productDesign.image}"	
										</div>
										</td>
									</tr> 
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
				  <table width="100%" id="productPriceCategory" class="table bg auto_arrange .goodtable2"  style="display: none;">
					
					<input type="hidden" name="productPriceCategoryMap[0].category"  class="history" id="categoryOther" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" readonly="readonly" value="零售价" />
					<input type="hidden" name="productPriceCategoryMap[0].price"  class="history"  id="price" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" value="${ productPriceCategoryList[0].price}" />
					<input type="hidden" name="productPriceCategoryMap[0].money" class="history" id="money" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"  value="${  productPriceCategoryList[0].money}" />
					<input type="hidden" id="pcID" name="productPriceCategoryMap[0].pcID"   value="${  productPriceCategoryList[0].pcID}" />
                    <input type="hidden" id="pcKey" name="productPriceCategoryMap[0].pcKey"  value="${  productPriceCategoryList[0].pcKey}"  />
					<%-- <thead>
						<th align="center" bgcolor="#FFFFFF" height="25" width="70px" >价格类别</th>
						<th align="center" bgcolor="#FFFFFF" width="70px"  >单价</th>
						<th align="center" bgcolor="#FFFFFF" width="70px" >金额</th>
						<th align="center" bgcolor="#FFFFFF" width="70px" ><img src="<%=basePath%>/images/r_8_06.gif"  title="增加"  class="addPrice" style="cursor: pointer;"/></th>
					</thead>
					<tbody>
						<c:forEach var='b' items="${productPriceCategoryList}" varStatus="status">
							<tr class="checkgoods" id="kelongOfPrice${status.index+1 }">
							<td align="center" bgcolor="#FFFFFF" height="26">
								<input type="text" name="productPriceCategoryMap[${status.index+1 }].category"  class=" fou" id="categoryOther" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" readonly="readonly" value="${ b.category}" />
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="productPriceCategoryMap[${status.index+1 }].price"  class=" fou jisuan isNaN "  id="price" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;" value="${ b.price}" />
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="productPriceCategoryMap[${status.index+1 }].money" class=" fou jisuan  isNaN" id="money" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"  value="${ b.money}" />
							</td>
                                     <td align="center" bgcolor="#FFFFFF">
                                     	  <img src="<%=basePath%>images/r_8_10.gif" title="编辑" class="editPrice" style="cursor:pointer;" />
                                     	 <img src="<%=basePath%>images/r_8_08.gif" title="删除" class="deletePrice" style="cursor:pointer;" />
                                     	<input type="hidden" id="pcID" name="productPriceCategoryMap[${status.index+1 }].pcID"   value="${ b.pcID}" />
                                     	<input type="hidden" id="ppID" name="productPriceCategoryMap[${status.index+1 }].ppID"  value="${ b.ppID}"  />
                                     	 <input type="hidden" id="pcKey" name="productPriceCategoryMap[${status.index+1 }].pcKey"  value="${ b.pcKey}"  />
                                     </td>
                                     	
							</tr>
						</c:forEach>
						<tr id="kelongOfPrice" style="display:none;"> 
							<td align="center" bgcolor="#FFFFFF" height="26">
								<s:select  list="%{#request.priceManageList}" cssStyle="margin-left: 2px;width: 95%;margin-right: 5px;" id="categoryOther" 
									listKey="codeValue" listValue="codeValue" 
									name="category" theme="simple"></s:select>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="price" class=" fou jisuan isNaN " id="price" style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="money"  class=" fou jisuan isNaN "  id="money"  style="margin-left: 2px;width: 100%;border: 0px;text-align: center;"  />
							</td>
                                     <td align="center" bgcolor="#FFFFFF">
                                     	 <img src="<%=basePath%>images/r_8_08.gif" title="删除" class="deletePrice" style="cursor:pointer;" />
                                     	<input type="hidden" id="pKey" name="pKey">
                                     	<input type="hidden" id="pcID" name="pcID">
                                     	<input type="hidden" id="ppID" name="ppID">
                                     </td>
						</tr>
					</tbody> --%>
				</table>
		</div>
		
		<%-- <div id="documentList" >
				<table width="100%" class="table bg auto_arrange .goodtable2" >
					<thead>
						<th align="center" bgcolor="#FFFFFF" height="25" width="70px" >文档名称</th>
						<th align="center" bgcolor="#FFFFFF" width="70px"  >操作</th>
						<th align="center" bgcolor="#FFFFFF" width="70px" >文档名称</th>
						<th align="center" bgcolor="#FFFFFF" width="70px" >操作</th>
					</thead>
					<tbody>
						<tr>
							<td height="37" align="right">
								附产品说明书：
							</td>
							<td>
								<input class=" accessoriesUrl1" type="button"
									value="文档编辑" />
								<input name="productDesign.manualUrl" id="manualUrl" value="${productDesign.manualUrl}"
									type="hidden" />
							</td>
							<td align="right">
								附产品宣传：
							</td>
							<td>
								<input class=" accessoriesUrl2" type="button"
									value="文档编辑" />
								<input name="productDesign.propagandaUrl" id="propagandaUrl" value="${productDesign.propagandaUrl}"
									type="hidden" />
							</td>
						</tr>
						<tr>
							<td height="37" align="right">
								附公司文件：
							</td>
							<td>
								<input class=" accessoriesUrl3" type="button"
									value="文档编辑" />
								<input name="productDesign.fileUrl" id="fileUrl" value="${productDesign.fileUrl}"
									type="hidden" /> 
							</td>
								
							<td  align="right">
								是否在微信显示：
							</td>
							<td>
								<select name="productDesign.showweixin"  style="width: 60px;">
									<option value="01" ${productDesign.showweixin=='01'?'selected="selected"':''} />是</option>
									<option value="02" ${productDesign.showweixin=='02'?'selected="selected"':''} />否</option>
								</select>
							</td>					
						</tr>
					</tbody>
				</table>
		</div> --%>
							
		<div style="margin-top:10px;">
		备注：<input type="text" id="remark" name="productDesign.remark" class="inputbottom" style="width:96%;" value="${productDesign.remark}">
		<p>责任人：<input type="text" class="inputbottom" size="15" value="${staff.staffName}"/>&nbsp;&nbsp;&nbsp;包装日期：<input type="text" name="productDesign.PackagingDate" class="inputbottom" id="PackagingDate" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" size="20" >
		</p>
		</div>
				
       </div>
			
			<input type="hidden" name="sub" value="${session_value}"/>
		</form>
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
		<%------------------------------------选择往来单位------------------------------------%>
		
		
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								单位名称：
							</td>
							<td width="142">
								<input name="ccompanyID" class="input" id="ccompanyIDs" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									<input type="hidden" name="parms" id="dwparms" />
			
							</td>
							<td width="80">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="dwzycount"></span>&nbsp;&nbsp;页 </a>
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
											<div id="dwTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
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
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								姓名：
							</td>
							<td width="142">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
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
									<input type="hidden" name="parms" id="grparms" />
			
							</td>
							<td width="80">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp;页 </a>
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
											<div id="grTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>


		
	<%------------------------------------各种查询框------------------------------------%>
    
	<div id="goodsQuery" style="white-space:wrap;display:none;width:950px;height:320px;background:#E4F1FA;border:#a8c7ce 1px solid">
	  <div class="divtx" style="height:30px;line-height:30px;">查询</div><div class="close closes"></div>
	  <div style="width:950px;height:270px;overflow:auto;background:#E4F1FA;">
		<table class="table" cellpadding="10"  width="100%">
		<thead id="goodthead">
			
			</thead>
			<tbody id="goodboy">
			
			
			</tbody>
		</table>
        </div>
        <center>
		<table id="querya">
			<tr>
				<td width="80"><a id="wpsyq" style="cursor:pointer;" title="0" >上一页</a></td>
				<td width="80"><a id="wpxyq"  title="0" style="cursor:pointer;">下一页</a></td>
				<td width="100"><a id="wpzyq">共&nbsp;&nbsp; <span
						style="color: red;" id="wpzycountq"></span>&nbsp;&nbsp;页 </a>
						<input type="hidden"  id="querys"/>
					    <input type="hidden"  id="types"/>
						</td>
			</tr>
		</table>
		</center>

	</div>
	
	
	<%------------------------------------项目分类和项目------------------------------------%>
		
		<form name="selectxmForm" id="selectxmForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;width: 230px;"
				id="xmjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							项目信息
						</div>
					</div>
					<table width="99%" height="33" id="searchxm"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<!-- <td width="100" align="right">
								项目名称：
							</td>
							<td width="142">
								<input name="parameter" class="input" id="parameterxm" size="10" style="margin-left: 2px;" />
								<input  type="hidden" id="selectxm" />
								<input  type="hidden" id="selectxms" />
							</td> -->
							<td height="33" style="text-align: center;">
								<!-- <input type="button" class="btn02" id="searchxmbtn" name="button7"
									value="查询" /> -->
								<input type="button" class="btn02" id="qdxm" name="button5"
									value="确定" />
								<!-- <input type="button" class="btn02 xzxm" name="button5"
									value="新增" /> -->
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input  type="hidden" id="selectxm" />
								<input  type="hidden" id="selectxms" />
			
							</td>
							<%-- <td width="80">
								<a id="xmsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="xmxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="xmzy">共&nbsp;&nbsp; <span style="color: red"
									id="xmzycount"></span>&nbsp;&nbsp;页 </a>
							</td> --%>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree" id="treeg" 
												style="overflow: auto; z-index: 99; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttree.jsp?codeID=scode201410284shpd9x4fa0000000005&title=项目分类"   width="600" height="300"></iframe></div>
											<div class="mohuc text_tree" style="overflow: auto; z-index: 99; height: 280px;display:none;"></div>
											<div style="margin-top:5px;margin-left:35px;float:left;text-align: center;">
												<table>
													<tbody>
														<tr>
															<td>模糊查询</td>
															<td><input type="text" size="10" id="inputmoc" /><input type="button" class="btncon" id="moc" ></td>
														</tr>
													</tbody>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
							<!-- <td width="80%" valign="top" align="left">
								<div
								style="margin-top: 2px;  height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择项目</td>
									</tr>
								</table>
								<table width='99%' align='center' id='xmtable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<th align='center' bgcolor='#E4F1FA' width='3%'>选择</th>
										<th align='center' bgcolor='#E4F1FA' width='3%'>序号</th>
										<th align='center' bgcolor='#E4F1FA' width='15%' align="left">项目编号</th>
										<th align='center' bgcolor='#E4F1FA' width='20%'>项目名称</th>
										<th align='center' bgcolor='#E4F1FA' width='8%'>主项目</th>
										<th align='center' bgcolor='#E4F1FA' width='6%'>负责人</th>
										<th align='center' bgcolor='#E4F1FA' width='6%'>创建人</th>
									</thead>
									<tbody id="body_02xm"></tbody>
								</table>
								</div>
									</td> -->
									</tr>
								</table>
							</div>
							</div>
			<s:token></s:token>
		</form>
		<%------------------------------------部门树和人 ------------------------------------%>
		<form name="selectdeptForm" id="selectdeptForm" method="post"
			enctype="multipart/form-data">
			
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="deptjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							组织机构
						</div>
					</div>
					<table width="99%" height="33" id="searchdept"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								员工姓名：
							</td>
							<td width="142">
								<input class="input" id="parameterrm"
									size="10" style="margin-left: 2px;" />
								<input type="hidden" id="selectdept"
									/>
								<input type="hidden" id="selectdeptname" />
								<input type="hidden" id="deptpos" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchdeptbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qddept" name="button5"
									value="确定" />
								
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									
			
							</td>
							<td width="80">
								<a id="dpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dpzy">共&nbsp;&nbsp; <span style="color: red"
									id="dpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree"
												style="overflow: auto; z-index: 99;width:170px; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/organizationtree.jsp" width="250" height="270"></iframe></div>
											
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
								<div 
									style="margin-top: 2px; height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择员工</td>
									</tr>
								</table>
								<table width='99%' align='center' id='dptable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<tr>
											<th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>序号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员编号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员姓名</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>性别</th>
											<th align='center' bgcolor='#E4F1FA' width='100'>出生日期</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>籍贯</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>手机号</th>
											<th align='center' bgcolor='#E4F1FA'>身份证</th>
										</tr>
									</thead>
									<tbody id="body_02dept"></tbody>
								</table>

							</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		
	
<div id="div_allFun" class="rTBotr" style="display:none;z-index:210000; position:absolute;">...</div> 	
	
	
		
		
	
	<%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>

<%-- <script type="text/javascript"> 
 	var basePath = "<%=basePath%>";
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{ 
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
		    file_upload_limit: 10,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,//点击按钮将会打开单文件上传的对话框
		    button_window_mode : true,//SWFUpload.WINDOW_MODE.Opaque,//在页面上显示swf可被覆盖
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>images/pic.jpg",
            button_width:150,
            button_height:25,  
            button_placeholder_id:"uploads", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList",//隐藏域
                uploadMode: "LIST"//?
            }
        }
        SWFLoad(SWFUpload1_id,LoadSettings);
    }
    addLoadEvent(SWFUpload1_load);
    function SWFUpload1_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        $("#upload_content").hide();
        
        var hidIdList=$("#hidIdList").val();
    	var result=hidIdList.split(",");
    	var str="";
    	for(var i=0;i<result.length-1;i++){
    		str="<center><image src='<%=basePath%>"+result[i]+"' style='width: 250px;height:120px;'/></center>";
    	}
    	 $("#post_article_content").prepend(str);
    	 //$("#hidIdList").val("");
    }
    function uploads(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
		//$("#upload_content").show();
    } 
    </script> --%>			
</body>
</html>

