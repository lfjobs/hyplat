<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>超市</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
<%--    <link rel="stylesheet" href="<%=basePath%>/css/ea/unmannedsupermarket/searchComponent.css">--%>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/unmannedsupermarket/classifyComponent.css">
    <script src="<%=basePath%>js/ea/unmannedsupermarket/supermarket_home1.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath%>/js/ea/unmannedsupermarket/classifyComponent.js" type="text/javascript"
            charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/unmannedsupermarket/supermarket1.css"/>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var longitude //经度
        var latitude //纬度
        var posNum = "";//社区每个机器的ID

        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
        if (isAndroid) {
            try {
                posNum = Android.forAndroidDeviceId();
            } catch (error) {
                //出错说明不在APP内
            }
        }

        document.addEventListener("DOMContentLoaded", function () {
            const box = document.getElementById("searchBox");
            const input = document.getElementById("searchInput");
            const icon = document.getElementById("searchIcon");
            const title = document.getElementById("gwkc");
            const local = document.getElementById("local");

            // 点击图标展开
            icon.addEventListener("click", () => {
                box.classList.add("expanded");
                title.style.opacity = 0;
                local.style.opacity = 0;
                input.style.display = "block";
                input.focus();
            });

            // 输入框失焦时判断是否要收起
            input.addEventListener("focusout", (e) => {
                // 如果点击的是搜索框内部，不收起
                input.focus();
                if (box.contains(e.relatedTarget)) return;

                // 无内容 → 收起
                if (!input.value.trim()) {
                    box.classList.remove("expanded");
                    input.style.display = "none";
                    title.style.opacity = 1;
                    local.style.opacity = 1;
                }
            });
            const buttons = document.querySelectorAll(".category-buttons p");
            console.log(buttons.length);
            // 默认第一个选中
            if (buttons.length > 0) {
                buttons[0].classList.add("active");
            }

            buttons.forEach(btn => {
                btn.addEventListener("click", function () {
                    // 清除所有按钮的选中状态
                    buttons.forEach(b => b.classList.remove("active"));
                    // 给当前点击的按钮加选中
                    this.classList.add("active");
                });
            });
            document.getElementById("searchInput").addEventListener("keydown", function (e) {
                if (e.key === "Enter") {
                    e.preventDefault();  // 防止键盘默认提交行为
                    console.log("触发搜索");
                    currentType = document.getElementById("searchInput").value.trim();
                    supermarketSearch();
                }
            });
        });
    </script>


</head>
<body>
<header class="clearfix">

</header>

<div class="content">

    <div class="top-fixed">

        <ul class="nav">
            <!-- 左 - 返回按钮 -->
            <li class="nav-left" onclick="history.back()">
                <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/left_jt.png" class="icon-back">
            </li>
            <!-- 搜索框（放在返回右边） -->
            <li class="nav-search">
                <div class="search-wrapper" aria-label="搜索">
                    <div class="search-box" id="searchBox">
                        <div class="search-icon" id="searchIcon" role="button" aria-label="打开搜索">
                            <!-- 简单 SVG 放大镜 -->
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" aria-hidden="true">
                                <path d="M21 21l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                <circle cx="11" cy="11" r="6" stroke="currentColor" stroke-width="2" fill="none"></circle>
                            </svg>
                        </div>

                        <form id="searchForm" action="/search" method="get" style="display:inline-flex; align-items:center; width:100%;">
                            <input
                                    id="searchInput"
                                    class="search-input"
                                    name="q"
                                    type="search"
                                    autocomplete="off"
                                    placeholder="搜索店铺、商品、公司名称"
                                    aria-label="搜索"
                            />
<%--                            <button type="button" id="clearBtn" class="clear-btn hidden" aria-label="清除搜索">✕</button>--%>
                        </form>
                    </div>
                </div>
            </li>
            <!-- 中 - 标题 -->
<%--            <li class="nav-center" id="gwkc">购物快车</li>--%>

            <!-- 右 - 定位按钮 -->
            <li class="nav-right" id="local" onclick="">
                <img src="<%=basePath %>images/WFJClient/local.png" class="icon-locate">
                <span id="locationText"></span>
            </li>
        </ul>


        <div style="border-top: 1px dashed #999; margin: 3px 0;"></div>

        <!-- 筛选菜单 -->
<%--        <ul class="filter-menu">--%>
<%--            <li id="sales">--%>
<%--                <span class="p">最新发布</span>--%>
<%--                <span class="screen">--%>
<%--                <img src="<%=basePath %>images/WFJClient/DigitalMall/down2.png" alt="">--%>
<%--            </span>--%>
<%--                <div class="case">--%>
<%--                    <dd id="newest">最新发布</dd>--%>
<%--                    <dd id="smart">综合排序</dd>--%>
<%--                    <dd id="praise">好评优先</dd>--%>
<%--                    <dd id="plow">价格最低</dd>--%>
<%--                    <dd id="ptop">价格最高</dd>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--            <li id="pop">销量优先</li>--%>
<%--            <li id="screen">筛选分类</li>--%>
<%--        </ul>--%>

<%--        <!-- 分类按钮 -->--%>
        <div class="category-buttons">
            <p id="shipin" onclick="resetAndLoad('食品')">食品</p>
            <p id="canyin" onclick="resetAndLoad('餐饮')">餐饮</p>
            <p id="jiaxiao" onclick="resetAndLoad('驾校')">驾校</p>
            <p id="chaoshi" onclick="resetAndLoad('超市')">超市</p>
            <p id="wm" onclick="resetAndLoad('外卖')">外卖</p>
            <p id="gd" onclick="showMore()">更多</p>


        </div>

    </div>
    <div class="nest_page" style="background: #f3f3f3;">
        <div class="nest_hd">
            <a href="###" class="nest_back"></a>
            <span>选择行业类别</span>
        </div>
        <div class="nest_bd"></div>
    </div>
    <div class="content_hidden">
        <div class="content intro">
            <div class="con_shop">
                <ul>
                </ul>
            </div>
        </div>
    </div>
    <!-- 商品列表 -->
    <div id="supermarketBox" class="scroll-box">
        <!-- 商品列表项动态加载 -->
    </div>
    <div class="overlay_text">
        <span>正在加载，请稍候……</span>
    </div>

    <div class="menu-modal" id="menu-modal">
        <div class="menu-content" id="menu-content">

            <!-- 固定头部 -->
            <div class="menu-header">
                <span class="menu-back" onclick="hidewin()">返回</span>
            </div>

            <!-- 固定搜索框 -->
            <div class="div-search">
                <input type="text" id="type_search" placeholder="请输入搜索内容">
            </div>

            <!-- 可滚动内容 -->
            <div class="menu-body" id="menu-body">
                <!-- JS 动态内容插入这里 -->
            </div>

        </div>
    </div>


</div>

</body>
</html>
