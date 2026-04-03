<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="height: 100%;">
  <head>
    <base href="<%=basePath%>">
    
    <title>注册错误页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0"   user-scalable=no  />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="height: 100%;">
    <div class="404error" style="text-align: center;background-color: #fff;height: 100%;">
        <div style="position: absolute;top: 20%;">
            <img src="<%=basePath%>images/zhuceError.png" style="width: 60%;">
            <p style="font-size: 0.8rem;">您的推荐人微分金APP不是最新版本，</p>
            <p style="font-size: 0.8rem;">请联系ta更新至最新版本，重新扫码注册。</p>
        </div>
    </div>

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
