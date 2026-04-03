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

<!DOCTYPE HTML>
<html>
<head lang="en">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">


<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/human/attence/signface.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/human/attence/base.css" />
<link rel="stylesheet/less" type="text/css"
	href="<%=basePath%>css/ea/human/attence/signFail.less" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/human/attence/signFail.css" />
<script src="<%=basePath%>js/font-size.js" type="text/javascript"
	charset="utf-8"></script>

<script type="text/javascript">
    	var basePath="<%=basePath%>";
    	function back(){
    	  document.location.href = basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
    	}
    	 setInterval(function(){
              	  document.location.href = basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
            }, 5000);
</script>
<title>签到失败</title>

</head>
<body>
		<header>
			<ul class="clearfix">
				<li onclick="back();">
					<img src="<%=basePath%>images/home/img-1.png" >
				</li>
				<li>
					人脸识别考勤打卡
				</li>
			</ul>
		</header>
		<div class="content">
			<p class="p-img">
				<img src="<%=basePath%>images/home/img-3.png" >
			</p>
			<p class="p-jg">签到失败</p>
			<p class="p-txt">您的账号没有入职该公司，请入职后再签到</p>
		</div>

</body>
<script src="<%=basePath%>js/ea/human/attence/less.js"
	type="text/javascript" charset="utf-8"></script>
</html>