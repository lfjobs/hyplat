<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String type = request.getParameter("type");

%>

<html>
	<head>
	</head>
	<frameset border='0' frameSpacing='0' rows="40, *" frameBorder='0'>
		<frame name='menu' src="menu.jsp?type=<%=type%>" frameBorder='0' noResize />
		<frame name='main' src="" frameBorder='0' noResize scrolling='yes' />
	</frameset>
</html>
