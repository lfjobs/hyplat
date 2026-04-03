<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    CAccount ca = (CAccount) session.getAttribute("account");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>入职合同</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/employmentContract.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/employmentContract.js"></script>
</head>
<body class="body">
<header id="div-header">
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            入职合同
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <div class="layui-tab layui-tab-brief" style="height:100%">
        <ul class="layui-tab-title div-contract-tabs">
            <li class="layui-this" id="contract_0">合同模板</li>
            <li id="contract_1">已选上传模板</li>
            <li id="contract_2">合同状态</li>
        </ul>
        <div class="layui-tab-content layui-tab-contract" style="padding:5px 0">
            <div class="layui-tab-item layui-show div-contract_0">
                <section class="sec-nav sec-hide div-temp-nav" style="margin-top:10px">
                    <ul class="clearfix" >
                        <li class="clearfix " style="margin-left:10px">
                            <p class="draft" style="height: 24px;font-size: 14px"><img style="height:100%;marign-right:3px" src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>新增模板</p>

                        </li>
                    </ul>
                </section>
                <div style="width:100%;height:100%" class="contrat-temp-data">
                    <iframe id="iframe-contract"  name="iframe" src="" width="100%" height="100%" frameborder="0"></iframe>
                </div>
            </div>
            <div class="layui-tab-item div-contract_1 ">
                <div class="contract-temp-data" style="overflow: hidden auto; height: 639px;"></div>
            </div>
            <div class="layui-tab-item div-contract_2">
                <div class="spd-content">
                    <div class="dtd-oa-search-bar">
                        <div class="dtd-oa-search-bar-icon-wrapper query-data  ">
                            <i class="layui-icon">&#xe615;</i>
                        </div>
                        <input type="text" class="dtd-oa-search-bar-input" placeholder="姓名/手机号"  name="queryName" id="queryNameContract" autocomplete="off">
                        <div class="dtd-oa-search-bar-icon-wrapper close-data" onclick="clearQueryName()" style="right: 75px;">
                            <i class="layui-icon">&#x1006;</i>
                        </div>
                        <button class="layui-btn layui-btn-primary layui-btn-sm " onclick="getDataByQueryName()" style="margin-left:10px;margin-top:-4px">搜索</button>
                    </div>
                </div>
                <section class="sec-list-contract "  >
                    <div class="div-sec-data-contract" >
                        <div class="data-title-contract">
                            <ul>
                                <li>姓名</li>
                                <li>合同类型</li>
                                <li>合同状态</li>
                                <%--<li>合同位置</li>--%>
                                <li>性别</li>
                                <li>入职时间</li>
                                <li>电话</li>
                                <li>专兼职</li>
                                <li>人员编号</li>
                                <li>身份证</li>
                                <li>级别</li>
                            </ul>
                        </div>
                        <div class="data-list-contract div-data-contract"  style="overflow-y:auto;overflow-x:hidden">
                        </div>
                    </div>
                </section>
            </div>


        </div>

    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "<%=ca.getCompanyID()%>";
    var curstaffID = "<%=ca.getStaffID()%>";
    const type = "${param.type}";
    var queryStaffId = "${param.staffID}";
    if (queryStaffId !== "" ){
        $(".div-contract-tabs").hide();
    }

</script>
</body>
</html>
