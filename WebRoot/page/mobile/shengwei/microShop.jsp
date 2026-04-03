<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>微店类型</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
   <style>
		/*reset*/
		*{margin:0;padding:0;font-size:12px; line-height:16px;}
		li,dl,dt,dd{list-style:none;}
		img{border:none 0; cursor:pointer;}
		a{text-decoration:none;color:#333;}
		body{background:#e6e6e6;}
		.fl{ float:left;}
		/*head*/
		.head{width:100%;height:48px; background-color:#43abe1; text-align:center; color:#fff; font-family:"微软雅黑"; font-size:18px; line-height:48px;}
		.pic{width:90%; background-color:#fff; border:#c8c8c8 1px solid;margin:auto;margin-top:10px; margin-left:5%;}
		.pic img{width:40%;}
		.line01,.line02,.line03{ width:100%;}
		.line01 img{margin-top:30px;margin-left:5%;margin-right:5%; float:left; }
		.line02 { text-align:center;}
		.line02 img{ text-align:center;margin:auto;margin-top:30px;}
	</style>
    <script>
        $(function () {          
            imgAuto();//初始化图片大小
            window.onresize = function () {
                imgAuto();//屏幕缩放时图片的大小
            }
        });
        function imgAuto() {
            var wid = document.documentElement.clientWidth * .8;
            $(".imgAuto").each(function () {
                $(this).css("height", wid);
            });
        }//遍历循环所有class="imgAuto"的标签并改变其大小
    </script>
        <script>
		var imgUrl = 'http://www.impf2010.com/images/wechat.jpg';
		var lineLink = document.location.href;
		var descContent = document.title;
		var shareTitle = document.title;
		var appid = 'wxc9937e3a66af6dc8';
		function shareFriend() {
		    WeixinJSBridge.invoke('sendAppMessage',{
		                            "appid": appid,
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": '微商店',
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('send_msg', res.err_msg);
		                            })
		}
		function shareTimeline() {
		    WeixinJSBridge.invoke('shareTimeline',{
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": descContent,
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('timeline', res.err_msg);
		                            });
		}
		function shareWeibo() {
		    WeixinJSBridge.invoke('shareWeibo',{
		                            "content": descContent,
		                            "url": lineLink,
		                            }, function(res) {
		                            _report('weibo', res.err_msg);
		                            });
		}
		// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {

	        // 发送给好友
	        WeixinJSBridge.on('menu:share:appmessage', function(argv){
	            shareFriend();
	            });
	
	        // 分享到朋友圈
	        WeixinJSBridge.on('menu:share:timeline', function(argv){
	            shareTimeline();
	            });
	
	        // 分享到微博
	        WeixinJSBridge.on('menu:share:weibo', function(argv){
	            shareWeibo();
	            });
        }, false);
	</script>
</head>
	<body>
		<div class="head">企业微分金店</div>
		<div class="pic fl">
			<div class="line01 fl"><!-- 住宿店  餐饮店 -->
		        <a href="<%=basePath%>/ea/shengwei/getShopGoods.jspa?weidiantype=住宿店"><img src="<%=basePath%>images/shengwei/pic05.png"></a>
		        <a href="<%=basePath%>/ea/shengwei/getShopGoods.jspa?weidiantype=餐饮店"><img src="<%=basePath%>images/shengwei/pic03.png"></a>	
		    </div>
		    <div class="line02 fl"><!-- 培训店 -->
		        <a href="<%=basePath%>/ea/shengwei/getShopGoods.jspa?weidiantype=培训店"><img src="<%=basePath%>images/shengwei/pic04.png"></a>
		    </div>
		    <div class="line01 fl"><!-- 招商店   茶园店-->
		        <a href="<%=basePath%>/ea/shengwei/getShopGoods.jspa?weidiantype=招商店"><img src="<%=basePath%>images/shengwei/pic02.png"></a>
		        <a href="<%=basePath%>/ea/shengwei/getShopGoods.jspa?weidiantype=茶园店"><img src="<%=basePath%>images/shengwei/pic06.png"></a>
		    </div>
		</div>
	</body>
</html>
