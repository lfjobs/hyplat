<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/contacts/Restaurant/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/contacts/Restaurant/QR_scan.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/restaurant/scanCodePay.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>

    <title>扫码付款</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var user = "${user}";
        var sccid = "${sccid}";
        var jum = "";
        var morre = "";
        var money = 0;
        var staffid = "${staffid}";
        var goodsname = "${pp.goodsName}";
        var ppid = "${pp.ppID}";
        var waiterID = "${waiterID}";
        var companyId = "${companyId}";
        var attach = "${attach}";
        var coID = "${coID}";
       var  ccompanyId= "${contactCompany.ccompanyID}";

        /*if(ccompanyId=="contactCompany20111025KX3WK7HN550000002511"||ccompanyId=="contactCompany20190514B8GDGN9MFZ0000022316"||ccompanyId=="contactCompany20110428N95WRUI5W80000001935"||ccompanyId=="contactCompany20111030KX3WK7HN550000025332"||ccompanyId=="contactCompany202602095UPXKJGAAQ0000000202"||ccompanyId=="contactCompany202602096V4RJAEAMX0000000138"||ccompanyId=="contactCompany2026020937TW4J2NJD0000000074"){
                  document.location.href = basePath+"ea/industry/ea_CompanyProducts.jspa?ccompanyId="+ccompanyId+"&pos=";
        }*/

    </script>
    <script type="text/javascript">
        $(function () {
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {
                if (ua.indexOf("browser") != -1) {
                    $(".wechat").hide();
                } else {
                    $(".wechat").show();
                }
            }else{
                //微信
                $(".wechat").show();
            }


        });




    </script>




</head>

<body style="background:#f6f6f6;">
<div class="loading" style="display:none;">
    <img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
    <p><span>加载中...</span></p>
</div>
<header class="com_head">

    <h1>付款</h1>
</header>
<div class="wrap_page QR_pay_wrap">
    <div class="QR_pay_box">
        <input type="hidden" value="${contactCompany.companyName}" id="companyName">
        <img class="head_img"  alt="" src="<%=basePath%><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/contacts/restaurant/scanCodePay/headimg.jpg</s:else>" />
        <span>向公司"${contactCompany.companyName}"付款</span>
        <div class="QRpay_inp_wrap">
            <div class="qrpay_tit">金额</div>
            <div class="QRpay_inp_box" onclick="">
                <span>￥</span>
                <input type="number" class="QRpay_inp" id="QRpay_inp" autofocus value="${money}">
            </div>
        </div>
    </div>
    <button href="javascript:;" class="QRpay_btn exchange" disabled id="QRpay_btn">确认付款</button>
    <a href="<%=basePath%>/ea/industry/ea_CompanyProducts.jspa?ccompanyId=${contactCompany.ccompanyID}&pos=" class=" QRpay_btn3">商城活动下单</a>
</div>
<div class="overlay"></div>
<div class="payways_wrap">
    <div class="payways_tit">选择您想采用的</div>
    <a href="###" class="pay_way ways_zfb" onclick="zf('1')"><span>支付宝支付</span></a>
    <a href="###" class="pay_way ways_wx wechat"  onclick="zf('3')"><span>微信支付</span></a>
    <%--<a href="###" class="pay_way ways_yhk" onclick="zf('2')"><span>银行卡支付</span></a>--%>
</div>

<form name="formsutm" id="formsutm" method="post">
    <s:hidden name="journalNum" id="journalNum"></s:hidden>
    <s:hidden name="baseUrl" id="baseUrl"></s:hidden>
    <s:hidden name="morre" id="morre"></s:hidden>
    <s:hidden name="staffid" id="staffid"></s:hidden>
    <s:hidden name="sccid" id="sccid"></s:hidden>
    <s:hidden name="ppid" id="ppid"></s:hidden>
    <s:hidden name="isflag" id="isflag"></s:hidden>
    <input type="submit" style="display: none" name="submit" id="submit" />
</form>
<script>
    $(function(){
        var money = "${money}";

        var QRinp = document.getElementById("QRpay_inp");
        var btn = document.getElementById("QRpay_btn");
        if(money!=""&&money!="0"){
            btn.disabled = false;
           $("#QRpay_inp").attr("readonly",true)
        }
        //$(".QRpay_inp").focus();
        QRinp.addEventListener("input", function() {
            var str = this.value.trim();
            if (str.indexOf(".") > -1) {
                if (str.length == 1) {
                    this.value = "0" + ".";
                } else if (str.length > 1) {
                    var inp_arr = str.split(".");
                    var part_arr = inp_arr[1].split('');
                    if (part_arr.length > 2) {
                        var b = part_arr.join('').substring(0, 2);
                        inp_arr[1] = b;
                        this.value = inp_arr.join('.');
                    }
                }

            }
            var t = Number(this.value.trim());
            console.log(t);
            if (t >= 0.1) {
                btn.disabled = false;
            } else {
                btn.disabled = true;
            }
        })
        $(".QRpay_btn").click(function() {
            $(".overlay").addClass("active");
            $(".payways_wrap").addClass("show");
        })
        $(".overlay").click(function() {
            $(".payways_wrap").removeClass("show");
            $(this).removeClass("active");
        })
        //处理浏览器输入法遮挡
        var screenH = window.innerHeight;
        window.onresize = function() {
            var t = window.innerHeight;
            //console.log(t);
            //console.log(screenH);
            var inp = $("input:focus")[0];
            if (t < screenH) {
                btn.scrollIntoView(false);
            }
        }
        window.onload=function(){
            btn.scrollIntoView(false);
        }

    })
</script>
</body>

</html>