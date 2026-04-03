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

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>拣货出库单</title>

    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/deliveryDetails.css"/>
    <script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/deliveryDetails.js"></script>
    <script type="text/javascript">
        function onFocus() {

            var target = event.target
            setTimeout(function () {
                target.readOnly = false
            }, 0)
        }
        function onBlur() {
            event.target.readOnly = true
        }
        var staffid = "${param.staffid}";
        var sort = "${param.sort}";
        //大屏用
        var posNum = "";//大屏id
        var dpFlag = false;//大屏标识
        try {
            //判断是否是大屏终端
            posNum = Android.forAndroidDeviceId();
            var url = "ea/smg/sajax_sm_isExistPosNum.jspa";
            $.ajax({
                url: url,
                type: "get",
                dataType: "json",
                async: true,
                data: {
                    posNum: posNum
                },
                success: function (data) {
                    var m = eval("(" + data + ")");
                    var result = m.result;
                    if (result != "0") {
                        posNum = "";
                    }
                },
                error: function (data) {
                    // alert("验证失败");
                    posNum = "";
                }
            });
            console.log('---' + posNum);
            if (posNum == null || posNum == "") {//跳转小屏
                dpFlag = false;
            } else {//跳转大屏
                dpFlag = true;
            }
        } catch (e) {
            dpFlag = false;
        }
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>拣货出库单</li>
        <li><%--打印--%></li>
    </ul>
</header>
<div class="container">
    <input type="hidden" id="orderid" value="${ordobj[0]}"/>
    <ul class="ul-con">
        <li>拣货出库号：${ordobj[1]}</li>
        <li>订单号：${ordobj[2]}</li>
        <li>下单时间：${ordobj[4]}&nbsp&nbsp&nbsp&nbsp&nbsp拣货时间：${ordobj[3]}</li>
        <%--<li>拣货时间：${ordobj[3]}</li>--%>

        <li>采购商：${ordobj[5]}</li>
        <li>拣货人：${ordobj[6]}</li>
        <li>拣货状态：<c:if test="${ordobj[8]=='01'}">
            <span>未拣货</span>
        </c:if>
            <c:if test="${ordobj[8]=='02'}">
                <span>已拣货</span>
            </c:if>
        </li>
        <li>买家留言：${ordobj[7]}</li>
    </ul>
    <form name="transport" id="transport">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <th>名称及规格</th>
                <th>单位</th>
                <th>订单数</th>
                <th>数量/重量</th>
                <th>差数</th>
                <%--<th>单价</th>
                <th>金额</th>--%>
            </tr>
            <c:forEach items="${gl}" var="item" varStatus="status">
                <tr id="${item[0] }" class="goodssj ${item[10] }">
                    <td class="goodname">
                        <span class="spanstyle">${item[1] }</span>
                        <input type="hidden" class="dw" value="${item[6] }"/>
                        <input type="hidden" class="je" value="${item[7] }"/>
                        <input type="hidden" class="ppid" value="${item[8] }"/>
                        <input type="hidden" class="isscale" value="${item[9] }"/>
                        <input type="hidden" class="borcode" value="${item[10] }"/>
                        <input type="hidden" class="weinum" name="${item[0] }" value="${item[4] }"/>
                    </td>
                    <td>${item[2] }</td>
                    <td class="td-dds">${item[3] }</td>
                    <td class="num">${item[4] }</td>
                    <td class="td-cs">${item[5] }</td>
                </tr>
            </c:forEach>

        </table>
    </form>
    <div class="zj">
        <ul class="clearfix">
            <%--<li class="li-zjs">总件数:<span>30</span></li>
            <li class="li-tj">体积:0.6</li>
            <li class="li-zl">重量:7kg</li>--%>
            <li class="li-hjje">合计金额:<span>${ordobj[9]}</span></li>
        </ul>
    </div>
    <div class="js clearfix">
        <c:if test="${ordobj[8]=='01'}"><p id="submit">提交发货</p></c:if>
        <p id="wei">无码称重</p>
        <p id="sm">扫码拣货</p>
        <input type="text" id="displayIndex" style="opacity: 0;" onfocus="onFocus()" onblur="onBlur()"/>
    </div>
</div>

<!-- 无码称重1 -->
<div class="wmcz"></div>
</body>
</html>
