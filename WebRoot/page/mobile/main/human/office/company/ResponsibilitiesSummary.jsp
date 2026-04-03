<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
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
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/company/ResponsibilitiesSummary.js"></script>
         <script type="text/javascript"> 
       var  basePath='<%=basePath%>';           
       var  search='${search}';    
       var  companyID='${companyID}'; 
       var  pNumber = ${pageNumber};	
       var cstaffID="${stafflist[0].staffID}";
     </script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
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
                         <th width="80" align="center">
                            部门名称
                        </th>
                         <th width="80" align="center">
                            员工编号
                        </th>
                        <th width="80" align="center">
                            员工姓名
                        </th>
                        <th width="100" align="center">
                            岗位职责编号
                        </th>
                        <th width="150" align="center">
                            岗位职责档案编号
                        </th>
                        <th width="80" align="center">
                            岗位起始时间
                        </th>
                        <th width="80" align="center">
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
                        <th width="80" align="center">
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
                        <th width="60" align="center">
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
                                <span id='companyID'>${companyIDName}</span>
                            </td>
                             <td>
                                 <span id='departmentID'>${departmentIDName}</span>
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
                                <span id="startDate" class="datas">${startDate}</span>
                            </td>
                            <td>
                                <span id="endDate" class="datas">${endDate}</span>
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
                            	<span id='organizationPID'>${organizationPIDName}</span>
                            </td>
                            <td>
                            	<span id='organizationCID'>${organizationCIDName}</span>
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
                              <s:if test="fileNum==null">无</s:if>
                             <s:else>
                                <span id="look"  onclick="lookfileNum('${fileNum}');"><a href="#">查看</a></span>
                            </s:else>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/responsibilitiessummary/ea_getResponsibilitiesListSummary.jspa?pageNumber=${pageNumber}&search=${search}&companyID=${companyID}">
                </c:param>
            </c:import>
        </div>
        <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
<div class="jqmWindow jqmWindowcss1" style="width: 900px;top:0%" id="jqModel">
 
       <div class="drag">岗位职责管理
	    <div class="close"></div>
	    </div>
<input type="submit" name="submit" style="display:none"/>
 <div class="content">
  <div class="contentbannb">
  </div>
      <table width="885" height="400" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;"><tr><td><table width="885" height="340" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
  <tr>
    <td align="right">员工编号：</td>
    <td><input type="text" id="staffCode" readonly="readonly" name="staffCode"/></td>
    <td align="right">员工姓名：</td>
    <td><input type="text" id="staffName" readonly="readonly" name="staffName"/></td>
    <td width="180" rowspan="6" align="center"><img src="" id="pic" width="96" height="128" /></td>
  </tr>
  <tr>
    <td width="135" align="right">岗位职责编号：</td>
    <td width="227"><input name="responsibilities.responsibilitiesNum" id="responsibilitiesNum" type="text" class="input"  size="20"/></td>
    <td width="152" align="right">岗位职责档案编号：</td>
    <td width="191"><input name="responsibilities.recordNum" id="recordNum" type="text" class="input"  size="20"/></td>
    </tr>
  <tr>
    <td align="right">岗位起始时间：</td>
    <td><input name="responsibilities.startDate" id="startDate"  type="text" class="input"  size="20"/></td>
    <td align="right">岗位截止时间：</td>
    <td><input name="responsibilities.endDate" id="endDate"   type="text" class="input" size="20"/></td>
    </tr>
  <tr>
    <td align="right">岗位名称：</td>
    <td><input name="responsibilities.postName" id="postName" type="text" class="input"  size="20"/></td>
    <td align="right">职务名称：</td>
    <td><input name="responsibilities.dutyName" type="text" class="input" id="dutyName"  size="20"/></td>
    </tr>
  <tr>
    <td height="19" align="right">岗位情况管理：</td>
    <td><input type="text" id="postmanage" readonly="readonly" name="staffCode"/></td>
    
    </tr>
  <tr>
    <td align="right">工作单位名称：</td>
    <td><input name="responsibilities.companyName"  type="text" class="input" id="companyName" size="20"/></td>
    <td align="right">部门名称：</td>
    <td> <input name="responsibilities.companyName"  type="text" class="input" id="departmentID" size="20"/>  </td>
    </tr>
  <tr>
    <td align="right">直接行政上级：</td>
    <td><input name="responsibilities.companyName"  type="text" class="input" id="organizationPID" size="20"/>  </td>
    <td align="right">直接行政下级：</td>
    <td><input name="responsibilities.companyName"  type="text" class="input" id="organizationCID" size="20"/></td>
    <td width="180" align="center">员工头像</td>
  </tr>
  <tr>
    <td align="right">管理范围：</td>
    <td colspan="4"><textarea name="responsibilities.managesCope" cols="80" rows="2" class="input" id="managesCope"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容1：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent1" cols="80" rows="2" class="input" id="jobContent1"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容2：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent2" cols="80" rows="2" class="input" id="jobContent2"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容3：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent3" cols="80" rows="2" class="input" id="jobContent3"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容4：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent4" cols="80" rows="2" class="input" id="jobContent4"></textarea></td>
    </tr>
    <tr>
    <td align="right">工作内容5：</td>
    <td colspan="4"><textarea name="responsibilities.jobContent5" cols="80" rows="2" class="input" id="jobContent5"></textarea></td>
    </tr>
  <tr>
</table></td>
        </tr>
</table>
</div> 
</div>
<s:token></s:token>
</form>
        <!--搜索窗口 -->
         <form name="postSearchForm" id="postSearchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width:500px;top: 10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table width="421" height="153" id="cataffSearchTable">
<tr>
                        <td width="118" align="right">
                            员工编号：                        </td>
                        <td width="314">
                          <input name="staffResponsibilities.staffCode" />
                        </td>
                  </tr>
                    <tr>
                        <td align="right">
                            员工姓名：                        </td>
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
                            公司名称：                        </td>
                      <td>
                        <select id="deptID" name="staffResponsibilities.companyID" ><option value="">请选择公司</option></select>
                        </td>	
                  </tr>
                  <tr>
                   <td align="right">
                            部门名称：                        </td>
                      <td width="314">
                        <select id="orgID" name="staffResponsibilities.departmentID" ><option value="">请选择公司</option></select>
                    </td>
                  </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
                  </div>
            </form>
    </body>
</html>