<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/bootstrap.css" />
<script src="<%=basePath%>js/bootstrap.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/promotionmix.css" type="text/css"></link>    
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/promotion.js"></script>

<title>促销产品搭配</title>
</head>

<body>
	<div class="wfj11_013" style="overflow:auto;">
    	<div class="wfj11_013_top">
        	<ul id="tops">
            	<li><a href="javascript:history.go(-1)" target="_self"><img src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_return_02.png" /></a></li>
            	<li>促销产品搭配</li>
            	<!-- <li><a href="javascript:;"><img src="../Images/top_more_02.png" /></a></li> -->
            </ul>
        </div>
        <div class="wfj11_013_hidden">
        	<div class="wfj11_013_auto" id="wfj11_013_auto">
                <div class="wfj11_013_top_img">
                    <img src="<%=basePath %>images/WFJClient/Newjspim/shop-banner.jpg" />
                </div>              
                <div class="shop-mach_grd shop-mach_grd1">
                    <div class="shop_img">
                        <img src="<%=basePath%>${prolist[0][3]}">
                    </div>
                    <div class="shop_txt">
                        <p>${prolist[0][0] }</p>
                        <input type="hidden" value="${prolist[0][1]}"/>
                        <input type="hidden" value="${prolist[0][5]}"/>
                        <c:if test="${prolist[0][8]!=0 }">
                        <button>
                        <span>选择商品属性</span><img src="<%=basePath %>/images/WFJClient/Newjspim/xiaotu_14.jpg"> 
                        </button></c:if>
                    </div>
                    <div class="shop_price">
                        <p>￥<span>${prolist[0][2] }</span></p>
                        <p>${count }</p>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <c:forEach items="${prolist }" var="entity" begin="1">
                <div class="shop-mach_grd">
                    <div class="shop_img2">
                        <div class="match_img2">
                            <div class="gou"></div>
                        </div>
                        <div class="match_img"><img src="<%=basePath%>${entity[3]}"></div>
                    </div>
                    <div class="shop_txt">
                        <p>${entity[0] }</p>
                        <input type="hidden" value="${entity[1] }"/>
                        <input type="hidden" value="${entity[5] }"/>
                        <c:if test="${entity[8]!=0}">
                        <button>                   
                     	<span>选择商品属性</span><img src="<%=basePath %>/images/WFJClient/Newjspim/xiaotu_14.jpg"> 
                     	</button></c:if>
                    </div>
                    <div class="shop_price">
<%--                         <p>￥<span>${entity[2] }</span></p> --%>
<!--                         <p>x1</p> -->
							<p><img src="<%=basePath %>/images/WFJClient/Newjspim/cuxiao.png" style="width: 60%;"/></p>
                    </div>
                    <div class="clearfix"></div>
                </div></c:forEach>
            </div>
        </div>
        
    	<!--中联园区底部-->
        <div class="wfj11_013_bottoms">
            <ul id="kleft" class="kleft">
            </ul>
            <ul id="buy2" class="buy2">
                <li>
                    <p>￥<span><fmt:formatNumber value="${prolist[0][2]*count }"/></span></p>               
                </li>
                <li><div id="buynow" style="background:#FF6507;">立即购买</div></li>
            </ul>
        </div>
    	<!--中联园区底部 end-->
	</div>
    
    <div class="box box2">
      	 <div class="cover cover2"></div>
         <div class="choose_size choose_size2">
              <div class="summary summary2">
                    <div class="img">
                        <img src="" />
                    </div>
                    <div class="main">
                        <div class="pricer">
                            <span></span>
                        </div>
                        <div class="stock">
                            <p><span style="color: #AEAEAE;">还未选</span>
                            <span id="color">颜色分类</span>
                            <span id="guige">规格分类</span>                         
                        </div>
                    </div>
                    <button class="sback">
                        <img src="<%=basePath %>images/WFJClient/Newjspim/x.png" />
                    </button>
                </div>
                
               <div class="choose_con">
                        <div class="con_control con_control2">
                            <ul>
                                <li id="daxiao">
                                    <h2>颜色分类</h2>
                                    <div class="items color co">                                                                   
                                    </div>
                                </li>
                                <li id="daxiao">
                                    <h2>规格分类</h2>
                                    <div class="items color guige">                                                                   
                                    </div>
                                </li>                      
                            </ul>
                        </div>
                </div>
                <div class="bottom_button2">
                    <button>确定</button>
                </div>
          </div>
     </div>
     <form action="<%=basePath%>ea/wfjshop/ea_gm.jspa?" name="orderForm" method="post" style="display:none;">
     	<input type="submit" style="display:none;" name="submit"/>
     	<input type="hidden" value="" id="ptppid" name="ptppid"/>
     	<input type="hidden" value="" id="count" name="count"/>
     	<input type="hidden" value="" id="ppid" name="ppid"/>
     	<input type="hidden" value="" id="standard" name="standard"/>
     	<input type="hidden" value="" id="ptstandard" name="ptstandard"/>
     </form>  
    <script type="text/javascript">
    var basePath='<%=basePath%>';
    var count="${count}";
    var ptstandard=""; 
	</script>
</body>
</html>
















