<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
%>
<html>
<head>
    <meta charset="utf-8" />
    <title>预算单发布</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/budgetRelease/budgetRelease.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/budgetRelease/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/budgetRelease/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/swiper/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<script>
    //初始化数据
    var basePath="<%=basePath%>";
    var cashierBillsId = "${cashierBillsId}";//预算单号
</script>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/budgetRelease/register_return.png"/>
        </li>
        <li>
            预算单发布
        </li>
        <li>
            <img src="<%=basePath %>images/scMobile/payBudget/budgetRelease/yusuan_img_02.png"/>
        </li>
    </ul>
</header>
<div class="content">
    <nav>
        <ul class="clearfix">
            <li <c:if test="${fbJumpType eq 0}">class="active"</c:if> onclick="toReleaseList(0);">
                发布
            </li>
            <li <c:if test="${fbJumpType eq 1}">class="active"</c:if> onclick="toReleaseList(1);">
                已发布
            </li>
            <li <c:if test="${fbJumpType eq 2}">class="active"</c:if> onclick="toReleaseList(2);">
                未发布
            </li>
        </ul>
    </nav>
    <div class="div_scroll">
        <c:if test="${empty cashierBills}">
            <section class="nav_2">
                <ul class="clearfix">
                    <li class="active" style="text-align:center;width: 100%;">不可重复比价审核</li>
                </ul>
            </section>
        </c:if>
        <c:if test="${!empty cashierBills}">
            <section class="con_jh">
                <ul>
                    <li class="djxh">
                        <label for="">单据序号： </label>
                        <input type="text" value="${cashierBills.journalNum}"
                    </li>
                    <li class="clearfix">
                        <p class="p_gs">
                            <label for="">公司：</label>
                            <input type="text" value="${cashierBills.companyName}" readonly/>
                        </p>
                        <div class="p_xuanze">
                            <label for="">创收部门：</label>
                            <p>
                                <span class="btn1">${cashierBills.departmentName}</span>
                            </p>
                        </div>
                    </li>
                    <li class="clearfix">
                        <p class="">
                            <label for="">项目名称：</label>
                            <input type="text"  value="${cashierBills.projectName}" readonly/>
                        </p>
                        <p>
                            <label for="">项目分类：</label>
                            <input type="text" value="${cashierBills.xmtypename}" readonly/>
                        </p>
                    </li>
                    <li class="clearfix">
                        <p class="">
                            <label for="">责任人：</label>
                            <input type="text"  value="${cashierBills.staffName}(${cashierBills.staffCode})" readonly/>
                        </p>
                        <p>
                            <label for="">制单人：</label>
                            <input type="text" value="${cashierBills.inputName}" readonly/>
                        </p>
                    </li>
                    <li class="clearfix">
                        <p class="">
                            <label for="">制单日期：</label>
                            <input type="text"  value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />" readonly/>
                        </p>
                        <div class="p_xuanze">
                            <label for="">单据状态：</label>
                            <p>
                                <span class="btn1">
                                    <c:if test="${!empty cashierBills.paystatus }">
                                        <c:if test="${cashierBills.paystatus eq '00' }">项目未分配</c:if>
                                        <c:if test="${cashierBills.paystatus eq '01'}">项目已分配未跟踪</c:if>
                                        <c:if test="${cashierBills.paystatus eq '02'}">项目已跟踪未考评</c:if>
                                        <c:if test="${cashierBills.paystatus eq '03'}">项目已考评</c:if>
                                    </c:if>
                                    <c:if test="${empty cashierBills.paystatus}">
                                        <c:if test="${cashierBills.status eq '00'}">草稿</c:if>
                                        <c:if test="${cashierBills.status eq '01'}">审核中-招标前</c:if>
                                        <c:if test="${cashierBills.status eq '02'}">已通过-招标前</c:if>
                                        <c:if test="${cashierBills.status eq '03'}">比价审核中</c:if>
                                        <c:if test="${cashierBills.status eq '04'}">已提交资金申请</c:if>
                                        <c:if test="${cashierBills.status eq '05'}">待会计审核</c:if>
                                        <c:if test="${cashierBills.status eq '06'}">待出纳审核</c:if>
                                        <c:if test="${cashierBills.status eq '07'}">已审核</c:if>
                                        <c:if test="${cashierBills.status eq '20'}">税务单据</c:if>
                                        <c:if test="${cashierBills.status eq '08'}">三审已归档</c:if>
                                        <c:if test="${cashierBills.status eq '09'}">待确认收款</c:if>
                                        <c:if test="${cashierBills.status eq '40'}">待确定预算收入单</c:if>
                                        <c:if test="${cashierBills.status eq '45'}">已收款</c:if>
                                        <c:if test="${cashierBills.status eq '46'}">系统生成</c:if>
                                        <c:if test="${cashierBills.status eq '11'}">驳回待修改</c:if>
                                    </c:if>
                                </span>
                            </p>
                        </div>
                    </li>
                </ul>
            </section>
            <%--选择具体项目后显示项目详细信息--%>
            <c:if test="${!empty goodBeanslist}">
                <div id="tab_wpgl">
                    <ul class="ul_con">
                        <c:forEach items="${goodBeanslist}" var="entity" varStatus="v">
                            <li class="clearfix">
                                <section class="clearfix">
                                    <div>
                                        <img src="<%=basePath %>/${entity[1].photoPath}"/>
                                    </div>
                                    <ul>
                                        <li class="img_no1 txt">${entity[1].goodsName}</li>
                                        <li class="img_no2 txt">条码：${entity[1].barCode}</li>
                                        <li class="img_no3 txt">
                                            <span>规格：${entity[1].standard}</span>
                                            单位：${entity[1].variableID}
                                        </li>
                                        <li class="img_no4 txt">
                                            <p>进价：<span>¥${entity[0].price}</span></p>
                                            <p>状态：<span>未审</span></p>
                                        </li>
                                    </ul>
                                </section>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </c:if>
    </div>
</div>
<section class="button fixed_bottom">
    <p onclick="toRelease();">
        发布
    </p>
</section>
<script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/swiper/index.js" type="text/javascript" charset="utf-8"></script>
<script>
    //跳转已发布/未发布页
    function toReleaseList(fbJumpType){
        var url = "";
        if(fbJumpType == 0){//跳转发布页
            url = "ea/scBudget/ea_toRelease.jspa";
        }else{
            url = "ea/scBudget/ea_toBudgetReleaseList.jspa";
        }
        var parame = "?fbJumpType="+fbJumpType+ "&cashierBillsId=" + cashierBillsId;
        window.location.href = basePath + url + parame;
    }

    //发布
    function toRelease(){
        window.location.href = "ea/scBudget/ea_confCostSheet.jspa?cashierBillsId=" + cashierBillsId;
    }

    //后退
    function toBack() {
        window.location.href = basePath + "ea/scBudget/ea_toPayBudgetList.jspa";
    }

    //监听点击浏览器后退
    $(function(){
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
    });
</script>
</body>
</html>