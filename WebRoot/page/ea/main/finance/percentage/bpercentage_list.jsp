<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
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
    <title>佣金管理</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <%--<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <%--<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
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
     /*   var pNumber = '${pageForm.pageNumber}';*/
    </script>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/percentage/BPercentage.js"></script>
</head>
<body>
<div id="main_main" class="main_main">
    <table class="JQueryflexme">
        <thead>
        <tr class="tablewith">
            <th width="30" align="center">
                选择
            </th>
            <th width="100" align="center">
                贴牌佣金百分比
            </th>
            <th width="130" align="center">
                设备安装佣金百分比
            </th>
            <th width="130" align="center">
                设备投资佣金百分比
            </th>
            <th width="130" align="center">
                省级代理佣金百分比
            </th>
            <th width="130" align="center">
                县级代理佣金百分比
            </th>
            <th width="130" align="center">
                村级代理佣金百分比
            </th>
            <th width="130" align="center">
                客户积分佣金百分比
            </th>
            <th width="110" align="center">
                业务佣金百分比
            </th>
            <th width="130" align="center">
                设置佣金百分比责任人
            </th>
            <th width="120" align="center">
                设置时间
            </th>

        </tr>
        </thead>
        <%
            int number = 1;
        %>
        <tbody>
        <s:iterator value="pageForm.list">
            <tr id="${brokerageId}" class="td_bg01 saveAjax" class="trclass">
                <td class="td_bg01">
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${brokerageId}"/>
                    <input type="hidden" name="brokerageId" id="brokerageId"
                           value="${brokerageId}"/>
                </td>
                <td class="td_bg01">
                    <span id="tp">${tp}%</span>
                </td>
                <td class="td_bg01">
                    <span id="sbaz">${sbaz}%</span>
                </td>
                <td class="td_bg01">
                    <span id="sbtz">${sbtz}%</span>
                </td>
                <td class="td_bg01">
                    <span id="sjdl">${sjdl}%</span>
                </td>
                <td class="td_bg01">
                    <span id="xjdl">${xjdl}%</span>
                </td>
                <td class="td_bg01">
                    <span id="cjdl">${cjdl}%</span>
                </td>
                <td class="td_bg01">
                    <span id="khjf">${khjf}%</span>
                </td>
                <td class="td_bg01">
                    <span id="ywyj">${ywyj}%</span>
                </td>
                <td class="td_bg01">
                    <span id="principal">${principal}</span>
                </td>
                <td class="td_bg01">
                    <span id="times"><fmt:formatDate value="${times}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                </td>

                <%
                    number++;
                %>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../page_navigator.jsp">
        <c:param name="actionPath" value="/ea/percentage/ea_selectBPercentage.jspa?pageNumber=${pageNumber}">
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
