<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>确认报名</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/WFJClient/NEWjspcss/my.css"/>
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.min.js"></script>
    <style type="text/css">
        .confi .details {
            height: 3.5rem;
        }

        .confi .details img {
            width: 3rem;
            height: 3rem;
            float: left;
            margin: 0.3rem;
        }

        .confi .row li {
            line-height: 1.5rem;
            overflow: hidden;
            border-bottom: 1px solid #ddd;
            padding: 0 0.5rem;
        }

        .confi .row li .iptinput {
            /*width: 10rem;*/
            height: 1.5rem;
            line-height: 0.5rem;
            border: none;
            float: right;
            font-size: 0.7rem;
            text-align: right;
        }

        .con {
            overflow: auto;
        }

        .confi .name {
            overflow: hidden;
            padding: 0.2rem;
        }

        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 0.5);
        }

        .content ul input.noviceAddress {
            text-overflow: ellipsis;
        }

        .content ul .term_li {
            font-size: 0.5rem;
            line-height: 1rem;
            margin-top: 0.5rem;
            padding-bottom: 0.5rem;
        }

        .content ul li div.sex {
            float: left;
            padding-right: .3rem
        }

        .content ul li div.sex .img-01 {
            display: inline-block;
        }

        .content ul li div.sex .img-02 {
            display: none;
        }

        .content ul li div.sex img {
            width: 0.8rem;
        }

        .content ul li.active div.sex .img-01 {
            display: none
        }

        .content ul li.active div.sex .img-02 {
            display: inline-block
        }

        .content ul li.active p {
            font-size: 1rem;
            line-height: 1rem;
            letter-spacing: .1rem;
        }

        .content ul li p input {
            width: 4rem;
            height: 1rem;
            line-height: 0.5rem;
            border: none;
            border-bottom: 1px solid #383737;
            font-size: 0.7rem;
        }

        .content ul li p input:nth-of-type(1) {

            width: 10rem;
            text-align: center;
        }

        /*----------------地址--------------*/
        .div-address {
            display: none;
            z-index: 9;
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            background-color: #fff;
        }

        .div-address .div-box .address-header > ul {
            padding: 0 0.5rem;
        }

        .div-address .div-box .address-header > ul li {
            float: left;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: 0.8rem;
            color: #000000;
        }

        .div-address .div-box .address-header > ul li:first-of-type {
            width: 20%;
        }

        .div-address .div-box .address-header > ul li:first-of-type img {
            width: 0.5rem;
        }

        .div-address .div-box .address-header > ul li:nth-of-type(2) {
            width: 58%;
            text-align: center;
        }

        .div-address .div-box .address-header > ul li:nth-of-type(3) {
            width: 20%;
            text-align: right;
            font-size: 0.6rem;
        }

        .div-address .div-box .address-header > ul li:nth-of-type(3) img {
            width: 0.75rem;
        }

        .div-address .div-box .div-isok {
            left: 0;
            width: 100%;
            background-color: #fff;
            padding: .5rem 0;
        }

        .div-address .div-box .div-isok p {
            background-color: #f74c32;
            border-radius: 0.3rem;
            width: 14rem;
            margin: 0 auto;
            text-align: center;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: .6rem;
            color: #fff;
        }

        .div-address .div-con .div-name {
            border-bottom: .025rem solid #efe8e8;
            padding: .1rem .55rem .1rem .55rem;
        }

        .content .div-leixing {
            border-bottom: .025rem solid #eee;
            padding: 0 .75rem;
        }

        .div-address .div-con .div-name label {
            display: block;
            /*height: 2rem;*/
            line-height: 1.5rem;
            font-size: 0.9rem;
            color: #222;
        }

        .div-address .div-con .div-name input {
            height: 1.5rem;
            /* line-height: 2rem; */
            font-size: .65rem;
            color: #666;
            background-color: transparent;
            border: 0;
            width: 100%;
        }

        .div-address .div-con .div-only input {
            float: right;
            width: 80%;
            text-align: right;
        }

        .div-address .div-con .div-only label {
            display: inline;
        }

        .div-sxq {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, .4);
            z-index: 9;
        }

        .div-sxq .box {
            border-radius: 0.3rem;
            border: 0.025rem solid #f4f4f4;
            width: 10rem;
            background-color: #fff;
            margin: 8rem auto 0 auto;
            text-align: center;
        }

        .div-sxq .box ul {
            overflow-y: scroll;
            height: 15rem;
        }

        .div-sxq .box ul li {
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: .65rem;
            color: #222;
            border-bottom: .025rem solid #f4f4f4;
        }

        .div-sxq .box ul li.active {
            color: #f74c30
        }

        /*----------------end--------------*/

        .div-commit {
            z-index: 9;
            display: none;
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            background-color: rgba(0, 0, 0, 0.3);
        }

        .div-zffs {
            display: none;
            z-index: 9;
            position: fixed;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            background-color: #fff;
        }

        .div-zffs .box .wfj01_019_top_title {
            width: 100%;
            clear: both;
            float: left;
        }

        .div-zffs .wfj01_019_top_title .wfj01_019_top_link {
            width: 100%;
            background-color: #FFF;
            cursor: pointer;
            height: 1.5rem;
            line-height: 1.5rem;
        }

        .div-zffs .wfj01_019_top_title .wfj01_019_top_link li {
            font-size: 0.8rem;
            color: rgb(18 17 17);
            text-align: center;
            width: 50%;
            margin: 0 0;
            float: left;
            border-bottom: #f3eded solid 0.1rem;
            height: 100%;
        }

        .div-zffs .wfj01_019_top_title .wfj01_019_top_link li.active {
            border-bottom: #f74c31 solid 0.1rem;
            background-color: #ef9687;
            color: #121111;
        }

        .div-zffs .wfj01_019_top_title .wfj01_019_top_link li img {
            height: 0.8rem;
            float: left;
            padding-left: 1.5rem;
            padding-right: 0.2rem;
            padding-top: 0.4rem;
        }

        .div-zffs .wfj01_019_top_title .wfj01_019_top_link li label {
            float: left;
            padding-right: 2rem;
            height: 100%;
        }

        .div-zffs .box .div-title {
            display: none;
            padding-top: 2rem;
        }

        /*.div-zffs .title2 {
            display: none;
        }*/

        .div-zffs .div-dw {
            border-bottom: .1rem solid #f3eded;
            padding: 0 .5rem;
            float: left;
            width: 94%;
        }

        .div-zffs .div-dw img {
            height: 0.8rem;
            float: left;
            padding-left: 1.5rem;
            padding-right: 0.2rem;
            padding-top: 0.4rem;
        }

        .div-zffs .div-dw .fee-label {
            float: left;
            padding-right: 2rem;
            height: 1.5rem;
            line-height: 1.5rem;
        }

        .div-zffs .div-dw .div-left {
            float: left;
            width: 20%;
            padding: .3rem 0 .1rem 0;
        }

        .div-zffs .div-dw label {
            display: block;
            height: 0.6rem;
            line-height: 0.8rem;
            font-size: 0.6rem;
            color: #222;
        }

        .div-zffs .div-dw .div-left span {
            height: 0.8rem;
            line-height: 1rem;
            font-size: .5rem;
            color: #ccc;
            background-color: transparent;
            border: 0;
        }

        .div-zffs .div-dw .div-right {
            height: 2rem;
            line-height: 2rem;
            float: right;
            white-space: nowrap;
            width: 65%;
            padding-right: 0.1rem;
        }

        .div-zffs .div-dw .div-right input {
            border: 0;
            height: 100%;
            width: 70%;
            background-color: white;
            color: red;
            text-align: left;
            border-bottom: .1rem solid #242020;
            font-size: 1rem;
        }

        .div-zffs .div-bottom {
            left: 0;
            width: 94%;
            background-color: #fff;
            padding: 3rem 0.5rem;
            float: left;
        }

        .div-zffs .div-bottom p {
            background-color: #f74c32;
            border-radius: 0.3rem;
            width: 14rem;
            margin: 0 auto;
            text-align: center;
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: .7rem;
            color: #fff;
        }

        /*提示窗口agein*/
        .div-tingyong {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, .4);
            z-index: 10;
        }

        .div-tingyong .box {
            width: 12rem;
            margin: 30vh auto 0 auto;
        }

        .div-tingyong .box > p {
            border-top-left-radius: 0.3rem;
            border-top-right-radius: 0.3rem;
            background-color: #f74c32;
            height: 2rem;
            line-height: 2rem;
            font-size: .65rem;
            color: #fefefe;
            text-align: center;
            position: relative;
        }

        .div-tingyong .box > p img {
            position: absolute;
            width: 1.5rem;
            top: -8%;
            right: -2%
        }

        .div-tingyong .box .div-box p {
            background-color: #fff;
            height: 4rem;
            line-height: 4rem;
            font-size: .75rem;
            color: #222;
            text-align: center;
            border-bottom: 0.025rem solid #eee;
        }

        .div-tingyong .box .div-box div p {
            border-bottom-left-radius: .3rem;
            border-bottom-right-radius: 0.3rem;
            color: #666;
            position: relative;
            width: 100%;
            font-size: 0.7rem;
            height: 2rem;
            line-height: 2rem;
            background-color: #fff;
            text-align: center;
        }

        .div-tingyong .box .div-box div p:nth-of-type(1):before {
            content: "";
            position: absolute;
            height: 100%;
            width: .025rem;
            right: .025rem;
            background-color: #eee
        }

        .div-xy {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, .4);
            z-index: 9;
        }

        .div-xy .box {
            border-radius: 0.3rem;
            border: 0.025rem solid #f4f4f4;
            width: 13rem;
            background-color: #fff;
            margin: 8rem auto 0 auto;
            text-align: center;
        }

        .div-xy .box ul {
            overflow-y: scroll;
            height: 11rem;
        }

        .div-xy .box ul li {
            height: 1.5rem;
            font-size: .65rem;
            color: #222;
            line-height: 1.5rem;
            overflow: hidden;
            padding: 0 0.2rem;
        }

        .div-xy .box ul li .left{
            font-size: 0.65rem;
        }

        .div-xy .box ul li .iptinput {
            height: 1.3rem;
            line-height: 0.5rem;
            border: none;
            float: right;
            font-size: 0.7rem;
            text-align: right;
            width: 65%;
            border-bottom: 0.01rem solid #565353;
        }

        .div-xy .box ul li .bottom_xy {
            margin: 0.5rem;
            background-color: red;
            color: white;
            border-radius: 0.3rem;
            height: 1rem;
            line-height: 1rem;
        }
        #loading {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9999;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #loading:before {
            content: "";
            width: 30px;
            height: 30px;
            border: 3px solid #fff;
            border-top-color: transparent;
            border-radius: 50%;
            animation: spin 1s ease-in-out infinite;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }
    </style>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;">确认报名</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <form id="enrollSubmit">
        <div class="content">
            <div class="con confi">
                <div class="name">
                    <img src="<%=basePath %>${logo == null?"st/new_images/ico-name.png":logo}">
                    <h5>${com.companyName}</h5>
                </div>
                <div class="details">
                    <c:choose>
                        <c:when test="${priceType=='4'}"><%--特价活动--%>
                            <span class="tj" style="z-index:2;"><i></i></span>
                        </c:when>
                        <c:when test="${priceType=='3'}"><%--促销活动--%>
                            <span class="cx" style="z-index:2;"><i></i></span>
                        </c:when>
                        <c:when test="${priceType=='2'}"><%--VIP价--%>
                            <span class="vip" style="z-index:2;"><i></i></span>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                    <img src="<%=basePath%>${photo}">
                    <div class="text">
                        <h5>${goodsName}</h5>
                        <%--<p class="remark">备注信息:${remark}</p>--%>
                        <div class="btm" style="display:none;">
                            <span id="enrollfee" style="display: none"></span>
                            <p class="money">&yen;<span>0</span></p>
                            <p class="number">x1<span></span></p>
                        </div>
                    </div>
                </div>
                <c:if test="${ptlist ne null&&fn:length(ptlist)>0 }">
                    <%--<hr style="margin: 0 0.6rem; border-top: 1px solid #ddd;">--%>
                    <div class="zs-goods">
                        <img src="<%=basePath%>/images/WFJClient/Newjspim/zs-goods.png">
                        <h2>促销赠送商品</h2>
                        <div class="clearfix"></div>
                    </div>
                    <c:forEach items="${ptlist }" var="entity" varStatus="status">
                        <%--<hr style="margin: 0 0.6rem; border-top: 1px solid #ddd;">--%>
                        <div class="con-ord_grd">
                            <input type="hidden" value="${entity[1] }" id="ptppid"/>
                            <input type="hidden" value="${entity[5] }" class="companyId"/>
                            <img style="width: 4.6rem; height: 3.6rem;" src="<%=basePath %>${entity[3] }">
                            <div class="con-ord_grd_txt">
                                <span>${entity[0] }</span>
                                <h3>${ptstandard[status.index]=='规格暂无'?"":ptstandard[status.index] }</h3>
                                <h4>促销赠品产品</h4>
                                <p>x1</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </c:forEach>
                    <input type="hidden" value="" name="ptppid"/>
                    <input type="hidden" value="" name="companyId"/>
                    <input type="hidden" value="" name="ptmorre"/>
                    <input type="hidden" value="" name="ptstandard"/>
                </c:if>
                <ul class="row">
                    <li style="display: none;padding-top: 0.2rem;" class="personDIV">
                        <p class="left">学员照片</p>
                        <img class="iptinput" id="personImage" onclick="setImage()" src="<%=basePath %>${personImage}" style="width: 30px; height: 30px;">
                    </li>
                    <li onclick="applicationdiv()">
                        <p class="left">学员姓名</p>
                        <input type="text" id="noviceName" name="noviceName" class="iptinput"
                               value="${realname}" autocomplete="off" readonly="readonly"/>
                        <input type="hidden" id="custype" value="${custype}"/>
                    </li>
                    <li onclick="applicationdiv()">
                        <p class="left">学员电话</p>
                        <input type="text" id="novicePhone" name="novicePhone" class="iptinput"
                               value="${phone}" autocomplete="off" readonly="readonly"/>
                    </li>
                    <li onclick="applicationdiv()">
                        <p class="left">学员身份证</p>
                        <input type="text" value="${staffIdentityCard}" name="enrollForm.idCard" id="idCard" class="iptinput" autocomplete="off" readonly="readonly">
                    </li>
                    <li class="">
                        <p class="left">常住地址</p>
                        <input type="text" id="noviceAddress" name="noviceAddress" class="noviceAddress iptinput"
                               readonly/>
                    </li>
                    <li class="">
                        <p class="left">业务员</p>
                        <input type="text" class="iptinput marketing" id="marketing"
                               value="${mname} ${mname==mphone?"":(mphone)}" readonly>
                        <input type="hidden" class="iptinput msccid" id="msccid" value="${msccid}">
                    </li>
                    <li class="coach" onclick="selectPeo(1,null)">
                        <p class="left">找教练员</p>
                        <a href="#;">
                            <span class="coach_"></span>
                            <span style="display: none" id="coach_"></span>
                            <img src="<%=basePath %>st/new_images/ico-right.png">
                        </a>
                    </li>
                    <li class="car" onclick="coachByCar()">
                        <p class="left">找教练车</p>
                        <a href="#;">
                            <span class="car_"></span>
                            <span style="display: none" id="car_"></span>
                            <img src="<%=basePath %>st/new_images/ico-right.png"/>
                        </a>
                    </li>
                    <li class="site" onclick="findSite()">
                        <p class="left">找场地</p>
                        <a href="#;">
                            <span class="site_"></span>
                            <span id="site_" style="display: none"></span>
                            <img src="<%=basePath %>st/new_images/ico-right.png"/>
                        </a>
                    </li>
                    <li class="li-zffs">
                        <p class="left">支付方式</p>
                        <a href="#;">
                            <span class="zffs_"></span>
                            <span style="display: none"></span>
                            <img src="<%=basePath %>st/new_images/ico-right.png"/>
                        </a>
                    </li>
                    <%--<li class="term_li" style="display: none;font-size: 0.8rem;">
                        <p>
                            <span class="zffs-span"></span>:
                            <br>
                            <span>${com.companyName}</span>单位收管理等费用金额<span class="glFee-span"></span>元、
                        </p>
                        <p class="onlyfee-p">
                            另<span class="operatingStaffName-span"></span>同志收实操作培训费金额
                            <span class="operatingFee-span"></span>元、
                            两项合计金额
                            <span class="sumfee-span"></span>元、收款附件.
                        </p>
                        <p class="timefee-p">
                            操作费以<span style="color: red;">计时收费</span>方式收取
                        </p>
                    </li>--%>
                    <%--<li class="term_li" id="01">
                        <div class="sex">
                            <img class="img-01"
                                 src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png"/>
                            <img class="img-02"
                                 src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"/>
                        </div>
                        <p>
                            合起付:
                            ${com.companyName}单位收管理等费用金额<span class="glFee"></span>元、
                            另<input type="text" class="term_iptin operatingStaffName" disabled="disabled" readonly/>
                            同志收实操作培训费金额
                            <input type="number" class="term_iptin operatingFee" disabled="disabled" style="color: red;"/>元、
                            两项合计金额
                            <span class="sumfee"></span>元、付款附件.
                        </p>
                    </li>
                    <li class="term_li" id="02">
                        <div class="sex">
                            <img class="img-01"
                                 src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png"/>
                            <img class="img-02"
                                 src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"/>
                        </div>
                        <p>
                            分开付:
                            ${com.companyName}单位收管理等费用金额<span class="glFee"></span>元、
                            另<input type="text" class="term_iptin operatingStaffName" disabled="disabled" readonly/>
                            同志收实操作培训费金额
                            <input type="number" class="term_iptin operatingFee" disabled="disabled" style="color: red;"/>元、
                            两项合计金额
                            <span class="sumfee"></span>元、收款附件.
                        </p>
                    </li>--%>
                </ul>
            </div>
            <div class="btn">
                <p>
                    ${(param.vipmoney ne null&&param.vipmoney ne "")?'会员专享价：':'总金额：'}
                    <span>&yen;</span>
                    <span class="mey"></span>
                </p>
                <button type="button" onclick="oederBtn()">确认订单</button>
            </div>
        </div>
        <input type="hidden" name="priceType" value="${priceType}" id="priceType"/>
        <input type="hidden" name="activityid" value="${activityid}" id="activityid"/>
        <input type="hidden" name="enrollForm.ppID" value="${ppid}"/>
        <input type="hidden" name="enrollForm.coach"/>
        <input type="hidden" name="enrollForm.suppout"/>
        <input type="hidden" name="enrollForm.staffId" value="${staffId}"/>
        <input type="hidden" name="enrollForm.director"/>
        <input type="hidden" name="enrollForm.companyID" value="${companyID}"/>
        <input type="hidden" name="enrollForm.licenceType" value="${categoryName}"/>
        <input type="hidden" name="enrollForm.price"/>
        <input type="hidden" name="enrollForm.carNumber"/>
        <input type="hidden" name="enrollForm.siteID"/>
        <input type="hidden" name="enrollForm.status"/>
        <input type="hidden" name="staff.reference" id="reference"/>
        <input type="hidden" name="staff.staffName" id="staffName"/>
        <input type="hidden" name="enrollForm.ppName" value="${goodsName}"/>
        <input type="hidden" name="enrollForm.coachStaffID"/>
        <input type="hidden" name="enrollForm.coachSccID"/>
        <input type="hidden" name="enrollForm.coachName"/>
        <input type="hidden" name="enrollForm.suppoutName"/>
        <input type="hidden" name="enrollForm.directorName"/>
        <input type="hidden" name="enrollForm.companyName" value="${com.companyName}"/>
        <input type="hidden" name="enrollForm.siteName"/>
        <input type="hidden" name="enrollForm.staffAddress" /> //学员地址
        <input type="hidden" name="enrollForm.operatingStaffId"/> //操作费收款人id
        <input type="hidden" name="enrollForm.operatingStaffName"/> //操作费收款人
        <input type="hidden" name="enrollForm.operatingSccid"/> //操作费收款人sccid
        <input type="hidden" name="enrollForm.operatingAcount"/> //操作费收款人帐号
        <input type="hidden" name="enrollForm.operatingFee" class="iptinput"/> //操作费
        <input type="hidden" name="enrollForm.payMethod" class="iptinput"/> //支付方式 01:合并支付 021:分开支付-一次性结清 022：分开支付-计时收费
        <input type="hidden" name="enrollForm.reserved1" class="reserved1"/>
        <input type="hidden" name="enrollForm.reserved2" class="reserved2"/>
        <input type="hidden" id="deviceList" class="deviceList" name="deviceList" value="${deviceList}"/>
    </form>
