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
		<title>规划管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/zlplanmanageNew.css">


        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
	
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
					<ul class="ul-header clearfix">
						<li onclick="toBack()">
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
						</li>
						<li>
							规划管理
						</li>
						<li>

						</li>
					</ul>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-1">办公室</p>
							<p class="p-2">组织构</p>
							<p class="p-2">规划办公</p>
						</li>
						<li class="clearfix">
							<p class="p-1">品牌文化</p>
							<ul class="ul-con2">
								<li class="clearfix">
									<p class="p-2">企业品牌</p>
									<p class="p-3">企业VI</p>
									<p class="p-3">企业形象</p>
									<p class="p-3">品牌物料</p>
									<p class="p-3">品牌推广</p>
								</li>
							</ul>
							<ul class="ul-con2">
								<li class="clearfix">
									<p class="p-2">企业文化</p>
									<p class="p-3">企业简介</p>
									<p class="p-3">企业目标</p>
									<p class="p-3">企业活动</p>
									<p class="p-3">企业资讯</p>
								</li>
							</ul>
						</li>
<%--						<a href="<%=basePath%>/page/WFJClient/pc/5l5C/office/zchanmanage.jsp?compayid=${param.companyID}&sccId=&staffid=${param.staffID}">--%>
						<li class="clearfix">
							<p class="p-1">项目管理</p>
							<ul class="ul-con2">
								<li class="clearfix">
									<p class="p-2">
										<span id="bType">项目设计</span>
									</p>
									<p class="p-2">
										<span id="costItem">初始项目</span>
									</p>
									<p class="p-2">
										<span id="tbgl">投标管理</span>
									</p>
<%--									<p class="p-2">收标预算</p>--%>
									<p class="p-2">比价审批</p>
<%--									<p class="p-2">申请账单</p>--%>
									<p class="p-2">收付管理</p>
									<p class="p-2">库存管理</p>
								</li>
							</ul>
						</li>
<%--						</a>--%>
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
			$("#bType").on('click', function () {
				window.location.href="<%=basePath%>/page/WFJClient/pc/5l5C/office/businessTypeManage.jsp?version=1.1"
			});
			$("#costItem").on('click', function () {
				<%--window.location.href="/page/WFJClient/pc/5l5C/office/budgetBill/costBudgetItemManage.jsp?companyID=${param.companyID}&staffID=${param.staffID}"--%>
				window.location.href="<%=basePath%>ea/scBudget/ea_toCostBudgetBillList.jspa?tenantFlag=other&companyid=${param.companyID}&staffId=${param.staffID}&menuId=ng"
			});
			$("#tbgl").on('click', function () {
				<%--window.location.href="<%=basePath%>/page/WFJClient/pc/5l5C/office/testPosition.jsp?companyID=${param.companyID}&staffID=${param.staffID}"--%>
			});

			//后退
			function toBack() {
				window.location.href = "<%=basePath%>page/WFJClient/pc/5l5C/office/officeindexNew.jsp";
			}

			//监听点击浏览器后退
			$(function(){
				pushHistory();
				window.addEventListener("popstate", function(e) {
					toBack();
				}, false);
				function pushHistory() {
					var state = {
						title: "title",
						url: ""
					};
					window.history.pushState(state, "title", "");
				}
			});
		</script>
	</body>
</html>
