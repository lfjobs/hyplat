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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectWlgr.css?version=20230517"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/budgetBill/selectWlgrSh.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" alt="返回">
            </a>
        </li>
        <li>选择收件人</li>
        <li></li>
    </ul>
</header>
<div class="content">
    <section class="headNav">
        <ul>
            <li><p><a href="javascript:nav('1','${param.keyId}','${param.type}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">集团内部</a></p></li>
            <li><p><a href="javascript:nav('2','${param.keyId}','${param.type}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来公司</a></p></li>
            <li><p><a href="javascript:nav('3','${param.keyId}','${param.type}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">往来个人</a></p></li>

        </ul>
    </section>
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="请输入姓名或者手机号" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <p class="resulttip">该用户尚未注册</p>
    <p class="navrecent">以下是最近联系人：</p>
    <section class="sec-ul">
        <ul class="ul-list">

        </ul>
    </section>
    <section class="sec-bottom">
        <p>
           确定提交
        </p>
    </section>
</div>



<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>

<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
        <p>提交中...</p>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var orgID = "${param.orgID}";
    var parameter = "${param.parameter}";
    var type = "${param.type}";
    var keyId = "${param.keyId}";
    var menuId = "${menuId}";

    function nav(c,keyId,type) {
        if(c=="1"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectCompanySh.jsp?type="+type+"&keyId="+keyId);
        } else if(c=="2"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectWldwSh.jsp?type="+type+"&keyId="+keyId);

        } else if(c=="3"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectWlgrSh.jsp?type="+type+"&keyId="+keyId);
        }
    }
</script>
</html>

