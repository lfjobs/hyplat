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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectDept.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectDept.js"></script>
    <title>人员信息</title>

</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick=deptBack() target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li >
            选择部门
        </li>
    </ul>
</header>
<div class="content" >
    <div class="select-dept-data"><label class="div-dept">部门：</label><label class="dept-name"></label></div>
    <article id="deptBox" >
        <ul id="deptList" class="dept-list div-mask-list"></ul>
    </article>
    <div class="div-bottom" style="display:none">
        <div class="dept-data" >
            <div style="padding:10px">
                <span>已选择：</span>
                <span class="person-num"></span>
                <i class="layui-icon" style="float:right;">&#xe619;</i>
            </div>
        </div>
        <button class="layui-btn layui-btn-radius person-submit" style="background-color:#ef8717;font-size:16px;padding:0 30px" onclick="submitData()">
            确定&nbsp;&nbsp;
            <span class="div-dept-data">1/100</span>
        </button>
    </div>
</div>
<!--已选数据-->
<section id="dataLayer" class="express-data-box">
    <header>
        <h3 style="font-weight:bold">已选择</h3>
        <i class="layui-icon close-mask"  onclick="closeMask()">&#x1006;</i>
        <span class="submit-mask">确定</span>
    </header>
    <article id="dataBox"  style="overflow-y:auto">
        <ul id="selectedDataList" class=" div-mask-list"></ul>
    </article>
</section>
<!--遮罩层-->
<div id="mask" class="mask"></div>
</body>
<script type="text/javascript">
    const basePath = "<%=basePath%>";
    const selectedId= "${param.selectedId}";
    const type= "${param.type}";
    var selectedData = [];
    if (selectedId != ""){
        const selectedIdArr = selectedId.split(",");
        for (let i = 0; i < selectedIdArr.length; i++){
            selectedData.push(selectedIdArr[i]);
        }
    }


</script>
</html>
