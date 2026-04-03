<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设备管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    设备管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <p class="p-title">人事</p>
                    <p class="p-height clearfix">
                       考勤机 NFC工作证 工作服
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>

                <li class="clearfix">
                    <p class="p-title">办公室</p>
                    <p class="p-height">
                       空调  打印机  电脑  饮水机
                        <a  style="font-size: 16px;" href="<%=basePath%>page/WFJClient/pc/5l5C/office/car.jsp">车辆</a>
                        车闸
                        人闸
                        <a style="font-size: 16px;" href="<%=basePath%>ea/faceDevice/ea_findFaceDeviceInfo.jspa">人脸识别</a> 监控 LED 服务器 路由器 电灯
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>

                <li class="clearfix">
                    <p class="p-title">财务</p>
                    <p class="p-height">
                        收银机 票据打印机
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">生成</p>
                    <p class="p-height">
                        特种作业 挖掘机 装载机 叉车
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">营销</p>
                    <p class="p-height">
                        采购管理 比价审批 收验管理
                        库房管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">设备管理</p>
                    <p class="p-height">
                        基本信息  证件年审  设备变更  设备维护  设备维修  使用记录  充值使用
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
            </ul>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    //计算中间区域宽度
    $(".p-height").each(function () {
        var pWth = $(".pc-box").width() - $(this).prev().width() - 100;
        $(this).width(pWth + "px")
    })
    //计算列表高度
    $(".p-height").each(function () {
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
        var pHei = $(this).parent().outerHeight() - 51;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 50 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
</script>
</body>
</html>
