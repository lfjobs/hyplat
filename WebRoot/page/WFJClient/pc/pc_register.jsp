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
		<title>注册</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/login/pc_register.css">
		<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/login/l_zoom.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/l_zoom.min.js"></script>
		<script src="<%=basePath%>js/l_drag.min.js"></script>
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
				<header>
					<ul class="clearfix">
						<li>
							<a href="javascript:history.back(-1)" >
							<img src="<%=basePath%>images/WFJClient/pc/login/img_03.png"/>
							</a>
						</li>
						<li>
							注册
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="content">
					<ul>
						<li class="clearfix">
							<label for="">
								<img src="<%=basePath%>images/WFJClient/pc/login/img_07.png"/>
							</label>
							<input type="text" placeholder="请填写姓名" name="" id="" value="" />
						</li>
						<li class="clearfix">
							<label for="">
								<img src="<%=basePath%>images/WFJClient/pc/login/img_10.png"/>
							</label>
							<input type="text" placeholder="请填写手机号" name="" id="" value="" />
						</li>
						<li class="clearfix">
							<label for="">
								<img src="<%=basePath%>images/WFJClient/pc/login/img_13.png"/>
							</label>
							<input type="text" placeholder="请输入验证码" name="" id="" value="" />
							<input type="button" name="" id="" class="inp-button" value="获取验证码" />
						</li>
						<li class="clearfix">
							<label for="">
								<img src="<%=basePath%>images/WFJClient/pc/login/img_16.png"/>
							</label>
							<input type="text" placeholder="请设置密码" name="" id="" value="" />
						</li>
					</ul>
				</div>
				<div class="div-bottom">
					<p>
						完成
					</p>
				</div>
				
			</div>
		</div>
		<script type="text/javascript">
			//启用拖动以及缩放
//			$('.pc-box').l_zoom('free').l_drag();
		</script>
	</body>
</html>
