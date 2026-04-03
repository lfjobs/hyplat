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

<title>安全</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/securityManage.css">
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sort = "${param.sort}";
    var companyID = "${param.companyID}";
    var pagetype="${pagetype}";
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
            安全
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
                    <p>巡查</p>
                </li>
                <li>
                    <p id="handle">处理</p>
                </li>
                <li>
                    <p id="emergency">
                        应急
                    </p>
                </li>
            </ul>
            <ul class="ul-tab2 ul-tab-fh clearfix">
                <li>
                    <p id="Inspect">巡查管理</p>
                </li>
                <li>
                    <p id="NFC">绑定设置</p>
                </li>
                <li>
                    <p>
                        安全分类
                    </p>
                </li>
            </ul>
        </div>
        <div class="box-fh2">
            <iframe id="mainframe" name="admin1" scrolling="no" frameBorder="0"></iframe>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("li p").click(function () {
            if ($(this).is(".active")) {
                $(this).removeClass("active");
            } else {
                $(".active").removeClass("active");
                $(this).addClass("active");
            }
        });

        $("#Inspect").click(function () {
            $("#mainframe").attr("src", basePath + "/page/WFJClient/secure/nfc/InspectList.jsp?companyID=" + companyID);
        });

        $("#NFC").click(function () {
            $("#mainframe").attr("src", basePath + "/page/WFJClient/secure/nfc/NfcList.jsp?companyID=" + companyID);
        });

        $("#handle").click(function () {
            window.location.href =basePath+"page/WFJClient/secure/nfc/secureHandle.jsp?companyID=" + companyID;
        });

        $("#emergency").click(function () {
            window.location.href =basePath+"page/WFJClient/secure/nfc/secureEmergency.jsp?companyID=" + companyID;
        });

        if(pagetype=="nfc"){
            $("#NFC").trigger("click");
        }else if(pagetype=="inspect"){
            $("#Inspect").trigger("click");
        }else {
            $("#Inspect").trigger("click");
        }
    });
</script>
</html>

