<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>

 <%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>

<body>

<header class="head">
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">添加经济平台</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden content_hidden_">
    <div class="content content_">
        <div class="con_">
        <c:forEach items="${list}" var="ap">
            <div class="gjpt_zzy gjpt_tjpt" onclick="dj('${ap[1]}')">
                <ul>
                
                    <li >
                        <div class="left"><img src="<%=basePath%>images/BuildPlatform/ico-gjpt.png" alt=""></div>
                        <div class="right">
                            <p>${ap[2]}</p>
                        </div>
                    </li>
                    
                </ul>
            </div>
            </c:forEach>
        </div>
    </div>
</div>


<script>
var param;
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content_").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");

        $(".content_hidden_").css("height",$(window).height()*0.92-4+"px");
        $(".con_").css("height",$(window).height()*0.925-4+"px");
        /*$(".gjpt_zzy ul li .right").css("height",$(".gjpt_zzy ul li .left").height()+"px");*/

    });
    function dj(ppid,name){
    	url = basePath+"ea/WfjIndustryPlatform/ea_getQuery.jspa?type=02&ppid="+ppid;
    	document.location.href = url;
    }
</script>

</body>
</html>