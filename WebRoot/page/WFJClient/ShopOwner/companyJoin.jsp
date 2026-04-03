<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>加入平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/join.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/marketing/wfjeshop/companyJoin.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <img src="<%=basePath%>images/WFJClient/ret.png"/>
        </li>
        <li>
          填写公司信息
        </li>
        <li>
            下一步
        </li>
    </ul>
</header>
<div class="content">
    <ul>
        <li>
            <label for="">公司名称</label>
            <input type="text" name="" placeholder="请输入您的公司名称" id="" value="" />
        </li>
        <li>
            <label for="">行业平台</label>
            <input type="text" name="" placeholder="请选择行业平台" id="industry" value="" />
            <img src="<%=basePath%>images/WFJClient/pic_1.png"/>
        </li>
    </ul>
    <ul class="ul_search">
        <li>
            <span class="red">教育</span>培训>音乐培训>吉他
        </li>
        <li>
            <span class="red">教育</span>培训>音乐培训>琵琶
        </li>
        <li>
            <span class="red">教育</span>培训>音乐培训>钢琴
        </li>
        <li>
            <span class="red">教育</span>培训>音乐培训>大提琴
        </li>
        <li>
            <span class="red">教育</span>培训>音乐培训>琵琶
        </li>
        <li>
            <span class="red">教育</span>培训>音乐培训>小提琴
        </li>
    </ul>
</div>
<script>
    var ul_height=$(window).height()-$(".content ul:first-of-type").height() -$("header").height() ;
    $(".ul_search").css("height",ul_height);
</script>
</body>
</html>