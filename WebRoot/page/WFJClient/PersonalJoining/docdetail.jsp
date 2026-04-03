<%@page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/wfjapp1.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/news.css" />
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
<title>文档详情</title>

</head>

<body onload="loaded()">

	<div class="main">
		<%-- <div class="wfj12_002">
			<div class="wfj12_002_top">
				<ul id="tops">
					<li><a href="javascript:;"
						onClick="javascript:history.back(-1);"><img
							src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" />
					</a>
					</li>
					<li>帮助文档</li>
					<li></li>
				</ul>
			</div>
		</div> --%>
		<article>
			<h1 style="margin-left:1rem;">${object1[0]}</h1>
			<h2 style="margin-left:1rem;">

				<span class="keyword">${list[0][0]}</span>

			</h2>
			<div class="line"></div>
			<div class="content">
				<c:forEach items="${list}" var="item">

					<p>
						<span>${item[3]}</span>
					</p>
					</tr>
					<div style="width:100%;">${maplist[item[2]]}</div>
				</c:forEach>
			</div>
		</article>


		<div></div>
</body>
<script type="text/javascript">
var basePath='<%=basePath%>';

	$("body").css("height", $(window).height());
	/* $("#tops").find("li").attr("style", "float:left;");
	$("#tops").find("li").eq(0).attr("style", "width:15%;");
	$("#tops").find("li").eq(0).find("img").attr(
			"style",
			"height:" + $(window).height() * 0.04 + "px;padding-left:"
					+ $(window).height() * 0.02 + "px; vertical-align:middle;");
	$("#tops").find("li").eq(1).attr(
			"style",
			"width:70%; text-align:center; font-size:" + $(window).height()
					* 0.025 + "px;color:#373737;");
	$("#tops").find("li").eq(2).attr("style", "width:15%; text-align:center;");
	$("#tops").find("li").eq(2).find("img").attr(
			"style",
			"height:" + $(window).height() * 0.03
					+ "px; width:auto; vertical-align:middle;"); */
	$(".wfj12_002_top").css("height",
			$(window).height() * 0.08 + "px;background-color:#FFF;");
	$(".wfj12_002_top").css("lineHeight", $(window).height() * 0.08 + "px");

	function loaded() {
		$(".content").attr("style", "width:100%;text-align:left;");
		$(".content").find(".content_tab").css("width",
				$(".content").width() + "px");
		$(".content").find("div:first").css("width",
				$(".content").width() + "px");
		$(".content").find("p").css("width", $(".content").width() + "px");
		/* $(".content").find("img").css("width", $(".content").width() + "px"); */
		$(".content").find("p").css({"font-size":$(window).height() * 0.023 + "px"});
        $(".content").find("span").css({"padding-left":"5px"});
		
		$(".keyword").css({"font-size":$(window).height() * 0.023 + "px"});
	/* 	$(".content").find("img").each(function() {
			if ($(this).width() > $(".content").width()) {
				$(".content").find("img").css({
					"width" : $(".content").width() + "px",
					"height" : "auto",
					"margin" : "0 auto"
				})
			} else {
				$(".content").find("img").css({
					"height" : "auto",
					"margin" : "0 auto"
				});
			}
		}); */

	}
</script>
</html>



