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
<html>
<base href="<%=basePath%>">

<title>&lrm;</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/documentManage.css">
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
</script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            行政管理
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li>
                    <p class="active">文书管理</p>
                </li>
                <li>
                    <p class="wsfl">分类流程</p>
                </li>
                <li>
                    <p class="t">
                        文书模版
                    </p>
                </li>
            </ul>
        </div>
        <div class="box-fh2">
            <iframe src="<%=basePath%>page/WFJClient/pc/5l5C/office/documentList.jsp?companyID=${param.companyID}"
                    id="mainframe" name="admin1" scrolling="no" frameBorder="0"></iframe>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
//        $("li p").click(function () {
//            if ($(this).is(".active")) {
//                $(this).removeClass("active");
//            } else {
//                $(".active").removeClass("active");
//                $(this).addClass("active");
//            }
//        });



        $(".lc").click(function () {
            window.location.href =basePath+"/ea/documentcommon/ea_showDocumentModule.jspa?bb=new&module=";
        });

        $(".t").click(function () {
            window.location.href =basePath+"//page/WFJClient/pc/5l5C/office/templateManage.jsp?companyID="+companyID;
        });

        $(".wsfl").click(function () {
            window.location.href=basePath+"/page/WFJClient/pc/5l5C/office/cateProcedure.jsp?companyID="+companyID;
        });
    });
</script>
</html>

