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
	<title>商帮圈</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/sbq/sbqindex.css"/>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/sbq/sbq_index.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/sbq/sbq_find.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/sbq/sbq_nearby.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>


	<script type="text/javascript">

        var basePath="<%=basePath%>";

        var pagenumber = 0;
        var  pagenumber1 = 0;
        var  pagenumber2 = 0;
        var ccomIDPlatform = "${param.ccomIDPlatform}";




	</script>
</head>
<body>

<header>
	<h2 class="clearfix head" >
		<div class="active">帮圈</div>
		<div>附近</div>
		<div>发现</div>
	</h2>

</header>
<div class="content">
	<menu class="tjmenu">
		   <%--<li>--%>
			<%--<section>--%>
				<%--<div class="clearfix">--%>
					<%--<img src="<%=basePath%>images/ea/collage/img_01.png"/>--%>
					<%--<h3>爱笑的人</h3>--%>
					<%--<p><span>2018-02-03</span><span>12:30:06</span></p>--%>
				<%--</div>--%>
				<%--<p>--%>
					<%--随意拿了一本书，漫不经心地翻了一下， 里面的文字便像绳子一般把我从万丈深渊拉了起来。--%>
				<%--</p>--%>
			<%--</section>--%>
			<%--<ul class="click_slick clearfix">--%>
				<%--<li>--%>
					<%--<a href="<%=basePath%>images/ea/collage/img_04.png">--%>
						<%--<img src="<%=basePath%>images/ea/collage/img_04.png"/>--%>
					<%--</a>--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--<a href="<%=basePath%>images/ea/collage/img_03.png">--%>
						<%--<img src="<%=basePath%>images/ea/collage/img_03.png"/>--%>
					<%--</a>--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--<a href="<%=basePath%>images/ea/collage/img_03.png">--%>
						<%--<img src="<%=basePath%>images/ea/collage/img_03.png"/>--%>
					<%--</a>--%>
				<%--</li>--%>

			<%--</ul>--%>
			<%--<div>--%>
				<%--<a class="fabulous">--%>
					<%--<img src="<%=basePath%>images/ea/collage/fabulous.png"/>--%>
					<%--<span>8w</span>--%>
				<%--</a>--%>
				<%--<a>--%>
					<%--<img src="<%=basePath%>images/ea/collage/comment.png"/>--%>
					<%--<span>8w</span>--%>
				<%--</a>--%>
			<%--</div>--%>
		<%--</li>--%>
	</menu>
</div>
<div class="content" style="display: none;">
	<menu class="fjmenu">
		<%--<li class="clearfix">--%>
			<%--<span>2km</span>--%>
			<%--<img src="<%=basePath%>images/ea/driving/elkc/head.png"/>--%>
			<%--<h3>叶梦</h3>--%>
			<%--<p><img src="<%=basePath%>images/ea/collage/male.png"/>18</p>--%>
			<%--<p class="txt">这家伙很懒，什么都没有留下。</p>--%>
		<%--</li>--%>

	</menu>
</div>
<div class="content" style="display: none;">
	<menu class="fxmenu">
		<%--<li class="clearfix">--%>
			<%--<img src="<%=basePath%>images/ea/driving/elkc/head.png"/>--%>
			<%--<p class="txt">优胜中小教育行业金莲花胶...</p>--%>
			<%--<span>关注</span>--%>
		<%--</li>--%>

	</menu>
</div>
<div class="dynamic">
	<a href="javascript:;">
		<img src="<%=basePath%>images/ea/collage/dynamic.png"/>
	</a>
</div>
</body>
</html>
