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
<title>基数数据维护</title>
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
    		<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeManage.jspa?basic=scode20141029rnzhjs7ap60000000004'"></div>
				<div class="center_a"><strong>项目物品基础数据</strong></div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeFive.jspa?codeID=scode20141028whpjevz6ge0000000009'"></div>
				<div  class="center_a"><strong>人事基本数据</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeFive.jspa?codeID=scode20141028whpjevz6ge0000000010'"></div>
				<div  class="center_a"><strong>办公室基本数据</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeFive.jspa?codeID=scode20141028whpjevz6ge0000000011'"></div>
				<div  class="center_a"><strong>财务基础数据</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeFive.jspa?codeID=scode20141028whpjevz6ge0000000012'"></div>
				<div  class="center_a"><strong>生产基础数据</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/ccode/ea_ccodeFive.jspa?codeID=scode20141028whpjevz6ge0000000013'"></div>
				<div  class="center_a"><strong>营销基础数据</strong></div>
			</td>
    </tr>
    </table>
    </div>
  </body>
</html>
