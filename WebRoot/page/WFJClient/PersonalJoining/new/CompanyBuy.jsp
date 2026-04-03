<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司平台管理系统-公司购买</title>

<script charset="utf-8" type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/lxb.js"></script>
<script charset="utf-8" type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/v.js"></script>

<script type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/mobilego.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>page/WFJClient/PersonalJoining/new/common.css">
<link rel="stylesheet"
	href="<%=basePath%>page/WFJClient/PersonalJoining/new/aboutus.css">
<style type="text/css">
.p-aboutus .news-list {
	margin: 50px 0
}

.news-list a {
	font-size: 18px;
	color: #000;
	display: block;
	height: 45px;
	line-height: 45px;
	width: 100%
}

.news-list li:hover {
	background-color: #ccc
}

.news-list a .fRight {
	float: right
}

.inputstyle {
	display: inline-block;
	background-color: #e8e8e8;
	border: 0 none;
	border-radius: 60px;
	font-size: 14px;
	height: 21px;
	line-height: 21px;
	padding: 10px 20px 11px;
	width: 300px;
	color: #000;
	outline: 0
}

#login ul #g_yz .inputstyle {
	width: 155px !important;
	margin-right: 10px
}

#login ul #g_yz #codeImage {
	width: 68px;
	height: 24px
}

#login ul #g_yz a {
	color: #999;
	margin: 14px 5px 0 0
}

#login ul #g_yz a:hover {
	color: #03a9f4
}

h1,h2,h3,h4 {
	padding: 0;
	margin: 0
}

#login {
	width: 340px;
	padding: 0 40px;
	background-color: #fff;
	border-radius: 5px;
	border: 1px solid #dfdfdf;
	position: absolute;
	right: 85px;
	top: 106px
}

#login h2 {
	padding: 30px 0 15px;
	font-weight: 400;
	font-size: 21px;
	color: #666;
	text-align: center;
	letter-spacing: 3px
}

#login ul {
	padding: 0;
	margin: 0
}

.pass-holder {
	position: absolute;
	left: 20px;
	top: 25px;
	color: #999;
	cursor: text
}

#login ul li {
	list-style-type: none;
	padding: 15px 0;
	overflow: hidden;
	vertical-align: middle;
	position: relative
}

#login ul #field_login_options {
	padding-top: 2px;
	margin-top: -10px;
	font-size: 12px;
	color: #999
}

#login ul #field_login_options input {
	margin-top: 1px
}

#login ul #field_login_options a {
	right: 5px !important;
	color: #999
}

#login ul #field_login_options a:hover {
	color: #03a9f4
}

#login ul li span.f-label {
	display: none;
	width: 80px;
	float: left;
	text-align: right;
	padding-right: 4px;
	padding-top: 5px;
	vertical-align: middle
}

#login ul li img,#login ul li input,#login ul li label {
	vertical-align: middle
}

#login ul li div {
	text-align: right;
	padding-right: 5px;
	padding-top: 5px
}

#login ul li a {
	float: right;
	text-align: right;
	padding: 0 0;
	color: #1e5494
}

#login .title-box {
	padding: 16px 0 30px
}

#login .title-box .merchant-name,#login .title-box .separetor {
	float: left
}

#login .title-box .user-action-l {
	float: right;
	color: #999
}

#login .title-box .user-action-l:hover {
	color: #03a9f4
}

#login .title-box .merchant-name {
	width: 231px;
	text-align: right;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	height: 19px;
	display: inline-block
}

#login .title-box .separetor {
	line-height: 16px
}

.login_button {
	height: 40px;
	line-height: 40px;
	width: 340px;
	border-radius: 50px;
	text-align: center;
	float: none;
	border: 0 none;
	background-color: #f5a918;
	color: #fff;
	letter-spacing: 8px;
	cursor: pointer;
	font-size: 16px;
	outline: 0
}

.login_button:hover {
	background-color: #fdb326
}

