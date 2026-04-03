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
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_resume.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var resumeID = "${param.resumeID}";
var position = "${param.position}";
var date1 = "${edu[0]}";
var date2 = "${edu[1]}";
var date3 = "${edu[2]}";
var token = 0;
var iscol = "${iscol}";
var istou = "${istou}";
var users = "";
var back = "${param.back}";

</script>


</head>
<body>
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
			<li style="text-align: center;">
				
			</li>
		</ul>
	</div>
	
</header>
<div class="main">
	<div class="xinxi_top">
		<div class="xinxi_top_img">
		            <c:if test='${obj[4] ne null&&obj[4] ne ""}'>
					     <img src="<%=basePath%>${obj[4]}"  width="50" height="50" alt="" />
				        
				      </c:if>
				      <c:if test='${obj[4] eq null||obj[4] eq ""}'>
				        <img src="<%=basePath%>images/ea/production/head2x.png"  width="50" height="50" alt="" />
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
				<span>年龄：</span><span class="age">${obj[13]}周岁</span>
			</li>
			<li>
				<span>工作经验：</span><span class="edu"></span>
			</li>
			<li>
				<span>籍贯 ：</span><span>${obj[6]}</span>
			</li>
			<li>
				<span>现居地址 ：</span><span>${obj[7]}</span>
			</li>
			
			
			<li>
				<span>邮箱 ：</span><span><c:if test='${istou=="yes"}'>${obj[8]}</c:if><c:if test='${istou=="no"}'>抢人才后可见</c:if></span>
			</li>
			<li>
				<span>联系电话 ：</span><span><c:if test='${istou=="yes"}'>${obj[9]}</c:if><c:if test='${istou=="no"}'>抢人才后可见</c:if></span>
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
<c:if test='${param.type=="抢人才" }'>
<footer class="zhi_xqing">
	<div id="">
		<div class="toujianli tou">抢人才</div>
		<div class="footer_img shoucang">
		    <img src="<%=basePath%>images/ea/recruit/ico0_28.png" id="collect"/>
			<img src="<%=basePath%>images/ea/recruit/shoucang_03.png" class="dN"  id="cancelcol" />
		</div>
	</div>
</footer>
<div class="tan_kuang tan2 dN">
		<div class="zhaopin_kuang1">
			<img src="<%=basePath%>images/ea/recruit/zhao_03.png" alt="" />
			<span>
				<span class="again">
					继续抢人才
				</span>
				
			</span>
		</div>
</div>
<div class="jianli_tan_mo dN tan_mo">
<div class="jianli_tan">
	<p>确认是否邀请</p>
	<p style="font-weight:bold;">邀请一位人才会消耗</br>
	100微分金金币(1元)</p>
	<div>
		<div class="no">取消</div>
		<div class="yue">确认</div>
	</div>
</div>
</div>
</c:if>

<div class="jianli_tan_mo dN tiptan">
<div class="jianli_tan">
	<p class="tiptitle">温馨提示</p>
	<p class="tipcontent" style="font-weight:bold;"></p>
	<div>
		<div class="tipcan">取消</div>
		<div class="tipconfirm" style="color:#FF6600;">确认</div>
	</div>
</div>
</div>
	</body>
</html>
