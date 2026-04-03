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
    <title>选择支付方式-支付完成</title>
    <link href="<%=basePath%>page/newMyapp/css/shop_jj.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>page/newMyapp/css/myStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>page/newMyapp/js/shop_jj.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
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
        <div class="pay_complete">
            <img src="<%=basePath%>page/newMyapp/images/ico_com.png">
            <h3>支付完成</h3>
            <ul>
            	<li>
                    <p class="tit">已付金额</p><p>：</p><p><span>${total_amount}元</span></p>
                </li>
                <li>
                    <p class="tit">商户订单号</p><p>：</p><p>${out_trade_no}</p>
                </li>
                <li>
                    <p class="tit">支付宝交易号</p><p>：</p><p>${trade_no}</p>
                </li>
                <li>
                    <p class="tit">支付完成时间</p><p>：</p><p>${timestamp}</p>
                </li>
            </ul>
            <c:if test="${isflag!='score'}">
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=05"><input type="button" value="返回商城"></a>
            </c:if>
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
    <div class="alert_"></div>
</body>
</html>