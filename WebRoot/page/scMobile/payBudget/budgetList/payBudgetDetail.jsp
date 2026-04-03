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

%>
<html>
<head>
    <meta charset="utf-8" />
    <title>预算单添加</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<style>
    .content {
        background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
        background-size: 100%
    }
    #ttsw_billID{
        width: 55%!important;
    }
</style>
<script>
    //初始化数据
    var basePath="<%=basePath%>";
</script>
<body class="hy">
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            预算单详情
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec_con1">
        <p>
            <label for=""><span></span>单据凭证号：</label>
            <input type="text" name="cashierBills.journalNum" id="ttsw_billID" value="${cashierBills.journalNum}" placeholder="自动获取" readonly/>
        </p>
        <div class="clearfix">
            <p>
                <label for=""><span></span>公司：</label>
                <input type="text" id="ttsw_companyNmae" name="cashierBills.companyName"  class="gs_name" value="${cashierBills.companyName}" placeholder="" readonly/>
            </p>
            <p>
                <label for=""><span></span>创收部门：</label>
                <input type="text" name="cashierBills.departmentName" id="ttsw_dep_y_name" value="${cashierBills.departmentName}" placeholder="请选择部门" readonly />
            </p>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>项目名称：</label>
                <input type="text" name="cashierBills.projectName" id="project-name" value="${cashierBills.projectName}" placeholder="请选择项目名称" readonly/>
            </p>
            <p class="clearfix">
                <label for=""><span>*</span>项目分类： </label>
                <input type="text" name="cashierBills.xmtypename" id="project-fl" value="${cashierBills.xmtypename}" placeholder="请选择项目分类" readonly/>
            </p>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>责任人： </label>
                <input type="text" class="xiala-1"  value="${cashierBills.staffName}(${cashierBills.staffCode})" placeholder="" required readonly/>
            </p>
            <div class="select_box select_box1"></div>
        </div>
        <div class="clearfix">
            <p>
                <label for=""><span></span>制单人：</label>
                <input type="text" name="" id="ttsw_singleName" value="${cashierBills.inputName}" placeholder="自动获取" required  readonly/>
            </p>
            <p>
                <label for=""><span>*</span>制单日期： </label>
                <input type="text" name="" value="<fmt:formatDate value="${cashierBills.cashierDate}" pattern="yyyy-MM-dd" />" placeholder="" required readonly/>
            </p>
        </div>
        <p>
            <label for=""><span></span>单据状态： </label>
            <c:if test="${!empty cashierBills.paystatus }">
                <c:if test="${cashierBills.paystatus eq '00' }"><input type="text" value="项目未分配" readonly/></c:if>
                <c:if test="${cashierBills.paystatus eq '01'}"><input type="text" value="项目已分配未跟踪" readonly/></c:if>
                <c:if test="${cashierBills.paystatus eq '02'}"><input type="text" value="项目已跟踪未考评" readonly/></c:if>
                <c:if test="${cashierBills.paystatus eq '03'}"><input type="text" value="项目已考评" readonly/></c:if>
            </c:if>
            <c:if test="${empty cashierBills.paystatus}">
                <c:if test="${cashierBills.status eq '00'}"><input type="text" value="草稿" readonly/></c:if>
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
            </c:if>
        </p>
    </section>
    <%--选择具体项目后显示项目详细信息--%>
    <c:if test="${!empty goodBeanslist}">
        <section class="sec_con2">
            <ul class="ul_con">
                <c:forEach items="${goodBeanslist}" var="entity" varStatus="v">
                    <li class="clearfix" onclick="toBarCodeInfo('${entity[0].goodsBillsID }');">
                        <section class="clearfix">
                            <div>
                                <img src="<%=basePath %>/${entity[1].photoPath}"/>
                            </div>
                            <ul>
                                <li class="img_no1 txt">${entity[1].goodsName}</li>
                                <li class="img_no2 txt">条码：${entity[1].barCode} </li>
                                <li class="img_no3 txt">
                                    <span>规格：${entity[1].standard} </span>
                                    单位：${entity[1].variableID}
                                </li>
                                <li class="img_no4 txt">
                                    <span>进价：¥${entity[0].price}</span>
                                    状态：<span>未审</span>
                                </li>
                            </ul>
                        </section>
                    </li>
                </c:forEach>
            </ul>
        </section>
    </c:if>
</div>
</body>
<script>
    function toBarCodeInfo(goodsBillsID){
        window.location.href = basePath + "ea/scBudget/ea_toBarCodeInfo.jspa?search="+goodsBillsID+"&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}";
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
</html>
