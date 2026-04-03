<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	/* String url = "ea/industry/ea_getiInvestment.jspa?codeID="+request.getParameter(codeID)+"&codesId="+request.getParameter(codesId)+"&codename="+request.getParameter(codename);
	session.setAttribute("url", url); */
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/contacts/investment/bootstrap.css"
	type="text/css"></link>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/contacts/investment/fadongji.css"
	type="text/css"></link>


<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.js"></script>


<title>天太世统科技有限公司</title>
<style type="text/css">
.lis_K {
	overflow: hidden;
	border-bottom: 1px solid #e3e3e3;
	margin: 0.2rem 0;
	padding: 0.3rem 0;
}

.li_lis_img {
	height: 2rem;
	width: 2rem;
}

.li_lis {
	padding-left: 0.6rem;
}
</style>
</head>
<body>
	<div class="top">
		<ul>
			<li class="arrow"><img
				src="<%=basePath%>images/contacts/comments/wfj_return_02.png"
				onclick="fanhui()" />
			</li>
			<li>${code.codeDesc}</li>
			<li></li>
		</ul>
	</div>
	<div class="main_hide">
		<div class="main">
			<div class="col-xs-12 banner">
				<img src="<%=basePath%>${code.iconPath}" alt="" />
			</div>
			<div class="col-xs-12" style="margin-top:2rem;">
				<div class="text_lis">
					<div class="col-xs-12">
						<img src="<%=basePath%>images/contacts/attractment/zhence.png"
							 class="pull-left ico1" />
						<h3 class="pull-left biaot">政策支持</h3>
					</div>
					<p class="col-xs-12 Text">依托高速铁路、煤矿与金属矿采掘、基础设施、科技重大专项等十大领域重点工程，振兴装备制造业</p>
				</div>
				<div class="text_lis">
					<div class="col-xs-12">
						<img src="<%=basePath%>images/contacts/attractment/pt.png" alt=""
							class="pull-left ico1" />
						<h3 class="pull-left biaot">平台优势</h3>
					</div>
					<p class="col-xs-12 Text">基于互联网大数据，在会员审核，咨询跟踪等多个环节对风险进行全方位把控。</p>
				</div>
				<div class="text_lis">
					<div class="col-xs-12">
						<img src="<%=basePath%>images/contacts/attractment/yuny.png"
							 class="pull-left ico1" />
						<h3 class="pull-left biaot">运营规范</h3>
					</div>
					<p class="col-xs-12 Text">通过规范的流程及严格的审查制度，保障客户资金安全，确保系统平稳运营。</p>
				</div>
				<div class="text_lis">
					<div class="col-xs-12">
						<img src="<%=basePath%>images/contacts/attractment/xitong.png"
							 class="pull-left ico1" />
						<h3 class="pull-left biaot">系统安全</h3>
					</div>
					<p class="col-xs-12 Text">通过规范的流程及严格的审查制度，保障客户资金安全，确保系统平稳运营</p>
				</div>
			</div>
			 
			<div class="col-xs-12" style="height: 2rem;line-height: 2rem;"
				id="divs">
				<c:forEach var="gs" items="${productList}">
					<div class='lis_K'>
						<a onclick='dianji(this)'><div class='col-xs-10 pull-left'>
								<img class='li_lis_img pull-left' id='srp'
									src='<%=basePath%>${gs[3]}' /> <span class='li_lis pull-left'
									style='line-height:2rem;' id='xitong'>${gs[0]}</span><input
									type='hidden' id='ricep' value='${gs[2]}'><input
									type='hidden' id='ppid' value='${gs[1]}'>
							</div>
							<div class='col-xs-2 pull-left'></div>
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>


<script>
	var codesId = "${codesId}";
	var basePath = "<%=basePath%>";
</script>
<script type="text/javascript" src="<%=basePath%>js/restaurant/investment/threeIndustry.js"></script>
	



</body>
</html>