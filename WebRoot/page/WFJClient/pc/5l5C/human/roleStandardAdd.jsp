<%@ page import="hy.ea.bo.CAccount" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/roleStandardAdd.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <script src="<%=basePath%>js/ea/human/menu/roleStandardAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>岗位标准化修改</title>

</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li class="div_header">
            岗位添加
        </li>
    </ul>
</header>
<div class="content">
    <form name="form" id="form" method="post" class="div-form">
        <div class="div-name">
            <label >岗位名称</label>
            <input type="text"  placeholder="请填写岗位名称"  name="roleName" id="roleName" value="${roleStandard.roleName}"/>
            <input type="hidden"  name="oldRoleName" id="oldRoleName" value="${roleStandard.roleName}"/>
            <input type="hidden"  name="roleId" id="roleId" value="${roleStandard.roleId}"/>
        </div>

        <div class="div-name" >
            <label >描述</label>
            <input type="text"  placeholder="请填写描述"  name="roleDesc" id="roleDesc" value="${roleStandard.roleDesc}"/>
        </div>
    </form>
    <div class="div-role-menu">
        <div id="menuTree" style="width: 100%;z-index:99;"></div>
    </div>
    <div class="div-bottom"  onclick="save()">保存</div>

</div>


</body>
<script type="text/javascript">
    const basePath = "<%=basePath%>";
    const type = "${param.type}";
    const roleId = "${param.roleId}";
    const empowerId = "${param.empowerId}";
    $("#empowerId").val(empowerId);
    $("#roleId").val(roleId);
    if (type == "power"){
        $(".div-form").hide();
        $(".div-role-menu").show();
    } else {
        $(".div-form").show();
        $(".div-role-menu").hide();
    }
    if (type == "edit"){
        $(".div_header").html("岗位修改");
    } else  if (type == "power"){
        $(".div_header").html("岗位授权");
    }

</script>
</html>
