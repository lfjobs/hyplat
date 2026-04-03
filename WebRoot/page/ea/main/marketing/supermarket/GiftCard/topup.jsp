<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta charset="UTF-8">
    <title>积分充值</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/supermarket/topup.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/ea/marketing/supermarket/topup.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/qrcode.js" type="text/javascript"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var methodPayment="";
        var jum = "${jum}";
        var staffid = "${staffID}";
        var integral = "${integral}";
        var sccid = "${sccid}";
        var subject = "微分金积分"
        var price="";
        var isflag = "score"
        var coin  ="coin";
        var khd="a";
        var flag="a";
        var identifying="a";
        var ccompanyId="a";
    </script>
</head>
<body style="background: #ccc;">
<div id="topup">
    <h2>
        充值
    </h2>
    <p>
        现有积分：<span></span>
    </p>
    <p>
        充值：
        <input type="text" name="" id="integralNumber" value="" />积分
    </p>
    <div class="address_mil add_address select_bid address_bid sub">
        <div class="title">
            <h4><i style="background-color: #ff5d15;"></i>支付方式</h4>
        </div>
        <ul class="payment_con">
            <li class="active">
                <i class="radio"><span></span></i>
                <input type="hidden" class="payMethod" value="aliPay">
                <img src="<%=basePath%>page/newMyapp/images/ico-alipay.png">
            </li>
            <li>
                <i class="radio"><span></span></i>
                <input type="hidden" class="payMethod" value="weChat">
                <img src="<%=basePath%>page/newMyapp/images/ico-WeChat%20Pay.png">
                <img class="tuijian" src="<%=basePath%>page/newMyapp/images/commend.png">
            </li>
        </ul>
        <div class="showPay">
            <input type="button" value="确认付款" class="payment">
        </div>
        <!--二维码-->
        <div id="code">
            <div>
                <p>总金额：<span></span></p>
                <div id="qrcode"></div>
                <p>微信支付</p>
                <%--<img src="<%=basePath%>/page/newMyapp/images/weChatDesc.png"/>--%>
            </div>
        </div>

    </div>
</div>
</body>
<script type="text/javascript">

</script>
</html>

</html>
