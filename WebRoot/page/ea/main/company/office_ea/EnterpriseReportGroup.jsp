<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>  
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>企业纪实管理-集团汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/company/office_ea/EnterpriseReportGroup.js"></script>
        <script>
             var basePath = "<%=basePath%>";
             var ppageNumber = ${pageNumber};
             var token=0;
             var  search = '${search}';
</script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="50" align="center">
                            序号
                        </th>
                        <th width="70" align="center">
                           时间
                        </th>
                        <th width="120" align="center">
                           单位
                        </th>
                        <th width="120" align="center">
                           纪实名称
                        </th>
                         <th width="240" align="center">
                           纪实主题内容
                        </th>
                         <th width="120" align="center">
                           纪实状况
                        </th>
                         <th width="140" align="center">
                           纪实备注
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                              <span id="reportDate" class="datas">${fn:substring(reportDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="companyName">${companyName}</span>
                            </td>
                             <td>
                                <span id="reportName">${reportName}</span>
                            </td>
                             <td>
                                <span id="reportContent">${reportContent}</span>
                            </td>
                             <td>
                                <span id="reportStatus">${reportStatus}</span>
                            </td>
                            <td>
                                <span id="remark">${remark}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/reportgroup/ea_getEnterpriseReportList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
             <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post" name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                          纪实时间：
                        </td>
                        <td>         
                           <input name="enterpriseReport.reportDate" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                        纪实名称：
                        </td>
                        <td>
                           <input name="enterpriseReport.reportName" />
                        </td>
                    </tr>
                </table>
                <div style="text-align:center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                    <input type="submit" name="submit" style="display:none"/>
                </div>
            </form>
        </div>
        <s:token></s:token>
  </body>
</html>
