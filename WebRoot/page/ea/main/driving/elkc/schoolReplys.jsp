<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/1 0001
  Time: 17:39
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
    <title>学员投诉详情</title>
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
        <li style="width: 80%;">学员投诉详情</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <p class="tit">学员投诉</p>
            <ul class="complaint">
                <li class="">
                    <input type="hidden" value="${list[0][7]}" id="clid">
                    <img src="<%=basePath%>${list[0][0]==null?"images/ea/driving/elkc/head.png":list[0][0]}" class="img">
                    <div class="text">
                        <p class="coach">${list[0][1]}</p>
                        <c:if test="${list[0][8]=='1'}"><p class="subject">科目一</p></c:if>
                        <c:if test="${list[0][8]=='2'}"><p class="subject">科目二</p></c:if>
                        <c:if test="${list[0][8]=='3'}"><p class="subject">科目三</p></c:if>
                        <c:if test="${list[0][8]=='4'}"><p class="subject">科目四</p></c:if>
                    </div>
                    <a onclick="getPhone(${list[0][0]})"><img src="<%=basePath%>/images/ea/elkc/ico_tel.png" class="right"></a>
                </li>
            </ul>
            <div class="comp_txt">
                <p class="idea"><span>投诉意见：</span>${list[0][4]}</p>
                <div class="driving_">
                    <p class="tit"><span>驾校回复：</span></p>
                    <textarea class="driving" >${list[0][5]}</textarea>
                </div>
                <h5>投诉人：${list[0][6]}</h5>
                <h5>投诉时间：${fn:substring(list[0][9],0,19)}</h5>
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




    $(document).on("click",".huifu",function () {
        var companyreply=$(".driving").val();
        if(companyreply==""){
            alert("请先回复");
            return
        }
        var id=$("#clid").val();
        $.ajax({
            url:"<%=basePath%>ea/complaint/sajax_ea_replyComplaint.jspa?clid="+id+"&companyreply="+companyreply,
            type:"post",
            async:false,
            dateType:"jose",
            success :function (date) {
                var medate=eval("("+date+")");
                var fan=medate.fanhui;
                if(fan=="ok"){
                    window.location.href="<%=basePath%>page/ea/main/driving/elkc/schoolReplySuccess.jsp";
                }
            }

        } )

    })
    });

    function getPhone(num) {
        var ev = window.event || arguments.callee.caller.arguments[0];

        if (window.event) ev.cancelBubble = true;
        else {
            ev.stopPropagation();
        }
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                if (confirm("确定呼叫?")) {
                    Android.callPhone(num+"");
                }
            } else if (isiOS == true) {
                if (confirm("确定呼叫?")) {
                    var url = "func=" + 'iosCallphone';
                    params = {'phoneNum': num};
                    for (var i in params) {
                        url = url + "&" + i + "=" + params[i];
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            }
        }else {
            alert("无电话号码");
        }
    }
</script>
</body>
</html>
