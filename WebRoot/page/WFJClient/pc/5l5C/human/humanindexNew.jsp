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
		<title>人事管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
			<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/humanindex.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var sort = "${param.sort}";
			var companyID = "${param.companyID}";
			var staffID = "${param.staffID}";
		</script>
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
							人事管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<%-- <section class="clearfix">
						<div>
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_02.png"/>
						</div>
						<p>
							白静雨
						</p>
					</section> --%>
					<ul class="ul-con">
					<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/orgsystemNew.jsp">
						<li class="clearfix">
							<p class="p-title">组织系统</p>
							<p class="p-height clearfix">
								系统认证
								密码管理
								版本升级
								组织授权
								系统日志
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/zpmanageNew.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
						<li class="clearfix">
							<p class="p-title">招聘管理</p>
							<p class="p-height">
								用人管理
								招聘面试
								招聘收件
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/lzmanage.jsp">
							<li class="clearfix">
								<p class="p-title">培训管理</p>
								<p class="p-height">
									培训项目
									培训题库
									培训实施
									培训结果
								</p>
								<div class="div-more">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</div>
							</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/rzlmanage.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
						<li class="clearfix">
							<p class="p-title">入在离管理</p>
							<p class="p-height">
								入职
								在职
								离职
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>

							<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/societyhr.jsp">
						<li class="clearfix">
							<p class="p-title">社会人力</p>
							<p class="p-height">
							社会人力资源管理
							现代人力管理
								历史人力管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
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
			//计算中间区域宽度
			$(".p-height").each(function(){
				var pWth=$(".pc-box").width()-$(this).prev().width()-100;
				$(this).width(pWth+"px")
			})
			//计算列表高度
			$(".p-height").each(function(){
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
				var pHei=$(this).parent().outerHeight()-51;
				$(this).parent().find(".p-title").css('line-height',pHei+"px");
				$(this).parent().find(".div-more").css('line-height',pHei+50+"px");
			})
			//判断页面是否有底部导航
			if($("*").is(".div-bottom")){
				$(".container").addClass("pc-bottom");
			}
		</script>
	</body>
</html>
