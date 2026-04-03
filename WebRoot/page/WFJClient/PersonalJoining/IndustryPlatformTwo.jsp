<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>${param.content}联营经济平台</title>
    
	 <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style2016.9.18.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>

  </head>
  
 <header>
    <ul>
        <li style="width: 10%;">
            <a id="returnClick"><img src="<%=basePath%>/images/WFJClient/Platform/left_jt.png"></a>
        </li>
        <li style="width: 80%;">${param.content}联营经济平台</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content content2">
    <ul class="pingtai" style="padding-bottom: 1rem;">
    <c:forEach items="${list}" var="a">
        <a onclick="on(this)">
        <input type="hidden" id="content" value="${a[0]}">
        <input type="hidden" id="ppid" value="${a[1]}">
        <input type="hidden" id="standard" value="${a[3]}">
         <input type="hidden" id="money" value="${a[2]}">
            <li>
                <div class="img">
                    <img src="<%=basePath%>/images/WFJClient/Platform/pingtai.png" alt="">
                </div>
                <div class="txt">
                    <h5>${a[0]}</h5>
                   <%-- <p>&yen;<span>${a[2]}</span></p>--%>
                </div>
                <hr style="width: 73%;height: 1px;background: #c6c6c6;border: none;margin: 0;position: absolute;bottom: 0;right: 0;">
            </li>
        </a>
        </c:forEach>
        <input type="hidden" id="addre" value="${param.addre}">
    </ul>
</div>

<script>
   var basePath = "<%=basePath%>";
function on(obj){
	var content = $(obj).find("#content").val();
	var ppid = $(obj).find("#ppid").val();
	//var standard = $(obj).find("#standard").val();
	var money = $(obj).find("#money").val();
	var addre = $("#addre").val();
	var p;
	var standard;
	
	p=content.substring(0,2);
	var types;
	var typeId;
	if(p=="省级"){
		standard="a";
	
	}else if(p=="村级"){
		standard="c";
	
	}else if(p=="县级"){
		standard="b";
	
	}
	
	
	document.location.href= basePath+"ea/wfjplatform/ea_getPlatform.jspa?type=qureyd&content="+content+"&ppid="+ppid+
						"&money="+money+"&addre="+addre+"&standard="+standard; 
	
}
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
        $("#returnClick").click(function() {history.go(-1)});
    });
</script>
<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>