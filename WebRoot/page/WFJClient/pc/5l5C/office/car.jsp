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
    <title>车辆管理</title>
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
                    车辆
                </li>
            </ul>
        </header>
        <div class="outer_div">
            <div class="box_div" id="myDiv">
                <div class="text-container">新车</div>
                <div class="text-container">上户</div>
                <div class="text-container">证件</div>
                <div class="text-container">维修保养</div>
                <div class="text-container">油耗</div>
                <div class="text-container">燃气</div>
                <div class="text-container">保险</div>
                <div class="text-container">NCG</div>
                <div class="text-container">委托书</div>
                <div class="text-container">财务</div>
            </div>
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

    // 获取所有div容器
    const textContainers = document.querySelectorAll('.text-container');

    // 为每个容器添加点击事件监听器
    textContainers.forEach(container => {
        container.addEventListener('click', function() {
            // 移除其他div的下划线和颜色类
            textContainers.forEach(otherContainer => {
                otherContainer.classList.remove('underline');
            });
            // 给当前点击的div添加下划线和颜色类
            this.classList.add('underline');
            var text = container.textContent;
            switch (text) {
                case '新车':
                    break;
                case '上户':
                    window.location.href = "<%=basePath%>page/WFJClient/pc/5l5C/office/carChanges.jsp";
                    break;
                case '证件':
                    break;
                case '维修保养':
                    break;
                case '油耗':
                    break;
                case '燃气':
                    break;
                case '保险':
                    break;
                case 'NCG':
                    break;
                case '委托书':
                    break;
                case '财务':
                    break;
            }
        });
    });
</script>
</body>
<style type="text/css">
    * {
        margin: 0;
        padding: 0;
    }

    .outer_div {
        width: 100%;
        overflow-x: auto;
    }

    .outer_div .box_div {
        height: 100px;
        white-space: nowrap;
        /* 元素之间不会有空隙 */
        font-size: 0;
    }

    .outer_div .box_div div {
        font-size: 16px;
        box-sizing: border-box;
        white-space: normal;
        word-wrap: break-word;
        word-break: break-all;
        overflow: hidden;
        display: inline-block;
        height: 30px;
        margin-left: 6px;
        margin-top: 4px;
        margin-right: 6px;
    }

    .underline {
        text-decoration: underline;
        text-underline-offset: 8px;
        text-decoration-color: red; /* 设置下划线颜色为红色 */
        -webkit-text-decoration-color: red; /* 针对Safari浏览器的兼容性设置 */
        color: #00A854; /* 豆绿色 */
    }
</style>
</html>
