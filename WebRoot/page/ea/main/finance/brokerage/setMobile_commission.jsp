<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/brokerage/setMobile_commission.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/container/mqttLock.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/ea/finance/brokerage/setMobile_commission.js"></script>
    <title>佣金设置</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var yjtype = "${param.yjtype}";
        var totalPct = parseFloat("${totalPct}") * 0.01 + 1;
        var priceType = "${param.priceType}";
        var originPage='${param.originPage}';
        var codeing = "${param.codeing}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li id="title_li">佣金</li>
        <li></li>
    </ul>
</header>
<form name="proForm" id="proForm" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>
    <input type="hidden" name="yjtype" value="${param.yjtype}"/>
    <input type="hidden" name="mapPro.ppid" id="ppid" value="${pRetail[12]}"/>
    <input type="hidden" name="mapPro.pname" id="pname" value="${pRetail[6]}"/>
    <input type="hidden" name="mapPro.priceid" id="priceid" value="${pRetail[0]}"/>
    <input type="hidden" name="mapPro.pricekey" id="pricekey" value="${pRetail[10]}"/>
    <input type="hidden" name="mapPro.state" id="state" value="${pRetail[11]}"/>
    <div class="content">
        <div class="div-name" id="product-div">
            <label for="">产品信息</label>
            <a href="#" class="main_inp_right ">
                <p class="pname-p">
                    <c:choose>
                        <c:when test="${pRetail[6]==null||pRetail[6]==''}">
                            请选择
                        </c:when>
                        <c:otherwise>
                            ${pRetail[6]}
                        </c:otherwise>
                    </c:choose>
                </p>
                <img src="<%=basePath%>st/new_images/ico-right.png"/>
            </a>
        </div>
        <div class="div-name">
            <label for="">成本价</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写成本价" name="mapPro.cb" class="isNotnull num_input sumNum"
                           value="${pRetail[2]}" maxlength="2" id="cb_input"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name companyName">
            <label for="">业务佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写成业务佣金" name="mapPro.yw" class="isNotnull num_input sumNum"
                           value="${pRetail[3]}" id="yw_input"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name staffName" id="investment">
            <label for="">设备投资类型</label>
            <a class="main_inp_right ">
                <p id="tz_type">
                    <c:choose>
                        <c:when test="${pRetail[5]=='01'}">
                            教练
                        </c:when>
                        <c:when test="${pRetail[5]=='02'}">
                            创客单车
                        </c:when>
                        <c:when test="${pRetail[5]=='03'}">
                            超市
                        </c:when>
                        <c:otherwise>
                            无
                        </c:otherwise>
                    </c:choose>
                </p>
                <input name="mapPro.tzt" id="tzt" type="hidden" value="${pRetail[5]}"/>
                <img src="<%=basePath%>st/new_images/ico-right.png"/>
            </a>
        </div>
        <div class="div-name">
            <label for="">设备投资代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写投资佣金" name="mapdl.p20170605KY3VAANZJG0000000003"
                           class="isNotnull num_input sumNum dl" id="p20170605KY3VAANZJG0000000003" value="${mapdl.p20170605KY3VAANZJG0000000003}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">设备安装代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写安装佣金" name="mapdl.p20170220ZVZR76B88M0000000017"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000017}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">贴牌代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写贴牌佣金" name="mapdl.p20170220ZVZR76B88M0000000016"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000016}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">省级代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写省级佣金" name="mapdl.p20170220ZVZR76B88M0000000018"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000018}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">县级代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写县级佣金" name="mapdl.p20170220ZVZR76B88M0000000019"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000019}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">村级代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写村级佣金" name="mapdl.p20170220ZVZR76B88M0000000020"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000020}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <div class="div-name">
            <label for="">客户积分代理佣金</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写客户积分" name="mapdl.p20170220ZVZR76B88M0000000022"
                           class="isNotnull num_input sumNum dl" value="${mapdl.p20170220ZVZR76B88M0000000022}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <c:if test="${totalPct>0}">
            <div class="div-name">
                <label for="">消费红包</label>
                <div class="main_inp_right">
                    <p>
                        <input type="number" placeholder="自动计算" name="xfhb" id="wrap-input"
                               class="isNotnull num_input"
                               value="${pRetail[1]*totalPct}"/>
                    </p>
                    <p>元</p>
                </div>
            </div>
        </c:if>
        <div class="div-name">
            <label for="">系统单价</label>
            <div class="main_inp_right">
                <p>
                    <input type="number" placeholder="请填写系统单价" name="mapPro.dj" id="price_input"
                           class="isNotnull num_input sumNum" value="${pRetail[1]}"/>
                </p>
                <p>元</p>
            </div>
        </div>
        <c:if test="${totalPct>0}">
            <div class="div-name">
                <label for="">加消费红包售价</label>
                <div class="main_inp_right">
                    <p>
                        <input type="number" placeholder="自动计算" name="xfhb" id="sum-price-input"
                               class="isNotnull num_input"
                               value="${pRetail[1]*1+totalPct}"/>
                    </p>
                    <p>元</p>
                </div>
            </div>
        </c:if>
        <div class="div-bottom">
            <p class="submitDraft">
                保存草稿
            </p>
            <p class="submitAudit tj">
                提交审核
            </p>
            <p class="submitUpdate">
                修改
            </p>
            <p class="submitIsOk">
                确定
            </p>
        </div>
    </div>
</form>

<!--温馨提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="tittle-p"></p>
            <div class="clearfix">
                <%--<p class="left close-tingyong">取消</p>--%>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>


<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt=""/>
        <p>保存中...</p>
    </div>
</div>

<%--投资类型--%>
<div class="investment">
    <div class="investment_con">
        <div>投资类型</div>
        <ul>
            <li value="01">教练车</li>
            <li value="02">创客单车</li>
            <li value="03">超市</li>
            <li value="00">无</li>
        </ul>
    </div>
</div>

<%--产品列表--%>
<div id="divList"
     style="display: none;position: absolute; top: 0%; width: 100%; height: 100%; background: rgb(255, 255, 255); z-index: 1001;">
    <header>
        <ul class="clearfix">
            <li id="close_li">
                <a target="_self" class="close-a">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li class="li-text"></li>
            <li></li>
        </ul>
    </header>
    <div class="content">
        <section class="sec-search">
            <label class="f-label">
                <img src="<%=basePath%>images/ea/office/contract/selectp/img_01.png"/>
            </label>
            <input type="text" id="search"/>
            <label class="n-label">
                <img id="sao" src="<%=basePath%>images/WFJClient/PersonalJoining/saoerweima.png"/>
            </label>
        </section>
        <section class="sec-ul">
            <ul class="ul-list">

            </ul>
        </section>
        <section class="sec-bottom">
            <p>
                确定
            </p>
        </section>
    </div>
</div>
</body>
</html>
