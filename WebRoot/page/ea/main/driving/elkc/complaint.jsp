<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/1 0001
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title>投诉列表</title>
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/elkc/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/elkc/complaint_style.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
          &nbsp;
        </li>
        <li style="width: 80%;text-indent: 10%;">投诉列表</li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <p class="tit">学员投诉</p>
            <ul class="complaint">
                <c:forEach items="${list}" var="all">
                <li class="teach">
                    <input type="hidden" id="teacherid" value="${all[2]}">
                    <input type="hidden" id="staffid" value="${all[3]}">
                    <input type="hidden" id="clId" value="${all[4]}">
                    <img src="<%=basePath%>${all[0]==null?"images/ea/driving/elkc/head.png":all[0]}" class="img">
                    <div class="school">
                        <p class="name">${all[1]}<c:if test="${all[6]!=null}"><b>已回复</b></c:if></p>
                        <p class="time"><fmt:formatDate value="${all[5]}" pattern="yyyy-MM-dd HH:mm:ss" /></p>

                    </div>
                    <img src="<%=basePath%>/images/ea/elkc/ico-right.png" class="right">
                </li>
                </c:forEach>

            </ul>

        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".content .con").css("height",$(window).height()*0.92+"px");




    });
    $(document).on("click",".teach",function(){
        var teacherId=$(this).find("#teacherid").val();
        var clId=$(this).find("#clId").val();
        document.location.href="<%=basePath%>ea/complaint/ea_teachers.jspa?teacherId="+teacherId+"&clId="+clId;
    });
</script>
</body>
</html>
