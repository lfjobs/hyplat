
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>视易加信息管理</title>
<script src="<%=basePath%>shiyijia/js/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>shiyijia/css/tinyTips.css" />
<script type="text/javascript" src="<%=basePath%>shiyijia/js/tinyTips.js"></script>

</head>
<style type="text/css">
<!--
#Layer11 {
	position:absolute;
	width:481px;
	height:486px;
	z-index:1;
	left: 503px;
	top: 114px;
}
#Layer11 img{
cursor: pointer;
}

body{
font-family: '微软雅黑'; 
}
#Layer1 {
	position:absolute;
	width:120px;
	height:120px;
	z-index:1;
	margin-left: 50%;
	margin-top: 26px;
	left: -62px;
	top: 2px;
}
#Layer2 {
	position:absolute;
	width:120;
	height:120;
	z-index:2;
	margin-left: 458px;
	margin-top: 110px;
}
#Layer3 {
	position:absolute;
	width:120;
	height:120;
	z-index:3;
	margin-left: 770px;
	margin-top: 106px;
	left: -743px;
	top: 150px;
}
#Layer4 {
	position:absolute;
	width:120;
	height:120;
	z-index:4;
	margin-left: 770px;
	margin-top: 263px;
	left: -440px;
	top: -159px;
}
#Layer5 {
	position:absolute;
	width:120;
	height:120;
	z-index:5;
	margin-left: 460px;
	margin-top: 269px;
	left: -434px;
	top: -165px;
}
#Layer6 {
	position:absolute;
	width:120;
	height:120;
	z-index:6;
	margin-left: 50%;
	margin-top: 352px;
	left: -62px;
	top: -28px;
}
#Layer7 {
	position:absolute;
	width:120;
	height:120;
	z-index:7;
	margin-left: 50%;
	margin-top: 189px;
	left: -61px;
	top: -13px;
}
#Layer8 {
	position:absolute;
	width:108px;
	height:108px;
	z-index:8;
	left: 328px;
	top: 254px;
}
#Layer12 {
	position:absolute;
	width:260px;
	height:530px;
	z-index:8;
	left: 101px;
	top: 75px;
}
</style>
<script type="text/javascript">
		 $(document).ready(function() {
			$('div.tTip').tinyTips('green', '<img src="<%=basePath%>shiyijia/images/yanguangshi.png" /><br />查询本门店所有验光师资料信息.');
			$('div.end').tinyTips('green', 'title');
				
		});
		</script>
</head>

<body>
<br /><br />
<div style="margin-left:610px;position:absolute;color:#034b86"><h1 align="center">视易加系统平台管理</h1></div>
<div id="Layer11">

<div id="Layer1"  onclick="document.location.href='<%=basePath%>/ea/studentsInformation/ea_getListStudent.jspa'"    onmouseover="document.getElementById('02').src='<%=basePath%>shiyijiaImages/xs4.png'" onmouseout="document.getElementById('02').src='<%=basePath%>shiyijiaImages/xs.png'"><img src="<%=basePath%>shiyijiaImages/xs.png" width="120" height="120" id="02"/></div>
<div id="Layer3" onmouseover="document.getElementById('03').src='<%=basePath%>shiyijiaImages/jz7.png'" onmouseout="document.getElementById('03').src='<%=basePath%>shiyijiaImages/jz.png'"><img src="<%=basePath%>shiyijiaImages/jz.png" width="120" height="120" id="03"/></div>
<div id="Layer4" class = "end" title="查询检测所有信息结果"  onmouseover="document.getElementById('04').src='<%=basePath%>shiyijiaImages/jc6.png'" onmouseout="document.getElementById('04').src='<%=basePath%>shiyijiaImages/jc.png'"><img src="<%=basePath%>shiyijiaImages/jc.png" width="120" height="120" id="04"/></div>
<div id="Layer5" class="tTip"  onmouseover="document.getElementById('01').src='<%=basePath%>shiyijiaImages/yg1.png'" onmouseout="document.getElementById('01').src='<%=basePath%>shiyijiaImages/yg.png'"><img src="<%=basePath%>shiyijiaImages/yg.png"   width="120" height="120" id="01"/></div>
<div id="Layer6" onmouseover="document.getElementById('06').src='<%=basePath%>shiyijiaImages/xf3.png'" onmouseout="document.getElementById('06').src='<%=basePath%>shiyijiaImages/xf.png'"><img src="<%=basePath%>shiyijiaImages/xf.png" width="120" height="120" id="06"/></div>
<div id="Layer7" onmouseover="document.getElementById('07').src='<%=basePath%>shiyijiaImages/zd2.png'" onmouseout="document.getElementById('07').src='<%=basePath%>shiyijiaImages/zd.png'"><img src="<%=basePath%>shiyijiaImages/zd.png" width="120" height="120" id="07"/></div>
<div id="Layer8" onmouseover="document.getElementById('08').src='<%=basePath%>shiyijiaImages/hj5.png'" onmouseout="document.getElementById('08').src='<%=basePath%>shiyijiaImages/hj.png'"><img src="<%=basePath%>shiyijiaImages/hj.png" width="120" height="120" id="08"/></div>
</div>
</div>
<!-- 流程图 -->
<div id="Layer12" >
	<img src ="<%=basePath%>shiyijiaImages/liuchengtu.jpg"/>
</div>

<div style="text-align:center;clear:both">
</div>

</body>
</html>
