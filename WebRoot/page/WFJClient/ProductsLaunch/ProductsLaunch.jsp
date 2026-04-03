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
%>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=0, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>发布产品</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/shop.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/productdescribe.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/checkboxStyle.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/production/new_style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>util/layui/css/layui.css">

    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/comm.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/toucher.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery-latest.js"></script>
    <script type="text/javascript" src="<%=basePath %>util/layui/layui.all.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>
    <script src="https://res2.wx.qq.com/open/js/jweixin-1.6.0.js " type="text/javascript"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/container/mqttLock.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/productslaunch.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/product.js"></script>

    <%--滚动--%>
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/jquery.marquee@1.6.0/jquery.marquee.min.js"></script>

    <script>
        var ppId="${prolist[0][1]}";
        var pagenumber = 0;
        var pagecount = 0;
        var t;
        var num = 0;
        var delList = "";
        var user = '${user}';
        var companyId = '${companyId}';
        var basePath = '<%=basePath%>';
        var text = '';//页面行业分类
        var temp = "";
        var sizevalue = '';
        var colorvalue = '';
        var j =${fn:length(functionList)>0?(fn:length(functionList)):0};
        var d = 0;
        var k = 0;
        var backu = '<%=session.getAttribute("vipback")%>';
        var ptcount = '${ptcount}';
        var ret = '${ret}';
        var sys = '${sys}';
        var goodsId = $("#goodsId").val();
        var cz = false;
        var typez = "${typez}";
        var carType = "${feeScale.carType}";
        var timeUnits = "${feeScale.timeUnits}";

        var timeType = "${feeScale.timeType}";

        var siteId = "${feeScale.siteId}";
        var totalPct = "${totalPct}";
    </script>
    <%--http://localhost:8080//ea/productslaunch/ea_toProductsLaunch.jspa?sys=wdhy--%>
    <style>
        p {
            margin: 0;
        }

        .carousel-inner {
            height: 100%;
        }

        .carousel-inner .item {
            height: 100%;
        }

        .carousel-inner .item img {
            margin: 0;
        }

        .bannerc {
            line-height: 230px;
        }

        .carousel-indicators {
            bottom: 0;
        }

        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 0.5);
        }

        /*2017年6月6日 17:22:33 修改选择类别样式*/
        .investment, .depotManage, .variable {
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            z-index: -1;
            background-color: rgba(0, 0, 0, 0.6);
            opacity: 0;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;

        }

        .act {
            opacity: 1;
            z-index: 1000;
        }

        .investment_con, .depotManage_con, .variable_con {
            background: #fff;
            text-align: center;
            width: 70% !important;
            margin: 0 auto;
        }

        .investment_con > div, .depotManage_con > div:first-child, .variable_con > div {
            background: #dedede;
            line-height: 42px;
        }

        .depotManage_con ul li {
            display: inline-block; /* 让列表项并排显示 */
            text-align: left; /* 内部文本左对齐 */
            margin-right: 10px; /* 可选的，列表项之间的间隔 */
        }

        .investment_con ul li, .variable_con ul li {
            border-bottom: 1px solid #eee;
            line-height: 30px;
            height: 2rem !important;
            line-height: 2rem !important;
        }

        .depotManage_con .depotManageTree {
            height: 10rem;
        }

        /*弹框样式*/
        #jjdw_xz {
            top: 0;
            z-index: 999999;
            bottom: 0;
            display: none;
            position: fixed;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4);
        }

        #jjdw_xz section {
            margin-top: 45%;
        }

        #jjdw_xz section ul {
            width: 60%;
            margin: 0 auto;
            background: #fff;
        }

        #jjdw_xz section h2 {
            width: 60%;
            margin: 0 auto;
            height: 1.5rem;
            line-height: 1.5rem;
            text-align: center;
            border-bottom: 1px solid #ccc;
            background: #ccc;
        }

        #jjdw_xz section ul li {
            height: 2rem;
            line-height: 2rem;
            text-align: center;
            font-size: 0.65rem;
        }

        #jjdw_xz section ul li:hover {
            background-color: red;
        }

        #jjdw_xz section ul li:first-of-type {
            border-bottom: 1px solid #ccc;
        }

        #jjdw_p {
            margin-right: 0;
        }

        .tiaoma_text {
            color: #ccc
        }

        #productdescribe .main_inp_right > p, #promotionalProducts .main_inp_right > p {
            float: right;
        }

        .content .sec-list ul li {
            padding: 0 .5rem;
            max-height: 25rem;
            line-height: 2.5rem;
            font-size: .85rem;
            color: #222;
            border-bottom: .025rem solid #eee;
            cursor: pointer;
            clear: both;
        }

        .content .sec-list ul li p:nth-of-type(1) {
            float: left;
            width: 55%;
            overflow: hidden;
            word-break: keep-all;
            text-align: center;
        }

        .content .sec-list ul li p:nth-of-type(2) {
            float: right;
            font-size: .7rem;
        }

        .content .sec-list ul li:first-of-type {
            border-top: .025rem solid #eee
        }

        .sec-hide {
            background: #f9f9f9;
            padding-left: 0.5rem;
        }

        .content .nav {
            position: fixed;
            z-index: 999;
            top: -0.5rem;
            width: 100%;
        }

        .content .sec-nav ul li .del img {
            width: 1.2rem;
        }

        .content .p-wq span:nth-of-type(1) {
            padding-right: 0.2rem;
        }

        .depotManage_div, .div-iframe {
            position: absolute;
            top: 0%;
            width: 100%;
            height: 100%;
            background: rgb(255, 255, 255);
            z-index: 1001;
            display: none;
        }

        .layui-tab-content {
            overflow-y: auto;
        }

        .dtd-oa-search-bar {
            display: inline-block;
            position: relative;
            width: 100%;
            height: 42px;
            line-height: 1;
            font-size: 14px;
            box-sizing: border-box;
            border-radius: 6px;
            padding: 10px 10px 0px 10px;
        }

        .dtd-oa-search-bar-input {
            border: 1px solid rgb(145 125 88 / 50%);
            font-size: 14px !important;
            line-height: 1.5 !important;
            box-sizing: border-box;
            width: 100%;
            padding: 4px 25px 4px 30px;
            border-radius: 4px;
        }

        .dtd-oa-search-bar-icon-wrapper {
            position: absolute;
            width: 16px;
            height: 16px;
            top: 40%;
            left: 10px;
            margin-top: 2px;
            line-height: 16px;
            text-align: center;
            margin-left: 5px;
        }

        .div-iframe iframe {
            height: 100%;
            width: 100%;
        }


        .content .sec-bottom {
            position: fixed;
            width: 100%;
            left: 0;
            bottom: 0;
            background-color: #fff;
            z-index: 999;
            padding: .25rem 0;
        }

        .content .sec-bottom p {
            text-align: center;
            width: 90%;
            margin: 0 auto;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: .75rem;
            color: #fff;
            border-radius: 0.2rem;
            background-color: #f74c30;
        }

        .marquee {
            white-space: nowrap;
            overflow: hidden;
            box-sizing: border-box;

            /* 设置动画名称和持续时间 */
            animation: scroll 10s linear infinite;
        }

        .div-iframe .jylx {
            height: 90%;
        }

        .top ul {
            display: flex;
            align-items: center;
            justify-content: space-between;
            height: 2rem !important;
        }

        .div-iframe .top ul .close img {
            width: 0.6rem;
        }

        @media (min-width: 960px) {
            .investment_con, .depotManage_con, .variable_con{
                width: 40% !important;
            }
            .top ul{
                height: 5rem!important;
            }
            .div-paytype{
                width: 60% !important;
                margin: 0 auto;
            }
            .addCategorie{
                width: 60%!important;
                margin: 0 20%;
            }
            .content .sec-bottom{
                bottom: 5rem;
            }
            .div-iframe .jylx{
                height: 85%;
            }
        }

            .js-marquee {
            color: #e8642d;
        }

        /* 定义关键帧 */

    </style>
