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
		<title>总账明细账</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/finance/zzdetail.css">
		
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
						总账明细账
					</li>
					<li>
						
					</li>
				</ul>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">总账管理</p>
							<p class="p-height">
								
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">固定资产</p>
							<p class="p-height">
								固定资产  资产报损管理  资产增加 资产减少  资产报表
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">应付应收管理</p>
							<p class="p-height">
								应收管理  应付管理  应收明细  应 付明细
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">工资管理</p>
							<p class="p-height">
								应付工资  已付工资  工资报表  工 资分摊  计件工资
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">销售管理</p>
							<p class="p-height">
								客户管理  销售订货  销售发货  销 售退货  销售调拨
							</p>
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
			//计算列表高度
			$(".p-height").each(function(){
				$(this).parent().find(".p-title").css('line-height',$(this).height()+"px");
				$(this).parent().find(".div-more").css('line-height',$(this).height()+"px");
			})
		</script>
	</body>
</html>
