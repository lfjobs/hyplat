<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>图片列表</title>
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
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/style.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery-1.3.1.min.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.easing.1.3.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/photoListSort.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.core.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.widget.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.mouse.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.sortable.js"></script>
		<script type="text/javascript">
         var  basePath='<%=basePath%>';   
         var photoBoxId = '<%=request.getAttribute("photoBoxID")%>';     
       </script>


		<style type="text/css">
.acss a:link {
	text-decoration: none;
}
</style>

		<style>
body {
	font-family: Arial;
}

#picdiv img {
	border: none;
}

a {
	text-decoration: none;
	color: #946652;
}

#manager ul li {
	list-style-type: none;
	display: inline;
	color: #946652;
}

.stylediv {
	padding-left: 5px;
	padding-right: 5px;
}

#picboxdiv {
	overflow: scroll;
	width: 120px;
	height: 136px;
}

#full {
	overflow: hidden;
	display: none;
	border: 2px solid #CCCCCC;
	position: absolute;
	width: 120px;
	height: 150px;
	top: 50px;
	left: 175px;
	z-index: 99;
	background-color: #FFFFFF;
}

.mouseover {
	background-color: #FFD306;
}
</style>

	</head>
	<body>
		<center>
			<div style="margin-top: 10px;">
				<a href="javascript:submitsort2('sortAsc')">时间升序</a> |
				<a href="javascript:submitsort2('sortDesc')">时间降序</a> |
				<a href="javascript:submitsort2('sortName')">按名称</a>
			</div>
		</center>
		<br>
		<div style="float: right; margin-right: 450px;">
			<img src="<%=basePath%>images/ea/office/fileCabinet/save.png"
				style="cursor: pointer;" onclick="savePhotoSort();" />
			<a href="javascript:backPicList();">返回图片列表</a>
		</div>
		<div
			style="width: 800px; height: auto; overflow: auto; margin-top: 40px;"
			id="picdiv">

			<%
				int number = 1;
			%>
			<s:iterator id="docList" value="listpic">

				<div id="picdiv_${key}"
					style="width: 129px; height: 160px; float: left; margin-top: 20px; padding-left: 10px; overflow: hidden;">
					<div
						style="border: 1px solid #000000; text-align: center; width: 127px; height: 128px; line-height: 127px; cursor: move;">

						<img src="<%=basePath%>${photoFile}" name="pic" title=""
							width="126" height="124" />

					</div>
					<div style="height: 20px;" id="${photoID}div">
						<span id="${photoID}span" title="${photoName}">${pnShort}</span>
					</div>

				</div>



				<%
					number++;
				%>

			</s:iterator>

		</div>

	</body>

</html>


