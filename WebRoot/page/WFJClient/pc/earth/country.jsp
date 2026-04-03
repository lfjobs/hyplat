<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/earth/earth.css?version=20230709">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/WFJClient/pc/package/css/swiper.css"/>
    <%--    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet">--%>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <%--        <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/package/js/swiper.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>/js/WFJClient/pc/earth/earth.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/jweixin-1.5.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/uni.webview.1.5.2.js" type="text/javascript" charset="utf-8"></script>
    <title>国家国旗展示</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .country-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
            gap: 16px;
        }

        .country-item {
            text-align: center;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            background-color: #f9f9f9;
        }

        .country-flag {
            width: 60px;
            height: 45px;
            object-fit: cover;
            margin-bottom: 8px;
        }

        .country-name {
            font-size: 14px;
            color: #333;
        }
    </style>
</head>

<body>
<header>

    <ul class="clearfix">
        <li style="display: flex; align-items: center;">

        </li>
        <li>
            <a href="<%=basePath%>page/ea/main/marketing/supermarket/container/hgmain.jsp">
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_55.png" alt="">
            </a>
        </li>
        <li>

        </li>
    </ul>
</header>
<h2>国家和国旗</h2>
<div class="country-grid" id="countryList"></div>

<script>
    const countries = [
        {name: '中国', code: 'cn'}, {name: '美国', code: 'us'}, {name: '法国', code: 'fr'},
        {name: '德国', code: 'de'}, {name: '英国', code: 'gb'}, {name: '日本', code: 'jp'},
        {name: '韩国', code: 'kr'}, {name: '加拿大', code: 'ca'}, {name: '印度', code: 'in'},
        {name: '巴西', code: 'br'}, {name: '俄罗斯', code: 'ru'}, {name: '澳大利亚', code: 'au'},
        {name: '新西兰', code: 'nz'}, {name: '南非', code: 'za'}, {name: '意大利', code: 'it'},
        {name: '西班牙', code: 'es'}, {name: '葡萄牙', code: 'pt'}, {name: '荷兰', code: 'nl'},
        {name: '比利时', code: 'be'}, {name: '瑞士', code: 'ch'}, {name: '瑞典', code: 'se'},
        {name: '挪威', code: 'no'}, {name: '丹麦', code: 'dk'}, {name: '芬兰', code: 'fi'},
        {name: '波兰', code: 'pl'}, {name: '奥地利', code: 'at'}, {name: '爱尔兰', code: 'ie'},
        {name: '匈牙利', code: 'hu'}, {name: '捷克', code: 'cz'}, {name: '斯洛伐克', code: 'sk'},
        {name: '希腊', code: 'gr'}, {name: '土耳其', code: 'tr'}, {name: '以色列', code: 'il'},
        {name: '阿联酋', code: 'ae'}, {name: '埃及', code: 'eg'}, {name: '尼日利亚', code: 'ng'},
        {name: '墨西哥', code: 'mx'}, {name: '阿根廷', code: 'ar'}, {name: '智利', code: 'cl'},
        {name: '哥伦比亚', code: 'co'}, {name: '秘鲁', code: 'pe'}, {name: '乌克兰', code: 'ua'},
        {name: '哈萨克斯坦', code: 'kz'}, {name: '蒙古', code: 'mn'}, {name: '越南', code: 'vn'},
        {name: '泰国', code: 'th'}, {name: '马来西亚', code: 'my'}, {name: '印度尼西亚', code: 'id'},
        {name: '菲律宾', code: 'ph'}, {name: '新加坡', code: 'sg'}, {name: '巴基斯坦', code: 'pk'},
        {name: '伊朗', code: 'ir'}, {name: '沙特阿拉伯', code: 'sa'}, {name: '卡塔尔', code: 'qa'},
        {name: '冰岛', code: 'is'}, {name: '摩洛哥', code: 'ma'}, {name: '突尼斯', code: 'tn'},
        {name: '肯尼亚', code: 'ke'}, {name: '坦桑尼亚', code: 'tz'}, {name: '乌干达', code: 'ug'}
    ];

    const container = document.getElementById('countryList');

    countries.forEach(c => {
        const item = document.createElement('div');
        item.className = 'country-item';

        const img = document.createElement('img');
        img.className = 'country-flag';
        img.src = 'https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/6.6.6/flags/4x3/' + c.code + '.svg';
        const name = document.createElement('div');
        name.className = 'country-name';
        name.innerText = c.name;

        item.appendChild(img);
        item.appendChild(name);
        item.onclick = () => {
            // 点击时更新首页显示
            localStorage.setItem("国家", name.innerText);
            // 跳转到首页
            window.location.href = '<%=basePath%>/ea/earth/ea_earthIndex.jspa';
        };
        container.appendChild(item);

    });
</script>

</body>
</html>


