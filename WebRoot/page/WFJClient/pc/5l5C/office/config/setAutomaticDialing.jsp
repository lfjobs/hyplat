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
    <title>自动拨号设置</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staffSend.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
        let pageNumber = 1, pageSize = 40, pageCount = 0;
        let scrollBool = true;
        var query = null;
        var typeValue = '';
        let selectedDatas = [];
        let selectedDatas3 = [];
        let selectedDatas2 = [];
    </script>
    <style>
        .div1 {
            display: flex;
            justify-content: space-between; /* 使内容分散对齐，图片在最右侧 */
            align-items: center; /* 垂直居中 */
            padding: 10px;
        }

        .div2 {
            display: flex;
            justify-content: space-between; /* 使内容分散对齐，图片在最右侧 */
            align-items: center; /* 垂直居中 */
            padding: 10px;
        }

        .div1 .title {
            font-size: 16px;
            color: #333;
        }

        .div2 .title {
            font-size: 16px;
            color: #333;
        }

        .data {
            margin-left: auto;
            padding: 10px;
        }

        .data2 {
            margin-left: auto;
            padding: 10px;
        }

        .div1 img {
            max-height: 15px; /* 可选：根据需求调整图片大小 */
        }

        .div2 img {
            max-height: 15px; /* 可选：根据需求调整图片大小 */
        }

        .btn-submit1 {
            border-radius: 15px;
            padding: 10px 12px;
            color: white;
            background-color: #f74c32;
            text-align: center;
            font-size: 15px;
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
            自动拨号设置
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="center">
    <section style="height: 100vh;">
        <div style="height: 80%">
            <div class="div1">
                <div class="title">联系人</div>
                <div class="data"></div>
                <div><img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"></div>
            </div>
            <div class="div2">
                <div class="title">电话录音</div>
                <div class="data2"></div>
                <div><img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"></div>
            </div>
        </div>

        <div class="btn-submit1">保存</div>
    </section>
</div>

</body>
<script>

    $(function () {
        var typeValue = getParameterByName("type");
        selectedDatas2 = JSON.parse(localStorage.getItem("selectedDatas2"));
        selectedDatas3 = JSON.parse(localStorage.getItem("selectedDatas3"));
        selectedDatas = JSON.parse(localStorage.getItem("selectedDatas1"));
        if (typeValue === "select") {
            // 从 localStorage 中读取 selectedDatas1
            if (selectedDatas2) {
                $(".data").text(selectedDatas2.length);
                console.log("Selected Datas:", selectedDatas2);
                if (selectedDatas3){
                    $(".data2").text(selectedDatas3.length);
                    console.log("Selected Datas:", selectedDatas3);
                }else {
                    $(".data2").text("");
                }

                // 在这里可以进一步处理 selectedDatas 数据
            } else {
                console.log("No selected datas found.");
            }
        } else if (typeValue === "lv") {
            // 从 localStorage 中读取 selectedDatas1
            if (selectedDatas3) {
                $(".data2").text(selectedDatas3.length);
                console.log("Selected Datas:", selectedDatas3);
                if (selectedDatas2){
                    $(".data").text(selectedDatas2.length);
                    console.log("Selected Datas:", selectedDatas2);
                }else {
                    $(".data").text(selectedDatas.length);
                }
                // 在这里可以进一步处理 selectedDatas 数据
            } else {
                console.log("No selected datas found.");
            }
        } else if (typeValue === "all") {
            // 从 localStorage 中读取 selectedDatas1
            if (selectedDatas) {
                $(".data").text(selectedDatas.length);
                console.log("Selected Datas:", selectedDatas);
                // 在这里可以进一步处理 selectedDatas 数据
            } else {
                console.log("No selected datas found.");
            }
        }else {
            // 从 localStorage 中读取 selectedDatas1
            if (selectedDatas) {
                $(".data").text(selectedDatas.length);
                console.log("Selected Datas:", selectedDatas);
                // 在这里可以进一步处理 selectedDatas 数据
            } else {
                console.log("No selected datas found.");
            }
        }

    });

    //查询
    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }

    //点击批量设置
    $(document).on("click", ".div1", function (event) {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/selectContactPerson.jsp?type=person";
    });    //点击批量设置
    $(document).on("click", ".div2", function (event) {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/selectContactPerson.jsp?type=lv";
    });

</script>
</html>
