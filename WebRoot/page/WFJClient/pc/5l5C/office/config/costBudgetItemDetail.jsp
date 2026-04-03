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
    String personalId = (String) session.getAttribute("personalId");
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
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css"/>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/diyUpload.js"></script>
    <%--    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/font-size.js"></script>--%>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/ea/budgetBill/costBudgetItemDetail.js"></script>
    <title>初始项目详情</title>
    <style>
        .tianjia-content {
            padding-bottom: 60px; /* 根据 div-button 的实际高度调整 */
        }

        .headNav1 li {
            margin-left: 20px;
            text-align: right;
            /* width: 24%; */
            float: right;
            height: 1.5rem;
            line-height: 1.5rem;
            color: #222;
            font-size: .6rem;
            padding-right: .5rem;
        }

        .headNav2 li {
            margin-left: 20px;
            text-align: right;
            /* width: 24%; */
            float: right;
            height: 1.5rem;
            line-height: 1.5rem;
            color: #222;
            font-size: .6rem;
            padding-right: .5rem;
        }

        .headNav1 li a img {
            width: 1.2rem;
            margin-right: 0.1rem;
            vertical-align: middle;
        }

        .div-button {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            z-index: 9999;
            /*text-align: center;*/
            padding: 40px 0;
            background-color: #fff;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
            font-size: 0.7rem;
        }

        .jijia {
            display: none;
        }

        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
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
            width: 64% !important;
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

        .layui-layer-msg {
            color: #000 !important; /* 强制黑色字体 */
        }

        .layer-msg {
            color: #333 !important;
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
        var staffID = "<%= paramMap==null ? personalId : paramMap.get("staffId")%>";
        var ppid = "${param.ppId}";
        var colorvalue = '';
        var menuId = "${menuId}";
        var attriJson = '${costAddBean.attriJson}';
        // console.log(attriJson);
        var amap;
        if (attriJson) {
            amap = eval("(" + attriJson + ")");
        }
        // var amap = JSON.parse(attriMap);
        console.log(amap);
        var billsType = "初始项目单";
        var searchParams = new URLSearchParams(window.location.search);
        var params = new Map();
        searchParams.forEach(function (value, key) {
            params.set(key, value);
        });
        if (params.size > 0) {
            if (null != params.get("billsType")) {
                billsType = params.get("billsType");
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
                    ${(billsType=='收入' || billsType=='支出') ? '初始项目':billsType}详情
                </li>
                <li id="isupdate" style="display: none;">
                    ${(billsType=='收入' || billsType=='支出') ? '初始项目':billsType}修改
                </li>
            </ul>
        </div>

        <div class="tianjia-content">
            <%--隐藏域传值用--%>
            <input type="hidden" name="costAddBean.tzFlag" value="true"/><%--跳转标识--%>
            <input type="hidden" name="costAddBean.goodsId" value="${costAddBean.goodsId}"/>
            <%--后退传值用--%>
            <input type="hidden" name="delGoodsBillsIds" value="${delGoodsBillsIds}"/><%--修改页面删除已保存的货物id数组--%>
            <input type="hidden" name="showFlag" value="${costAddBean.showFlag}"/><%--是否是选择全部false全部true单一部门--%>
            <input type="hidden" name="departmentID" value="${costAddBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="departmentName" value="${costAddBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <input type="hidden" name="cashierBillsId" value="${costAddBean.cashierBillsId}"/><%--已添加预算单id（修改用）--%>
            <%--前一页面传过来的数据--%>
            <input type="hidden" name="costAddBean.companyId" value="${costAddBean.companyId}"/><%--公司id--%>
            <input type="hidden" name="costAddBean.departmentID" value="${costAddBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="costAddBean.departmentName" value="${costAddBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <input type="hidden" name="costAddBean.billId" value="${costAddBean.billId}"/><%--单据凭证号--%>
            <input type="hidden" name="costAddBean.companyName" value="${costAddBean.companyName}"/><%--公司名称--%>
            <input type="hidden" name="costAddBean.itemName" value="${costAddBean.itemName}"/><%--项目名称--%>
            <input type="hidden" name="costAddBean.itemType" value="${costAddBean.itemType}"/><%--项目分类--%>
            <input type="hidden" name="costAddBean.xmType" value="${costAddBean.xmType}"/><%--项目类型--%>
            <input type="hidden" name="costAddBean.itemCode" value="${costAddBean.itemCode}"/> <%--项目编号--%>
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
            <input type="hidden" id="attrinames" name="costAddBean.attrinames" value="${costAddBean.attrinames}"/>
            <input type="hidden" id="attrinamec" name="costAddBean.attrinamec" value="${costAddBean.attrinamec}"/>
            <input type="hidden" id="attri" name="costAddBean.attri" value="${attri}">
            <input type="hidden" id="ImagesPath" name="costAddBean.photoStr" value="${costAddBean.photoStr}"/>
            <input type="hidden" id="VideoPath" name="costAddBean.videoStr" value="${costAddBean.videoStr}"/>
            <input type="hidden" id="isScale" name="costAddBean.isScale" value="${costAddBean.isScale}"/>
            <input type="hidden" id="unitOfMeasureCode" name="costAddBean.unitofmeasurecode"
                   value="${costAddBean.unitofmeasurecode}"/>
            <ul class="clearfix">
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">往来个人</p>
                    <input type="text" id="connectName" name="costAddBean.connectName"
                           value="${costAddBean.connectName}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">开始日期</p>
                    <input type="text" id="serviceStartDate" name="costAddBean.serviceStartDate"
                           value="${costAddBean.serviceStartDate}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p for="">结束日期</p>
                    <input type="text" id="serviceEndDate" name="costAddBean.serviceEndDate"
                           value="${costAddBean.serviceEndDate}" placeholder="请输入"/>
                </li>
                <li class="li-01 chengzhong1 clearfix">
                    <p>云计称重</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 chengzhong2 clearfix">
                    <p>云计称重</p>
                    <input type="text" value="" readonly name="" id="jjinput">
                </li>
                <li class="li-01 jijia1 clearfix jijia">
                    <p>计价单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 jijia2 clearfix jijia">
                    <p>计价单位</p>
                    <input type="text" value="${costAddBean.unitofmeasurecode }" readonly class="">
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>物品名称</p>
                    <input type="text" id="goodsName" name="costAddBean.goodsName" value="${costAddBean.goodsName}"
                           placeholder="请输入"/>
                </li>

                <li class="li-01 tiaoma1 clearfix">
                    <p>物品条码</p>
                    <input type="text" id="barCode" name="costAddBean.barCode" value="${costAddBean.barCode}"
                           placeholder="请输入"/>
                </li>


                <li class="li-01 xiangmu1 clearfix">
                    <p>项目分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 xiangmu2 clearfix">
                    <p>项目分类</p>
                    <input type="text" value="${costAddBean.producttype }" readonly name="costAddBean.producttype"
                           id="producttype">
                    <input type="hidden" value="${costAddBean.productCode }" name="costAddBean.productCode"
                           id="productCode">
                </li>
                <li class="li-01 hangye1 clearfix">
                    <p>行业分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 hangye2 clearfix">
                    <p>行业分类</p>
                    <input type="text" value="${costAddBean.tradeName }" name="costAddBean.tradeName"
                           id="tradeName"/>
                    <input type="hidden" value="${costAddBean.tradeID }" name="costAddBean.tradeID" id="tradeID"/>
                    <input type="hidden" value="${costAddBean.tradeCode }" readonly id="tradeCode"
                           name="costAddBean.tradeCode"/>
                </li>
                <li class="li-01 wupin1 clearfix">
                    <p>物品类别</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt=""/>
                </li>
                <li class="li-02 wupin2 clearfix">
                    <p>物品类别</p>
                    <input type="text" value="${costAddBean.typeID }" readonly name="costAddBean.typeID"
                           id="typeID"/>
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
                    <p>单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 danwei2 clearfix">
                    <p>单位</p>
                    <input type="text" value="${costAddBean.variableId}" readonly name="costAddBean.variableId"
                           id="variableID">
                    <input type="hidden" value="${costAddBean.specsParentId}" readonly id="specsId">
                </li>
                <li class="li-01 guige1 clearfix">
                    <p>规格</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 guige2 clearfix">
                    <p>规格</p>
                    <input type="text" value="${costAddBean.guigeTypeValue}" readonly
                           name="costAddBean.guigeTypeValue"
                           id="guigeTypeValue">
                    <input type="hidden" value="${costAddBean.guigeType}" readonly name="costAddBean.guigeType"
                           id="guigeType">
                    <input type="hidden" value="${costAddBean.guigeTypeId}" readonly name="costAddBean.guigeTypeId"
                           id="guigeTypeId" type="hidden">
                    <input type="hidden" value="${costAddBean.specsParentId}" readonly
                           name="costAddBean.specsParentId"
                           id="specsParentId" type="hidden">

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
                <li class="li-01 mingcheng1 clearfix">
                    <p>数量</p>
                    <input type="text" id="budgetNumber" onchange="computeAmount()" name="costAddBean.budgetNumber"
                           value="${costAddBean.budgetNumber}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>单价</p>
                    <input type="text" id="unitPrice" onchange="computeAmount()" name="costAddBean.unitPrice"
                           value="${costAddBean.unitPrice}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>初始余额</p>
                    <input type="text" id="initialBalance" name="costAddBean.initialBalance"
                           value="${costAddBean.initialBalance}" readonly/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>金额</p>
                    <input type="text" id="amount" name="costAddBean.amount" value="${costAddBean.amount}"
                           placeholder="根据规格、数量、单价自动计算" readonly/>
                    <input type="hidden" id="budgetAmount" name="costAddBean.budgetAmount"
                           value="${costAddBean.budgetAmount}" onchange="budgetAmountChg()" placeholder="请输入"/>
                </li>
                <li class="li-03 loanDirection clearfix">
                    <p>收付方向</p>
                    <section class="headNav">
                        <ul>
                            <li><a href="javascript:nav('付')"><img id="loan"
                                                                    src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">付</a>
                            </li>
                            <li><a href="javascript:nav('收')"><img id="borrow"
                                                                    src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">收</a>
                            </li>
                            <input type="text" id="loanDirection" name="costAddBean.loanDirection"
                                   value="${empty costAddBean.loanDirection ? '收' :costAddBean.loanDirection}"
                                   style="display: none;"/><%--所选项目类型--%>
                        </ul>
                    </section>
                </li>
                <li class="li-01 budgetAmount clearfix">
                    <p>收方金额</p>
                    <input type="text" id="borrowAmount" name="costAddBean.borrowAmount"
                           value="${costAddBean.borrowAmount}" readonly/>
                </li>
                <li class="li-01 budgetAmount clearfix">
                    <p>付方金额</p>
                    <input type="text" id="loanAmount" name="costAddBean.loanAmount"
                           value="${costAddBean.loanAmount}"
                           readonly/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>余额</p>
                    <input type="text" id="balance" name="costAddBean.balance" value="${costAddBean.balance}"
                           readonly/>
                </li>
                <li class="li-01 wldw1 clearfix">
                    <p>收方名称</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wldw2 clearfix">
                    <p>收方名称</p>
                    <input type="text" id="accountShow" name="costAddBean.accountShow" required
                           value="${costAddBean.accountShow}" readonly/>
                    <input type="hidden" value="${costAddBean.accountFlag}" readonly name="costAddBean.accountFlag"
                           id="accountFlag">
                </li>

                <li class="li-01 wldw3 clearfix">
                    <p>付方名称</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wldw4 clearfix">
                    <p>付方名称</p>
                    <input type="text" id="accountShowFrom" name="costAddBean.accountShowFrom" required
                           value="${costAddBean.accountShowFrom}" readonly/>
                    <input type="hidden" value="${costAddBean.accountFlagFrom}" readonly
                           name="costAddBean.accountFlagFrom" id="accountFlagFrom">
                </li>

            </ul>
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
                </ul>
            </div>
            <div class="div-con">
                <div class="div-tab">
                    <div class="div-01">
                        <div class="demo">
                            <%--                        <div id="as"></div>--%>
                            <c:if test="${fn:length(costAddBean.photoList)>0 }">
                                <span style="display:none;" id="arrilist">${fn:length(costAddBean.photoList) }</span>
                                <div class="parentFileBox" style="width: 360px;">
                                    <ul class="fileBoxUl">
                                        <c:forEach items="${costAddBean.photoList }" var="entity" varStatus="status">
                                            <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                                <div class="viewThumb">
                                                    <img src="<%=basePath %>${entity.imgurl}">
                                                </div>
                                                <div class="diyCancel" style="display: none;"></div>
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
                </ul>
            </div>
            <div class="div-con">
                <div class="div-tab">
                    <div class="div-01">
                        <div class="demo">
                            <%--                        <div id="ass"></div>--%>
                            <c:if test="${fn:length(costAddBean.videoList)>0 }">
                                <span style="display:none;" id="arrvlist">${fn:length(costAddBean.videoList) }</span>
                                <div class="parentFileBox" style="width: 360px;">
                                    <ul class="fileBoxUl">
                                        <c:forEach items="${costAddBean.videoList }" var="entity" varStatus="status">
                                            <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                                <div class="viewThumb">
                                                    <video src="<%=basePath %>${entity.imgurl}"/>
                                                </div>
                                                <div class="diyCancel" style="display: none;"></div>
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

        <%--结尾--%>

        <div class="div-button" style="display: flex; flex-direction: column; gap: 16px;">
            <div class="review" style="margin-left: 20px;"
                 onclick="toBarCodeInfo('${costAddBean.goodsBillsID }');">审核
            </div>

            <%--            <ul class="headNav1" style="display: flex">--%>
            <%--                <li style="color: red;">意见 :</li>--%>
            <%--                <li><a href="javascript:nav1('同意')"><img id="loan1"--%>
            <%--                                                           src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">同意</a>--%>
            <%--                </li>--%>
            <%--                <li><a href="javascript:nav1('不同意')"><img id="borrow1"--%>
            <%--                                                             src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">不同意</a>--%>
            <%--                </li>--%>
            <%--                <input type="hidden" id="opinion1" name="goodsBillsExtOpinion.reviewOpinion"--%>
            <%--                       value="${goodsBillsExtOpinion.reviewOpinion}">--%>
            <%--            </ul>--%>

            <%--            <ul class="headNav2" style="display: flex;color: red">--%>
            <%--                <li style="color: red">审批人 :</li>--%>
            <%--                <li style="margin-left: 0px">编号:${goodsBillsExtOpinion.reviewerNameCode}</li>--%>
            <%--                <li style="margin-left: 0.1rem;">姓名:${goodsBillsExtOpinion.reviewerName}</li>--%>
            <%--                <li style="margin-left: 0.1rem;overflow-x: auto;white-space: nowrap;max-width: 100px;height: 1.9rem;overflow-y: hidden;">--%>
            <%--                    (${goodsBillsExtOpinion.reviewNameSource})--%>
            <%--                </li>--%>

            <%--            </ul>--%>
        </div>

    </div>
</form>
</body>
<script>
    let search, cashierBillsId, billsTypes;
    $(function () {
        billsTypes = getParameterByName('billsType');
        var buttonHeight = $('.div-button').outerHeight();
        $(".tianjia-content").css("padding-bottom", buttonHeight + "px");
        search = getParameterByName("search");
        cashierBillsId = getParameterByName("cashierBillsId");
        var opinionValue = $("#opinion1").val();
        if (opinionValue == "1") {
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
        } else if (opinionValue == "0") {
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
        }
    });
    $(document).ready(function () {
        // 禁用所有input输入框（排除隐藏域）
        $('input:not([type="hidden"])').attr('readonly', 'readonly');

        // 禁用所有select下拉框
        $('select').attr('disabled', 'disabled');

        // 禁用所有textarea文本域
        $('textarea').attr('readonly', 'readonly');
        $('.headNav a').removeAttr('href');
    });

    function nav1(opinion) {
        showCustomConfirm(function () {
            // 处理界面状态更新
            if (opinion === '同意') {
                $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
                $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
                // $("#opinion1").val('1');
            } else if (opinion === '不同意') {
                $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
                $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
                // $("#opinion1").val('0');
            }
            // 提交到后台
            submitReview(opinion);
        });
    }

    function submitReview(opinion) {
        $.ajax({
            url: basePath + 'ea/reviewCirculate/sajax_ea_updateOpinion.jspa', // 替换为实际URL
            type: 'POST',
            data: {
                'goodsBillsExtOpinion.reviewOpinion': opinion,
                'search': search,
                'cashierBillsId': cashierBillsId
            },
            success: function (response) {
                layer.msg("审核成功");
                var redirectPath = localStorage.getItem("fullPath");
                // 设置延迟跳转
                setTimeout(function () {
                    if (redirectPath) {
                        window.location.href = redirectPath;
                    } else {
                        window.history.back();
                    }
                }, 1500);
                localStorage.removeItem("fullPath");
            },
            error: function (xhr, status, error) {
                layer.msg("审核失败");
            }
        });
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }

    function showCustomConfirm(confirmCallback) {
        // 创建弹框元素
        var confirmBox = `<div id="customConfirm" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%;
         background-color: rgba(0,0,0,0.5); z-index: 10000; display: flex; justify-content: center;
         align-items: center;">
        <div style="background: white; border-radius: 8px; padding: 20px; min-width: 300px;
             box-shadow: 0 4px 12px rgba(0,0,0,0.3); text-align: center;">
            <p style="margin-bottom: 20px; font-size: 16px; color: #333;">是否确定?</p>
            <div>
                <button id="confirmYes" style="margin-right: 10px; padding: 8px 20px;
                        background-color: #007bff; color: white; border: none; border-radius: 4px;
                        cursor: pointer;">确定</button>
                <button id="confirmNo" style="padding: 8px 20px; background-color: #6c757d;
                        color: white; border: none; border-radius: 4px; cursor: pointer;">取消</button>
            </div>
        </div>
    </div>`;

        // 添加到页面
        $('body').append(confirmBox);

        // 绑定确认按钮事件
        $('#confirmYes').on('click', function () {
            $('#customConfirm').remove();
            if (confirmCallback) confirmCallback();
        });

        // 绑定取消按钮事件
        $('#confirmNo').on('click', function () {
            $('#customConfirm').remove();
        });
    }

    function toBarCodeInfo(goodsBillsID) {
        window.location.href = basePath + "ea/reviewCirculate/ea_toCostBudgetItemDetail1.jspa?search=" + goodsBillsID +
            "&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}&billsType=" + billsType;
    }


</script>
</html>