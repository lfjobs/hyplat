<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<base href="<%=basePath%>">

<title>&lrm;</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/templateManage.css">
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var staffID = "${param.staffID}";
</script>
<style>
    .ul-tab-fh li {
        padding: 0 3%;
        width: 40%;
    }
</style>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            项目物品
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li class="projectType">
                    <p id="ul-tab1" class="active">项目分类</p>
                </li>
                <li class="itemManagement">
                    <p id="ul-tab2">物品管理</p>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="iframecom" >
    <iframe id="iframe"  name="iframe" src="<%=basePath%>/page/WFJClient/pc/5l5C/office/businessTypeManage.jsp?version=1.1" width="100%" height="100%" frameborder="0"></iframe>
</div>


<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="tittle-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $(".ul-tab-fh li p").click(function () {
            $(".ul-tab-fh .active").removeClass("active");
            $(this).addClass("active");

            var id = $(this).attr("id");
            $(".tabs").hide();
            $("."+id).show();
            $(".tabs .active").removeClass("active");
            $("."+id).find("li").find("p").eq(0).addClass("active");
            $("."+id).find("li").find("p").eq(0).trigger("click");
            // console.log($(this).attr("class"));
            // console.log($(this).parent().attr("class"));
            jumptab($(this).parent().attr("class"));
        });
    });


    function jumptab(defaults){
        var url;
        if(defaults=="projectType"){
             url="/page/WFJClient/pc/5l5C/office/businessTypeManage.jsp?version=1.1";

        }else if(defaults=="itemManagement"){
            url="/page/WFJClient/ProductsLaunch/ProductManage.jsp?companyID="+companyID+"&staffID="+staffID;


        }
        var original = document.getElementsByTagName("iframe")[0];
        var newFrame = document.createElement("iframe");

        newFrame.setAttribute('frameborder', '0', 0);
        newFrame.src = basePath+url;
        var parent = original.parentNode;
        parent.replaceChild(newFrame,original);
    }
</script>
</html>

