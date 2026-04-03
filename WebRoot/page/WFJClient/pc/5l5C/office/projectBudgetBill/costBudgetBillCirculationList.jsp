<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");
    String sccid = session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"";
    String tenant = (String)session.getAttribute("tenantFlag");
%>
<html lang="en">
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>初始项目</title>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBasicStyle.css" rel="stylesheet">
</head>
<style>
    .bug-nav li {
        padding: 0 0.5rem;
    }
    .bug-nav {
        font-size: 0.5rem;
    }
    .bug-nav2 {
        overflow: hidden;
        background-color: #f2f2f2;
        color: #222;
        font-size: 0.5rem;
        padding: 0 0;
    }
    .bug-nav2 li {
        padding-left: 1.5rem;
        line-height: 1.5rem;
        float: left;
        text-align: center;
    }
    .bug-nav3 {
        overflow: hidden;
        background-color: #f2f2f2;
        color: #222;
        font-size: 0.5rem;
        padding: 0 0;
        width: 1200px;
    }
    .bug-nav3 li {
        width: 3rem;
        margin-left: 0rem;
        line-height: 1.5rem;
        float: left;
        text-align: center;
    }
    .bug-nav-box {
        height: 2rem;
        line-height: 2rem;
    }
    .active_color{
        background-color: #0d9908;
    }
</style>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var treeid = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";//公司id
    var pageCount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pageNumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    var departmentId = "${departmentID}";//部门id，标识用于判断是那个菜单
    var showFlag = ${showFlag};//false查看总列表true查看分列表
    var departmentName = "${departmentName}";//所选部门名称
    var search = "${search}";//模糊查询条件
    var searchType = ${searchType};//模糊查询条件类型
    var staffId = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";//登录人id
    var companyid = "${companyid}";
    var menuId = "${menuId}";
    var cashierBillsId = "${cashierBillsId}";
    var tenantFlag = "${tenantFlag}";
    var sccid = "${sccid}";
    var tenantFlag = "${tenantFlag}";
    if(!tenantFlag){
        tenantFlag = "${tenant}";
    }
    var billsType = "${billsType}";
</script>
<body>
<ul class="header">
    <li onclick="toBack()">
        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
    </li>
    <li>初始项目</li>
</ul>
    <!--一级菜单-->
    <div class="bug-nav-box" id="ttsw_one_menu_id"></div>
    <ul class="bug-nav2">
        <li onclick="circularize();">传阅</li>
        <li onclick="circularize('approval');">审核</li>
        <li onclick="toQuery();">查询</li>
        <li onclick="printDetail();">打印</li>
