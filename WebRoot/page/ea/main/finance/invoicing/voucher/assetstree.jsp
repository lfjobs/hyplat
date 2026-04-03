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
<title>资产负债内容设定</title>
 <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />

<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/ea/finance/invoicing/voucher/assetstree.js"></script>

<script type="text/javascript">
   
  var basePath='<%=basePath%>';

</script>
<style type="text/css">
/* #addTree{
  border:1px solid red;
} */
td.tree{
 vertical-align:top;
 border:1px solid #DAE7F6;
 width:18%;

}
</style>
</head>
<body>
	<div class="main_main">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tbl">
			<tr>
				<td class="tree">
					<div id="addTree" style="overflow:auto;width:250px;height:672px;"></div></td>
				<td style="width: 82%;" valign="top"><iframe id="mainframe"
						frameborder="0" style="width: 100%;"></iframe></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			
			
			$("#mainframe").css({
				"height" : $(window).height()-2 + "px"
			});
		});
	</script>
</body>


</html>
