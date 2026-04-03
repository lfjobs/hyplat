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
    <title>数字地球商城</title>
    <link rel="stylesheet" href="<%=basePath %>page/newMyapp/lunbo/focusStyle.css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/lunbo/lunbo.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/PC_searchResult.js" type="text/javascript"></script>
    
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var search;
		var tradecode;
		var ppid = "";
		var goodsid = "";
		var companyid = "";
		var goodsname = '${ppk.goodsName}';
		var bl = ${map.b};
	</script>
</head>
<body>

    <div class="content">

        <!--搜索-->
        <div class="mil search_mil">
            <a href="#;" class="logo"><img src="<%=basePath %>/images/contacts/loadsite/logo.png"></a>
            <div class="shop-search">
                <input type="text" placeholder="请输入您要查找的商品" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您要查找的商品'" onkeypress="if (event.keyCode == 13)seek(this);" value="${ppk.goodsName}" class="seek">
                <button onclick="seek(this)">搜索</button>
            </div>
            <div class="shop_cart">
                <div class="cart">
                    <img src="<%=basePath %>images/WFJClient/PersonalJoining/cart.png" alt="">
                    <p>${map.goodNum }</p>
                </div>
                <a href="#;"><button>我的购物车 ></button></a>
                <ul class="shop_cart_list">
                    <i></i>
                    <c:choose>
				       <c:when test="${map.b}">
		                  	<c:choose>
						       <c:when test="${map.beanList==null}">
				                  	<p>购物车空空如也～～～</p>
						       </c:when>
						       <c:otherwise>  
						       		<c:forEach var="b" items="${map.beanList }">
						       			<li>
					                        <a href="#;">
					                        	<input class="ppid" type="hidden" value="${b[0] }">
					                            <img src="<%=basePath%>${b[2]}" alt="">
					                            <div class="text">
					                                <h5>${b[1] }</h5>
					                                <p><span class="qian">&yen;${b[3] }</span><span class="nub">×${b[4] }</span></p>
					                            </div>
					                        </a>
					                    </li>
						       		</c:forEach>
						       		<a href="javascript:void(0);" class="more">查看更多</a>
							   </c:otherwise>
							</c:choose>
				       </c:when>
					</c:choose>
                </ul>
            </div>
        </div>
        <div class="search_top">
            <h5></h5>
            <h2><a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa">首页></a><a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=05">数字地球商城></a><span>${ppk.goodsName}</span></h2>
        </div>

        <!--数字地球商城-->
        <div class="">
            <div class="mil_shop_con_">
                <div class="mil_shop_con">
                    <div class="shop-sort">
                        <button class="active" data-name="">综合排序</button>
                        <button data-name="pop">销量</button>
                        <button data-name="ptop">价格<img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-down.png"></button>
                        <button data-name="plow">价格<img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-up.png"></button>

                    </div>
                    <ul class="shop-con">
                        <!-- js拼接 -->
                    </ul>
                    <div class="shop_page">
                        <!-- js拼接 -->
                    </div>
                </div>
            </div>
        </div>
        <!--热卖商品-->
        <div class="mil_shop_con hot_shop">
            <ul>
               	<!-- js拼接 -->
            </ul>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<script>
    $(document).ready(function(){
        /*购物车*/
        $(".search_mil .shop_cart").mouseover(function(){
        	if(bl){
        		$(this).addClass("on_cart");
        	}
        });
        $(".search_mil .shop_cart").mouseleave(function(){
	        if(bl){
	            $(this).removeClass("on_cart");
	        }
        });
        /*综合排序*/
        $(".shop-sort button").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        	search = $(this).attr("data-name");
            loaded();
        });
        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {
                $(".return").hide();
            }
        });
    })
</script>
</body>
</html>