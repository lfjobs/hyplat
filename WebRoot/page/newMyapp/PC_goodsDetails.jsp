<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>数字地球商城详情页</title>
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/reset.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/pagination.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/swiper-3.4.2.min.css"  type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>page/newMyapp/js/jquery.pagination.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/ea/pcwfj/PC_goodsDetails.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/swiper-3.4.2.jquery.min.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var ppID = '${map.obj[1]}';
		var goodsId='${map.obj[4]}'
		var goodsname='${map.obj[0]}';
		var companyId='${map.obj[5]}';
		var type='${map.obj[6]}';
		var pptlength = '${fn:length(map.ppt)}';
		var colorlength = '${fn:length(map.color)}';
		var sizelength = '${fn:length(map.size)}';
		var size='${map.size}';
		var titleJudge = '${titleJudge}';
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
	</script>
</head>
<body>
    <div id="header">
        <ul>
        	<li class="logo">
            	<a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>page/newMyapp/images/wfj.png" alt="" class="log"></a>
        	</li>
        	<li class="scroll">
            	<img src="<%=basePath%>page/newMyapp/images/scroll.png" alt="">
        	</li>
        </ul>
    </div>
    <div class="content">
        <div class="ind_con_head">
            <ul>
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
                 <a href="#;" class='investment'><li>中联园招商</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <!-- <a href="#;" class='member'><li>会员中心</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=06" class='readExtensively'><li>中联园博览</li></a>
                <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
                <!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
                <!--<div class="right">
                    <input type="search">
                    <img src="new_images/ico-search2.png" alt="">
                </div>-->
            </ul>
        </div>
        <!--产品详情页内容-->
        <div class="detail_page">
            <h5 class="top_text">数字地球商城 > ${map.obj[0]} </h5>

            <div class="det_mil">
                <div class="left">
                    <!--单图-->
                   <!-- <img src="new_images/shop.png" alt="">-->
                    <!--多图-->
                    <a href="#;"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/shop_left.png" alt="" class="left_ swiper-button-prev2"></a>
                    <a href="#;"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/shop_right.png" alt="" class="right_ swiper-button-next2"></a>
                    <div class="swiper-container swiper-container-det">
                        <div class="swiper-wrapper">
                        	<div class="swiper-slide">
	                            <img src="<%=basePath%>${map.obj[3]}" onerror="this.src='<%=basePath%>images/ea/production/forum/reportAnError.png'" alt="">
	                        </div>
                        	<c:forEach items="${map.images }" var="s">
	                            <div class="swiper-slide">
	                                <img src="<%=basePath%>${s.imgurl}" onerror="this.src='<%=basePath%>images/ea/production/forum/reportAnError.png'" alt="">
	                            </div>
	                        </c:forEach>
                        </div>
                    </div>
                     <div class="swiper-pagination"></div>
					<script>
					 /*多图*/
					    var mySwiper2 = new Swiper('.swiper-container-det', {
					        autoplay: 3000,//可选选项，自动滑动
					          loop: true,
					          simulateTouch : false,
					        pagination : '.swiper-pagination',
					        paginationClickable :true,
					        prevButton:'.swiper-button-prev2',
					        nextButton:'.swiper-button-next2',
					    })
					</script>
                </div>
                <div class="right">
                	<form id="mainForm" method="post">
                    <h1 class="name">${map.obj[0]}</h1>
                    <div class="money">
                        <h1>&yen;<span class="mon">${map.obj[2]}</span></h1>
                        <div class="bottom">
                            <p class="freight">运费<span>免运费</span></p>
                            <p class="sales">月销量<span>${map.obj[7]}</span>笔</p>
                            <p class="sales">${map.city}</p>
                        </div>
                    </div>
                    <c:forEach items="${map.goodsStandard}" var="goodsStandard">
                    <div class="color">
                        <h4>${goodsStandard.key}
                        	<input type="hidden" class="attriname" />
                        </h4>
                        <ul class="xuan _color">
                        	<c:forEach items="${goodsStandard.value}" var="c">
                            	<li>${c}
                            	<input type="hidden" class="attrivalue" /></li>
                            </c:forEach>
                        </ul>
                    </div>
                    </c:forEach>
                    <div class="color">
                        <h4>数量</h4>
                        <div class="nub">
                            <button id="cut" type="button"></button>
                            <input type="text" value="1" readonly="readonly" name="count" id="num">
                            <button id="add" type="button"></button>
                        </div>
                    </div>
                    <c:if test="${fn:length(map.ppt)!=0}">
                    	<div class="color gift">
                        	<h4>赠品</h4>
                        	<div class="gift_con">
                            	<ul>
                            		<c:forEach items="${map.ppt}" var="p">
                                		<li class="list">
                                    		<i></i>
                                    		<img src="<%=basePath%>${p[3]}" class="gift">
                                    		<div class="text">
                                    			<input type="hidden" class="ptppidHide" value="${p[1]}" />
                    							<input type="hidden" name="ptppid" class="ptppids" />
                    							<input type="hidden" name="ptStandard" class="ptStandards" />
                                        		<p>${p[0]}</p>
                                        		<p class="giftPrice">&yen;${p[2]}</p>
                                    		</div>
                                    		<div class="ComboBox">
                                    			<c:forEach items="${map.giftMap}" var="giftStandards">
                                    				<c:if test="${giftStandards.key==p[1]}">
                                    					<c:forEach items="${giftStandards.value}" var="giftStandard">
                                        					<div class="gift_color">
                                            					<h5>${giftStandard.key}
                                            						<input type="hidden" class="giftAttriname" />
                                            					</h5>
                                            					<ul>
                                            						<c:forEach items="${giftStandard.value}" var="giftC">
                                                						<li>${giftC}
																			<input type="hidden" class="giftAttrivalue" />
																		</li>
                                                					</c:forEach>
                                            					</ul>
                                        					</div>
                                        				</c:forEach>
                                        			</c:if>
                                        		</c:forEach>
                                        		<div class="gift_color" id="numb">
                                            		<h5>数量
                                            			<input type="hidden" class="giftAttriname" />
                                            		</h5>
                                            		<ul>
                                                		<li>1
                                                			<input type="hidden" class="giftAttrivalue" />
                                                		</li>
                                            		</ul>
                                        		</div>
                                    		</div>
                                		</li>
                                	</c:forEach>
                           		</ul>
                        	</div>
                    	</div>
                    </c:if>
                    <div class="btn">
                        <button type="button" class="add_cart" onclick="addToShoppingCart();">加入购物车</button>
                        <button type="button" class="buy" onclick="buyNow();">立即购买</button>
                    </div>
                    <input type="hidden" id="giftGoods" />
                    <input type="hidden" name="ppk.ppID" value="${map.obj[1]}" id="goodsPpid"/>
                    <input type="hidden" name="standard" id="standard" />
                    <input type="hidden" name="giftMoney" id="giftMoney" />
                    </form>
                </div>
            </div>
            <!--热搜-->
            <div class="det_mil hot_recommend">
                <h2>热卖推荐</h2>
                <a href="#;"><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/shop_left.png" alt="" id="left" class="swiper-button-prev"></a>
                <a href="#;"><img src="<%=basePath%>page/newMyapp/images/newPCHomepage/shop_right.png" alt="" id="right" class="swiper-button-next"></a>
                <div class="swiper-container swiper-container-hot">
                    <div class="swiper-wrapper">
                        
                    </div>
                </div>
				<script>
				    /*热搜*/
				    var mySwiper = new Swiper('.swiper-container-hot', {
				        autoplay: false,//可选选项，自动滑动
				      /*  loop: true,*/
				      simulateTouch : false,
				        slidesPerView : 4,
				        slidesPerGroup : 4,
				        prevButton:'.swiper-button-prev',
				        nextButton:'.swiper-button-next',
				    })
				    /*商品*/
				</script>
            </div>
            <!--商品详情-->
            <div class="shop-details">
                <ul class="top">
                    <li class="active" id="det" onclick="particulars();">商品详情</li>
                    <li id="sale" onclick="afterSales();">售后服务</li>
                    <li id="eval" onclick="comment();">商品评价</li>
                </ul>
                <ul class="shop-con">
                    <!-- js拼接 -->
                </ul>
            </div>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p>  -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
</body>
</html>