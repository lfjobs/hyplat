<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
%>
<html lang="en">
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>预算单</title>
    <link href="<%=basePath %>css/scMobile/payBudget/budgetList/style.css" rel="stylesheet">
</head>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var treeid = "<%=paramMap.get("companyId")%>";//公司id
    var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    var departmentId = "${departmentID}";//部门id，标识用于判断是那个菜单
    var showFlag = ${showFlag};//false查看总列表true查看分列表
    var departmentName = "${departmentName}";//所选部门名称
    var search = "${search}";//模糊查询条件
    var searchType = ${searchType};//模糊查询条件类型
    var staffId = "<%=paramMap.get("staffId")%>";//登录人id
    console.log(treeid+'-----'+staffId);
</script>
<body>
    <ul class="header">
        <li onclick="toBack();"><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt=""></li>
        <li>预算单</li>
        <li><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-more.png" alt=""></li>
    </ul>
    <!--一级菜单-->
    <div class="bug-nav-box" id="ttsw_one_menu_id"></div>
    <ul class="bug-nav2">
        <li onclick="toAdd();">添加</li>
        <li onclick="delCostSheet();">删除</li>
        <li onclick="toUpdate();">修改</li>
        <li onclick="toQuery();">查询</li>
        <li>打印</li>
        <li onclick="toRelease();">发布</li>
    </ul>
    <div class="bug-con  main_hide">
        <ul class="tj_con">
            <c:forEach items="${pageForm.list}" var = "entity" varStatus="v">
                <li class="clearfix<c:if test="${v.index+1 ne fn:length(pageForm.list)}"> ttsw_last</c:if>">
                    <section id="sec-checked">
                        <aside class="aside_no" checkCasId="${entity.cashierBillsID}">
                            <img class="img_no" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                            <img class="img_yes" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                        </aside>
                        <h5>单据凭证号：${entity.journalNum}<img src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>
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
    <footer>
        <ul>
            <li>
                <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-1.png">
                <h5>常用采购单</h5>
            </li>
            <li onclick="toBudgetReleaseList();">
                <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-2.png">
                <h5>采购单发布</h5>
            </li>
            <li onclick="toWholesaleMall();">
                <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-3.png">
                <h5>批发商城</h5>
            </li>
            <li>
                <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-4.png">
                <h5>商家</h5>
            </li>
            <li>
                <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-5.png">
                <h5>扫码</h5>
            </li>
        </ul>
    </footer>
</body>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
<script type="application/javascript" src="<%=basePath %>js/scMobile/payBudget/budgetList/font-size.js"></script>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/javascript.js" type="application/javascript"></script>
<%--异步获取当前登录人所在菜单--%>
<script type="text/javascript" src="<%=basePath%>js/scMobile/payBudget/budgetList/menuForMobileUtil.js"></script>
<%--异步上拉加载js文件--%>
<script type="text/javascript" src="<%=basePath%>js/scMobile/payBudget/budgetList/payBudgetList_searchresult.js"></script>
<script>
    //添加方法
    function toAdd() {
        var url = "ea/scBudget/ea_toAddPayBudget.jspa";
        var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //跳转详情
    function toDetail(id){
        var url = "ea/scBudget/ea_toDetail.jspa";
        var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+id;
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
            var url = "ea/scBudget/ea_toUpPayBudget.jspa";
            var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+id;
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
                 var url = "ea/scBudget/ea_delCostSheet.jspa?cashierBillsId="+id;
                 window.location.href = basePath + url;
             }
         }else{
             alert("请选择要删除的数据");
             return false;
         }
    }

    //跳转查询页面
    function toQuery(){
        var url = "ea/queryUtil/ea_toQuery.jspa?jumpType=YSD_LB";
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
 //   function toBack() {
  //      window.history.back();
//        var userAgent = navigator.userAgent;
//        if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Presto") != -1) {
//            window.location.replace("about:blank");
//        } else {
//            window.opener = null;
//            window.open("", "_self");
//            window.close();
//        }
//    }
    //后退
    function toBack() {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            window.history.back();
        }
    }
    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            //alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能
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