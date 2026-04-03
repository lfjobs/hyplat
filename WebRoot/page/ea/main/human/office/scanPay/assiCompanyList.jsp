<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>中联园博览</title>

    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script src="<%=basePath%>js/WFJClient/setHtmlFont.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/WFJClient/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/district.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/base.css"/>
</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    $(function(){
        $(".overlay").addClass("active").find(".popup_box").show();

    });

</script>

<style type="text/css">
   .list_wrap{

       padding-top:0rem;
   }
   .popup_box{width: 12rem;border: .15rem solid #1b1b1b;border-radius: .25rem;position: relative;display: none;}
   .popup_bd{height: 6rem;background-color: #f93c3d;background-size: 60%;background-position: center;background-repeat: no-repeat;border-top-left-radius: .2rem;border-top-right-radius: .2rem;}
   .popup_fd{background: #ffffff;padding: .8rem .6rem .6rem;}
   .popup_fd>span{display: block;text-align: center;font-size: .683rem;color: #1b1b1b;line-height: 1rem;}
   .sure_btn{outline: 0;border: 0;display: block;width: 10rem;margin: 2rem auto .6rem;text-align: center;line-height: 1.8rem;background: #80d46f;border-radius: .15rem;color: #fff;font-size: .8rem;}
   .popup_text span{color: #bf0e0e;}

</style>
<body>
<header class="com_head">

        <a></a>
        <h1>公司商城</h1>

</header>
<c:if test="${fn:length(companylist)==0}">
    <div class="overlay">
        <div class="popup_box">
            <div></div>
            <div class="popup_fd">
                <span class="popup_text">您还没有公司商城</span>
                <span class="popup_text">升级公司会员开启您的商城</span>
                <a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany" class="sure_btn" >立即升级成为公司会员</a>
            </div>
        </div>
    </div>

</c:if>
<div class="wrap_page list_wrap" style="">
    <div class="content">
        <div class="c_list_wrap">

            <c:forEach items="${companylist}" var="item">
           <a href="<%=basePath%>ea/assicode/ea_getCompanyProList.jspa?ccompanyId=${item.ccompanyID}" class='c_box clearfix'>
           <input type="hidden" id="companyname" value="${item.companyName}"/>
           <input type='hidden' id='ccompanyId'  value="${item.ccompanyID}"/>
           <img src="<%=basePath%>${item.logoPath!=null?item.logoPath:'images/WFJClient/PersonalJoining/logo@2x.png'}" alt='' class='c_img'>
           <div class='c_text'><div class='c_type'><span>${item.industryType}</span></div>
           <div class='c_name'>${item.companyName}</div><div class='c_info'><span>${companyAddr}</span></div></div></a>
            </c:forEach>

        </div>
    </div>
</div>



</body>
</html>