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
    <title>项目计划明细添加</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>
    <script src="http://res2.wx.qq.com/open/js/jweixin-1.6.0.js " type="text/javascript"></script>
</head>
<style>
    .content {
        background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
        background-size: 100%
    }

    .clearfix .headNav {
        /*height:2.4rem;*/
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
        width: 5%;
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
    table tr td:nth-of-type(5) {
        width: 18%;
    }
    /*table tr td:nth-of-type(6) {*/
    /*    width: 17%;*/
    /*}*/
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
        padding: .5rem .25rem;
        /*height: 2rem;*/
        /*line-height: 2rem;*/
    }
    .clearfix .iconNav ul li img.icon  {
        width: 90%;
        /*margin-left: 1rem;*/
        /*margin-right: 0.6rem;*/
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

    .div-wupin {
        display: none;
        z-index: 9;
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: rgba(0, 0, 0, 0.3);
        background-color: #fff;
    }
    .div-wupin .div-box .wupin-header > ul {
        padding: 0 0.5rem;
    }
    .div-wupin .div-box .wupin-header > ul li {
        float: left;
        height: 1.5rem;
        line-height: 1.5rem;
        font-size: 0.8rem;
        color: #000000;
    }
    .div-wupin .div-box .wupin-header > ul li:first-of-type {
        width: 20%;
    }
    .div-wupin .div-box .wupin-header > ul li:first-of-type img {
        width: 0.45rem;
    }
    .div-wupin .div-box .wupin-header > ul li:nth-of-type(2) {
        width: 58%;
        text-align: center;
    }
    .div-wupin .div-box .wupin-header > ul li:nth-of-type(3) {
        width: 20%;
        text-align: right;
        font-size: 0.6rem;
    }
    .div-wupin .div-box .wupin-header > ul li:nth-of-type(3) img {
        width: 0.75rem;
    }
    .div-wupin .div-box .div-con .ul-left {
        height: 92vh;
        padding-top: 0.5rem;
        width: 35%;
        overflow-y: scroll;
        background-color: #f8f8f8;
        float: left;
    }
    .div-wupin .div-box .div-con .ul-left li {
        overflow-x: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        padding-left: 0.6rem;
        height: 1.5rem;
        line-height: 1.5rem;
        font-size: 0.7rem;
        color: #3d3d3d;
        background-color: #f8f8f8;
    }
    .div-wupin .div-box .div-con .ul-left li.active {
        color: #fb5858;
        background-color: #fff;
    }
    .div-wupin .div-box .div-con .div-right {
        float: left;
        width: 54.5%;
    }
    .div-wupin .div-box .div-con .div-right .ul-02,
    .div-wupin .div-box .div-con .div-right .ul-03,
    .div-wupin .div-box .div-con .div-right .ul-04 {
        width: 33%;
        text-align: center;
        float: left;
    }
    .div-wupin .div-box .div-con .div-right .ul-02 li,
    .div-wupin .div-box .div-con .div-right .ul-03 li,
    .div-wupin .div-box .div-con .div-right .ul-04 li {
        overflow-x: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        height: 1.5rem;
        line-height: 1.5rem;
        font-size: 0.7rem;
        color: #000000;
    }
    .div-wupin .div-box .div-con .div-right .ul-02 li.active,
    .div-wupin .div-box .div-con .div-right .ul-03 li.active,
    .div-wupin .div-box .div-con .div-right .ul-04 li.active {
        color: #fb5858;
    }
    .div-wupin .div-box .div-con .div-right .ul-03,
    .div-wupin .div-box .div-con .div-right .ul-04 {
        display: none;
    }
    .div-wupin .div-box .ul1 .ul-left {
        width: 35%;
    }
    .div-wupin .div-box .ul1 .div-right {
        width: 54.5%;
    }
    .div-wupin .div-box .ul1 .div-right .ul-02 {
        width: 50%;
        display: block;
    }
    .div-wupin .div-box .ul1 .div-right .ul-03 {
        width: 33%;
        display: none;
    }
    .div-wupin .div-box .ul1 .div-right .ul-04 {
        width: 33%;
        display: none;
    }
    .div-wupin .div-box .ul2 .ul-left {
        width: 35%;
    }
    .div-wupin .div-box .ul2 .div-right {
        width: 59.5%;
    }
    .div-wupin .div-box .ul2 .div-right .ul-02 {
        width: 50%;
    }
    .div-wupin .div-box .ul2 .div-right .ul-03 {
        width: 50%;
        display: block;
    }
    .div-wupin .div-box .ul2 .div-right .ul-04 {
        width: 0%;
        display: none;
    }
    .div-wupin .div-box .ul3 .ul-left {
        width: 30%;
    }
    .div-wupin .div-box .ul3 .div-right {
        width: 69.5%;
    }
    .div-wupin .div-box .ul3 .div-right .ul-02 {
        width: 30%;
    }
    .div-wupin .div-box .ul3 .div-right .ul-03 {
        width: 30%;
        display: block;
    }
    .div-wupin .div-box .ul3 .div-right .ul-04 {
        width: 30%;
        display: block;
    }
    .div-name>div #div_table table tr th:nth-of-type(2) {
        width: 30%;
    }
    .div-name>div #div_table table tr td:nth-of-type(2) {
        width: 30%;
    }
    .div-name>div #div_table table tr th:nth-of-type(3) {
        width: 60%;
    }
    .div-name>div #div_table table tr td:nth-of-type(3) {
        width: 30%;
    }
    .navrecent{
        padding-left: 0.9rem;
        padding-top:1rem;
        font-size: 0.7rem;
        display:none;
    }
    .sec-ul2 {
        padding-bottom: 3rem
    }

    .sec-ul2 ul {
        padding: 0 .625rem 0 .625rem
    }

    .sec-ul2 ul li div.sex {
        height: 2.55rem;
        line-height: 2.55rem;
        float: left;
        padding-right: .6rem
    }

    .sec-ul2 ul li div.sex img {
        width: 1.6rem;
    }

    .sec-ul2 ul li div.sex .img-01 {
        display: inline-block
    }

    .sec-ul2 ul li div.sex .img-02 {
        display: none
    }

    .sec-ul2 ul li .div-img {
        margin-right: 0.5rem;
        display: flex;
        align-items: center;
        height: 2.1rem;
        line-height: 2.1em;
        float: left;
        overflow: hidden;
        border-radius: 50%;
        margin-top: 0.4rem;
    }

    .sec-ul2 ul li .div-img img {
        width:  2.1rem;
        object-fit: cover;
    }
    .sec-ul2 ul li {
        border-bottom: 0.025rem solid #e9e9e9;
    }

    .sec-ul2 ul li p {
        float: left;
        height: 3rem;
        line-height: 3rem;
        font-size: .5rem;
        color: #181818;
    }

    .sec-ul2 ul li p:nth-of-type(2) {
        clear:both;
        height: 0.5rem;
        line-height: 0.5rem;
        font-size: .6rem;
        color: #181818;
        margin-left:4.5rem;
    }
    .sec-ul2 ul li p:nth-of-type(3) {
        clear:both;
        height: 2.55rem;
        line-height: 2.55rem;
        font-size: .6rem;
        color: #181818;
        margin-left:4.5rem;
    }

    .sec-ul2 ul li.active div.sex .img-01 {
        display: none
    }

    .sec-ul2 ul li.active div.sex .img-02 {
        display: inline-block
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
</script>
<style>
    #ttsw_billID {
        width: 55% !important;
    }
    .div-name>div {
        position: fixed;
        width: 100%;
        height: 100%;
        bottom: 0;
        background-color: #fff;
        border-top-left-radius: 0.5rem;
        border-top-right-radius: 0.5rem;
    }

    .div-name>div>section:first-of-type input:first-of-type {
        float: left;
        width: 60%;
        padding-left: 10%
    }

    .div-name>div>section:first-of-type input:nth-of-type(2) {
        float: right;
        width: 19%;
        color: #222
    }
    .div-name>div>section:first-of-type input:last-of-type {
        float: right;
        width: 19%;
        color: #222;
        padding-left: 1rem;
    }
    .content ul.ul_con3 {
        padding-top: 2.5rem;
        padding: 2.5rem .25rem
    }

    .content ul.ul_con3 li {
        text-align: center;
        float: left;
        width: 20%
    }

    .content ul.ul_con3 li p.p_img img {
        width: 63%
    }
