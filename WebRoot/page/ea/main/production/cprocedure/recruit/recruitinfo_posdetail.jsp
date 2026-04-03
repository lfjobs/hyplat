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

<title>职位详情</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_posdetail.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var riId = "${param.riId}";
var position = "${param.position}";
var iscol = "${iscol}";
var staffid = "";
var istou = "${istou}";
var token = 0;
var back = "${param.back}";
var companyId = '${companyId}';
var ccompanyId = '${ccompanyID}';
var companyname = '${search}';
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
				职位详情
			</li>
			<li style="text-align: center;">
				
			</li>
		</ul>
	</div>
	
</header>
<div class="main">
	<div class="xiangq_top">
		<div id="" class="zongshu_top">
			<div class="pull-left">${obj[1]}</div>
			<div class="pull-right" style="color: #ff6600;">[${obj[8]}]</div>
		</div>
		<div id="" class="zongshu">
			<ul>
				<li class="first">
					<img src="<%=basePath%>images/ea/recruit/ico0_03.png"/>
					<span>${obj[3]}</span>
				</li>
				<li>
					<img src="<%=basePath%>images/ea/recruit/ico0_05.png"/>
					<span>${obj[6]}</span>
				</li>
				<li>
					<img src="<%=basePath%>images/ea/recruit/ico0_07.png"/>
					<span>${obj[4]}</span>
				</li>
				<li>
					<img src="<%=basePath%>images/ea/recruit/ico0_09.png"/>
					<span>${obj[7]}</span>
				</li>
			</ul>
		</div>
		<%-- <div class="zongshu_bot">
			<span>职位诱惑：弹性工作 A轮融资 前景广阔 老板好~</span>
		</div> --%>
	</div>
	<div class="miaoshu">
		<div class="miaoshu_header">
			<img src="<%=basePath%>images/ea/recruit/ico0_16.png"/>
			<span>职位描述</span>
		</div>
		<div class="miaoshu_main">
			任职需求:</br>${obj[9]}
			
		</div>
	</div>
	<div class="qingq_gongsi">
	  <span class="ccompanyID" style="display:none;">${obj[11]}</span>
		<ul>
		<li>
			<div class="zhao_main_lis_left pull-left text-center img-responsive">
				<div class="img_wai" style="height: 4rem;">
				    <c:if test='${obj[0] ne null&&obj[0] ne ""}'>
					     <img src="<%=basePath%>${obj[0]}" alt="" />
				        
				      </c:if>
				      <c:if test='${obj[0] eq null||obj[0] eq ""}'>
				        <img src="<%=basePath%>images/ea/recruit/gongsi_10.png" alt="" />
				     </c:if>
				</div>
			</div>
			<div class="zhao_main_lis_center pull-left" style="width: 70%;">
				<h5 style="line-height: 4rem;">${obj[2]}</h5>
			</div>
			<div class="pull-left zhao_main_lis_right" style="width: 10%;">
				<div class="text_wai" style="height: 4rem;">
					<img style="height:2rem;" src="<%=basePath%>images/ea/recruit/wfj_return_07.png"/>
				</div>
				
			</div>
		</li>
		</ul>
	</div>
	<div class="gongzuo_di">
		<div id="">
			<img src="<%=basePath%>images/ea/recruit/ico0_20.png"/>
			<span>工作地点</span>
		</div>
		<div>${obj[10]}</div>
	</div>
	<div class="xiangsi_zhi">
		<img src="<%=basePath%>images/ea/recruit/ico0_24.png" alt="" />
		<span>相似职位</span>
	</div>
	<div class="xiangsi_zhi_main">
		<ul>
		
		<c:forEach items="${list}" var="item">
		<li>
		        <span style="display:none;" class="riId">${item[5]}</span>
				<span style="display:none;" class="position">${item[1]}</span>
			<div class="zhao_main_lis_left pull-left text-center img-responsive gs">
				<div class="img_wai">
				   <c:if test='${item[0] ne null&&item[0] ne ""}'>
					     <img src="<%=basePath%>${item[0]}" alt="" />
				        
				      </c:if>
				      <c:if test='${item[0] eq null||item[0] eq ""}'>
				        <img src="<%=basePath%>images/ea/recruit/gongsi_10.png" alt="" />
				     </c:if>
				</div>
			</div>
			<div class="zhao_main_lis_center pull-left gs">
				<h4>${item[1]}</h4>
				<p class="comname">${item[2]}</p>
				
				<div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt="" />${item[3]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[4]}
					</span>
				</div>
			</div>
			<div class="pull-left zhao_main_lis_right">
				<p class="comname">${fn:substring(item[7],5,10)}</p>
				<p>${item[6]}</p>
				
			</div>
			<div style="clear:both;"></div>
		</li>
		</c:forEach>
		</ul>
	</div>
</div>
<footer class="zhi_xqing">
	<div id="">
		<div class="toujianli">投递简历</div>
		<div class="footer_img shoucang">
			<img src="<%=basePath%>images/ea/recruit/ico0_28.png" id="collect"/>
			<img src="<%=basePath%>images/ea/recruit/shoucang_03.png" class="dN"  id="cancelcol" />
		</div>
	</div>
</footer>


<div class="jianli_tan_mo dN tan_mo">
<div class="jianli_tan">
	<p>投递失败</p>
	<p style="font-weight:bold;">您的简历不完善</p>
	<div>
		<div class="can">取消</div>
		<div class="wan" style="color:#FF6600;">去完善简历</div>
	</div>
</div>
</div>

	</body>
</html>
