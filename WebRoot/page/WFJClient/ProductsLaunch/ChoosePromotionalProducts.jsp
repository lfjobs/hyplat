<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String ppID = request.getParameter("ppID");
	String user = request.getParameter("user");
	String salesPromotionId = request.getParameter("salesPromotionId");
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>选择商品</title>
    <script src="<%=basePath %>js/ea/production/resume/jquery-2.1.1.min.js" type="text/javascript"></script>
<link href="<%=basePath %>css/ea/production/Products.css" rel="stylesheet"
	type="text/css">
<script src="<%=basePath %>js/ea/production/resume/javascript.js" type="text/javascript"></script>
    <script src="<%=basePath %>js/ea/marketing/wfjeshop/ChoosePromotionalProducts.js" type="text/javascript"></script>
</head>
<body class="body">
<input id="ppID" type="hidden" value="<%=ppID %>"/>
<input id="user" type="hidden" value="<%=user %>"/>
<input id="salesPromotionId" type="hidden" value="<%=salesPromotionId %>"/>
    <div class="res_top">
        <ul>
            <li><a id="backtrack" ><img src="<%=basePath %>images/ea/production/left_jt.png" alt=""></a></li>
            <li>请选择</li>
            <li></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="search_shop">
            <input type="text" placeholder="" align="center" id="change">
            <div id="sea_shop"><img src="<%=basePath %>images/ea/production/search.png"> <p>搜索</p><div class="clearfix"></div></div>
        </div>
    <div class="res_bot" >
        
	       	<div class="shop_lie" id="joint">
	            
	        </div>
        
        
    </div>
    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
    <div class="footer">
            <div>
                <p>选择了<span id="shop_span">0</span>件促销品</p>
            </div>
            	<button id="submit">确定选择</button>
            <div class="clearfix"></div>
        </div>
<script>
var basePath = '<%=basePath%>';
var pageCount;//总页数 
var pageNumber;//当前页
var pageSize;//每页显示多少条
var recordCount;//总条数
var ppid;//主产品id
var user;//用户
var change;
var t;
var salesPromotionID;
var promotionID;
var judge;
    $(function(){
        $("input").focus(function(){
           $(".search_shop div").hide()
        });
        $("input").blur(function(){
           $(".search_shop div").show()
        });
        $("#sea_shop").click(function(){
            $(".search_shop div").hide()
        });

        $(".res_bot").css("height",$(window).height()*0.92+$(".footer").height()+"px")
    });
</script>
</body>
</html>