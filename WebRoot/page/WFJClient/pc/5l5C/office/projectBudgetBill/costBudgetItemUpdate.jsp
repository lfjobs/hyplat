<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");
%>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pm_base.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetItemAdd.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/diyUpload.js"></script>
<%--    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/font-size.js"></script>--%>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/ea/projectBudgetBill/costBudgetItemUpdate.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectWldw.css"/>
    <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <title>项目订单明细修改</title>
    <style>
        .jijia {
            display: none;
        }

        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
        }

        #prompt {
            width: 100%!important;
            z-index: 1001!important;
            top: 453.04px!important;
            position: absolute;
            display: none;
        }
        .div-tianjia .tianjia-content .li-03 {
            border-bottom: 0.025rem solid #efefef;
            margin: 3px 0.5rem;
        }
        .div-tianjia .tianjia-content .li-03 p {
            float: left;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: 0.6rem;
            color: #252525;
        }
        .li-03 .headNav {
            /*height:2.4rem;*/
        }

        .li-03 .headNav ul li {
            text-align: right;
            width: 24%;
            float: right;
            height: 1.5rem;
            line-height: 1.5rem;
            color: #222;
            font-size: .6rem;
            padding-right: .5rem;
        }

        .li-03 .headNav ul li a img {
            width: 1.2rem;
            margin-right: 0.1rem;
            vertical-align: middle;
        }

        .tianjia-content ul li input {
            width: 60%!important;
            width: 45%;
            background-color: transparent;
            float: right;
            text-align: right;
            line-height: 1.5rem;
            font-size: .6rem;
            color: #222;
            border: 0;
            padding-right: .5rem;
        }

        ul li p span {
            display: inline-block;
            width: .25rem;
            color: #d60000;
        }

    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
        var staffID = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";
        var ppid = "${param.ppId}";
        var colorvalue = '';
        var attriJson = '${costAddBean.attriJson}';
        console.log(attriJson);
        var amap;
        if(attriJson){
            amap   = eval("(" + attriJson + ")");
        }

        // var amap = JSON.parse(attriMap);
        // console.log(amap.长);
        var editFlag = "${editFlag}";
        console.log("标记2："+editFlag);
        var cashierBillsId = "${cashierBillsId}";
        var goodsBillsID = "${costAddBean.goodsBillsID != null ? costAddBean.goodsBillsID : keyNum}";
        var goodsName = "";
        var goodsId = "";
        var barCode = "";
        var menuId = "ng";
        var typeId = "";
        var typeName = "";
        var billsType = "采购单";
        var searchParams = new URLSearchParams(window.location.search);
        var params = new Map();
        searchParams.forEach(function(value, key) {
            params.set(key,value);
        });
        if(params.size > 0){
            if(null != params.get("billsType")){
                billsType = params.get("billsType");
            }
            if(null != params.get("goodsName")){
                goodsName = params.get("goodsName");
            }
            if(null != params.get("goodsId")){
                goodsId = params.get("goodsId");
            }
            if(null != params.get("barCode")){
                barCode = params.get("barCode");
            }
            if(null != params.get("menuId")){
                menuId = params.get("menuId");
            }
            if(null != params.get("typeId")){
                typeId = params.get("typeId");
            }
            if(null != params.get("typeName")){
                typeName = params.get("typeName");
            }
        }
    </script>
