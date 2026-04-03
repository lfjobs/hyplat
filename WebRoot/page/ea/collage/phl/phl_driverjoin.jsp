<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>司机加入</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_car.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/slick/slick.css"/>
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>css/ea/collage/phl/slick/slick.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="content">
	<section>
		<h3 class="clearfix">
			有货拉物流
			<span><a href="#">司机加盟</a></span>
		</h3>
		<ul>
			<li class="clearfix">
				<img src="<%=basePath%>images/ea/collage/phl/img_06.png"/>
				<menu>
			<li>
				车主：吴师傅
			</li>
			<li>
				车牌号：鄂C58866
			</li>
			<li>
				载重：900公斤
			</li>
			<li>
				载重体积：2.4方
			</li>
			</menu>
			<menu>
				<li>
					车型：小面包车
				</li>
				<li>
					长宽高：1.8*1.2*1.1
				</li>
				<li>
					市场：湖北宏伟批发市场
				</li>
			</menu>
			</li>
			<li class="clearfix">
				<img src="<%=basePath%>images/ea/collage/phl/img_07.png"/>
				<menu>
			<li>
				车主：吴师傅
			</li>
			<li>
				车牌号：鄂C58866
			</li>
			<li>
				载重：900公斤
			</li>
			<li>
				载重体积：2.4方
			</li>
			</menu>
			<menu>
				<li>
					车型：小面包车
				</li>
				<li>
					长宽高：1.8*1.2*1.1
				</li>
				<li>
					市场：湖北宏伟批发市场
				</li>
			</menu>
			</li>
		</ul>
	</section>
</div>
</body>
</html>