</div>
<%--学员信息--%>
<div class="div-xy">
    <div class="box">
        <ul>

            <div id="headDIV" class="headDIV" style="display: none;">
               <li style="padding-top: 0.5rem;">
                   <p class="left">人脸照片：</p>
                   <img id="headImage" src="<%=basePath %>${personImage}" style="width: 30px; height: 30px;margin-left: -140px;">
                   <input  type="file" id="headImageFile" accept="image/jpg,image/png;capture=camera" style="margin-left:-30px;position: absolute;width: 2rem;height: 1.6rem;float:right;margin-right:-5rem;opacity: 0;">
               </li>
            </div>
            <li>
                <p class="left">学员姓名：</p>
                <input type="text" name="noviceName" class="noviceName iptinput" autocomplete="off" style="text-align: left;"/>
            </li>
            <li>
                <p class="left">学员电话：</p>
                <input type="text" name="novicePhone" class="novicePhone iptinput" autocomplete="off" style="text-align: left;"/>
            </li>
            <li>
                <p class="left">学员身份证：</p>
                <input type="text" name="idCard" class="idCard iptinput" autocomplete="off" style="text-align: left;"/>
            </li>
            <li>
                <p class="left">识别身份证自动获取</p>
                <img id="photoPositive" src="<%=basePath%>images/sm.jfif" style="width: 25px; height: 25px;">
                <input class="photoPositiveInfo" type="file" id="photoPositiveInfo" accept="image/jpg,image/png;capture=camera" style="margin-left:-40px;position: absolute;width: 2rem;height: 1.3rem;float:right;margin-right:-5rem;opacity: 0;">
            </li>
            <li>
                <p class="bottom_xy">确定</p>
            </li>
        </ul>
    </div>
