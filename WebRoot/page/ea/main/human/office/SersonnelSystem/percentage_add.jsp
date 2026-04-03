<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@page import="hy.ea.bo.Company"%>
<%@page import="hy.ea.bo.CAccount"%>
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
	CAccount acc= (CAccount) session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<title>超市佣金设计-新增与编辑</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
      		
		<script type="text/javascript" src="<%=basePath%>js/ea/validate.js" ></script>
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/ea/validate.css" />
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/css.css"  />
		
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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/yongjin.css"/>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/supermarket_add.js"></script>
<style type="text/css">
#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}

<!---表格拖动列宽 -->
.bg table {
	font-size: 12px;
	color: #000000;
}

.bg td,th {
	font-size: 12px;
	color: #000000;
	text-align: center;
	line-height: 15px;
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
  
#Layer2{
  width: 950px; 
  height:300px; 
  overflow: auto;
  border:1px solid #99bbe8;
  margin-top:5px;
  }
.over_bg{
	position: fixed;
    top: 0;
    right: 0;
    background: rgba(0,0,0,.6);
    z-index: 99;
    bottom: 0;
    left: 0;
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
  	var cid="<%=c.getCompanyID()%>";
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
	var billsType = "${billsType}";
	var zctype = "${zctype}";
	var placeholder="";//占位符
	var selects =${fn:length(productPriceCategoryList)+1};//克隆行标识符
	var showType="${showType}";//区别物品添加与修改操作
	var parentId="${productDesign.ppID}";
	var flexbutton="";
	var fiveClear = "${fiveClear}";
	var $yjtable="";
	var ls="";
	var cc="";
	var yj="";
	var ppname = "${productDesign.goodsName }";
	var producttype = "${productDesign.producttype}";
	var pclist = "${pclist}";
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
		<!-- 操作标签表格 -->
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0"
			style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
			<tr>
				<td align="left" style="height:24px;"><nobr>
					<input type="button" class="menubtn JQuerySubmitgd grey" disabled="disabled" value="保存" />
					<%--<input type="button" class="menubtn addline grey" disabled="disabled" name="button3" value="添加子公司" />--%>
					<input type="button" class="menubtn" name="button3" value="帮助" />
					<input type="button" class="menubtn closewindow" value="关闭" />
					<input type="hidden" value="${jumptype}" name="jumptype" />
					<input type="button" class="menubtn " value="设置批发价格" />
					</nobr>
				</td>
			</tr>
		</table>
		<div id="maindiv">
			<input type="submit" name="submit" style="display: none" />
			<div id="content1" style="width: 1100px;">
				<!-- 头标签表格 -->
				<table class="linetable" id="table3" cellpadding="10"
					cellspacing="5" width="1100">
					<tr>
						<td align="center" colspan="8" id="titlestyle"><span>产品佣金设计</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="100"><span class="xx">*</span>产品分类：</td>
						<td><input type="text" id="producttype" readonly
								class="inputbottom" style="width: 100px;"
								name="productDesign.producttype"
								value="${productDesign.producttype }" />
							<input type="button" class="btncon projectbtn" />
						</td>
						<td height="20" align="right"><span class="xx">*</span>产品编号：</td>
						<td><input id="projectCode" class="inputbottom notnull"
								readonly name="productDesign.productCode" style="width: 180px"
								value="${productDesign.productCode}" />
							<input type="hidden" name="productDesign.fiveClear" value="${fiveClear}" />
						</td>
						<td align="right" width="100"><span class="xx">*</span>产品名称：</td>
						<td><input type="text" id="projectName" readonly
								class="inputbottom notnull" style="width: 160px;"
								name="productDesign.goodsName"
								value="${productDesign.goodsName }" />
							<input type="button" class="btncon projectbtn" />
						</td>
						<td>
							<input type="button" value="业务佣金分配比例" id="yewu"/>
						</td>
						<input type="hidden" id="proID" />
					</tr>
				</table>
			</div>
			<div id="Layer2" style="width: 1100px; height: 500px;overflow:auto;" >
				<table id='cpid' width='100%'></table>
				
				<!-- 查看显示表格 -->
				<table id='edit' width='100%'">
					<s:iterator value="pclist" var="pc">
						<tr>
							<td>
								<div class="wfj11_111">
									<div class="wfj11_111_width">
										<table width="70%">
											<tr class="wfj11_111_bg left_title">
												<td width="8%"><span>*</span>产品名称</td>
												<td width="8%"><span>*</span>零售价</td>
												<td width="8%"><span>*</span>出厂价</td>
												<s:iterator value="#pc.vallist" var="dl">
													<td width="8%"><span>*</span>${dl[1]}佣金</td>
												</s:iterator>
												<td  width="8%"><span>*</span>业务佣金</td>
                                                <td width="8%"><span>*</span>设备投资类别</td>
											</tr>
											<tr class="wfj11_111_height">
												<td style="padding-left:5px;" align="left"><input
													id="cpname" name="ppname" readonly="readonly"
													style="width:100%;border:0px;text-align:left;"
													class="cpname cp" value="${pc.ppname }" /></td>
												<td align="right"><input name="rePrice" readonly
													class="jisuan model1 fou posnumred cp brokerage" id="ls"
													style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
													value="${pc.rePrice }" /> <input type="hidden" name="ppid"
													id="ppID" class="cpid cp" /></td>
												<td align="right"><input id="cc" name="efPrice" readonly
																		 class="jisuan model1 fou posnumred cp brokerage"
																		 style="display:inline;width:100%;height:99%;border:0px; text-align: right;"
																		 value="${pc.efPrice }" /></td>
												<s:iterator value="#pc.vallist" var="dl">
													<td align="right"><input readonly 
																			 class="jisuan model1 fou posnumred cp brokerage"
																			 style="display:inline;width:100%;height:99%; border:0px; text-align: right;" 
																			 value="${dl[2]}" />
														<input type="hidden"  class="cp" /></td>
												</s:iterator>
												<td align="right"><input name="brokerage" id="yj"
													size="3" class="cp yewu brokerage" readonly
													style="width:100%; border:0px; text-align: right;"
													value="${pc.brokerage }" /></td>
                                                <td align="right" id="tzTypeEdit" >${pc.tzType=='01'?"教练车":pc.tzType=='02'?"创客单车":"无"}</td>
                                                <td align="right" id="tzTypeEditYj" style="display: none;">
				                                    <select name="tzType" style="width:100%;height:99%; border:0px; text-align: right;" class="cp" id="cp">
				                                        <option value="">无</option>
				                                        <option value="01">教练车</option>
				                                        <option value="01">创客单车</option>
				                                    </select>
				                                </td>
											</tr>
										</table>
										<!-- 业务佣金分配比例表 -->
									    <table class="tbleft" width="60%" style="z-index:999;position: absolute;top: 30%;left: 20%;background: #fff;display:none;">
											<tr class="wfj11_111_bg wfj11_111_title_width01">
												<td rowspan="2" colspan="2"><span>*</span>会员名称</td>
												<s:iterator value="#pc.pmlist" var="hx">
													<td colspan="2">${hx.goodsName }</td>
												</s:iterator>
											</tr>
											<tr class="wfj11_111_bg">
												<s:iterator value="#pc.pmlist" var="hx">
													<td width="30px">比例</td>
													<td>金币</td>
												</s:iterator>
											</tr>

											<s:iterator value="#pc.yjlist" var="zx" status="zxi">
												<tr>
													<td colspan="2" width="20%">${zx[0]}</td>
													<s:iterator value="#pc.pmlist" var="hx" status="hxi">
														<td>
															${zx[hxi.index+1]}
														</td>
														<td align="right"><span
															class="jbs${zxi.index}${hxi.index+1 }"></span> <script
																type="text/javascript">
																$(function(){
																	var a="${pc.brokerage}";
																	var b="${zx[hxi.index+1]}";
																	var c="${zxi.index}${hxi.index+1 }";
																	
																	if(b!=""&&b!=null&&b!=" "){
																		var d=parseFloat(a==""?"0.00":a)*parseFloat(b==""?"0.00":b)
																		$(".jbs"+c).text(d.toFixed(3));
																	}
																});
														</script></td>
													</s:iterator>
												</tr>
											</s:iterator>
										</table>
										
									</div>
								</div>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<input type="hidden" id="line" />
			<!-- 添加显示表格 -->
			<div id="Layer1" style="display: none;">
				<div class="wfj11_111">
					<div class="wfj11_111_width">
						<table width="70%">
							<tr class="wfj11_111_bg left_title">
								<td width="8%"><span>*</span>产品名称</td>
								<td width="8%"><span>*</span>零售价</td>
								<td width="8%"><span>*</span>出厂价</td>
								<c:forEach items="${dlList}" var="dl">
									<td width="8%"><span>*</span>${dl.goodsName}佣金(%)</td>
								</c:forEach>
								<td width="8%"><span>*</span>业务佣金(%)</td>
                                <td width="8%"><span>*</span>设备投资类别</td>
							</tr>
							<tr class="wfj11_111_height">
								<td style="padding-left:5px;" align="left"><input
									id="cpname" name="ppname" readonly="readonly"
									style="width:100%;border:0px;text-align:left;"
									class="cpname cp" value="${pc.ppname }" /></td>
								<td align="right"><input name="rePrice"
														 class="jisuan model1 fou posnumred cp brokerage" id="ls"
														 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
														 value="${pc.rePrice }" /> <input type="hidden" name="ppid"
																						  id="ppID" class="cpid cp" /></td>
								<td align="right"><input id="cc" name="efPrice"
									class="jisuan model1 fou posnumred cp brokerage"
									style="display:inline;width:100%;height:99%;border:0px; text-align: right;"
									value="${pc.efPrice }" /></td>

								
								<c:forEach items="${dlList}" var="dl" varStatus="idxStatus">
									<td align="right"><input name="setupSub[${idxStatus.index}].amount" id="${dl.ppID}"
															 class="jisuan model1 fou posnumred cp dl brokerage qian" 
															 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
															 value="0" />
										<input type="hidden" name="setupSub[${idxStatus.index}].typePpid" class="cp" value="${dl.ppID}"/></td>
								</c:forEach>
								<td align="right"><input name="brokerage" id="yj" size="3"
									class="cp brokerage yewu"
									style="width:100%; height:99%; border:0px; text-align: right;"
									value="${pc.brokerage }"/></td>
                                <td align="right">
                                    <select name="tzType" style="width:100%;height:99%; border:0px; text-align: right;" class="cp" id="cp">
                                        <option value>无</option>
                                        <option value="01">教练车</option>
                                        <option value="01">创客单车</option>
                                    </select></td>
							</tr>
						</table>
						<table class="tbleft" width="60%" style="z-index:999;position: absolute;top: 30%;left: 20%;background: #fff;display:none;">
							<tr class="wfj11_111_bg wfj11_111_title_width01">
								<td rowspan="2" colspan="2"><span>*</span>会员名称</td>
								<s:iterator value="main" var="hx">
									<td colspan="2">${hx.goodsName }</td>
								</s:iterator>
							</tr>
							<tr class="wfj11_111_bg">
								<s:iterator value="main" var="hx">
									<td width="30px">比例</td>
									<td>金币</td>
								</s:iterator>
							</tr>

							<s:iterator value="yjlist" var="zx" status="zxi">
								<tr>
									<td colspan="2" width="20%">${zx[1]}</td>
									<s:iterator value="main" var="hx" status="hxi">
										<td>${zx[hxi.index+2]}
											<input type="hidden" name="bsid" value="${hx.yjId}" /> 
											<input type="hidden" name="meid" value="${zx[0]}" />
										</td>
										<td align="right"><span class="jbs${hxi.index+2 }"></span>
										</td>
									</s:iterator>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</div>
			
			<input type="hidden" id="line" />
			<!-- 修改显示表格 -->
			<div id="Layer3" style="display: none;">
				<div class="wfj11_111">
					<div class="wfj11_111_width">
						<table width="70%">
						<s:iterator value="pclist" var="pc">
							<tr class="wfj11_111_bg left_title">
								<td width="8%"><span>*</span>产品名称</td>
								<td width="8%"><span>*</span>零售价</td>
								<td width="8%"><span>*</span>出厂价</td>
								<c:forEach items="${dlList}" var="dl">
									<td width="8%"><span>*</span>${dl.goodsName}佣金</td>
								</c:forEach>
								<td width="8%"><span>*</span>业务佣金</td>
                                <td width="8%"><span>*</span>设备投资类别</td>
							</tr>
							<tr class="wfj11_111_height">
								<td style="padding-left:5px;" align="left"><input
									id="cpname" name="ppname" readonly="readonly"
									style="width:100%;border:0px;text-align:left;"
									class="cpname cp" value="${pc.ppname }" /></td>
								<td align="right"><input name="rePrice"
														 class="jisuan model1 fou posnumred cp brokerage" id="ls"
														 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
														 value="${pc.rePrice }" /> <input type="hidden" name="ppid"
																						  id="ppID" class="cpid cp" /></td>
								<td align="right"><input id="cc" name="efPrice"
									class="jisuan model1 fou posnumred cp brokerage"
									style="display:inline;width:100%;height:99%;border:0px; text-align: right;"
									value="${pc.efPrice }" /></td>
								<c:if test="${empty pc.vallist }">
									<c:forEach items="${dlList}" var="dl" varStatus="idxStatus">
										<td align="right"><input name="setupSub[${idxStatus.index}].amount" id="${dl.ppID}"
															 class="jisuan model1 fou posnumred cp dl brokerage editDlyj qian"
															 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
															 value="0" />
										<input type="hidden" name="setupSub[${idxStatus.index}].typePpid" class="cp" value="${dl.ppID}"/></td>
									</c:forEach>
								</c:if> 
								<c:if test="${!empty pc.vallist}">
									<c:set var="flag" scope="session" value="0"/>
									<c:forEach items="${dlList}" var="dl" varStatus="idxStatus">
										<c:forEach items="${pc.vallist}" var="dlp" >
											<c:if test="${dlp[3] == dl.ppID }">
												<td align="right"><input name="setupSub[${idxStatus.index}].amount" id="${dlp[3]}"
															 class="jisuan model1 fou posnumred cp dl brokerage editDlyj qian"
															 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
															 value="${dlp[2]}" />
												<input type="hidden" name="setupSub[${idxStatus.index}].typePpid" class="cp" value="${dlp[3]}"/></td>
												<c:set var="flag" scope="session" value="1"/>
											</c:if>
										</c:forEach>
										
										<c:if test="${flag == 0 }">
											<td align="right"><input name="setupSub[${idxStatus.index}].amount" id="${dl.ppID}"
															 class="jisuan model1 fou posnumred cp dl brokerage editDlyj qian"
															 style="display:inline;width:100%;height:99%; border:0px; text-align: right;"
															 value="0" />
											<input type="hidden" name="setupSub[${idxStatus.index}].typePpid" class="cp" value="${dl.ppID}"/></td>
										</c:if>
										<c:set var="flag" scope="session" value="0"/>
									</c:forEach>
								</c:if>
								<td align="right"><input name="brokerage" id="yj" size="3"
									class="cp brokerage yewu"
									style="width:100%; height:99%; border:0px; text-align: right;"
									value="${pc.brokerage }"/></td>
                                <td align="right">
                                    <select name="tzType" style="width:100%;height:99%; border:0px; text-align: right;" class="cp" id="cp"
                                    value=${pc.tzType=='01'?"教练车":pc.tzType=='02'?"创客单车":"无"}>
                                        <option value>无</option>
                                        <option value="01">教练车</option>
                                        <option value="01">创客单车</option>
                                    </select></td>
							</tr>
							</s:iterator>
						</table>
						<table class="tbleft" width="60%" style="z-index:999;position: absolute;top: 30%;left: 20%;background: #fff;display:none;">
							<tr class="wfj11_111_bg wfj11_111_title_width01">
								<td rowspan="2" colspan="2"><span>*</span>会员名称</td>
								<s:iterator value="main" var="hx">
									<td colspan="2">${hx.goodsName }</td>
								</s:iterator>
							</tr>
							<tr class="wfj11_111_bg">
								<s:iterator value="main" var="hx">
									<td width="30px">比例</td>
									<td>金币</td>
								</s:iterator>
							</tr>

							<s:iterator value="yjlist" var="zx" status="zxi">
								<tr>
									<td colspan="2" width="20%">${zx[1]}</td>
									<s:iterator value="main" var="hx" status="hxi">
										<td>${zx[hxi.index+2]}
											<input type="hidden" name="bsid" value="${hx.yjId}" /> 
											<input type="hidden" name="meid" value="${zx[0]}" />
										</td>
										<td align="right"><span class="jbs${hxi.index+2 }"></span>
										</td>
									</s:iterator>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</div>
			
			<s:if test="showType=='edit'">
				<div style="margin-top:10px;">
					<p>
						设计佣金责任人：<input type="text" class="inputbottom" size="15"
							value="${productDesign.yjstaname}" />&nbsp;&nbsp;&nbsp;设计佣金日期：<input
							type="text" name="productDesign.PackagingDate"
							class="inputbottom" id="PackagingDate"
							value="<fmt:formatDate value="${productDesign.yjdate}" pattern="yyyy-MM-dd" />"
							size="20">
					</p>
				</div>
			</s:if>
			<s:else>
				<div style="margin-top:10px;">
					<p>
						设计佣金责任人：<input type="text" class="inputbottom" size="15"
							value="<%=acc.getStaffName() %>" />&nbsp;&nbsp;&nbsp;设计佣金日期：<input
							type="text" name="productDesign.PackagingDate"
							class="inputbottom" id="PackagingDate"
							value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />"
							size="20">
					</p>
				</div>
			</s:else>
		<input type="hidden" name="sub" value="${session_value}" />
	</form>
	<!-- 项目预算添加表单结束 -->
	
	<%------------------------------------项目分类和项目------------------------------------%>
		
		<form name="selectxmForm" id="selectxmForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
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
							<td width="100" align="right">
								项目名称：
							</td>
							<td width="142">
								<input name="parameter" class="input" id="parameterxm"
									size="10" style="margin-left: 2px;" />
								<input  type="hidden" id="selectxm" />
								<input  type="hidden" id="selectxms" />
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchxmbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdxm" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzxm" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								
			
							</td>
							<td width="80">
								<a id="xmsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="xmxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="xmzy">共&nbsp;&nbsp; <span style="color: red"
									id="xmzycount"></span>&nbsp;&nbsp;页 </a>
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
										
											<div class="text_tree" id="treeg" 
												style="overflow: auto; z-index: 99; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttree.jsp?codeID=scode201410284shpd9x4fa0000000005&title=项目分类"   width="600" height="300"></iframe></div>
											<div class="mohuc text_tree" style="overflow: auto; z-index: 99; height: 280px;display:none;"></div>
											<div style="margin-top:5px;float:right;">模糊查询<input type="text" size="10" id="inputmoc" /><input type="button" class="btncon" id="moc" ></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
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
										<th align='center' bgcolor='#E4F1FA' width='15%' align="left">产品编号</th>
										<th align='center' bgcolor='#E4F1FA' width='20%'>产品名称</th>
										<th align='center' bgcolor='#E4F1FA' width='8%'>产品分类</th>
										</thead>
									<tbody id="body_02xm"></tbody>
								</table>
								</div>
									</td>
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
					<div class="drag">组织机构</div>
				</div>
				<table width="99%" height="33" id="searchdept" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">员工姓名：</td>
						<td width="142"><input class="input" id="parameterrm"
							size="10" style="margin-left: 2px;" /> <input type="hidden"
							id="selectdept" /> <input type="hidden"
							id="selectdeptname" /> <input type="hidden" id="deptpos" /></td>
						<td height="33"><input type="button" class="btn02"
							id="searchdeptbtn" name="button7" value="查询" /> <input
							type="button" class="btn02" id="qddept" name="button5" value="确定" />

							<input type="button" class="btn02 JQueryreturns" name="button4"
							value="关闭" /></td>
						<td width="80"><a id="dpsy" title="0">上一页</a></td>
						<td width="80"><a id="dpxy" title="0">下一页</a></td>
						<td width="100"><a id="dpzy">共&nbsp;&nbsp; <span
								style="color: red" id="dpzycount"></span>&nbsp;&nbsp;页 </a></td>
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
											style="overflow: auto; z-index: 99;width:170px; height: 280px;">
											<iframe
												src="<%=basePath%>page/ea/main/finance/invoicing/organizationtree.jsp"
												width="250" height="270"></iframe>
										</div></td>
								</tr>
							</table></td>
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

							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>



	<div id="div_allFun" class="rTBotr"
		style="display:none;z-index:210000; position:absolute;">...</div>


	<div class="over_bg"></div>


	<%------------------------------------用于表单提交-----------------------------------%>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>

<script type="text/javascript">
		$(document).ready(function(e) {
			$(".wfj11_111_height").each(function(){
				var $prent=$(this).parent().parent().parent();
				var $td=$(this).find("td");
				$td.find("td").eq(0).css("padding-left","5px");
				$td.find("td").eq(3).css("border-right","none");
			});
			
			
			
			$("#yewu").click(function(){
				var yewu = $(".yewu").val();
				if(yewu > 0){
					if($(".tbleft").css("display")=="none"){
						$(".tbleft").show();
						$(".over_bg").show();
					}else{
						$(".tbleft").hide()
						$(".over_bg").show();
					}
				}else{
					alert("业务佣金为负数");
				}
			})
			$(".over_bg").click(function(){
				$(this).hide();
				$(".tbleft").hide();
			})
        });
		
		

</script>
</body>
</html>

