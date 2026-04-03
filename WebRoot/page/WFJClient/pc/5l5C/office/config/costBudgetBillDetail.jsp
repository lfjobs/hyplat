<!DOCTYPE html>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    String personalId = (String) session.getAttribute("personalId");

%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>初始项目单明细</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBillAdd.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/inventoryAudit.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
</head>
<style>
    .content {
        background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
        background-size: 100%
    }

    .clearfix .headNav {
        /*height:2.4rem;*/
    }

    #ttsw_billID {
        width: 55% !important;
    }

    .clearfix .headNav ul li {
        text-align: center;
        width: 24%;
        float: left;
        height: 2rem;
        line-height: 2rem;
        color: #222;
        font-size: .6rem;
    }

    .clearfix .headNav ul li img {
        width: .6rem;
        margin-right: 0.1rem;
    }

    table {
        width: 100%;
        border-top: 0.025rem solid #e9e9e9;
        border-bottom: 0.025rem solid #e9e9e9;
    }

    table tr td:first-of-type {
        /*width: 0.1%;*/
    }

    table tr td:nth-of-type(2) {
        width: 16%;
    }

    table tr td:nth-of-type(3) {
        width: 16%;
    }

    table tr td:nth-of-type(4) {
        width: 16%;
    }

    table tr td:nth-of-type(5) {
        width: 15%;
    }

    table tr td {
        text-align: center;
        height: 1.8rem;
        line-height: 0.8rem;
        font-size: 0.5rem;
        color: #1f1f1f;
        overflow-x: auto;
        white-space: nowrap;
    }

    .clearfix .iconNav ul li {
        text-align: center;
        width: 10%;
        float: left;
        height: 2rem;
        line-height: 2rem;
        color: #222;
        font-size: .6rem;
    }

    .clearfix .iconNav ul li img.icon {
        width: 70%;
        margin-left: 1rem;
        margin-right: 0.6rem;
        margin-top: 0.125rem;
    }

    aside {
        padding-top: 0.2rem;
        padding-right: 0.4rem;
        float: left;
    }

    aside img {
        width: 0.8rem;
        margin: 0 auto;
    }

    aside.aside_yes .img_no {
        display: none;
    }

    aside.aside_yes .img_yes {
        display: block;
    }

    aside.aside_no .img_yes {
        display: none;
    }

    aside.aside_no .img_no {
        display: block;
    }

    html, body {
        height: 100%;
        overflow: hidden;
    }

    header {
        background-color: #f74c32;
    }

    header ul {
        /*padding: 0 .5rem*/
    }

    header ul li {
        float: left;
        height: 2rem;
        line-height: 2rem;
        font-size: 0.9rem;
        color: #ffffff;
    }

    header ul li:first-of-type {
        width: 20%
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

    header ul li:first-of-type img {
        /* margin-left: .95rem; */
        width: .6rem;
    }

    .layui-layer-msg {
        color: #000 !important; /* 强制黑色字体 */
    }

    .layer-msg {
        color: #333 !important;
    }

    .sec_con2 {
        height: calc(100vh - 300px);
        overflow-y: auto;
    }

    .ul_con table tbody {
        display: block;
        max-height: calc(100vh - 400px);
        overflow-y: auto;
    }

    .ul_con table thead,
    .ul_con table tbody tr {
        display: table;
        width: 100%;
        table-layout: fixed;
    }

    .content section.sec_con1 > div p label {
        height: 1.4rem;
        line-height: 1.7rem;
    }

    .content section.sec_con1 > div p input {
        width: 45%;
        height: 1rem;
        line-height: 1rem;
        font-size: .6rem;
        color: #222;
        background-color: transparent;
        border: 0;
    }

    table tr td.quantity2 {
        padding-top: 0.5rem;
    }

    .payment-summary {
        display: inline-block;
        /*padding: 10px 20px;*/
        text-align: right;
        white-space: nowrap; /* 防止换行 */
        font-size: clamp(0.6rem, 2vw, 0.9rem);
    }


    .payment-summary .payment-item {
        display: inline-block;
        margin-right: 5px;
        font-size: clamp(0.6rem, 2vw, 0.85rem);
        color: #666;
    }


    .payment-summary .payment-item .amount {
        font-weight: bold;
        font-size: clamp(0.65rem, 2.2vw, 0.9rem);
        /*margin-left: 5px;*/
    }

    .payment-summary .pay-btn {
        display: inline-block;
        padding: 8px 30px;
        /*background-color: #ff4d4f;*/
        color: #398fe7;
        border-radius: 4px;
        font-size: clamp(0.7rem, 2.5vw, 1rem);
        cursor: pointer;
        /*margin-left: 30px;*/
        vertical-align: middle; /* 垂直居中对齐 */
    }
</style>
<script>
    //初始化数据
    var basePath = "<%=basePath%>";
    var menuId = "${menuId}";
    var billsType = "${billsType}";
</script>
<body class="hy">
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" alt="返回">
            </a>
        </li>
        <li>${billsType}明细</li>
        <li></li>
    </ul>
</header>
<div class="content">
    <section class="sec_con1">
        <p>
            <label for=""><span></span>单据凭证号：</label>
            <input type="text" name="cashierBills.journalNum" id="ttsw_billID" value="${cashierBills.journalNum}"
                   placeholder="自动获取" readonly/>
        </p>
        <c:if test="${not empty cashierBills.companyName}">
            <div class="clearfix">
                <p>
                    <label for=""><span></span>公司：</label>
                    <input type="text" id="ttsw_companyNmae" name="cashierBills.companyName" class="gs_name"
                           value="${cashierBills.companyName}" placeholder="" readonly/>
                </p>
                <p>
                    <label for=""><span></span>部门：</label>
                    <input type="text" name="cashierBills.departmentName" id="ttsw_dep_y_name"
                           value="${cashierBills.departmentName}" placeholder="请选择部门" readonly/>
                </p>
            </div>
        </c:if>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>行业分类：</label>
                <input type="text" name="cashierBills.tradeName" id="project-name" value="${cashierBills.tradeName}"
                       placeholder="请选择项目名称" readonly/>
            </p>
            <p class="clearfix">
                <label for=""><span>*</span>项目分类： </label>
                <input type="text" name="cashierBills.xmtypename" id="project-fl" value="${cashierBills.xmtypename}"
                       placeholder="请选择项目分类" readonly/>
            </p>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>责任人： </label>
                <input type="text" class="xiala-1" value="${cashierBills.staffName}(${cashierBills.staffCode})"
                       placeholder="" required readonly/>
            </p>
            <p>
                <label for=""><span></span>制单人：</label>
                <input type="text" name="" id="ttsw_singleName" value="${cashierBills.inputName}" required readonly/>
            </p>
        </div>
        <div class="clearfix">
            <p>
                <label for=""><span>*</span>制单日期： </label>
                <input type="text" name=""
                       value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />"
                       placeholder="" required readonly/>
            </p>
            <p>
                <label for=""><span></span>单据状态： </label>
                <c:if test="${!empty cashierBills.paystatus }">
                    <c:if test="${cashierBills.paystatus eq '00' }"><input type="text" value="项目未分配"
                                                                           readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '01'}"><input type="text" value="项目已分配未跟踪"
                                                                          readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '02'}"><input type="text" value="项目已跟踪未考评"
                                                                          readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '03'}"><input type="text" value="项目已考评"
                                                                          readonly/></c:if>
                </c:if>
                <c:if test="${empty cashierBills.paystatus}">
                    <c:if test="${cashierBills.status eq '00'}"><input type="text" value="拟稿" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '01'}"><input type="text" value="审核中-招标前"
                                                                       readonly/></c:if>
                    <c:if test="${cashierBills.status eq '02'}"><input type="text" value="已通过-招标前"
                                                                       readonly/></c:if>
                    <c:if test="${cashierBills.status eq '03'}"><input type="text" value="比价审核中" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '04'}"><input type="text" value="已提交资金申请"
                                                                       readonly/></c:if>
                    <c:if test="${cashierBills.status eq '05'}"><input type="text" value="待会计审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '06'}"><input type="text" value="待出纳审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '07'}"><input type="text" value="已审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '20'}"><input type="text" value="税务单据" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '08'}"><input type="text" value="三审已归档" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '09'}"><input type="text" value="待确认收款" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '40'}"><input type="text" value="待确定预算收入单"
                                                                       readonly/></c:if>
                    <c:if test="${cashierBills.status eq '45'}"><input type="text" value="已收款" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '46'}"><input type="text" value="系统生成" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '11'}"><input type="text" value="驳回待修改" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '50'}"><input type="text" value="传阅中" readonly/></c:if>
                </c:if>
            </p>
            <c:if test="${!empty cashierBills.ctUserName }">
                <p class="clearfix">
                    <label for="">往来个人： </label>
                    <input type="text" class="xiala-1" value="${cashierBills.ctUserName}" placeholder="" required
                           readonly/>
                </p>
            </c:if>
            <c:if test="${!empty cashierBills.startTime }">
                <p class="clearfix">
                    <label for="">开始日期： </label>
                    <input type="text" name=""
                           value="<fmt:formatDate value="${cashierBills.startTime}" pattern="yyyy-MM-dd" />"
                           placeholder="" required readonly/>
                </p>
            </c:if>
            <c:if test="${!empty cashierBills.endTime }">
                <p>
                    <label for="">结束日期： </label>
                    <input type="text" name=""
                           value="<fmt:formatDate value="${cashierBills.endTime}" pattern="yyyy-MM-dd" />"
                           placeholder="" required readonly/>
                </p>
            </c:if>
            <p>
                <label for="">单据类别： </label>
                <input type="text" name="" value="${empty cashierBills.billsType ? '' : cashierBills.billsType}"/><%--所选项目类型--%>
            </p>
            <c:if test="${!empty cashierBills.priceSub }">
                <p class="clearfix">
                    <label for="">总金额：</label>
                    <input type="text" name="" id="totalAmount" value="${cashierBills.priceSub}" required readonly/>
                </p>
            </c:if>
            <p style="width: 100%; text-align: right; ${(cashierBills.billsType eq '支出' and cashierBills.status eq '07') ? '' : 'display:none;'}">
            <div class="payment-summary" style="padding-top: 20px;">
                <span class="payment-item">全部商品:&nbsp<span class="amount amount1">${cashierBills.priceSub}元</span></span>
                <span class="payment-item">未审批:&nbsp<span class="amount">0 元</span></span>
                <span class="payment-item">已审批:&nbsp<span class="amount">0 元</span></span>
            </div>
            <div class="payment-summary">
                <span class="payment-item" style="color: green">勾选应付:&nbsp<span
                        class="amount">0元</span></span>
                <span class="payment-item" style="color: green">实付:&nbsp<input type="text" id="actualPayAmount"
                                                                                 value="0" style="color: red; font-weight: bold; border: 1px solid #ddd;
                        border-radius: 4px; width: clamp(40px, 10vw, 80px); text-align: right;
                        font-size: clamp(0.6rem, 2vw, 0.85rem);"/>元</span>
                <span class="payment-item" style="color: green">勾选未付:&nbsp<span
                        class="amount">0元</span></span>
            </div>
            <div class="payment-summary">
                <span class="payment-item">未选未付&nbsp:&nbsp<span
                        class="amount">0元</span></span>
                <label id="payLabel" class="pay-btn" style="color: red ;font-weight: bold;">支付</label>
            </div>
            </p>
        </div>
        <div class="clearfix">
            <section class="headNav" style="display: none;">
                <ul>
                    <li><a href="javascript:nav('收入')"><img id="srdj"
                                                              src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">收入</a>
                    </li>
                    <li><a href="javascript:nav('支出')"><img id="zcfb"
                                                              src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">支出</a>
                    </li>
                    <input type="text" id="billsType" name="cashierBills.billsType"
                           value="${empty cashierBills.billsType ? '收入' : cashierBills.billsType}"
                           style="display: none;"/><%--所选项目类型--%>
                </ul>
            </section>

        </div>
    </section>
    <%--选择具体项目后显示项目详细信息--%>
    <section class="sec_con2">
        <%--选择具体项目后显示项目详细信息--%>
        <c:if test="${!empty goodBeanslist}">
            <ul class="ul_con">
                <table>
                    <tr>
                        <td></td>
                        <td>物品名称</td>
                        <td>初始余额</td>
                        <td>收方金额</td>
                        <td>付方金额</td>
                        <td>余额</td>
                        <td>审核意见</td>
                    </tr>
                    <% int number = 1; %>
                    <c:forEach var='entity' items="${goodBeanslist}">
                        <tr class="rgid">
                            <td class="goodname">
                                <aside class="aside_no" checkCasId="${entity.goodsBillsID}">
                                    <img class="img_no"
                                         src="<%=basePath%>images/scMobile/payBudget/budgetList/wupinguanli_07.png"
                                         style="display:block"/>
                                    <img class="img_yes"
                                         src="<%=basePath%>images/scMobile/payBudget/budgetList/wupinguanli_10.png"
                                         style="display:none"/>
                                </aside>
                            </td>
                            <td class="quantity2"
                                onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.goodsName}</td>
                            <td class="goodname"
                                onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.initialBalance}</td>
                            <td class="quantity1"
                                onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.borrowAmount}</td>
                            <td data-amount="${entity.loanAmount}" class="requantity"
                                onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.loanAmount}</td>
                            <td class="requantity"
                                onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.balance}</td>
                            <td class="requantity" onclick="toBarCodeInfo('${entity.goodsBillsID }');">
                                <c:choose>
                                    <c:when test="${empty entity.goodsBillsExtOpinion or empty entity.goodsBillsExtOpinion.goodsBillsID}">
                                        <span style="color: red;">未审核</span>
                                    </c:when>
                                    <c:when test="${entity.goodsBillsExtOpinion.reviewOpinion eq '1'}">
                                        同意
                                    </c:when>
                                    <c:otherwise>
                                        驳回
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <% number++; %>
                    </c:forEach>
                </table>
            </ul>
        </c:if>
    </section>
