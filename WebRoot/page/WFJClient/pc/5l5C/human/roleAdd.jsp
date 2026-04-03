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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/roleAdd.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <script src="<%=basePath%>js/ea/human/menu/roleAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>角色编辑</title>
    <style>
        .express-dept-box header .close-mask { right: 0; background: url(<%=basePath%>images/ea/office/contract/stamp/close.png) no-repeat center; background-size: .675rem .675rem; }

    </style>
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
            角色添加
        </li>
    </ul>
</header>
<div class="content">
    <form name="form" id="form" method="post" class="div-form">
        <div class="div-name">
            <label >部门名称</label>
            <input type="text"  placeholder="请选择部门名称"  name="organizationNameDesc" id="organizationNameDesc" value="${role.organizationNameDesc}" readonly/>
            <input type="hidden"  name="oldOrganizationNameDesc" id="oldOrganizationNameDesc" value="${role.organizationNameDesc}"/>
            <input type="hidden"    name="organizationName" id="organizationName" value="${role.organizationName}"/>
        </div>
        <div class="div-name">
            <label >岗位名称</label>
            <input type="text"  placeholder="请填写岗位名称"  name="roleName" id="roleName" value="${role.roleName}"/>
            <input type="hidden"  name="oldRoleName" id="oldRoleName" value="${role.roleName}"/>
            <input type="hidden"    name="roleId" id="roleId" value="${role.roleID}"/>
            <input type="hidden"    name="roleKey" id="roleKey" value="${role.roleKey}"/>
        </div>

        <div class="div-name" >
            <label >描述</label>
            <input type="text"  placeholder="请填写描述"  name="roleDesc" id="roleDesc" value="${role.roleDesc}"/>
        </div>
    </form>
    <div class="div-role-menu">
        <div id="menuTree" style="width: 100%;z-index:99;"></div>
    </div>
    <div class="div-bottom"  onclick="save()">保存</div>

</div>
<!--表单提示-->
<%--<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>--%>
<!--选择部门-->
<section id="deptLayer" class="express-dept-box">
    <header>
        <h3>选择部门</h3>
        <i class="layui-icon div-back" style="display:none" onclick="deptBack()">&#xe603;</i>
        <a id="closeMenu" class="close-mask" href="javascript:void(0)" title="关闭"></a>
    </header>
    <div class="select-dept-data"><label class="div-dept">部门：</label><label class="dept-name"></label></div>
    <article id="deptBox" >
        <ul id="deptList" class="dept-list div-mask-list"></ul>
    </article>
</section>

<!--遮罩层-->
<div id="mask" class="mask"></div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const type = "${param.type}";
    const roleId = "${param.roleId}";
    $("#roleId").val(roleId);
    if (type == "power"){
        $(".div-form").hide();
        $(".div-role-menu").show();
    } else {
        $(".div-form").show();
        $(".div-role-menu").hide();
    }
    if (type == "edit"){
        $(".div_header").html("角色修改");
    } else  if (type == "power"){
        $(".div_header").html("角色授权");
    }

</script>
</html>
