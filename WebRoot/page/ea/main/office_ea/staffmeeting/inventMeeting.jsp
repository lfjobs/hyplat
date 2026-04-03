<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>会议邀请</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/inventMeeting.css">
<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="<%=basePath%>js/font-size.js" type="text/javascript"
	charset="utf-8"></script>
<script src="<%=basePath%>js/clipboard.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/layui/css/layui.css">
<script src="<%=basePath%>js/layui/layui.js" type="text/javascript" charset="utf-8"></script>


<script type="text/javascript">
	$(function() {
	var layer = "";
	   layui.use(['layer'], function(){
        layer = layui.layer;
 
});
	
	
		//复制
		var clipboard = new ClipboardJS('.copyBtn');//点击class为copyBtn的按钮复制内容

		clipboard.on('success', function(e) {

			e.clearSelection();
			showMsg('复制成功');
		});

		clipboard.on('error', function(e) {
			showMsg('您的手机暂不支持，请长按进行复制');
		});

		function showMsg(data) {
			 
           layer.msg(data);

		}
	});
	
	
	function  openApp() {
        var ua = navigator.userAgent.toLowerCase();
        console.log(ua);
        if(this.isWeixinBrowser(ua)){
            $('.layer').show();//遮罩层（使用外部浏览器打开，此处样式自行设定）
        }else{
          window.location.href = 'wfjapp://shuzidiqiu/jumpvideo/info?roomId=${staffMeeting.roomid}&meetingPsw=${staffMeeting.meetingpsw}';
            if(this.isAndroid(ua)){
                //android
              
                var loadDateTime = Date.now();
                var turn = setTimeout(function(){
                    var timeOutDateTime = Date.now();
                    if ((timeOutDateTime - loadDateTime) < 2200 ){
                        window.location.href = '<%=basePath%>ea/wfjshop/ea_getjspzc.jspa?sccid=${param.sccid}';
                    }
                },2000);
            }else{
                //ios
                var loadDateTime = Date.now();
                var turn = setTimeout(function(){
                    var  timeOutDateTime = Date.now();
                    console.log(timeOutDateTime - loadDateTime);
                    if ((timeOutDateTime - loadDateTime) < 2200 ){
                         window.location.href = '<%=basePath%>ea/wfjshop/ea_getjspzc.jspa?sccid=${param.sccid}';
                    }
                },2000);
            }
        }
    }
    function isWeixinBrowser(ua) {
        return (/micromessenger/.test(ua)) ? true : false;
    }
    
    function isAndroid(ua) {
        return ua.indexOf('android') > -1 || ua.indexOf('linux') > -1;
    }

</script>
</head>
<header>
	<ul class="clearfix">
		<li>会议邀请</li>
	</ul>
</header>
<div class="content">
	<ul>
		<li class="clearfix">
			<p>会议名称:</p>
			<p>${staffMeeting.meetingName}</p></li>
		<li class="clearfix">
			<p>会议主题:</p>
			<p>${staffMeeting.meetingTheme}</p></li>
		<li class="clearfix">
			<p>会议号:</p>
			<p class="hyh" id="foo">${staffMeeting.roomid}</p> <span onclick=""
			class="copyBtn" data-clipboard-text="${staffMeeting.roomid}">
				复制 </span></li>
		<c:if test="${staffMeeting.meetingpsw ne null}">
		<li class="clearfix">
			<p>会议密码:</p>
			<p>${staffMeeting.meetingpsw}</p></li>
			</c:if>
		<li class="clearfix">
			<p>开始时间:</p>
			<p>${fn:substring(staffMeeting.startDate,0,19)}</p></li>
		<li class="clearfix">
			<p>结束时间:</p>
			<p>${fn:substring(staffMeeting.endDate,0,19)}</p></li>
	</ul>
	<input type="button" name="" id="" value="加入会议" onclick="openApp()"/>
</div>
</body>

</html>
