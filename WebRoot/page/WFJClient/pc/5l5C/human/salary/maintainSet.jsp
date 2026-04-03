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
    <title>级别级差</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/salary/maintainSet.css">
        <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/salary/maintainSet.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1); return false" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            级别级差
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="" >
        <div class="layui-form div-form" >
            <div class="layui-form-item level-data" >
                <label class="form-label" style="color:#249133">时间</label>
                <div class="layui-input-block">
                    <label class="form-label" id="date">${param.date}</label>
                </div>
            </div>
            <div class="layui-form-item level-data">
                <label class="form-label" style="color:#0d9b21">级别序号</label>
                <div class="layui-input-block">
                    <label class="form-label" id="gradeName">${param.gradeName}</label>
                    <label class="form-label label-set " id="levelSet"> 设置</label>
                </div>
            </div>
            <div class="layui-form-item level-data">
                <label class="form-label" style="color:#0d9b21">级别编码</label>
                <div class="layui-input-block">
                    <label class="form-label" id="gradeNumStr">${param.gradeNumStr}</label>
                    <label class="form-label" id="gradeNum" style="display:none">${param.gradeNum}</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">基本保障</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " id="guaranteeSet" > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">非工资福利津贴</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " id="welfareSet"> 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">激励绩效</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">签到出勤</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">工资金额</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">加班4天\月</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">交保险</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">交公积金</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">交税金额</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">预支折扣</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="form-label">结算结清</label>
                <div class="layui-input-block">
                    <label class="form-label label-set " > 设置</label>
                </div>
            </div>
        </div>
    </section>
</div>

<script type="text/javascript">
    const basePath = "<%=basePath%>";
    const param = [];
    param.push("gradeName=" + "${param.gradeName}");
    param.push("&&gradeNum=" + "${param.gradeNum}");
    param.push("&&gradeNumStr=" + "${param.gradeNumStr}");
    param.push("&&date=" + "${param.date}");

</script>
</body>
</html>
