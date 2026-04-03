<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
	<title>刷脸支付</title>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/Popt.js"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/citySet.js"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/facePay.js" type="text/javascript"></script>


	<script>
        var basePath = "<%=basePath%>";
        var journalNum = "${param.journalNum}";
        var totalMoney = "${param.totalMoney}";
        var totalNum = "${param.totalNum}";
        var posNum = "${param.posNum}";
        var comID = "${param.comID}";
        var sccId = "${param.sccId}";
        if(comID==""){
            comID="company201009046vxdyzy4wg0000000065";
        }
        var companyName = "${param.companyName}";
        if(companyName==""){
            companyName="溯源互帮";
		}
        var fh = "${param.fh}";
        var address="${param.address}";
        var cardNum = "${param.cardNum}";
        var wxbind = "${param.wxbind}";
        var addressID = "${param.addressID}";
        var  directUrl  = "${param.directUrl}";
        var zf = "${param.zf}";
        $(function(){
            $("#city").click(function (e) {
                SelCity(this, e);
            })
        })
	</script>


</head>
<body>

<section class="code-pay">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header class="h2">
			<img src="<%=basePath%>images/supermarket/header_r2.png" alt="">
			<p>${param.wxbind eq "gw"?"刷脸购物卡支付":"刷脸支付"}<br/>
				<span>请正对镜头扫描脸部进行付款</span></p>
			<a href="javascript: window.history.go(-1);"><input type="button" value="返回"></a>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure class="h3">
			<p class="amount">应付总额：<span>${param.totalMoney}</span>(共${param.totalNum}件)</p>
			<div class="ico">
			    <img src="<%=basePath%>images/supermarket/img3.png" id="alipay">
				<img src="<%=basePath%>images/supermarket/img2.png" id="wxpay">
			</div>
		</figure>
		<!--内容 end-->
		<!--确认收货地址弹框 2019.1.25-->
		<div class="alert_address_" style="display: none;"></div>

		<div class="alert_address" id="t1" style="display: none;">
			<h1>请确认您的收货信息</h1>
			<ul class="text">
				<li>姓名：<span class="name"></span></li>
				<li>电话：<span class="tel"></span></li>
				<li>详细地址：<span class="detail"></span></li>
			</ul>
			<div class="btn">
				<input type="button" value="修改" id="editBtn">
				<input type="button" value="确定" id="confirmBtn">
				<span style="display:none;" class="addressID"></span>
			</div>
		</div>
		<!--填写收货地址弹框 2019.1.25-->
		<div class="alert_address alert_t-address" id="t2" style="display: none;">
			<form id="addressform">
				<h1 class="adtitle">请填写您的收货信息</h1>
				<ul class="text">
					<li>姓名：<input type="text" value="" placeholder="收货人姓名" name="staffAddress.consignee" class="verification name"></li>
					<li>电话：<input type="number" value="" placeholder="收货人联系方式" name="staffAddress.phone" class="verification tel"></li>
					<li>地址：<input type="text" value="" placeholder="请您选择省/市/区" readonly id="city" > </li>
					<li>详细地址：<textarea placeholder="填写详细地址如门牌号" name="staffAddress.addressDetailed" class="verification det"></textarea></li>
				</ul>
				<div class="btn">
					<input type="button" value="确定" onclick="addAddress()">
					<input id="address" type="hidden" name="staffAddress.area" value="">
					<input id="addressID" type="hidden" name="staffAddress.addressID" value="">

					<input id="sccId" type="hidden" name="sccId" value="">
				</div>
			</form>
		</div>
	</article>
	<!--扫码支付内容 end-->
</section>
<div class="mm-alert">
	<div>
		<h1 class="ttip">温馨提示</h1>
		<h5 class="ct"></h5>
		<input type="button" value="确定">
	</div>
</div>
<div class="alert_img" style="display: none;">
	<img src="<%=basePath%>images/supermarket/zfzing.png">
</div>
</body>
</html>