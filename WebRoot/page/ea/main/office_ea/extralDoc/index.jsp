<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>投诉首页</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />

		<script type="text/javascript">
   
             var basePath='<%=basePath%>'; 

          
       </script>
		<style type="text/css">

li {
	list-style-type: none;
	display: inline;
}

a:hover {
	text-decoration: none;
}
</style>

	</head>
	<body>
		<div style="padding-left: 20px;">

			<p>
				您共有：
				<font style="font-size:14px;color:red;"><span id="totals"></span></font>个待处理投诉
			</p>

			<ul>
				<li>
					<a href="#" onclick="parent.tonclick('draft');">投诉处理箱(<span
						id="results1">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('approvalno');">待审批投诉(<span
						id="results2">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('stampno');">待盖章投诉(<span
						id="results3">0</span>)</a>
				</li>
			</ul>


		</div>

	</body>

</html>
