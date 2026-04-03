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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/NfcList.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/employee.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>

    <title>员工列表</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>";
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var companyID = "${param.companyID}";
    </script>
</head>
<body>
<div class="content">
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">
            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>images/WFJClient/pc/5l5c/salary/edit.png"/>修改</p>
            </li>
            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="query"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>查询</p>
            </li>
            <li class="clearfix">
                <p class="print-p"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>打印</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="sex"> 选择</div>
                    <div class="ln-p" title="人员编号">人员编号</div>
                    <div class="sn-p" title="档案编号">档案编号</div>
                    <div class="state-p" title="员工头像">员工头像</div>
                    <div class="model-p pc" title="姓名">姓名</div>
                    <div class="oask-p" title="性别">性别</div>
                    <div class="staff-p pc" title="职务">职务</div>
                    <div class="location-p pc" title="单位名称">单位名称</div>
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

<div class="div-chaxun">
    <div class="div-box">
        <p class="p-top">
            请输入查询信息
        </p>
        <p class="p-inp clearfix">
            <label>安全点编号</label>
            <input type="number" placeholder="请填写安全点编号" name="ln" id="ln"  min="1" oninput="this.value=this.value.replace(/\D/g);" value="${ln}"/>
        </p>
        <p class="p-inp clearfix">
            <label>责任人</label>
            <input type="text" placeholder="请填写责任人" name="staffName" id="staffName" value="${staffName}"/>
        </p>
        <p class="p-inp clearfix">
            <label>安全类别</label>
            <input type="text" placeholder="请填写安全类别" name="oaskName" id="oaskName" value="${oaskName}"/>
        </p>
        <p class="p-bottom">
            查询
        </p>
    </div>
</div>
</body>
</html>
