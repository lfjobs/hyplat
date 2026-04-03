<%--
  Created by IntelliJ IDEA.
  User: ljc
  Date: 2017/3/14 0014
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <script src="<%=basePath%>js/WFJClient/ProductAgent/companyAgent.js"></script>
    <title>公司招商主页</title>
</head>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var t ;
    var companyId = '${companyId}';
    var flag = '00';
</script>
<body>
<header class="com_head">
    <a href="<%=basePath%>ea/productAgent/ea_proAgentDetail.jspa?ppId=${ppId}" class="back"></a>
    <h1></h1>
</header>
<div class="wrap_page">
    <div class="cm_info_wrap">
        <div class="cm_tit clearfix">
            <img src="" class="cm_img" alt="">
            <div class="cm_text">
                <div></div>
                <div></div>
            </div>
        </div>
       <!-- <p class="cm_info">福州高端食品有限公司位于福建省省会城市福州市中心地段，公司成立于2014年，主营定型包装，散装食品的销售。代理大礁海味系列食品的销售，如7味大礁大黄鱼组合，大礁干贝，天然大礁节虾，野生大礁石斑鱼等原生海味食品。</p>-->
    </div>
    <div class="cm_pro_wrap clearfix">
    </div>
</div>

</body>
</html>
