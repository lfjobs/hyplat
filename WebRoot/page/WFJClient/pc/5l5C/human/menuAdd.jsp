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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/menuAdd.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/menu/menuAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>菜单编辑</title>
    <style>
        .express-type-box header .close-mask { right: 0; background: url(<%=basePath%>images/ea/office/contract/stamp/close.png) no-repeat center; background-size: .675rem .675rem; }

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
            菜单添加
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <div class="content">
        <div class="div-name div_menu_parent" >
            <label style="width:30%;float:left">父级菜单</label>
            <a id="menuParentTree" href="javascript:void(0)">
                <dl>
                    <dd class="div_parent_name">${menu.menuParentName}</dd>
                    <input type="hidden"  name="menuPID" id="menuPID" value="${menu.menuPID}"/>
                    <input type="hidden"  name="menuPIDList" id="menuPIDList" value="${menu.menuPIDList}"/>
                    <input type="hidden"  name="oldMenuPID" id="oldMenuPID" value="${menu.menuPID}"/>
                    <input type="hidden"  name="menuKey" value="${menu.menuKey}"/>
                    <input type="hidden"  name="menuId" value="${menu.menuId}"/>
                </dl>
            </a>
        </div>
        <div class="div-name ">
            <label  class="div_type">菜单类型</label>
            <a id="menuTypeSelect" href="javascript:void(0)">
                <dl>
                    <dd>目录</dd>
                    <input type="text"  style="display:none"  name="menuType" id="menuType" value="${menu.menuType}"/>
                </dl>
            </a>
        </div>

        <div class="div-name div_menu_name">
            <label >菜单名称</label>
            <input type="text"  placeholder="请填写名称"  name="menuName" id="menuName" value="${menu.menuName}"/>
            <input type="hidden"  name="oldMenuName" id="oldMenuName" value="${menu.menuName}"/>
            <input type="hidden"    name="menuId" id="menuId" value="${menu.menuId}"/>
        </div>

        <div class="div-name div_menu_url" >
            <label >URL路径</label>
            <input type="text"  placeholder="请填写URL路径"  name="menuURL" id="menuURL" value="${menu.menuURL}"/>
        </div>

        <div class="div-name div_menu_mark"  style="display:none">
            <label >授权标识</label>
            <input type="text"  placeholder="请填写授权标识"  name="menuMark" id="menuMark" value="${menu.menuMark}"/>
        </div>

        <div class="div-name div_menu_sort_num"  style="display:none">
            <label >序号</label>
            <input type="number"  placeholder="请填写序号"  name="sortNum" id="sortNum" value="${menu.sortNum}"/>
        </div>

        <div class="div-name div_menu_desc">
            <label >描述</label>
            <input type="text"  placeholder="请填写描述"  name="menuDesc" id="menuDesc" value="${menu.menuDesc}"/>
        </div>

        <div class="div-bottom"  onclick="save()">保存</div>

    </div>

</form>
<!--选择菜单类型弹层-->
<section id="menuTypeLayer" class="express-type-box">
    <header>
        <h3>选择菜单类型</h3>
        <a id="closeType" class="close-mask" href="javascript:void(0)" title="关闭"></a>
    </header>
    <article id="typeBox">
        <ul id="typeList" class="type-list div-mask-list">
            <li onClick="selectMenuType(0);">目录</li>
            <li onClick="selectMenuType(1);">菜单</li>
            <li onClick="selectMenuType(2);">按键</li>
        </ul>
    </article>
</section>
<!--选择父级菜单-->
<section id="menuParentLayer" class="express-type-box">
    <header>
        <h3>选择父级菜单</h3>
        <i class="layui-icon div-back" style="display:none" onclick="menuBack()">&#xe603;</i>
        <a id="closeMenu" class="close-mask" href="javascript:void(0)" title="关闭"></a>
        <%--<i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#xe618;</i>--%>
    </header>
    <article id="menuParentBox" >
        <ul id="menuList" class="menu-list div-mask-list">

        </ul>
    </article>
</section>

<!--遮罩层-->
<div id="mask" class="mask"></div>

</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>"
    const type = "${param.type}";
    let menuPID;
    let num = 0;
    $("#menuType").val(0);
    if (type == "add"){
        menuPID = "${param.menuPID}";
        $("#menuPID").val("${param.menuPID}")
        $(".div_parent_name").html("${param.menuPrentName}")
    } else {
        menuPID = "${menu.menuPID}";
        $(".div_header").html("菜单修改");
        const menuTypeList = ["目录", "菜单", "按键"];
        $("#menuTypeSelect dl dd").html(menuTypeList["${menu.menuType}"]);
        selectMenuType("${menu.menuType}");
        $(".div_menu_sort_num").show();
    }
    if (menuPID == null || menuPID == "") {
        $(".div_menu_parent").hide();
    } else {
        $(".div_menu_parent").show();
    }

</script>
</html>
