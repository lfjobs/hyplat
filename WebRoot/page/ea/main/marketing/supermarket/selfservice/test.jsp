<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
	<title>微分金自助收银机</title>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/keyboard2.css">
	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>


	<script type="text/javascript">
        var basePath="<%=basePath%>";

        function test(obj) {
            VirtualKeyboard.toggle('ipt', 'softkey');
            $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
            $("#softkey").attr("style","position:absolute;z-index:99;top:"+top+"px;left:"+left+"px;");
        }
	</script>
</head>
<body>

<input type="text"  class="barcode"  value="" placeholder="扫描或手动输入条码" id="ipt" onclick='test(this);' >
<div id="softkey"></div>
</body>
</html>
