<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
<meta http-equiv ="proma" content = "no-cache"/>
 <meta http-equiv="cache-control" content="no cache" />
 <meta http-equiv="expires" content="0" />

<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css" rel="stylesheet"
	type="text/css" />
	
<link href="<%=basePath%>css/ea/production/default.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/production/normalize.css" rel="stylesheet"
	type="text/css" />
	<link href="<%=basePath%>css/ea/production/styleresume.css" rel="stylesheet"
	type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>

<title>个人简历</title>
<style type="text/css">
		#jqmeter-container{width: 300px;height: 13px;margin:0 auto;}
		


	</style>
	
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var deg1 = "${obj[2]}";//个人简介
	var deg2 = "${obj2}";//教育背景
	var deg3 = "${obj3}";//求职意向
	var deg4 = "${obj4}";//工作经验
	var date = "${obj[8]}";
	var data = "${act}";
	var resumeID = "${resumeID}";//每个人的id
	var resumeIDp = "${resumeIDp}";//每个人的id
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	var back = "${param.back}";
</script>
</head>
<body >

	<div class="res_top" >
		<input type="hidden" id="staffid" value="${staffid}">
		<ul>
			<li><a id="returnClick" ><img
					src="<%=basePath%>images/resume/left_jt.png" alt="">
			</a>
			</li>
		
			<li>个人简历</li>
			<!--隐身设置.html  -->
			<li><a id="stealthSetting">默认设置</a>
			</li>
			<div class="clearfix"></div>
		</ul>
	</div>
	<div class="res_top_hidden">
	<div class="res_bot">
		<div class="res_bot_grd">
			<div class="res_bot_touxiang">
			<c:if test="${obj[3]==null}">
			<img src="<%=basePath%>images/ea/production/head2x.png" alt="">
			</c:if>
				<img src="<%=basePath%>${obj[3]}" alt="">
			</div>
			
	 			<p>${obj[0]}</p>
            <h5><span>${obj[6]}</span><span id="ss"></span><span>${obj[7]}</span><a  id="edit"><img src="<%=basePath%>images/resume/editing.png" class="edi">编辑</a></h5>
			
			<!-- 简历详情.html -->
			<a id="resumeDetails"><button>预览简历</button>
			</a>
		</div>
		<div class="res_bot_grd2">
			<input type="hidden" id="degree01" value="${obj[2]}">
			<div id="jqmeter-container" style="height:30px;"></div>
		
			<p align="right">${fn:substring(obj[1],0,10)}</p>
			<div class="clearfix"></div>
		</div>
		<div class="res_bot_grd2">
		
			<div class="res_bot_mil">
				<!--实习经验.html  -->
				<a id="min">
					<div>简历名称</div>
					<div>
						<span>${obj[9]!=null&&obj[9]!=null?obj[9]:"个人简历"}</span>
						<img src="<%=basePath%>images/resume/right_jt.png" style="width: 0.8rem;">

					</div>
					<div class="clearfix"></div> </a>
			</div>
		  
			<div class="res_bot_mil">
				<!-- 求职意向.html -->
				<a id="JobSearchIntention">
					<div>求职意向</div>
					<div>
						<c:if test="${obj3=='1'}">
	                     	完整
	                     </c:if>
						<img src="<%=basePath%>images/resume/right_jt.png" style="width: 0.8rem;">

					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil">
				<!--教育背景.html  -->
				<a id="educationalBackg">
					<div>教育背景</div>
					<div>
						<c:if test="${obj2=='1'}">
	                     	完整
	                     </c:if>
						<img src="<%=basePath%>images/resume/right_jt.png" style="width: 0.8rem;">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil">
				<!--实习经验.html  -->
				<a id="internshipExperience">
					<div>工作/实习经历</div>
					<div>
						<c:if test="${obj4=='1'}">
	                     	完整
	                      </c:if>
						<img src="<%=basePath%>images/resume/right_jt.png" style="width: 0.8rem;">

					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil">
				<!--自我评价.html  -->
				<a id="selfEvaluation">
				
				
				 <div>自我评价<span>（选填）</span></div>
                    <div><c:if test="${obj[2]=='1'}">
	                     	完整
	                     </c:if><img src="<%=basePath%>images/resume/right_jt.png" style="width: 0.8rem;"> </div>
                    <div class="clearfix"></div>
					
			</a>
		</div>
	</div>
<input id="resumeID" type="hidden" value="${list[0][5] }">
	</div>
	</div>
<script src="<%=basePath%>js/ea/production/resume/jqmeter.min.js"
		type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/production/resume/resume.js"
		type="text/javascript"></script>
		
<script>
$(document).ready(function(){
var p;
var a;
var anb = "0";

p=date.substring(0,7);
a = parseInt(data)-parseInt(p);
if(isNaN(a)){
	
	$("#ss").text(anb+"岁");
	
	return
}else{
	$("#ss").text(a+"岁");
}


var goal='100';
if(deg3!=null&&deg3!=undefined&&deg3=="1"){
	var ant='25';
} 
if(deg2!=null&&deg2!=undefined&&deg2=="1"){
	var ant='50';
} 
if(deg4!=null&&deg4!=undefined&&deg4=="1"){
	var ant='75';
} 

if(deg1!=null&&deg1!=undefined&&deg1=="1"){
	if(deg3!=null&&deg3!=undefined&&deg3=="1"){
		if(deg2!=null&&deg2!=undefined&&deg2=="1"){
			if(deg4!=null&&deg4!=undefined&&deg4=="1"){
				var ant='100';
			}
		} 
	}	
}
			$("#jqmeter-container").jQMeter({
				goal:goal,
    			raised:ant,
    			width:'300px',
    			height:'13px'
			});
			
});
</script>

</body>
</html>