<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>未注册粉丝查询</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            未注册粉丝查询
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="container">
                <div class="dataPo">
                    <s:form id="queryCrmCustomerPO" action="ea_updateData.jspa" namespace="/ea/crmCustomerPO"
                            method="POST">
                        <div class="div-title-name"><label>基本信息</label></div>
                        <div class="div-name" style="display: none">
                            <label>id</label>
                            <input type="text" id="id" name="crmCustomerPO.id"
                                   value="${crmCustomerPO.id}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>姓名</label>
                            <input type="text" placeholder="请输入姓名" id="userName" name="crmCustomerPO.userName"
                                   value="${crmCustomerPO.userName}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>电话</label>
                            <input type="text" placeholder="请输入手机号" id="contactInfo"
                                   name="crmCustomerPO.contactInfo"
                                   value="${crmCustomerPO.contactInfo}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>身份证</label>
                            <input type="text" placeholder="请输入身份证号码" id="cardNumber"
                                   name="crmCustomerPO.cardNumber"
                                   value="${crmCustomerPO.cardNumber}"/>
                        </div>
                        <div class="div-name">
                            <label>地址</label>
                            <input type="text" placeholder="请输入居住地址" id="residentialAddress"
                                   name="crmCustomerPO.residentialAddress"
                                   value="${crmCustomerPO.residentialAddress}"/>
                        </div>
                        <div class="div-name">
                            <label>备注</label>
                            <input type="text" placeholder="请填写备注" id="extendInfo" name="crmCustomerPO.extendInfo"
                                   value="${crmCustomerPO.extendInfo}"/>
                        </div>
                        <div class="div-name">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="socialStatus"
                                   name="crmCustomerPO.socialStatus"
                                   value="${crmCustomerPO.socialStatus}"/>
                        </div>
                        <%--隐藏--%>
                        <div class="div-name">
                            <label>导入方式</label>
                            <input type="text" placeholder="" id="importerMode"
                                   name="crmCustomerPO.importerMode"
                                   value="${crmCustomerPO.importerMode}"/>
                        </div>
                        <div class="div-name">
                            <label>导入人</label>
                            <input type="text" placeholder="请输入导入人" id="importerId"
                                   name="crmCustomerPO.importerId"
                                   value="${crmCustomerPO.importerId}"/>
                        </div>
                        <div class="div-name">
                            <label>证件类型</label>
                            <input type="text" placeholder="请输入证件类型" id="cardType" name="crmCustomerPO.cardType"
                                   value="${crmCustomerPO.cardType}"/>
                        </div>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="memberLevel"
                                   name="crmCustomerPO.memberLevel"
                                   value="${crmCustomerPO.memberLevel}"/>
                        </div>
                        <div class="div-name">
                            <label>添加时间</label>
                            <input type="text" placeholder="请输入身份" id="createdAt" name="crmCustomerPO.createdAt"
                                   value="${crmCustomerPO.createdAt}"/>
                        </div>
                        <div class="div-name">
                            <label>修改时间</label>
                            <input type="text" placeholder="请输入身份" id="updatedAt" name="crmCustomerPO.createdAt"
                                   value="${crmCustomerPO.updatedAt}"/>
                        </div>
                    </s:form>
                </div>

            </div>
        </div>
    </section>

</div>
</body>
</html>
