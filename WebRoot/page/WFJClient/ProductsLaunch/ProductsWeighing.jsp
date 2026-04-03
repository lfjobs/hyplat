<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>移动版无码称重</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/ProductsWeighing.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/ProductsWeighing.js"></script>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var companyID = "${param.companyID}";
        var staffid = "${param.staffid}";//登录人id
        var sccid = "${param.sccid}";//登录人idaaactivityID
        var ccompanyID = "";
        var pageNumber = 0;
        var pageSize = 35;
        var pageCount = 0;
        var number = 0;
        var weight="";
        var ppname ="${param.ppname}";
        var showButton="${param.showButton}";
        var sort = "${param.sort}";
    </script>

</head>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="javascript:window.history.go(-1);return false;">
            <img src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png"/>
        </li>
        <li>
            无码称重
        </li>
    </ul>
</header>
<div class="content">
    <section class="header">
        <input type="text" placeholder="请输入名称" name="" id="ppname" value=""/>
        <%--<div>--%>
            <%--<img src="<%=basePath%>images\WFJClient\sweep-code.png"/>--%>
        <%--</div>--%>
    </section>
    <section class="nav-1">
        <div>
            <ul class="clearfix fcate">
                <li class='active'>
                    全部
                </li>
            </ul>
        </div>
    </section>
    <section class="nav-2">
        <div>
            <ul class="clearfix ul_list scate"></ul>
        </div>
    </section>
    <section class="sec-con">
        <ul class="clearfix ul_list_sp"></ul>
    </section>
    <p class="p-button" >
        新产品称重
    </p>
</div>

<!--------------------------------------------------验货称重开始--------------------------------------------->
<div class="inspection-sheet">
    <div class="box">
        <h2>
            验货单
        </h2>
        <ul>
            <li>
                <label for="">物品名称：</label>
                <input type="text" name="" id="" value="苹果" placeholder="自动获取"/>
            </li>
            <li>
                <label for="">单价：</label>
                <input type="text" name="" id="" value="10元/kg" placeholder="自动获取"/>
            </li>
            <li>
                <label for="">收货数量：</label>
                <input type="number" name="" id="" value="50" placeholder="自动获取"/>
            </li>
            <li>
                <label for="">验货数量：</label>
                <input type="number" name="" id="" value="49" placeholder="录入获取/称重获取"/>
            </li>
            <li>
                <label for="">误差数：</label>
                <input type="number" name="" id="" value="1" placeholder="录入获取/称重获取"/>
            </li>
            <li>
                <label for="">验货人：</label>
                <input type="text" name="" id="" value="张三" placeholder="自动获取"/>
            </li>
        </ul>
        <section class="sec-btn">
            <div class="clearfix">
                <input type="button" name="" class="peeled" value="去皮"/>
                <input type="button" name="" class="zero" value="清零"/>
            </div>
            <div class="clearfix">
                <input type="button" name="" id="" value="取消"/>
                <input type="button" name="" id="" value="确定"/>
            </div>
        </section>
    </div>
</div>
<!--------------------------------------------------验货称重结束--------------------------------------------->

<!--------------------------------------------------称重开始--------------------------------------------->
<div class="invCheck inspection-sheet">
    <div class="box">
        <h2>
            无码称重
        </h2>
        <ul>
            <li>
                <label for="">物品名称：</label>
                <input type="text" name="goodsName" class="goodsName inputValue" readonly/>
                <input type="hidden" class="pd inputValue"/>
                <input type="hidden" class="num inputValue"/>
                <input type="hidden" class="goodsid inputValue"/>
                <input type="hidden" class="prcc inputValue">
            </li>
            <li>
                <label for="">商品plu：</label>
                <input type="text" name="plu" class="plu inputValue" readonly/>
            </li>
            <li class="weightLi">
                <label for="">重量：</label>
                <input type="number" name="quantity" class="weight inputValue" oninput="if(value<0)value=0"/>KG
            </li>
            <li class="inputnumLi" style="display: none">
                <label for="">商品个数：</label>
                <input type="number" class="inputnum inputValue" oninput="if(value<0)value=0"/>PCS
            </li>
        </ul>
        <section class="sec-btn">
            <div class="clearfix">
                <input type="button" name="" class="peeled" value="去皮"/>
                <input type="button" name="" class="zero" value="清零"/>
            </div>
            <div class="clearfix">
                <input type="button" name="" id="" value="取消"/>
                <input type="button" name="" class="isok" value="确定"/>
            </div>
        </section>
    </div>
</div>
<!--------------------------------------------------称重结束--------------------------------------------->

<!--------------------------------------------------无产品称重开始--------------------------------------------->
<div class="weigh">
    <div class="box">
        <h2>
            称重
        </h2>
        <ul>
            <li>
                <label for="">称重：</label>
                <input type="number" name="quantity" class="weight inputValue" oninput="if(value<0)value=0"/>KG
                <input type="hidden" class="num inputValue"/>
            </li>
            <li class="inputnumLi" style="display: none">
                <label for="">商品个数：</label>
                <input type="number" class="inputnum inputValue" oninput="if(value<0)value=0"/>PCS
            </li>
        </ul>
        <section class="clearfix sec-btn">
            <div class="clearfix">
                <input type="button" name="" class="peeled" value="去皮"/>
                <input type="button" name="" class="zero" value="清零"/>
            </div>
            <div class="clearfix">
                <input type="button" name="" id="" value="取消"/>
                <input type="button" name="" class="isok" value="确定"/>
            </div>
        </section>
    </div>
</div>
<!--------------------------------------------------无产品称重结束--------------------------------------------->

<div class="mm-alert">
    <div  class="mm-div-tiaojia">
        <h1>提示</h1>
        <h5 class="ct"></h5>
        <input type="button" value="确定">
    </div>
</div>
</body>
</html>
