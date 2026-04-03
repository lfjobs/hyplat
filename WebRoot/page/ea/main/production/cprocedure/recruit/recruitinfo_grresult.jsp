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
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="x-rim-auto-match" content="none"/>
<meta name="HandheldFriendly" content="true"/>
<title>求职搜索结果</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/ea/production/sousuo.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_grresult.js"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";	
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
var token = 0;
var keyword ="${searchCriteria.keyword}";
var sex ="${searchCriteria.sex}";
var education ="${searchCriteria.education}";
var experience ="${searchCriteria.experience}";
var age ="${searchCriteria.age}";
var users = "";


</script>

</head>
<body>
		<header class="fixed">
		<div class="header">
		<ul>
			<li class="arrar" style="width: 10%;">
				<div>
					<img src="<%=basePath%>images/ea/recruit/top_reture.png" alt="" />
				</div>
			</li>

			<li class="header_c" style="width: 78%;">
				<span class="header_c_tet"><img src="<%=basePath%>images/ea/recruit/search999.png"/><span></span></span>
				<div class="search_k">
				<input type="search" name="" id="search" placeholder="搜索职位" value="${searchCriteria.keyword}" />
				</div>
			</li>
			<li class="asd" style="text-align: center;right: 0;position: absolute; display:none;">
				<div class="header_c_text">
					<a  href="#" class="sousuo"> 搜索</a>
					<a  href="#"class="cancel"> 取消</a>
				</div>
			</li>

		</ul>
	</div>
	<div class="search_down">

   </div>
	
</header>
<div class="main">
<nav class="paixu_nav">
	<ul>
		<li>
			<div class="sex">
			<span>性别</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li>
		<li>
			<div class="learn">
			<span>学历</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li>
		<li>
			<div class="teli">
			<span>经验</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li>
		<li>
			<div class="Sort">
			<span>年龄</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li>
	</ul>
</nav>
<div class="zhao_main">
	<ul class="bodyresult">
		<c:if test="${fn:length(pageForm.list) ne 0}">
	      <c:forEach items="${pageForm.list}" var="item"  varStatus="status">
	        <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
              <li class="last" id="${item[0]}">
            </c:if> 
             <c:if test="${fn:length(pageForm.list)-1 ne status.index}"> 
              <li id="${item[0]}">
            </c:if> 
             <span style="display:none;" class="resumeID">${item[0]}</span>
			 <span style="display:none;" class="position">${item[1]}</span>
			<div class="zhao_main_lis_left pull-left text-center img-responsive">
			    <c:if test='${item[8] eq "0"}'>
							
				   <div class="img_wai">
					<img class="quan_xuan" style="width: 2rem;" src="<%=basePath%>images/ea/recruit/ico_zhi_06.png"/>
					<img class="dN quan_xuan" style="width: 2rem;" src="<%=basePath%>images/ea/recruit/chan_07.png"/>
			
					</div>
				</c:if>
			</div>
			<div class="zhao_main_lis_center pull-left">
				<h4>${item[1]}</h4>
				<p>${item[2]}</p>
				<div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item[3]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[7]}
					</span>
				</div>
			</div>
			<div class="pull-left zhao_main_lis_right">
				<div class="text_wai">
				               <c:if test='${item[8] eq "0"}'>
									<div class="qiang dan">抢</div>
								</c:if>
								<c:if test='${item[8] ne "0"}'>
										<div class="yiqiang dan">已抢</div>
								</c:if>
				
				</div>
				
			</div>
		</li>
		</c:forEach>
		</c:if>
		 <c:if test="${fn:length(pageForm.list) eq 0}">
					<li style="height:auto;">
						<div style="text-align:center;padding-bottom:20%;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
								src="<%=basePath%>images/ea/recruit/tipnone.png" width="180">
							<p style="font-weight:bold; font-size:16px;">
								没有找到相关的职位求职者，换个关键词<br />或者减少筛选条件试一试
							</p>
						</div></li>
				</c:if>
		
	</ul>
	
	
