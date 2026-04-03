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
    <title>微分金店</title>  
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
	<!--<script src="<%=basePath%>js/WFJClient/shopShow.js" type="text/javascript"></script> -->
  </head>
  
  <body>
	<iframe id="indexTop" align="center" width="100%"  height="46px" src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
    <ul id="menuMore" class="menuMore" >
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
    </ul>
	<div class="con">
		<div class="so fl">
        <div class="search">
        	<form action="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" id="submit">
            	<input type="text" value="" name="corganization.organizationName" placeholder="输入编号 店名或地址"/>
            	<input type="hidden" name="search" value="searchShops"/>
            	<input type="hidden" name="shopCategory" value="微商店"/>
            	<input type="hidden" name="companyId" value="<%=request.getParameter("companyId")%>"/>
	        </form>
            <img alt="" id="search" title="" src="<%=basePath %>images/WFJClient//sos.png"/> 
        </div>
    </div>
   
     <div class="goodsSearchNav fl">
       <div class="credibility fl"><a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&sort=bhjx">编号降序</a></div>
       <div class="sales fl"><a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getParameter("companyId")%>&sort=dmjx">店名降序</a></div>
   </div>	    	   
	    <div class="clear"></div>
	    <div class="storeContent storeContentDis fl">
		    <s:iterator value="beans" id="arr">
		    	<div class="store fl">
		           <a href="<%=basePath%>/ea/wfjshop/ea_getShopProducts.jspa?organizationID=${arr[0]}&comId=${arr[5]}">
				    <img class="store" alt="" title="" src="<%=basePath %>${arr[4]==null ? '/images/WFJClient/zwtp160.png' : arr[4]}" />
	                <p>${arr[1] }</p>
	                <span>${arr[2] }</span>
		           </a>
	                <div>
	                	<c:forEach begin="1" end="${arr[6]}" var="i">
	                    	<img class="star" alt="" title="" src="<%=basePath %>${arr[7]}"/>
	                    </c:forEach>
                    	<d>加盟</d>
	                </div>
		        </div>
		    </s:iterator>
	    </div>	  
	    <div class="clear"></div>
	</div>
	<div class="floorNav fexBottom">
	    <div><a  href="javascript:;" target="_parent"><img id="home" alt="" title=""  src="<%=basePath %>images/WFJClient/home.png"/> </a> </div>
	    <div><a id="shopCart" href="<%=basePath %>/ea/buyproducts/ea_putInCart.jspa" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/shopCart.png"/> </a> </div>
	    <div><a id="me" href="<%=basePath%>/ea/buyproducts/ea_getMyOrders.jspa" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/me.png"/> </a> </div>
	    <div><a id="ordersearch" href="<%=basePath%>/page/WFJClient/GoodsShow/searchOdr.jsp" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/searchOdr.png"/></a></div>
	</div>
</body>
  	<script type="text/javascript">
  	   
  		$('#indexTop').load(function () {
  	  		var doc=document.getElementById("indexTop").contentWindow.document;
  	  		doc.getElementById("topbar_title").innerHTML="微分金店";
	  	  	doc.getElementById("return").onclick=function(){
				window.history.go(0);
			}	
	  	   doc.getElementById("topbar_refresh").onclick=function(){
				window.history.go(0);
			}	
		});
  		$("#search").click (function(){
  			$("#submit").submit();
  	  	  	});

  		<%--$('#indexBottom').load(function () {
	  		var doc=document.getElementById("indexBottom").contentWindow.document;
	  		//doc.getElementById("home").href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?companyId=<%=request.getAttribute("companyId")%>&shopCategory=微商店";	
	  		doc.getElementById("home").onclick=function(){
  				window.history.go(0);
				}
			doc.getElementById("shopCart").href="<%=basePath %>/ea/buyproducts/ea_putInCart.jspa";
			doc.getElementById("me").href="<%=basePath%>/ea/buyproducts/ea_toCasher.jspa";
		});	--%>
         
  </script>
 </html>
