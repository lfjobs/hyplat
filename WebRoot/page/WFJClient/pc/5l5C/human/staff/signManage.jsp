<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/signManage.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>util/jdate/jdate.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/signManage.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="returnPage()" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            管理
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="sec-nav sec-hide ">
        <ul class="clearfix" >
            <li class="clearfix">
                <p class="add">添加</p>
            </li>
            <li class="clearfix">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p class="del">删除</p>
            </li>
        </ul>
    </section>
    <section class="sec-list "  >
        <div class="div-sec-query" style="width:100%;height:40px;float:left">
            <input type="text"    id="beginDate" class="" style="width:100px !important;height:30px;float:left"/>
            <span style="float:left;margin-left:10px">至</span>
            <input type="text"    id="endDate" class="" style="width:100px !important;height:30px;float:left;margin-left:10px" />
            <button class="layui-btn  layui-btn-sm" onclick="initData()" style="float:left;margin-left:10px">查询</button>
        </div>
        <div class="div-sec-data" style="float:left;width:100%" >
            <div class="data-title">
                <ul>
                    <li>电话</li>
                    <li>星期</li>
                    <li>时间</li>
                </ul>
            </div>
            <div class="data-list div-data"  style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>
    <section class="sec-detail "  >
        <form name="form" class="div-form" id="form">
            <div class="div-base-data">
                <div class="div-name">
                    <label ><span style="color:red">*</span>手机号</label>
                    <input type="text" id="account" readonly/>
                    <input type="hidden"   id="signKey" />
                </div>
            </div>
            <div class="div-name" >
                <label ><span style="color:red">*</span>时间</label>
                <input type="text"  placeholder="请选择入职时间"  name="signDate" id="signDate"  />
            </div>
        </form>
        <div class="div-bottom"  onclick="saveData()">保存</div>
    </section>

</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const account= "${param.account}";

</script>
</body>
</html>
