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
		<title>办公室-后勤管理-个人接待管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	
	<br/>
	<table >
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>个人接待管理</strong></div></td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/contactuser/ea_getListContactUser.jspa?'"></div><div class="center_a">个人接待信息管理</div></td>
        <td><div class="na_back_img" onclick=""></div><div class="center_a">个人接待信息报表</div></td>
      </tr>
    </table>
	</body>
</html>
