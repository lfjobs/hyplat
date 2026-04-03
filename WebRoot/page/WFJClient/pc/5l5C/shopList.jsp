<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>账单核心信息</title>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/font-size.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", sans-serif;
        }

        body {
            /* background-color: #f5f5f5; */
            padding: 0.25rem;
        }

        .bill-container {
            /* background: #fff; */
            border-radius: 0.25rem;
            overflow: hidden;
            /* box-shadow: 0 1rem 1rem rgba(0,0,0,0.1); */
        }

        .bill-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.45rem;
            /* border-bottom: 0.5rem solid #eee; */
        }

        .bill-title {
            font-size: 0.9rem;
            font-weight: 500;
            color: #333;
        }

        .share-btn {
            color: #007aff;
            font-size: 0.65rem;
            text-decoration: none;
        }

        .shopping-list-section {
            padding: 0.15rem 0.25rem;
        }

        .section-title {
            display: flex;
            align-items: center;
            font-size: 0.65rem;
            color: #333;
            margin-bottom: 0.5rem;
            padding: 0.25rem;
        }

        .section-title .num {
            display: inline-block;
            width: 0.9rem;
            height: 0.9rem;
            line-height: 0.9rem;
            text-align: center;
            background-color: #007aff;
            color: #fff;
            border-radius: 50%;
            font-size: 0.65rem;
            margin-right: 0.25rem;
        }

        .search-bar {
            display: flex;
            align-items: center;
            border: 0.1rem solid #eee;
            border-radius: 0.5rem;
            padding: 0.2rem;
            margin-bottom: 0.1rem;
        }

        .search-bar input {
            flex: 1;
            border: none;
            outline: none;
            font-size: 0.65rem;
            color: #666;
            padding: 0.25rem;
        }

        .search-bar .search-icon {
            color: #999;
            font-size: 0.8rem;
        }

        .goods-table {
            width: 100%;
            border-collapse: collapse;
        }

        .goods-table th,
        .goods-table td {
            padding: 0.3rem 0.3rem;
            text-align: left;
            font-size: 0.6rem;
            border-bottom: 0.1rem solid #eee;
        }

        .goods-table th {
            color: #666;
            font-weight: 500;
            text-align: center;
        }

        .goods-table td {
            color: #333;
        }

        .goods-table .price {
            color: #333;
        }

        .goods-table .op-btn {
            color: #ff3b30;
            text-decoration: none;
            margin-left: 0.35rem;
        }

        .summary-section {
            /* padding: 0.3rem; */
            /* border-top: 0.5rem solid #eee; */
        }

        .summary-title {
            font-size: 0.7rem;
            color: #333;
            margin-bottom: 0.25rem;
        }

        .summary-item {
            display: flex;
            align-items: center;
            font-size: 0.6rem;
            color: #333;
            margin: 0 0.25rem;
            flex-wrap: wrap;
            padding: 0.25rem 0.35rem;
        }

        .summary-item .dot {
            width: 0.25rem;
            height: 0.25rem;
            border-radius: 50%;
            background-color: #007aff;
            margin-right: 0.25rem;
        }

        .summary-item .total-amount {
            color: #333;
        }

        .summary-item .pay-info {
            color: #007aff;
            margin-left: 10rem;
            padding: 0.45rem;
        }
    </style>
</head>
<body>
<div class="bill-container">
    <!-- 账单头部 -->
    <div class="bill-header">
        <div class="bill-title">账单核心信息</div>
        <a href="#" class="share-btn">分享</a>
    </div>

    <!-- 购物清单区域 -->
    <div class="shopping-list-section">
        <div class="section-title">
            <span class="num">1</span>
            购物清单
        </div>
        <div class="search-bar">
            <input type="text" placeholder="搜索商品">
            <span class="search-icon">🔍</span>
        </div>
        <table class="goods-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>商品名称</th>
                <th>数量/状态</th>
                <th>金额</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${beanList}" var="entity">
                <tr>
                    <td>1</td>
                    <td>${entity[1]}</td>
                    <td>${entity[7]}</td>
                    <td class="price">￥
                        <c:choose>
                            <c:when test="${entity[12]!=null&& entity[10]!=null}">
                                <c:if test="${entity[12]=='00'}"><%--促销活动--%>
                                </c:if>
                                <c:if test="${entity[12]=='01'}"><%--特价活动--%>
                                </c:if>
                                <span class="qian"
                                      id="price">${entity[10]}</span><%--活动价--%>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${entity[11]!=null && cusType=='vip'&&param.posNum eq null}">
                                                                    <span class="qian"
                                                                          id="price">${entity[11]}</span><%--VIP价--%>
                                    </c:when>
                                    <c:otherwise>
                                                                    <span class="qian"
                                                                          id="price">${entity[4]}</span><%--零售价--%>
                                    </c:otherwise>
                                </c:choose>

                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="#" class="op-btn">调价</a>
                        <a href="#" class="op-btn">删除</a>
                    </td>
                </tr>
                <c:forEach items="${ccmap}" var="item">
                    <c:if test="${item.key==entity[0]}">
                        <c:forEach items="${item.value}" var="subitem">
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 订单汇总区域 -->
    <div class="summary-section">
        <div class="section-title">
            <span class="num">2</span>
            订单汇总
        </div>
        <div class="summary-item">
            <span class="dot"></span>
            <span>总件数: <span>0</span>件</span>
        </div>
        <div class="summary-item">
            <span class="dot"></span>
            <span class="total-amount">合计金额: ¥<span>0</span></span>
        </div>
        <div class="summary-item">
            <span class="pay-info">应付¥<span>0</span>元</span>
            <span class="pay-info">未付¥<span>0</span>元</span>
            <span class="pay-info">实付¥<span>0</span>元</span>
        </div>
    </div>
</div>
</body>
</html>
