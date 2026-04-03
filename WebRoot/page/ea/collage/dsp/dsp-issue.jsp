<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/collage/dsp/dsp-issue.js?v=2.0" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/aliyun-upload-sdk/aliyun-upload-sdk-1.5.7.min.js"></script>
    <script src="<%=basePath%>js/aliyun-upload-sdk/lib/es6-promise.min.js"></script>
    <script src="<%=basePath%>js/aliyun-upload-sdk/lib/aliyun-oss-sdk-6.17.1.min.js"></script>

    <title>发布小视频</title>
</head>
<body>

<style>
    .cover-picker {
        height: 72px;
        width: 100%;
        overflow: scroll;
        display: flex;
        flex-direction: row;
        list-style: none;
        scrollbar-width: none;
        gap: 4px;
    }

    .cover-picker img {
        width: 56px;
        aspect-ratio: 9/16;
        border-radius: 4px;
    }

    #selected-cover {
        height: 100%;
        width: 100%;
        display: block;
        justify-content: center;
        text-align: center;
    }

    #selected-cover img {
        width: 100%;
        height: auto;
        display: block;
    }

    /* 进度条容器样式 */
    .progress-container {
        width: 100%;
        background-color: #ededed;
        border-radius: 4px;
        overflow: hidden; /* 确保进度条超出容器部分不可见 */
    }

    /* 进度条样式 */
    .progress-bar {
        height: 4px; /* 进度条高度 */
        width: 0; /* 初始宽度为0 */
        background-color: #dc0000; /* 进度条颜色 */
        border-radius: 4px; /* 圆角边框 */
        transition: width 1s ease-in-out; /* 宽度变化动画 */
    }
</style>

<div id="cover-picker-dialog" style="background: rgba(0,0,0); display: none"
     class="absolute text-white m-auto flex flex-col p-2 space-y-4 h-full w-full">
    <div class="text-center flex-none">选择封面</div>
    <div id="selected-cover" class="flex-1">
        <div class="flex flex-col w-128">
            <div class="m-auto">
                <div class="progress-container">
                    <div class="progress-bar"></div>
                </div>
                <div class="text-sm">
                    正在提取视频封面...
                </div>
            </div>
        </div>
    </div>
    <div class="cover-picker flex-none">
    </div>
    <div class="flex-none">
        <div class="flex flex-row space-x-2 w-full">
            <button id="cover-cancel" class="p-2 bg-gray-500 w-full border border w-full rounded">取消上传</button>
            <button id="cover-confirm" class="p-2 bg-red-500 w-full border border w-full rounded">确认封面</button>
        </div>
    </div>
</div>

<div id="products-picker" class="absolute bg-white w-full h-full flex flex-col hidden">
    <div class="text-center flex">
        <div class="p-2 w-full bg-red-500 flex flex-row text-xl text-white">
            <a onclick="$('#products-picker').hide(); return false;" target="_self">
                <img class="w-4" src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
            <div class="grow text-center">选择产品</div>
            <div></div>
        </div>
    </div>

    <div id="products-picker-contianer" class="w-full p-2 grow" style="overflow: scroll; padding-bottom: 60px;">
    </div>

    <div style="position: fixed; bottom: 0; width: 100%; display: flex;">
        <button style="background: red; margin: 10px; padding: 10px; width: 100%; font-size: 12pt; color: white"
                onclick="updateCheckData()">确定产品
        </button>
    </div>
</div>

<div class="p-2 w-full bg-red-500 flex flex-row text-xl text-white">
    <a onclick="javascript: window.history.go(-1);return false;" target="_self">
        <img class="w-4" src="<%=basePath%>images/ea/office/contract/selectp/return.png">
    </a>
    <div class="grow text-center">发布小视频</div>
    <div></div>
</div>

<div id="upload-dialog" class="absolute h-full w-full hidden" style="background: rgba(185,185,185,0.47)">
    <div class="h-full w-full flex flex-col justify-center items-center ">
        <div class="text-center">
            <div class="flex gap-2">
                <p class="text-small">
                    视频上传中
                </p>
                <img style="width: 24px;height: 24px;" src="<%=basePath%>/images/ea/lottery/loading.gif"/>
            </div>
            <div id="auth-progress"></div>
            <%--<div id="status"></div>--%>
        </div>
    </div>
</div>

<form id="issueForm">
    <div class="w-full p-4 space-y-4">

        <div class="space-y-4">
            <div class="w-full flex flex-row space-x-4">
                    <span class="m-auto">
                        视频标题
                    </span>
                <input id="titleName" name="titleName" placeholder="请填写视频标题" class="grow pt-2 pb-2"
                       style="border-bottom: 1px black solid">
            </div>

            <div class="w-full flex flex-row space-x-4">
                    <span class="m-auto">
                        上传编辑
                    </span>
                <div class="grow flex flex-row space-x-2">
                    <img class="rounded-full border-gray-400 border-dashed border-2" style="width: 36px; height: 36px"/>
                </div>
                <span class="text-sm font-bold m-auto" onclick="alert('功能开发中...')">
                    编辑
                </span>
            </div>

            <div class="w-full flex flex-row space-x-4">
                <span class="m-auto">视频上传</span>
                <div class="grow">
                    <img id="cover-preview" class="rounded-full border-gray-400 border-dashed border-2"
                         style="width: 36px; height: 36px"/>
                </div>
                <span class="text-sm font-bold m-auto" id="select-file">上传视频</span>
            </div>
        </div>
        <input id="video-input" type="file" class="bg-blue-200 hidden" accept="video/mp4"/>

        <div class="flex flex-row justify-between">
            <span>
                带货上架
            </span>

            <button type="button" class="text-sm font-bold" id="pick-products">上架产品</button>
        </div>

        <div id="products-container">

        </div>

        <div class="flex flex-row justify-between">
            <span class="text-gray-700 text-small">是否公开</span>
            <div class="space-x-2">
                <input type="radio" name="state" value="00" class="pr-1" checked/><span>公开</span>
                <input type="radio" name="state" value="01" class="pr-1"/><span>不公开</span>
            </div>
        </div>
        <div class="flex flex-row justify-between">
            <span class="text-gray-700 text-small">价格类型</span>
            <div class="space-x-2">
                <input type="radio" name="priceType" value="1" class="pr-1" checked/><span>零售价</span>
                <input type="radio" name="priceType" value="2" class="pr-1"/><span>批发价</span>
            </div>
        </div>

        <div class="w-full">
            <div class="pt-8 pl-2 pr-2">
                <button id="publish-button" type="button"
                        class="w-full w-full bg-red-500 disabled:bg-gray-300 p-2 text-white rounded text-center">
                    发布
                </button>
            </div>
        </div>
    </div>
</form>

</body>
<script type="text/javascript">
    var pageNumber = 1
    var pageSize = 20
    var totalPages = 1
    var basePath = "<%=basePath%>";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
</script>

</html>