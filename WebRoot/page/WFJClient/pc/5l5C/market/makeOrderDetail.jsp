<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String cashierBillsID = request.getParameter("cashierBillsID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成交客户</title>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/selectCompany.css?version=1">
    <style>
        .order_head li {
            height: 30px;
            width: 8.83rem;
            float: left; /* 设置高度为40像素，根据您的需求调整值 */
        }

        hr {
            border-style: dashed;
            border-color: gray;
        }

        .container {
            text-align: center; /* 这会使内部内容水平居中 */
            margin-top: 30px;
        }

        .print-button {
            color: black;
            font-weight: bold;
            font-size: 1.0em;
        }
        .orderInformation {
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* 这里设置阴影的样式 */
            border-radius: 10px 20px 10px 20px;
            margin-bottom: 10px;
            width: 93%;
            margin-left: auto; /* 左侧外边距设为自动，使元素水平居中 */
            margin-right: auto; /* 右侧外边距也设为自动，保持元素水平居中 */
            paddin: 10px;
        }
        .orderleft {
            float: left;
            color: #6C6C6C;
            padding-left: 8px;
        }
        .orderright {
            float: right;
            color: black;
            padding-right: 8px;
        }
    </style>
</head>

<body>
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>订单详情</li>
            </ul>
        </header>
    </div>
</div>

<div id="orderDetail">
    <!-- 订单详情显示内容 -->
</div>


<div class="container">
    <!-- 添加一个打印按钮 -->
    <button class="print-button" onclick="window.print()">打印</button>
</div>

<script>
    $(document).ready(function () {
        var url = "<%=basePath%>" + "ea/turnovermanage/ea_getOrderDetail.jspa";
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data: {
                cashierBillsID: "<%=cashierBillsID%>"
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var orderDetail = member.orderDetail;
                if (orderDetail == null) {
                    allOrderContent.html("暂无订单数据。");
                }
                var divElement = document.getElementById("orderDetail");
                var fkDate = '';
                var shDate = '';
                if (orderDetail.fkDate != null) {
                    fkDate = formatTime(orderDetail.fkDate.time);
                }
                if (orderDetail.shDate != null) {
                    shDate = formatTime(orderDetail.shDate.time);
                }
                var html = "";
                html += "<br>"
                html += "<div class='orderInformation'>"
                html += "<span style='float: left;font-weight: bold;padding-left: 3px'>订单信息:</span><br>";
                html += "<span class='orderleft'>订单编号：</span><span class='orderright'>" + orderDetail.journalNum + "</span><br>";
                html += "<span class='orderleft'>商品名称：</span><span class='orderright'>" + orderDetail.goodsName + "</span><br>";
                html += "<span class='orderleft'>规 格：</span><span class='orderright'>" + orderDetail.standard + "</span><br>";
                html += "<span class='orderleft'>单 位：</span><span class='orderright'>" + orderDetail.goodsVariableID + "</span><br>";
                html += "<span class='orderleft'>单 价：</span><span class='orderright'>" + orderDetail.price + "</span><br>";
                html += "<span class='orderleft'>金 额：</span><span class='orderright'>" + orderDetail.priceSub + "</span><br>";
                html += "<span class='orderleft'>下单时间：</span><span class='orderright'>" + orderDetail.standard + "</span><br><br>";
                html += "</div>"
                html += "<div class='orderInformation'>"
                html += "<span style='float: left;font-weight: bold;padding-left: 3px'>支付信息:</span><br>";
                html += "<span class='orderleft'>付款时间：</span><span class='orderright'>" + fkDate + "</span><br>";
                html += "<span class='orderleft'>支付方式：</span><span class='orderright'>" + orderDetail.payType + "</span><br>";
                html += "<span class='orderleft'>转款附件：</span><span class='orderright'>" + orderDetail.attachment + "</span><br><br>";
                html += "</div>"
                html += "<div class='orderInformation'>"
                html += "<span style='float: left;font-weight: bold;padding-left: 3px'>收货信息:</span><br>";
                html += "<span class='orderleft'>收货姓名：</span><span class='orderright'>" + orderDetail.receivename + "</span><br>";
                html += "<span class='orderleft'>收货电话：</span><span class='orderright'>" + orderDetail.receivetel + "</span><br>";
                html += "<span class='orderleft'>收货地址：</span><span class='orderright'>" + orderDetail.receiveaddress + "</span><br>";
                html += "<span class='orderleft'>收货时间：</span><span class='orderright'>" + shDate + "</span><br><br>";
                html += "</div>"
                html += "<div class='orderInformation'>"
                html += "<span style='float: left;font-weight: bold;padding-left: 3px'>状态:</span><br>";
                html += "<span class='orderleft'>收款状态：</span><span class='orderright'>" + orderDetail.skStatus+"</span><br>";
                html += "<span class='orderleft'>合同状态：</span><span class='orderright'>" + orderDetail.skStatus+"</span><br>";
                html += "</div>"
                divElement.innerHTML = html;
            },
            error: function () {
                console.log("出错了")
            }
        });
    })

    /* 格式化时间 - 毫秒值转换年月日 */
    function formatTime(milliscond) {
        var date = new Date(milliscond);
        var year = date.getFullYear();
        var month = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        var hour = ("0" + date.getHours()).slice(-2);
        var formattedDate = year + "-" + month + "-" + day;
        return formattedDate + " " + hour + ":00";
    }
</script>


</body>


</html>
