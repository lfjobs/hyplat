<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
    <base href="<%=basePath%>">

    <title>金币详情明细</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <style type="text/css">
        /*公共样式*/
        html, body {
            -moz-user-select: none;
            -khtml-user-select: none;
            user-select: none;
            font-size: 1rem;
        }

        body {
            margin: 0 auto;
            background-color: #fff;
            width: 100%;
        }

        html, body, div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, h6, form, input, select, button, textarea, iframe, table, th,
        td {
            margin: 0 0;
            padding: 0px 0px;
            color: #666;
            font-family: '微软雅黑';
        }

        img {
            cursor: pointer;
            border: none;
        }

        ul, li, ol {
            list-style: none;
        }

        a, a:link {
            text-decoration: none;
            color: #666;
        }

        a:active, a:hover {
            text-decoration: none;
        }

        a:focus {
            outline: none;
        }

        .left {
            float: left;
        }

        .right {
            float: right;
        }

        /*公共样式*/

        .clearfix {
            clear: both;
        }

        /*--7.15--*/

        /*--bill-details--*/
        header {
            width: 100%;
            height: 45px;
            /*position: fixed;*/
            top: 0;
            background-color: #fff;
            border-bottom: 1px solid #c6c6c6;
            z-index: 9;
        }

        header ul {
            padding: 0;
            height: 100%;
        }

        header ul li {
            float: left;
            text-align: center;
        }

        header ul li:nth-child(2) {
            font-size: 5vw;
        }

        header ul li img {
            width: 35%;
            margin-top: 1rem;
        }

        .bill-de_head {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px 0;
            border-bottom: 1px solid #ddd;
        }

        .bill-de_head div {
            width: 50px;
            height: 50px;
            border: 2px solid #ddd;
            border-radius: 50%;
            overflow: hidden;
            margin-right: 15px;
        }

        .bill-de_head div img {
            width: 100%;
            height: 100%;
        }

        .bill-de_head span {
            font-size: 18px;
            letter-spacing: 1px;
        }

        .bill-de_mil {
            text-align: center;
            border-bottom: 1px solid #ddd;
            padding: 20px 0;
        }

        .bill-de_mil p {
            font-size: 31px;
            word-wrap: break-word;
        }

        .bill-de_mil h5 {
            font-size: 15px;
            padding-top: 5px;
        }

        .bill-de_mil2 {
            border-bottom: 1px solid #ddd;
        }

        .bill-de_mil2 li {
            display: flex;
            margin: 20px 0;
        }

        .bill-de_mil2 li p:nth-child(1) {
            width: 25%;
            font-size: 16px;
        }

        .bill-de_mil2 li p:nth-child(2) {
            font-size: 16px;
            width: 75%;
            word-wrap: break-word;
        }

        .bill-de_mil2 li p span {
            margin-left: 10px;
        }

        /*--bill-details end--*/

        /*--convert-bill--*/
        .con-bill_mil li {
            display: flex;
            align-items: center;
            padding: 15px 0;
            border-bottom: 1px solid #ddd;
        }

        .con-bill_mil li p {
            width: 20%;
        }

        .con-bill_mil li .con-bill_head {
            width: 50px;
            height: 50px;
            border: 1px solid #ddd;
            border-radius: 50%;
            overflow: hidden;
            margin-right: 15px;
        }

        .con-bill_mil li .con-bill_head img {
            width: 100%;
            height: 100%;
        }

        .con-bill_txt {
            width: 60%;
        }

        .con-bill_txt p {
            width: 100% !important;
            font-size: 15px;
        }

        /*--convert-bill end--*/
        .lodop-font {
            padding-top: 3rem;
            display: inline-block;
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var wfjGuizeCalc = "${wfjGuizeCalc}";
        var LODOP;
    </script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;" onclick="history.go( -1 );">
            <img src="<%=basePath %>images/ea/finance/BenDis/left_jt.png"/>
        </li>
        <li style="width: 80%;">账单详情</li>
        <li style="width: 10%;" id="print">打印</li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="bill-de_head">
            <div>
                <c:if test="${details[4]==null}">
                    <img src="<%=basePath%>images/ea/finance/BenDis/头像@2x.png">
                </c:if>
                <c:if test="${details[4]!=null}">
                    <img src="<%=basePath%>${details[4]}">
                </c:if>
            </div>
            <span class="staffname">${details[2]}</span>
        </div>
        <div class="bill-de_mil">
            <p><c:if test="${wfjGuizeCalc=='A' }">+</c:if>
                <c:if test="${wfjGuizeCalc=='D' }">+</c:if>
                <c:if test="${wfjGuizeCalc=='M' }">-</c:if>
                <span><fmt:formatNumber value="${detail.jifenDetailScore }" groupingUsed="true"/>
               </span>
            </p>
            <h5>完成交易</h5>
        </div>
        <ul class="bill-de_mil2">

            <c:if test="${details[1]!=null}">
                <li>
                    <p>商户订单</p>
                    <p class="jumnum">${details[1]}</p>
                </li>
            </c:if>

            <li>
                <p>商品信息</p>
                <p class="product">${detail.jifenDetailName}</p>
            </li>

            <c:if test="${details[2]!=null}">
                <li>
                    <p>用户名称</p>
                    <p>${details[2]}&nbsp(${details[3]})</p>
                </li>
            </c:if>

            <c:if test="${withdrawalWay!=null}">
                <li>
                    <p>交易方式</p>
                    <p id="fs">${withdrawalWay =="00"?"微信":withdrawalWay =="01"?"支付宝":withdrawalWay =="02"?"银行卡":""}</p>
                </li>
            </c:if>

            <li>
                <p>交易时间</p>
                <p class="payDate"><fmt:formatDate value="${detail.jifenDetailDate }"
                                                   pattern="yyyy-MM-dd HH:mm:ss"/></p>
            </li>
            <c:if test="${state!=null}">
                <li>
                    <p>交易状态</p>
                    <p class="payState">${state}</p>
                </li>
            </c:if>
            <c:if test="${responseCode!=null}">
                <li>
                    <p>交易应答码</p>
                    <p>${responseCode}</p>
                </li>
            </c:if>
            <c:if test="${bankName!=null}">
                <li>
                    <p>开户银行</p>
                    <p>${bankName}</p>
                </li>
            </c:if>
            <c:if test="${cardNo!=null}">
                <li>
                    <p>收款账号</p>
                    <p>${cardNo}</p>
                </li>
            </c:if>
            <c:if test="${usrName!=null}">
                <li>
                    <p>收款人姓名</p>
                    <p>${usrName}</p>
                </li>
            </c:if>
            <c:if test="${number!=null}">
                <li>
                    <p>流水号</p>
                    <p>${number}</p>
                </li>
            </c:if>
            <c:if test="${remark!=null}">
                <li>
                    <p>备注</p>
                    <p>${remark}</p>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<!--<script type="text/javascript" src="baidumap/baiduapi.js"></script>-->

<script>
    $(function () {

        var staffname = $(".staffname").text();
        var product = $(".product").text();
        var payDate = $(".payDate").text();
        var payState = $(".payState").text();
        var fs = $("#fs").text();
        var jumnum = $(".jumnum").text();
        var ccid = "${ccid}";
        var ccname = "${ccname}";
        var wfjGuizeCalc = "${wfjGuizeCalc=="A"?"+":"-"}";
        var jifenDetailScore = "${detail.jifenDetailScore}";
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        var CtUserName=null;
        var Reference=null;
        var StaffIdentityCard=null;
        var ReferrerAddress=null;
        if (jumnum != null && jumnum != "") {
            $.ajax({
                url: basePath + "/ea/jinbi/sajax_getCashbill.jspa",
                dataType: "json",
                type: 'get',
                cache: false,//IE下要关闭cache，否则不会刷新
                data: {"journalNum": jumnum},
                success: function (data) {
                    var member = eval("(" + data + ")");
                    CtUserName = member.CtUserName;
                    Reference = member.Reference;
                    StaffIdentityCard = member.StaffIdentityCard;
                    ReferrerAddress = member.ReferrerAddress;

                }
            });
        }

        $("header").attr("style", "height:" + $(window).height() * 0.08 + "px;line-height:" + $(window).height() * 0.08 +
            "px;position:fixed;");
        $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:" + $(window).height() * 0.08 + "px");
        $(".content").attr("style", ";overflow: auto;width:95%;margin:0 auto;");
        $(".bill-de_mil p").attr("style", "font-size:" + $(window).width() * 0.08 + "px;");
        $(".bill-de_head div").attr("style", "width:" + $(window).width() * 0.12 + "px;" + "height:" + $(window).width() * 0.12 +
            "px;");
        $(".bill-de_mil h5").attr("style", "font-size:" + $(window).width() * 0.04 + "px;");
        $(".bill-de_mil2 li p:nth-child(1)").attr("style", "font-size:" + $(window).width() * 0.04 + "px;");
        $(".bill-de_mil2 li p:nth-child(2)").attr("style", "font-size:" + $(window).width() * 0.04 + "px;");

        $("#print").click(function () {
            try {
                var url = "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccid + "&industryType=&etype=&sc=web";
                if (isAndroid == true) {
                    Android.androidPrintIntegralInfo3(wfjGuizeCalc + jifenDetailScore + "", wfjGuizeCalc + jifenDetailScore / 100
                        + "", product + "", staffname + "", fs + "", payDate + "", url + "", ccname + "");//调用安卓接口
                } else if (isiOS == true) {
                    var url = "func=" + 'iosSetAPAuth';
                    params = {
                        'inAndExp': inAndExp,
                        'integral': integral,
                        'rules': rules,
                        'operator': operator,
                        'money': money,
                        'journalNum': journalNum,
                        'time': time
                    };
                    for (var i in params) {
                        url = url + "&" + i + "=" + params[i];
                        console.log(url);
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                } else {
                    var lineHeight = 0;
                    LODOP = getLodop();
                    try {
                        LODOP.PRINT_INIT("账单详情");
                        //LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
                        LODOP.SET_PRINT_STYLE("FontName", "微软雅黑");
                        lineHeight += 30;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 55, 100, 30, "账单详情");
                        lineHeight += 60;
                        LODOP.SET_PRINT_STYLE("FontSize", 10);
                        LODOP.ADD_PRINT_TEXT(lineHeight, 10, 40, 25, "金币: ");
                        LODOP.ADD_PRINT_TEXT(lineHeight, 40, 100, 25, wfjGuizeCalc + " " + jifenDetailScore);
                        lineHeight += 30;
                        LODOP.SET_PRINT_STYLE("FontSize", 9);
                        LODOP.ADD_PRINT_TEXT(lineHeight, 10, 40, 25, "金额: ");
                        LODOP.ADD_PRINT_TEXT(lineHeight, 40, 100, 25, wfjGuizeCalc + " " + jifenDetailScore / 100);
                        if (jumnum != null && jumnum != "") {
                            lineHeight += 30;
                            LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "商户订单：" + jumnum);
                            lineHeight += 10;
                        }
                        lineHeight += 30;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 10, 70, 25, "商品信息：");
                        lineHeight += 30;
                        var ps = product.split(",");
                        for (i = 0; i < ps.length; i++) {
                            LODOP.SET_PRINT_STYLE("FontSize", 8);
                            var p = parseInt(ps[i].length / 7) + (ps[i].length % 7 ? 1 : 0);
                            LODOP.ADD_PRINT_TEXT(lineHeight, 70, 110, p * 10, ps[i]);
                            lineHeight = lineHeight + p * 10;
                        }
                        if (payState == null || payState == "") {
                            staffTitle = "付款人：";
                        } else {
                            staffTitle = "收款人：";
                        }
                        LODOP.SET_PRINT_STYLE("FontSize", 9);
                        lineHeight += 20;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, staffTitle + staffname);
                        if (fs != null && fs != "") {
                            lineHeight += 20;
                            LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "交易方式：" + fs);
                        }
                        lineHeight += 20;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "时间: " + payDate);
                        if (payState != null && payState != "") {
                            lineHeight += 20;
                            LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "交易状态：" + payState);
                        }
                        if (StaffIdentityCard != null && StaffIdentityCard != "") {
                            lineHeight += 20;
                            LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "学员信息：");
                            if (CtUserName != null && CtUserName != "") {
                                lineHeight += 20;
                                LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "姓名：" + CtUserName);
                            }
                            if (Reference != null && Reference != "") {
                                lineHeight += 20;
                                LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "手机号：" + Reference);
                            }

                                lineHeight += 20;
                                LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "身份证：" + StaffIdentityCard);

                            if (ReferrerAddress != null && ReferrerAddress != "") {
                                lineHeight += 40;
                                LODOP.ADD_PRINT_TEXT(lineHeight, 10, 170, 25, "地址：" + ReferrerAddress);
                            }
                        }
                        if (ccid != null && ccid != "") {
                            lineHeight += 60;
                            LODOP.ADD_PRINT_BARCODE(lineHeight, 30, 170, 130, 'QRCode', basePath + url);
                        }
                        lineHeight += 120;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 5, 170, 150, ccname);
                        lineHeight += 130;
                        LODOP.ADD_PRINT_TEXT(lineHeight, 5, 170, 20, "--------------------");
                        LODOP.SET_PRINT_STYLEA(1, "Bold", 1);
                        LODOP.SET_PRINT_STYLEA(2, "Bold", 1);
                        LODOP.SET_PRINT_STYLEA(4, "Bold", 1);
                        LODOP.SET_PRINT_STYLEA(1, "FontSize", 14);
                        LODOP.SET_PRINT_STYLEA(1, "FontName", "微软雅黑");
                        LODOP.SET_PRINT_STYLEA(10, "HOrient", "微软雅黑");
                        //LODOP.PREVIEW();
                        if (LODOP.CVERSION) {
                            LODOP.On_Return = function (TaskID, Value) {
                                if (Value >= 0) LODOP.PRINT(); else alert("选择失败！");
                            }
                            LODOP.SELECT_PRINTER();
                            //LODOP.PREVIEW();
                        }
                    } catch (err) {
                    }
                }
            } catch (e) {
                alert(e);
            }
        })
    });
</script>
</body>
</html>
