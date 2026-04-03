<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>北京天太世统科技有限公司</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>

</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="<%=basePath%>/people/manage/ea_teamShow.jspa?flag=<%=request.getParameter("flag")%>&ccompanyId=<%=request.getParameter("ccompanyId")%>"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
        </li>
        <li style="width: 80%;">人员详情</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con con_pl" style="background: #fff">
            <ul class="team team_det">
                <li>
                	<s:if test="#request.map.obj[6]==null||request.map.obj[6]==''">
                	 	<img src="<%=basePath %>images/BuildPlatform/touxiang.png" alt="" class="head">
                	</s:if>
                	<s:else>
                	 	<img src="<%=basePath %>${request.map.obj[6]}" alt="" class="head">
                	</s:else>
                    <div class="txt">                   	
                        <h4><span><s:property value="#request.map.obj[0]"/></span><span class="jl"><s:property value="#request.map.obj[4]"/></span>
<%--                         <img src="<%=basePath %>images/BuildPlatform/vip.png" alt=""> --%>
                        </h4>
                        <p><s:property value="#request.map.obj[5]"/></p>
                        <h5></h5>
                    </div>
                </li>
            </ul>
            <div class="team_text">
            		${request.map.content }
            </div>

<!--             <div class="btn_jia"> -->
<!--                 <a href="#;"><input type="button" value="咨询" id="zx"></a> -->
<!--             </div> -->
        </div>      
    </div>
</div>



<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px;position: relative;");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        /* $(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con_pl").css("height",$(window).height()*0.84-2+"px");
        $(".btn_jia").attr("style","height:"+$(window).height()*0.08+"px");
    });
</script>


</body>
</html>