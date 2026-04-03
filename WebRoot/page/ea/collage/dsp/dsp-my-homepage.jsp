<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page import="net.sf.json.JSONObject" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    JSONObject profile = (JSONObject) request.getAttribute("profile");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css"/>
    <script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/collage/dsp/dsp-my-homepage.js" type="text/javascript" charset="utf-8"></script>

    <title></title>
</head>
<body class="h-full">
<!-- 头部 -->
<div style="position: absolute; margin: 8px; padding: 12px; z-index: 9999; background: rgba(218,218,218,0.54) ; border-radius: 50%; width: 48px; height: 48px;">
    <%--href="<%=basePath%>ea/mycenter/ea_myIndex.jspa"--%>
    <a onclick="window.history.back(); return false;" target="_self">
        <img width="14" src="<%=basePath%>images/ea/office/contract/selectp/return.png"/>
    </a>
</div>

<style>
    .active {
        text-decoration: underline;
        text-underline: black;
        text-underline-offset: 8px;
        text-underline-style: dashed-heavy;
        text-decoration-thickness: 2px;
    }
</style>

<div class="relative w-full h-full">
    <div class="text-white"
         style="background-image: url(https://img2.baidu.com/it/u=1771027537,407852464&fm=253&fmt=auto&app=138&f=GIF?w=700&h=800)">

        <div class="p-8 pt-24 flex flex-row space-x-2">
            <img class="rounded-full w-16" src="<%=basePath%>${profile["headImage"]}"
                 onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"'
                 alt="">
            <div class="m-auto">
                <div class="text-2x font-bold">
                    ${profile["staffName"]}
                </div>
                <div class="text-sm font-thin">
                    ${profile["account"]}
                </div>
            </div>
        </div>
    </div>

    <div class="h-full w-full rounded-lg space-y-2">

        <div class=" p-4 space-y-2">
            <div class="flex flex-row">
                <div class="space-x-4 grow flex text-center">
                    <span class="text-1x">
                        <b>${profile["gznum"]}</b>
                        <p class="text-gray-600">关注</p>
                    </span>
                    <span class="text-1x">
                        <b>${profile["fsnum"]}</b>
                        <p class="text-gray-600">粉丝</p>
                    </span>
                </div>
                <div class="rounded bg-red-500 text-white right-4 bottom-4 m-auto">
                    <a href="<%=basePath%>ea/dsp/ea_issue.jspa">
                        <div class="pl-4 pr-4 pt-1 pb-1 text-white" style="font-size: 1rem">+视频带货</div>
                    </a>
                </div>
            </div>

            <div class="">
                <c:if test="${not empty  profile['brief']}">
                    ${profile["brief"]}
                </c:if>

                <c:if test="${empty  profile['brief']}">
                    点击添加介绍，让大家认识你
                </c:if>
            </div>

            <div class="flex justify-center">
                <div class="w-full text-center active" id="select-tab1">作品</div>
                <div class="w-full text-center" id="select-tab2">私密</div>
                <div class="w-full text-center" id="select-tab3">收藏</div>
                <div class="w-full text-center" id="select-tab4">喜欢</div>
            </div>
        </div>

        <div class="ul-list flex flex-row flex-wrap" id="ttsw_list">
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    var pageNumber = 1
    var pageSize = 20
    var totalPages = 1
    var basePath = "<%=basePath%>";
    var videoStaffId = "<%=profile.getString("staffId")%>";
    var videoStaffName = "<%=profile.getString("staffName")%>";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';

    var localVideoList = [];
</script>
</html>