.login_button:active {
	box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125)
}

#error-tip {
	position: absolute;
	background: #ffffe1;
	border: 1px solid #F4E6B1;
	padding: 5px 20px 5px 10px;
	color: #f56a4f;
	width: 200px;
	z-index: 9999
}

#error-tip .error-content {
	line-height: 25px;
	padding-left: 22px
}

#error-tip .icon-tip {
	position: absolute;
	top: 10px;
	left: 10px;
	width: 18px;
	height: 18px;
	background: url(images/error.png) no-repeat;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='Content/images/error.png',
		sizingMethod='scale' )
}

#error-tip .triangle-wrapper {
	position: absolute;
	bottom: -12px;
	left: 30px;
	width: 0;
	height: 0;
	border: 6px solid;
	border-color: #F4E6B1 transparent transparent transparent;
	background: 0 0
}

#error-tip .triangle-inner {
	position: absolute;
	top: -8px;
	left: -6px;
	width: 0;
	height: 0;
	border: 6px solid;
	border-color: #ffffe1 transparent transparent transparent;
	background: 0 0
}

#error-tip .close-btn {
	position: absolute;
	top: 3px;
	right: 5px;
	font-style: normal;
	font-size: 10px;
	cursor: pointer
}

#codeImage {
	cursor: pointer
}

#yzlink {
	cursor: pointer;
	position: absolute;
	right: 0;
	top: 12px
}

.input-botfont {
	display: none;
	color: #6076a0;
	padding-left: 85px;
	line-height: 16px
}

.input-advise {
	display: none;
	color: #7c7c7c;
	padding-left: 85px;
	line-height: 20px;
	padding-bottom: 3px
}

.f-label-b {
	display: block;
	padding-left: 5px
}

.s-mask {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 1999;
	background: #fff;
	filter: alpha(opacity =   50);
	-moz-opacity: .5;
	opacity: .5
}

.s-alert {
	display: block;
	position: fixed;
	background: #fff;
	z-index: 2000;
	width: 466px;
	left: 50%;
	top: 36%;
	margin-left: -233px;
	padding: 0;
	border: 0;
	-moz-box-shadow: 0 0 30px #999;
	-webkit-box-shadow: 0 0 30px #999;
	box-shadow: 0 0 30px #999
}

.s-alert-content {
	padding: 45px 70px 20px 70px;
	font-size: 14px;
	line-height: 22px;
	text-align: center;
	word-wrap: break-word
}

.s-alert-content-msg {
	padding-bottom: 30px;
	color: #444
}

.s-alert-button {
	text-align: center
}

.s-alert-button span {
	display: inline-block;
	color: #bababa;
	border: 1px solid #d9d9d9;
	padding: 0 24px;
	line-height: 28px;
	cursor: pointer;
	background: 0 0;
	background-color: #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	box-shadow: none;
	height: 28px;
	float: none
}
</style>
</head>
<body>
	<div class="inc-headerfixed"></div>
	<div class="g-w930 p-aboutus">

		<div class="news-list">
			<ul>
				<li><a class="news-title" href="<%=basePath %>ea/industry/ea_marketing.jspa?types=平台">
						<span>微分金平台是什么呢？</span> <span class="fRight">2015/09/22</span>
				</a>
				</li>
				<li><a class="news-title" href="<%=basePath %>ea/industry/ea_marketing.jspa?types=之歌">
						<span>微分金之歌和口号你有听过吗？</span> <span class="fRight">2015/09/17</span>
				</a>
				</li>
				
				<li><a class="news-title" href="<%=basePath %>ea/industry/ea_marketing.jspa?types=系统">
						<span>互联网+”驾校未来发展趋势?</span> <span class="fRight">2015/04/02</span>
				</a>
				</li>
			</ul>
		</div>

		<script type="text/javascript"
			src="<%=basePath %>page/WFJClient/PersonalJoining/new/common.js"></script>
</body>
</html>