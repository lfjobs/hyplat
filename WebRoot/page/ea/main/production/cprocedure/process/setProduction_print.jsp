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
    <title>设置生产量</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/setProduction_print.js"></script>	
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	
	<style type="text/css">
		span{
			display:-moz-inline-box;
			display:inline-block;
		}
	</style>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var orgId="<%=session.getAttribute("organizationID")%>";
		var staffId="";
		var productID="";
		var amount="${productionAmount.amount}";
	</script>
  </head>
  
  <body>
    <form method="post" id="form" name="form" >
  <input type="submit" id="submit" name="submit" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
    <center>
    	<div style="border: 1px solid #999999;width: 60%;height: 70%;margin-top: 4%;">
    		<div style="background-color: #009DD9;width:100%;height:8%;font-size: 30px;color:#fff;padding-top: 2%;">设置生产量</div>
    		<div style="width: 100%;height:17%;">
				<input type="hidden" id="productionAmountID" name="productionAmount.productionAmountID" value="${productionAmount.productionAmountID}">
				<input type="hidden" id="productionAmountKey" name="productionAmount.productionAmountKey" value="${productionAmount.productionAmountKey}">
    			<table style="width: 100%;height: 100%;">
    				<tr style="height: 48%;">
    					<td style="width: 32%;">
    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
    						<input type="hidden" name="productionAmount.companyID" value="${productionAmount.companyID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.companyName}" name="productionAmount.companyName"></span>
    					</td>
    					<td style="width: 32%;"></td>
    					<td>
    						<span  style="width: 80px; text-align: right;">生产批次号：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.batchNumber}" id="batchNumber" name="productionAmount.batchNumber"></span>
    					</td>
    				</tr>
    				<tr style="height: 48%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">产&nbsp;品&nbsp;名&nbsp;称：</span>
    						<input type="hidden" id="productID" name="productionAmount.productID" value="${productionAmount.productID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" value="${productionAmount.goodsName}" readonly="readonly" id="goodsName" name="productionAmount.goodsName"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">责&nbsp;&nbsp;&nbsp;任&nbsp;&nbsp;&nbsp;人：</span>
    						<input type="hidden" id="staffsID" name="productionAmount.staffID" value="${productionAmount.staffID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.staffName}" id="staffsName" name="productionAmount.staffName"></span>
    					</td>
    					<td>
   							<span  style="width: 80px; text-align: right;">设&nbsp;置&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.setDate}" name="productionAmount.setDate" id="setDate" ></span>
    					</td>
    				</tr>
    			</table>
    		</div>	
    		<div style="height: 45%;width: 100%;" class="tableSample">
    			<table cellpadding="0" cellspacing="0" class="table"  style="width: 80%;float: left;">
	    			<thead>
	    				<tr height="30">
	    					<th><span style="width:320px;overflow: hidden;">物品结构</span></th>
	    					<th><span style="width: 200px;overflow: hidden;">物品编号</span></th>
	    					<th><span style="width: 170px;overflow: hidden;">结构数量</span></th>
	    					<th><span style="width: 169px;overflow: hidden;">生产数量</span></th>
	    				</tr>
	    				</thead>
    				</table>
    				<div style="height: 90%;width: 100%;overflow: hidden;position: relative;top: -5.5px" class="tableOuter">
	    				<div style="height: 95%;overflow-y: auto;" class="tableInner">
		    				<table  cellpadding="0" cellspacing="0" class="table" style="float: left;" id="tableBody">
			    				<tbody>
			    					
			    				</tbody>
		    			</table>
	    			</div>
	    		</div>
    		</div>
    		<hr color="#DADCDB"/>
    		<div style="width: 100%;height:6%;">
    			<span style="float: left;font-size: 14px;">
    				<span>制单人：</span>
    				<input type="hidden" value="${productionAmount.singleID}" id="singleID" name="productionAmount.singleID">
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${productionAmount.singleName}" id="singleName" name="productionAmount.singleName"></span>
    			</span>
    			<span style="float: right;font-size: 14px;">
    				<span>制单时间：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${productionAmount.singleDate}" id="singleDate" name="productionAmount.singleDate"></span>
    			</span>
    		</div>   		
    	</div>
    </center>
  <s:token></s:token>
  </form>
  </body>
</html>
