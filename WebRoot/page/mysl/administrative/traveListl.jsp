<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2025/5/20
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>外出申请列表</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 6px;
            text-align: center;
        }
        th {
            background: #f2f2f2;
        }
    </style>
</head>
<body>
<h2>外出申请列表</h2>

<table>
    <thead>
    <tr>
        <th>序号</th>
        <th>申请人</th>
        <th>所属公司</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>申请日期</th>
        <th>天数</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="travel" items="${travelList}" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${travel.staffName}</td>
            <td>${travel.companyName}</td>
            <td>${travel.startDate}</td>
            <td>${travel.endDate}</td>
            <td>${travel.applyDate}</td>
            <td>${travel.travelDays}</td>
            <td>
                <c:choose>
                    <c:when test="${travel.status == '1'}">已通过</c:when>
                    <c:when test="${travel.status == '0'}">未审核</c:when>
                    <c:otherwise>未知</c:otherwise>
                </c:choose>
            </td>
            <td>
                <a href="view.jsp?key=${travel.key}">查看</a> |
                <a href="edit.jsp?key=${travel.key}">编辑</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
