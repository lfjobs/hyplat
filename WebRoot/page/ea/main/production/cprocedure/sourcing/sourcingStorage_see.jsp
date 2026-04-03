<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购入库单打印页面</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/sourcing/sourcingStorage_ins.js"></script>	
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	<style type="text/css">
		span{
			display:-moz-inline-box;
			display:inline-block;
		}
		.tab td,th{
			border-left:1px solid #000;border-top:1px solid #000
		}
		.tab{
			border-right:1px solid #000;border-bottom:1px solid #000;
		}
	</style>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
	</script>
  </head>
  
  <body>
  <form method="post" id="form" name="form" >
  <input type="submit" id="submit" name="submit" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
    <center>
    	<div style="width: 864px;margin-top: 2%;">
    		<div style="width:100%;height:40px;font-size: 30px;color:#000;">采购入库单</div>
    		<div style="width: 100%;height:86px;">
    			<table style="width: 100%;height: 100%;">
    				<tr style="height: 25%;">
    					<td style="width: 33%;">
    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${c.companyName}"></span>
    					</td>
    					<td style="width: 33%;">
    						<input type="hidden" id="cashierBillsID" value="${c.cashierBillsID}" name="cashierbills.cashierBillsID">
    						<span  style="width: 80px; text-align: right;">单&nbsp;据&nbsp;编&nbsp;号：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${c.journalNum}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${c.departmentName}"></span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;&nbsp;&nbsp;购&nbsp;&nbsp;&nbsp;员：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${f.staffName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;购&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${f.purchaseDate}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">仓&nbsp;库&nbsp; 名&nbsp;称：</span>
    						<span>
    							<input type="text" class="inputbottom" style="width: 180px;border-color: #000;" id="depotName" name="financialBill.depotName" readonly="readonly" value="${f.depotName}">
    						</span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">验&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" readonly="readonly" value="${f.staffsName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">收&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;"  readonly="readonly" value="${f.consigneeName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">收&nbsp;货&nbsp; 日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" value="${f.goodsReceiptDate}" readonly="readonly"></span>
    					</td>
    				</tr>
    				<tr style="height: 25%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">入&nbsp;库&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;border-color: #000;" name="financialBill.purchaseDate" id="cashierDate" readonly="readonly" value="${f.purchaseDate}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">责&nbsp;&nbsp;&nbsp;任&nbsp;&nbsp;&nbsp;人：</span>
    						<span>
    							<input type="text" class="inputbottom" id="staffsName" name="cashierbills.staffName" style="width: 180px;border-color: #000;" readonly="readonly" value="${c.staffName}">
    						</span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">选&nbsp;择&nbsp; 科&nbsp;目：</span>
    						<span>
    							<input type="text" class="inputbottom" id="tosubjects" style="width: 180px;border-color: #000;" name="financialBill.subjectName" readonly="readonly" value="${f.subjectName}">
    						</span>
    					</td>
    				</tr>
    			</table>
    		</div>	
    		<div style="width: 100%;" class="tableSample">
    			<table cellpadding="0" cellspacing="0" class="tab"   style="width: 80%;float: left;">
	    			<thead>
	    				<tr height="30">
	    					<th><span style="width: 30px;overflow: hidden;">序号</span></th>
	    					<th><span style="width:80px;overflow: hidden;">物品编号</span></th>
	    					<th><span style="width: 73px;overflow: hidden;">物品名称</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">收货数量</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">入库数量</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">单价</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">总金额</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">类别</span></th>
	    					<th><span style="width: 30px;overflow: hidden;">单位</span></th>
	    					<th><span style="width: 45px;overflow: hidden;">规格</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">物流方式</span></th>
	    					<th><span style="width: 100px;overflow: hidden;">供应商</span></th>
	    					<th><span style="width: 91px;overflow: hidden;">供应商负责人</span></th>
	    					<th><span style="width: 78px;overflow: hidden;">负责人电话</span></th>
	    					<th><span style="width: 41px;overflow: hidden;">备注</span></th>
	    				</tr>
	    				</thead>
	    				<tbody>
	    					<c:forEach items="${goodsList}" var="list" varStatus="a">
	    						<tr>
	    							<td>
	    								<input type="hidden" name="goodsBillsList[${a.index}].goodsBillsID" value="${list.goodsBillsID}">
	    								<span style="width: 30px;overflow: hidden;">${a.index+1}</span>
	    							</td>
	    							<td><span style="width: 80px;overflow: hidden;">${list.goodsNum}</span></td>
	    							<td><span style="width: 73px;overflow: hidden;">${list.goodsName}</span></td>
	    							<td><span style="width: 50px;overflow: hidden;">${list.isQualify}</span></td>
	    							<td><input type="text" style="border: 0px;width: 50px;" name="goodsBillsList[${a.index}].reQuantity" value="${list.isQualify}" readonly="readonly"></td>
	    							<td><span style="width: 40px;overflow: hidden;">${list.price}</span></td>
	    							<td><span style="width: 50px;overflow: hidden;">${list.money}</span></td>
	    							<td><span style="width: 40px;overflow: hidden;">${list.typeID}</span></td>
	    							<td><span style="width: 30px;overflow: hidden;">${list.goodsVariableID}</span></td>
	    							<td><span style="width: 45px;overflow: hidden;">${list.standard}</span></td>
	    							<td><span style="width: 50px;overflow: hidden;">${list.logistics}</span></td>
	    							<td><span style="width: 100px;overflow: hidden;">${list.supplier}</span></td>
	    							<td><span style="width: 91px;overflow: hidden;">${list.supplierStaffName}</span></td>
	    							<td><span style="width: 78px;overflow: hidden;">${list.supplierStaffTelephone}</span></td>
	    							<td><span style="width: 41px;overflow: hidden;">${list.remark}</span></td>
	    						</tr>
	    					</c:forEach>
			    				</tbody>
    				</table>
    		</div>
    		<div style="width: 100%;height:6%;">
    			<span style="float: left;font-size: 14px;">
    				<span>制单人：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;border-color: #000;" readonly="readonly" value="${c.inputName}"></span>
    			</span>
    			<span style="float: right;font-size: 14px;">
    				<span>制单时间：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;border-color: #000;" readonly="readonly" value="${fn:substring(c.cashierDate,0,10)}"></span>
    			</span>
    		</div>
    	</div>
    </center>
  <s:token></s:token>
  </form>
  </body>
</html>
