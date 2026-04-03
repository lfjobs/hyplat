<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>行业导航列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/css/WFJClient/wfjapp.css" type="text/css"></link>
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
</head>
<body>
	<div class="wfj12_019">
		<div class="wfj12_019_numshop_top" style='border-bottom:1px solid #ddd;'>
			<ul>
				<li><a href="<%=basePath %>ea/industry/ea_getAllCompanyList.jspa?industryType="
					target="_self"><img src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></li>
				<li>行业资源平台</li>
				<li>
					<%-- <a href="javascript:;"><img
						src="<%=basePath%>images/WFJClient/PersonalJoining/top_more.png" /> --%>
					</a></li>
			</ul>
		</div>
		<!--地球数字商城搜索框-->
		<div class="wfj_search" id="wfjsearch">
			<div class="wfj_width">
				<ul>
					<li><input style="outline:none;" type="text" value="请输入行业名称或公司名称进行查询"
						onfocus="this.value='';"
						onblur="if(this.value==''){this.value='请输入行业名称或公司名称进行查询';}" /></li>
					<li><img src="<%=basePath%>images/WFJClient/PersonalJoining/iconfont-sousuo.png" id="img" />
					</li>
				</ul>
			</div>
		</div>

		<div class="wfj12_019_content">
			<div class="wfj12_019_hidden">
				<!--地球数字商城-->
				<div class="wfj12_019_numshop disnone">
					<div class="wfj12_019_numshop_left" style="width:35%;">
						<div class="wfj12_019_numshop_left_content">
							<div class="wfj12_019_numshop_left_hidden">
								<ul id="daohang"></ul>
							</div>
						</div>
					</div>
					<div class="wfj12_019_numshop_right" style="width:65%;">
						<div class="wfj12_019_numshop_right_content">
							<div class="wfj12_019_numshop_right_hidden">
								<ul id="hangyecontent" style="width: 98%;margin: 0 1%;"></ul>
							</div>
						</div>
					</div>
				</div>
				<!--地球数字商城 end-->
			</div>
		</div>
	</div>

	<script type="text/javascript">
	var basePath = "<%=basePath%>";
    </script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/industrylist.js"></script>
</body>
</html>