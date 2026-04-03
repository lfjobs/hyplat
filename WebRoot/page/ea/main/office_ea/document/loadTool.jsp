<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>下载中心</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
   <style type="text/css">
   u{
    color:red;
   }
   </style>
	</head>

	<body>
	       下载中心
		<table>
			<tr>
				<td style="font-size:14px;color:blue;">
					一、为保障您顺畅使用数字签名进行盖章，请下载安装
					<a href="<%=basePath%>/js/cabs/ttsw2012525.zip"><u>根证书</u>
					</a>
				</td>
			</tr>
			<tr>
				<td style="font-size:14px;color:blue;">
					二、为了使用UKey，请下载安装
					<a href="<%=basePath%>/js/cabs/HaiTaiUSBKey.zip"><u>HaiTaiUkey驱动</u>
					</a>
				</td>
			</tr>
			<tr>
				<td style="font-size:14px;color:blue;">
					三、为了使用电子印章系统盖章，请下载安装
					<a href="<%=basePath%>/js/cabs/ZSSeal.zip"><u>ZSSeal客户端</u>
					</a>
				</td>
			</tr>
		</table>




	</body>
</html>
