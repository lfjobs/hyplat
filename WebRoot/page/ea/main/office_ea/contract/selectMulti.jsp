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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectMulti.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/selectMulti.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<div id="overlay" >
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li>
            分发文件
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
       <p>添加接收人</p>
    </section>
    <section class="sec-ul">
        <ul class="ul-list">
          <%--  <li>
                <div class="z-div">


                    <img class="del-btn" src="<%=basePath%>images/ea/office/contract/del.png">
                    <img class="h-btn" src="<%=basePath%>images/ea/production/head2x.png"/>


                    <p>孟竹</p>

                </div>

            </li>
            <li>
                <div class="z-div">


                    <img class="del-btn" src="<%=basePath%>images/ea/office/contract/del.png">
                    <img class="h-btn" src="<%=basePath%>images/ea/production/head2x.png"/>


                    <p>孟竹</p>

                </div>

            </li>
            <li>
                <div class="z-div">


                    <img class="del-btn" src="<%=basePath%>images/ea/office/contract/del.png">
                    <img class="h-btn" src="<%=basePath%>images/ea/production/head2x.png"/>


                    <p>孟竹</p>

                </div>

            </li>--%>
        </ul>
    </section>
    <p class="navrecent">以下是最近联系人：</p>
    <section class="sec-ul2">
        <ul class="ul-list2">

        </ul>
    </section>
    <div class="plus-p"><img class="plus" src="<%=basePath%>images/ea/office/contract/plus.png"></div>
    <section class="sec-bottom">
        <p>
            确定分发
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

<div class="iframecom" >
<iframe id="iframe" src="<%=basePath%>page/ea/main/office_ea/contract/selectCompany.jsp" width="100%" height="100%" frameborder="0"></iframe>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var orgID = "${param.orgID}";
    var parameter = "${param.parameter}";
    var typee = "${param.typee}";
    var docId = "${param.docId}";


</script>
</html>

