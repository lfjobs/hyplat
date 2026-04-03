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
    String menuName = (String)session.getAttribute("menuName");
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>初始项目</title>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBasicStyle.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
    <script src="<%=basePath %>js/ea/budgetBill/javascript.js" type="application/javascript"></script>
    <%--异步获取当前登录人所在菜单--%>
    <script type="text/javascript" src="<%=basePath%>js/ea/budgetBill/menuForMobileUtil.js"></script>
    <%--异步上拉加载js文件--%>
    <script type="text/javascript" src="<%=basePath%>js/ea/budgetBill/costBudgetDealingsList.js"></script>
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
    var staffId = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";//登录人id
    var companyid = "${companyid}";
    var menuId = "${menuId}";
    var tenantFlag = "${tenantFlag}";
    if(!tenantFlag){
        tenantFlag = "${tenant}";
    }
    var cashierBillsId = "${cashierBillsId}";
    var menuName = "${menuName}";
    if(menuName){
        menuName = "<%= menuName %>"
    }
</script>
<body>
    <ul class="header">
        <li onclick="toBack()">
            <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
        </li>
        <li>初始项目</li>
    </ul>
    <div class="bug-nav-box" id="ttsw_one_menu_id"></div>
    <ul class="bug-nav2">
        <li id="showCompany">往来公司</li>
        <li id="showPerson">往来个人</li>
        <li id="showOther">其它</li>
    </ul>
    <div class="list">
        <ul>
        </ul>
    </div>
</body>
</html>