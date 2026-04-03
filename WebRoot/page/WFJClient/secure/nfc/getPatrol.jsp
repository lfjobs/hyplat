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
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/getPatrol.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>video/video-js.min.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath%>video/video.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/secure/nfc/getPatrol.js" type="text/javascript"
            charset="utf-8"></script>
    <title>安全巡查-查看</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png">
            </a>
        </li>
        <li>查看详情</li>
    </ul>
</header>
<div class="content">
    <div class="div-name">
        <label for="">安全编号</label>
        <input type="text" value="${inspectVo[3]}" readonly/>
    </div>
    <div class="div-name">
        <label for="">芯片编号</label>
        <input type="text" value="${inspectVo[2]}" readonly id="title"/>
    </div>
    <div class="div-name">
        <label for="">安全类型</label>
        <input type="text" value="<s:iterator value="#request.kind">${kname},</s:iterator>" readonly/>
    </div>
    <div class="div-name">
        <label for="">安全巡查地点</label>
        <input class="emergencyType" type="text" value="${inspectVo[7]}" readonly/>
    </div>
    <div class="div-name">
        <label for="">
            <s:if test="#request.inspectVo[9]==00">红</s:if>
            <s:elseif test="#request.inspectVo[9]==01">黄</s:elseif>
            <s:else>绿</s:else>
        </label>
        <img src="<%=basePath%>images/ea/office/contract/selectp/img_03.png">
    </div>
    <div class="div-name">
        <label for="">巡查结果</label>
        <input type="text" value="${inspectVo[8]}" readonly/>
    </div>
    <div class="div-name">
        <label for="">巡查图片</label>
        <div class="div-img">
            <s:iterator value="#request.Annex">
                <s:if test="fildType=='01'">
                    <img src="<%=basePath%>${fildPath}" class="img-click">
                </s:if>
            </s:iterator>
        </div>
    </div>
    <div class="div-name">
        <label for="">巡查视频</label>
        <div class="div-video">
            <s:iterator value="#request.Annex">
                <s:if test="fildType=='02'">
                    <img src="<%=basePath%>/images/ea/production/drive/k2_top03.png" title="${fildPath}"
                         class="vod-click">
                </s:if>
            </s:iterator>
        </div>
    </div>
    <div class="div-name">
        <label for="">巡查人</label>
        <input type="text" value="${inspectVo[13]}" readonly/>
    </div>
</div>

<div class="div-tingyong div-dqd">
    <div class="box">
        <p>视频播放</p>
        <div class="div-box">
            <video
                    id="my-player"
                    preload="auto"
                    class="video-js"
                    controls
                    webkit-playsinline="true"
                    playsinline="true"
                    x5-playsinline>
                <p class="vjs-no-js">
                    To view this video please enable JavaScript, and consider upgrading to a
                    web browser that
                    <a href="https://videojs.com/html5-video-support/" target="_blank">下一个</a>
                </p>
            </video>
            <div class="clearfix">
                <p class="left previous-p">上一个</p>
                <p class="right latter-p">下一个</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
