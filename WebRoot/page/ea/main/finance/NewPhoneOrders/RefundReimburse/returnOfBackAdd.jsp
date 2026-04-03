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
    <title>买家地址（运单号）</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/> 
   <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
   <script type="text/javascript">
   		var cashId = "${oa.oaBillId }";
   		var staffid = "${staffid }";
   
   </script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        <li style="width: 80%;text-align: center;">退货详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content rec_content app_content">
       
        <div class="tj_txt">        	
            <ul>
                <li>
                    <h3>收件人</h3><p ><span >${refundSheet.receiverName }</span></p>
                </li>
                <li>
                    <h3>收件电话</h3><p><span>${refundSheet.receiverTel }</span></p>
                </li>
                <li>
                    <h3>收件邮编</h3><p><span>${refundSheet.postcode }</span></p>
                </li>
                <li>
                    <h3>收件地址</h3><p><span>${refundSheet.refundAddress }</span></p>
                </li>
            </ul>
        </div>
                
        <div class="tk_flow"><h3 id="txwl">填写物流信息</h3></div>
         <div class="Returns_number">
        <div  class="mil_r">            
            <ul id="number">
                <li><h4>运单号</h4></li>
                <li><input type="text" placeholder="输入运单号" id="num"></li>
            </ul>
        </div>  
            
        <div class="btn_r" id="ret">
             <p><i><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-ret.png"></i>退货</p>
        </div>          
    </div>    
    </div>
</div>

<!--弹出层  -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
	    <div>
	       <span style="position: relative;top: 19.8%;z-index:100;"></span>
	    </div>
    </center>
</div>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");

      	//弹出层
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.10+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

    })
</script>
<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");

        $("#express").click(function(){
            $(".alert_tk").show();
        });
        $(".alert_tk li").click(function(){
            var kd_txt=$(this).text();
            $(this).addClass("active").siblings().removeClass("active");
            $("#express").text(kd_txt);
            $(".alert_tk").hide();
        });
    })
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
<script type="text/javascript">
	$(document).ready(function(){
		
		var basePath = "<%=basePath %>";

		$("#ret").click(function(){				
			
			var num = $("#num").val();						
			var url = basePath + "/ea/refundMoney/sajax_expNo.jspa?expno="+num;
						
			if(num.length == 0){
				prompt("请填写运单号！");					
			}else{
				
				if(confirm(num+"确定运单号填写正确？")){
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					success : function(data){
						var member = eval("("+data+")");						
						var shippers = member.Shippers;						
						if(shippers == ""){
							prompt("填写的运单号错误，请重新填写！");							
							$("#num").val("");
						}else{														
							var code = shippers[0].ShipperCode;
							var name = shippers[0].ShipperName;
							
							if(name="中通速递"){
								name="中通快递";
							}							
							document.location.href= basePath + "/ea/refundMoney/ea_refundOfBack.jspa?cashId="+cashId+"&staffid="+staffid+"&express="+name+"&expno="+num+"&expCode="+code;															
						}								
					}				
				});														
				}
			}				
		});	
		$("#num").click(function(){				
			$("#num").val("");		
		});
		
	});
	
		
	//弹出层
	function prompt(obj){
		if($("#prompt").css("display")!="none"){
			return;
		}
		$("#prompt").find("span").text(obj);
		$("#prompt").fadeIn(500);
		setTimeout(function(){
			$("#prompt").fadeOut(500);
			$("#prompt").find("span").text("");
		}, 2000);				
	}

</script>
</html>

