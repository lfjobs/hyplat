<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/23 0023
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
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
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/student.css">
    <script src="<%=basePath%>/js/ea/driving/elkc/jquery.min.js"></script>
    <title>学员管理</title>
</head>
<body>
<header class="com_head">
    <%--<a onclick="javascript: window.history.go(-1);return false;" class="back"></a>--%>
    <h1>学员管理</h1>
</header>
<div class="wrap_page">
    <div class="student_info clearfix">
        <%--<img src="<%=basePath%>/images/ea/driving/elkc/headimg1.jpg" alt="" class="head_img">--%>
        <a href="javascript:;" class="head_img">
            <s:if test="#request.StudentInfo[0][5]==null||request.StudentInfo[0][5]==''">
                <img src="<%=basePath %>/images/ea/driving/elkc/head.png" class="head_img" alt="">
            </s:if>
            <s:else>
                <img src="<%=basePath %>${StudentInfo[0][5]}" class="head_img" alt="">
            </s:else>
        </a>
        <div class="s_info_text">
            <div class="s_info_name">${StudentInfo[0][2] }</div>
            <div class="s_info_nm">编号：${StudentInfo[0][4] }</div>
        </div>
    </div>
    <hr class="s_hr">
    <div class="s_list_wrap">
        <a href="<%=basePath%>driving/elkc/ea_basicInfoPage.jspa?staffId=${StudentInfo[0][3]}" class="s_list_box s_list_01">
            <div class="s_list">
                个人信息
            </div>
        </a>
        <a href="###" onclick="perfect()" class="s_list_box s_list_02">
            <div class="s_list">
                评价管理
            </div>
        </a>
        <a href="<%=basePath%>ea/complaint/ea_findComplaint.jspa?staffid=${StudentInfo[0][3]}" class="s_list_box s_list_03">
            <div class="s_list">
                投诉管理
            </div>
        </a>
        <%--<a href="###" class="s_list_box s_list_04">--%>
            <%--<div class="s_list">--%>
                <%--信息管理--%>
            <%--</div>--%>
        <%--</a>--%>
        <input type="hidden" id="personNum" value="${StudentInfo[0][1]}">
    </div>
</div>
<style>
    .popup_box{width: 12rem;border: .15rem solid #1b1b1b;border-radius: .25rem;position: relative;display: none;}
    .popup_bd{height: 6rem;background-color: #f93c3d;background-size: 60%;background-position: center;background-repeat: no-repeat;border-top-left-radius: .2rem;border-top-right-radius: .2rem;}
    .popup_fd{background: #ffffff;padding: .8rem .6rem .6rem;}
    .popup_fd>span{display: block;text-align: center;font-size: .683rem;color: #1b1b1b;line-height: 1rem;}
    /* .sure_btn{display: block;width: 4rem;margin: .6rem auto 0;border: .1rem solid #1b1b1b;background: #e5cc1a;color: #1b1b1b;text-align: center;font-size: .7rem;line-height: 1.2rem;border-radius: .15rem;}*/
    .popup_text span{color: #bf0e0e;}
    .close_btn{position: absolute;width: 1rem;height: 1rem;background: url(../../../../../images/ea/lottery/close.png) no-repeat center;background-size: contain;right: .5rem;top: .4rem;}
    .sure_btn{outline: 0;border: 0;display: block;width: 10rem;margin: 2rem auto .6rem;text-align: center;line-height: 1.8rem;background: #80d46f;border-radius: .15rem;color: #fff;font-size: .8rem;}

</style>
<div class="overlay">
    <div class="popup_box">
        <div class="popup_fd">
            <span class="popup_text">您没有填写身份证号信息</span>
            <a href="<%=basePath%>/driving/elkc/ea_basicInfoPage.jspa?staffId=${StudentInfo[0][3]}" class="sure_btn" >请完善用户信息</a>
        </div>
    </div>
</div>
<script>

    function perfect(){
        var personNum=$("#personNum").val();
        if(personNum==null||personNum==""){
            $(".overlay").addClass("active").find(".popup_box").show();
        }else {
            window.location.href="<%=basePath%>/ea/student/ea_getMyTeacherByRecord.jspa?staffId=${StudentInfo[0][3]}";
        }
    }

</script>
</body>
</html>