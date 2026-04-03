<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>售前服务管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">
</style>		
	</head>
<body>
<table width="450" border="0">
  <tr>
    <td>
    	<div class="na_back_img_ks"></div>
          <div class="center_a"><strong>媒体宣传办</strong></div>
  </td>
	<td><div class="na_back_img_jt_hx"></div></td>
    <td>
    	<div class="na_back_img" ></div>
<div class="center_a">电视宣传组</div>
	</td>
    <td>
    	<div class="na_back_img" ></div>
<div class="center_a">报纸宣传组</div>
</td>
  </tr>
</table>
</body>
</html>