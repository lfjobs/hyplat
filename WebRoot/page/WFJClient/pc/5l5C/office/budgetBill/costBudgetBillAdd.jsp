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
    String tenantFlag = (String)session.getAttribute("tenantFlag");
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>初始项目单添加</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBillAdd.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js" type="text/javascript"
            charset="utf-8"></script>
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
        padding-right: .5rem;
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
        width: 18%;
    }
    table tr td:nth-of-type(5) {
        width: 18%;
    }
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
    // console.log(staffId + "----------------" + companyid);
    var menuId = "${menuId}";
    var tenantFlag = "${tenantFlag}";
    var billsType = "初始项目单";
    var head = "";
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        if(null != params.get("billsType")){
            billsType = params.get("billsType");
        }
        if(null != params.get("head")){
            head = params.get("head");
        }
    }
    var depotID = "${depotID}";//仓库id
    var depotNameArray;//仓库名称（下拉显示用）
    var depotIdArray;//仓库id（下拉显示用）
</script>
<style>
    #ttsw_billID {
        width: 55% !important;
    }
</style>
<body class="hy">
<%--项目名称/分类显示--%>
<div class="div-name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_item_search_id" class="" placeholder="搜索项目名称" name="" value=""/>
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
        <li id = "title">
            ${(billsType=='收入' || billsType=='支出') ? '初始项目':billsType}添加
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="f2" action="ea_saveCostBudgetSheet.jspa" namespace="/ea/scBudget" method="POST">
        <section class="sec_con1">
            <p>
                <label for=""><span></span>单据凭证号：</label>
                <input type="text" name="cashierBills.journalNum" id="ttsw_billID" value="${billID}" placeholder="自动获取"
                       required readonly/>
            </p>
            <c:if test="${tenantFlag eq 'other'}">
            <div class="clearfix">
                <p>
                    <label for=""><span></span>公司：</label>
                    <input type="text" id="ttsw_companyNmae" name="cashierBills.companyName" class="gs_name"
                           value="${companyName}" placeholder="" required readonly/>
                </p>
                    <%--所选分部门则显示不可修改--%>
                <c:if test="${showFlag eq true}">
                    <p>
                        <label for=""><span></span>部门：</label>
                        <input type="text" name="cashierBills.departmentName" id="ttsw_dep_y_name"
                               value="${departmentName}" placeholder="请选择部门" required readonly/>
                        <input type="text" name="cashierBills.departmentID" id="ttsw_dep_y_id" value="${departmentID}"
                               readonly style="display: none;"/><%--所选部门id--%>
                    </p>
                </c:if>
                    <%--所选为总列表，则显示下来自己选择--%>
                    <p class="clearfix">
                        <label for=""><span>*</span>部门： </label>
<%--                        <input type="text" id="ttsw_dep_n_name" name="cashierBills.departmentName"--%>
<%--                               value="${empty costBaseBean.departmentName ? departmentName : costBaseBean.departmentName}" required readonly/>--%>
                        <input type="text" class="csbm_xiala-1" id="ttsw_dep_n_name" name="cashierBills.departmentName"
                               value="${empty costBaseBean.departmentName ? (orgList[0].organizationName) : costBaseBean.departmentName}" placeholder="请选择部门" required readonly/>
                        <input type="text" class="csbm_id_xiala-1" id="ttsw_dep_n_id" name="cashierBills.departmentID"
                               value="${empty costBaseBean.departmentID ? (orgList[0].organizationID) : costBaseBean.departmentID}" readonly style="display: none;"/><%--所选部门id--%>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </p>
                    <div class="select_box csbm_select_box1"></div>
            </div>
            </c:if>
            <div class="clearfix">
                <p class="clearfix">
                    <label for=""><span>*</span>行业分类： </label>
                    <input type="text" name="cashierBills.tradeName" id="hyfl" value="${costBaseBean.tradeName}"
                           placeholder="请选择行业分类" required readonly/>
                    <input type="hidden" name="cashierBills.tradeId" id="hyflId" value="${costBaseBean.tradeId}" required readonly/>
                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                </p>
                <p class="clearfix">
                    <label for=""><span>*</span>项目分类： </label>
                    <input type="text" name="cashierBills.xmtypename" id="xiangmu" value="${costBaseBean.xmTypeName}"
                           placeholder="请选择项目分类" required readonly/>
                    <input type="hidden" name="cashierBills.xmtype" id="xiangmuId" value="${costBaseBean.xmType}" required readonly/>
                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                </p>
