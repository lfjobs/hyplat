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
		<title>企业战略规划管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/zlplanmanage.css">
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
							企业战略规划管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="content">
					<div class="div-nav">
						<ul class="ul-nav clearfix">
							<li class="active">
								企业战略规划管理 
							</li>
							<li>
								项目管理
							</li>
						</ul>
					</div>
					<div class="div-tap">
						<ul class="ul-con">
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_12.png"/>
									</p>
									<p>公司规划管理</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_13.png"/>
									</p>
									<p>部门规划管理</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_79.png"/>
									</p>
									<p>个人规划管理</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_15.png"/>
									</p>
									<p>职业规划管理</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_16.png"/>
									</p>
									<p>项目规划设计</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
						</ul>
					<ul class="ul-con">
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_07.png"/>
									</p>
									<p>项目预算计划</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_80.png"/>
									</p>
									<p>项目授权分配</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_09.png"/>
									</p>
									<p>项目跟踪</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_10.png"/>
									</p>
									<p>项目质量考评</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
							<li class="clearfix">
								<div class="clearfix">
									<p>
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_11.png"/>
									</p>
									<p>项目成果</p>
								</div>
								<p class="p-right">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</p>
							</li>
						</ul>
					</div>
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
			//导航切换
			$('.ul-nav li').click(function(){
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
				$(".div-tap ul").hide();
				$(".div-tap ul").eq($(this).index()).show();
			});
		</script>
	</body>
</html>
