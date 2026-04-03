<%@ page language="java" pageEncoding="UTF-8" %>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>代码管理</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size:12px;
}
a:link,a:visited,a:active{
	text-decoration:none;
}
a:hover{
	text-decoration:underline;
}
a {
	color: #333;
	text-decoration:none;
}
a:active{color: #002f76;
font-weight:bold;}
-->
</style>
</head>
<body >
 <div style="width:100%; margin:0 auto; margin-top:30px;">
<table width="395" height="314" border="0" cellpadding="0" cellspacing="0" style="margin:0 auto;">
  <tr>
    <td width="100" height="71" align="right"><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/ea/cccode/ea_ccodeManage.jspa'"><img src="<%=basePath%>images/004.gif" width="50" height="50" border="0" /><br />
    代码管理</a></td>
    <td width="91" align="center"><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/ea/cgoodsmanage/ea_getListGoodsManage.jspa'"><img src="<%=basePath%>images/002.gif" width="50" height="50" border="0" /><br />
   物品管理</a></td>
    <td width="91" align="center"><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/ea/cdepotmanage/ea_getListDepotManage.jspa'"><img src="<%=basePath%>images/002.gif" width="50" height="50" border="0" /><br />
   库房管理</a></td>
  </tr>
</table>
</div>
</body>
</html>
