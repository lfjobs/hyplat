<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>网站咨询详情</title>

<link href="<%=basePath%>myPC/css/style.css" rel="stylesheet"
	type="text/css">
<script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="<%=basePath%>myPC/js/html5shiv.min.js"></script>
    <script src="<%=basePath%>myPC/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="header">
		<ul>
			<li class="logo">
			                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>myPC/images/wfj.png" alt="" class="log"></a>

			</li>
			<li class="name">
				<div>
					<h3>中国互联网行业领导品牌</h3>
					<h5>北京天太世统科技有限公司</h5>
				</div>
			</li>
			<li class="login"><a id="login"> <input type="button"
					value="登录"> </a> <a id="register"> <input type="button"
					value="注册" id="zc"> </a></li>
		</ul>
	</div>
	<div class="content con">
		<div class="ind_con_head">
			<ul>
				<a id="index"><li>网站首页</li> </a>
				<a id="Web-New"><li class="active">网站咨询</li> </a>
				<a id="bg"><li>办公</li> </a>
				<a id="Dress-custom"><li>服务专区</li> </a>
			</ul>
		</div>
		<div class="web_con web_con_det">
			<div class="web_con_det_head">
				<a href="#;">首页</a>><a href="#;">网站资讯</a>><a href="#;">行业资讯</a>
			</div>
			<div class="web_con_left">
				<div class="web_con_left1 web_con_left3">
					<h3>推荐资料</h3>
				</div>
				<ul class="web_con_left4">
					<a href="#;">
						<li>
							<h3>解析：互联网平台SEO优化布局的基本思路</h3>
							<p>
								<img src="<%=basePath%>myPC/images/001.jpg" alt=""><span>今天与大家分享就闲话少说咱们直接进入主题</span>
							</p></li> </a>
					<hr
						style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">
					<a href="#;">
						<li>
							<h3>交通运输部关于修改修改</h3>
							<p>
								<img src="<%=basePath%>myPC/images/news.png"><span>《交通运输部关于修改《道路运输从业人才</span>
							</p>
					</li> </a>
					<hr
						style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">
					<a href="#;">
						<li>
							<h3>交通运输部关于修改修改</h3>
							<p>
								<img src="<%=basePath%>myPC/images/news.png"><span>《交通运输部关于修改《道路运输从业人才</span>
							</p>
					</li> </a>
					<hr
						style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">

				</ul>
			</div>
			<!--服务详情  -->
			<c:if test="${param.nu=='服务' }">
			 <%@ include file="/myPC/fuwu.jsp" %>
			 </c:if>
			 <!--资料详情  -->
			<c:if test="${param.nup=='资料' }">
			 <%@ include file="/myPC/zl.jsp" %>
			 </c:if>
			
		</div>
	</div>
	<a href="#header" class="return"><img
		src="<%=basePath%>myPC/images/return.png"> </a>
	</div>
	<div id="footer">
		<div class="text footer_txt">
			<p>
			
			</p>
			<p>
				Copyright &copy; 2010-2015北京天太世统科技有限公司<span
					style="padding-left: 50px;">京ICP备10034132号 </span>
			</p>
			<p>
				公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span>
			</p>
			<p>版权所有：北京天太世统科技有限公司</p>
			
		</div>
	</div>
	<div class="alert_"></div>
	<div class="alert">
		<h3>系统信息</h3>
		<div>
			<h4>科技前沿资料</h4>
			<div class="text">
				<p>
					需要：&yen;<span>1.00</span>元
				</p>
				<p>注：如需下载可注册VIP会员后付费下载</p>
				<div class="anniu">
					<a id="pay"><input type="button" value="VIP专享下载" id="zhuan">
					</a> <input type="button" value="返回" id="return">
				</div>
				<p class="last_p">最终解释权归北京天太世统科技有限公司所有</p>
			</div>
		</div>
	</div>
	<script>
    $(document).ready(function(){
    var basePath = "<%=basePath%>";
			$(".alert").css({
				top : $(window).height() * 0.5 - 134 + 'px',
				left : $(window).width() * 0.5 - 200 + 'px'
			});

			$(".ind_con_head ul li").click(function() {
				$(this).addClass("active").siblings().removeClass("active");
			});
			 $("#login").click(function(){
            var url = basePath+"myPC/login.jsp";
            document.location.href = url;
        });
			$("#index").click(function() {
				var url = basePath + "ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
				document.location.href = url;
			});
			$("#bg").click(function() {
				var url = basePath + "page/ea/index.jsp";
				document.location.href = url;
			});
			$("#register").click(function() {
				var url = basePath + "myPC/register.jsp";
				document.location.href = url;
			});
			$("#Web-New").click(function() {
				 var url = basePath+"ea/wfjshop/ea_getNews.jspa";
            document.location.href = url;
			});
			$("#Dress-custom").click(function() {
				var url = basePath + "myPC/Dress-custom.jsp";
				document.location.href = url;
			});
			$("#pay").click(function() {
				var url = basePath + "myPC/pay.jsp";
				document.location.href = url;
			});
			$("#return").click(function() {
				history.go(-1);
			});
			//绑定滚动条事件
			$(window).bind("scroll", function() {
				var sTop = $(window).scrollTop();
				var sTop = parseInt(sTop);

				if (sTop >= 1100) {
					$(".return").slideDown();
					$(".return").show();
				} else {
					$(".return").hide();
				}
			});

		})
	</script>
</body>
</html>