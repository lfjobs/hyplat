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
    <title>数字地球商城-购物车</title>
    <link href="<%=basePath%>page/newMyapp/css/shop_jj.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>page/newMyapp/js/shop_jj.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/pcwfj/PC_goodsShoppingCart.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    	var basePath = '<%=basePath%>';
    </script>
</head>
<body>
    <div class="content">
        <!--搜索-->
        <div class="mil search_mil sc_search">
            <a href="#;" class="logo"><img src="<%=basePath%>page/newMyapp/images/logo.png"></a>
            <h1>购物车</h1>
            <div class="shop-search">
                <input type="text" placeholder="请输入您要查找的商品" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您要查找的商品'" onkeypress="if (event.keyCode == 13)selShoppingCartGoods(this);" value="${param.showParam}" class="selGoods">
                <button type="button" onclick="selShoppingCartGoods(this);">搜索</button>
            </div>
        </div>
        <h5 class="gradient"></h5>
        <c:if test="${map.goodsMap!=null}">
	        <div class="sc_cart_con">
	        	<form id="CartForm" method="post">
	        		<input type="hidden" name="showParam" value="payShoppingCart">
	            	<ul class="title">
	                	<li class="all">
	                    	<div class="checkbox">
	                        	<input type="checkbox" value="1" id="checkbox-top" name="check" />
	                        	<label for="checkbox-top"></label>
	                    	</div>
	                    	<p>全选</p>
	                	</li>
	                	<li class="blurb">商品信息</li>
	                	<li class="price">商品价格</li>
	                	<li class="number">数量</li>
	                	<li class="money">金额</li>
	                	<li class="operation">操作</li>
	            	</ul>
	            	<c:forEach items="${map.goodsMap}" var="goodsEntry">
	            		<div class="sc_mil">
	                		<h5 class="name">
	                    		<div class="checkbox">
	                        		<input type="checkbox" value="1" id="${goodsEntry.key}" name="check" class="all_check"/>
	                        		<label for="${goodsEntry.key}"></label>
	                    		</div>
	                    		${goodsEntry.key}	
	                		</h5>
	                		<ul class="mil_list">
	                			<c:forEach items="${goodsEntry.value}" var="goods" varStatus="status">
	                    			<li>
	                        			<div class="checkbox">
	                        				<input type="hidden" name="cartIds" class="cartIds">
	                            			<input type="checkbox" value="1" id="${goods[0]}" name="check" class="check"/>
	                            			<label for="${goods[0]}"></label>
	                        			</div>
	                        			<img src="<%=basePath%>${goods[3]}" alt="" class="shop" id="goodsShop">
	                        			<input type="hidden" class="ppID" value="${goods[1]}">
	                        			<div class="txt">
	                            			<h5>${goods[2]}</h5>
	                            			<p>规格：${goods[7]}</p>
	                        			</div>
	                        			<p class="money">&yen;<span class="goodsPrice">${goods[4]}</span></p>
	                        			<div class="f_l add_chose">
	                            			<button type="button" class="reduce">-</button>
	                            			<input type="text" value="${goods[6]}" class="text" readonly/>
	                            			<input type="hidden" name="counts" class="counts">
	                            			<button type="button" class="add">+</button>
	                        			</div>
	                        			<p class="total_price">&yen;<span></span></p>
	                        			<button type="button" class="delete">删除</button>
	                        			<c:forEach items="${map.giftList}" var="gift">
	                        				<c:if test="${gift[0]==goods[0]}">
												<!--赠品-->
	                        					<div class="clear"></div>
	                        					<div class="gift">
	                            					<div class="shop_img">
	                                					<img src="<%=basePath%>${gift[8]}" class="shop">
	                                					<input type="hidden" class="pptId" value="${gift[2]}">
	                                					<p>赠</p>
	                            					</div>
	                            					<div class="txt">
	                                					<h5>${gift[6]}</h5>
	                                					<p>规格：${gift[3]}</p>
	                            					</div>
	                            					<p class="money">&yen;<span class="giftPrice">${gift[7]}</span></p>
	                            					<p class="number">${gift[4]}</p>
	                        					</div>
	                        				</c:if>
	                        			</c:forEach>
	                        			<c:if test="${!status.last}">
	                       					<hr />
	                       				</c:if>
	                    			</li>
	                    		</c:forEach>
	              			</ul>
	            		</div>
	           		</c:forEach>
	           	</form>
	        </div>
	        <div id="fixed_box">
	        	<div class="Clearing">
	            	<ul class="">
	                	<li class="All"><!--<input type="checkbox" id="ch"><label for="ch"></label>-->
	                    	<div class="checkbox">
	                        	<input type="checkbox" value="1" id="checkbox" name="" />
	                        	<label for="checkbox"></label>
	                    	</div>
	                    		全选
	                	</li>
	                	<li class="selected">已选商品<span>0</span>件</li>
	                	<li class="Total_cost">合计费用：<span>&yen;0.00</span></li>
	                	<button onclick="payShoppingCart();">结算</button>
	            	</ul>
	        	</div>
	        </div>
        </c:if>
        <c:if test="${map.goodsMap==null}">
        	<ul class="shop-con">
        		<c:if test="${param.showParam!=null && param.showParam!=''}">
        			<div class="selNot" align="center">
                    	<img src="<%=basePath%>page/newMyapp/images/wu.png">
                        <p>没有您所搜索的商品</p>
                    </div>
        		</c:if>
        		<c:if test="${param.showParam==null || param.showParam==''}">
	                <div class="wu" align="center">
	                    <img src="<%=basePath%>images/WFJClient/Newjspim/emptycar.png">
	                </div>
                </c:if>
            </ul>
        </c:if>
        <!--热卖商品-->
        <div class="mil_shop_con hot_shop">
            <ul>
                <h3 class="title">热卖商品</h3>
                <!--JS拼接-->
            </ul>
        </div>
   		<div class="det-add_alert_">
        	<div class="det-add_alert">
            	<h2>确认删除该商品吗？</h2>
            	<div class="btn">
                	<button id="abolish">取消</button>
                	<button id="sure" style="background-color: #ff6600;">确认</button>
            	</div>
        	</div>
    	</div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
           <!--  <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>          
        </div>
    </div>
</body>
</html>