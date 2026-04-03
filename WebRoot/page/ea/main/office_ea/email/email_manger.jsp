<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人邮箱管理</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
<script  src="<%=basePath%>js/ea/office_ea/email/email_manger.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}

-->
</style>
<script type="text/javascript">
  var basePath="<%=basePath%>";
   var treeid;
   var treename;
   var tree;
</script>
</head>
<body>
<form name="csbjectsForm" method="post">
<input name="sub" value="${session_value}" type="hidden"/><!-- 代替token--></form>
<div>
     <div id="aadTree"   style="float:left;overflow-x:hidden;width:15%; height:510px;background-color:#f5f5f5;border :1px solid Silver;overflow:auto;">	
     </div>
     <div style="float:left;;height:508px;border :1px solid Silver;width:84%">
	   <iframe src="" style="width:100%;"  name="ccode" marginwidth="0" height="508px;" marginheight="0" scrolling="auto" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0" > </iframe>
    <div>
</div>
</body>
</html>