</div>

<!--提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep" style="white-space: pre-wrap;"></p>
            <input type="hidden" class="title-input"/>
            <div class="clearfix">
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>

<!--找教练员、客服、主管-->
<div class="body3">
    <header>
        <ul>
            <li style="width: 10%;">
                <img src="<%=basePath %>st/images/left_jt.png">
            </li>
            <li style="width: 80%;"><span id="selectPeo"></span></li>
            <li style="width: 10%;"></li>
        </ul>
    </header>
    <div class="content_hidden">
        <div class="content">
            <div class="search_frd_ search_coach">
                <div class="search_frd">
                    <input type="text" class="search">
                    <div class="search_" id="search_text">
                        <img src="<%=basePath %>st/images/ico-search.png" alt="">
                        <p></p>
                    </div>
                </div>
            </div>
            <div class="con">
                <ul class="coach_list" id="cos_list"></ul>
            </div>
        </div>
    </div>
</div>
<!--找教练车-->
<div class="body4">
    <header>
        <ul>
            <li style="width: 10%;">
                <img src="<%=basePath %>st/images/left_jt.png">
            </li>
            <li style="width: 80%;">选择教练车</li>
            <li style="width: 10%;"></li>
        </ul>
    </header>
    <div class="content_hidden">
        <div class="content">
            <div class="search_frd_ search_coach">
                <div class="search_frd">
                    <input type="text" class="search">
                    <div class="search_">
                        <img src="<%=basePath %>st/images/ico-search.png" alt="">
                        <p>搜索车辆型号</p>
                    </div>
                </div>
            </div>
            <div class="con">
                <ul class="coach_list" id="car_list"></ul>
            </div>
        </div>
    </div>
