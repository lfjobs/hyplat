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
		<title>行政管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/xzmanage.css">
		<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/login/l_zoom.css">
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
							行政管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="content">
					<ul class="ul-con">
					<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/dwmanage.jsp">
						<li class="clearfix">
						
							<div class="clearfix">
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_02.png"/>
								</p>
								<p>单位管理</p>
							</div>
							<p class="p-right">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</p>

						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/qyculture.jsp">
						<li class="clearfix">
							<div class="clearfix">
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_81.png"/>
								</p>
								<p>文化建设</p>
							</div>
							<p class="p-right">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</p>
						</li>
						</a>
							<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/xzbulid.jsp">
						<li class="clearfix">
							<div class="clearfix">
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_04.png"/>
								</p>
								<p>行政建设</p>
							</div>
							<p class="p-right">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</p>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/officezlmanage.jsp">
						<li class="clearfix">
							<div class="clearfix">
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_05.png"/>
								</p>
								<p>办公资料</p>
							</div>
							<p class="p-right">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</p>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/zlsearch.jsp">
						<li class="clearfix">
							<div class="clearfix">
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_82.png"/>
								</p>
								<p>资料库</p>
							</div>
							<p class="p-right">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</p>
						</li>
						</a>
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
//			//底部导航点击
//			$('.div-bottom ul li').click(function(){
//				$(this).siblings().removeClass("active");
//				$(this).addClass("active")
//			});
		</script>
	</body>
</html>
