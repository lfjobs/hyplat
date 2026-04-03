<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>岗位职责列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script>
               var responsibilitiesID = "";
                var b=true;
                var token = 0;
                  var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
            var psearch='${search}';
            var staffID="${staffID}";
            var treeID = '<%=session.getAttribute("organizationID")%>';
	        var treeName = parent.parent.frames["leftFrame"].tree.getItemText(treeID);
	        var treePID = parent.parent.frames["leftFrame"].tree.getParentId(treeID);
	        var treePName =parent.parent.frames["leftFrame"].tree.getItemText(treePID);
            var notoken = 0;
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/office/personal_department/Responsibilities.js"></script>

	</head>
	<body >
	<form action="" name="cstaffForm" id="cstaffForm" method="post">
	<input type="submit" name="submit" style="display:none"/>
	      <input name="responsibilities.responsibilitiesID" id="responsibilitiesID" type="hidden" class="input"  size="20"/>
          <input name="staffID" id="staffID" value="${staffID}" type="hidden" class="input"  size="20"/>
	</form>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center"> 
                            请选择
                        </th>
                         <th width="60" align="center">
                            部门名称
                         </th>
                         <th width="60" align="center">
                            员工编号
                        </th>
                        <th width="60" align="center">
                            员工姓名
                        </th>
                        <th width="80" align="center">
                            岗位职责编号
                        </th>
                        <th width="120" align="center">
                            岗位职责档案编号
                        </th>
                        <th width="80" align="center">
                            岗位起始时间
                        </th>
                        <th width="80" align="center">
                            岗位截止时间
                        </th>
                        <th width="80" align="center">
                            岗位名称 
                        </th>
                        
                        <th width="80" align="center">
                            职务名称
                        </th>
                        <th width="80" align="center">
                            岗位情况管理
                        </th>
                        <th width="70" align="center">
                            工作单位名称
                        </th>
                        <th width="200" align="center">
                            直接行政上级 
                        </th>
                        <th width="200" align="center">
                           直接行政下级
                        </th>
                        <th width="120" align="center">
                            管理范围
                        </th>
                        <th width="120" align="center">
                            工作内容1 
                        </th>
                         <th width="120" align="center">
                            工作内容2 
                        </th>
                         <th width="120" align="center">
                            工作内容3 
                        </th>
                         <th width="120" align="center">
                            工作内容4 
                        </th>
                         <th width="120" align="center">
                            工作内容5
                        </th>
                        <th width="80" align="center">
                             文件号 
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${responsibilitiesID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${responsibilitiesID}"/>
                            </td>
                            <td>
                                 <span>${departmentIDName}</span>
                            </td>
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                               
                            <td>
                                <span id="responsibilitiesNum">${responsibilitiesNum}</span>
                            </td>
                            <td>
                                <span id="recordNum">${recordNum}</span>
                            </td>
                            <td>
                                <span id="startDate" class="datas">${fn:substring(startDate,0,10)}</span>
                            </td>
                            <td>
                                <span id="endDate" class="datas">${fn:substring(endDate,0,10)}</span>
                            </td>
                            <td>
                                <span id="postName">${postName}</span>
                            </td>
                            <td>
                                <span id="dutyName">${dutyName}</span>
                            </td>
                            <td>
                                <span id="postmanage">${postmanage}</span>
                            </td>
                            <td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span>${organizationPIDName}</span>
                            </td>
                            <td>
                                <span >${organizationCIDName}</span>
                            </td>
                            <td>
                                <span id="managesCope">${managesCope}</span>
                            </td>
                            <td>
                                 <span id="jobContent1">${jobContent1}</span>
                                 <span id="photo" style="display:none">${photo}</span>
                                 <span id="staffID" style="display:none">${staffID}</span>
                                 <span id="responsibilitiesKey" style="display:none">${responsibilitiesKey}</span>
                                 <span id="responsibilitiesID" style="display:none">${responsibilitiesID}</span>
                                 <span id="departmentID" style="display:none">${departmentID}</span>
                                 <span id="organizationPID" style="display:none">${organizationPID}</span>
                                 <span id="organizationCID" style="display:none">${organizationCID}</span>
                                 <span id="fileNum" style="display:none">${fileNum}</span>
                            </td>
                             <td>
                                 <span id="jobContent2">${jobContent2}</span>
                            </td>
                                <td>
                                 <span id="jobContent3">${jobContent3}</span>
                            </td>
                                <td>
                                <span id="jobContent4">${jobContent4}</span>
                            </td>
                               <td>
                                <span id="jobContent5">${jobContent5}</span>
                            </td>
                             <td >
                              <s:if test="fileNum==null||fileNum==''">无</s:if>
                             <s:else>
                                <span id="look"  onclick="lookImage('${fileNum}');"><a href="#">查看</a></span>
                            </s:else>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/responsibilities/ea_getResponsibilitiesList.jspa?search=${search}&pageNumber=${pageNumber}&staffID=${staffID}">
                </c:param>
            </c:import>
        </div>
       <!--搜索窗口 -->
        <form name="postSearchForm" id="postSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
					<tr>
                        <td align="right">
                            岗位名称： </td>
                       <td>
                            <input name="staffResponsibilities.postName" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="staffResponsibilities.departmentID" >
                          <option value="">全部</option>
                        </select>
                        </td>
                    </tr>
                </table>
            <div align="center">
              <input name="staffID" value="${staffID}" type="hidden"/>
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
          
        </div>
          </form>
          <div class="jqmWindow" style="width: 400px;right: 25%;" id="newamount">
                                            <div class="drag">
                                                文件：
                                            </div>
                                            
                                           <img id="wenjian" width="350" height="400"/>
                                            
         </div>
          <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
    </body>
</html>