</div>
<!--找场地-->
<div class="body7">
    <header>
        <ul>
            <li style="width: 10%;">
                <img src="<%=basePath %>st/images/left_jt.png">
            </li>
            <li style="width: 80%;">选择场地</li>
            <li style="width: 10%;"></li>
        </ul>
    </header>
    <div class="content_hidden">
        <div class="content">
            <div class="search_frd_ search_coach">
                <div class="search_frd">
                    <input type="text" class="search">
                    <div class="search_">
                        <img src="<%=basePath %>st/images/ico-search.png" alt="">
                        <p>搜索场地名称</p>
                    </div>
                </div>
            </div>
            <div class="con">
                <ul class="coach_list" id="site_list"></ul>
            </div>
        </div>
    </div>
</div>
<!-- 地址 -->
<div class="div-address div-data">
    <div class="div-box">
        <div class="address-header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="http://localhost:8080/images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>地址</li>
                <li class="keep"></li>
            </ul>
        </div>
        <div class="div-con clearfix address">
            <div class="div-isok">
                <p class="isok">
                    确定
                </p>
            </div>
            <div class="line" style="height:7.55px;background-color: #eee"></div>
            <div class="div-name div-only address-div" title="省">
                <label>省</label>
                <input type="text" readonly placeholder="选择省级区域" id="s" class="notnull"/>
                <input type="hidden" id="sid" class="c"/>
            </div>
            <div class="div-name div-only address-div" title="县">
                <label>县</label>
                <input type="text" readonly placeholder="选择县级区域" id="x" class="notnull"/>
                <input type="hidden" id="xid" class="c"/>
            </div>
            <div class="div-name div-only address-div" title="区">
                <label>区</label>
                <input type="text" readonly placeholder="选择区级区域" id="q" class="notnull"/>
                <input type="hidden" id="qid" class="c"/>
            </div>
            <div class="div-name">
                <label>详细地址</label>
                <input type="text" placeholder="请填写详细地址" id="xx" class="notnull" autocomplete="off"/>
            </div>
        </div>
    </div>
