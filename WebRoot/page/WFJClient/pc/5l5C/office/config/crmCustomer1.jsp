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
    <title>未注册粉丝导入</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/crmCustomerPo.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .data-title ul,
        .data-ul1 {
            min-width: max-content;
        }

        /* 列宽定义 */
        .data-title ul li:nth-child(1) {
            width: 40px;
        }

        .data-title ul li:nth-child(2) {
            width: 80px;
        }

        .data-title ul li:nth-child(3) {
            width: 100px;
        }

        .data-title ul li:nth-child(4) {
            width: 90px;
        }

        /*.data-title ul li:nth-child(5) {*/
        /*    width: 180px;*/
        /*}*/

        .data-title ul li:nth-child(6) {
            width: 120px;
        }

        /* 列宽定义 */
        .data-list ul li:nth-child(1) {
            width: 40px;
            margin-left: 20px;
        }

        .data-list ul li:nth-child(2) {
            width: 80px;
        }

        .data-list ul li:nth-child(3) {
            width: 100px;
        }

        .data-list ul li:nth-child(4) {
            width: 190px;
        }

        .data-list ul li:nth-child(5) {
            width: 180px;
        }

        .data-list ul li:nth-child(6) {
            width: 90px;
        }

        .data-ul1 {
            display: flex;
            align-items: stretch;
        }

        .push-li3 {
            margin-right: 30px;
            color: #151414;
        }

        .content .sec-nav ul li {
            margin-left: 10px;
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
<%--            <div class="tab-item one"--%>
<%--                 style="height: 30px; width: 1px; background-color: #373434; margin: 0 1px;"></div>--%>
<%--            <div class="tab-item noImported two" style="flex: 1; text-align: center; cursor: pointer;">--%>
<%--                未导入--%>
<%--            </div>--%>
<%--        </div>--%>
    </section>
    <section class="sec-nav sec-hide ">
        <ul class="clearfix">
            <li class="clearfix">
                <p class="add">添加</p>
            </li>
            <%--            <li class="clearfix">--%>
            <%--                <p class="edit">修改</p>--%>
            <%--            </li>--%>
            <%--            <li class="clearfix">--%>
            <%--                <p class="del">删除</p>--%>
            <%--            </li>--%>
            <%--            <li class="clearfix">--%>
            <%--                <p class="query">查询</p>--%>
            <%--            </li>--%>
            <%--            <li class="clearfix">--%>
            <%--                <!-- 隐藏的 input 元素 -->--%>
            <%--                <input type="file" id="excelFile" accept=".xlsx,.xls" style="display: none">--%>
            <%--                <!-- 视觉上的“导入”按钮 -->--%>
            <%--                <p class="importData" onclick="document.getElementById('excelFile').click()">导入</p>--%>
            <%--            </li>--%>
            <li class="clearfix">
                <p class="allocation">分配</p>
            </li>
            <li class="clearfix">
                <p class="outboundCallTask">拨打任务</p>
            </li>
            <li class="clearfix">
                <p class="systemData">系统数据</p>
            </li>
            <li class="clearfix">
                <p class="sendMessage">短信</p>
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
                    <%--                    <li>身份证</li>--%>
                    <%--                    <li>时间</li>--%>
                    <li>导入人</li>
                    <li class="selectedAll" style="margin-left: auto">全选<img
                            src="<%=basePath%>js/tree/codebase/imgs/iconUncheckAll.gif" alt="全不选"></li>
                </ul>
            </div>
            <div class="data-list div-data"
                 style="color: rgb(21, 20, 20); overflow-y: auto; overflow-x: hidden; width: 100%;">
            </div>
        </div>
    </section>
</div>
<script>
    // document.getElementById('excelFile').addEventListener('change', function (e) {
    //     const file = e.target.files[0];
    //     if (!file) return;
    //
    //     const reader = new FileReader();
    //     reader.onload = function (e) {
    //         const data = new Uint8Array(e.target.result);
    //         const workbook = XLSX.read(data, {type: 'array'});
    //         const sheetName = workbook.SheetNames[0];
    //         const worksheet = workbook.Sheets[sheetName];
    //
    //         const jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
    //         localStorage.setItem("dataImport", JSON.stringify(jsonData));
    //         document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/dataImport.jsp?type=wzcfsimport";
    //         console.log(jsonData);
    //         // if (!jsonData || jsonData.length === 0) {
    //         //     $(".data-list").html("暂无数据");
    //         //     $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
    //         // } else {
    //         //     const list = jsonData;
    //         //     const htmlstr = [];
    //         //     for (let i = 1; i < list.length; i++) { // 从第1行开始渲染
    //         //         htmlstr.push("<ul id='" + list[i][1] + "' cosid='" + list[i][1] + "' class='data-ul data-ul-" + list[i][1] + "'>");
    //         //         htmlstr.push("<li name='" + (list[i][0] == null ? " " : list[i][0]) + "' class='push-li1'>" + (list[i][0] == null ? " " : list[i][0]) + "</li>");
    //         //         htmlstr.push("<li phone='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li3'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
    //         //         htmlstr.push("<li cardId='" + (list[i][1] == null ? " " : list[i][1]) + "' class='push-li2'>" + (list[i][1] == null ? " " : list[i][1]) + "</li>");
    //         //         htmlstr.push("</ul>");
    //         //     }
    //         //     $(".data-list").html(htmlstr.join(""));
    //         // }
    //     };
    //     reader.readAsArrayBuffer(file);
    // });

</script>
</body>
</html>