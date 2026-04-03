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
    <title>支付</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <%--    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/inventoryAudit.css"/>--%>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/afterSales.css">

    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <%--    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>--%>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
        var ppid = "${param.ppId}";
        var colorvalue = '';
        var menuId = "${menuId}";
        var attriJson = '${costAddBean.attriJson}';
        var amap;
        if (attriJson) {
            amap = eval("(" + attriJson + ")");
        }
        var billsType = "初始项目单";
        var searchParams = new URLSearchParams(window.location.search);
        var params = new Map();
        searchParams.forEach(function (value, key) {
            params.set(key, value);
        });
        if (params.size > 0) {
            if (null != params.get("billsType")) {
                billsType = params.get("billsType");
            }
        }

        // 获取传递过来的已选择物品完整数据
        var selectedGoodsData = [];
        if (params.has("selectedData")) {
            try {
                var jsonData = decodeURIComponent(params.get("selectedData"));
                selectedGoodsData = JSON.parse(jsonData);
                console.log('已选择的物品数据:', selectedGoodsData);
            } catch (e) {
                console.error('解析数据失败:', e);
            }
        }
        var allItemsAmount = params.has("allItemsAmount") ? params.get("allItemsAmount") : "0";
        var checkPayableAmount = params.has("checkPayableAmount") ? params.get("checkPayableAmount") : "0";
        var checkUnpaidAmount = params.has("checkUnpaidAmount") ? params.get("checkUnpaidAmount") : "0";
        var unselectedUnpaidAmount = params.has("unselectedUnpaidAmount") ? params.get("unselectedUnpaidAmount") : "0";
        var actualPayAmount = params.has("actualPayAmount") ? params.get("actualPayAmount") : "0";
    </script>
    <style>
        aside img {
            width: 1rem;
        }

        .stat-card {
            flex: 1;
            border-radius: 10px;
            padding: 15px;
            color: #666;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            border: 1px solid #b7a4a4;
        }


        .card-title {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #666;
        }

        .card-tabs .tab {
            font-size: 12px;
            padding: 4px 5px;
            border-radius: 4px;
            margin-right: 5px;
            color: #666;
        }

        .card-tabs .tab.active {
            background: #e6f7ff;
            color: #1890ff;
        }

        .card-balance {
            margin-top: 15px;
            display: flex;
            justify-content: space-between;
            font-size: 14px;
        }

        .balance-num {
            font-weight: bold;
            color: #1890ff;
        }

        .blue-card .card-data {
            display: flex;
            justify-content: space-around;
            text-align: center;
            margin-top: 10px;
        }

        .white-card .card-tabs {
            display: flex;
            justify-content: space-around;
            text-align: center;
            margin-top: 10px;
            color: #666;
        }

        .blue-card .num {
            font-size: 20px;
            font-weight: bold;
        }

        .blue-card .label {
            font-size: 12px;
            opacity: 0.8;
        }

        /* 功能网格区 */
        .function-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
            padding: 15px;
            background: #fff;
            margin: 10px 0;
            border-radius: 10px;
        }

        .grid-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            cursor: pointer;
        }

        .grid-item img {
            width: 40px;
            height: 40px;
            margin-bottom: 8px;
            background: #f5f7fa; /* 占位背景 */
            border-radius: 8px;
            padding: 5px;
        }

        .grid-item span {
            font-size: 12px;
            color: #333;
        }

        /* 底部列表区 */
        .bill-list-section {
            background: #fff;
            /*margin: 10px 15px;*/
            border-radius: 10px;
            padding: 15px;
        }

        .list-header {
            display: flex;
            justify-content: space-between;
            font-weight: bold;
            margin-bottom: 15px;
            font-size: 16px;
        }

        .close-btn {
            cursor: pointer;
            color: #999;
        }

        .bill-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .bill-list li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px solid #f0f0f0;
        }

        .bill-info {
            display: flex;
            align-items: center;
        }

        .bill-icon {
            width: 30px;
            height: 30px;
            margin-right: 10px;
            background: #e6f7ff;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #1890ff;
        }

        .bill-detail div:first-child {
            font-size: 14px;
            color: #333;
        }

        .bill-detail div:last-child {
            font-size: 12px;
            color: #999;
            margin-top: 4px;
        }

        .bill-amount {
            font-weight: bold;
            color: #333;
        }

        header {

            /* 橙红色渐变 */
            background: #f74c32;
            /*padding-bottom: 10px;*/
        }

        header ul {
            padding: 0 .5rem;
            height: 50px;
            display: flex; /* 启用 flex 布局 */
            align-items: center; /* 垂直居中 */
            justify-content: center; /* 水平居中 */
        }

        header ul li {
            float: none; /* 移除浮动 */
            height: auto;
            line-height: normal;
            font-size: 1.5rem;
            color: #ffffff;
        }

        /* 第一个 li（返回按钮）靠左 */
        header ul li:first-of-type {
            position: absolute;
            left: 10px;
        }

        /* 第二个 li（标题）居中 */
        header ul li:nth-of-type(2) {
            text-align: center;
            flex: 1;
        }

        /* 第三个 li 靠右 */
        header ul li:last-of-type {
            position: absolute;
            right: 10px;
        }

        .stats-container {
            display: flex;
            justify-content: space-between;
            /*padding: 0 15px 15px 15px; !* 顶部 padding 改为 0 *!*/
            gap: 10px;
            margin-top: 10px; /* 向上偏移，与 header 衔接 */
        }


        .white-card {
            background: #fff;
            color: #333;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .sec_con2 {
            height: calc(100vh - 300px);
            overflow-y: auto;
        }

        .content section.sec_con2 ul.ul_con {
            padding-top: .1rem;
        }

        table {
            width: 100%;
            border-top: 0.025rem solid #e9e9e9;
            border-bottom: 0.025rem solid #e9e9e9;
        }

        .ul_con table tbody {
            display: block;
            max-height: calc(100vh - 400px);
            overflow-y: auto;
        }

        .ul_con table thead, .ul_con table tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        table tr td {
            text-align: center;
            height: 3rem;
            line-height: 0.9rem;
            font-size: 0.75rem;
            color: #1f1f1f;
            overflow-x: auto;
            white-space: nowrap;
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

        .ul_con table thead, .ul_con table tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        table tr td.quantity2 {
            padding-top: 0.9rem;
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
            支付
        </li>
        <li>
        </li>
    </ul>

</header>
<div class="content">
    <section class="sec-list">
        <%--        <div class="stats-container">--%>
        <%--            <div class="stat-card white-card">--%>
        <%--                <div class="card-title">支付统计</div>--%>
        <%--                <div class="card-tabs">--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="label">总支付</div>--%>
        <%--                        <div class="num" id="payableAmount">${cashierBills.priceSub}</div>--%>
        <%--                    </div>--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="label">已支付</div>--%>
        <%--                        <div class="num" id="paidAmount">0</div>--%>
        <%--                    </div>--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="label">未支付</div>--%>
        <%--                        <div class="num" id="unpaidAmount">0</div>--%>
        <%--                    </div>--%>
        <%--                </div>--%>
        <%--                &lt;%&ndash;                <div class="card-tabs">&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                    <span class="tab">总支付</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                    <span class="tab">已支付</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                    <span class="tab">未支付</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                </div>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                <div class="card-tabs">&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                <span class="tab amount" id="payableAmount"&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                      data-amoun="${cashierBills.priceSub}">${cashierBills.priceSub}</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                    <span class="tab amount" id="paidAmount">0</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                    <span class="tab amount" id="unpaidAmount">0</span>&ndash;%&gt;--%>
        <%--                &lt;%&ndash;                </div>&ndash;%&gt;--%>

        <%--            </div>--%>
        <%--            <div class="stat-card blue-card">--%>
        <%--                <div class="card-title">审批统计</div>--%>
        <%--                <div class="card-data">--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="num" id="approvedPercent">0</div>--%>
        <%--                        <div class="label">已审批</div>--%>
        <%--                    </div>--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="num" id="unapprovedCount">0</div>--%>
        <%--                        <div class="label">未审批</div>--%>
        <%--                    </div>--%>
        <%--                    <div class="data-item">--%>
        <%--                        <div class="num" id="pendingPercent">0</div>--%>
        <%--                        <div class="label">先审批</div>--%>
        <%--                    </div>--%>
        <%--                </div>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <!-- 2. 中部功能网格 -->
        <div class="function-grid">
            <div class="grid-item" onclick="doPayment('扫码付款')">
                <img src="<%=basePath%>images/icons/img.png" alt="扫码付款">
                <span>扫码付款</span>
            </div>
            <div class="grid-item" onclick="doPayment('刷脸支付')">
                <img src="<%=basePath%>images/icons/img_1.png" alt="刷脸支付">
                <span>刷脸支付</span>
            </div>
            <div class="grid-item" onclick="doPayment('现金收款')">
                <img src="<%=basePath%>images/icons/img_2.png" alt="现金收款">
                <span>现金收款</span>
            </div>
            <div class="grid-item" onclick="doPayment('现金收款')">
                <img src="<%=basePath%>images/icons/img_3.png" alt="现金收款">
                <span>银行卡支付</span>
            </div>
            <div class="grid-item" onclick="doPayment('购物卡支付')">
                <img src="<%=basePath%>images/icons/img_4.png" alt="购物卡支付">
                <span>购物卡支付</span>
            </div>
            <div class="grid-item" onclick="doPayment('碰一碰支付')">
                <img src="<%=basePath%>images/icons/img_5.png" alt="碰一碰支付">
                <span>微信支付</span>
            </div>
            <div class="grid-item" onclick="doPayment('碰一碰支付')">
                <img src="<%=basePath%>images/icons/img_6.png" alt="碰一碰支付">
                <span>碰一碰支付</span>
            </div>
            <div class="grid-item" onclick="doPayment('转他人支付')">
                <img src="<%=basePath%>images/icons/img_7.png" alt="转他人支付">
                <span>转他人支付</span>
            </div>
        </div>
        <!-- 3. 支付统计信息 -->
        <div class="bill-list-section" id="paymentStatsSection" style="display: none;">
            <div class="list-header">
                <span>支付统计</span>
            </div>
            <div style="display: flex">
                <p style="margin: 8px 0;"><strong>全部商品:</strong> <span id="stat_allItemsAmount"
                                                                           style="color: #1890ff;">0</span> 元</p>
                <p style="margin: 8px 0;"><strong>勾选应付:</strong> <span id="stat_checkPayableAmount"
                                                                           style="color: #52c41a;">0</span> 元</p>
                <p style="margin: 8px 0;"><strong>实付金额:</strong> <span id="stat_actualPayAmount"
                                                                           style="color: red; font-weight: bold;">0</span>
                    元</p>
                <p style="margin: 8px 0;"><strong>勾选未付:</strong> <span id="stat_checkUnpaidAmount"
                                                                           style="color: #faad14;">0</span> 元</p>
                <p style="margin: 8px 0;"><strong>未选未付:</strong> <span id="stat_unselectedUnpaidAmount"
                                                                           style="color: #666;">0</span> 元</p>
            </div>
        </div>

        <!-- 4. 底部待审批账单 -->
        <div class="bill-list-section">
            <div class="list-header">
                <span>为审批账单</span>
                <span>应付金额:</span>
                <span class="actualPayAmount"></span>

                <span class="close-btn">×</span>
            </div>
            <section class="sec_con2">
                <%--选择具体项目后显示项目详细信息--%>
                <c:if test="${!empty goodBeanslist}">
                    <%--                    <ul class="ul_con">--%>
                    <%--                        <table>--%>
                    <%--                            <tr>--%>
                    <%--                                <td></td>--%>
                    <%--                                <td>物品名称</td>--%>
                    <%--                                <td>初始余额</td>--%>
                    <%--                                <td>收方金额</td>--%>
                    <%--                                <td>付方金额</td>--%>
                    <%--                                <td>余额</td>--%>
                    <%--                                <td style="width: 15%">审核意见</td>--%>
                    <%--                            </tr>--%>
                    <%--                            <% int number = 1; %>--%>
                    <%--                            <c:forEach var='entity' items="${goodBeanslist}">--%>
                    <%--                                <tr class="rgid">--%>
                    <%--                                    <td class="goodname">--%>
                    <%--                                        <aside class="aside_no" checkCasId="${entity.goodsBillsID}">--%>
                    <%--                                            <img class="img_no"--%>
                    <%--                                                 src="<%=basePath%>images/scMobile/payBudget/budgetList/wupinguanli_07.png"--%>
                    <%--                                                 style="display:block"/>--%>
                    <%--                                            <img class="img_yes"--%>
                    <%--                                                 src="<%=basePath%>images/scMobile/payBudget/budgetList/wupinguanli_10.png"--%>
                    <%--                                                 style="display:none"/>--%>
                    <%--                                        </aside>--%>
                    <%--                                    </td>--%>
                    <%--                                    <td class="quantity2"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.goodsName}</td>--%>
                    <%--                                    <td class="goodname"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.initialBalance}</td>--%>
                    <%--                                    <td class="quantity1"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.borrowAmount}</td>--%>
                    <%--                                    <td class="requantity"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.loanAmount}</td>--%>
                    <%--                                    <td class="requantity"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.balance}</td>--%>
                    <%--                                    <td class="requantity requantity1"--%>
                    <%--                                        onclick="toBarCodeInfo('${entity.goodsBillsID }');">--%>
                    <%--                                        <c:choose>--%>
                    <%--                                            <c:when test="${empty entity.goodsBillsExtOpinion or empty entity.goodsBillsExtOpinion.goodsBillsID}">--%>
                    <%--                                                <span style="color: red;">未审核</span>--%>
                    <%--                                            </c:when>--%>
                    <%--                                            <c:when test="${entity.goodsBillsExtOpinion.reviewOpinion eq '1'}">--%>
                    <%--                                                同意--%>
                    <%--                                            </c:when>--%>
                    <%--                                            <c:otherwise>--%>
                    <%--                                                驳回--%>
                    <%--                                            </c:otherwise>--%>
                    <%--                                        </c:choose>--%>
                    <%--                                    </td>--%>
                    <%--                                </tr>--%>
                    <%--                                <% number++; %>--%>
                    <%--                            </c:forEach>--%>
                    <%--                        </table>--%>
                    <%--                    </ul>--%>
                </c:if>
            </section>
        </div>
    </section>
