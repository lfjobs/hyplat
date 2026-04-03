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
    <title>员工</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/staff/dataImport.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .data-ul1 {
            display: flex;
        }

        .data-title ul li:nth-child(1) {
            width: 70px;
        }

        .data-title ul li:nth-child(2) {
            width: 120px;
        }

        .data-title ul li:nth-child(3) {
            width: 150px;
        }

        .data-title ul li:nth-child(4) {
            width: 180px;
        }

        .data-title ul li:nth-child(5) {
            width: 180px;
        }

        .data-list ul li:nth-child(1) {
            width: 40px;
            margin-left: 20px;
        }

        .data-list ul li:nth-child(2) {
            width: 80px;
        }

        .data-list ul li:nth-child(3) {
            width: 150px;
        }

        .data-list ul li:nth-child(4) {
            width: 220px;
        }

        .data-list ul li:nth-child(5) {
            width: 60px;
            text-align: right;
        }

        .two {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 30px;
        }
        .tabContainer {
            display: flex;
            width: 100%;
        }

        .tab-item {
            flex: 1;
            text-align: center;
        }
    </style>
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
            未注册粉丝导入
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="category">
        <ul class="tabContainer" style="display:flex">
            <li class="tab-item imported two" >已导入</li>
            <li class="tab-item noImported two" >未导入</li>
        </ul>
<%--        <div id="tabContainer"--%>
<%--             style="display: flex; height: 30px; justify-content: space-between; border-bottom: 1px solid #373434; align-items: center; color: #242421;">--%>
<%--            <div class="tab-item imported two" style="flex: 1; text-align: center; cursor: pointer;">--%>
<%--                已导入--%>
<%--            </div>--%>
<%--            <div class="tab-item one" style="height: 30px; width: 1px; background-color: #373434; margin: 0 1px;"></div>--%>
<%--            <div class="tab-item noImported two" style="flex: 1; text-align: center; cursor: pointer;">--%>
<%--                未导入--%>
<%--            </div>--%>
<%--        </div>--%>
    </section>
    <section class="sec-nav sec-hide ">
        <ul class="clearfix">
            <li class="clearfix">
                <!-- 隐藏的 input 元素 -->
                <input type="file" id="excelFile" accept=".xlsx,.xls" style="display: none">
                <!-- 视觉上的“导入”按钮 -->
                <p class="importData" onclick="document.getElementById('excelFile').click()">导入</p>
            </li>
            <li class="clearfix sum1">
                <P>共 0 条数据</P>
            </li>
            <li class="clearfix sum">
                <P>勾选 0 条数据</P>
            </li>
        </ul>
    </section>
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="data-title">
                <ul class="flex-container">
                    <li>序号</li>
                    <li>姓名</li>
                    <li>电话</li>
                    <li>身份证</li>
                    <li class="selectedAll" style="margin-left: auto">全选<img
                            src="<%=basePath%>js/tree/codebase/imgs/iconCheckAll.gif" alt="全不选"></li>
                </ul>
            </div>
            <div class="data-list div-data" style="color: #151414;overflow-x: auto;overflow-y: auto;width: 100%">
            </div>
        </div>
        <section class="button" style="width: 100%;height: 50px;">
            <div>
                <div class="btn-submit"
                     style="border-radius: 15px;padding: 10px 12px;color: white;background-color: #f74c32;text-align: center;font-size: 15px;">
                    导入数据
                </div>
            </div>
        </section>
    </section>

</div>
<script>
    let jsonDataVos = [];
    document.getElementById('excelFile').addEventListener('change', function (e) {
        const file = e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function (e) {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, {type: 'array'});
            const sheetName = workbook.SheetNames[0];
            const worksheet = workbook.Sheets[sheetName];
            const jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
            const parse = JSON.parse(JSON.stringify(jsonData));
            for (let i = 1; i < parse.length; i++) {
                const jsonDataVo = {
                    "name": parse[i][0],
                    "number": parse[i][1],
                    "contact": parse[i][2],
                    "address": parse[i][3],
                    "extend": parse[i][4],
                    "social": parse[i][5]
                };
                jsonDataVos.push(jsonDataVo);
            }
            localStorage.setItem("dataImport", JSON.stringify(jsonData));
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/dataImport.jsp?type=wzcfsimport";
        };
        reader.readAsArrayBuffer(file);
    });
</script>
</body>
</html>
