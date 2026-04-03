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
    <title>自动拨号</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/automaticDialing.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/pc/5L5C/marketing/branch/automaticDialing.js" type="module"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
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
            自动拨号
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" placeholder="搜索">
    </div>
    <div>  <section class="sec-nav sec-hide ">
        <ul class="clearfix center-items">
            <li>
                <div id="qd" class="item item1">
                    <i class="layui-icon" style="font-size: 40px"><img
                            src="<%=basePath%>images/WFJClient/pc/5l5c/qd.png"/></i>
                    <p class="massSending">企业电话</p>

                </div>
            </li>
            <li>
                <div id="gd" class="item item2">
                    <i class="layui-icon" style="font-size: 32px"><img
                            src="<%=basePath%>images/WFJClient/pc/5l5c/gd.png"/></i>
                    <p class="massText">个人电话</p>
                </div>
            </li>
        </ul>
    </section></div>
    <div> <section class="sec-nav sec-hide1 ">
        <ul class="clearfix operate">
            <li class="clearfix import">
                导入电话
            </li>
            <li class="clearfix dialingRecord">
                拨通记录
            </li>
            <li class="clearfix noPhoneCall">
                未通电话
            </li>
            <li class="clearfix intendedCustomers">
                意向客户
            </li>
        </ul>
    </section></div>
    <div> <section class="sec-nav sec-hide1 ">
        <ul class="clearfix operate">
            <li class="clearfix">
                成交客户
            </li>
            <li class="clearfix dx">
                短信
            </li>
            <li class="clearfix">
                录音转文字
            </li>
            <li class="clearfix">
                文字记录
            </li>
        </ul>
    </section></div>


   <div>
    <section class="sec-list" style=" overflow: hidden auto; height: 600px;">
        <div class="div-sec-data">
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>
       </div>
</div>
<script>
    function personalAddressBook(data) {
        pageDisplay(data);
    }
    function pageDisplay(data1) {
        let htmlstr = [];
        let counter = 1;
        let loadContact = JSON.parse(data1);
        loadContact.forEach((data) => {
            htmlstr.push("<div class='contact-item'>");
            // 左侧圆形头像
            htmlstr.push("<div class='avatar-container'>");
            htmlstr.push("<div class='avatar' title='" + data.name + "'>");
            htmlstr.push((data.name || "").charAt(0));
            htmlstr.push("</div>");
            htmlstr.push("</div>");
            // 中间和右侧部分容器
            htmlstr.push("<div class='content-container'>");
            // 中间部分（姓名和电话，在同一行）
            htmlstr.push("<div class='info-container'>");
            htmlstr.push("<div class='name-number'>");
            htmlstr.push("<li class='name'>" + data.name + "</li>");
            htmlstr.push("<li class='number'>" + data.number + "</li>");
            htmlstr.push("</div>");
            htmlstr.push("</div>");
            // 右侧部分（固定标签，在同一行）
            htmlstr.push("<div class='tags-container'>");
            htmlstr.push("<ul class='tags-list'>");
            htmlstr.push("<li>名片</li>");
            htmlstr.push("<li>电话录音</li>");
            htmlstr.push("<li>录音转文字</li>");
            htmlstr.push("<li>文字记录</li>");
            htmlstr.push("</ul>");
            htmlstr.push("</div>");
            htmlstr.push("</div>"); // 结束 content-container
            htmlstr.push("</div>"); // 结束 contact-item
            counter++; // 递增计数器
        });
        const moreData = document.getElementById('more-data');
        if (moreData != null) {
            moreData.remove()
        }
        $(".data-list").html(htmlstr.join(""));
    }
</script>
</body>
</html>