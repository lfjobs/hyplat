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
	<title>货品分类</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_procate.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_procate.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript">

        var basePath="<%=basePath%>";

	</script>
</head>
<body>
<header>
	<section>
		<input type="search" name="" id="" value="" placeholder="搜索货品分类" />
	</section>
</header>
<div class="content">
	<menu class="leftmenu">
		<%--<li class="active">--%>
			<%--水果--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--蔬菜--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--畜牧水产--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--粮油米面--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--农副加工--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--苗木花草--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--中药材--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--包装--%>
		<%--</li>--%>
	</menu>
	<!--以下分类内容最多五个字-->
	<menu class="rightmenu">
		<%--<li>--%>
			<%--<p>全部</p>--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--<h3 class="li_bor">--%>
				<%--油类--%>
			<%--</h3>--%>
			<%--<ul class="clearfix">--%>
				<%--<li>花生油</li>--%>
				<%--<li>黄秋葵油</li>--%>
				<%--<li>火麻油</li>--%>
				<%--<li>红花籽油</li>--%>
				<%--<li>核桃油</li>--%>
				<%--<li>芝麻油</li>--%>
				<%--<li>蚝油</li>--%>
				<%--<li>芝麻油</li>--%>
				<%--<li>葵花籽油</li>--%>
			<%--</ul>--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--<h3 class="li_bor">--%>
				<%--种植类--%>
			<%--</h3>--%>
			<%--<ul class="clearfix">--%>
				<%--<li>豆豉</li>--%>
				<%--<li>大豆</li>--%>
				<%--<li>大米</li>--%>
				<%--<li>大麦</li>--%>
				<%--<li>稻壳</li>--%>
				<%--<li>冻米</li>--%>
				<%--<li>玉米</li>--%>
				<%--<li>稻谷</li>--%>
				<%--<li>黄豆</li>--%>
			<%--</ul>--%>
		<%--</li>--%>
	</menu>
</div>
</body>

</html>

