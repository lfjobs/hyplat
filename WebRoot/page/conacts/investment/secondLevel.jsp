<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" /> 
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/contacts/investment/investment.css" type="text/css"></link>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.js"></script>


<title>天太世统科技有限公司</title>
<style type="text/css">
.nav-bar{
    font-size: 18px;
    line-height: 40px;
    background: #fff;
    color: #000;
    border-bottom:1px solid #e3e3e3;
}
.nav-bar a{
	color:#000;}


</style>
</head>
<body>
<p class="nav-bar text-center navbar-fixed-top">
		<a class="pull-left nav_left" >
			<img style="width:15px;" src="<%=basePath%>images/contacts/comments/wfj_return_02.png" onclick="fanhui()">
		</a>
		<a>中联园区招商</a>
		<a class="pull-right nav_right"></a>
	</p>
	
	<div class="fix_top"></div>
	<div class="container">
		<div class="row">
			<div class="list-group">
			     <input type="hidden" id="pageNumber" value="${pageForm.pageNumber}"/>
                 <input type="hidden" id="pageCount" value="${pageForm.pageCount}"/>
				<c:forEach items="${pageForm.list}" var="d">
				
					<a href="#" class="list-group-item xuan" onclick="Jump(this)"> <img
						src="<%=basePath%>${d.iconPath}" class="nav_img pull-left" />
						<input type="hidden" id="codepid" name="codepid" value="${d.codeID}">
						<p class="list-group-item-text pull-left">
							<span  id="codename"> ${d.codeValue}</span> 
								
						</p> </a>
				</c:forEach>
			</div>
			 
	</div>
</body>

<script>
	var basePath = "<%=basePath%>";
	var codesId = "${codesId}";
</script>
<script type="text/javascript" src="<%=basePath%>js/restaurant/investment/secondLevel.js"></script>

</body>
</html>