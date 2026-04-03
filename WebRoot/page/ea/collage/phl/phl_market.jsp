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
	<title>批发市场</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_market.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_market.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>



	<script type="text/javascript">

        var basePath="<%=basePath%>";

        var pagenumber = 0;

	</script>
</head>
<body>
<div class="content">
	<section>
		<h3 class="clearfix">
			批发市场
		</h3>
		<ul class="mkul">
			<%--<li class="clearfix">--%>
				<%--<img src="images/pic_01.png"/>--%>
				<%--<p>火猫货科技有限公司</p>--%>
				<%--<p>--%>
					<%--<img src="images/location.png"/>--%>
					<%--<span class="txt">北京市海淀区苏州街北一街17号</span>--%>
					<%--<span>800m</span>--%>
				<%--</p>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<img src="images/pic_02.png"/>--%>
				<%--<p>凯尔拉科技有限公司</p>--%>
				<%--<p>--%>
					<%--<img src="images/location.png"/>--%>
					<%--<span class="txt">北京市海淀区苏州街北一街17号</span>--%>
					<%--<span>800m</span>--%>
				<%--</p>--%>
			<%--</li>--%>
		</ul>
	</section>
</div>
</body>
</html>

