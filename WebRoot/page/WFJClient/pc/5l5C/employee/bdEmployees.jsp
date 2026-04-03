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
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/bdEmployee.js"></script>

    <title>员工报到</title>
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
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>报到入职</p>
            </li>
            <li class="clearfix">
                <p class="print-p"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>删除</p>
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
                    <div class="sn-p" title="姓名">姓名</div>
                    <div class="oask-p" title="应聘方向">应聘方向</div>
                    <div class="ln-p" title="应聘职位">应聘职位</div>
                    <div class="ln-p" title="面试状态">面试状态</div>
                    <div class="ln-p" title="工作经验">工作经验</div>
                </div>
            </li>
        </ul>
    </section>
</div>


<div class="div-mianshi">
    <div class="div-box">
        <p class="p-top">
            员工入职信息
        </p>
        <p class="p-inp clearfix">
            <label>入职时间</label>
            <input type="date" placeholder="请填写入职时间" name="registerDate" id="registerDate"/>
        </p>
        <p class="p-inp clearfix">
            <label>转正时间</label>
            <input type="date" placeholder="请填写转正时间" name="becomesDate" id="becomesDate"/>
        </p>
        <p class="p-inp clearfix">
            <label>报到地点</label>
            <input type="text" placeholder="请填写报到地点" name="auditionPlace" id="auditionPlace"/>
        </p>
        <p class="p-inp clearfix">
            <label>备注</label>
            <input type="text" placeholder="请填写备注" name="remark" id="remark"/>
        </p>
        <p class="m-bottom">
            保存
        </p>
    </div>
</div>



</body>
</html>
