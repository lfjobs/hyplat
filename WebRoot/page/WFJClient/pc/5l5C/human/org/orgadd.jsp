<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/org/orgadd.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/org/orgadd.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>


</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            组织机构<span class="title-span">添加</span>
        </li>
    </ul>
</header>
<form name="organizationform" id="organizationform" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">


    <div class="div-name div-htfl">
        <label for="" class="htfl">所属上级：</label>
        <p class="p-htfl">${porganization.organizationName}</p>

        <input type="hidden"   name="organization.organizationPID" value="${porganization.organizationID}" id="pid"/>
        <input type="hidden"    value="${porganization.organizationLevel}" id="orgLevel"/>
    </div>

    <div class="div-name">
        <label for="">机构序号：</label>

        <input type="text"  placeholder="请填写机构序号"  name="organization.organizationNumber" id="organizationNumber" value="${organization.organizationNumber}"/>


    </div>
    <div class="div-name">
        <label for="">机构编号：</label>

        <input type="text"  placeholder="请填写机构编号"  name="organization.ocode" id="ocode" value="${organization.ocode}"/>


    </div>
    <div class="div-name">
        <label for="">部门名称：</label>

        <input type="text"  placeholder="请填写部门名称"   name="organization.organizationName"  id="organizationName" value="${organization.organizationName}"/>
        <input type="hidden"   name="organization.organizationID"  id="organizationID" value="${organization.organizationID}"/>
        <input type="hidden"   name="organization.organizationLevel"  id="organizationLevel" value="${organization.organizationLevel}"/>

    </div>
    <div class="div-leixing clearfix">
        <div class="div-left">
            <label for="">工作范围：</label>
        </div>
        <div class="div-right clearfix" id="div-org">
            <p class="p-org">人事处办公</p>
            <input type="hidden" id="organizationUrl"  name="organization.organizationUrl" value="${organization.organizationUrl eq null?'/ea/office/ea_toSersonnelSystem':organization.organizationUrl}"/>
            <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png"/>

        </div>
    </div>

    <div class="div-bottom">
        <p class="saveDraft">
            保存
        </p>

    </div>

</div>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>

<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>
<!--选择-->
<div class="div-yinzhang">
    <div class="box">
        <ul>
        <c:forEach items="${SInterfaceList}" var="item">
                <li data-value="${item.interfaceUrl}">
                        ${item.interfaceName}
               </li>
                </c:forEach>

        </ul>
    </div>
</div>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var view = "${param.view}";
if ($("#organizationID").val() === ""){
    $("#organizationLevel").val(parseInt($("#orgLevel").val()) + 1);
}
</script>
</html>
