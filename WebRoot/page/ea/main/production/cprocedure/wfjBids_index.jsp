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

<html style="font-size: 62.5%;background: #fff;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>投标首页</title>


<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/reset.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/shopping.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/wfjBids_index.js"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=f24dd8c87226673baeba0239b2863a6d&plugin=AMap.Geocoder"></script>
<!-- UI组件库 1.0 -->
<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
var cityselect = "${param.city}";


	
</script>


</head>
<body>
	<div class="atop">
        <div class="top-dh">
            <div class="top-dh-img">
            	<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
                <img src="<%=basePath%>images/ea/bids/jiantou1.png" alt="">
                </a>
            </div>
            <div class="csb"><span id="city"></span><span class="s"></span></div>
        </div>
        <div class="top_search">
            <div class="top_search_kuang">
                <input type="text" name="" value="" placeholder="快速招人，找需求" class="ipt" >
                <div class="xuqiu">
                    <img src="<%=basePath%>images/ea/bids/xuqiu_03.jpg" alt=""/>
                    <p><nobr>发需求</nobr></p>
                </div>
            </div>
        </div>
    </div>
    <ul class="zbs">
    	<li onclick="viewBidsGood();"><img class="header_img" src="<%=basePath%>images/ea/bids/fenlei_03.png" /><h6>招标商品</h6></li>
        <li onclick="viewRecruitIndex();"><img class="header_img" src="<%=basePath%>images/ea/bids/fenlei_05.png" /><h6>招聘人才</h6></li>
        <!--<li><img class="header_img" src="<%=basePath%>images/ea/bids/fenlei_07.png" /><h6>招标服务</h6></li>-->
        <li onclick="investService()"><img class="header_img" src="<%=basePath%>images/ea/bids/zhaoshang.png" /><h6>招商服务</h6></li>

        <div class="clear"></div>
    </ul>
	<div class="tsfw">
        <div class="tsfw1">
            <img src="<%=basePath%>images/ea/bids/fw.png" alt=""/>
            <h3>招标行业</h3>
        </div>
    </div>
    <ul class="tsfw-fl">

    <c:forEach items="${hotindus}" var="item">
    	<li class="indus">
    	<input type="hidden"  value="${item[3]}" class="tradeID"/>
    	<input type="hidden"  value="${item[2]}" class="tradeName"/>
            <div class="tsfw-fl-1">
                <h4><c:if test="${fn:length(item[2]) > 5}">${fn:substring(item[2],0,5)}</c:if>
                <c:if test="${fn:length(item[2]) <= 5}">${item[2]}</c:if>
                </h4>
             <p>${item[4]}</p>
            </div>
            <img class="fw_img" src="<%=basePath%>${item[1]}" />
        </li>
   </c:forEach>
    <c:if test="${fn:length(hotindus) eq 0}"> 
       <li>
      
            <div class="tsfw-fl-1">
                <h4>汽车服务</h4>
                <p>汽车周边，细心服务</p>
            </div>
            <img class="fw_img" src="<%=basePath%>images/ea/bids/fw_qc.png" />
        </li>
        <li>
            <div class="tsfw-fl-1">
        	<h4>美食餐饮</h4>
            <p>我身边的美食专家</p></div>
            <img class="fw_img" src="<%=basePath%>images/ea/bids/fw_ms.png" />
        </li>
        <li>
            <div class="tsfw-fl-1">
        	<h4>运动</h4>
            <p>运动健康季</p>
            </div>
            <img class="fw_img" src="<%=basePath%>images/ea/bids/fw_yd.png" />
        </li>    
     </c:if> 
        <li style="border-right:none;width: 24%;" class="ali">
            <div class="tsfw-fl-1"  style="padding-bottom:21px;">
        	<h4>全部行业</h4>
            <p>项目推荐，最优质</p>
             
            </div>
            <img class="fw_img allimg" src="<%=basePath%>images/ea/production/All2x.png" />
        </li> 
        <div class="clear"></div>
    </ul>
    <div class="tsfw">
        <div class="tsfw1">
            <img src="<%=basePath%>images/ea/bids/ico_16.png" alt=""/>
            <h3>项目推荐</h3>
        </div>
    </div>
    <ul class="tj_con">
        <c:forEach   items="${pageForm.list}" var = "item" varStatus="status">
        <c:if test="${status.index+1 eq fn:length(pageForm.list)}">
        <li style="width:100%;" class="last" onclick="viewMainProduct('${item[1]}','${item[2]}','${item[8]}')">
        </c:if>
        <c:if test="${status.index+1 ne fn:length(pageForm.list)}">
        <li style="width:100%;" onclick="viewMainProduct('${item[1]}','${item[2]}','${item[8]}')">
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
</body>
<script>
    $(function(){
        $(".atop").css("height",$(window).height()*0.25+"px");
        $(".ipt").css("height",$(".xuqiu").height()+"px");
        $(".tsfw h3").css("line-height",$(".tsfw img").height()+"px");
        $(".tsfw-fl img").css("height",$(".tsfw-fl img").width()+"px");
        $(".allimg").css("height",($(".tsfw-fl img").width()-20)+"px");
        $(".allimg").css("width",($(".tsfw-fl img").width()-20)+"px");
        $(".allimg").css("padding-left","5px");
        $(".tsfw-fl li").css({"border-bottom":"none"});
        
        $(".tj_img").css({"height":"10rem"});
        $(".tj_text").css({"height":$(".tj_img").height()-"30"+"px"});
    })
</script>
</html>