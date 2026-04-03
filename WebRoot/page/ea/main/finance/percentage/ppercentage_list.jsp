<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>价格管理</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
  <%--  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
  <%--  <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>--%>
    <script type="text/javascript"
            src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <style type="text/css">
        .xx {
            color: #FF0000;
            margin-right: 2px;
        }
    </style>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
       /* var pNumber = '${pageForm.pageNumber}';*/
    </script>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/percentage/PPercentage.js"></script>
</head>
<body>
<div id="main_main" class="main_main">
    <table class="JQueryflexme">
        <thead>
        <tr class="tablewith">
            <th width="30" align="center">
                选择
            </th>
            <th width="80" align="center">
                零售价百分比
            </th>
            <th width="100" align="center">
                活动价百分比
            </th>
            <th width="100" align="center">
                vip价百分比
            </th>
            <th width="100" align="center">
                批发价百分比
            </th>
            <th width="80" align="center">
                特价百分比
            </th>
            <th width="120" align="center">
                设置价格百分比责任人
            </th>
            <th width="120" align="center">
                设置时间
            </th>
            <th width="120" align="center">
                产品名称
            </th>
            <th width="80" align="center">
                类别
            </th>

        </tr>
        </thead>
        <%
            int number = 1;
        %>
        <tbody>
        <s:iterator value="pageForm.list" var="list">
            <tr id="${list[0]}" class="td_bg01 saveAjax" class="trclass">
                <td class="td_bg01">
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${list[0]}"/>
                    <input type="hidden" name="percentageId" id="percentageId"
                           value="${list[0]}"/>
                </td>
                <td class="td_bg01">
                    <span id="retail">${list[1]}%</span>
                </td>
                <td class="td_bg01">
                    <span id="activity">${list[2]}%</span>
                </td>
                <td class="td_bg01">
                    <span id="vip">${list[3]}%</span>
                </td>
                <td class="td_bg01">
                    <span id="wholesale">${list[4]}%</span>
                </td>
                <td class="td_bg01">
                    <span id="special">${list[5]}%</span>
                </td>
                <td class="td_bg01">
                    <span id="principal">${list[6]}</span>
                </td>
                <td class="td_bg01">
                    <span id="times"><fmt:formatDate value="${list[7]}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                </td>
                <td class="td_bg01">
                    <span id="goodsname">${list[8]}</span>
                </td>
                <td class="td_bg01">
                    <span id="codevalue">${list[9]}</span>
                </td>

                <%
                    number++;
                %>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../page_navigator.jsp">
        <c:param name="actionPath" value="/ea/percentage/ea_selectPPercentage.jspa?pageNumber=${pageNumber}">
        </c:param>
    </c:import>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    <iframe src="" name="main" width="99%" scrolling="no" marginwidth="0" height="0" marginheight="0" frameborder="0"
            id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
    </iframe>
</div>


<!--JS遮罩层-->
<div id="fullbg"></div>
</body>
</html>
