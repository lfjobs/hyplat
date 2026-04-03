<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>店铺简介</title>  
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
   <style>
        .con div.shopRegInfo{ width:96%; margin:auto; margin-top:10px;}
        .con div.shopRegInfo div.shopInfo{ width:100%; height: 78px; }
        .con div.shopRegInfo div.shopInfo img.shopLogo{ position: relative; width:60px; height: 60px; line-height: 60px; margin-top:10px; margin-left:4px; border-radius: 4px;-moz-border-radius: 4px 4px 4px 4px;-webkit-border-radius: 4px;}
        .con div.shopRegInfo div.shopInfo h1{ margin-left:75px; margin-top:10px; height: 30px; line-height: 30px;}
        .con div.shopRegInfo div.shopInfo div{ margin-left:75px; height: 30px; line-height: 30px;}
        .con div.shopRegInfo div.shopInfo div img.shopStar{ width:15px; height: 15px;}
        .con div.shopRegInfo div.shopNav{ width:100%; height: 40px; border-top: 1px dotted #aaa; overflow: hidden;}
        .con div.shopRegInfo div.shopNav span{ display: block; width:20%; height: 40px; line-height: 40px; text-align: center; float: left; font-weight: bold;}
        .con div.shopRegInfo div.shopNav d{ display: block; width:80%; height: 40px; line-height: 40px; text-align: left; float: left; }
    </style>
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	<ul id="menuMore" class="menuMore" >
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
       </ul>
	<div class="con">
	    <div class="shopRegInfo">
	        <div class="shopInfo fl">
	            <a href="javascript:;">
	            	<c:choose>
			       <c:when test="${beans[0][1]==null}">
			       	<img class="shopLogo fl" alt="" title="" src="<%=basePath %>/images/WFJClient/zwtp160.png" />
			       </c:when>
			       <c:otherwise>
			             <img class="shopLogo fl" alt="" title="" src="<%=basePath %>${beans[0][1]}" />
			       </c:otherwise>
			    	</c:choose>
	                <h1>${corganization.organizationName}</h1>
	                <div>
	                    <c:forEach begin="1" end="${beans[0][7]}" var="i">
		                   <img class="star" alt="" title="" src="<%=basePath %>${beans[0][8]}"/>
		                </c:forEach>
	                </div>
	            </a>
	        </div>
	        <div class="clear"></div>
	        <div class="shopNav fl">
	            <span>简介</span>
	            <d>${beans[0][0] }</d>
	        </div>
	        <div class="clear"></div>
	        <div class="shopNav fl">
	            <span>所在地</span>
	            <d>${beans[0][2]} </d>
	        </div>
	        <div class="clear"></div>
	        <div class="shopNav fl">
	            <span>开店时间</span>
	            <d><fmt:formatDate value="${beans[0][3] }" pattern="yyyy-MM-dd" /> </d>
	        </div>
	        <div class="clear"></div>
	        <div class="shopNav fl">
	            <span>掌柜名</span>
	            <d>${beans[0][4] }</d>
	        </div>
	        <div class="clear"></div>
	        <div class="shopNav fl">
	            <span>服务电话</span>
	            <d><a tel="">${beans[0][5] }</a></d>
	        </div>
	        <div class="clear"></div>
	    </div>
	</div>
  </body>
   <script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
  <script type="text/javascript">
  	$('#indexTop').load(function () {
  		var doc=document.getElementById("indexTop").contentWindow.document;
  		doc.getElementById("topbar_title").innerHTML="店铺简介";
	});
	</script>
</html>
