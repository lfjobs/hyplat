<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>传阅审核</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/inventoryAudit.css"/>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBasicStyle.css" rel="stylesheet">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/inventoryAudit.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyId = "${param.companyId}";
        var staffID = "${param.staffID}";
        var menuId = "${menuId}";
        var pageCount = ${pageForm.pageCount==null?0:pageForm.pageCount};
        var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
        var pageSize = ${pageForm.pageSize==null?20:pageForm.pageSize};
        var pageNumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    </script>

    <style>
        html, body {
            height: 100%;
            overflow: hidden;
        }

        .clear {
            clear: both;
        }

        header {
            background-color: #f74c32;
        }

        header ul {
            padding: 0 .5rem
        }

        header ul li {
            float: left;
            height: 2rem;
            line-height: 2rem;
            font-size: 0.7rem;
            color: #ffffff;
        }

        header ul li:first-of-type {
            width: 20%
        }

        header ul li:first-of-type img {
            width: .6rem
        }

        header ul li:nth-of-type(2) {
            width: 58%;
            text-align: center
        }

        header ul li:nth-of-type(3) {
            width: 20%;
            text-align: right;
            font-size: .6rem
        }

        .category input[type="text"] {
            height: 1.8rem; /* 设置输入框高度 */
        }

        .category input[type="button"] {
            height: 1.8rem; /* 设置按钮高度 */
        }

        .layui-layer-msg {
            color: #000 !important; /* 强制黑色字体 */
        }

        .layer-msg {
            color: #333 !important;
        }

        .flex-container {
            display: flex;
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .flex-container li {
            padding: 10px 20px;
            cursor: pointer;
            margin-right: 10px;
        }

        /* 默认状态 */
        .flex-container p {
            color: #333; /* 默认文字颜色 */
            margin: 0;
        }

        /* 激活状态 - 字体变绿色 */
        .flex-container li.active p {
            color: green;
            font-weight: bold; /* 可选：加粗突出显示 */
        }

        .flex-container1.payment2 li.active {
            background-color: #e6f7ff;
            color: #1890ff;
            /*border-bottom: 2px solid #1890ff;*/
        }

        .flex-container1.payment2 li {
            transition: all 0.3s ease;
        }
    </style>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" alt="返回">
            </a>
        </li>
        <li>传阅审核</li>
        <li></li>
    </ul>
</header>
<div class="content">
    <section class="category">
        <div class="box clearfix">
            <label for="search">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png" alt="搜索图标">
            </label>
            <input type="text" name="search" id="search" placeholder="搜索单子">
        </div>
        <div><input type="button" name="qsearch" id="qsearch" value="搜索"></div>
    </section>
    <section class="data-title1" style="display: grid;">

        <ul class="flex-container1 payment2">
            <li class="Payment1" id="paymentItem1" style="height: 0px;line-height: 0px; cursor: pointer;"><p
                    class="isPayment" style="display:none">
                已审已支付
            </p></li>
            <li class="Payment1" id="paymentItem2" style="height: 0px;line-height: 0px; cursor: pointer;"><p
                    class="noPayment" style="display:none">
                已审未支付
            </p></li>

        </ul>


        <ul class="flex-container1">
            <li class="send1" style="height: 0px;line-height: 0px"><p class="send" style="display:none">
                <%--                <img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png" alt="审批图标">--%>
                传阅发送
            </p></li>
            <%--                                        <li><p class="stampReject"><img src="<%=basePath%>images/ea/office/contract/selectp/reject1.png"--%>
            <%--                                                                        alt="驳回图标">驳回</p></li>--%>
        </ul>
    </section>
    <%--    <section class="data-title">--%>
    <%--        <ul class="flex-container">--%>
    <%--            <li><p class="submitAudit">--%>
    <%--&lt;%&ndash;                <img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png" alt="审批图标">&ndash;%&gt;--%>
    <%--                审批--%>
    <%--            </p></li>--%>
    <%--                        <li><p class="stampReject"><img src="<%=basePath%>images/ea/office/contract/selectp/reject1.png"--%>
    <%--                                                        alt="驳回图标">驳回</p></li>--%>
    <%--        </ul>--%>
    <%--    </section>--%>
    <section class="data-title">
        <%--        <ul class="flex-container">--%>
        <%--            <li><p class="noReceivebox">未审</p></li>--%>
        <%--            <li><p class="isReceivebox">已审</p></li>--%>
        <%--            <li><p class="reject">驳回</p></li>--%>
        <%--            <li><p class="isSend">已发送</p></li>--%>
        <%--        </ul>--%>
        <ul class="flex-container">
            <li><p class="allReceivebox">全部审核单</p></li>
            <li><p class="noReceivebox">未审</p></li>
            <li><p class="isReceivebox">已审</p></li>
            <li><p class="payment">支付</p></li>
            <li><p class="reject" style="display: none">驳回</p></li>
            <li><p class="isSend" style="display: none">已发送</p></li>
        </ul>
    </section>

    <section class="sec-list" style="overflow-y:auto ">
        <!-- 数据列表 -->
        <div class="main_hide bug-con" style="overflow-y:auto ">
            <ul class="tj_con">
            </ul>
        </div>
    </section>

</div>
<script>
    // 获取所有 li 元素
    const items = document.querySelectorAll('.flex-container li');

    // 默认激活第一个（未审）
    items[0].classList.add('active');

    // 绑定点击事件
    items.forEach(item => {
        item.addEventListener('click', () => {
            // 移除所有 active 类
            items.forEach(i => i.classList.remove('active'));
            // 给当前点击的加上 active
            item.classList.add('active');
        });
    });
    const paymentItems = document.querySelectorAll('.flex-container1.payment2 .Payment1');
    paymentItems.forEach(item => {
        item.addEventListener('click', function () {
            // 移除所有支付标签的 active 类
            paymentItems.forEach(i => i.classList.remove('active'));
            // 给当前点击的加上 active
            this.classList.add('active');

            // 根据点击的是哪个标签，执行不同的操作
            if (this.querySelector('.isPayment')) {
                console.log('点击了"已审已支付"');
                // 这里可以添加筛选已审已支付数据的逻辑
            } else if (this.querySelector('.noPayment')) {
                console.log('点击了"已审未支付"');
                // 这里可以添加筛选已审未支付数据的逻辑
            }
        });
    });

</script>
</body>
</html>
