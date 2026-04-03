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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/securityManage.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/mianshiNotice.js"></script>

    <title>面试通知</title>
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

            <li class="clearfix ">
                <p class="mianshi"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>面试通知</p>
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
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="tittle-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>


<div class="div-mianshi">
    <div class="div-box">
        <p class="p-top">
            员工面试信息
        </p>
        <p class="p-inp clearfix">
            <label>面试地点</label>
            <input type="text" placeholder="请填写面试地点" name="place" id="place"/>
        </p>
        <p class="p-inp clearfix">
            <label>面试时间</label>
            <input type="text" placeholder="请填写面试时间" name="auditionDate" id="auditionDate"/>
        </p>
        <p class="p-inp clearfix">
            <label>面试考官</label>
            <input type="text" placeholder="请填写面试考官" name="examiner" id="examiner"/>
        </p>
        <p class="p-inp clearfix">
            <label>面试部门</label>
            <input type="text" placeholder="请填写面试部门" name="auditionDept" id="auditionDept"/>
        </p>
        <p class="m-bottom">
            保存
        </p>
    </div>
</div>



</body>
</html>
