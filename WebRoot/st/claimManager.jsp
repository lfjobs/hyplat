<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>我的</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/claimManager.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath %>st/js/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<header>
    <menu class="clearfix">
        <li>
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/e_road_express_03.png"></a>
        </li>
        <li>联营驾校</li>
    </menu>
</header>
<div class="kong"></div>
<div class="content">
    <section>
        <a href="#">
            <img src="<%=basePath %>st/images/my_1.png" alt="" />
            <h3>驾校自律招生在线认领</h3>
            <p>（省市县乡村社区）</p>
        </a>
    </section>
    <section>
        <a href="<%=basePath%>/ea/industry/ea_claimCompanyList.jspa">
            <img src="<%=basePath %>st/images/my_2.png" alt="" />
            <h3>认领驾校</h3>
            <p>（省级驾校招生平台）</p>
        </a>
    </section>
    <section>
        <a href="#">
            <img src="<%=basePath %>st/images/my_3.png" alt="" />
            <h3>个人加入</h3>
            <p>（省级驾校招生平台）</p>
        </a>
    </section>
    <section>
        <a href="#">
            <img src="<%=basePath %>st/images/my_4.png" alt="" />
            <h3>进入体验</h3>
            <p>（查看所有驾校平台）</p>
        </a>
    </section>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header menu li").css("height",$(window).height()*0.08-1+"px");
        $("header menu li").css("line-height",$(window).height()*0.08-1+"px");
        $("div.kong").css("height",$(window).height()*0.08-1+"px");
    });
</script>
</html>
