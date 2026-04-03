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
    <title>学时查询详细</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:history.go(-1)" class="back"></a>
    <h1>学时查询详细</h1>
</header>
<div class="wrap_page">
    <div class="xs_wrap">
        <div class="xs_name">${param.studentName} <c:if test="${subject eq '1' }">科一</c:if>
                                                <c:if test="${subject eq '2' }">科二</c:if>
                                                <c:if test="${subject eq '3' }">科三</c:if>
                                                <c:if test="${subject eq '4' }">科四</c:if>实操练车明细</div>
    </div>
</div>

</body>
<script>
    var basePath = "<%=basePath %>";
    var studentId="${studentId}";
    var subject="${subject}";
</script>
<script>
$(function () {
    periodDetails();
})
    function periodDetails() {
        var url = basePath+"mobile/office/sajax_ea_getAjaxPeriodDetails.jspa?studentId="+studentId+"&subject="+subject;
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            success : function (data) {
                var mb=eval("("+data+")");
                var str = new Array();
                for(var i=0;i<mb.length;i++){
                    str.push(" <dl class='xs_con'>");
                    str.push("<dt>"+mb[i].STARTTIME.substring(0,10) +" <span>["+mb[i].STATUS+"]</span><a href='"+basePath+"mobile/office/mobileoffice_drivingPic.jspa?photo1="+mb[i].PHOTO1+"&photo2="+mb[i].PHOTO2+"&photo3="+mb[i].PHOTO3+"' class='xs_det'>练车照片</a></dt>");
                    str.push("<dd>培训开始时间："+mb[i].STARTTIME+"</dd>");
                    str.push("<dd>培训结束时间："+mb[i].STARTTIME+"</dd>");
                    str.push("<dd>有效时间："+mb[i].DURATION+"分钟</dd>");
                    str.push("</dl>");
                }
                $(".xs_wrap").append(str.join(""));
            }
        })
    }
</script>
</html>