<%--        <li onclick="toRelease();">发布</li>--%>
    </ul>
    <div class="bug-nav-box">
        <ul class="bug-nav3">
            <li id="init">初始项目单</li>
            <li id="inspection">验货单</li>
            <li id="inbound">入库单</li>
            <li id="outbound">出库单</li>
            <li id="checkInventory">盘库单</li>
            <li id="lossReported">报损单</li>
            <li id="sell">采购单</li>
            <li id="launch">上架单</li>
            <li id="down">下架单</li>
            <li id="order">订货单</li>
            <li id="delivery">送货单</li>
            <li id="collectMoney">收款单</li>
            <li id="reimbursement">费用报销单</li>
        </ul>
    </div>
    <div class="bug-con  main_hide">
        <ul class="tj_con">
            <c:forEach items="${pageForm.list}" var = "entity" varStatus="v">
                <li class="clearfix<c:if test="${v.index+1 ne fn:length(pageForm.list)}"> ttsw_last</c:if>">
                    <section id="sec-checked" class="sec-checked">
                        <aside class="aside_no" checkCasId="${entity.cashierBillsID}">
                            <img class="img_no" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                            <img class="img_yes" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                        </aside>
                        <h5>单据凭证号：${entity.journalNum}<img src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>
                    </section>
                    <div onclick="toDetail('${entity.cashierBillsID}');" >
                        <c:if test="${not empty entity.companyName}"><p>公司：${entity.companyName}</p></c:if>
                        <c:if test="${not empty entity.projectCode}"><p>项目编号：${entity.projectCode}</p></c:if>
                        <p>行业分类：${entity.tradeName}</p>
                        <p>项目分类：${entity.xmtypename}</p>
                        <p>单据类别：${entity.billsType}</p>
                        <c:if test="${'入库单' == entity.billsType || '出库单' == entity.billsType || '盘库单' == entity.billsType || '报损单' == entity.billsType}">
                            <p>仓库：${empty entity.dataDepotName ? '' : entity.dataDepotName}</p>
                        </c:if>
                        <c:if test="${not empty entity.departmentName}"><p>责任人部门：${entity.departmentName}</p></c:if>
                        <p>责任人：${entity.staffName}(${entity.staffCode})</p>
                        <p>制单人：${entity.inputName}</p>
                        <p>制单日期：${fn:substring(entity.cashierDate,0,10)}</p>
                        <c:if test="${!empty entity.ctUserName }">
                            <p>往来个人：${entity.ctUserName}</p>
                            <p>&nbsp;</p>
                        </c:if>
                        <c:if test="${!empty entity.startTime }">
                            <p>开始日期：${fn:substring(entity.startTime,0,10)}</p>
                        </c:if>
                        <c:if test="${!empty entity.endTime }">
                            <p>结束日期：${fn:substring(entity.endTime,0,10)}</p>
                        </c:if>
                        <c:if test="${!empty entity.priceSub }">
                            <p>总金额：${entity.priceSub}</p>
                        </c:if>
                        <p class="un">单据状态：
                            <span>
                                <c:if test="${!empty entity.paystatus }">
                                    <c:if test="${entity.paystatus eq '00' }">项目未分配</c:if>
                                    <c:if test="${entity.paystatus eq '01'}">项目已分配未跟踪</c:if>
                                    <c:if test="${entity.paystatus eq '02'}">项目已跟踪未考评</c:if>
                                    <c:if test="${entity.paystatus eq '03'}">项目已考评</c:if>
                                </c:if>
                                <c:if test="${empty entity.paystatus}">
                                    <c:if test="${entity.status eq '00'}">拟稿</c:if>
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
                                    <c:if test="${entity.status eq '50'}">传阅中</c:if>
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
</body>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
<script src="<%=basePath %>js/ea/budgetBill/javascript.js" type="application/javascript"></script>
<%--异步获取当前登录人所在菜单--%>
<script type="text/javascript" src="<%=basePath%>js/ea/budgetBill/menuForMobileUtil.js"></script>
<%--异步上拉加载js文件--%>
<script type="text/javascript" src="<%=basePath%>js/ea/budgetBill/costBudgetList.js"></script>
<script>
    //跳转详情
    function toDetail(id){
        var url = "ea/scBudget/ea_toCostBudgetDetail.jspa";
        var parameter = "?showFlag="+showFlag+"&menuId="+menuId+"&cashierBillsId="+id+"&billsType="+billsType;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //传阅
    function circularize(op){
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
           id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            window.location.href = basePath + "/page/WFJClient/pc/5l5C/office/budgetBill/selectCompany.jsp?type=cy&keyId="+id;//?docId="+docId+"&typee=p";
        }else{
            if(op == "approval"){
                alert("请选择要审核的数据");
            }else{
                alert("请选择要传阅的数据");
            }
        }
    }

    //跳转查询页面
    function toQuery(){
        var url = "ea/queryUtil/ea_toCostBudgetQuery.jspa?jumpType=DJFB_LB";
        window.location.href = basePath + url;
    }

    //后退
    function toBack() {
        if(tenantFlag=="other"){
            window.location.href = basePath + "page/WFJClient/pc/5l5C/office/zlplanmanageNew.jsp";
        }else if(tenantFlag=="personal"){
            window.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?sccid="+sccid;
        }
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

    function printDetail(){
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            if(isAndroid || isiOS) {
                alert("请在电脑端进行打印操作");
                return;
            }
            var url = "ea/scBudget/ea_toCostBudgetDetail.jspa";
            var parameter = "?pageType=printPage"+"&cashierBillsId="+id;
            // window.open = basePath + url + parameter;
            window.open(basePath + url + parameter);
        }else{
            alert("请选择要打印的数据");
            return false;
        }
    }
</script>
</html>