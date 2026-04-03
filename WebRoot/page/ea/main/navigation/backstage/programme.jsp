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
<title>编程代码管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
<style>
table tr td {
	height: 50px;
}
</style>
</head>  
  <body>
    <br />
    <div align="center" style="padding-top: 15%">
    <table width="90%" cellspacing="0" cellpadding="5" align="center">
    <tr>
    		<td><div class="na_back_img"  ></div>
				<div class="center_a"><strong>编程代码管理</strong></div>
			</td>
			<td><div class="na_back_img" ></div>
				<div  class="center_a"><strong>人事编程代码</strong></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
				<div  class="center_a"><strong>办公室编程代码</strong></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
				<div  class="center_a"><strong>财务编程代码</strong></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
				<div  class="center_a"><strong>生产编程代码</strong></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
				<div  class="center_a"><strong>营销编程代码</strong></div>
			</td>
    </tr>
    </table>
    </div>
  </body>
</html>