</div>
</body>
<script>
    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }

    $('#payLabel').on('click', function () {
        // 获取所有已选中的物品数据
        var selectedData = [];
        $('.aside_yes').each(function () {
            var row = $(this).closest('tr');
            var checkCasId = $(this).attr('checkCasId');

            if (checkCasId) {
                var item = {
                    goodsBillsID: checkCasId,
                    goodsName: row.find('td.quantity2').text().trim(),
                    initialBalance: row.find('td.goodname').text().trim(),
                    borrowAmount: row.find('td.quantity1').text().trim(),
                    loanAmount: row.find('td.requantity:first').text().trim(),
                    balance: row.find('td.requantity').eq(1).text().trim(),
                    reviewOpinion: row.find('td.requantity1 span').length > 0 ?
                        (row.find('td.requantity1 span').text().trim() === '未审核' ? '未审核' :
                            (row.find('td.requantity1').text().trim() === '同意' ? '同意' : '驳回')) : '未审核'
                };
                selectedData.push(item);
            }
        });

        if (selectedData.length === 0) {
            layer.msg('请至少选择一个物品');
            return;
        }

        // 获取实付金额
        var actualPayAmount = $('#actualPayAmount').val() || '0';

        // 获取 cashierBillsId
        var cashierBillsId = getParameterByName("cashierBillsId");

        // 获取支付区域的所有金额数据
        var allItemsAmount = $('.payment-summary').eq(0).find('.amount.amount1').text().replace('元', '');
        var checkPayableAmount = $('.payment-summary').eq(1).find('.payment-item:first .amount').text().replace('元', '');
        var checkUnpaidAmount = $('.payment-summary').eq(1).find('.payment-item:last .amount').text().replace('元', '');
        var unselectedUnpaidAmount = $('.payment-summary').eq(2).find('.amount').text().replace('元', '');

        // 将数据转换为 JSON 字符串并编码
        var jsonData = encodeURIComponent(JSON.stringify(selectedData));

        // 跳转到支付页面
        var url = "page/WFJClient/pc/5l5C/office/config/payment.jsp";
        var params = "?cashierBillsId=" + encodeURIComponent(cashierBillsId);
        params += "&selectedData=" + jsonData;
        params += "&actualPayAmount=" + encodeURIComponent(actualPayAmount);
        params += "&allItemsAmount=" + encodeURIComponent(allItemsAmount);
        params += "&checkPayableAmount=" + encodeURIComponent(checkPayableAmount);
        params += "&checkUnpaidAmount=" + encodeURIComponent(checkUnpaidAmount);
        params += "&unselectedUnpaidAmount=" + encodeURIComponent(unselectedUnpaidAmount);

        window.location.href = basePath + url + params;

        // var url = "ea/reviewCirculate/ea_toCostBudgetDetail1.jspa";
        // var parameter = "?cashierBillsId=" + getParameterByName("cashierBillsId");
        // window.location.href = basePath + url + parameter;
    });
    // 添加图片切换事件
    $(document).on('click', '.aside_no', function () {
        // 切换选中状态的图片
        $(this).find('.img_no').hide();
        $(this).find('.img_yes').css("display", "block");
        $(this).removeClass('aside_no').addClass('aside_yes');
        calculateSelectedAmount();
    });

    $(document).on('click', '.aside_yes', function () {
        // 切换回未选中状态的图片
        $(this).find('.img_yes').hide();
        $(this).find('.img_no').show();
        $(this).removeClass('aside_yes').addClass('aside_no');
        calculateSelectedAmount();
    });

    // 计算选中金额之和的函数
    function calculateSelectedAmount() {
        var totalAmount = 0;
        var allItemsAmount = 0;
        var unselectedAmount = 0;
        // 遍历所有行，计算全部商品的总金额和未选中金额
        $('table tbody tr').each(function () {
            var amountStr = $(this).find('td[data-amount]').attr('data-amount');

            if (amountStr) {
                var amount = parseFloat(amountStr);
                if (!isNaN(amount)) {
                    allItemsAmount += amount;

                    // 检查该行是否被选中
                    var asideElement = $(this).find('aside');
                    if (!asideElement.hasClass('aside_yes')) {
                        // 未选中，累加到未选金额
                        unselectedAmount += amount;
                    }
                }
            }
        });

        // 更新"全部商品"的金额
        $('.payment-summary').eq(0).find('.amount').text(allItemsAmount.toFixed(2) + '元');

        // 遍历所有选中的行（aside_yes）
        $('.aside_yes').each(function () {
            // 获取当前行的余额数据
            var row = $(this).closest('tr');
            var amountStr = row.find('td[data-amount]').attr('data-amount');

            if (amountStr) {
                var amount = parseFloat(amountStr);
                if (!isNaN(amount)) {
                    totalAmount += amount;
                }
            }
        });

        // 更新"勾选应付"的金额显示
        $('.payment-summary').eq(1).find('.payment-item:first .amount').text(totalAmount.toFixed(2) + '元');

        // 同时更新"勾选未付"的金额（应付 - 实付）
        var actualPay = parseFloat($('#actualPayAmount').val()) || 0;
        var unpaidAmount = totalAmount - actualPay;
        $('.payment-summary').eq(1).find('.payment-item:last .amount').text(unpaidAmount.toFixed(2) + '元');

        // 更新"未选未付"的金额
        $('.payment-summary').eq(2).find('.amount').text(unselectedAmount.toFixed(2) + '元');
    }

    // 监听实付金额输入框的变化
    $(document).on('input', '#actualPayAmount', function () {
        calculateSelectedAmount();
    });
    $(function () {
        // 遍历所有行，计算全部商品的总金额
        $('table tbody tr').each(function () {
            var amountStr = $(this).find('td[data-amount]').attr('data-amount');

            if (amountStr) {
                var amount = parseFloat(amountStr);
                if (!isNaN(amount)) {
                    allItemsAmount += amount;
                }
            }
        });

        // 更新"全部商品"的金额
        $('.payment-summary').eq(0).find('.amount').text(allItemsAmount.toFixed(2) + '元');
    })

    function toBarCodeInfo(goodsBillsID) {
        window.location.href = basePath + "ea/reviewCirculate/ea_toCostBudgetItemDetail.jspa?search=" + goodsBillsID +
            "&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}&billsType=" + billsType;
    }

    $(document).ready(function () {
        // 遍历所有审核意见为"同意"的行，并设置为选中状态
        $('td.requantity').each(function () {
            var text = $(this).text().trim();
            if (text === '同意' || text === '驳回') {
                var asideElement = $(this).closest('tr').find('aside');
                asideElement.removeClass('aside_no').addClass('aside_yes');
                asideElement.find('.img_no').hide();
                asideElement.find('.img_yes').show();
            }
        });
    });
</script>
</html>
