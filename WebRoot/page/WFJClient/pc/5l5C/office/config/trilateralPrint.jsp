<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>三方平台推广打印</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/finance/invoicing/csbsprint.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            font-size: 14px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .info-table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }

        .info-table th, .info-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .info-table th {
            background-color: #f2f2f2;
            width: 120px;
        }

        .info-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .div-bottom {
            margin-left: 300px;
        }
        .div-bottom p {
            background-color: #83818d;
            border-radius: 0.4rem;
            width: 2.5rem;
            margin-left: 60px;
            text-align: center;
            height: 2.5rem;
            line-height: 2.5rem;
            font-size: 1rem;
            color: #fff;
        }
    </style>
    <script type="text/javascript">
        var taxstatusDu = "${cashierBills.status}";
    </script>
</head>
<body>
<div class="div-bottom">
    <p onclick="print()">
        打印
    </p>
</div>
<h2>三方平台推广数据</h2>
<table class="info-table">
    <tr>
        <th>公司名称</th>
        <td>${trilateralData.companyName}</td>
    </tr>
    <tr>
        <th>人员编号</th>
        <td>${trilateralData.personnelID}</td>
    </tr>
    <tr>
        <th>姓名</th>
        <td>${trilateralData.name}</td>
    </tr>
    <tr>
        <th>性别</th>
        <td>${trilateralData.sex}</td>
    </tr>
    <tr>
        <th>行业</th>
        <td>${trilateralData.sector}</td>
    </tr>
    <tr>
        <th>电话</th>
        <td>${trilateralData.phone}</td>
    </tr>
    <tr>
        <th>注册平台名称</th>
        <td>${trilateralData.registrationPlatformName}</td>
    </tr>
    <tr>
        <th>注册号</th>
        <td>${trilateralData.registrationNo}</td>
    </tr>
    <tr>
        <th>注册账号</th>
        <td>${trilateralData.registerAccount}</td>
    </tr>
    <tr>
        <th>密码</th>
        <td>${trilateralData.pwd}</td>
    </tr>
    <tr>
        <th>推广方式</th>
        <td>${trilateralData.uploadProject}</td>
    </tr>
    <tr>
        <th>事由</th>
        <td>${trilateralData.promotionReasons}</td>
    </tr>
    <tr>
        <th>分配状态</th>
        <td>${trilateralData.distributionPersonName eq '1' ? '已分配' : (trilateralData.distributionPersonName eq '0' || empty trilateralData.distributionPersonName ? '未分配' : '未分配')}</td>    </tr>
    <tr>
        <th>责任人</th>
        <td>${trilateralData.businessPersonnel}</td>
    </tr>
    <tr>
        <th>私信</th>
        <td>${trilateralData.privateMessage}</td>
    </tr>
    <tr>
        <th>审核状态</th>
        <td>${trilateralData.audit1}</td>
    </tr>
</table>
</body>
<script>


    function print() {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        if (isAndroid || isiOS) {
            alert("请在电脑端进行打印操作");
            return;
        }

        // 修正选择器，打印整个表格或指定的打印区域
        $("table.info-table").print({
            globalStyles: true,
            mediaPrint: false,
            stylesheet: null,
            noPrintSelector: ".no-print",
            iframe: true,
            append: null,
            prepend: null,
            deferred: $.Deferred()
        });
    }


</script>
</html>
