<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">

<title>同意退款并退货</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/BenDis/approverefund.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/finance/consignee.css" />

<script type="text/javascript">
var basePath="<%=basePath%>";


</script>
<style type="text/css">

.noborder{
border:none;
height:22px;
width:100%;
}

</style>
</head>

<body>
	<div class="main">
	<form name="form" id="form" method="post">
	<input type="submit" style="display:none;" name="submit"/>
	<div class="contaner" style="width:40%;">
	
	   <div class="top">填写退货信息</div>
		<table  class="tbl" cellspacing="5" cellpadding="5" style="width:80%;">
          
			<tr>
				<td align="right" style="width:45%;">退款单编号:</td>
				<td class="line">${refundSheet.refundCode}</td>

			</tr>
			<tr>
				<td  align="right">订单编号:</td>
				<td class="line">${refundSheet.orderCode}</td>

			</tr>
			<tr>
				<td  align="right">退货地址:</td>
				
				<td class="line"><input type="text" class="noborder" name="refundSheet.refundAddress"/></td>

			</tr>
			<tr>
				<td align="right">邮编:</td>
				<td class="line"><input type="text" name="refundSheet.postcode" class="noborder"/></td>

			</tr>
			<tr>
				<td  align="right">收货接收人姓名:</td>
				<td class="line">
				
				<input name="refundSheet.receiverID" type="hidden" />
				<input name="refundSheet.receiverName" type="text" class="noborder"/>
				<input type="hidden"  name="refundSheet.rsid" value="${refundSheet.rsid}"/>
				</td>

			</tr>
			<tr>
				<td  align="right">收货接收人电话:</td>
				<td class="line"><input name="refundSheet.receiverTel" type="text" class="noborder"/></td>

			</tr>
			<tr>
				<td align="right">接收仓库编号:</td>
				<td class="line"><input name="refundSheet.warehouseCode" type="text" class="noborder"/></td>

			</tr>
			
			<tr>
				<td align="right">留言:</td>
				<td class="line"><textarea name="refundSheet.remark"  cols="28" rows="8" class="noborder" style="height:100px;"></textarea></td>

			</tr>
		</table>

	</div>
		<div class="bottom">
		<input type="hidden" name="refundSheet.refundstate" value="01"/>
			<input type="button" class="btn save" value="提交保存" />
		</div>
		</form>
	</div>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
