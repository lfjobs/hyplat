<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    CAccount ca = (CAccount) session.getAttribute("account");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>入职管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/entryManage.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
       <%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/entryStatistics.css">--%>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
       <%-- <script src="<%=basePath%>js/echarts.js" type="text/javascript" charset="utf-8"></script>--%>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/entryManage.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            入职管理
        </li>
        <li></li>
    </ul>
</header>

<div class="content">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li class="layui-this select-li-0" id="0">人员入职</li>
            <li class="select-li-1" id="1">岗位分析</li>
            <li class="select-li-2" id="2">入职合同</li>
        </ul>
        <div class="layui-tab-content" style="padding:0">
            <div class="layui-tab-item layui-show">
                <section class="sec-nav sec-hide ">
                    <ul class="clearfix" >
                        <li class="clearfix">
                            <p class="add">人员分配</p>
                        </li>
                        <li class="clearfix" style="float:right">
                            入职人数：<label class="personData">0</label>人
                        </li>
                    </ul>
                </section>
                <div class="spd-content">
                    <div class="dtd-oa-search-bar" style="margin-top:-10px">
                        <div class="dtd-oa-search-bar-icon-wrapper query-data  ">
                            <i class="layui-icon">&#xe615;</i>
                        </div>
                        <input type="text" class="dtd-oa-search-bar-input" placeholder="姓名/手机号"  name="queryName" id="queryName" autocomplete="off">
                        <div class="dtd-oa-search-bar-icon-wrapper close-data" onclick="clearQueryName()" style="right: 75px;">
                            <i class="layui-icon">&#x1006;</i>
                        </div>
                        <button class="layui-btn layui-btn-primary layui-btn-sm " onclick="getDataByQueryName()" style="margin-left:10px;margin-top:-4px">搜索</button>
                    </div>
                </div>
                <section class="sec-list "  >
                    <div class="div-sec-data" >
                        <div class="data-title">
                            <ul>
                                <li>序号</li>
                                <li>姓名</li>
                                <li>性别</li>
                                <li>入职时间</li>
                                <li>电话</li>
                                <li>专兼职</li>
                                <li>人员编号</li>
                                <li>身份证</li>
                                <li>级别</li>
                            </ul>
                        </div>
                        <div class="data-list div-data"  style="overflow-y:auto;overflow-x:hidden">
                        </div>
                    </div>
                </section>
            </div>
            <div class="layui-tab-item">
                <div class="div-statistics">
                    <iframe  name="iframe" src="" width="100%" height="100%" frameborder="0"></iframe>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="layui-tabs div-staff-contract" >
                   <iframe id="iframe-contract"  name="iframe" src="" width="100%" height="100%" frameborder="0"></iframe>

                </div>
            </div>


        </div>
    </div>

</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var curstaffID = "${curstaffID}";
    var module = "contract";
    var companyID = "<%=ca.getCompanyID()%>";
</script>
</body>
</html>
