<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>5L5C</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/orgsystem.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    停车管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">

            <ul class="ul-con">
                <li class="clearfix">
                    <p class="p-title">普通停车</p>
                    <p class="p-height">
                        <a href="<%=basePath%>ea/qrshare/ea_jumpManagement.jspa">出入记录</a>
                        <a href="<%=basePath%>ea/carmanage/ea_feescale.jspa?news=news">收费标准</a>
                        <a href="<%=basePath%>page/WFJClient/pc/5l5C/human/codemanage.jsp">场地管理</a>
                        <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01&sourcePage=00">停车位管理</a>
                        <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01">设备管理</a>
                        <a href="<%=basePath%>page/WFJClient/pc/5l5C/human/codemanage.jsp">收支设置</a>
                        <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01&sourcePage=00">收支余</a>
                    </p>

                </li>


                <li class="clearfix">
                    <p class="p-title">空位停车</p>
                    <p class="p-height">
                        <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01">设备管理</a>
                        <a href="<%=basePath%>page/WFJClient/pc/5l5C/human/codemanage.jsp">收支设置</a>
                        <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01&sourcePage=00">收支余</a>

                    </p>

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
    var  basePath = "<%=basePath%>";
    //计算中间区域宽度
    $(".p-height").each(function(){
        var pWth=$(".pc-box").width()-$(this).prev().width()-80;
        $(this).width(pWth+"px")
    })
    //计算列表高度
    $(".p-height").each(function(){
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
        var pHei=$(this).parent().outerHeight()-51;
        $(this).parent().find(".p-title").css('line-height',pHei+"px");
        $(this).parent().find(".div-more").css('line-height',pHei+50+"px");
    })
    //判断页面是否有底部导航
    if($("*").is(".div-bottom")){
        $(".container").addClass("pc-bottom");
    }
</script>
</body>
</html>
