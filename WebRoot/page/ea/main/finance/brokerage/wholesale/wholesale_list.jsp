<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmx" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" conten="text/html; charset=utf-8"/>
    <title>营销产品发布>>产品发布</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <%--<link href="<%=basePath%>/WebRoot/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>--%>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pNumber = "${pageNumber}";
        var search = '${search}';

    </script>
</head>
<body>

<form style="display: none;" name="addressForm" id="addressForm"
      method="post">
    <s:token></s:token>
    <input type="submit" name="submit" style="display: none"/>
</form>

<div id="main_main" class="main_main">
    <table class="address">
        <thead>
        <tr>
            <th width="30" align="center">
                选择
            </th>
            <th width="30" align="center">
                序号
            </th>
            <th width="110" align="center">
                公司名称
            </th>

            <th width="110" align="center">
                产品编号
            </th>
            <th width="100" align="center">
                条码
            </th>
            <th width="190" align="center">
                产品名称
            </th>
            <th width="50" align="center">
                产品单位
            </th>

            <th width="70" align="center">
                成本价
            </th>
            <th width="70" align="center">
                系统批发价
            </th>
            <th width="70" align="center">
                展示批发价
            </th>
            <th width="70" align="center">
                发布状态
            </th>
            <th width="70" align="center">
                佣金状态
            </th>


        </tr>
        </thead>
        <tbody id="productList">
        <% int number = 1; %>
        <c:forEach var='arr' items="${pageForm.list}">
            <tr id="${arr[0]}">
                <td>
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${arr[0]}"/>
                    <input type="hidden" name="ppid" id="ppid"
                           value="${arr[0]}"/>
                    <input type="hidden" class="wholesaleId"
                           value="${arr[11]}"/>
                </td>
                <td>
                    <span id="number"><%=number%></span>
                </td>
                <td>
                    <span id="companyname">${arr[1]}</span>
                </td>
                <td>
                    <span id="productcode">${arr[2]}</span>
                </td>
                <td>
                    <span class="barCode">${arr[3]}</span>
                </td>
                <td>
                    <span class="goodsName">${arr[4]}</span>
                        <%--  <span id="goodsID" style="display: none">${arr[5]}</span>--%>
                </td>
                <td>
                    <span id="variableID">${arr[5]}</span>
                </td>
                <td>
                    <span id="factory">${arr[6]}</span><%--成本价--%>
                </td>
                <td>
                    <p id="price">${arr[7]}</p><%--系统批发价--%>
                </td>
                <td>
                    <span class="showPrice">${arr[8]}</span><%--展示批发价--%>
                </td>
                <td>
                    <span id="showweixin">${arr[9]=="01"?"已发布":"未发布"}</span>
                </td>
                <td>
                    <span id="wholesalestatus">${arr[10]=="01"?"已设计佣金":"未设计佣金"}</span>
                </td>
            </tr>
            <% number++; %>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="../../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/wholesale/ea_selectWholesaleList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
    </c:import>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<script type="text/javascript" src="<%=basePath%>js/ea/finance/brokerage/wholesale/wholesale.js"></script>
</body>
</html>
