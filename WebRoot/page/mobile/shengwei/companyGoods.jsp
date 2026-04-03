<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>企业商品</title>
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<style>
		html {
			overflow-x: hidden;
		}
		
		img {
			vertical-align: top;
			border: 0;
		}
		
		div,h1 {
			word-break: break-all;
		}
		
		div,h1,p,ul,li,label,textarea,input,button,form {
			margin: 0;
			padding: 0;
		}
		
		input,textarea {
			border: 0;
		}
		
		a {
			text-decoration: none;
			color: #000;
		}
		
		ul {
			list-style: none;
		}
		
		.fl {
			float: left;
		}
		
		.fr {
			float: right;
		}
		
		.clear {
			clear: both;
			height: 0;
			font-size: 0;
			line-height: 0;
			overflow: hidden;
		}
		
		body {
			background-color: #e6e6e6;
			font-family: Helvetica, STHeiti STXihei, Microsoft JhengHei,
				Microsoft YaHei, Tohoma, Arial;
			font-size: 12px;
			line-height: 20px;
			color: #000000;
			margin: 15px 12px;
			min-width: 296px;
			-webkit-text-size-adjust: none;
		}
		
		#topbar {
			display: none;
			position: fixed;
			left: 0;
			top: 0;
			width: 100%;
			height: 46px;
			background-color: #43abe1;
			border-bottom: 2px solid #2da9bf;
			z-index: 500;
		}
		
		.topbar_title {
			font-size: 20px;
			color: #ffffff;
			line-height: 20px;
			text-align: center;
			text-overflow: ellipsis;
			-o-text-overflow: ellipsis;
			white-space: nowrap;
			overflow: hidden;
			margin: 0 auto;
			width: 200px;
			padding: 13px 0;
		}
		
		.box {
			width: 100%;
		}
		
		.single {
			width: 45%;
			background: #fff;
			margin-left: 3%;
			margin-top: 15px;
		}
		
		.double {
			width: 45%;
			background: #fff;
			margin-right: 3%;
			margin-top: 15px;
		}
		
		.box img {
			float: left;
			width: 100%;
		}
		
		.box p {
			line-height: 25px;
			color: #000;
			padding-left: 10px;
			padding-right: 10px;
			font-weight: bold;
		}
		
		.box p label {
			margin-right: 10px;
		}
		
		.box p .red {
			color: red;
		}
		
		.box p .blu {
			color: #73bce7;
		}
		
		.box p .txt_ {
			text-decoration: underline;
		}
		
		.box p span {
			color: #636363;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		var oImg=$(".box img");
		for(var i=0;i<oImg.length;i++){
			oImg[i].style.height=(oImg[0].width)+"px";
		}	
		});
	</script>
	
	</head>
	<body style="margin-top: 50px;">
		<div id="topbar" style="display: block;">
			<div id="topbar_title" class="topbar_title">
				${courseList[0][5]}
			</div>
		</div>
		<div class="box fl">
		
		<% int number = 2; %>
		
		<s:iterator value="courseList" id="arr">
		
			<div class=<% if(number%2==0){ %> "single fl" <% }else{ %> "double fr" <% } %>>
				 <c:choose>
				    <c:when test="${arr[7]=='培训店'}">
				       <a href="<%=basePath%>/ea/shengwei/ListCourse.jspa?weidianType=${arr[7]}&courseId=${arr[0]}&companyid=${arr[8]}">
				 		 <img alt="" src="<%=basePath %>${arr[6]}" />
				 	   </a>
				    </c:when>
				    <c:otherwise>  
				      <img alt="" src="<%=basePath %>${arr[6]}" />
				    </c:otherwise>
				</c:choose>
				 
				<p>
					<label>
						${arr[3]}
					</label>	
					<label  class="red">
						￥${arr[4]}
					</label>					
				</p>
				<p>
					<label class="blu">
						${arr[1] }
					</label>
				</p>
				<p>
					<span class="txt_">${arr[5] }</span>
				</p> 
			</div>
			<% if(number%2==1){ %>
			<div class="clear"></div>
			<% } %>
			<% number++; %>
			</s:iterator>
		</div>
	</body>
</html>
