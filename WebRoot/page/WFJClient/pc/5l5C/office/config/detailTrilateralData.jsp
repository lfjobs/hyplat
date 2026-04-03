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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>三方-详情</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralData.css">
    <%--    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/specsConfig.css">--%>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body>
<header>
    <div class="div-ul1">
        <ul class="clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li>
                三方
            </li>
            <li>
            </li>
        </ul>
    </div>
</header>
<div class="content" style="width: 100%">
    <s:form id="updateTrilateralData" action="ea_assignment3.jspa" namespace="/ea/trilateral" method="POST">
        <div class="div-name">
            <P style="display: none">
                <label>id</label>
                <input type="text" name="trilateralData.id"
                       id="id" value="${trilateralData.id}"/>
            </p>
            <P>
                <label>公司名称</label>
                <input required type="text" placeholder="请填写公司名称" name="trilateralData.companyName"
                       id="companyName" value="${trilateralData.companyName}"/>
            </p>
            <p>
                <label>人员编号</label>
                <input type="text" placeholder="请填写人员编号" name="trilateralData.personnelID" id="personnelID"
                       value="${trilateralData.personnelID}"/>
            </p>
            <p>
                <label>姓名</label>
                <input required type="text" placeholder="请填写姓名" name="trilateralData.name" id="name"
                       value="${trilateralData.name}"/>
            </p>
            <p>
                <label>性别</label>
                <input type="text" placeholder="请填写性别" name="trilateralData.sex" id="sex"
                       value="${trilateralData.sex}"/>
            </p>
            <p>
                <label>行业</label>
                <input type="text" placeholder="请填写行业" name="trilateralData.sector" id="sector"
                       value="${trilateralData.sector}"/>
            </p>
            <p>
                <label>电话</label>
                <input required type="text" placeholder="请填写手机号" name="trilateralData.phone" id="phone"
                       value="${trilateralData.phone}"/>
            </p>
            <p>
                <label>注册平台名称</label>
                <input required type="text" placeholder="请填写注册平台名称"
                       name="trilateralData.registrationPlatformName" id="registrationPlatformName"
                       value="${trilateralData.registrationPlatformName}"/>
            </p>
            <p>
                <label>注册号</label>
                <input required type="text" placeholder="请填写注册号" name="trilateralData.registrationNo"
                       id="registrationNo" value="${trilateralData.registrationNo}"/>
            </p>
            <p>
                <label>注册账号</label>
                <input required type="text" placeholder="请填写注册账号" name="trilateralData.registerAccount"
                       id="registerAccount" value="${trilateralData.registerAccount}"/>
            </p>
            <p>
                <label>密码</label>
                <input required type="text" placeholder="请填写密码" name="trilateralData.pwd" id="pwd"
                       value="${trilateralData.pwd}"/>
            </p>
            <p>
                <label>上传项目</label>
                                    <input type="text" placeholder="请填写上传项目" name="trilateralData.uploadProject" id="uploadProject" value="${trilateralData.uploadProject}"/>

            </p>
            <p>
                <label>推广事由</label>
                <input type="text" placeholder="请填写推广事由" name="trilateralData.promotionReasons"
                       id="promotionReasons" value="${trilateralData.promotionReasons}"/>
            </p>
            <p>
                <label>分配服务</label>
                <input type="text" placeholder="请填写分配服务" name="trilateralData.distributionService"
                       id="distributionService" value="${trilateralData.distributionService}"/>
            </p>
            <p>
                <label>服务跟踪</label>
                <input type="text" placeholder="请填写服务跟踪" name="trilateralData.serviceTracking"
                       id="serviceTracking" value="${trilateralData.serviceTracking}"/>
            </p>
            <p>
                <label>业务人</label>
                <input type="text" placeholder="请填写业务人" name="trilateralData.businessPersonnel"
                       id="businessPersonnel" value="${trilateralData.businessPersonnel}"/>
            </p>
        </div>
        <div class="inputBut100">
            <button type="button" style="width: 100%; height: 50px; background: #b9b2b1; font-size: 19px; color: #392d2d; border: none; cursor: pointer;" onclick="assignment('${trilateralData.id}')">
                分配业务员
            </button></div>
    </s:form>

</div>

<script>
    /**
     * 分配业务员
     */
    function assignment(id) {
        event.preventDefault(); // 阻止默认行为
        // 构建安全的 URL
        var url = basePath + "ea/trilateral/ea_assignment3.jspa?id=" + encodeURIComponent(id);
        // 提交表单或导航到新页面
        document.getElementById('updateTrilateralData').action = url;
        document.getElementById('updateTrilateralData').submit();
    }
</script>

</body>
</html>
