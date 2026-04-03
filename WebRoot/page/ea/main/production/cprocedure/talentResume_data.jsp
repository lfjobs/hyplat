<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>简历详情</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/talentResume_data.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	span{
		display:-moz-inline-box;
		display:inline-block;
	}
	input{
	border: 1px solid #BDBDBD;
	background-size:100% 100%;}
</style>
<script type="text/javascript">

var basePath="<%=basePath%>";	
var tpID="${obj[0]}";
</script>


</head>
<body>
<div class="outer_title">
<div class="outer_body">
		<header class="fixed">
	<div class="header">
		<ul>
			<li  class="arrar">
				<div>
					<img src="<%=basePath%>images/ea/recruit/top_reture.png" alt="" />
				</div>
			</li>
			<li class="header_c" style="text-align: center;">
				简历详情
			</li>
		</ul>
	</div>
</header>
<div class="main">
	<div class="xinxi_top">
		<div class="xinxi_top_img">
		
		<c:if test='${obj[4] eq null||obj[4] eq ""}'>
			<img src="<%=basePath%>images/ea/recruit/gongsi_10.png" alt="" />
		</c:if>
		<c:if test='${obj[4] ne null&&obj[4] ne ""}'>
			<img src="<%=basePath%>${obj[4]}" alt="" />
		</c:if>
		</div>
		<p>${obj[2]}</p>
	</div>
	
	<div id="" class="gongsi_jie">
	<div id="" class="gongsi_jie_main">
		<ul class="gongxi_jian" style="border-bottom: none;">
			<li>
				<span>性别：</span><span>${obj[5]}</span>
			</li>
			<li>
				<span>年龄：</span><span class="age">${obj[12]}</span>
			</li>
			<li>
				<span>工作经验：</span><span class="edu">${obj[13] }</span>
			</li>
			<li>
				<span>籍贯 ：</span><span>${obj[6]}</span>
			</li>
			<li>
				<span>现居地址 ：</span><span>${obj[7]}</span>
			</li>
			<li>
				<span>邮箱 ：</span><span>${obj[8]}</span>
			</li>
			<li>
				<span>联系电话 ：</span><span>${obj[9]}</span>
			</li>
		</ul>																	
	</div>
	</div>
	<div id="" class="gongsi_jie">
		<div id="" style="width:96%;margin: 0 auto;background: #f2f2f2;height:1rem;"></div>
	<div class="gongsi_jie_main">
			<h4>求职意向</h4>
			<ul class="gongxi_jian" style="border-bottom: none;">
		<li>
			<span>期望职位：</span><span>${obj[1]}</span>
		</li>
		<li>
			<span>期望薪资：</span><span>${obj[10]}元</span>
		</li>
		<li>
			<span>期望地区：</span><span>${obj[3]}</span>
		</li>
		
	</ul>
		</div>
	</div>
	<div id="" class="gongsi_jie">
		<div id="" style="width:96%;margin: 0 auto;background: #f2f2f2;height:1rem;"></div>
	<div class="gongsi_jie_main">
			<h4>工作经验</h4>
			<c:if test="${fn:length(srlist) ne 0}">
			<c:forEach items="${srlist}" var="item">
			<ul class="gong_jinyan">
				<li>${item.companyName}</li>
				<li><span>任职职位：</span><span>${item.postName}</span></li>
				<%-- <li><span>薪资水平：</span><span></span></li> --%>
				<li><span>在职时间：</span><span>${fn:substring(item.startTime,0,10)}至<c:if test="${item.endTime ne null}">${fn:substring(item.endTime,0,10)}</c:if><c:if test="${item.endTime eq null}">今</c:if>
				</span></li>
			</ul>
			</c:forEach>
			</c:if>
			<c:if test="${fn:length(srlist) eq 0}">
			无
			</c:if>
		</div>
		
	</div>
	<div class="gongsi_jie jiaoyu_jli">
				<div id="" style="width:96%;margin: 0 auto;background: #f2f2f2;height:1rem;"></div>
	<div class="gongsi_jie_main">
	<h4>教育经历</h4>
	<c:if test="${fn:length(edulist) ne 0}">
		<c:forEach items="${edulist}" var="item">
			<ul class="gong_jinyan">
				<li>${fn:substring(item.admissionTime,0,10)}至${fn:substring(item.graduationTime,0,10)}</li>
				<li><h4 style="border-bottom: 0;line-height:2rem;">${item.name}</h4></li>
				<li class="zhuanye"><span>${item.education}</span><span>${item.professionalName}</span></li>
			</ul>
	   </c:forEach>
		</c:if>	
			<c:if test="${fn:length(edulist) eq 0}">
			无
			</c:if>
		</div>
		</div>
		<div class="gongsi_jie jiaoyu_jli" >
			<div id="" style="width:96%;margin: 0 auto;background: #f2f2f2;height:1rem;"></div>
			<div class="gongsi_jie_main" style="padding-bottom:15px;">
				<h4>自我评价</h4>
				<p ><c:if test='${obj[11] eq null}'>无</c:if><c:if test='${obj[11] ne null}'>${obj[11]}</c:if></p>
			</div>
		</div>
		</div>