</div>
<%--省、县、区数据加载--%>
<div class="div-sxq">
    <input type="hidden" id="type-input"/>
    <div class="box">
        <ul></ul>
    </div>
</div>

<%--支付方式--%>
<div class="div-zffs" style="height: 100%;">
    <div class="box" style="height: 100%;overflow-y: scroll;">
        <header>
            <ul>
                <li style="width: 10%;">
                    <img src=""><%--<%=basePath %>st/images/left_jt.png--%>
                </li>
                <li style="width: 80%;">支付方式</li>
                <li style="width: 10%;"></li>
            </ul>
        </header>
        <div class="wfj01_019_top_title">
            <ul class="wfj01_019_top_link left">
                <li id="title1" class="title-li">
                    <img src="<%=basePath %>/images/ea/office/contract/selectp/dxseleted.png" class="title1-y title-y title-img" style="display: none;"/>
                    <img src="<%=basePath %>/images/ea/office/contract/selectp/dxwselet.png" class="title1-n title-n title-img" style="display: inline;"/>
                    <label class="title1-label inline">合并付</label>
                </li>
                <li id="title2" class="active title-li">
                    <img src="<%=basePath %>/images/ea/office/contract/selectp/dxseleted.png" class="title2-y title-y title-img" style="display: inline;"/>
                    <img src="<%=basePath %>/images/ea/office/contract/selectp/dxwselet.png" class="title2-n title-n title-img" style="display: none;"/>
                    <label class="title2-label inline">分开付</label>
                </li>
            </ul>
        </div>
        <div id="hbf" class="div-title1 div-title"  style="height: 100%;">
            <div class="div-dw title2">
                <img src="<%=basePath %>/images/ea/office/contract/selectp/dxseleted.png" class="onlyfee-y"
                     style="display: inline;"/>
                <img src="<%=basePath %>/images/ea/office/contract/selectp/dxwselet.png" class="onlyfee-n"
                     style="display: none;"/>
                <label class="onlyfee-label fee-label">一次性结清</label>
                <img src="<%=basePath %>/images/ea/office/contract/selectp/dxseleted.png" class="timefee-y"
                     style="display: none;"/>
                <img src="<%=basePath %>/images/ea/office/contract/selectp/dxwselet.png" class="timefee-n"
                     style="display: inline;"/>
                <label class="timefee-label fee-label">计时收费</label>
            </div>
            <%--<div class="div-dw">
                <div class="div-left">
                    <label for="">管理费收款单位</label>
                    <span class="zffs-span">报名所在驾校</span>
                </div>
                <div class="div-right" id="div-dw">
                    <input type="text" class="term_iptin" autocomplete="off" disabled="disabled"
                           readonly value="${com.companyName}" placeholder="报名所在驾校"/>
                </div>
            </div>
            <div class="div-dw">
                <div class="div-left">
                    <label for="">管理费金额</label>
                    <span class="zffs-span">单位：元</span>
                </div>
                <div class="div-right" id="div-glf">
                    <input type="text" class="term_iptin glFee" autocomplete="off" disabled="disabled" readonly/>
                </div>
            </div>
            <div class="div-dw onlyFee">
                <div class="div-left">
                    <label for="">操作费收款人</label>
                    <span class="zffs-span">通过选择的教练查找绑定车辆的责任人</span>
                </div>
                <div class="div-right" id="div-jl">
                    <input type="text" class="term_iptin operatingStaffName" autocomplete="off" disabled="disabled"
                           readonly/>
                </div>
            </div>--%>
            <div class="div-dw onlyFee" style="border: 0; padding: 1rem 0.5rem;">
                <div class="div-left">
                    <label for="">操作费金额</label>
                    <%--<span class="zffs-onlyFee-span">单位：元</span>--%>
                </div>
                <div class="div-right" id="div-czf">
                    <input type="number" class="term_iptin operatingFee" autocomplete="off"/>
                    <span style="font-size: 1rem;">元</span>
                </div>
            </div>
            <%--<div class="div-dw">
                <div class="div-left">
                    <label for="">合计金额</label>
                    <span class="zffs-span">管理费+实操费  (单位：元)</span>
                </div>
                <div class="div-right" id="div-sum">
                    <input type="text" class="term_iptin sumfee" autocomplete="off" disabled="disabled" readonly/>
                </div>
            </div>--%>
            <div class="div-dw term_li" style="font-size: 0.8rem; border: 0;">
                <p>
                    <span class="zffs-span"></span>:
                    <br>
                    <span>${com.companyName}</span>单位收管理等费用金额<span class="glFee-span"></span>元、
                </p>
                <p class="onlyfee-p">
                    另<span class="operatingStaffName-span"></span>同志收实操作培训费金额
                    <span class="operatingFee-span"></span>元、
                    两项合计金额
                    <span class="sumfee-span"></span>元、收款附件.
                </p>
                <p class="timefee-p">
                    操作费以<span style="color: red;">计时收费</span>方式收取
                </p>
            </div>
            <div class="div-bottom last-bottom">
                <p class="submitAudit">
                    保存
                </p>
            </div>
        </div>
    </div>
