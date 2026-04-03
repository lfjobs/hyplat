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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/staffRecord.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>util/jdate/jdate.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/staffRecord.js"></script>
    <title>人员档案</title>

</head>

<body>
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            人员档案
        </li>
    </ul>
</header>
<div class="content" style="overflow-y:auto">
    <div class="div-base-data" style="margin-top:10px">
        <div class="div-staff-base" style="width:180px">
            <label >姓名：</label>
            <input type="text"   id="staffName"  value="${staff.staffName}" />
            <input type="hidden"   id="staffId"  value="${staff.staffID}"/>
        </div>
        <div class="div-staff-base" style="width:100px">
            <label >性别：</label>
            <input type="text"   id="sex"  value="${staff.sex}" style="width:50px " />
        </div>

        <div class="div-staff-base">
            <label >手&nbsp;机&nbsp;号&nbsp;：</label>
            <input type="text" class="div-input"  id="reference"  value="${staff.reference}" />
        </div>
        <div class="div-staff-base">
            <label >身份证号：</label>
            <input type="text"  class="div-input" id="staffIdentityCard"  value="${staff.staffIdentityCard}" />
        </div>
        <div class="div-staff-base div-staffAddress">
            <label style="float:left">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;：</label>
            <label class="label-staffAddress" style="float:left;color:#666">${staff.staffAddress}</label>
        </div>

    </div>
    <div class="div-data">
        <div>
            <div class="div-data-title">招聘规划</div>
            <div class="div-record-data">
                <label class="'div-info">人员规划</label>
                <label class="'div-info">用工条件</label>
                <label class="'div-info">规章制度</label>
            </div>
        </div>
        <div>
            <div class="div-data-title">面试管理</div>
            <div class="div-record-data">
                <label class="'div-info" onclick="selectData('interviewReceipt')"> 面试收件</label>
                <label class="'div-info" onclick="selectData('resume')">个人简历</label>
                <label class="'div-info" onclick="selectData('interviewRegistration')">面试登记</label>
                <label class="'div-info" onclick="selectData('interviewResult')">面试结果</label>
            </div>
        </div>
        <div>
            <div class="div-data-title">入职人员</div>
            <div class="div-record-data">
                <label class="'div-info staffData" onclick="selectData('staffData')">入职信息</label>
                <label class="'div-info">入职岗位</label>
                <label class="'div-info" onclick="selectData('salaryLevel')">升级降级</label>
                <label class="'div-info" onclick="selectData('entryContract')" >入职合同</label>
                <label class="'div-info">试用期考核</label>
                <label class="'div-info">在职规章制度</label>
            </div>
        </div>
        <div>
            <div class="div-data-title">在职管理</div>
            <div class="div-record-data">
                <label class="'div-info" onclick="selectData('projectPlan')">工作项目</label>
                <label class="'div-info" onclick="selectData('signData')">签到考勤</label>
                <label class="'div-info" onclick="selectData('staffSalary')">人员工资</label>
                <label class="'div-info" onclick="selectData('salaryLevelData')">工资级差</label>
            </div>
        </div>
        <div>
            <div class="div-data-title">离职管理</div>
            <div class="div-record-data">
                <label class="'div-info" onclick="selectData('resignationData')">离职人员</label>
                <label class="'div-info">工作交接</label>
                <label class="'div-info">手续办理</label>
                <label class="'div-info">增值服务</label>
                <label class="'div-info">黑名单</label>
            </div>
        </div>
        <div>
            <div class="div-data-title">附件管理</div>
            <div class="div-record-data">
                <label class="'div-info">员工登记表</label>
                <label class="'div-info">离职证明</label>
                <label class="'div-info">证件管理</label>
                <label class="'div-info">身份证</label>
                <label class="'div-info">荣誉证</label>
                <label class="'div-info">工作表现</label>
            </div>
        </div>

    </div>

</div>
</body>
<script type="text/javascript">
    const  basePath = "<%=basePath%>";
    var companyId = "${companyId}";

</script>
</html>
