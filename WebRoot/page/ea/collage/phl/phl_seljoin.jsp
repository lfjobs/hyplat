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
	<title>司机加入选择</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_seljoin.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var ccompanyID='';
        $(function () {
                var url = document.location.toString();
                var arrUrl = url.split("=");
            ccompanyID = arrUrl[1];
        });
        function toCarJoin(sel) {
            window.location.href="<%=basePath%>page/ea/collage/phl/phl_carjoin.jsp?ccompanyID="+ccompanyID+"&sel="+sel;
        }
	</script>
</head>
<body>
<div class="content">
	<div>
		<a id="ownCar" href="javascript:;" onclick="toCarJoin(1)">
			<p>
				<img src="<%=basePath%>images/ea/collage/phl/car.png"/>
			</p>
			<p>
				个人有车加入
			</p>
		</a>
	</div>
	<div>
		<a href="#" onclick="toCarJoin(2)">
			<p>
				<img src="<%=basePath%>images/ea/collage/phl/nocar.png"/>
			</p>
			<p>
				个人无车加入
			</p>
		</a>
	</div>
</div>
</body>
</html>
