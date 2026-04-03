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
    <title>学习进度</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script src="<%=basePath %>js/BuildPlatform/jquery-1.9.1.min.js"></script>
</head>
<script>
    var basePath = "<%=basePath %>";
    var staffId="${staffId}";
    var companyId = "${companyId}";
    var flag = "${flag}";
</script>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <%--<a href="javascript:history.go(-1)"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>--%>
        </li>
        <li style="width: 85%;text-indent: 10%;">学习进度</li>
        <li style="width: 5%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="schedule_top">
            <ul>
                <li class="active"><h5>科目一</h5><p style="display: none">1</p></li>
                <li><h5>科目二</h5><p style="display: none">2</p></li>
                <li><h5>科目三</h5><p style="display: none">3</p></li>
                <li><h5>科目四</h5><p style="display: none">4</p></li>
                <%--<li><h5>毕业</h5><p>5</p></li>--%>
            </ul>
        </div>
        <div class="schedule_con">
            <ul class="schedule_con-1"></ul>
            <ul class="schedule_con-2"></ul>
            <ul class="schedule_con-3"></ul>
            <ul class="schedule_con-4"></ul>
            <%--<ul class="schedule_con-5"></ul>--%>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".schedule_con").css("height",$(window).height()*0.92-$(".schedule_top").height()+"px");

        $(".schedule_top ul li").click(function(){
            var page = $("ul li").index(this)-2;
            var page2 = page-1;
            $(this).addClass("active").siblings().removeClass("active");
            $(".schedule_con").find("ul").eq(page2).show().siblings().hide();
            loaded();
        });
        loaded();
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
    function getmsg(num) {
        var ev = window.event || arguments.callee.caller.arguments[0];

        if (window.event) ev.cancelBubble = true;
        else {
            ev.stopPropagation();
        }
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid==true){
                if(confirm("确定发送短信邀请?")){
                    Android.callmsg(num+"");
                }
            }else if(isiOS==true){
                if(confirm("确定发送短信邀请?")){
                    var url= "func=" + 'message';
                    params={'name':num};
                    for(var i in params){
                        url = url + "&" + i + "=" + params[i];
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            }
        }else {
            alert("无电话号码")
        }
    }

    function loaded() {
            var subject = $(".active p").text();
            var url = basePath +"mobile/office/sajax_ea_findStudent.jspa?staffId="+staffId+"&subject="+subject;
            if(flag=="el"){
                url= basePath +"mobile/office/sajax_ea_stCompanyByStudent.jspa?companyId="+companyId+"&subject="+subject;
            }
            $.ajax({
                type : "GET",
                url : url,
                async : false,
                dataType : "json",
                success : function(data) {
                    $(".schedule_con-"+subject).empty();
                    var json = eval('(' + data + ')');
                    var listStudent = json.listStudent;
                    var list = new Array();
                    for (var i= 0;i<listStudent.length;i++){
                        list.push("<li onclick=\"query('"+listStudent[i][5]+"','"+subject+"','"+listStudent[i][0]+"')\"><img src='<%=basePath %>"+(listStudent[i][2]==null?"/images/ea/driving/elkc/head.png":listStudent[i][2])+"' class='img' />");
                        list.push("<p>"+(listStudent[i][0]==null?"":listStudent[i][0])+"</p>");
                        list.push("<div class='p_m'>");
                        list.push("<a onclick='getPhone("+listStudent[i][4]+")'><img src='<%=basePath %>images/BuildPlatform/ico-tel.png' class='tel'></a>");
                        list.push("<a onclick='getmsg("+listStudent[i][4]+")'><img src='<%=basePath %>images/BuildPlatform/ico-mes.png' class='mes'></a>");
                        list.push("</div></li>");
                    }
                    if(listStudent!=null){
                            $(".schedule_con-"+subject).append(list.join(""));
                    }else {
                        $(".schedule_con-"+subject).append("<li>暂无数据</li>");
                    }

                }
            })
        }
        function query(studentId,subject,studentName) {
            window.document.location.href=basePath+"mobile/office/mobileoffice_periodDetails.jspa?studentId="+studentId+"&subject="+subject+"&studentName="+studentName
        }

</script>
<script>

</script>

</body>
</html>