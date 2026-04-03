<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp1.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/news.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
<script src="<%=basePath %>js/ea/marketing/wfjeshop/SearchList.js"></script>

<title>地球数字商城广告</title>
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
</script>
<body>
<div class="main">
	<div class="wfj12_002">
		<div class="wfj12_002_top">
	       	<ul id="tops">
	           	<li><a href="javascript:;" onClick="javascript:history.back(-1);"><img src="<%=basePath %>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></li>
	           	<li>${adList[0][0] }</li>
	        </ul>
	    </div>
    </div>
    	<c:forEach items="${functionlist }" var="fun">
    <article>
    	<h1 style="margin-left:1rem;"></h1>
        <h2 style="margin-left:1rem;">
            <span class="keyword">${fun.name }</span>
        </h2>
        <div class="line"></div>
        <div class="content">
          <table class="content_tab" cellpadding="0" cellspacing="0">
    		<c:forEach items="${maplist }" var="m">
    		<c:if test="${fun.gfid==m.key }">
        	<tr>
        		<td style="height: 150px;">
        	    	<span><div style="width:100%;">${m.value}</div></span> 
        		</td>
        	</tr>
        	</c:if>
			</c:forEach>
          </table>
        </div>
    </article></c:forEach>
    <div>
 		<ul class="download">
 			<li>查看更多动态 <b>></b></li>
 		</ul>   
    </div>
<div class="shadow"></div>
</div>
</body>
<script type="text/javascript">
$(function(){
	  $(".content").attr("style","width:100%;text-align:center;");
	});
		$("body").css("height",$(window).height());
		$("#tops").find("li").attr("style","float:left;");
		$("#tops").find("li").eq(0).attr("style","width:15%;");
		$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
		$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#373737;");
		$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
		$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
		$(".wfj12_002_top").css("height",$(window).height()*0.08+"px;background-color:#FFF;");
		$(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
		$(".content").find(".content_tab").css("width",$(".content").width()+"px")
		$(".content").find("div:first").css("width",$(".content").width()+"px")
		$(".content tr td").find("p").css("width",$(".content").width()+"px")
		$(".content tr td").find("img").css("width",$(".content").width()+"px")
		$(".keyword").attr("style","font-size:"+$(window).width()*0.06+"px;");
		$(".content tr td").find("p").attr("style","font-size:"+$(window).width()*0.03+"px;");
	
		$(".download").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;width:100%;");
		$(".download").find("li").attr("style","text-align:center;background:#F74C31;font-size:"+$(window).height()*0.025+"px;color:#FFF;")
	window.onload=function(){
			$(".content").find("img").each(function(){
				   if($(this).width()>$(".content").width()){

				      $(".content").find("img").css({"width":$(".content").width()+"px","height":"auto","margin":"0 auto"})
				   }else{
				      $(".content").find("img").css({"height":"auto","margin":"0 auto"})
				   }
				
				});
		}
		$(".download").click(function(){
			window.location.href=basePath+"ea/digitalmall/ea_DigitalMall.jspa?";
		});
</script>
</html>