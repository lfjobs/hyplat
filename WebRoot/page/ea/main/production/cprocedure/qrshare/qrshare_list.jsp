<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html class="firtbody">
<head>
<title>
	
</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/qrshare/qr_share.css">


<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/qrshare_list.js"
	type="text/javascript"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";
	
	//分享
	var pageNumber;
	var pageCount;
	var sccid = '${obj[4]}';
	var staffid = '${obj[5]}';
	var companyId = '${obj[6]}';
    var companyID = "${companyID}";
	var androidJudge="${sessionScope.androidJudge}";
	//简介/文化/新闻
	var miniSystemJudge='${miniSystemJudge}';
	var sccids = "${sessionScope.sccid}";
</script>
</head>
<body class="firtbody">
	<header class="com_head">
		<a href="javascript:backtrack();" class="back"></a>
		<h1>
			<c:if test="${miniSystemJudge eq '00'}">
				公司简介
			</c:if>
			<c:if test="${miniSystemJudge eq '01'}">
				公司文化
			</c:if>
			<c:if test="${miniSystemJudge eq '02'}">
				公司新闻
			</c:if>
			<c:if test="${miniSystemJudge eq '03'}">
				资讯分享
			</c:if>
			<c:if test="${miniSystemJudge eq '04'}">
				公司论坛
			</c:if>
		</h1>
	</header>
	<div class="wrap_page">
		<c:if test="${miniSystemJudge eq '03'}">
			<div class="qr_info_wrap clearfix">
				<div class="qr_headimg img_wrap">
					<img src="<%=basePath%>${obj[0]==null?"
						images/ea/production/head2x.png":obj[0]}" alt="">
				</div>
				<div class="qr_text_wrap">
					<div class="qr_ttop">
						<span class="qr_name">${obj[1]}</span>（<span>${obj[2]}</span>）
	
					</div>
					<div>${obj[3]}</div>
				</div>
				<a
					href="<%=basePath%>ea/vipcenter/ea_Platform.jspa?staffid=${obj[5]}&sccid=${obj[4]}&type=3&miniSystemJudge=${miniSystemJudge}"
					class="qr_list_btn">切换平台</a>
			</div>
			<hr class="QR_hr">
		</c:if>
		<div class="qr_list_wrap" style="padding-bottom: 2rem;">
			<!-- js拼接 -->
		</div>
		<a href="javascript:void(0);" class="release_QR" onclick="attition()"></a>
		<input type="file" accept="image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp" multiple="" id="imgs" style="display: none">
	</div>
	<div id="loading">
		<img src="<%=basePath%>images/ea/production/qrshare/loading.gif" alt="">
	</div>
</body>
<script>
		window.onload = function() {
			if(miniSystemJudge!="03"){
				staffid = '${caccount.staffID}';
				companyId = '${caccount.companyID}';
			}
			ajax();
		}
</script>
</html>