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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/carmanage/feeAuditAdd.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/carmanage/feeAuditAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>


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
             免人工审核添加
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">



        <div class="div-name">
            <label for="">车牌号</label>
            <input type="text" id="carNumber" placeholder="请输入车牌号" autofocus name="carNumber" />


        </div>




    <div class="div-name">
        <label for="">场地名称</label>

        <select class="site" name="siteId"></select>

        <input type="hidden"  id="siteName"  name="siteName" />
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

</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";

</script>
</html>
