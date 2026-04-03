<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>选择商品</title>
<link rel="stylesheet" href="<%=basePath %>css/WFJClient/budget.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<script src="<%=basePath %>js/ea/marketing/wfjeshop/budget.js"></script>
</head>
<body class="body">
    <div class="res_top">
        <ul>
            <li><a href="<%=basePath %>ea/wfjbudget/ea_budgetDetail.jspa?cashierBillsID=${cashierBillsID}&user=${user}&companyId=${companyId}"><img src="<%=basePath%>images/WFJClient/budget/left_jt.png" alt=""></a></li>
            <li>${ppname!=null?ppname:'选择产品' }</li>
            <li></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
        <div class="search_shop">       
            <div id="sea_shop"><input type="text" placeholder="搜索产品名称" align="center"/> <img src="<%=basePath%>images/WFJClient/budget/search.png" onclick="searchPro()"><div class="clearfix"></div></div>
        </div>
        <div class="shop_lie">
        <c:if test="${fn:length(pageForm.list) eq 0 }">
        	<div id="noresult" style="text-align:center;height:100px;background-color: #fff;"><img style="height:180px;" src="<%=basePath %>images/WFJClient/DigitalMall/noresult.png"></div>      
        </c:if>
        <c:if test="${fn:length(pageForm.list)>0 }">
        <c:forEach items="${pageForm.list }" var="entity" varStatus="status">
            <c:if test="${status.index ne fn:length(pageForm.list)-1 }">
            <div class="shop_grd">
            	<input type="hidden" value="${ entity[1]}" id='ppid'/>
				<input type="hidden" value="${ entity[5]}" id='goodsid'/>
				<input type="hidden" value="${ entity[6]}" id='companyid'/>
				<input type="hidden" value="${ entity[10]}" id='ccompanyid'/>
                <img src="<%=basePath%>${entity[4] }">
                <div class="shop_txt">
                    <p>${entity[0] }</p>
                    <p>￥<span>${entity[2] }</span></p>
                </div>
                <div class="shop_img">
                    <img class="shop_img1" src="<%=basePath%>images/WFJClient/budget/gou2.png">
                    <img class="shop_img3" src="<%=basePath%>images/WFJClient/budget/gou2-.png">
                </div>
                <div class="clearfix"></div>
            </div></c:if>
            <c:if test="${status.index eq fn:length(pageForm.list)-1 }">
					<div class="shop_grd last">
					  	<input type="hidden" value="${ entity[1]}" id='ppid'/>
						<input type="hidden" value="${ entity[5]}" id='goodsid'/>
						<input type="hidden" value="${ entity[6]}" id='companyid'/>
						<input type="hidden" value="${ entity[10]}" id='ccompanyid'/>
						<img src="<%=basePath%>${entity[4] }">
						<div class="shop_txt">
							<p>${entity[0] }</p>
							<p>￥<span>${entity[2] }</span></p>
						</div>
						<div class="shop_img">
							<img class="shop_img1"
								src="<%=basePath%>images/WFJClient/budget/gou2.png"> <img
								class="shop_img3"
								src="<%=basePath%>images/WFJClient/budget/gou2-.png">
						</div>
						<div class="clearfix"></div>
					</div>
			</c:if>
            </c:forEach> </c:if>
        </div>
        <div class="footer">
            <div>
                <p>选择了<span id="shop_span">0</span>个项目</p>
            </div>
            <button onclick="chanceMorePro()">确定选择</button>
            <div class="clearfix"></div>
        </div>
        <form style="display:none;" name="productForm" method="post" action="<%=basePath %>ea/wfjbudget/ea_chancePro.jspa?">
        	<input type="submit" name="submit" style="display:none;"/>
        	<input type="hidden" value="${user }" name="user"/>
        	<input type="hidden" value="${goodsBillsID }" name="goodsBillsID"/>
        	<input type="hidden" value="${cashierBillsID }" name="cashierBillsID"/>
        	<input type="hidden" value="${companyId }" name="companyId"/>
        	<input type="hidden" value="" name="ppId" id="ppids"/>
        </form>
    </div>
<script>
var basePath='<%=basePath%>';
var user='${user}';
var goodsBillsID='${goodsBillsID}';
var cashierBillsID='${cashierBillsID}';
var companyId='${companyId}';
$(function(){
	$("#noresult").css({"height":$(window).height()-$(".res_top").height()-$(".search_shop").height()+"px"});
});
</script>
</body>
</html>