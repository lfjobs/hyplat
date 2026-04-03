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
<title>安全类别维护</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/safe/safekinds_manager.js"></script>
<script type="text/javascript">
   var basePath= '<%=basePath%>';
   var pNumber='${pageNumber}';
   var treeid;
   var treename;
   var tree;
   var date;
</script>
</head>
<body>
<form name="codeForm" method="post">
<input name="sub" value="${session_value}" type="hidden"/>
<!-- 代替token--></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;" valign="top">    
    <div class="qh_gg_nav">&nbsp;安全检查类别维护</div>
   <div id="aadTree" style="border: 0px solid #000000;"></div> 
    </td>
    <td style="width: 85%;" valign="top">
       <iframe src="" name="ccode" width="100%" marginwidth="0" height="450px;" marginheight="0" scrolling="no" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
    </td>
  </tr>
</table>
</body>
</html>
