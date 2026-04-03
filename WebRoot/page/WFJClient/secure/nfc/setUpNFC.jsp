<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/setUpNFC.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/secure/nfc/setUpNFC.js"></script>
    <title>NFC</title>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var sn = "${param.sn}";//序列号
        var model = "${param.model}";//芯片型号
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            芯片设置
            <%-- <a onclick="window.history.go(-1);return false;" target="_self">
                 <img src="<%=basePath%>images/ea/office/contract/stamp/return.png">
             </a>--%>
        </li>
        <li>
        </li>
        <li>
            <%--<a href="<%=basePath%>/page/WFJClient/pc/5l5C/office/securityManage.jsp?companyID=${companyID}" target="_self">
                列表
            </a>--%>
        </li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

    <div class="content">
        <%--<div class="div-name">
            <label for="">安全点编号</label>
            <input type="text"  placeholder="请填写安全点编号"  name="document.ln" id="ln" value="${document.ln}"/>
        </div>--%>
        <div class="div-name">
            <label for=""><span class="rad">* </span>芯片序列号</label>
            <input type="text" placeholder="请填写芯片序列号" class="isNotnull" name="nfc.sn" id="sn" value="${param.sn}" readonly/>
        </div>
        <div class="div-name">
            <label for=""><span class="rad">* </span>芯片型号</label>
            <input type="text" placeholder="请填写芯片型号" class="isNotnull"  name="nfc.model" id="model" value="${param.model}" readonly/>
        </div>
        <div class="div-name companyName">
            <label for=""><span class="rad">* </span>绑定公司</label>
            <input type="text" placeholder="请选择公司" class="isNotnull" readonly name="nfc.companyName" id="companyName"/>
            <input type="hidden" name="nfc.companyID" id="companyId"/>
        </div>
        <div class="div-name staffName">
            <label for=""><span class="rad">* </span>绑定责任人</label>
            <input type="text" placeholder="请选择责任人" class="isNotnull"  readonly name="nfc.staffName" id="staffName"/>
            <input type="hidden" name="nfc.staffID" id="StaffId"/>
        </div>
        <div class="div-name">
            <label for="">设备编号</label>
            <input type="text" placeholder="请输入设备号  例如：车牌号" name="nfc.en" id="en"/>
        </div>
        <div class="div-name">
            <label for=""><span class="rad">* </span>安全点地点</label>
            <input type="text" maxlength="50" class="isNotnull"  placeholder="芯片绑定公司地点 如：厨房、仓库、下水道、车间、财务处等" name="nfc.bindLocation"
                   id="bindLocation"
                   value="${bindLocation}"/>
        </div>
        <div class="div-bottom">
            <p class="submitAudit">
                保存
            </p>
        </div>
    </div>
</form>

<!--温馨提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <%--<p class="left close-tingyong">取消</p>--%>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>
<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt=""/>
        <p>保存中...</p>
    </div>
</div>

<div id="divList"
     style="display: none;position: absolute; top: 0%; width: 100%; height: 100%; background: rgb(255, 255, 255); z-index: 1001;">
    <header>
        <ul class="clearfix">
            <li>
                <a target="_self" class="close-a">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li class="li-text"></li>
            <li></li>
        </ul>
    </header>
    <div class="content">
        <section class="sec-search">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/selectp/img_01.png"/>
            </label>
            <input type="text" id="search"/>
        </section>
        <section class="sec-ul">
            <ul class="ul-list">

            </ul>
        </section>
        <section class="sec-bottom">
            <p>
                确定
            </p>
        </section>
    </div>
</div>
</body>
</html>
