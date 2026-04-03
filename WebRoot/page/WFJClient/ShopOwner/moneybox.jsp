<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
<base href="<%=basePath%>">
<title>钱盒pay</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/wfjeshop/moneybox.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/WFJClient/NEWjspcss/boxmoney.css"
	type="text/css"></link>

<script type="text/javascript">
   var  basePath="<%=basePath%>";
</script>
</head>
<body>
	<header>
		<ul>
			<li style="width: 10%;" id="back"><img src="<%=basePath%>images/WFJClient/Newjspim/left_jt.png"></li>
			<li style="width: 80%;">支付</li>
			<li style="width: 10%;"></li>
			<div class="clearfix"></div>
		</ul>
	</header>
	<form name="moneyBox" id="moneyBox"  method="post">
	<div class="pay_content">
		<div class="pay_mil-1">
			<img src="<%=basePath%>images/WFJClient/Newjspim/payhezi.png">
			<p>钱盒商务通支付</p>
		</div>
		<hr style="border-top: 1px solid #ddd;width: 90%;margin: 0 auto;">
		<div class="pay_mil-2" align="center">
			<p>${morre}</p>
			<h5>交易金额</h5>
		</div>
		<hr style="border-top: 1px solid #ddd;width: 90%;margin: 0 auto;">
		<div class="pay_mil-3" align="left">
			<img src="<%=basePath%>images/WFJClient/Newjspim/shoukuan.png">
			<div class="pay_mil-3_txt">
				<p>
					银行账户：<span>0200 2107 0902 0118 801（19位）</span>
				</p>
				<p>开户名称：北京天太世统科技有限公司</p>
				<p>开户银行：中国工商银行</p>
				<p>支行：北京东直门支行</p>
				<p>支行地址：北京市东城区东直门外新中街甲2号</p>
			</div>
		</div>
		<hr style="border-top: 1px solid #ddd;width: 90%;margin: 0 auto;">
		<div class="pay_mil-4" align="left">
			<h2>
				收货人：<span>${order.receivename}</span>
			</h2>
			<h3>${order.receivetel}</h3>
			<div class="clearfix"></div>
			<p>
				收货地址<span>：${order.receiveaddress}</span>
			</p>
		</div>
		<hr style="border-top: 1px solid #ddd;width: 90%;margin: 0 auto;">
		<div class="pay_mil-3" align="left">
			<img src="<%=basePath%>images/WFJClient/Newjspim/jiekuan.png">
			<div class="pay_mil-5_txt" align="center" id="main">
				<div>
					<p>姓名</p>
					<input type="text" placeholder="请输入付款人姓名" name="cbills.boxPayName">
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>电话</p>
					<input type="text" placeholder="请输入付款人电话" name="cbills.boxPayTel">
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>发卡行</p>
					<input type="text" placeholder="请输入发卡行，e.g.工行，农行"  name="cbills.backName">
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>卡号</p>
					<input type="text" placeholder="请个输入卡号" name="cbills.userAccountNum">
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>流水号</p>
					<input type="text" placeholder="请输入流水号" name="cbills.trade_no" >
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>交易时间</p>
					<input type="text" placeholder="请输入交易时间"  name="cbills.tradeDate" >
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>盒子SN号</p>
					<input type="text" placeholder="请输入盒子SN号"  name="cbills.snNum">
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<div>
					<p>备注信息</p>
					<input type="text" placeholder="请输入备注信息" name="cbills.remark" >
				</div>
				<hr style="border-top: 1px solid #ddd;width: 100%;margin: 0 auto;">
				<input  type="button"  class="pay_btn"  id="submitPay" value="确认提交"/>
				<input type="hidden" name="ddid"  value="${order.oaBillJounum}"/>
			</div>

		</div>
	</div>

</form>


	<script>
		$(document).ready(
				function() {
					$("header").attr(
							"style",
							"height:" + $(window).height() * 0.08
									+ "px;line-height:" + $(window).height()
									* 0.08 + "px;");
					$(".pay_content").attr(
							"style",
							"margin-top:" + $(window).height() * 0.08
									+ "px;height:" + $(window).height() * 0.92
									+ "px;overflow: auto;");

				});
	</script>

</body>