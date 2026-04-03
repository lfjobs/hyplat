<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微分金</title>
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
  <iframe id="indexTop" align="center" height="46px" width="100%" src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	   <ul id="menuMore" class="menuMore" >
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
       </ul>
	   <div class="con" >
	   		 <div class="so fl">
	        <div class="search">
	        	<form action="<%=basePath%>/ea/buyproducts/ea_getJournalNumByBill.jspa" id="submit">
	            	<input type="text" value="" name="journalNum" placeholder="请输入订单号"
	            	onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 
	            </form>
	            <img alt="" title="" id="search" src="<%=basePath%>images/WFJClient/sos.png"/>
	        </div>
	   	 </div>
	   	 
	   	 <!-- 循环 -->
	<c:choose><c:when test="${beans==null || fn:length(beans) == 0}">无结果</c:when></c:choose>
	<c:forEach items="${beans }" var="item">
    <div class="orderDetail fl">
        <div class="orderDetailNav orderDetailCon">
            <div class="orderDetailCon">
                <img alt="" src="<%=basePath %>${item[4] }"/>
                <div class="orderHeader fl">
                    <p class="orderTitle fl">${item[0] }</p>
                    <p class="orderTitle fl" id="pirce">￥${item[1] }</p>
                </div>
            </div>
        </div>
        <div class="orderDetailNav">
          		  购买数量
                <d id="num">${item[3] }</d>
        </div>
        <div class="orderFloor">
            <div class="orderConfirm">
                <c:if test="${item[8] eq '00' }">
					<div class="confirm fr" id="jiesuan">已付款</div>
				</c:if>
				<c:if test="${item[8] eq '01' }">
					<c:if test="${item[6] eq '01' }"><div class="confirm fr" id="jiesuan">现金支付</div></c:if>
					<c:if test="${item[5] eq '00' }">
						<a >
							<div class="confirm fr" id="jiesuan">付款</div>
						</a>
					</c:if>
					<c:if test="${item[6] eq '00' }">
						<a >
							<div class="confirm fr" id="jiesuan">付款</div>
						</a>
					</c:if>
				</c:if>
                <d class="fr" id="money"></d>
                <span class="fr">合计：￥${item[2] }&nbsp;</span>
            </div>
        </div>
    </div>
    </c:forEach>
    <!-- 循环 end-->
   </div>
	<div class="floorNav fexBottom">
	    <div style="width:25%;"><a id="navHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/productNavHover.png"/> </a> </div>
	    <div style="width:25%;"><a id="buyCommHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/buyCommHover.png"/> </a> </div><%--加入购物车 --%>
	    <div style="width:25%;"><a id="shopConHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/shopConHover.png"/> </a> </div>
	    <div style="width:25%;"><a id="woWHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/WoWHover.png"/> </a> </div>
	</div>
  </body>
  <script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
  <script type="text/javascript">
  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "微分金";
		
	});		
  	var token=0;
	$("#search").click (function(){
			if(token!=0){
				return;
			}
			token=1;
  			$("#submit").submit();
  	});
 </script>
</html>
