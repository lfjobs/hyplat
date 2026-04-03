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
    <script src="<%=basePath%>js/ea/collage/dsp/dsp-author-homepage.js" type="text/javascript" charset="utf-8"></script>

    <title>${video}</title>
</head>
<body class="h-full">
<!-- 头部 -->

<div class="relative text-white bg-black"
     STYLE="background-image: url(https://img2.baidu.com/it/u=1771027537,407852464&fm=253&fmt=auto&app=138&f=GIF?w=700&h=400)">

    <div class="p-4">
        <a onclick="javascript: window.history.go(-1);return false;" target="_self">
            <img class="w-4" src="<%=basePath%>images/ea/office/contract/selectp/return.png">
        </a>
    </div>

    <div class="p-8 flex flex-row space-x-2">
        <img class="rounded-full w-16" src="<%=basePath%>${profile["headImage"]}"
             onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"'>
        <div class="m-auto">
            <div class="text-2x font-bold">
                ${profile["staffName"]}
            </div>
            <div class="text-sm font-thin">
                手机号: ${profile["account"]}
            </div>
        </div>
    </div>
</div>

<div class="h-full w-full rounded-lg p-2 space-y-4">

    <div class="space-x-4">
        <span class="text-1x"><b>${profile["gznum"]}</b>关注</span>
        <span class="text-1x"><b>${profile["fsnum"]}</b>粉丝</span>
    </div>

    <c:if test="${not empty  profile['brief']}">
        <div>
          ${profile["brief"]}
        </div>
    </c:if>

    <c:if test="${empty  profile['brief']}">
        <div class="text-sm">
            谢谢你的关注
        </div>
    </c:if>

    <div class="font-bold">作品</div>

    <div class="ul-list flex flex-row flex-wrap" id="ttsw_list">
    </div>
</div>

</body>
<script type="text/javascript">
    var pageNumber = 1
    var pageSize = 20
    var totalPages = 1
    var basePath = "<%=basePath%>";
    var videoStaffId = "${profile.staffId}";
    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
</script>
</html>