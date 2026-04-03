<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectMember.css?version=20230517"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/plan/selectMember.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
            <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
                </a>
        </li>
        <li>
            ${param.orgName eq null?'集团内部':param.orgName}
        </li>
    </ul>
</header>
<div class="content">
    <%--<section class="sec-search">--%>
        <%--<label for="">--%>
            <%--<img src="<%=basePath%>images/ea/office/contract/selectp/img_01.png"/>--%>
        <%--</label>--%>
        <%--<input type="text" placeholder="请输入姓名或者手机号" value="" id="search"  />--%>
    <%--</section>--%>

        <section class="sec-search">
            <div class="box clearfix">
                <label for="">
                    <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
                </label>
                <input type="text" name="" id="search" placeholder="请输入姓名或者手机号" />
            </div>
            <div><input type="button" name="" id="qsearch" value="搜索" /></div>
        </section>
        <p class="resulttip">没有查询到该人员</p>
        <p class="navrecent">以下是最近联系人：</p>
    <section class="sec-ul">
        <ul class="ul-list">

        </ul>
    </section>
    <section class="sec-bottom">
        <p>
           确定提交
        </p>
    </section>
</div>



<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>


<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
        <p>提交中...</p>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var orgID = "${param.orgID}";
    var keyId = "${param.keyId}";
    var type = "${param.type}";
    var menuId = "${menuId}";
    var sccid='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
</script>
</html>

