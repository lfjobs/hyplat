<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>注册</title>

    <link href="<%=basePath %>myPC/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>myPC/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>myPC/js/respond.min.js"></script>
    <![endif]-->
   <script type="text/javascript">
var basePath='<%=basePath%>';
	var i;
	var c = 1;
	var d = 1;
	var q = 0;
	var times = 59;

	var ttoken = 0;
</script>
</head>
<body  style="background: #fff">
    <div id="header" class="login_header">
        <ul>
            <li class="logo">
                <a id="index"><img src="<%=basePath %>myPC/images/wfj.png" alt="" class="log"></a>
            </li>
            <li class="name login_name">
                <div>
                    <h3>账户登录注册</h3>

                </div>
            </li>
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区">
            <li class="login login_login">
                <img src="<%=basePath %>myPC/images/return2.png" alt="">
                <div>
                    <h3>返回首页</h3>
                </div>
            </li>
            </a>
        </ul>
    </div>
    <div class="content login_con refg_con">
        <form class="fot_frm" id="myform" name="myform" method="post">
            <h3>账户注册</h3>
            <input type="hidden" name="weidiantype" value="${weidiantype }" />
			<input type="hidden" name="user" value="15810799888" /> 
            
            <div>
                <span>姓名：</span>
                <input type="text" id="name" name="staff.staffName">
            </div>
            <div>
                <span>密码：</span>
                <input type="password" id="password">
            </div>
            <div>
                <span>确认密码：</span>
                <input type="password" id="password2">
            </div>
            <div>
                <span>手机号：</span>
                <input type="tel" id="count" name="phones" onblur="isshouji()">
            </div>
            <div>
                <span>验证码：</span>
                <input type="text" id="yzms" class="yzm" onblur="yanz()" >
                <div onclick="duanxin()" id="huoquyzm">获取验证码</div> 
            </div>
            <label><input type="checkbox" checked="checked" id="agree">&nbsp;<span>已阅读并接受</span><a href="#;">《微分金用户协议》</a></label>
            <div id="zc"><input type="submit" value="注  册" onclick="sut()"></div>
        </form>
    </div>
   
<script>
    $(document).ready(function(){
	
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

		$("#index").click(function(){
            var url = basePath+"ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
            document.location.href = url;
        });
       
        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {

                $(".return").hide();

            }
        });
    })
</script>
<script >

function yanz(){
   	if(q==0){
   		alert("请先获取验证码");
           return;
       }
       if($("#yzms").val().length<6){
       	alert("验证码不能小于六位数");
           return;
       }
       if($("#yzms").val()==''){
				alert("请填写验证码");
				c=1;
				return;
			}
			if($("#yzms").val()!=i){
 			alert("验证码不正确");
 			c=1;  
 			return;    			     	
 		}else{
 			c=0;
 		}
 
 }

    
	function sut(){

		if($("#name").val()==""){
			alert("请输入姓名");
			$("#name").focus();
			return;
		}
		if($("#count").val()==""){
			alert("请输入注册手机号");
			$("#count").focus();
			return;
		}
		
	    if(c==1){
			alert("请先手机验证");
 			return;
 		}
 		if(d==1){
 			if(c==1){
     			alert("请先手机验证");
 				return;
 			}
 		}
		


 		if($("#password").val()!=$("#password2").val()){
 			alert("两次密码不一致，重新输入");
 			$("#password").focus();
 			return;
 		}

 		if($("#password").val().length<6){
 			alert("密码长度不安全");
 			return;
 		} 
 	
       
 		if(ttoken==1){
 			return false;
 		}
 		ttoken = 1;

		$("#myform").attr("action",basePath+"/ea/wfjshop/ea_seves.jspa");
		$("#myform").submit();
}

function isshouji(){
	var count=$("#count").val();
	if(count!=''){
		$.ajax({
	    	  cache : true, 
	    	  type :"POST",
	    	  url : basePath+"/ea/android/sajax_ea_isacounnt.jspa?pahe="+count,
	    	  async :false,
	    	  dataType : "json",
	    	  success :function(data){
	    	   	var member = eval("(" + data + ")");
	    		if(member.result==0){
	    	   		 d=1;
	    		}
	    		else{
	    		   alert("已被注册,请更换手机号码！");

	    		   $("#count").val("");
	    		   $("#count").focus();
	    		   d=2;
	    		   return;
	    		}	
	    	 }	    	   
		  		}); 
	}else{
		$("#count").val("");
	}
}



function duanxin(){
		var count=$("#count").val();
      if(times<59)
		{
			alert("请稍后");	
			      return;
		}
      if(count=="")
      {
          alert("手机号为空");
      return;
      }
      var reg=/^\d{11}$/;
  
      if(!reg.test(count))
      {
        alert("手机号码输入错误");
          return;
      }
   		  update(times);
   		   q=1;
      		$.ajax({
    	  cache : true, 
    	  type :"POST",
    	  url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+count,
    	 	
    	  async :false,
    	  dataType : "json",
    	  success :function(data){
    		  var member = eval("(" + data + ")");
    				i= member.returna;
    				
    	 	    }	    	  
	  		});
}




function update(num) {
	if(num>0){
		$("#huoquyzm").text("已发送（"+num+"）");
		$("#huoquyzm").css("cursor","not-allowed");
		$("#huoquyzm").css("backgroundColor","#999");
		$("#huoquyzm").css("color","#FFF");
        times = num;
		setTimeout(function(){
				update(num-1);
		},1000);
	}else{
		$("#huoquyzm").css("cursor","pointer");
		$("#huoquyzm").css("backgroundColor","#ff6600");
		$("#huoquyzm").css("color","#FFF");
		$("#huoquyzm").text("获取验证码");
		times=59;
	}
}


</script>
</body>
</html>