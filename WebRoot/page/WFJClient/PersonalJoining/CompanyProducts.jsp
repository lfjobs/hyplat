<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>联营商城</title>
   <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companyWeb.css" />
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/bootstrap.css">
   <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/bootstrap.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/toucher.js"></script>
   <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/companyProducts.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/jquery.fly.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
</head>
<body>
<header>
   <ul>
       <li style="width: 10%;" class="backt">
           <%--<%=basePath%>ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId }--%>
           <a href="javascript:history.go(-1)"><img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/left_jt.png"></a>
       </li>
       <li style="width: 70%;">联营商城</li>
       <li class="cartList" style="width: 20%;"><a href="<%=basePath %>/ea/wfjshop/ea_getcity.jspa?pos=${pos}&companyId=${companyId}" >
           <div class="cart">
               <img src="<%=basePath%>/images/WFJClient/Newjspim/shopcar.png" id="search"/>
               <div class="num0" style="display: none;"><span id="span">0</span></div>
           </div>
       </a></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">

<div class="content">
    <div id="myCarousel" class="carousel slide">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators carousel-indicators3">
        	<c:forEach items="${adlist }" var="entity" varStatus="status">
        		<c:if test="${status.index eq 0 }">
            		<li data-target="#myCarousel" data-slide-to="${status.index }" class="active"></li>
            	</c:if>
            	<c:if test="${status.index ne 0 }">
            	<li data-target="#myCarousel" data-slide-to="${status.index }"></li>
            	</c:if>
            </c:forEach>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
        	<c:forEach items="${adlist }" var="entity" varStatus="status">
        		<c:if test="${status.index eq 0 }">
        			<div class="item active">
        			<input type="hidden" value="${entity[2] }"/>
        			<img src="<%=basePath %>${entity[3]}" alt="First slide" style="width: 100%;"></div>
        		</c:if>
        		<c:if test="${status.index ne 0 }">
        			<div class="item">
        			<input type="hidden" value="${entity[2] }"/>
        			<img src="<%=basePath %>${entity[3]}" alt="First slide" style="width: 100%;"></div>
        		</c:if>	
        	</c:forEach>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="carousel-control left left2" href="#myCarousel" id="carleft" data-slide="prev"></a>
        <a class="carousel-control right right2" href="#myCarousel" id="carright" data-slide="next"></a>
    </div>


    <div>
        <div class="search_box">
            <input type="text" class="search">
            <span class="search_overly"><i class="com_search"></i>搜索</span>
        </div>
    </div>
   <div class="china_nav js-shop">
       <ul>  
       <c:forEach items="${beans }" var="entity" varStatus="status">
       		<c:if test="${entity[5] == '产品分类'}"> 	    
            <li>
            <input type="hidden" value="${entity[0] }"/>
           		<div> 
           			<div>${entity[1] }</div>
           		</div>
           </li>           
           </c:if> 
           <c:if test="${status.index+1 % 2==0 }"> 
           <hr style="border-top: 1px solid #ddd;clear: both;margin: 0;"></c:if>
           </c:forEach> 
           <div class="clearfix"></div>
       </ul>
   </div>

    <div class="jo-shop1">
        <div class="search_con01">
            <div class="nav">
                <div class="screening">
                    <ul>
                        <li class="Regional" id="pop" onclick="Sorts(this.id)">销量优先</li>
                        <li class="Brand" id="plow" onclick="Sorts(this.id)">价格最低</li>
                        <li class="Sort">类型</li>
                    </ul>
                </div>
                <div class="style">
                    <img id="img1" style="" src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/style_01.png" />
                    <img id="img2" style="display:none;" src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/style_02.png" />
                </div>
            </div>
            <!-- style1 -->
            <article id="context1">
                <ul class="style1_con">                
                </ul>
            </article>
            <!-- style2 -->
            <article id="context2" style="display:none;">
                <ul class="style2_con">             
                </ul>
            </article>           
        </div>
        <div class="Sort-eject Sort-height">
			<ul class="Sort-Sort" id="Sort-Sort">
				<li id="smart" onclick="Sorts(this.id)">综合搜索</li>				
				<li id="newest" onclick="Sorts(this.id)">最新发布</li>
				<li id="praise" onclick="Sorts(this.id)">好评优先</li>
				<li id="ptop" onclick="Sorts(this.id)">价格最高</li>
			</ul>
		</div>
    </div>
</div>
</div>
<div class="alert_"></div>
<div class="alert_new_"></div>
<div class="box2">
    <div class="choose_size choose_size2">
        <div class="summary summary2">
            <div class="img">
                <img src="">
            </div>
            <div class="main">
                <div class="pricer">
                    ￥869.00
                </div>
                <div class="stock">
                    <p><span style="color: #AEAEAE;">还未选</span><span id="size"></span>&nbsp;&nbsp;<span id="set-meal"></span></p>
                </div>
            </div>
            <button class="sback">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/companyHomepage/x.png">
            </button>
        </div>

        <div class="choose_con">
            <div class="con_control con_control2">
                <ul>
                    <li id="daxiao" style="display:none;">
                        <h2></h2>
                        <div class="items size">
                            <label></label>

                        </div>
                    </li>
                    <li id="yanse">
                        <h2>套餐类别</h2>
                        <div class="items set-meal">
                            <label>套餐一</label>
                            <label>套餐二</label>
                        </div>
                    </li>
                </ul>
                <div class="number">
                    <button type="button" class="decrease">-</button>
                    <h5>1</h5>

                    <button type="button" class="increase">+</button>
                    <div class="clear"></div>
                </div>
            </div>

        </div>

        <div class="bottom_button2" style="display: block;">
            <button class="jrconfirm">确定</button>

        </div>
    </div>


<script type="text/javascript">
  var t=0;
  var basePath='<%=basePath%>';
  var region='';
  var pagenumber=0;
  var pagecount=0;
  var ccompanyId='${ccompanyId}';
  var companyId='${companyId}';
  var search="smart";
  var flag='';
  var ppIds='categories';
  var pos = "${pos}";
  var stardard ="";
  var myTouch = util.toucher(document.getElementById('myCarousel'));
	myTouch.on('swipeLeft',function(e){
		document.getElementById("carright").click();	
	}).on('swipeRight',function(e){
		document.getElementById("carleft").click();
	});





</script>
</body>
</html>