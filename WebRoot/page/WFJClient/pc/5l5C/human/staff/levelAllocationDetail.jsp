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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/levelAllocationDetail.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>util/jdate/jdate.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectPageData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectTab.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectApprover.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/levelAllocationDetail.js"></script>
    <title>人员信息</title>

</head>

<body>
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >
            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li class="header-title">
            级别分配
        </li>
        <li></li>
    </ul>
</header>
<div class="content">
    <form name="form" class="div-form layui-form" id="form">
        <%--审核信息--%>
        <div class="div-examine-data" style="display:none">
            <div class="div-title-name"><label>审核信息</label></div>
            <div class="div-name " >
                <label style="width:120px"><span style="color:red">*</span>审核状态</label>
                <div class="input-block" style="margin-top:-6px">
                    <input type="radio" name="examineProcessData.examineStatus" value="1" title="通过" checked >
                    <input type="radio" name="examineProcessData.examineStatus" value="0" title="驳回" >
                </div>
            </div>
            <div class="div-name div-examine-name" >
                <label ><span style="color:red">*</span>审核人</label>
                <input type="text"    name="examineProcessData.examineStaffName"  value="${examineProcessData.examineStaffName}"  readonly/>
            </div>

            <div class="div-name">
                <label >审核意见</label>
                <textarea name="desc" placeholder="请输入内容" name="examineProcessData.examineRemark" value="examineProcessData.examineRemark" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="div-base-data layui-collapse">
            <div class="div-title-name"><label>级别分配信息</label></div>
            <div class="div-name" style="dislay:none">
                <label >信息</label>
                <input type="hidden"   name="staffLevelAllocation.levelAllocationKey" id="levelAllocationKey" value="${staffLevelAllocation.levelAllocationKey}"/>
                <input type="hidden"   name="staffLevelAllocation.levelAllocationId"  id="levelAllocationId"  value="${staffLevelAllocation.levelAllocationId}" />
                <input type="hidden"   name="staffLevelAllocation.examineProcessId"  id="examineProcessId"  value="${staffLevelAllocation.examineProcessId}" />
                <i class="layui-icon i-icon-right">&#xe602;</i>
            </div>
            <div class="div-name" onclick="selectData('person')">
                <label ><span style="color:red">*</span>姓名</label>
                <input type="text"  placeholder="请选择人员"  name="staffLevelAllocation.staffName" id="staffName" value="${staffLevelAllocation.staffName}" readonly/>
                <input type="hidden"   name="staffLevelAllocation.staffId"  id="staffID"  value="${staffLevelAllocation.staffId}" />

                <i class="layui-icon i-icon-right">&#xe602;</i>
            </div>

            <div class="div-name " >
                <label ><span style="color:red">*</span>原级别</label>
                <input type="text"   placeholder="根据选择的员工自动填写" name="salaryDataOld.salaryLevelSerial" id="salaryLevelSerialOld" value="${salaryDataOld.salaryLevelSerial}"  readonly/>
                <input type="hidden"   id="salaryLevelIdOld" name="staffLevelAllocation.salaryLevelIdOld" value="${staffLevelAllocation.salaryLevelIdOld}" />
            </div>
            <div class="div-name " >
                <label style="color:#666">原级别编码</label>
                <input type="text"   placeholder="根据选择的员工自动填写"  id="salaryLevelNumOld" value="${salaryDataOld.salaryLevelNum}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">原级别基本保障</label>
                <input type="text"   placeholder="根据选择的员工自动填写" id="guaranteeSalaryOld" value="${salaryDataOld.guaranteeSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">原级别福利保障</label>
                <input type="text"   placeholder="根据选择的员工自动填写" id="welfareSalaryOld" value="${salaryDataOld.welfareSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">原级别加班(4天)</label>
                <input type="text"   placeholder="根据选择的员工自动填写" id="workSalaryOld" value="${salaryDataOld.overtimeSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">原级别工资合计</label>
                <input type="text"   placeholder="根据选择的员工自动填写" id="salaryOld" value="${salaryDataOld.summarySalary}" readonly/>
            </div>
            <div class="div-name " onclick="selectData('level')" >
                <label ><span style="color:red">*</span>现级别</label>
                <input type="text"    name="salaryDataNow.salaryLevelSerial" id="salaryLevelSerial" placeholder="请选择" value="${salaryDataNow.salaryLevelSerial}" readonly/>
                <input type="hidden"   id="salaryLevelId" name="staffLevelAllocation.salaryLevelId" value="${staffLevelAllocation.salaryLevelId}" />
                <i class="layui-icon i-icon-right">&#xe602;</i>
            </div>
            <div class="div-name " >
                <label style="color:#666">现级别编码</label>
                <input type="text"   placeholder="根据选择的现级别自动填写"  id="salaryLevelNum" value="${salaryDataNow.salaryLevelNum}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">现级别基本保障</label>
                <input type="text"   placeholder="根据选择的现级别自动填写" id="guaranteeSalary" value="${salaryDataNow.guaranteeSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">现级别福利保障</label>
                <input type="text"   placeholder="根据选择的现级别自动填写" id="welfareSalary" value="${salaryDataNow.welfareSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">现级别加班(4天)</label>
                <input type="text"   placeholder="根据选择的员工自动填写" id="workSalaryNow" value="${salaryDataNow.overtimeSalary}" readonly/>
            </div>
            <div class="div-name " >
                <label style="color:#666">现级别工资合计</label>
                <input type="text"   placeholder="根据选择的现级别自动填写" id="salary" value="${salaryDataNow.summarySalary}" readonly/>
            </div>
            <div class="div-name">
                <label >描述</label>
                <textarea  placeholder="请输入内容" class="layui-textarea" name="staffLevelAllocation.remark" value="${staffLevelAllocation.remark}" ></textarea>
            </div>
            <div class="div-allocation-data">
                <div class="div-name " >
                    <label ><span style="color:red">*</span>分配人</label>
                    <input type="text"   name="staffLevelAllocation.createStaffName" id="createStaffName" value="${staffLevelAllocation.createStaffName}"/>
                    <input type="hidden"   name="staffLevelAllocation.createStaffId" id="createStaffId" value="${staffLevelAllocation.createStaffId}"/>
                </div>
                <div class="div-name "  >
                    <label ><span style="color:red">*</span>分配时间</label>
                    <input type="text"   name="staffLevelAllocation.createDate" id="createDate" value="${staffLevelAllocation.createDate}"/>
                </div>
            </div>
        </div>
        <div class="div-examine-process" style="display:none">
            <div class="div-title-name"><label>审核流程</label></div>
            <ul class="layui-timeline examine-process" style="margin-top:10px">
            </ul>
        </div>
    </form>
    <div class="div-bottom"  onclick="save()">保存</div>
</div>
</body>
<script type="text/javascript">
    const  basePath = "<%=basePath%>";
    const companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
    const pattern = "${param.type}";
    const status = "${param.status}" == "" ? "0" : "${param.status}";

    if ("query" == pattern){
        //$("input").attr("disabled","true");
        $(".i-icon-right").hide();
        $(".div-bottom").height(0);
        $(".div-bottom").hide();
        $(".div-file .div-file-no").show();
        $(".div-file .div-icon-upload").hide();
    } else {
        $(".div-file .div-file-no").hide();
        $(".div-file .div-icon-upload").show();
    }

    if ("add" == pattern || "edit" == pattern) {
        $(".div-examine-data").hide();
    }
    if ("add" == pattern){
        $(".div-allocation-data").hide();
    }
    if ("examine" == pattern){
        $(".div-examine-data").show();
        $(".div-examine-name").hide();
        $(".header-title").html("级别审核");
    }

</script>
</html>
