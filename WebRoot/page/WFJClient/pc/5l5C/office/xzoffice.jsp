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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/cateProcedure.css">
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
            <a onclick="window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            办公文书
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh2" id ="c2">
            <div class="nav-div">
                <ul>
                    <li>
                        <p class="actived" onclick="docflow('doc')">公文</p>
                    </li>
                    <li>
                        <p onclick="docflow('contract')">合同</p>
                    </li>
                    <li>
                        <p>通知</p>
                    </li>
                    <li>
                        <p>通报</p>
                    </li>
                    <li>
                        <p>请示</p>
                    </li>
                    <li>
                        <p>批示</p>
                    </li>
                    <li>
                        <p>章程</p>
                    </li>
                </ul>
               </div>
            <div class="main-div">
            <iframe src="<%=basePath%>/ea/documentcommon/ea_showDocumentModule.jspa?bb=new&module=doc&ifr=nohead&companyID=${param.companyID}";
                    id="mainframe" name="admin1" scrolling="yes" frameBorder="0"></iframe>
                </div>
        </div>
    </div>
</div>
</body>
<script>

    function docflow(module){
        $("#mainframe").attr("src",basePath+"ea/documentcommon/ea_showDocumentModule.jspa?companyID="+companyID+"&module="+module+"&d=<%=Math.random()%>&bb=new&ifr=nohead");

    }
    $(function () {
        $("#mainframe").attr("height",$(window).height()-110);

        $(".box-fh p").click(function () {

           var cy =  $(".box-fh .active").attr("data-value");

           $("#"+cy).hide();

            $(".box-fh .active").removeClass("active");

            $(this).addClass("active");


            var c =  $(this).attr("data-value");

            $("#"+c).show();

        });

        $(".box-fh2 .nav-div p").click(function () {
            $(".box-fh2 .nav-div .actived").removeClass("actived");

            $(this).addClass("actived");




        });

    });
</script>
</html>

