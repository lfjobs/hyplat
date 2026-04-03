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

        .headNav1 {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            list-style: none;
            padding: 0;
        }

        .headNav1 li {
            margin-right: 10px;
            display: flex;
            align-items: center;
        }


        .headNav1 li a {
            display: flex;
            align-items: center;
            text-decoration: none;
        }

        .headNav1 li a img {
            width: 1.2rem;
            margin-right: 0.1rem;
            vertical-align: middle;
        }

        .div-button {
            bottom: 0;
            left: 0;
            z-index: 9999;
            text-align: center;
            background-color: #fff;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
            font-size: 0.7rem;
            border: 2px solid #b7bdb7;
            border-radius: 30px;
            padding: 20px 0;
        }

        .div-tianjia .tianjia-content .li-01 {
            border-bottom: 0.025rem solid #efefef;
            margin: 7px 0.5rem;
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

        .div-tianjia .tianjia-content {
            padding-top: 1rem;
        }

        /* 为规格、数量、单价、金额字段添加并排布局样式 */
        .li-02.guige2,
        .li-01.mingcheng1 {
            float: left; /* 使用左浮动实现并排 */
            width: 40%; /* 每个元素占据48%宽度，留出2%间隙 */
            box-sizing: border-box;
            margin-right: 2%; /* 添加右侧间距 */
        }

        /* 清除浮动影响 */
        .tianjia-content ul:after {
            content: "";
            display: table;
            clear: both;
        }

        /* 确保所有字段垂直对齐 */
        .li-02.guige2 p,
        .li-01.mingcheng1 p {
            text-align: left;
            padding: 0;
            margin: 0;
            line-height: 1.5rem;
        }

        .li-01.shijian1 div#timeDiv {
            float: right;
            width: 45%;
            font-size: 0.6rem;
        }

        /* 审核人字段样式 */
        .li-01.shenhe1 div {
            float: right;
            width: 45%;
            text-align: right;
            line-height: 1.5rem;
            font-size: 0.6rem;
            color: #222;
            padding-right: 1.3rem;
            /*background-color: #f8f9fa;*/

        }

        header ul li {
            float: left;
            height: 2rem;
            line-height: 2rem;
            font-size: 1rem;
            color: #fff;
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
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" alt="返回">
            </a>
        </li>
        <li>审核</li>
        <li></li>
    </ul>
</header>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">
    <!-- 添加物品 -->
    <div class="div-tianjia">

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
                <li class="li-02 guige2 clearfix">
                    <p>规格</p>
                    <input type="text" value="${costAddBean.guigeTypeValue}" readonly
                           name="costAddBean.guigeTypeValue"
                           id="guigeTypeValue1">
                    <input type="hidden" value="${costAddBean.guigeType}" readonly name="costAddBean.guigeType"
                           id="guigeType1">
                    <input type="hidden" value="${costAddBean.guigeTypeId}" readonly name="costAddBean.guigeTypeId"
                           id="guigeTypeId1" type="hidden">
                    <input type="hidden" value="${costAddBean.specsParentId}" readonly
                           name="costAddBean.specsParentId"
                           id="specsParentId1" type="hidden">

                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>数量</p>
                    <input type="text" id="budgetNumber" onchange="computeAmount()" readonly
                           name="costAddBean.budgetNumber"
                           value="${costAddBean.budgetNumber}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>数量</p>
                    <input type="text" id="budgetNumber1" name="costAddBean.budgetNumber"
                           placeholder="请输入" onchange="budgetAmountChg1()"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>单价</p>
                    <input type="text" id="unitPrice" onchange="computeAmount()" readonly name="costAddBean.unitPrice"
                           value="${costAddBean.unitPrice}" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>单价</p>
                    <input type="text" id="unitPrice1" name="costAddBean.unitPrice"
                           placeholder="请输入" onchange="budgetAmountChg1()"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>金额</p>
                    <input type="text" id="amount" readonly name="costAddBean.amount" value="${costAddBean.amount}"
                           placeholder="根据规格、数量、单价自动计算" readonly/>
                    <input type="hidden" id="budgetAmount" name="costAddBean.budgetAmount"
                           value="${costAddBean.budgetAmount}" onchange="budgetAmountChg()" placeholder="请输入"/>
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>金额</p>
                    <input type="text" id="amount1" name="costAddBean.amount" readonly
                           placeholder="根据规格、数量、单价自动计算"/>
                    <input type="hidden" id="budgetAmount1" name="costAddBean.budgetAmount"
                           onchange="budgetAmountChg1()" placeholder="请输入"/>
                </li>

                <li class="li-01 shijian1 clearfix">
                    <p>时间</p>
                    <div id="timeDiv"></div>
                </li>
                <li class="li-01 shenhe1 clearfix">
                    <p>审核人</p>
                    <div>${goodsBillsExtOpinion.reviewerName}</div>
                </li>
            </ul>
            <div style="margin: 7px 0.5rem;display: flex;">
                <p>意见</p>
                <div class="headNav3" style="flex: 1; display: flex; justify-content: center; align-items: center;">
                    <ul class="headNav1"
                        style="display: flex; justify-content: center; margin: 0; padding: 0; width: 100%;">
                        <li style="margin-right:3rem;"><a href="javascript:nav1('同意')"><img id="loan1"
                                                                                              src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">同意</a>
                        </li>
                        <li style="margin-right: 0;"><a href="javascript:nav1('驳回')"><img id="borrow1"
                                                                                            src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">驳回</a>
                        </li>
                        <input type="hidden" id="opinion1" name="goodsBillsExtOpinion.reviewOpinion"
                               value="${goodsBillsExtOpinion.reviewOpinion}">
                    </ul>
                </div>
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
        <div class="review">提交</div>
    </div>
    <div id="opinionModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
     background-color: rgba(0,0,0,0.5); z-index: 10000; justify-content: center; align-items: center;">
        <div style="background: white;
    padding: 20px;
    justify-content: center;
    text-align: center;border-radius: 40px;
    margin: 20px;
    margin-top: 50%;">
            <h3 style="margin-top: 0; color: #333;">请输入审核意见</h3>
            <textarea id="opinionInput" rows="4" style="width: 90%; padding: 10px; border: 1px solid #ddd;
                 border-radius: 4px; resize: vertical;" placeholder="请输入审核意见..."></textarea>
            <div style="margin-top: 15px; text-align: right;">
                <button id="cancelBtn" style="margin-right: 10px; padding: 8px 16px; background-color: #6c757d;
                    color: white; border: none; border-radius: 4px; cursor: pointer;">取消
                </button>
                <button id="submitOpinionBtn" style="padding: 8px 16px; background-color: #007bff;
                    color: white; border: none; border-radius: 4px; cursor: pointer;">确定
                </button>
            </div>
        </div>

    </div>
</form>
</body>
<script>
    let search, cashierBillsId, opinionNew, billsTypes;
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
        updateCurrentTime();
    });

    function nav1(opinion) {
        opinionNew = opinion;
        // showCustomConfirm(function () {
        // 处理界面状态更新
        if (opinion === '同意') {
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
            // $("#opinion1").val('1');
        } else if (opinion === '驳回') {
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
            // $("#opinion1").val('0');
        }
        // 提交到后台
        // submitReview(opinion);
        // });
    }

    function submitReview(opinion) {
        $.ajax({
            url: basePath + 'ea/reviewCirculate/sajax_ea_updateOpinion.jspa', // 替换为实际URL
            type: 'POST',
            data: {
                'goodsBillsExtOpinion.reviewOpinion': opinion,
                'search': search,
                'cashierBillsId': cashierBillsId,
                'billsTypes': billsTypes
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


    function budgetAmountChg1() {
        var budgetNumber1 = $("#budgetNumber1").val().trim();
        var unitPrice1 = $("#unitPrice1").val().trim();
        // 正则表达式验证数字格式（包括小数）
        var numberRegex = /^\d+(\.\d+)?$/;
        if (numberRegex.test(budgetNumber1) && numberRegex.test(unitPrice1)) {
            var amount = parseFloat(budgetNumber1) * parseFloat(unitPrice1);
            $("#amount1").val(amount.toFixed(2));
        } else if (budgetNumber1 === "" && unitPrice1 === "") {
            // 两个都为空时清空金额
            $("#amount1").val("");
        }
    }


    // 或者使用类名组合
    var divElement = document.getElementById('timeDiv');

    // 设置div内容为当前时间
    function updateCurrentTime() {
        var now = new Date();
        var currentTime = now.toLocaleString(); // 格式化为本地时间格式
        divElement.textContent = currentTime;
    }

    $(document).ready(function () {
        $('.review').on('click', function () {
            // 如果 opinionNew 为空，则根据图片状态判断是同意还是驳回
            if (!opinionNew) {
                var loanSrc = $("#loan1").attr("src");
                var borrowSrc = $("#borrow1").attr("src");

                // 检查哪个按钮被选中（图片包含 dxseleted.png 表示选中）
                if (loanSrc && loanSrc.indexOf('dxseleted.png') !== -1) {
                    opinionNew = '同意';
                } else if (borrowSrc && borrowSrc.indexOf('dxseleted.png') !== -1) {
                    opinionNew = '驳回';
                }
            }

            // 如果没有选择任何意见，提示用户
            if (!opinionNew) {
                layer.msg('请选择审核意见');
                return;
            }

            submitReview(opinionNew);
        });
    });
    // 存储当前操作类型（同意或驳回）
    var currentOpinion = '';

    function nav1(opinion) {
        currentOpinion = opinion;

        // 显示意见输入弹框
        $('#opinionModal').show();
        $('#opinionInput').focus();
    }

    // 确定按钮事件
    $('#submitOpinionBtn').click(function (e) {
        e.preventDefault(); // 阻止默认提交行为

        var opinionText = $('#opinionInput').val().trim();

        // 如果没有输入意见，提示用户
        if (!opinionText) {
            layer.msg('请输入审核意见');
            return;
        }

        // 更新隐藏字段的值
        $('#opinion1').val(opinionText);

        // 更新按钮状态
        if (currentOpinion === '同意') {
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
        } else if (currentOpinion === '驳回') {
            $("#loan1").attr("src", basePath + "images/ea/office/contract/selectp/dxwselet.png");
            $("#borrow1").attr("src", basePath + "images/ea/office/contract/selectp/dxseleted.png");
        }

        // 隐藏弹框
        $('#opinionModal').hide();
        $('#opinionInput').val(''); // 清空输入框

        // 更新审核状态值（同意为 1，驳回为 0）
        var opinionValue = (currentOpinion === '同意') ? '1' : '0';

        // 如果有隐藏字段存储审核状态，可以在这里设置
        // $("#opinionStatus").val(opinionValue);

        layer.msg('已选择：' + currentOpinion);

        // 确保不刷新页面
        return false;
    });

    // 取消按钮事件
    $('#cancelBtn').click(function (e) {
        e.preventDefault(); // 阻止默认行为
        $('#opinionModal').hide();
        $('#opinionInput').val('');
        return false; // 确保不刷新
    });

    // 点击弹框背景关闭弹框
    $(document).mouseup(function (e) {
        var container = $("#opinionModal > div");
        if (!container.is(e.target) && container.has(e.target).length === 0) {
            $('#opinionModal').hide();
            $('#opinionInput').val('');
        }
    });

    // 阻止表单的默认提交行为
    $('#launchForm').on('submit', function (e) {
        e.preventDefault();
        return false;
    });

</script>
</html>