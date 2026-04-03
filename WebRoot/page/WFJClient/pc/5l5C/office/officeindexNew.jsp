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
    <title>办公管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/officeindex.css?version=1.1">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li onclick="toBack()">
                <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                </li>
                <li>
                    办公管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">

            <ul class="ul-con">
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/zlplanmanageNew.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
                    <li class="clearfix">
                        <p class="p-title">规划管理</p>
                        <p class="p-height clearfix">
                            办公室
                            品牌文化
                            项目管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/documentManageNew.jsp">
                    <li class="clearfix">

                        <p class="p-title">行政建设</p>
                        <p class="p-height">

                            全部文书
                            文书分类
                            流程
                            文书模板

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/infomanageNew.jsp">
                    <li class="clearfix">
                        <p class="p-title">信息管理</p>
                        <p class="p-height">

                            公共信息
                            三方平台
                            网络安全
                            设置

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/hqinmanageNew.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
                    <li class="clearfix">
                        <p class="p-title">后勤管理</p>
                        <p class="p-height">
                            批发商城 接待管理 考场训场 停车管理
                            资产管理 安全管理 设备管理 物流管理<br>
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/auditmanageNew.jsp">
                    <li class="clearfix">
                        <p class="p-title">督查审批</p>
                        <p class="p-height">
                            督查项目
                            督查巡查
                            审批管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>

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

    //后退
    function toBack() {
        window.location.href = "<%=basePath%>page/WFJClient/pc/5l5C/funclayer.jsp";
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });

</script>
</body>
</html>
