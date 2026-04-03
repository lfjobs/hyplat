<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/1 0001
  Time: 17:23
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
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/elkc/left_jt.png"></a>
        </li>
        <li style="width: 80%;text-indent: 10%;">投诉列表</li>
        <li style="width: 10%;">
            <a href="<%=basePath%>ea/complaint/ea_findTeacher.jspa?staffid=${param.staffid}"><img src="<%=basePath%>/images/ea/elkc/ico-add.png"></a>
        </li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <p class="tit">我的投诉</p>
            <ul class="complaint">

                <c:forEach items="${list}" var="com">
                <li class="teach">
                    <input type="hidden" id="teacherid" value="${com[2]}">
                    <input type="hidden" id="staffid" value="${com[3]}">
                    <input type="hidden" id="clId" value="${com[4]}">
                    <img src="<%=basePath%>${com[0]==null?"images/ea/driving/elkc/head.png":com[0]}" class="img">
                    <p class="school">${com[1]}</p>
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

        //编辑分类
        $(document).on("click",".redact",function(){
            $(this).text("保存");
            $(this).removeClass().attr("id","save");
            $(".product li .txt input").removeAttr("readonly","readonly");
            $(".product li .txt img").attr("src", "<%=basePath%>images/ea/elkc/ico-delete.png").addClass("det");
            $(".add").show();
        });
        $(document).on("click",".product li .det",function(){
            $(this).parents("ul li").remove();
        });

        //保存分类
        $(document).on("click","#save",function(){
            $(this).text("编辑分类");
            $(this).removeAttr("id").addClass("redact");
            $(".product li .txt input").attr("readonly","readonly");
            $(".product li .txt img").attr("src", "<%=basePath%>images/ea/elkc/ico_right.png").removeClass("det");
            $(".add").hide();
        });
        $(".add").click(function(){
            var li = "<li><div class='txt'><input type='text' value='' placeholder='请添加分类'><img src='<%=basePath%>images/ea/elkc/ico-delete.png' class='right det'></div></li>";
            $(".product").append(li);
        })

    });

    $(document).on("click",".teach",function(){
        var teacherId=$(this).find("#teacherid").val();
        var staffId=$(this).find("#staffid").val();
        var clId=$(this).find("#clId").val();
        document.location.href="<%=basePath%>ea/complaint/ea_teacher.jspa?teacherId="+teacherId+"&staffid="+staffId+"&clId="+clId;
    });
</script>
</body>
</html>
