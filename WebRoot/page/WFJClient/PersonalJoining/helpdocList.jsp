<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>帮助文档列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/WFJClient/wfjapp.css" type="text/css"></link>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>

</head>
<body>
	<div class="wfj12_019_news">
<%-- 		<div class="wfj12_019_news_top">
			<ul>
				<li><a onClick="javascript:history.back(-1);"> <img
						src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" />
				</a>
				</li>
				<li>帮助文档</li>
				<li></li>
			</ul>
		</div> --%>

		<div class="wfj12_019_content">
			<div class="wfj12_019_hidden">
				<!--帮助文档-->
				<div class="wfj12_019_news">
					<div class="wfj12_019_news_content">
						<div class="wfj12_019_width" >
						   <c:forEach  items="${doclist}" var="item" varStatus="state">
								<table class="news" id="${item[1]}">
									<tr>
										<td>${state.index+1}、${item[0]}</td>

									</tr>

								</table>
							</c:forEach>
						</div>
					</div>
					
				</div>
				<!--帮助文档end-->
			</div>
		</div>
	</div>
		
	<script type="text/javascript">
	
var ccompanyId = "${ccompanyId }";
var basePath = "<%=basePath%>";



	</script>
	
	<script type="text/javascript">
	$(document).ready(function(e) {
		$("#wrapper").attr("style","top:"+($(window).height()*0.081)+"px;");
		
		$(".wfj12_019").css("height",$(window).height());
	/* 	$(".wfj12_019_news_top").find("ul").attr("style","height:"+$(window).height()*0.08+"px;lineHeight:"+$(window).height()*0.08+"px;background-color:#FFF;");
		$(".wfj12_019_news_top").find("li").eq(0).attr("style","float:left;width:15%;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		$(".wfj12_019_news_top").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
		$(".wfj12_019_news_top").find("li").eq(1).attr("style","float:left;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#373737;");
    	 */
    	$(".wfj12_019_content").attr("style","height:"+($(window).height()-$(".wfj12_019_news_top").height())+"px;width:"+$(window).width()+"px;overflow:hidden;");
    	$(".wfj12_019_hidden").attr("style","height:"+parseInt($(".wfj12_019_content").height())+"px;overflow:auto;");
    	
		$(".wfj12_019_news_content").find("table").attr("style","width:"+$(window).width()+"px;margin-top:"+$(window).height()*0.015+"px;")
    	$(".wfj12_019_news_content").find("td").attr("style","font-size:"+$(window).height()*0.028+"px;color:#373737;");
		$(".wfj12_019_news_bottom").attr("style","margin-top:"+$(window).height()*0.05+"px;margin-bottom:"+$(window).height()*0.015+"px;");
		$(".wfj12_019_news_bottom").find("li").attr("style","text-align: center;font-size:"+$(window).height()*0.025+"px;color:#373737;line-height:"+$(window).height()*0.03+"px;");
		
		if(($(".wfj12_019_news_content").height()+$(".wfj12_019_news_bottom").height()+$(window).height()*0.08)<$(".wfj12_019_content").height()){
			$(".wfj12_019_news_bottom").css("margin-top",($(".wfj12_019_content").height()-$(".wfj12_019_news_bottom").height()-$(window).height()*0.015));
		}
	   $(".wfj12_019_content").find("td").css({"padding-left":"10px"});
		// 新闻详情
		$(".news").click(function() {
			var goodsid = $(this).attr("id");
			document.location.href = basePath
					+ "ea/industry/ea_viewHelpDocDetail.jspa?goodsID=" + goodsid;
		});
    });
    
    </script>
		
	
</body>
</html>