</div>

<%--支付--%>
<div class="div-commit">
    <div class="wfj11_015_buy_commit">
        <div class="wfj11_015_need">
            <div class="wfj11_015_width">
                <ul>
                    <li class="left">需支付：</li>
                    <li class="right"><span>￥</span><span class="xzf"></span></span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="wfj11_015_allbay">
            <div class="wfj11_015_width">
                <table>
                    <tr>
                        <td colspan="2">选择支付方式</td>
                    </tr>
                    <tr class="wfj11_015_choice">
                        <td align="left"><img class="all_pay"
                                              src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png"/>
                        </td>
                        <td class="second" align="right"><img
                                src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
                                width="24" height="24" name="1"/>
                        </td>
                    </tr>
                    <tr class="wfj11_015_choice wechat">
                        <td align="left">
                            <img class="all_pay" src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png"/>
                        </td>
                        <td class="second" align="right">
                            <img src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png" width="24" height="24"
                                 name="3"/>
                        </td>
                    </tr>
                    <%--<tr class="wfj11_015_choice">
                        <td align="left" style="font-size:18.875px;height:67.95px;line-height:45.3px;">
                            <img class="all_pay" src="http://www.impf2010.com:80//images/WFJClient/Newjspim/xxzz.png" style="height:30.2px;">
                        </td>
                        <td class="second" align="right" style="font-size:18.875px;height:67.95px;line-height:45.3px;">
                            <img src="http://www.impf2010.com:80//images/WFJClient/Newjspim/choice_02.png" width="24" height="24" name="4" style="height:22.65px;width:auto">
                        </td>
                    </tr>--%>
                    <tr>
                        <td colspan="2" align="center">
                            <div id="paycommit"
                                 onclick="zf()">确认支付
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath %>st/js/once_charge.js"></script>
<script type="text/javascript">
    var goodsName = '${goodsName}';
    var companyID = '${companyID}';
    var companyName = "${com.companyName}";
    var ppid = '${ppid}';
    var photo = '${photo}';
    var remark = '${remark}';
    var basePath = "<%=basePath %>";
    var pageType = "${param.pageType}";
    var vipmoney = "${param.vipmoney}";
    var price = "${price}";
    if (vipmoney != null && vipmoney != "") {
        price = vipmoney;
    }
    var sbtzfee = "${sbtzfee}";
    var brand = "${brand}";
    var licenceType = "${licenceType}";
    var categoryName = "${categoryName}";
    var categoryId = "${categoryId}";
    var model = "${product.model}";
    var journalNum = "${param.journalNum}";
    var mode = "${param.mode}";
    var totalMoney = "${param.totalMoney}";
    var totalNum = "${param.totalNum}";
    var cardNum = "${param.cardNum}";
    var paymentCode = "${param.paymentCode}";

    var sccId = "${param.sccId}";
    var posNum = "${param.posNum}";
    var staffId = "${staffId}";
    var ptppid = "";
    var ptstandard = "";
    var ddid = "";
    var zffs = 1;//选择的支付方式   默认为支付宝
    var token = "";
    var user1 = "";
    var sccid = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var supername = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSuperioragent():"" %>';
    var staffID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var openid = "${param.openid}";
    var mphone = "${mphone}";


