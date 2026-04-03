<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>


	<%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/message/mes.css">

	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/message/mes.js" type="text/javascript" charset="utf-8"></script>
	<title>消息</title>
</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<!-- <img src="<%=basePath%>images/WFJClient/pc/newimg/return.png" > -->
		</li>
		<li>
			消息
		</li>
		<li>
			<img src="<%=basePath%>images/WFJClient/pc/newimg/img_02.png" alt="">
		</li>
	</ul>
</header>
<div class="content">
	<ul>
		<li class="clearfix">
			<div class="div-img">
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_03.jpg" alt="">
			</div>
			<div class="div-right clearfix">
				<div class="div-left">
					<p>消息助手</p>
					<p>所有服务消息</p>
				</div>
				<p class="right">
					03-24
				</p>
			</div>
		</li>
		<li class="clearfix">
			<div class="div-img">
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_05.jpg" alt="">
			</div>
			<div class="div-right clearfix">
				<div class="div-left">
					<p>好友</p>
					<p>查看所有好友</p>
				</div>
				<p class="right">
					03-24
				</p>
			</div>
		</li>
		<li class="clearfix">
			<div class="div-img">
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_06.jpg" alt="">
			</div>
			<div class="div-right clearfix">
				<div class="div-left">
					<p>加好友</p>
					<p>添加新的朋友</p>
				</div>
				<p class="right">
					03-24
				</p>
			</div>
		</li>
	</ul>

	<div style="height: 134px"></div>

	<div class="content-bottom"></div>
</div>
<div class="footer div-bottom">
	<ul class="clearfix">
		<li class="active">
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_09.jpg" alt="">
			</div>
			<p>
				消息
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_07.jpg" alt="">
			</div>
			<p>
				通讯
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
			</div>
			<p>
				数字
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_10.jpg" alt="">
			</div>
			<p>
				5L5C
			</p>
		</li>
		<li>
			<div>
				<img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
			</div>
			<p>
				我的
			</p>
		</li>
	</ul>
</div>

<div id="download-app" style="position: fixed; bottom: 72px; left: 0; right: 0; width: 100%; display: none; background: white;">
	<p style="position: absolute; right: 16px;text-align: center; color: gray; font-size: 0.8rem" onclick="closeHelp()">关闭</p>
	<div style="padding-bottom: 24px">
		<p style="text-align: center; color: gray; font-size: 1.0rem">下载APP使用更方便</p>
		<div style="display: flex; justify-content: center">
			<%--<a href="http://www.impf2010.com:80/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk">
                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
            </a>--%>
			<a href="https://sj.qq.com/search?q=%E6%95%B0%E5%AD%97%E5%9C%B0%E7%90%83">
				<img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
			</a>
			<a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">
				<img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_09.png"/>
			</a>
		</div>
	</div>
</div>

<script>
	function isApp() {
		let deviceId = "";
		try {
			if(isAndroid) {
				deviceId = Android.forAndroidDeviceId()
			}
			if(isiOS) {
				deviceId = "-"
			}
		} catch (e) {
			deviceId = ""
		}
		return deviceId !== ""
	}

	function closeHelp() {
		document.getElementById("download-app").style.display = "none"
	}

	document.getElementById("download-app").style.display = isApp() ? "none" : "block"
</script>

</body>

<script type="text/javascript">
	var  basePath = "<%=basePath%>";
</script>
</html>