</div>
</div>

	<div class="jianli_tan_mo dN tan_mo">
		<div class="jianli_tan">
			<p>确认是否邀请</p>
			<p style="font-weight:bold;">
				邀请一位人才会消耗</br> 100微分金金币(1元)
			</p>
			<div>
				<div class="no">取消</div>
				<div class="yue">确认</div>
			</div>
		</div>
	</div>
	<div class="tan_kuang tan2 dN">
		<div class="zhaopin_kuang1">
			<img src="<%=basePath%>images/ea/recruit/zhao_03.png" alt="" /> <span>
				<span class="again"> 继续抢人才 </span> </span>
		</div>
	</div>

	<div class="tou_j_k dN toqiang">
		<p></p>
		<p>抢人才</p>
	</div>
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
	
	<!-- 性别 -->
	<div class="sex-eject Sort-height sel">
		<ul class="Sort-Sort" id="sex-Sort">
			<li><span>全部</span><img  class="gou" 
				src="<%=basePath%>images/ea/recruit/rightgou.png"/> </li>
			<li><span>男</span></li>
			<li><span>女</span></li>
			
			
		</ul>
	</div>
	
	
	<!-- 学历 -->
	<div class="learn-eject Sort-height sel">
		<ul class="Sort-Sort" id="learn-Sort">
			<li><span>全部</span><img  class="gou"
				src="<%=basePath%>images/ea/recruit/rightgou.png"/> </li>
			<li><span>中专及以下</span></li>
			<li><span>大专</span></li>
			<li><span>本科</span></li>
			<li><span>硕士</span></li>
			<li><span>MBA</span></li>
			<li><span>EMBA</span></li>
			<li><span>博士</span></li>
			<li><span>其他</span></li>
			
		</ul>
	</div>
	<!-- End Category -->
	
	
	<!-- 经验 -->
<div class="grade-eject sel">
    <ul class="grade-w" id="gradew">      
		    <li><span class="orange">全部</span> <img  class="gou"
				src="<%=basePath%>images/ea/recruit/rightgou.png" /></li>
			<li><span>无经验</span></li>
			<li><span>1年以下</span></li>
			<li><span>1-3年</span></li>
			<li><span>3-5年</span></li>
			<li><span>5-10年</span></li>
			<li><span>10年以上</span></li>

	</ul>

</div>
<!-- End grade -->
	
<!-- 年龄 -->
	<div class="Sort-eject Sort-height sel">
		<ul class="Sort-Sort" id="Sort-Sort">
			<li><span class="orange">全部</span> <img  class="gou"
				src="<%=basePath%>images/ea/recruit/rightgou.png" />
			</li>
			<li><span>18-25岁</span></li>
			<li><span>26-30岁</span></li>
			<li><span>31-35岁</span></li>
			<li><span>36-40岁</span></li>
			<li><span>41-45岁</span></li>
			<li><span>46-50岁</span></li>
			<li><span>51岁以上</span></li>
			
		</ul>
	</div>
	<!-- End Category -->
	
	<!-- 查询条件 -->
   <form id="searchForm" name="searchForm" method="post">
    <input type="submit" style="display:none;" name="submit" />
	<input type="hidden"  name="searchCriteria.industry" id="industry" value="${searchCriteria.industry}"/>
	<input type="hidden"  name="searchCriteria.position" id="pos" value="${searchCriteria.position}"/>
	<input type="hidden"  name="searchCriteria.city" id="citys" value="${searchCriteria.city}"/>
	<input type="hidden"  name="searchCriteria.keyword" id="keyword" value="${searchCriteria.keyword}"/>
	<input type="hidden"  name="searchCriteria.sex" id="sex" value="${searchCriteria.sex}"/>
	<input type="hidden"  name="searchCriteria.education" id="education" value="${searchCriteria.education}"/>
	<input type="hidden"  name="searchCriteria.experience" id="experience" value="${searchCriteria.experience}"/>
	<input type="hidden"  name="searchCriteria.age" id="age" value="${searchCriteria.age}"/>
	<input type="hidden"  name="type" value="gr"/>
	</form>
</body>
</html>
