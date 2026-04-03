<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>明细</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/integral_detail.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var LODOP;
    </script>
</head>
<body>
<header>
    <menu class="clearfix">
        <li>
            <a  href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/finance/Gold_Pool/left.png"></a>
        </li>
        <li>明细</li>
    </menu>
</header>
<div class="content">
    <section>
        <p>
            <span class="inAndExp">${param.inAndExp =="A"?"+":"-"}</span>
            <span class="integral">${param.integral}</span>
        </p>
        <p class="rules">${param.rules}</p>
    </section>
    <ul>
        <li>
            操作人
            <span class="operator">${param.operator}</span>
        </li>
        <c:if test="${param.rules eq '积分充值' }">
            <li>
                充值金额
                <span class="money">${param.integral/100}</span>
            </li><li>
            充值积分
            <span>${param.integral}</span>
        </li>
        </c:if>
        <c:if test="${param.rules != '积分充值' }">
            <li>
                商品名称
                <span>${param.pdName}</span>
            </li>
            <li>
            积分
            <span>${param.integral}</span>
        </li>
        </c:if>
    <li>
        订单号
        <c:if test="${param.rules == '积分充值' }">
            <a href="<%=basePath%>ea/pobuy/ea_getCashBill.jspa?journalNum=${param.journalNum}&sccId=${param.sccId}&entrance=integral"><span class="journalNum">${param.journalNum}</span>
        </c:if>
        <c:if test="${param.rules != '积分充值' }">
            <span class="journalNum">${param.journalNum}</span>
        </c:if>
    </li><li>
        时间
        <span class="time">${param.time}</span>
    </li>
    </ul>
</div>
<footer>
    <input type="button" name="" id="print" value="打印" />
</footer>
</body>
<script type="text/javascript">
$(function () {
        $("#print").click(function () {
            var pdName = $(".pdName").text();
            var inAndExp = $(".inAndExp").text();
            var integral= $(".integral").text();
            var rules= $(".rules").text();
            var operator= $(".operator").text();
            var money= $(".money").text();
            var journalNum= $(".journalNum").text();
            var time = $(".time").text();
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            try {
                if (isAndroid == true) {
                    Android.androidPrintIntegralInfo(inAndExp+"",integral+"",rules+"",operator+"",money+"",journalNum+"",time+"");//调用安卓接口
                }else if (isiOS == true) {
                    var url= "func=" + 'iosSetAPAuth';
                    params={'inAndExp':inAndExp,'integral':integral,'rules':rules,'operator':operator,'money':money,'journalNum':journalNum,'time':time};
                    for(var i in params){
                        url = url + "&" + i + "=" + params[i];
                        console.log(url);
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }else {
                    LODOP=getLodop();
                    LODOP.PRINT_INIT("充值明细");
                    LODOP.SET_PRINT_PAGESIZE(3,500,0.2,"");
                    LODOP.SET_PRINT_STYLE("FontSize",14);
                    LODOP.ADD_PRINT_TEXT(20,55,100,30,"积分明细");
                    LODOP.ADD_PRINT_TEXT(50,70,250,20,inAndExp+integral);
                    LODOP.SET_PRINT_STYLE("FontSize",8);
                    LODOP.ADD_PRINT_TEXT(70,70,80,20,rules);
                    LODOP.ADD_PRINT_TEXT(80,0,250,20,"-------------------------------");
                    LODOP.SET_PRINT_STYLE("FontSize",9);
                    LODOP.ADD_PRINT_TEXT(110,10,200,20,"操作人: "+operator);
                    if(rules=="积分充值"){
                        LODOP.ADD_PRINT_TEXT(130,10,200,20,"充值金额: "+money);
                        LODOP.ADD_PRINT_TEXT(150,10,200,20,"充值积分: "+integral);
                    }else {
                        LODOP.ADD_PRINT_TEXT(130,10,200,20,"商品名称: "+pdName);
                        LODOP.ADD_PRINT_TEXT(150,10,200,20,"积分: "+integral);
                    }
                    LODOP.ADD_PRINT_TEXT(170,10,300,20,"订单号: "+journalNum);
                    LODOP.ADD_PRINT_TEXT(190,10,300,20,"时间: "+time);
                    LODOP.ADD_PRINT_TEXT(320,70,250,50,".");

                    LODOP.PRINT();
                }
            }catch(e){
                // window.history.go(-1);return false;
            }
        })
})
</script>
</html>
