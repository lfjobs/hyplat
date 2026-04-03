<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文流转查看页</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
body{
width:70%;
font-style:normal;
font-size:12px;
font-family:微软雅黑;
background-color: #519DB4;


}
span{
color:red;
}
p{
color:white;
}
</style>
<%-- <script type="text/javascript">
$(document).ready(function(){
if($.browser.msie) {
alert("this is msie");
}
if($.browser.safari)
{
alert("this is safari!");
}
if($.browser.mozilla)
{
alert("this is mozilla!");
}
if($.browser.opera) {
alert("this is opera");
}
})
</script> --%>
</head>

<body style="margin-top: 63px;">
<input type="file" name="xx"  value="浏览"/>
<div><img src="<%=basePath%>images/ea/office/document/notice.gif" width="46" height="29" hspace="156" vspace="12" /></div>
<div style="padding-left: 10%; font-size: 14px;">
			<p>
				<span >2013年5月8日功能更新</span>
			</p>
			
			<p>
				1、新建页面收件人再点击至领导审批或传阅草稿时提示选择，在页面中不再作选择。
			</p>
			<p>
				2、左边的导航树做了样式更改，单击树即可。
			</p>
			<p>
				3、已发送增加拟稿人的相关信息。
			</p>
			<p>
				4、拟稿人信息改为申报人信息。审批人信息改为签发(审批)人信息。
			</p>
			<p>
				5、公文中的文件缓急以及主题在新建页面中不用选择，改在附件中选择并且同步到新建页面上。
			</p>


</div>
</body>
</html>
