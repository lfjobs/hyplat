<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>金币池</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath %>css/contacts/style12.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	
	<script type="text/javascript">
		var khd="${khd}";
		var basePath="<%=basePath%>";
    	var user="${user}";
    	var sccid="${sccid}";
    	var flag = "${flag}";
    	var app = "${app}";//苹果隐藏金币充值
	</script>

</head>

<body>
	<div class="wfj01_015">
		<c:if test="${khd == 0}">		
	    	<!--中联园区头部-->
	    	<div class="wfj_top">
	        	<ul style="overflow: hidden;position: relative;">
	        		<c:choose>
	        			<c:when test="${flag == 'sys'}">
	        				<li class="wfj_return"><a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa" target="_self"><img class="head_img" src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png"/></a></li>       				       			       				        			     			
	        			</c:when>
	        			<c:otherwise>
	        				<li class="wfj_return"><a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa?backu=2" target="_self"><img class="head_img" src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png"/></a></li>       					        				        			
	        			</c:otherwise>       		       		       		
	        		</c:choose>    	
	            	<li>金币池</li>
	            </ul>
	        </div>
	    </c:if>
	    	<!--中联园区头部 end-->   
     
        <div class="wfj01_015_content">
        	<div class="wfj01_015_hidden">
            	<div class="wfj01_015_topimg">
                    <ul>
						<li><div class="wfj01_016_topdiv"
								style="border-radius:50%;overflow:hidden;width:29%;margin:0 auto;">								
								<c:choose>
									<c:when test="${staff.headimage != null }">
										<img style="display:block;width:100%;height:100%;" src="<%=basePath %>${staff.headimage }" />																		
									</c:when>
									<c:otherwise>
										<img style="display:block;width:100%;height:100%;" src="<%=basePath %>images/WFJClient/VipCenter/headimage.png" />																											
									</c:otherwise>								
								</c:choose>							
							</div>
						</li>
						<li><span>${staff.staffName}</span></li>                      
                    </ul>
                </div>
                
                <div class="wfj01_015_congold">
                    <table>
                        <tr>
                            <td rowspan="3" width="35%" align="center"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_01.png" /></td>
                            <td><font><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }" groupingUsed="true"/></font></td>
                        </tr>
                        <tr>
                            <td>金币余额</td>
                        </tr>
                        <tr>
                            <td>可兑换<fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore/100 }" type="currency" groupingUsed="true"/></td>
                        </tr>
                    </table>
                </div>
                
                <div class="wfj01_015_bottom">
                    <div class="wfj01_015_bottom_link" >
                        <ul class="left wfj01_015_link01">
                            <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_02.png" /></li>
                            <li>金币兑现</li>
                        </ul>
                        <ul class="right wfj01_015_link02">
                            <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_03.png" /></li>
                            <li>兑换账单</li>
                        </ul>
                        <ul class="left wfj01_015_link03">
                            <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_04.png" /></li>
                            <li>好友排名</li>
                        </ul>
<!--                         <ul class="right wfj01_015_link04 show" id="app"> -->
<%--                             <li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_05.png" /></li> --%>
<!--                             <li>金币充值</li> -->
<!--                         </ul> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    <script type="text/javascript">
    	
    	$(document).ready(function(e) {
    	
    		var u = navigator.userAgent;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			if(isAndroid==true){
				var arr=new Array();
				arr.push('<ul class="right wfj01_015_link04 show" id="app">');
				arr.push('<li><img src="'+basePath+'images/ea/finance/BenDis/wfj_gold_05.png"/></li>');
				arr.push('<li>金币充值</li></ul>'); 
				$(".wfj01_015_bottom_link").append(arr.join(""));
			}
    	
    	
			$("body").css("height",$(window).height()) ;
         	
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px");
            
            $(".wfj01_015 .wfj_top ul").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
            $(".wfj01_015 .wfj_top ul li").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
            $(".wfj01_015 .wfj_top ul li:nth-child(1)").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:15%;");
            $(".wfj01_015 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
            $(".wfj01_015 .wfj_top ul li:nth-child(3)").attr("style","width:15%;");

			$("#scroller").css("height",$(window).height()-$(".wfj_top").height()+"px");
			if($(window).width()>$(window).height()){
				$(".wfj01_015").css("width","70%");
				$(".wfj01_015_content").attr("style","width:100%; height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:hidden;");
				$(".wfj01_015_hidden").attr("style","width:"+parseInt($(".wfj01_015_content").width()+17)+"px; height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:auto;");
			}else{
				$(".wfj01_015").css("width","100%");
			}
			
			$(".wfj01_016_topdiv").css({"height":$(".wfj01_016_topdiv").width()+"px","overflow":"hidden"})
            $(".wfj01_015_topimg").attr("style","padding:"+$(window).height()*0.035+"px 0;");
            $(".wfj01_015_topimg").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.035+"px;");
            $(".wfj01_015_topimg").find("li").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj01_015_congold").attr("style"," margin:"+$(window).height()*0.015+"px auto; padding:"+$(window).height()*0.02+"px 0;");
            $(".wfj01_015_congold").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			
            $(".wfj01_015_bottom_link").find("ul").attr("style"," margin-bottom:"+$(window).width()*0.005+"px;");
            $(".wfj01_015_bottom_link").find("ul").find("li").attr("style"," height:"+$(window).height()*0.1+"px; line-height:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;");
            $(".wfj01_015_bottom_link").find("ul").find("li").find("img").attr("style"," height:"+$(window).height()*0.06+"px; padding-left:"+$(window).height()*0.05+"px;");
            $(".wfj01_015_bottom_link").find("ul").find("li").find("img").each(function(){
            	var ihei=imgPosition($(".wfj01_015_bottom_link").find("ul").height(),$(window).height()*0.06);
            	$(this).attr("style",$(this).attr("style")+"margin-top:"+ihei+"px"); 
       
            });
			
			
			$(".wfj01_015_link01").click(function(){
				open(basePath+"/ea/jinbi/ea_getwfjtixian.jspa?user="+user+"&sccid="+sccid+"&khd="+khd+"&flag="+flag,"_self");
			});
			$(".wfj01_015_link02").click(function(){
				open(basePath+"/ea/jinbi/ea_gethyjifenBill.jspa?user="+user+"&sccid="+sccid+"&khd="+khd+"&flag="+flag,"_self");
			});
			$(".wfj01_015_link03").click(function(){
				open(basePath+"/ea/jinbi/ea_getFriends.jspa?user="+user+"&sccid="+sccid+"&khd="+khd+"&flag="+flag,"_self");
			});
			$(".wfj01_015_link04").click(function(){	
				open(basePath+"/ea/jinbi/ea_getwfjchongzhi.jspa?user="+user+"&sccid="+sccid+"&khd="+khd+"&flag="+flag,"_self");
			});   
		});
		
		function imgPosition(phi,thi){
			return (phi-thi)/2;
		}
		
	</script>
</body>
</html>
