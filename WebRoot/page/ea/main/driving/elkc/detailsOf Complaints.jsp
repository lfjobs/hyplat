<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/27 0027
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>投诉详情</title>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/complaint_style.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/elkc/left_jt.png"></a>
        </li>
        <li style="width: 80%;text-indent: 10%;">投诉详情</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <p class="tit">我的投诉</p>
            <ul class="complaint">
                <li class="">
                    <img src="<%=basePath%>/images/ea/elkc/ico-header_.png" class="img">
                    <div class="text">
                        <p class="coach">张三教练</p>
                        <p class="subject">科目二</p>
                    </div>
                    <a href="tel:10086"><img src="<%=basePath%>/images/ea/elkc/ico_tel.png" class="right"></a>
                </li>
            </ul>
            <div class="comp_txt">
                <p class="idea"><span>投诉意见：</span>车子出故障了~~~</p>
                <p class="reply"><span>驾校回复：</span>不好意思，接受您的建议,改正中~</p>
                <h5>投诉人：张三</h5>
                <h5>投诉时间：2017/11/24 13:30:00</h5>
                <input type="button" value="回复" class="huifu">
            </div>
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
            $(".product li .txt img").attr("src", "<%=basePath%>/images/ea/elkc/ico-delete.png").addClass("det");
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
            $(".product li .txt img").attr("src", "<%=basePath%>/images/ea/elkc/ico_right.png").removeClass("det");
            $(".add").hide();
        });
        $(".add").click(function(){
            var li = "<li><div class='txt'><input type='text' value='' placeholder='请添加分类'><img src='<%=basePath%>/images/ea/elkc/ico-delete.png' class='right det'></div></li>";
            $(".product").append(li);
        })

    });
</script>
</body>
</html>
