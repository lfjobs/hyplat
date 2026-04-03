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

    <title>员工详情</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>";
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var companyID = "${param.companyID}";
        var auditionKey = "${param.auditionKey}";
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
    <span class="key">姓&nbsp;&nbsp;名:</span>
    <span class="value" id="staffName"></span>
</div>
<div>
    <span class="key">身份证号:</span>
    <span class="value" id="staffIdentityCard"></span>
</div>
<div>
    <span class="key">应聘方向:</span>
    <span class="value" id="auditionDirection"></span>
</div>
<div>
    <span class="key">应聘岗位:</span>
    <span class="value" id="auditionPost"></span>
</div>
<div>
    <span class="key">工作经验:</span>
    <span class="value" id="experience"></span>
</div>
<div>
    <span class="key">面试地点:</span>
    <span class="value" id="place"></span>
</div>
<div>
    <span class="key">面试考官:</span>
    <span class="value" id="examiner"></span>
</div>
<div>
    <span class="key">面试时间:</span>
    <span class="value" id="auditionDate"></span>
</div>
<div>
    <span class="key">面试部门:</span>
    <span class="value" id="auditionDept"></span>
</div>
<div>
    <span class="key">面试结果:</span>
    <span class="value" id="status"></span>
</div>

<script>
    // 使用JavaScript获取接口数据
    var url = "ea/cosincumbent/sajax_n_ea_getauditionList.jspa?";
    $.ajax({
        url: basePath + url,
        type: "POST",
        dataType: "json",
        aysnc: false,
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            "companyID": companyID,
            "auditionKey": auditionKey,
            "status": 0
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var arry = m.pageForm;
            document.getElementById('staffName').textContent = arry.list[0].staffName;
            document.getElementById('staffIdentityCard').textContent = arry.list[0].staffIdentityCard;
            document.getElementById('auditionDirection').textContent = arry.list[0].auditionDirection;
            document.getElementById('auditionPost').textContent = arry.list[0].auditionPost;
            document.getElementById('experience').textContent = arry.list[0].experience;
            document.getElementById('place').textContent = arry.list[0].place;
            document.getElementById('examiner').textContent = arry.list[0].examiner;
            document.getElementById('auditionDate').textContent = arry.list[0].auditionDate;
            document.getElementById('auditionDept').textContent = arry.list[0].auditionDept;
            document.getElementById('status').textContent = arry.list[0].status;
        }
    })
</script>



</body>
</html>
