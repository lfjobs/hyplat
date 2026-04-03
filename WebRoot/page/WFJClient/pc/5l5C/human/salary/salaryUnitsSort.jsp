<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryUnitsSort.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/5l5C/human/salary/salaryUnitsSort.js" type="text/javascript" charset="utf-8"></script>
	<title>&lrm;</title>
	<script type="text/javascript">

		var basePath = "<%=basePath%>";

	</script>
</head>
<body>
<header>
	<ul class="clearfix">
		<li >
			<a onclick="javascript: window.history.go(-1);return false;"  target="_self">

				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
			</a>
		</li>
		<li>
			薪级结构排序
		</li>
		<li>

		</li>
	</ul>
</header>
<div class="content">

	<section class="sec-list"  id="phone-sec">
		<ul class="ul">

		</ul>
	</section>
</div>



<!--表单提示-->
<div class="div-tingyong">
	<div class="box">
		<p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
		<div class="div-box">
			<p class="titlep"></p>
			<div class="clearfix">
				<p class="left close-tingyong">取消</p>
				<p class="right close-confirm">确定</p>
			</div>
		</div>
	</div>
</div>



</body>

</html>
