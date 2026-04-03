<!DOCTYPE html>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>产品搜索</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>
    <script src="http://res2.wx.qq.com/open/js/jweixin-1.6.0.js " type="text/javascript"></script>
</head>
<style>
    html,body {
        font-size: 25px;
    }
    .content {
        background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
        background-size: 100%
    }
    .searchUl {
        overflow: hidden;
        background-color: #f2f2f2;
        color: #222;
        font-size: 0.6rem;
        padding: 0 0.5rem;
        width: 100%;
    }
    .searchUl li {
        margin-right: 1rem;
        height: 1.3rem;
        line-height: 1.3rem;
        float: left;
        text-align: left;
        width: 100%;
    }
    .ul-list2 {
        overflow: hidden;
        background-color: #f2f2f2;
        color: #222;
        font-size: 0.6rem;
        padding: 0 0.5rem;
        width: 100%;
    }
    .ul-list2 li {
        margin-right: 1rem;
        height: 1.3rem;
        line-height: 1.3rem;
        float: left;
        text-align: left;
        width: 100%;
    }
    .search{
        padding-left: 8px;
        height: 2rem;
        line-height: 2rem;
    }
    .search img{
        width: 22px;
        height: 20px;
        float: left;
        margin-top: 0.5rem;
    }
    .search input[type="text"]{
        width: 8rem;
        height: 1.2rem;
        border-style: none;
        float: left;
        margin-top: 0.4rem;
        margin-left: 0.2rem;
        font-size: 0.6rem;
        background-color: #f2f2f2;
    }
    .search input[type="button"]{
        width: 2.5rem;
        height: 1.2rem;
        border-style: none;
        float:right;
        margin-top: 0.4rem;
        margin-right: 0.2rem;
    }
    .navRecent{
        padding-left: 8px;
        font-size: 0.7rem;
        height: 2rem;
        line-height: 2rem;
        font-weight: 550;
    }
</style>
<script>
    //初始化数据
    var basePath = "<%=basePath%>";
    var treeid = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";//公司id
    var showFlag = ${showFlag};//false所有查询全部数据 true查询当前部门数据
    var departmentId = "${departmentID}";//创收部门id
    var depNameArray;//部门名称（下拉显示用）
    var depIdArray;//部门id（下拉显示用）
    var staffIdArray;//负责人id（下拉显示用）
    var staffNameArray;//负责人名称（下拉显示用）
    var staffCodeArray;//负责编码名称（下拉显示用）
    var pagecount;//总页面数（选择项目页分也用）
    var count;//总条数（选择项目页分也用）
    var pageSize;//每页多少条（选择项目页分也用）
    var pagenumber;//第几页（选择项目页分也用）
    var timer;//接收定时器用
    var companyId = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";//公司id
    var companyid = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
    var staffId = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";
    var companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
    // console.log(staffId + "----------------" + companyid);
    var goodsPurpose = "${purpose.goodsPurpose}";
    // console.log("行业："+goodsPurpose);
    var editFlag = "${editFlag}";
    // console.log("标志："+editFlag);
    var cashierBillsId = "${cashierBillsId}";
    var goodsBillsID = "";
    var menuId = "${menuId}";
    var billsType = "初始项目单";
    var typeId = "";
    var typeName = "";
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        if(null != params.get("billsType")){
            billsType = params.get("billsType");
        }
        if(null != params.get("typeId")){
            typeId = params.get("typeId");
        }
        if(null != params.get("typeName")){
            typeName = params.get("typeName");
        }
        if(null != params.get("goodsBillsID")){
            goodsBillsID = params.get("goodsBillsID");
        }
    }
</script>
<body>
<header>
    <ul class="clearfix">
        <li onclick="toBack()">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            产品搜索
        </li>
    </ul>
</header>
<div class="search_input">
    <div>
        <section class="clearfix search">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_item_search_id" placeholder="搜索产品名称" />
            <input type="button" value="取消" onclick="toBack();"/>
            <input type="button" value="查询" onclick="searchGoods();"/>
        </section>
        <div id="div_search">
            <ul id="searchUl" class="searchUl"></ul>
        </div>
    </div>
