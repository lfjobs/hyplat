<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>使用中</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/opensuc.css"/>

<script>
    var basePath = "<%=basePath%>";
</script>

</head>
<body>
<header>
	<ul class="clearfix">
		<li>
            &nbsp;
		</li>
		<li>
			请稍后再试
		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-img"><img src="<%=basePath%>images/supermarket/container/wgw.png"></p>
	<p class="p-title">其他顾客正在挑选商品请稍后再试!</p>



</div>
</body>
</html>