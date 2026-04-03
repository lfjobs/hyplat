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
        <title>管理理念管理--集团汇总</title>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/company/office_ea/EnterpriseManageGroup.js"></script> <script>
             var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
            var token=0;
            var search =' ${search}';
</script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
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
                        <tr>
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
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/managegroup/ea_getEnterpriseManageList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
     
        <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post"  name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                   <tr>
                        <td>
                           管理理念：
                        </td>
                        <td>         
                           <input name="enterpriseManage.manageIdea" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                          理念内容：
                        </td>
                        <td>
                           <input name="enterpriseManage.manageContent" />
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