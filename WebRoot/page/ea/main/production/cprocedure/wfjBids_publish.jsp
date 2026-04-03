<!DOCTYPE html>
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

<html style="font-size: 62.5%;background: #f4f4f4;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />

<title>发布需求</title>

<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/wfjBids_publish.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
</script>


</head>
<body>

	<div class="tan_mo dN">
		<div class="tan_main_out"></div>
		<div class="tan_main">
			<div class="tan_main_lis">
				<h4>请选择合适的类别</h4>
			</div>
			<div class="tan_main_lis_hide">
				<ul class="tabul">


				</ul>
			</div>


		</div>
	</div>
	<form name="form1" id="form1" enctype="multipart/form-data" method="post" >
	<div class="tan_out">
		<div class="top-sp">
			<ul>
				<li class="arrar">
					<div>
						<img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
					</div></li>
				<li><h5 class="shangpin">描述需求</h5>
				</li>
			</ul>
		</div>
		<div class="main_hide">
			<p class="demand_head">请描述您的需求</p>
			<div class="demand_top">
				<textarea class="" name="" id="" cols="" rows="5"
					placeholder="如:我有什么样的要求"></textarea>
			</div>
			<div class="demand_top_pic"></div>
			<div class="demand_add">
				<img onclick="fileSelect();"
					src="<%=basePath%>images/ea/bids/wfj_addimg_03.png" alt="" />
			</div>
			
				<input style="display: none;" id="fileToUpload" type="file" name="photo"
					onchange="change(this)" />
			
			<div class="demand_mid">
				<div class="demand_mid_lis add_leibie">
					<ul class="">
						<li class="pull-left">类别</li>
						<li class="pull-left"><span class="demand_mid_lis_lei"><input
								class="pullImg" placeholder="选择类别" />
						</span>
						</li>
						<li class="pull-left"><img
							src="<%=basePath%>images/ea/bids/wfj_return_07.png" alt="" />
						</li>
					</ul>
				</div>
				<div class="demand_mid_lis">
					<ul class="">
						<li class="pull-left">预算</li>
						<li class="pull-left"><input class="pullImg" type="number"
							placeholder="请输入您觉得合适的价格" />
						</li>
					</ul>
				</div>
			</div>
			<div class="demand_bot">
				<div class="demand_bot_lis">
					<span>是否是企业</span> <label class="switch"> <input
						class="mui-switch mui-switch-animbg" type="checkbox" checked>
					</label>
				</div>
			</div>
			<div class="demand_di">
				<div class="but_tijiao">提交</div>
			</div>
		</div>
	</div>
	</form>
	<script>
		$(function() {
			$(".main_hide").css(
					{
						"height" : $(window).height()
								- $(".top-sp").outerHeight() + "px"
					});
			$(".tan_mo").css({
				"height" : $(window).height() + "px",
				"width" : $(window).width() + "px"
			});
			$(".tan_main_lis_hide").css(
					{
						"height" : $(window).height()
								- $(".tan_main .tan_main_lis").height() + "px",
						"overflow" : "auto"
					});
			$(".tan_main_out").css("width",
					$(window).width() - $(".tan_main").width() + "px").css(
					"height", $(window).height() + "px");
			$(".add_leibie").click(function() {
				$(".tan_mo").show();
			});
			$(".tan_main_out").click(function() {
				$(".tan_mo").hide();
			});
			$(".tan_main_lis_hide ul li").click(function() {
				var lei = $(this).html();
				$(".tan_mo").hide();
				$(".demand_mid_lis_lei").html(lei);
			})

		})
	</script>

</body>
</html>