</style>
<body class="hy">
<%--项目名称/分类显示--%>
<div class="div-name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_item_search_id" class="" placeholder="搜索项目名称" name="" value=""/>
            <input type="button" value="取消" onclick="closeInfo();"/>
            <input type="button" value="查询" onclick="initItemInfo();"/>
        </section>
        <div id="div_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_item_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_add">
                确定
            </p>
        </section>
    </div>
</div>
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li onclick="toBack()">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            项目计划明细添加
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="f2" action="ea_toAddPlanCostBudgetBill.jspa" namespace="/ea/scBudget" method="POST">
        <section class="sec_con1">
            <div class="clearfix">
                <p class="clearfix">
                    <label for=""><span>*</span>物品分类： </label>
                    <input type="text" name="purpose.goodsPurpose" id="wupinType" value="${purpose.goodsPurpose}"
                           placeholder="请选择物品分类" required readonly/>
                    <input type="hidden" name="purpose.goodsPurposeId" id="wupinTypeId" value="${purpose.goodsPurposeId}"
                           placeholder="请选择物品分类" required readonly/>
                    <input type="hidden" id="billsType" name="purpose.billsType" value="${purpose.billsType}"/><%--条形码--%>
<%--                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>--%>
                </p>
                <section class="iconNav">
                    <ul>
                        <li onclick="toAndroidCallcamera();"><img class="icon" src="<%=basePath %>images/WFJClient/budget/scan.png"/></li>
                        <li onclick="toAddItem();"><img class="icon" src="<%=basePath %>images/WFJClient/pic_1.png"/></li>
                        <li onclick="toRemoveGoodsBills();"><img class="icon" src="<%=basePath %>images/WFJClient/budget/ico_1_07.png"/></li>
                        <li><img class="icon" src="<%=basePath %>images/scMobile/payBudget/addBudget/img_4_13.png"/></li>
                    </ul>
                </section>
            </div>
        </section>
    </s:form>
    <section class="sec_con2">
        <%--选择具体项目后显示项目详细信息--%>
        <c:if test="${!empty scanGoodsMap}">
            <ul class="ul_con">
            <table>
                <tr>
                    <td></td>
                    <td>物品名称</td>
                    <td>初始余额</td>
                    <td>收方金额</td>
                    <td>借方金额</td>
                    <td>余额</td>
                </tr>
                <% int number = 1; %>
                <c:forEach var='entity' items="${scanGoodsMap}">
                    <tr class="rgid">
                        <td>
                            <aside id="sec-checked" class="aside_no" checkCasId="${entity.value.goodsBillsID}">
                                <img class="img_no" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                                <img class="img_yes" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                            </aside>
                        </td>
                        <td class="quantity2" onclick="toBarCodeInfo('${entity.value.goodsBillsID }');">${entity.value.goodsName}</td>
                        <td class="goodname" onclick="toBarCodeInfo('${entity.value.goodsBillsID }');">${entity.value.initialBalance}</td>
                        <td class="quantity1" onclick="toBarCodeInfo('${entity.value.goodsBillsID }');">${entity.value.borrowAmount}</td>
                        <td class="requantity" onclick="toBarCodeInfo('${entity.value.goodsBillsID }');">${entity.value.loanAmount}</td>
                        <td class="requantity" onclick="toBarCodeInfo('${entity.value.goodsBillsID }');">${entity.value.balance}</td>
