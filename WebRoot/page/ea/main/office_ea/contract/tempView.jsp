<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page language="java" import="hy.ea.bo.office.DocumentFileAttach" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java"
         import="java.util.*,com.zhuozhengsoft.pageoffice.*"
%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/tempView.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png">
            </a>
        </li>
        <li>
            查看模板
        </li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

    <div class="content">
        <div class="div-leixing clearfix">
            <div class="div-left">
                <label for="">是否共享</label>
            </div>
            <div class="div-right clearfix" id="div-leixing">
                <p class="p-leixing">${param.sysSet eq "00"?"是":"否"}</p>


            </div>
        </div>
        <div class="div-name">
            <label for="">模板分类</label>
            <input type="text" placeholder="请选择模板分类" readonly id="specificTemplate" value="${param.templateTypeName}"/>


        </div>

        <div class="div-name">
            <label for="">模板名称</label>
            <input type="text" placeholder="请填写模板名称" readonly value="${param.fileShowName}"/>
        </div>


        <div class="div-leixing clearfix">
            <div class="div-left">
                <label for="">文件格式</label>

            </div>
            <div class="div-right clearfix" id="div-docType">
                <p class="p-docType">${param.fileType eq "W"?"Word":"Excel"}</p>


            </div>
        </div>




        <div class="div-bottom">
            <p onclick="viewContent()">
                查看正文
            </p>

        </div>

    </div>

</form>

</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";

    function viewContent(){
        var title = "${param.fileShowName}";

        var templateId = "${param.templateId}";


        var ulp = basePath
                + "ea/androiddoc/sajax_ea_getPdfTempView.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
                templateId: templateId
            },
            success: function (data) {
                var member = eval('(' + data + ')');
                var pdfpath = member.pdfpath;

                document.location.href = basePath + "page/ea/main/office_ea/contract/pdfTempView.jsp?pdfpath="+pdfpath+"&title="+title;


            },
            error: function (data) {

                console.log("获取链接失败");
            }


        });

    }

</script>
</html>
