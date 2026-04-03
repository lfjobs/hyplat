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
    <div class="myWFJName fl">
        <h1 class="me">${key_customer.account}</h1>
    </div>
    <div class="clear"></div>
    <div class="myOrderData fl">
        <div class="orderData fl">
            <img alt="" title="" src="<%=basePath %>/images/WFJClient/order.png"/>
            <a href="<%=basePath%>/ea/wfjcustomer/ea_personalInfor.jspa"><span>我的资料</span>
            <d>>></d></a>
         </a>
        </div>
        <div class="clear"></div>
        <div class="orderData fl">
            <img alt="" title="" src="<%=basePath %>/images/WFJClient/data.png"/>
            <span>我的订单</span>
        </div>
        <div class="clear"></div>
        <div class="myOrderDataNav fl">
            <div class="myNav myNavColor fl"><a href='<%=basePath%>/ea/buyproducts/ea_getOrders.jspa'>全部</a></div>
            <div class="myNav fl"><a href="<%=basePath%>/ea/buyproducts/ea_getOrders.jspa?fkstatus=01">待付款</a></div>
            <div class="myNav fl"><a href="<%=basePath%>/ea/buyproducts/ea_getOrders.jspa?fkstatus=00">待发货</a></div>
            <div class="myNav fl">待收货</div>
            <div class="myNav fl">退货/售后</div>
        </div>

    </div>
    <div class="clear"></div>
    <c:set var="cashId" value=""></c:set>
    <c:set var="moneys" value="0"></c:set>
    
   	<c:forEach var="cartItem" items="${beans}" varStatus="idc">
		<c:if test="${idc.index!=0}">
			<c:if test="${cartItem[9]!=cashId}">
		        <div class="thisOrder" id="thisOrder">
		        <span style="float:left;font-size: 12px;color:black;">合计：￥${moneys}</span>
		            <a class="payment fr zfb ${wfStatus2}xj" name="${cartItem[12]}" onclick="pay('${number}','${moneys}','${names}','${wfStatus1}','${wfStatus2}','${wfStatus3}','${wfStatus4}')" >支付</a>
		            <a class="cancelOrder fr ${wfStatus2}xj" name="${cartItem[12]}" onclick="cancelOrder()">取消订单</a>
		       </div>
		       <div class="clear"></div>
		       </div><c:set var="moneys" value="0"></c:set>
		  	</c:if>
	  	</c:if>
	   	<c:if test="${cartItem[9]!=cashId}">
	   	<c:set var="names" value=""></c:set>
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
                <img alt="" title="" src="<%=basePath %>${cartItem[7]==null ? '/images/WFJClient/zwtp160.png' : cartItem[7]}"/>
                <p class="black">${cartItem[3]}</p>
                <d></d>
                <d></d>
            </div>
            <div class="cartPrice fl">
                <p>￥${cartItem[4]}</p>
                <p>数量：${cartItem[6]}</p>
            </div>
        </div>
       
        <c:set value="${cartItemAmount + cartItem[6]}" var="cartItemAmount" />
        <c:set value="${cartItemAmountMoney + cartItem[6]*cartItem[4]}" var="cartItemAmountMoney" />
        
        <div class="clear"></div>
        <div class="allPrice"><d>订单号：${cartItem[14] }&nbsp;共${cartItemAmount}件&nbsp;合计：￥${cartItemAmountMoney}</d></div>
       <%-- 之前在底 --%>
        <input type="hidden" value="${cartItem[9]}" id="caid"/>
        <input type="hidden" value="${cartItem[10]}" id="goid"/>
		<input type="hidden" value="${cartItem[13]}" name="hdfk"/>
	
		
  		<%-- 之前在底 --%>
  		<c:set var="cashId" value="${cartItem[9]}"></c:set>
   		<c:set var="moneys" value="${moneys+cartItem[6]*cartItem[4]}"></c:set><%-- 记录一个订单的总金额 --%>
   		<c:set var="names" value="${names},${cartItem[3]}"></c:set>
   		<c:set var="number" value="${cartItem[14]}"></c:set>
   		<c:set var="wfStatus1" value="${cartItem[15]}"></c:set>
   		<c:set var="wfStatus2" value="${cartItem[16]}"></c:set>
   		<c:set var="wfStatus3" value="${cartItem[17]}"></c:set>
   		<c:set var="wfStatus4" value="${cartItem[18]}"></c:set>
   		<form id="alipaymentform" name="alipaymentform"  method="post" enctype="multipart/form-data" _input_charset="UTF_8">
		    <input type="hidden" value="${cartItem[14]}" id="WIDout_trade_no" name="WIDout_trade_no"/>
			<input type="hidden" value="${moneys}" id="WIDtotal_fee" name="WIDtotal_fee"/>
			<input  type="hidden" value="${cartItem[3]}" id="WIDsubject" name="WIDsubject"/>
	        <input type="hidden" name="submit" id="submit" style="display: none;"/>
		</form>
    </c:forEach>
    <c:if test="${cashId!=''}">
     <div class="thisOrder" id="thisOrder">
        <span style="float:left;font-size: 12px;color:black;">合计：￥${moneys}</span>
            
           <%-- <a class="cancelOrder fr" name="${cartItem[12]}" href="<%=basePath %>ea/buyproducts/ea_deteleCostSheet.jspa?cashierBills.cashierBillsID=${cartItem[9]}">取消订单</a> --%>
            <a class="payment fr zfb ${wfStatus2}xj" name="${cartItem[12]}" onclick="pay('${number}','${moneys}','${names}','${wfStatus1}','${wfStatus2}','${wfStatus3}','${wfStatus4}')" >支付</a>
            <a class="cancelOrder fr ${wfStatus2}xj" name="${cartItem[12]}" onclick="cancelOrder()">取消订单</a>
            
       </div>
       <div class="clear"></div>
  </div>
  </c:if>
