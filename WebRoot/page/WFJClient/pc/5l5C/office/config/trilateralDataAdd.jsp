<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>三方-添加</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralData.css">
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <%--    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>--%>
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <%--    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralDataNew.css">--%>


    <%--    <script type="text/javascript" src="<%=basePath%>js/scMobile/qyrz/gdLocation.js"></script>--%>
    <%--    <script type="text/javascript" src="<%=basePath%>js/ea/human/gdLocationTrilateral.js"></script>--%>

    <%--    <script type="text/javascript"--%>
    <%--            src="https://webapi.amap.com/maps?v=1.4.15&key=358dde761a483ba6780ee510803f6108"></script><!--高德地图API-->--%>
    <%--    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>--%>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
        var cate = "${param.cate}";
        var dwLnglatX = "${param.dwLnglatX}";
        var dwLnglatY = "${param.dwLnglatY}";
    </script>
    <style>
        .div-name {
            /*height: 700px;*/
            overflow-y: auto;
        }

        .div-name p {
            display: block; /* 改为块级显示 */
            margin: 10px 0;
        }

        .div-name label {
            display: block; /* label 占据整行 */
            margin-bottom: 0.1rem; /* label 和 input 之间留空隙 */
            line-height: 1.5rem;
            font-size: .95rem;
            /*font-weight: bold;*/


        }

        .div-name input {
            /*width: 100%; !* input 占据整行宽度 *!*/
            padding: 8px;
            box-sizing: border-box; /* 包含padding在内的总宽度为100% */
            text-align: initial;
        }

        input[type="file"] {
            width: 100%;
            padding: 8px;
            /*border: 1px solid #ddd;*/
            border-radius: 4px;
            background-color: #fafafa;
        }

        .submit-button-container {
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            width: 100%;
            padding: 10px 0;
            background-color: #fff;
            border-top: 1px solid #eee;
            z-index: 1000;
        }

        .submit-button-container input[type="submit"] {
            width: 90%;
            margin: 0 auto;
            display: block;
            padding: 12px;
            font-size: 16px;
        }

        .search_button {
            float: right;
            margin-top: -20px;
        }

        .search-results-container {
            width: 98%;
            height: 100%;
            overflow-y: auto;
        }

        .result-table {
            margin: 10px 0;
        }

        .result-table th, .result-table td {
            /*text-align: left;*/
            vertical-align: middle;
        }

        /* 取消弹窗内容区域的默认边距 */
        .layui-layer-content {
            padding-top: 0 !important;
            margin-top: 0 !important;
        }


        /* 全局样式重置 */
        .layui-layer-content {
            padding: 0 !important;
            margin: 0 !important;
            border: none !important;
        }

        /* 搜索结果容器样式 */
        .search-results-container {
            height: 100%;
            overflow-y: auto;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* 表格样式 */
        .result-table {
            width: 100%;
            border-collapse: collapse;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        thead th {
            position: relative;
            top: 0;
            background-color: #f4f4f4;
        }

        .layui-layer .layui-layer-page {
            border-radius: 20px;
        }

        .div-name1 {
            border-bottom: .025rem solid #eee;
            /*padding: .625rem .75rem .5rem .75rem;*/
            clear: both;
            overflow: hidden;
            background-color: #fff;
            color: #222;
            font-size: .95rem;
        }

        .div-name1 .i-icon-right {
            /*font-size: 20px;*/
            float: right;
            margin-top: -40px;
            font-family: layui-icon !important;
            font-style: normal;
            -webkit-font-smoothing: antialiased;
        }

        .div-name1 .i-icon-right {
            /*font-size: 20px;*/
            float: right;
            margin-top: -40px;
        }

        select {
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #fff;
            text-align: left;

        }

        /*hangye*/
        .div-hangye {
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

        .div-hangye .div-box .hangye-header > ul {
            padding: 0 0.5rem;
        }

        .div-hangye .div-box .hangye-header > ul li {
            float: left;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: 0.8rem;
            color: #000000;
        }

        .div-hangye .div-box .hangye-header > ul li:first-of-type {
            width: 20%;
        }

        .div-hangye .div-box .hangye-header > ul li:first-of-type img {
            width: 0.45rem;
        }

        .div-hangye .div-box .hangye-header > ul li:nth-of-type(2) {
            width: 58%;
            text-align: center;
        }

        .div-hangye .div-box .hangye-header > ul li:nth-of-type(3) {
            width: 20%;
            text-align: right;
            font-size: 0.6rem;
        }

        .div-hangye .div-box .hangye-header > ul li:nth-of-type(3) img {
            width: 0.75rem;
        }

        .div-hangye .div-box .div-con .ul-left {
            height: 92vh;
            padding-top: 0.5rem;
            width: 35%;
            overflow-y: scroll;
            background-color: #f8f8f8;
            float: left;
        }

        .div-hangye .div-box .div-con .ul-left li {
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

        .div-hangye .div-box .div-con .ul-left li.active {
            color: #fb5858;
            background-color: #fff;
        }

        .div-hangye .div-box .div-con .div-right {
            float: left;
            width: 54.5%;
        }

        .div-hangye .div-box .div-con .div-right .ul-02,
        .div-hangye .div-box .div-con .div-right .ul-03,
        .div-hangye .div-box .div-con .div-right .ul-04 {
            width: 33%;
            text-align: center;
            float: left;
        }

        .div-hangye .div-box .div-con .div-right .ul-02 li,
        .div-hangye .div-box .div-con .div-right .ul-03 li,
        .div-hangye .div-box .div-con .div-right .ul-04 li {
            overflow-x: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: 0.7rem;
            color: #000000;
        }

        .div-hangye .div-box .div-con .div-right .ul-02 li.active,
        .div-hangye .div-box .div-con .div-right .ul-03 li.active,
        .div-hangye .div-box .div-con .div-right .ul-04 li.active {
            color: #fb5858;
        }

        .div-hangye .div-box .div-con .div-right .ul-03,
        .div-hangye .div-box .div-con .div-right .ul-04 {
            display: none;
        }

        .div-hangye .div-box .ul1 .ul-left {
            width: 35%;
        }

        .div-hangye .div-box .ul1 .div-right {
            width: 54.5%;
        }

        .div-hangye .div-box .ul1 .div-right .ul-02 {
            width: 50%;
            display: block;
        }

        .div-hangye .div-box .ul1 .div-right .ul-03 {
            width: 33%;
            display: none;
        }

        .div-hangye .div-box .ul1 .div-right .ul-04 {
            width: 33%;
            display: none;
        }

        .div-hangye .div-box .ul2 .ul-left {
            width: 35%;
        }

        .div-hangye .div-box .ul2 .div-right {
            width: 59.5%;
        }

        .div-hangye .div-box .ul2 .div-right .ul-02 {
            width: 50%;
        }

        .div-hangye .div-box .ul2 .div-right .ul-03 {
            width: 50%;
            display: block;
        }

        .div-hangye .div-box .ul2 .div-right .ul-04 {
            width: 0%;
            display: none;
        }

        .div-hangye .div-box .ul3 .ul-left {
            width: 30%;
        }

        .div-hangye .div-box .ul3 .div-right {
            width: 69.5%;
        }

        .div-hangye .div-box .ul3 .div-right .ul-02 {
            width: 30%;
        }

        .div-hangye .div-box .ul3 .div-right .ul-03 {
            width: 30%;
            display: block;
        }

        .div-hangye .div-box .ul3 .div-right .ul-04 {
            width: 30%;
            display: block;
        }

        /* 行业分类弹窗头部字体大小 */
        .div-hangye .hangye-header > ul li {
            font-size: 16px !important; /* 调整头部字体大小 */
        }

        /* 行业分类标题字体大小 */
        .div-hangye .hangye-header > ul li:nth-of-type(2) {
            font-size: 18px !important; /* 中间的标题文字更大些 */
            font-weight: bold;
        }

        /* 左侧菜单字体大小 */
        .div-hangye .div-box .div-con .ul-left li {
            font-size: 14px !important; /* 调整左侧列表字体大小 */
        }

        /* 右侧选项字体大小 */
        .div-hangye .div-box .div-con .div-right .ul-02 li,
        .div-hangye .div-box .div-con .div-right .ul-03 li,
        .div-hangye .div-box .div-con .div-right .ul-04 li {
            font-size: 13px !important; /* 调整右侧选项字体大小 */
        }

        /* 激活状态字体大小（如果需要不同样式） */
        .div-hangye .div-box .div-con .ul-left li.active {
            font-size: 14px !important;
        }

        .div-hangye .div-box .div-con .div-right .ul-02 li.active,
        .div-hangye .div-box .div-con .div-right .ul-03 li.active,
        .div-hangye .div-box .div-con .div-right .ul-04 li.active {
            font-size: 13px !important;
        }

        .content .div-img {
            position: relative;
            border-bottom: .025rem solid #eee;
            /*padding: 0 .75rem*/
        }

        .content .div-img .div-left {
            float: left;
            width: 50%;
            padding: .625rem 0 .5rem 0
        }

        .content .div-img .div-left label {
            display: block;
            height: 2.2rem;
            line-height: 2.2rem;
            font-size: .95rem;
            color: #222
        }

        .content .div-img .div-left input {
            height: 1.3rem;
            line-height: 1.3rem;
            font-size: .85rem;
            color: #ccc;
            background-color: transparent;
            border: 0;
            width: 100%;
        }

        .content .div-img #sdfFile {
            position: absolute;
            left: 0;
            width: 100%;
            height: 4rem;
            background-color: transparent;
            color: transparent;
            opacity: 0
        }

        .content .div-img #imgSdf {
            margin: .8rem 0 0 0;
            float: right;
            width: 2.1rem;
            height: 2.1rem;
        }
    </style>
</head>
<body>
<header>
    <div class="div-ul1">
        <ul class="clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li>
                三方平台推广--添加
            </li>
            <li>
            </li>
        </ul>
    </div>
</header>
<div class="content" style="width: 100%;">
    <s:form id="TrilateralDataAdd" action="ea_addTrilateralData.jspa" namespace="/ea/trilateral" method="POST"
            enctype="multipart/form-data" onsubmit="return validateForm()">
    <div class="div-name">
            <%--                    <div style="display: -webkit-box">--%>
            <%--                        <label style="margin-right: 8px;">公司名称</label>--%>
            <%--                        <section>--%>
            <%--                            <div class="search_wrapper">--%>
            <%--                                <img src="https://impf2010.com:443/images/scMobile/qyrz/pic_04.png" width="18" height="18">--%>
            <%--                                <input type="search" id="keywords" name="trilateralData.companyName"--%>
            <%--                                       placeholder="搜索企业/公司/商家/名称"--%>
            <%--                                       style=" font-size: 11pt !important; clear: right"/>--%>
            <%--                                <div id="clear_button">--%>
            <%--                                    清除--%>
            <%--                                </div>--%>
            <%--                                <div id="search_button">--%>
            <%--                                    搜索--%>
            <%--                                </div>--%>
            <%--                            </div>--%>
            <%--                        </section>--%>
            <%--                        <div id="allmap"></div>--%>
            <%--                    </div>--%>
            <%--        <div style="display: -webkit-box">--%>
            <%--            <label style="margin-right: 8px;">姓名</label>--%>
            <%--            <section>--%>
            <%--                <div class="search_wrapper">--%>
            <%--                        &lt;%&ndash;                    <img src="https://impf2010.com:443/images/scMobile/qyrz/pic_04.png" width="18" height="18">&ndash;%&gt;--%>
            <%--                    <input type="text" id="keywords" name="trilateralData.name"--%>
            <%--                           placeholder="请输入姓名"--%>
            <%--                           style=" font-size: 11pt !important; clear: right"/>--%>
            <%--                    <div id="search_button">--%>
            <%--                        搜索--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </section>--%>
            <%--            <div id="allmap"></div>--%>
            <%--        </div>--%>
        <p>
            <label><span style="color: #f74c32">*</span>姓名</label>
            <input required type="text" placeholder="请填写姓名" name="trilateralData.name" id="name"/>
            <input style=" align-self: flex-end;background-color: #007BFF; text-align: center; color: white ;border: none;  border-radius: 4px;"
                   type=button class="search_button"
                   value="搜索" onclick="searchByName()">
        </p>
        <p style="display: none">>
            <label>姓名</label>
            <input type="text" placeholder="请填写姓名" name="trilateralData.staffId" id="staffId"/>
        </p>
        <p>
            <label>公司名称</label>
            <input style="width: 100%" type="text" placeholder="请填写公司名称" name="trilateralData.companyName"
                   id="companyName"/>
        </p>
        <p style="display: none">
            <label>公司名称</label>
            <input style="width: 100%;" type="text" placeholder="请填写公司名称"
                   name="trilateralData.companyId"
                   id="companyId"/>
        </p>
        <p>
            <label>人员编号</label>
            <input style="width: 100%" type="text" placeholder="请填写人员编号" name="trilateralData.personnelID"
                   id="personnelID"/>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>性别</label>
            <input type="text" placeholder="请填写性别" name="trilateralData.sex" id="sex"/>
        </p>
        <p>
            <label>行业</label>
            <input class="hangye1" style="width: 100%" type="text" placeholder="请填写行业"
                   name="trilateralData.sector" id="sector"
                   value="${trilateralData.sector}"/>
            <input class="hangye1" style="display: none" type="text" placeholder="请填写行业"
                   name="trilateralData.sectorId" id="sectorId"
                   value="${trilateralData.sectorId}"/>
            <input class="hangye1" style="display: none" type="text"
                   placeholder="请填写行业"
                   name="trilateralData.sectorCode" id="sectorCode"
                   value="${trilateralData.sectorCode}"/>

        </p>
        <p>
            <label><span style="color: #f74c32">*</span>电话</label>
            <input style="width: 100%" required type="text" placeholder="请填写手机号" name="trilateralData.phone"
                   id="phone"/>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>注册平台名称</label>
            <select style="padding: 8px; box-sizing: border-box;" required
                    name="trilateralData.registrationPlatformName" id="registrationPlatformName">
                <option value="">请选择注册平台</option>
            </select>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>注册号</label>
            <input style="width: 100%" required type="text" placeholder="请填写注册号"
                   name="trilateralData.registrationNo"
                   id="registrationNo"/>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>注册账号</label>
            <input style="width: 100%" required type="text" placeholder="请填写注册账号"
                   name="trilateralData.registerAccount"
                   id="registerAccount"/>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>密码</label>
            <input style="width: 100%" required type="text" placeholder="请填写密码" name="trilateralData.pwd"
                   id="pwd"/>
        </p>
        <p>
            <label><span style="color: #f74c32">*</span>推广方式</label>
            <input style="width: 100%" required type="text" placeholder="微信 小红书 抖音...."
                   name="trilateralData.uploadProject"
                   id="uploadProject"/>
        </p>
        <p>
            <label>事由</label>
            <input style="width: 100%" type="text" placeholder="请填写推广事由" name="trilateralData.promotionReasons"
                   id="promotionReasons"/>
        </p>
        <p>
            <label>分配</label>
            <select style="padding: 8px; box-sizing: border-box;"
                    name="trilateralData.distributionState"
                    id="distributionState" disabled>
                <option value="0" selected>未分配</option>
                <option value="1">已分配</option>
            </select>
        </p>
            <%--        <p>--%>
            <%--            <label>服务跟踪</label>--%>
            <%--            <input type="text" placeholder="请填写服务跟踪" name="trilateralData.serviceTracking"--%>
            <%--                   id="serviceTracking"/>--%>
            <%--        </p>--%>
        <p>
            <label>责任人</label>
            <input style="width: 100%" type="text" placeholder="请填写责任人" name="trilateralData.businessPersonnel"
                   id="businessPersonnel"/>
        </p>
        <p>
            <label>私信</label>
            <input style="width: 100%" type="text" placeholder="请填写私信内容" name="trilateralData.privateMessage"
                   id="privateMessage"/>
        </p>

        <p>
            <label>审核</label>
            <select style="padding: 8px; box-sizing: border-box;"
                    name="trilateralData.audit1"
                    id="audit1" disabled>
                <option value="0" selected>未审核</option>
                <option value="1">已审核</option>
            </select>
        </p>

        <p>
            <label>托管</label>
            <select style="padding: 8px; box-sizing: border-box;"
                    name="trilateralData.trusteeship" id="trusteeship">
                <option value="">请选择是否托管</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </p>
        <p>
            <label>上传</label>
            <select style="padding: 8px; box-sizing: border-box;"
                    name="trilateralData.isUpload" id="isUpload">
                <option value="">请选择是否上传</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </p>
        <div class="div-name1 div-baseData div-contract " onclick="selectData('contractType')">
            <label>签订合同</label>
            <input type="text" placeholder="请选择签订合同" id="contractTypeName"
                   name="trilateralData.contractAddress" value="" readonly="">
            <input type="hidden" placeholder="请选择签订合同" id="contractTypeId" name="trilateralData.contractAddId"
                   value="" readonly="">
            <i class="layui-icon i-icon-right"></i>
        </div>

            <%--        <p>--%>
            <%--            <label><span style="color: #f74c32">*</span>录入三方平台证件附件</label>--%>
            <%--            <input style="width: 100%" type="file" name="trilateralData.attachmentAddress"--%>
            <%--                   id="attachmentAddress"--%>
            <%--                   accept=".pdf,.doc,.docx,.jpg,.jpeg,.png"/>--%>
            <%--        </p>--%>
        <p>
            <label><span style="color: #f74c32">*</span>附件名称</label>
            <input style="width: 100%" type="text" name="trilateralData.attachmentAddressName"
                   id="attachmentAddressName"
                   placeholder="请填写附件名称" value="${trilateralData.attachmentAddressName}"/>
        </p>
        <div class="div-img clearfix">
            <div class="div-left">
                <label for="">上传证件附件</label>
                <input type="text" name="trilateralData.attachmentAddress" value=""
                       disabled placeholder="支持doc/docx,xls/xlsx"/>
            </div>
            <input type="file" name="image" id="sdfFile" value="" onchange="f_change(this);"
                   accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
            <img alt="图片" src="<%=basePath%>images/ea/office/contract/uattach.png" id="imgSdf">
        </div>
    </div>
    <div class="submit-button-container">
        <input style="background-color: #e64d3d; text-align: center; color: white ;width: 100%;border: none;"
               type=submit
               value="确认">
        </s:form>
    </div>
    <%--<div>    <ul class="ul-list" id="ttsw_zbsj_list">--%>
    <%--</ul></div>--%>

    <%--    <div id="selectListContainer" class="select-list-container">--%>
    <%--        <ul id="selectList" class="select-list">--%>
    <%--        </ul>--%>
    <%--    </div>--%>

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
</body>
<script>
    var contractBool = false, type = "base", seletedType = "";
    var contractBool = false, type = "base", seletedType = "";
    var pageNumber = 1, totalPages = 1, loading = false, pageSize = 10, staffId = staffID;
    var videoList = [];
    $(function () {
        initCss();
        getBusinessPersonnel();
        getDictDataListByType();
    });

    function initCss() {
        $(".div-name").height($(window).height() - $(".submit-button-container").height() - 100);
        // $(".div-name").height($(window).height() - 200);
    }

    /**
     * 根据姓名搜索功能
     */
    function searchByName() {
        var name = $("#name").val();
        if (!name) {
            layer.msg("请输入姓名");
            return false;
        }
        var pageSize = 300;
        var pageNumber = 1;
        const param = new Array();
        param.push("pageNumber=" + pageNumber);
        param.push("&&pageSize=" + pageSize);
        // 发送AJAX请求到后台
        $.ajax({
            url: basePath + "ea/trilateral/sajax_ea_searchTrilateralByName.jspa?" + param.join("&&"),
            type: "post",
            data: {
                "trilateralData.name": name
            },
            dataType: "json",
            success: function cbf(data) {
                const codeList = eval("(" + data + ")");
                if (codeList == null) {
                    layer.msg(data.message || "暂无信息,请填写");
                } else {
                    // 获取返回的搜索结果数据（二维数组格式）
                    var trilateralDataArray = codeList.list;

                    if (trilateralDataArray && trilateralDataArray.length > 0) {
                        // 将二维数组转换为对象数组格式
                        var trilateralDataList = convertArrayToObject(trilateralDataArray);

                        if (trilateralDataList.length === 1) {
                            // 如果只有一个结果，直接填充到表单
                            fillFormData(trilateralDataList[0]);
                            layer.msg("找到1条匹配数据，已填充");
                        } else {
                            // 如果有多条结果，显示选择对话框
                            showMultipleResultsDialog(trilateralDataList);
                        }
                    } else {
                        layer.msg("未找到匹配的数据");
                    }
                }
            },

            error: function () {
                layer.msg("搜索请求失败");
            }
        });
    }

    function showMultipleResultsDialog(dataList) {
        var tableHtml = '<div class="search-results-container" style="height: 100%; overflow: auto; margin: 0; padding: 0;">';

        // 直接使用无边距的div替代h4
        tableHtml += '<div style="margin: 0; padding: 5px 10px 10px 15px; font-size: 14px; color: #333;">搜索结果 (' + dataList.length + ' 条记录)</div>';

        tableHtml += '<table class="result-table" style="width:100%; border-collapse: collapse; margin: 0; padding: 0;">';
        tableHtml += '<thead><tr style="background-color: #f5f5f5;"><th>姓名</th><th>电话</th><th>行业</th><th>公司名称</th><th>操作</th></tr></thead><tbody>';

        for (var i = 0; i < dataList.length; i++) {
            var item = dataList[i];
            tableHtml += '<tr class="data-list" style="border-bottom: 1px solid #ddd; height: 40px;">';
            tableHtml += '<td style="padding: 8px;">' + (item.name || '') + '</td>';
            tableHtml += '<td style="padding: 8px;">' + (item.phone || '') + '</td>';
            tableHtml += '<td style="padding: 8px;">' + (item.sector || '') + '</td>';
            tableHtml += '<td style="padding: 8px;">' + (item.companyName || '') + '</td>';

            tableHtml += '<td style="padding: 8px; display: none">' + (item.companyId || '') + '</td>';
            tableHtml += '<td style="padding: 8px;display: none">' + (item.staffId || '') + '</td>';


            tableHtml += '<td style="padding: 8px;"><button onclick="selectResult(' + i + ')" style="background-color: #007BFF; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer;">选择</button></td>';
            tableHtml += '</tr>';
        }
        tableHtml += '</tbody></table></div>';

        window.currentDataList = dataList;

        layer.open({
            type: 1,
            title: '请选择要填充的数据',
            area: ['90%', '500px'],
            content: tableHtml,
            btn: ['关闭'],
            success: function (layero, index) {
                $('.layui-layer-content', layero).css({
                    'height': '400px',
                    'padding-top': '0',
                    'margin-top': '0'
                });
            },
            yes: function (index) {
                layer.close(index);
            }
        });
    }


    /**
     * 选择某一条结果并填充到表单
     */
    function selectResult(index) {
        var selectedData = window.currentDataList[index]; // 需要先保存数据列表
        if (selectedData) {
            fillFormData(selectedData);
            layer.msg("数据已填充");
            // 关闭对话框
            layer.closeAll('dialog');
        }
    }

    /**
     * 将二维数组转换为对象数组
     * 数组索引含义：
     * [0] - 公司名称
     * [1] - 人员编号
     * [2] - 注册号
     * [3] - 员工ID
     * [4] - 姓名
     * [5] - 性别
     * [6] - 电话
     * [7] - 行业
     * [8] - 序号
     */
    function convertArrayToObject(arrayData) {
        var result = [];
        for (var i = 0; i < arrayData.length; i++) {
            var row = arrayData[i];
            result.push({
                companyName: row[0] !== null ? row[0] : '',      // 公司名称
                companyId: row[1] !== null ? row[1] : '',      // 公司名称
                personnelID: row[2] !== null ? row[2] : '',      // 人员编号
                staffId: row[3] !== null ? row[3] : '',          // 员工ID
                name: row[4] !== null ? row[4] : '',             // 姓名
                sex: row[5] !== null ? row[5] : '',              // 性别
                phone: row[6] !== null ? row[6] : '',            // 电话
                sector: row[7] !== null ? row[7] : '',           // 行业
                index: row[8] !== null ? row[8] : ''             // 序号
            });
        }
        return result;
    }


    /**
     * 将搜索结果数据填充到表单中
     */
    function fillFormData(data) {
        // 根据返回的数据结构，将值填充到对应的输入框
        if (data.companyName) {
            $("#companyName").val(data.companyName);
        }
        if (data.companyId) {
            $("#companyId").val(data.companyId);
        }
        if (data.personnelID) {
            $("#personnelID").val(data.personnelID);
        }
        if (data.name) {
            $("#name").val(data.name);
        }
        if (data.staffId) {
            $("#staffId").val(data.staffId);
        }
        if (data.sex) {
            $("#sex").val(data.sex);
        }
        if (data.sector) {
            $("#sector").val(data.sector);
        }
        if (data.phone) {
            $("#phone").val(data.phone);
        }


    }

    function getBusinessPersonnel() {
        $.ajax({
            url: basePath + "ea/trilateral/sajax_ea_getBusinessPersonnel.jspa",
            type: "post",
            dataType: "json",
            success: function cbf(data) {
                $("#businessPersonnel").val(data);
            },
            error: function () {
                layer.msg("");
            }
        });
    }

    function selectData(type) {
        if ("contractType" === type) {
            // 选择合同类型
            const param = {
                "titleName": "选择类型",
                "id": "7",
                "name": "4",
                "height": "40%",
                "inputId": "contractTypeId",
                "inputName": "contractTypeName",
                "type": "checkbox",
                "textLeft": "20%"
            };
            let url = basePath + "ea/contract/sajax_ea_getTempConType.jspa";
            getContractTempData(url, param);
        }
    }

    function getContractTempData(urlStr, param) {
        if (contractBool) {
            return false;
        }
        contractBool = true;
        eject.initData(param);
        t.url = urlStr;
        t.paramData = param;
        $.ajax({
            url: encodeURI(t.url),
            type: "get",
            async: true,
            dataType: "json",
            success: function (data) {
                const dataList = eval("(" + data + ")");
                const list = dataList.list;
                if (list.length > 0) {
                    eject.getDataOpen(list, param);
                    var isSet = dataList.isSet;
                    for (var i = 0; i < list.length; i++) {
                        var obj = list[i];
                        var templatePath = obj[0];
                        var templateId = obj[1];
                        var fileType = obj[2];
                        var sysSet = obj[3];
                        var fileShowName = obj[4];
                        var temptId = obj[5];
                        var templateTypeName = obj[6];
                        var contractType = obj[7];
                        $("#select-" + contractType).find("label").after(
                            "<label style='float:right;margin-left:30px' onclick=deleteCompanyContract('" + templateId + "')>删除</label>" +
                            '<label style="float:right;margin-left:10px" onclick="tempview(\'' + contractType + '\',1,\'' + templatePath + '\',\'' + fileShowName + '\',\'' + temptId + '\',\'' + templateTypeName + '\',\'' + fileType + '\',\'' + sysSet + '\',\'' + templateId + '\',\'' + fileShowName + '\',\'' + isSet + '\',event)">编辑查看</label>');

                    }

                }
                contractBool = false;
            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    }

    function getDictDataListByType() {
        let url = basePath + "ea/trilateral/sajax_ea_getDictDataListByType.jspa?type=registrationPlatformName";
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            async: false,
            success: function (response) {
                const dataList = eval("(" + response + ")");
                // 清空现有选项
                $("#registrationPlatformName").empty()
                    .append('<option value="">请选择注册平台</option>');

                // 检查响应数据
                if (dataList && Array.isArray(dataList)) {
                    dataList.forEach(function (item) {
                        if (item.dictName) {
                            $("#registrationPlatformName").append(
                                $('<option>', {
                                    value: item.dictName,
                                    text: item.dictName
                                })
                            );
                        }
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("加载注册平台数据失败:", error);
                layer.msg("加载注册平台数据失败");
            }
        });
    }

    function getProductTypeNew(pid, pvalue, ti, type) {
        var getcodeurl = basePath + "ea/scBudget/sajax_ea_getBusinessTypeByPID.jspa";
        $.ajax({
            url: encodeURI(getcodeurl),
            type: "get",
            async: true,
            dataType: "json",
            data: {
                codeID: pid
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var oList = member.codeList;
                var listhtml = "";
                var tii = ti + 1;
                var claname = "";
                var $divHtml;
                var str = [];
                if (null == oList || oList.length <= 0) {
                    if (type == 1 && ti != 5) {
                        $(".xiangmu2 #producttype").val($("#" + pid).find(".codeValue").text());
                        $(".xiangmu2 #productCode").val($("#" + pid).find(".codeSn").text());
                        $(".div-xiangmu").hide();
                        $(".xiangmu1").hide();
                        $(".xiangmu2").show();
                    }
                    return;
                }
                if (type == 1) {
                    claname = "xmfl-right";
                    $divHtml = $(".xmfl");
                } else if (type == 2) {
                    claname = "hyfl-right";
                    $divHtml = $(".hyfl");
                } else if (type == 3) {
                    claname = "wplb-right";
                    $divHtml = $(".wplb");
                }

                if (ti == 1) {
                    str.push("<ul class='ul-left'>");
                } else if (ti == 2) {
                    str.push("<div class='div-right " + claname + "'> <ul class='ul-02'>");
                } else if (ti == 3) {
                    str.push("<ul class='clearfix ul-03'>");
                } else if (ti == 4) {
                    str.push("<ul class='clearfix ul-04'>");
                }
                if (pvalue != null && pvalue != "") {
                    pvalue = pvalue + "\>";
                } else {
                    pvalue = "";
                }
                for (var i = 0; i < oList.length; i++) {
                    str.push("<li id='" + oList[i].typeId + "' onclick='getProductTypeNew(" + "\"" + oList[i].typeId + "\",\"" + pvalue + oList[i].typeNum + oList[i].typeName + "\"," + tii + "," + type + ")'>");
                    str.push("<span class='codeValue'>" + oList[i].typeName + "</span>");
                    str.push("<span class='codeSn' style='display: none'>" + oList[i].typeShowNum + "</span>");
                    str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeSn + oList[i].typeName + "</span>");
                    str.push("</li>");
                    //(" + oList[i].codeSn + ")
                }
                if (ti == 1) {
                    str.push("</ul>");
                    $divHtml.append(str.join(""));
                } else if (ti == 2) {
                    str.push("</ul></div>");
                    $divHtml.append(str.join(""));
                } else if (ti == 3 || ti == 4) {
                    str.push("</ul>");
                    $("." + claname + "").append(str.join(""));
                }
            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    }

    // 	显示
    $(document).on("click", ".hangye1,.hangye2", function () {
        $(".hyfl").empty();
        getProductTypeNew("", "", 1, 2);
        $(".div-hangye").show();
    });
    $(document).on("click", ".div-close", function () {
        $(".div-hangye").hide();
    });

    //行业
    // 	切换选项
    $(document).on("click", ".div-hangye .ul-left li", function () {
        $(".div-right").remove();
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul1");
    });
    // 	缩进
    $(document).on("click", ".div-hangye .div-right .ul-02 li", function () {
        $(".div-hangye .div-right .ul-03").remove();
        $(".div-hangye .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul2");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    $(document).on("click", ".div-hangye .div-right .ul-03 li", function () {
        $(".div-hangye .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul3");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-hangye .div-right .ul-04 li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $("#sector").val($(this).find(".codeValue").text());
        $("#sectorId").val($(this).attr("id"));
        $("#sectorCode").val($(this).find(".pvalue").text());
        $(".div-hangye").hide();
        $(".hyfl").empty();
    });
    var reader = new FileReader();

    function f_change(file) {

        var img = document.getElementById('imgSdf');

        var name = file.files[0].name;

        //读取File对象的数据
        reader.onload = function (evt) {
            //data:img base64 编码数据显示
            img.width = "100";
            img.height = "100";
            var ext = evt.target.result;
            var fileType = "M";
            if (name.indexOf("xls") != -1 || name.indexOf("xls") != -1) {
                ext = basePath + "images/ea/office/contract/excel-ext.png";
                fileType = "E";
            } else if (name.indexOf("doc") != -1 || name.indexOf("docx") != -1) {

                ext = basePath + "images/ea/office/contract/word.png";
                fileType = "W";
            } else if (name.indexOf("pdf") != -1 || name.indexOf("PDF") != -1) {
                ext = basePath + "images/ea/office/contract/PDF.png";
                fileType = "P";
            }
            img.src = ext;

            $("#fileType").val(fileType);
        }
        reader.readAsDataURL(file.files[0]);
    }

    function validateForm() {
        var fileInput = document.getElementById('sdfFile');
        var attachmentName = document.getElementById('attachmentAddressName');

        // 检查是否有文件被选择
        if (fileInput.files.length > 0) {
            // 如果有文件上传，则附件名称必填
            if (attachmentName.value.trim() === '') {
                layer.msg('请填写附件名称');
                return false;
            }
        }
        return true;
    }

    document.getElementById('sdfFile').addEventListener('change', function (e) {
        var attachmentNameInput = document.getElementById('attachmentAddressName');
        if (e.target.files.length > 0) {
            // 设置为必填并添加视觉提示
            attachmentNameInput.required = true;
            // 可以添加红色边框提示
            attachmentNameInput.style.borderColor = '#f74c32';
        } else {
            // 移除必填状态
            attachmentNameInput.required = false;
            attachmentNameInput.style.borderColor = '';
        }
    });
</script>
</html>