</head>
<body>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">
    <s:token></s:token>
    <header>
        <%--<s:if test="#request.sys=='sys'">
            <p><a id="returnClick" href="<%=basePath %>/mobile/office/mobileoffice_fastApplication.jspa?"><img
                    src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png" alt=""/></a></p>
        </s:if>
        <s:else>
            <p><a id="returnClick" href="<%=basePath %>ea/vipcenter/ea_vipDemand.jspa?"><img
                    src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png" alt=""/></a></p>
        </s:else>--%>
        <p><a id="returnClick" onclick="goBack()">
            <img src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png" alt=""/>
        </a></p>
        <span>发布产品</span>
        <%--<p><img id="sao" src="<%=basePath%>images/WFJClient/PersonalJoining/saoerweima.png" alt="扫一扫"
                onclick="javascript:Android.callcamera()"/></p>--%>
    </header>

    <!--------------------------------------------- 主内容开始 ------------------------------------------------->
    <div class="main">
        <div class="main_hidden">
            <input type="submit" id="submit" style="display:none;"/>
            <input type="hidden" id="companyId" name="companyId" value="${companyId}"/>
            <input type="hidden" id="user" name="user" value="${user }"/>
            <input type="hidden" id="sizevalue" name="sizevalue" value="${sizevalue }"/>
            <input type="hidden" id="colorvalue" name="colorvalue" value="${colorvalue }"/>
            <input type="hidden" id="goodsId" name="goodsManage.goodsID" value="${prolist[0][0]}"/>
            <input type="hidden" id="ppId" name="productPackaging.ppID" value="${prolist[0][1]}"/>
            <input type="hidden" id="t1" name="goodsManage.tradeName" value="${prolist[0][5] }"/>
            <input type="hidden" id="tzType" name="setup.tzType" value="${prolist[0][12] }"/>
            <input type="hidden" id="t2" name="goodsManage.tradeID" value="${prolist[0][3] }"/>
            <input type="hidden" id="t3" name="goodsManage.tradeCode" value="${prolist[0][4] }"/>
            <input type="hidden" id="categoryId" name="goodsManage.categoryId" value="${prolist[0][13]}"/>
            <input type="hidden" id="categoryName" name="goodsManage.categoryName" value="${prolist[0][14]}"/>
            <input type="hidden" id="attrinames" name="attrinames" value="${attrinames}">
            <input type="hidden" id="attrinamec" name="attrinamec" value="${attrinamec}">
            <input type="hidden" id="isScale" name="goodsManage.isScale" value="${prolist[0][15]}">
            <input type="hidden" id="depotID" name="depotID" value="${prolist[0][19]}">
            <input type="hidden" id="depotName" name="depotName" value="${prolist[0][20]}">
            <input type="hidden" id="depotCoding" name="depotCoding" value="${prolist[0][21]}">
            <input type="hidden" id="stanpro" name="productPackaging.stanpro" value="${prolist[0][22]}">
            <input type="hidden" id="variableID" name="goodsManage.variableID" value="${prolist[0][24]}">
            <input type="hidden" id="unitOfMeasureCode" name="unitOfMeasureCode" value="${prolist[0][17] }"/>
            <input type="hidden" id="paytype" name="productPackaging.paytype" value="${prolist[0][17] }"/>
            <%--<input type="hidden" id="invNum" name="invNum" value="${prolist[0][6]}"/>--%>
            <input type="hidden" id="itemID" name="itemID" value=""/>
            <!-- 改动 -->
            <%--         <input type="hidden" name=goodsManage.goodsKey value="${goodsManage.goodsKey }"/>
                    <input type="hidden" name="productPackaging.ppKey" value="${productPackaging.ppKey }"/>
                    <input type="hidden" name="setup.sukey" value="${setup.sukey }"/> --%>
            <div class="main_hide" style="overflow: auto;">
                <c:choose>
                    <c:when test="${arrlist ne null&&fn:length(arrlist)>0 }">
                        <div class="main_img" style="height:230px;">
                            <p style="display:none;">
                                <img class="file0" onclick="fileSelect();"
                                     src="<%=basePath%>images/WFJClient/PersonalJoining/shangchuan_07.png" alt=""/>
                            </p>
                            <img style="display:block" class="file1" onclick="fileSelect();"
                                 src="<%=basePath%>images/WFJClient/PersonalJoining/shangchuan_07.png" alt=""/>
                            <div id="abc" class="carousel slide" style="margin: 0 auto; display:block;">
                                <ol class="carousel-indicators">
                                    <c:forEach items="${arrlist }" var="entity" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.index eq 0 }">
                                                <li data-target="#abc" class="active"
                                                    data-slide-to="${status.index }"></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li data-target="#abc" data-slide-to="${status.index }"></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ol>
                                <!--图片-->
                                <div class="carousel-inner">
                                    <c:forEach items="${arrlist }" var="entity" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.index eq 0 }">
                                                <div class="active item actives">
                                                    <input type="hidden" name="apId" value="${entity.apid}"/>
                                                    <input type="hidden" class="sort" value="${status.index+1 }"/>
                                                    <img id="preview" src="<%=basePath %>${entity.imgurl}" alt="产品图片"/>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="item imgs actives">
                                                    <input type="hidden" name="apId" value="${entity.apid }"/>
                                                    <input type="hidden" class="sort" value="${status.index+1 }"/>
                                                    <img id="preview" src="<%=basePath %>${entity.imgurl}" alt="产品图片"/>
                                                </div>
                                            </c:otherwise></c:choose>
                                    </c:forEach>
                                </div>
                                <div class="main_img_but">
                                    <a href="#abc" class="bannerc carousel-control left"
                                       data-slide="prev" id="left">&larr;</a>
                                    <a href="#abc" class="bannerc carousel-control right" data-slide="next"
                                       id="right">&rarr;</a>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="main_img ftc">
                            <p>
                                <img class="file0" onclick="fileSelect();"
                                     src="<%=basePath%>images/WFJClient/PersonalJoining/shangchuan_07.png" alt=""/>
                            </p>
                            <img class="file1" onclick="fileSelect();" style="display:none;"
                                 src="<%=basePath%>images/WFJClient/PersonalJoining/shangchuan_07.png" alt=""/>
                            <div id="abc" class="carousel slide" style="margin: 0 auto;">
                                <ol class="carousel-indicators">
                                </ol>
                                <!--图片-->
                                <div class="carousel-inner"></div>
                                <div class="main_img_but">
                                    <a href="#abc" class="bannerc carousel-control left"
                                       data-slide="prev" id="left">&larr;</a>
                                    <a href="#abc" class="bannerc carousel-control right" data-slide="next"
                                       id="right">&rarr;</a>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
                <!--焦点-->
                <%--名称--%>
                <div class="main_inp ftc">
                    <input id="ppname" type="text" placeholder="请输入商品名称" name="goodsManage.goodsName"
                           value="${name!=null&&name!="" ? name:prolist[0][2]}"/>
                </div>
                <%--电子秤打条码--%>
                <div class="main_inp2 ftc">
                    <div class="main_inp_left">电子秤打条码</div>
                    <div class="main_inp_right">
                        <p></p>
                    </div>
                    <div class="toggle toggle--switch toggle_ZDY">
                        <input type="checkbox" id="toggle--switch" class="toggle--checkbox">
                        <label class="toggle--btn" for="toggle--switch">
                            <span class="toggle--feature" data-label-on="是" data-label-off="否"></span>
                        </label>
                    </div>
                </div>
                <%--计价单位--%>
                <div id="jjdw" class="main_inp2 ftc">
                    <div class="main_inp_left">计价单位</div>
                    <div class="main_inp_right">
                        <p id="jjdw_p">${prolist[0][17]=="KGM"?"KGM已重计价":prolist[0][17]=="PCS"?"PCS以数计价":""}</p>
                        <p id="jjdw_img" style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--产品条码--%>
                <div class="main_inp2 div_cptm ftc">
                    <div class="main_inp_left">产品条码</div>
                    <div class="main_inp_right">
                        <p class="tiaoma_text">
                            <input id="barCode" type="text" placeholder="系统自动生成"
                                   name="goodsManage.barCode" value="${barcode!=null ? barcode:prolist[0][18]}"/>
                            <span id="codeSpan" style="display: none">${prolist[0][18]}</span>
                        </p>
                        <p class="tiaoma_img" style="float:right;">
                            <img id="sao" src="<%=basePath%>images/WFJClient/PersonalJoining/saoerweima.png" alt="扫一扫"
                                 onclick="scanCode()"/>
                        </p>
                    </div>
                </div>
                <%--入库仓库--%>
                <div class="main_inp2" id="depotManage" style="display: none;">
                    <div class="main_inp_left">入库仓库</div>
                    <div class="main_inp_right">
                        <p id="dmname" class="marquee scroll">${prolist[0][20]}</p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--是否标品--%>
                <div class="main_inp2" id="stanpro_div" style="display: none;">
                    <div class="main_inp_left">是否标品</div>
                    <div class="main_inp_right">
                        <p></p>
                    </div>
                    <div class="toggle toggle--switch toggle_ZDY">
                        <input type="checkbox" id="toggle--switch_stanpro" class="toggle--checkbox">
                        <label class="toggle--btn" for="toggle--switch_stanpro">
                            <span class="toggle--feature" data-label-on="是" data-label-off="否"></span>
                        </label>
                    </div>
                </div>
                <%--计量单位--%>
                <div class="main_inp2" id="variable" style="display: none;">
                    <div class="main_inp_left">计量单位</div>
                    <div class="main_inp_right">
                        <p id="v-p"></p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--单重--%>
                <div class="main_inp2" id="singleWeight" style="display: none;">
                    <div class="main_inp_left">单重</div>
                    <div class="main_inp_right">
                        <p><input class="singleWeight_input" type="number" value="${prolist[0][23]}"
                                  name="productPackaging.singleWeight"/></p>
                        <p id="singleWeight-p" style="font-size: 0.5rem;"></p>
                    </div>
                </div>
                <%--产品分类--%>
                <div class="main_inp2 products ftc">
                    <div class="main_inp_left">产品分类</div>
                    <div class="main_inp_right">
                        <p class="product_lei" id="product_lei">${prolist[0][14]}</p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--------------------场地收费开始--------------------%>
                <%--车类型--%>
                <div class="main_inp2 tc">
                    <div class="main_inp_left">车类型</div>
                    <div class="main_inp_right">
                        <select name="feeScale.carType" class="sel-select" id="carType">
                            <option value="c">教练车</option>
                            <option value="p">私家车</option>
                        </select>

                    </div>
                </div>
                <%--时间单位--%>
                <div class="main_inp2 tc">
                    <div class="main_inp_left">时间单位</div>
                    <div class="main_inp_right">
                        <select class="sel-select" name="feeScale.timeUnits" id="timeUnits">
                            <option value="0">小时</option>
                            <option value="1">包天</option>
                            <option value="2">包月30天</option>
                            <option value="3">包年</option>
                        </select>
                    </div>
                </div>
                <%--包天制度--%>
                <div class="main_inp2 tc btzd mil">
                    <div class="main_inp_left">包天制度</div>
                    <div class="main_inp_right">

                        <select class="sel sel-select" name="feeScale.timeType" id="timeType">
                            <option value="0">当天(00点结束)</option>
                            <option value="24">24小时制</option>
                            <option value="8">8小时制</option>
                        </select>

                    </div>
                </div>
                <%--免费时长(分钟)--%>
                <div class="main_inp2 tc">
                    <div class="main_inp_left">免费时长(分钟)</div>
                    <div class="main_inp_right">
                        <p>
                            <input maxlength="20" name="feeScale.feeMini" value="${feeScale.feeMini}" id="feeMini"
                                   type="number" step="1" min="0"
                                   onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''">
                        </p>
                    </div>
                </div>
                <%--场地--%>
                <div class="main_inp2 tc">
                    <div class="main_inp_left">场地</div>
                    <div class="main_inp_right">
                        <select class="sel-select site" name="feeScale.siteId">

                        </select>
                    </div>
                </div>
                <%--责任人--%>
                <div class="main_inp2 tc" id="zrr">
                    <div class="main_inp_left">责任人</div>
                    <div class="main_inp_right">
                        <p class="product_lei responsibleName">

                            ${feeScale.staffName}

                        </p>
                        <p style="float:right;">
                            <input type="hidden" id="responsibleID" name="feeScale.staffID"
                                   value="${feeScale.staffID}"/>
                            <input type="hidden" id="responsibleName" name="feeScale.staffName"
                                   value="${feeScale.staffName}"/>
                            <input type="hidden" name="feeScale.feecID" value="${feeScale.feecID}"/>
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--------------------场地收费结束--------------------%>
                <%--产品规格--%>
                <div class="main_inp2 ftc" id="productsize">
                    <div class="main_inp_left">产品规格</div>
                    <div class="main_inp_right">
                        <p id="ps"></p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--产品品牌--%>
                <div class="main_inp2 ftc">
                    <div class="main_inp_left">产品品牌</div>
                    <div class="main_inp_right">
                        <p>
                            <input type="text" value="${prolist[0][16]}" name="goodsManage.brand"/>
                        </p>
                    </div>
                </div>
                <%--交易类型--%>
                <div class="main_inp2 paytype">
                    <div class="main_inp_left">交易类型</div>
                    <div class="main_inp_right">
                        <p id="paytype-p"></p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--平台展示价格--%>
                <div class="main_inp2">
                    <div class="main_inp_left">展示价格</div>
                    <div class="main_inp_right">
                        <p>
                            <input class="ver sj" type="number" placeholder="自动计算(引流红包+系统价格)" readonly="readonly"/>
                        </p>
                        <p>元</p>
                    </div>
                </div>
                <%--系统销售价格--%>
                <div class="main_inp2">
                    <div class="main_inp_left">系统销售价格</div>
                    <div class="main_inp_right">
                        <p>
                            <input class="ver xtsj" type="number" placeholder="${interval }" value="${prolist[0][8]}"
                                   name="setup.rePrice"/>
                        </p>
                        <p>元</p>
                    </div>
                </div>
                <%--代理佣金--%>
                <c:if test="${dlyj!=null}">
                    <c:forEach items="${dlyj}" var="dl" varStatus="idxStatus">
                        <div class="main_inp2">
                            <div class="main_inp_left">${dl[1]}佣金</div>
                            <div class="main_inp_right">
                                <p>
                                    <input id="${dl[4]}" class="ver dlyj" type="text" value="${dl[2]}"
                                           name="pssMap[${idxStatus.index}].amount"/>
                                    <input type="hidden" value="${dl[3]}" name="pssMap[${idxStatus.index}].suskey"/>
                                    <input type="hidden" value="${dl[0]}" name="pssMap[${idxStatus.index}].susid"/>
                                    <input type="hidden" value="${dl[4]}" name="pssMap[${idxStatus.index}].typePpid"/>
                                </p>
                                <p>元</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <%--代理佣金--%>
                <c:if test="${dlyj==null}">
                    <c:forEach items="${dlList}" var="dl" varStatus="idxStatus">
                        <div class="main_inp2">
                            <div class="main_inp_left">${dl.goodsName}佣金</div>
                            <div class="main_inp_right">
                                <p>
                                    <input class="ver dlyj" type="number" name="pssMap[${idxStatus.index}].amount"
                                           id="${dl.ppID}"/>
                                    <input type="hidden" value="${dl.ppID}" name="pssMap[${idxStatus.index}].typePpid"/>
                                </p>
                                <p>元</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <%--消费红包--%>
                <div class="main_inp2">
                    <div class="main_inp_left">引流红包</div>
                    <div class="main_inp_right">
                        <p>
                            <input class="ver xfhb" type="number" readonly="readonly" placeholder="自动计算"/>
                        </p>
                        <p>元</p>
                    </div>
                </div>
                <%--业务佣金--%>
                <div class="main_inp2">
                    <div class="main_inp_left">业务佣金</div>
                    <div class="main_inp_right">
                        <p>
                            <input class="ver dlyj" type="number" value="${prolist[0][9]}" name="setup.brokerage"/>
                        </p>
                        <p>元</p>
                    </div>
                </div>
                <%--投资设备类型--%>
                <div class="main_inp2" id="investment">
                    <div class="main_inp_left">投资设备类型</div>
                    <div class="main_inp_right">
                        <p id="tz_type">
                            ${prolist[0][12]=='01'?'教练车':prolist[0][12]=='02'?'创客单车':prolist[0][12]=='03'?'超市':"无"}
                        </p>
                        <p style="float:right;">
                            <img src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <%--初始盘库库存--%>
                <c:if test="${prolist[0][1]==''||prolist[0][1]==null}">
                    <div class="main_inp2" id="kc">
                        <div class="main_inp_left">初始盘库库存</div>
                        <div class="main_inp_right">
                            <p><input class="kc" type="number"
                                      value="${prolist[0][6]==null||prolist[0][6]==""?w_weight:prolist[0][6]}"
                                      name="productPackaging.invNum"  oninput="if(value<0)value=0"/>
                            </p>
                            <p class="kc_p">件</p>
                        </div>
                    </div>
                </c:if>
                <%--产品描述--%>
                <div class="main_inp2" id="productdescribe" style="border-top: 4px solid #e3e3e3;">
                    <div class="main_inp_left">产品描述</div>
                    <div class="main_inp_right">
                        <p style="margin-right:15px;">
                            <span id="complete"></span>
                            <img id="pt" src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                        </p>
                    </div>
                </div>
                <!-- 设置促销产品 -->
                <c:if test="${not empty prolist[0][1]}">
                    <div class="main_inp2" id="promotionalProducts" style="border-top: 4px solid #e3e3e3;">
                        <div class="main_inp_left">设置促销产品</div>
                        <div class="main_inp_right">
                            <p style="margin-right:15px;">
                                <span id="complete_pt"></span>
                                <img id="ptt" src="<%=basePath%>images/WFJClient/PersonalJoining/go_03.png" alt=""/>
                            </p>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <footer>
        <div class="footer_left fabu" onclick="toLaunchOrcang('tocang')" id="frkc">放入库存</div>
        <div class="footer_right fabu" onclick="toLaunchOrcang('')" id="lkfb">立刻发布</div>
    </footer>
    <!--------------------------------------------- 主内容结束 ------------------------------------------------->

    <!--------------------------------------------- 产品规格开始 ------------------------------------------------->
    <div class="product_size"
         style="display:none;position: absolute;top:0%;width: 100%;height:100%;background: #FFFFFF;z-index:1001;">
        <div class="top">
            <ul>
                <li class="arrow sizereturn">
