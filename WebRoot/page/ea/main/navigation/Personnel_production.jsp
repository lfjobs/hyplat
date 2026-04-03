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
<title>人事生产管理</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
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
    <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/human/company_sc.jsp'"></div>
				<div class="center_a"><strong>公司人事管理</strong></div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/human/group_sc.jsp'"></div>
				<div  class="center_a"><strong>集团人事管理</strong></div>
			</td>
			<td>
				<div  class="center_a"><strong></strong></div>
			</td>
			<td>
				<div  class="center_a"><strong></strong></div>
			</td>
			<td>
				<div  class="center_a"><strong></strong></div>
			</td>
			
    </tr>
    </table>
    </div>
  </body>
</html>
