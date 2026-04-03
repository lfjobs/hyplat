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
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryUnitslist.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/WFJClient/pc/5l5C/human/salary/salaryUnitslist.js" type="text/javascript" charset="utf-8"></script>
	<title>&lrm;</title>
	<script type="text/javascript">


		var basePath = "<%=basePath%>";
		var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
		var pageCount  = ${pageForm==null?0:pageForm.pageCount};


		var sccId = "${sccId}";


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
			薪资结构设置
		</li>
		<li>

		</li>
	</ul>
</header>
<div class="content">

	<section class="sec-nav sec-hide">
		<!--sec-hide-->
		<ul class="clearfix" >

			<li class="clearfix ">
				<p class="zadd"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/auto.png"/>系统生成</p>

			</li>

			<li class="clearfix">
				<p class="saddy"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加一级单元</p>

			</li>

			<li class="clearfix">
				<p class="sadde"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>添加二级单元</p>
			</li>
			<li class="clearfix">
				<p class="itemadd"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/additem.png"/>添加项目节点</p>
			</li>
			<li class="clearfix">
				<p class="edit"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/edit.png">修改名称</p>
			</li>
			<li class="clearfix">
				<p class="sort"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/sort.png"/>排序</p>
			</li>
			<li class="clearfix">
				<p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
			</li>

		</ul>
	</section>

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

<div class="loading">
	<div class="div-box">
		<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
		<p>正在加载...</p>
	</div>
</div>

</body>


</html>
