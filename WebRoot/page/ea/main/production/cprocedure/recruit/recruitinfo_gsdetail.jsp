<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />

<title>公司详情</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_gsdetail.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var riId = "${param.riId}";
var position = "${param.position}";
var back="${param.back}";
var ccompanyId = '${ccompanyID}';
var companyId = '${companyId}';
var companyname = '${search}';
var pageNumber = '${pageNumber}';
var pageSize = '${pageSize}';
var rikey = '${recruitInfo.rikey}';
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
				<li class="header_c" style="text-align: center;">公司信息</li>
				<li style="text-align: center;"></li>
			</ul>
		</div>

	</header>
	<div class="main">
		<div class="xinxi_top">
			<div class="xinxi_top_img">
			          <c:if test='${ccom.logoPath ne null&&ccom.logoPath ne ""}'>
					     <img src="<%=basePath%>${ccom.logoPath}" alt="" style="width: 7rem;height: 7rem;border-radius: 50%;"/>
				        
				      </c:if>
				      <c:if test='${ccom.logoPath eq null||ccom.logoPath eq ""}'>
				        <img src="<%=basePath%>images/ea/recruit/gongsi_10.png" alt="" />
				     </c:if>
			
				
			</div>
			<p>${ccom.companyName}</p>
		</div>
		<ul class="gongxi_jian">
			<li><span>行业：</span><span>${ccom.industryType}</span></li>
			<li><span>性质：</span><span>${ccom.comPro}</span></li>
			<li><span>规模：</span><span>${ccom.comScale}</span></li>
			<li><span>网站：</span><span><a href="${ccom.companyWeb}"
					style="font-size: 12px;">进入网站</a></span></li>
		</ul>
		<div class="gongsi_dizhi">
			<img src="<%=basePath%>images/ea/recruit/ico0_03.png" alt="" /> <span>${ccom.companyAddr}</span>
		</div>
		<div id="" class="gongsi_jie">
			<div id=""
				style="width: 96%; margin: 0 auto; background: #f2f2f2; height: 1rem;"></div>
			<div class="gongsi_jie_main">
				<h4>公司介绍</h4>
				<p>${ccom.comPurpose}</p>
			</div>
		</div>
		<div class="other_zhi">该公司职位</div>
		<div class="other_zhi_main">
			<ul>
				<c:forEach items="${list}" var="item">
					<li><span style="display: none;" class="riId">${item[5]}</span>
						<span style="display: none;" class="position">${item[1]}</span>
						<div class="zhao_main_lis_center gspositon pull-left gs">
							<h4>${item[1]}</h4>
							<p class="comname">${item[2]}</p>
							<div>
								<span class="yaoqiu"> <img class="little_img"
									src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item[3]}
								</span><span class="yaoqiu"> <img class="little_img"
									src="<%=basePath%>images/ea/recruit/ico_15.png" />${item[4]}
								</span>
							</div>
						</div>
						<div class="pull-right zhao_main_lis_right gs">
							<p class="comname">${fn:substring(item[7],5,10)}</p>
							<p class="salary">${item[6]}</p>
						</div></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
