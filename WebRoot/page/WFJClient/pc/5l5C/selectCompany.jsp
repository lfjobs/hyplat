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
		<title>选择公司</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/selectCompany.css?version=1">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/5l5C/selectCompany.js" type="text/javascript" charset="utf-8"></script>

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
							选择公司
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="content">
					<div class="div-con">
						<ul class="xz">
							<%-- <li>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_08.png"/>
								<div class="div-right">
									<p class="p-top">
										北京天太胜威国际投资管理有限公司
									</p>
									<p class="p-bottom">
										北京  东城  东直门外大街
									</p>
									<!-- <ul class="clearfix">
										<li>
											已上市
										</li>
										<li>
											20-100人
										</li>
										<li>
											移动互联网
										</li>
									</ul> -->
								</div>
							</li>
							<li>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_08.png"/>
								<div class="div-right">
									<p class="p-top">
										北京天太胜威国际投资管理有限公司
									</p>
									<p class="p-bottom">
										北京  东城  东直门外大街
									</p>
									
								</div>
							</li>
							<li>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_08.png"/>
								<div class="div-right">
									<p class="p-top">
										北京天太胜威国际投资管理有限公司
									</p>
									<p class="p-bottom">
										北京  东城  东直门外大街
									</p>
									
								</div>
							</li>
							<li>
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_08.png"/>
								<div class="div-right">
									<p class="p-top">
										北京天太胜威国际投资管理有限公司
									</p>
									<p class="p-bottom">
										北京  东城  东直门外大街
									</p>
									
								</div>
							</li> --%>
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
			var sccId  = "${param.sccId}";
			var bd = "${param.bd}";
			if(null == sccId || "" == sccId){
				sccId = '<%= request.getAttribute("sccId")%>';
			}
		</script>
	</body>
</html>
