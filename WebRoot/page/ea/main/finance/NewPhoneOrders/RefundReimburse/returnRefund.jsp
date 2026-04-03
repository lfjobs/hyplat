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
    <title>仅退款</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		var cashid="${cashId}";
		var staffid="${staffid}";	
		var tp ="${tp}";
	</script>
</head>

<body>

<!--弹出层  -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
	    <div>
	       <span style="position: relative;top: 19.8%;z-index:100;"></span>
	    </div>
    </center>
</div>
                
<form enctype="multipart/form-data" id="SearchForm" name="SearchForm" method="post" action="">
	<input type="submit" id="submit" style="display:none;"/>
	<div class="header">
	    <ul>
	        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
	        <li style="width: 80%;text-align: center;">仅退款</li>
	        <li style="width: 10%"></li>
	    </ul>
	</div>
	
	<div class="content_hidden">
		
		    <div class="content  app_content">		        
		        <div class="mil">
		            <span>退货原因</span>
		            <p id="tkyy"><span>请选择退款原因</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right.png" alt=""></a></p>
		        </div>		        
		        <div class="mil">
		            <span>退款金额(&yen;)</span>
		            <p><span id="money">${money}</span></p>		           
		        </div>
		        <div class="mil">
		            <textarea rows="4" placeholder="退款说明" id="account" onkeyup="checkLen(this)"></textarea>		            
		       		<div id="sum" style="float: right;" >您还可以输入 <span id="count">50</span> 个文字</div>
		        </div>
		    </div>
		    <div class="btn_2">
		            <input type="button" value="提交申请" id="tijiao">
		    </div>		    
	</div>
	
	<div class="alert_sh">	    
	    <ul class="tkyy alert_">
	        <li>不喜欢/不想要<div><h5></h5></div></li>
	        <li>为按约定时间发货<div><h5></h5></div></li>
	        <li>快递/物流一直没有送到<div><h5></h5></div></li>
	        <li>快递/物流无跟踪记录<div><h5></h5></div></li>
	        <li>其他<div><h5></h5></div></li>	 	        
	    </ul>	    
	</div>

</form>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".btn_2").css("height",$(window).height()*0.1+"px");
        $(".content").css("height",$(window).height()*0.82+"px");

        //弹出层
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.10+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

        //退款类型
        $(".alert_sh").click(function(){
            $(".alert_sh").hide();
            $(".alert_sh .tkyy").hide();
            $(".alert_sh .tklx").hide();
        });
        //退款类型
        $("#tklx").click(function(){
            $(".alert_sh").show();
            $(".alert_sh .tklx").show();
        });
        $(".alert_sh .tklx li").click(function(){
            var txt=$(this).text();
            $(this).addClass("active").siblings().removeClass("active");
            $(".alert_sh").hide();
            $("#tklx span").text(txt);
        });
        //退款原因
        $("#tkyy").click(function(){
            $(".alert_sh").show();
            $(".alert_sh .tkyy").show();
        });
        $(".alert_sh .tkyy li").click(function(){
            var txt=$(this).text();
            $(this).addClass("active").siblings().removeClass("active");
            $(".alert_sh").hide();
            $(".alert_sh .tkyy").hide();
            $("#tkyy span").text(txt);
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
		var basePath = "<%=basePath%>";
		$("#tijiao").click(function(){
			
			var type = $("#tklx").text();//退款类型
			var reason = $("#tkyy").text();//退款原因
			var money = $("#money").text();//退款金额
			var account = $("#account").val();//退款说明	
			
			if(reason == "请选择退款原因"){
				prompt("请选择退款原因");
			}else if(account.length == 0 || account.match(/^\s+$/g)){
				prompt("请填写退款说明！");
				$("#account").val(null);
			}else if(confirm("你确定要提交吗?")){
				var url= basePath +"/ea/refundMoney/ea_getReturnRefund.jspa?cashId="+cashid+"&staffid="+staffid+"&type="+type+"&reason="+reason+"&money="+money+"&account="+account+"&tp="+tp;				
				$("#SearchForm").attr("action",url);
				$("#submit").click();
			}								
		});							
	});
	
	//文本框输入字数限定
	function checkLen(obj){
		var maxChars = 50;//最多字符数
		if (obj.value.length > maxChars){
			obj.value = obj.value.substring(0,maxChars);
		}		
		var curr = maxChars - obj.value.length;
		document.getElementById("count").innerHTML = curr.toString();		
	}
	
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

