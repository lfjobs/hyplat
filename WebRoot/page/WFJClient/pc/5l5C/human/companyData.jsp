<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/companyData.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/menu/companyData.js"></script>
    <title>商家信息</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
    <style>

    </style>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            商家信息
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content" style="overflow-y:auto">
    <div class="sec-con-company">
        <div class="company_list">
            <label class="">认领公司名称</label>
            <input type="text" name="companyName"   value="${contactCompany.companyName }" id="contactCompany.companyName"/>
        </div>
        <div class="company_list">
            <label class="">类别</label>
            <input type="text" name="type"   id="type"/>
        </div>
        <div class="company_list">
            <label class="">认领企业金额</label>
            <input type="text" name="ccomTypeName"    id="ccomTypeName"/>
        </div>
        <div class="company_list">
            <label class="">交易时间</label>
            <input type="text" name="teccDate"   value="${cusCom.teccDate }" id="teccDate"/>
        </div>
        <div class="company_list">
            <label class="">支付账号</label>
            <input type="text" name="account"   value="${cusCom.account }" id="account"/>
        </div>
        <div class="company_list">
            <label class="">认证是否完善</label>
            <input type="text" name="authStateName"   id="authStateName"/>
        </div>
        <div class="company_list">
            <label class="">责任人</label>
            <input type="text" name="cresponsible"   value="${contactCompany.cresponsible }" id="cresponsible"/>
        </div>
        <div class="company_list">
            <label class="">电话回访记录</label>
            <input type="text" name="visitListName"   value="" id="visitListName"/>
        </div>
    </div>
    <div class="div-card">
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyId = "${company.companyID}";
    $(".company_list input").width($(".sec-con-company").width() - 130);
    var ccomType = "${company.ccomtype }" == "" ? 0 : "${company.ccomtype }";
    var ccomTypeArr = ["200000元","100000元","50000元","12000元","3500元","600元","0元"];
    $("#ccomTypeName").val(ccomTypeArr[ccomType]);
    var typeName = "初级认领";
    $("#type").val(typeName);
    var authState = "${contactCompany.authState}";
    $("#authStateName").val("未完善");
    if (authState == "02") {
        $("#authStateName").val("未完善");
    }
   
</script>
</body>

</html>