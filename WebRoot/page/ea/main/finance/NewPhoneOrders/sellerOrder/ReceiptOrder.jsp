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
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
var journalNum ="${param.oaBillId}";
var casid ="${param.casid}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a  href="javascript:history.go(-1);"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">订单详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content rec_content">
        <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleaddress.jsp"/>
       <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleCentral.jsp"/>
       <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleOrderDetails.jsp"/>

    </div>
    
</div>
<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92-40+"px");
        $(".so_shop ul li img").css("height",$(".so_shop ul li img").width()+"px");


        $(".up_btn #up").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #down").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").show();
            $(".code h4").show();
            $(".code .code_").show();
        });
        
    })
</script>
<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>

