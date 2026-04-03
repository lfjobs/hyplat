<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>5L5C公文流转主页</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
</head>
<frameset rows="45,*" cols="*" frameborder="no" border="0" framespacing="0" id="updsfdsfsdf">
  <frame src="documenttopBar.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame">
  <frameset cols="90,*" frameborder="no" border="0" framespacing="0" id="fvfvvg">
     <frame src="documentnavigatorpage.jsp" name="leftFrame" scrolling="No" style="overflow-y:scroll"  id="leftFrame" />
     <frame src="documentbody.jsp" name="mainFrame" id="mainFrame" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>
