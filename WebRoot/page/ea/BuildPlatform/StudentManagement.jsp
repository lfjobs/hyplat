<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/17 0017
  Time: 15:02
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
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/zy_sys.css">
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <title>学员管理系统</title>
</head>
<script type="text/javascript">
    var staffId='${StudentInfo[0][3]}';
    var companyId='${param.companyId}';
</script>
<body>
<header class="com_head">
    <a onclick="javascript: window.history.go(-1);return false;" class="back"></a>
    <h1>学员管理系统</h1>
</header>
<div class="wrap_page">
    <div class="zy_home">
        <a href="javascript:;" class="head_img">
            <s:if test="#request.StudentInfo[0][5]==null||request.StudentInfo[0][5]==''">
                <img src="<%=basePath %>/images/BuildPlatform/touxiang.png" alt="">
            </s:if>
            <s:else>
                <img src="<%=basePath %>${StudentInfo[0][5]}" alt="">
            </s:else>
        </a>
        <div class="head_name">${StudentInfo[0][2] }</div>
        <div class="home_list">
            <a href="<%=basePath %>/ea/elkcInform/ea_traineeInformation.jspa">消息管理<b class="info_num"></b></a>
            <a href="#" onclick="perfect(1)">学习进度</a>
            <a href="<%=basePath%>mobile/office/mobileoffice_StudentManagement.jspa?flag=2">学员管理</a>
            <a href="#" onclick="perfect(0)">预约管理</a>
        </div>
    </div>
</div>
<style>
    .popup_box{width: 12rem;border: .15rem solid #1b1b1b;border-radius: .25rem;position: relative;display: none;}
    .popup_bd{height: 6rem;background-color: #f93c3d;background-size: 60%;background-position: center;background-repeat: no-repeat;border-top-left-radius: .2rem;border-top-right-radius: .2rem;}
    .popup_fd{background: #ffffff;padding: .8rem .6rem .6rem;}
    .popup_fd>span{display: block;text-align: center;font-size: .683rem;color: #1b1b1b;line-height: 1rem;}
    /* .sure_btn{display: block;width: 4rem;margin: .6rem auto 0;border: .1rem solid #1b1b1b;background: #e5cc1a;color: #1b1b1b;text-align: center;font-size: .7rem;line-height: 1.2rem;border-radius: .15rem;}*/
    .popup_text span{color: #bf0e0e;}
     .close_btn{position: absolute;width: 1rem;height: 1rem;background: url(../../../images/ea/lottery/close.png) no-repeat center;background-size: contain;right: .5rem;top: .4rem;}
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
    var  basePath="<%=basePath%>";
    window.onload = window.onresize = function() {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    }
    function perfect(flag){
          $.ajax({
                url:basePath +"/mobile/office/sajax_ea_studentInfor.jspa?",
                type:"post",
                data:{
                    "companyId":companyId
                },
                async:false,
                success: function cbf(data){
                    var member=eval("("+data+")");
                    var ii=member.list;
                        if(ii.length>0&&ii[0]!=null){
                            if(flag==0){
                                window.location.href=basePath +"ea/coachreserv/ea_appointment.jspa?companyId=${param.companyId}&Card="+ii[0]+"&app=sj";
                            }else if(flag==1){
                                window.location.href=basePath +"mobile/office/mobileoffice_queryPeriod.jspa?idCard="+ii[0];
                            }
                        }else {
                            $(".overlay").addClass("active").find(".popup_box").show();
                    }
               }
            });
    }

</script>
</body>
</html>