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
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
   <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/filePre.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/fileTmpPre.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>


    <title>&lrm;</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var  ppid = "${obj[0]}";
        var totalMoney = "${obj[1]}";
        var sccid = "${sccId}";
        var staffID = "${staffID}";
        var companyName = "${obj[2]}";
        var goodsName = "${obj[3]}";
        var  docId = "${doc.docId}";
    </script>
    <style>
        .fileContent{
          width: 96%;
          padding: 0.5rem 2% 0 2%;
          overflow-x: hidden;
      }
    </style>
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
            学员培训协议
        </li>
    </ul>
</header>

<div class="fileContent">
${fileStr}
    </div>
<!--选择支付方式弹框-->
<div class="div-zffs" style="display: none;">
    <div class="box">
        <p>
            选择支付方式
            <img id="inp-close" src="<%=basePath%>images/ea/office/contract/img_03.png"/>
        </p>
        <ul>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/img_10.png"/>
                </div>
                <p>微信支付</p>
                <div class="sex">
                    <input id="female-1" type="radio" name="pay"  checked="checked" class="input-male" data-index="3">
                    <span class="female-custom active"></span>
                </div>
            </li>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/img_07.png"/>
                </div>
                <p>支付宝支付</p>
                <div class="sex">
                    <input id="female-2" type="radio" name="pay" class="input-male" data-index="1">
                    <span class="female-custom"></span>
                </div>
            </li>

        </ul>
        <section>
            <input type="button"  id="inp-yes" value="确定支付(${obj[1]}元)" />
        </section>
    </div>
</div>
</body>
<section class="sec-zf">
    <p>
        支付并签约
    </p>
</section>

</body>

</html>
