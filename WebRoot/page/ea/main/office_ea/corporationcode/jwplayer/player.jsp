<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<%
String filepath =request.getParameter("path");

String imagepath = request.getParameter("imagepath");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>在线播放</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<script type="text/javascript" src="jwplayer.js"></script>
  </head>
  
  <body>
    <div id="jwplayer" style="background: transparent !important; margin:0; width:100%; height:270px; overflow:hidden;">
	<div id="player"></div>
</div>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var filepath = basePath+"<%=filepath%>";
var imagepath = basePath+"<%=imagepath%>";

	jwplayer("player").setup({
	  skin: "glow.zip",
	  stretching: "fill",
	  flashplayer: "player.swf",
	  image: imagepath,
	  width: 320,
	  height:270,
	  levels: [{file: filepath}]
	});
</script>
</body>

</html>