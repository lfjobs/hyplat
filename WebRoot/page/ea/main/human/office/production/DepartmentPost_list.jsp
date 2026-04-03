<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>岗位职责汇总列表</title>  
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %> 
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/DepartmentPost_list.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        
        <script>
            var pbasePath = "<%=basePath%>";
            var ppageNumber = ${pageNumber};
            var psearch = '${search}';
            var depPostID = "";
            var pnum = '${pageForm.pageNumber}';
            var comID = '${account.companyID}';
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
                        <th width="200" align="center">
                            公司名称
                        </th>
                        <th width="100" align="center">
                            所属部门
                        </th>
                        <th width="100" align="center">
                            职位名称
                        </th>
                        <th width="100" align="center">
                            职位编号
                        </th>
                        <th width="100" align="center">
                            辖员人数
                        </th>
                        <th width="100" align="center"align="center">
                            岗位定员
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <c:forEach var='arr' items="${pageForm.list}">
                        <tr id="${arr[0]}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${arr[0]}"/>
                            </td>
                             <td>
                                <span id="companyName">${arr[1]}</span>
                            </td>
                            <td>
                                <span id="organizationName">${arr[2]}</span>
                            </td>
                            <td>
                                <span id="postName">${arr[3]}</span>
                            </td>
                            <td>
                                <span id="postNum">${arr[4]}</span>
                            </td>
                            <td>
                                <span id="adminNum">${arr[5]}</span>
                            </td>
                            <td>
                                <span id="postSureNum">${arr[6]}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </c:forEach>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/departmentpost/ea_getDepartmentPostList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        
        <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
                  <tr>
                     <td align="right">
                         部门名称：                        
                     </td>
                     <td>
                     <select name="departmentPost.organizationID" id="organizationID">
                     <option value="">全部</option>
                     </select>
                     </td>
                  </tr>
                  <tr>
                   <td align="right">
                         职位名称：                      
                   </td>
                   <td>
                       <input name="departmentPost.postName" />
                    </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>