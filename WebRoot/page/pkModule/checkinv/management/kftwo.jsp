<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");

%>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>库房管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/kufang/kufang.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/kufang/swiper.min.css"/>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/kufang/javascript.js" type="application/javascript"></script>
</head>
<script>
    var basePath = "<%=basePath%>";
    var depotPID = '${depotID}';
    var departmentId = "${departmentID}";//部门id
    var showFlag = "${showFlag}";//false所有查询全部数据 true查询当前部门数据
    var sort = "${sort}";
</script>
<body class="">
<ul class="header header2" id="ulTitle">
    <li onclick="toBack()"><img src="<%=basePath %>/images/parkkingpay/ico-left.png" alt=""></li>
    <li>库房管理</li>
</ul>
<div class="kf_con">
    <ul class="ck2">
       <c:forEach items="${depotManagelist}" var = "entity" varStatus="v" >
        <li onclick="deoptone(this)" value="${entity.depotID}">${entity.depotName}</li>
       </c:forEach>
    </ul>
</div>
</body>
<script>
        if (sort == 2) {
            $("#ulTitle").hide();
        } else {
            $("#ulTitle").show();
        }
    //盘库仓库的跳转方法
    function deoptone(obj) {
        var depotName = $(obj).text();
        var depotId = obj.attributes[1].nodeValue;
        if (sort == 2) {
            $('#kfname', window.parent.document).text(depotName);
            $('#kfid', window.parent.document).val(depotId);
            $('#kfid', window.parent.document).text(depotId);
            $(".div-kc", window.parent.document).hide();
        } else {
            var url = "ea/cashinv/ea_toAddCheckInv.jspa";
            var parameter = "?depotName="+depotName+"&depotId="+depotId;
            window.location.href = basePath + url + parameter;
        }
    }
    //后退
    function toBack() {
        window.location.href = basePath + "ea/cashinv/ea_tree.jspa";
    }
</script>
</html>

