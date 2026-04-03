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
        <title>企业视频管理-公司</title>
         <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/company/office_ea/EnterpriseVideoCompany.js"></script>
        <script>
        	 var token = 0;
             var basePath = "<%=basePath%>";
             var ppageNumber =${pageNumber};
            var search = '${search}';
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
                        <th width="100" align="center">
                           名称
                        </th>
                        <th width="150" align="center">
                           录像主题描述
                        </th>
                        <th width="120" align="center">
                        摄制年度
                        </th>
                         <th width="150" align="center">
                        备注
                        </th>
                         <th width="120" align="center">
                       扫描附件
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
                                <span id="enName">${enName}</span>
                            </td>
                            <td>
                                <span id="enSubject">${enSubject}</span>
                            </td>
                             <td>
                                <span id="enDate">${enDate}</span>
                            </td>
                            <td>
                                <span id="mark">${mark}</span>
                            </td>
                            <td>
                                <s:if test="videoPath==null||videoPath==''">无</s:if>
                             <s:else>
                             <span id="videoPath"  onclick="lookImage('${videoPath}');"><a href="#">查看</a></span>
                             </s:else>
                                <span id="videoPath" style="display:none;">${videoPath}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/enterprisevideoC/ea_getEnterpriseVideoList.jspa?pageNumber=${pageNumber}">
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
                           录像名称：
                        </td>
                        <td>         
                           <input name="enterpriseVideo.enName" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                        主题：
                        </td>
                        <td>
                           <input name="enterpriseVideoenSubject" />
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
        <iframe name = "hidden" height="0" width="100%"></iframe>
    </body>
</html>