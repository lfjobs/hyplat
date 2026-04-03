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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>预约</title>
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/font-size.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/Make_an_appointment.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/office_ea/makeApp/Make_an_appointment.js"></script>

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sccId = '${sccId}';
        var cbId = '${cbId}';
        var  posNum = "";
        var ddid = "${param.ddid}";


    </script>
</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
        </li>
        <li style="width: 80%;">预约成功</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="shop_div">
                <div class="shop_top">
                    <h1>预约成功</h1>
                    <p class="tit">恭喜<span>${ppk.staffName}</span>女士/先生预约成功</p>
                    <p>
                        此预约当天有效，逾期无效，凭此订单到驾校考场计时收费场地入口扫码使用。
                    </p>
                </div>
                <input type="button" value="查看订单" class="check">
            </div>

        </div>
    </div>
</div>


<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.92+"px");
        if(posNum!=null&&posNum!=""){
            $(".check").val("打印小票");

            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

            try {
                if (isAndroid == true) {
                    Android.speechOutputForAndroid("恭喜预约成功，请打印小票");
                } else {
                    console.log("请在安卓设备访问！");
                }
            }catch(error){

            }
        }



    });
</script>
</body>
</html>