</div>

</body>
<script type="text/javascript">
    $(function () {
        var parameterByName = getParameterByName("actualPayAmount");
        $(".actualPayAmount").text(parameterByName + "元");

        billsTypes = getParameterByName('billsType');
        var buttonHeight = $('.div-button').outerHeight();
        $(".tianjia-content").css("padding-bottom", buttonHeight + "px");
        search = getParameterByName("search");
        cashierBillsId = getParameterByName("cashierBillsId");

        // 如果有传递完整数据，直接渲染表格到 sec_con2 并显示支付统计
        if (selectedGoodsData && selectedGoodsData.length > 0) {
            renderSelectedGoodsTable();
            showPaymentStats();
        }
    });

    // 显示支付统计信息
    function showPaymentStats() {
        $('#stat_allItemsAmount').text(parseFloat(allItemsAmount || 0).toFixed(2));
        $('#stat_checkPayableAmount').text(parseFloat(checkPayableAmount || 0).toFixed(2));
        $('#stat_actualPayAmount').text(parseFloat(actualPayAmount || 0).toFixed(2));
        $('#stat_checkUnpaidAmount').text(parseFloat(checkUnpaidAmount || 0).toFixed(2));
        $('#stat_unselectedUnpaidAmount').text(parseFloat(unselectedUnpaidAmount || 0).toFixed(2));
        $('#paymentStatsSection').show();
    }

    // 渲染已选择的物品表格到 sec_con2
    function renderSelectedGoodsTable() {
        var html = '<ul class="ul_con">' +
            '<table>' +
            '<tr>' +
            '<td></td>' +
            '<td>物品名称</td>' +
            '<td>初始余额</td>' +
            '<td>收方金额</td>' +
            '<td>付方金额</td>' +
            '<td>余额</td>' +
            '<td style="width: 15%">审核意见</td>' +
            '</tr>';

        $(selectedGoodsData).each(function (index, entity) {
            html += '<tr class="rgid">' +
                '<td class="goodname">' +
                '<aside class="aside_yes" checkCasId="' + entity.goodsBillsID + '">' +
                '<img class="img_no" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_07.png" style="display:none"/>' +
                '<img class="img_yes" src="' + basePath + 'images/scMobile/payBudget/budgetList/wupinguanli_10.png" style="display:block"/>' +
                '</aside>' +
                '</td>' +
                '<td class="quantity2" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' + entity.goodsName + '</td>' +
                '<td class="goodname" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' + entity.initialBalance + '</td>' +
                '<td class="quantity1" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' + entity.borrowAmount + '</td>' +
                '<td data-amount="' + entity.loanAmount + '" class="requantity" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' + entity.loanAmount + '</td>' +
                '<td class="requantity" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' + entity.balance + '</td>' +
                '<td class="requantity requantity1" onclick="toBarCodeInfo(\'' + entity.goodsBillsID + '\');">' +
                (entity.reviewOpinion === '同意' ? '同意' : (entity.reviewOpinion === '驳回' ? '驳回' : '<span style="color: red;">未审核</span>')) +
                '</td>' +
                '</tr>';
        });

        html += '</table>' +
            '</ul>';

        // 直接将 HTML 放入 sec_con2 中
        $('.sec_con2').html(html);
    }


    // 修改原有的支付函数，支持不同类型
    function doPayment(type) {
        // 如果是现金收款，跳转到现金收款页面
        if (type === '现金收款' || type === '现金银行卡其它支付') {
            // 获取已选择的物品数据
            var selectedIds = [];
            $('aside.aside_yes').each(function () {
                var checkCasId = $(this).attr('checkCasId');
                if (checkCasId) {
                    selectedIds.push(checkCasId);
                }
            });

            if (selectedIds.length === 0) {
                layui.layer.msg('请至少选择一个物品');
                return;
            }

            // 获取实付金额
            var actualPayAmount = $('.actualPayAmount').text().replace('元', '') || '0';
            // 跳转到现金收款页面
            var url = "page/WFJClient/pc/5l5C/office/config/cashReceive.jsp";
            var params = "?cashierBillsId=" + encodeURIComponent(cashierBillsId);
            params += "&selectedIds=" + encodeURIComponent(selectedIds.join(','));
            params += "&actualPayAmount=" + encodeURIComponent(actualPayAmount);
            params += "&totalCount=" + encodeURIComponent(selectedIds.length);
            params += "&payType=" + encodeURIComponent(type);

            window.location.href = basePath + url + params;
            return;
        }

        // 其他支付方式走原有确认流程
        layui.layer.confirm('确认进行' + type + '操作吗？', function (index) {
            // 调用支付接口
            $.ajax({
                url: basePath + 'payment/doPayment.action',
                type: 'POST',
                data: {
                    companyID: companyID,
                    staffID: staffID,
                    payType: type // 新增支付类型参数
                },
                success: function (result) {
                    if (result.success) {
                        layui.layer.msg('操作成功');
                    } else {
                        layui.layer.msg('操作失败：' + result.message);
                    }
                },
                error: function () {
                    layui.layer.msg('请求失败');
                }
            });
            layui.layer.close(index);
        });
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
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

    $(document).ready(function () {
        // 统计审核状态
        var approvedCount = 0;      // 已审批（同意）
        var unapprovedCount = 0;    // 未审批
        var pendingCount = 0;       // 先审批（驳回）
        var totalCount = 0;         // 总数

        // 遍历所有审核意见单元格
        $('td.requantity1').each(function () {
            var text = $(this).text().trim();

            // 判断审核状态
            if (text === '同意') {
                approvedCount++;
            } else if (text === '驳回') {
                pendingCount++;
            } else if (text === '未审核') {
                unapprovedCount++;
            }

            totalCount++;

            // 原有的选中状态逻辑
            if (text === '同意' || text === '驳回') {
                var asideElement = $(this).closest('tr').find('aside');
                asideElement.removeClass('aside_no').addClass('aside_yes');
                asideElement.find('.img_no').hide();
                asideElement.find('.img_yes').show();
            }
        });

        // 计算百分比
        var approvedPercent = totalCount > 0 ? Math.round((approvedCount / totalCount) * 100) : 0;
        var pendingPercent = totalCount > 0 ? Math.round((pendingCount / totalCount) * 100) : 0;

        // 填充审批统计数据
        $('#approvedPercent').text(approvedPercent + '%');
        $('#unapprovedCount').text(unapprovedCount);
        $('#pendingPercent').text(pendingPercent + '%');
    });
</script>
</html>