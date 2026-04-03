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
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
    CAccount ca = (CAccount) session.getAttribute("account");
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
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/selectPageData.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/staffData.js"></script>

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
            人员信息
        </li>
        <li><label id="edit">修改</label></li>


    </ul>
</header>
<div class="content">
    <form name="form" class="div-form" id="form" >
        <div class="div-base">
            <div class="div-base-data">
                <div class="div-title-name"><label>基本信息</label></div>
                <div class="div-name div-baseData" onclick="selectData('staff')">
                    <label ><span style="color:red">*</span>姓名</label>
                    <input type="text"  placeholder="请填写姓名"  name="staff.staffName" id="staffName" value="${staff.staffName}" readonly/>
                    <input type="hidden"   id="staffID" name="staff.staffID"  value="${staff.staffID}" />
                    <input type="hidden"   id="staffKey" name="staff.staffKey"  value="${staff.staffKey}" />
                    <input type="hidden"   id="cosId" name="cos.cosID"  value="${cos.cosID}" readonly/>
                    <input type="hidden"   id="cosKey" name="cos.cosKey"  value="${cos.cosKey}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <%--<div class="div-name " onclick="selectData('dept')">
                    <label ><span style="color:red">*</span>部门</label>
                    <input type="text"  placeholder="请选择部门"  id="deptName" name="cos.organizationName"  value="${cos.organizationName}" readonly/>
                    <input type="hidden"  placeholder="请选择部门"  id="deptId" name="cos.organizationID"  value="${cos.organizationID}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>--%>
                <div class="div-name " onclick="selectData('post')">
                    <label ><span style="color:red">*</span>岗位</label>
                    <input type="text"  placeholder="请选择岗位" id="postName"  value="${cos.depPostName}" readonly/>
                    <input type="hidden"  placeholder="请选择岗位" id="depPostID"  name="cos.depPostID"  value="${cos.depPostID}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <div class="div-name " onclick="selectData('role')">
                    <label ><span style="color:red">*</span>角色</label>
                    <input type="text"  placeholder="请选择角色" id="roleName"  name="cos.roleName"  value="${cos.roleName}" readonly/>
                    <input type="hidden"  placeholder="请选择角色" id="roleID"  name="cos.roleId"  value="${cos.roleId}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <div class="div-name div-baseData" onclick="selectData('level')">
                    <label ><span style="color:red">*</span>级别</label>
                    <input type="text"  placeholder="请选择级别"  name="salaryLevelSerial" id="salaryLevelSerial" value="${cos.salaryLevelName}" readonly/>
                    <input type="hidden"  placeholder="请选择级别" id="salaryLevelId"  name="cos.salaryLevelId"  value="${cos.salaryLevelId}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <div class="div-name">
                    <label ><span style="color:red">*</span>手机号</label>
                    <input type="text" class="div-name-edit" placeholder="请填写手机号"  name="staff.reference" id="reference" value="${staff.reference}"/>
                </div>
                <div class="div-name div-baseData" >
                    <label ><span style="color:red">*</span>入职时间</label>
                    <input type="text"  placeholder="请选择入职时间"  name="cos.entryDate" id="entryDate" value="${cos.entryDate}" readonly/>
                </div>
               <%-- <div class="div-name div-resign" style="disaplay:none">
                    <label >离职时间</label>
                    <input type="text"  placeholder="请选择离职时间"   id="dimissionDate" value="${cos.entryDate}"/>
                </div>--%>
                <div class="div-name">
                    <label ><span style="color:red">*</span>身份证号</label>
                    <input type="text"  class="div-name-edit" placeholder="请选择身份证号" id="staffIdentityCard" name="staff.staffIdentityCard"   value="${staff.staffIdentityCard}" />
                </div>

                <div class="div-name">
                    <label >出生日期</label>
                    <input type="text"  placeholder="根据身份证号自动填写"  name="staff.birthday" id="birthday" value="${staff.birthday}" readonly/>
                </div>
                <div class="div-name">
                    <label >性别</label>
                    <input type="text"  placeholder="根据身份证号自动填写"  name="staff.sex" id="sex" value="${staff.sex}" readonly />
                </div>
                <div class="div-name " onclick="selectData('education')">
                    <label >学历</label>
                    <input type="text"  placeholder="请选择学历" id="culturalDegree"  name="staff.culturalDegree"  value="${staff.culturalDegree}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>
                <div class="div-name">
                    <label >地址</label>
                    <input type="text"  placeholder="请填写地址" id="staffAddress" name="staff.staffAddress"  value="${staff.staffAddress}" />
                </div>
            </div>

            <%--工作信息--%>
            <div class="div-work-data">
                <div class="div-title-name"><label>工作信息</label></div>
                <div class="div-name " onclick="selectData('status')">
                    <label ><span style="color:red">*</span>员工类型</label>
                    <input type="text"  placeholder="请选择员工类型"  id="statusName"  readonly/>
                    <input type="hidden"  placeholder="请选择员工类型"  id="statusId" name="cos.status"  value="${cos.status}" readonly/>
                    <i class="layui-icon i-icon-right">&#xe602;</i>
                </div>

                <div class="div-name div-contract " onclick="selectData('contractType')">
                    <label ><span style="color:red" class="span-red">*</span>签订合同</label>
                    <input type="text"  placeholder="请选择签订合同" id="contractTypeName"  value="${cos.contractTypeName}" readonly/>
                    <input type="hidden"  placeholder="请选择签订合同"  id="contractTypeId" name="cos.contractType" value="${cos.contractType}" readonly/>
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
                <div class="div-name">
                    <label >工作年限</label>
                    <input type="text"  placeholder="请输入工作年限" id="workYear" name="staff.workYear"   value="${staff.workYear}" />
                </div>
            </div>

            <%--个人材料--%>
            <div class="div-material-data file">
                <div class="div-title-name"><label>附件管理</label></div>
                <div class="div-name div-identityCard" >
                    <div class="div-file " onclick="clickFile('identityCard')">
                        <span>身份证</span>
                        <input type="file" name="file" id="identityCard" class="file-upload" accept="image/*" multiple>
                        <span class="div-file-no" style="display:none">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-resignProve">
                    <div class="div-file" onclick="clickFile('resignProve')">
                        <span>离职证明</span>
                        <input type="file" name="file"  id="resignProve" class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-originalSalary">
                    <div class="div-file " onclick="clickFile('originalSalary')">
                        <span>原单位工资</span>
                        <input type="file" name="file" id="originalSalary" class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-education">
                    <div class="div-file" onclick="clickFile('education')">
                        <span>学历证</span>
                        <input type="file" name="file" id="education"  class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-degree">
                    <div class="div-file" onclick="clickFile('degree')">
                        <span>学位证</span>
                        <input type="file" name="file" id="degree"  class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-note">
                    <div class="div-file" onclick="clickFile('note')">
                        <span>个人简历</span>
                        <input type="file" name="file" id="note"  class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-health">
                    <div class="div-file" onclick="clickFile('health')">
                        <span>入职体检</span>
                        <input type="file" name="file" id="health" class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
                </div>
                <div class="div-name div-honor">
                    <div class="div-file" onclick="clickFile('honor')">
                        <span>荣誉证</span>
                        <input type="file" name="file" id="honor" class="file-upload" accept="image/*">
                        <span class="div-file-no">未上传</span>
                        <i class="layui-icon div-icon-upload" >&#xe60d;</i>
                    </div>
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
    const companyID = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";
    const staffStatus = {"00":"兼岗","01":"专岗"};
    var curstaffID = "<%=ca.getStaffID()%>";
    var readonlyBool = false;
    var pattern = "${param.type}";
    if ("add" != pattern){
        const status = "${cos.status}";
        $("#statusName").val(staffStatus[status]);
    }
    if ("01" !== $("#statusId").val()){
        $(".div-contract .span-red").hide();
    }
    if (pattern === "readonly"){
        pattern = "query";
        readonlyBool = true;
    }


</script>
</html>
