<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>选择支付方式</title>
    <link href="<%=basePath%>page/newMyapp/css/myStyle.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>page/newMyapp/css/shop_jj.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/qrcode.js" type="text/javascript"></script>
    <script src="<%=basePath%>page/newMyapp/js/shop_jj.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/pcwfj/PC_goodsPayMethod.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var payJournalNum ="${param.payJournalNum}";
    	var subject = "${map.subject}";
    	var total_amount = "${map.total_amount}";
    	var body = "${map.body}";
    	var goodsSize = "${map.goodsSize}";
    </script>
</head>
<body>
    <div class="content">
        <!--搜索-->
        <div class="mil search_mil sc_search">
            <a href="#;" class="logo"><img src="<%=basePath%>page/newMyapp/images/logo.png"  class="log"></a>
            <h1>数字地球</h1>
            <img src="<%=basePath%>page/newMyapp/images/top1.png" class="top">
            <img src="<%=basePath%>page/newMyapp/images/top2.png" class="top" style="margin-right: 25px;">
        </div>
        <h5 class="gradient"></h5>
        <div class="address_mil add_address select_bid address_bid">
            <div class="title">
                <h4><i></i>北京天太世统科技有限公司</h4>
            </div>
            <ul class="con_bid payment_con_bid">
	        	<li>
	        		<div class="txt">
	            		<p></p>
	            	</div>
	                	<p class="cost">应付总金额：&yen;<span>${map.total_amount}</span></p>
	        	</li>
            </ul>
            <c:if test="${map.goodsSize>1}">
	            <div class="payment_details">
	                <ul class="">
	                    <li class="look">查看订单详情▼</li>
	                    <li class="pack">收起订单详情▲</li>
	                </ul>
	                <p>${map.body}</p>
	            </div>
            </c:if>
        </div>
        <div class="address_mil add_address select_bid address_bid sub">
            <div class="title">
                <h4><i style="background-color: #ff5d15;"></i>支付方式</h4>
            </div>
            <ul class="payment_con">
                <li  class="active">
                    <i class="radio"><span></span></i>
                    <input type="hidden" class="payMethod" value="aliPay">
                    <img src="<%=basePath%>page/newMyapp/images/ico-alipay.png">
                </li>
                <li >
                    <i class="radio"><span></span></i>
                    <input type="hidden" class="payMethod" value="weChat">
                    <img src="<%=basePath%>page/newMyapp/images/ico-WeChat%20Pay.png">
                    <img class="tuijian" src="<%=basePath%>page/newMyapp/images/commend.png">
                </li>
            </ul>
 			<div class="showPay">
				<input type="button" value="确认付款" class="payment">
			</div>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
           <!--  <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>            
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
</body>
</html>