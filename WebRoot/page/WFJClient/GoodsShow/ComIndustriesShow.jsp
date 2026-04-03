<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>公司微分金首页面</title>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body>
		<iframe id="indexTop" align="center" width="100%" height="46px"
			src="<%=basePath%>page/WFJClient/Template/Top/Label.jsp"
			frameborder="no" border="0" marginwidth="0" marginheight="0"
			scrolling="no"></iframe>
		<ul id="menuMore" class="menuMore">
			<li>
				<a href="javascript:;"><img alt="" title=""src="<%=basePath%>images/WFJClient/message.png" /> <span>消息</span></a>
			</li>
			<li>
				<a href="javascript:;"><img alt="" title=""src="<%=basePath%>images/WFJClient/homeMore.png" /> <span>首页</span></a>
			</li>
		</ul>
		<div class="con">
			<div class="so fl">
				<div class="search">
					<input type="text" value="" />
					<a href="javascript:;"><img alt="" title=""src="<%=basePath%>images/WFJClient/sos.png" /> </a>
				</div>
			</div>
			<div class="clear"></div>
			<div class="industriedAd fl">
				<div class="industriedAdLeft fl">
					广告招租
				</div>
				<div class="industriedAdRight fr">
					<a href="<%=basePath %>/ea/wfjshop/ea_searchShops.jspa?companyId=<%=request.getParameter("companyId")%>"><p>
							搜店铺
						</p>
					</a>
					<a href="javascript:;"><p>
							搜二维码
						</p>
					</a>
				</div>
			</div>
			<div class="clear"></div>
			<div class="industried">
				<div class="header fl">
					<a href="javascript:;"><span>热门市场</span>
					</a>
					<a href="javascript:;"><d class="more">更多...</d>
					</a>
				</div>
				<div class="clear"></div>
				<div class="industriedAdmissions industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=招生">招生</a>
				</div>
				<div class="industriedAccommodation industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=住宿">住宿</a>
				</div>
				<div class="industriedSupermarket industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=超市">超市</a>
				</div>
				<div class="industriedSales industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=售楼">售楼</a>
				</div>
				<div class="industriedCar industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=汽车">汽车</a>
				</div>
				<div class="clear"></div>
				<div class="industriedFood industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=餐饮">餐饮</a>
				</div>
				<div class="industriedGarden industriedText fl">
					<a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&shopCategory=茶园">茶园</a>
				</div>
				<div class="clear"></div>
				<div class="header fl">
					<a href="javascript:;"><span>秒杀精品</span>
					</a>
					<a href="javascript:;"><d class="more">更多...</d>
					</a>
				</div>
				<div class="secondsKill industriedText fl">
					<a href="javascript:;">最美香辣蟹3只10元起</a>
				</div>

			</div>
		</div>
		<script type="text/javascript"
			src="<%=basePath%>/js/WFJClient/topMore.js"></script>
		<script type="text/javascript">
	$('#indexTop').load(function() {
		var doc = document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "微分金";
		doc.getElementById("return").onclick = function() {
			window.history.go(0);
		}
	});
</script>
	</body>
</html>
