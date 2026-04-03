<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %><%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/WFJClient/base.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ea/finance/invoicing/InitialiaePrint.css"/>
    <script type="text/javascript" charset="utf-8" src="/js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/jquery-1.9.1.min.js"></script>
    <title>详情</title>
</head>
<body class="fixedhea">
<header>
    <ul class="clearfix">
        <li onclick="toBack()">
            <img src="/images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            初始库存
        </li>
        <li id="print">
            打印
        </li>
    </ul>
</header>
<div class="fixedhea_box"></div>
<div class="content">
    <ul class="ul-01">
        <li class="clearfix">
            <p>初始库存单号</p>
            <p id="journalnum">${bill.journalnum}</p>
        </li>
        <li class="clearfix">
            <p>初始库存时间</p>
            <p id="adddate"><fmt:formatDate value="${bill.adddate}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        </li>
        <li class="clearfix">
            <p>商品类别</p>
            <p id="codename">${bill.codename}</p>
        </li>
        <li class="clearfix">
            <p>初始库存责任人</p>
            <p id="staffname">${bill.staffname}</p>
        </li>
        <li class="clearfix">
            <p>公司</p>
            <p id="comname">丰田瑞泰公司</p>
        </li>
        <s:iterator value="#request.goodslist">
            <ul class="goods" id="${initializeid}">
                <li class="clearfix">
                    <p>名称及规格</p>
                    <p class="goodname">${goodname}</p>
                </li>
                <li class="clearfix">
                    <p>单位</p>
                    <p class="unit">${unit}</p>
                </li>
                <li class="clearfix">
                    <p>初始库存</p>
                    <p class="quantity">${quantity}</p>
                </li>
                <li class="clearfix">
                    <p>是否启用在线</p>
                    <p class="showweixin">${showweixin== "01" ? "启动" : "未启动"}</p>
                </li>
                <li class="clearfix">
                    <p>库仓</p>
                    <p class="warehousename">${warehousename}</p>
                </li>
            </ul>
        </s:iterator>
    </ul>
</div>
</body>
<script type="text/javascript">
    $(function () {
        $("#print").click(function () {
            try {
                if (isAndroid == true) {
                    Android.speechOutputForAndroid("开始打印，请稍后!");
                    var goodsbill = [];
                    $(".goods").each(function () {
                        goodsbill.push({
                            "goodname": $(this).find(".goodname").text(),
                            "unit": $(this).find(".unit").text(),
                            "showweixin": $(this).find(".showweixin").text(),
                            "warehousename": $(this).find(".warehousename").text(),
                            "quantity": $(this).find(".quantity").text()
                        });
                    });
                    var billjson = {
                        "journalnum": $("#journalnum").text(),
                        "adddate": $("#adddate").text(),
                        "codename": $("#codename").text(),
                        "staffname": $("#staffname").text(),
                        "comname": $("#comname").text(),
                        "goodsbill": goodsbill
                    };
                    Android.printInitialInventory(JSON.stringify(printJson));
                    //alert(JSON.stringify(billjson));
                } else {
                    console.log("请在安卓设备访问！");
                }
            } catch (error) {

            }
        });
    });
    function toBack() {
        try {
            if (isAndroid == true) {
                console.log("安卓");
                Android.callAndroidjianli();//调用安卓接口
            } else if (isiOS == true) {
                console.log("IOS");
                var url = "func=" + 'callIOSjianli';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        } catch (error) {
            window.history.go(-1);
            return false;
        }
    }
</script>
</html>

