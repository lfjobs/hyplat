<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
<title>支付成功</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">

<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_sucorder.js?version=20250429" type="text/javascript"></script>
<script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script>
    var basePath = "<%=basePath%>";
    var journalNum = "${journalNum}";
    var fhform = "${param.fhform}";
    var staffcode = "";
    var posNum = "${param.posNum}";
    var zf = "";
    var paytype = "${param.paytype}";

    var t = 0;
    var addressID = "${param.addressID}";
    var fhform = "${param.fhform}";

    var companyName = "${contactCompany.companyName}";
    var companyAddr = "${contactCompany.companyAddr}";
    var companyTel = "${contactCompany.companyTel}";
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    $(function () {
        try {
            if (isAndroid == true) {
                if (paytype == "转他人支付") {
                    Android.speechOutputForAndroid("操作成功，您可以打印购物小票");
                } else {
                    Android.speechOutputForAndroid("支付成功，您可以打印购物小票");
                }

            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }

    });

</script>


</head>
<body>

<section class="success">
    <!--扫码支付内容-->
    <article>
        <!--头部-->
        <header>
            <img src="<%=basePath%>images/supermarket/gou.png">
            <c:if test="${param.paytype=='转他人支付'}">
                <p>操作成功</p>
            </c:if>
            <c:if test="${param.paytype!='转他人支付'}">
                <p>支付成功</p>
            </c:if>
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <h2>订单编号：<span>${journalNum}</span></h2>
            <ul class="listc">
                <c:forEach items="${goodlist}" var="item">
                    <li class="tr">
                        <input type="hidden" value="${item.ssprice}" class="ssprice"/>
                        <input type="hidden" value="${item.zlprice}" class="zlprice"/>
                        <input type="hidden" value="${item.variableID}" class="variableID"/>
                        <h5><span
                                class="goodsName">${item.goodsName}${item.stardard eq "默认规格"||item.stardard eq null ?"":"*".concat(item.stardard)}</span>
                        </h5>
                        <p style="display: none;"><span class="barCode">${item.barCode}</span></p>
                        <p class="nub"><span class="specNum" style="display: none;">${item.specNum}</span><span
                                class="num">${item.itemNum}</span></p>

                        <p style="display: none;"><span class="tprice">${item.price}</span></p>
                        <p class="mony">&yen;<span class="price"></span></p>
                    </li>


                </c:forEach>


            </ul>
            <h4>合计<p>共<span class="totalnum">0</span>件 &yen;<span class="totalmoney">0</span></p></h4>
        </figure>
        <!--内容 end-->
        <div class="btn">
            <input type="button" value="打印小票" id="print">


            <c:if test='${param.posNum ne null &&param.posNum ne ""&&param.fhform ne "3"}'>
                <a><input type="button" value=" 返回结算首页 " id="backhome"></a>
                <a><input type="button" value=" 返回选择商品 " id="backshop"></a>
            </c:if>
            <c:if test='${param.posNum eq null || param.posNum eq ""&&param.fhform ne "3"}'>
                <a><input type="button" value=" 返回首页 " id="backhome"></a>
            </c:if>
        </div>
    </article>
    <!--扫码支付内容 end-->
    <!--打印小票弹框-->
    <div class="alert_suc_">
        <div class="alert_suc">
            <h2>请稍等一下</h2>
            <p>小票正在打印中哦</p>
            <img src="<%=basePath%>images/supermarket/print.png" alt="">
        </div>
    </div>
</section>

</body>
</html>