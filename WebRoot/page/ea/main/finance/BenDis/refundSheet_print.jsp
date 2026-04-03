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

<title>退货单查看</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/finance/consignee.css" />

<script type="text/javascript">
var basePath="<%=basePath%>";

$(function(){
var num = 0;
var money = 0;
$(".num").each(function(){
    num+=Number($(this).text());

});

$(".money").each(function(){
    money+=Number($(this).text());

});

$("#num").text(num);
$("#total").text("￥"+money);


});



</script>
</head>

<body>
	<div class="main">
	<div class="contaner">
		<table  class="tbl" cellspacing="5" cellpadding="5">
           <caption >退款单</caption>
			<tr>
				<td colspan="5" align="right">退款单编号:</td>
				<td>${refundSheet.refundCode}</td>

			</tr>
			<tr>
				<td align="right">公司:</td>
				<td class="line">${refundSheet.companyName}</td>
				<td align="right">订单编号:</td>
				<td class="line">${refundSheet.orderCode}</td>
				<td align="right">下单时间:</td>
				<td class="line">${fn:substring(refundSheet.orderDate,0,19)}</td>
			</tr>
			<tr>
				<td align="right">用户姓名:</td>
				<td class="line">${refundSheet.userName}</td>
				<td align="right">用户账号:</td>
				<td class="line">${refundSheet.userAccount}</td>
				<td align="right">会员类型:</td>
				<td class="line">${refundSheet.vipType}</td>
			</tr>
			<tr>
				<td align="right">退款类型:</td>
				<td class="line">
				        <s:if test='refundSheet.refundType=="00"'>仅退款</s:if>
						<s:if test='refundSheet.refundType=="01"'>退款并退货</s:if>
				</td>
				<td align="right">退货原因:</td>
				<td class="line">${refundSheet.refundCause}</td>
				<td align="right">退款金额:</td>
				<td class="line">${refundSheet.refundMoney}</td>
			</tr>
			<tr>
				<td align="right">退款说明:</td>
				<td colspan="3" class="line">${refundSheet.refundRemark}</td>
				<td align="right">退款数量:</td>
				<td class="line">${refundSheet.refundNum}</td>

			</tr>
			<tr>
				<td align="right">退款凭证:</td>
				<td class="line" colspan="5">
				<s:if test='refundSheet.voucherfile1!=null&&refundSheet.voucherfile1!=""'><img src="<%=basePath%>${refundSheet.voucherfile1}" title="单击放大" width="100" height="100" onclick="lookImage('${refundSheet.voucherfile1}');"/></s:if>
                <s:if test='refundSheet.voucherfile2!=null&&refundSheet.voucherfile2!=""'><img src="<%=basePath%>${refundSheet.voucherfile1}" title="单击放大" width="100" height="100" onclick="lookImage('${refundSheet.voucherfile2}');"/></s:if>
                <s:if test='refundSheet.voucherfile3!=null&&refundSheet.voucherfile3!=""'><img src="<%=basePath%>${refundSheet.voucherfile1}" title="单击放大" width="100" height="100" onclick="lookImage('${refundSheet.voucherfile3}');"/></s:if>
				
				</td>
				
			</tr>
			<tr>
				<td align="right">退货申请时间:</td>
				<td colspan="5" class="line">${fn:substring(refundSheet.refundDate,0,19)}</td>

			</tr>
			
		    <tr>
				<td align="right">状态:</td>
				<td  class="line">
						<s:if test='refundSheet.refundstate=="00"'>买家提交退款申请</s:if>
						<s:if test='refundSheet.refundstate=="01"'>卖家同意退款</s:if>
						<s:if test='refundSheet.refundstate=="02"'>卖家拒绝退款</s:if>
						<s:if test='refundSheet.refundstate=="03"'>卖家确认收货</s:if>
						<s:if test='refundSheet.refundstate=="04"'>卖家已银行打款</s:if>
				
				
				</td>
				<td align="right">处理时间:</td>
				<td  class="line">${fn:substring(refundSheet.refundDate,0,19)}</td>

			</tr>
			
			  <tr>
				<td align="right">退货地址:</td>
				<td  colspan="3" class="line">${refundSheet.refundAddress}</td>
				<td align="right">邮编:</td>
				<td  class="line">${postcode}</td>

			</tr>
			
			<tr>
				<td align="right">接收人电话:</td>
				<td  class="line">${refundSheet.receiverTel}</td>
				<td align="right">接收人名字:</td>
				<td  class="line">${refundSheet.receiverName}</td>
				<td align="right">接收仓库编号:</td>
				<td  class="line">${refundSheet.warehouseCode}</td>

			</tr>
			
			<tr>
				<td align="right">退货快递:</td>
				<td  class="line">${refundSheet.express}</td>
				<td align="right">运单号:</td>
				<td  class="line">${refundSheet.waybillno}</td>
				<td align="right">运费:</td>
				<td  class="line">${refundSheet.freight}</td>

			</tr>
           


		</table>
		<table class="detail">
			<thead>
				<tr class="thead" style="background:#ffffff;">
					<th>序号</th>
					<th>产品编号</th>
					<th>产品名称</th>
					<th>产品批次号</th>
					<th>单价</th>
					<th>数量</th>
					<th>折扣</th>
					<th>总金额</th>
					

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="list">
				<tr>
					<td><%=number%></td>
					<td>${goodsNum}</td>
					<td>${goodsName}</td>
					<td>${batchNumber}</td>
					<td>${price}</td>
					<td class="num">${quantity}</td>
					<td>${rebate}</td>
					<td class="money">${money}</td>
					

				
				</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<table class="total" cellspacing="5" cellpadding="5">
			<tr>
				<td>产品数量：</td>
				<td id="num"></td>
				<td>产品总金额：</td>
				<td id="total"></td>
			</tr>
			
		</table>
	</div>
	</div>
</body>
</html>
