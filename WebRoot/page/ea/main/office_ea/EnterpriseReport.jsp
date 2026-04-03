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
        <title>企业纪实管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/EnterpriseReport.js"></script>
        <script>
             var basePath = "<%=basePath%>";
                var reportID = "";
             var ppageNumber = ${pageNumber};
             var token=0;
</script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
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
                        <tr id="${reportID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${reportID}"/>
                            </td>
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
                                <span id="reportID" style="display:none">${reportID}</span>
                                 <span id="reportKey"  style="display:none">${reportKey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/report/ea_getEnterpriseReportList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
      
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                  <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">企业纪实信息
				    <div class="close"></div>
				  </div>
				  </div>
				    <table width="699" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699"  border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="5" style="margin-top: 5px; margin-bottom: 5px;margin-left: 20px">
                       <tr>
                         <td  align="right">单位：</td>
                         <td ><input type="text" id="companyName" name="enterpriseReport.companyName"/></td>
                          <td  align="right">时间：</td>
                         <td ><input onfocus="date(this);" type="text" class="input" id="reportDate" name="enterpriseReport.reportDate"/></td>
                       </tr>
                       <tr>
                         <td   align="right">纪实名称：</td>
                         <td ><input type="text" id="reportName" name="enterpriseReport.reportName"/></td>
                          <td   align="right">纪实状况：</td>
                         <td ><input type="text" id="reportStatus" name="enterpriseReport.reportStatus"/></td>
                       </tr>
                       <tr>
                          <td align="right">纪实主题内容：</td>
                         <td  colspan="3"><textarea class="ckTextLength" maxlength="250" name="enterpriseReport.reportContent" cols="55" rows="3" id="reportContent"></textarea></td>
                       </tr>
                       <tr>
                         <td   align="right">备注：</td>
                         <td colspan="3"><textarea name="enterpriseReport.remark" class="ckTextLength" maxlength="250" cols="55" rows="3" id="remark"></textarea></td>
                       </tr>
                       <tr>
                          <td height="30" colspan="5" align="center"><input name="enterpriseReport.reportKey" id="reportKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseReport.reportID" id="reportID" type="hidden" class="input"  size="20"/>
                             <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                             <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                       </tr>
                     </table></td>
				   </tr>
				</table>
				</div>
        </div>
        <s:token></s:token>
            </form>
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>