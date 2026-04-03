<%@page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%  
    String path=request.getContextPath();
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp1.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/news.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
<title>中联园区资讯</title>

</head>

<body onload="loaded()">

<div class="main">
	<div class="wfj12_002">
		<div class="wfj12_002_top">
	       	<ul id="tops">
	           	<li><a href="javascript:;" onClick="javascript:history.back(-1);"><img src="<%=basePath %>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></li>
	           	<li>资讯详情</li>
	           	<li><%-- <a href="javascript:;"><img src="<%=basePath%>images/WFJClient/PersonalJoining/top_more.png" /></a> --%></li>
	        </ul>
	    </div>
    </div>
    <article>
    	<h1 style="margin-left:1rem;"> ${object1[0]}</h1>
        <h2 style="margin-left:1rem;">
        	<span class="time">${fn:substring(object1[1], 0, 10)}</span>
        	<c:if test="${object1[3]=='新闻'}">
            <span class="keyword">资讯</span>
            </c:if>
            <c:if test="${object1[3]!='新闻'}">
            <span class="keyword">${object1[3]}</span>
            </c:if>
        </h2>
        <div class="line"></div>
        <div class="content">
          <table class="content_tab" cellpadding="0" cellspacing="0">
            <tr>
               <td>
               <div style="width:100%;position:relative;">
             	 <img src="<%=basePath%>${object1[2]}" style="width: 100%;height: 100%;"/>
             	 <div>查看详情</div>
               </div>
               </td>
            </tr>
            <s:iterator value="maplist1" var="ml">
           <%--  <tr>
            	<td><b><span>${ml.value[1]}</span></b></td>
            </tr> --%>
        	<tr>
        		<td style="height: 150px;">
        	    	<span><div style="width:100%;">${ml.value[0]}</div></span> 
        		</td>
        	</tr>
        	</s:iterator>
          </table>
        </div>
    </article>
		<div class="wfj12_019_body">
			<!--公司名片二维码 -->
			<table align="center" id="table1" name="table1" style="width: 100%;">
				<tr>
					<td align="center">
					<s:iterator value="cclist" var="li">
						<img style="width:150px;height:150px;" src="${li[3]}" width="90px"
							height="90px" /><br> <a href="<%=basePath %>ea/industry/ea_CompanyCard.jspa?ccompanyId=${li[0]}"
							style="color: #373737">${li[1] }</a> <br>
					</s:iterator>
					</td>
				</tr>
			</table>
		</div>

		<div>
 		<ul class="download">
 			<li>查看更多动态 <b>></b></li>
 		</ul>   
    </div>
<div class="shadow"></div>
    <section class="more" style="padding:0 4.5%;">
    	<header><span></span>更多精彩
        </header>
        <ul id="more_news">
        	<c:forEach items="${list1 }" var="li" varStatus="l">
        		<c:if test="${l.index<4 }">
       				<li><a href="ea_getWFJnews.jspa?ccompanyId=${ccompanyId}&search=${search}&goodsid=${li[1]}&share=${li[0]}">${li[0]}</a></li>
        		</c:if>
        	</c:forEach>
        </ul>
    </section>
</div>
<!--暂时拿掉  -->
<!-- <div class="foot">
    北京天太世统科技有限公司<br>
    Copy Right &copy; 2010-2015 all right reserved
</div> -->
</body>
<script type="text/javascript">
var basePath='<%=basePath%>';
	
	$("body").css("height",$(window).height());
	$("#tops").find("li").attr("style","float:left;");
	$("#tops").find("li").eq(0).attr("style","width:15%;");
	$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-left:"+$(window).height()*0+"px; vertical-align:middle;");
	$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#373737;");
	$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
	$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
	$(".wfj12_002_top").css("height",$(window).height()*0.08+"px;background-color:#FFF;");
	$(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
	
	
	$(".download").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;width:100%;");
	$(".download").find("li").attr("style","text-align:center;background:#F74C31;font-size:"+$(window).height()*0.025+"px;color:#FFF;")
	
	$(".download").find("li").click(function(){
		var url = basePath + "ea/buyproducts/ea_queryForm.jspa";
		document.location.href = url;
	});

	
	
function loaded(){
	$(".content").attr("style","width:100%;text-align:center;");
	$(".content").find(".content_tab").css("width",$(".content").width()+"px");
	$(".content").find("div:first").css({"width":"95%","padding":"0 2.5%"});
	$(".content tr td").find("p").css("width","95%");
	$(".content tr td").find("p").css("padding","0 2.5%");
	$(".content tr td").find("img").css("width","100%");
	$(".content").find("div").eq(1).attr("style","position:absolute;height:20px;line-height:20px;text-align:center;width:100px;bottom:30px;right:40px;color:#fff;background:rgba(0,0,0,0.5);");
	$(".content").find("img").each(function(){
	   	if($(this).width()>$(".content").width()){
	      	$(".content").find("img").css({"width":"100%","height":"auto","margin":"0 auto"})
	   	}else{
	      	$(".content").find("img").css({"height":"auto","margin":"0 auto"})
	   	};
	   	
	    if($(".content_tab p").find("img").length>0){
			$(this).parents("p").css("text-indent","0");
		}else{
			
		} 
    });
	$(".content").find("div").eq(1).click(function(){
		var ccompanyId = "${ccompanyId }";
		if(ccompanyId == ""){
			var url = basePath + "ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
			document.location.href = url;
		}
		if(ccompanyId != ""){
			var url = basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId;
			document.location.href = url;
		}
	});
	
	
	
}	
</script>
<script type="text/javascript">
	$(document).ready(function(){
		
	})
</script>
</html>



