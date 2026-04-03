<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>成品入库单</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/productStorage_data.js"  type="text/javascript" ></script>
    <link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	 <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var status="${status}",id="${ut.cashierbillsid}",dtnumber="${ut.quantity}";
    	$(function(){
	    	if(dtnumber!="")
	    		$(".number").attr("readonly","").css("borderBottom","1px solid #AEADA7");
    	});
    	var fiveClear="${fiveClear}";
		var category="${category}";
    </script>
    <style type="text/css">
    	#tableR{
			border-bottom:1px solid #a8c7ce;
		}
		#tableR td{
			border-top:1px solid #a8c7ce;
		}
		input{
			border: 0px;width:100%;
			/*height:80%;*/
		}
    </style>
  </head>
  <body>
  	<center>
    <form id="goodsform" name="goodsform" method="post">
  	<input type="submit" id="goodssubmit" name="goodssubmit" style="display: none;">
  	<!--     -----------------------------添加物品------------------------------   -->
	<div style="width: 35%;height:85%;border: 1px solid #000;"  id="single">
		<div style="background-color: #009DD9;height:55px;text-align: center;">
			<font size="5" color="#FFFFFF" style="position: relative;top:18px;">产&nbsp;品&nbsp;入&nbsp;库&nbsp;管&nbsp;理</font>
		</div>
		<hr color="#FFFFFF"/>
		<div><center>
		<table class="goodsOutOfTheLibrary" id="tableR" width="100%;" cellpadding="0" cellspacing="0"> 
			<tr height="45px;">
				<td class="fieldName" align="right" >入库单编号：</td>
				<td class="fieldContent">
					<input type="text"  readonly="readonly" class="journalNum" value="${ut.journalnum}">
					<input type="hidden" name="utboundOrderVo.ppId" class="ppId" value="${pp.ppID}">
					<input type="hidden"  name="utboundOrderVo.journalnum"  class="journalNum" value="${ut.journalnum}"/>
					<input type="hidden"  name="fiveClear"  class="fiveClear" value="${fiveClear}"/>
					<input type="hidden"  name="category"  class="category" value="${category}"/>
					<input type="hidden"  name="dcheckid"  class="goodsDcheckid"/>
					
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >产品编号：</td>
				<td class="fieldContent">
					<input type="text"  name="utboundOrderVo.goodsnum"  id="productNumber"  class="productNumber" value="${ut.goodsnum}">
					<input type="hidden" name="utboundOrderVo.goodsId" class="goodsId" value="${ut.goodsId}">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right"  width="30%;"> 产品名称：</td>
				<td class="fieldContent"  width="70%;">
					<input type="text"   style="border-bottom: 1px solid #AEADA7;" id="goodsName" class="goodsName"  readonly="readonly"
							placeholder="点击选择物品" name="utboundOrderVo.goodsname" value="${ut.goodsname}">
					<input type="hidden" name="utboundOrderVo.typeId" id="typeName" class="typeName"  value="${ut.typeId}">
					<input type="hidden" name="type" class="utType"><input type="hidden" name="cashierBillsID" class="utCashi">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >入库数量：</td>
				<td class="fieldContent">
					<input type="text"  name="utboundOrderVo.quantity" readonly="readonly" id="number" class="number" value="${ut.quantity}">
					<input type="hidden" id="price" name="utboundOrderVo.price" class="price" value="${ut.price}">
					<input type="hidden" id="specifications" name="utboundOrderVo.standard" class="specifications" value="${ut.standard}">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >仓库编号：</td>
				<td class="fieldContent">
					<input type="text"   readonly="readonly">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >仓库名称：</td>
				<td class="fieldContent">
					<input class="warehouseName" value="${depot.depotName}"  name="utboundOrderVo.drdepotName" type="text" readonly="readonly">
					<input type="hidden" class="warehouseId" name="utboundOrderVo.drdepotID" value="${depot.depotID}">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >仓库地址：</td>
				<td class="fieldContent">
					<input type="text"  readonly="readonly">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >责任人：</td>
				<td class="fieldContent">
					<input type="hidden" class="staffID" name="utboundOrderVo.staffID" value="${staff.staffID}">
					<input type="text"   class="staffName" name="utboundOrderVo.staffName" readonly="readonly" value="${staff.staffName}">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >验收人：</td>
				<td class="fieldContent">
					<input type="text"  name="utboundOrderVo.staffsName" class="acceptanceName" readonly="readonly" value="${ut.staffsName}"/>
					<input type="hidden"  name="utboundOrderVo.staffsID" class="acceptanceId" value="${ut.staffsID}"/>
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >验收时间：</td>
				<td class="fieldContent">
					<input type="text"  name="utboundOrderVo.examinegoodsDate" class="examineDate" readonly="readonly" value="${ut.examinegoodsDate}">
				</td>
			</tr>
			<tr height="45px;">
				<td class="fieldName" align="right" >当前时间：</td>
				<td class="fieldContent">
					<input type="text"  name="utboundOrderVo.cashierDate"  class="currentTime" readonly="readonly" value="${ut.cashierDate}">
				</td>
			</tr>
		</table>
		</center></div>
		<br/>
		<input type="button" value="确定" class="theLibrary" style="width: 90px;height:40px;position: relative;left: -60px;">
		<input type="button" value="取消" class="theLibrary" style="width: 90px;height:40px;position: relative;left: 67px;"">
	</div>
	
	<!-- -----------------------------选择物品-------------------------- -->
			<div class="jqmWindow jqmWindowcss1" style="position: absolute;;top: 20px;" disabled="true"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择合格的产品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="3"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100px;" rowspan="2">
								<font>根据条件查询：</font>
							</td>
							<td width="200px;">
								<select id="industry"><option value="">行业分类</option></select>
								<select style="position: relative;left:7px; " id="product"><option value="">产品分类</option></select>
									<input type="text" placeholder="填写产品编号" id="parameter">
							</td>
							<td rowspan="2">
								<nobr>
								<input type="button" class="btn02 goodsButton" id="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02 goodsButton" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 goodsButton JQueryreturns" name="button4"
									value="关闭" />
								</nobr>
							</td>
						</tr>
						<%--<tr>--%>
							<%--<td><input type="text" placeholder="填写项目编号" id="parameter"></td>--%>
						<%--</tr>--%>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 2px; margin-bottom: 2px;">
						<tr>
							<td width="100%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; height: 300px; width: 100%; overflow: auto;">
									<table width="98%" height="26" align='center' cellspacing="0" style="background: url(<%=basePath %>images/admin_images/bg_01.gif) repeat-x;border-color: inherit"
										cellpadding="1" style='font-size: 12px;' class='bannb_01'>
										<tr>
											<td height="24" align="left" valign="top" class="txt01">
												&nbsp;点击选择物品
											</td>
										</tr>
									</table>
									<table width='100%' align='center' id='gotable' cellpadding='0'
										cellspacing='0' class='table' >
										<thead>
										<tr>
											<th height='21' align='center' bgcolor='#E4F1FA'>
												选择
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												生产批次号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品名称
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品分类
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品规格
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												单价
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												数量
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												金额
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												验收人
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												验收时间
											</th>
										</tr>                        
										</thead>
										<tbody id="tbodya">
											<tr id="sampleTr" style="display: none;" name="goods">
												<td></td><td></td><td></td><td></td><td></td>
												<td></td><td></td><td></td><td></td><td></td><td></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
  	</form>
  		<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
	<form method="post" name="forms" id="forms">
		<input type="submit" name="submits" id="submits" style="display: none;">
	</form>
	</center>
	<iframe name="hidden" border="0"  height="0" frameborder="0"></iframe>
  </body>
</html>
