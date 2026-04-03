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
        <title>岗位职责汇总列表</title>
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
         <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/ResponsibilitiesSummary.js">
        </script>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script> 
       
        <script>
            var pbasePath = "<%=basePath%>";
            var ppageNumber = ${pageNumber};
            var psearch = '${search}';
            var responsibilitiesID = "";
            var treeID = '<%=session.getAttribute("organizationID")%>';
            var treeName = parent.tree.getItemText(treeID);
 	        var treePID = parent.tree.getParentId(treeID);
         	var treePName = parent.tree.getItemText(treePID);
            var tlist = "${stafflist[0].staffID}";
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
                            部门名称
                        </th>
                         <th width="100" align="center">
                            员工编号
                        </th>
                        <th width="100" align="center">
                            员工姓名
                        </th>
                        <th width="100" align="center"align="center">
                            岗位职责编号
                        </th>
                        <th width="100" align="center">
                            岗位职责档案编号
                        </th>
                        <th width="100" align="center">
                            岗位起始时间
                        </th>
                        <th width="100" align="center">
                            岗位截止时间
                        </th>
                        <th width="100" align="center">
                            岗位名称 
                        </th>
                        <th width="100" align="center">
                            职务名称
                        </th>
                        <th width="100" align="center">
                            岗位情况管理
                        </th>
                        <th width="100" align="center">
                            工作单位名称
                        </th>
                        <th width="200" align="center">
                            直接行政上级 
                        </th>
                        <th width="200" align="center">
                           直接行政下级
                        </th>
                        <th width="100" align="center">
                            管理范围
                        </th>
                        <th width="100" align="center">
                            工作内容1 
                        </th>
                         <th width="100" align="center">
                            工作内容2 
                        </th>
                         <th width="100" align="center">
                            工作内容3 
                        </th>
                         <th width="100" align="center">
                            工作内容4 
                        </th>
                         <th width="100" align="center">
                            工作内容5
                        </th>
                        <th width="50" align="center">
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
                                <span id="companyIDName">${companyIDName}</span>
                            </td>
                            <td>
                                <span id="departmentIDName">${departmentIDName}</span>
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
                                <span id="startDate">${fn:substring(startDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="endDate">${fn:substring(endDate, 0, 10)}</span>
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
                                 <span>${departmentIDName}</span> <span style="display:none" id="departmentID">${departmentID}</span>
                            </td>
                            <td>
                                <span>${organizationPIDName}</span> <span style="display:none" id="organizationPID">${organizationPID}</span>
                            </td>
                            <td>
                                <span >${organizationCIDName}</span><span style="display:none" id="organizationCID">${organizationCID}</span>
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
                <c:param name="actionPath" value="ea/responsibilitiessummary/ea_getResponsibilitiesList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
<div class="jqmWindow jqmWindowcss1" style="top:1%;" id="jqModel">
 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
       <div class="drag">岗位职责管理
	    <div class="close"></div>
	    </div>
<input type="submit" name="submit" style="display:none"/>
 <div class="content">
  <div class="contentbannb">
  </div>
      <table width="885" height="420" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
  <tr>
    <td align="right" >员工编号：</td>
    <td><input type="text" class="model3" id="staffCode" readonly="readonly" name="staffCode"/></td>
    <td align="right">员工姓名：</td>
    <td><s:select list="%{#request.stafflist}" id="staffID"   listKey="staffID" listValue="staffName" name="responsibilities.staffID" theme="simple"></s:select></td>
    <td width="180" rowspan="6" align="center"><img id="pic" width="96" height="128" /></td>
  </tr>
  <tr>
    <td width="135" align="right">岗位职责编号：</td>
    <td width="227"><input name="responsibilities.responsibilitiesNum" id="responsibilitiesNum" type="text" class="model3"  size="20"/></td>
    <td width="152" align="right">岗位职责档案编号：</td>
    <td width="191"><input name="responsibilities.recordNum" id="recordNum" type="text" class="model3"  size="20"/></td>
    </tr>
  <tr>
    <td align="right">岗位起始时间：</td>
    <td><input name="responsibilities.startDate" id="startDate" onfocus="date(this);"  type="text" class="model3"  size="20"/></td>
    <td align="right">岗位截止时间：</td>
    <td><input name="responsibilities.endDate" id="endDate"  onfocus="date(this);" type="text" class="model3" size="20"/></td>
    </tr>
  <tr>
    <td align="right">岗位名称：</td>
    <td><input name="responsibilities.postName" id="postName" type="text" class="model3"  size="20"/></td>
    <td align="right">职务名称：</td>
    <td><input name="responsibilities.dutyName" type="text" class="model3" id="dutyName"  size="20"/></td>
    </tr>
  <tr>
    <td height="19" align="right">岗位情况管理：</td>
    <td><select name="responsibilities.postmanage" id="postmanage" class="select" >
      <option value="专岗">专岗</option>
      <option value="兼岗">兼岗</option>
    </select></td>
    <td align="right">文件号：</td>
    <td><input name="responsibilities.fileNum" type="hidden" class="fileNum" id="fileNum"/>
        <input name="photo" type="file" contentEditable="false" class="input" size="15" />    </td>
    </tr>
  <tr>
    <td align="right">工作单位名称：</td>
    <td><input name="responsibilities.companyName"  type="text" class="model3" id="companyName" size="20"/></td>
    <td align="right">部门名称：</td>
    <td><select id="departmentID" name="responsibilities.departmentID" >
    </select>    </td>
    </tr>
  <tr>
    <td align="right">直接行政上级：</td>
    <td><select id="organizationPID" name="responsibilities.organizationPID" >
    </select>    </td>
    <td align="right">直接行政下级：</td>
    <td><select id="organizationCID" name="responsibilities.organizationCID" >
    </select></td>
    <td width="180" align="center">员工头像</td>
  </tr>
  <tr>
    <td align="right">管理范围：</td>
    <td colspan="4"><textarea name="responsibilities.managesCope"  style="width: 730px;height: 30px;"  class="model3" id="managesCope"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容1：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent1"  style="width: 730px;height: 30px;"  class="model3" id="jobContent1"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容2：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent2"  style="width: 730px;height: 30px;"  class="model3" id="jobContent2"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容3：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent3" " style="width: 730px;height: 30px;"  class="model3" id="jobContent3"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容4：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent4"  style="width: 730px;height: 30px;"  class="model3" id="jobContent4"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容5：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent5"  style="width: 730px;height: 30px;"  class="model3" id="jobContent5"></textarea></td>
    </tr>
     <tr>
    <td colspan="5" align="center">
         <input name="responsibilities.responsibilitiesID" id="responsibilitiesID" type="hidden" class="input"  size="20"/>
         <input type="button"   class="input-button JQueryPrint" style="cursor:pointer;width:80px;" value="打印预览" />
        <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /> 
    </td>
    </tr>
</table>
</div>
    <s:token></s:token>
</form>
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
                        <td width="123" align="right">
                            员工编号：        </td>
<td width="261">
<input name="staffResponsibilities.staffCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                            员工姓名：                      </td>
                  <td>
                            <input name="staffResponsibilities.staffName" />
                        </td>
                    </tr>
					<tr>
                        <td align="right">
                            岗位名称：                        </td>
                  <td>
                            <input name="staffResponsibilities.postName" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="staffResponsibilities.departmentID" ></select>
                        </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
          <div class="jqmWindow" style="width: 400px;right: 25%;" id="newamount">
                                            <div class="drag">
                                                文件：
                                            </div>
                                           <img id="wenjian" width="350" height="400"/>
                                            
         </div>
    </body>
</html>