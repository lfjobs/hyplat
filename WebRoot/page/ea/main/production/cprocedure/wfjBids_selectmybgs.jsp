<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html style="font-size: 62.5%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />

<title>招标详情</title>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/wfjBids_selectmybgs.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script src="<%=basePath%>/js/toucher.js"></script>


<script type="text/javascript">

var basePath="<%=basePath%>";

var goodsID = "${bidsinfo.goodsID}";
var cashierBillsID = "${inviteBids.cashierBillsID}";
var mainpid="${mainpid}";
var ppid = "${ppid}";
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};


</script>


</head>
<body>
	<div class="top-sp">
		<ul>
			<li class="arrar">
				<div>
					<img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
				</div></li>
			<li><h5 class="shangpin">投标</h5>
			</li>
		</ul>
	</div>
	<div class="main_auto">
		<div class="tou_banner">
			           <c:if test="${gm.photoPath==null}">
							<img class="pullImg" src="<%=basePath%>images/ea/bids/fw_qc.png">
						</c:if>
						<c:if test="${gm.photoPath!=null}">
							<img class="pullImg" src="<%=basePath%>${gm.photoPath}">
						</c:if>

		</div>
		<ul class="tj_con">
			<c:forEach items="${pageForm.list}" var="item" varStatus="status">
				<c:if test="${status.index+1 eq fn:length(pageForm.list)}">
					<li class="last" style="width:100%;">
				</c:if>
				<c:if test="${status.index+1 ne fn:length(pageForm.list)}">
					<li style="width:100%;">
				</c:if>
				<div class="tj_img">
					<img src="<%=basePath%>${item[2]}" />
				</div>
				<div class="toubiao2_main_right">
					<div class="tj_text">
						<h3>${item[1]}</h3>
						<ul>
							<li class="price">￥${item[4]}</li>
						</ul>
					</div>
					<div class="img_xuan">
					    <input type="hidden" value="${item[0]}" class="ppid">
						<img class="no" src="<%=basePath%>images/ea/bids/chan_03_03.png"
							alt="" /> <img class="yes dN"
							src="<%=basePath%>images/ea/bids/chan_07.png" alt="" />
					</div>
				</div>
				</li>
			</c:forEach>

		</ul>
	</div>
	<div class="footer" style="background: #454545;">
		<div class="footer_left">
			选择了<span>0</span>个竞标产品
		</div>
		<div class="footer_right">选择确认</div>
	</div>
     
      <form name="form" id="form" method="post">
        <input type="submit" style="display:none;" name="submit"/>
        <input type="hidden"  name="ppid" id="ppids"/>
        <input type="hidden"  name="bidsinfo.goodsID" value="${bidsinfo.goodsID}"/>
        <input type="hidden"  name="inviteBids.cashierBillsID" value="${inviteBids.cashierBillsID}"/>
        <input type="hidden"  name="mainpid" value="${mainpid}"/>
     
     </form>
	<script>
		$(function() {
			$(".main_auto").css(
					"height",
					$(window).height() - $(".top-sp").outerHeight()
							- $(".footer").outerHeight() + "px").css(
					"background", "#ECECEC");
			$(".tj_con .tj_img").css({
				"height" : $(".tj_text").height() + "px"
			});
		$(".tou_banner").css("height", $(window).height() * 0.4 + "px");
		$(".img_xuan").css("height", $(".tj_text").height() + "px");
		$(".tj_con").css("margin-top", "1rem");
        $(".tj_img").css({"height":"10rem"})
        $(".tj_text").css({"height":$(".tj_img").height()+"px"});
        $(".toubiao2_main_right").css({"height":$(".tj_img").height()+"px"});
        $(".img_xuan").css({"height":$(".toubiao2_main_right").height()+"px"});
			
		})
	</script>
</body>
</html>