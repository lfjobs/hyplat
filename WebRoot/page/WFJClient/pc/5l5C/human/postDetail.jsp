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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/postDetail.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/postDetail.js" type="text/javascript" charset="utf-8"></script>

    <title>岗位添加</title>

</head>

<body>
<header class="header">
    <ul class="clearfix "  >
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li class="div_header">
            岗位添加
        </li>
    </ul>
</header>
<div class="content">
    <form name="form" id="form" method="post" class="div-form">
        <div class="div-name">
            <label ><span style="color:red">*</span>岗位名称</label>
            <input type="text"  placeholder="岗位名称"  name="departmentPost.postName" id="postName" value="${departmentPost.postName }" />
            <input type="hidden" name="departmentPost.depPostKey" value="${departmentPost.depPostKey }" />
            <input type="hidden" name="departmentPost.depPostID" value="${departmentPost.depPostID }" />
            <input type="hidden" name="departmentPost.companyID" value="${departmentPost.companyID }"/>
        </div>
        <div class="div-name">
            <label ><span style="color:red">*</span>职位编号</label>
            <input type="text"  placeholder="岗位编号"  name="departmentPost.postNum" id="postNum" value="${departmentPost.postNum }" readonly/>
        </div>
        <div class="div-name dept">
            <label ><span style="color:red">*</span>所属部门</label>
            <input type="text"  placeholder="所属部门"   id="organizationName" value="${departmentPost.organizationName }" readonly />
            <input type="hidden"  placeholder="所属部门"  name="departmentPost.organizationID" value="${departmentPost.organizationID }" id="organizationID" />
            <input type="hidden"   name="departmentPost.leveloneOrgID" id="leveloneOrgID" value="${departmentPost.leveloneOrgID }"/>
        </div>
        <div class="div-name" >
            <label ><span style="color:red">*</span>辖员人数</label>
            <input type="text"  placeholder="辖员人数"  name="departmentPost.adminNum" id="adminNum" value="${departmentPost.adminNum }" readonly/>
        </div>
        <div class="div-name">
            <label ><span style="color:red">*</span>职位定员</label>
            <input type="text"  placeholder="职位定员"  name="departmentPost.postSureNum" value="${departmentPost.postSureNum }" id="postSureNum" />
        </div>
        <div class="div-name">
            <label ><span style="color:red">*</span>岗位职责</label>
            <textarea  placeholder="请输入岗位职责" class="layui-textarea" id = "postResponsibility" name="departmentPost.postResponsibility"  >${departmentPost.postResponsibility}</textarea>
        </div>
        <div class="div-name">
            <label ><span style="color:red">*</span>任职要求</label>
            <textarea  placeholder="请输入任职要求" class="layui-textarea" id="responsibilityRequire" name="departmentPost.responsibilityRequire"  >${departmentPost.responsibilityRequire}</textarea>
        </div>
        <div class="div-name">
            <label >备注</label>
            <textarea  placeholder="请输入" class="layui-textarea" name="departmentPost.remark" value="${departmentPost.remark}" ></textarea>
        </div>
    </form>
    <div class="div-bottom"  onclick="save()">保存</div>
</div>

</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>"
    const type = "${param.type}";
    var maxNum = "${maxNum}";
    if ("add" === type){
        $("#postNum").val(maxNum);
        $("#adminNum").val(0);
    } else if ("edit" === type){
        $(".div_header").html("岗位修改");
        $("#postName").prop("readOnly",true);
    }

</script>
</html>
