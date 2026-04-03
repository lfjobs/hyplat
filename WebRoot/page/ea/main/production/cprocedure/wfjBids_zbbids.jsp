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
var ppid = "${ppid}";
var cashierBillsID = "${inviteBids.cashierBillsID}";
</script>


</head>
<body>
	<div class="top-sp">
		<ul>
			<a href="javascript:history.go(-1)">
				<li class="arrar">
					<div>
						<img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
					</div></li>
			</a>
			<li><h5 class="shangpin">招标项目</h5>
			</li>
		</ul>
	</div>
	<div class="main_auto">


		<div class="main_img">
			<div id="carousel-example-generic" class="carousel slide both"
				style="width: 100%; margin:0 auto;">
				<ol class="carousel-indicators">
					<c:if test='${fn:length(attrlist)!=0}'>
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
					</c:if>
					<c:forEach items="${attrlist}" varStatus="status">
						<li data-target="#carousel-example-generic"
							data-slide-to="${status.index+1}"></li>
					</c:forEach>

				</ol>
				<!--图片-->

				<div class="carousel-inner">

					<div class="item active">
						<c:if test="${mainobj[3]==null}">
							<img class="pullImg" src="<%=basePath%>images/ea/bids/fw_qc.png">
						</c:if>
						<c:if test="${mainobj[3]!=null}">
							<img class="pullImg" src="<%=basePath%>${mainobj[3]}">
						</c:if>

					</div>
					<c:forEach items="${attrlist}" var="item">
						<div class="item">
							<img class="pullImg" src="<%=basePath%>${item.imgurl}">
						</div>
					</c:forEach>
				</div>
				<div class="main_img_but">
					<a href="#carousel-example-generic" id="carleft"
						class="bannerc carousel-control left" data-slide="prev"></a> <a
						href="#carousel-example-generic" id="carright"
						class="bannerc carousel-control right" data-slide="next"></a>
				</div>
			</div>
		</div>
		<div class="header1">
			<h4 class="biao_header">${mainobj[0]}</h4>
			<div class="zhaobiao">
				<span class="zhao">预算</span><span class="price">${mainobj[5]}元</span>
			</div>
			<span class="rele">发布时间 <span>${fn:substring(mainobj[10],0,19)}</span> </span>
		</div>
		<ul class="header2">
			<li>招标状态</li>
			<li class="days"><input type="hidden" value="${mainobj[6]}" />
			</li>
		</ul>
		<div class="biao_reta">
			<h3>招标详情</h3>
			<div class="biao_reta_main">${mainobj[9]}</div>
		</div>
		<div class="biao_publisher">
			<div class="biao_publisher_left pull-left">${mainobj[8]}(发布者)</div>
			<div class="pull-right biao_publisher_right">${mainobj[11]}</div>
		</div>
		<div class="biao_subproject">
			<div class="biao_subproject_head">所有子项目</div>
		</div>

		<c:forEach items="${sublist}" var="item">
			<div class="biao_subproject_lis">
				<div class="subproject_lis_left pull-left">
					<img class="pullImg" src="<%=basePath%>${item[5]}" alt="" />
				</div>
				<div class="subproject_lis_right pull-right"
					onclick="viewDetail('${item[1]}')">
					<p class="xqing">${item[3]}</p>
					<div class="bjia">
						<span class="pull-left">&yen;<b class="price">${item[4]}</b>
						</span> <span class="pull-right look_xqing">欢迎投标</span>
					</div>
				</div>
			</div>
		</c:forEach>


	</div>
	<script type="text/javascript">
		$(function() {
			$(".main_auto").css(
					"height",
					$(window).height() - $(".top-sp").outerHeight()
							- $(".footer").outerHeight() + "px");

			$(".subproject_lis_right").css("height",
					$(".subproject_lis_left").height() + "px")
					
					
			 $(".carousel-inner > .item").css({"height":$(window).height()*0.45+"px"});

//			$(".arrar").click(
//					function() {
//						document.location.href = basePath
//								+ "ea/purchasebids/ea_findbidIndexList.jspa";
//					});
			computeDays();

		})
          //计算剩余时间
		function computeDays() {
            if($(".days").find("input").val()!=""&&$(".days").find("input").val()!=undefined){
			var str = $(".days").find("input").val().split(" ");

			var ar_ds = str[0].split('-');
			var ar_ts = str[1].split(':');
			var ds = new Date(ar_ds[0], ar_ds[1] - 1, ar_ds[2], ar_ts[0],
					ar_ts[1], ar_ts[2]);

			var cur = new Date();
			var result = ds.getTime()-cur.getTime();

			//计算出相差天数  
			var days = Math.floor(result / (24 * 3600 * 1000));

			//计算出小时数  

			var leave1 = result % (24 * 3600 * 1000); //计算天数后剩余的毫秒数  
			var hours = Math.floor(leave1 / (3600 * 1000));
			//计算相差分钟数  
			var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数  
			var minutes = Math.floor(leave2 / (60 * 1000));
			var day = minutes < 0 ? "已截止" : "剩";
			if (days > 0) {
				day += days + "天";
			}
			if (hours > 0) {
				day += hours + "小时";
			}
			if (minutes > 0) {
				day += minutes + "分钟";
			}
			   $(".days").text(day);
			}else{
		      $(".days").text("不限");
		
	        }
		}
       //点击查看子项目产品
		function viewDetail(goodsID) {

			document.location.href = basePath
					+ "ea/purchasebids/ea_viewSubProduct.jspa?ppid=" + ppid
					+ "&bidsinfo.goodsID=" + goodsID
					+ "&inviteBids.cashierBillsID=" + cashierBillsID;

		}
	</script>
	<script type="text/javascript">
		var myTouch = util.toucher(document
				.getElementById('carousel-example-generic'));
		myTouch.on('swipeLeft', function(e) {
			$('#carright').click();
		}).on('swipeRight', function(e) {
			$('#carleft').click();
		});
	</script>
</body>
</html>