<%--                        <td class="isqualify">${entity.value.standard}</td>--%>

<%--                        <td><img onclick="toRemoveGoodsBills('${entity.value.goodsBillsID}');" class="img_del"--%>
<%--                                 src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_del.png"/></td>--%>
                        <td type="hidden">
                            <input type="hidden" class="ttsw_jj" value="${entity.value.budgetAmount}"/>
                        </td><%--价格--%>
                    </tr>
                    <% number++; %>
                </c:forEach>
            </table>
            </ul>
        </c:if>
        <div class="clearfix">
            <div class="left">
                <p>合计：¥<span id="ttsw_allPrice">0</span></p>
                <p>共<span id="ttsw_num_pro">0</span>种商品</p>
            </div>
            <div class="right">
                <p onclick="toAdd();">提交</p>
            </div>
        </div>
    </section>
    <p class="navrecent">以下是最近添加物品：</p>
    <section class="sec-ul2">
        <ul class="ul-list2">
        </ul>
    </section>
    <!-- 物品类别 -->
    <div class="div-wupin">
        <div class="div-box">
            <div class="wupin-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        物品类别
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix wplb">
            </div>
        </div>
    </div>
<%--    <ul class="ul_con3 clearfix">--%>
<%--        &lt;%&ndash;  <li id="ttsw_smq_id" onclick="Android.callcamera();"/> &ndash;%&gt;--%>
<%--        <li id="ttsw_smq_id" onclick="toAndroidCallcamera();">--%>
<%--        <p class="p_img">--%>
<%--            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_07.png"/>--%>
<%--        </p>--%>
<%--        <p class="p_p">--%>
<%--            扫码枪--%>
<%--        </p>--%>
<%--        </li>--%>
<%--        <li id="ttsw_tj_id" onclick="toAddItem();">--%>
<%--            <p class="p_img">--%>
<%--                <img src="<%=basePath %>images/WFJClient/pic_1.png"/>--%>
<%--            </p>--%>
<%--            <p class="p_p">--%>
<%--                添加--%>
<%--            </p>--%>
<%--        </li>--%>
<%--            <li id="ttsw_del_id" onclick="toDelItem();">--%>
<%--                <p class="p_img">--%>
<%--                    <img src="<%=basePath %>images/WFJClient/budget/ico_1_07.png"/>--%>
<%--                </p>--%>
<%--                <p class="p_p">--%>
<%--                    删除--%>
<%--                </p>--%>
<%--            </li>--%>
<%--    </ul>--%>
</div>
<%--页面添加数据封装成bean提交跳转页面用--%>
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
<script type="text/javascript" src="<%=basePath%>js/ea/plan/toCostBudgetItemAdd.js"></script>
<%--调用安卓js文件--%>
<script type="text/javascript" src="<%=basePath %>js/ea/plan/productslaunch.js"></script>
<script>

    function nav(selectId) {
        <%--$("#"+selectId).attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");--%>
        if (selectId == "收入调价") {
            $("#zcfb").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxwselet.png");
            $("#srdj").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");
        } else {
            $("#srdj").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxwselet.png");
            $("#zcfb").attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");
        }
        $("#billsType").val(selectId);

    }

    //点击审核提交
    function toAdd() {
        // var result = toCheckNull(0);
        // result="true";
        // if (result == "true") {//验证通过
        //     $("#f2").submit();
        // } else {
        //     alert(result);
        //     return false;
        // }
        var url = basePath + "ea/scBudget/ea_toAddPlanCostBudgetBill.jspa";
        if(editFlag == "edit"){
            url = basePath + "ea/scBudget/ea_toUpdatePlanCostBudgetBill.jspa?cashierBillsId="+cashierBillsId;
        }
        $("#f2").attr("action",url);
        // console.log("editFlag:"+editFlag+"url:"+url);
        $("#f2").submit();
    }

    //调用扫码枪获取货品信息
    function toAndroidCallcamera() {
        // var result = toCheckNull(1);
        // if (result == "true") {//验证通过
        //     //TODO:暂时注释，方便测试
        //     // Android.callcamera();
        //     calltiaoma('111');
        // } else {
        //     alert(result);
        //     return false;
        // }
        //TODO:暂时注释，方便测试
        // Android.callcamera();
        // calltiaoma('111');

        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if(isWeixin){
            var url = basePath
                + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";
            var retUrl = location.href.split('#')[0];
            $.ajax({
                url : url,
                type : "post",
                async : false,
                dataType : "json",
                data:{
                    retUrl: retUrl
                },
                success : function(data) {
                    var m = eval("("+data+")");
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: m.appId, // 必填，公众号的唯一标识
                        timestamp:m.timestamp , // 必填，生成签名的时间戳
                        nonceStr: m.nonceStr, // 必填，生成签名的随机串
                        signature: m.signature,// 必填，签名
                        jsApiList: ["scanQRCode"] // 必填，需要使用的JS接口列表
                    });

                    wx.error(function (res) {
                        console.log(res);
                        alert("错误");
                        alert(res);
                    });


                    wx.ready(function () {
                        wx.scanQRCode({
                            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                            scanType: ["qrCode","barCode","datamatrix","pdf417"], // 可以指定扫二维码还是一维码，默认二者都有
                            success: function (res) {
                                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                // alert(result);
                                if(result.indexOf("http")!=-1){
                                    window.location.href = result;
                                }else{
                                    var barcode = "";
                                    if(result.indexOf(",")!=-1){
                                        barcode = result.substring(result.indexOf(",")+1);
                                    }else{
                                        barcode = result;
                                    }
                                    $("#ttsw_hid_barcode").val(barcode);

                                    var wupinType = $("#wupinType").val();
                                    var wupinTypeId = $("#wupinTypeId").val();
                                    var billsType = $("#billsType").val();
                                    if(!wupinTypeId){
                                        alert("请选择物品分类");
                                        return false;
                                    }
                                    $("#ttsw_hid_goodsPurpose").val(wupinType);
                                    $("#ttsw_hid_goodsPurposeId").val(wupinTypeId);
                                    $("#ttsw_hid_billsType").val(billsType);
                                    // console.log("条码值："+$("#ttsw_hid_barcode").val());
                                    //公司id
                                    //treeid = "company20180510CQZCDKTT690000006064";//测试用
                                    // $("#ttsw_hid_companyId").val(treeid);
                                    var url = "ea_scanningPlanCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId;
                                    $("#f1").attr('action',url);    //通过jquery为action属性赋值
                                    $("#f1").submit();    //提交ID为myform的表单

                                }
                            }
                        });
                    });

                }
            });


        }else {

            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            if (isAndroid == true) {

                Android.callcamera();
            } else {
                var url = "func=" + 'calltiaomaIOS';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    }

    //校验是否为空
    function toCheckNull(num) {
        //所选部门id名称
        var message = "true";
        //单据凭证号
        var hid_billId = $("#ttsw_billID").val();
        if (hid_billId == null || hid_billId == "") {
            message = "请录入单据凭证号";
        }
        //公司名称
        var hid_companyName = $("#ttsw_companyNmae").val();
        if (hid_companyName == null || hid_companyName == "") {
            message = "请录入公司名称";
        }
        //项目名称
        var hid_itemName = $("#project-name").val();
        if (hid_itemName == null || hid_itemName == "") {
            message = "请选择项目名称";
        }
        //项目分类
        var hid_itemType = $("#project-fl").val();
        if (hid_itemType == null || hid_itemType == "") {
            message = "请选择项目分类";
        }
        //负责人id
        var hid_staffFzrId = $(".ttsw_emp_id").val();
        if (hid_staffFzrId == null || hid_staffFzrId == "") {
            message = "请选择负责人";
        }
        //制单人名称
        var hid_singleName = $("#ttsw_singleName").val();
        if (hid_singleName == null || hid_singleName == "") {
            message = "请填写制单人";
        }
        //判断金额是否为0
        if (num == 0) {
            var priceAll = $("#ttsw_allPrice").text();
            if (priceAll == "" || priceAll == null || priceAll == "0") {
                message = "请扫描商品或商品金额不能为0";
            }
        }
        return message;
    }

    function toBarCodeInfo(goodsBillsID) {
        window.location.href = basePath + "ea/scBudget/ea_toPlanCostBudgetItemToUpdate.jspa?keyNum=" + goodsBillsID + "&editFlag=addPage&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}";
    }

    //根据id删除以保存数据
    function toRemoveGoodsBills() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id){
            var url = "ea_removePlanGoodsBillOfToAdd.jspa?keyNum=" + id+"&editFlag=add";
            $("#f2").attr('action',url);    //通过jquery为action属性赋值
            $("#f2").submit();    //提交ID为myform的表单
        }

        // document.location.replace(basePath + "ea/scBudget/ea_removeGoodsBillOfAdd.jspa?keyNum=" + goodsBillsId+"&editFlag=add&cashierBillsId="+cashierBillsId+"&menuId="+menuId);
    }

    //后退
    function toBack() {
        if(editFlag == "edit"){
            window.location.href = basePath + "ea/scBudget/ea_toUpdatePlanCostBudgetBill.jspa?commitFlag='no'&cashierBillsId="+cashierBillsId;
       }else{
            window.location.href = basePath + "ea/scBudget/ea_toAddPlanCostBudgetBill.jspa?commitFlag='no'";
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
</script>
</html>
