<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css" rel="stylesheet"
	type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>

<title>教育背景</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
		var resumeID = "${resumeID}";//每个人的id
		var jitype="${param.jitype}";
		var sccId="${param.sccId}";
		var back = "${param.back}";
</script>
</head>

<body class="body">
	<div class="res_top">
		<input type="hidden" id="staffid" value="${staffid}">
		<input type="hidden" id="type" value="${type}">
		<input type="hidden" id="resumeID" value="${param.resumeID}">
		<ul>
			<li><a><img src="<%=basePath%>images/resume/left_jt.png"
					id="returnClick">
			</a>
			</li>
			<li>教育背景</li>
			<li></li>
			<div class="clearfix"></div>
		</ul>
	</div>
	<div class="res_bot edu-bg">
		<div class="edu-bg_mil" align="center"
			style="background-color: #f3f3f3;">
			<!--添加教育背景  -->
			<a id="addEducationalBackg"><button>+添加教育背景</button>
			</a>
		</div>
		<c:forEach items="${list}" var="list">
			<div class="edu-bg_mil" onclick="xiugai(this)">
				<%-- ${fn:substring(list.admissionTime,0,10)} --%>
				<p>${fn:substring(list.admissionTime,0,10)}--${fn:substring(list.graduationTime,0,10)}</p>
				<h5>
					${list.name}<img onclick="dianji(this)"
						src="<%=basePath%>images/resume/shan.png" alt="">
				</h5>
				<p>${list.education} | ${list.professionalName}</p>
				<input type="hidden" id="key" value="${list.educationKey}">
				<input type="hidden" id="type" value="${type}">
				<input type="hidden" id="resumeID" value="${list.resumeID}">
				
			</div>
		</c:forEach>
		<div class="alert_div2" style="top: -45px ;">
			<div class="alert_txt">
				<p>确认要删除吗？</p>
				<div>
					<a id="add">
						<button id="qued">确定</button>
					</a>
					<button id="quxiao">取消</button>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
<script src="<%=basePath%>js/ea/production/resume/education.js"
	type="text/javascript"></script>
</body>
</html>