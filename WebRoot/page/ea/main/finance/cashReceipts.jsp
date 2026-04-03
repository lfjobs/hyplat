<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/9 0009
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人兑现收款单管理</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/BenDis/cash.js"></script>
    <script>
        var basePath = "<%=basePath%>";
        var ppageNumber = "${pageNumber}";
        var pageSize = "${pageSize}";
        var inforType="${inforType}";
        var nameSearch="${nameSearch}";
        var sdate='${sdate}';
        var edate='${edate}';
        var token=0;
        var pl="${pl}";
        var hylb="${hylb}";
        var iisnull="${iisnull}";
        var str="";
        var zzorder ="${zzorder}";
        var clientPositioningID="";
    </script>
</head>
<body>
<div class="main_main">
    <table class="address">
        <thead>
        <tr class="tablewith">
            <th width="40" align="center">请选择</th>
            <th width="50" align="center">序号</th>
            <th width="100" align="center">订单编号</th>
            <th width="100" align="center">单据编号</th>
            <th width="180" align="center">公司名称</th>
            <th width="180" align="center">银行名称</th>
            <th width="180" align="center">银行支行地址</th>
            <th width="100" align="center">兑现方名称</th>
            <th width="100" align="center">银行卡号</th>
            <th width="100" align="center">兑现金额</th>
            <th width="100" align="center">兑现时间</th>
        </thead>
        <%int number = 1;%>
        <c:forEach items="${pageForm.list}" var="a">
            <tr id="${a[0]}">
                <td><input type="radio" name="a" class="JQuerypersonvalue"
                           value="${a[0]}" />
                </td>
                <td><span><%=number%></span>
                </td>
                <td><span id="jNumOrder">${a[16]}<input value="${a[2]}" type="hidden" class="billtype"/></span>
                </td>
                <td><span id="journalNum">${a[3]}</span>
                </td>
                <td><span id="company">${a[1]}</span>
                </td>
                <td><span id="company">${a[1]}</span>
                </td>
                <td><span id="company">${a[1]}</span>
                </td>
                <td><span id="appropriationcompanyName">${a[9]}</span>
                </td>
                <td><span id="logoNote">${a[10]}</span>
                </td>
                <td><span id="logoNote">${a[13]}</span>
                </td>
                <td><span id="logoNote">${a[5]}</span>
            </tr>
            <%  number++; %>
        </c:forEach>

    </table>
    <form style="display: none;" name="addressForm" id="addressForm"
          method="post" action="<%=basePath%>ea/activity/ea_getcomporder.jspa?">
        <s:token></s:token>
        <input id="formtest" type="test" name="inforType"
               style="display:none" />
        <input type="submit" name="submit" style="display:none" />
    </form>
    <c:import url="../../../ea/page_navigator.jsp">
        <c:param name="actionPath"
                 value="/ea/bdbill/ea_skd.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}&hylb=${hylb}&pl=${pl}&sdate=${sdate}&edate=${edate}&iisnull=${iisnull}&nameSearch=${nameSearch}&inforType=${inforType}">
        </c:param>
    </c:import>
    <s:token></s:token>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
        framespacing="0" height="0"></iframe>
</body>
</html>