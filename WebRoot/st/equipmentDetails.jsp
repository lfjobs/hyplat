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
    <title>设备展示详情</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>

</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;">设备展示详情</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="facility_details">
                <div class="top">
                    <img src="<%=basePath%>${param.photo==null || param.photo=="null" ?"st/images/ico-car1.png":param.photo}" class="left">
                    <div class="right">
                        <ul>
                            <li>
                                <h5>车牌号：</h5><p>${param.carNum==null?"":param.carNum}</p>
                            </li>
                            <li>
                                <h5>车型：</h5><p>${param.cartype==null||param.cartype=="null" ?"":param.cartype}</p>
                            </li>
                            <%--<li>--%>
                                <%--<h5>教学科目：</h5><p>C2科目</p>--%>
                            <%--</li>--%>
                            <li>
                                <h5>教练姓名：</h5><p>${param.name==null?"":param.name}</p>
                            </li>
                        </ul>
                    </div>
                </div>
                <ul class="txt">
                    <li>联系方式：<span>${param.reference==null?"":param.reference}</span></li>
                    <li>联系地址：<span>${param.address==null?"":param.address}</span></li>
                </ul>
                <div class="bottom">
                    <a onclick="getPhone(${param.reference})" ><img src="<%=basePath %>st/images/ico-tel.png" class="left"></a>
                    <a onclick="getmsg(${param.reference})" ><img src="<%=basePath %>st/images/ico-sms.png" class="right"></a>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function getPhone(num) {
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                if (confirm("确定呼叫?")) {
                    Android.callPhone(num);
                }
            } else if (isiOS == true) {
                if (confirm("确定呼叫?")) {
                    var url = "func=" + 'phone';
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
    function getmsg(num) {
        if (num != null && num != ""){
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if(isAndroid==true){
            if(confirm("确定发送短信邀请?")){
                Android.callmsg(num);
            }
        }else if(isiOS==true){
            if(confirm("确定发送短信邀请?")){
                var url= "func=" + 'iosCallphone';
                params={'name':num};
                for(var i in params){
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
            }
        }else {
            alert("无电话号码");
        }
    }
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");

    });
</script>

</body>
</html>