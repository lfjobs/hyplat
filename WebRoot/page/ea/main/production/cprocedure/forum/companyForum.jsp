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
		<title>${obj[0] }</title> <script type="text/javascript"
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
					src="<%=basePath%>/js/ea/production/cprocedure/forum/companyForum.js"></script>
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
		<li style="width: 10%;"><a href="javascript:fanhui()"><img
				src="<%=basePath%>/images/BuildPlatform/left_jt.png">
		</a></li>
		<li style="width: 80%;">公司论坛</li>
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
				<ul class="lt_top">
					<li class="head"><img
						src="<%=basePath%>${obj[1]==null?'images/WFJClient/PersonalJoining/logo@2x.png':obj[1]}">
					</li>
					<li class="txt">
						<h4>
							帖子<span>${invitationCount }</span>&nbsp;粉丝<span>${attentionCount
								}</span>
						</h4></li>
					<li><input type="button" class="att"
						value="${attention.hetherAttention==false?'加关注':'取消关注' }">
					</li>
				</ul>
				<div id="menu">
					<ul class="tabs lt_lie">
						<li><a href="#;" class="common active">帖子</a>
						</li>
						<li><a href="#;" class="essence">精华</a>
						</li>
						<li><a href="#;" class="reply">回复</a>
						</li>
						<a href="###;" onclick="myall()">我的</a>
					</ul>
					<div id="tabs-container" class="swiper-container">
						<div class="swiper-wrapper swiper-no-swiping" id="menu_con">
							<!--帖子-->
							<div class="swiper-slide">
								<div class="content-slide">
									<ul>
										<!-- js拼接 -->
									</ul>
								</div>
							</div>
							<!--精华-->
							<div class="swiper-slide">
								<div class="content-slide">
									<ul>
										<!-- js拼接 -->
									</ul>
								</div>
							</div>
							<!--回复-->
							<div class="swiper-slide">
								<div class="content-slide">
									<ul>
										<!-- js拼接 -->
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="btn_jia">
					<img src="<%=basePath%>/images/BuildPlatform/jia.png" alt=""
						onclick="whetherRegister()"> <input type="file"
						accept="image/*" multiple="" id="imgs" style="display: none">
				</div>

			</div>
			<!-- <div class="alert"></div>-->
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
	<div class="alert_txt_"></div>
	<div class="alert_txt">
		<textarea rows="5" placeholder="回复设计星空"></textarea>
		<input type="button" value="取 消" id="qx2"> <input
			type="button" value="发 表" id="fb">
	</div>


	<script type="text/javascript">
	var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var ccompanyid = '${concom.ccompanyID}';
		var judge = "00";
		var companyid = '${obj[2]}';
		var sccid;
		var commonEssence = '${commonEssence}';
	</script>

	<script>
		jQuery.fn.limit = function() {
			var self = $("[limit]");
			self.each(function() {
				var objString = $(this).text();
				var objLength = $(this).text().length;
				var num = $(this).attr("limit");
				if (objLength > num) {
					//                $(this).attr("title",objString);
					objString = $(this).text(
							objString.substring(0, num) + "...");
				}
			})
		};
		$(function() {
			$("[limit]").limit();
		})
		function fanhui() {
//			document.location.href = basePath
//					+ "ea/industry/ea_getCompanyHome.jspa?ccompanyId="
//					+ ccompanyid;
            history.back();
		}
		window.onload = function() {
			/*2017.2.4*/
			$(".invitation_no").css("height",
					$("#menu_con .swiper-slide").height() + "px");
		}
	</script>
</body>
</html>