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
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/perstudentinfo.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/perstudentinfo.js" type="text/javascript" charset="utf-8"></script>
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
         学员信息
        </li>

    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

    <div class="content">

        <div class="div-name">
            <label for="">学员姓名：</label>
            <input type="text"  placeholder="请填写学员真实姓名"   name = "noviceName" id="noviceName" value="${cashierBills.ctUserName}" />
        </div>

        <div class="div-name">
            <label for="">身份证号码：</label>
            <input type="text"  placeholder="填写身份证号码"  name="noviceCode" id="noviceCode" value="${cashierBills.staffIdentityCard}"/>
            <input type="hidden"  name="cashierBillsID" id="cashierBillsID" value="${cashierBills.cashierBillsID}"/>
        </div>
        <div class="div-name">
            <label for="">手机号：</label>
            <input type="text"  placeholder="填写手机号"  name="reference" id="reference" value="${cashierBills.reference}"/>

        </div>

        <div class="div-bottom">
            <p class="save">
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
