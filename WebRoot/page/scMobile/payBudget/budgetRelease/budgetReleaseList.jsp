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
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var cashierBillsId = "${cashierBillsId}";//预算单号
    var fbJumpTypeStr = ${fbJumpType };//发布跳转类型
    var fbJumpFlag = ${fbJumpFlag };//发布跳转类型
    var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
</script>
<style>
    /*列表呈现*/
    .bug-con{
        overflow: auto;
        height: 30rem;
    }
    .bug-con ul{
        overflow: hidden;
    }
    .bug-con ul li{
        overflow: hidden;
        padding: 0.5rem 3% 0.5rem 3%;
        border-bottom: 1px solid #f2f2f2;
    }
    .bug-con ul li div{
        float: left;
    }
    .bug-con ul li h5{
        font-size: 0.55rem;
        line-height: 1.3rem;
    }
    .bug-con ul li h5 img{
        width: 0.25rem;
        float: right;
        margin-top: 2.5%;
    }
    .bug-con ul li p{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 50%;
        float: left;
        font-size: 0.55rem;
        padding: 0.1rem 0;
    }
    .bug-con ul li .un span{
        color: #ed0000;
    }
</style>
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
            <c:if test="${fbJumpFlag eq false}">
                <li <c:if test="${fbJumpType eq 0}">class="active"</c:if> onclick="toReleaseList(0);">
                    发布
                </li>
            </c:if>
            <li <c:if test="${fbJumpType eq 1}">class="active"</c:if> onclick="toReleaseList(1);">
                已发布
            </li>
            <li <c:if test="${fbJumpType eq 2}">class="active"</c:if> onclick="toReleaseList(2);">
                未发布
            </li>
        </ul>
    </nav>
    <div class="div_scroll bug-con  main_hide">
        <ul class="tj_con">
            <c:forEach items="${pageForm.list}" var="entity" varStatus="v">
                <li class="clearfix<c:if test="${v.index+1 ne fn:length(pageForm.list)}"> ttsw_last</c:if>">
                    <section id="sec-checked">
                        <h5>单据凭证号：${entity.journalNum}</h5>
                    </section>
                    <div onclick="toDetail('${entity.cashierBillsID}');" >
                        <p>公司：${entity.companyName}</p>
                        <p>项目编号：${entity.projectCode}</p>
                        <p>项目分类：${entity.xmtypename}</p>
                        <p>项目名称：${entity.projectName}</p>
                        <p>单据类别：${entity.billsType}</p>
                        <p>责任人部门：${entity.departmentName}</p>
                        <p>责任人：${entity.staffName}(${entity.staffCode})</p>
                        <p>制单人：${entity.inputName}</p>
                        <p>制单日期：${fn:substring(entity.cashierDate,0,19)}</p>
                        <p class="un">单据状态：
                            <span>
                                    <c:if test="${!empty entity.paystatus }">
                                        <c:if test="${entity.paystatus eq '00' }">项目未分配</c:if>
                                        <c:if test="${entity.paystatus eq '01'}">项目已分配未跟踪</c:if>
                                        <c:if test="${entity.paystatus eq '02'}">项目已跟踪未考评</c:if>
                                        <c:if test="${entity.paystatus eq '03'}">项目已考评</c:if>
                                    </c:if>
                                    <c:if test="${empty entity.paystatus}">
                                        <c:if test="${entity.status eq '00'}">草稿</c:if>
                                        <c:if test="${entity.status eq '01'}">审核中-招标前</c:if>
                                        <c:if test="${entity.status eq '02'}">已通过-招标前</c:if>
                                        <c:if test="${entity.status eq '03'}">比价审核中</c:if>
                                        <c:if test="${entity.status eq '04'}">已提交资金申请</c:if>
                                        <c:if test="${entity.status eq '05'}">待会计审核</c:if>
                                        <c:if test="${entity.status eq '06'}">待出纳审核</c:if>
                                        <c:if test="${entity.status eq '07'}">已审核</c:if>
                                        <c:if test="${entity.status eq '20'}">税务单据</c:if>
                                        <c:if test="${entity.status eq '08'}">三审已归档</c:if>
                                        <c:if test="${entity.status eq '09'}">待确认收款</c:if>
                                        <c:if test="${entity.status eq '40'}">待确定预算收入单</c:if>
                                        <c:if test="${entity.status eq '45'}">已收款</c:if>
                                        <c:if test="${entity.status eq '46'}">系统生成</c:if>
                                        <c:if test="${entity.status eq '11'}">驳回待修改</c:if>
                                    </c:if>
                                </span>
                        </p>
                    </div>
                </li>
            </c:forEach>
            <c:if test="${empty pageForm}">
                <li style="text-align:center;">暂无数据</li>
            </c:if>
        </ul>
    </div>
</div>
<script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/swiper/index.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>js/scMobile/payBudget/budgetRelease/budgetReleaseList.js" type="text/javascript" charset="utf-8"></script>
<script>
    //跳转已发布/未发布页
    function toReleaseList(fbJumpType){
        var url = "";
        if(fbJumpType == 0){//跳转发布页
            url = "ea/scBudget/ea_toRelease.jspa";
        }else{
            url = "ea/scBudget/ea_toBudgetReleaseList.jspa";
        }
        var parame = "?fbJumpType="+fbJumpType+"&fbJumpFlag="+fbJumpFlag+ "&cashierBillsId=" + cashierBillsId;
        window.location.href = basePath + url + parame;
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