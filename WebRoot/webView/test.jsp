<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
<h1>Session 所有数据展示</h1>
<table border="1">
    <tr>
        <th>属性名</th>
        <th>属性值</th>
        <th>操作</th>
    </tr>
    <%
        // 获取 Session 对象
        HttpSession s = request.getSession(false);
        if (s != null) {
            // 获取 Session 中所有属性名的枚举
            java.util.Enumeration<String> attributeNames = s.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = s.getAttribute(attributeName);
    %>
    <tr>
        <td><%= attributeName %></td>
        <td><%= attributeValue %></td>
        <td>
            <form action="clearSessionData" method="post">
                <input type="hidden" name="keyToClear" value="<%= attributeName %>">
                <input type="submit" value="清除">
            </form>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="2">Session 不存在，<a href="login.jsp">请重新登录</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>