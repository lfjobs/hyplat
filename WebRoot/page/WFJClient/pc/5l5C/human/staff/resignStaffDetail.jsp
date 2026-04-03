<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ page import="hy.ea.bo.CAccount" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CAccount ca = (CAccount) session.getAttribute("account");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/resignStaffDeatil.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>util/jdate/jdate.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectPageData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/resignStaffDetail.js"></script>

    <title>人员信息</title>

</head>

<body>
<header class="header">
    <ul class="clearfix">
        <li>
            <a onclick="returnPage()" target="_self" >
            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            离职人员信息
        </li>
        <li></li>
    </ul>
</header>
<div class="content">
    <form name="form" class="div-form" id="form" >
        <div class="div-base">
            <div class="div-base-data">
                <div class="div-name div-baseData" onclick="selectData('staff')">
                    <label ><span style="color:red">*</span>申请人</label>
                    <input type="text"  placeholder="请填写姓名"  name="staff.staffName" id="staffName" value="${staff.staffName}" readonly/>
                    <input type="hidden"   id="staffID" name="staff.staffID"  value="${staff.staffID}" />
                    <input type="hidden"   id="staffKey" name="staff.staffKey"  value="${staff.staffKey}" />
                    <input type="hidden"   id="dimissionStaffID" name="cOSDimissionStaff.dimissionStaffID"  value="${cOSDimissionStaff.dimissionStaffID}" />
                    <input type="hidden"   id="dimissionStaffKey" name="cOSDimissionStaff.dimissionStaffKey"  value="${cOSDimissionStaff.dimissionStaffKey}" />
                    <i class="layui-icon i-icon-right i-select-staff">&#xe602;</i>
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>手机号</label>
                    <input type="text"    name="staff.reference" id="reference"  value="${staff.reference}" readonly />
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>入职日期</label>
                    <input type="text"  placeholder="请选择离职时间入职日期"  name="cos.entryDate" id="entryDate" value="${cos.entryDate}" readonly/>
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>离职时间</label>
                    <input type="text"  placeholder="请选择离职时间"  name="cOSDimissionStaff.dimissionDate" id="dimissionDate" value="${cOSDimissionStaff.dimissionDate}"/>
                </div>
                <div class="div-name div-baseData" onclick="selectData('dimissionStatus')">
                    <label ><span style="color:red">*</span>离职类型</label>
                    <input type="text"  placeholder="请选择离职状态"  name="cOSDimissionStaff.dimissionStatusName" id="dimissionStatusName" value="${cOSDimissionStaff.dimissionStatusName}" readonly/>
                    <input type="hidden"   id="dimissionStatus" name="cOSDimissionStaff.dimissionStatus"  value="${cOSDimissionStaff.dimissionStatus}" />
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>离职原因</label>
                    <input type="text"  placeholder="请填写离职原因"  name="cOSDimissionStaff.dimissionCause" id="dimissionCause" value="${cOSDimissionStaff.dimissionCause}"/>
                </div>
               <div class="div-name div-baseData div-issued" style="display:none">
                    <label ><span style="color:red">*</span>经手人</label>
                    <input type="text"  placeholder="请填写经手人"  name="cOSDimissionStaff.issued" id="issued" value="${cOSDimissionStaff.issued}"/>
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>工作交接人</label>
                    <input type="text"  placeholder="请填写工作交接人"  name="cOSDimissionStaff.handover" id="handover" value="${cOSDimissionStaff.handover}"/>
                </div>
                <div class="div-name div-baseData div-contract " onclick="selectData('contractType')">
                    <label >签订合同</label>
                    <input type="text"  placeholder="请选择签订合同" id="contractTypeName" name="cOSDimissionStaff.contractTypeName"  value="${cOSDimissionStaff.contractTypeName}" readonly/>
                    <input type="hidden"  placeholder="请选择签订合同"  id="contractTypeId" name="cOSDimissionStaff.contractType" value="${cOSDimissionStaff.contractType}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <c:forEach items="${lists}" var="item" varStatus="index">
                    <div class="div-name con-div" >
                        <div class="div-file">
                            <span><a onclick="viewFile('${item[3]}','${item[2]}','${item[4]}','${item[5]}')">${item[0]}</a></span>

                            <span class="div-file-no1">
                        <c:choose>
                            <c:when test="${item[2]=='A'}">
                                待签约
                            </c:when>
                            <c:when test="${item[2]=='R'}">
                                已驳回
                            </c:when>
                            <c:when test="${item[2]=='U'}">
                                不批准
                            </c:when>
                            <c:when test="${item[2]=='F'}">
                                已签约
                            </c:when>
                            <c:when test="${item[2]=='P'}">
                                待分发
                            </c:when>
                            <c:when test="${item[2]=='S'||item[2]=='T'}">
                                审核中
                            </c:when>
                            <c:when test="${item[2]=='I'}">
                                拟稿中
                            </c:when>
                            <c:when test="${item[2]=='O'}">
                                待阅读
                            </c:when>
                            <c:when test="${item[2]=='K'}">
                                待支付
                            </c:when>
                            <c:when test="${item[2]=='y'}">
                                邀请
                            </c:when>
                            <c:otherwise>
                                已阅读
                            </c:otherwise>
                        </c:choose>
                    </span>
                            <span style="float:right;margin-right:30px;color:green"><a onclick="getStaffContractByDocId('${staff.staffID}','${item[3]}','${item[0]}','${item[2]}','${item[4]}','${item[5]}')">审核流程</a></span>
                        </div>
                    </div>
                </c:forEach>
                <div class="div-name div-baseData div-salary" style="display:none" onclick="selectData('salary')">
                    <label style="margin:13px 0;">工资查看</label>
                    <i class="layui-icon i-icon-right ">&#xe602;</i>
                </div>
            </div>
        </div>
        <div class="div-examine-process" style="display:none">
            <div class="div-name div-baseData" onclick="selectData('staff')">
                <label >姓名</label>
                <input type="text"  placeholder="请填写姓名"   value="${staff.staffName}" readonly/>
            </div>
            <div class="div-name">
                <label >性别</label>
                <input type="text"    value="${staff.sex}" readonly />
            </div>
            <div class="div-name" onclick="getContract()">
                <label >合同类型</label>
                <input type="text"  id="contractTypeNameDetail"  readonly />
                <input type="hidden" id="docId" readonly/>
                <input type="hidden" id="contractStatusNum" readonly/>
                <input type="hidden" id="contractStatusStaff" readonly/>
                <input type="hidden" id="contractStatusDraft" readonly/>
                <i class="layui-icon " style=" font-size: 20px;float: right;margin-top: -40px;">&#xe602;</i>
            </div>
            <div class="div-name">
                <label >合同状态</label>
                <input type="text"  id="contractStatus"  readonly   />
            </div>
            <div class="div-name">
                <label >合同状态位置</label>
                <input type="text"  id="contractStatsName"  readonly />
            </div>
            <div class="div-title-name"><label>审核流程</label></div>
            <ul class="layui-timeline examine-process" style="margin-top:10px">
            </ul>
        </div>
    </form>
    <div class="div-bottom"  onclick="saveData()">保存</div>
</div>
</body>
<script type="text/javascript">
    const  basePath = "<%=basePath%>";
    var pattern = "${param.type}";
    var curstaffID = "<%=ca.getStaffID()%>";
    if ("edit" === pattern){
        $(".div-issued").show();
        $(".div-salary").show();
        $(".i-select-staff").hide();
    }
    if ("query" === pattern){
        $(".i-icon-right").hide();
        $(".div-bottom").hide();
    }
</script>
</html>
