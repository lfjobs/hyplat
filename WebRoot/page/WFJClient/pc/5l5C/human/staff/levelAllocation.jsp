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
    <title>級別分配</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/levelAllocation.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
        <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectTab.js"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/levelAllocation.js"></script>
</head>
<body class="body">
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            升降级（级别确认）
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="sec-nav sec-hide ">
        <ul class="clearfix" >
            <li class="clearfix  ">
                <p class="add">添加</p>
            </li>
            <li class="clearfix li-clearfix li-edit">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix li-clearfix li-del">
                <p class="del">删除</p>
            </li>
            <li class="clearfix ">
                <p class="query">详情</p>
            </li>
            <li class="clearfix li-clearfix li-examine" style="display:none">
                <p class="examine">审核</p>
            </li>
            <li class="clearfix li-clearfix li-approverPerson" style="display:none">
                <p class="approverPerson">转交审批</p>
            </li>
            <li class="clearfix li-clearfix li-level" style="display:none">
                <p class="level">级别生效</p>
            </li>

            <li class="clearfix  li-status" style="float:right">
                <p class="select">筛选</p>
            </li>
        </ul>
    </section>
    <section class="sec-list "  >
        <div class="div-sec-data" >
            <div class="data-title">
                <ul>
                    <li style="height:20px">  </li>
                    <li>时间</li>
                    <li>姓名</li>
                    <li>原级别</li>
                    <li>现级别</li>
                    <li>状态</li>
                    <li>基本保障</li>
                    <li>非工资福利津贴</li>
                    <li>周末加班</li>
                    <li>合计</li>
                </ul>
            </div>
            <form name="form" class="div-form layui-form" id="form">
                <div class="data-list div-data"  style="overflow-y:auto;overflow-x:hidden">
                </div>
            </form>

        </div>
    </section>

</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var staffId = "<%=ca.getStaffID()%>";
    var companyId = "<%=ca.getCompanyID()%>";
    var roleId = "<%=ca.getRoleID()%>";
    var status = "${param.status}" ;
    if (status === "" || status === undefined){
        status = "0";
    }
    var queryStaffId = "${param.staffId}";
    if (queryStaffId !== "" ){
        $(".sec-nav").hide();
    }

</script>
</body>
</html>
