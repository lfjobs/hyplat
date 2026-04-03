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
<title>timeout</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    var journalNum ="${journalNum}";

	if (parent.token == 13) {
		parent.re_load(journalNum);
	}

</script>
</head>

<body>
</body>
</html>
