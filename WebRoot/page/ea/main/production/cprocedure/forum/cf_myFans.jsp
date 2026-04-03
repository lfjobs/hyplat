<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
<meta charset="UTF-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<title></title> <script type="text/javascript"
			src="<%=basePath%>/js/BuildPlatform/font-size.js"></script>
		<link type="text/css" rel="stylesheet"
			href="<%=basePath%>/css/BuildPlatform/swiper-3.3.1.min.css">
			<link type="text/css" rel="stylesheet"
				href="<%=basePath%>/css/ea/production/forum/new_style.css">
				<script type="text/javascript"
					src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/BuildPlatform/swiper-3.3.1.min.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/ea/production/cprocedure/forum/new-page.js"></script>
				<script type="text/javascript"
					src="<%=basePath%>/js/ea/production/cprocedure/forum/cf_myFans.js"></script>
					<script>
if(window.name != "bencalie"){
     location.reload();
     window.name = "bencalie";
}
else{
     window.name = "";
}
</script>
</head>
<body>

	<header>
	<ul>
		<li style="width: 10%;"><a href="javascript:###;"
			onclick="javascript: window.history.go(-1);return false;"><img
				src="<%=basePath%>/images/BuildPlatform/left_jt.png">
		</a></li>
		<li style="width: 80%;">粉丝</li>
		<li style="width: 10%; display: none;"><img
			src="<%=basePath%>/images/BuildPlatform/ico-search.png" alt=""
			id="search">
		</li>
		<div class="clearfix"></div>
	</ul>
	</header>
	<div class="content_hidden">
		<div id="prompt" style="width: 100%; display: none;z-index: 1001">
			<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
			</center>
		</div>
		<div class="content">
			<div class="con con_pl">
				<ul class="gz_mil">
					<%-- js拼接 --%>
				</ul>
			</div>
			<!--<div class="alert"></div>-->
			<div class="alert_search">
				<div class="top">
					<input type="search" name="" placeholder="搜索"
						onfocus="this.placeholder=''" onblur="this.placeholder='搜索'"
						value="" class="sousuo"> <input type="submit" value="搜索"
						id="ss"> <input type="submit" value="取消" id="qx">
				</div>
			</div>
		</div>
	</div>
	<script>
	var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var community = '${community}';
		var sccId = '${ccom.sccId}';
	</script>

	<script>
		$(document).ready(
				function() {
					$("header").css("height",
							$(window).height() * 0.08 - 1 + "px");
					$("header").css("line-height",
							$(window).height() * 0.08 - 1 + "px");
					$(".content_hidden").attr(
							"style",
							";overflow: hidden;" + "height:"
									+ $(window).height() * 0.92 + "px");
					$(".content").attr(
							"style",
							"overflow: hidden;" + "height:"
									+ $(window).height() * 0.92 + "px");
					$(".head_top").css("height",
							$(window).height() * 0.08 - 1 + "px");
					$(".head_top ul li").css("line-height",
							$(window).height() * 0.05 + "px");
					$(".head_top ul li:nth-child(1) dl").css("margin",
							$(window).height() * 0.015 + "px");
					$(".head_top ul li:nth-child(2) input").attr(
							"style",
							"margin:" + $(window).height() * 0.015
									+ "px;margin-left:0;line-height:"
									+ $(window).height() * 0.05 + "px;");
					/*$(".con").css("height",$(window).height()*0.828+"px");*/
					$(".con").css("height", $(window).height() * 0.92 + "px");

					$(".mil_txt .mil img").css("height",
							$(".mil_txt .mil img").width() + "px");
					$(".lt_top")
							.css("height", $(window).height() * 0.12 + "px");
					//  $("#menu_con .tag").css("max-height",$(".con_pl").height()-$(".lt_top").height()-$(".lt_lie").height()-$(".btn_jia").height()+"px");

				});
	</script>
	<script>
		var swiper = new Swiper('.swiper-container', {
			pagination : '.swiper-pagination',
			slidesPerView : 3,
			paginationClickable : true,
			spaceBetween : 30,
			loop : true,
		});
	</script>
</body>
</html>