<%--                    <a href="javascript:" target="_self">--%>
                        <img src="<%=basePath%>images/WFJClient/PersonalJoining/top_reture.png"/>
<%--                    </a>--%>
                </li>
                <li>产品规格</li>
<%--                <a>--%>
                    <li class="done">
                        <button type="button" form="size" style="border:none; background:#fff;">完成</button>
                    </li>
<%--                </a>--%>
                <div class="clear"></div>
            </ul>
        </div>
        <article>
            <section class="color">
                <input class="sezi_title" value="颜色">
                <ul class="size_new">
                    <%--
                     <li><input value="特别的红色" name="new_1"><img class="size_del" src="<%=basePath%>images/WFJClient/PersonalJoining/size_del.png"></li>
                     --%>
                    <li><input class="seze_edit" placeholder="自定义颜色"></li>


                </ul>
                <ul class="size_old">
                    <li><input value="白色" readonly name="old_1"></li>
                    <li><input value="粉色" readonly name="old_2" class=""></li>
                    <li><input value="米黄色" readonly name="old_3"></li>
                    <li><input value="蓝色" readonly name="old_4" class=""></li>
                    <li><input value="紫色" readonly name="old_5"></li>
                    <li><input value="黑色" readonly name="old_6"></li>
                    <div class="clear"></div>
                </ul>
            </section>


            <section class="size">
                <input class="sezi_title" value="尺码大小">
                <ul class="size_new">
                    <%--
                     <li><input value="大小" name="new_1"><img class="size_del" src="<%=basePath%>images/WFJClient/PersonalJoining/size_del.png"></li>
                     --%>
                    <li><input class="seze_edit" placeholder="自定义大小"></li>

                </ul>
                <ul class="size_old">
                    <li><input value="S" readonly name="old_1"></li>
                    <li><input value="M" readonly name="old_2" class=""></li>
                    <li><input value="L" readonly name="old_3"></li>
                    <li><input value="XL" readonly name="old_4" class=""></li>
                    <li><input value="XXL" readonly name="old_5"></li>
                    <li><input value="XXXL" readonly name="old_6"></li>
                </ul>
            </section>
        </article>
    </div>
    <!--------------------------------------------- 产品规格结束 ------------------------------------------------->

    <!--------------------------------------------- 产品描述开始 ------------------------------------------------->
    <div class="product_describe"
         style="display:none;position: absolute;top:0%;width: 100%;height:100%;background: #FFFFFF;z-index:1001;">
        <input type="hidden" name="htl" id="editcontent"/>
        <div class="top">
            <ul>
                <li class="arrow des"><a href="javascript:" target="_self"><img
                        src="<%=basePath%>images/WFJClient/PersonalJoining/top_reture.png"/></a></li>
                <li>产品描述</li>
                <li class="top_hidden">完成</li>
                <div class="clear"></div>
            </ul>
        </div>
        <article>
            <div class="editable" id="edit">
                <c:if test="${fn:length(functionList)>0 }">
                    <c:forEach items="${functionList }" var="entity" varStatus="status">
                        <div class="content" id="content${status.index }">${entity }</div>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(functionList)<=0 }">
                    <div class="content" id="content0">
                        <div contenteditable="true" class="editablesmall">
                            <p class="moren">此处添加文字描述</p>
                        </div>
                    </div>
                </c:if>
            </div>
        </article>
        <ul class="footer">
            <li class="foot_g"><img src="<%=basePath%>images/WFJClient/PersonalJoining/foot_add.png">
                添加图片
                <!-- <input name="photo" id="doc" multiple style="width:100%; height:100%; position:absolute; top:0; left:0; opacity:0;" onChange="javascript:setImagePreviews();" accept="image/*" type="file"> -->
            </li>
            <li class="foot_r">
                <button type="button"></button>
                完成
            </li>
        </ul>
    </div>
    <!--------------------------------------------- 产品描述结束 ------------------------------------------------->

    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
        <center>
            <div>
                <span style="position: relative; top: 19.8%;"></span>
            </div>
        </center>
    </div>
