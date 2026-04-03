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
    <title>预约管理</title>

</head>
<body>
<header class="com_head">
    <%--<a onclick="javascript: window.history.go(-1);return false;" class="back"></a>--%>
    <h1>预约管理</h1>
</header>
<div class="wrap_page">
    <div class="s_list_wrap">
        <c:if test="${param.app=='app'}">
        <a href="#" onclick="perfects()" class="s_list_box s_list_01">
            <div class="s_list">
                我要约车
            </div>
        </a>
        </c:if>
        <c:if test="${param.app=='sj'}">
            <a href="#" onclick="perfect()" class="s_list_box s_list_01">
                <div class="s_list">
                    我要约车
                </div>
            </a>
        </c:if>
        <c:if test="${param.app=='app'}">
        <a href="<%=basePath%>/ea/elkcRecord/ea_recorde.jspa?staffId=${param.staffId}" class="s_list_box s_list_02">
            <div class="s_list">
                我的约车记录
            </div>
        </a>
        </c:if>
        <c:if test="${param.app=='sj'}">
            <a href="<%=basePath%>/ea/elkcRecord/ea_record.jspa" class="s_list_box s_list_02">
                <div class="s_list">
                    我的约车记录
                </div>
            </a>
        </c:if>
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
            <span class="popup_text">您没有学时，请您购买学时</span>
            <a onclick="buyPeriod()" class="sure_btn" >请您购买学时</a>
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
    function perfect(){
        $.ajax({
            url:basePath +"ea/coachreserv/sajax_ea_hour.jspa?",
            type:"post",
            async:false,
            success: function cbf(data){
                var member=eval("("+data+")");
                if(member.hasTime>0){
                        window.location.href=basePath +"ea/coachreserv/ea_CoacheRservation.jspa?companyId=${param.companyId}&flag=2";
                }else {
                    $(".overlay").addClass("active").find(".popup_box").show();
                }
            }
        });
    }
    function perfects(){
        $.ajax({
            url:basePath +"ea/coachreserv/sajax_ea_hourAPP.jspa?staffId=${param.staffId}",
            type:"post",
            async:false,
            success: function cbf(data){
                var member=eval("("+data+")");
                if(member.hasTime>0){
                    window.location.href=basePath +"ea/coachreserv/ea_CoacheRservations.jspa?companyId=${param.companyId}&flag=1&staffId=${param.staffId}";
                }else {
                    $(".overlay").addClass("active").find(".popup_box").show();
                }
            }
        });
    }
    function buyPeriod() {
        var studentId = $("#studentId").val()
        $.ajax({
            type : "GET",
            url : basePath +"mobile/office/sajax_ea_buyPeriod.jspa?idCard=${param.Card}",
            async : false,
            dataType : "json",
            success : function(data) {
                var json = eval('(' + data + ')');
                var detailsParameter = json.detailsParameter;
                window.location.href = basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+detailsParameter[0]+"&goodsid="+detailsParameter[1]+"&companyId="+detailsParameter[2]+"&ccompanyId="+detailsParameter[3];
            }
        })
    }

</script>
</body>
</html>