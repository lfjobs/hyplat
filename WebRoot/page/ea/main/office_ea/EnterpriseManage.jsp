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
        <title>管理理念管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath %>css/ea/validate.css" rel="stylesheet" type="text/css"  />
        
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/EnterpriseManage.js"></script>
        <script>
             var basePath = "<%=basePath%>";
                var manageID = "";
            var ppageNumber=${pageNumber};
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
                         <th width="60" align="center">
                            序号
                        </th>
                        <th width="100" align="center">
                           管理理念
                        </th>
                        <th width="400" align="center">
                           管理理念内容
                        </th>
                        <th width="120" align="center">
                            管理理念注释
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${manageID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${manageID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="manageIdea">${manageIdea}</span>
                            </td>
                            <td>
                                <span id="manageContent">${manageContent}</span>
                            </td>
                            <td>
                                <span id="manageNote">${manageNote}</span>
                                <span id="manageID" style="display:none">${manageID}</span>
                                 <span id="manageKey"  style="display:none">${manageKey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/manage/ea_getEnterpriseManageList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
     
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                  <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">管理理念信息
				    <div class="close"></div>
				  </div>
				  </div>
				 <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="196" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="134" height="32" align="right">管理理念：</td>
                         <td width="123"><input type="text" id="manageIdea" name="enterpriseManage.manageIdea"/></td>
                         <td width="134" align="right">管理理念注释：</td>
                         <td width="123"><input type="text" id="manageNote" name="enterpriseManage.manageNote"/></td>
                       
                       </tr>
                       <tr>
                         <td width="134" align="right">管理理念内容：</td>
                         <td  colspan="3"><textarea name="enterpriseManage.manageContent" cols="55" maxlength="250" class="input ckTextLength" rows="5" id="manageContent"></textarea></td>
                       </tr>
                       <tr>
                          <td height="30" colspan="5" align="center"><input name="enterpriseManage.manageKey" id="manageKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseManage.manageID" id="manageID" type="hidden" class="input"  size="20"/>
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