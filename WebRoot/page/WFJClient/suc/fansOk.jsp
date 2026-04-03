<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>购买成功</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/zcok.css" type="text/css"></link></head>
  <script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
<body>
	<div class="wfj11_025">
    	<div class="wfj11_025_top">
        	<ul id="tops">
            	<li><a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025" target="_self"><img src="<%=basePath%>/images/WFJClient/wfj_return_01.png"></img></a></li>
            	<li>交易成功</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>/images/WFJClient/top_more.png"></img></a></li>
            </ul>
        </div>
        <div class="wfj11_025_content">
        	<div class="wfj11_025_width">
            	<div class="wfj11_025_succeed">
                	<ul>
                    	<li id="succeed"><img src="<%=basePath%>/images/WFJClient/wfj_right_01.png"></img>恭喜您购买成功</li>
                    	<li><span>订单号：<%=request.getParameter("ddid")%></span></li>
                    </ul>
                </div>
          
            </div>
        </div>
    </div>
    
    
    
    
    <script type="text/javascript">
		var lottery  = "${param.showType}";

    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
	      
			$(".wfj11_025_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_025_top").css("lineHeight",$(window).height()*0.08+"px");
			
			$(".wfj11_025_succeed").attr("style","margin:"+$(window).height()*0.1+"px auto "+$(window).height()*0.1+"px auto;line-height:"+$(window).height()*0.08+"px;");
			$(".wfj11_025_succeed").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.1+"px;");
			$(".wfj11_025_succeed").find("#succeed").attr("style","font-size:"+$(window).height()*0.03+"px; color:#000;");

			$(".wfj11_025_down").find("li").attr("style","height:"+$(window).height()*0.08+"px;");
			$(".wfj11_025_down").find("div").attr("style","font-size:"+$(window).height()*0.018+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");

			if("lottery" == lottery){
				$("#succeed").html("恭喜您领取奖品成功");
			}
        });
    </script>
</body>
</html>
