<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>退款退货详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/> 
   <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
  
  <script type="text/javascript"> 
  	var status = '${refundSheet.refundstate}';
  	var tp = '${tp}';//仅退款（退货退款）区别   00 仅退款       01 退货退款
  </script>
  
  
</head>
<body>
<div class="header">
<ul>
	<li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
	<li style="width: 80%;text-align: center;">退货退款</li>
	<li style="width: 10%"></li>
</ul>
</div>

<div class="content_hidden">
    <div class="content  app_content">
        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/tijiao.png" alt="" width="100%">
				<div class="tj_txt">
					<ul>
						<li>
							<h3>退货数量</h3><p><span>${refundSheet.refundNum }</span></p>
						</li>
						<li>
							<h3>退款金额</h3><p class="mon_">&yen;<span>${refundSheet.refundMoney }</span></p>
						</li>
						<li>
                    <h3>订单编号</h3><p><span>${refundSheet.orderCode }</span></p>
                </li>
                <li>
                    <h3>退回账号</h3><p><span>${refundSheet.userAccount }</span></p>
                </li>
            </ul>
        </div>
        <div class="tk_flow" id="div">           
            <c:choose>
            	<c:when test="${refundSheet.refundstate=='02' }">
            		<h3>卖家拒绝退货，请联系卖家！</h3>        	
            	</c:when>
            	<c:otherwise>
            	 <h3>申请流程</h3> 
            		<ul>
		                <li>
		                    <div class="yuan" id="a">1</div>
		                    <div class="txt">
		                        <h4 id="ah">申请已提交</h4>
		                        <p id="ap">您的退货申请已成功提交，等待商家同意</p>
		                    </div>
		                </li>
		                <li>
		                    <div class="yuan" id="b">2</div>
		                    <div class="txt">
		                        <h4 id="bh">申请通过，请尽快寄回退货商品</h4>
		                        <p id="bp">您的退货申请已受理，寄回退货商品。</p>
		                    </div>
		                </li>
		                <li>
		                    <div class="yuan" id="c">3</div>
		                    <div class="txt">
		                        <h4 id="ch">退货商品物流中</h4>
		                        <p id="cp">等待商家确认收货。</p>
		                    </div>
		                </li>
		                <li>
		                    <div class="yuan" id="d">4</div>
		                    <div class="txt">
		                        <h4 id="dh">申请处理中</h4>
		                        <p id="dp">您的退货商品已收到，申请正在处理中。</p>
		                    </div>
		                </li>               
		                <li>
		                    <div class="yuan" id="e">5</div>
		                    <div class="txt">
		                        <h4 id="eh">申请受理完成</h4>
		                        <p id="ep">审核完成，退款会在1-5个工作日内退至您的微分金账户。</p>
		                    </div>
		                </li>
		            </ul>           	           	
            	</c:otherwise>                       
            </c:choose>                      
        </div>     
    </div>
</div>
<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");

    })
</script>

<script>
    $(document).ready(function(){      
        if(status == '00'){//申请已提交
        	$("#a").css("background","#ffa500");
        	$("#ah").css("color","#3d3d3d");
        	$("#ap").css("color","#666");
        }else if(status == '01'){//审核通过
        	$("#a").css("background","#ffa500");
        	$("#ah").css("color","#3d3d3d");
        	$("#ap").css("color","#666");
        	$("#b").css("background","#ffa500");
        	$("#bh").css("color","#3d3d3d");
        	$("#bp").css("color","#666");
        }else if(status == '03'){//商品物流中
        	$("#a").css("background","#ffa500");
        	$("#ah").css("color","#3d3d3d");
        	$("#ap").css("color","#666");
        	$("#b").css("background","#ffa500");
        	$("#bh").css("color","#3d3d3d");
        	$("#bp").css("color","#666");
        	$("#c").css("background","#ffa500");
        	$("#ch").css("color","#3d3d3d");
        	$("#cp").css("color","#666");
        }else if(status == '04'){//卖家确认收货
        	$("#a").css("background","#ffa500");
        	$("#ah").css("color","#3d3d3d");
        	$("#ap").css("color","#666");
        	$("#b").css("background","#ffa500");
        	$("#bh").css("color","#3d3d3d");
        	$("#bp").css("color","#666");
        	$("#c").css("background","#ffa500");
        	$("#ch").css("color","#3d3d3d");
        	$("#cp").css("color","#666");
        	$("#d").css("background","#ffa500");
        	$("#dh").css("color","#3d3d3d");
        	$("#dp").css("color","#666");
        }else{//卖家已银行打款
        	$(".tk_flow yuan").css("background","#ffa500");
        	$(".tk_flow h4").css("color","#3d3d3d");
        	$(".tk_flow p").css("color","#666");
        }  
    });  
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
</script>
</body>
</html>

