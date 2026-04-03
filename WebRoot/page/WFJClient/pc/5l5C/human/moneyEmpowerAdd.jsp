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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/moneyEmpowerAdd.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/menu/moneyEmpowerAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>权限编辑</title>

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
            权限添加
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <div class="content">

        <div class="div-name">
            <label class="div-empower-name" style="width:110px;float:left">权限金额(元)</label>
            <input type="text"  placeholder="请填写权限金额(元)"  name="empowerName" id="empowerName" style="float:left" value="${moneyEmpower.empowerName}"/>
            <input type="hidden"  name="oldEmpowerName" id="oldEmpowerName" value="${moneyEmpower.empowerName}"/>
            <input type="hidden"  name="empowerKey" id="empowerKey" value="${moneyEmpower.empowerKey}"/>
        </div>
        <div class="div-name ">
            <label  class="div_type">企业类型</label>
            <a id="companyTypeSelect" href="javascript:void(0)">
                <dl>
                    <dd>0元加入</dd>
                    <input type="text"  style="display:none"  name="ccomType" id="ccomType" value="${moneyEmpower.ccomType}"/>
                    <input type="text"  style="display:none"  name="oldCcomType" id="oldCcomType" value="${moneyEmpower.ccomType}"/>
                </dl>
            </a>
        </div>
        <div class="div-name div_empower_desc">
            <label >描述</label>
            <input type="text"  placeholder="请填写描述"  name="empowerDesc" id="empowerDesc" style="width:100%" value="${moneyEmpower.empowerDesc}"/>
        </div>

        <div class="div-bottom"  onclick="save()">保存</div>

    </div>

</form>
<!--选择菜单类型弹层-->
<section id="companyTypeLayer" class="express-type-box">
    <header>
        <h3>企业类型</h3>
        <a id="closeType" class="close-mask" href="javascript:void(0)" title="关闭"></a>
    </header>
    <article id="typeBox">
        <ul id="typeList" class="type-list div-mask-list">
            <li onClick="selectCompanyType(0);">大型企业平台管理商城系统</li>
            <li onClick="selectCompanyType(1);">中型企业平台管理商城系统</li>
            <li onClick="selectCompanyType(2);">小型企业平台管理商城系统</li>
            <li onClick="selectCompanyType(3);">微型企业平台管理商城系统</li>
            <li onClick="selectCompanyType(4);">小微型企业平台管理商城系统</li>
            <li onClick="selectCompanyType(5);">供应商企业平台管理商城系统</li>
            <li onClick="selectCompanyType(6);">0元加入</li>
        </ul>
    </article>
</section>
<!--遮罩层-->
<div id="mask" class="mask"></div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>"
    const type = "${param.type}";

</script>
</html>
