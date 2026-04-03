<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>现金支付</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/inventoryAudit.css"/>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBasicStyle.css" rel="stylesheet">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <%--    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/inventoryAudit.js"></script>--%>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyId = "${param.companyId}";
        var staffID = "${param.staffID}";
    </script>

    <style>
        html, body {
            height: 100%;
            overflow: hidden;
        }

        header {
            background-color: #f74c32;
        }

        header ul {
            padding: 0 .5rem
        }

        header ul li {
            float: left;
            height: 2rem;
            line-height: 2rem;
            font-size: 0.7rem;
            color: #ffffff;
        }

        header ul li:first-of-type {
            width: 20%
        }

        header ul li:first-of-type img {
            width: .6rem
        }

        header ul li:nth-of-type(2) {
            width: 58%;
            text-align: center
        }

        header ul li:nth-of-type(3) {
            width: 20%;
            text-align: right;
            font-size: .6rem
        }

        .content .category {
            padding: 0px;
            padding-top: 0.3rem;
        }

        .payment-info {
            background: #fff;
            margin: 10px 15px;
            padding-left: 40px;
            padding-top: 10px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .payment-info p {
            margin: 10px 0;
            font-size: 0.8rem;
            color: #333;
            line-height: 1.8;
        }

        .payment-info p strong {
            font-weight: bold;
            color: #000;
        }

        .password-input p {
            font-size: 0.75rem;
            color: #666;
            margin-bottom: 10px;
        }

        .password-box {
            display: flex;
            gap: 8px;
            justify-content: flex-start;
        }

        .password-box input {
            width: 1.5rem;
            height: 1.5rem;
            border: 1px solid #666262;
            border-radius: 4px;
            text-align: center;
            font-size: 1.2rem;
            font-weight: bold;
            color: #333;
        }


        .payment-method {
            background: #fff;
            margin: 10px 15px;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .payment-method p {
            font-size: 0.8rem;
            color: #1890ff;
            margin: 0;
            font-weight: bold;
        }

        .transfer-record {
            background: #fff;
            margin: 10px 15px;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .transfer-record span {
            font-size: 0.8rem;
            color: #1890ff;
            font-weight: bold;
        }

        .transfer-record input[type="checkbox"] {
            width: 1.2rem;
            height: 1.2rem;
        }

        .submit-btn {
            margin: 20px 15px;
            background: linear-gradient(to right, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-size: 0.9rem;
            font-weight: bold;
            width: calc(100% - 30px);
            cursor: pointer;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }

        .submit-btn:active {
            opacity: 0.8;
        }

    </style>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" alt="返回">
            </a>
        </li>
        <li>现金支付</li>
        <li></li>
    </ul>
</header>
<div class="content">
    <section class="category">

        <div style="height: 2rem;width: 100%;background-color: #607997eb; position: relative;">
            <div class="category-title">
                <img style="height: 1.3rem;padding: 10px;" src="<%=basePath%>images/icons/img_8.png">
            </div>
            <span style="color: white;font-size: 0.7rem; position: absolute; right: 10px; bottom: 5px;">请在支付安全的情况下进行密码操作</span>
        </div>
    </section>
    <section class="data-title" style="text-align: right; padding: 10px;">
        <div onclick="javascript: window.history.back() ;return false;" style=" display: inline-block; border: 1px solid
             green; padding: 5px 15px; border-radius: 4px; color: green; font-size: 0.7rem;
        ">
            返回
        </div>
    </section>

    <section>
        <div style="color: #0a84ff;padding-left: 30px;">现金兑换支付</div>
    </section>


    <!-- 支付信息区域 -->
    <section class="payment-info">
        <p><strong>应收：</strong><span id="receivableAmount">XXXX</span>元（共<span id="totalCount">XXXX</span>件）</p>
        <p><strong>实收：</strong><input type="number" id="actualAmount"
                                        style="border: none; border-bottom: 1px solid #ddd; width: 100px; text-align: right; font-weight: bold;"
                                        value="0"/>元</p>
        <p><strong>找零：</strong><span id="changeAmount" style="color: #52c41a;">0.00</span>元</p>
    </section>

    <!-- 密码输入区域 -->
    <section class="payment-info password-input">
        <p>请操作员输入交易密码确认收款</p>
        <div class="password-box">
            <input type="password" maxlength="1"
                   oninput="if(value.length>1)value=value.slice(0,1); moveFocus(this, 1)"/>
            <input type="password" maxlength="1"
                   oninput="if(value.length>1)value=value.slice(0,1); moveFocus(this, 2)"/>
            <input type="password" maxlength="1"
                   oninput="if(value.length>1)value=value.slice(0,1); moveFocus(this, 3)"/>
            <input type="password" maxlength="1"
                   oninput="if(value.length>1)value=value.slice(0,1); moveFocus(this, 4)"/>
            <input type="password" maxlength="1"
                   oninput="if(value.length>1)value=value.slice(0,1); moveFocus(this, 5)"/>
            <input type="password" maxlength="1" oninput="if(value.length>1)value=value.slice(0,1)"/>
        </div>
    </section>
    <!-- 支付方式 -->
    <section class="payment-method">
        <p>银行卡 微信 支付宝等，其他支付</p>
    </section>

    <!-- 转账记录选项 -->
    <section class="transfer-record">
        <span>转账记录传附加</span>
        <input type="checkbox" id="transferRecord"/>
    </section>

    <!-- 提交按钮 -->
    <button class="submit-btn" onclick="submitPayment()">确认收款</button>
</div>
<script>
    // 密码框自动跳转
    function moveFocus(current, nextIndex) {
        if (current.value.length === 1) {
            var inputs = document.querySelectorAll('.password-box input');
            if (nextIndex <= inputs.length) {
                inputs[nextIndex - 1].focus();
            }
        }
    }

    // 计算找零
    document.getElementById('actualAmount').addEventListener('input', function () {
        var receivable = parseFloat(document.getElementById('receivableAmount').innerText) || 0;
        var actual = parseFloat(this.value) || 0;
        var change = actual - receivable;
        document.getElementById('changeAmount').innerText = change.toFixed(2);

        if (change >= 0) {
            document.getElementById('changeAmount').style.color = '#52c41a';
        } else {
            document.getElementById('changeAmount').style.color = '#f5222d';
        }
    });

    // 提交支付
    function submitPayment() {
        var password = '';
        var inputs = document.querySelectorAll('.password-box input');
        inputs.forEach(function (input) {
            password += input.value;
        });

        if (password.length !== 6) {
            layer.msg('请输入 6 位交易密码', {
                skin: 'layer-msg-success',
                time: 2000,
                shade: 0.3,
                offset: '50%' // 居中显示
            });
            return;
        }

        var actualAmount = parseFloat(document.getElementById('actualAmount').value) || 0;
        var receivable = parseFloat(document.getElementById('receivableAmount').innerText) || 0;

        if (actualAmount < receivable) {
            layer.msg('实收金额不能小于应收金额', {
                skin: 'layer-msg-success',
                time: 2000,
                shade: 0.3,
                offset: '50%' // 居中显示
            });
            return;
        }

        // 这里可以添加提交到后端的代码
        layer.msg('收款成功', {
            skin: 'layer-msg-success',
            time: 2000,
            shade: 0.3,
            offset: '50%' // 居中显示
        }, function (index) {
            layer.close(index);
            // 延迟返回
            setTimeout(function () {
                window.history.back();
            }, 500);
        });

        // 延迟返回
        setTimeout(function () {
            window.history.back();
        }, 1500);
    }

    // 获取传递的参数
    $(function () {
        var selectedIds = getParameterByName('selectedIds');
        var actualPayAmount = getParameterByName('actualPayAmount');
        var cashierBillsId = getParameterByName('cashierBillsId');
        //条数
        var totalCount = getParameterByName('totalCount');

        // 如果有传递金额，填充到页面
        if (actualPayAmount) {
            document.getElementById('receivableAmount').innerText = actualPayAmount;
            document.getElementById('actualAmount').value = actualPayAmount;
            document.getElementById('changeAmount').innerText = '0.00';
        }
        // 填充商品数量（需要从后端获取）
        document.getElementById('totalCount').innerText = totalCount;
    });

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }
</script>

</body>
</html>
