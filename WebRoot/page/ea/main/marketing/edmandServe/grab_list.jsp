<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/edit.js"></script>
    <title>选择抢单对象</title>
</head>
<body style="background: #f2f2f2;">
<s:if test="tle==1">
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>发布记录</h1>
</header>
</s:if>
<div class="wrap_page" style="background: #f2f2f2;padding: .8rem .6rem;<s:if test='tle==null||tle==0'>margin-top:0; </s:if>">
    <div class="qd_wrap">
        <s:iterator value="#request.serveList" var="gl">
                <a href="javascript:a('${gl[3]}');" class="qd_box" id="${gl[3]}">
                    <div class="qd_top clearfix">
                        <span>${gl[0]}</span>
                        <span>${gl[1]}</span>
                    </div>
                    <div class="qd_bottom clearfix">
                        <span>${gl[2]}</span>
                        <span>${wtvalue}</span>
                    </div>
                </a>
        </s:iterator>
        <s:iterator begin="#request.serveList.size()+1" end="5">
            <div class="no_qd">等待接单者抢单</div>
        </s:iterator>
    </div>
</div>
<script type="text/javascript">
    var type = "${wtvalue}";
    var sccid = "${dlsccid}";
    var basePath = "<%=basePath %>";
    function a(dsid) {
        //if(type == "服务平台"){
            window.location.href=basePath+"ea/dserve/ea_selQdPerDetail.jspa?dsid="+dsid+"&dlsccid="+sccid+"&type="+type;
        //}else{
        //    alert("功能研发中···");
        //}
    }
</script>
</body>
</html>
