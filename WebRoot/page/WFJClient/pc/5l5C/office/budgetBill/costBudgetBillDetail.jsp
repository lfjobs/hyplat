<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");

%>
<html>
<head>
    <meta charset="utf-8" />
    <title>初始项目单明细</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBillAdd.css"/>
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
        width: 20%;
    }
    table tr td:nth-of-type(2) {
        width: 20%;
    }
    table tr td:nth-of-type(3) {
        width: 20%;
    }
    table tr td:nth-of-type(4) {
        width: 20%;
    }
    /*table tr td:nth-of-type(5) {*/
    /*    width: 20%;*/
    /*}*/
    table tr td {
        text-align: center;
        height: 1.8rem;
        line-height: 0.8rem;
        font-size: 0.6rem;
        color: #1f1f1f;
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
    .clearfix .iconNav ul li img.icon  {
        width: 70%;
        margin-left: 1rem;
        margin-right: 0.6rem;
        margin-top: 0.125rem;
    }

    aside{
        padding-top: 0.2rem;
        padding-right: 0.4rem;
        float: left;
    }
    aside img{
        width: 0.8rem;
        margin: 0 auto;
    }
    aside.aside_yes .img_no{
        display: none;
    }
    aside.aside_yes .img_yes{
        display: block;
    }
    aside.aside_no .img_yes{
        display: none;
    }
    aside.aside_no .img_no{
        display: block;
    }
</style>
<script>
    //初始化数据
    var basePath="<%=basePath%>";
    var menuId = "${menuId}";
    var billsType = "${billsType}";
    var head = "${head}";
</script>
<body class="hy">
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            ${billsType}明细
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec_con1">
        <p>
            <label for=""><span></span>单据凭证号：</label>
            <input type="text" name="cashierBills.journalNum" id="ttsw_billID" value="${cashierBills.journalNum}" placeholder="自动获取" readonly/>
        </p>
<c:if test="${not empty cashierBills.companyName}">
        <div class="clearfix">
            <p>
                <label for=""><span></span>公司：</label>
                <input type="text" id="ttsw_companyNmae" name="cashierBills.companyName"  class="gs_name" value="${cashierBills.companyName}" placeholder="" readonly/>
            </p>
            <p>
                <label for=""><span></span>部门：</label>
                <input type="text" name="cashierBills.departmentName" id="ttsw_dep_y_name" value="${cashierBills.departmentName}" placeholder="请选择部门" readonly />
            </p>
        </div>
</c:if>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>行业分类：</label>
                <input type="text" name="cashierBills.tradeName" id="project-name" value="${cashierBills.tradeName}" placeholder="请选择项目名称" readonly/>
            </p>
            <p class="clearfix">
                <label for=""><span>*</span>项目分类： </label>
                <input type="text" name="cashierBills.xmtypename" id="project-fl" value="${cashierBills.xmtypename}" placeholder="请选择项目分类" readonly/>
            </p>
        </div>
        <div class="clearfix" id="inv">
            <p>
                <label for=""><span>*</span>仓库：</label>
                <input type="text" name="cashierBills.dataDepotName" id="ttsw_inv_n_name" value="${empty cashierBills.dataDepotName ? '' : cashierBills.dataDepotName}" readonly />
            </p>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>责任人： </label>
                <input type="text" class="xiala-1"  value="${cashierBills.staffName}(${cashierBills.staffCode})" placeholder="" required readonly/>
            </p>
            <p>
                <label for=""><span></span>制单人：</label>
                <input type="text" name="" id="ttsw_singleName" value="${cashierBills.inputName}" required  readonly/>
            </p>
            <%--            <div class="select_box select_box1"></div>--%>
<%--            <p>--%>
<%--                <label for=""><span></span>位置：</label>--%>
<%--                <input type="text" name="" id="address" name="cashierBills.address" value="金牛区红星美凯龙" required readonly/>--%>
<%--                <input type="text" name="" id="coordinate" style="display: none;" name="cashierBills.coordinate" value="" required readonly/>--%>
<%--            </p>--%>
        </div>
        <div class="clearfix">
            <p>
                <label for=""><span>*</span>制单日期： </label>
                <input type="text" name="" value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />" placeholder="" required readonly/>
            </p>
            <p>
                <label for=""><span></span>单据状态： </label>
                <c:if test="${!empty cashierBills.paystatus }">
                    <c:if test="${cashierBills.paystatus eq '00' }"><input type="text" value="项目未分配" readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '01'}"><input type="text" value="项目已分配未跟踪" readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '02'}"><input type="text" value="项目已跟踪未考评" readonly/></c:if>
                    <c:if test="${cashierBills.paystatus eq '03'}"><input type="text" value="项目已考评" readonly/></c:if>
                </c:if>
                <c:if test="${empty cashierBills.paystatus}">
                    <c:if test="${cashierBills.status eq '00'}"><input type="text" value="拟稿" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '01'}"><input type="text" value="审核中-招标前" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '02'}"><input type="text" value="已通过-招标前" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '03'}"><input type="text" value="比价审核中" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '04'}"><input type="text" value="已提交资金申请" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '05'}"><input type="text" value="待会计审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '06'}"><input type="text" value="待出纳审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '07'}"><input type="text" value="已审核" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '20'}"><input type="text" value="税务单据" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '08'}"><input type="text" value="三审已归档" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '09'}"><input type="text" value="待确认收款" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '40'}"><input type="text" value="待确定预算收入单" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '45'}"><input type="text" value="已收款" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '46'}"><input type="text" value="系统生成" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '11'}"><input type="text" value="驳回待修改" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '50'}"><input type="text" value="传阅中" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '15'}"><input type="text" value="已入库" readonly/></c:if>
                    <c:if test="${cashierBills.status eq '16'}"><input type="text" value="已出库" readonly/></c:if>
                </c:if>
            </p>
            <c:if test="${!empty cashierBills.ctUserName }">
                <p class="clearfix">
                    <label for="">往来个人： </label>
                    <input type="text" class="xiala-1"  value="${cashierBills.ctUserName}" placeholder="" required readonly/>
                </p>
            </c:if>
            <c:if test="${!empty cashierBills.startTime }">
                <p class="clearfix">
                    <label for="">开始日期： </label>
                    <input type="text" name="" value="<fmt:formatDate value="${cashierBills.startTime}" pattern="yyyy-MM-dd" />" placeholder="" required readonly/>
                </p>
            </c:if>
            <c:if test="${!empty cashierBills.endTime }">
                <p>
                    <label for="">结束日期： </label>
                    <input type="text" name="" value="<fmt:formatDate value="${cashierBills.endTime}" pattern="yyyy-MM-dd" />" placeholder="" required readonly/>
                </p>
            </c:if>
            <p>
                <label for="">单据类别： </label>
                <input type="text" name="" value="${empty cashierBills.billsType ? '' : cashierBills.billsType}" /><%--所选项目类型--%>
            </p>
