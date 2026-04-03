<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
    <title>找回密码</title>

    <link href="<%=basePath %>page/newMyapp/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
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
                                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>page/newMyapp/images/wfj.png" alt="" class="log"></a>

                <img src="<%=basePath %>page/newMyapp/images/wfj.png" alt="" class="log"></a>
            </li>
            <li class="name login_name">
                <div>
                    <h3>找回密码</h3>

                </div>
            </li>
            <a id="index">
            <li class="login login_login">
                <img src="<%=basePath %>page/newMyapp/images/return2.png" alt="">
                <div>
                    <h3>返回首页</h3>
                </div>
                <!--<input type="button" value="登录">
                <input type="button" value="注册">-->
            </li>
            </a>
        </ul>
    </div>
    <div class="content login_con refg_con">
       <form class="fot_frm" id="myform" name="myform" method="post">
            <h3>找回密码</h3>
            <div>
                <span>手机号：</span>
                <input type="tel" id="count" name="phones">
            </div>
            <div>
                <span>新密码：</span>
                <input type="password" id="password">
            </div>
            <div>
                <span>确认密码：</span>
                <input type="password" id="password2">
            </div>
           <div>
                <span>验证码：</span>
                <input type="text" id="yzm" onblur="yanz()">
                <div onclick="duanxin()" id="huoquyzm">获取验证码</div> 
            </div>
            <div id="zc"><input type="submit" value="确  定"></div>
        </form>
    </div>
   

<script>
    $(document).ready(function(){
		var basePath = "<%=basePath%>";
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
		$("#index").click(function(){
            var url = basePath+"page/newMyapp/index.jsp";
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
<script>
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


</script>
</body>
</html>