<%--                <p class="clearfix">--%>
<%--                    <label for=""><span>*</span>项目名称：</label>--%>
<%--                    <input type="text" name="cashierBills.projectName" id="project-name" value="${costBaseBean.itemName}"--%>
<%--                           placeholder="请选择项目名称" style="display: none;" required readonly/>--%>
<%--&lt;%&ndash;                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>&ndash;%&gt;--%>
<%--                </p>--%>
                <input type="text" name="cashierBills.projectName" id="project-name" value="${costBaseBean.itemName}"
                       placeholder="请选择项目名称" style="display: none;" required readonly/>
                <input type="text" id="ttsw_item_check_id" name="cashierBills.proID" value="${costBaseBean.itemId}"
                       style="display: none;"/><%--所选项目ppid--%>
<%--                <input type="text" id="ttsw_item_check_type" name="cashierBills.xmtype" value="${costBaseBean.xmType}"--%>
<%--                       style="display: none;"/>&lt;%&ndash;所选项目类型&ndash;%&gt;--%>
                <input type="text" id="ttsw_item_check_code" name="cashierBills.projectCode" value="${costBaseBean.itemCode}"
                       style="display: none;"/><%--所选项目编号--%>
            </div>
            <div class="clearfix" id="inv">
                <p class="clearfix">
                    <label for=""><span>*</span>仓库： </label>
                    <input type="text" class="inv_name" id="ttsw_inv_n_name" name="cashierBills.dataDepotName"
                           value="${empty costBaseBean.dataDepotName ? (invList[0].depotName) : costBaseBean.dataDepotName}" placeholder="请选择仓库" required readonly/>
                    <input type="text" class="inv_id" id="ttsw_inv_n_id" name="cashierBills.dataDepotID"
                           value="${empty costBaseBean.dataDepotID ? (invList[0].depotID) : costBaseBean.dataDepotID}" readonly style="display: none;"/>
                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                </p>
                <div class="select_box csbm_select_box2"></div>
            </div>
<%--            <div class="clearfix">--%>
<%--                <p>--%>
<%--                    <label for=""><span></span>图片<span></span><span></span><span></span></label>--%>
<%--                    <label for=""><span></span><span></span><span></span>视频</label>--%>
<%--                </p>--%>
<%--                <p>--%>
<%--                    <label for=""><span></span>位置：</label>--%>
<%--                    <input type="text" name="" id="address" name="cashierBills.address" value="${empty costBaseBean.address ? '金牛区红星美凯龙':costBaseBean.address}" required readonly/>--%>
<%--                    <input type="text" name="" id="coordinate" style="display: none;" name="cashierBills.coordinate" value="${empty costBaseBean.coordinate ? '':costBaseBean.coordinate}" required readonly/>--%>
<%--                </p>--%>
<%--            </div>--%>
            <div class="clearfix">
                <p class="clearfix">
                    <label for=""><span>*</span>责任人： </label>
                    <input type="text" class="xiala-1"
                           value="${empty costBaseBean.staffName ? staff.staffName:costBaseBean.staffName}(${empty costBaseBean.staffCode ? staff.staffCode:costBaseBean.staffCode})"
                           placeholder="" required readonly/>
                    <input type="text" name="cashierBills.staffID" class="ttsw_emp_id"
                           value="${empty costBaseBean.staffFzrId ? staff.staffID:costBaseBean.staffFzrId}"
                           style="display: none;"/>
                    <input type="text" name="cashierBills.staffName" class="ttsw_emp_name"
                           value="${empty costBaseBean.staffName ? staff.staffName:costBaseBean.staffName}"
                           style="display: none;"/>
                    <input type="text" name="cashierBills.staffCode" class="ttsw_emp_code"
                           value="${empty costBaseBean.staffCode ? staff.staffCode:costBaseBean.staffCode}"
                           style="display: none;"/>
                        <%--                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>--%>
                </p>
                <p>
                    <label for=""><span></span>制单人：</label>
                    <input type="text" name="" id="ttsw_singleName"
                           value="${empty costBaseBean.singleName ? staff.staffName:costBaseBean.singleName}" placeholder="自动获取"
                           required readonly/>
                </p>
            </div>
            <div class="clearfix">
                <p>
                    <label for=""><span>*</span>制单日期： </label>
                    <input type="text" name="" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />"
                           placeholder="" required readonly/>
                </p>
                <p class="clearfix">
                    <label for=""><span></span>单据状态： </label>
                    <input type="text" name="" id="" value="拟稿" placeholder="自动获取" readonly/>
                </p>
            </div>
            <div class="clearfix">
                <p>
                    <label for="">往来个人： </label>
                    <input type="text" name="cashierBills.ctUserName" value="${costBaseBean.ctUserName}" placeholder="" />
                </p>
            </div>
            <div class="clearfix">
                <p>
                    <label for="">开始日期： </label>
                    <input type="text" name="cashierBills.startTime" value="${costBaseBean.startTime}" placeholder=""/>
                </p>
                <p>
                    <label for="">结束日期： </label>
                    <input type="text" name="cashierBills.endTime" value="${costBaseBean.endTime}" placeholder="" />
                </p>
            </div>
            <div class="clearfix">
                <section class="headNav" id = "billsTypeSection">
                    <ul>
                        <li><label for="">单据类别： </label></li>
                        <li>
                            <a href="javascript:nav('收入')"><img id="srdj"
                                                                  src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">收入</a>
                        </li>
                        <li>
                            <a href="javascript:nav('支出')"><img id="zcfb"
                                                                  src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">支出</a>
                        </li>
                    </ul>
                    <input type="text" id="billsType" name="cashierBills.billsType" value="${empty costBaseBean.billsType ? '收入' : costBaseBean.billsType}"
                               style="display: none;" />
                </section>
                <section class="iconNav">
                    <p><input type="text" value="" /></p>
                    <ul>
