<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/sealvideo.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">
   var basePath = "<%=basePath%>";



    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li >
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li>
          签约人脸识别视频
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <c:if test="${videoUrl ne null&&videoUrl ne ''}">
    <c:if test="${fn:contains(videoUrl,'http')}">
        <video src="${videoUrl}" controls="controls" loop="loop">
            您的浏览器版本低，不支持视频播放。
        </video>

    </c:if>
        <c:if test="${!fn:contains(videoUrl,'http')}">
        <video src="<%=basePath%>${videoUrl}" controls="controls" loop="loop">
            您的浏览器版本低，不支持视频播放。
        </video>
        </c:if>
    </c:if>
    <c:if test="${videoUrl eq null||videoUrl eq ''}">

             <p> 这次签约无人脸视频</p>

    </c:if>


</div>


</body>

</html>