</head>
<body>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">
    <!-- 添加物品 -->
    <div class="div-tianjia">
        <div class="tianjia-header">
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </a>
                </li>
                <li>
                    ${billsType}明细项修改
                </li>
                <li id="isupdate" style="display: none;">
                    ${billsType}明细项修改
                </li>
            </ul>
        </div>

        <div class="tianjia-content">
            <%--隐藏域传值用--%>
            <input type="hidden" id="cashierBillsId" name="cashierBillsId" value="${cashierBillsId}"/><%--已添加预算单id（修改用）--%>
            <input type="hidden" name="costAddBean.tzFlag" value="true"/><%--跳转标识--%>
            <%--            <input type="hidden" name="costAddBean.goodsId" value="${costAddBean.goodsId}"/>--%>
            <%--后退传值用--%>
            <input type="hidden" name="delGoodsBillsIds" value="${delGoodsBillsIds}"/><%--修改页面删除已保存的货物id数组--%>
            <input type="hidden" name="showFlag" value="${costAddBean.showFlag}"/><%--是否是选择全部false全部true单一部门--%>
            <input type="hidden" id="editFlag" name="editFlag" value="${editFlag}">
            <input type="hidden" name="departmentID" value="${costAddBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="departmentName" value="${costAddBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <%--前一页面传过来的数据--%>
            <input type="hidden" name="costAddBean.companyId" value="${costAddBean.companyId}"/><%--公司id--%>
            <input type="hidden" name="costAddBean.departmentID" value="${costAddBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="costAddBean.departmentName" value="${costAddBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <input type="hidden" name="costAddBean.billId" value="${costAddBean.billId}"/><%--单据凭证号--%>
            <input type="hidden" name="costAddBean.companyName" value="${costAddBean.companyName}"/><%--公司名称--%>
            <input type="hidden" name="costAddBean.itemName" value="${costAddBean.itemName}"/><%--项目名称--%>
            <input type="hidden" name="costAddBean.itemType" value="${costAddBean.itemType}"/><%--项目分类--%>
            <input type="hidden" name="costAddBean.xmType" value="${costAddBean.xmType}"/><%--项目类型--%>
            <input type="hidden" name="costAddBean.itemCode" value="${costAddBean.itemCode}"/><%--项目编号--%>
            <input type="hidden" name="costAddBean.itemId" value="${costAddBean.itemId}"/><%--项目id--%>
            <input type="hidden" name="costAddBean.staffFzrId" value="${costAddBean.staffFzrId}"/><%--负责人id--%>
            <input type="hidden" name="costAddBean.staffName" value="${costAddBean.staffName}"/><%--负责人名称--%>
            <input type="hidden" name="costAddBean.staffCode" value="${costAddBean.staffCode}"/><%--负责人编号--%>
            <input type="hidden" name="costAddBean.singleName" value="${costAddBean.singleName}"/><%--制单人名称--%>
            <input type="hidden" name="costAddBean.barcode" value="${costAddBean.barCode}"/><%--条形码--%>

            <input type="hidden" id="companyID" name="costAddBean.companyID" value="${costAddBean.companyId}"/>
            <input type="hidden" id="goodsKey" name="costAddBean.ppid" value="${costAddBean.ppid }"/>
            <input type="hidden" id="goodsID" name="costAddBean.goodsId" value="${costAddBean.goodsId }"/>
            <input type="hidden" id="sizevalue" name="costAddBean.sizevalue" value="${costAddBean.sizevalue }"/>
            <input type="hidden" id="colorvalue" name="costAddBean.colorvalue" value="${costAddBean.colorvalue }"/>
            <input type="hidden" id="attrinames" name="costAddBean.attrinames" value="${costAddBean.attrinames}">
            <input type="hidden" id="attrinamec" name="costAddBean.attrinamec" value="${costAddBean.attrinamec}">
            <input type="hidden" id="attri" name="costAddBean.attri" value="${costAddBean.attri}">
            <input type="hidden" id="ImagesPath" name="costAddBean.photoStr" value="${costAddBean.photoStr}">
            <input type="hidden" id="VideoPath" name="costAddBean.videoStr" value="${costAddBean.videoStr}">
            <input type="hidden" name="costAddBean.videoList" value="${costAddBean.videoList}"/>
            <input type="hidden" name="costAddBean.photoList" value="${costAddBean.photoList}"/>
            <input type="hidden" id="isScale" name="costAddBean.isScale" value="${costAddBean.isScale}">
            <input type="hidden" id="goodsBillsID" name="costAddBean.goodsBillsID" value="${costAddBean.goodsBillsID}">
            <input type="hidden" id="unitOfMeasureCode" name="costAddBean.unitofmeasurecode"
                   value="${costAddBean.unitofmeasurecode}"/>
            <input type="hidden" id="goodsPurpose" name="costAddBean.goodsPurpose" value="${costAddBean.goodsPurpose}">
            <input type="hidden" id="goodsPurposeId" name="costAddBean.goodsPurposeId" value="${costAddBean.goodsPurposeId}">
            <input type="hidden" name="commitFlag" value="back">
            <input type="hidden" name="costAddBean.logoPath" value="${costAddBean.logoPath}"/>
            <input type="hidden" name="costAddBean.goodsBillsExtId" value="${costAddBean.goodsBillsExtId}"/>

            <%--            <input type="hidden" name="cashierBillsId" value="${cashierBillsId}"/>--%>
            <ul class="clearfix">
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">往来个人</p>
                    <input type="text" id="connectName" name="costAddBean.connectName" value="${costAddBean.connectName}"  placeholder="请输入" />
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">开始日期</p>
                    <input type="text" id="serviceStartDate" name="costAddBean.serviceStartDate" value="${costAddBean.serviceStartDate}" placeholder="请输入" />
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">结束日期</p>
                    <input type="text" id="serviceEndDate" name="costAddBean.serviceEndDate" value="${costAddBean.serviceEndDate}" placeholder="请输入" />
                </li>
                <li class="li-01 chengzhong1 clearfix">
                    <p for=""><span>*</span>云计称重：</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 chengzhong2 clearfix">
                    <p for=""><span>*</span>云计称重：</p>
                    <input type="text" value="" required readonly name="" id="jjinput">
                </li>
                <li class="li-01 jijia1 clearfix jijia">
                    <p for="">计价单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 jijia2 clearfix jijia">
                    <p for="">计价单位</p>
                    <input type="text" value="${costAddBean.unitofmeasurecode }" required readonly class="">
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>物品名称</p>
                    <input type="text" id="goodsName" name="costAddBean.goodsName" required value="${costAddBean.goodsName}" onclick="searchGoods();" readonly />
                </li>
                <li class="li-01 tiaoma1 clearfix">
                    <p for=""><span>*</span>物品条码</p>
                    <input type="text" id="barCode" name="costAddBean.barCode" required value="${costAddBean.barCode}" readonly />
                </li>
                <li class="li-01 xiangmu1 clearfix">
                    <p for=""><span>*</span>项目分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 xiangmu2 clearfix">
                    <p for=""><span>*</span>项目分类</p>
                    <input type="text" value="${costAddBean.producttype }" required readonly name="costAddBean.producttype"
                           id="producttype">
                    <input type="hidden" value="${costAddBean.productCode }" name="costAddBean.productCode"
                           id="productCode">
                </li>
                <li class="li-01 hangye1 clearfix">
                    <p for=""><span>*</span>行业分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 hangye2 clearfix">
                    <p for=""><span>*</span>行业分类</p>
                    <input type="text" value="${costAddBean.tradeName }" required name="costAddBean.tradeName" id="tradeName"/>
                    <input type="hidden" value="${costAddBean.tradeID }" name="costAddBean.tradeID" id="tradeID"/>
                    <input type="hidden" value="${costAddBean.tradeCode }" readonly id="tradeCode" name="costAddBean.tradeCode"/>
                </li>
                <li class="li-01 wupin1 clearfix">
                    <p for=""><span>*</span>物品类别</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wupin2 clearfix">
                    <p for=""><span>*</span>物品类别</p>
                    <input type="text" value="${costAddBean.typeID }" required readonly name="costAddBean.typeID" id="typeID"/>
                </li>
                <li class="li-01 tupian1 clearfix">
                    <p>图片</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 tupian2 clearfix">
                    <p>图片</p>
                    <div></div>
                </li>
                <li class="li-01 shipin1 clearfix">
                    <p>视频</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 shipin2 clearfix">
                    <p>视频</p>
                    <div></div>
                </li>
                <li class="li-01 danwei1 clearfix">
                    <p for=""><span>*</span>单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 danwei2 clearfix">
                    <p for=""><span>*</span>单位</p>
                    <input type="text" value="${costAddBean.variableId}" required readonly name="costAddBean.variableId"
                           id="variableID">
                    <input type="hidden" value="${costAddBean.specsParentId}" readonly id="specsId">
                </li>
                <li class="li-01 guige1 clearfix">
                    <p for=""><span></span>规格</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 guige2 clearfix">
                    <p for=""><span></span>规格</p>
                    <input type="text" value="${costAddBean.guigeTypeValue}" required readonly name="costAddBean.guigeTypeValue"
                           id="guigeTypeValue">
                    <input type="hidden" value="${costAddBean.guigeType}" readonly name="costAddBean.guigeType"
                           id="guigeType">
                    <input type="hidden" value="${costAddBean.guigeTypeId}" readonly name="costAddBean.guigeTypeId"
                           id="guigeTypeId" type="hidden">
                    <input type="hidden" value="${costAddBean.specsParentId}" readonly name="costAddBean.specsParentId"
                           id="specsParentId" type="hidden">
                    <input type="hidden" readonly id="unitOld"/>
                    <%--                    <div>--%>
                    <%--                        <img src="img/img_03.png" alt="">--%>
                    <%--                        <p>已编辑</p>--%>
                    <%--                    </div>--%>
                </li>
                <li class="li-01 pinpai1 clearfix">
                    <p>品牌管理</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 pinpai2 clearfix">
                    <p>品牌管理</p>
                    <input type="text" value="${costAddBean.brand}" readonly name="costAddBean.brand" id="brand"/>
                </li>
                <li class="li-01 dengji1 clearfix">
                    <p>等级管理</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 dengji2 clearfix">
                    <p>等级管理</p>
                    <input type="text" readonly value="${costAddBean.gradeName }" name="costAddBean.gradeName"
                           id="gradeName">
                    <input type="hidden" value="${costAddBean.gradeid }" name="costAddBean.gradeid" id="gradeid">
                </li>
                <%--                <li class="li-01 mingcheng1 clearfix">--%>
                <%--                    <p>库存</p>--%>
                <%--                    <input type="text" id="invInvenQuantity" name="costAddBean.invInvenQuantity" value="${costAddBean.invInvenQuantity}"  readonly />--%>
                <%--                </li>--%>
                <li class="li-01 detailed1 clearfix">
                    <p>宝贝详情</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 detailed2 clearfix">
                    <p>宝贝详情</p>
                    <div>
                        <img src="img/img_03.png" alt="">
                        <p>已编辑</p>
                    </div>
                </li>
                <%--                <li class="li-01 mingcheng1 clearfix">--%>
                <%--                    <p>调查价</p>--%>
                <%--                    <input type="text" id="price" name="costAddBean.price" value="${costAddBean.budgetMoney}"  placeholder="请输入" />--%>
                <%--                </li>--%>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>数量</p>
                    <input type="text" id="budgetNumber" onchange="computeAmount()" required name="costAddBean.budgetNumber" value="${costAddBean.budgetNumber}"  placeholder="请输入" />
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>单价</p>
                    <input type="text" id="unitPrice" onchange="computeAmount()" required name="costAddBean.unitPrice" value="${costAddBean.unitPrice}"  placeholder="请输入" />
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>初始余额</p>
                    <input type="text" id="initialBalance" name="costAddBean.initialBalance" value="${costAddBean.initialBalance}"  readonly/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>金额</p>
                    <input type="text" id="amount" name="costAddBean.amount" value="${costAddBean.amount}" placeholder="根据规格、数量、单价自动计算"   readonly/>
                    <input type="hidden" id="budgetAmount" name="costAddBean.budgetAmount" value="${costAddBean.budgetAmount}" onchange="budgetAmountChg()" placeholder="请输入" />
                </li>
                <li class="li-03 loanDirection clearfix">
                    <p for=""><span>*</span>收付方向</p>
                    <section class="headNav">
                        <ul>
                            <li><a href="javascript:nav('付')"><img id="loan"
                                                                    src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">付</a>
                            </li>
                            <li><a href="javascript:nav('收')"><img id="borrow"
                                                                    src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">收</a>
                            </li>
                            <input type="text" id="loanDirection" name="costAddBean.loanDirection" value="${empty costAddBean.loanDirection ? '收' :costAddBean.loanDirection}"
                                   style="display: none;"/><%--所选项目类型--%>
                        </ul>
                    </section>
                </li>
                <li class="li-01 budgetAmount clearfix">
                    <p>收方金额</p>
                    <input type="text" id="borrowAmount" name="costAddBean.borrowAmount" placeholder="自动计算" value="${costAddBean.borrowAmount}"  readonly />
                </li>
                <li class="li-01 budgetAmount clearfix">
                    <p>付方金额</p>
                    <input type="text" id="loanAmount" name="costAddBean.loanAmount" placeholder="自动计算" value="${costAddBean.loanAmount}"  readonly />
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for=""><span>*</span>余额</p>
                    <input type="text" id="balance" name="costAddBean.balance" placeholder="自动计算" value="${costAddBean.balance}"  readonly/>
                </li>
                <li class="li-01 wldw1 clearfix">
                    <p for=""><span>*</span>收方名称</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wldw2 clearfix">
                    <p for=""><span>*</span>收方名称</p>
                    <input type="text" id="accountShow" name="costAddBean.accountShow" required value="${costAddBean.accountShow}" readonly/>
                    <input type="hidden" value="${costAddBean.accountFlag}" readonly name="costAddBean.accountFlag" id="accountFlag">
                </li>

                <li class="li-01 wldw3 clearfix">
                    <p for=""><span>*</span>付方名称</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wldw4 clearfix">
                    <p for=""><span>*</span>付方名称</p>
                    <input type="text" id="accountShowFrom" name="costAddBean.accountShowFrom" required value="${costAddBean.accountShowFrom}" readonly/>
                    <input type="hidden" value="${costAddBean.accountFlagFrom}" readonly name="costAddBean.accountFlagFrom" id="accountFlagFrom">
                </li>
                <%--                <li class="li-01 budgetAmount clearfix">--%>
                <%--                    <p>预算金额</p>--%>
                <%--                    <input type="text" id="budgetAmount" name="costAddBean.budgetAmount" value="${costAddBean.budgetAmount}" onchange="budgetAmountChg()" placeholder="请输入" />--%>
                <%--                </li>--%>
            </ul>
        </div>
        <div class="div-tijiao" onclick="toLaunchOrcang()" >
            修改
        </div>
    </div>
    <!-- 云计称重 -->
    <div class="div-chengzhong">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    云计称重
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix">
                <p class="active">是</p>
                <p>否</p>
            </div>
            <%--            <div class="div-bottom">--%>
            <%--                <p>--%>
            <%--                    确定--%>
            <%--                </p>--%>
            <%--            </div>--%>
        </div>
    </div>
    <!-- 计价单位 -->
    <div class="div-jijia">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    计价单位
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix">
                <p class="active">KGM(以重计价)</p>
                <p>PCS(以数计价)</p>
            </div>
            <%--            <div class="div-bottom">--%>
            <%--                <p>--%>
            <%--                    确定--%>
            <%--                </p>--%>
            <%--            </div>--%>
        </div>
    </div>
    <!-- 物品条码 -->
    <div class="div-tiaoma">
        <div class="div-box">
            <div class="tiaoma-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="img/img_01.png" alt="">
                    </li>
                    <li>
                        <!-- 物品条码 -->
                    </li>
                    <li>
                        <!-- <img src="img/img_02.png" alt=""> -->
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <img src="img/img_06.png" alt="">
            </div>
        </div>
    </div>
    <!-- 物品名称 -->
    <div class="div-mingcheng">
        <div class="div-box">
            <div class="mingcheng-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        物品名称
                    </li>
                    <li class="keep">
                        保存
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <div>
                    <input type="text" value="" placeholder="请输入物品名称或者条码" id="input-mingcheng">
                    <p>test</p>
                    <p>test</p>
                    <p>test</p>
                    <p>test</p>
                </div>
            </div>
        </div>
    </div>
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
                <%--<ul class="ul-left">
                    <li>
                        产成品
                    </li>
                    <li class="active">
                        半成品
                    </li>
                    <li>
                        零部件
                    </li>
                    <li>
                        原材料
                    </li>
                    <li>
                        辅料
                    </li>
                    <li>
                        费用类
                    </li>
                    <li>
                        固定资产
                    </li>
                    <li>
                        项目管理
                    </li>
                    <li>
                        其他
                    </li>
                    <li>
                        公司网站
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix ul-02">
                        <li>科一</li>
                        <li>科一</li>
                        <li>科一</li>
                    </ul>
                    <ul class="clearfix ul-03">
                        <li>科二</li>
                        <li>科二</li>
                        <li>科二</li>
                    </ul>
                    <ul class="clearfix ul-04">
                        <li>科三</li>
                        <li>科三</li>
                        <li>科三</li>
                    </ul>
                </div>--%>
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
                <%--<ul class="ul-left">
                    <li>
                        A101机械工业加工
                    </li>
                    <li class="active">
                        B102汽车交通工具
                    </li>
                    <li>
                        C103软件网络计算机
                    </li>
                    <li>
                        D104军事船舶制造
                    </li>
                    <li>
                        E105航空航天科技
                    </li>
                    <li>
                        B102汽车交通工具
                    </li>
                    <li>
                        C103软件网络计算机
                    </li>
                    <li>
                        D104军事船舶制造
                    </li>
                    <li>
                        E105航空航天科技
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix">
                        <li>汽车驾校</li>
                        <li>汽车美容</li>
                        <li>汽车加油</li>
                        <li>汽车维修</li>
                        <li>汽车保险</li>
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
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
                <%--<ul class="ul-left">
                    <li>
                        产成品
                    </li>
                    <li class="active">
                        半成品
                    </li>
                    <li>
                        零部件
                    </li>
                    <li>
                        原材料
                    </li>
                    <li>
                        辅料
                    </li>
                    <li>
                        费用类
                    </li>
                    <li>
                        固定资产
                    </li>
                    <li>
                        项目管理
                    </li>
                    <li>
                        其他
                    </li>
                    <li>
                        公司网站
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix">
                        <li>科一</li>
                        <li>科二</li>
                        <li>科三</li>
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
    <!-- 首页图片 -->
    <div class="div-tupian2" style="opacity: 0; transform: translate(1000000px);">
        <div class="div-box">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    图片
                </li>
                <li class="p-tijiao">
                    保存
                </li>
            </ul>
        </div>
        <div class="div-con">
            <div class="div-tab">
                <div class="div-01">
                    <div class="demo">
                        <div id="as"></div>
                        <c:if test="${fn:length(costAddBean.photoList)>0 }">
                            <span style="display:none;" id="arrilist">${fn:length(costAddBean.photoList) }</span>
                            <div class="parentFileBox" style="width: 360px;">
                                <ul class="fileBoxUl">
                                    <c:forEach items="${costAddBean.photoList }" var="entity" varStatus="status">
                                        <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                            <div class="viewThumb">
                                                <img src="<%=basePath %>${entity.imgurl}">
                                            </div>
                                            <div class="diyCancel" onclick="removeExist('fileBox_WU_FILE_0_${status.index }')"></div>
                                            <div class="diySuccess" style="display: block;"></div>
                                            <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                            <div class="diyFilePath" style="display: none;">${entity.imgurl}</div>
                                            <div class="diyBar" style="display: none;">
                                                <div class="diyProgress" style="width: 100%;"></div>
                                                <div class="diyProgressText">上传完成</div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 首页视频 -->
    <div class="div-shipin2" style="opacity: 0; transform: translate(1000000px);">
        <div class="div-box">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    视频
                </li>
                <li class="p-tijiao">
                    保存
                </li>
            </ul>
        </div>
        <div class="div-con">
            <div class="div-tab">
                <div class="div-01">
                    <div class="demo">
                        <div id="ass"></div>
                        <c:if test="${fn:length(costAddBean.videoList)>0 }">
                            <span style="display:none;" id="arrvlist">${fn:length(costAddBean.videoList) }</span>
                            <div class="parentFileBox" style="width: 360px;">
                                <ul class="fileBoxUl">
                                    <c:forEach items="${costAddBean.videoList }" var="entity" varStatus="status">
                                        <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                            <div class="viewThumb">
                                                <video src="<%=basePath %>${entity.imgurl}" />
                                            </div>
                                            <div class="diySuccess" style="display: block;"></div>
                                            <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                            <div class="diyFilePath" style="display: none;">${entity.imgurl}</div>
                                            <div class="diyCancel" onclick="removeVideo('fileBox_WU_FILE_0_${status.index }')"></div>
                                            <div class="diyBar" style="display: none;">
                                                <div class="diyProgress" style="width: 100%;"></div>
                                                <div class="diyProgressText">上传完成</div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 品牌管理 -->
    <div class="div-pinpai">
        <div class="div-box">
            <div class="pinpai-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        品牌管理
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <p>
                    <label for="">品牌名称</label>
                    <input type="text" value="${costAddBean.brand }" class="pinpaival"
                           placeholder="请输入品牌名称">
                </p>
                <p>
                    <label for="">品牌logo</label>
                    <input type="text" placeholder="上传品牌logo">
                </p>
                <div id="div-img3">
                    <input type="file" name="fileLogo" id="sdfFile3" onchange="f_change3(this);"
                           accept="image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp">
                    <img alt="" src="<%=basePath%>${costAddBean.logoPath }" id="imgSdf3">
                </div>
            </div>
            <div class="div-bottom">
                <p>
                    提交
                </p>
            </div>
        </div>
    </div>
    <!-- 单位 -->
    <div class="div-danwei">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    单位
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix dwgl">
                <%--<p class="active">地槽</p>
                <p>平方</p>
                <p>盒</p>
                <p>米</p>
                <p>张</p>
                <p>方</p>
                <p>圈</p>
                <p>个</p>
                <p>台</p>
                <p>升</p>
                <p>瓶</p>
                <p>袋</p>
                <p>克</p>
                <p>辆</p>
                <p>颗</p>
                <p>斤</p>
                <p>千克</p>
                <p>棵</p>
                <p>件</p>--%>
            </div>
            <%--            <div class="div-bottom">--%>
            <%--                <p>--%>
            <%--                    提交--%>
            <%--                </p>--%>
            <%--            </div>--%>
        </div>
    </div>
    <!-- 规格 -->
    <%--    <div class="div-guige div-guige1">--%>
    <%--        <div class="div-box">--%>
    <%--            <div class="guige-header">--%>
    <%--                <ul class="clearfix">--%>
    <%--                    <li class="div-close">--%>
    <%--                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        产品规格--%>
    <%--                    </li>--%>
    <%--                    <li class="keep">--%>
    <%--                        保存--%>
    <%--                    </li>--%>
    <%--                </ul>--%>
    <%--            </div>--%>
    <%--            <section class="color">--%>
    <%--                <input type="text" class="sezi_title" value="颜色">--%>
    <%--                <div class="inpAdd clearfix"></div>--%>
    <%--                <p class="add-input">自定义</p>--%>
    <%--                <ul class="clearfix size_old">--%>
    <%--                    <li>--%>
    <%--                        白色--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        粉色--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        米黄色--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        蓝色--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        紫色--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        黑色--%>
    <%--                    </li>--%>
    <%--                </ul>--%>
    <%--            </section>--%>
    <%--            <section class="size">--%>
    <%--                <input type="text" class="sezi_title" value="尺码">--%>
    <%--                <div class="inpAdd clearfix"></div>--%>
    <%--                <p class="add-input">自定义</p>--%>
    <%--                <ul class="clearfix size_old">--%>
    <%--                    <li>--%>
    <%--                        S--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        M--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        L--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        XL--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        XXL--%>
    <%--                    </li>--%>
    <%--                    <li>--%>
    <%--                        XXXL--%>
    <%--                    </li>--%>
    <%--                </ul>--%>
    <%--            </section>--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <!--新版本规格-->
    <div class="div-guige div-guige-new">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix ggn1">
                    <p>规格类型</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="clearfix ggn2" style="display: none;">
                    <p>规格类型</p>
                    <input type="text" readonly value="${costAddBean.guigeType}" class="guigeTypeNew" code="" parentId="" id="guigeTypeNew">
                </li>
            </ul>
            <ul class="ul-con ggn" id="ggn">
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!--规格：长度-->
    <div class="div-guige div-guige-changdu">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>长</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="长-">
                    <p>m</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!--规格：面积-->
    <div class="div-guige div-guige-mianji">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>长</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="长-">
                    <p>m</p>
                </li>
                <li class="clearfix">
                    <p>宽</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="宽-">

                    <p>m</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!--规格：立方-->
    <div class="div-guige div-guige-lifang">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>长</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="长-">
                    <p>m</p>
                </li>
                <li class="clearfix">
                    <p>宽</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="宽-">

                    <p>m</p>
                </li>
                <li class="clearfix">
                    <p>高</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="高-">

                    <p>m</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 规格：重量 -->
    <div class="div-guige div-guige-zhongliang">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>重量</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="重量-">
                    <p>kg</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 规格：时间 -->
    <div class="div-guige div-guige-shijian">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>时间</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="时间-">
                    <p>s</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 规格：个数 -->
    <div class="div-guige div-guige-geshu">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>个数</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="个数-">
                    <p>个</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 规格：比例 -->
    <div class="div-guige div-guige-bili">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>比例</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="比例-">
                    <p>%</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 规格：件 -->
    <div class="div-guige div-guige-jian">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>数量</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="件-">
                    <p>件</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 等级管理 -->
    <div class="div-dengji">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    等级名称
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-con djgl">
                <%--<ul>
                    <li>一级</li>
                    <li>二级</li>
                    <li>三级</li>
                    <li>四级</li>
                    <li>五级</li>
                </ul>--%>
            </div>
        </div>
    </div>
    <!-- 规格类型管理 -->
    <div class="div-guigeType">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    规格类型
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-con guigeType">
            </div>
        </div>
    </div>
    <!-- 往来关系管理 -->
    <div class="div-accountType">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    往来关系
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-con accountType">
                <ul>
                    <li atype="company">单位</li>
                    <li atype="personal">个人</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- 往来关系管理(付) -->
    <div class="div-accountTypeFrom">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    往来关系
                </p>
                <div class="div-close">
                    <img src="<%=basePath %>images/BuildPlatform/close_ico_b.png" alt="">
                </div>
            </div>
            <div class="div-con accountType">
                <ul>
                    <li atype="company">单位</li>
                    <li atype="personal">个人</li>
                </ul>
            </div>
        </div>
    </div>
    <!--往来关系：公司-->
    <div class="div-account div-account-company">
        <header>
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    往来单位
                </li>
            </ul>
        </header>
        <div class="company-content">
            <section class="sec-search">
                <div class="div-name">
                    <label for="">往来公司</label>
                    <input type="text"  placeholder="请填写往来公司"  id="sjrgs" autocomplete="off" value="${costAddBean.accountName }" name="costAddBean.accountName"/>
                    <input type="hidden"  id="comid" value="${costAddBean.accountNameId }" name="costAddBean.accountNameId"/>
                </div>
                <%--                <div class="div-name">--%>
                <%--                    <label for="">收件人</label>--%>
                <%--                    <input type="text"  placeholder="请填写收件人姓名或者手机号"  id="sjr" autocomplete="off"/>--%>
                <%--                    <input type="hidden"   id="staffid" value="${costAddBean.accountNameId }" name="costAddBean.accountNameId"/>--%>
                <%--                    <input type="hidden"   id="orgid"/>--%>
                <%--                    <input type="hidden"   id="staffname" value="${costAddBean.accountName }" name="costAddBean.accountName"/>--%>
                <%--                </div>--%>
<%--                <div class="div-name">--%>
<%--                    <label for="">联系人</label>--%>
<%--                    <input type="text"  placeholder="请填写联系人"  id="wlgrFromC1" autocomplete="off" value="${costAddBean.accountNameFromP }" name="costAddBean.accountNameFromP"/>--%>
<%--                    <input type="hidden"  id="wlgrIdFromC1" value="${costAddBean.accountNameIdFromP }" name="costAddBean.accountNameIdFromP"/>--%>
<%--                </div>--%>
                <div class="div-name">
                    <label for="">手机号</label>
                    <input type="text"  id="accountPhone" placeholder="请填写公司对公账户联系方式" value="${costAddBean.accountPhone == 'null' ? '' : costAddBean.accountPhone }" name="costAddBean.accountPhone"/>
                </div>
                <div class="div-name">
                    <label for="">账号</label>
                    <input type="text"  id="accountNum" placeholder="请填写公司对公账户号" value="${costAddBean.accountNum }" name="costAddBean.accountNum"/>
                </div>
                <div class="div-name">
                    <label for="">开户行</label>
                    <input type="text"  id="openBank" placeholder="请填写开户行" value="${costAddBean.openBank }" name="costAddBean.openBank"/>
                </div>

            </section>

            <section class="sec-ul">
                <p class="title-p">请在以下搜索结果进行选择</p>
                <ul class="ul-list">

                </ul>
            </section>

            <p class="navrecent">以下是最近联系人：</p>
            <section class="sec-ul2">
                <ul class="ul-list2">
                </ul>
            </section>
            <section class="sec-bottom">
                <p>
                    提交
                </p>
            </section>
        </div>
    </div>
    <!--往来关系：个人-->
    <div class="div-account div-account-personal">
        <header>
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    往来个人
                </li>
            </ul>
        </header>
        <div class="personal-content">
            <section class="sec-search">
                <div class="div-name">
                    <label for="">往来个人</label>
                    <input type="text"  placeholder="请填写往来个人"  id="wlgr" autocomplete="off" value="${costAddBean.accountNameP }" name="costAddBean.accountNameP"/>
                    <input type="hidden"  id="wlgrId" value="${costAddBean.accountNameIdP }" name="costAddBean.accountNameIdP"/>
                </div>
                <div class="div-name">
                    <label for="">手机号</label>
                    <input type="text"  id="accountPhoneP" placeholder="请填写公司对公账户联系方式" value="${costAddBean.accountPhoneP }" name="costAddBean.accountPhoneP"/>
                </div>
                <div class="div-name">
                    <label for="">账号</label>
                    <input type="text"  id="accountNumP" placeholder="请填写公司对公账户号" value="${costAddBean.accountNumP }" name="costAddBean.accountNumP"/>
                </div>
                <div class="div-name">
                    <label for="">开户行</label>
                    <input type="text"  id="openBankP" placeholder="请填写开户行" value="${costAddBean.openBankP }" name="costAddBean.openBankP"/>
                </div>
                <%--                <div class="box clearfix">--%>
                <%--                    <label for="">--%>
                <%--                        <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>--%>
                <%--                    </label>--%>
                <%--                    <input type="text" name="" id="search" placeholder="请输入姓名或者手机号" />--%>
                <%--                </div>--%>
                <%--                <div><input type="button" name="" id="qsearch" value="搜索" /></div>--%>
            </section>
            <section class="sec-ul">
                <p class="title-p">请在以下搜索结果进行选择</p>
                <ul class="ul-list">

                </ul>
            </section>
            <p class="resulttip">该用户尚未注册</p>
            <p class="navrecent">以下是最近联系人：</p>
            <section class="sec-ul2">
                <ul class="ul-list2">

                </ul>
            </section>
            <section class="sec-bottom">
                <p>
                    提交
                </p>
            </section>
        </div>
    </div>
    <!--往来关系：公司（付）-->
    <div class="div-account div-account-companyFrom">
        <header>
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    往来单位
                </li>
            </ul>
        </header>
        <div class="company-content">
            <section class="sec-search">
                <div class="div-name">
                    <label for="">往来公司</label>
                    <input type="text"  placeholder="请填写往来公司"  id="sjrgsFrom" autocomplete="off" value="${costAddBean.accountNameFrom }" name="costAddBean.accountNameFrom"/>
                    <input type="hidden"  id="comidFrom" value="${costAddBean.accountNameIdFrom }" name="costAddBean.accountNameIdFrom"/>
                </div>
                <%--                <div class="div-name">--%>
                <%--                    <label for="">收件人</label>--%>
                <%--                    <input type="text"  placeholder="请填写收件人姓名或者手机号"  id="sjr" autocomplete="off"/>--%>
                <%--                    <input type="hidden"   id="staffid" value="${costAddBean.accountNameId }" name="costAddBean.accountNameId"/>--%>
                <%--                    <input type="hidden"   id="orgid"/>--%>
                <%--                    <input type="hidden"   id="staffname" value="${costAddBean.accountName }" name="costAddBean.accountName"/>--%>
                <%--                </div>--%>
<%--                <div class="div-name">--%>
<%--                    <label for="">联系人</label>--%>
<%--                    <input type="text"  placeholder="请填写联系人"  id="wlgrFromC" autocomplete="off" value="${costAddBean.accountNameFromP }" name="costAddBean.accountNameFromP"/>--%>
<%--                    <input type="hidden"  id="wlgrIdFromC" value="${costAddBean.accountNameIdFromP }" name="costAddBean.accountNameIdFromP"/>--%>
<%--                </div>--%>
                <div class="div-name">
                    <label for="">手机号</label>
                    <input type="text"  id="accountPhoneFrom" placeholder="请填写公司对公账户联系方式" value="${costAddBean.accountPhoneFrom == 'null' ? '' : costAddBean.accountPhoneFrom }" name="costAddBean.accountPhoneFrom"/>
                </div>
                <div class="div-name">
                    <label for="">账号</label>
                    <input type="text"  id="accountNumFrom" placeholder="请填写公司对公账户号" value="${costAddBean.accountNumFrom }" name="costAddBean.accountNumFrom"/>
                </div>
                <div class="div-name">
                    <label for="">开户行</label>
                    <input type="text"  id="openBankFrom" placeholder="请填写开户行" value="${costAddBean.openBankFrom }" name="costAddBean.openBankFrom"/>
                </div>

            </section>

            <section class="sec-ul">
                <p class="title-p">请在以下搜索结果进行选择</p>
                <ul class="ul-list">

                </ul>
            </section>

            <p class="navrecent">以下是最近联系人：</p>
            <section class="sec-ul2">
                <ul class="ul-list2">
                </ul>
            </section>
            <section class="sec-bottom">
                <p>
                    提交
                </p>
            </section>
        </div>
    </div>
    <!--往来关系：个人（付）-->
    <div class="div-account div-account-personalFrom">
        <header>
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    往来个人
                </li>
            </ul>
        </header>
        <div class="personal-content">
            <section class="sec-search">
                <div class="div-name">
                    <label for="">往来个人</label>
                    <input type="text"  placeholder="请填写往来个人"  id="wlgrFrom" autocomplete="off" value="${costAddBean.accountNameFromP }" name="costAddBean.accountNameFromP"/>
                    <input type="hidden"  id="wlgrIdFrom" value="${costAddBean.accountNameIdFromP }" name="costAddBean.accountNameIdFromP"/>
                </div>
                <div class="div-name">
                    <label for="">手机号</label>
                    <input type="text"  id="accountPhoneFromP" placeholder="请填写公司对公账户联系方式" value="${costAddBean.accountPhoneFromP }" name="costAddBean.accountPhoneFromP"/>
                </div>
                <div class="div-name">
                    <label for="">账号</label>
                    <input type="text"  id="accountNumFromP" placeholder="请填写公司对公账户号" value="${costAddBean.accountNumFromP }" name="costAddBean.accountNumFromP"/>
                </div>
                <div class="div-name">
                    <label for="">开户行</label>
                    <input type="text"  id="openBankFromP" placeholder="请填写开户行" value="${costAddBean.openBankFromP }" name="costAddBean.openBankFromP"/>
                </div>
                <%--                <div class="box clearfix">--%>
                <%--                    <label for="">--%>
                <%--                        <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>--%>
                <%--                    </label>--%>
                <%--                    <input type="text" name="" id="search" placeholder="请输入姓名或者手机号" />--%>
                <%--                </div>--%>
                <%--                <div><input type="button" name="" id="qsearch" value="搜索" /></div>--%>
            </section>
            <section class="sec-ul">
                <p class="title-p">请在以下搜索结果进行选择</p>
                <ul class="ul-list">

                </ul>
            </section>
            <p class="resulttip">该用户尚未注册</p>
            <p class="navrecent">以下是最近联系人：</p>
            <section class="sec-ul2">
                <ul class="ul-list2">

                </ul>
            </section>
            <section class="sec-bottom">
                <p>
                    提交
                </p>
            </section>
        </div>
    </div>
    <!-- 产品详情 -->
    <div class="product_describe" style="display: none;">
        <input type="hidden" name="costAddBean.htl" id="editcontent"/>
        <div class="header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    宝贝详情
                </li>
                <li class="keep">
                    <!-- 保存 -->
                </li>
            </ul>
        </div>
        <article>
            <div class="editable" id="edit">
                <span style="display:none;" id="functionList">${fn:length(costAddBean.functionList) }</span>
                <c:if test="${fn:length(costAddBean.functionList)>0 }">
                    <c:forEach items="${costAddBean.functionList }" var="entity" varStatus="status">
                        <div class="content" id="content${status.index }">${entity }</div>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(costAddBean.functionList)<=0 }">
                    <div class="content" id="content0">
                        <div contenteditable="true" class="editablesmall">
                            <p class="moren">此处添加文字描述</p>
                        </div>
                    </div>
                </c:if>
            </div>
        </article>
        <ul class="footer">
            <li class="foot_g"><img src="http://www.impf2010.com:80/images/WFJClient/PersonalJoining/foot_add.png">
                添加图片
                <!-- <input name="photo" id="doc" multiple style="width:100%; height:100%; position:absolute; top:0; left:0; opacity:0;" onChange="javascript:setImagePreviews();" accept="image/*" type="file"> -->
            </li>
            <li class="foot_r">
                <button type="button"></button>
                完成
            </li>
        </ul>
    </div>
</form>
<div id="prompt" style="width: 100%; display: none;z-index: 1001">
    <center>
        <div>
            <span style="position: relative; top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>
<script>
    //后退
    function toBack() {
        var url;
        console.log("标记："+editFlag);
        if (editFlag == "addPage") {
            url = "ea/scBudget/ea_toAddProjectItem.jspa";
        }else if (editFlag == "add") {
            url = "ea/scBudget/ea_toAddProjectBill.jspa";
        }else{
            url = "ea/scBudget/ea_toUpdateProjectBill.jspa?cashierBillsId="+cashierBillsId+"&billsType="+billsType;
        }
        window.location.href =  basePath + url;
        // window.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillList.jspa?menuId=" + menuId;
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