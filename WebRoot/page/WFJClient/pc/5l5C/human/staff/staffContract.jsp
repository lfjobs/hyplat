<%@ page import="java.util.Map" %>
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
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/staffData.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>util/jdate/jdate.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/staffContract.js"></script>
    <title>人员合同</title>

</head>

<body>
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            人员合同
        </li>


    </ul>
</header>
<div class="content">
    <form name="form" class="div-form" id="form">
        <div class="div-base-data">
            <div class="div-name div-baseData" onclick="selectData('staff')">
                <label >姓名</label>
                <input type="text"  placeholder="请填写姓名"  name="staff.staffName" id="staffName" value="${staff.staffName}" readonly/>
                <input type="hidden"   id="staffID" name="staff.staffID"  value="${staff.staffID}" />
                <input type="hidden"   id="staffKey" name="staff.staffKey"  value="${staff.staffKey}" />
                <input type="hidden"   id="cosId" name="cos.cosID"  value="${cos.cosID}" readonly/>
                <input type="hidden"   id="cosKey" name="cos.cosKey"  value="${cos.cosKey}" readonly/>
            </div>
            <div class="div-name">
                <label >性别</label>
                <input type="text"  name="staff.sex" id="sex" value="${staff.sex}" readonly />
            </div>
            <div class="div-name" onclick="viewFile()">
                <label >合同类型</label>
                <input type="text"  name="staff.sex" id="contractTypeName" value="${docStaff.contractTypeName}" readonly />
                <i class="layui-icon i-icon-right">&#xe602;</i>
            </div>
            <div class="div-name">
                <label >合同状态</label>
                <input type="text"  name="staff.sex" id="contractStatus"  readonly   />
            </div>
            <div class="div-name">
                <label >合同状态位置</label>
                <input type="text"  name="staff.sex" id="contractStatsName" value="${document.examineResult}" readonly />
            </div>
            <div class="div-name div-baseData" >
                <label >入职时间</label>
                <input type="text"  placeholder="请选择入职时间"  name="cos.entryDate" id="entryDate" value="${cos.entryDate}"/>
            </div>
            <div class="div-name">
                <label >手机号</label>
                <input type="text"  placeholder="请填写手机号"  name="staff.reference" id="reference" value="${staff.reference}"/>
            </div>
            <div class="div-name " onclick="selectData('status')">
                <label >员工类型</label>
                <input type="text"  placeholder="请选择员工类型"  id="statusName"  readonly/>
                <input type="hidden"  placeholder="请选择员工类型"  id="statusId" name="cos.status"  value="${cos.status}" readonly/>
            </div>
            <div class="div-name">
                <label >身份证号</label>
                <input type="text"  placeholder="请选择身份证号" id="staffIdentityCard" name="staff.staffIdentityCard"   value="${staff.staffIdentityCard}" />
            </div>
            <div class="div-name div-baseData" onclick="selectData('level')">
                <label >级别</label>
                <input type="text"  placeholder="请选择级别"  name="salaryLevelSerial" id="salaryLevelSerial" value="${cos.salaryLevelName}" readonly/>
                <input type="hidden"  placeholder="请选择级别" id="salaryLevelId"  name="cos.salaryLevelId"  value="${cos.salaryLevelId}" readonly/>
            </div>
            <div class="div-name " onclick="selectData('post')">
                <label >岗位</label>
                <input type="text"   id="postName"  value="${cos.depPostName}" readonly/>
                <input type="hidden"   id="depPostID"  name="cos.depPostID"  value="${cos.depPostID}" readonly/>
            </div>
            <div class="div-name " >
                <label >角色</label>
                <input type="text"   id="roleName"  name="cos.roleName"  value="${cos.roleName}" readonly/>
                <input type="hidden"  id="roleID"  name="cos.roleId"  value="${cos.roleId}" readonly/>
            </div>
            <div class="div-examine-process" >
                <div class="div-title-name"><label>审核流程</label></div>
                <input type="hidden"   id="drafterName"    value="${document.drafterName}" readonly/>
                <input type="hidden"   id="subscriberName2"    value="${document.subscriberName2}" readonly/>
                <input type="hidden"   id="subscriberName"    value="${document.subscriberName}" readonly/>
                <input type="hidden"   id="sealerName"    value="${document.sealerName}" readonly/>
                <input type="hidden"   id="publisherName"    value="${document.publisherName}" readonly/>
                <input type="hidden"   id="receiverName"    value="${document.receiverName}" readonly/>
                <input type="hidden"   id="guidangName"    value="${document.guidangName}" readonly/>
                <input type="hidden"   id="draftTime"    value="${document.draftTime}" readonly/>
                <input type="hidden"   id="subscribeTime"    value="${document.subscribeTime}" readonly/>
                <input type="hidden"   id="sealTime"    value="${document.sealTime}" readonly/>
                <input type="hidden"   id="publishTime"    value="${document.publishTime}" readonly/>
                <input type="hidden"   id="readTime"    value="${document.readTime}" readonly/>
                <input type="hidden"   id="guidangTime"    value="${document.guidangTime}" readonly/>
                <ul class="layui-timeline examine-process" style="margin-top:10px">
                </ul>
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    const  basePath = "<%=basePath%>";
    const companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
    const staffStatus = {"00":"兼岗","01":"专岗"};
    const contractStatusObject = {"A":"待签约","R":"已驳回","U":"不批准","F":"已签约","P":"待分发","S":"审核中","T":"审核中","I":"拟稿中","O":"待阅读","K":"待支付","y":"邀请"}
    var curstaffID = "${curstaffID}";
    var pattern = "${param.type}";
    const status = "${cos.status}";
    $("#statusName").val(staffStatus[status]);
    const contractStatus = "${document.status}";
    $("#contractStatus").val(contractStatusObject[contractStatus]);
    $(".content").height($(window).height() - $("header").height());
    $(".div-form").height($(".content").height() - $(".div-bottom").height() - 20);
    var dataJson = JSON.stringify("${document}");
    const docId = "${document.docId}"
    const v1 = "${document.subscriberID2}";
    const v2 = "${document.drafterID}"



</script>
</html>
