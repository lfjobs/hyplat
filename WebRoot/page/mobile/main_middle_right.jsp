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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" href="<%=basePath%>css/ea/admin.css"
			type="text/css" />
		<title>显示/隐藏左侧导航栏</title>
		<style type="text/css">
body {
	background-image: url(../../images/ea/admin/bg_03.gif);
	background-repeat: repeat-x;
}
</style>
		<script type="text/javascript">
			function Submit_onclick_right(){
				if(parent.document.getElementById("officeFrame").cols == "179,6,*" ) {
					parent.document.getElementById("officeFrame").cols  = "0,6,*";
					
				} 
				
				else {
				  	parent.document.getElementById("officeFrame").cols = "179,6,*";
				}
				
			}
</script>
	</head>
	<body>
		<table width="7" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="7" height="400" style="cursor: pointer;"
					onclick="javascript:Submit_onclick_right()">
					<img id="ImgArrow" src="<%=basePath%>images/ea/admin/middle.gif" />
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</body>
</html>
