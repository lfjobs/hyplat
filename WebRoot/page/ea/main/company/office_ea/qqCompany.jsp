<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>QQ管理公司汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
                <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/company/office_ea/qqCompany.js"></script>
        <script type="text/javascript">
		 var  basePath = '<%=basePath%>';           
         var  pNumber = '${pageNumber}';  
         var  search = '${search}';
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            序号
                        </th>
                         <th width="80" align="center">
                            编号		
                        </th>
                         <th width="80" align="center">
                            姓名
                        </th>
                         <th width="200" align="center">
                         QQ号
                        </th>
                         <th width="200" align="center">
                           手机
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${qqID}"  >
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                            <td>
                                <span id="qqNum">${qqNum}</span>
                            </td>
                            <td>
                               <span id="staffID">${staffID}</span>
                            </td>
                            <td>
                                <span id="qqSequence">${qqSequence}</span>
                            </td>
                            <td>
                                <span id="qqTel">${qqTel}</span>
                                <span id="qqKey" style="display:none">${qqKey}</span>
                                <span id="qqID" style="display:none">${qqID}</span>
                                <span id="companyID" style="display:none">${companyID}</span>
                                <span id="organizationID" style="display:none">${organizationID}</span> 
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/qqcompany/ea_getQqCompanyList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
	
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 300px;right: 39%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            <div class="drag">
            <input type="submit" name="submit" style="display:none"/>
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="260px" id="cataffSearchTable"> 
                    <tr>
                        <td align="right">
                            QQ号:
						</td>
                        <td>
                        	 <input name="qq.qqSequence" id="qqSequence" />
                        </td>
                    </tr>
                </table>
            <div style="text-align: center;"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
    </body>
</html>