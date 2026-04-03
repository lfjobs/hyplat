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
		<title>督查审批</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/auditmanageNew.css">


        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
					<ul class="ul-header clearfix">
						<li>
						<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
							</a>
						</li>
						<li>
							督查审批
						</li>
						<li>
							
						</li>
					</ul>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-1">督查项目</p>
							<ul class="ul-con2">
								<li class="clearfix">
									<p class="p-2">策划层</p>
									<p class="p-3">股东会</p>
									<p class="p-3">监事会</p>
									<p class="p-3">董事会</p>
									<p class="p-3">工会</p>
									<p class="p-3">顾问会</p>
								</li>
							</ul>
							<ul class="ul-con2">
								<li class="clearfix">
									<p class="p-2">决策层</p>
									<p class="p-3">董事长室</p>
								</li>
								<li class="clearfix">
									<p class="p-2">执行层</p>
									<p class="p-3">总经理</p>
								</li>
								<li class="clearfix">
									<p class="p-2">功能层</p>
									<p class="p-3">人事</p>
									<p class="p-3">办公</p>
									<p class="p-3">财务</p>
									<p class="p-3">生产</p>
									<p class="p-3">营销</p>
								</li>
								<li class="clearfix">
									<p class="p-2">创收层</p>
									<p class="p-3">创收</p>
								</li>
							</ul>
						</li>
						<li class="clearfix">
								<p class="p-1">督查巡查</p>
								<p class="p-2">检查项目</p>
								<p class="p-2">检查实施</p>
								<p class="p-2">考评检查</p>
								<p class="p-2">处理结果</p>
						</li>
						<li class="clearfix">
							<p class="p-1">审批管理</p>
							<p class="p-2">审批</p>
							<p class="p-2">已审批</p>
							<p class="p-2">未审批</p>
							<p class="p-2">驳回</p>
							<p class="p-2">作废</p>
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

		</script>
	</body>
</html>
