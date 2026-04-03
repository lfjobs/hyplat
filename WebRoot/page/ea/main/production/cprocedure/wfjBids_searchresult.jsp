<!doctype html>
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

<title>招标查询</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
	
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/wfjBids_searchresult.js"></script>





<script type="text/javascript">

var basePath="<%=basePath%>";
var parameter = "${parameter}";
var tradeName = "${tradeName}";
var tradeID = "${tradeID}";
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};

	
</script>


</head>
<body>
	<div class="top-sp">
        <ul>
            <a href="javascript:history.go(-1)">
                <li class="arrar">
                    <div>
                     <img src="<%=basePath%>images/ea/bids/jiantou-hei_03.png" alt="">
                    </div>
                </li>
            </a>
            <li><h5 class="shangpin" style="font-size:10px;">招标商品</h5></li>
        </ul>


    </div>
    <div class="main_hide">
        <div class="main_auto">


    <div class="ipt-s-ss" id="ipt-s-ss">
    	<input type="text" name="" value="" id="ipt" class="ipt-ss">
    	<span class="ssk" id="ssk">搜索</span>
    </div>
    <ul class="tj_con">
      <c:forEach   items="${pageForm.list}" var = "item" varStatus="status">
        <c:if test="${status.index+1 eq fn:length(pageForm.list)}">
        <li class="last" onclick="viewMainProduct('${item[1]}','${item[2]}','${item[8]}')">
        </c:if>
        <c:if test="${status.index+1 ne fn:length(pageForm.list)}">
        <li onclick="viewMainProduct('${item[1]}','${item[2]}','${item[8]}')">
        </c:if>
            <div class="tj_img"><img src="<%=basePath%>${item[3]}" /></div>
            <div class="tj_text">
                <h3>${item[0]}</h3>
                <ul>
                    <li class="price">￥${item[5]}</li>
                    <li class="biao"><c:if test="${item[4]==null}">0</c:if><c:if test="${item[4]!=null}">${item[4]}</c:if>次投标</li>
                    <li class="days"><input type="hidden" value="${item[6]}"/></li>
                </ul>
            </div>
        </li>
       </c:forEach>
    </ul>
        </div>
    </div>
</body>
<script>

    $(function(){
        $(".main_hide").css("height",$(window).height()-$(".top-sp").outerHeight()+"px");
        $(".tj_con .tj_img").css({"height":$(".tj_text").height()+"px"});

//        $(".arrar").click(function(){
//           document.location.href = basePath
//								+ "ea/purchasebids/ea_findbidIndexList.jspa";
//        })
        $(".tj_img").css({"height":"10rem"});
        $(".tj_text").css({"height":$(".tj_img").height()-"30"+"px"});
    })
</script>
<script>

var ipt=document.getElementById('ipt');
var ssk=document.getElementById('ssk');
ipt.onclick=function(){
    ssk.style.display="none";
}
ipt.onblur=function(){
    ssk.style.display="block";
}
$(".tj_con").find("li").eq(0).attr("style","border-top:2px solid #ebebeb;width:100%;");

</script>

</html>