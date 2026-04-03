<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/securityManage.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/NfcList.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/employee/employee.js"></script>
    <%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>--%>

    <title>招聘管理</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>";
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var companyID = "${param.companyID}";
        var staffID = "${param.staffId}";
    </script>
    <style>
        .key {
            font-weight: bold;
        }
        .value {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            员工详情
        </li>
        <li>
        </li>
    </ul>
</header>
<div>
    <span class="key">人员编号:</span>
    <span class="value" id="staffId111"></span>
</div>
<div>
    <span class="key">姓&nbsp;&nbsp;名:</span>
    <span class="value" id="staffNam111e"></span>
</div>
<div>
    <span class="key">人员编号:</span>
    <span class="value" id="staffId"></span>
</div>
<div>
    <span class="key">姓&nbsp;&nbsp;名:</span>
    <span class="value" id="staffName"></span>
</div>
<div>
    <span class="key">性&nbsp;&nbsp;别:</span>
    <span class="value" id="sex"></span>
</div>
<div>
    <span class="key">电&nbsp;&nbsp;话:</span>
    <span class="value" id="phone"></span>
</div>
<div>
    <span class="key">身份证号:</span>
    <span class="value" id="idNo"></span>
</div>
<div>
    <span class="key">地&nbsp;&nbsp;址:</span>
    <span class="value" id="address"></span>
</div>
<div>
    <span class="key">部门名称:</span>
    <span class="value" id="deptName"></span>
</div>
<div>
    <span class="key">岗位名称:</span>
    <span class="value" id="postName"></span>
</div>
<div>
    <span class="key">职务名称:</span>
    <span class="value" id="jobName"></span>
</div>
<div>
    <span class="key">级&nbsp;&nbsp;别:</span>
    <span class="value" id="level"></span>
</div>
<div>
    <span class="key">职责管理:</span>
    <span class="value" id="dutyManager"></span>
</div>
<div>
    <span class="key">档案管理:</span>
    <span class="value" id="dataManager"></span>
</div>

<script>
    // 使用JavaScript获取接口数据
    var url = "ea/cosincumbent/sajax_n_ea_getStaffListPc.jspa?";
    alert("111:"+staffID)
    $.ajax({
        url: basePath + url,
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            "afterStaffID": staffID,
            "companyID": companyID
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var arry = m.pageForm;
            document.getElementById('staffId').textContent = arry.list[0][1];
            document.getElementById('staffName').textContent = arry.list[0][3];
            document.getElementById('sex').textContent = arry.list[0][4];
            document.getElementById('phone').textContent = arry.list[0][12];
            document.getElementById('address').textContent = arry.list[0][13];
            document.getElementById('deptName').textContent = arry.list[0][6];
            document.getElementById('postName').textContent = arry.list[0][15];
            document.getElementById('jobName').textContent = arry.list[0][16];
            document.getElementById('level').textContent = arry.list[0][4];
            document.getElementById('dutyManager').textContent = arry.list[0][4];
            document.getElementById('dataManager').textContent = arry.list[0][4];
        }
    })
</script>



</body>
</html>
