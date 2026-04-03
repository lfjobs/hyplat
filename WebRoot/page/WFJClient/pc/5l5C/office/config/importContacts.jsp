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
    <title>数据导入</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/importContacts.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0, 0, 0);
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content1 {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            overflow: auto;
            /*height: 30%;*/
            margin-top: 60%;
            border-radius: 20px;
        }

        .btn-cancel, .btn-confirm {
            display: inline-block;
            vertical-align: middle;
            margin: 0 20px;
            padding: 10px 20px;
            cursor: pointer;
        }

        .substance1 {
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 10px;
            gap: 15px; /* 控制两个输入项之间的间距 */
            height: 50px;
            text-align: center;
            margin: 20px;
            font-size: 16px;
            color: #8e8b92;
        }

        .substance1 input {
            outline: none;
            font-size: 14px;
            padding: 5px 0;
            color: #1e1d1f;;
        }
        .modal-footer1 {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 50px;
        }
        .separator {
            width: 1px;
            height: 100%;
            background-color: #a89d9d;
            margin: 0 20px;
        }
        #editName, #editPhone {
            outline: none; /* 去除聚焦时的蓝色轮廓 */
            font-size: 14px;
            padding: 5px 0;
        }
        .data-title {
            height: 35px;
        }
        .div-sec-data ul {
             height: 35px;
            margin: 10px 0;}
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
            数据导入
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav sec-hide ">
        <ul class="clearfix">
            <li class="clearfix">
                <p class="importData">导入</p>
            </li>
            <li class="clearfix">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p class="del">删除</p>
            </li>
            <li class="clearfix">
                <p class="allocation">分配</p>
            </li>
        </ul>
    </section>
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="data-title">
                <ul  class="flex-container1" style="margin-left: 30px;display: flex">
                    <li>选择当前所有联系人</li>
                    <li class="selectedAll" style="margin-left: auto"><img
                            src="<%=basePath%>js/tree/codebase/imgs/iconUncheckAll.gif" alt="全不选"></li></ul>
<%--                <ul class="flex-container">--%>
<%--                    <li>序号</li>--%>
<%--                    <li>姓名</li>--%>
<%--                    <li>电话</li>--%>
<%--                </ul>--%>
            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>
    <!-- 自定义弹窗结构 -->
    <div id="btnModal" class="modal" style="display:  none;">
        <div class="modal-content1">
            <div class="title">
                <div style="font-size: 18px;color: #3d3a41;">修改客户信息:</div>
            </div>
            <div style="border-bottom: 1px solid #c3bbbb;">
                <div class="substance1" style="flex-direction: column; gap: 15px;">
                    <!-- 姓名 -->
                    <div>
                        <label style="width: 60px;">姓名：</label>
                        <input type="text" id="editName" name="name" style="flex: 1; border: none; background: transparent;" />
                    </div>

                    <!-- 手机号 -->
                    <div>
                        <label style="width: 60px;">手机号：</label>
                        <input type="text" id="editPhone" name="phone" style="flex: 1; border: none; background: transparent;" />
                    </div>
                </div>
            </div>
            <div class="modal-footer1">
                <div id="modal-btn-cancel1" class="btn-cancel">取消</div>
                <div class="separator"></div>
                <div id="modal-btn-confirm1" class="btn-confirm active1">确认</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>