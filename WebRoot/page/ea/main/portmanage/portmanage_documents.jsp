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
    <title>文档管理</title>
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
        var projectId='${projectId}';
        var  search='${search}';
        //var roadID = '';
        // var personurl = "";
        //var notoken = 0;
        //var carID=parent.carID;
        //var token=0;
        //var type='${type}';
    </script>
    <script type="text/javascript" src="<%=basePath%>js/ea/portmanage/portmanage_documents.js"></script>
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
                文档名称
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
        <%
            int number = 1;
        %>
        <tbody>
        <s:iterator value="pageForm.list">
            <tr id="${documentsId}" class="td_bg01 saveAjax" class="trclass">
                <td class="td_bg01">
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${documentsId}"/>
                    <input type="hidden" name="documentsKey" id="documentsKey"
                           value="${documentsKey}"/>
                    <input type="hidden" name="documentsId" id="documentsId"
                           value="${documentsId}"/>
                </td>
                <td class="td_bg01">
                    <span id="documentsName">${documentsName}</span>
                </td>
                <td class="td_bg01">
                    <span id="documentsCreaterName">${documentsCreaterName}</span>
                </td>
                <td class="td_bg01">
                    <span id="documentsCreatTime">${fn:substring(documentsCreatTime, 0, 10)}</span>
                </td>
                <td class="td_bg01">
                    <span id="documentsModifierName">${documentsModifierName}</span>
                </td>
                <td class="td_bg01">
                    <span id="documentsModificationTime">${fn:substring(documentsModificationTime, 0, 10)}</span>
                        <%--	<span id="projectId" style="display:none">${projectId}</span>
                             <span id="projectKey"  style="display:none">${projectKey}</span>--%>
                </td>
                <%
                    number++;
                %>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../page_navigator.jsp">
        <c:param name="actionPath" value="ea/ccode1/ea_pm_selectAPIDocuments.jspa?search=${search}&projectId=${projectId}&pageNumber=${pageNumber}">
        </c:param>
    </c:import>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    <iframe src="" name="main" width="99%" scrolling="no" marginwidth="0" height="0" marginheight="0" frameborder="0"
            id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
    </iframe>
</div>

<!--api文档添加页面 -->
<div class="contentbannb jqmWindow jqmWindowcss1"
     style="width: 480px; left: 65%; top: 5%;" id="jqModelAdd">
    <form name="addForm" id="addForm" method="post"
          enctype="multipart/form-data">
        <input type="submit" name="submit" style="display: none"/>
        <div class="content">
            <div class="contentbannb">
                <div class="drag">
                    添加
                    <div class="close"></div>
                </div>
            </div>
            <table width="100%" border="0" id="stafftable" cellpadding="5"
                cellspacing="5">

                <tr>
                    <td style="width:15%;" align="right">
                        文档名称:
                    </td>
                    <td style="width:33%;">
                        <input name="apiDocuments.documentsName"  size="20"
                            class="baseName isremove put3" placeholder="文档名称" id="documentsName2"/>
                        <input type="hidden" name="apiDocuments.projectId" value="${projectId}"/>
                    </td>
                </tr>


                <tr>
                    <td colspan="4" align="center">

                        <input type="button" class="input-button JQuerySubmitAdd"
                            style="cursor: pointer; width: 80px;" value="提交" />
                        <input name="sub" value="${session_value}" type="hidden" />
                        <!-- 代替token-->
                        <input type="button" class="input-button JQueryreturn"
                            style="cursor: pointer; width: 80px;" value="取消" />
                    </td>
                </tr>
            </table>

        </div>
        <s:token></s:token>
    </form>
</div>

<!--api文档修改页面 -->
<div class="contentbannb jqmWindow jqmWindowcss1"
     style="width: 680px; left: 65%; top: 5%" id="jqModelUpdate">
    <form name="updateForm" id="updateForm" method="post"
          enctype="multipart/form-data">
        <input type="submit" name="submit" style="display: none"/>
        <div class="content">
            <div class="contentbannb">
                <div class="drag">
                    修改
                    <div class="close"></div>
                </div>
            </div>
            <table width="100%" border="0" id="stafftable1" cellpadding="5"
                   cellspacing="5">

                <tr>
                    <td style="width:15%;" align="right">
                        文档名称:
                    </td>
                    <td style="width:33%;">
                        <input type="hidden" name="apiDocuments.documentsId" id="documentsId1"/>
                        <input name="apiDocuments.documentsName"  size="20"
                               class="baseName isremove put3" placeholder="文档名称" id="documentsName1"/>
                      <%--<input type="text" name="apiDocuments.projectId" value="${projectId}"/>--%>
                    </td>
                </tr>

                <tr>
                    <td colspan="4" align="center">

                        <input type="button" class="input-button JQuerySubmitUpdate"
                               style="cursor: pointer; width: 80px;" value="提交" />
                        <input name="sub" value="${session_value}" type="hidden" />
                        <!-- 代替token-->
                        <input type="button" class="input-button JQueryreturns"
                               style="cursor: pointer; width: 80px;" value="取消" />
                    </td>
                </tr>
            </table>
        </div>
        <s:token></s:token>
    </form>
</div>

<!-- 查询信息 -->
<form name="documentsSearchForm" id="documentsSearchForm" method="post">
    <div class="jqmWindow" style="width: 300px; right: 35%; top: 15%"
         id="jqModelDocumentsSearch">
        <div class="drag">
            查询文档信息
            <div class="close">
            </div>
        </div>
        <table id="carSearchTable" width="100%" cellpadding="5"
               cellspacing="5">

            <tr>
                <td align="right">
                    查询:
                </td>
                <td>
                    <input name="search" placeholder="请输入您要查询的文档名称" value="${search}"/>
                </td>
            </tr>



        </table>
        <div align="center">
            <input type="button" class="input-button" id="searchDocuments"
                   value=" 查询 "/>
        </div>
    </div>
</form>
<!--JS遮罩层-->
<div id="fullbg"></div>
</body>
</html>
