<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/human/attence/signface.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/base.css" />
<link rel="stylesheet/less" type="text/css" href="<%=basePath%>css/ea/human/attence/signface.less" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/attence/signface.css" />
<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
    var basePath="<%=basePath%>";
	var companyId = "${company.companyID}";
	var companyname = "${company.companyName}";
</script>
<title>签到</title>
<style type="text/css">
#prompt div {
	width: 70%;
	background: rgba(0, 0, 0, 0.5);
}
</style>
</head>
<body>

	<header>
		<ul class="clearfix">
			<li onclick="back();">
				<img src="<%=basePath%>images/home/img-1.png">
			</li>
			<li>人脸识别考勤打卡</li>
		</ul>
	</header>
	<div class="content">
		<p class="p-img">
			<img src="<%=basePath%>images/home/img-001.png">
		</p>
		<div class="p-time" id="signface">
			<div>
				<p>签到</p>
				<p>
					
				</p>
			</div>
		</div>
		<p class="p-gs">
			<img src="<%=basePath%>images/home/img-04.png">签到公司：${company.companyName}
		</p>
	   <p class="p-gs p-dz">
		   <img src="<%=basePath%>images/home/img-05.png" >当前时间：<span class="date">${date}</span>
		</p>
	</div>

	<div style="font-size: 10px; color: gray; position: absolute; bottom: 8px;left: 8px;">
		签到设备号：${posNum}
	</div>

	<!-- 提示窗口 -->
	<div id="prompt" style="width: 100%;display: none;">
		<center>
			<div>
				<span style="position: relative;top: 19.8%;"></span>
			</div>
		</center>
	</div>

</body>
<script src="<%=basePath%>js/ea/human/attence/less.js"
	type="text/javascript" charset="utf-8"></script>
</html>