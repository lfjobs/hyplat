<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>学历学位</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
var back = "${param.back}";

</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>学历学位</li>
            <li></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
    
    <input type="hidden" id="resumeID" value="${param.resumeID}">
    <!-- 前台传的 -->
    <input type="hidden" id="keys" value="${param.keys}">
    <input type="hidden" id="educationIDs" value="${param.educationIDs}">
    <input type="hidden" id="resumeIDs" value="${param.resumeIDs}">
    
    <input type="hidden" id="keya" value="${param.keya}">
    <input type="hidden" id="educationIDa" value="${param.educationIDa}">
    <input type="hidden" id="resumeIDa" value="${param.resumeIDa}">
    
    <input type="hidden" id="admissionTime" value="${param.admissionTime}">
    <input type="hidden" id="graduationTime" value="${param.graduationTime}">
    <input type="hidden" id="name" value="${param.name}">
    <input type="hidden" id="staffid" value="${param.staffid}">
    <input type="hidden" id="staffids" value="${param.staffids}">
    
    <input type="hidden" id="type" value="${param.type}">
    <input type="hidden" id="typedata" value="${param.typedata}">
    <input type="hidden" id="professionalName" value="${param.professionalName}">
        <div class="degree_txt">
            <p>初中</p>
            <p>中技</p>
            <p>高中</p>
            <p>中专</p>
            <p>大专</p>
            <p>本科</p>
            <p>硕士</p>
            <p>MBA</p>
            <p>EMBA</p>
            <p>博士</p>
            <p>其他</p>
        </div>
    </div>
  <script src="<%=basePath%>js/ea/production/resume/degree.js" type="text/javascript"></script>
  
</body>
</html>