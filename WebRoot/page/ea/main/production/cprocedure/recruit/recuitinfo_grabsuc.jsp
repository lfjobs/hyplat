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
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_grabsuc.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var position = "${param.position}";
var resumeID = "${param.resumeID}";
var token = 0;
var back = "${param.back}";


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
					<li class="header_c" style="width: 80%;text-align:center;">
						抢人才
					</li>
					
				</ul>
			</div>
			
		</header>
<div class="main">
	<div class="yaoqing">
		<div class="yaoqing_top">
			<img src="<%=basePath%>images/ea/recruit/duigou_03.png" alt="" />
			<span>邀请成功！</span>
		</div>
		<div class="yaoqing_bot">
		 	请等待TA的消息或来电 
		</div>
	</div>
	<div class="yaoqing_tui">
		<h4>为你推荐以下求职者</h4>
	</div>
	<div class="yaoqing_tui_main">
		<ul>
		<c:if test="${fn:length(tuilist) ne 0 }">
		<c:forEach items="${tuilist}" var="item">
		<li>
		     <span style="display:none;" class="resumeID">${item[0]}</span>
             <span style="display:none;" class="position">${item[1]}</span>
			<div class="zhao_main_lis_left pull-left text-center img-responsive">
				<div class="img_wai">
					<img class="quan_xuan" style="width: 2rem;" src="<%=basePath%>images/ea/recruit/ico_zhi_06.png"/>
					<img class="dN quan_xuan " style="width: 2rem;" src="<%=basePath%>images/ea/recruit/chan_07.png"/>
			
					</div>
			</div>
			<div class="zhao_main_lis_center pull-left">
				<h4>${item[1]}</h4>
				<p>${item[2]}</p>
				<div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item[3]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[5]}
					</span>
				</div>
			</div>
		</li>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(tuilist) eq 0 }">
		<li>
		    <div  style="text-align:center;padding-bottom:20%;">
		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=basePath%>images/ea/recruit/tipnone.png" width="180">
		      <p style="font-weight:bold; font-size:16px;">暂无匹配求职者推荐&nbsp;&nbsp;</p>
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
	<div class="tou unsel">邀请</div>
</footer>

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

<div class="jianli_tan_mo dN tiptan">
<div class="jianli_tan">
	<p class="tiptitle">温馨提示</p>
	<p class="tipcontent" style="font-weight:bold;"></p>
	<div class="confrim">
		<span>确定</span>
	</div>
</div>
</div>

	</body>
</html>