<%--                        <li><img class="icon" src="<%=basePath %>images/WFJClient/budget/scan.png"/></li>--%>
                        <li ><img class="icon" onclick="toAddItem();" src="<%=basePath %>images/WFJClient/pic_1.png"/></li>
                        <li ><img class="icon" onclick="toRemoveGoodsBills();" src="<%=basePath %>images/WFJClient/budget/ico_1_07.png"/></li>
                        <li><img class="icon" src="<%=basePath %>images/scMobile/payBudget/addBudget/img_4_13.png"/></li>
                    </ul>
                </section>
            </div>
        </section>

        <section class="sec_con2">
            <%--选择具体项目后显示项目详细信息--%>
            <c:if test="${!empty cacheGoodsMap}">
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
                    <c:forEach var='entity' items="${cacheGoodsMap}">
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
                    <input type="hidden" id="allPrice" name="cashierBills.priceSub" value="" />
                </div>
                <div class="right">
                    <p id="commit" onclick="toAdd();">提交</p>
                </div>
            </div>
        </section>
        <input type="hidden" id="head" name="head" value="${head}"/>
    </s:form>

    <!-- 项目分类 -->
    <div class="div-xiangmu">
        <div class="div-box">
            <div class="xiangmu-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        项目分类
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix xmfl">
            </div>
        </div>
    </div>
    <!-- 行业分类 -->
    <div class="div-hangye">
        <div class="div-box">
            <div class="hangye-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        行业分类
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix hyfl">
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
    <input type="hidden" name="costBaseBean.showFlag" value="${showFlag}"/><%--是否是选择全部false全部true单一部门--%>
    <input type="hidden" id="ttsw_hid_companyId" name="costBaseBean.companyId" value=""/><%--公司id--%>
    <input type="hidden" id="ttsw_hid_departmentID" name="costBaseBean.departmentID" value=""/><%--所选部门id--%>
    <input type="hidden" id="ttsw_hid_departmentName" name="costBaseBean.departmentName" value=""/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" id="ttsw_hid_dataDepotID" name="costBaseBean.dataDepotID" value=""/><%--所选仓库id--%>
    <input type="hidden" id="ttsw_hid_dataDepotName" name="costBaseBean.dataDepotName" value=""/><%--所选仓库名称--%>
    <input type="hidden" id="ttsw_hid_billId" name="costBaseBean.billId" value=""/><%--单据凭证号--%>
    <input type="hidden" id="ttsw_hid_companyName" name="costBaseBean.companyName" value=""/><%--公司名称--%>
    <input type="hidden" id="ttsw_hid_xmTypeName" name="costBaseBean.xmTypeName" value=""/><%--项目名称--%>
