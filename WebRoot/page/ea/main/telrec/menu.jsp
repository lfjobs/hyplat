<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String type = request.getParameter("type");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="<%=basePath%>page/ea/main/telrec/css/style.css"
			rel="stylesheet" type="text/css">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript">
			var basePath="<%=basePath%>";
		</script>
		<script src="<%=basePath%>page/ea/main/telrec/js/menu.js"
			type="text/javascript"></script>
	</head>
	<body>
		<table>
			<tr>			
				<td>
					<div class="left1 right1" id="infoDealp">
						手机信息处理中心&nbsp;
					</div>
				</td>
				<td>
					<div class="left1 right1" id="infoDealz">
						座机信息处理中心&nbsp;
					</div>
				</td>
				<td>
					<div class="left1" id="wavshow">
						电话接待语音
					</div>
				</td>
				<td>
					<div class="left1" id="wavshow1">
						电话打出语音
					</div>
				</td>
				<td>
					<div class="left1" id="Company">
						接待单位记录
					</div>
				</td>
				<td>
					<div class="left1" id="Personal">
						接待个人记录
					</div>
				</td>
				<td>
					<div class="left1" id="returnVisited">
						电话打出记录
					</div>
				</td>
				<%--<td>
					<div class="left1" id="tongji">
						电话访问统计
						<div>
				</td>
				
				<td>
					<div class="left1" id="download">
						<a target="_blank"
							href="<%=basePath%>page/ea/main/telrec/activeSetup/telRecActiveX.rar">工具下载</a>
					</div>
				</td>--%>
			</tr>
		</table>
		<script type="text/javascript">
		 
		 var type = '<%=type%>';
    	</script>

	</body>
</html>
