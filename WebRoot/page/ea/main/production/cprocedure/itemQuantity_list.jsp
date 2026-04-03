<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <title>项目产品批量生产出库</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/production/cprocedure/itemQuantity_list.js"  type="text/javascript" ></script>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
		var basePath="<%=basePath%>";
		var dtnumber=0;
		var id="";
		</script>
		<style type="text/css">
		.fieldName{
		text-align:right;
		width: 130px;
		}
		.goodsOutOfTheLibrary tr{
		height:30px;
		}
		#occlusion{
		background: rgba(255,255,255, 0.5);
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0px;
		left: 0px;
		}
		#gotable  th{
		border: 1px solid #a8c7ce;
		}
		#gotable  td{
		border: 1px solid #a8c7ce;
		}
		</style>
  </head>
  
  <body>
  <div  id="main_main" class="main_main"> 	<div id="fexdiv">
  	<table class="fexlist">
  	  <thead>
  	  	<tr>
	  	  <th width="35" align="center">选择</th>
	  	  <th width="80" align="center">出库单编号</th>
	  	  <th width="80" align="center">产品编号</th>
	  	  <th width="110" align="center">产品名称</th>
	  	  <th width="90" align="center">规格</th>
	  	  <th width="110" align="center">出库数量</th>
	  	  <th width="60" align="center">单价</th>
	  	  <th width="70" align="center">总金额</th>
	  	  <th width="80" align="center">仓库名称</th>
	  	  <th width="100" align="center">仓库地址</th>
	  	  <th width="80" align="center">负责人</th>
	  	  <th width="110" align="center">出库时间</th>
  		</tr>
  	  </thead>
  	  <tbody>
  	  	<c:forEach items="${pageForm.list}" var="l" >
  	  		<tr class="dps" name="dps">
  	  			<td><input type="radio" name="listRadio" class="radio" id="${l.cashierbillsid}"></td>
  	  			<td><span>${l.journalnum}</span></td>
  	  			<td><span>${l.goodsnum}</span></td>
  	  			<td><span>${l.goodsname}</span></td>
  	  			<td><span>${l.standard}</span></td>
  	  			<td><span>${l.quantity}</span></td>
  	  			<td><span>${l.price}</span></td>
  	  			<td><span>${l.money}</span></td>
  	  			<td><span>${l.depotname}</span></td>
  	  			<td><span>--</span></td>
  	  			<td><span>${l.staffName}</span></td>
  	  			<td><span class="date">${l.cashierDate}</span></td>
  	  		</tr>
  	  	</c:forEach>
  	  </tbody>
  	</table>
  	</div>
  </div>
  <form id="form" name="form" method="post" action="">
    <input type="hidden" name="statusbill"  id="statusbill" value="${statusbill}">
	<!--     -----------------------------添加物品------------------------------   -->
	<div style="width: 380px;height:500px;border: 1px solid #ffffff;background-color: #DBFAF8;
			position: absolute;top: 9px;left: 530px;" class="jqmWindow jqmWindowcss2" id="single">
		<div style="background-color: #63B8FF;height:45px;text-align: center;">
			<font size="5" color="#FFFFFF" style="position: relative;top:12px;">生&nbsp;产&nbsp;出&nbsp;库&nbsp;管&nbsp;理</font>
		</div>
		<hr color="#FFFFFF"/>
		<div><center>
		<table class="goodsOutOfTheLibrary" >
			<tr>
				<td class="fieldName">出库单编号：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"  style="width: 130px;" disabled="disabled" id="journalNum">
					<input type="hidden" id="goodsId" name="utboundOrderVo.inventoryID"><input type="hidden"  name="utboundOrderVo.journalnum"  id="journalNum"</td>
			</tr>
			<tr>
				<td class="fieldName">产品编号：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"   style="width: 130px;" id="productNumber" disabled="disabled">
					<input type="hidden"  name="utboundOrderVo.goodsNum"  id="productNumber" ></td>
			</tr>
			<tr>
				<td class="fieldName">产品名称：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"   style="width: 130px;" id="goodsName"
					placeholder="点击选择物品" name="utboundOrderVo.goodsname"><input type="hidden" id="goodsHidden" ></td>
			</tr>
			<tr>
				<td class="fieldName">规格：</td><td style="width: 40px"/><td class="fieldContent"><input type="text" style="width: 130px;" disabled="disabled" id="standard" class="standard">
					<input type="hidden"  name="utboundOrderVo.standard" id="standard" class="standard"></td>
			</tr>
			<tr>
				<td class="fieldName">批量生产出库数量：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"  name="utboundOrderVo.quantity"  style="width: 130px;" disabled="disabled" id="number"></td>
			</tr>
			<tr>
				<td class="fieldName">单价：</td><td style="width: 40px"/><td class="fieldContent"><input type="text" style="width: 130px;" disabled="disabled" id="price" class="price">
					<input type="hidden"  name="utboundOrderVo.price" id="price" class="price"></td>
			</tr>
			<tr>
				<td class="fieldName">总金额：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"  style="width: 130px;" disabled="disabled" id="money" class="money">
					<input type="hidden"  name="utboundOrderVo.money" id="money" class="money"></td>
			</tr>
			<tr>
				<td class="fieldName">仓库名称：</td><td style="width: 40px"/><td class="fieldContent">
					<input type="text"   style="width: 130px;" disabled="disabled" id="warehouseName" ><input type="hidden" id="warehouseId" name="utboundOrderVo.Depotid">
					<input type="hidden"   id="warehouseName" name="utboundOrderVo.Depotname"></td>
			</tr>
			<tr>
				<td class="fieldName">仓库地址：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"   style="width: 130px;" disabled="disabled">
					<input type="hidden" /></td>
			</tr>
			<tr>
				<td class="fieldName">负责人：</td><td style="width: 40px"/><td class="fieldContent">
					<input type="text"   style="width: 130px;" disabled="disabled" id="staffName" ><input type="hidden" id="staffID" name="utboundOrderVo.staffID">
					<input type="hidden"   id="staffName" name="utboundOrderVo.staffName"></td>
			</tr>
			<tr>
				<td class="fieldName">出库时间：</td><td style="width: 40px"/><td class="fieldContent"><input type="text"   style="width: 130px;" disabled="disabled" id="currentTime">
					<input type="hidden"  name="utboundOrderVo.cashierDate"  id="currentTime"></td>
			</tr>
		</table>
		</center></div>
		<br/><hr color="#FFFFFF"/>
		<input type="button" value="确定" class="theLibrary" style="width: 60px;height:25px;position: relative;top: 7px;left: 100px;">
		<input type="button" value="取消" class="theLibrary" style="width: 60px;height:25px;position: relative;top: 7px;left: 165px;"">
	</div>
	
	<!-- -----------------------------选择物品-------------------------- -->
			<div class="jqmWindow jqmWindowcss1" style="position: absolute;;top: 20px;" disabled="true"
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
								<span id="goodtitle">条码或物品编号：</span>
							</td>
							<td width="80">
								<input name="parameter" class="input" id="parameter" size="20" type="text"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
							<nobr>
							&nbsp;&nbsp;
								<input type="button" class="btn02" id="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								</nobr>
							</td>
						</tr>
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
												物品名称
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												所属库房
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												条码
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												物品编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												物品类型
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												库存数量
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												单价
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												规格
											</th>
										</tr>                        
										</thead>
										<tbody id="tbodya">
											<tr id="sampleTr" style="display: none;">
												<td></td><td></td><td></td><td></td>
												<td></td><td></td><td></td><td></td><td></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
	
	<!--  -------------------------------------选择序号区间---------------------------------------------->
	<div class="jqmWindow jqmWindowcss1"   id="goodsNum"
		style="width: 350px;height: 170px;position: absolute;top: 90px;left: 655px;background-color: #FEF6AC">
		<div style="width: 100%;height: 34px;text-align: center;background-color: #27D7F7">
			<font size="3" style="position:relative;top:8px;" color="#FFFFFF">选择物品序号区间</font>
		</div>
		<div>
			<div style="text-align: center;"><span><font color="#FC7984">当前物品可用的序号为：</font><font color="red" id="availableNumber"></font></span><br/>
				  <span><font color="#FC7984">由于序号中间可能有断点，所以数量与选择的序号区间可能不同</font></span></div>
			<div style="position: relative;top: 10px;text-align: center;">
				<input type="text" class="itemNumber" id="start" placeholder="开始序号" style="width: 100px;height: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" class="itemNumber" id="end" placeholder="结束序号" style="width: 100px;height: 15px;"> 
			</div>
			<div style="position:relative;top: 20px;left: 46px;">
				<font color="#716F6F">当前所选序号中可用数量为：</font><font id="SNum"></font>
			</div>
		</div>
		<div style="position:relative;top: 30px;text-align: center;">
			<input type="button" value="提交" class="goodsNumBut" style="width: 60px;height:18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="返回" class="goodsNumBut" style="width: 60px;height:18px;">
		</div>
	</div>
	  	<input type="submit" name="submit" id="submit" style="display: none;">
	</form>
	<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?pageNumber=${pageNumber}&statusbill=${statusbill}">
		</c:param>
	</c:import>
		<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
  </body>
</html>
