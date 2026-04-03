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
		<link href="<%=basePath%>css/ea/production/mobiscroll.custom-2.5.0.min.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/production/stylezt.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/production/common2.css" rel="stylesheet"
	type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/jquery.mobile-1.3.0.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/mobiscroll.custom-2.5.0.min.js"
	type="text/javascript"></script>

<title>添加实习经验</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	var back ="${param.back}";
$(function(){
            var opt = {
                preset: 'date', //日期
                theme: 'sense-ui', //皮肤样式
                display: 'modal', //显示方式
                mode: 'scroller', //日期选择模式
                dateFormat: 'yy-mm-dd', // 日期格式
                setText: '确定', //确认按钮名称
                cancelText: '取消',//取消按钮名籍我
                dateOrder: 'yymmdd', //面板中日期排列格式
                dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
                endYear:2050 //结束年份
            };
            $("#beginTime").mobiscroll(opt).date(opt);
            $("#endTime").mobiscroll(opt).date(opt);
           
        });
</script>
</head>
<body>
	<div class="res_top">
		<ul>
			<li><a><img src="<%=basePath%>images/resume/left_jt.png"
					id="returnClick">
			</a>
			</li>
			<li>教育背景</li>
			<li onclick="doFn()">保存</li>
			<div class="clearfix"></div>
		</ul>
	</div>
	<input type="hidden" id="staffid" value="${param.staffid}">
	<input type="hidden" id="type" value="${param.type}">
	<input type="hidden" id="resumeID" value="${param.resumeID}">
	<div class="res_bot">
		<div class="res_bot_grd2">
			<div class="res_bot_mil">

				<div style="width:30%;">学校名称</div>
				<div style="width:65%;">
					<input style="width:100%;text-align: right;" class="ipt" type="text" id="scname"  placeholder="输入学院名称"
						required="required" value="${param.name}" >
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="res_bot_mil">
				<div>入学时间</div>
				<div >
					<input class="ipt_1" id="beginTime"  type="text"
						value="${param.admissionTime}"><img
						src="<%=basePath%>images/resume/right_jt.png" style="display: none;">
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="res_bot_mil">
				<div>毕业时间</div>
				<div  >
					<input class="ipt_1" id="endTime" type="text"
						value="${param.graduationTime}"><img
						src="<%=basePath%>images/resume/right_jt.png" style="display: none;">
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="res_bot_mil">
				<!--  专业搜索.html-->
				<a id="JobSearch">
					<div>专业名称</div>
					<div>
						<span id="professionalName">${param.professionalName}</span><img
							src="<%=basePath%>images/resume/right_jt.png">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="res_bot_mil">
				<!-- 学历学位.html -->
				<a id="degree">
					<div>学历学位</div>
					<div>
						<span id="education">${param.education}</span><img
							src="<%=basePath%>images/resume/right_jt.png">
					</div>
					<div class="clearfix"></div> </a>
			</div>
			<div class="alert_div" id="alert_d" style="top: 0;">
				<div class="alert">不能为空</div>
			</div>
		</div>
		<div class="alert_div2" style="top: -45px ;">
			<div class="alert_txt">
				<p>是否确认保存？</p>
				<div>
					<a id="add">
						<button id="quedingp">确定</button>
					</a>
					<button id="quxiao">取消</button>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<div id="datePlugin"></div>
	</div>
<script src="<%=basePath%>js/ea/production/resume/addEducation.js"
	type="text/javascript"></script>
<form id="form" name="form" method="post">
	<input type="submit" id="submit" name="submit" style="display: none;">
	<iframe name="hidden"  style="display: none;"></iframe>
</form>
</body>
</html>