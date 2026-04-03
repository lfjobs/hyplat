<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>5L5C公文流转主页</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/flexslider.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/jquery.mmenu.all.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/style.css" />

		<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/jquery.mmenu.min.all.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/jquery.flexslider.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/o-script.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/documentNavControl.js"></script>
</head>
<style>
.i-pane{
color:white;
}
</style>
<body class="o-page">
<div id="page">
			<!-- Header -->
			<div id="header">
				<a href="#menu"><img src="<%=basePath%>images/websuitMini/menu.png"  alt=""/></a><a class="backBtn" href="javascript:history.back();"><img src="<%=basePath%>images/websuitMini/history.png"  alt=""/></a>公 文 流 转</div>
			
			<!-- Banner -->
			<div class="bannerPane">
			  <section class="slider">
				<div class="flexslider">
				  <ul class="slides">
					<li>
						<img src="<%=basePath%>images/cp_01.gif" />
						</li>
						<li>
						<img src="<%=basePath%>images/cp_03.gif" />
						</li>
						<li>
						<img src="<%=basePath%>images/cp_04.gif" />
						</li>
				  </ul>
				</div>
			  </section>
			</div>
			<!-- End Banner -->
			
			<!-- Content -->
			<div id="content">
				<div class="mainIconPane">
					<span class="i-pane i-green">
						<i class="i-phone"></i>
					</span>
					<h4>特色一</h4>
					<p>
						<span class="c-green">个人</span> 收件箱实时查阅，快速转至领导人审批
					</p>
				</div>
				
				<div class="mainIconPane">
					<span class="i-pane i-orange">
						<i class="i-neat"></i>
					</span>
					<h4>特色二</h4>
					<p>
						<span class="c-orange">网页</span> 操作如今变得轻盈，便捷，采用最新HTML5技术
					</p>
				</div>
				
				<div class="mainIconPane">
					<span class="i-pane i-red">
						<i class="i-flexible"></i>
					</span>
					<h4>特色三</h4>
					<p>
						<span class="c-red">上传</span>可将提前编写好的公文文件通过手机网站上传至服务器！
					</p>
				</div>
			</div>
			<div class="block">
				探索我们的网站
			</div>
			
			<div class="exploreSiteFullPane">
				
				<a href="window.location.href='<%=basePath%>page/ea/websuitMini/documentmain1.jsp'" class="explorePane middle">
					<span class="i-pane i-blue">
						<i class="i-home">home</i>
					</span>
					<h4>主页</h4>
				</a>
				<a href="about.html" class="explorePane middle">
					<span class="i-pane i-blue">
						<i class="i-about">about</i>
					</span>
					<h4>关于我们</h4>
				</a>
				<a href="blog.html" class="explorePane">
					<span class="i-pane i-blue">
						<i class="i-blog">blog</i>
					</span>
					<h4>博客</h4>
				</a>
				<a href="gallery.html" class="explorePane">
					<span class="i-pane i-blue">
						<i class="i-gallery">gallery</i>
					</span>
					<h4>Gallery</h4>
				</a>
				<a href="shortcodes.html" class="explorePane middle">
					<span class="i-pane i-blue">
						<i class="i-shortcodes">shortcodes</i>
					</span>
					<h4>Shortcodes</h4>
				</a>
				<a href="contact.html" class="explorePane">
					<span class="i-pane i-blue">
						<i class="i-contact">contact</i>
					</span>
					<h4>联系我们</h4>
				</a>
			</div>
			
			<!-- Social Icons -->
			<div class="socialMedia scl-grey">
				<a href="#" class="ico-facebook" title="facebook">facebook</a>
				<a href="#" class="ico-twitter" title="twittertwitter">twitter</a>
				<a href="#" class="ico-linkedin" title="linkedin">linkedin</a>
				<a href="#" class="ico-gplus" title="gplus">gplus</a>
				<a href="#" class="ico-youtube" title="youtube">youtube</a>
				<a href="#" class="ico-tumblr" title="tumblr">tumblr</a>
				<a href="#" class="ico-pinterest" title="pinterest">pinterest</a>
			</div>
			
			<!-- Menu navigation -->
			<nav id="menu">
				<ul>
					<li class="Selected">
						<a>
							<i class="i-home i-small"></i>拟稿
						</a>
                        <ul>
							<li><a  id="uploadfile" onclick="document.location.href='<%=basePath%>page/ea/websuitMini/uploaddocumentFile.jsp'">起草上传</a></li>
							<li>
								<a onclick="document.location.href='<%=basePath%>ea/documentinfo/ea_getReceiveBoxList.jspa?searchType=mobile&data=Math.random()'">收件箱</a>
							</li>
							<li>
								<a href="blog-single-post.html">已发送</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-about i-small"></i>审批
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未审批公文</a></li>
							<li>
								<a href="blog-single-post.html">已审批公文</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-blog i-small"></i>盖章
						</a>
						<ul>
							<li><a href="blog-single-post.html">已盖章</a></li>
							<li>
								<a href="blog-single-post.html">未盖章</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-gallery i-small"></i>发文管理
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未发公文</a></li>
							<li>
								<a href="blog-single-post.html">已发公文</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-shortcodes i-small"></i>阅读管理
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未阅读</a></li>
							<li>
								<a href="blog-single-post.html">已阅读</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-contact i-small"></i>公文归档
						</a>
                        <ul>
							<li><a href="blog-single-post.html">已归档</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
</body>
</html>
