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
%>
<html lang="en">
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>采购销售订单</title>
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
        padding-left: 1.7rem;
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
    var tenantFlag = "${tenantFlag}";
    var sccid = "${sccid}";
    var billsType = "${billsType}";
</script>
<body>
<ul class="header" style="background-color: #f74c32;color: #fff;">
    <li onclick="toBack()">
        <img src="<%=basePath %>images/WFJClient/pc/5l5c/img_03.png" alt="">
    </li>
    <li>采购销售订单</li>
</ul>
    <!--一级菜单-->
    <div class="bug-nav-box" id="ttsw_one_menu_id"></div>
    <ul class="bug-nav2">
        <li onclick="toQuery();">查询</li>
        <li onclick="printDetail();">打印</li>
    </ul>
    <div class="bug-con  main_hide">
        <ul class="tj_con">
        </ul>
    </div>
</body>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/javascript.js" type="application/javascript"></script>
<%--异步获取当前登录人所在菜单--%>
<script type="text/javascript" src="<%=basePath%>js/ea/projectBudgetBill/menuForMobileUtil.js"></script>
<%--异步上拉加载js文件--%>
<script type="text/javascript" src="<%=basePath%>js/ea/projectBudgetBill/costBudgetSentList.js"></script>
<script>
    //跳转详情
    function toDetail(id){
        var url = "ea/scBudget/ea_toProjectBudgetDetail.jspa";
        var parameter = "?showFlag="+showFlag+"&menuId="+menuId+"&cashierBillsId="+id+"&billsType="+billsType;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //跳转查询页面
    function toQuery(){
        var url = "ea/queryUtil/ea_toCostBudgetQuery.jspa?jumpType=DJFB_LB";
        window.location.href = basePath + url;
    }

    //后退
    function toBack() {
        if (tenantFlag == "personal") {
            window.location.href = basePath + "ea/mycenter/ea_myIndex.jspa";
        } else {
            // window.location.href = basePath + "ea/scBudget/ea_projectOrderManage.jspa?tenantFlag=other&companyid=" + treeid + "&staffId=" + staffId;
            window.location.href = basePath + "ea/5l5c/ea_work5L5C.jspa?companyID="+(companyid == "" ? treeid : companyid)+"&staffID="+staffId;
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
            var url = "ea/scBudget/ea_toProjectBudgetDetail.jspa";
            var parameter = "?pageType=printPage"+"&cashierBillsId="+id;
            window.open(basePath + url + parameter);
        }else{
            alert("请选择要打印的数据");
            return false;
        }
    }
</script>
</html>