</div>
</div>
<form name="form" id="form" method="post">
	<input type="submit" id="submit" name="submit" style="display: none;">
	<iframe name="hidden"  style="display: none;"></iframe>
<div class=" interview_title">
<div class="interview_body" >
	<div style="width:100%;height:8%;background-color: #1582C1;text-align: center;">
		<font style="font-size: 25px;color:#fff;position: relative;top: 10%;">面试邀请函</font>
	</div>
	<div style="position: relative;top: 4%;height: 13%;">
		<div style="position: relative;left: 4%;width: 96%;">
				<font style="font-weight:bold;">${obj[2]}&nbsp;&nbsp;</font>
				<font style="color:#ABABAB">   你好：</font>
				<input type="hidden" name="xm" value="${obj[2]} 你好：">
		</div>
		<div style="position: relative;left: 4%;width: 96%;color:#ABABAB;height:90%;">
				<textarea type="text" name="talentPool.content" style="width: 100%;height: 100%;border: 0px;overflow: hidden;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经过我们公司的初步筛选，认为你与我们的职位要求很匹配，现诚挚邀请你来我公司面谈。请准时出席，如时间上有变化也请尽快与我们联系。</textarea>
		</div>
	</div>
	<hr style="position: relative;top: 4%;height: 1%;"/>
	<div style="position: relative;top: 2%;height:45%;">
		<table style="width: 100%;height:100%; border-collapse:3px;">
			<tr>
				<td align="right" width="20%;"><font style="color:red;">*</font>面试时间</td><td width="5%;"></td>
				<td>
					<input type="text" class="interviewTime inputr" name="talentPool.interviewDate" style="width: 50%;height: 75%;background-image: url('<%=basePath%>images/ea/production/30123.png');" placeholder="请选择开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					<select style="height: 75%;background-color: #F3F9FD;" class="ws"><option value="">上午</option><option value="">下午</option></select>
					<select style="height: 75%;width:22%;background-color: #F3F9FD;" class="sj">
						<option value="8:00">8:00</option>
						<option value="8:30">8:30</option>
						<option value="9:00">9:00</option>
						<option value="9:30">9:30</option>
						<option value="10:00">10:00</option>
						<option value="10:30">10:30</option>
						<option value="11:00">11:00</option>
						<option value="11:30">11:30</option>
						<option value="12:00">12:00</option>
						<option value="12:30">12:30</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><font style="color:red;">*</font>面试地点</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" placeholder="请填写真实地点" name="talentPool.interviewPlace" class="interviewPlace inputr"></td>
			</tr>
			<tr>
				<td align="right">
					<font style="color:red;">*</font>联
					<span style="width:3.5%;"></span>系
					<span style="width:3.5%;"></span>人
				</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" placeholder="请选择联系人" name="talentPool.contactor" class="contactor inputr"></td>
			</tr>
			<tr>
				<td align="right"><font style="color:red;">*</font>联系电话</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" name="talentPool.contactTel" class="contactTel inputr"></td>
			</tr>
			<tr>
				<td align="right"><font>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</font></td><td></td>
				<td>
					<input type="checkbox" class="chbox" value="请携带简历" name="talentPool.remark">请携带简历
					<input type="checkbox" class="chbox" value="请带笔纸" name="talentPool.remark">请带笔纸
					<input type="checkbox" class="chbox" value="请带作品" name="talentPool.remark">请带作品
					<input type="checkbox" class="chbox" value="请着正装" name="talentPool.remark">请着正装<br>
					<input type="checkbox" class="chbox">其他
					<input type="text" style="background-color: #F3F9FD;width: 77%;height: 45%;" name="talentPool.remark">
				</td>
			</tr>
		</table>
	</div>
	<hr style="height: 1%;"/>
	<div style="height:9%;">
		<input type="checkbox" name="ccbox" class="chbox ccbox"><font>同时发送短信通知</font><font  color="#858585" style="font-size: 12%;">（需要发送1条，短信剩余0条。余额不足，请联系销售充值。）</font>
	</div>
	<div style="height:8%;">
		<span style="position: relative;left: 25%;width: 20%;background-color: #1582C1;text-align: center;color: #fff;cursor:pointer;" class="buttom">发送</span>
		<span style="position: relative;left: 35%;width: 20%;text-align: center;color: #000;border:1px solid #000;cursor:pointer;" class="buttom">取消</span>
	</div>
</div>
</div>
</form>
<c:if test='${obj[15] eq "03"}'>
	<footer class="jianli_footer">
		<div class="tou" style="width:98.5%;cursor:pointer;">发送面试通知</div>
		<!-- 	<div>查询下载</div> -->
	</footer>
</c:if>

<script>
	$(function(){
		$(".main").css("height",$(window).height()-$("header").outerHeight()-$("footer").outerHeight()+"px");
	
		

	})
</script>
	</body>
</html>
