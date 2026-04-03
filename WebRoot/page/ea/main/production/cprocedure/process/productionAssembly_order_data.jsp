<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产组装</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/productionAssembly_order_data.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="${utboundOrderVo.goodsbillsid}";
		var staffName="${c.staffName}";
		var cls="";
		var fiveClear="${fiveClear}";
		var quantity="${g.quantity}";
	</script>
	<style type="text/css">
		#tableR{
			border-right:1px solid #a8c7ce;border-bottom:1px solid #a8c7ce;
		}
		#tableR td{
			border-left:1px solid #a8c7ce;border-top:1px solid #a8c7ce;
		}
		a{text-decoration:none;}
	</style>
  </head>
  <body>
		  <div style="width: 70%;position: relative;left: 15%;top:5%;height:95%;">
		  		<div style="height: 10%;width: 100%;color: #fff;font-size: 36px;text-align: center;line-height: 180%;background-color:#009DD9">
		  				生&nbsp;&nbsp;产&nbsp;&nbsp;组&nbsp;&nbsp;装
		  		</div>
		  		<div style="width: 100%;">
			  		<div style="margin-top: 30px;text-align: center;">
			  			<span>产品名称：</span>
			  			<span><select style="width: 180px;border: 0px;border-bottom: 1px solid #a8c7ce;" id="sel"><option value="">请选择</option></select></span>
			  			<span><input type="button" value="查询" id="selButton"></span>
			  		</div>
		  		</div>
		  		<div style="height: 10%;width: 100%;">
		  			<table style="width: 100%;height: 100%;">
	    				<tr style="height: 48%;">
	    					<td style="width: 32%;">
	    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
	    						<span><input type="text" class="inputbottom"  style="width: 180px;"  readonly="readonly" value="${c.companyName}" name="productionAmount.companyName"></span>
	    					</td>
	    					<td style="width: 41%;"></td>
	    					<td>
	    						<span  style="width: 80px; text-align: right;">主产品名称：</span>
	    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${g.goodsName}" id="batchNumber" name="productionAmount.batchNumber"></span>
	    					</td>
	    				</tr>
    				</table>
		  		</div>
		  		<div style="height: 70%;width: 100%;">
		  			<table class="table">
		  				<thead>
		  					<tr>
		  						<th width='300px;'>产品名称</th>
		  						<th width='111px;'>结构量</th>
		  						<th width='111px;'>库存量</th>
		  						<th width='111px;'>生产量</th>
		  						<th width='110px;'>出库量</th>
		  						<th width='179px;'>负责人</th>
		  						<th width='70px;'>操作</th>
		  					</tr>
		  				</thead>
		  				<div>
		  					<div>
		  						<table id="tableR" cellpadding="0" cellspacing="0"  style="position: relative;top: -5px;">
		  							<tbody>
		  							</tbody>
		  						</table>
		  					</div>
		  				</div>
		  			</table>
		  		</div>
		  </div>
  </body>
</html>
