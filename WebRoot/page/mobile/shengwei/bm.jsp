<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>我要报名</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/bm.css">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/css.css">

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script>
        $(function () {
            
            imgAuto();//初始化图片大小
			
			$(".bm_tx_fx").click(function(){Fscreen();});
			$(".bm_tx_pyq").click(function(){Fscreen();});
			$(".bm_2_an").click(function(){$(".bm_2").attr("class","new");});
            window.onresize = function () {
                imgAuto();//屏幕缩放时图片的大小

                //if (wid > 1400) {
                //    $("#aaa").css("width", 1400);
                //} else if (wid < 200) {
                //    $("#aaa").css("width", 1400);
                //} else {
                //    $("#aaa").css("width", wid);
                //}
            }
        });
		
			function Fscreen(){
			$(".new").attr("class","bm_2");
		}

        function imgAuto() {
            var wid = document.documentElement.clientWidth * .8;
            $(".imgAuto").each(function () {
                $(this).css("width", wid);
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
		                            "desc": '点击发布产品...',
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
	<body bgcolor="#E7E7E7">
		<div class="bm_tx_bt">
			在线报名
		</div>
		<div class="geduan"></div>
		<div class="bm_kuang">
			<div class="geduan"></div>
			<div class="bm_pic">
				<a href="<%=basePath%>page/mobile/shengwei/bmxz.jsp"><img class="imgAuto" src="<%=basePath%>images/shengwei/bman_1.png" />
				</a>
			</div>
			<div class="geduan"></div>
		</div>
		<div class="geduan"></div>
		<div class="bm_kuang">
			<div class="geduan"></div>
			<div class="bm_pic">
				<a href="<%=basePath%>/ea/shengwei/ListCourse.jspa?weidianType=培训店&companyid=<%=request.getParameter("companyId")%>"><img class="imgAuto" src="<%=basePath%>images/shengwei/bman_2.png" /></a>
			</div>
			<div class="geduan"></div>
		</div>
		<div class="geduan"></div>
	</body>
</html>
