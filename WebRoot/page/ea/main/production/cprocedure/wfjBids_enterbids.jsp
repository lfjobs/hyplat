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

<html style="font-size: 62.5%;background: #fff;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />

<title>招标详情</title>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/tan.css" />
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/wfjBids_enterbids.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
</script>


</head>
<body>
	<div class="top-sp">
		<ul>
			<li class="arrar">
				<div>
					<img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
				</div>
			</li>
			<li><h5 class="shangpin">投标</h5></li>
		</ul>
	</div>
	<form id="form" name="form" method="post">
		<div class="main_auto">
			<div class="tou_head">
				<div class="tou_head_top">
					<div class="pull-left text-center" style="width: 100%;">
						<img style="width: 10rem;height: 6rem;"
							src="<%=basePath%>${good[2]}" alt="" />
					</div>
					<div class="text-center pull-left" style="width: 100%;font-size:2rem;margin-top:1rem;">${good[0]}</div>
				</div>
				<div class="yusuan text-center">预算￥${good[1]}</div>
			</div>
			<c:forEach items="${bidlist}" var="item">
				<div class="biao_subproject_lis">
					<div class="subproject_lis_left pull-left">
						<img class="pullImg" src="<%=basePath%>${item[2]}" alt="" />
					</div>
					<div class="subproject_lis_right pull-right">
						<p class="xqing">${item[1]}</p>
						<div class="bjia" style="border-bottom: 1px dashed #e3e3e3;">
							<span class="pull-left">&yen;<b class="price">${item[4]}</b>
							</span>
						</div>

					</div>
				</div>
			</c:forEach>
			<div class="add_subproject add_kuang">
				<div class="add_subproject_main">
					<div class="main_left pull-left text-center">
						<img src="<%=basePath%>images/ea/bids/A30E.tmp.jpg" />
					</div>
					<div class="main_right pull-left">
						<h4>添加竞标产品</h4>
					</div>
				</div>
			</div>
			<div class="add_kuang">
				<textarea class="pullImg" id="" cols="30" rows="10"
					name="bidsinfo.bidremark" placeholder="填写竞标备注..."></textarea>
			</div>
			<div class="but_tijiao text-center">提交保存</div>
			<input type="hidden" name="mainpid" value="${mainpid}" />
			<input type="hidden" name="ppid" value="${ppid}" id="pp" /> 
			<input type="hidden" name="bidsinfo.goodsID" value="${good[3]}" /> 
			<input type="hidden" name="bidsinfo.cashierBillsID" value="${good[4]}" /> 
			<input type="submit" name="submit" style="display:none;" />
	</form>
	</div>
	
	<iframe name="hidden" width="0" height="0"></iframe>
	<div id="bg"></div>
<!-- 提示框 -->
	<div class="tan_kuang tanshow">
		<img src="<%=basePath%>/images/ea/bids/img1_03.png" />
		<div class="tan_kuang_text_top">
			<h3>对不起您还没有发布产品</h3>
			<p>
				发布产品后<br/>即可进行投标
				
			</p>
		</div>
		<div class="tan_kuang_text_bot">
			<p>立即发布产品</p>
		</div>
	</div>

	<script>
	var cashierBillsID = "${inviteBids.cashierBillsID}";
	var goodsID = "${bidsinfo.goodsID}";
	var mainpid = "${mainpid}";
	var ppids = "${ppid}";
		$(function() {
			$(".main_auto").css("height",
					$(window).height() - $(".top-sp").outerHeight() + "px");
			$(".subproject_lis_right").css("height",
					$(".subproject_lis_left").height() + "px");
			$(".bjia").css("bottom", $(".buy").height() + "px");
			$(".add_kuang").css("height",
					$(".biao_subproject_lis").height() + "px");

		});

		
	</script>
</body>
</html>