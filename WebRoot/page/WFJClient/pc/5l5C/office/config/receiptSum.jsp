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
    <title>收款单汇总</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/receiptSum.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/receiptSum.js" type="module"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            收款单汇总
        </li>
        <li>
        </li>
    </ul>
</header>
<section>
    <div class="byDate">
        <div>日期:</div>
        <div class="date-inputs"><input type="text" size="10" name="sdate" onfocus="date(this)" id="sdates">
            &nbsp;-&nbsp;
            <input type="text" size="10" name="edate" onfocus="date(this)" id="edates">
            <input type="button" value="查询" id="tosearch"></div>
    </div>
    <div class="byProjectName">
        <div>项目名称:</div>
        <div class="projectName">
            <select>
                <option>
                    人员场地进出费用
                </option>
                <option>
                    包月计时
                </option>
                <option>
                    包天计时
                </option>
                <option>
                    扫码付款
                </option>
            </select>
            <input type="button" value="查询" id="tosearch1"></div>
    </div>


</section>
<%--主数据--%>
<section class="sec-list">
    <div class="div-sec-data">
        <div class="data-title">
        <ul class="flex-container">
            <li>序号</li>
            <li>项目名称</li>
            <li>公司名称</li>
            <li>付款方名称</li>
            <li>付款金额</li>
            <li>付款时间</li>
        </ul></div>
        <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden">
    </div>

    </div>
</section>
</body>
<script type="text/javascript">
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        // 执行一个laydate实例
        laydate.render({
            elem: '#sdates' // 绑定元素

        });
        laydate.render({
            elem: '#edates' // 绑定元素

        });
    });
</script>
</html>