<%--    <input type="hidden" id="ttsw_hid_xmType" name="costBaseBean.xmType" value=""/>&lt;%&ndash;项目分类&ndash;%&gt;--%>
    <input type="hidden" id="ttsw_hid_xmType" name="costBaseBean.xmType" value=""/><%--项目类型--%>
    <input type="hidden" id="ttsw_hid_tradeId" name="costBaseBean.tradeId" value=""/><%--行业分类--%>
    <input type="hidden" id="ttsw_hid_tradeName" name="costBaseBean.tradeName" value=""/><%--行业类型--%>
    <input type="hidden" id="ttsw_hid_itemCode" name="costBaseBean.itemCode" value=""/><%--项目编号--%>
    <input type="hidden" id="ttsw_hid_itemId" name="costBaseBean.itemId" value=""/><%--项目id--%>
    <input type="hidden" id="ttsw_hid_staffFzrId" name="costBaseBean.staffFzrId" value=""/><%--负责人id--%>
    <input type="hidden" id="ttsw_hid_staffName" name="costBaseBean.staffName" value=""/><%--负责人名称--%>
    <input type="hidden" id="ttsw_hid_staffCode" name="costBaseBean.staffCode" value=""/><%--负责人编号--%>
    <input type="hidden" id="ttsw_hid_singleName" name="costBaseBean.singleName" value=""/><%--制单人名称--%>
    <input type="hidden" id="ttsw_hid_barcode" name="costBaseBean.barCode" value=""/><%--条形码--%>
    <input type="hidden" id="ttsw_hid_billsType" name="costBaseBean.billsType" value=""/><%--条形码--%>
    <input type="hidden" id="ttsw_hid_address" name="costBaseBean.address" value=""/><%--地址--%>
    <input type="hidden" id="ttsw_hid_coordinate" name="costBaseBean.coordinate" value=""/><%--坐标--%>
<%--    <input type="hidden" id="ttsw_hid_deptName" name="costBaseBean.departmentName" value=""/>&lt;%&ndash;部门名称&ndash;%&gt;--%>
    <input type="hidden" name="costBaseBean.identification" value="add"/><%--跳转页面标识--%>
</s:form>
</body>
<%--下拉控制js文件--%>
<script type="text/javascript" src="<%=basePath%>js/ea/budgetBill/costBudgetBillAdd.js"></script>
<%--调用安卓js文件--%>
<script type="text/javascript" src="<%=basePath %>js/ea/budgetBill/productslaunch.js"></script>
<script>
    function nav(selectId) {
        <%--$("#"+selectId).attr("src", "<%=basePath%>images/ea/office/contract/selectp/dxseleted.png");--%>
        if (selectId == "收入") {
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
        var result = toCheckNull(0);
        if (result == "true") {//验证通过
            $("#f2").submit();
            $("#commit").removeAttr("onclick");
        } else {
            alert(result);
            return false;
        }

    }

    //调用扫码枪获取货品信息
    function toAndroidCallcamera() {
        var result = toCheckNull(1);
        if (result == "true") {//验证通过
            //TODO:暂时注释，方便测试
            // Android.callcamera();
            calltiaoma('111');
        } else {
            alert(result);
            return false;
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
        if (tenantFlag!="personal" && (hid_companyName == null || hid_companyName == "")) {
            message = "请录入公司名称";
        }
        //项目名称
        // var hid_itemName = $("#project-name").val();
        // if (hid_itemName == null || hid_itemName == "") {
        //     message = "请选择项目名称";
        // }
        //项目分类
        var hid_itemType = $("#xiangmu").val();
        if (hid_itemType == null || hid_itemType == "") {
            message = "请选择项目分类";
        }
        var hid_hyfl = $("#hyfl").val();
        if (hid_hyfl == null || hid_hyfl == "") {
            message = "请选择行业分类";
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
        window.location.href = basePath + "ea/scBudget/ea_toCostBudgetItemUpdate.jspa?keyNum=" + goodsBillsID + "&editFlag=add&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}&menuId="+menuId+"&billsType="+billsType+"&head="+head;
    }

    //根据id删除以保存数据
    function toRemoveGoodsBills() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id){
            var url = "ea_removeGoodsBillOfAdd.jspa?keyNum=" + id+"&editFlag=add&menuId="+menuId+"&billsType="+billsType+"&head="+head;
            $("#f2").attr('action',url);    //通过jquery为action属性赋值
            $("#f2").submit();    //提交ID为myform的表单
        }

        // document.location.replace(basePath + "ea/scBudget/ea_removeGoodsBillOfAdd.jspa?keyNum=" + goodsBillsId+"&editFlag=add&cashierBillsId="+cashierBillsId+"&menuId="+menuId);
    }

    //后退
    function toBack() {
        var menuName = $("#ttsw_dep_n_name").val();
        if(!menuName){
            menuName = $("#ttsw_dep_y_name").val();
        }
        window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillList.jspa?menuId="+menuId+"&menuName="+menuName+"&billsType="+billsType+"&head="+head;
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
