<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<title>在线播放视频</title>	
	
        
     <!-- player skin -->
   <link rel="stylesheet" type="text/css" href="skin/minimalist.css"/>

   <!-- site specific styling -->
   <style type="text/css">
   body { font: 12px "Myriad Pro", "Lucida Grande", sans-serif; text-align: center; padding-top: 5%; }
   .flowplayer { height:320px; }
   </style>

   <!-- flowplayer depends on jQuery 1.7.1+ (for now) -->
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>

   <!-- include flowplayer -->
   <script type="text/javascript" src="flowplayer.min.js"></script>
  
</head>
<body >

 <!-- the player -->
   <div class="flowplayer" data-swf="flowplayer.swf" data-ratio="0.4167">
      <video>
         <source type="video/webm" src="http://stream.flowplayer.org/bauhaus/624x260.webm">
         <source type="video/mp4" src="<%=basePath%><%=request.getParameter("path")%>">
         <source type="video/ogv" src="http://stream.flowplayer.org/bauhaus/624x260.ogv">
      </video>
   </div>
</body>
</html>
