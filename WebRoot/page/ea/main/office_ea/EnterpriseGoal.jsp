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
        <title>企业目标管理</title>
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
        <script src="<%=basePath%>js/ea/office_ea/EnterpriseGoal.js"></script>
        <script type="text/javascript">
        	 var token = 0;
	         var goalID = "";
			 var  basePath='<%=basePath%>';           
	         var  pNumber =${pageNumber};     
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
                        <th width="480" align="center">
                           企业目标内容
                        </th>
                        <th width="120" align="center">
                            企业目标注释
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${goalID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${goalID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="enterpriseName">${enterpriseName}</span>
                            </td>
                            <td>
                                <span id="goalContent">${goalContent}</span>
                            </td>
                            <td>
                                <span id="goalNote">${goalNote}</span>
                                <span id="goalID" style="display:none">${goalID}</span>
                                 <span id="goalKey"  style="display:none">${goalKey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/goal/ea_getEnterpriseGoalList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                 <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">企业目标信息
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="196" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="134"  align="right">企业名称：</td>
                         <td width="124"><input type="text" id="enterpriseName" name="enterpriseGoal.enterpriseName"/></td>
                         <td width="134" align="right">企业目标注释：</td>
                         <td width="124"><input type="text" id="goalNote" name="enterpriseGoal.goalNote"/></td>
                       
                       </tr>
                       <tr>
                         <td width="134" height="130" align="right">企业目标内容：</td>
                         <td  colspan="3"><textarea class="ckTextLength" maxlength="250" name="enterpriseGoal.goalContent" cols="55" rows="5" id="goalContent"></textarea></td>
                       </tr>
                       <tr>
                          <td height="30" colspan="5" align="center"><input name="enterpriseGoal.goalKey" id="goalKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseGoal.goalID" id="goalID" type="hidden" class="input"  size="20"/>
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
            <iframe name = "hidden" height="0" width="100%"></iframe>
    </body>
</html>