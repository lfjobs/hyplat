<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-cache" />
        <title>框架页</title>
        <%@ page import="java.net.URLEncoder" %>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
      <script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<link href="<%=basePath%>css/css.css"rel="stylesheet" type="text/css" />
		<style type="text/css">
		<!--
		body {
			background-image: url(<%=basePath%>/images/r_2_06.gif);
			background-repeat: repeat-x;
		}
		-->
		</style>
		<script>
		 /*
		 	parent.document.getElementById("leftFrame").style.display = "none";
		 */
		 </script>
		</head>

		<frameset cols="186,*" id="officeFrame" frameborder="no" border="0" framespacing="0">
			<frame src="<%=basePath%>/ea/office/ea_getCompanyMessage.jspa?result=result" name="left_tree" frameborder="no" scrolling="no" noresize="noresize" id="left_tree" title="left_tree" />
			<frameset rows="320px;" frameborder="no" border="0" framespacing="0">
				<frame src="" name="daohang" id="daohang" title="mainFrame_body" scrolling="no" noresize="noresize"/>
			</frameset>
		</frameset>
		<noframes><body>
			</body>
		</noframes></html>
