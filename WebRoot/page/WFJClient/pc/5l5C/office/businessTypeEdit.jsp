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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/businessType/businessTypeAdd.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/businessType/businessTypeAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>项目设计修改</title>


</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            项目设计修改
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
<div class="content">


    <div class="div-name div-htfl">
        <label for="" class="htfl">父级行业</label>
        <p class="p-htfl">${businessType.typePName}</p>
    </div>
    <div class="div-name">
        <label for="">项目编号</label>
        <input type="text"  placeholder="请填写项目编号"  name="typeNum" id="typeNum" value="${businessType.typeNum}"/>
        <input type="hidden"  name="typePID" value="${businessType.typePID}"/>
        <input type="hidden"  name="typeKey" value="${businessType.typeKey}"/>
        <input type="hidden"  name="oldTypeNum" value="${businessType.typeNum}"/>
    </div>

    <div class="div-name">
        <label for="">行业名称</label>
        <input type="text"  placeholder="请填写行业名称"  name="typeName" id="typeName" value="${businessType.typeName}"/>
    </div>
    <div class="div-name">
        <label for="">行业描述</label>
        <input type="text"  placeholder="请填写行业描述"  name="typeDesc" id="typeDesc" value="${businessType.typeDesc}"/>
    </div>

    <div class="div-bottom"  onclick="update()">保存</div>

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

</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var typeName = "${businessType.typeName}";
console.log("获取："+typeName);
</script>
</html>