</form>

<!--------------------------------------------- 产品分类开始 ------------------------------------------------->
<div class="addCategorie"
     style="display: none; position: fixed;top: 0px;z-index: 10001;height: 100%;background: #fff;width: 100%;left: 0;bottom: 0;height: 100%;">
    <header>
        <div class="top">
            <ul>
                <li id="back" style="width: 10%;">
                    <img src="<%=basePath %>images/WFJClient/PersonalJoining/back_03.png">
                </li>
                <li style="width: 70%;text-indent: 10%;">产品分类</li>
                <li style="width: 20%;" class="redact">编辑分类</li>
            </ul>
        </div>
    </header>
    <div class="content_hidden" style="width: 100%; overflow-y: auto;height: 100%;">
        <div class="contents">
            <div class="con" style="overflow: auto;">
                <form id="typeForm" method="post">
                    <input type="submit" name="submit" style="display:none"/>
                    <input type="hidden" id="delList" name="delList" style="display:none"/>
                    <input type="hidden" name="type" value="${type}" style="display:none"/>
                    <ul class="product" style="padding-bottom: 7rem;">
                    </ul>
                </form>
                <div align="center">
                    <input type="button" value="添加" class="add">
                </div>

            </div>
        </div>
    </div>
</div>
<!--------------------------------------------- 产品分类结束 ------------------------------------------------->