</script>
<script>
    $(document).ready(function () {
        $("header").css("height", $(window).height() * 0.05 - 1 + "px");
        $("header").css("line-height", $(window).height() * 0.05 - 1 + "px");
        $(".content_hidden").attr("style", ";overflow: hidden;" + "height:" + $(window).height() * 0.96 + "px");
        $(".content").attr("style", "overflow: hidden;" + "height:" + $(window).height() * 0.96 + "px");
        $(".head_top").css("height", $(window).height() * 0.08 - 1 + "px");
        $(".head_top ul li").css("line-height", $(window).height() * 0.05 + "px");
        $(".head_top ul li:nth-child(1) dl").css("margin", $(window).height() * 0.015 + "px");
        $(".head_top ul li:nth-child(2) input").attr("style", "margin:" + $(window).height() * 0.015 + "px;margin-left:0;line-height:" + $(window).height() * 0.05 + "px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height", $(window).height() * 0.96 - $(".btn").height() + "px");
        $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.25 + "px");
        $("#prompt").find("div").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

        $(".wfj11_015_consignee").find("td").attr("style", "font-size:" + $(window).height() * 0.02 + "px;line-height:" + $(window).height() * 0.03 + "px;");
        $(".wfj11_015_consignee").attr("style", "padding:" + $(window).height() * 0.02 + "px 0; margin-bottom:" + $(window).height() * 0.01 + "px;");
        $(".wfj11_015_consignee").css("position", "relative");
        $(".img_ico").css({
            "height": $(".wfj11_015_consignee .wfj11_015_width").height() + "px",
            "top": $(window).height() * 0.02 + "px"
        })
        $(".img_ico>p").css({"height": $(".img_ico").height() + "px"})
        $(".img_ico1").css("left", $(window).height() * 0.02 + "px")

        $(".wfj11_015_need").attr("style", "height:" + $(window).height() * 0.06 + "px;line-height:" + $(window).height() * 0.06 + "px;");
        $(".wfj11_015_need").find("li").attr("style", "font-size:" + $(window).height() * 0.03 + "px;color:#000;");
        $(".wfj11_015_need").find("li").find("span").attr("style", "font-size:" + $(window).height() * 0.03 + "px;color:#F74C31;");
        $(".wfj11_015_allbay").find("td").attr("style", "font-size:" + $(window).height() * 0.025 + "px;height:" + $(window).height() * 0.09 + "px;line-height:" + $(window).height() * 0.06 + "px;");
        $(".wfj11_015_allbay").find("td").eq(0).css("height", $(window).height() * 0.05 + "px")
        $(".wfj11_015_allbay").find("tr").eq(5).css("height", $(window).height() * 0.1 + "px")
        $(".wfj11_015_allbay").find("td").find("img").attr("style", "height:" + $(window).height() * 0.03 + "px;width:auto");
        $(".wfj11_015_allbay").find("td").find(".all_pay").attr("style", "height:" + $(window).height() * 0.04 + "px;");
        $(".wfj11_015_allbay").find("td").find("#paycommit").attr("style", " width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:" + $(window).height() * 0.006 + "px; height:" + $(window).height() * 0.06 + "px; line-height:" + $(window).height() * 0.06 + "px;font-size:" + $(window).height() * 0.025 + "px;");

    });
