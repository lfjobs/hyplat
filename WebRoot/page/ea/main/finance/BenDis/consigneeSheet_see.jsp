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

<title>收货单查看</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/finance/consignee.css" />

<script type="text/javascript">

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

//查看详情
function viewDetail(ppID){

window.open("<%=basePath%>ea/prodesign/ea_getEditOrPrevPage.jspa?productPackaging.ppID="+ppID);

}

</script>
</head>

<body>
	<div class="main">
	<div class="contaner">
		<table  class="tbl" cellspacing="5" cellpadding="5">
           <caption >收货单</caption>
			<tr>
				<td colspan="5" align="right">收货单编号:</td>
				<td>${consigneeSheet.consigneCode}</td>

			</tr>
			<tr>
				<td align="right">公司:</td>
				<td class="line">${consigneeSheet.companyName}</td>
				<td align="right">订单编号:</td>
				<td class="line">${consigneeSheet.orderCode}</td>
				<td align="right">下单时间:</td>
				<td class="line">${fn:substring(consigneeSheet.orderDate,0,19)}</td>
			</tr>
			<tr>
				<td align="right">用户姓名:</td>
				<td class="line">${consigneeSheet.userName}</td>
				<td align="right">用户账号:</td>
				<td class="line">${consigneeSheet.userAccount}</td>
				<td align="right">会员类型:</td>
				<td class="line">${consigneeSheet.vipType}</td>
			</tr>
			<tr>
				<td align="right">收货人名称:</td>
				<td class="line">${consigneeSheet.consigneeName}</td>
				<td align="right">收货电话:</td>
				<td class="line">${consigneeSheet.consigneeTel}</td>
				<td align="right">邮编:</td>
				<td class="line">${consigneeSheet.postcode}</td>
			</tr>
			<tr>
				<td align="right">收货地址:</td>
				<td colspan="3" class="line">${consigneeSheet.consigneeAddress}</td>
				<td align="right">收货时间:</td>
				<td class="line">${fn:substring(consigneeSheet.consignneDate,0,19)}</td>

			</tr>
			<tr>
				<td align="right">发货人名称:</td>
				<td class="line">${consigneeSheet.senderName}</td>
				<td align="right">发货电话:</td>
				<td class="line">${consigneeSheet.senderTel}</td>
				<td align="right">发货时间:</td>
				<td class="line">${fn:substring(consigneeSheet.sendDate,0,19)}</td>
			</tr>
			<tr>
				<td align="right">发货地址:</td>
				<td colspan="5" class="line">${consigneeSheet.sendAddress}</td>

			</tr>


		</table>
		<table class="detail">
			<thead>
				<tr class="thead">
					<th>序号</th>
					<th>产品编号</th>
					<th>产品名称</th>
					<th>产品批次号</th>
					<th>单价</th>
					<th>数量</th>
					<th>折扣</th>
					<th>总金额</th>
					<th>操作</th>

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
					<td><a href="javascript:viewDetail('${ppID}');">详情</a></td>

				
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
