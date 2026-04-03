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
    
    <title>我的微分金</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
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
    <div class="clear"></div>
    <c:set var="cashId" value=""></c:set>
    <c:set var="moneys" value="0"></c:set>
    
   	<c:forEach var="cartItem" items="${beans}" varStatus="idc">
		<c:if test="${idc.index!=0}">
			<c:if test="${cartItem[15]!=cashId}">
		        <div class="thisOrder" id="thisOrder">
		        <span style="float:left;font-size: 12px;color:black;">订单号：${cashId}</span>
		       </div>
		       <div class="clear"></div>
		       </div><c:set var="moneys" value="0"></c:set>
		  	</c:if>
	  	</c:if>
	   	<c:if test="${cartItem[15]!=cashId}">
			<div class="cartOrder fl">
	        <div class="cartHeader">
	            <div class="userName fl">${cartItem[2]}</div>
	            <div class="editor fr" style="width: 25%;">${cartItem[8]}</div>
	        </div>
	        <div class="clear"></div>
	        <h style="display:none;">${cartItem[9]}</h>
			<c:set var="moneys" value="0"></c:set>
	   	</c:if>
        <c:set var="cartItemAmount" value="0"></c:set>
        <c:set var="cartItemAmountMoney" value="0"></c:set>
        
        <div class="cartCon">
            <div class="cartContents fl">
                <img alt="" title="" src="<%=basePath %>${cartItem[11]==null ? '/images/WFJClient/zwtp160.png' : cartItem[11]}"/>
                <p class="black" style="margin-top: 20px;">${cartItem[3]}</p>
                <d></d>
                <d></d>
            </div>
        </div>
        <c:set value="${cartItemAmount + cartItem[6]}" var="cartItemAmount" />
        <c:set value="${cartItemAmountMoney + cartItem[4]*cartItem[6]}" var="cartItemAmountMoney" />
        <div class="clear"></div>
        <div class="allPrice1"><d>共&nbsp;${cartItemAmount}&nbsp;件&nbsp;&nbsp;合计：&nbsp;￥${cartItemAmountMoney}</d></div>
        <div class="allPrice1"><d>收货人姓名：${cartItem[16] }</d></div>
        <div class="allPrice1"><d>收货人电话：${cartItem[17] }</d></div>
        <div class="allPrice"><d>收货人地址：${cartItem[18] }</d></div>
        <c:set var="countPrice" value="${cartItemAmountMoney+countPrice }"/>
   		<c:set var="cashId" value="${cartItem[15]}"></c:set>
    </c:forEach>
    <c:if test="${cashId!=''}">
     <div class="thisOrder" id="thisOrder">
        <span style="float:left;font-size: 12px;color:black;">订单号：${cashId} 共计：${countPrice }</span>
     </div>
     <div class="clear"></div>
    </div>
  </c:if>
</div>
</body>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
  <script type="text/javascript">

    var basePath="<%=basePath%>";

  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "客户订单";
	});	
  	
  </script>
</html>
