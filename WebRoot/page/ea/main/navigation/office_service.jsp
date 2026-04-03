        <%@ page language="java" pageEncoding="UTF-8" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办公室售前服务</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
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
<div>
<table width="108" border="0" cellpadding="0" cellspacing="0" style="margin:0 auto;">
  <tr>
    <td width="108" height="71" align="center"><a href="<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa"><img src="<%=basePath%>images/004.gif" width="50" height="50" border="0" /><br />
   往来单位管理</a></td>
    </tr>
</table>
</div>
</body>
</html>
