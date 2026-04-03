<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>个人信息	维护</title>
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
	<style type="text/css">
		.tex{color: red;font-size: 12px;}
	</style>
  </head> 
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	<div class="con">
      <div class="customerInfo">
      <form action="<%=basePath %>/ea/wfjcustomer/ea_savePersonalInfor.jspa" method="post" id="submit">
        
        <div class="info fl">
            <div class="optionsNav fl">账号</div>
            <div class="optionsCon fl">${customer.account}</div>
        </div>
        <div class="clear"></div>
       
        <div class="info fl">
            <div class="optionsNav fl">姓名</div>
            <div class="optionsCon fl">
            	<span id="N1">${staff.staffName }</span> 
            	<input type="text" name="staff.staffName" id="N2" value="${staff.staffName }" class="yname staffName" style="display: none;"/>
            </div>
            <div class="optionsOper fl"><p><a href="javascript:;" id="N3">修改</a> </p></div>
        </div>
       
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"> 地址</div>
            <div class="optionsCon fl"><span>${staffAddress.addressDetailed }</span><!-- <span>${sessionScope.key_address}</span> --></div>
            <div class="optionsOper fl"><p><a href="<%=basePath %>ea/wfjcustomer/ea_getAddress.jspa">修改</a> </p></div>
        </div>
        <div class="clear"></div>       
            
        </form>
        <div class="clear"></div>
        <div class="customerButton"><a href="javascript:;" id="reg"> 确定</a></div>
        <div class="clear"></div>
         <div class="info fl">
    		<span class="error" style="color:red;"><a class="tex" id="aa"></a></span>
         </div>
     </div>
	</div>
  </body>
  <script type="text/javascript">
  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML="个人信息";
		doc.getElementById("return").onclick=function(){
			window.history.go(-1);
		}
	});
     document.getElementById("N3").onclick=function(){
    	 document.getElementById("N1").style.display="none";
    	 document.getElementById("N2").style.display="block";
	 }
     document.getElementById("R3").onclick=function(){
    	 document.getElementById("R1").style.display="none";
    	 document.getElementById("R2").style.display="block";
	}
     document.getElementById("P3").onclick=function(){
    	 document.getElementById("P1").style.display="none";
    	 document.getElementById("P2").style.display="block";
	}
     document.getElementById("G3").onclick=function(){
    	 document.getElementById("G1").style.display="none";
    	 document.getElementById("G2").style.display="block";
	}
     
  
  </script>
  <script type="text/javascript">
  $(document).ready(function() {	 
      $("#reg").click(function () {
		if($(".password").val().length < 6 || $(".password").val().length >20 || $(".password").val()==''){
			$("#aa").text("密码应该为6-20位之间");
			return;
			}
		var staffname=$(".staffName").val();
		var reg =/^[\u4e00-\u9fa5]{2,64}$/
		if(staffname.length <1 || staffname.length >5 || staffname=='' || !reg.test(staffname)){
			$("#aa").text("请输入您的中文姓名！");
			return;
			}
        var cardId=$(".staffIdentityCard").val();
        reg1 = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
        if (cardId == "" || !reg1.test(cardId)){
        	 $("#aa").text('请输入正确的身份证号码！');
        	 return;
             }
        var phone=$(".cellphone").val();
        reg2 = /^1\d{10}$/;
		if (phone == "" || !reg2.test(phone)) {
			  $("#aa").text('请输入正确的手机号码！');
			  return;
			  }    	 
 		$("#submit").submit();
          
   	});
	});
  
  </script>
</html>
