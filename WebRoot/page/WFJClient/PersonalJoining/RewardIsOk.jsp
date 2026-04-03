<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>&lrm;</title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>\css\WFJClient\RewardStyle.css">
</head>
<body>
<header>
    <ul>
        <li><a href=""></a></li>
        <li>支付成功</li>
        <li></li>
    </ul>
</header>
<section class="reward">
    <article class="succeed">
        <img src="<%=basePath%>/images/WFJClient/PersonalJoining/bg3.png" alt="" class="bg">
        <a href="<%=basePath%>/ea/earth/ea_earthIndex.jspa"><input type="button" value="返回首页"></a>
    </article>
</section>
<script>
    $(document).ready(function(){
    	var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid("支付成功");
            } else if (isiOS == true) {
            	console.log("声音提醒开发中");
            }
        } catch (e) {
            console.log("报错啦");
        }
    });
</script>
</body>
</html>
