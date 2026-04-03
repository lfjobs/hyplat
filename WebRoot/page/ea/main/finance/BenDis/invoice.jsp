<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Company c = (Company)session.getAttribute("currentcompany");
    CAccount ca = (CAccount)session.getAttribute("account");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>发货单--打印</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/invoiceIndex.css">
</head>
<body>
<div>
    <section>
        <h1>
            <img src="<%=basePath%>css/images/invoiceLogo.png"/>
            配送单
        </h1>
        <div class="clearfix">
            <p>订单号：${cashierBills.journalNum}</p>
            <p class="name">制单人：<%=ca.getAccountName()%></p>
            <p>打印日期：<%
                Date d = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String now = df.format(d);
                out.println(now);
            %></p>
        </div>
        <span>
             <%
                 hy.ea.bo.finance.CashierBills data = (hy.ea.bo.finance.CashierBills) request
                         .getAttribute("CashierBills");
                 if (data.getjNumOrder() != null) {
                     StringBuffer barCode = new StringBuffer();
                     barCode.append("<img src='");
                     barCode.append(request.getContextPath());
                     barCode.append("/CreateBarCode?data=");
                     barCode.append(data.getjNumOrder());
                     barCode
                             .append("&barType=TF25&height=50&headless=true&drawText=true&width=1' wdith='200'>");
                     out.println(barCode.toString());
                 } else {
                     out.println("no data");
                 }
             %><br />
         <i style="font-size: 8px;font-style: normal;margin-top: 2px;display: block">${cashierBills.journalNum}</i>
        </span>

        </td>
    </section>
    <table border="0" cellspacing="1" cellpadding="1">
        <tr>
            <td>
                序号
            </td>
            <td>
                商品条码
            </td>
            <td>
                商品名称
            </td>
            <td>
                数量/重量
            </td>
            <td>
                待配送数量
            </td>
            <td>
                单价
            </td>
        </tr>
        <%int number = 1;%>
        <s:iterator value="goodBillslist" var="gp">
            <tr id="kelong<%=number%>" class="checkgoods">
                <!-- 序号 -->
                <td><%=number%></td>
                <td>${gp[1].barCode}</td>
                <!-- 产品名称 -->
                <td>${gp[0].goodsName}</td>
                <td class="quantity">${gp[0].quantity}</td>
                <td class="quantity">${(gp[0].fhnum==null||gp[0].fhnum=="")?gp[0].quantity:gp[0].fhnum}</td>
                <td class="price">${gp[0].price}</td>
            </tr>
            <% number++;%>
        </s:iterator>
        <tr>
            <td colspan="4"></td>
            <td>总件数：<span class="quantityNum"></span></td>
            <td>总金额：<span class="priceNum"></span></td>
        </tr>
    </table>
    <section class="clearfix">
        <ul>
            <li>收货人：${sh.receivename}</li>
            <li>联系电话：${sh.receivetel}</li>
            <li>收货地址：${sh.receiveaddress}</li>
            <li>支付日期：${sh.fkDate}</li>
        </ul>
        <ul>
            <li>配送员：</li>
            <li>配送员电话：</li>
            <li>商家：${cashierBills.companyName}</li>
            <li>拣货员：</li>
        </ul>
    </section>
</div>
</body>
<script type="text/javascript">
    $(function(){
        var priceNum = 0;
        var quantityNum = 0;

        $(".price").each(function () {
            priceNum +=parseFloat($(this).text())*parseFloat($(this).prev().text());
        })
        $(".quantity").each(function () {
            quantityNum +=parseFloat($(this).text())
        })
        $(".priceNum").text(priceNum)
        $(".quantityNum").text(quantityNum)
    });
</script>
</html>
