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
    <title>需求发布-成功</title>
</head>
<body>
<s:if test="tle==1">
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>家应急发布</h1>
</header>
</s:if>
<div class="wrap_page" <s:if test='tle==null||tle==0'>style="margin-top:0;" </s:if>>
    <div class="success">
        恭喜您，发布成功！
    </div>
    <div id="m">5秒后关闭页面</div>
</div>
<script>
    var basePath = "<%=basePath%>";
    var staffid="";
    var sccId="";
    var nums = 5;
    let clock = setInterval(doLoop, 1000); //一秒执行一次
    function doLoop() {
        nums--;
        if (nums > 0) {
            $("#m").html(nums + '秒后关闭页面');
        } else {
            clearInterval(clock); //清除js定时器
            $("#m").html('秒后关闭页面');
            nums = 5; //重置时间
            document.location.href = basePath+"page/ea/main/marketing/edmandServe/certificate_Specifics.jsp?staffid="+staffid+"&sccId="+sccId;
        }
    }
</script>
</body>
</html>
