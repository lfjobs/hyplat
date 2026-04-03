<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

    <script src="<%=basePath%>js/dayjs/dayjs.min.js"></script>
    <script src="<%=basePath%>js/dayjs/locale/zh-cn.js"></script>
    <script src="<%=basePath%>js/dayjs/plugin/customParseFormat.js"></script>
    <script src="<%=basePath%>js/dayjs/plugin/relativeTime.js"></script>

    <script>
        dayjs.locale('zh-cn')
        dayjs.extend(window.dayjs_plugin_customParseFormat)
        dayjs.extend(window.dayjs_plugin_relativeTime)
    </script>

    <script src="<%=basePath%>js/ea/collage/dsp/dsp-comment.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>

<style>
    html, body {
        height: 100%;
    }

    .video-comments {
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;
    }

    .video-comments .toolbar {
        width: 100%;
        font-size: 10pt;
        text-align: center;
        display: flex;
        justify-content: center;
    }

    .toolbar .title {
        width: 100%;
        flex-grow: 1;
        padding: 8px;
    }

    .toolbar .items {
        white-space: nowrap;
        padding: 8px;
    }

    .comments-container {
        flex-grow: 1;
        overflow-y: scroll;
    }

    .item-container {
    }

    .item-wrapper {
        padding: 8px 16px 8px 16px;
        display: flex;
        flex-direction: row;
        align-items: flex-start;
        gap: 16px;
    }

    .item-wrapper .avatar {
        width: 48px;
        height: 48px;
        background: red;
        border-radius: 50%;
    }

    .item-content {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        gap: 4px;
    }

    .item-content .nickname {
        font-size: 10pt;
        color: #808080;
    }

    .item-content .content {
        font-size: 12pt;
        color: #181818;
    }

    .item-content .footer-row {
        font-size: 10pt;
        color: #808080;
    }

    .footer-row {
        display: flex;
        flex-direction: row;
        gap: 16px;
    }

    .footer-row .reply {
        color: #464646;
    }

    .footer-container {
        width: 100%;
    }

    .input-wrapper {
        display: flex;
        padding: 8px;
        gap: 8px;
    }

    .input-wrapper input {
        width: 100%;
        padding: 8px;
        background: #f3f3f3;
        border: none;
        border-radius: 16px;
        color: #131313;
        flex-grow: 1;
    }

    .input-wrapper button {
        padding: 8px;
        background: #ff4f4f;
        border: none;
        border-radius: 16px;
        color: #ffffff;
        text-wrap: nowrap;
    }

    .disabled {
        background: #f3f3f3 !important;
        color: #7e7e7e;
    }

    .toggle-button {
        padding-top: 8px;
        font-size: 8pt;
        color: #313131;
    }

    .toggle-button::before {
        content: '----';
        text-decoration: line-through;
        position: relative;
        color: #d3d3d3;
    }

    /* 回复容器 */
    .replies-container .avatar {
        width: 24px;
        height: 24px;
    }

    .nologin-wrapper {
        font-size: 12pt;
        text-align: center;
        font-weight: bold;
        color: red;
        padding: 8px;
    }

    .nologin-wrapper::after {
        content: ">";
    }

    .no-more {
        font-size: 14pt;
        text-align: center;
        padding: 8px;
    }

    .load-more {
        font-size: 10pt;
        text-align: center;
        padding: 8px;
    }
</style>
<div class="video-comments">
    <div class="toolbar">
        <span></span>
        <div class="title"><b id="comments-count"></b>条评论</div>
        <div class="items">
            <span class="close">关闭</span>
        </div>
    </div>
    <div class="comments-container">

    </div>
    <div class="footer-container">
        <div class="input-wrapper">
            <input id="comment-input" placeholder="善语结善缘，恶言伤人心">
            <button id="comment-submit" class="disabled" disabled="disabled">发送</button>
        </div>
        <div class="nologin-wrapper" onclick="window.parent.jumpToLogin()">
            登录看更多精彩评论
        </div>
    </div>
</div>

<dialog id="nologin-dialog">
    登錄
</dialog>

</body>
<script type="text/javascript">
    var pageNumber = 1
    var pageSize = 20
    var totalPages = 1
    var totalElements = 0
    var commentList = []
    var basePath = "<%=basePath%>";
    var videoId = "${param.videoId}";
    var videoStaffId = "${param.videoStaffId}";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var staffName = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getNickName():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';

</script>
</html>
