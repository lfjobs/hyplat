<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<link href="<%=basePath%>css/contacts/style11.css" rel="stylesheet" type="text/css"/>

<script src="<%=basePath%>js/jquery-1.6.1.min.js"  type="text/javascript"></script>
<title>粉丝添加成功</title>
</head>

<body>
	<div class="wfj11_025">
	   <c:if test='${showType!="notitle"}'>
    	<div class="wfj11_025_top">
        	<ul id="tops">
            	<li><a href="<%=basePath%>/page/conacts/conts.jsp" target="_self"><img src="<%=basePath%>/images/contacts/wfj_return_01.png" /></a></li>
            	<li>提交成功</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>/images/contacts/top_more.png" /></a></li>
            </ul>
        </div>
       </c:if>
        <div class="wfj11_025_content">
        	<div class="wfj11_025_width">
            	<div class="wfj11_025_succeed">
                	<ul>
                    	<li id="succeed"><img src="<%=basePath%>/images/contacts/wfj_right_01.png" />恭喜您已提交成功！</li>
                    	<li>您已经保存成功</li>
                    </ul>
                </div>
                <div class="wfj11_025_down">
                	<ul>
                    	<li class="left"><div><a style="color:red;" href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa">现在注册</a></div></li>
                    	<li class="right"><div><a style="color:red;" href="javascript:window.close()">以后注册</a></div></li>
                    	
                    </ul>
                 
                </div>
                
            </div>
        </div>
    </div>
    
    
    
    
    <script type="text/javascript">
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//ä¿®æ¹å­ä½å¤§å°
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
        });
    </script>
</body>
</html>
















