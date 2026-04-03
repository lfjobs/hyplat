<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.flushBuffer();
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>


</head>
<script>
    var basePath = "<%=basePath%>";
    var companyid = "${param.companyid}";
    var journalNum = "${param.oaBillId}";
    var casid = "${param.casid}";
    var staffid = "${param.staffid}";
    var type = "${param.type}";
    var cashierBillsID = "${cashierBillsID}";
    var sort = "${param.sort}";
</script>
<body id="tops">
<div class="header">
    <ul>
        <li style="width: 10%;"><a onclick="dianji()"><img
                src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">订单详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<!-- text-indent: 0.6rem; -->
<div class="content_hidden">
    <div class="content rec_content">
        <!-- 引入外部jsp -->
        <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleaddress.jsp"/>
        <!-- 引入外部jsp -->
        <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleCentral.jsp"/>
        <!-- 引入外部jsp -->
        <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleOrderDetails.jsp"/>
    </div>

    <!-- 引入外部jsp -->
    <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titlebottom.jsp"/>

</div>
<script type="text/javascript" src="<%=basePath%>js/ea/finance/NewPhoneOrders/sellerOrder/order.js"></script>
<script>
    $(document).ready(function () {
        $(".header ul li").css("line-height", $(window).height() * 0.08 + "px");
        $(".header").css("height", $(window).height() * 0.08 + "px");
        $(".content_hidden").css("height", $(window).height() * 0.92 + "px");
        if($(".tjlh .clearfix p").size()>4){
            $(".content").css("height", $(window).height() * 0.88 - 40 + "px");
        }else{
            $(".content").css("height", $(window).height() * 0.92 - 40 + "px");
        }
        console.log($(".tjlh .clearfix p").size())
        $(".so_shop ul li img").css("height", $(".so_shop ul li img").width() + "px");


        $(".up_btn #up").click(function () {
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #down").click(function () {
            $(this).hide().siblings().show();
            $(".mon .txt h5").show();
            $(".code h4").show();
            $(".code .code_").show();
        });
        //  $("#beizhu2").text($("#beizhu").val());
        $("#money2").text($("#money").val());
        if (sort == 2) {
            $("#tops").removeClass("no-header");
        } else {
            $("#tops").addClass("no-header");
        }
    });

    window.onload = window.onresize = function () {
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px'
    }
</script>
</body>
</html>
