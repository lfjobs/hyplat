<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>过年赚钱</title>
<link href="<%=basePath%>css/contacts/recepit/gnzq.css" rel="stylesheet"
		type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
<style type="text/css">
	*{margin:0; padding:0;}
	a{text-decoration: none;}
	img{max-width: 100%; height: auto;}
	.weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
	.weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
</style>

<script type="text/javascript">
 function weixinTip(ele){
      var isWeixin = true;
      var ua = navigator.userAgent.toLowerCase();
	 if (ua.match(/MicroMessenger/i) == "micromessenger") {
			        isWeixin=true;
			    } else {
			        isWeixin=false;
		}
	    if(isWeixin){
	         var winHeight = $(window).height();
		     $(".weixin-tip").css("height",winHeight);
	         $(".weixin-tip").show();
	    }
   }
   $(function(){
   $(".weixin-tip").click(function(){
      $(".weixin-tip").hide();
   
   });
});
    </script>
</head>



<body>
<div class="weixin-tip">
		<p>
			<img src="<%=basePath%>images/contacts/loadsite/live_weixin.png" alt="微信打开"/>
		</p>
</div>  

<div class="main">
    	<div class="logo"><img src="<%=basePath%>images/contacts/loadsite/logo.png"></div>
        <div class="pic1"><img src="<%=basePath%>images/contacts/loadsite/pic1.png"></div>
        <div class="text1"><img src="<%=basePath%>images/contacts/loadsite/text1.png"></div>
    	<div class="head"><img src="<%=basePath%>images/contacts/loadsite/head.png"></div>
        <div class="pic2"><img src="<%=basePath%>images/contacts/loadsite/pic2.png"></div>

        <div class="fenge" id="topfen">
        	<img class="imgleft" src="<%=basePath%>images/contacts/loadsite/fenge3.png"><img class="imgright" src="<%=basePath%>images/contacts/loadsite/fenge4.png">
        </div>
        <div class="pic3"><img src="<%=basePath%>images/contacts/loadsite/pic3.png"></div>
        <div class="text2"><img src="<%=basePath%>images/contacts/loadsite/text2.png"></div>
        <div class="down">
        	<a href="<%=basePath%>upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk" id="JdownApp" onclick="weixinTip('JdownApp')"><img src="<%=basePath%>images/contacts/loadsite/button1.png"></a>
             <a href="http://www.pgyer.com/djcY"><img src="<%=basePath%>images/contacts/loadsite/button2.png"></a>
        </div>
        <div class="foot"><img src="<%=basePath%>images/contacts/loadsite/foot.png"></div>
</div>
  
</body>
</html> 
