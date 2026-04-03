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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/extralDoc/settings.js"></script>

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

		<!--设置 -->
		<form id="setForm" name="rejectForm">
			<div style="text-align: left; width: 400px; right: 25%; top: 10%"
				id="jqModelSet">
				<input type="submit" name="submit" style="display: none" />
				<div style="margin-top:30px;margin-left:15px;">
					<font style="font-size:15px;color:#15428b">责任人设置</font>

				</div>
			</div>

			<table cellspacing="10" id="settable">
				<tr>
					<td align="right">
						当前投诉责任人：

					</td>
					<td align="left">
						<input type="text" id="dealerName" readonly size="10" />
						<input type="hidden" id="dealerID" value="" />
						&nbsp;
						<b><a href="#"
							onclick="importGY('ea/extralflow/ea_getStaffformalList.jspa')">权限移交</a>
						</b>


					</td>

				</tr>

				<tr>
					<td>
						&nbsp;
					</td>
					<td align="center">
						<input type="checkbox" value="" checked id="trasData"/>
						同时移交数据

					</td>

				</tr>
				<tr>

					<td colspan="2" align="center">
						<input type="button" value="确定"  id="totransfer"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="socialJqm" class="jqmWindow"
			style="width: 60%; height: 250px; absolute; display: none; left: 20%; top: 10%; z-index: 9999; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 30px;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 20px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

	</body>

</html>