<!--------------------------------------------- 投资类型开始 ------------------------------------------------->
<div class="investment">
    <div class="investment_con">
        <div>投资类型</div>
        <ul>
            <li value="01">教练车</li>
            <li value="02">创客单车</li>
            <li value="03">超市</li>
            <li>无</li>
        </ul>
    </div>
</div>
<!--------------------------------------------- 投资类型结束 ------------------------------------------------->

<!--------------------------------------------- 计价单位开始 ------------------------------------------------->
<div id="jjdw_xz">
    <section>
        <h2>
            计价单位
        </h2>
        <ul>
            <li>
                KGM以重计价
            </li>
            <li>
                PCS以数计价
            </li>
        </ul>
    </section>
</div>
<!--------------------------------------------- 计价单位结束 ------------------------------------------------->

<!--------------------------------------------- 责任人 开始 ------------------------------------------------->
<c:if test="${typez eq 'tc'}">
    <div class="iframecom" style="display:none;">
        <iframe id="iframe" src="<%=basePath%>page/WFJClient/ProductsLaunch/selectStaffs.jsp" width="100%" height="100%"
                frameborder="0"></iframe>
    </div>
</c:if>
<!--------------------------------------------- 责任人 结束 ------------------------------------------------->

<!--------------------------------------------- 计量单位开始 ------------------------------------------------->
<div class="variable">
    <div class="variable_con">
        <div>计量单位</div>
        <ul>
            <li title="瓶">KG/瓶</li>
            <li title="个">KG/个</li>
            <li title="袋">KG/袋</li>
            <li title="桶">KG/桶</li>
            <li title="包">KG/包</li>
            <li title="无">KG/无</li>
        </ul>
    </div>
