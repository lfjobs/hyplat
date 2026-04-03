<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>票券</title>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/MeetingActivityList.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>
<script>
</script>
<header>
    <ul>
        <li style="width: 13%;">
            <a href="<%=basePath%>ea/lottery/ea_selMeetingDetail.jspa?&activityId=${activityId}"><img src="<%=basePath%>images/ea/lottery/left_jt.png"></a>
        </li>
        <li style="width: 74%;">票券</li>
        <li style="width: 13%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <c:forEach items="${ticketList }" var="x">
            <div class="tick-pur_mil">
                <div class="tick-pur_mil_txt">
                    <h3>&#xA5;${x[3] }</h3><h5>${x[2] }</h5>
                    <div class="clearfix"></div>
                    <p>${x[1] }</p>
                    <hr style="border-top: 3px solid #84c3cb;margin: 0.2rem 0;">
                    <h4>剩余<span>${x[4] }</span>张</h4>
                </div>
                <div class="tick-pur_mil_txt2">
                    <c:if test="${x[4] == 0}">
                        <h2 class="sq">已售罄</h2>
                    </c:if>
                    <c:if test="${x[4] > 0}">
                        <h2 id="goupiao">购票</h2>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!--<script type="text/javascript" src="baidumap/baiduapi.js"></script>-->

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position:fixed;");
        $(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
        $(".content").attr("style",";overflow: auto;");
        $(".content").css("height",$(window).height()*0.92-1+"px");

        $("#goupiao").click(function(){
            alert("功能研发中");
        })

    });
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>