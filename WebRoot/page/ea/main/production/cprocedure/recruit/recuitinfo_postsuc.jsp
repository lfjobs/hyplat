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

<title>邀请成功</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_postsuc.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var token = 0;
var position = "${param.position}";
var riId = "${param.riId}";
var back = "${param.back}";

</script>


</head>
	<body>
		<header class="fixed">
	<div class="header">
		<ul>
			<li class="arrar">
				<div>
					<img src="<%=basePath%>images/ea/recruit/top_reture.png" alt="" />
				</div>
			</li>
			<li class="header_c" style="text-align: center;">
				免费投递
			</li>
			<li style="text-align: center;">
				
			</li>
		</ul>
	</div>
	
</header>
<div class="main">
	<div class="yaoqing">
		<div class="yaoqing_top">
			<img src="<%=basePath%>images/ea/recruit/duigou_03.png" alt="" />
			<span>投递成功！</span>
		</div>
		<div class="yaoqing_bot">
			请等待TA的消息或来电
		</div>
	</div>
	<div class="yaoqing_tui">
		<h4>为你推荐以下职位</h4>
	</div>
	<div class="yaoqing_tui_main">
		<ul>
		<c:if test="${fn:length(tuilist) ne 0 }">
		<c:forEach items="${tuilist}" var="item">
		<li>
		     <span style="display:none;" class="riId">${item.riId}</span>
			<div class="zhao_main_lis_left pull-left text-center img-responsive">
				<div class="img_wai">
					<img class="quan_xuan" style="width: 1.5rem;" src="<%=basePath%>images/ea/recruit/ico_zhi_06.png"/>
					<img class="dN quan_xuan" style="width: 1.5rem;" src="<%=basePath%>images/ea/recruit/chan_07.png"/>
			
					</div>
			</div>
			<div class="zhao_main_lis_center pull-left" style="width:56%;">
				<h4>${item.positionName}</h4>
				<p class="comname">${item.companyName}</p>
				<div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item.workCity}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item.education}
					</span>
				</div>
			</div>
			<div class="pull-left zhao_main_lis_right" style="text-align:right;width:22%;">
				<p class="date" style="white-space:nowrap;">${fn:substring(item.publishDate,0,10)}</p>
				<p>${item.salary}</p>
				
			</div>
		</li>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(tuilist) eq 0 }">
		<li>
		    <div  style="text-align:center;padding-bottom:20%;">
		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=basePath%>images/ea/recruit/tipnone.png" width="180">
		      <p style="font-weight:bold; font-size:16px;">暂无相似职位推荐&nbsp;&nbsp;</p>
		    </div>
		</li>
		</c:if>
	
	</ul>
	</div>
</div>
<footer class="tui_footer">
     <div class="full">
		<img src="<%=basePath%>images/ea/recruit/ico_zhi_06.png" alt="" />
		<img class="dN" src="<%=basePath%>images/ea/recruit/chan_07.png"/>
		<span>全选</span>
	</div>
	<div class="tou unsel">投递</div>
</footer>

<div class="jianli_tan_mo dN tan_mo">
<div class="jianli_tan">
	<p>温馨提示</p>
	<p style="font-weight:bold;">确定要投递简历？</p>
	<div>
		<div class="no">取消</div>
		<div class="yue">确认</div>
	</div>
</div>
</div>

	</body>
</html>