</div>
<div class="content">
    <p class="navRecent">最近选择的产品</p>
    <section class="sec-ul2">
        <ul class="ul-list2">
        </ul>
    </section>
</div>

<s:form id="f1" action="" namespace="/ea/scBudget" method="POST">
    <input type="hidden" name="costAddBean.showFlag" value="${showFlag}"/><%--是否是选择全部false全部true单一部门--%>
    <input type="hidden" id="ttsw_hid_companyId" name="costAddBean.companyId" value=""/><%--公司id--%>
    <input type="hidden" id="ttsw_hid_departmentID" name="costAddBean.departmentID" value=""/><%--所选部门id--%>
    <input type="hidden" id="ttsw_hid_departmentName" name="costAddBean.departmentName" value=""/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" id="ttsw_hid_billId" name="costAddBean.billId" value=""/><%--单据凭证号--%>
    <input type="hidden" id="ttsw_hid_companyName" name="costAddBean.companyName" value=""/><%--公司名称--%>
    <input type="hidden" id="ttsw_hid_itemName" name="costAddBean.itemName" value=""/><%--项目名称--%>
    <input type="hidden" id="ttsw_hid_itemType" name="costAddBean.itemType" value=""/><%--项目分类--%>
    <input type="hidden" id="ttsw_hid_xmType" name="costAddBean.xmType" value=""/><%--项目类型--%>
    <input type="hidden" id="ttsw_hid_itemCode" name="costAddBean.itemCode" value=""/><%--项目编号--%>
    <input type="hidden" id="ttsw_hid_itemId" name="costAddBean.itemId" value=""/><%--项目id--%>
    <input type="hidden" id="ttsw_hid_staffFzrId" name="costAddBean.staffFzrId" value=""/><%--负责人id--%>
    <input type="hidden" id="ttsw_hid_staffName" name="costAddBean.staffName" value=""/><%--负责人名称--%>
    <input type="hidden" id="ttsw_hid_staffCode" name="costAddBean.staffCode" value=""/><%--负责人编号--%>
    <input type="hidden" id="ttsw_hid_singleName" name="costAddBean.singleName" value=""/><%--制单人名称--%>
    <input type="hidden" id="ttsw_hid_barcode" name="costAddBean.barCode" value=""/><%--条形码--%>
    <input type="hidden" id="ttsw_hid_billsType" name="costAddBean.billsType" value=""/><%--条形码--%>
    <input type="hidden" id="ttsw_hid_address" name="costAddBean.address" value=""/><%--地址--%>
    <input type="hidden" id="ttsw_hid_coordinate" name="costAddBean.coordinate" value=""/><%--坐标--%>
    <input type="hidden" id="ttsw_hid_goodsPurpose" name="costAddBean.goodsPurpose" value=""/><%--用途--%>
    <input type="hidden" id="ttsw_hid_goodsPurposeId" name="costAddBean.goodsPurposeId" value=""/><%--用途--%>
    <input type="hidden" name="costAddBean.identification" value="add"/><%--跳转页面标识--%>
</s:form>
</body>
<%--下拉控制js文件--%>
<script type="text/javascript" src="<%=basePath%>js/ea/projectBudgetBill/goodsSearch.js"></script>
<script type="text/javascript">
    function toBack() {
        // if(editFlag == "edit"){
        //     window.location.href = basePath + "ea/scBudget/ea_toProjectItemUpdate.jspa?keyNum=" + goodsBillsID + "&editFlag=edit&" +
        //         "showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+cashierBillsId+"&menuId="+menuId+"&billsType="+billsType+"&typeId="+typeId+"&typeName="+typeName;
        // }else{
        //     window.location.href = basePath + "ea/scBudget/ea_addProjectItem.jspa?editFlag=add&cashierBillsId="+cashierBillsId+"&billsType="+billsType+"&typeId="+typeId+"&typeName="+typeName;
        // }

        // var url = "ea_scanningProjectInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId+"&billsType="+billsType+"&typeId="+typeId+"&typeName="+typeName;
        // $("#f1").attr('action',url);
        // $("#f1").submit();
        window.history.go(-1);
        return false;
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
