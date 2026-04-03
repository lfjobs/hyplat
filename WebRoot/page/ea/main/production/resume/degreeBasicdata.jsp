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
	var staffKey= "${param.staffKey}";
	var staffID= "${param.staffID}";
	var staffName= "${param.staffName}";
	var endTime= "${param.endTime}";
	var culturalDegree= "${param.culturalDegree}";
	var nativePlace= "${param.nativePlace}";
	var staffAddress= "${param.staffAddress}";
	var reference= "${param.reference}";
	var referenceOrganization= "${param.referenceOrganization}";
	var resumeID= "${param.resumeID}";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	
	
	
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
  <script src="<%=basePath%>js/ea/production/resume/degreeBasicdata.js" type="text/javascript"></script>
  
</body>
</html>