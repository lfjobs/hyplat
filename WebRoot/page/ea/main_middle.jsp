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
	background-image: url(<%=basePath%>images/certer.png);
	background-repeat: no-repeat;
}
</style>
		<script type="text/javascript">
			var basePath="<%=basePath%>"
			function Submit_onclick(){
				if(window.parent.document.getElementById("leftFrame").style.display == "block") {
				     window.parent.document.getElementById("bgMainContent").cols="0,10,*";
					 window.parent.document.getElementById("leftFrame").style.display = "none";
					 //document.getElementById("ImgArrow").src=basePath+"images/plat/main/switch_right.gif";
					 //document.getElementById("ImgArrow").alt="打开左侧导航栏";
				} else {
				     window.parent.document.getElementById("bgMainContent").cols="192,10,*";
					 window.parent.document.getElementById("leftFrame").style.display = "block";
					 //document.getElementById("ImgArrow").src=basePath + "images/plat/main/switch_right.gif";
					 //document.getElementById("ImgArrow").alt="隐藏左侧导航栏";
				}
			}
</script>
<!-- codeBase="page/ea/main/telrec/activeSetup/telRecActiveX.cab#version=2,1,1,0"  -->
		<object  classid="clsid:BE564711-3713-4e3e-9E78-91B039A4969C" width="2" height="2" id="telrec" name="telrec">
		<param name="Userid" value="${account.accountID}" />
			<param name="Companyid" value="${account.companyID}" />
			<param name="Serverpath" value="<%=request.getServerName() + ":" + request.getServerPort()%>"/>
			<param name="Contextpath" value="<%=request.getContextPath()%>"/>
			<param name="username" value="${account.accountName}"/>
		</object>
	</head>
	<body>
		<table width="7" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="7" height="400" style="cursor: pointer;"
					onclick="javascript:Submit_onclick()">
					<img id="ImgArrow" style="margin-left:2px;" src="<%=basePath%>images/ea/admin/middle.gif" />
				</td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>
	</body>
</html>
