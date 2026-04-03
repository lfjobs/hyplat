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
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css"/>
    <link rel="stylesheet" href="<%=basePath %>st/css/apply.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <title>产品分类</title>
</head>

<body>
<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>选择分类</h1>
</header>
<div class="wrap_page">
    <div class="select_type clearfix" id="productType">
        <a href="<%=basePath%>/st/enroll/ea_getAssociatedMall.jspa?allPro=1&companyID=${companyID}" class="s_type_box">全部</a>
    </div>
</div>

<script type="text/javascript">
    var companyId ='${companyID}';
    var basePath = "<%=basePath %>";
</script>
<script type="text/javascript">
    $(function () {
        var  url = basePath + "/st/enroll/sajax_ea_getProductByType.jspa?companyID="+companyId;
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            success : function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                var str = "";
                for (var i = 0; i < list.length; i++) {
                    var entity = list[i];
                    str+="<a href='<%=basePath%>/st/enroll/ea_getAssociatedMall.jspa?companyID="+entity[2]+"&categoryId="+entity[0]+"&categoryName="+entity[1]+"' class='s_type_box'>" + entity[1] + "</a>";
                }
                $("#productType").append(str);
            }
        });
    })
</script>
</body>

</html>
