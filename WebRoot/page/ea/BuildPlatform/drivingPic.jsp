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
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/order_period.css">
    <script src="<%=basePath %>js/BuildPlatform/jquery-1.9.1.min.js"></script>
    <title>学时查询</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>学时查询</h1>
</header>
<div class="wrap_page">
    <div class="lc_img_wrap">
        <div class="lc_tit">练车照片</div>
        <div class="lc_img_box clearfix">

        </div>
    </div>
</div>

</body>
<script>
    var basePath = "<%=basePath %>";
    var photo1="${photo1}";
    var photo2="${photo2}";
    var photo3="${photo3}";
</script>
<script>
    $(function () {
        photo();
    })
    function photo() {
        var url = basePath+"mobile/office/sajax_ea_getAjaxPhoto.jspa?photo1="+photo1+"&photo2="+photo2+"&photo3="+photo3;
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            success : function (data) {
                var mb=eval("("+data+")");
                var str = new Array();
                for(var i=0;i<mb.length;i++){
                    str.push("<div class='lc_img'>");
                    str.push("<img src='http://123.57.250.159/elkc/fm/studentLessonPhoto/download/"+mb[i].PHOTO_ID+"' alt=''>");
                    str.push("<span>"+mb[i].TAKE_PHOTO_DATE.substring(0,mb[i].TAKE_PHOTO_DATE.length-2)+"</span>");
                    str.push("</div>");
                }
                $(".lc_img_box").append(str.join(""));
            }
        })
    }
</script>

</html>
