<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    alert("保存成功");
    var proId = "${procedure.proId}";
	window.parent.document.getElementById("proIds").value=proId;
	
</script>
</head>

<body>
</body>
</html>
