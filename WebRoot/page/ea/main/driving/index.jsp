<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>驾校管理首页</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/doc.css" />

		<script type="text/javascript">
   
             var basePath='<%=basePath%>'; 

          
       </script>

	</head>
	<body>
		<div id="index">

			<!-- <a href="#" style="font-size:15px;" onclick="parent.newBuildDoc();">新建草稿</a> -->

		</div>
		<div style="padding-left: 20px;">

			<p>
				<%-- 您共有：
				<font style="font-size: 14px; color: red;"><span id="totals"></span>
				</font>个待处理公文 --%>
			</p>

		<!-- <ul>
				<li>
					<a href="#" onclick="parent.tonclick('draft');">草稿箱(<span
						id="results1">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('receivebox');">收件箱(<span
						id="results2">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('approvalno');">未审批(<span
						id="results3">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('stampno');">未盖章(<span
						id="results4">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('nosenddoc');">未分发(<span
						id="results5">0</span>)</a>
				</li>
				<li>
					&nbsp;
				</li>
				<li>
					<a href="#" onclick="parent.tonclick('receivemanage');">未阅读(<span
						id="results6">0</span>)</a>
				</li>
			</ul>

           -->	
		</div>
		<div style="padding-left: 30%; padding-bottom: 10px;">
			<img src="<%=basePath%>images/ea/office/document/notice.gif"
				width="40" height="30" />
			公告信息
		</div>
	<!--  <div style="padding-left: 10%; color: red;">
			2013年2月21日功能更新

		</div>
		<div style="padding-left: 10%; font-size: 14px;">
			<p>
				1、新建页面更改,选择收件人的时候在右侧，模板也在右侧。
			</p>
			<p>
				2、增加功能：可以选择模板，也可以不选择模板，只选择文件格式。
			</p>
			<p>
				3、在已发送列表中增加转发功能
			</p>
			<p>
				4、传阅草稿和驳回的公文在收件箱中展示。
			</p>
			<p>
				5、已传阅记录均在已发送中展示。
			</p>
			<p>
				6、在传至信息平台功能中增加临时添加收件人的功能；
			</p>

		</div>
		<div style="padding-left: 10%; color: red;">
			2013年3月11日功能更新

		</div>
		<div style="padding-left: 10%; font-size: 14px;">
			<p>
				1、新建页面更改：公文模板，以及收件人按最初方式展示。
			</p>
			<p>
				2、增加功能：收件箱中传阅草稿更改为多选。
			</p>
			<p>
				3、增加公文跟踪记录。
			</p>
					<p>
				4、传阅草稿和驳回的公文在收件箱中展示。
			</p>


		</div>-->	
		<!-- <div style="padding-left: 10%; color: red;">
			2014年9月功能更新

		</div>
		<div style="padding-left: 10%; font-size: 14px;">
			<p>
				1、图标连接导航改为树形结构导航
			</p>
			<p>
				2、导航菜单命名变更
			</p>
			<p>
				3、变更模块：培训跟踪，科目信息-教学项目设置，登记表打印
			</p>

		</div> -->

	</body>

</html>
