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
		<meta charset="UTF-8">
		<title>网络推广</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/market/networktg.css">
		
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
				<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
		
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
				<div class="div-header">
					5L5C管理系统
				</div>
				<ul class="ul-header clearfix">
					<li>
					<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
						<img src="<%=basePath%>images/WFJClient/pc/5l5c/return.png"/>
						</a>
					</li>
					<li>
						网络推广
					</li>
					<li>
						
					</li>
				</ul>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">短信推广管理</p>
							<p class="p-height">
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">邮件推广管理</p>
							<p class="p-height">
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">博客推广管理</p>
							<p class="p-height">
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">微信推广管理</p>
							<p class="p-height">
								微信活动管理  微信菜单管理  微信 招聘管理
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">QQ推广管理</p>
							<p class="p-height">
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">网站推广管理</p>
							<p class="p-height">
							</p>
						</li>
						<!--<li class="clearfix">
							<p class="p-title">销售报表</p>
							<p class="p-height">
								毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
							</div>
						</li>-->
					</ul>
				</div>
				<div class="footer div-bottom">
					<ul class="clearfix">
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
							</div>
							<p>
								消息
							</p>
						</li>
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
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
						<li class="active">
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
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
			</div>
		</div>
		<script type="text/javascript">
				var  basePath = "<%=basePath%>";
			//计算列表高度
			$(".p-height").each(function(){
				$(this).parent().find(".p-title").css('line-height',$(this).height()+"px");
				$(this).parent().find(".div-more").css('line-height',$(this).height()+"px");
			})
		</script>
	</body>
</html>
