<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cs" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库房信息1</title>
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript">
   var basePath = '<%=basePath%>';
   var pNumber=${pageNumber};
   var depotPID = '${depotID}';
var usetype='${param.usetype}';
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
</head>
<body>
<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择仓库</td></tr></table>
            <table class="table">
                <thead>
                    <tr>
                        <th width="40" align="center">
                           请选择
                        </th>
                        <th width="240" align="center">
                          仓库编码
                        </th>
                        <th width="240" align="center">
                          仓库名称
                        </th>
                        <th width="240" align="center">
                          使用状态
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${depotID}" align="center">
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${depotID}"/>
                            </td>
                            
                             <td>
                                <span id="depotCoding" style="as">${depotCoding}</span>
                            </td>
                               
                            <td>
                                <span id="depotName">${depotName}</span>
                            </td>
                            <td>
                                <span id="useState">${useState=='00'?'未启用':'已启用'}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <br/><br/>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=${pageNumber}&depotID=${depotID}&usetype=ck">
                </c:param>
            </c:import>
    </body>
</html>