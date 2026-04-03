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
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/qd.css">
    <title>设置成功</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>设置成功</h1>
</header>
<div class="wrap_page">
    <div class="suc_wrap">
        <img src="<%=basePath%>images/ea/edmandServe/succes_ico.png" alt="">
        <div class="suc_l1">恭喜您！</div>
        <div class="suc_l2">${insurePerson[0]}为您服务</div>
        <div class="suc_l3">您的抢单服务人员设置成功</div>
    </div>
    <div class="succeed_operation clearfix">
        <a onclick="backPhone(${insurePerson[1]})" class="tell_btn">
            电话联系
        </a>
        <a onclick="backMessage(${insurePerson[1]})" class="msg_btn">
            好友短信
        </a>
    </div>
    <%--<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?user=${account}" class="sure_btn">确定</a>--%>
    <a href="<%=basePath%>/ea/earth/ea_earthIndex.jspa?login=login" class="sure_btn">确定</a>
</div>
<script type="text/javascript">
    function backPhone(num) {
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                if (confirm("确定呼叫?")) {
                    Android.callPhone(num);
                }
            } else if (isiOS == true) {
                if (confirm("确定呼叫?")) {
                    var url = "func=" + 'phone';
                    params = {'phoneNum': num};
                    for (var i in params) {
                        url = url + "&" + i + "=" + params[i];
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            }
        }else {
            alert("无电话号码");
        }
    }
    function backMessage(num) {
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid==true){
                if(confirm("确定发送短信邀请?")){
                    Android.callmsg(num);
                }
            }else if(isiOS==true){
                if(confirm("确定发送短信邀请?")){
                    var url= "func=" + 'iosCallphone';
                    params={'name':num};
                    for(var i in params){
                        url = url + "&" + i + "=" + params[i];
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            }
        }else {
            alert("无电话号码");
        }
    }
</script>
</body>
</html>
