<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
  <head>
    <title>Web图片上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>UploadFile/ImageUploader.js"></script>
  </head>
  
  <body>
    <div id="msg"></div>
	<script  type="text/javascript">
		var imgUploader = new ImageUploader();
		imgUploader.Config["PostUrl"] = <%=basePath%> + "ea/image/ea_ProcessRequest.jspa?";
		imgUploader.Fields["UserID"] = "dsh";

		window.onload = function()
		{
			imgUploader.Init();
		}
	</script>
  </body>
</html>