<%--            <c:if test="${!empty cashierBillsExt.reviewerName }">--%>
<%--                <p class="clearfix">--%>
<%--                    <label for="">审核人： </label>--%>
<%--                    <input type="text" class="xiala-1" value="${cashierBillsExt.reviewerName}"  readonly/>--%>
<%--                </p>--%>
<%--            </c:if>--%>
<%--            <c:if test="${!empty cashierBillsExt.reviewTime }">--%>
<%--                <p>--%>
<%--                    <label for="">审核时间： </label>--%>
<%--                    <input type="text" name="" value="<fmt:formatDate value="${cashierBillsExt.reviewTime}" pattern="yyyy-MM-dd HH:mm:ss" />" placeholder="" readonly/>--%>
<%--                </p>--%>
<%--            </c:if>--%>
            <c:if test="${!empty cashierBills.priceSub }">
                <p class="clearfix">
                    <label for="">总金额：</label>
                    <input type="text" name="" id="" value="${cashierBills.priceSub}" required  readonly/>
                </p>
            </c:if>
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
                    <input type="text" id="billsType" name="cashierBills.billsType" value="${empty cashierBills.billsType ? '收入' : cashierBills.billsType}"
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
                        <td>物品名称</td>
                        <td>初始余额</td>
                        <td>收方金额</td>
                        <td>付方金额</td>
                            <%--                        <td>规格</td>--%>
                        <td>余额</td>
                    </tr>
                    <% int number = 1; %>
                    <c:forEach var='entity' items="${goodBeanslist}">
                        <tr class="rgid">
                            <td class="quantity2" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.goodsName}</td>
                            <td class="goodname" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.initialBalance}</td>
                            <td class="quantity1" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.borrowAmount}</td>
                            <td class="requantity" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.loanAmount}</td>
                            <td class="requantity" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.balance}</td>
                                <%--                            <td class="isqualify" onclick="toBarCodeInfo('${entity.goodsBillsID }');">${entity.standard}</td>--%>

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
    function toBack() {
        if(menuId == "receive"){
            window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillReceiveList.jspa?menuId="+menuId+"&billsType="+billsType+"&head="+head;
        }else if(menuId == "sent"){
            window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillSentList.jspa?menuId="+menuId+"&billsType="+billsType+"&head="+head;
        }else if(menuId == "approval"){
            window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillApprovalList.jspa?menuId="+menuId+"&billsType="+billsType+"&head="+head;
        } else{
            window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillList.jspa?menuId="+menuId+"&billsType="+billsType+"&head="+head;
        }
    }

    function nav(selectId) {
        <%--$("#"+selectId).attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");--%>
        if (selectId == "收入") {
            $("#zcfb").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxwselet.png");
            $("#srdj").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");
        } else {
            $("#srdj").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxwselet.png");
            $("#zcfb").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");
        }
        $("#billsType").val(selectId);

    }

    //监听点击浏览器后退
    $(function(){
        var billsType = $("#billsType").val();
        nav(billsType);

        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
        if(billsType == "入库单" || billsType == "出库单" || billsType == "盘库单" || billsType == "报损单"){
            $("#inv").show();
        }else {
            $("#inv").hide();
        }
    });

    function toBarCodeInfo(goodsBillsID){
        window.location.href = basePath + "ea/scBudget/ea_toCostBudgetItemDetail.jspa?search="+goodsBillsID+
            "&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}&billsType="+billsType+"&head="+head;
    }
</script>
</html>
