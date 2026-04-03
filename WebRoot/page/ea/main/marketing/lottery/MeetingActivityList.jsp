<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>发布活动列表</title>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/bootstrap.css">
    <link rel="stylesheet" href="http://localhost:8080/hyplat/css/WFJClient/login/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/MeetingActivityList.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>

<header>
    <ul>
        <li style="width: 18%;">
            <a onclick="javascript: window.history.go(-1);return false;" target="_self"  class="back"></a>
        </li>
        <li style="width: 100%;">发布活动列表</li>
        <%--<a href="Creating-activities.html"><li style="width: 13%;"><img src="<%=basePath%>images/ea/lottery/fabu2.png" style="width: 40%;"></li></a>--%>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">

    <div class="content">
        <%--<div class="selection">
            <ul>
                <li><span>全类型</span><img src="<%=basePath%>images/ea/lottery/bottom.png"></li>
                <div class="yinc">
                    <p>全类型1</p>
                    <p>全类型2</p>
                    <p>全类型3</p>
                    <p>全类型4</p>
                </div>
                <li><span>全时段</span><img src="<%=basePath%>images/ea/lottery/bottom.png"></li>
                <div class="yinc">
                    <p>全时段</p>
                    <p>今天</p>
                    <p>明天</p>
                    <p>本周</p>
                    <p>本月</p>
                </div>
                <li><span>全价位</span><img src="<%=basePath%>images/ea/lottery/bottom.png"></li>
                <div class="yinc">
                    <p>全价位</p>
                    <p>收费</p>
                    <p>免费</p>
                </div>
                <div class="clearfix"></div>
            </ul>
        </div>--%>
        <div class="china_grd china_grd2" id="meetingList">
            <div align="center">
                <h3>精选专场</h3>
            </div>
            <hr style="margin: 0 auto;border-top: 1px solid #ddd;width: 95%;">
           <%-- <div class="china_mil">
                <a href="#;">
                    <img src="<%=basePath%>images/ea/lottery/news1.jpg" alt="">
                    <div class="china_mil_txt">
                        <h4>新三版上市，到底该何去何从，内容创业：2016年中巡礼+“新每体”圈行业深蹲盘点</h4>
                    </div>
                    <div class="clearfix"></div>
                    <p>北京朝阳 <span>07/23</span></p>
                </a>
            </div>--%>
        </div>

    </div>
</div>
<!--<script type="text/javascript" src="baidumap/baiduapi.js"></script>-->

<script>
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var t;
</script>
<script src="<%=basePath%>js/ea/marketing/lottery/MeetingActivityList.js"></script>
<script>

</script>
</body>
</html>
