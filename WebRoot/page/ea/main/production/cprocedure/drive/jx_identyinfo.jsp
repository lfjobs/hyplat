<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>身份信息</title>



<link rel="stylesheet" href="<%=basePath%>css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/new_drive.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>
</head>

<body>
<ul class="drive_top">
    <li class="arrow">
        <a href="基本资料_new.html">
            <img src="<%=basePath%>images/ea/production/drive/wfj_return_02.png" alt=""/>
        </a>
    </li>
    <li>
        基本资料
    </li>
    <li class="drive_top_right">
        保存
    </li>
</ul>
<ul class="drive_bottom">
   <li class="lis_b">
       <div>证件号码</div>
       <div><input class="pullInput" type="text" placeholder=""/></div>
   </li>
    <li class="lis_b">
        <div>暂住证号</div>
        <div><input class="pullInput" type="text" placeholder=""/></div>
    </li>
    <li class="lis_b">
        <div>家庭地址</div>
        <div><textarea class="pullInput" placeholder="地址信息"></textarea></div>
    </li>
    <li class="lis_b">
        <div>居住地址</div>
        <div><textarea class="pullInput" placeholder="地址信息"></textarea></div>
    </li>
    <li class="lis_b">
        <div>邮编</div>
        <div><input class="pullInput" type="text" placeholder=""/></div>
    </li>
    <li class="lis_b">
        <div>电话</div>
        <div><input class="pullInput" type="text" placeholder="11位手机号"/></div>
    </li>

</ul>


<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>