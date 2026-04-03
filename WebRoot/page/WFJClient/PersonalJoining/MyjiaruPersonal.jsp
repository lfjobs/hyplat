<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员招商</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

  <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/wfjapp.css" type="text/css"></link></head>
  <script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
  <body>
	<div class="wfj11_006">
    	<div class="wfj11_006_top">
        	<div style="width:92%; margin:0 auto;">
        	<div class="wfj11_006_return"><a id="returnClick" href="javascript:history.go(-1)" target="_self"><img
						src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></div>
            <div class="wfj11_006_join">
            	<ul>
                	 <li class="wfj11_006_join_left"><a href="javascript:;">个人加入</a></li>
                	<!--<li class="wfj11_006_join_right"><a href="javascript:;">公司加入</a></li> -->
                </ul>
            </div>
            <div class="wfj11_006_more"><%-- <a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/top_more.png" /></a> --%></div>
            </div>
        </div>
       
        
        <div class="wfj11_006_me">
         	<c:forEach items="${beans}" var="rglist">
        	<table width="100%" class="dianji">
	           	<tr>
	           		
	               	<td width="25%" rowspan="2" class="wfj11_006_busi_left_img"><img src="<%=basePath%>${rglist[3]}" /></td>
	               	<td width="50%">${rglist[0]}</td> 
	            </tr>
	           	<tr>
	               	<td><span style="color:#F74C31;">￥${rglist[2]}</span></td>
	            </tr>
	            <tr style = "display:none;">
		            <td>
		            	<input type="hidden" value="${rglist[1]}"  name="ppid"/>
		            	<input type="hidden" value="${rglist[5]}"  name="goodsid"/>
		            	<input type="hidden" value="${rglist[6]}"  name="companyId"/>
		            	<input type="hidden" value="${ccompanyId}"  name="ccompanyId"/>
		            </td>
	            </tr>
               
            </table>
        	</c:forEach>
        </div>
    </div>
    
    <script type="text/javascript">
    
    $(function(){
    	$(".dianji").click(function(){
    		var ppid = $("[name='ppid']").val();
    		var goodsid = $("[name='goodsid']").val();
    		var companyId = $("[name='companyId']").val();
    		var ccompanyId = $("[name='ccompanyId']").val();
    		document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId;
    	});
    })
    
    var basePath = "<%=basePath%>";
    var zlyq = "${param.zlyq}";
	function name() {
	f
	}
    if(zlyq=="1"){
        $(".wfj11_006_business").css("display","");
	    $(".wfj11_006_me").css("display","none");
		$(".wfj11_006_join_left").css("backgroundColor","#F74C31");
		$(".wfj11_006_join_left").find("a").css("color","#FFF");
		$(".wfj11_006_join_right").css("backgroundColor","#FFF");
		$(".wfj11_006_join_right").find("a").css("color","#F74C31");
		$(".wfj11_006_join_right").css("display","none");
		$(".wfj11_006_join_left").css("display","none");
    
     }
    	$(document).ready(function(e) {
            $(".wfj11_006_join").find("li").click(function(){
					if($(this).text()=="个人加入"){
						$(".wfj11_006_business").css("display","none");
						$(".wfj11_006_me").css("display","");
						$(".wfj11_006_join_left").attr("style","backgroundcolor:#FFF;");
						$(".wfj11_006_join_left").find("a").attr("style","color:#F74C31;font-size:"+$(window).width()*0.05+"px;");
						$(".wfj11_006_join_right").css("backgroundColor","#fff");
						$(".wfj11_006_join_right").find("a").css("color","#666");
					}else if($(this).text()=="公司加入"){
						$(".wfj11_006_business").css("display","");
						$(".wfj11_006_me").css("display","none");
						$(".wfj11_006_join_left").css("backgroundColor","#fff");
						$(".wfj11_006_join_left").find("a").css("color","#666");
						$(".wfj11_006_join_right").attr("style","backgroundcolor:#FFF;");
						$(".wfj11_006_join_right").find("a").attr("style","color:#F74C31;font-size:"+$(window).width()*0.05+"px;");
					}
 					$(this).attr("style","color:#F74C31;border-bottom: 1px solid #f74c31;font-size:"+$(window).height()*0.024+"px;").siblings().attr("style","border-bottom:none;color:#666;");
 
 					
 				});
           
           	$(".wfj11_006_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");			
			$(".wfj11_006_return").attr("style","height:"+$(window).height()*0.08+"px;");
           	$(".wfj11_006_return").find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-top:"+$(".wfj11_006_return").height()*0.3+"px;");
    		$(".wfj11_006_more").attr("style","height:"+$(window).height()*0.08+"px;");
    		$(".wfj11_006_more").find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-top:"+$(".wfj11_006_return").height()*0.25+"px;");
    		/* $(".wfj11_006_join").attr("style","margin-top:"+$(".wfj11_006_top").height()*0.2+"px;"); */ 
    		$(".wfj11_006_business table tr").attr("style","font-size:"+$(window).height()*0.03+"px;");  
    		$(".wfj11_006_business table tr td a").attr("style","color:#000;");   
   			$(".wfj11_006_me table tr").attr("style","font-size:"+$(window).height()*0.03+"px;");  
    		$(".wfj11_006_me table tr td a").attr("style","color:#000;");  
    		
    		
    		/* $(".wfj11_006 .wfj11_006_top .wfj11_006_join .wfj11_006_join_left a").css("padding-bottom",$(window).height()*0.024+"px");	 */ 	
    			 	
    			$(".wfj11_006 .wfj11_006_top .wfj11_006_join li").css("line-height",$(window).height()*0.08+"px");
    			$(".wfj11_006 .wfj11_006_top .wfj11_006_join li a").css("font-size",$(window).width()*0.05+"px");
    			$(".wfj11_006_me td a").css("font-size",$(window).width()*0.05+"px");
    			$(".wfj11_006_business td a").css("font-size",$(window).width()*0.05+"px");
    	
    	
    	   	var u = window.navigator.userAgent;
        	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        	if(isAndroid==true){		
        		var obj = document.getElementById("returnClick");
        		obj.setAttribute("href","#");
        		//obj.setAttribute("onclick", "javascript:Android.callAndroidjianli()");  
        		obj.setAttribute("onclick", "retAndroid()");
        	}else if(isiOS==true){   		
        		var obj = document.getElementById("returnClick");
        		obj.setAttribute("href","#");        		
        		obj.setAttribute("onclick", "retIOS()");        	
        	}
    		
    	});
        //安卓，苹果返回
        function retAndroid(){
        	try{       		
        		Android.callAndroidjianli();
        	}catch(err){
     			$("#returnClick").removeAttr("onclick");
     			$("#returnClick").attr("href","javascript:history.go(-1)");
        	}
        }
     	function retIOS(){
     		try{
     			calupgraded('');
     		}
     		catch(err){    		
     			$("#returnClick").removeAttr("onclick");
     			$("#returnClick").attr("href","javascript:history.go(-1)");
     		}    		
     	}
        //加入会员
        function joinVip(ppid,ccompanyId){
         var url = basePath+"/ea/wfjshop/sajax_ea_validatecanBuy.jspa";
         $.ajax({
         url:url,
         type:"get",
         dataType:"json",
         data:{
         ppid:ppid,
         ccompanyId:ccompanyId
  
         },
         success:function(data){
            var m = eval("("+data+")");
            var result = m.result;
          	var mk = m.mk;
          	var ptppid=m.ptppid;
			if (result != "success") {

				alert(result);
				return;
			} else {
				if(mk=="nomk"){
					$(".mk").show();
				}
            
                document.location.href=basePath+"/ea/wfjshop/ea_gm.jspa?ppid="+ppid+"&ccompanyId="+ccompanyId+"&count=1&mk="+mk+"&ptppid="+ptppid;
             }
         },
         error:function(data){
         alert("验证失败");
         }
         
         });
        
      

          }

    	
    </script>
    
    
    <!--中联园区底部-->
    <c:if test='${param.foot=="wechat"}'>
    <div style="width:100%; margin:0 auto; max-width:1080px;">
        <div class="wfj11_005_bottom">
            <div class="wfj11_005_bottom_hei">
                <ul>
                    <li><a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_01.png" /></a></li>
                    <li><a href="javascript:;">会话</a></li>
                </ul>
                <ul>
                    <li><a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_02.png" /></a></li>
                    <li><a href="javascript:;">联系人</a></li>
                </ul>
                <ul>
                    <li><a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_03.png" /></a></li>
                    <li><a href="javascript:;" style=" color:#F00;">中联园区</a></li>
                </ul>
                <ul>
                    <li><a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_04.png" /></a></li>
                    <li><a href="javascript:;">我办公</a></li>
                </ul>
            </div>
        </div>
    </div>
      </c:if>
    <!--中联园区底部 end-->
</body>
</html>
