<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applytype.css" />
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" href="<%=basePath%>js/Mdate/needcss/Mdate.css">
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_type.js"></script>

    <title>公司认证类型</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var organization_type = "${applyParam.organization_type}";
        var out_request_no = "${applyParam.out_request_no}";
        var  companyID = "${companyID}";
        var  staffID = "${staffID}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;"  >
            <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            公司认证
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <p class="p-lx">选择认证类型</p>
    <section class="sec-rad">
        <div class="clearfix"  data-index="2401">
            <div class="left">
                <p>个人卖家</p>
                <p>无需营业执照。</p>
            </div>
            <div class="right">
                <img src="<%=basePath%>images/ea/office/pbapply/img_002.png"/>
                <img src="<%=basePath%>images/ea/office/pbapply/img_003.png"/>
            </div>
        </div>
        <div class="clearfix" data-index="2">
            <div class="left">
                <p >企业</p>
                <p>需要营业执照</p>
            </div>
            <div class="right">
                <img src="<%=basePath%>images/ea/office/pbapply/img_002.png"/>
                <img src="<%=basePath%>images/ea/office/pbapply/img_003.png"/>
            </div>
        </div>
        <div class="clearfix"  data-index="4">
            <div class="left">
                <p>个体工商户</p>
                <p>需要营业执照</p>
            </div>
            <div class="right">
                <img src="<%=basePath%>images/ea/office/pbapply/img_002.png"/>
                <img src="<%=basePath%>images/ea/office/pbapply/img_003.png"/>
            </div>
        </div>
        <div class="clearfix" data-index="3">
            <div class="left">
                <p >党政、机关及事业单位</p>
                <p>如：交通、旅游、医疗、教育等机构</p>
            </div>
            <div class="right">
                <img src="<%=basePath%>images/ea/office/pbapply/img_002.png"/>
                <img src="<%=basePath%>images/ea/office/pbapply/img_003.png"/>
            </div>
        </div>
        <div class="clearfix" data-index="1708">
            <div class="left">
                <p>其他组织</p>
                <p>如社会团体、民办非企业、基金会</p>
            </div>
            <div class="right">
                <img src="<%=basePath%>images/ea/office/pbapply/img_002.png"/>
                <img src="<%=basePath%>images/ea/office/pbapply/img_003.png"/>
            </div>
        </div>
    </section>
    <div class="div-bottom">
        <p class="p-01" id="next">
            下一步
        </p>
        <div class="div-tiaokuan active">
            <img src="<%=basePath%>images/ea/office/pbapply/img_004.png"/>
            <img src="<%=basePath%>images/ea/office/pbapply/img_005.png"/>
            同意《数字地球功能服务条款》
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //个人/企业选择
    $(".sec-rad>div").click(function(){
        $(this).parent("section").children("div").removeClass("active");
        $(this).addClass("active");
    })

    //同意条款判断
    $(".div-tiaokuan").click(function(){
        if($(this).is(".active")){
            $(this).removeClass("active")
        }else{
            $(this).addClass("active")
        }
    })
</script>
</html>