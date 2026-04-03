    <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/zy_sys.css">
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <title>办公系统</title>
</head>
<script type="text/javascript">
/*var staffId='${list[0][2]}';
var companyId='${list[0][3]}';*/
</script>
<body>
    <header class="com_head">
            <a href="<%=basePath %>mobile/office/mobileoffice_toMobileLogin.jspa?sys=sys&flag=yd" class="back"></a>
            <h1>5L5C资源系统</h1>
    </header>
    <div class="wrap_page">
        <div class="zy_home">
            <a href="javascript:;" class="head_img">
            	<s:if test="#request.personalInfo[0][0]==null||request.personalInfo[0][0]==''">
            		<img src="<%=basePath %>/images/BuildPlatform/touxiang.png" alt="">
            	</s:if>
            	<s:else>
            		<img src="<%=basePath %>${personalInfo[0][0]}" alt="">
            	</s:else>                
            </a>
            <div class="head_name">${personalInfo[0][1] }</div>
            <div class="home_list">
                <a href="<%=basePath %>ea/workmessage/ea_taskMessage.jspa">任务消息<b class="info_num"></b></a>
                <a href="###">任务进度</a>
                <a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa?">应用操作</a>
                <a href="###">系统维护</a>
            </div>
        </div>
    </div>
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
</body>
</html>