</div>
</body>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
  <script type="text/javascript">
 

    var basePath="<%=basePath%>";

  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "我的微分金";
	});	
  	$(document).ready(function() {
  		 $(".cancelOrder").click(function(){
 			var cashid=$(this).parent().parent().find("h").text();
 			$(this).attr("href","<%=basePath %>ea/buyproducts/ea_deteleCostSheet.jspa?cashierBills.cashierBillsID="+cashid);
   		})
		var status=document.getElementsByName("00");
		for(var i=0;i<status.length;i++){
			status[i].style.display="none";
		}
		
		var wfStatus2=$(".01xj");
		for(var i=0;i<wfStatus2.length;i++){
			wfStatus2[i].style.display="none";
		}
		var hdfks=document.getElementsByName("hdfk");
		for(var i=0;i<hdfks.length;i++){
			var value=hdfks[i].value;
			if(value=="01"){//货到付款
				$(hdfks[i]).parent().find("a").hide();
			}
		}


});


	//支付宝支付
   function pay(number,money,name,wfStatus1,wfStatus2,wfStatus3,wfStatus4){
       name=name.substring(1);
	        if(wfStatus1=="00"){
				if(wfStatus4=="02"){
					//银联支付
					document.location.href = basePath+"/ea/buyproducts/ea_unionpayMoney.jspa?journalNum="+number+"&total="+money+"&baseUrl="+basePath;
				}else if(wfStatus4=="01"){
				  
	
					_AP.pay(encodeURI(encodeURI(basePath+"page/WFJClient/CustomerOrder/wfjAlipay.jsp?WIDout_trade_no="+number+"&WIDtotal_fee="+money+"&WIDsubject="+name)));
					return;
				}else if(wfStatus4=="00"){
				     

			
				document.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
					"appid=wxff4c5683480d6664&redirect_uri=http://www.impf2010.com:80/ea/buyproducts/ea_weChatpay.jspa?params="+number+"-"+name+"-"+money+"&showwxpaytitle=1&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
					
				}else{
					alert("支付发生错误，请重新选择付款方式");
					return;
				}
			}else if(wfStatus1=="01"){
				if(wfStatus2=="00"){
				
				if(wfStatus4=="02"){
										//银联支付
					document.location.href = basePath+"/ea/buyproducts/ea_unionpayMoney.jspa?journalNum="+number+"&total="+money+"&baseUrl="+basePath;
				}else if(wfStatus4=="01"){
				  
	
					_AP.pay(encodeURI(encodeURI(basePath+"page/WFJClient/CustomerOrder/wfjAlipay.jsp?WIDout_trade_no="+number+"&WIDtotal_fee="+0.01+"&WIDsubject="+name)));
					return;
				}else if(wfStatus4=="00"){
				     
					document.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
					"appid=wxff4c5683480d6664&redirect_uri=http://www.impf2010.com:80/ea/buyproducts/ea_weChatpay.jspa?params="+number+"-"+name+"-"+money+"&showwxpaytitle=1&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
					
				}else{
					alert("支付发生错误，请重新选择付款方式");
					return;
				}
				}else if(wfStatus2=="01"){
					//现金支付里面分   立即支付+货到付款
					if(wfStatus3=="00"){
						//立即支付
						//$("#toCasherForm").submit();
					}else if(wfStatus3=="01"){
						//货到付款
						//$("#toCasherForm").submit();
					}else{
						alert("发生未知错误，请重新选择付款方式");
					}
					return;
				}
			}else{
				alert("发生未知错误，请重新选择付款方式");
				return;
			}
	  
	
	}
  </script>
</html>
