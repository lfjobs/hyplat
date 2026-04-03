<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<title>单据审核状态树</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	</head>
	<script type="text/javascript">
 var basePath="<%=basePath%>";
 var parentID;
 var treeid = null;
 var treename;
 var parentid;
 var parentname;
 var tree;
 var date;
</script>
	<script
		src="<%=basePath%>js/ea/mysl/administrative/ReceiptCheck-left.js"></script>
	<body>
		<form name="scheduledproductform" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td id="qh_sw" style="width: 10%; margin-left: 5px; height: 611px;" valign="top">
					<div class="qh_gg_nav" style='width:100%'>&nbsp;审核管理</div>
						<div style="position: absolute; width: 130px;  height: 425px">
							<div id="aadTree" style="width: 200px; z-index=-1; display: yes; border: 0px solid #000000; top: 32px;">
								
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>