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
    <title>售后服务</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/afterSales.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .tab-item1 {
            flex: 1;
            text-align: center;
            color: black;
        }

        .tab-item1.active {
            color: rgb(22, 186, 170);;
            font-weight: bold;
        }

        .time li {
            flex: 1;
            text-align: center;
            color: black;
        }
    </style>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            售后服务
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="search searTime" style="display: none">
        <ul class="tabContainer1 time" style="display:flex;font-size: 0.95rem;height: 50px;align-items: center;">
            <li>日期 :</li>
            <li>
                <input type="date" id="startDate" placeholder="开始日期"
                       value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"/>
            </li>
            <li>--</li>
            <li>
                <input type="date" id="endDate" placeholder="结束日期"
                       value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"/>
            </li>
            <li>
                <button onclick="searchByDateRange()">搜索</button>
            </li>
        </ul>
    </section>
    <section class="search">
        <ul class="tabContainer1" style="display:flex;font-size: 0.81rem;height: 50px;align-items: center;">
            <li class="tab-item1 two active">今日</li>
            <li class="tab-item1 two">昨日</li>
            <li class="tab-item1 two">本周</li>
            <li class="tab-item1 two">上周</li>
            <li class="tab-item1 two">本月</li>
            <li class="tab-item1 two">上月</li>
            <li class="tab-item1 two">本季</li>
            <li class="tab-item1 two">上季</li>
            <li class="tab-item1 two">本年</li>
            <li class="tab-item1 two">上年</li>
            <li class="tab-item1 two">自定义</li>
        </ul>
    </section>
    <section class="category">

        <ul class="tabContainer" style="display:flex;font-size: 0.95rem;height: 50px;align-items: center;">
            <li class="tab-item order two">已交费订单</li>
            <li class="tab-item opinion two">客户意见</li>
            <li class="tab-item isProcess two">已处理</li>
            <li class="tab-item noProcess two">未处理</li>
        </ul>
    </section>
    <section class="sec-list">

        <div class="div-sec-data"
             style="overflow-y: hidden;overflow-x: auto; width: 100%; max-height: 80vh; position: relative;">
            <!-- 将标题和列表合并到同一个横向滚动容器中 -->
            <div class="data-scroll-container" style="max-height: calc(100vh - 200px); width: max-content;">
                <!-- 标题：上下 sticky，左右随内容滚动 -->
                <div class="data-title"
                     style="position: sticky; top: 0; z-index: 10; background-color: #f8f8f8; width: 100%;">
                    <ul class="flex-container">
                        <li>序号</li>
                        <li>编号</li>
                        <li>名称</li>
                        <li></li>
                        <li style="display: none">品牌</li>
                        <li style="display: none">有效期</li>
                        <li style="display: none">收费时间</li>
                    </ul>
                </div>

                <!-- 数据列表 -->
                <div class="data-list div-data" style="overflow: auto;font-size: 15px;">
                    <!-- 数据项 -->
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        // 获取所有的.option元素（在当前页面中存在）
        const options = document.querySelectorAll('.tab-item');

        // 默认选中第一个.option元素
        if (options.length > 0) {
            // options[0].style.backgroundColor = '#4a8e4a';
            options[0].style.color = '#16baaa';
            options[0].style.borderBottom = '2px solid #16baaa';
            options[0].style.fontweight = 'bold';
        }

        options.forEach(option => {
            option.addEventListener('click', function () {
                // 清除所有.option元素的背景色
                options.forEach(opt => {
                    opt.style.backgroundColor = '';
                });

                // this.style.backgroundColor = '#4a8e4a';
                this.style.color = '#16baaa';
                options[0].style.borderBottom = '2px solid #16baaa';
                options[0].style.fontweight = 'bold';
            });
        });
    });
</script>
</html>