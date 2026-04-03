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
		<title>信息管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
			<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/infomanage.css">
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
							信息管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">网络管理</p>
							<p class="p-height clearfix">
								软件系统管理   IP地址管理   短信管理   微信管理   QQ管理 网络硬盘管理   域名管理   网络传真管理   呼叫中心管理
							</p>
							<%-- <div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div> --%>
						</li>
						<li class="clearfix">
							<p class="p-title">公共信息管理</p>
							<p class="p-height">
								公告通知管理   公共传媒   投票管理   公共电话簿 简报管理   数据提醒管理  公共日程管理  公共日志管理
							</p>
							<%-- <div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div> --%>
						</li>
						<li class="clearfix">
							<p class="p-title">企业信息管理</p>
							<p class="p-height">
								企业信息管理   企业信息推广
							</p>
							<%-- <div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div> --%>
						</li>
						<li class="clearfix">
							<p class="p-title">网络安全管理</p>
							<p class="p-height">
								网络加密管理   网络杀毒管理   网络建设1   带宽建设2 网络安全建设3   服务器建设4   终端建设
							</p>
							<%-- <div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div> --%>
						</li>
						<li class="clearfix">
							<p class="p-title">消息管理</p>
							<p class="p-height">
								消息提醒
							</p>
							<%-- <div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div> --%>
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
