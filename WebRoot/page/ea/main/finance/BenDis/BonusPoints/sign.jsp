<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">   
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/BonusPoints/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/BonusPoints/style.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/BonusPoints/jquery-1.9.1.min.js"></script>	
	<script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var sccid="${sccid}";
		var staffid="${staffid}";	
		var account = "${account}";
		var tosign = "${toSign}";//判断用户是否签到过  yes 签过了    no  没有签到				
		var ccompanyId = "${ccompanyId}";//往来单位	
		var khd = "${khd}"
	</script>
	<title>签到</title>
</head>
<body>
<div class="content_sign">
	
	<header>
        <%-- <a href="<%=basePath%>/ea/bonuspoints/ea_getbpDetail.jspa?sccid=${sccid}&account=${account}&staffid=${staffid }&khd=0">
        <img src="<%=basePath%>images/ea/finance/BonusPoints/left_jt.png" alt=""></a> --%>
    </header>

    <img src="<%=basePath%>images/ea/finance/BonusPoints/bg.png" class="bg">
    <div class="btn">
        <img src="<%=basePath%>images/ea/finance/BonusPoints/yes.png" alt="" class="yes">
        <img src="<%=basePath%>images/ea/finance/BonusPoints/no.png" alt="" class="no">
    </div>
    <div class="text">
        <p>当前积分：<span class="nub">${bp.bonusPointScore==null?0:bp.bonusPointScore }</span></p>
        <h5>注意：一个手机号每天只能签到一次</h5>
    </div>
	
    <!--签到成功弹框-->
    <div class="alert_succeed_"></div>
    <div class="alert_succeed">
        <img src="<%=basePath%>images/ea/finance/BonusPoints/x.png" alt="" class="x">
        <p>恭喜~签到成功</p>
        <h5 id="jifenNum">已赠送100积分</h5>
    </div>
</div>

<script>
    $(document).ready(function(){
    	
    	if(tosign == "yes"){
    		$(".yes").hide();
			$(".no").show();
    	}
    	
    	if(khd == "0"){
    		var str = new Array();
    		str.push('<a href="'+basePath+'ea/bonuspoints/ea_getbpDetail.jspa?sccid=${sccid}&account=${account}&staffid=${staffid }&khd=0">');
    		str.push('<img src="'+basePath+'images/ea/finance/BonusPoints/left_jt.png" alt="">');
    		str.push('</a>');
    		$("header").html(str.join(""));
    		
    	}
    
    	$(".content_sign").attr("style","height:"+$(window).height()*1+"px;");
        var num = $(".content_sign .text .nub").text();             
        $(".alert_succeed_").click(function(){
            $(".alert_succeed_").hide();
            $(".alert_succeed").hide();
            $(".content_sign .btn .yes").hide();
            $(".content_sign .btn .no").show();
        });
        $(".content_sign .btn .yes").one("click",function(){  
        	var url = basePath + "/ea/bonuspoints/sajax_toSign.jspa?sccid="+sccid+"&staffid="+staffid+"&account="+account+"&ccompanyId="+ccompanyId;
        	$.ajax({
        		url : url,
    			type : "get",
    			async : false,
    			dataType : "json",
    			success : function (data) {	   				
    				var flag = data.flag;
    				var companyname = data.companyname;
    				var jiFenNum = data.jiFenNum;
    				if(flag == "签到成功"){ 
    					 $("#jifenNum").text("已赠送"+jiFenNum+"积分");
    					 var num2=parseInt(num)+parseInt(jiFenNum);    					 
    					 $(".content_sign .text .nub").text(num2);
    					 $(".alert_succeed_").show();           
    			         $(".alert_succeed").show();    			         
    			         var u = window.navigator.userAgent;
   						 var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
   						 var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
   						 if(isAndroid==true){
   							Android.方法名(companyname,jiFenNum);
   						 }else if(isiOS==true){
   							 var url= "func=" + 'iossignmark';        
	       	                 params={'companyname':companyname,'signNum':jiFenNum}; 
	       	                 for(var i in params){
       	                     	url = url + "&" + i + "=" + params[i];
       	                  	  }   
       	                      window.webkit.messageHandlers.Native.postMessage(url); 
   						 }  						  						    			            			            			         
    				}else{
    					alert(flag);
    				}   				    				
    			}        	
        	});        	          
        });
        //签到成功弹框
        $(".alert_succeed .x").click(function(){
            $(".alert_succeed_").hide();
            $(".alert_succeed").hide();
            $(".content_sign .btn .yes").hide();
            $(".content_sign .btn .no").show();
        });

    });
</script>

</body>
</html>