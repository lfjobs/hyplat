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
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/employeeList.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/mianshiEnd.js"></script>

    <title>面试结果</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>";
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body>
<div class="content">
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">
            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>口试通过</p>
            </li>
            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>笔试通过</p>
            </li>
            <li class="clearfix ">
                <p class="mianshi"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>口试未通过</p>
            </li>
            <li class="clearfix">
                <p class="print-p"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>笔试未通过</p>
            </li>
            <li class="clearfix">
                <p class="query"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>查询</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="sex"> 选择</div>
                    <div class="ln-p" title="人员编号" name = "iddd">人员编号</div>
                    <div class="sn-p" title="姓名">姓名</div>
                    <div class="oask-p" title="电话">电话</div>
                    <div class="ln-p" title="身份证">身份证</div>
                </div>
            </li>
        </ul>
    </section>
</div>


</body>
</html>
