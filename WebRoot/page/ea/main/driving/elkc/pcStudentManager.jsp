<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>约车管理</title>


    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
          type="text/css" />
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=basePath%>js/ea/driving/elkc/pcStudentManager.js"
            type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />




    <script type="text/javascript">

        var basePath="<%=basePath%>";
        var  ppageNumber = "${pageForm.pageNumber}";
        var etoId = "";

    </script>
</head>
<body>
<div style="margin-top:10px;margin-left:10px;display:none;"
     class="query">
    约车管理<form id="SearchForm" name="SearchForm" method="post">
    <input type="submit" name="submit" style="display:none;"/>学员姓名:
    <input type="text" class="studentName" id="studentName" name="tbElycOrderRecord.studentName" value="${tbElycOrderRecord.studentName}" placeholder="请输入学员姓名">
    教练姓名:
    <input type="text" class="teacherName" id="teacherName" name="tbElycOrderRecord.teacherName" value="${tbElycOrderRecord.teacherName}" placeholder="请输入教练姓名">
    开始时间：
    <input type="text" class="required text" id="startTime" name="StartTime" value="${StartTime}" onfocus="date(this);" placeholder="请选择开始时间">
    结束时间：
    <input type="text" class="required text" id="endTime" name="EndTime" value="${EndTime}" onfocus="date(this);" placeholder="请选择结束时间">
    状态：
    <select name="tbElycOrderRecord.status" id="status">
        <option value="">全部</option>
        <option value="1">正常已约</option>
        <option value="2">已取消</option>
        <option value="3">已签到</option>
        <option value="4">已签退</option>
        <option value="5">已评价</option>
        <option value="6">超时关闭</option>
    </select>

    <input
            type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
</form>
</div>

<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
    <form id="SearchForm" name="SearchForm" method="post">
        <input type="submit" name="submit" style="display:none;"/>约车管理 &nbsp;&nbsp;&nbsp;
    </form>

</div>
<div class="main_main">
    <table class="JQueryflexme">
        <thead>
        <tr class="tablewith">
            <th width="40" align="center">选择</th>
            <th width="40" align="center">序号</th>
            <th width="50" align="center">学员姓名</th>
            <th width="120" align="center">学员电话</th>
            <th width="150" align="center">学员身份证号</th>
            <th width="50" align="center">教练姓名</th>
            <th width="150" align="center">教练身份证号</th>
            <th width="120" align="center">教练电话</th>
            <th width="150" align="center">课程开始时间</th>
            <th width="150" align="center">课程结束时间</th>
            <th width="40" align="center">课程时长</th>
            <th width="40" align="center">状态</th>
            <th width="150" align="center">取消时间</th>
            <th width="150" align="center">实际签到时间</th>
            <th width="150" align="center">实际签退时间</th>
            <th width="100" align="center">预约来源</th>
            <th width="100" align="center">预约科目</th>
            <th width="150" align="center">预约时间</th>
            <th width="50" align="center">替班教练</th>
        </tr>
        </thead>
        <tbody>
        <% int number = 1; %>
        <s:iterator value="pageForm.list" var="f">
            <tr id="${f.etoId}">
                <td><input type="radio" name="a" class="JQuerypersonvalue" value="${f.etoId}" /></td>
                <td><%=number%></td>
                <td><span id="sname">${f.studentName}</span></td>
                <td><span id="studentPhone">${f.studentPhone}</span></td>
                <td><span id="studentNum">${f.studentNum}</span></td>
                <td><span id="tname">${f.teacherName}</span></td>
                <td><span id="teacherNum">${f.teacherNum}</span></td>
                <td><span id="teacherPhone">${f.teacherPhone}</span></td>
                <td><span id="lessionStartTime">${fn:substring(f.lessionStartTime, 0, 19)}</span></td>
                <td><span id="lessionEndTime">${fn:substring(f.lessionEndTime, 0, 19)}</span></td>
                <td><span id="hours">${f.hours}</span></td>
                <c:choose>
                    <c:when test="${f.status eq '1'}">
                        <td><span>已约</span></td>
                    </c:when>
                    <c:when test="${f.status eq '2'}">
                        <td><span>已取消</span></td>
                    </c:when>
                    <c:when test="${f.status eq '3'}">
                        <td><span>已签到</span></td>
                    </c:when>
                    <c:when test="${f.status eq '4'}">
                        <td><span>已签退</span></td>
                    </c:when>
                    <c:when test="${f.status eq '5'}">
                        <td><span>已评价</span></td>
                    </c:when>
                    <c:when test="${f.status eq '6'}">
                        <td><span>超时关闭</span></td>
                    </c:when>
                </c:choose>
                <td><span id="cancelTime">${fn:substring(f.cancelTime, 0, 19)}</span></td>
                <td><span id="actualCheckInTime">${fn:substring(f.actualCheckInTime, 0, 19)}</span></td>
                <td><span id="actualCheckOutTime">${fn:substring(f.actualCheckOutTime, 0, 19)}</span></td>
                <c:choose>
                    <c:when test="${f.source eq '0'}">
                        <td><span>微分金app</span></td>
                    </c:when>
                    <c:when test="${f.source eq '1'}">
                        <td><span>e路快车app</span></td>
                    </c:when>
                    <c:when test="${f.source eq '2'}">
                        <td><span>微信</span></td>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${f.subject eq '1'}">
                        <td><span>科目一</span></td>
                    </c:when>
                    <c:when test="${f.subject eq '2'}">
                        <td><span>科目二</span></td>
                    </c:when>
                    <c:when test="${f.subject eq '3'}">
                        <td><span>科目三</span></td>
                    </c:when>
                    <c:when test="${f.subject eq '4'}">
                        <td><span>科目四</span></td>
                    </c:when>
                </c:choose>
                <td><span id="orderTime">${fn:substring(f.orderTime, 0, 19)}</span></td>
                <td><span id="replaceTeacherName">${f.replaceTeacherName}</span></td>
            </tr>
            <% number++; %>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="/ea/coachreserv/ea_pcStudentManager.jspa?pageNumber=${pageNumber}&tbElycOrderRecord.studentName=${tbElycOrderRecord.studentName}&tbElycOrderRecord.teacherName=${tbElycOrderRecord.teacherName}&StartTime=${StartTime}&EndTime=${EndTime}&tbElycOrderRecord.status=${tbElycOrderRecord.status}">
        </c:param>
    </c:import>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
        framespacing="0" height="0"></iframe>


</body>
</html>