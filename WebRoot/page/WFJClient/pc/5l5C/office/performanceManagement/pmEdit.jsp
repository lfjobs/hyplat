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
    <script src="<%=basePath%>js/ea/office_ea/performanceManagement/pmAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>激励绩效修改</title>
    <style>
        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
        }
        .content .div-bottom {
            border-top: 5rem solid #f8f8f8;
        }
    </style>

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
            激励绩效修改
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <div class="content">


        <div id="parentIdDiv" class="div-name div-htfl">
            <label for="" class="htfl">父层级</label>
            <p id="parentName" class="p-htfl">${pm.parentName}</p>
        </div>
        <div class="div-name">
            <label for="">名称</label>
            <input type="text"  placeholder="请填写名称"  name="name" id="name" value="${pm.name}"/>
            <input type="hidden"  name="parentId" value="${pm.parentId}"/>
            <input type="hidden"  name="key" value="${pm.key}"/>
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
<div id="prompt" style="width: 100%; display: none;z-index: 1001">
    <center>
        <div>
            <span style="position: relative; top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
</html>
