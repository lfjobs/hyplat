<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/assiCode/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/assiCode/sfk.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/restaurant/jquery.qrcode.min.js"></script>

    <title>二维码付款</title>


    <script src="<%=basePath%>/js/qrcode.js"></script>


</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    var logoPath = "${contactCompany.logoPath}";
    var text = "";
    var dp = "${dp}";
    if(dp=="2"){
        text = basePath+"ea/restaurant/ea_scancode.jspa?scancode=05${waiterID}&tj=${sccid}&companyId=${companyId}";

    }else{

        text = basePath+"ea/restaurant/ea_scancode.jspa?scancode=06${ppid}&tj=${sccid}&waiterID=${waiterID}&quality=${quality}"
    }

   console.log(text);
    $(function(){
        init();
    });


    function init() {
        var qrcode = $("#qrCodeDiv").qrcode({
            render: "canvas", // 渲染方式有table方式（IE兼容）和canvas方式
            width: 220, //宽度
            height: 220, //高度
            text: text, //内容
            typeNumber: -1,//计算模式
            correctLevel: 2,//二维码纠错级别
            background: "#ffffff",//背景颜色
            foreground: "#000000" //二维码颜色
        });
        var canvas=qrcode.find('canvas').get(0);
        $('#QR').attr('src',canvas.toDataURL('image/jpg'));
        var margin = ($("#QR").height()- $("#qrCodeIco").height()) / 2; //控制Logo图标的位置
        $("#qrCodeIco").css("margin", margin);

        var sum = 0;
        $(".col").each(function(){

            sum+=Number($(this).find(".price").val())*Number($(this).find(".quality").val());

        });
        $(".qr_price").html("<i>￥</i>"+Math.round(sum*100)/100);
    }

</script>


<body style="background: #fafafa;">
<header class="com_head">
    <h1>收付款</h1>
</header>
<div class="wrap_page" style="background: #fafafa;">
    <div class="qr_wrap">


        <div class="qr_img" >

            <img id="qrCodeIco"  src="<%=basePath%>${contactCompany.logoPath!=null?contactCompany.logoPath:'images/WFJClient/PersonalJoining/logo@2x.png'}" style="position: absolute;width:45px; height: 45px;"/>
          <img id="QR"  alt="">
        </div>
        <div id="qrCodeDiv" style="display:none;"></div>

        <div class="qr_text">
            向"${contactCompany.companyName}"付款
        </div>
        <div class="qr_info clearfix">
            <span>合计</span>
            <span class="qr_price"><i>￥</i>168.00</span>
        </div>
    </div>
    <div class="order_detail">
        <div class="o_d_tit">订单详情</div>
        <ul class="order_list">
            <c:forEach items="${list}" var="item">
                <li class="col">

                    <span>${item[0]}</span>
                    <input class="price" type="hidden" value="${item[1]}"/>
                    <input class="quality" type="hidden" value="${item[2]}"/>
                    <span>￥${item[1]} x${item[2]}</span>
                </li>

            </c:forEach>


        </ul>
    </div>
</div>
</body>
</html>

