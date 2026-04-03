<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <title>接口管理</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
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
        var pNumber = '${pageForm.pageNumber}';
        var documentsId = '${documentsId}';
        var search = '${search}';
        var allport = '${allport}';
        //var roadID = '';
        // var personurl = "";
        //var notoken = 0;
        //var carID=parent.carID;
        //var token=0;
        //var type='${type}';
    </script>
    <script type="text/javascript" src="<%=basePath%>js/ea/portmanage/portmanage_port.js"></script>
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
                接口名称
            </th>
            <th width="80" align="center">
                接口URL
            </th>
            <th width="200" align="center">
                创建人
            </th>
            <th width="80" align="center">
                创建时间
            </th>
            <th width="80" align="center">
                修改人
            </th>
            <th width="80" align="center">
                修改时间
            </th>

        </tr>
        </thead>
        <tbody>
        <s:iterator value="pageForm.list">
            <tr id="${portId}" class="td_bg01 saveAjax" class="trclass">
                <td class="td_bg01">
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${portId}"/>
                    <input type="hidden" name="documentsKey" id="documentsKey"
                           value="${portKey}"/>
                    <input type="hidden" name="documentsId" id="documentsId"
                           value="${portId}"/>
                </td>
                <td class="td_bg01">
                    <span id="portName">${portName}</span>
                </td>
                <td class="td_bg01">
                    <span id="portUrl">${portUrl}</span>
                </td>
                <td class="td_bg01">
                    <span id="portCreaterName">${portCreaterName}</span>
                </td>
                <td class="td_bg01">
                    <span id="portCreatTime">${fn:substring(portCreatTime, 0, 10)}</span>
                </td>
                <td class="td_bg01">
                    <span id="portModifierName">${portModifierName}</span>
                </td>
                <td class="td_bg01">
                    <span id="portModificationTime">${fn:substring(portModificationTime, 0, 10)}</span>
                </td>
            </tr>
        </s:iterator>
        </tbody>
        <h5>${list}</h5>
    </table>
    <c:import url="../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/ccode1/ea_pm_selectPorts.jspa?search=${search}&allport=${allport}&documentsId=${documentsId}&pageNumber=${pageNumber}">
        </c:param>
    </c:import>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    <iframe src="" name="main" width="99%" scrolling="no" marginwidth="0" height="0" marginheight="0" frameborder="0"
            id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
    </iframe>
</div>

<!-- 查询信息 -->
<form name="portSearchForm" id="portSearchForm" method="post">
    <div class="jqmWindow" style="width: 300px; right: 35%; top: 15%"
         id="jqModelPortSearch">
        <div class="drag">
            查询接口信息
            <div class="close">
            </div>
        </div>
        <table id="carSearchTable" width="120%" cellpadding="5"
               cellspacing="5">

            <tr>

                <td>
                    按接口名称查询: <input name="search" type="text" placeholder="请输入您要查询的接口名称" value="${search}"/>
                </td>
            </tr>
            <tr>
                <td>
                    查询所有文档的接口:<input name="allport" type="checkbox" value="all" style="zoom:150%;margin-left:5px"
                                     <c:if test="${allport=='all'}">checked</c:if>/>
                </td>
            </tr>

        </table>
        <div align="center">
            <input type="button" class="input-button" id="searchPort"
                   value="确认"/>
        </div>
    </div>
</form>
<!--JS遮罩层-->
<div id="fullbg"></div>
</body>
</html>
