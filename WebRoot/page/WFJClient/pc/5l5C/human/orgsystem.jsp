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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/orgsystem.css">
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
							组织系统
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
						<li class="clearfix">
							<p class="p-title">企业认证</p>
							<p class="p-height">
								<a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01">认领认证</a>
								<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/codemanage.jsp">元素代码</a>
								<a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01&sourcePage=00">银行卡管理</a>
								<a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=01&sourcePage=01">超级管理</a>
								<a href="">集团管理</a>
							</p>
							<%--<div class="div-more">--%>
								<%--<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>--%>
							<%--</div>--%>
						</li>

						
						<li class="clearfix">
						
							<p class="p-title">组织授权</p>
							<p class="p-height">
								组织机构
								部门职能
								岗位账号
							</p>
							<%--<div class="div-more">--%>
								<%--<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>--%>
							<%--</div>--%>
						</li>

						<li class="clearfix">
							<p class="p-title">密码管理</p>
							<p class="p-height">
								<a href="">密码管理</a>
								<a href="">公司交易</a>
							</p>
							<%--<div class="div-more">--%>
								<%--<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>--%>
							<%--</div>--%>
						</li>
						<li class="clearfix">
							<p class="p-title">系统日志</p>
							<p class="p-height">
							</p>
							<!--<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>-->
						</li>
						<li class="clearfix">
							<p class="p-title">版本升级</p>
							<p class="p-height">
							</p>
							<!--<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>-->
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
				var pWth=$(".pc-box").width()-$(this).prev().width()-80;
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
