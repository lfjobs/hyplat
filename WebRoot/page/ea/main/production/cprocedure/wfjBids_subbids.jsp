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
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>招标详情</title>



<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/tan.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/wfjBids_subbids.js"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script src="<%=basePath%>/js/toucher.js"></script>





</head>
<body>
<div class="top-sp">
    <ul>
        <li class="arrar">
            <div>
                <img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
            </div>
        </li>
        <li><h5 class="shangpin">招标商品</h5></li>
    </ul>
</div>
<div class="main_auto">


    <div class="main_img">
        <div id="carousel-example-generic" class="carousel slide both" style="width: 100%; margin:0 auto;">
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
						<c:if test="${good[3]==null}">
							<img class="pullImg" src="<%=basePath%>images/ea/bids/fw_qc.png">
						</c:if>
						<c:if test="${good[3]!=null}">
							<img class="pullImg" src="<%=basePath%>${good[3]}">
						</c:if>

					</div>
					<c:forEach items="${attrlist}" var="item">
						<div class="item">
							<img class="pullImg" src="<%=basePath%>${item.imgurl}">
						</div>
					</c:forEach>

            </div>
            <div class="main_img_but">
                <a href="#carousel-example-generic" id="carleft" class="bannerc carousel-control left" data-slide="prev"></a>
                <a href="#carousel-example-generic" id="carright" class="bannerc carousel-control right" data-slide="next"></a>
            </div>
        </div>
    </div>
    <div class="header1">
        <h4 class="biao_header">${good[0]}</h4>
        <div class="zhaobiao"><span class="zhao">预算</span><span class="price">${good[1]}元</span></div>
        <span class="rele">发布时间 <span>${fn:substring(mainobj[10],0,19)}</span></span>
    </div>
    <ul class="header2">
        <li>招标状态</li>
       	<li class="days"><input type="hidden" value="${mainobj[6]}" /></li>
    </ul>
    <div class="biao_reta">
        <h3>招标详情</h3>
        <div class="biao_reta_main">
        ${mainobj[9]}
        </div>
    </div>
    <div class="biao_parameter">
        <h3>型号参数</h3>
        <div class="biao_reta_main"><span>${good[2]}</span>

        </div>
    </div>
    <div class="biao_publisher">
        <div class="biao_publisher_left pull-left">${mainobj[8]}(发布者)</div>
        <div class="pull-right biao_publisher_right">${mainobj[11]}</div>
    </div>
    <div class="biao_subproject">
        <div class="biao_subproject_head">竞标项目</div>
    </div>
    
    <c:forEach items="${sublist}" var="item">
    <div class="biao_subproject_lis">
        <div class="subproject_lis_left pull-left">
            <img class="pullImg" src="<%=basePath%>${item[0]}" alt=""/>
        </div>
        <div class="subproject_lis_right pull-right">
            <p class="xqing">${item[1]}</p>
            <div class="bjia" style="border-bottom: 1px dashed #e3e3e3;">
                <span class="pull-left">&yen;<b class="price">${item[2]}</b></span>
            </div>
            <div class="buy">
            <input type="hidden" value="${item[3]}" class="pid"/>
            <input type="hidden" value="${item[4]}" class="companyid"/>
            <input type="hidden" value="${item[6]}" class="ccompanyid"/>
            <input type="hidden" value="${item[7]}" class="tgoodsID"/>
                <div>
                    <span>立即购买</span>
                    <span class="glyphicon glyphicon-play"></span>
                </div>
            </div>
            <c:if test='${item[5]=="01"}'>
              <img class="bingo" src="<%=basePath%>images/ea/bids/bingo_03.png" alt=""/>
            </c:if>
          
        </div>
    </div>
</c:forEach>
  

</div>
<div class="footer">
    <ul>
        <li>
            <div>发布招标</div>
        </li>
        <li>
            <div class="toubiao">投标</div>
        </li>
    </ul>
</div>

<div id="bg"></div>
<!-- 提示框 -->
	<div class="tan_kuang tanshow">
		<img src="<%=basePath%>/images/ea/bids/img2_03.png" />
		<div class="tan_kuang_text_top">
			<h3>对不起您还没有权限投标</h3>
			<p>
				您可以升级为公司会员</br> 将有资格进行投标竞标
			</p>
		</div>
		<div class="tan_kuang_text_bot">
			<p>马上升级会员</p>
		</div>
	</div>
<script type="text/javascript">

var basePath="<%=basePath%>";
var goodsID = "${bidsinfo.goodsID}";
var cashierBillsID="${inviteBids.cashierBillsID}";
var ppid = "${mainobj[1]}";


    $(function(){
        $(".main_auto").css("height",$(window).height()-$(".top-sp").outerHeight()-$(".footer").outerHeight()+"px");
        $(".subproject_lis_right").css("height",$(".subproject_lis_left").height()+"px")
        $(".bjia").css("bottom",$(".buy").height()+"px");
        $(".carousel-inner > .item").css({"height":$(window).height()*0.45+"px"});
 
    });
    
</script>
<script type="text/javascript">
    var myTouch = util.toucher(document.getElementById('carousel-example-generic'));
    myTouch.on('swipeLeft',function(e){
        $('#carright').click();
    }).on('swipeRight',function(e){
        $('#carleft').click();
    });
</script>
</body>
</html>