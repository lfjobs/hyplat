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
		<title>5L5C</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/pxmanage.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
				<header>
					<ul class="clearfix">
						<li>
						   <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
							</a>
						</li>
						<li>
							培训管理
						</li>
						<li>

						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">培训题库</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">教师管理</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">场地管理</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">办公管理</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">管理培训</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">合格管理</p>

							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>

						</li>
						
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


			//判断页面是否有底部导航
			if($("*").is(".div-bottom")){
				$(".container").addClass("pc-bottom");
			}
		</script>
	</body>
</html>
