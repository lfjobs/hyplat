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
    <title>项目计划</title>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/style.css" rel="stylesheet">
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
        width: 18%;
        line-height: 2rem;
        float: left;
        text-align: center;
    }
    .bug-nav-box {
        height: 2.5rem;
    }
</style>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var treeid = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";//公司id
    var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    var departmentId = "${departmentID}";//部门id，标识用于判断是那个菜单
    var showFlag = ${showFlag};//false查看总列表true查看分列表
    var departmentName = "${departmentName}";//所选部门名称
    var search = "${search}";//模糊查询条件
    var searchType = ${searchType};//模糊查询条件类型
    var staffId = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";//登录人id
    var companyid = "${companyid}";
    var menuId = "${menuId}";
    var tenantFlag = "${tenantFlag}";
    if(!tenantFlag){
        tenantFlag = "${tenant}";
    }
    var cashierBillsId = "${cashierBillsId}";
    var sccid = "${sccid}";
</script>
<body>
<ul class="header">
    <li onclick="toBack()">
        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
    </li>
    <li>项目计划</li>
    <%--        <li><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-more.png" alt=""></li>--%>
</ul>
    <!--一级菜单-->
    <div class="bug-nav-box" id="ttsw_one_menu_id"></div>
    <ul class="bug-nav2">
        <li onclick="toAdd();">添加</li>
        <li onclick="delCostSheet();">删除</li>
        <li onclick="toUpdate();">修改</li>
<%--        <li>传阅</li>--%>
<%--        <li>审批</li>--%>
        <li onclick="toQuery();">查询</li>
        <li onclick="printDetail();">打印</li>
<%--        <li onclick="toRelease();">发布</li>--%>
    </ul>
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
                        <c:if test="${not empty entity.departmentName}"><p>责任人部门：${entity.departmentName}</p></c:if>
                        <p>责任人：${entity.staffName}(${entity.staffCode})</p>
                        <p>制单人：${entity.inputName}</p>
                        <p>制单日期：${fn:substring(entity.cashierDate,0,19)}</p>
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
<script type="application/javascript" src="<%=basePath %>js/scMobile/payBudget/budgetList/font-size.js"></script>
<script src="<%=basePath %>js/ea/budgetBill/javascript.js" type="application/javascript"></script>
<%--异步获取当前登录人所在菜单--%>
<script type="text/javascript" src="<%=basePath%>js/ea/plan/menuForMobileUtil.js"></script>
<%--异步上拉加载js文件--%>
<script type="text/javascript" src="<%=basePath%>js/ea/plan/costBudgetList.js"></script>
<script>
    //添加方法
    function toAdd() {
        var url = "ea/scBudget/ea_toAddPlanCostBudgetBill.jspa";
        var parameter = "?showFlag="+showFlag+"&companyid="+companyid+"&menuId="+menuId;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //跳转详情
    function toDetail(id){
        var url = "ea/scBudget/ea_toPlanCostBudgetDetail.jspa";
        var parameter = "?showFlag="+showFlag+"&menuId="+menuId+"&cashierBillsId="+id;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //修改预算单
    function toUpdate(){
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
           id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            var url = "ea/scBudget/ea_toUpdatePlanCostBudgetBill.jspa";
            var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+id+"&menuId="+menuId;
            if(showFlag){//单独部门传部门名称过去
                parameter += "&departmentName="+departmentName;
            }
            window.location.href = basePath + url + parameter;
        }else{
            alert("请选择要修改的数据");
            return false;
        }
    }

    //删除预算单
    function delCostSheet(){
        //循环获取选中的值
         var id = "";
         $(".aside_yes").each(function (){
             id=$(this).attr("checkCasId");
         });
         if(id != "" && id != null){
             var r = confirm("确定删除该数据吗？");
             if (r == true) {
                 var url = "ea/scBudget/ea_delPlanCostBudgetSheet.jspa?cashierBillsId="+id+"&menuId="+menuId;
                 window.location.href = basePath + url;
             }
         }else{
             alert("请选择要删除的数据");
             return false;
         }
    }

    //跳转查询页面
    function toQuery(){
        //项目计划列表
        var url = "ea/queryUtil/ea_toCostBudgetQuery.jspa?jumpType=DJFB_LB&tenantFlag="+tenantFlag;
        window.location.href = basePath + url;
    }

    //跳转发布页面
    function toRelease(){
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            var url = "ea/scBudget/ea_toRelease.jspa?cashierBillsId="+id;
            window.location.href = basePath + url;
        }else{
            alert("请选择要发布的数据");
            return false;
        }
    }

    //跳转采购单发布列表页
    function toBudgetReleaseList(){
        var url = "ea/scBudget/ea_toBudgetReleaseList.jspa?fbJumpType=1&fbJumpFlag=true";
        window.location.href = basePath + url;
    }

    //跳转批发商城页
    function toWholesaleMall(){
        //alert("暂未开通，尽请期待");
        var url = "ea/wholesaleMall/ea_toWholesaleMall.jspa";
        window.location.href = basePath + url;
    }

    //后退
    function toBack() {
        if(tenantFlag=="other"){
            window.history.back();
            // window.location.href = basePath + "page/WFJClient/pc/5l5C/office/zlplanmanageNew.jsp";
            //window.location.href = basePath + "ea/5l5c/ea_work5L5C.jspa?companyID="+treeid+"&staffID="+staffId;
        }else if(tenantFlag=="personal"){
            window.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?sccid="+sccid;
        }
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
            // back();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });

    function back(){
        window.history.go(-1);return false;
    }

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
            var url = "ea/scBudget/ea_toPlanCostBudgetDetail.jspa";
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