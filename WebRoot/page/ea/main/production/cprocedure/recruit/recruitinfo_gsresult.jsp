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


<title>招聘搜索结果</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/ea/production/sousuo.css">

<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_gsresult.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
var token1 = 0;
var staffid = "";
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
				<input type="search" name="" id="search" placeholder="搜索职位/公司" value="" />
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
			<div class="teli">
			<span>智能排序</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li>
		<%-- <li>
			<div class="dis">
			<span>附近</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>
			</div>
		</li> --%>
		<li>
			<div  class="Sort"> 
			<span>月薪</span>
			<img src="<%=basePath%>images/ea/recruit/icon_03.png"/>

			</div>
		</li>
		<li>
			<div class="more">
			<span>更多</span>
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
              <li class="last" id="${item[4]}">
            </c:if> 
             <c:if test="${fn:length(pageForm.list)-1 ne status.index}"> 
              <li id="${item[4]}">
            </c:if> 
             <span style="display:none;" class="riId">${item[4]}</span>
			 <span style="display:none;" class="position">${item[0]}</span>
			
			   <div class="zhao_main_lis_left pull-left text-center img-responsive">
			     <c:if test='${item[6] eq "0"}'>
				    <div class="img_wai">
				   
					<img class="quan_xuan" style="width: 2rem;" src="<%=basePath%>images/ea/recruit/ico_zhi_06.png"/>
					<img class="dN quan_xuan" style="width: 2rem;" src="<%=basePath%>images/ea/recruit/chan_07.png"/>
					</div>
				</c:if>
			  </div>
			
			<div class="zhao_main_lis_center pull-left">
				<h4>${item[0]}</h4>
				<p class="comname">${item[1]}</p>
				<div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item[2]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[3]}
					</span>
				</div>
			</div>
			<div class="pull-left zhao_main_lis_right">
				<div class="text_wai">
				               <c:if test='${item[6] eq "0"}'>
									<div class="tou dan">投</div>
								</c:if>
								<c:if test='${item[6] ne "0"}'>
										<div class="yitou dan">已投</div>
								</c:if>
					
				</div>
				
			</div>
			
		</li>
	</c:forEach>
	</c:if>
	 <c:if test="${fn:length(pageForm.list) eq 0}">
	 <li style="height:auto;">
		    <div style="text-align:center;padding-bottom:20%;">
		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=basePath%>images/ea/recruit/tipnone.png" width="180">
		      <p style="font-weight:bold; font-size:16px;">没有找到相关的职位，换个关键词<br/>或者减少筛选条件试一试</p>
		    </div>
		 </li>
		 </c:if>
	
	</ul>
	
	
</div>
</div>
<div class="tan_kuang tan1 dN">
		<div class="zhaopin_kuang">
			<img src="<%=basePath%>images/ea/recruit/zhao_03.png" alt="" />
			<span>
				<span class="again">
					继续投递简历
				</span>
				
			</span>
		</div>
	</div>
<div class="tou_j_k dN tou">
	<p></p>
	<p>投递简历</p>
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
<!-- grade -->
<div class="grade-eject sel">
    <ul class="grade-w" id="gradew">      
			<li><span class="orange">智能排序</span><img  class="gou"
				src="<%=basePath%>images/ea/recruit/rightgou.png" /></li>
			<li><span>月薪最高</span></li>
			<li style="border:none;"><span>最新发布</span></li>

	</ul>

</div>
<!-- End grade -->


<!-- Category -->
<div class="sel dismain dis-eject" style="width:100%;height:100%;display:none;">
<div class="selmain">
    <div class="left" >
      <ul class="grade-left">
            <li class="init"><span>学历</span><span></span></li>
			
      </ul>
    </div>
    <div class="right">
       <ul class="grade-right">
            <li class="ful"><span>全部</span><span class="rightbor" style="display:none;"></span><img  class="gou" 
				src="<%=basePath%>images/ea/recruit/rightgou.png"/> </li>
	
      </ul>
     
    </div>
   </div>

</div>
<!-- End Category -->










<!-- Category -->
<div class="sel zmain distance-eject" style="width:100%;height:100%;display:none;">
<div class="selmain">
    <div class="left" >
      <ul class="grade-left">
            <li class="init"><span>学历</span><span class="education"></span></li>
			<li><span>发布时间</span><span class="publishDate"></span></li>
			<li><span>工作经验</span><span class="experience"></span></li>
			<li><span>职位类型</span><span class="posType"></span></li>
			<li><span>公司性质</span><span class="comPro"></span></li>
			<li><span>公司规模</span><span class="comScale"></span></li>
      </ul>
    </div>
    <div class="right">
       <ul class="grade-right" id="content">
       <!-- 学历 -->
            <li class="ful"><span>全部</span><span class="rightbor" style="display:none;"></span><img  class="gou" 
				src="<%=basePath%>images/ea/recruit/rightgou.png"/> </li>
	
      </ul>
     
    </div>
   </div>
   <div class="clear"></div>
   <div class="white opra">
   
     <div class="btnwhite clearcri" style="float:left;margin-left:15%;"><span>清空条件</span></div>
     <div class="btnorange confirmcri" style="float:right;margin-right:15%;"><span>确定</span></div>
   </div> 
</div>
<!-- End Category -->

<!-- Category -->
	<div class="Sort-eject Sort-height sel">
		<ul class="Sort-Sort" id="Sort-Sort">
			<li><span class="orange">全部</span> <img  class="gou"
				src="<%=basePath%>images/ea/recruit/rightgou.png" />
			</li>
			<li><span>1千以下</span></li>
			<li><span>1千-2千</span></li>
			<li><span>2千-4千</span></li>
			<li><span>4千-6千</span></li>
			<li><span>6千-8千</span></li>
			<li><span>8千-1万</span></li>
			<li><span>1万-1.5万</span></li>
			<li><span>1.5万-2万</span></li>
			<li><span>2万-3万</span></li>
			<li><span>3万-5万</span></li>
			<li><span>5万以上</span></li>
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
	<input type="hidden"  name="searchCriteria.education" id="education" value="${searchCriteria.education}"/>
	<input type="hidden"  name="searchCriteria.publishDate" id="publishDate" value="${searchCriteria.publishDate}"/>
	<input type="hidden"  name="searchCriteria.experience" id="experience" value="${searchCriteria.experience}"/>
    <input type="hidden"  name="searchCriteria.posType" id="posType" value="${searchCriteria.posType}"/>
	<input type="hidden"  name="searchCriteria.comPro" id="comPro" value="${searchCriteria.comPro}"/>
	<input type="hidden"  name="searchCriteria.comScale" id="comScale" value="${searchCriteria.comScale}"/>
	<input type="hidden"  name="searchCriteria.salary" id="salary" value="${searchCriteria.salary}"/>
	<input type="hidden"  name="searchCriteria.sortbycri" id="sortbycri" value="${searchCriteria.sortbycri}"/>
	<input type="hidden"  name="type" value="gs"/>
	</form>

</body>
</html>
