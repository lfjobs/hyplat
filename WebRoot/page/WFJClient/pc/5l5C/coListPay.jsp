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
    <title>收款码-公司列表</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/selectCompany.css?version=1">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdn.jsdelivr.net/npm/qrcode@1.5.1/build/qrcode.min.js"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/coListPay.js" type="text/javascript" charset="utf-8"></script>
    <!-- ======================
         按钮样式（与系统兼容）
         ====================== -->
    <style>
        /* 顶部菜单容器 */
        .top-menu {
            display: flex;
            justify-content: center;
            gap: 0.3rem; /* 按钮间距 */
            padding: 0.3rem 0;
            background: #ffffff;
            border-bottom: 0.3rem solid #eee;
        }

        /* 菜单按钮样式 */
        .top-menu button {
            padding: 0.8rem 1.3rem;
            font-size: 1rem;
            border: none;
            border-radius: 0.3rem;
            background: #f5f5f5;
            color: #333;
            cursor: pointer;
            transition: all 0.25s ease;
            white-space: nowrap;
        }

        /* 鼠标悬浮效果 */
        /*.top-menu button:hover {
            background: #007bff;
            color: #fff;
            transform: translateY(-1rem);
            box-shadow: 0 3rem 6rem rgba(0, 123, 255, 0.15);
        }*/

        /* 点击效果 */
        .top-menu button:active {
            /*transform: translateY(0);*/
            background: #007bff;
            color: #fff;
            box-shadow: 0 3rem 6rem rgba(0, 123, 255, 0.15);
        }

        /* 搜索框容器（和你图片样式一致） */
        .search-box {
            padding: 0.5rem 1rem;
            display: none; /* 默认隐藏 */
        }

        .search-wrapper {
            display: flex;
            align-items: center;
            height: 3rem;
            background: #f5f5f5;
            border-radius: 0.5rem;
            overflow: hidden;
        }

        /* 搜索图标 */
        .search-icon {
            padding: 1rem 1rem;
            font-size: 1.8rem;
            color: #fff;
            background-color: #f74c31;
        }

        /* 输入框 */
        .search-input {
            padding: 0.4rem;
            flex: 1;
            border: none;
            background: transparent;
            outline: none;
            font-size: 0.9rem;
            color: #333;
        }

        .search-input::placeholder {
            color: #aaa;
        }

        /* 搜索按钮 */
        /*.search-btn {
            width: 4rem;
            height: 100%;
            border: none;
            !*background: #ff0000;*!
            background: #f74c31;
            color: #fff;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
        }*/

        /* 按钮容器 */
        .company-btn-group {
            display: inline-block;
            margin-bottom: 0.5rem;
            vertical-align: middle;
        }

        /* 通用按钮样式 */
        .company-btn {
            padding: 0.5rem 1rem;
            font-size: 1rem;
            border-radius: 0.3rem;
            cursor: pointer;
            border: none;
            margin-left: 5rem;
        }

        /* 商城按钮 */
        .shop-btn {
            background-color: #0099ff;
            color: #fff;
        }

        .shop-btn:hover {
            background-color: #0088ee;
        }

        /* 二维码按钮 */
        .qrcode-btn {
            background-color: #55c900;
            color: #fff;
        }

        .qrcode-btn:hover {
            background-color: #48b300;
        }

        /* ======================
           2. 二维码弹框样式
           ====================== */
        .qrcode-modal {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 9999;
        }

        .qrcode-box {
            background: #fff;
            padding: 2.5rem;
            border-radius: 3rem;
            text-align: center;
            position: relative;
        }

        .qrcode-close {
            position: absolute;
            top: 5rem;
            right: 3rem;
            font-size: 1rem;
            cursor: pointer;
            color: #666;
        }

        .qrcode-img {
            width: 22rem;
            height: 22rem;
            margin: 1rem 0;
        }

        .li-list {
            display: flex;
            align-items: center;
            justify-content: space-around;
        }

        .li-list * {
            padding: 0 !important;
            margin: 0 !important;
        }

        .li-list .img-left {
            width: 3.4rem;
            height: 3.4rem;
            float: none !important;
        }

        .li-list .div-right {
            width: 60% !important;
            padding: 0.5rem 0 !important;
        }

        .li-list .company-btn-group {
            width: 10% !important;
            text-align: center;
        }

        .li-list .company-btn-group button {
            background-color: #fff;
            padding: 0.25rem;
        }

        .li-list .company-btn-group img {
            width: 1.65rem;
            height: 1.65rem;
        }

        .li-list .company-btn-group img.img-top {
            padding-top: 0.6rem !important;
            padding-bottom: 0.6rem !important;
        }

        .li-list .company-btn-group img.img-bottom {
            width: 1.9rem;
            height: 1.9rem;
        }
    </style>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    选择公司
                </li>
                <li>

                </li>
            </ul>
        </header>
        <!-- 顶部菜单 -->
        <div class="top-menu">
            <button id="searchBtn">搜出公司付款码</button>
            <button id="collectBtn">收藏付款码</button>
            <button id="itemBtn">付款项目</button>
        </div>
        <!-- 搜索框-->
        <div class="search-box" id="searchBox">
            <div class="search-wrapper">
                <input type="text" class="search-input" name="search" autocomplete="off" placeholder="  搜索企业/公司/商家/名称">
                <%--<button class="search-btn">搜索</button>--%>
                <span class="search-icon" id="search-span">🔍</span>
            </div>
        </div>
        <div class="content" id="content">
            <div class="div-con" style="padding: 1rem;">
                <span style="margin: 1rem;fontSize: 1rem;color: #ff0000">已在职公司</span>
                <ul class="xz"></ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    const basePath = "<%=basePath%>";
    let sccId = "${param.sccId}";
    const bd = "${param.bd}";
    document.addEventListener('DOMContentLoaded', function () {
        // 获取元素
        const searchBtn = document.getElementById('searchBtn');
        const collectBtn = document.getElementById('collectBtn');
        const itemBtn = document.getElementById('itemBtn');
        const searchBox = document.getElementById('searchBox');
        const companyList = document.getElementById('content');


        // 点击 搜出公司付款码
        searchBtn.onclick = function () {
            $(".xz").empty();
            searchBox.style.display = 'block';  // 显示搜索框
            companyList.style.display = 'none'; // 隐藏公司列表
        };

        // 点击 收藏付款码
        collectBtn.onclick = function () {
            searchBox.style.display = 'none';   // 隐藏搜索框
            companyList.style.display = 'block';// 显示公司列表
        };

        // 点击 付款项目
        itemBtn.onclick = function () {
            searchBox.style.display = 'none';   // 隐藏搜索框
            companyList.style.display = 'block';// 显示公司列表
        };

    });

    if (null == sccId || "" == sccId) {
        sccId = '<%= request.getAttribute("sccId")%>';
    }

    // 1. 跳转到商城
    function goShop(comId) {
        document.location.href = basePath + "ea/wfjshop/ea_getcity.jspa?state=0&companyId=" + comId + "&returnPage=01";
    }

    // 2. 显示二维码弹框
    function showQrcode(comId, comName) {
        document.location.href = basePath + "ea/productslaunch/ea_getErWeiMa2.jspa?companyId=" + comId + "&falg=2";
    }

    // 关闭二维码弹框
    function closeQrcode() {
        let modal = document.getElementById('qrcodeModal');
        if (modal) modal.remove();
    }
</script>
</body>
</html>
