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
var staffid ="${param.staffid}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1);"><img src="<%=basePath%>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">退货单</li>
    </ul>
</div>
        <div class="header">
            <ul class="rec_head">
                <li class="active appraise">退货中</li>
                <li class="successful">退货结束</li>
            </ul>
        </div>
<div class="content_hidden">
    <div class="content rec_content">
        <div class="rec_con">
            <!--退货中-->
            <div class="rec_eva" id="appraise"></div>
            <!--退货结束-->
            <div class="rec_eva" id="successful"></div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=basePath%>js/ea/finance/NewPhoneOrders/sellerOrder/myReturnOrder.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/finance/NewPhoneOrders/sellerOrder/order.js"></script>

</body>
</html>

