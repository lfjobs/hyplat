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
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/fenPeiEmployee.js"></script>

    <title>员工分配</title>
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
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>人员分配</p>
            </li>

            <li class="clearfix">
                <p class="query"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>查看</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="sex"> 选择</div>
                    <div class="sn-p" title="姓名">公司名称</div>
                    <div class="oask-p" title="应聘方向">编员人数</div>
                    <div class="ln-p" title="应聘职位">职务名称</div>
                    <div class="ln-p" title="面试状态">职务编号</div>
                </div>
            </li>
        </ul>
    </section>
</div>
</body>
</html>
