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
    <title>已交费订单详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/afterSales.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>
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
            详情
        </li>
        <li style="font-size: 1rem; display: none ;" class="edit">
            修改
        </li>
    </ul>
</header>
<div class="content up">
    <section class="sec-list up">
        <div class="div-sec-data up">
            <div class="container">
                <div class="dataPo">
                    <s:form id="updAfterSales" action="sajax_ea_updAfterSalesData.jspa" namespace="/ea/crmCustomerPO"
                            method="POST">

                        <div class="div-title-name"><label>购买人姓名</label></div>
                        <div class="div-name" style="display: none">
                            <label>id</label>
                            <input type="text" id="account" name="afterSales.account"
                                   value="${afterSales.account}"/>
                        </div>
                        <div class="div-title-name"><label>购买人电话</label></div>
                        <div class="div-name" style="display: none">
                            <label>id</label>
                            <input type="text" id="accountName" name="afterSales.accountName"
                                   value="${afterSales.accountName}"/>
                        </div>

                        <div class="div-title-name"><label>已交费订单详情</label></div>
                        <div class="div-name" style="display: none">
                            <label>id</label>
                            <input type="text" id="id" name="afterSales.cashierbillsId"
                                   value="${afterSales.cashierbillsId}"/>
                        </div>
                        <div class="div-name">
                            <label>商品编号</label>
                            <input type="text" placeholder="请输入商品编号" id="goodsId" name="afterSales.goodsId"
                                   value="${afterSales.goodsId}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>商品名称</label>
                            <input type="text" placeholder="请输入商品名称" id="goodsNameBill"
                                   name="afterSales.goodsNameBill"
                                   value="${afterSales.goodsNameBill}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>品牌</label>
                            <input type="text" placeholder="请输入品牌" id="mnemonicCode"
                                   name="afterSales.mnemonicCode"
                                   value="${afterSales.mnemonicCode}"/>
                        </div>
                        <div class="div-name">
                            <label>有效期</label>
                            <input type="text" placeholder="请输入有效期" id="validityPeriod"
                                   name="afterSales.validityPeriod"
                                   value="${afterSales.validityPeriod}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>收费时间</label>
                            <input type="text" placeholder="请输入收费时间" id="chargingTime"
                                   name="afterSales.chargingTime"
                                   value="${afterSales.chargingTime}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>客户意见</label>
                            <input type="text" placeholder="请输入意见" id="customerOpinion"
                                   name="afterSales.customerOpinion"
                                   value="${afterSales.customerOpinion}"
                                   maxlength="500"
                                   oninput="checkWordCount(this, 500)"
                                   onblur="checkWordCount(this, 500)"/>
                            <div class="word-count" id="customerOpinionCount"
                                 style="font-size: 0.8rem; color: #999; text-align: right;">0/500
                            </div>
                        </div>
                        <div class="div-name">
                            <label>客户单位</label>
                            <input type="text" placeholder="请输入所在单位" id="ccompanyName"
                                   name="afterSales.ccompanyName"
                                   value="${afterSales.ccompanyName}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>责任人</label>
                            <input type="text" placeholder="请输入责任人" id="cresponsible"
                                   name="afterSales.cresponsible"
                                   value="${afterSales.cresponsible}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>责任人联系方式</label>
                            <input type="text" placeholder="请输入手机号" id="responsibleTel"
                                   name="afterSales.responsibleTel"
                                   value="${afterSales.responsibleTel}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>是否处理</label>
                            <select id="isProcess" name="afterSales.isProcess" onchange="toggleProcessResult()">
                                <option value="">请选择处理情况</option>
                                <option value="1" ${afterSales.isProcess == '1' ? 'selected' : ''}>已处理</option>
                                <option value="0" ${afterSales.isProcess == '0' ? 'selected' : ''}>未处理</option>
                            </select>
                        </div>

                        <!-- 处理结果输入框，默认隐藏 -->
                        <div class="div-name" id="processResultDiv" style="display: none;">
                            <label>处理结果</label>
                            <input type="text" placeholder="请输入处理结果" id="processResult"
                                   name="afterSales.processResult"
                                   value="${afterSales.processResult}"/>
                        </div>
                        <div class="div-name">
                            <label>客户定位地址</label>
                            <input type="text" placeholder="请输入地址" id="address"
                                   name="afterSales.address"
                                   value="${afterSales.address}"/>
                        </div>
                        <div class="div-name">
                            <label>处理责任人</label>
                            <input type="text" placeholder="请输入处理责任人" id="processPerson"
                                   name="afterSales.processPerson"
                                   value="${afterSales.processPerson}"/>
                        </div>
                        <div class="div-name">
                            <label>联系方式</label>
                            <input type="text" placeholder="请输入处理责任人手机号" id="processPersonPhone"
                                   name="afterSales.processPersonPhone"
                                   value="${afterSales.processPersonPhone}"/>
                        </div>
                        <div class="div-name">
                            <label>售后客服</label>
                            <input type="text" placeholder="请输入售后客服" id="staffName"
                                   name="afterSales.staffName"
                                   value="${afterSales.staffName}" readonly/>
                        </div>
                        <div class="div-name">
                            <label>售后客服联系方式</label>
                            <input type="text" placeholder="请输入售后客服手机号" id="staffPhone"
                                   name="afterSales.staffPhone"
                                   value="${afterSales.staffPhone}" readonly/>
                        </div>

                    </s:form>
                </div>

            </div>
        </div>
    </section>

</div>
<div style="background-color: #f74c32;gap: 80px;border-radius: 40px;" class="inputBut">
    <div class="updateData" onclick="updateData1()">提交</div>
    <%--    <div class="updateData"><input style="background: #f74c32;" type="reset" value="数据重置"></div>--%>
</div>
<script>
    function toggleProcessResult() {
        const selectElement = document.getElementById('isProcess');
        const processResultDiv = document.getElementById('processResultDiv');

        if (selectElement.value === '1') { // 如果选择了“已处理”
            processResultDiv.style.display = 'block'; // 显示处理结果输入框
        } else {
            processResultDiv.style.display = 'none'; // 隐藏处理结果输入框
        }
    }

    // 页面加载时检查初始状态并更新显示
    window.onload = function () {
        toggleProcessResult(); // 确保页面加载时的状态正确
    };

    function updateData1() {
        var form = document.getElementById('updAfterSales');
        var formData = new FormData(form);
        fetch(form.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                layer.msg("提交成功");
            } else {
                layer.msg("提交失败，请重试");
            }
        })
            .catch(error => {
                console.error('Error:', error);
                layer.msg("提交失败，请重试");
            });
    }

    function updateWordCount(element, maxLength) {
        const countElement = document.getElementById(element.id + 'Count');
        if (countElement) {
            countElement.textContent = element.value.length + '/' + maxLength;
            if (element.value.length > maxLength) {
                countElement.style.color = 'red';
            } else {
                countElement.style.color = '#999';
            }
        }
    }

    function checkWordCount(element, maxLength) {
        updateWordCount(element, maxLength);
        if (element.value.length > maxLength) {
            element.value = element.value.substring(0, maxLength);
            layer.msg("输入内容不能超过" + maxLength + "字");
            updateWordCount(element, maxLength); // 更新显示
        }
    }

    // 页面加载完成后初始化字数显示
    document.addEventListener('DOMContentLoaded', function () {
        const customerOpinion = document.getElementById('customerOpinion');
        if (customerOpinion) {
            updateWordCount(customerOpinion, 500);
        }
    });
</script>
</body>
</html>
