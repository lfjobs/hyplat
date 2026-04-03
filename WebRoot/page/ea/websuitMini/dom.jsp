<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>公文查阅</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" href="<%=basePath%>js/ea/websuitMini/screen.css" type="text/css" media="screen, projection">
	 <script src="<%=basePath%>js/jquery.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/flowplayer.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/swfobject.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/md5.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/viewer.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/main.js"></script>
	</head>
	<body>

<div class="container" style="width:830px;height:1024px;">
    <div id="edoviewer"></div>
    
    <script type="text/javascript">
    
        var viewer = EdoViewer.createViewer('edoviewer', {
          server_url: 'http://viewer.everydo.com:9870',
          location: '',
          source_url: 'http%3A//download.zopen.cn/demo/demo.doc',
          ip: '',

          timestamp: '',
          preview_signcode: '',
          pdf_signcode: '',
          download_signcode: '',
          account: 'zopen',
          instance: 'default',
          width: 825,
          height: 1024
        });
        viewer.load();
 </script>
</div>



<a href="http://api.idocv.com/view/uepoJXw">预览文档</a>
<input class="J_button" accept="image/jpeg,image/png,image/gif,image/bmp" style="display: block; background:transparent; width: 207px; height:69px;margin-left:-69px;" type="file">
	</body>
</html>
