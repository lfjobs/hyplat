<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
	    <meta name="apple-mobile-web-app-capable" content="yes" />
	    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
	    <meta name="format-detection" content="telephone=no, email=no" />
	    <meta name="screen-orientation" content="portrait">
	    <meta name="x5-orientation" content="portrait">
	    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/setHtmlFont.js"></script>
	    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/login/base.css">
	    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/login/download_app.css">
	    <script src="<%=basePath%>/js/WFJClient/jquery.min.js"></script>
	    <title>微分金数字地球</title>
	</head>
<body>
    <header class="com_head">
        <a href="javascript:history.go(-1);" class="back"></a>
        <h1>微分金数字地球</h1>
    </header>
    <div class="wrap_page">
        <div class="download_wrap">
            <img src="<%=basePath%>/images/WFJClient/Login/img_top.png" class="img_top" alt="">
            <div class="text_01">恭喜您注册成功</div>
            <div class="text_02">马上下载微分金APP，开启财富之旅！</div>
            <div class="btn_wrap clearfix">
                <a href="<%=basePath%>upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk" class="android"></a>
                <a href="https://itunes.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en&mt=8" class="ios"></a>
            </div>
            <a href="<%=basePath%>upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk" class="zan"></a>
            <img src="<%=basePath%>/images/WFJClient/Login/img_bottom.png" class="img_bottom" alt="">
            <img src="<%=basePath%>/images/WFJClient/Login/text.png" class="text_img" alt="">
            <div class="animate_wrap">
                <img class="earth" src="<%=basePath%>/images/WFJClient/Login/earth.png" alt="">
                <div class="wfj_wrap">
                    <div  class="wfj"><img src="<%=basePath%>/images/WFJClient/Login/wfj.png" alt=""></div>
                    <div>数字地球</div>
                </div>
                <div class="pointer"></div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
var basePath='<%=basePath%>';
		function is_weixin() {
		    var ua = navigator.userAgent.toLowerCase();
		    if (ua.match(/MicroMessenger/i) == "micromessenger") {
		        return true;
		    } else {
		        return false;
		    }
		}
		var isWeixin = is_weixin();
		var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
		function loadHtml(){
			var div = document.createElement('div');
			div.id = 'weixin-tip';
			div.innerHTML = '<p><img src="'+basePath+'images/live_weixin.png" alt="微信打开"/></p>';
			document.body.appendChild(div);
		}
		
		function loadStyleText(cssText) {
	        var style = document.createElement('style');
	        style.rel = 'stylesheet';
	        style.type = 'text/css';
	        try {
	            style.appendChild(document.createTextNode(cssText));
	        } catch (e) {
	            style.styleSheet.cssText = cssText; //ie9以下
	        }
            var head=document.getElementsByTagName("head")[0]; //head标签之间加上style样式
            head.appendChild(style); 
	    }
	    var cssText = "#weixin-tip{position: fixed; left:0; top:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80); width: 100%; height:100%; z-index: 999;} #weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;} #weixin-tip p img{width:100%;}";
		if(isWeixin){
			loadHtml();
			loadStyleText(cssText);
		}
	</script>
</html>