<!DOCTYPE html>
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
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>我的订单</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="#;"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">我的订单</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="header">
    <ul class="rec_head order_head">
        <li class="active all_order">全部订单</li>
        <li class="obligation">待付款</li>
        <li class="overhang">待发货</li>
        <li class="pra">待收货</li>
    </ul>
</div>
<div class="content_hidden">
    <div class="content rec_content">
        <div class="rec_con">
            <!--全部订单-->
            <div class="rec_eva" id="all_order" >
            </div>
            <!--待付款-->
            <div class="rec_eva" id="obligation" >
            </div>
            <!--待发货-->
            <div class="rec_eva" id="overhang" >
            </div>
            <!--待收货-->
            <div class="rec_eva" id="pra" >
            </div>
        </div>
    </div>
</div>



    <script type="text/javascript" src="<%=basePath%>js/ea/finance/NewPhoneOrders/sellerOrder/MyOrders.js"></script>

</body>
</html>
