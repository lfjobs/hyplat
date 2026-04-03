<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>退货详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var cashid="${refundSheet.cashierBillsID}";	
	var staid = "${staid}";
	var tp = "${tp}";
</script>

</head>

<body>
<div class="header">
    <ul><li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        <li style="width: 80%;text-align: center;">退货详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content rec_content det_content">
        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/banner2.png" alt="" width="100%">     
        <div class="mil_det" id="show">
            <ul id="wl">
                <li style="width: 10%;text-align: left;"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-car.png" alt=""></li>
                <li style="width: 85%;" >  
					<c:choose>
						<c:when test="${wllist[fn:length(wllist)-1][1]==null }">
							 <p>暂无物流信息</p>
                    		 <h5></h5>
						</c:when>        
        				<c:otherwise>
        					<p>${wllist[fn:length(wllist)-1][1] }</p>
                    		<h5>${wllist[fn:length(wllist)-1][0] }</h5>
        				</c:otherwise>       
       				 </c:choose>                  
                </li>
                <li style="width: 5%;text-align: right;"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right.png" alt=""></li>
            </ul>
            <s:iterator value="#request.rlist" var="r">
            <ul class="det">
                <li style="width: 10%;text-align: left;"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-left.png" alt=""></li>
                <li style="width: 85%;">
                    <h3>${refundSheet.receiverName }</h3>
                    <span class="tel">${refundSheet.receiverTel }</span>
                    <div style="clear: both;"></div>
                    <p>退货地址：<span>${refundSheet.refundAddress }</span></p>
                </li>
            </ul>
            </s:iterator>
        </div>
    
        <div class="company">
            <div class="left img">
                <img src="<%=basePath %>${pb.companyLogo}" alt="">
            </div>
            <div class="txt">
                <span>${pb.companyName }</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right2.png" alt=""></a>
            </div>
        </div>
       <s:iterator value="#request.pb.goodsList" var="pf">      
        <div class="shop_mil" onclick="goodsDetail('${pf[7] }','${pf[10] }','${pf[12] }')">
            <div class="left">
                <img src="<%=basePath %>${pf[6]}" alt="">
            </div>
            <div class="txt">
                <h3>${pf[5] }</h3>
                <h4>产品规格：<span>${pf[11] }</span></h4>
                <h4>备注：<span></span></h4>
            </div>
            <div class="txt2">
                <h3>&yen;<span>${pf[3] }</span></h3>
                <h4>x<span>${pf[2] }</span></h4>
            </div>
        </div>
        </s:iterator>
        
        <!-- 促销品 -->
        <s:iterator value="#request.pb.ptgoodsList" var="pg">
          	<div class="shop_mil" onclick="goodsDetail('${pg[1] }','${pg[7] }','${pg[3] }')">
            	<div class="left">
               	<img src="<%=basePath %>${pg[6]}" alt="">
            	</div>
            	<div class="txt">
                	<h3>${pg[2] }</h3>
                	<h4>产品规格：<span>${pg[5] }</span></h4>
            	</div>
            	<div class="txt2">
                	<h3><span></span></h3>
                	<h4>x<span>1</span></h4>
            	</div>
            	<img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-cu.png" class='cu'>
        	</div>       
        </s:iterator>
        
        <div class="mon">

            <div class="btn">   
            	<input id="rf" type="button" value="请退货">                                            
              
            </div>
           
                              
            <div class="txt">
            <c:if test="${refundSheet.refundNum }!=null">
            	<h5>退货数量<p><span>${refundSheet.refundNum }</span></p></h5>
            </c:if>             	
                <h5>退款金额<p>&yen;<span>${refundSheet.refundMoney }</span></p></h5>
                <h5>申请时间<p><span>${refundSheet.refundDate }</span></p></h5>                
            </div>
            
        </div>
        
        <!-- 促销品 -->
        <div class="so-much">
            <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/so-much.png" alt="">
        </div>
        <div class="so_shop">
            <ul>
            	<s:iterator value="#request.cplist" var="cp">
                <li onclick="goodsDetail('${cp[0]}','${cp[16]}','${pb.companyid }')">
                    <img src="<%=basePath %>${cp[5]}" alt="">
                    <div class="txt">
                        <h4>${cp[2]}</h4>
                        <p>&yen;<span>${cp[4]}</span></p>
                    </div>
                </li>
             </s:iterator>
            </ul>
        </div>
    </div>
     
</div>
<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92-40+"px");
        $(".so_shop ul li img").css("height",$(".so_shop ul li img").width()+"px");
    	load(cashid);
    })
    
    function load(cashid){
    	var url = basePath+"ea/refundMoney/sajax_check.jspa?cashId="+cashid;
    	$.ajax({
    		url : url,
			type : "get",
			async : false,
			dataType : "json",
    		success : function(data){
    			var member = eval("("+data+")");
    			var type = member.type;
    			    			    		
    			if(type==0){//买家申请退货
    				$("#rf").val("申请中");
    				$("#show").hide();
    			}else if(type == 1){//卖家同意退退货
    				$("#rf").val("请退货");
    				$("#show").hide();
    			}else if(type==2){//买家退货中
    				$("#rf").val("退货中");
    			}else if(type==3){
    				$("#rf").val("退货结束");
    			}else if(type==5){//买家退款中
    				$("#rf").val("退款中");
    			}else if(type==6){
    				$("#rf").val("退款结束");
    			}else{
    				$("#rf").val("商家拒绝");
    				$("#show").hide();
    			} 			   			
    		}   		
    	});     	        	
    }
</script>

<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
    function goodsDetail(ppid,goodsid,companyId){
    	window.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId;
    }
</script>



</body>
 <script type="text/javascript">
	
	$(document).ready(function(){
		var basePath = "<%=basePath%>";
	$("#rf").click(function(){
		var rf = $("#rf").val();
		if(rf=="申请中" || rf=="退款中" || rf=="退款结束"){
			document.location.href=basePath+"/ea/refundMoney/ea_getReturnRefund.jspa?cashId="+cashid+"&tp="+tp+"&staffid="+staid;
		}else if(rf=="请退货"){
			document.location.href=basePath+"/ea/refundMoney/ea_gainAddress.jspa?cashId="+cashid;
		}else if(rf=="商家拒绝"){
			document.location.href=basePath+"/ea/refundMoney/ea_getReturnRefund.jspa?cashId="+cashid;
		}else{//退货中   退货结束     跳转到物流页降价面
			document.location.href=basePath+"/ea/refundMoney/ea_view.jspa?cashId="+cashid;
		}														
	});
	
	$("#wl").click(function(){
		document.location.href=basePath+"/ea/refundMoney/ea_view.jspa?cashId="+cashid;
	});		
});
</script> 
</html>