</div>
<!--------------------------------------------- 计量单位结束 ------------------------------------------------->

<!--------------------------------------------- 交易类型开始 ------------------------------------------------->
<div class="div-paytype">
    <div class="div-box">
        <div class="paytype-header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    交易类型
                </li>
                <li class="keep">
                    <!-- 保存 -->
                </li>
            </ul>
        </div>
        <div class="div-con clearfix jylx">
        </div>
    </div>
</div>
<!--------------------------------------------- 交易类型结束 ------------------------------------------------->
<%--遮罩层--%>
<div class="zhezhao"></div>

<%--入库\库房--%>
<div class="div-iframe" style="display: none;">
    <div class="top">
        <ul>
            <li class="close" style="line-height: 2.5rem;margin: 0.5rem;">
                <a href="javascript:" target="_self">
                    <img src="<%=basePath%>images/WFJClient/PersonalJoining/top_reture.png"/>
                </a>
            </li>
            <li style="margin: 0.5rem;"></li>
            <li style="margin: 0.5rem;"></li>
        </ul>
    </div>
    <div class="content clearfix jylx">
        <iframe name="main" id="iframe-" marginwidth="0" scrolling="yes" marginheight="0" frameborder="0"
                id="iframe-buhuo" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
    </div>
</div>


<jsp:include page="/page/overlay.jsp"/>
<script type="text/javascript">
    $(document).ready(function () {

        //编辑分类
        $(document).on("click", ".redact", function () {
            $(this).text("保存");
            $(this).removeClass().attr("id", "save");
            $(".typeName").each(function () {
                if ($(this).prev().val() != 1) {
                    $(this).removeAttr("readonly", "readonly");
                    $(this).next().attr("src", "<%=basePath %>images/ea/production/ico-delete.png").addClass("det");
                }
            });
            if ($(".product").find(".typeName").size() == 0) {
                $(".product").html("");
            }

            $(".add").show();
        });

        $(document).on("click", ".product li .det", function () {
            if ($(this).attr("placeholder") == null || $(this).attr("placeholder") == "") {
                if (delList == "") {
                    delList = $(this).prev().prev().prev().prev().val();
                } else {
                    delList += ("," + $(this).prev().prev().prev().prev().val());
                }
                $("#delList").val(delList)
            }
            $(this).parents("ul li").remove();
            if ($(".product").children().length == 0) {
                if (delList) {
                    var delLists = delList.split(",");
                } else {
                    $(".product").append("<div class='no'><img src='" + basePath + "images/ea/production/no.png' width='100%'><p>您还没有添加分类，赶快添加吧</p></div>");
                    $(".addCategorie .top ul").children("li").eq(2).hide();
                    return;
                }
                var count = 0;
                for (var m in delLists) {
                    if (delLists[m] == "undefined") {
                        count++;
                    }
                    if (count == delLists.length) {
                        $(".product").append("<div class='no'><img src='" + basePath + "images/ea/production/no.png' width='100%'><p>您还没有添加分类，赶快添加吧</p></div>");
                        $(".addCategorie .top ul").children("li").eq(2).hide();
                    }
                }
            }
        });
        //保存分类
        $(document).on("click", "#save", function () {
            var url = basePath + "ea/productslaunch/sajax_ea_ProductTypeCRUD.jspa?";
            var formData = $("#typeForm").serialize();
            //formData = decodeURIComponent(formData, true);
            $.ajax({
                type: "get",
                url: url + formData,
                async: false,
                dataType: "json",
                success: function (data) {
                    delList = "";
                    $(".product_lei").text("");
                    $("#categoryId").val("");
                    $("#categoryName").val("");
                    alert("编辑成功！");
                }
            });
            $(this).text("编辑分类");
            $(this).removeAttr("id").addClass("redact");
            $(".product li .txt input").attr("readonly", "readonly");
            $(".product li .txt img").attr("src", "<%=basePath %>images/ea/production/ico_right.png").removeClass("det");
            $(".add").hide();
            $(".addCategorie").hide();
            pagenumber = 0;
            pagecount = 0;
        });

        $(".add").click(function () {
            $(".addCategorie .top ul").children("li").eq(2).show();
            if ($(".product").find(".typeName").size() == 0) {
                $(".product").html("");
            }

            var li = "<li><div class='txt'><input class='typeName' type='text' value='' placeholder='请添加分类'><img src='<%=basePath %>images/ea/production/ico-delete.png' class='right det'></div></li>";
            $(".product").append(li);
            $(".product li").last().find("input").focus();
        });

        $(document).on("change", ".typeName", function () {
            console.info($(this).val());
            $(this).prev().prev().prev().prev().attr("name", "typeList[" + num + "].categoryKey");
            $(this).prev().prev().prev().attr("name", "typeList[" + num + "].categoryId");
            $(this).prev().prev().attr("name", "typeList[" + num + "].companyId");
            $(this).prev().attr("name", "typeList[" + num + "].status");
            $(this).attr("name", "typeList[" + num + "].categoryName");
            num++;
        });
    });

    $(document).on("click", "#back", function () {
        if (!$(".add").is(":hidden")) {
            $(this).next().next().text("编辑分类");
            $(this).next().next().removeAttr("id").addClass("redact");
            $(".product li .txt input").attr("readonly", "readonly");
            $(".product li .txt img").attr("src", "<%=basePath %>images/ea/production/ico_right.png").removeClass("det");
            $(".add").hide();
        }
        $(".addCategorie").hide();
        pagenumber = 0;
        pagecount = 0;
    })

    $(document).ready(function () {
        var string = '${prolist[0][5]}';
        var _temp = string.replace(">", "");
        var _t = _temp.replace(/[A-Za-z0-9]/g, "");
        $(".hangye_lei").text(_t);
        $("#ps").text('${prolist[0][10]}');
        var str = $("#ps").text();
        if (str != '') {
            $(".size_old").find("input").removeClass("input_xz");
            var t = str.split(",");
            for (var i = 0; i < t.length; i++) {
                var temp = t[i];
                $(".size_old").find("input").each(function () {
                    if ($(this).val() == temp) {
                        $(this).addClass("input_xz");
                    }
                });
            }
            $(".color").find(".input_xz").each(function () {
                colorvalue += $(this).val() + ",";
            });
            $(".size").find(".input_xz").each(function () {
                sizevalue += $(this).val() + ",,";
            });
            $("#colorvalue").val(colorvalue);
            $("#sizevalue").val(sizevalue);
        }
    });

    var myTouch = util.toucher(document.getElementById('abc'));

    myTouch.on('swipeLeft', function (e) {
        document.getElementById("right").click();
    }).on('swipeRight', function (e) {
        document.getElementById("left").click();
    });

    $(".main_hide").css("margin-bottom", $(window).height() * 0.1 + "px");


    // var num1=num2=num3=0
    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        if (clientWidth >= 960) {
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 8 + 'px';

        } else {
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    }

    //开关
    //$("#toggle--switch").prop("checked", true);
    $(".tiaoma_img").hide();


    $(document).on("click", "#jjdw", function () {
        $("#jjdw_xz").show();
    });

    $(document).on("click", "#jjdw_xz li", function () {
        $("#jjdw_xz").hide();
        $("#jjdw_p").text($(this).text());

        $("#jjdw_xz li").css("background", "#fff");
        $(this).css("background", "#f1f1f1");
    });

    $('.scroll').marquee({
        duration: 3000,
        //speed:50,
        // duplicated:true,
        //startVisible:true,
        delayBeforeStart: 0,
        //pauseOnCycle:true
    });
    // **对于询问如何在通过Ajax加载内容后使用该插件的人，请阅读此更新说明。
    //文档：https://gitcode.com/gh_mirrors/jq/jQuery.Marquee?utm_source=artical_gitcode&index=bottom&type=card&
    // 所以现在你可以像这样启动插件 var $mq = $('.marquee').marquee()，然后可以暂停、恢复、切换（暂停/恢复），以及销毁方法，例如要从元素上移除跑马灯插件，只需使用 $mq.marquee('destroy')。同样，你可以随时使用 $mq.marquee('pause') 来暂停跑马灯。

</script>
</body>
</html>