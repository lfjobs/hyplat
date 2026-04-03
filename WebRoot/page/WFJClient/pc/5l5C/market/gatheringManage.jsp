<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String companyID = request.getParameter("companyID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>交易管理</title>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/selectCompany.css?version=1">
    <style>
        .page {
            float: right; /* 将页面字段文本右对齐 */
        }

        /* 修改已未收款订单的样式 */
        .all_Order {
            font-size: 18px; /* 设置字体大小为18像素 */
            color: green; /* 设置字体颜色为蓝色 */
            font-weight: bold;
        }

        /* 修改已收款订单的样式 */
        .obligation {
            font-size: 18px; /* 设置字体大小为18像素 */
            color: black; /* 设置字体颜色为绿色 */
            font-weight: bold;
        }

        /* 修改未收款订单的样式 */
        .overhang {
            font-size: 18px; /* 设置字体大小为18像素 */
            color: black; /* 设置字体颜色为红色 */
            font-weight: bold;
        }

        .order_head li {
            height: 30px;
            width: 8.66rem;
            float: left; /* 设置高度为40像素，根据您的需求调整值 */
        }

        hr {
            border-style: dashed;
            border-color: gray;
        }

        .print-button {
            color: black;
            font-weight: bold;
            font-size: 1.0em;
            float: right;

        }

        .order-container {
            max-width: 100%;
            padding: 10px;
            box-sizing: border-box;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* 这里设置阴影的样式 */
            border-radius: 10px 20px 10px 20px;
            margin-bottom: 10px;
        }

        .orderHead {
            display: flex;
            justify-content: space-around; /* 在主轴上平均分配空间 */
            align-items: center; /* 垂直居中 */
            background-color: #f0f0f0; /* 背景颜色，可根据需要调整 */
            margin-bottom: 20px;
        }

        .rec_head {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex; /* 使ul的子元素变为flex项 */
        }

        .rec_head li {
            padding: 10px;
            cursor: pointer;
        }

        .order-number {
            display: inline-block;
            width: 100%;
            margin-bottom: 5px;
            box-sizing: border-box;
        }

        .order-info {
            display: inline-block;
            width: 100%;
            margin-bottom: 5px;
            box-sizing: border-box;
            color: #6C6C6C;
        }

        .total {
            margin-top: -20px;
            margin-bottom: 10px;
        }

        @media (min-width: 5000px) {
            .order-info {
                width: 50%;
                display: block;
            }
        }
    </style>


</head>

<body>
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>收款管理</li>
            </ul>
        </header>
    </div>
</div>

<div class="orderHead">
    <ul class="rec_head order_head">
        <li class="obligation" id="skd" onclick="toggleOrders('skd')">收款单</li>
        <li class="overhang" id="zkd" onclick="toggleOrders('zkd')">支款单</li>
    </ul>
</div>

<div id="skd_content">

</div>
<div id="zkd_content">

</div>


<script>

    $(document).ready(function (){
        // 发送AJAX请求获取数据并填充下拉框
        $.ajax({
            url: "<%=basePath%>" + "ea/bdbill/sajax_phoneSkd.jspa", // 替换为你的后端接口URL
            method: 'POST',
            success: function (data) {
                document.getElementById("skd").style.color = "green";
               // var skdContent = document.getElementById("skd_content");
                var skdContent = $("#skd_content");
                // 渲染订单列表
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    skdContent.html("暂无收款单数据。");
                }
                console.log(data)
            },
            error: function (error) {
                console.error("请求失败！！！");
            }
        });
    })

    function setupScrollListenerSKD() {
        // 监听窗口滚动事件
        $(window).scroll(function () {
            // 当滚动到页面底部时，触发加载更多数据
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                getSKDList(); // 在这里调用getList来加载更多数据
            }
        });
    }

    function setupScrollListenerZKD() {
        // 监听窗口滚动事件
        $(window).scroll(function () {
            // 当滚动到页面底部时，触发加载更多数据
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                getZKDList(); // 在这里调用getList来加载更多数据
            }
        });
    }

    /* 格式化时间 - 毫秒值转换年月日 */
    function formatTime(milliscond) {
        var date = new Date(milliscond);
        var year = date.getFullYear();
        var month = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        var hour = ("0" + date.getHours()).slice(-2);
        var formattedDate = year + "-" + month + "-" + day;
        return formattedDate + " " + hour + ":00";
    }

    function toggleOrders(option) {
        // 隐藏所有订单内容
        document.getElementById('skd_content').style.display = 'none';
        document.getElementById('zkd_content').style.display = 'none';
        // 根据选项显示相应的订单内容
        if (option === 'skd') {
            document.getElementById('skd').style.display = 'block';
            document.getElementById("zkd").style.color = "black";
            document.getElementById("skd").style.color = "green";
            setupScrollListenerSKD();
        } else if (option === 'zkd') {
            document.getElementById('zkd').style.display = 'block';
            document.getElementById("skd").style.color = "black";
            document.getElementById("zkd").style.color = "green";
            setupScrollListenerZKD();

        }
    }
</script>

</body>

</html>
