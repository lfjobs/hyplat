<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>店铺搜索</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
 <body>
 <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
   <ul id="menuMore" class="menuMore" >
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
    </ul>
  <div class="con">
    <div class="so fl">
        <div class="search">
        	<form action="<%=basePath%>/ea/wfjshop/ea_searchShops.jspa" id="submit">
            	<input type="text" value="" name="corganization.organizationName" placeholder="搜店铺"/>
            	<input type="hidden" name="search" value="searchShops"/>
            	<input type="hidden" name="companyId" value="<%=request.getParameter("companyId")%>"/>
	        </form>
            <img alt="" id="search" title="" src="<%=basePath %>images/WFJClient//sos.png"/> 
        </div>
    </div>
    <div class="clear"></div>
   <div class="goodsSearchNav fl">
       <div class="credibility fl">信誉度优先</div>
       <div class="sales fl">销量优先(待开发)</div>
   </div>
   <div class="clear"></div>
   <% int i=0; %>
   <div class="goodsSearchNum fl">共找到<h id="NO2"></h>个店铺</div>
   <s:iterator value="shopList" id="arr">
	   <div class="clear"></div>
	    <div class="goodsSearchList">
	        <a href="<%=basePath%>/ea/wfjshop/ea_getShopProducts.jspa?organizationID=${arr[0]}&comId=${arr[6]}">
	            <img class="product fl" alt="" title="" src="<%=basePath %>${arr[3]}"/>
	            <div class="productDetail fl">
	                <h1>${arr[1] }</h1>
	                <p>${arr[4] }</p>
	                <div>
	                    <c:forEach begin="1" end="${arr[7]}" var="i">
	                    	<img class="star" alt="" title="" src="<%=basePath %>${arr[8]}"/>
	                    </c:forEach>
	                </div>
	            </div>
	            <div class="productData fr">
	                <p>月销量100</p>
	                <d>建店：${arr[5] }</d>
	            </div>
	        </a>
	    </div>
	    <% i++; %>
    </s:iterator>
    <input type="hidden" id="NO1" value="<%=i %>"/>
</body>
 <script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
 <script type="text/javascript">
 	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "店铺搜索";	
	});	
	$(document).ready(function(){
		var no1=$("#NO1").val();
		$("#NO2").text(no1);
		})
  	$("#search").click (function(){
		$("#submit").submit();
  	  	});
 </script>
</html>
