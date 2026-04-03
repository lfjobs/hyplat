<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>转他人付款</title>

<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/supermarket/base.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/supermarket/transferPay.css" />
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.11.3.js" ></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/font-size.js" ></script>
<script type="text/javascript">
    var basePath="<%=basePath%>";

    var showFlag="${showFlag}";
    var showStyle = 1;
    var state="02";
</script>
</head>

<body>
	<%--<header>
		<ul class="clearfix">
			<li><img
				src="<%=basePath%>images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
			</li>
			<li>转他人付款</li>
			<li></li>
		</ul>
	</header>--%>
	<div class="container">
		<ul class="ul-tab clearfix">
			<li>
				<p class="active">公司财务支付</p>
			</li>
			<li>
				<p>个人财务支付</p>
			</li>
		</ul>
		<ul class="ul-tab2 clearfix">
			<li id="gys">
				<p vlaue="01">供应商帮采购商下单</p>
			</li>
			<li id="cgy">
				<p class="active" vlaue="02">采购人员下单</p>
			</li>
		</ul>
		<form id="tpForm" name=tpForm>
		<ul class="ul-con"></ul>
		</form>
		<div class="js">
			<p  onclick="confirm()">提交</p>
		</div>
	</div>
</body>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/supermarket/transferPay.js"></script>
</html>
