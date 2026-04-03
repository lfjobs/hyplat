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
<title>代码元素管理</title>
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
    		<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20150501kze3xkwxgv0000000006'"></div>
				<div class="center_a"><strong>代码元素管理</strong></div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000005'"></div>
				<div  class="center_a"><strong>人事代码元素</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000006'"></div>
				<div  class="center_a"><strong>办公室代码元素</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000026'"></div>
				<div  class="center_a"><strong>财务代码元素</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000027'"></div>
				<div  class="center_a"><strong>生产代码元素</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000028'"></div>
				<div  class="center_a"><strong>营销代码元素</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="location.href='<%=basePath%>/ea/ccode1/ea_pm_selectProjects.jspa'"></div>
				<div  class="center_a"><strong>接口管理代码元素</strong></div>
			</td>

    </tr>
    </table>
    </div>
  </body>
</html>
