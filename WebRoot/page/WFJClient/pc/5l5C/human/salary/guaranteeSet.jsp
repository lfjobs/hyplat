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
    <title>基本保障</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/salary/guaranteeSet.css">
    <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/salary/guaranteeSet.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="returnPage()" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            基本保障
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section class="" >
        <div class="layui-form" >
            <div class="layui-form-item level-data" >
                <label class="form-label" style="color:#249133" >时间</label>
                <div class="layui-input-block">
                    <label class="form-label" id="date">${param.date}</label>
                </div>
            </div>
            <div class="layui-form-item level-data">
                <label class="form-label" style="color:#0d9b21">级别序号</label>
                <div class="layui-input-block">
                    <label class="form-label" id="gradeName">${param.gradeName}</label>
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
                    <label class="form-label label-no-set"  onclick="setGuaranteeAllData()"> 一键设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-basic">
                <label class="form-label">基本工资<span id="basicData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set"  onclick=setGuaranteeData("basic")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-role">
                <label class="form-label">职能工资<span id="roleData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set " onclick=setGuaranteeData("role")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-duty">
                <label class="form-label">职责工资<span id="dutyData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set " onclick=setGuaranteeData("duty")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-compete">
                <label class="form-label">竞职金<span id="competeData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set " onclick=setGuaranteeData("compete")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-secrecy">
                <label class="form-label">保密工资<span id="secrecyData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set " onclick=setGuaranteeData("secrecy")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item div-level">
                <label class="form-label">调平金额<span id="levelData" class="span-salary"></span></label>
                <div class="layui-input-block">
                    <label class="form-label label-no-set " onclick=setGuaranteeData("level")> 未设置</label>
                </div>
            </div>
            <div class="layui-form-item guarantee-item">
                <label class="form-label">正常5天\周\月</label>
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