</script>
<div id="loading"></div>
</body>
<script>
    $("#loading").fadeOut();
    var basePath = "<%=basePath%>";
    $('#photoPositiveInfo').change(function() {
        $("#loading").fadeIn();
        var formData = new FormData();
        var fileInput = document.getElementById('photoPositiveInfo');
        var img = document.getElementById('photoPositiveInfo');
        var file = fileInput.files[0];
        formData.append('photoPositiveInfo', file);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', basePath+'ea/mycenter/sajax_ea_getIdCardInfo.jspa', true);
        xhr.timeout = 10000;//10000毫秒
        xhr.onload = function() {
            if (this.status == 200) {
                const data=JSON.parse(this.responseText);
                if(data!=null){
                    const dataJSON = JSON.parse(data);
                    $(".noviceName").val(dataJSON.name);
                    $(".idCard").val(dataJSON.cardNumber);
                    $(".noviceAddress").val(dataJSON.address);
                }else{
                    $("#loading").fadeOut();
                    alert("自动获取失败，请手动输入！")
                }
            }
            $("#loading").fadeOut();
        };
        xhr.ontimeout = function() {
            $("#loading").fadeOut();
            alert("自动获取失败，请手动输入！")
        };
        xhr.send(formData);
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            document.getElementById("photoPositive").src = url;
        };
        reader.readAsDataURL(file);
    });

    $('#headImageFile').change(function() {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            document.getElementById("headImage").src = url;
            document.getElementById("personImage").src = url;
        };
        reader.readAsDataURL(file);
    })
</script>
</html>