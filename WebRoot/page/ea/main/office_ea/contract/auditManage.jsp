<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>个人商户</title>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

    <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

    <script src="<%=basePath%>js/ea/office_ea/contract/auditManage.js" type="text/javascript" charset="utf-8"></script>

    <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script type="text/javascript">
        var enterpriseStampID = "";
        var search="${search}";
        var basePath="<%=basePath%>";
        var auditStatus = "${enterpriseStamp.auditStatus}";
        var pNumber = ${pageForm==null?0:pageForm.pageNumber};

    </script>

</head>
<body>
<form  name="searchForm" id="searchForm">
    <input type="submit" name="submit" style="display:none"/>
    <input  name="enterpriseStamp.stampName" class="stampName" style="display:none"/>
    <input  name="enterpriseStamp.auditStatus" value="${enterpriseStamp.auditStatus}" style="display:none"/>
    <input type="hidden" name="search" value="search" />

    <s:token/>
</form>

<table class="flexme11">
    <thead>
    <tr>
        <th width="30" align="center">
            选择
        </th>
        <th width="100" align="center">
            印章名称
        </th>
        <th width="90" align="center">
          责任人
        </th>
        <th width="200" align="center">
            提交日期
        </th>
        <th width="100" align="center">
           审核日期
        </th>
        <th width="150" align="center">
           审核状态
        </th>
        <th width="150" align="center">
            驳回理由
        </th>
        <th width="150" align="center">
           电子印章
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${pageForm.list}" var="item" >

        <tr id="${item.enterpriseStampID}">
            <td>
                <input type="radio" name="a" class="JQuerypersonvalue"
                       value="${item.enterpriseStampID}" />
            </td>
            <td>

                <span id="stampName">${item.stampName}</span>
            </td>
            <td>
                <span id="responsibleName">${item.responsibleName}</span>

            </td>

            <td>
                <span id="createTime">${fn:substring(item.createTime,0,19)}</span>

            </td>
            <td>
                <span id="auditDate">${fn:substring(item.auditDate,0,19)}</span>
            </td>
            <td>
                <c:choose>
                <c:when test="${item.auditStatus=='01'}">
                    审核中
                </c:when>
                <c:when test="${item.auditStatus=='02'}">
                    审核通过
                </c:when>
                <c:when test="${item.auditStatus=='03'}">
                    驳回
                </c:when>
                </c:choose>
            </td>
            <td>
                <span id="rejectReason">${item.rejectReason}</span>

            </td>
            <td>
                <span id="scanningAccessories"  onclick="lookImage('${item.scanningAccessories}');">查看</span>

            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<c:import url="../../../page_navigator.jsp">
    <c:param name="actionPath" value="ea/enterprisestamp/ea_getAuditStampList.jspa?search=${search}&pageNumber=${pageNumber}&enterpriseStamp.auditStatus=${enterpriseStamp.auditStatus}">
    </c:param>
</c:import>
<!--添加窗口 -->
<form name="addForm" id="addForm" method="post">
    <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
        <input type="submit" name="submit" style="display:none"/>
        <div class="drag">
            驳回
            <div class="close">
            </div>
        </div>

        <table width="100%" id="addTable">
            <tr>
                <td align="right">驳回原因：</td>
                <td>
                    <textarea rows="10" cols="60" id="rejectReasons" autofocus="autofocus"></textarea>


                </td>

            </tr>


        </table>
        <div align="center">
            <input type="button" class="input-button" id="save" value=" 提交 "  style="margin: 10px;" />
        </div>
    </div>
</form>





<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
