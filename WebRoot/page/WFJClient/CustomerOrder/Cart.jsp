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
    
    <title>购物车</title>
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
	<script type="text/javascript">
	var adrss='${staffAddress}';
	
	</script>
  </head>
  <style>
    div.con div.cartPrice span{ float: left; display: none; margin-left: 8px; width:20px;line-height: 20px; text-align: center; background-color: #ccc; border-radius: 4px;-moz-border-radius: 4px 4px 4px 4px;-webkit-border-radius: 4px; cursor: pointer; }
  </style>
 <body>
 <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
 <ul id="menuMore" class="menuMore" >
     <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
     <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
 </ul>
 <div class="con">
	<form name="toCasherForm" id="toCasherForm" action="<%=basePath%>/ea/buyproducts/ea_toCasher.jspa" method="post" target="_blank">
	<div class="cart" style="height: 120px;">
		<div style="width: 40%;height: 40px; margin-left:10%; line-height:40px; overflow:hidden; font-size:16px; font-weight:bold; float: left">${key_customer.account}</div>
		<div style="width: 50%;height: 40px;line-height:40px; overflow:hidden; font-size:16px; font-weight:bold; float: right">${key_staff.reference }</div>
		<div style="width: 70%;height: 40px; margin-left:10%;line-height:40px; overflow:hidden; float: left">${staffAddress.addressDetailed }</div>
		<div style="width: 20%;height: 40px;line-height:40px; overflow:hidden; float: right"><a href="<%=basePath %>ea/wfjcustomer/ea_getAddress.jspa">修改</a></div>
		
		<div style="width: 90%;height: 35px; margin-left:10%;line-height:40px; overflow:hidden; float: left">
		支付方式：<select id="sfirst" name="sfirst" onchange="changeway(1)"><option value="00">在线支付</option><option value="01">订单购买</option></select>
		<select id="ssecond" name="ssecond" onchange="changeway(2)" style="display:none" disabled="disabled" ><option value="00">在线支付</option><option value="01">现金支付</option></select>
		<select id="sthird" name="sthird" onchange="changeway(3)" style="display:none" disabled="disabled"><option value="00">立即支付</option><option value="01">货到付款</option></select>
		<select id="ssecond2" name="ssecond2" onchange="changeway(22)" ><option value="00">微信支付</option><option value="01">支付宝支付</option><option value="02">银联支付</option></select>
		</div>
		
	</div>
    <div class="clear"></div>
	<%-- 循环session,显示全部购物车商品 --%>	
	
	<c:forEach var="cartItem" items="${key_cart_map}">
	    <div class="cart fl">
	        <div class="cartHeader">
	            <div class="userName fl"><a class="black" href="javascript:;">${cartItem.value.orgnizationName}</a> </div>
	            <div class="editor fr"><a class="black" href="javascript:;" id="edit" name="edit">编辑</a></div>
	        </div>
	        <div class="clear"></div>
	        <div class="cartCon">
	            <div class="selectImg fl">
	                <div class="select selectdis fl"><input type="checkbox" name="chkPid" class="checkb" value="${cartItem.value.pid}"></div>
	            </div>
	            <div class="cartContents fl">
	                <img alt="" title="" src="<%=basePath %>${cartItem.value.pic}"/>
	                <p class="black">${cartItem.value.pname}</p>
	                <d></d>
	                <d></d>
	            </div>
	            <div class="cartPrice fl">
                	<p id="price" name="price">￥${cartItem.value.price}</p>
               	 	<p id="p1" name="p1">×1</p>
               	 	<span>+</span><span>-</span>
               	 	<input type="hidden" value="${cartItem.value.invenQuantity }"/>
	            </div>
	             
	        </div>
	        <div class="clear"></div>
	        <div class="allPrice"><d  id="all">共1件商品</d><d>合计：￥</d><span id="money">${cartItem.value.price}</span></div>
	        <input class="cartItem" type="hidden" value="${cartItem.value.pid }"/>
	    </div>
	    <div class="clear"></div>
	    
    </c:forEach>
    <select name="notSelectPid" id="notSelectPid"  multiple="multiple" style="display:none;"></select>
    <input type="hidden" id="priceSum" name="priceSum"/>
    <input type="hidden" id="baseUrl" name="baseUrl" value="<%=basePath%>"/>
    <input type="hidden" id="WIDout_trade_no" name="WIDout_trade_no" value="${key_customer.account}"/>
    <input type="hidden" id="WIDsubject" name="WIDsubject" value="${key_customer.account}"/>
    </form>
    <%-- 循环session,显示全部购物车商品 --%>

	<div class="cartFloor">
         <div class="cartConfirm">
		 	<div class="selectImg fl">
				<div class="select selectdis fl"><input type="checkbox" id="allcheck" class="checkb" value="1" onclick="mm(this)"></div>
			</div>
            <div class="confirm fr"><a href="javascript:toCasher();">提交订单</a></div>
              <d class="fr">￥</d>
            <span class="fr">合计：</span>
		</div>
	</div>
	<div class="clear"></div>
 </div>
</body>
  <script type="text/javascript" src="<%=basePath %>js/WFJClient/topMore.js"></script>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
	<script type="text/javascript">
    var basePath = '<%=basePath%>'; 
    $('#indexTop').load(function () {
 		var doc=document.getElementById("indexTop").contentWindow.document;
 		doc.getElementById("topbar_title").innerHTML = "购物车";
 	});	    
   $(function(){
    	// 文档就绪
		function addPrice(){
			var max=0;
			var $check=$(".cart div.cartCon input");
			for(var i=0;i<$check.length;i++){
				if($check[i].checked==true){
					var allPrice=parseInt($($check[i]).parents(".cart").find("div.allPrice span").text());
					max+=allPrice;
				}
			}
			$("div.cartFloor div.cartConfirm>d").text("￥"+max);
			$("#priceSum").val(max);			
		}
		addPrice();
	   $(".cart div.cartCon input").click(function(){
			addPrice();
	   });
	   $("#allcheck").click(function(){
			addPrice();
	   });
       $(".cart div.cartHeader div.editor a").click(function(){
            if($(this).text()=="返回"){
            	 $(this).parents("div.cart").find("div.cartPrice span").hide();
                 $(this).text("编辑");
                 return;
                }
            $(this).parents("div.cart").find("div.cartPrice span").show();
            $(this).text("返回");
        });
        $(".cart div.cartPrice span:even").click(function(){
            var count=parseInt($(this).parent("div.cartPrice").find("p").last().text().substring(1));
            count++;
            var num= $(this).parent("div.cartPrice").find("input").val();
            <%--if(count>num){
				alert("库存不足"+count+"件！");
            	return;
            }暂不判断库存 --%>
            $(this).parent("div.cartPrice").find("p").last().text("×"+count);
            var price= parseInt($(this).parent("div.cartPrice").find("p").first().text().substring(1));
            $(this).parents("div.cart").find("div.allPrice span").text(count*price);
            $(this).parents("div.cart").find("div.allPrice d").first().text("共"+count+"件商品");
            addPrice();
			//
			var content = $(this).parents("div.cart").find("input.cartItem").val();					
			$.ajax({
	         	type: "POST",
	         	url:  basePath + "ea/buyproducts/ea_updateCartItem.jspa",
	         	data: {           
	         			"pid":content,
	         			"itemnum":count
	         		  }, 
	         	dataType:"text",
	         	success:function(data)
	         	{	         		
	         		//alert("内容保存完毕.");
	         	},
	         	error:function(data)
	         	{
	         		alert("系统发生异常,请联系管理员.");	         		
	         	}
		 });
     });
     $(".cart div.cartPrice span:odd").click(function(){
         var count=parseInt($(this).parent("div.cartPrice").find("p").last().text().substring(1));
         if(count>1){
             count--;
         }
         $(this).parent("div.cartPrice").find("p").last().text("×"+count);
         var price= parseInt($(this).parent("div.cartPrice").find("p").first().text().substring(1));
         $(this).parents("div.cart").find("div.allPrice span").text(count*price);
         $(this).parents("div.cart").find("div.allPrice d").first().text("共"+count+"件商品");
         addPrice();

        //
		var content = $(this).parents("div.cart").find("input.cartItem").val();					
		$.ajax({
	         	type: "POST",
	         	url:  basePath + "ea/buyproducts/ea_updateCartItem.jspa",
	         	data: {           
	         			"pid":content,
	         			"itemnum":count
	         		  }, 
	         	dataType:"text",
	         	success:function(data)
	         	{	         		
	         		//alert("内容保存完毕.");
	         	},
	         	error:function(data)
	         	{
	         		alert("系统发生异常,请联系管理员.");	         		
	         	}
		 });
          
         });
       });
         function mm(o){
            	var a = document.getElementsByName("chkPid");
            	for (var i=0;i<a.length;i++){
               		a[i].checked = o.checked;
            	}
        	}
         //去结算
         function toCasher(){
 
          if(adrss==null||adrss=='')
          {
               alert("填写收货地址");
             return;
          }
         	var myDate = new Date();
			var year = myDate.getFullYear()+"";    
			var month = (myDate.getMonth()+1)+"";    
			var date = myDate.getDate()+"";       
			var time = myDate.getTime()+"";	//获取当前时间(从1970.1.1开始的毫秒数)
			var sum = year+month+date+time;
			var number=$("#WIDout_trade_no").val()+"";
			number+=sum;
			var name=$("#WIDsubject").val()+"";
			name+=sum;
        	if($(".checkb:checked").length==0){
     			alert("请选择产品！");
       			return;
       		}; 	
         	var notSelect = document.getElementsByName("chkPid");
         	$("#notSelectPid").find("option").remove();
         	for (var i=0;i<notSelect.length;i++){
         		if (!notSelect[i].checked){
         			$("#notSelectPid").append("<option value=\"" + notSelect[i].value + "\"></option>");
         		}
         	}
         	$("#notSelectPid").find("option").attr("selected",true);
               document.getElementById("toCasherForm").action = "<%=basePath%>/ea/buyproducts/ea_toCasher.jspa";
			$("#toCasherForm").submit();
         }
         function changeway(ope){
         	switch (ope) {
			case 1://第一层
				if($("#sfirst").val()=="01"){//订单购买
					$("#ssecond").show().removeAttr("disabled");
					
				}else{//在线支付
					$("#ssecond").attr("disabled","disabled").hide();
					$("#sthird").attr("disabled","disabled").hide();
					$("#ssecond2").show().removeAttr("disabled");
				}
				break;
			case 2://第二层
				if($("#ssecond").val()=="01"){//现金支付
					$("#sthird").show().removeAttr("disabled");
				}else{//在线支付
					$("#sthird").attr("disabled","disabled").hide();
					$("#ssecond2").show().removeAttr("disabled");
					
					
				}
				break;
			case 3://第三层
				
				break;
			default:
				break;
			}
         }
     </script>
     

<script type="text/javascript">
//为了跳转页面后又返回上一页的时候付款方式的统一
$().ready(function(){
	$("select[name='sfirst'] option[value='00']").attr('selected','selected');
});
</script>
</html>
