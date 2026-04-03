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
    <title>成交客户</title>
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
                <li>成交订单</li>
            </ul>
        </header>
    </div>
</div>

<div class="orderHead">
    <ul class="rec_head order_head">
        <li class="active all_Order" id="all_orders" top="20rem" onclick="toggleOrders('all')">&nbsp;&nbsp;&nbsp;&nbsp;已未收款订单</li>
        <li class="obligation" id="paid_orders" onclick="toggleOrders('paid')">&nbsp;&nbsp;已收款订单</li>
        <li class="overhang" id="unpaid_orders" onclick="toggleOrders('unpaid')">未收款订单</li>
    </ul>
</div>
<div class="total">
    <span>&emsp;</span><span style="color: red" id="totalOrderCount"></span>
    <!-- 添加一个打印按钮 -->
    <button class="print-button" onclick="window.print()">打 印</button>
</div>


<!-- 根据选择切换显示的内容 -->
<div id="all_order_content">
    <!-- 显示所有订单 -->
</div>

<div id="paid_order_content" style="display: none;">
    <!-- 显示已付款订单 -->
</div>

<div id="unpaid_order_content" style="display: none;">
    <!-- 显示未付款订单 -->
</div>


<script type="text/javascript">
    var pageNumber = 0;
    var pageSize = 25;
    window.onload = function () {
        getList();
        // 监听窗口滚动事件
        setupScrollListenerAll();
    }

    function setupScrollListenerAll() {
        // 监听窗口滚动事件
        $(window).scroll(function () {
            // 当滚动到页面底部时，触发加载更多数据
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                getList(); // 在这里调用getList来加载更多数据
            }
        });
    }

    function setupScrollListenerUn() {
        // 监听窗口滚动事件
        $(window).scroll(function () {
            // 当滚动到页面底部时，触发加载更多数据
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                findListUnpaid (); // 在这里调用getList来加载更多数据
            }
        });
    }

    function setupScrollListenerPay() {
        // 监听窗口滚动事件
        $(window).scroll(function () {
            // 当滚动到页面底部时，触发加载更多数据
            if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
                findListPaid(); // 在这里调用getList来加载更多数据
            }
        });
    }

    function getList() {
        var allOrderContent = $("#all_order_content");
        var url = "<%=basePath%>" + "ea/turnovermanage/ordermanage-sajax/sajax_getOrderList.jspa";
        pageNumber = pageNumber + 1
        // 发送Ajax请求
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                companyid: "<%=companyID%>",
                fkStatus: "",
                pageNum: pageNumber, // 传递页码参数
                pageSize: pageSize // 传递每页记录数参数
            },
            success: function (data) {
                // 渲染订单列表
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    allOrderContent.html("暂无订单数据。");
                }
                var orderList = pageForm.list;
                var html = "";
                document.getElementById("totalOrderCount").innerText = "总订单数：" + pageForm.recordCount;
                for (var i = 0; i < orderList.length; i++) {
                    var order = orderList[i];
                    var formattedDateTime = formatTime(order.cashierdate.time);
                    var status = "";
                    if (order.fkStatus == "01" || order.fkStatus == "05"
                        || order.fkStatus == "06" || order.fkStatus == "07") {
                        status = "未收款"
                    } else {
                        status = "已收款"
                    }
                    var textColor = '';
                    if (status === '未收款') {
                        textColor = 'red'; // 未收款状态的文本颜色为红色
                    } else if (status === '已收款') {
                        textColor = 'green'; // 已收款状态的文本颜色为绿色
                    }
                    html += "<div class='order-container' onclick='redirectToDetailPage(\"" + order.cashierBillsID + "\")'>";
                    html += "<p class='order-number'>&nbsp;&nbsp;订单编号：" + order.journalNum + "<span class='status' style='color: " + textColor + ";float: right;'><strong>" + status + "</strong></span></p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;名称：" + order.projectName + "</p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;金额：" + order.priceSub + "<span class='page'>下单时间：" + formattedDateTime + "</span></p>";
                    html += "</div>";
                }
                allOrderContent.append(html);
                pageNumber++;
            },
            error: function () {
                allOrderContent.html("无法加载订单数据。");
            }
        });
    }

    function findListUnpaid() {
        var allOrderContent = $("#unpaid_order_content");
        var url = "<%=basePath%>" + "ea/turnovermanage/sajax_getOrderList.jspa";
        pageNumber = pageNumber + 1;
        // 发送Ajax请求
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                companyid: "<%=companyID%>",
                fkStatus: "01",
                pageNum: pageNumber, //传递页码参数
                pageSize: pageSize // 传递每页记录数参数
            },
            success: function (data) {
                // 渲染订单列表
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    allOrderContent.html("暂无订单数据。");
                }
                var orderList = pageForm.list;
                var html = "";
                for (var i = 0; i < orderList.length; i++) {
                    var order = orderList[i];
                    var formattedDateTime = formatTime(order.cashierdate.time);
                    var status = "";
                    if (order.fkStatus == "01" || order.fkStatus == "05"
                        || order.fkStatus == "06" || order.fkStatus == "07") {
                        status = "未收款"
                    } else {
                        status = "已收款"
                    }
                    html += "<div class='order-container' onclick='redirectToDetailPage(\"" + order.cashierBillsID + "\")'>";
                    html += "<p class='order-number'>&nbsp;&nbsp;订单编号：" + order.journalNum + "<span class='status' style='color:red;float: right;' ><strong>" + status + "</strong></span></p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;名称：" + order.projectName + "</p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;金额：" + order.priceSub + "<span class='page'>下单时间：" + formattedDateTime + "</span></p>";
                    html += "</div>";
                }
                allOrderContent.append(html);
                pageNumber++;
            },
            error: function () {
                allOrderContent.html("无法加载订单数据。");
            }
        });
    }

    function findListPaid() {
        var allOrderContent = $("#paid_order_content");
        var url = "<%=basePath%>" + "ea/turnovermanage/sajax_getOrderList.jspa";
        pageNumber = pageNumber + 1;
        // 发送Ajax请求
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                companyid: "<%=companyID%>",
                fkStatus: "00",
                pageNum: pageNumber, // 传递页码参数
                pageSize: pageSize // 传递每页记录数参数
            },
            success: function (data) {
                // 渲染订单列表
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    allOrderContent.html("暂无订单数据。");
                }
                var orderList = pageForm.list;
                var html = "";
                for (var i = 0; i < orderList.length; i++) {
                    var order = orderList[i];
                    var formattedDateTime = formatTime(order.cashierdate.time);
                    var status = "";
                    if (order.fkStatus == "01" || order.fkStatus == "05"
                        || order.fkStatus == "06" || order.fkStatus == "07") {
                        status = "未收款";
                    } else {
                        status = "已收款";
                    }
                    html += "<div class='order-container' onclick='redirectToDetailPage(\"" + order.cashierBillsID + "\")'>";
                    html += "<p class='order-number'>&nbsp;&nbsp;订单编号：" + order.journalNum + "<span class='status' style='color:green;float: right;'><strong>" + status + "</strong></span></p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;名称：" + order.projectName + "</p>";
                    html += "<p class='order-info'>&nbsp;&nbsp;金额：" + order.priceSub + "<span class='page'>下单时间：" + formattedDateTime + "</span></p>";
                    html += "</div>";
                }
                allOrderContent.append(html);
                pageNumber++;
            },
            error: function () {
                allOrderContent.html("无法加载订单数据。");
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

    function redirectToDetailPage(cashierBillsID) {
        window.location.href = "<%=basePath%>page/WFJClient/pc/5l5C/market/makeOrderDetail.jsp?cashierBillsID=" + cashierBillsID;
    }

    function toggleOrders(option) {
        // 隐藏所有订单内容
        document.getElementById('all_order_content').style.display = 'none';
        document.getElementById('paid_order_content').style.display = 'none';
        document.getElementById('unpaid_order_content').style.display = 'none';
        // 根据选项显示相应的订单内容
        if (option === 'all') {
            document.getElementById('all_order_content').style.display = 'block';
            document.getElementById("paid_orders").style.color = "black";
            document.getElementById("unpaid_orders").style.color = "black";
            document.getElementById("all_orders").style.color = "green";
            document.getElementById("totalOrderCount").style.display = "";
            setupScrollListenerAll();
        } else if (option === 'paid') {
            document.getElementById('paid_order_content').style.display = 'block';
            document.getElementById("paid_orders").style.color = "green";
            document.getElementById("all_orders").style.color = "black";
            document.getElementById("unpaid_orders").style.color = "black";
            document.getElementById("totalOrderCount").style.display = "none";
            findListPaid();
            setupScrollListenerPay();
        } else if (option === 'unpaid') {
            document.getElementById('unpaid_order_content').style.display = 'block';
            document.getElementById("unpaid_orders").style.color = "green";
            document.getElementById("paid_orders").style.color = "black";
            document.getElementById("all_orders").style.color = "black";
            document.getElementById("totalOrderCount").style.display = "none";
            findListUnpaid();
            setupScrollListenerUn();
        }
    }
</script>
</body>

</html>
