<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>送货详情</title>

    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/ea/supermarket/base.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/finance/NewPhoneOrders/sellerOrder/Office/transporGoods.css"/>
    <script type="text/javascript" charset="utf-8" src="js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="js/ea/finance/NewPhoneOrders/sellerOrder/Office/transporGoods.js"></script>
    <script type="text/javascript">
        var reansportid = "${param.reansportid}";
        var staffid = "${param.staffid}";
        var sort = "${param.sort}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            送货单
        </li>
        <li>
            <%--打印--%>
        </li>
    </ul>
</header>
<div class="container">
    <input type="hidden" id="transportid" value="${ordobj[0]}"/>
    <ul class="ul-con">
        <li>送货号：${ordobj[1]}</li>
        <li>订单号：${ordobj[2]}</li>
        <li>下单时间：${ordobj[4]}&nbsp &nbsp &nbsp &nbsp &nbsp送货时间：${ordobj[3]}
            <!-- <li>下单时间：2020-06-26 11:20:48</li> -->
        <li>采购商：${ordobj[5]}</li>
        <li>送货人：${ordobj[6]}</li>
        <li>送货状态：<c:if test="${ordobj[7]=='01'}">
            <span>未送货</span>
        </c:if>
            <c:if test="${ordobj[7]=='02'}">
                <span>已送货</span>
            </c:if>
        </li>
        <li>收件人：${ordobj[8]}</li>
        <li>收件人电话：${ordobj[9]}</li>
        <li>地址：${ordobj[10]}</li>
        <!-- <li>发货商：北京市龙腾网有限公司</li> -->
        <li>买家留言：${ordobj[7]}</li>
    </ul>
    <table>
        <tr>
            <td>
                名称及规格
            </td>
            <td>
                单位
            </td>
            <td>
                订单数
            </td>
            <td>
                数量/重量
            </td>
            <td>
                差数
            </td>
            <!-- <td>
                单价
            </td>
            <td>
                金额
            </td> -->
        </tr>
        <c:forEach items="${gl}" var="item" varStatus="status">

            <tr id="${item[0]}" class="tr-zl goodssj ${item[10] }">
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
        </tr>
    </table>
    <div class="zj">
        <ul class="clearfix">
            <!-- <li class="li-zjs">
                总件数:<span>30</span>
            </li>
            <li class="li-tj">
                体积:0.6
            </li>
            <li class="li-zl">
                重量:7kg
            </li> -->
            <li class="li-hjje">
                合计金额:<span>${ordobj[11]}</span>
            </li>
        </ul>
    </div>
    <c:if test="${ordobj[7]=='01'}">
        <div class="js">
            <p class='btn1' onclick=isOK("btn1")>
                无需物流
            </p>
            <p class='btn2'>
                提交物流
            </p>
        </div>
    </c:if>
</div>
<div class="txdh">
    <section>
        <p class="clearfix">
            <label for="">填写单号</label>
            <input type="hidden" id="biaoshi">
            <input type="" name="" id="yd"/>
            <img src="images/ea/finance/NewPhoneOrders/sellerOrder/Office/tk_03.png" id="smtm"/>
        </p>
        <div class="div-btn clearfix">
            <p class="close">取消</p>
            <p class="close" onclick=isOK("close")>确定</p>
        </div>
    </section>
</div>
</body>
</html>
