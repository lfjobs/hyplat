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
        <title>工作态度管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/EnterpriseManner.js"></script>
        <script>
           var token = 0;
           var basePath = "<%=basePath%>";
           var mannerID = "";
          	var ppageNumber = ${pageNumber};
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
                        <th width="100" align="center">
                           企业名称
                        </th>
                        <th width="300" align="center">
                           工作态度
                        </th>
                        <th width="120" align="center">
                            工作态度注释
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${mannerID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${mannerID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="enterpriseName">${enterpriseName}</span>
                            </td>
                            <td>
                                <span id="mannerContent">${mannerContent}</span>
                            </td>
                            <td>
                                <span id="mannerNote">${mannerNote}</span>
                                <span id="mannerID" style="display:none">${mannerID}</span>
                                 <span id="mannerKey"  style="display:none">${mannerKey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/manner/ea_getEnterpriseMannerList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                 <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">工作态度信息
				    <div class="close"></div>
				  </div>
				  </div>
				    <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="196" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="134"  align="right">企业名称：</td>
                         <td width="124"><input type="text" id="enterpriseName" name="enterpriseManner.enterpriseName"/></td>
                         <td width="134" align="right">工作态度注释：</td>
                         <td width="124"><input type="text" id="mannerNote" name="enterpriseManner.mannerNote"/></td>
                       
                       </tr>
                       <tr>
                         <td width="134" height="123" align="right">工作态度：</td>
                         <td  colspan="3"><textarea class="ckTextLength" maxlength="250" name="enterpriseManner.mannerContent" cols="55" rows="5" id="mannerContent"></textarea></td>
                       </tr>
                       <tr>
                          <td height="30" colspan="5" align="center"><input name="enterpriseManner.mannerKey" id="mannerKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseManner.mannerID" id="mannerID" type="hidden" class="input"  size="20"/>
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
            <iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
    </body>
</html>