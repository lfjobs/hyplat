<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/production/common2.css" rel="stylesheet"
	type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/iscroll.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>

<script>
var staff="${param.staffIDs}";
var resumeID="${param.resumeID}";
var jitype="${param.jitype}";
var sccId="${param.sccId}";
var back = "${param.back}";
</script>
<title>求职意向</title>
</head>
<body>
	<div class="res_top">
	<input type="hidden" id="typep" value="${param.type}">
		<input type="hidden" id="staffid" value="${param.staffid}">
			<input type="hidden" id="resumeID" value="${param.resumeID}">
		<input type="hidden" id="works" value="${wanted.work}">
		<input type="hidden" id="region" value="${wanted.region}">
		<input type="hidden" id="positions" value="${wanted.position}">
		<input type="hidden" id="industrya" value="${wanted.industry}">
		<input type="hidden" id="salarys" value="${wanted.salary}">
		<input type="hidden" id="statuss" value="${wanted.status}">
		<input type="hidden" id="jobWantedKeys" value="${wanted.jobWantedKey}">
		<input type="hidden" id="resumeIDs" value="${wanted.resumeID}">
		<input type="hidden" id="jobWantedIds" value="${wanted.jobWantedId}">
		<input type="hidden" id="staffIDs" value="${wanted.staffID}">
		<ul>
			<li><a><img src="<%=basePath%>images/resume/left_jt.png"
					id="returnClick"> </a></li>
			<li>求职意向</li>
			<c:if test="${wanted.staffID==null}">
			<li id="saves">保存</li>
			</c:if>
			<c:if test="${wanted.staffID!=null}">
			<li id="bianji">保存</li>
			</c:if>
		</ul>
	</div>
	<div class="res_bot">
		<div class="res_bot_grd2">
			<div class="res_bot_mil" id="gzxz">
				<div>工作性质</div>
				<div>
					<span id="vaue">${param.work}</span><img
						src="<%=basePath%>images/resume/right_jt.png">
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="res_bot_mil">
			<a id="diqu">
				<div>期望地区</div>
				<div>
					<!--工作地区  -->
					<span id="reg">${param.region}</span><img
						src="<%=basePath%>images/resume/right_jt.png">
				</div>
				<div class="clearfix"></div></a>
			</div>
			<div class="res_bot_mil job-int_mil">
				<!--职位搜索  -->
				<a id="JobSearch">
					<div>职位类别</div>
					<div>
						<!--职位  -->
						<span id="pos">${param.position}</span><img
							src="<%=basePath%>images/resume/right_jt.png">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil job-int_mil">
				<!--行业搜索2  -->
				<a id="industrySearch2">
					<div>行业类别</div>
					<div>
						<!-- 行业类别 -->
						<span id="industry">${param.industrys}</span><img
							src="<%=basePath%>images/resume/right_jt.png">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil job-int_mil2">
				<!-- 期望薪资.html -->
				<a id="salaryExpectation">
					<div>
						<!-- 薪资 -->
						<span id="salary"></span><span>期望薪资（税前）</span>
					</div>
					<div>
						<p id="qbox">${param.salary}</p>
						<img src="<%=basePath%>images/resume/right_jt.png">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil" id="qzzt">
				<div>求职状态</div>
				<div>
					<span id="vaues" name="status">${param.status}</span><img
						src="<%=basePath%>images/resume/right_jt.png">
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div class="job-int_gzxz">
		<div class="job-int_d3"></div>
		<div class="job-int_d1">
			<div class="job-int_d">
				<button id="btn_qx">取消</button>
				<button id="btn_wc" onclick="WorkingProperty(this)">完成</button>
				<div class="clearfix"></div>
			</div>
			<div class="job-int_d2">
				<p class="confirmationBox">全职</p>
				<p>兼职</p>
				<p>实习</p>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="job-int_qzzt">
		<div class="job-int_d3-2"></div>
		<div class="job-int_d1">
			<div class="job-int_d job-int_d2">
				<button id="btn_qx2">取消</button>
				<button id="btn_wc2" onclick="Jobstatus(this)">完成</button>
				<div class="clearfix"></div>
			</div>
			<div class="job-int_d2 job-int_d2_2">
				<p class="confirmationBox2">离职，可立即上岗</p>
				<p>在职，正考虑换个环境</p>
				<p>在职，暂无跳槽打算</p>
				<p>在职，有更好的机会可以考虑</p>
				<p>应届毕业生</p>
			</div>
		</div>
	</div>
	<form id="form" name="form" method="post">
	<input type="submit" id="submit" name="submit" style="display: none;">
	<iframe name="hidden"  style="display: none;"></iframe>
</form>
	<script>
	var basePath="<%=basePath%>";
	</script>
	<script
		src="<%=basePath%>js/ea/production/resume/jobSearchIntention.js"
		type="text/javascript"></script>

</body>
</html>