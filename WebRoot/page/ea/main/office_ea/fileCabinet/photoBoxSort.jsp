<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>图片库排序</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/style.css" type="text/css"
			charset="utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/photomanage/jquery_dialog.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/fileCabinet/photoBoxSort.js"></script>

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
         
       </script>


		<style type="text/css">
a {
	text-decoration: none;
}
</style>

		<style>
body {
	font-family: Arial;
}
a {
	text-decoration: none;
	color: #946652;
}
ul#navigation {
	right: 440px;
	margin-bottom: 50px;
}



.sep {
	background: url(<%=basePath%>images/admin_images/act_btn00.gif)
		no-repeat;
	height: 24px;
	width: 70px;
	cursor: pointer;
	border: solid 0px #111;
	font-size: 10px;
}

.mousedown {
	cursor: crosshair;
}
</style>


	</head>
	<body>

			<center><div style="margin-top: 10px;"><a href="javascript:submitsort2('sortAsc')">时间升序</a> | <a href="javascript:submitsort2('sortDesc')">时间降序</a> | <a href="javascript:submitsort2('sortName')">按名称</a></div></center> <div style="float:right;margin-right:380px;"> <img src="<%=basePath%>images/ea/office/fileCabinet/save.png"style="cursor:pointer;" onclick="saveSort();"/><a href="javascript:backPicBox();">返回图片库</a></div>
					
					<div id="aaa"
						style="width: 800px; height: 350px; margin-top: 40px;">

						<%
							int number = 1;
						%>
						<s:iterator id="docList" value="listbox">
							<div id="aaa_${key}" class="aaa";
								style="float: left; width: 126px; height: 180px; padding-left: 5px; overflow: hidden;">
								<table style="margin: 0; padding: 0; border: none;">
									<tr>
										<td  width="116"
											height="123" align="center" valign="middle"
											style="background-image: url(<%=basePath%>images/ea/office/fileCabinet/photoFrame.gif); cursor:move;">
											<img src="<%=basePath%>${coverUrl}" name="imgface" />

										</td>
									</tr>
									<tr>
										<td align="center">
											<span title="${photoBoxName}">${PbnShort}</span>
											<input type="hidden" id="hidobn" value="" />
											
										</td>
									</tr>
								</table>
								<%
									number++;
								%>
							</div>
						</s:iterator>

					</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>

</html>
