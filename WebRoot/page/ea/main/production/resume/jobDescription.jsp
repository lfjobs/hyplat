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
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css"
	rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js" type="text/javascript"></script>
    
<title>工作描述</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
    var back = "${param.back}";

</script>
</head>
<body class="body">
    <div class="res_top">
     <input type="hidden" id="resumeID" value="${param.resumeID}">
    <!-- 后台 -->
    <input type="hidden" id="id" value="">
	<!-- 前台 -->
    <input type="hidden" id="staffid" value="${param.staffid}">
    <input type="hidden" id="typedatas" value="${param.typedata}">
    <input type="hidden" id="typedata" value="${param.type}">
    <input type="hidden" id="name" value="${param.name}">
    <input type="hidden" id="admissionTime" value="${param.admissionTime}">
    <input type="hidden" id="graduationTime" value="${param.graduationTime}">
    <input type="hidden" id="position" value="${param.position}">
    <input type="hidden" id="postName" value="${param.postName}">
    
    <input type="hidden" id="recordKeys" value="${param.recordKeys}">
    <input type="hidden" id="recordIDs" value="${param.recordIDs}">
    <input type="hidden" id="resumeIDs" value="${param.resumeIDs}">
    
     <input type="hidden" id="recordKeya" value="${param.recordKeya}">
    <input type="hidden" id="recordIDa" value="${param.recordIDa}">
    <input type="hidden" id="resumeIDa" value="${param.resumeIDa}">
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>工作描述</li>
            <!-- 添加实习经验.html -->
            <li><a id="addInternshipExperience">保存</a></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot self-ass">
        <div>
            <textarea rows="5" id="duties">${param.duties}</textarea>
            <p>还可以输入<span>400</span>个字</p>
        </div>
    </div>

    <script src="<%=basePath%>js/ea/production/resume/jobDesc.js" type="text/javascript"></script>
    
</body>
</html>