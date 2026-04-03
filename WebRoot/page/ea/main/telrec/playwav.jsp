<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
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
		<title>语音播放</title> 
	</head>
	<body>
		<%
			String wavpath = request.getParameter("wavpath");
			File file = new File("wavpath");
			if (wavpath == null || wavpath.equals("") || file.exists()) {
		%>
		无声音文件
		<%
			} else {
		%>
		<object align=middle
			classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" class="OBJECT"
			id="MediaPlayer" width="400" height="400">
			<param NAME="url" value="<%=request.getParameter("wavpath")%> ">
			<param name="InvokeURLs" value="1">
			<param name="enabled" value="-1">			
			<embed type=application/x-oleobject
				codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701">
			</embed>
		</object>
		<%
			}
		%>
	</body>
	
</html>
